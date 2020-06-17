package com.ansill.tesla.api.high;

import com.ansill.tesla.api.low.model.AccountCredentials;
import com.ansill.tesla.api.model.ClientBuilder;
import com.ansill.tesla.api.raw.exception.AuthenticationException;
import com.ansill.tesla.api.raw.exception.ReAuthenticationException;
import com.ansill.validation.Validation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;

/** Highly-Opinionated client */
public final class Client{

  /** Default fast changing data lifetime - used to prevent too-frequent polling */
  private static final Duration DEFAULT_FAST_CHANGING_DATA_LIFETIME = Duration.ofMillis(100); // 1/10 seconds

  /** Default slow changing data lifetime - used to prevent too-frequent polling */
  private static final Duration DEFAULT_SLOW_CHANGING_DATA_LIFETIME = Duration.ofMinutes(1);

  /** Low-level client */
  @Nonnull
  private final com.ansill.tesla.api.low.Client client;

  /** Lifetime in duration for fast-changing data to be cached in the memory before it's purged */
  @Nonnull
  private final AtomicReference<Duration> fastChangingDataLifetime = new AtomicReference<>();

  /** Lifetime in duration for slow-changing data to be cached in the memory before it's purged */
  @Nonnull
  private final AtomicReference<Duration> slowChangingDataLifetime = new AtomicReference<>();

  /**
   * Constructor that constructs high-level client using medium-level client
   *
   * @param client                   low-level client
   * @param fastChangingDataLifetime Lifetime in duration for fast-changing data to be cached in the memory before it's purged
   * @param slowChangingDataLifetime Lifetime in duration for slow-changing data to be cached in the memory before it's purged
   */
  private Client(
    @Nonnull com.ansill.tesla.api.low.Client client,
    @Nullable Duration fastChangingDataLifetime,
    @Nullable Duration slowChangingDataLifetime
  ){
    this.client = client;
    this.fastChangingDataLifetime.set(fastChangingDataLifetime !=
                                      null ? fastChangingDataLifetime : DEFAULT_FAST_CHANGING_DATA_LIFETIME);
    this.slowChangingDataLifetime.set(slowChangingDataLifetime !=
                                      null ? slowChangingDataLifetime : DEFAULT_SLOW_CHANGING_DATA_LIFETIME);
  }

  /**
   * Creates builder
   *
   * @return builder
   */
  @Nonnull
  public static com.ansill.tesla.api.high.Client.Builder builder(){
    return new com.ansill.tesla.api.high.Client.Builder();
  }

  /**
   * Sets lifetime in duration for fast-changing data to be cached in the memory before it's purged
   *
   * @param duration lifetime in duration
   */
  public void setFastChangingDataLifetime(@Nonnull Duration duration){
    fastChangingDataLifetime.set(Validation.assertNonnull(duration, "duration"));
  }

  /**
   * Sets lifetime in duration for slow-changing data to be cached in the memory before it's purged
   *
   * @param duration lifetime in duration
   */
  public void setSlowChangingDataLifetime(@Nonnull Duration duration){
    slowChangingDataLifetime.set(Validation.assertNonnull(duration, "duration"));
  }

  /** Resets lifetime in duration for fast-changing data to default */
  public void resetFastChangingDataLifetime(){
    fastChangingDataLifetime.set(DEFAULT_FAST_CHANGING_DATA_LIFETIME);
  }

  /** Resets lifetime in duration for slow-changing data to default */
  public void resetSlowChangingDataLifetime(){
    slowChangingDataLifetime.set(DEFAULT_SLOW_CHANGING_DATA_LIFETIME);
  }

  /**
   * Authenticates to tesla account
   *
   * @param emailAddress email address
   * @param password     password
   * @return optional object that may contain the tesla account. If it is empty, then authentication has failed
   */
  @Nonnull
  public Optional<Account> authenticate(@Nonnull String emailAddress, @Nonnull String password){
    try{
      return Optional.of(new Account(
        client,
        client.authenticate(emailAddress, password),
        new AtomicReference<>(fastChangingDataLifetime),
        new AtomicReference<>(slowChangingDataLifetime),
        null,
        null
      ));
    }catch(AuthenticationException e){
      return Optional.empty();
    }
  }

