package com.ansill.tesla.api.high;

import com.ansill.lock.autolock.ALock;
import com.ansill.lock.autolock.AutoLock;
import com.ansill.tesla.api.low.exception.ReAuthenticationException;
import com.ansill.tesla.api.low.exception.VehicleIDNotFoundException;
import com.ansill.tesla.api.med.Client;
import com.ansill.tesla.api.med.model.AccountCredentials;
import com.ansill.validation.Validation;

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

    /** Time before credentials expires */
    @Nonnull
    private static final Duration DEFAULT_TIME_OFFSET_BEFORE_REFRESH = Duration.ofMinutes(2);

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

    /** Current "good" credentials */
    @Nonnull
    private AccountCredentials credentials;

    /** Closed flag */
    @Nonnull
    private final AtomicBoolean closed = new AtomicBoolean(false);

    /** Refresh subscription */
    @Nullable
    private RefreshSubscription refreshSubscription;

    /**
     * Creates Tesla Account and starts the refresh timer
     *
     * @param client                   low level client
     * @param credentials              good credentials
     * @param fastChangingDataLifetime lifetime for fast-changing data
     * @param slowChangingDataLifetime lifetime for slow-changing data
     */
    Account(
            @Nonnull Client client,
            @Nonnull AccountCredentials credentials,
            @Nonnull AtomicReference<AtomicReference<Duration>> fastChangingDataLifetime,
            @Nonnull AtomicReference<AtomicReference<Duration>> slowChangingDataLifetime
    ){
        this(client, credentials, fastChangingDataLifetime, slowChangingDataLifetime, null);
    }


    /**
     * Creates Tesla Account and starts the refresh timer
     *
     * @param client                   low level client
     * @param credentials              good credentials
     * @param fastChangingDataLifetime lifetime for fast-changing data
     * @param slowChangingDataLifetime lifetime for slow-changing data
     */
    Account(
            @Nonnull Client client,
            @Nonnull AccountCredentials credentials,
            @Nonnull AtomicReference<AtomicReference<Duration>> fastChangingDataLifetime,
            @Nonnull AtomicReference<AtomicReference<Duration>> slowChangingDataLifetime,
            @Nullable RefreshSubscription subscription
    ){
        this.client = client;
        this.credentials = credentials;
        this.fastChangingDataLifetime = fastChangingDataLifetime;
        this.slowChangingDataLifetime = slowChangingDataLifetime;
        this.refreshSubscription = subscription;
        resetTimer();
    }

    public void setGlobalFastChangingDataLifetime(@Nonnull Duration duration){
        fastChangingDataLifetime.set(new AtomicReference<>(duration));
    }

    public void setGlobalSlowChangingDataLifetime(@Nonnull Duration duration){
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
        var delay = Duration.between(Instant.now(), credentials.getExpirationTime().minus(DEFAULT_TIME_OFFSET_BEFORE_REFRESH));

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

        // Ensure that it's not closed
        if(this.closed.get()) throw new IllegalStateException("Account is closed!");

        // Lock it
        try(var ignored = new ALock(rrwl.writeLock()).doLock()){

            // Refresh it and update response
            credentials = client.refreshToken(credentials.getRefreshToken());

            // Fire subscription if exists
            if(refreshSubscription != null) refreshSubscription.getConsumer().accept(credentials);

        }catch(ReAuthenticationException exception){

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
