package com.ansill.tesla.low;

import com.ansill.tesla.low.exception.APIProtocolException;
import com.ansill.tesla.low.exception.AuthenticationException;
import com.ansill.tesla.low.exception.ClientException;
import com.ansill.tesla.low.exception.InvalidAccessTokenException;
import com.ansill.tesla.low.exception.ReAuthenticationException;
import com.ansill.tesla.low.exception.VehicleIDNotFoundException;
import com.ansill.tesla.low.exception.VehicleUnavailableException;
import com.ansill.tesla.low.model.CompleteVehicleDataResponse;
import com.ansill.tesla.low.model.GenericErrorResponse;
import com.ansill.tesla.low.model.SimpleReasonResponse;
import com.ansill.tesla.low.model.SuccessfulAuthenticationResponse;
import com.ansill.tesla.low.model.VehicleResponse;
import com.ansill.tesla.low.model.VehiclesResponse;
import com.ansill.tesla.utility.Utility;
import com.ansill.validation.Validation;
import com.google.gson.Gson;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import static com.ansill.tesla.utility.Constants.*;
import static com.ansill.tesla.utility.Utility.f;

/** Low Level Tesla API client */
public final class Client{

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(Client.class);
    /** Client ID to use */
    @Nonnull
    private final String clientId;
    /** Client secret to use */
    @Nonnull
    private final String clientSecret;

    /** URL */
    @Nonnull
    private final String url;

    /**
     * Sets up low-level client with default URL, client ID, and client secret
     */
    public Client(){
        this(URL, CLIENT_ID, CLIENT_SECRET);
    }

