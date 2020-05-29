package com.ansill.tesla.api.low;

import com.ansill.tesla.api.exception.VehicleInServiceException;
import com.ansill.tesla.api.exception.VehicleOfflineException;
import com.ansill.tesla.api.exception.VehicleSleepingException;
import com.ansill.tesla.api.low.exception.APIProtocolException;
import com.ansill.tesla.api.low.exception.AuthenticationException;
import com.ansill.tesla.api.low.exception.ClientException;
import com.ansill.tesla.api.low.exception.InvalidAccessTokenException;
import com.ansill.tesla.api.low.exception.ReAuthenticationException;
import com.ansill.tesla.api.low.exception.VehicleIDNotFoundException;
import com.ansill.tesla.api.low.model.ChargeState;
import com.ansill.tesla.api.low.model.ClimateState;
import com.ansill.tesla.api.low.model.CompleteVehicleDataResponse;
import com.ansill.tesla.api.low.model.DriveState;
import com.ansill.tesla.api.low.model.GenericErrorResponse;
import com.ansill.tesla.api.low.model.GuiSettings;
import com.ansill.tesla.api.low.model.SimpleReasonResponse;
import com.ansill.tesla.api.low.model.SimpleResponse;
import com.ansill.tesla.api.low.model.SuccessfulAuthenticationResponse;
import com.ansill.tesla.api.low.model.VehicleConfig;
import com.ansill.tesla.api.low.model.VehicleResponse;
import com.ansill.tesla.api.low.model.VehicleState;
import com.ansill.tesla.api.low.model.VehiclesResponse;
import com.ansill.tesla.api.model.ClientBuilder;
import com.ansill.tesla.api.utility.HTTPUtility;
import com.ansill.tesla.api.utility.ReusableResponse;
import com.ansill.validation.Validation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.ansill.tesla.api.utility.Constants.*;
import static com.ansill.utility.Utility.f;

/** Low Level Tesla API client */
public final class Client{

  /** Gson instance */
  private final static Gson GSON;

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

  /** Number of connection attempts */
  private static final int MAX_ATTEMPTS = 5;

  static{
    // builder
    var gson = new GsonBuilder();

    // Add strict GSON that will error out if any field is missing
    // TODO

    // Create it
    GSON = gson.create();
  }

  /** Client ID to use */
  @Nonnull
  private final String clientId;

  /** Client secret to use */
  @Nonnull
  private final String clientSecret;

  /** URL */
  @Nonnull
  private final String url;

  private final boolean verifySleepingState = true;

  /** Debugging function to fire on unexpected json properties */
  @Nonnull
  private final AtomicReference<Function<Object,Boolean>> debug = new AtomicReference<>();

