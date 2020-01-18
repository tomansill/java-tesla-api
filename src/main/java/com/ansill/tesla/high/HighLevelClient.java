package com.ansill.tesla.high;

import com.ansill.tesla.high.model.SuccessfulAuthenticationResponse;
import com.ansill.tesla.low.Client;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Optional;

import static com.ansill.tesla.utility.Constants.*;

/** Opinionated client */
public final class HighLevelClient{

    /** Low-level client */
    private final Client client;

    /** Sets up high-level client with default URL, client ID, and client secret */
    public HighLevelClient(){
        this(URL, CLIENT_ID, CLIENT_SECRET);
    }

    /**
     * Sets up high-level client with custom URL, client ID, and client secret
     *
     * @param url           URL
     * @param client_id     client id
     * @param client_secret client secret
     */
    public HighLevelClient(@Nonnull String url, @Nonnull String client_id, @Nonnull String client_secret){

        // Assign it
        this.client = new Client(url, client_id, client_secret);
    }

    @Nonnull
    public Optional<SuccessfulAuthenticationResponse> authenticate(
            @Nonnull String email_address,
            @Nonnull String password
    ) throws IOException{

        throw new RuntimeException();
        //return client.authenticate(email_address, password).map(SuccessfulAuthenticationResponse::convert);
    }
}
