package com.ansill.tesla.api.test;

import com.ansill.tesla.api.exception.VehicleInServiceException;
import com.ansill.tesla.api.exception.VehicleOfflineException;
import com.ansill.tesla.api.exception.VehicleSleepingException;
import com.ansill.tesla.api.low.Client;
import com.ansill.tesla.api.low.exception.APIProtocolException;
import com.ansill.tesla.api.low.exception.AuthenticationException;
import com.ansill.tesla.api.low.exception.ClientException;
import com.ansill.tesla.api.low.exception.InvalidAccessTokenException;
import com.ansill.tesla.api.low.exception.ReAuthenticationException;
import com.ansill.tesla.api.low.exception.VehicleIDNotFoundException;
import com.ansill.tesla.api.low.model.CompleteVehicleDataResponse;
import com.ansill.tesla.api.low.model.GenericErrorResponse;
import com.ansill.tesla.api.low.model.SuccessfulAuthenticationResponse;
import com.ansill.tesla.api.low.model.Vehicle;
import com.ansill.tesla.api.low.model.VehicleResponse;
import com.ansill.tesla.api.low.model.VehiclesResponse;
import com.google.gson.Gson;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.ansill.tesla.api.test.TestUtility.*;
import static com.ansill.utility.Utility.generateString;
import static org.junit.jupiter.api.Assertions.*;

class LowClientTest{

  private static final Gson GSON = new Gson();

  private static final AtomicReference<Consumer<Context>> AUTHENTICATION_HANDLER = new AtomicReference<>();

  private static final AtomicReference<Consumer<Context>> REVOKE_HANDLER = new AtomicReference<>();

  private static final AtomicReference<Consumer<Context>> VEHICLES_HANDLER = new AtomicReference<>();

  private static final AtomicReference<Consumer<Context>> VEHICLE_HANDLER = new AtomicReference<>();

  private static final AtomicReference<Consumer<Context>> VEHICLE_DATA_HANDLER = new AtomicReference<>();

  private static Javalin SERVER;

  private static int PORT;

  private Client client;

  private String client_id;

  private String client_secret;

  @BeforeAll
  static void setUp(){

    // Set port
    PORT = 2000;

    // Loop until available port exists
    var success = false;
    do{

      // Attempt to start server
      try{
        SERVER = Javalin.create().start(PORT);

        // If no exception, mark success
        success = true;

      }catch(Exception e){

        // Increment port
        PORT++;
      }

    }while(!success);

    // Bind endpoints
    SERVER.post("/oauth/token", ctx -> AUTHENTICATION_HANDLER.get().accept(ctx));
    SERVER.post("/oauth/revoke", ctx -> REVOKE_HANDLER.get().accept(ctx));
    SERVER.get("/api/1/vehicles", ctx -> VEHICLES_HANDLER.get().accept(ctx));
    SERVER.get("/api/1/vehicles/:id", ctx -> VEHICLE_HANDLER.get().accept(ctx));
    SERVER.get("/api/1/vehicles/:id/:type", ctx -> VEHICLE_HANDLER.get().accept(ctx));
    SERVER.post("/api/1/vehicles/:id/:type", ctx -> VEHICLE_HANDLER.get().accept(ctx));
    SERVER.get("/api/1/vehicles/:id/data_request/:type", ctx -> VEHICLE_DATA_HANDLER.get().accept(ctx));

  }

  @AfterAll
  static void tearDown(){
    SERVER.stop();
  }

  @BeforeEach
  void setUpEach(){
    client_id = generateString(16);
    client_secret = generateString(16);
    client = Client.builder()
                   .setUrl("http://localhost:" + PORT)
                   .setClientId(client_id)
                   .setClientSecret(client_secret)
                   .build();
  }

  @AfterEach
  void tearDownEach(){
    // Do nothing
  }

  @RepeatedTest(3)
  void testConstructor(){

    assertThrows(IllegalArgumentException.class, () -> Client.builder().setUrl("http://localhost:" + PORT).setClientId(
      null).setClientSecret(client_secret).build());

    assertThrows(IllegalArgumentException.class, () -> Client.builder().setUrl("http://localhost:" + PORT).setClientId(
      client_id).setClientSecret(null).build());

  }

  @RepeatedTest(3)
  void testAuthenticationSuccess(){

    // Generate email address
    var emailAddress = generateEmailAddress();

    // Generate password
    var password = generateString(8);

    // Generate response content
    var accessToken = generateString(32);
    var refreshToken = generateString(32);
    var createdAt = System.currentTimeMillis();
    int expiresIn = (int) ((Math.random() * 2000000) + 2000000);
    var bearer = generateString(8);

    // Set up 'catch' function
    AUTHENTICATION_HANDLER.set(ctx -> {

      // Ensure no leftovers
      var params = new HashSet<>(ctx.formParamMap().keySet());
      assertTrue(params.remove("grant_type"));
      assertTrue(params.remove("email"));
      assertTrue(params.remove("password"));
      assertTrue(params.remove("client_id"));
      assertTrue(params.remove("client_secret"));
      assertEquals(Collections.emptySet(), params);
      assertEquals(Collections.emptySet(), ctx.pathParamMap().keySet());
      assertEquals(Collections.emptySet(), ctx.queryParamMap().keySet());

      // grant type must exist and contains password
      assertEquals("password", ctx.formParam("grant_type"));
      assertEquals(emailAddress, ctx.formParam("email"));
      assertEquals(password, ctx.formParam("password"));
      assertEquals(client_id, ctx.formParam("client_id"));
      assertEquals(client_secret, ctx.formParam("client_secret"));

      // Set up response
      var response = new SuccessfulAuthenticationResponse(accessToken, bearer, expiresIn, refreshToken, createdAt);

      // Send response
      ctx.status(200);
      ctx.result(writeToJson(GSON, response));
    });

    // Fire it
    var result = assertDoesNotThrow(() -> client.authenticate(emailAddress, password));

    // Compare values
    assertEquals(accessToken, result.getAccessToken());
    assertEquals(refreshToken, result.getRefreshToken());
    assertEquals(createdAt, result.getCreatedAt());
    assertEquals(expiresIn, result.getExpiresIn());
    assertEquals(bearer, result.getTokenType());
  }

  @Test
  void testAuthenticationInvalidPassword(){

    // Generate email address
    var emailAddress = generateEmailAddress();

    // Generate password
    var password = generateString(8);

    // Generate response content
    var responseString = "authorization_required_for_txid_" + generateString(8);

    // Set up 'catch' function
    AUTHENTICATION_HANDLER.set(ctx -> {

      // Set up response
      var response = new GenericErrorResponse(null, null, responseString);

      // Send response
      ctx.status(401);
      ctx.result(writeToJson(GSON, response));
    });

    // Fire it
    var exception = assertThrows(AuthenticationException.class, () -> client.authenticate(emailAddress, password));

    // Check which email address
    assertEquals(emailAddress, exception.getAccountName());

  }

