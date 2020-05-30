package com.ansill.tesla.api.high;

import com.ansill.lock.autolock.ALock;
import com.ansill.lock.autolock.AutoLock;
import com.ansill.tesla.api.low.Client;
import com.ansill.tesla.api.low.model.AccountCredentials;
import com.ansill.tesla.api.raw.exception.ReAuthenticationException;
import com.ansill.tesla.api.raw.exception.VehicleIDNotFoundException;
import com.ansill.validation.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/** Tesla Account */
public class Account implements AutoCloseable{

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(Account.class);

  /** Time before credentials expires */
  @Nonnull
  private static final Duration DEFAULT_TIME_OFFSET_BEFORE_REFRESH = Duration.ofDays(1); // Lets not get things too close to the chest, 1 day left out of 45 days should be reasonable

  /** Low Level Client */
  @Nonnull
  private final Client client;

  /** RRWL to handle client refresh cycle */
  @Nonnull
  private final ReentrantReadWriteLock rrwl = new ReentrantReadWriteLock(true);

  /** Reference to the timer */
  @Nonnull
  private final AtomicReference<Timer> timer = new AtomicReference<>();

  /** Lifetime for fast-changing data */
  @Nonnull
  private final AtomicReference<AtomicReference<Duration>> fastChangingDataLifetime;

  /** Lifetime for fast-changing data */
  @Nonnull
  private final AtomicReference<AtomicReference<Duration>> slowChangingDataLifetime;

  /** Closed flag */
  @Nonnull
  private final AtomicBoolean closed = new AtomicBoolean(false);

  /** Current "good" credentials */
  @Nonnull
  private AccountCredentials credentials;

  /** Refresh subscription */
  @Nullable
  private RefreshSubscription refreshSubscription;

  @Nonnull
  private final AtomicReference<Duration> refreshDurationBeforeExpiration = new AtomicReference<>();

  /**
   * Creates Tesla Account and starts the refresh timer
   *
   * @param client                          low level client
   * @param credentials                     good credentials
   * @param fastChangingDataLifetime        lifetime for fast-changing data
   * @param slowChangingDataLifetime        lifetime for slow-changing data
   * @param refreshDurationBeforeExpiration duration before the expiration for Account to refresh the credentials
   */
  Account(
    @Nonnull Client client,
    @Nonnull AccountCredentials credentials,
    @Nonnull AtomicReference<AtomicReference<Duration>> fastChangingDataLifetime,
    @Nonnull AtomicReference<AtomicReference<Duration>> slowChangingDataLifetime,
    @Nullable RefreshSubscription subscription,
    @Nullable Duration refreshDurationBeforeExpiration
  ){
    this.client = client;
    this.credentials = credentials;
    this.fastChangingDataLifetime = fastChangingDataLifetime;
    this.slowChangingDataLifetime = slowChangingDataLifetime;
    this.refreshSubscription = subscription;
    this.refreshDurationBeforeExpiration.set(refreshDurationBeforeExpiration !=
                                             null ? refreshDurationBeforeExpiration : DEFAULT_TIME_OFFSET_BEFORE_REFRESH);
    resetTimer();
  }

  /**
   * Sets lifetime in duration for fast-changing data to be cached in the memory before it's purged
   *
   * @param duration duration
   */
  public void setDefaultTimeOffsetBeforeRefresh(@Nonnull Duration duration){
    refreshDurationBeforeExpiration.set(Validation.assertNonnull(duration, "duration"));
    resetTimer();
  }

  /**
   * Sets lifetime in duration for fast-changing data to be cached in the memory before it's purged
   *
   * @param duration lifetime in duration
   */
  public void setFastChangingDataLifetime(@Nonnull Duration duration){
    fastChangingDataLifetime.set(new AtomicReference<>(duration));
  }

  /**
   * Sets lifetime in duration for slow-changing data to be cached in the memory before it's purged
   *
   * @param duration lifetime in duration
   */
  public void setSlowChangingDataLifetime(@Nonnull Duration duration){
    slowChangingDataLifetime.set(new AtomicReference<>(duration));
  }

  /**
   * Returns access token
   *
   * @return token
   */
  @Nonnull
  String getToken(){
    return credentials.getAccessToken();
  }

  /**
   * Creates readlock for other dependent objects to claim to use the client. Used so that credentials can be safely changed during nobody is using it
   *
   * @return readlock
   */
  @Nonnull
  AutoLock getReadLock(){
    return new ALock(rrwl.readLock());
  }

  /** Resets the timer so it will refresh credentials before its expiry time */
  private void resetTimer(){

    // Log
    LOGGER.debug("Setting up refresh timer");

    // Set up TimerTask
    TimerTask task = new TimerTask(){
      @Override
      public void run(){
        try{
          refresh();
        }catch(ReAuthenticationException e){
          e.printStackTrace(); // TODO!!
        }
      }
    };

    // Figure out the delay
    var delay = Duration.between(
      Instant.now(),
      credentials.getExpirationTime().minus(refreshDurationBeforeExpiration.get())
    );

    // Log the delay
    LOGGER.debug("The delay until new firing event is {}", delay);

    // If delay is not positive, fire it now
    if(delay.toMillis() <= 0){
      try{
        refresh();
      }catch(ReAuthenticationException e){
        e.printStackTrace(); // TODO!!
      }
      return;
    }

    // Create new timer and schedule it
    var timer = new Timer();
    timer.schedule(task, delay.toMillis());

    // Cancel the timer if previously set
    timer = this.timer.getAndSet(timer);
    if(timer != null) timer.cancel();

  }

