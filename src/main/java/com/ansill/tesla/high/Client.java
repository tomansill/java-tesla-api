package com.ansill.tesla.high;

import com.ansill.tesla.low.exception.AuthenticationException;
import com.ansill.tesla.low.exception.ReAuthenticationException;
import com.ansill.validation.Validation;

import javax.annotation.Nonnull;
import java.util.Optional;

import static com.ansill.tesla.utility.Constants.*;

/** Highly-Opinionated client */
public final class Client{

    /** Low-level client */
    private final com.ansill.tesla.med.Client client;

    /** Sets up high-level client with default URL, client ID, and client secret */
    public Client(){
        this(URL, CLIENT_ID, CLIENT_SECRET);
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
        this.client = new com.ansill.tesla.med.Client(
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
            return Optional.of(new Account(client, client.authenticate(emailAddress, password),
                    fastChangingDataLifetime, slowChangingDataLifetime
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
            return Optional.of(new Account(client, client.refreshToken(refreshToken), fastChangingDataLifetime,
                    slowChangingDataLifetime
            ));
        }catch(ReAuthenticationException e){
            return Optional.empty();
        }
    }
}