  @Test
  void testAuthenticationInvalidClient(){

    // Generate email address
    var emailAddress = generateEmailAddress();

    // Generate password
    var password = generateString(8);

    // Set up description
    var description = "Client authentication failed due to unknown client, no client authentication included, or unsupported authentication method.";

    // Set up 'catch' function
    AUTHENTICATION_HANDLER.set(ctx -> {

      // Set up response
      var response = new GenericErrorResponse("invalid_client", description, null);

      // Send response
      ctx.status(401);
      ctx.result(writeToJson(GSON, response));
    });

    // Fire it
    var exception = assertThrows(ClientException.class, () -> client.authenticate(emailAddress, password));

    // Check which message
    assertEquals(new ClientException(description).getMessage(), exception.getMessage());
  }

  @Test
  void testAuthenticationInvalidForm(){

    // Generate email address
    var emailAddress = generateEmailAddress();

    // Generate password
    var password = generateString(8);

    // Set up description
    var description = "The request is missing a required parameter, includes an unsupported parameter value, or is otherwise malformed.";

    // Set up 'catch' function
    AUTHENTICATION_HANDLER.set(ctx -> {

      // Set up response
      var response = new GenericErrorResponse("invalid_request", description, null);

      // Send response
      ctx.status(401);
      ctx.result(writeToJson(GSON, response));
    });

    // Fire it
    assertThrows(APIProtocolException.class, () -> client.authenticate(emailAddress, password));

  }

  @RepeatedTest(3)
  void testRevokeSuccess(){

    // Generate response content
    var accessToken = generateString(32);

    // Set up 'catch' function
    REVOKE_HANDLER.set(ctx -> {

      // Ensure no leftovers
      assertEquals(Collections.emptySet(), ctx.formParamMap().keySet());
      assertEquals(Collections.emptySet(), ctx.pathParamMap().keySet());
      assertEquals(Collections.emptySet(), ctx.queryParamMap().keySet());

      // Request must contain correct header
      assertEquals("Bearer " + accessToken, ctx.header("Authorization"));

      // Send response
      ctx.status(200);
    });

    // Fire it
    assertDoesNotThrow(() -> client.revokeToken(accessToken));
  }

  @RepeatedTest(3)
  void testRefreshSuccess(){

    // Get old refresh token
    var oldRefreshToken = generateString(32);

    // Generate response content
    var accessToken = generateString(32);
    var refreshToken = generateString(32);
    var createdAt = System.currentTimeMillis();
    int expiresIn = (int) ((Math.random() * 2000000) + 2000000);
    var bearer = generateString(8);

    // Set up 'catch' function
    AUTHENTICATION_HANDLER.set(ctx -> {

      // Ensure no leftovers
      var params = new HashSet<>(ctx.formParamMap().keySet());
      assertTrue(params.remove("grant_type"));
      assertTrue(params.remove("refresh_token"));
      assertTrue(params.remove("client_id"));
      assertTrue(params.remove("client_secret"));
      assertEquals(Collections.emptySet(), params);
      assertEquals(Collections.emptySet(), ctx.pathParamMap().keySet());
      assertEquals(Collections.emptySet(), ctx.queryParamMap().keySet());

      // grant type must exist and contains password
      assertEquals("refresh_token", ctx.formParam("grant_type"));
      assertEquals(oldRefreshToken, ctx.formParam("refresh_token"));
      assertEquals(client_id, ctx.formParam("client_id"));
      assertEquals(client_secret, ctx.formParam("client_secret"));

      // Set up response
      var response = new SuccessfulAuthenticationResponse(accessToken, bearer, expiresIn, refreshToken, createdAt);

      // Send response
      ctx.status(200);
      ctx.result(writeToJson(GSON, response));
    });

    // Fire it
    var result = assertDoesNotThrow(() -> client.refreshToken(oldRefreshToken));

    // Compare values
    assertEquals(accessToken, result.getAccessToken());
    assertEquals(refreshToken, result.getRefreshToken());
    assertEquals(createdAt, result.getCreatedAt());
    assertEquals(expiresIn, result.getExpiresIn());
    assertEquals(bearer, result.getTokenType());
  }

  @Test
  void testRefreshInvalidToken(){

    // Get old refresh token
    var oldRefreshToken = generateString(32);

    // Generate response content
    var responseString = "authorization_required_for_txid_" + generateString(8);

    // Set up 'catch' function
    AUTHENTICATION_HANDLER.set(ctx -> {

      // Set up response
      var response = new GenericErrorResponse(null, null, responseString);

      // Send response
      ctx.status(401);
      ctx.result(writeToJson(GSON, response));
    });

    // Fire it
    assertThrows(ReAuthenticationException.class, () -> client.refreshToken(oldRefreshToken));

  }

  @Test
  void testRefreshInvalidClient(){

    // Get old refresh token
    var oldRefreshToken = generateString(32);

    // Set up description
    var description = "Client authentication failed due to unknown client, no client authentication included, or unsupported authentication method.";

    // Set up 'catch' function
    AUTHENTICATION_HANDLER.set(ctx -> {

      // Set up response
      var response = new GenericErrorResponse("invalid_client", description, null);

      // Send response
      ctx.status(401);
      ctx.result(writeToJson(GSON, response));
    });

    // Fire it
    var exception = assertThrows(ClientException.class, () -> client.refreshToken(oldRefreshToken));

    // Check which message
    assertEquals(new ClientException(description).getMessage(), exception.getMessage());
  }

  @Test
  void testRefreshInvalidForm(){

    // Get old refresh token
    var oldRefreshToken = generateString(32);

    // Set up description
    var description = "The request is missing a required parameter, includes an unsupported parameter value, or is otherwise malformed.";

    // Set up 'catch' function
    AUTHENTICATION_HANDLER.set(ctx -> {

      // Set up response
      var response = new GenericErrorResponse("invalid_request", description, null);

      // Send response
      ctx.status(401);
      ctx.result(writeToJson(GSON, response));
    });

    // Fire it
    assertThrows(APIProtocolException.class, () -> client.refreshToken(oldRefreshToken));

  }

  @RepeatedTest(3)
  void testVehiclesEmpty(){

    // Get old refresh token
    var accessToken = generateString(32);

    // Set up 'catch' function
    VEHICLES_HANDLER.set(ctx -> {

      // Ensure no leftovers
      assertEquals(Collections.emptySet(), ctx.formParamMap().keySet());
      assertEquals(Collections.emptySet(), ctx.pathParamMap().keySet());
      assertEquals(Collections.emptySet(), ctx.queryParamMap().keySet());

      // Bearer must exist
      assertEquals("Bearer " + accessToken, ctx.header("Authorization"));

      // Set up response
      var response = new VehiclesResponse(Collections.emptyList(), 0);

      // Send response
      ctx.status(200);
      ctx.result(writeToJson(GSON, response));
    });

    // Fire it
    var result = assertDoesNotThrow(() -> client.getVehicles(accessToken));

    // Compare values
    assertEquals(Collections.emptyList(), result.getResponse());
    assertEquals(0, result.getCount());
  }

