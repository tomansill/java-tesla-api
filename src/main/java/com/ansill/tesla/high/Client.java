package com.ansill.tesla.high;

import com.ansill.tesla.low.exception.AuthenticationException;

import javax.annotation.Nonnull;
import java.util.Optional;

import static com.ansill.tesla.utility.Constants.*;

/** Opinionated client */
public final class Client{

    /** Low-level client */
    private final com.ansill.tesla.low.Client client;

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
        this.client = new com.ansill.tesla.low.Client(url, clientId, clientSecret);
    }

    @Nonnull
    public Optional<TeslaAccount> authenticate(@Nonnull String emailAddress, @Nonnull String password){
        try{
            return Optional.of(new TeslaAccount(client, client.authenticate(emailAddress, password)));
        }catch(AuthenticationException e){
            return Optional.empty();
        }
    }
}
