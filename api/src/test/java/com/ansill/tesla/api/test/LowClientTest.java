package com.ansill.tesla.api.test;

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

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
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
}