  @RepeatedTest(30)
  void testVehicles(){

    // Get old refresh token
    var accessToken = generateString(32);

    // Set up list
    var vehicleMap = new HashMap<String,Vehicle>();
    var states = Arrays.stream(com.ansill.tesla.api.med.model.Vehicle.State.values())
                       .map(item -> item.toString()
                                        .toLowerCase())
                       .collect(Collectors.toList());
    IntStream.range(0, (int) ((Math.random() * 5) + 1)).forEach(i -> {
      Collections.shuffle(states);
      var vehicle = generateVehicle();
      vehicleMap.put(vehicle.getIdString(), vehicle);
    });

    // Set up 'catch' function
    VEHICLES_HANDLER.set(ctx -> {

      // Ensure no leftovers
      assertEquals(Collections.emptySet(), ctx.formParamMap().keySet());
      assertEquals(Collections.emptySet(), ctx.pathParamMap().keySet());
      assertEquals(Collections.emptySet(), ctx.queryParamMap().keySet());

      // Bearer must exist
      assertEquals("Bearer " + accessToken, ctx.header("Authorization"));

      // Set up response
      var response = new VehiclesResponse(new LinkedList<>(vehicleMap.values()), vehicleMap.size());

      // Send response
      ctx.status(200);
      ctx.result(writeToJson(GSON, response));
    });

    // Fire it
    var result = assertDoesNotThrow(() -> client.getVehicles(accessToken));

    // Compare values
    assertEquals(vehicleMap.size(), result.getCount());
    assertEquals(
      vehicleMap,
      result.getResponse().stream().collect(Collectors.toMap(Vehicle::getIdString, item -> item))
    );
  }

  @RepeatedTest(3)
  void testVehiclesInvalidToken(){

    // Get old refresh token
    var accessToken = generateString(32);

    // Set up 'catch' function
    VEHICLES_HANDLER.set(ctx -> {

      // Ensure no leftovers
      assertEquals(Collections.emptySet(), ctx.formParamMap().keySet());
      assertEquals(Collections.emptySet(), ctx.pathParamMap().keySet());
      assertEquals(Collections.emptySet(), ctx.queryParamMap().keySet());

      // Bearer must exist
      assertEquals("Bearer " + accessToken, ctx.header("Authorization"));

      // Send response
      ctx.status(401);
    });

    // Fire it
    assertThrows(InvalidAccessTokenException.class, () -> client.getVehicles(accessToken));

  }

  @Test
  void testVehiclesUnexpected(){

    // Get old refresh token
    var accessToken = generateString(32);

    // Set up 'catch' function
    VEHICLES_HANDLER.set(ctx -> {

      // Ensure no leftovers
      assertEquals(Collections.emptySet(), ctx.formParamMap().keySet());
      assertEquals(Collections.emptySet(), ctx.pathParamMap().keySet());
      assertEquals(Collections.emptySet(), ctx.queryParamMap().keySet());

      // Bearer must exist
      assertEquals("Bearer " + accessToken, ctx.header("Authorization"));

      // Send response
      ctx.status(500);
    });

    // Fire it
    assertThrows(APIProtocolException.class, () -> client.getVehicles(accessToken));

  }

  @RepeatedTest(50)
  void testVehicle(){

    // Get old refresh token
    var accessToken = generateString(32);

    // Id
    var vehicle = generateVehicle();

    // Set up 'catch' function
    VEHICLE_HANDLER.set(ctx -> {

      // Ensure no leftovers
      try{
        var pathParams = new HashSet<>(ctx.pathParamMap().keySet());
        assertTrue(pathParams.remove("id"));
        assertEquals(Collections.emptySet(), pathParams);
        assertEquals(Collections.emptySet(), ctx.formParamMap().keySet());
        assertEquals(Collections.emptySet(), ctx.queryParamMap().keySet());

        // Must be get
        assertEquals("get", ctx.method().toLowerCase());

        // Bearer must exist and path params must match
        assertEquals("Bearer " + accessToken, ctx.header("Authorization"));
        assertEquals(vehicle.getIdString(), ctx.pathParam("id"));
        //assertEquals("vehicle_data", ctx.pathParam("type"));

        // Set up response
        var response = new VehicleResponse(vehicle);

        // Send response
        ctx.status(200);
        ctx.result(writeToJson(GSON, response));
      }catch(Exception e){
        e.printStackTrace();
      }
    });

    // Fire it
    var result = assertDoesNotThrow(() -> client.getVehicle(accessToken, vehicle.getIdString()));

    // Compare values
    assertTrue(result.isPresent());
    assertEquals(vehicle, result.get().getResponse());
  }


  @Test
  void testVehicleInvalidVehicleId(){

    // Get old refresh token
    var accessToken = generateString(32);

    // Id
    var vehicleId = generateString(32);

    // Set up 'catch' function
    VEHICLE_HANDLER.set(ctx -> {

      // Ensure no leftovers
      try{
        var pathParams = new HashSet<>(ctx.pathParamMap().keySet());
        assertTrue(pathParams.remove("id"));
        assertEquals(Collections.emptySet(), pathParams);
        assertEquals(Collections.emptySet(), ctx.formParamMap().keySet());
        assertEquals(Collections.emptySet(), ctx.queryParamMap().keySet());

        // Must be get
        assertEquals("get", ctx.method().toLowerCase());

        // Bearer must exist and path params must match
        assertEquals("Bearer " + accessToken, ctx.header("Authorization"));

        // Send response
        ctx.status(404);

      }catch(Exception e){
        e.printStackTrace();
      }
    });

    // Fire it
    var result = assertDoesNotThrow(() -> client.getVehicle(accessToken, vehicleId));

    // Check result
    assertTrue(result.isEmpty());
  }

  @Test
  void testVehicleInvalidToken(){

    // Get old refresh token
    var accessToken = generateString(32);

    // Id
    var vehicleId = generateString(32);

    // Set up 'catch' function
    VEHICLE_HANDLER.set(ctx -> {

      // Ensure no leftovers
      try{
        var pathParams = new HashSet<>(ctx.pathParamMap().keySet());
        assertTrue(pathParams.remove("id"));
        assertEquals(Collections.emptySet(), pathParams);
        assertEquals(Collections.emptySet(), ctx.formParamMap().keySet());
        assertEquals(Collections.emptySet(), ctx.queryParamMap().keySet());

        // Must be get
        assertEquals("get", ctx.method().toLowerCase());

        // Bearer must exist and path params must match
        assertEquals("Bearer " + accessToken, ctx.header("Authorization"));
        assertEquals(vehicleId, ctx.pathParam("id"));

        // Send response
        ctx.status(401);

      }catch(Exception e){
        e.printStackTrace();
      }
    });

    // get
    assertThrows(InvalidAccessTokenException.class, () -> client.getVehicle(accessToken, vehicleId));

  }

