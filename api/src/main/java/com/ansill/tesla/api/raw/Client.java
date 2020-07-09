package com.ansill.tesla.api.raw;

import com.ansill.tesla.api.data.model.ChargeState;
import com.ansill.tesla.api.data.model.ClimateState;
import com.ansill.tesla.api.data.model.CompleteVehicle;
import com.ansill.tesla.api.data.model.DriveState;
import com.ansill.tesla.api.data.model.GuiSettings;
import com.ansill.tesla.api.data.model.MediaState;
import com.ansill.tesla.api.data.model.SoftwareUpdate;
import com.ansill.tesla.api.data.model.SpeedLimitMode;
import com.ansill.tesla.api.data.model.Vehicle;
import com.ansill.tesla.api.data.model.VehicleConfig;
import com.ansill.tesla.api.data.model.VehicleState;
import com.ansill.tesla.api.data.model.response.CompleteVehicleDataResponse;
import com.ansill.tesla.api.data.model.response.GenericErrorResponse;
import com.ansill.tesla.api.data.model.response.SimpleReasonResponse;
import com.ansill.tesla.api.data.model.response.SimpleResponse;
import com.ansill.tesla.api.data.model.response.SuccessfulAuthenticationResponse;
import com.ansill.tesla.api.data.model.response.VehicleResponse;
import com.ansill.tesla.api.data.model.response.VehiclesResponse;
import com.ansill.tesla.api.exception.VehicleInServiceException;
import com.ansill.tesla.api.exception.VehicleOfflineException;
import com.ansill.tesla.api.exception.VehicleSleepingException;
import com.ansill.tesla.api.model.ClientBuilder;
import com.ansill.tesla.api.raw.exception.APIProtocolException;
import com.ansill.tesla.api.raw.exception.AuthenticationException;
import com.ansill.tesla.api.raw.exception.ClientException;
import com.ansill.tesla.api.raw.exception.InvalidAccessTokenException;
import com.ansill.tesla.api.raw.exception.ReAuthenticationException;
import com.ansill.tesla.api.raw.exception.VehicleIDNotFoundException;
import com.ansill.tesla.api.utility.Constants;
import com.ansill.tesla.api.utility.HTTPUtility;
import com.ansill.tesla.api.utility.ReusableResponse;
import com.ansill.validation.Validation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import static com.ansill.utility.Utility.f;

/** Raw (Very Low Level) Tesla API client */
public final class Client{

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

  /** Number of connection attempts */
  private static final int MAX_ATTEMPTS = 5;

  /** Client ID to use */
  @Nonnull
  private final String clientId;

  /** Client secret to use */
  @Nonnull
  private final String clientSecret;

  /** URL */
  @Nonnull
  private final String url;

  /** Connect Timeout Duration */
  @Nonnull
  private final AtomicReference<Duration> connectTimeoutDuration = new AtomicReference<>();

  /** Read Timeout Duration */
  @Nonnull
  private final AtomicReference<Duration> readTimeoutDuration = new AtomicReference<>();

  private final boolean verifySleepingState = true;

  /** Debugging function to fire on unexpected json properties */
  @Nonnull
  private final AtomicReference<Function<Map<String,Optional<Object>>,Boolean>> unknownFieldsFunction = new AtomicReference<>();

  @Nonnull
  private final ObjectMapper objectMapper = new ObjectMapper();

