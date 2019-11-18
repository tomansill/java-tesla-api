package com.ansill.tesla.low;

import com.ansill.tesla.low.exception.APIProtocolException;
import com.ansill.tesla.low.model.CompleteVehicleData;
import com.ansill.tesla.low.model.SuccessfulAuthenticationResponse;
import com.ansill.tesla.low.model.Vehicle;
import com.ansill.tesla.utility.Utility;
import com.ansill.validation.Validation;
import com.google.gson.Gson;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.ansill.tesla.utility.Constants.*;
import static com.ansill.tesla.utility.Utility.f;

public final class LowLevelClient{

    /** URL */
    @Nonnull
    private final String url;

    /** Client ID to use */
    @Nonnull
    private final String client_id;

    /** Client secret to use */
    @Nonnull
    private final String client_secret;

    /**
     * Sets up low-level client with default URL, client ID, and client secret
     */
    public LowLevelClient(){
        this(URL, CLIENT_ID, CLIENT_SECRET);
    }

    /**
     * Sets up low-level client with custom URL, client ID, and client secret
     *
     * @param url           URL
     * @param client_id     client id
     * @param client_secret client secret
     */
    public LowLevelClient(@Nonnull String url, @Nonnull String client_id, @Nonnull String client_secret){

        // Ensure correct parameters
        Validation.assertNonnull(url, "url");
        Validation.assertNonnull(client_id, "client_id");
        Validation.assertNonnull(client_secret, "client_secret");

        // Ensure that URL is valid
        try{
            new URL(url);
            if(!url.endsWith("/")) url = url + "/";
        }catch(MalformedURLException e){
            throw new IllegalArgumentException("The URL is invalid!");
        }

        // Assign it
        this.url = url;
        this.client_id = client_id;
        this.client_secret = client_secret;
    }

    @Nonnull
    public Optional<SuccessfulAuthenticationResponse> authenticate(
            @Nonnull String email_address,
            @Nonnull String password
    )
    throws IOException{

        // Check parameters
        Validation.assertNonnull(email_address, "email_address");
        Validation.assertNonnull(password, "password");

        // Set up body
        var requestBody = new FormBody.Builder().add("grant_type", "password")
                                                .add("email", email_address)
                                                .add("password", password)
                                                .add("client_id", client_id)
                                                .add("client_secret", client_secret)
                                                .build();

        // Set up request
        Request request = new Request.Builder().url(url + "oauth/token")
                                               .addHeader("Content-Type", "application/x-www-form-urlencoded")
                                               .post(requestBody)
                                               .build();

        // Send request
        try(Response response = new OkHttpClient().newCall(request).execute()){

            // Handle if 200
            if(response.code() == 200){

                // Get body
                var body = Utility.getStringFromResponseBody(response)
                                  .orElseThrow(() -> new APIProtocolException("The request body is null!"));

                // Convert json response to object
                return Optional.of(new Gson().fromJson(body, SuccessfulAuthenticationResponse.class));

            }

            // Handle if 401
            else if(response.code() == 401){

                // Get body
                var body = Utility.getStringFromResponseBody(response)
                                  .orElseThrow(() -> new APIProtocolException("The request body is null!"));

                // Convert json response to object
                var error = new Gson().fromJson(body, FailedAuthenticationResponse.class);

                // Find out whether if it's password invalid or protocol error
                if(error.response == null){

                    // Respond with exception
                    throw new APIProtocolException(f(
                            "error: {}\t description: {}",
                            error.error,
                            error.error_description
                    ));

                }

                // It's invalid credentials error
                return Optional.empty();
            }

            // Throw error if unexpected
            else throw new APIProtocolException(f("Unexpected status code: {}", response.code()));

        }
    }