  @Test
  void testVehicleUnexpected(){

    // Get old refresh token
    var accessToken = generateString(32);

    // Id
    var vehicle = generateVehicle();

    // Set up 'catch' function
    VEHICLE_HANDLER.set(ctx -> {

      // Ensure no leftovers
      try{
        var pathParams = new HashSet<>(ctx.pathParamMap().keySet());
        assertTrue(pathParams.remove("id"));
        assertEquals(Collections.emptySet(), pathParams);
        assertEquals(Collections.emptySet(), ctx.formParamMap().keySet());
        assertEquals(Collections.emptySet(), ctx.queryParamMap().keySet());

        // Must be get
        assertEquals("get", ctx.method().toLowerCase());

        // Bearer must exist and path params must match
        assertEquals("Bearer " + accessToken, ctx.header("Authorization"));
        assertEquals(vehicle.getIdString(), ctx.pathParam("id"));

        // Send response
        ctx.status(500);

      }catch(Exception e){
        e.printStackTrace();
      }
    });

    // Fire it
    assertThrows(APIProtocolException.class, () -> client.getVehicle(accessToken, vehicle.getIdString()));
  }

  @RepeatedTest(50)
  void testCompleteVehicle(){

    // Get old refresh token
    var accessToken = generateString(32);

    // Id
    var vehicle = generateCompleteVehicle();

    // Set up 'catch' function
    VEHICLE_HANDLER.set(ctx -> {

      // Ensure no leftovers
      try{
        var pathParams = new HashSet<>(ctx.pathParamMap().keySet());
        assertTrue(pathParams.remove("id"));
        assertTrue(pathParams.remove("type"));
        assertEquals(Collections.emptySet(), pathParams);
        assertEquals(Collections.emptySet(), ctx.formParamMap().keySet());
        assertEquals(Collections.emptySet(), ctx.queryParamMap().keySet());

        // Must be get
        assertEquals("get", ctx.method().toLowerCase());

        // Bearer must exist and path params must match
        assertEquals("Bearer " + accessToken, ctx.header("Authorization"));
        assertEquals(vehicle.getIdString(), ctx.pathParam("id"));
        assertEquals("vehicle_data", ctx.pathParam("type"));

        // Set up response
        var response = new CompleteVehicleDataResponse(vehicle);

        // Send response
        ctx.status(200);
        ctx.result(writeToJson(GSON, response));
      }catch(Exception e){
        e.printStackTrace();
      }
    });

    // Fire it
    var result = assertDoesNotThrow(() -> client.getVehicleData(accessToken, vehicle.getIdString()));

    // Compare values
    assertEquals(vehicle, result.getResponse());
  }


  @Test
  void testCompleteVehicleInvalidVehicleId(){

    // Get old refresh token
    var accessToken = generateString(32);

    // Id
    var vehicleId = generateString(32);

    // Set up 'catch' function
    VEHICLE_HANDLER.set(ctx -> {

      // Ensure no leftovers
      try{
        var pathParams = new HashSet<>(ctx.pathParamMap().keySet());
        assertTrue(pathParams.remove("id"));
        assertTrue(pathParams.remove("type"));
        assertEquals(Collections.emptySet(), pathParams);
        assertEquals(Collections.emptySet(), ctx.formParamMap().keySet());
        assertEquals(Collections.emptySet(), ctx.queryParamMap().keySet());

        // Must be get
        assertEquals("get", ctx.method().toLowerCase());

        // Bearer must exist and path params must match
        assertEquals("Bearer " + accessToken, ctx.header("Authorization"));
        assertEquals("vehicle_data", ctx.pathParam("type"));

        // Send response
        ctx.status(404);

      }catch(Exception e){
        e.printStackTrace();
      }
    });

    // Fire it
    assertThrows(VehicleIDNotFoundException.class, () -> client.getVehicleData(accessToken, vehicleId));

  }

  @Test
  void testCompleteVehicleInvalidToken(){

    // Get old refresh token
    var accessToken = generateString(32);

    // Id
    var vehicleId = generateString(32);

    // Set up 'catch' function
    VEHICLE_HANDLER.set(ctx -> {

      // Ensure no leftovers
      try{
        var pathParams = new HashSet<>(ctx.pathParamMap().keySet());
        assertTrue(pathParams.remove("id"));
        assertTrue(pathParams.remove("type"));
        assertEquals(Collections.emptySet(), pathParams);
        assertEquals(Collections.emptySet(), ctx.formParamMap().keySet());
        assertEquals(Collections.emptySet(), ctx.queryParamMap().keySet());

        // Must be get
        assertEquals("get", ctx.method().toLowerCase());

        // Bearer must exist and path params must match
        assertEquals("Bearer " + accessToken, ctx.header("Authorization"));
        assertEquals(vehicleId, ctx.pathParam("id"));
        assertEquals("vehicle_data", ctx.pathParam("type"));

        // Send response
        ctx.status(401);

      }catch(Exception e){
        e.printStackTrace();
      }
    });

    // get
    assertThrows(InvalidAccessTokenException.class, () -> client.getVehicleData(accessToken, vehicleId));

  }

  @Test
  void testCompleteVehicleInService(){

    // Get old refresh token
    var accessToken = generateString(32);

    // Id
    var vehicleId = generateString(32);

    // Set up 'catch' function
    VEHICLE_HANDLER.set(ctx -> {

      // Ensure no leftovers
      try{
        var pathParams = new HashSet<>(ctx.pathParamMap().keySet());
        assertTrue(pathParams.remove("id"));
        assertTrue(pathParams.remove("type"));
        assertEquals(Collections.emptySet(), pathParams);
        assertEquals(Collections.emptySet(), ctx.formParamMap().keySet());
        assertEquals(Collections.emptySet(), ctx.queryParamMap().keySet());

        // Must be get
        assertEquals("get", ctx.method().toLowerCase());

        // Bearer must exist and path params must match
        assertEquals("Bearer " + accessToken, ctx.header("Authorization"));
        assertEquals("vehicle_data", ctx.pathParam("type"));

        // Send response
        ctx.status(405);

      }catch(Exception e){
        e.printStackTrace();
      }
    });

    // Fire it
    assertThrows(VehicleInServiceException.class, () -> client.getVehicleData(accessToken, vehicleId));

  }

  @Test
  void testCompleteVehicleSleeping(){

    // Get old refresh token
    var accessToken = generateString(32);

    // Id
    var vehicleId = generateString(32);

    // Set up 'catch' function
    VEHICLE_HANDLER.set(ctx -> {

      // Ensure no leftovers
      try{
        var pathParams = new HashSet<>(ctx.pathParamMap().keySet());
        assertTrue(pathParams.remove("id"));
        assertTrue(pathParams.remove("type"));
        assertEquals(Collections.emptySet(), pathParams);
        assertEquals(Collections.emptySet(), ctx.formParamMap().keySet());
        assertEquals(Collections.emptySet(), ctx.queryParamMap().keySet());

        // Must be get
        assertEquals("get", ctx.method().toLowerCase());

        // Bearer must exist and path params must match
        assertEquals("Bearer " + accessToken, ctx.header("Authorization"));
        assertEquals("vehicle_data", ctx.pathParam("type"));

        // Send response
        ctx.status(408);

      }catch(Exception e){
        e.printStackTrace();
      }
    });

    // Fire it
    assertThrows(VehicleSleepingException.class, () -> client.getVehicleData(accessToken, vehicleId));

  }

