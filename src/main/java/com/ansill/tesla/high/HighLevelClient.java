package com.ansill.tesla.high;

import com.ansill.tesla.high.model.CompleteVehicleData;
import com.ansill.tesla.high.model.SuccessfulAuthenticationResponse;
import com.ansill.tesla.high.model.Vehicle;
import com.ansill.tesla.low.LowLevelClient;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ansill.tesla.utility.Constants.*;

/** Opinionated client */
public final class HighLevelClient{

    /** Low-level client */
    private final LowLevelClient client;

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
        this.client = new LowLevelClient(url, client_id, client_secret);
    }

    @Nonnull
    public Optional<SuccessfulAuthenticationResponse> authenticate(
            @Nonnull String email_address,
            @Nonnull String password
    ) throws IOException{
        return client.authenticate(email_address, password).map(SuccessfulAuthenticationResponse::convert);
    }

    @Nonnull
    public Optional<SuccessfulAuthenticationResponse> refreshToken(@Nonnull String refresh_token) throws IOException{
        return client.refreshToken(refresh_token).map(SuccessfulAuthenticationResponse::convert);
    }

    @Nonnull
    public List<Vehicle> getVehicles(@Nonnull String access_token) throws IOException{

        // Get raw vehicles and convert them
        return client.getVehicles(access_token).stream().map(Vehicle::convert).collect(Collectors.toList());
    }

    @Nonnull
    public Optional<Vehicle> getVehicle(@Nonnull String access_token, @Nonnull String id) throws IOException{

        // Get raw vehicle and convert them
        return client.getVehicle(access_token, id).map(Vehicle::convert);
    }

    @Nonnull
    public Optional<CompleteVehicleData> getVehicleData(@Nonnull String access_token, @Nonnull String id)
    throws IOException{

        // Get raw data and convert them
        return client.getVehicleData(access_token, id).map(CompleteVehicleData::convert);
    }
}