    @Nonnull
    public Optional<SuccessfulAuthenticationResponse> refreshToken(@Nonnull String refresh_token)
    throws IOException{

        // Check parameters
        Validation.assertNonnull(refresh_token, "refresh_token");

        // Set up body
        var requestBody = new FormBody.Builder().add("grant_type", "refresh_token")
                                                .add("refresh_token", refresh_token)
                                                .add("client_id", client_id)
                                                .add("client_secret", client_secret)
                                                .build();

        // Set up request
        Request request = new Request.Builder().url(url + "oauth/token")
                                               .addHeader("Content-Type", "application/x-www-form-urlencoded")
                                               .post(requestBody)
                                               .build();

        // Send request
        try(Response response = new OkHttpClient().newCall(request).execute()){

            // Handle if 200
            if(response.code() == 200){

                // Get body
                var body = Utility.getStringFromResponseBody(response)
                                  .orElseThrow(() -> new APIProtocolException("The request body is null!"));

                // Convert json response to object
                return Optional.of(new Gson().fromJson(body, SuccessfulAuthenticationResponse.class));

            }

            // Handle if 401
            else if(response.code() == 401){

                // Get body
                var body = Utility.getStringFromResponseBody(response)
                                  .orElseThrow(() -> new APIProtocolException("The request body is null!"));

                // Convert json response to object
                var error = new Gson().fromJson(body, FailedAuthenticationResponse.class);

                // Find out whether if it's password invalid or protocol error
                if(!"invalid_grant".equals(error.error)){

                    // Respond with exception
                    throw new APIProtocolException(f(
                            "error: {}\t description: {}",
                            error.error,
                            error.error_description
                    ));

                }

                // It's invalid credentials error
                return Optional.empty();
            }

            // Throw error if unexpected
            else throw new APIProtocolException(f("Unexpected status code: {}", response.code()));

        }
    }

    @Nonnull
    public List<Vehicle> getVehicles(@Nonnull String access_token)
    throws IOException{

        // Check parameters
        Validation.assertNonnull(access_token, "access_token");

        // Set up request
        Request request = new Request.Builder().url(url + "api/1/vehicles")
                                               .addHeader("Authorization", "Bearer " + access_token)
                                               .get()
                                               .build();

        // Send request
        try(Response response = new OkHttpClient().newCall(request).execute()){

            // Handle if 200
            if(response.code() == 200){

                // Get body
                var body = Utility.getStringFromResponseBody(response)
                                  .orElseThrow(() -> new APIProtocolException("The request body is null!"));

                // Convert json response to object
                return Collections.unmodifiableList(new Gson().fromJson(body, VehiclesResponse.class).response);

            }

            // Throw error if unexpected
            else throw new APIProtocolException(f("Unexpected status code: {}", response.code()));

        }
    }

    @Nonnull
    public Optional<Vehicle> getVehicle(@Nonnull String access_token, @Nonnull String id_s)
    throws IOException{

        // Check parameters
        Validation.assertNonnull(access_token, "access_token");

        // Check parameters
        Validation.assertNonnull(id_s, "id_s");

        // Set up request
        Request request = new Request.Builder().url(url + "api/1/vehicles/" + id_s)
                                               .addHeader("Authorization", "Bearer " + access_token)
                                               .get()
                                               .build();

        // Send request
        try(Response response = new OkHttpClient().newCall(request).execute()){

            // Handle if 200
            if(response.code() == 200){

                // Get body
                var body = Utility.getStringFromResponseBody(response)
                                  .orElseThrow(() -> new APIProtocolException("The request body is null!"));

                // Convert json response to object
                return Optional.of(new Gson().fromJson(body, VehicleResponse.class).response);

            }

            // Return empty if not found
            else if(response.code() == 404) return Optional.empty();

                // Throw error if unexpected
            else throw new APIProtocolException(f("Unexpected status code: {}", response.code()));

        }
    }

    @Nonnull
    public Optional<CompleteVehicleData> getVehicleData(@Nonnull String access_token, @Nonnull String id_s)
    throws IOException{

        // Check parameters
        Validation.assertNonnull(access_token, "access_token");

        // Check parameters
        Validation.assertNonnull(id_s, "id_s");

        // Set up request
        Request request = new Request.Builder().url(url + "api/1/vehicles/" + id_s + "/vehicle_data")
                                               .addHeader("Authorization", "Bearer " + access_token)
                                               .get()
                                               .build();

        // Send request
        try(Response response = new OkHttpClient().newCall(request).execute()){

            // Handle if 200
            if(response.code() == 200){

                // Get body
                var body = Utility.getStringFromResponseBody(response)
                                  .orElseThrow(() -> new APIProtocolException("The request body is null!"));

                // Convert json response to object
                return Optional.of(new Gson().fromJson(body, CompleteVehicleDataResponse.class).response);

            }

            // Return empty if not found
            else if(response.code() == 404) return Optional.empty();

                // Throw error if unexpected
            else throw new APIProtocolException(f("Unexpected status code: {}", response.code()));

        }
    }

    private static class FailedAuthenticationResponse{
        private String error;
        private String error_description;
        private String response;
    }

    private static class VehiclesResponse{
        private List<Vehicle> response;
        private int count;
    }

    private static class VehicleResponse{
        private Vehicle response;
    }

    private static class CompleteVehicleDataResponse{
        private CompleteVehicleData response;
    }

}