  @Test
  void testCompleteVehicleSleepingViaSocketException(){

    // Get old refresh token
    var accessToken = generateString(32);

    // Generate vehicle
    var vehicle_temp = generateVehicle();

    // We have to set the state and vehicle is an immutable object... oh bother
    var vehicle = new Vehicle(
      vehicle_temp.getId(),
      vehicle_temp.getVehicleId(),
      vehicle_temp.getUserId(),
      vehicle_temp.getVIN(),
      vehicle_temp.getDisplayName(),
      vehicle_temp.getOptionCodes(),
      vehicle_temp.getColor(),
      vehicle_temp.getTokens(),
      com.ansill.tesla.api.med.model.Vehicle.State.ASLEEP.toString().toLowerCase(),
      vehicle_temp.isInService(),
      vehicle_temp.getOptionCodes(),
      vehicle_temp.isCalendarEnabled(),
      vehicle_temp.getApiVersion(),
      vehicle_temp.getBackseatToken(),
      vehicle_temp.getBackseatTokenUpdatedAt()
    );

    // Set up 'catch' function
    var tripped = new AtomicBoolean(false);
    var cdl = new CountDownLatch(1);
    VEHICLE_HANDLER.set(ctx -> {

      // Tripped is for second connection after sockettimeout
      if(tripped.getAndSet(true)){

        // Return vehicle data
        ctx.status(200);
        ctx.result(writeToJson(GSON, new VehicleResponse(vehicle)));

        return;
      }

      // Ensure no leftovers
      try{
        var pathParams = new HashSet<>(ctx.pathParamMap().keySet());
        assertTrue(pathParams.remove("id"));
        assertTrue(pathParams.remove("type"));
        assertEquals(Collections.emptySet(), pathParams);
        assertEquals(Collections.emptySet(), ctx.formParamMap().keySet());
        assertEquals(Collections.emptySet(), ctx.queryParamMap().keySet());

        // Must be get
        assertEquals("get", ctx.method().toLowerCase());

        // Bearer must exist and path params must match
        assertEquals("Bearer " + accessToken, ctx.header("Authorization"));
        assertEquals("vehicle_data", ctx.pathParam("type"));

        // Stall
        cdl.await(30, TimeUnit.SECONDS);
        //Thread.sleep(Duration.ofSeconds(2).toMillis());

      }catch(Exception e){
        e.printStackTrace();
      }
    });

    // Set timeout to too low
    client.setReadTimeoutDuration(Duration.ofMillis(150));

    // Fire it
    assertThrows(VehicleSleepingException.class, () -> client.getVehicleData(accessToken, vehicle.getIdString()));
    cdl.countDown();
  }

  @Test
  void testCompleteVehicleOffline(){

    // Get old refresh token
    var accessToken = generateString(32);

    // Generate vehicle
    var vehicle_temp = generateVehicle();

    // We have to set the state and vehicle is an immutable object... oh bother
    var vehicle = new Vehicle(
      vehicle_temp.getId(),
      vehicle_temp.getVehicleId(),
      vehicle_temp.getUserId(),
      vehicle_temp.getVIN(),
      vehicle_temp.getDisplayName(),
      vehicle_temp.getOptionCodes(),
      vehicle_temp.getColor(),
      vehicle_temp.getTokens(),
      com.ansill.tesla.api.med.model.Vehicle.State.OFFLINE.toString().toLowerCase(),
      vehicle_temp.isInService(),
      vehicle_temp.getOptionCodes(),
      vehicle_temp.isCalendarEnabled(),
      vehicle_temp.getApiVersion(),
      vehicle_temp.getBackseatToken(),
      vehicle_temp.getBackseatTokenUpdatedAt()
    );

    // Set up 'catch' function
    var tripped = new AtomicBoolean(false);
    var cdl = new CountDownLatch(1);
    VEHICLE_HANDLER.set(ctx -> {

      // Tripped is for second connection after sockettimeout
      if(tripped.getAndSet(true)){

        // Return vehicle data
        ctx.status(200);
        ctx.result(writeToJson(GSON, new VehicleResponse(vehicle)));

        return;
      }

      // Ensure no leftovers
      try{
        var pathParams = new HashSet<>(ctx.pathParamMap().keySet());
        assertTrue(pathParams.remove("id"));
        assertTrue(pathParams.remove("type"));
        assertEquals(Collections.emptySet(), pathParams);
        assertEquals(Collections.emptySet(), ctx.formParamMap().keySet());
        assertEquals(Collections.emptySet(), ctx.queryParamMap().keySet());

        // Must be get
        assertEquals("get", ctx.method().toLowerCase());

        // Bearer must exist and path params must match
        assertEquals("Bearer " + accessToken, ctx.header("Authorization"));
        assertEquals("vehicle_data", ctx.pathParam("type"));

        // Stall
        //Thread.sleep(Duration.ofSeconds(2).toMillis());
        cdl.await(30, TimeUnit.SECONDS);

      }catch(Exception e){
        e.printStackTrace();
      }
    });

    // Set timeout to too low
    client.setReadTimeoutDuration(Duration.ofMillis(150));

    // Fire it
    assertThrows(VehicleOfflineException.class, () -> client.getVehicleData(accessToken, vehicle.getIdString()));
    cdl.countDown();
  }

  @Test
  void testCompleteVehicleUnexpected(){

    // Get old refresh token
    var accessToken = generateString(32);

    // Id
    var vehicle = generateVehicle();

    // Set up 'catch' function
    VEHICLE_HANDLER.set(ctx -> {

      // Ensure no leftovers
      try{
        var pathParams = new HashSet<>(ctx.pathParamMap().keySet());
        assertTrue(pathParams.remove("id"));
        assertTrue(pathParams.remove("type"));
        assertEquals(Collections.emptySet(), pathParams);
        assertEquals(Collections.emptySet(), ctx.formParamMap().keySet());
        assertEquals(Collections.emptySet(), ctx.queryParamMap().keySet());

        // Must be get
        assertEquals("get", ctx.method().toLowerCase());

        // Bearer must exist and path params must match
        assertEquals("Bearer " + accessToken, ctx.header("Authorization"));
        assertEquals(vehicle.getIdString(), ctx.pathParam("id"));
        assertEquals("vehicle_data", ctx.pathParam("type"));

        // Send response
        ctx.status(500);

      }catch(Exception e){
        e.printStackTrace();
      }
    });

    // Fire it
    assertThrows(APIProtocolException.class, () -> client.getVehicleData(accessToken, vehicle.getIdString()));
  }

  private <T> void testVehicleData(String path, T data, CheckedBiFunction<String,String,T> function){

    // Get old refresh token
    var accessToken = generateString(32);

    // Set up id
    var vehicleId = generateString(32);

    // Set up 'catch' function
    VEHICLE_DATA_HANDLER.set(ctx -> {

      // Ensure no leftovers
      try{
        var pathParams = new HashSet<>(ctx.pathParamMap().keySet());
        assertTrue(pathParams.remove("id"));
        assertTrue(pathParams.remove("type"));
        assertEquals(Collections.emptySet(), pathParams);
        assertEquals(Collections.emptySet(), ctx.formParamMap().keySet());
        assertEquals(Collections.emptySet(), ctx.queryParamMap().keySet());

        // Must be get
        assertEquals("get", ctx.method().toLowerCase());

        // Bearer must exist and path params must match
        assertEquals("Bearer " + accessToken, ctx.header("Authorization"));
        assertEquals(vehicleId, ctx.pathParam("id"));
        assertEquals(path, ctx.pathParam("type"));

        // Set up response
        var response = Collections.singletonMap("response", data);

        // Send response
        ctx.status(200);
        ctx.result(writeToJson(GSON, response));
      }catch(Exception e){
        e.printStackTrace();
      }
    });

    // Fire it
    var result = assertDoesNotThrow(() -> function.apply(accessToken, vehicleId));

    // Compare values
    assertEquals(data, result);
  }

