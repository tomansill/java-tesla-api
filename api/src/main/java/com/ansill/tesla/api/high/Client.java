package com.ansill.tesla.api.high;

import com.ansill.tesla.api.low.exception.AuthenticationException;
import com.ansill.tesla.api.low.exception.ReAuthenticationException;
import com.ansill.tesla.api.med.model.AccountCredentials;
import com.ansill.tesla.api.model.ClientBuilder;
import com.ansill.validation.Validation;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/** Highly-Opinionated client */
public final class Client{

  /** Default fast changing data lifetime - used to prevent too-frequent polling */
  private static final Duration DEFAULT_FAST_CHANGING_DATA_LIFETIME = Duration.ofMillis(100); // 1/10 seconds

  /** Default slow changing data lifetime - used to prevent too-frequent polling */
  private static final Duration DEFAULT_SLOW_CHANGING_DATA_LIFETIME = Duration.ofMinutes(1);

  /** Global setting */
  private static final AtomicReference<Duration> GLOBAL_FAST_CHANGING_DATA_LIFETIME = new AtomicReference<>(
    DEFAULT_FAST_CHANGING_DATA_LIFETIME);

  /** Global setting */
  private static final AtomicReference<Duration> GLOBAL_SLOW_CHANGING_DATA_LIFETIME = new AtomicReference<>(
    DEFAULT_SLOW_CHANGING_DATA_LIFETIME);

  /** Med-level client */
  private final com.ansill.tesla.api.med.Client client;

  /**
   * Constructor that constructs high-level client using medium-level client
   *
   * @param client medium-level client
   */
  private Client(@Nonnull com.ansill.tesla.api.med.Client client){
    this.client = client;
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

  /** Builder */
  public static class Builder extends ClientBuilder<com.ansill.tesla.api.high.Client>{

    @Nonnull
    @Override
    public com.ansill.tesla.api.high.Client build(){
      return new com.ansill.tesla.api.high.Client(com.ansill.tesla.api.med.Client.builder()
                                                                                 .setUrl(this.url)
                                                                                 .setClientId(clientId)
                                                                                 .setClientSecret(clientSecret)
                                                                                 .setDebugFunction(debug)
                                                                                 .build());
    }
  }

  public void setGlobalFastChangingDataLifetime(@Nonnull Duration duration){
    GLOBAL_FAST_CHANGING_DATA_LIFETIME.set(Validation.assertNonnull(duration, "duration"));
  }

  public void setGlobalSlowChangingDataLifetime(@Nonnull Duration duration){
    GLOBAL_SLOW_CHANGING_DATA_LIFETIME.set(Validation.assertNonnull(duration, "duration"));
  }

  public void resetGlobalFastChangingDataLifetime(){
    GLOBAL_FAST_CHANGING_DATA_LIFETIME.set(DEFAULT_FAST_CHANGING_DATA_LIFETIME);
  }

  public void resetGlobalSlowChangingDataLifetime(){
    GLOBAL_SLOW_CHANGING_DATA_LIFETIME.set(DEFAULT_SLOW_CHANGING_DATA_LIFETIME);
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
        new AtomicReference<>(GLOBAL_FAST_CHANGING_DATA_LIFETIME),
        new AtomicReference<>(GLOBAL_SLOW_CHANGING_DATA_LIFETIME)
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
        new AtomicReference<>(GLOBAL_FAST_CHANGING_DATA_LIFETIME),
        new AtomicReference<>(GLOBAL_SLOW_CHANGING_DATA_LIFETIME)
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
        new AtomicReference<>(GLOBAL_FAST_CHANGING_DATA_LIFETIME),
        new AtomicReference<>(GLOBAL_SLOW_CHANGING_DATA_LIFETIME),
        new RefreshSubscription(
          consumer,
          Validation.assertNonnull(onError, "onError")
        )
      ));
    }catch(ReAuthenticationException e){
      return Optional.empty();
    }
  }
}