  private Client(
    @Nullable String url,
    @Nullable String clientId,
    @Nullable String clientSecret,
    @Nullable Duration connectTimeoutDuration,
    @Nullable Duration readTimeoutDuration,
    @Nullable Function<Map<String,Optional<Object>>,Boolean> unknownFieldsFunction
  ){

    // Use default if null
    if(url == null) url = Constants.URL;
    if(clientId == null && clientSecret == null){
      clientId = Constants.CLIENT_ID;
      clientSecret = Constants.CLIENT_SECRET;
    }

    // Ensure that URL is valid
    try{
      new URL(url);
      if(!url.endsWith("/")) url = url + "/";
    }catch(MalformedURLException e){
      throw new IllegalArgumentException("The URL is invalid!");
    }

    // Assign it
    this.url = url;
    this.clientId = Validation.assertNonnull(clientId, "client_id");
    this.clientSecret = Validation.assertNonnull(clientSecret, "client_secret");
    this.connectTimeoutDuration.set(connectTimeoutDuration);
    this.readTimeoutDuration.set(readTimeoutDuration);

    // Set object mapper
    var simpleModule = new SimpleModule();
    simpleModule.addDeserializer(Vehicle.class, new Vehicle.Deserializer(this.unknownFieldsFunction));
    simpleModule.addDeserializer(CompleteVehicle.class, new CompleteVehicle.Deserializer(this.unknownFieldsFunction));
    simpleModule.addDeserializer(ClimateState.class, new ClimateState.Deserializer(this.unknownFieldsFunction));
    simpleModule.addDeserializer(ChargeState.class, new ChargeState.Deserializer(this.unknownFieldsFunction));
    simpleModule.addDeserializer(DriveState.class, new DriveState.Deserializer(this.unknownFieldsFunction));
    simpleModule.addDeserializer(GuiSettings.class, new GuiSettings.Deserializer(this.unknownFieldsFunction));
    simpleModule.addDeserializer(MediaState.class, new MediaState.Deserializer(this.unknownFieldsFunction));
    simpleModule.addDeserializer(SoftwareUpdate.class, new SoftwareUpdate.Deserializer(this.unknownFieldsFunction));
    simpleModule.addDeserializer(SpeedLimitMode.class, new SpeedLimitMode.Deserializer(this.unknownFieldsFunction));
    simpleModule.addDeserializer(VehicleConfig.class, new VehicleConfig.Deserializer(this.unknownFieldsFunction));
    simpleModule.addDeserializer(VehicleState.class, new VehicleState.Deserializer(this.unknownFieldsFunction));
    objectMapper.registerModule(simpleModule);

    // Set function
    setUnknownFieldsFunction(unknownFieldsFunction);
  }

  @Nonnull
  private static <T> T fromJson(
    @Nonnull ObjectMapper objectMapper,
    @Nonnull ReusableResponse response,
    @Nonnull Class<T> type
  )
  throws APIProtocolException, ClientException{

    try{

      // Get body
      String body = response.getBodyAsString()
                            .orElseThrow(() -> new APIProtocolException("The request body is empty!"));

      // Forward it
      return fromJson(objectMapper, body, type);

    }catch(IOException e){
      throw new ClientException("Failed to parse content of response body", e);
    }
  }

  /**
   * Creates builder
   *
   * @return builder
   */
  @Nonnull
  public static Builder builder(){
    return new Builder();
  }

  @Nonnull
  private static <T> T fromJson(
    @Nonnull ObjectMapper objectMapper,
    @Nonnull ReusableResponse response,
    @Nonnull TypeReference<T> typeToken
  )
  throws APIProtocolException, ClientException{

    try{

      // Get body
      String body = response.getBodyAsString()
                            .orElseThrow(() -> new APIProtocolException("The request body is empty!"));

      // Forward it
      return fromJson(objectMapper, body, typeToken);

    }catch(IOException e){
      throw new ClientException("Failed to parse content of response body", e);
    }
  }

  @Nonnull
  private static <T> T fromJson(
    @Nonnull ObjectMapper objectMapper,
    @Nonnull Response response,
    @Nonnull Class<T> type
  )
  throws APIProtocolException, ClientException{

    try{

      // Get body
      String body = HTTPUtility.getStringFromResponseBody(response)
                               .orElseThrow(() -> new APIProtocolException("The request body is empty!"));

      // Forward it
      return fromJson(objectMapper, body, type);

    }catch(IOException e){
      throw new ClientException("Failed to parse content of response body", e);
    }
  }

  @Nonnull
  private static <T> T fromJson(
    @Nonnull ObjectMapper objectMapper,
    @Nonnull String string,
    @Nonnull Class<T> type
  ) throws APIProtocolException{
    T item;
    try{
      item = objectMapper.readValue(string, type);
    }catch(JsonProcessingException e){
      // Log what was in the string and push it up
      throw new APIProtocolException(f(
        "The JSON string is not in format of {} class. JSON message \n\"{}\"",
        type.getName(),
        string
      ), e);
    }
    if(item == null) throw new APIProtocolException(f(
      "The JSON string is not in format of {} class. JSON message \n\"{}\"",
      type.getName(),
      string
    ));
    return item;
  }