  void testVehicleDataInvalidVehicleId(String path, CheckedBiConsumer<String,String> function){

    // Get old refresh token
    var accessToken = generateString(32);

    // Id
    var vehicleId = generateString(32);

    // Set up 'catch' function
    VEHICLE_DATA_HANDLER.set(ctx -> {

      // Ensure no leftovers
      try{
        var pathParams = new HashSet<>(ctx.pathParamMap().keySet());
        assertTrue(pathParams.remove("id"));
        assertTrue(pathParams.remove("type"));
        assertEquals(Collections.emptySet(), pathParams);
        assertEquals(Collections.emptySet(), ctx.formParamMap().keySet());
        assertEquals(Collections.emptySet(), ctx.queryParamMap().keySet());

        // Must be get
        assertEquals("get", ctx.method().toLowerCase());

        // Bearer must exist and path params must match
        assertEquals("Bearer " + accessToken, ctx.header("Authorization"));
        assertEquals(path, ctx.pathParam("type"));

        // Send response
        ctx.status(404);

      }catch(Exception e){
        e.printStackTrace();
      }
    });

    // Fire it
    assertThrows(VehicleIDNotFoundException.class, () -> function.accept(accessToken, vehicleId));

  }

  void testVehicleDataInvalidToken(String path, CheckedBiConsumer<String,String> function){

    // Get old refresh token
    var accessToken = generateString(32);

    // Id
    var vehicleId = generateString(32);

    // Set up 'catch' function
    VEHICLE_DATA_HANDLER.set(ctx -> {

      // Ensure no leftovers
      try{
        var pathParams = new HashSet<>(ctx.pathParamMap().keySet());
        assertTrue(pathParams.remove("id"));
        assertTrue(pathParams.remove("type"));
        assertEquals(Collections.emptySet(), pathParams);
        assertEquals(Collections.emptySet(), ctx.formParamMap().keySet());
        assertEquals(Collections.emptySet(), ctx.queryParamMap().keySet());

        // Must be get
        assertEquals("get", ctx.method().toLowerCase());

        // Bearer must exist and path params must match
        assertEquals("Bearer " + accessToken, ctx.header("Authorization"));
        assertEquals(vehicleId, ctx.pathParam("id"));
        assertEquals(path, ctx.pathParam("type"));

        // Send response
        ctx.status(401);

      }catch(Exception e){
        e.printStackTrace();
      }
    });

    // get
    assertThrows(InvalidAccessTokenException.class, () -> function.accept(accessToken, vehicleId));

  }

  void testVehicleDataInService(String path, CheckedBiConsumer<String,String> function){

    // Get old refresh token
    var accessToken = generateString(32);

    // Id
    var vehicleId = generateString(32);

    // Set up 'catch' function
    VEHICLE_DATA_HANDLER.set(ctx -> {

      // Ensure no leftovers
      try{
        var pathParams = new HashSet<>(ctx.pathParamMap().keySet());
        assertTrue(pathParams.remove("id"));
        assertTrue(pathParams.remove("type"));
        assertEquals(Collections.emptySet(), pathParams);
        assertEquals(Collections.emptySet(), ctx.formParamMap().keySet());
        assertEquals(Collections.emptySet(), ctx.queryParamMap().keySet());

        // Must be get
        assertEquals("get", ctx.method().toLowerCase());

        // Bearer must exist and path params must match
        assertEquals("Bearer " + accessToken, ctx.header("Authorization"));
        assertEquals(vehicleId, ctx.pathParam("id"));
        assertEquals(path, ctx.pathParam("type"));

        // Send response
        ctx.status(405);

      }catch(Exception e){
        e.printStackTrace();
      }
    });

    // get
    assertThrows(VehicleInServiceException.class, () -> function.accept(accessToken, vehicleId));

  }

  void testVehicleDataSleeping(String path, CheckedBiConsumer<String,String> function){

    // Get old refresh token
    var accessToken = generateString(32);

    // Id
    var vehicleId = generateString(32);

    // Set up 'catch' function
    VEHICLE_DATA_HANDLER.set(ctx -> {

      // Ensure no leftovers
      try{
        var pathParams = new HashSet<>(ctx.pathParamMap().keySet());
        assertTrue(pathParams.remove("id"));
        assertTrue(pathParams.remove("type"));
        assertEquals(Collections.emptySet(), pathParams);
        assertEquals(Collections.emptySet(), ctx.formParamMap().keySet());
        assertEquals(Collections.emptySet(), ctx.queryParamMap().keySet());

        // Must be get
        assertEquals("get", ctx.method().toLowerCase());

        // Bearer must exist and path params must match
        assertEquals("Bearer " + accessToken, ctx.header("Authorization"));
        assertEquals(vehicleId, ctx.pathParam("id"));
        assertEquals(path, ctx.pathParam("type"));

        // Send response
        ctx.status(408);

      }catch(Exception e){
        e.printStackTrace();
      }
    });

    // get
    assertThrows(VehicleSleepingException.class, () -> function.accept(accessToken, vehicleId));

  }

  void testVehicleDataUnexpected(String path, CheckedBiConsumer<String,String> function){

    // Get old refresh token
    var accessToken = generateString(32);

    // Id
    var vehicle = generateVehicle();

    // Set up 'catch' function
    VEHICLE_HANDLER.set(ctx -> {

      // Ensure no leftovers
      try{
        var pathParams = new HashSet<>(ctx.pathParamMap().keySet());
        assertTrue(pathParams.remove("id"));
        assertTrue(pathParams.remove("type"));
        assertEquals(Collections.emptySet(), pathParams);
        assertEquals(Collections.emptySet(), ctx.formParamMap().keySet());
        assertEquals(Collections.emptySet(), ctx.queryParamMap().keySet());

        // Must be get
        assertEquals("get", ctx.method().toLowerCase());

        // Bearer must exist and path params must match
        assertEquals("Bearer " + accessToken, ctx.header("Authorization"));
        assertEquals(vehicle.getIdString(), ctx.pathParam("id"));
        assertEquals(path, ctx.pathParam("type"));

        // Send response
        ctx.status(500);

      }catch(Exception e){
        e.printStackTrace();
      }
    });

    // Fire it
    assertThrows(APIProtocolException.class, () -> function.accept(accessToken, vehicle.getIdString()));
  }

