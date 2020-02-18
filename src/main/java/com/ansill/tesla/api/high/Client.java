package com.ansill.tesla.api.high;

import com.ansill.tesla.api.low.exception.AuthenticationException;
import com.ansill.tesla.api.low.exception.ReAuthenticationException;
import com.ansill.tesla.api.med.model.AccountCredentials;
import com.ansill.validation.Validation;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static com.ansill.tesla.api.utility.Constants.*;

/** Highly-Opinionated client */
public final class Client{

    /** Default fast changing data lifetime - used to stop too-frequent polling */
    private static final Duration DEFAULT_FAST_CHANGING_DATA_LIFETIME = Duration.ofSeconds(5);

    /** Default fast changing data lifetime - used to stop too-frequent polling */
    private static final Duration DEFAULT_SLOW_CHANGING_DATA_LIFETIME = Duration.ofMinutes(1);

    /** Global setting */
    private static final AtomicReference<Duration> GLOBAL_FAST_CHANGING_DATA_LIFETIME = new AtomicReference<>(
            DEFAULT_FAST_CHANGING_DATA_LIFETIME);

    /** Global setting */
    private static final AtomicReference<Duration> GLOBAL_SLOW_CHANGING_DATA_LIFETIME = new AtomicReference<>(
            DEFAULT_SLOW_CHANGING_DATA_LIFETIME);

    /** Low-level client */
    private final com.ansill.tesla.api.med.Client client;

    /** Sets up high-level client with default URL, client ID, and client secret */
    public Client(){
        this(URL, CLIENT_ID, CLIENT_SECRET);
    }

    public void setGlobalFastChangingDataLifetime(@Nonnull Duration duration){
        GLOBAL_FAST_CHANGING_DATA_LIFETIME.set(duration);
    }

    public void setGlobalSlowChangingDataLifetime(@Nonnull Duration duration){
        GLOBAL_SLOW_CHANGING_DATA_LIFETIME.set(duration);
    }

    public void resetGlobalFastChangingDataLifetime(){
        GLOBAL_FAST_CHANGING_DATA_LIFETIME.set(DEFAULT_FAST_CHANGING_DATA_LIFETIME);
    }

    public void resetGlobalSlowChangingDataLifetime(){
        GLOBAL_SLOW_CHANGING_DATA_LIFETIME.set(DEFAULT_SLOW_CHANGING_DATA_LIFETIME);
    }

    /**
     * Sets up high-level client with custom URL, client ID, and client secret
     *
     * @param url          URL
     * @param clientId     client id
     * @param clientSecret client secret
     */
    public Client(@Nonnull String url, @Nonnull String clientId, @Nonnull String clientSecret){

        // Assign it
        this.client = new com.ansill.tesla.api.med.Client(
                Validation.assertNonemptyString(url),
                Validation.assertNonemptyString(clientId),
                Validation.assertNonemptyString(clientSecret)
        );
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
            return Optional.of(new Account(
                    client,
                    client.refreshToken(refreshToken),
                    new AtomicReference<>(GLOBAL_FAST_CHANGING_DATA_LIFETIME),
                    new AtomicReference<>(GLOBAL_SLOW_CHANGING_DATA_LIFETIME),
                    new RefreshSubscription(
                            Validation.assertNonnull(consumer, "consumer"),
                            Validation.assertNonnull(onError, "onError")
                    )
            ));
        }catch(ReAuthenticationException e){
            return Optional.empty();
        }
    }
}