  @Nonnull
  private static <T> T fromJson(
    @Nonnull ObjectMapper objectMapper,
    @Nonnull String string,
    @Nonnull TypeReference<T> typeToken
  ) throws APIProtocolException{
    T item;
    try{
      item = objectMapper.readValue(string, typeToken);
    }catch(JsonProcessingException e){
      // Log what was in the string and push it up
      throw new APIProtocolException(f(
        "The JSON string is not in format of {} class. JSON message \n\"{}\"",
        typeToken.getType().getTypeName(),
        string
      ), e);
    }
    if(item == null) throw new APIProtocolException(f(
      "The JSON string is not in format of {} class. JSON message \n\"{}\"",
      typeToken.getType().getTypeName(),
      string
    ));
    return item;
  }

  public void setUnknownFieldsFunction(@Nullable Function<Map<String,Optional<Object>>,Boolean> function){
    unknownFieldsFunction.set(function == null ? item -> false : function);
  }

  public void resetConnectTimeoutDuration(){
    this.connectTimeoutDuration.set(null);
  }

  public void resetReadTimeoutDuration(){
    this.readTimeoutDuration.set(null);
  }

  @Nonnull
  public Optional<Duration> getConnectTimeoutDuration(){
    return Optional.ofNullable(this.connectTimeoutDuration.get());
  }

  public void setConnectTimeoutDuration(@Nonnull Duration timeout){
    this.connectTimeoutDuration.set(Validation.assertNonnull(timeout, "timeout"));
  }

  @Nonnull
  public Optional<Duration> getReadTimeoutDuration(){
    return Optional.ofNullable(this.readTimeoutDuration.get());
  }

  public void setReadTimeoutDuration(@Nonnull Duration timeout){
    this.readTimeoutDuration.set(Validation.assertNonnull(timeout, "timeout"));
  }

  @Nonnull
  private Function<OkHttpClient.Builder,OkHttpClient.Builder> buildClientConfigurator(){
    return builder -> {
      var timeout = this.connectTimeoutDuration.get();
      if(timeout != null) builder.connectTimeout(timeout);
      timeout = this.readTimeoutDuration.get();
      if(timeout != null) builder.readTimeout(timeout);
      return builder;
    };
  }