  void testVehicleDataSleepingViaSocketException(String path, CheckedBiConsumer<String,String> function){

    // Get old refresh token
    var accessToken = generateString(32);

    // Generate vehicle
    var vehicle_temp = generateVehicle();

    // We have to set the state and vehicle is an immutable object... oh bother
    var vehicle = new Vehicle(
      vehicle_temp.getId(),
      vehicle_temp.getVehicleId(),
      vehicle_temp.getUserId(),
      vehicle_temp.getVIN(),
      vehicle_temp.getDisplayName(),
      vehicle_temp.getOptionCodes(),
      vehicle_temp.getColor(),
      vehicle_temp.getTokens(),
      com.ansill.tesla.api.med.model.Vehicle.State.ASLEEP.toString().toLowerCase(),
      vehicle_temp.isInService(),
      vehicle_temp.getOptionCodes(),
      vehicle_temp.isCalendarEnabled(),
      vehicle_temp.getApiVersion(),
      vehicle_temp.getBackseatToken(),
      vehicle_temp.getBackseatTokenUpdatedAt()
    );

    // Set up 'catch' function
    var cdl = new CountDownLatch(1);
    VEHICLE_HANDLER.set(ctx -> {

      try{

        // Return vehicle data
        ctx.status(200);
        ctx.result(writeToJson(GSON, new VehicleResponse(vehicle)));

      }catch(Exception e){
        e.printStackTrace();
      }
    });
    VEHICLE_DATA_HANDLER.set(ctx -> {
      try{

        // Ensure no leftovers
        var pathParams = new HashSet<>(ctx.pathParamMap().keySet());
        assertTrue(pathParams.remove("id"));
        assertTrue(pathParams.remove("type"));
        assertEquals(Collections.emptySet(), pathParams);
        assertEquals(Collections.emptySet(), ctx.formParamMap().keySet());
        assertEquals(Collections.emptySet(), ctx.queryParamMap().keySet());

        // Must be get
        assertEquals("get", ctx.method().toLowerCase());

        // Bearer must exist and path params must match
        assertEquals("Bearer " + accessToken, ctx.header("Authorization"));
        assertEquals(path, ctx.pathParam("type"));

        // Stall
        //Thread.sleep(Duration.ofSeconds(2).toMillis());
        cdl.await(30, TimeUnit.SECONDS);

      }catch(Exception e){
        e.printStackTrace();
      }
    });

    // Set timeout to too low
    client.setReadTimeoutDuration(Duration.ofMillis(150));

    // Fire it
    assertThrows(VehicleSleepingException.class, () -> function.accept(accessToken, vehicle.getIdString()));
    cdl.countDown();
  }

  void testVehicleDataOffline(String path, CheckedBiConsumer<String,String> function){

    // Get old refresh token
    var accessToken = generateString(32);

    // Generate vehicle
    var vehicle_temp = generateVehicle();

    // We have to set the state and vehicle is an immutable object... oh bother
    var vehicle = new Vehicle(
      vehicle_temp.getId(),
      vehicle_temp.getVehicleId(),
      vehicle_temp.getUserId(),
      vehicle_temp.getVIN(),
      vehicle_temp.getDisplayName(),
      vehicle_temp.getOptionCodes(),
      vehicle_temp.getColor(),
      vehicle_temp.getTokens(),
      com.ansill.tesla.api.med.model.Vehicle.State.OFFLINE.toString().toLowerCase(),
      vehicle_temp.isInService(),
      vehicle_temp.getOptionCodes(),
      vehicle_temp.isCalendarEnabled(),
      vehicle_temp.getApiVersion(),
      vehicle_temp.getBackseatToken(),
      vehicle_temp.getBackseatTokenUpdatedAt()
    );

    // Set up 'catch' function
    var cdl = new CountDownLatch(1);
    VEHICLE_HANDLER.set(ctx -> {

      try{

        // Return vehicle data
        ctx.status(200);
        ctx.result(writeToJson(GSON, new VehicleResponse(vehicle)));

      }catch(Exception e){
        e.printStackTrace();
      }
    });
    VEHICLE_DATA_HANDLER.set(ctx -> {

      try{

        // Ensure no leftovers
        var pathParams = new HashSet<>(ctx.pathParamMap().keySet());
        assertTrue(pathParams.remove("id"));
        assertTrue(pathParams.remove("type"));
        assertEquals(Collections.emptySet(), pathParams);
        assertEquals(Collections.emptySet(), ctx.formParamMap().keySet());
        assertEquals(Collections.emptySet(), ctx.queryParamMap().keySet());

        // Must be get
        assertEquals("get", ctx.method().toLowerCase());

        // Bearer must exist and path params must match
        assertEquals("Bearer " + accessToken, ctx.header("Authorization"));
        assertEquals(path, ctx.pathParam("type"));

        // Stall
        Thread.sleep(Duration.ofSeconds(2).toMillis());
        cdl.await(30, TimeUnit.SECONDS);

      }catch(Exception e){
        e.printStackTrace();
      }
    });

    // Set timeout to too low
    client.setReadTimeoutDuration(Duration.ofMillis(150));

    // Fire it
    assertThrows(VehicleOfflineException.class, () -> function.accept(accessToken, vehicle.getIdString()));
    cdl.countDown();
  }

  @RepeatedTest(50)
  void testVehicleChargeState(){
    testVehicleData("charge_state", generateChargeState(), (token, id) -> client.getVehicleChargeState(token, id));
  }

  @RepeatedTest(50)
  void testVehicleClimateState(){
    testVehicleData("climate_state", generateClimateState(), (token, id) -> client.getVehicleClimateState(token, id));
  }

  @RepeatedTest(50)
  void testVehicleDriveState(){
    testVehicleData("drive_state", generateDriveState(), (token, id) -> client.getVehicleDriveState(token, id));
  }

  @RepeatedTest(50)
  void testVehicleGuiSettings(){
    testVehicleData("gui_settings", generateGuiSettings(), (token, id) -> client.getVehicleGuiSettings(token, id));
  }

  @RepeatedTest(50)
  void testVehicleVehicleState(){
    testVehicleData("vehicle_state", generateVehicleState(), (token, id) -> client.getVehicleVehicleState(token, id));
  }

  @RepeatedTest(50)
  void testVehicleVehicleConfig(){
    testVehicleData(
      "vehicle_config",
      generateVehicleConfig(),
      (token, id) -> client.getVehicleVehicleConfig(token, id)
    );
  }

  @Test
  void testVehicleChargeStateInvalidVehicleId(){
    testVehicleDataInvalidVehicleId("charge_state", (token, id) -> client.getVehicleChargeState(token, id));
  }

  @Test
  void testVehicleChargeStateInvalidToken(){
    testVehicleDataInvalidToken("charge_state", (token, id) -> client.getVehicleChargeState(token, id));
  }

  @Test
  void testVehicleChargeStateUnexpected(){
    testVehicleDataUnexpected("charge_state", (token, id) -> client.getVehicleChargeState(token, id));
  }

  @Test
  void testVehicleChargeStateInService(){
    testVehicleDataInService("charge_state", (token, id) -> client.getVehicleChargeState(token, id));
  }