  /**
   * Authenticates to tesla account
   *
   * @param emailAddress          email address
   * @param password              password
   * @param refreshOffsetDuration duration of offset before credential expires for service to attempt to refresh
   * @return optional object that may contain the tesla account. If it is empty, then authentication has failed
   */
  @Nonnull
  public Optional<Account> authenticate(
    @Nonnull String emailAddress,
    @Nonnull String password,
    @Nonnull Duration refreshOffsetDuration
  ){
    refreshOffsetDuration = Validation.assertNonnull(refreshOffsetDuration, "refreshOffsetDuration");
    try{
      return Optional.of(new Account(
        client,
        client.authenticate(emailAddress, password),
        new AtomicReference<>(fastChangingDataLifetime),
        new AtomicReference<>(slowChangingDataLifetime),
        null,
        refreshOffsetDuration
      ));
    }catch(AuthenticationException e){
      return Optional.empty();
    }
  }

  /**
   * Authenticates to tesla account
   *
   * @param emailAddress email address
   * @param password     password
   * @param consumer     consumer that consumes new credentials
   * @param onError      runnable that runs when there's an error
   * @return optional object that may contain the tesla account. If it is empty, then authentication has failed
   */
  @Nonnull
  public Optional<Account> authenticate(
    @Nonnull String emailAddress,
    @Nonnull String password,
    @Nonnull Consumer<AccountCredentials> consumer,
    @Nonnull Consumer<ReAuthenticationException> onError
  ){
    try{

      // Refresh now
      AccountCredentials newCred = client.authenticate(emailAddress, password);
      Validation.assertNonnull(consumer, "consumer").accept(newCred);

      return Optional.of(new Account(
        client,
        newCred,
        new AtomicReference<>(fastChangingDataLifetime),
        new AtomicReference<>(slowChangingDataLifetime),
        new RefreshSubscription(
          consumer,
          Validation.assertNonnull(onError, "onError")
        ),
        null
      ));
    }catch(AuthenticationException e){
      return Optional.empty();
    }
  }


  /**
   * Authenticates to tesla account
   *
   * @param emailAddress          email address
   * @param password              password
   * @param consumer              consumer that consumes new credentials
   * @param onError               runnable that runs when there's an error
   * @param refreshOffsetDuration duration of offset before credential expires for service to attempt to refresh
   * @return optional object that may contain the tesla account. If it is empty, then authentication has failed
   */
  @Nonnull
  public Optional<Account> authenticate(
    @Nonnull String emailAddress,
    @Nonnull String password,
    @Nonnull Consumer<AccountCredentials> consumer,
    @Nonnull Consumer<ReAuthenticationException> onError,
    @Nonnull Duration refreshOffsetDuration
  ){
    try{

      refreshOffsetDuration = Validation.assertNonnull(refreshOffsetDuration, "refreshOffsetDuration");

      // Refresh now
      AccountCredentials newCred = client.authenticate(emailAddress, password);
      Validation.assertNonnull(consumer, "consumer").accept(newCred);

      return Optional.of(new Account(
        client,
        newCred,
        new AtomicReference<>(fastChangingDataLifetime),
        new AtomicReference<>(slowChangingDataLifetime),
        new RefreshSubscription(
          consumer,
          Validation.assertNonnull(onError, "onError")
        ),
        refreshOffsetDuration
      ));
    }catch(AuthenticationException e){
      return Optional.empty();
    }
  }