  private Client(
    @Nullable String url,
    @Nullable String clientId,
    @Nullable String clientSecret,
    @Nullable Function<Object,Boolean> debug
  ){

    // Use default if null
    if(url == null) url = URL;
    if(clientId == null && clientSecret == null){
      clientId = CLIENT_ID;
      clientSecret = CLIENT_SECRET;
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
    this.debug.set(debug);
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
    @Nonnull ReusableResponse response,
    Class<T> type,
    @Nonnull AtomicReference<Function<Object,Boolean>> debug
  )
  throws APIProtocolException, ClientException{

    try{

      // Get body
      String body = response.getBodyAsString()
                            .orElseThrow(() -> new APIProtocolException("The request body is empty!"));

      // Forward it
      return fromJson(body, type, debug);

    }catch(IOException e){
      throw new ClientException("Failed to parse content of response body", e);
    }
  }

  @Nonnull
  private static <T> T fromJson(
    @Nonnull ReusableResponse response,
    TypeToken<T> typeToken,
    @Nonnull AtomicReference<Function<Object,Boolean>> debug
  )
  throws APIProtocolException, ClientException{

    try{

      // Get body
      String body = response.getBodyAsString()
                            .orElseThrow(() -> new APIProtocolException("The request body is empty!"));

      // Forward it
      return fromJson(body, typeToken, debug);

    }catch(IOException e){
      throw new ClientException("Failed to parse content of response body", e);
    }
  }

  @Nonnull
  private static <T> T fromJson(
    @Nonnull Response response,
    Class<T> type,
    @Nonnull AtomicReference<Function<Object,Boolean>> debug
  )
  throws APIProtocolException, ClientException{

    try{

      // Get body
      String body = HTTPUtility.getStringFromResponseBody(response)
                               .orElseThrow(() -> new APIProtocolException("The request body is empty!"));

      // Forward it
      return fromJson(body, type, debug);

    }catch(IOException e){
      throw new ClientException("Failed to parse content of response body", e);
    }
  }

  @Nonnull
  private static <T> T fromJson(
    @Nonnull String string,
    Class<T> type,
    @Nonnull AtomicReference<Function<Object,Boolean>> debug
  ) throws APIProtocolException{
    T item;
    try{
      item = GSON.fromJson(string, type);
    }catch(JsonSyntaxException e){
      // Log what was in the string and push it up
      throw new JsonSyntaxException("Original: \n" + string, e);
    }
    if(item == null) throw new APIProtocolException(f(
      "The JSON string is not in format of {} class. JSON message \n\"{}\"",
      type.getName(),
      string
    ));
    checkJSONForMissingFields(string, item, debug);
    return item;
  }

  @SuppressWarnings("unchecked") // Shouldn't have problem with casting
  @Nonnull
  private static <T> T fromJson(
    @Nonnull String string,
    TypeToken<T> typeToken,
    @Nonnull AtomicReference<Function<Object,Boolean>> debug
  ) throws APIProtocolException{
    var item = (T) GSON.fromJson(string, typeToken.getType());
    if(item == null) throw new APIProtocolException(f(
      "The JSON string is not in format of {} class. JSON message \n\"{}\"",
      typeToken.getType().getTypeName(),
      string
    ));
    checkJSONForMissingFields(string, item, debug);
    return item;
  }

  private static void checkJSONForMissingFields(
    @Nonnull String string,
    Object result,
    AtomicReference<Function<Object,Boolean>> debug
  ) throws APIProtocolException{

    // Deserialize JSON but to plain old map
    Map<String,Object> map = GSON.fromJson(string, new TypeToken<Map<String,Object>>(){
    }.getType());

    // Run it
    recursiveCheckJSONForMissingFields(map, result, debug);
  }

  @SuppressWarnings("unchecked")
  private static void recursiveCheckJSONForMissingFields(
    @Nonnull Map<String,Object> map,
    @Nonnull Object result,
    AtomicReference<Function<Object,Boolean>> debug
  ) throws APIProtocolException{

    // Turn fields to set
    Map<String,Field> fields = getFields(result.getClass()).stream()
                                                           .collect(
                                                             Collectors.toMap(
                                                               Field::getName,
                                                               item -> item
                                                             )
                                                           );

    // Go every entry in the map - check if it exists in object
    for(var entry : map.entrySet()){

      if(!fields.containsKey(entry.getKey())){

        // Log to logger if debugger is undefined
        if(debug.get() == null){
          LOGGER.warn(
            "Class '{}' is missing a field called '{}' ({}), Stacktrace: {}",
            result.getClass().getName(),
            entry.getKey(),
            entry.getValue().getClass().getName(),
            Arrays.stream(Thread.currentThread().getStackTrace())
                  .map(StackTraceElement::toString)
                  .collect(Collectors.joining("\n"))
          );
        }else{

          // Otherwise pass it into debugger
          if(debug.get().apply(null)) throw new APIProtocolException(null); // TODO define message

        }
      }

      // If map, then get equivalent object and follow that in
      if(entry.getValue() instanceof Map){

        // Get inner map
        var inner = (Map<String,Object>) entry.getValue();

        // Get equivalent object
        var field = fields.get(entry.getKey());
        try{
          var value = field.get(result);

          // Now analyze that
          recursiveCheckJSONForMissingFields(inner, value, debug);

        }catch(IllegalAccessException e){

          // Try temporarily reduce access
          try{
            field.setAccessible(true);

            var value = field.get(result);

            // Now analyze that
            recursiveCheckJSONForMissingFields(inner, value, debug);

          }catch(IllegalAccessException ex){
            ex.printStackTrace();
          }finally{
            field.setAccessible(false);
          }
        }
      }

    }

  }

  private static Set<Field> getFields(@Nonnull Class<?> item){

    Set<Field> fields = new HashSet<>(Arrays.asList(item.getDeclaredFields()));

    // Check if there's a parent class that is not Object
    if(item.getSuperclass() != null){
      fields.addAll(getFields(item.getSuperclass()));
    }

    return fields;
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
    try(ReusableResponse response = HTTPUtility.httpCall(request)){

      // Handle code
      return switch(response.code()){

        // Success
        case 200 -> fromJson(response, SuccessfulAuthenticationResponse.class, debug);

        // Unauthorized
        case 401 -> {

          // Parse error
          var error = fromJson(response, GenericErrorResponse.class, debug);

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
    try(ReusableResponse response = HTTPUtility.httpCall(request)){

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
    try(ReusableResponse response = HTTPUtility.httpCall(request)){

      // Handle code
      return switch(response.code()){

        // Success
        case 200 -> fromJson(response, SuccessfulAuthenticationResponse.class, debug);

        // Unauthorized
        case 401 -> {

          // Parse error
          var error = fromJson(response, GenericErrorResponse.class, debug);

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

            // It's invalid token error
            throw new ReAuthenticationException();

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
    try(ReusableResponse response = HTTPUtility.httpCall(request)){

      // Handle code
      return switch(response.code()){

        // Success
        case 200 -> fromJson(response, VehiclesResponse.class, debug);

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
    try(ReusableResponse response = HTTPUtility.httpCall(request)){

      // Handle code
      return switch(response.code()){

        // Success
        case 200 -> Optional.of(fromJson(response, VehicleResponse.class, debug));

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
    try(ReusableResponse response = HTTPUtility.httpCall(request)){

      // Handle code
      return switch(response.code()){

        // Success
        case 200 -> fromJson(response, VehicleResponse.class, debug);

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
    try(ReusableResponse response = HTTPUtility.httpCall(request)){

      // Handle code
      return switch(response.code()){

        // Success
        case 200 -> fromJson(response, SimpleReasonResponse.class, debug);

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
    @Nonnull TypeToken<T> typeToken,
    @Nonnull String path,
    @Nonnegative int attemptsRemaining
  ) throws VehicleIDNotFoundException{

    // Set up request
    Request request = new Request.Builder().url(url + "api/1/vehicles/" + idString + "/" + path)
                                           .addHeader("Authorization", "Bearer " + accessToken)
                                           .get()
                                           .build();

    // Send request
    try(ReusableResponse response = HTTPUtility.httpCall(request)){

      // Handle code
      return switch(response.code()){

        // Success
        case 200 -> fromJson(response, typeToken, debug);

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
    @Nonnull TypeToken<T> typeToken,
    @Nonnull String path
  )
  throws VehicleIDNotFoundException{
    return getVehicleDataForm(accessToken, idString, typeToken, path, MAX_ATTEMPTS);
  }

  /** Builder */
  public static class Builder extends ClientBuilder<Client>{

    @Nonnull
    @Override
    public Client build(){
      return new Client(url, clientId, clientSecret, debug);
    }
  }

  @Nonnull
  public CompleteVehicleDataResponse getVehicleData(@Nonnull String accessToken, @Nonnull String idString)
  throws VehicleIDNotFoundException{

    // Check parameters
    Validation.assertNonnull(accessToken, "accessToken");

    // Check parameters
    Validation.assertNonnull(idString, "idString");

    // Type
    var type = new TypeToken<CompleteVehicleDataResponse>(){
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
    var typeToken = new TypeToken<SimpleResponse<ChargeState>>(){
    };

    // Get the data
    return getVehicleDataForm(accessToken, idString, typeToken, "data_request/charge_state").getResponse();
  }

  @Nonnull
  public ClimateState getVehicleClimateState(@Nonnull String accessToken, @Nonnull String idString)
  throws VehicleIDNotFoundException{

    // Check parameters
    Validation.assertNonnull(accessToken, "accessToken");

    // Check parameters
    Validation.assertNonnull(idString, "idString");

    // Type
    var typeToken = new TypeToken<SimpleResponse<ClimateState>>(){
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
    var typeToken = new TypeToken<SimpleResponse<DriveState>>(){
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
    var typeToken = new TypeToken<SimpleResponse<GuiSettings>>(){
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
    var typeToken = new TypeToken<SimpleResponse<VehicleState>>(){
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
    var typeToken = new TypeToken<SimpleResponse<VehicleConfig>>(){
    };

    // Get the data
    return getVehicleDataForm(accessToken, idString, typeToken, "data_request/vehicle_config").getResponse();
  }
}