  /**
   * Authenticates the account to retrieve an object with access and refresh tokens
   *
   * @param emailAddress email address to the account
   * @param password     password to the account
   * @return object that contains access and refresh tokens
   * @throws AuthenticationException thrown if failed to authenticate
   * @throws ClientException         thrown if failed to read from the service
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
    try(ReusableResponse response = HTTPUtility.httpCall(request, buildClientConfigurator())){

      // Handle code
      return switch(response.code()){

        // Success
        case 200 -> fromJson(objectMapper, response, SuccessfulAuthenticationResponse.class);

        // Unauthorized
        case 401 -> {

          // Parse error
          var error = fromJson(objectMapper, response, GenericErrorResponse.class);

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
    var requestBody = new FormBody.Builder().build();

    // Set up request
    Request request = new Request.Builder().url(url + "oauth/revoke")
                                           .addHeader("Authorization", "Bearer " + refreshToken)
                                           .post(requestBody)
                                           .build();

    // Send request
    try(ReusableResponse response = HTTPUtility.httpCall(request, buildClientConfigurator())){

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
    try(ReusableResponse response = HTTPUtility.httpCall(request, buildClientConfigurator())){

      // Handle code
      return switch(response.code()){

        // Success
        case 200 -> fromJson(objectMapper, response, SuccessfulAuthenticationResponse.class);

        // Unauthorized
        case 401 -> {

          // Parse error
          var error = fromJson(objectMapper, response, GenericErrorResponse.class);

          // If we get an response, ignore other error
          if(error.getResponse().isPresent()){

            // Get response
            var item = error.getResponse().orElseThrow(() -> new APIProtocolException(f(
              "Unexpected empty response in object: {}",
              error
            )));

            // Check if it's invalid password TODO correct place?
            if(!item.startsWith("authorization_required_for_txid_")) throw new APIProtocolException(f(
              "Unexpected string in 401 error response: {}",
              error
            ));

            // It's invalid token error
            throw new ReAuthenticationException();

          }else{

            // Check if it's invalid_client
            if("invalid_client".equals(error.getError().orElse(""))){
              throw new ClientException(error.getErrorDescription().orElseThrow());
            }

            // Check if it's invalid_grant
            if("invalid_grant".equals(error.getError().orElse(""))){

              // Check if error description matches
              if("The provided authorization grant is invalid, expired, revoked, does not match the redirection URI used in the authorization request, or was issued to another client."
                .equals(error.getErrorDescription().orElse(""))){

                // It's invalid token error
                throw new ReAuthenticationException();
              }
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
    try(ReusableResponse response = HTTPUtility.httpCall(request, buildClientConfigurator())){

      // Handle code
      return switch(response.code()){

        // Success
        case 200 -> fromJson(objectMapper, response, VehiclesResponse.class);

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
    try(ReusableResponse response = HTTPUtility.httpCall(request, buildClientConfigurator())){

      // Handle code
      return switch(response.code()){

        // Success
        case 200 -> Optional.of(fromJson(objectMapper, response, VehicleResponse.class));

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

  /**
   * Issues command to wake the vehicle up
   * <B>NOTE:</B> Does not guarantee that vehicle is awoke after this command completes
   *
   * @param accessToken access token
   * @param idString    vehicle id
   * @return vehicle response
   * @throws VehicleIDNotFoundException thrown if the vehicle does not exist
   */
  @Nonnull
  public VehicleResponse wakeup(@Nonnull String accessToken, @Nonnull String idString)
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
    try(ReusableResponse response = HTTPUtility.httpCall(request, buildClientConfigurator())){

      // Handle code
      return switch(response.code()){

        // Success
        case 200 -> fromJson(objectMapper, response, VehicleResponse.class);

        // Unauthenticated
        case 401 -> throw new InvalidAccessTokenException();

        // Not found
        case 404 -> throw new VehicleIDNotFoundException(idString);

        // In service
        case 405 -> throw new VehicleInServiceException();

        // Request Timeout
        case 408 -> throw new VehicleSleepingException(); // TODO will this ever happen?

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
  ) throws VehicleIDNotFoundException{

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
    try(ReusableResponse response = HTTPUtility.httpCall(request, buildClientConfigurator())){

      // Handle code
      return switch(response.code()){

        // Success
        case 200 -> fromJson(objectMapper, response, SimpleReasonResponse.class);

        // Unauthenticated
        case 401 -> throw new InvalidAccessTokenException();

        // In service
        case 405 -> throw new VehicleInServiceException();

        // Request Timeout
        case 408 -> throw new VehicleSleepingException();

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
  private <T> T getVehicleDataForm(
    @Nonnull String accessToken,
    @Nonnull String idString,
    @Nonnull TypeReference<T> typeToken,
    @Nonnull String path,
    @Nonnegative int attemptsRemaining
  ) throws VehicleIDNotFoundException{

    // Set up request
    Request request = new Request.Builder().url(url + "api/1/vehicles/" + idString + "/" + path)
                                           .addHeader("Authorization", "Bearer " + accessToken)
                                           .get()
                                           .build();

    // Send request
    try(ReusableResponse response = HTTPUtility.httpCall(request, buildClientConfigurator())){

      // Handle code
      return switch(response.code()){

        // Success
        case 200 -> fromJson(objectMapper, response, typeToken);

        // Unauthenticated
        case 401 -> throw new InvalidAccessTokenException();

        // Not found
        case 404 -> throw new VehicleIDNotFoundException(idString);

        // In service
        case 405 -> throw new VehicleInServiceException();

        // Request Timeout
        case 408 -> throw new VehicleSleepingException();

        // Unknown
        default -> throw new APIProtocolException(f("Unexpected status code: {}", response.code()));
      };
    }catch(SocketTimeoutException e){

      // Possible sleeping state
      if(!verifySleepingState){
        throw new ClientException(
          "SocketTimeoutException thrown on possible sleeping vehicle and Client has been told to not attempt to verify the state",
          e
        );
      }

      // Error if attempts ran out
      if(attemptsRemaining <= 0) throw new ClientException(
        "Failed to get vehicle data - client kept getting multiple SocketTimeoutException");

      // Call on vehicle
      var vehicle = this.getVehicle(accessToken, idString).orElseThrow();

      // Get state - if asleep, throw VehicleUnavailableException
      if("asleep".equals(vehicle.getResponse().getState())) throw new VehicleSleepingException();

      // Get state - if online, try again
      if("online".equals(vehicle.getResponse().getState())) return getVehicleDataForm(
        accessToken,
        idString,
        typeToken,
        path,
        attemptsRemaining - 1
      );

      // Get state - if offline, TODO do we need to do anything for this?
      if("offline".equals(vehicle.getResponse().getState())) throw new VehicleOfflineException();

      // Else throw protocol error
      throw new APIProtocolException(f(
        "Cannot determine the cause of SocketTimeoutException, received state '{}'",
        vehicle.getResponse().getState()
      ));

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
  private <T> T getVehicleDataForm(
    @Nonnull String accessToken,
    @Nonnull String idString,
    @Nonnull TypeReference<T> typeToken,
    @Nonnull String path
  )
  throws VehicleIDNotFoundException{
    return getVehicleDataForm(accessToken, idString, typeToken, path, MAX_ATTEMPTS);
  }

  @Nonnull
  public CompleteVehicleDataResponse getVehicleData(@Nonnull String accessToken, @Nonnull String idString)
  throws VehicleIDNotFoundException{

    // Check parameters
    Validation.assertNonnull(accessToken, "accessToken");

    // Check parameters
    Validation.assertNonnull(idString, "idString");

    // Type
    var type = new TypeReference<CompleteVehicleDataResponse>(){
    };

    // Get the data
    return getVehicleDataForm(accessToken, idString, type, "vehicle_data");
  }

  @Nonnull
  public ChargeState getVehicleChargeState(@Nonnull String accessToken, @Nonnull String idString)
  throws VehicleIDNotFoundException{

    // Check parameters
    Validation.assertNonnull(accessToken, "accessToken");

    // Check parameters
    Validation.assertNonnull(idString, "idString");

    // Type
    var typeToken = new TypeReference<SimpleResponse<ChargeState>>(){
    };

    var result = getVehicleDataForm(accessToken, idString, typeToken, "data_request/charge_state");

    // Get the data
    return result.getResponse();
  }

  @Nonnull
  public ClimateState getVehicleClimateState(@Nonnull String accessToken, @Nonnull String idString)
  throws VehicleIDNotFoundException{

    // Check parameters
    Validation.assertNonnull(accessToken, "accessToken");

    // Check parameters
    Validation.assertNonnull(idString, "idString");

    // Type
    var typeToken = new TypeReference<SimpleResponse<ClimateState>>(){
    };

    // Get the data
    return getVehicleDataForm(accessToken, idString, typeToken, "data_request/climate_state").getResponse();
  }

  @Nonnull
  public DriveState getVehicleDriveState(@Nonnull String accessToken, @Nonnull String idString)
  throws VehicleIDNotFoundException{

    // Check parameters
    Validation.assertNonnull(accessToken, "accessToken");

    // Check parameters
    Validation.assertNonnull(idString, "idString");

    // Type
    var typeToken = new TypeReference<SimpleResponse<DriveState>>(){
    };

    // Get the data
    return getVehicleDataForm(accessToken, idString, typeToken, "data_request/drive_state").getResponse();
  }

  @Nonnull
  public GuiSettings getVehicleGuiSettings(@Nonnull String accessToken, @Nonnull String idString)
  throws VehicleIDNotFoundException{

    // Check parameters
    Validation.assertNonnull(accessToken, "accessToken");

    // Check parameters
    Validation.assertNonnull(idString, "idString");

    // Type
    var typeToken = new TypeReference<SimpleResponse<GuiSettings>>(){
    };

    // Get the data
    return getVehicleDataForm(accessToken, idString, typeToken, "data_request/gui_settings").getResponse();
  }

  @Nonnull
  public VehicleState getVehicleVehicleState(@Nonnull String accessToken, @Nonnull String idString)
  throws VehicleIDNotFoundException{

    // Check parameters
    Validation.assertNonnull(accessToken, "accessToken");

    // Check parameters
    Validation.assertNonnull(idString, "idString");

    // Type
    var typeToken = new TypeReference<SimpleResponse<VehicleState>>(){
    };

    // Get the data
    return getVehicleDataForm(accessToken, idString, typeToken, "data_request/vehicle_state").getResponse();
  }

  @Nonnull
  public VehicleConfig getVehicleVehicleConfig(@Nonnull String accessToken, @Nonnull String idString)
  throws VehicleIDNotFoundException{

    // Check parameters
    Validation.assertNonnull(accessToken, "accessToken");

    // Check parameters
    Validation.assertNonnull(idString, "idString");

    // Type
    var typeToken = new TypeReference<SimpleResponse<VehicleConfig>>(){
    };

    // Get the data
    return getVehicleDataForm(accessToken, idString, typeToken, "data_request/vehicle_config").getResponse();
  }

  /** Builder */
  public static class Builder extends ClientBuilder<Client>{

    @Nonnull
    @Override
    public Client build(){
      return new Client(
        url,
        clientId,
        clientSecret,
        connectTimeoutDuration,
        readTimeoutDuration,
        unknownFieldsFunction
      );
    }
  }
}