    /**
     * Sets up low-level client with custom URL, client ID, and client secret
     *
     * @param url          URL
     * @param clientId     client id
     * @param clientSecret client secret
     */
    public Client(@Nonnull String url, @Nonnull String clientId, @Nonnull String clientSecret){

        // Ensure correct parameters
        Validation.assertNonnull(url, "url");
        Validation.assertNonnull(clientId, "client_id");
        Validation.assertNonnull(clientSecret, "client_secret");

        // Ensure that URL is valid
        try{
            new URL(url);
            if(!url.endsWith("/")) url = url + "/";
        }catch(MalformedURLException e){
            throw new IllegalArgumentException("The URL is invalid!");
        }

        // Assign it
        this.url = url;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @Nonnull
    private static <T> T fromJson(@Nonnull Response response, Class<T> type)
    throws APIProtocolException, ClientException{

        try{

            // Get body
            String body = Utility.getStringFromResponseBody(response)
                                 .orElseThrow(() -> new APIProtocolException("The request body is empty!"));

            // Forward it
            return fromJson(body, type);

        }catch(IOException e){
            throw new ClientException("Failed to parse content of response body", e);
        }
    }

    @Nonnull
    private static <T> T fromJson(@Nonnull String string, Class<T> type) throws APIProtocolException{
        var item = new Gson().fromJson(string, type);
        if(item == null) throw new APIProtocolException(f(
                "THe JSON string is not in format of {} class. JSON message \n\"{}\"",
                type.getName(),
                string
        ));
        return item;
    }

    /**
     * Authenticates the account to retrieve an object with access and refresh tokens
     *
     * @param emailAddress email address to the account
     * @param password     password to the account
     * @return object that contains access and refresh tokens
     * @throws IOException thrown if failed to read from the service
     */
    @Nonnull
    public SuccessfulAuthenticationResponse authenticate(
            @Nonnull String emailAddress,
            @Nonnull String password
    ) throws AuthenticationException, ClientException{

        // Check parameters
        Validation.assertNonnull(emailAddress, "emailAddress");
        Validation.assertNonnull(password, "password");

        // Set up body
        var requestBody = new FormBody.Builder().add("grant_type", "password")
                                                .add("email", emailAddress)
                                                .add("password", password)
                                                .add("client_id", clientId)
                                                .add("client_secret", clientSecret)
                                                .build();

        // Set up request
        Request request = new Request.Builder().url(url + "oauth/token")
                                               .addHeader("Content-Type", "application/x-www-form-urlencoded")
                                               .post(requestBody)
                                               .build();

        // Send request
        try(Response response = new OkHttpClient().newCall(request).execute()){

            // Handle code
            return switch(response.code()){

                // Success
                case 200 -> fromJson(response, SuccessfulAuthenticationResponse.class);

                // Unauthorized
                case 401 -> {

                    // Parse error
                    var error = fromJson(response, GenericErrorResponse.class);

                    // If we get an response, ignore other error
                    if(error.getResponse().isPresent()){

                        // Get response
                        var item = error.getResponse().orElseThrow(() -> new APIProtocolException(f(
                                "Unexpected empty response in object: {}",
                                error
                        )));

                        // Check if it's invalid password
                        if(!item.startsWith("authorization_required_for_txid_")) throw new APIProtocolException(f(
                                "Unexpected string in 401 error response: {}",
                                error
                        ));

                        // It's invalid credentials error
                        throw new AuthenticationException(emailAddress);

                    }else{

                        // Check if it's invalid_client
                        if("invalid_client".equals(error.getError().orElse(""))){
                            throw new ClientException(error.getErrorDescription().orElseThrow());
                        }

                        // Otherwise report protocol error
                        throw new APIProtocolException(f(
                                "error: {}\t description: {}",
                                error.getError(),
                                error.getErrorDescription()
                        ));
                    }
                }

                // Unknown
                default -> throw new APIProtocolException(f("Unexpected status code: {}", response.code()));
            };

        }catch(IOException e){

            // Wrap and re-throw
            throw new ClientException("Unhandled Exception has occurred", e);
        }
    }

    public void revokeToken(@Nonnull String refreshToken){

        // Check parameters
        Validation.assertNonnull(refreshToken, "refreshToken");

        // Set up body
        var requestBody = new FormBody.Builder().add("token", refreshToken).build();

        // Set up request
        Request request = new Request.Builder().url(url + "oauth/revoke")
                                               .addHeader("Content-Type", "application/x-www-form-urlencoded")
                                               .post(requestBody)
                                               .build();

        // Send request
        try(Response response = new OkHttpClient().newCall(request).execute()){

            // Handle code
            if(response.code() != 200){
                throw new APIProtocolException(f("Unexpected status code: {}", response.code()));
            }

        }catch(IOException e){

            // Wrap and re-throw
            throw new ClientException("Unhandled Exception has occurred", e);
        }
    }

    @Nonnull
    public SuccessfulAuthenticationResponse refreshToken(@Nonnull String refreshToken) throws ReAuthenticationException{

        // Check parameters
        Validation.assertNonnull(refreshToken, "refreshToken");

        // Set up body
        var requestBody = new FormBody.Builder().add("grant_type", "refresh_token")
                                                .add("refresh_token", refreshToken)
                                                .add("client_id", clientId)
                                                .add("client_secret", clientSecret)
                                                .build();

        // Set up request
        Request request = new Request.Builder().url(url + "oauth/token")
                                               .addHeader("Content-Type", "application/x-www-form-urlencoded")
                                               .post(requestBody)
                                               .build();

        // Send request
        try(Response response = new OkHttpClient().newCall(request).execute()){

            // Handle code
            return switch(response.code()){

                // Success
                case 200 -> fromJson(response, SuccessfulAuthenticationResponse.class);

                // Unauthenticated
                case 401 -> {

                    // Get error
                    var error = fromJson(response, GenericErrorResponse.class);

                    // Find out whether if it's password invalid or protocol error
                    if(!"invalid_grant".equals(error.getError().orElse(""))){

                        // Respond with exception
                        throw new APIProtocolException(f(
                                "error: {}\t description: {}",
                                error.getError().orElse("Empty error type"),
                                error.getErrorDescription().orElse("Empty error description")
                        ));

                    }

                    // Log the contents
                    LOG.debug("Error contents: {}", error);

                    // It's invalid credentials error
                    throw new ReAuthenticationException();
                }

                // Unknown
                default -> throw new APIProtocolException(f("Unexpected status code: {}", response.code()));
            };

        }catch(IOException e){

            // Wrap and re-throw
            throw new ClientException("Unhandled Exception has occurred", e);
        }
    }

    @Nonnull
    public VehiclesResponse getVehicles(@Nonnull String access_token) throws InvalidAccessTokenException{

        // Check parameters
        Validation.assertNonnull(access_token, "access_token");

        // Set up request
        Request request = new Request.Builder().url(url + "api/1/vehicles")
                                               .addHeader("Authorization", "Bearer " + access_token)
                                               .get()
                                               .build();

        // Send request
        try(Response response = new OkHttpClient().newCall(request).execute()){

            // Handle code
            return switch(response.code()){

                // Success
                case 200 -> fromJson(response, VehiclesResponse.class);

                // Unauthenticated
                case 401 -> throw new InvalidAccessTokenException();

                // Unknown
                default -> throw new APIProtocolException(f("Unexpected status code: {}", response.code()));
            };

        }catch(IOException e){

            // Wrap and re-throw
            throw new ClientException("Unhandled Exception has occurred", e);
        }
    }

    @Nonnull
    public Optional<VehicleResponse> getVehicle(@Nonnull String accessToken, @Nonnull String idString){

        // Check parameters
        Validation.assertNonnull(accessToken, "accessToken");

        // Check parameters
        Validation.assertNonnull(idString, "idString");

        // Set up request
        Request request = new Request.Builder().url(url + "api/1/vehicles/" + idString)
                                               .addHeader("Authorization", "Bearer " + accessToken)
                                               .get()
                                               .build();

        // Send request
        try(Response response = new OkHttpClient().newCall(request).execute()){

            // Handle code
            return switch(response.code()){

                // Success
                case 200 -> Optional.of(fromJson(response, VehicleResponse.class));

                // Unauthenticated
                case 401 -> throw new InvalidAccessTokenException();

                // Not found
                case 404 -> Optional.empty();

                // Unknown
                default -> throw new APIProtocolException(f("Unexpected status code: {}", response.code()));
            };

        }catch(IOException e){

            // Wrap and re-throw
            throw new ClientException("Unhandled Exception has occurred", e);
        }
    }

    @Nonnull
    public VehiclesResponse wakeup(@Nonnull String accessToken, @Nonnull String idString)
    throws VehicleIDNotFoundException{

        // Check parameters
        Validation.assertNonnull(accessToken, "accessToken");

        // Check parameters
        Validation.assertNonnull(idString, "idString");

        // Set up request
        Request request = new Request.Builder().url(url + "api/1/vehicles/" + idString + "/wake_up")
                                               .addHeader("Authorization", "Bearer " + accessToken)
                                               .post(RequestBody.create("", MediaType.parse("text/plain")))
                                               .build();

        // Send request
        try(Response response = new OkHttpClient().newCall(request).execute()){

            // Handle code
            return switch(response.code()){

                // Success
                case 200 -> fromJson(response, VehiclesResponse.class);

                // Unauthenticated
                case 401 -> throw new InvalidAccessTokenException();

                // Not found
                case 404 -> throw new VehicleIDNotFoundException(idString);

                // Request Timeout
                case 408 -> throw new VehicleUnavailableException(); // TODO will this ever happen?

                // Unknown
                default -> throw new APIProtocolException(f("Unexpected status code: {}", response.code()));
            };

        }catch(IOException e){

            // Wrap and re-throw
            throw new ClientException("Unhandled Exception has occurred", e);
        }
    }

    @Nonnull
    private SimpleReasonResponse invokeSimpleCommand(
            @Nonnull String accessToken,
            @Nonnull String idString,
            @Nonnull String command
    )
    throws VehicleIDNotFoundException{

        // Check parameters
        Validation.assertNonnull(accessToken, "accessToken");

        // Check parameters
        Validation.assertNonnull(idString, "idString");

        // Set up request
        Request request = new Request.Builder().url(url + "api/1/vehicles/" + idString + "/command/" + command)
                                               .addHeader("Authorization", "Bearer " + accessToken)
                                               .post(RequestBody.create("", MediaType.parse("text/plain")))
                                               .build();

        // Send request
        try(Response response = new OkHttpClient().newCall(request).execute()){

            // Handle code
            return switch(response.code()){

                // Success
                case 200 -> fromJson(response, SimpleReasonResponse.class);

                // Unauthenticated
                case 401 -> throw new InvalidAccessTokenException();

                // Request Timeout
                case 408 -> throw new VehicleUnavailableException();

                // Not found
                case 404 -> throw new VehicleIDNotFoundException(idString);

                // Unknown
                default -> throw new APIProtocolException(f("Unexpected status code: {}", response.code()));
            };

        }catch(IOException e){

            // Wrap and re-throw
            throw new ClientException("Unhandled Exception has occurred", e);
        }
    }

    @Nonnull
    public SimpleReasonResponse unlockDoors(@Nonnull String accessToken, @Nonnull String idString)
    throws VehicleIDNotFoundException{
        return this.invokeSimpleCommand(accessToken, idString, "door_unlock");
    }

    @Nonnull
    public SimpleReasonResponse lockDoors(@Nonnull String accessToken, @Nonnull String idString)
    throws VehicleIDNotFoundException{
        return this.invokeSimpleCommand(accessToken, idString, "door_lock");
    }

    @Nonnull
    public SimpleReasonResponse honkHorn(@Nonnull String accessToken, @Nonnull String idString)
    throws VehicleIDNotFoundException{
        return this.invokeSimpleCommand(accessToken, idString, "honk_horn");
    }

    @Nonnull
    public SimpleReasonResponse flashLights(@Nonnull String accessToken, @Nonnull String idString)
    throws VehicleIDNotFoundException{
        return this.invokeSimpleCommand(accessToken, idString, "flash_lights");
    }

    @Nonnull
    public SimpleReasonResponse startHVACSystem(@Nonnull String accessToken, @Nonnull String idString)
    throws VehicleIDNotFoundException{
        return this.invokeSimpleCommand(accessToken, idString, "auto_conditioning_start");
    }

    @Nonnull
    public SimpleReasonResponse stopHVACSystem(@Nonnull String accessToken, @Nonnull String idString)
    throws VehicleIDNotFoundException{
        return this.invokeSimpleCommand(accessToken, idString, "auto_conditioning_stop");
    }

    @Nonnull
    public SimpleReasonResponse setMaxRangeChargeLimit(@Nonnull String accessToken, @Nonnull String idString)
    throws VehicleIDNotFoundException{
        return this.invokeSimpleCommand(accessToken, idString, "charge_max_range");
    }

    @Nonnull
    public SimpleReasonResponse setStandardChargeLimit(@Nonnull String accessToken, @Nonnull String idString)
    throws VehicleIDNotFoundException{
        return this.invokeSimpleCommand(accessToken, idString, "charge_standard");
    }

    @Nonnull
    public SimpleReasonResponse openChargePortDoor(@Nonnull String accessToken, @Nonnull String idString)
    throws VehicleIDNotFoundException{
        return this.invokeSimpleCommand(accessToken, idString, "charge_port_door_open");
    }

    @Nonnull
    public SimpleReasonResponse closeChargePortDoor(@Nonnull String accessToken, @Nonnull String idString)
    throws VehicleIDNotFoundException{
        return this.invokeSimpleCommand(accessToken, idString, "charge_port_door_close");
    }

    @Nonnull
    public SimpleReasonResponse startCharge(@Nonnull String accessToken, @Nonnull String idString)
    throws VehicleIDNotFoundException{
        return this.invokeSimpleCommand(accessToken, idString, "charge_start");
    }

    @Nonnull
    public SimpleReasonResponse stopCharge(@Nonnull String accessToken, @Nonnull String idString)
    throws VehicleIDNotFoundException{
        return this.invokeSimpleCommand(accessToken, idString, "charge_stop");
    }

    @Nonnull
    public SimpleReasonResponse toggleMediaPlayback(@Nonnull String accessToken, @Nonnull String idString)
    throws VehicleIDNotFoundException{
        return this.invokeSimpleCommand(accessToken, idString, "media_toggle_playback");
    }

    @Nonnull
    public SimpleReasonResponse nextMediaTrack(@Nonnull String accessToken, @Nonnull String idString)
    throws VehicleIDNotFoundException{
        return this.invokeSimpleCommand(accessToken, idString, "media_next_track");
    }

    @Nonnull
    public SimpleReasonResponse previousMediaTrack(@Nonnull String accessToken, @Nonnull String idString)
    throws VehicleIDNotFoundException{
        return this.invokeSimpleCommand(accessToken, idString, "media_prev_track");
    }

    @Nonnull
    public SimpleReasonResponse nextFavoriteMedia(@Nonnull String accessToken, @Nonnull String idString)
    throws VehicleIDNotFoundException{
        return this.invokeSimpleCommand(accessToken, idString, "media_next_fav");
    }

    @Nonnull
    public SimpleReasonResponse previousFavoriteMedia(@Nonnull String accessToken, @Nonnull String idString)
    throws VehicleIDNotFoundException{
        return this.invokeSimpleCommand(accessToken, idString, "media_prev_fav");
    }

    @Nonnull
    public SimpleReasonResponse turnMediaVolumeUp(@Nonnull String accessToken, @Nonnull String idString)
    throws VehicleIDNotFoundException{
        return this.invokeSimpleCommand(accessToken, idString, "media_volume_up");
    }

    @Nonnull
    public SimpleReasonResponse turnMediaVolumeDown(@Nonnull String accessToken, @Nonnull String idString)
    throws VehicleIDNotFoundException{
        return this.invokeSimpleCommand(accessToken, idString, "media_volume_down");
    }


    @Nonnull
    public SimpleReasonResponse cancelSoftwareUpdate(@Nonnull String accessToken, @Nonnull String idString)
    throws VehicleIDNotFoundException{
        return this.invokeSimpleCommand(accessToken, idString, "cancel_software_update");
    }

    @Nonnull
    public CompleteVehicleDataResponse getVehicleData(@Nonnull String accessToken, @Nonnull String idString)
    throws VehicleIDNotFoundException{

        // Check parameters
        Validation.assertNonnull(accessToken, "accessToken");

        // Check parameters
        Validation.assertNonnull(idString, "idString");

        // Set up request
        Request request = new Request.Builder().url(url + "api/1/vehicles/" + idString + "/vehicle_data")
                                               .addHeader("Authorization", "Bearer " + accessToken)
                                               .get()
                                               .build();

        // Send request
        try(Response response = new OkHttpClient().newCall(request).execute()){

            // Handle code
            return switch(response.code()){

                // Success
                case 200 -> fromJson(response, CompleteVehicleDataResponse.class);

                // Unauthenticated
                case 401 -> throw new InvalidAccessTokenException();

                // Not found
                case 404 -> throw new VehicleIDNotFoundException(idString);

                // Request Timeout
                case 408 -> throw new VehicleUnavailableException();

                // Unknown
                default -> throw new APIProtocolException(f("Unexpected status code: {}", response.code()));
            };

        }catch(IOException e){

            // Wrap and re-throw
            throw new ClientException("Unhandled Exception has occurred", e);
        }
    }
}