  /**
   * Authenticates to tesla account
   *
   * @param refreshToken token used to refresh the credentials
   * @return optional object that may contain the tesla account. If it is empty, then authentication has failed
   */
  @Nonnull
  public Optional<Account> authenticate(@Nonnull String refreshToken){
    try{
      return Optional.of(new Account(
        client,
        client.refreshToken(refreshToken),
        new AtomicReference<>(fastChangingDataLifetime),
        new AtomicReference<>(slowChangingDataLifetime),
        null,
        null
      ));
    }catch(ReAuthenticationException e){
      return Optional.empty();
    }
  }

  /**
   * Authenticates to tesla account
   *
   * @param refreshToken          token used to refresh the credentials
   * @param refreshOffsetDuration duration of offset before credential expires for service to attempt to refresh
   * @return optional object that may contain the tesla account. If it is empty, then authentication has failed
   */
  @Nonnull
  public Optional<Account> authenticate(@Nonnull String refreshToken, @Nonnull Duration refreshOffsetDuration){
    refreshOffsetDuration = Validation.assertNonnull(refreshOffsetDuration, "refreshOffsetDuration");
    try{
      return Optional.of(new Account(
        client,
        client.refreshToken(refreshToken),
        new AtomicReference<>(fastChangingDataLifetime),
        new AtomicReference<>(slowChangingDataLifetime),
        null,
        refreshOffsetDuration
      ));
    }catch(ReAuthenticationException e){
      return Optional.empty();
    }
  }

  /**
   * Authenticates to tesla account while returning credentials
   *
   * @param refreshToken token used to refresh the credentials
   * @param consumer     consumer that consumes new credentials
   * @param onError      runnable that runs when there's an error
   * @return optional object that may contain the tesla account. If it is empty, then authentication has failed
   */
  @Nonnull
  public Optional<Account> authenticate(
    @Nonnull String refreshToken,
    @Nonnull Consumer<AccountCredentials> consumer,
    @Nonnull Consumer<ReAuthenticationException> onError
  ){
    try{

      // Refresh now
      AccountCredentials newCred = client.refreshToken(refreshToken);
      Validation.assertNonnull(consumer, "consumer").accept(newCred);

      // Return new account
      return Optional.of(new Account(
        client,
        newCred,
        new AtomicReference<>(fastChangingDataLifetime),
        new AtomicReference<>(slowChangingDataLifetime),
        new RefreshSubscription(
          consumer,
          Validation.assertNonnull(onError, "onError")
        ),
        null
      ));
    }catch(ReAuthenticationException e){
      return Optional.empty();
    }
  }

  /**
   * Authenticates to tesla account while returning credentials
   *
   * @param refreshToken          token used to refresh the credentials
   * @param consumer              consumer that consumes new credentials
   * @param onError               runnable that runs when there's an error
   * @param refreshOffsetDuration duration of offset before credential expires for service to attempt to refresh
   * @return optional object that may contain the tesla account. If it is empty, then authentication has failed
   */
  @Nonnull
  public Optional<Account> authenticate(
    @Nonnull String refreshToken,
    @Nonnull Consumer<AccountCredentials> consumer,
    @Nonnull Consumer<ReAuthenticationException> onError,
    @Nonnull Duration refreshOffsetDuration
  ){
    refreshOffsetDuration = Validation.assertNonnull(refreshOffsetDuration, "refreshOffsetDuration");
    try{

      // Refresh now
      AccountCredentials newCred = client.refreshToken(refreshToken);
      Validation.assertNonnull(consumer, "consumer").accept(newCred);

      // Return new account
      return Optional.of(new Account(
        client,
        newCred,
        new AtomicReference<>(fastChangingDataLifetime),
        new AtomicReference<>(slowChangingDataLifetime),
        new RefreshSubscription(
          consumer,
          Validation.assertNonnull(onError, "onError")
        ),
        refreshOffsetDuration
      ));
    }catch(ReAuthenticationException e){
      return Optional.empty();
    }
  }