  @Test
  void testVehicleChargeStateSleeping(){
    testVehicleDataSleeping("charge_state", (token, id) -> client.getVehicleChargeState(token, id));
  }

  @Test
  void testVehicleChargeStateSleepingViaSocketException(){
    testVehicleDataSleepingViaSocketException("charge_state", (token, id) -> client.getVehicleChargeState(token, id));
  }

  @Test
  void testVehicleChargeStateOffline(){
    testVehicleDataOffline("charge_state", (token, id) -> client.getVehicleChargeState(token, id));
  }

  @Test
  void testVehicleClimateStateInvalidVehicleId(){
    testVehicleDataInvalidVehicleId("climate_state", (token, id) -> client.getVehicleClimateState(token, id));
  }

  @Test
  void testVehicleClimateStateInvalidToken(){
    testVehicleDataInvalidToken("climate_state", (token, id) -> client.getVehicleClimateState(token, id));
  }

  @Test
  void testVehicleClimateStateUnexpected(){
    testVehicleDataUnexpected("climate_state", (token, id) -> client.getVehicleClimateState(token, id));
  }

  @Test
  void testVehicleClimateStateInService(){
    testVehicleDataInService("climate_state", (token, id) -> client.getVehicleClimateState(token, id));
  }

  @Test
  void testVehicleClimateStateSleeping(){
    testVehicleDataSleeping("climate_state", (token, id) -> client.getVehicleClimateState(token, id));
  }

  @Test
  void testVehicleClimateStateSleepingViaSocketException(){
    testVehicleDataSleepingViaSocketException("climate_state", (token, id) -> client.getVehicleClimateState(token, id));
  }

  @Test
  void testVehicleClimateStateOffline(){
    testVehicleDataOffline("climate_state", (token, id) -> client.getVehicleClimateState(token, id));
  }

  @Test
  void testVehicleDriveStateInvalidVehicleId(){
    testVehicleDataInvalidVehicleId("drive_state", (token, id) -> client.getVehicleDriveState(token, id));
  }

  @Test
  void testVehicleDriveStateInvalidToken(){
    testVehicleDataInvalidToken("drive_state", (token, id) -> client.getVehicleDriveState(token, id));
  }

  @Test
  void testVehicleDriveStateUnexpected(){
    testVehicleDataUnexpected("drive_state", (token, id) -> client.getVehicleDriveState(token, id));
  }

  @Test
  void testVehicleDriveStateInService(){
    testVehicleDataInService("drive_state", (token, id) -> client.getVehicleDriveState(token, id));
  }

  @Test
  void testVehicleDriveStateSleeping(){
    testVehicleDataSleeping("drive_state", (token, id) -> client.getVehicleDriveState(token, id));
  }

  @Test
  void testVehicleDriveStateSleepingViaSocketTimeoutException(){
    testVehicleDataSleepingViaSocketException("drive_state", (token, id) -> client.getVehicleDriveState(token, id));
  }

  @Test
  void testVehicleDriveStateOffline(){
    testVehicleDataOffline("drive_state", (token, id) -> client.getVehicleDriveState(token, id));
  }

  @Test
  void testVehicleGuiSettingsInvalidVehicleId(){
    testVehicleDataInvalidVehicleId("gui_settings", (token, id) -> client.getVehicleGuiSettings(token, id));
  }

  @Test
  void testVehicleGuiSettingsInvalidToken(){
    testVehicleDataInvalidToken("gui_settings", (token, id) -> client.getVehicleGuiSettings(token, id));
  }

  @Test
  void testVehicleGuiSettingsUnexpected(){
    testVehicleDataUnexpected("gui_settings", (token, id) -> client.getVehicleGuiSettings(token, id));
  }

  @Test
  void testVehicleGuiSettingsInService(){
    testVehicleDataInService("gui_settings", (token, id) -> client.getVehicleGuiSettings(token, id));
  }

  @Test
  void testVehicleGuiSettingsSleeping(){
    testVehicleDataSleeping("gui_settings", (token, id) -> client.getVehicleGuiSettings(token, id));
  }

  @Test
  void testVehicleGuiSettingsSleepingViaSocketTimeoutException(){
    testVehicleDataSleepingViaSocketException("gui_settings", (token, id) -> client.getVehicleGuiSettings(token, id));
  }

  @Test
  void testVehicleGuiSettingsOffline(){
    testVehicleDataOffline("gui_settings", (token, id) -> client.getVehicleGuiSettings(token, id));
  }

  @Test
  void testVehicleVehicleStateInvalidVehicleId(){
    testVehicleDataInvalidVehicleId("vehicle_state", (token, id) -> client.getVehicleVehicleState(token, id));
  }

  @Test
  void testVehicleVehicleStateInvalidToken(){
    testVehicleDataInvalidToken("vehicle_state", (token, id) -> client.getVehicleVehicleState(token, id));
  }

  @Test
  void testVehicleVehicleStateUnexpected(){
    testVehicleDataUnexpected("vehicle_state", (token, id) -> client.getVehicleVehicleState(token, id));
  }

  @Test
  void testVehicleVehicleStateInService(){
    testVehicleDataInService("vehicle_state", (token, id) -> client.getVehicleVehicleState(token, id));
  }

  @Test
  void testVehicleVehicleStateSleeping(){
    testVehicleDataSleeping("vehicle_state", (token, id) -> client.getVehicleVehicleState(token, id));
  }

  @Test
  void testVehicleVehicleStateSleepingViaSocketTimeoutException(){
    testVehicleDataSleepingViaSocketException("vehicle_state", (token, id) -> client.getVehicleVehicleState(token, id));
  }

  @Test
  void testVehicleVehicleStateOffline(){
    testVehicleDataOffline("vehicle_state", (token, id) -> client.getVehicleVehicleState(token, id));
  }

  @Test
  void testVehicleVehicleConfigInvalidVehicleId(){
    testVehicleDataInvalidVehicleId("vehicle_config", (token, id) -> client.getVehicleVehicleConfig(token, id));
  }

  @Test
  void testVehicleVehicleConfigInvalidToken(){
    testVehicleDataInvalidToken("vehicle_config", (token, id) -> client.getVehicleVehicleConfig(token, id));
  }

  @Test
  void testVehicleVehicleConfigUnexpected(){
    testVehicleDataUnexpected("vehicle_config", (token, id) -> client.getVehicleVehicleConfig(token, id));
  }

  @Test
  void testVehicleVehicleConfigInService(){
    testVehicleDataInService("vehicle_config", (token, id) -> client.getVehicleVehicleConfig(token, id));
  }

  @Test
  void testVehicleVehicleConfigSleeping(){
    testVehicleDataSleeping("vehicle_config", (token, id) -> client.getVehicleVehicleConfig(token, id));
  }

  @Test
  void testVehicleVehicleConfigSleepingViaSocketTimeoutException(){
    testVehicleDataSleepingViaSocketException(
      "vehicle_config",
      (token, id) -> client.getVehicleVehicleConfig(token, id)
    );
  }

  @Test
  void testVehicleVehicleConfigOffline(){
    testVehicleDataOffline("vehicle_config", (token, id) -> client.getVehicleVehicleConfig(token, id));
  }
}