  /**
   * Subscribes on account credentials refresh
   *
   * @param consumer consumer that consumes new credentials
   * @param onError  runnable that runs when there's an error
   */
  public void subscribeToCredentialsRefresh(
    @Nonnull Consumer<AccountCredentials> consumer,
    @Nonnull Consumer<ReAuthenticationException> onError
  ){
    this.refreshSubscription = new RefreshSubscription(
      Validation.assertNonnull(consumer),
      Validation.assertNonnull(onError)
    );
  }

  /**
   * Refreshes the credentials
   *
   * @throws ReAuthenticationException thrown if re-authentication fails
   */
  private void refresh() throws ReAuthenticationException{

    // Log it
    LOGGER.debug("Attempting to refresh");

    // Ensure that it's not closed
    if(this.closed.get()) throw new IllegalStateException("Account is closed!");

    // Lock it
    try(var ignored = new ALock(rrwl.writeLock()).doLock()){

      // Refresh it and update response
      credentials = client.refreshToken(credentials.getRefreshToken());

      // Log the successful refresh
      LOGGER.debug("Refresh is successful");

      // Fire subscription if exists
      if(refreshSubscription != null) refreshSubscription.getConsumer().accept(credentials);

    }catch(ReAuthenticationException exception){

      // Log the successful refresh
      LOGGER.debug("Exception has been thrown during attempting to refresh");

      // Fire subscription if exists
      if(refreshSubscription != null) refreshSubscription.getOnError().accept(exception);

      // Resume the exception throwing
      throw exception;
    }

    // Reset the timer
    resetTimer();

  }

  @Nonnull
  public Set<Vehicle> getVehicles(){

    // Perform it
    return performOnClient(client -> client.getVehicles(credentials.getAccessToken())
                                           .stream()
                                           .map(vehicle -> Vehicle.convert(vehicle, this))
                                           .collect(Collectors.toUnmodifiableSet())
    );
  }

  @Nonnull
  public Optional<Vehicle> getVehicleByName(@Nonnull String name){

    // Assert name
    Validation.assertNonemptyString(name);

    // Perform it
    return performOnClient(client -> client.getVehicles(credentials.getAccessToken())
                                           .stream()
                                           .map(vehicle -> Vehicle.convert(vehicle, this))
                                           .filter(item -> item.getName().equals(name))
                                           .findAny()
    );
  }

  @Nonnull
  public Optional<Vehicle> getVehicleByID(@Nonnull String id){

    // Assert id
    Validation.assertNonemptyString(id);

    // Perform it
    return performOnClient(client -> client.getVehicle(credentials.getAccessToken(), id)
                                           .map(item -> Vehicle.convert(item, this)));
  }

  @Nonnull
  public Optional<Vehicle> getVehicleByVIN(@Nonnull String vin){

    // Assert vin
    Validation.assertNonemptyString(vin);

    // Perform it
    return performOnClient(client -> client.getVehicles(credentials.getAccessToken())
                                           .stream()
                                           .map(vehicle -> Vehicle.convert(vehicle, this))
                                           .filter(item -> item.getVIN().equals(vin))
                                           .findAny()
    );
  }

  /**
   * Returns lifetime of fast-changing data
   *
   * @return lifetime reference
   */
  @Nonnull
  AtomicReference<AtomicReference<Duration>> getFastChangingDataLifetime(){
    return fastChangingDataLifetime;
  }

  /**
   * Returns lifetime of slow-changing data
   *
   * @return lifetime reference
   */
  @Nonnull
  AtomicReference<AtomicReference<Duration>> getSlowChangingDataLifetime(){
    return slowChangingDataLifetime;
  }

  /**
   * Performs a function on client
   *
   * @param function function
   * @param <T>      return value type
   * @return return value
   */
  <T> T performOnClient(@Nonnull Function<Client,T> function){

    // Ensure that it's not closed
    if(this.closed.get()) throw new IllegalStateException("Account is closed!");

    // Lock it
    try(var ignored = this.getReadLock().doLock()){

      // Run it
      return function.apply(this.client);

    }
  }

  /**
   * Performs a function on client
   *
   * @param function function
   * @param <T>      return value type
   * @return return value
   * @throws VehicleIDNotFoundException thrown if vehicle cannot be found
   */
  <T> T performOnClientWithVehicleException(@Nonnull FunctionWithVehicleException<Client,T> function)
  throws VehicleIDNotFoundException{

    // Ensure that it's not closed
    if(this.closed.get()) throw new IllegalStateException("Account is closed!");

    // Lock it
    try(var ignored = this.getReadLock().doLock()){

      // Run it
      return function.apply(this.client);

    }
  }

  @Override
  public void close(){
    if(!closed.compareAndSet(false, true)) return;
    var timer = this.timer.getAndSet(null);
    if(timer != null) timer.cancel();
  }
}