  /**
   * Authenticates to tesla account while returning credentials
   *
   * <b>NOTE:</b> This method entrusts the user to provide correct credentials. This method will not verify if any of
   * the credentials are correct. (Except for obvious things like incorrect creation/expiration times.)
   *
   * @param credentials           credentials
   * @param consumer              consumer that consumes new credentials
   * @param onError               runnable that runs when there's an error
   * @param refreshOffsetDuration duration of offset before credential expires for service to attempt to refresh
   * @return optional that may contain unverified tesla account if all looks fine, otherwise returns empty if expiration time is obviously expired
   */
  @Nonnull
  public Optional<Account> authenticateTrusted(
    @Nonnull AccountCredentials credentials,
    @Nonnull Consumer<AccountCredentials> consumer,
    @Nonnull Consumer<ReAuthenticationException> onError,
    @Nonnull Duration refreshOffsetDuration
  ){

    // Check all inputs
    Validation.assertNonnull(credentials, "credentials");
    Validation.assertNonnull(consumer, "consumer");
    Validation.assertNonnull(onError, "onError");
    Validation.assertNonnull(refreshOffsetDuration, "refreshOffsetDuration");

    // Check if expire time is valid
    if(Instant.now().isBefore(credentials.getExpirationTime())) return Optional.empty();

    // Return account
    return Optional.of(new Account(
      client,
      credentials,
      new AtomicReference<>(fastChangingDataLifetime),
      new AtomicReference<>(slowChangingDataLifetime),
      new RefreshSubscription(consumer, onError),
      refreshOffsetDuration
    ));
  }

  /** Builder */
  public static class Builder extends ClientBuilder<com.ansill.tesla.api.high.Client>{

    /** Lifetime in duration for fast-changing data to be cached in the memory before it's purged */
    private Duration fastChangingDataLifetime;

    /** Lifetime in duration for slow-changing data to be cached in the memory before it's purged */
    private Duration slowChangingDataLifetime;

    @Nonnull
    @Override
    public com.ansill.tesla.api.high.Client build(){
      var client = com.ansill.tesla.api.low.Client.builder()
                                                  .setUrl(this.url)
                                                  .setClientId(clientId)
                                                  .setClientSecret(clientSecret)
                                                  .setDebugFunction(debug)
                                                  .build();
      return new com.ansill.tesla.api.high.Client(client, fastChangingDataLifetime, slowChangingDataLifetime);
    }

    /**
     * Sets duration in fast-changing data
     *
     * @param fastChangingDataLifetime Lifetime in duration for fast-changing data to be cached in the memory before it's purged
     * @return updated builder
     */
    @Nonnull
    public Builder setFastChangingDataLifetime(Duration fastChangingDataLifetime){
      this.fastChangingDataLifetime = fastChangingDataLifetime;
      return this;
    }

    /**
     * Sets duration in slow-changing data
     *
     * @param slowChangingDataLifetime Lifetime in duration for slow-changing data to be cached in the memory before it's purged
     * @return updated builder
     */
    @Nonnull
    public Builder setSlowChangingDataLifetime(Duration slowChangingDataLifetime){
      this.slowChangingDataLifetime = slowChangingDataLifetime;
      return this;
    }

    @Nonnull
    @Override
    public Builder setUrl(@Nullable String url){
      return (Builder) super.setUrl(url);
    }

    @Nonnull
    @Override
    public Builder setClientId(@Nullable String clientId){
      return (Builder) super.setClientId(clientId);
    }

    @Nonnull
    @Override
    public Builder setClientSecret(@Nullable String clientSecret){
      return (Builder) super.setClientSecret(clientSecret);
    }

    @Nonnull
    @Override
    public Builder setDebugFunction(@Nullable Function<Object,Boolean> debug){
      return (Builder) super.setDebugFunction(debug);
    }

    @Nonnull
    @Override
    public Builder setConnectionTimeoutDuration(@Nullable Duration timeout){
      return (Builder) super.setConnectionTimeoutDuration(timeout);
    }

    @Nonnull
    @Override
    public Builder setReadTimeoutDuration(@Nullable Duration timeout){
      return (Builder) super.setReadTimeoutDuration(timeout);
    }
  }
}
