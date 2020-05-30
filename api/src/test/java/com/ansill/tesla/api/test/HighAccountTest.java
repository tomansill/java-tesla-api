package com.ansill.tesla.api.test;

import com.ansill.tesla.api.high.Account;
import com.ansill.tesla.api.high.Client;
import com.ansill.tesla.api.raw.model.SuccessfulAuthenticationResponse;
import com.ansill.tesla.api.raw.model.Vehicle;
import com.ansill.tesla.api.raw.model.VehicleResponse;
import com.ansill.tesla.api.raw.model.VehiclesResponse;
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
import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.ansill.tesla.api.test.TestUtility.*;
import static com.ansill.utility.Utility.generateString;
import static org.junit.jupiter.api.Assertions.*;

class HighAccountTest{

  private static final Consumer<Context> DEFAULT_FAIL = context -> context.status(600);

  private static final Gson GSON = new Gson();

  private static final AtomicReference<Consumer<Context>> AUTHENTICATION_HANDLER = new AtomicReference<>();

  private static final AtomicReference<Consumer<Context>> REVOKE_HANDLER = new AtomicReference<>();

  private static final AtomicReference<Consumer<Context>> VEHICLES_HANDLER = new AtomicReference<>();

  private static final AtomicReference<Consumer<Context>> VEHICLE_HANDLER = new AtomicReference<>();

  private static final AtomicReference<Consumer<Context>> VEHICLE_DATA_HANDLER = new AtomicReference<>();

  private static Javalin SERVER;

  private static int PORT;

  private String client_id;

  private String client_secret;

  private Account account;

  private String accessToken;

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

  static void getVehicleSuccess(
    Context ctx,
    String accessToken,
    Vehicle vehicle
  ){
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

    // Set up response
    var response = new VehicleResponse(vehicle);

    // Send response
    ctx.status(200);
    ctx.result(writeToJson(GSON, response));
  }

  static void auth(
    Context ctx,
    String emailAddress,
    String password,
    String client_id,
    String client_secret,
    String accessToken,
    String refreshToken,
    long createdAt,
    int expiresIn,
    String bearer
  ){

    try{
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

    }catch(Exception e){
      e.printStackTrace();
      ctx.status(600);
    }
  }

  @BeforeEach
  void setUpEach(){
    client_id = generateString(16);
    client_secret = generateString(16);
    var client = Client.builder()
                       .setUrl("http://localhost:" + PORT)
                       .setClientId(client_id)
                       .setClientSecret(client_secret)
                       .build();
    var email = generateEmailAddress();
    var pass = generateString(32);
    accessToken = generateString(32);
    AUTHENTICATION_HANDLER.set(ctx -> auth(
      ctx,
      email,
      pass,
      client_id,
      client_secret,
      accessToken,
      generateString(32),
      Instant.now().getEpochSecond(),
      (int) Duration.ofDays(100).getSeconds(),
      "1"
    ));
    account = client.authenticate(email, pass).orElseThrow();
    REVOKE_HANDLER.set(DEFAULT_FAIL);
    VEHICLES_HANDLER.set(DEFAULT_FAIL);
    VEHICLE_HANDLER.set(DEFAULT_FAIL);
    VEHICLE_DATA_HANDLER.set(DEFAULT_FAIL);
  }

  @AfterEach
  void tearDownEach(){
    account.close();
  }

  @Test
  void getVehiclesEmptySuccess(){

    // Set up a 'catch'
    VEHICLES_HANDLER.set(ctx -> {
      try{

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

      }catch(Exception e){
        e.printStackTrace();
        ctx.status(500);
      }
      VEHICLES_HANDLER.set(DEFAULT_FAIL);
    });

    // Fire it
    assertEquals(Collections.emptySet(), assertDoesNotThrow(() -> account.getVehicles()));
  }

  @RepeatedTest(50)
  void getVehiclesSuccess(){

    // Build vehicles
    var vehicles = IntStream.range(0, (int) ((Math.random() * 5) + 3))
                            .mapToObj(i -> generateVehicle())
                            .collect(Collectors.toMap(Vehicle::getIdString, v -> v));

    // Set up a 'catch'
    VEHICLES_HANDLER.set(ctx -> {
      try{

        // Ensure no leftovers
        assertEquals(Collections.emptySet(), ctx.formParamMap().keySet());
        assertEquals(Collections.emptySet(), ctx.pathParamMap().keySet());
        assertEquals(Collections.emptySet(), ctx.queryParamMap().keySet());

        // Bearer must exist
        assertEquals("Bearer " + accessToken, ctx.header("Authorization"));

        // Set up response
        var response = new VehiclesResponse(new LinkedList<>(vehicles.values()), 0);

        // Send response
        ctx.status(200);
        ctx.result(writeToJson(GSON, response));

      }catch(Exception e){
        e.printStackTrace();
        ctx.status(500);
      }
      VEHICLES_HANDLER.set(DEFAULT_FAIL);
    });

    // Fire it
    var results = assertDoesNotThrow(() -> account.getVehicles());

    // Convert results to map for easier management
    var map = results.stream().collect(Collectors.toMap(com.ansill.tesla.api.high.Vehicle::getId, vehicle -> vehicle));

    // Check keysets
    assertEquals(vehicles.keySet(), map.keySet());
  }

  @RepeatedTest(50)
  void getVehiclesByNameSuccess(){

    // Build vehicles
    var vehicles = IntStream.range(0, (int) ((Math.random() * 5) + 3))
                            .mapToObj(i -> generateVehicle())
                            .collect(Collectors.toMap(Vehicle::getIdString, v -> v));

    // Set up a 'catch'
    VEHICLES_HANDLER.set(ctx -> {
      try{

        // Ensure no leftovers
        assertEquals(Collections.emptySet(), ctx.formParamMap().keySet());
        assertEquals(Collections.emptySet(), ctx.pathParamMap().keySet());
        assertEquals(Collections.emptySet(), ctx.queryParamMap().keySet());

        // Bearer must exist
        assertEquals("Bearer " + accessToken, ctx.header("Authorization"));

        // Set up response
        var response = new VehiclesResponse(new LinkedList<>(vehicles.values()), 0);

        // Send response
        ctx.status(200);
        ctx.result(writeToJson(GSON, response));

      }catch(Exception e){
        e.printStackTrace();
        ctx.status(500);
      }
      VEHICLES_HANDLER.set(DEFAULT_FAIL);
    });

    // Pick one vehicle
    var shuffled = new LinkedList<>(vehicles.values());
    Collections.shuffle(shuffled);
    var chosenOne = shuffled.getFirst();

    // Fire it
    var results = assertDoesNotThrow(() -> account.getVehicleByName(chosenOne.getDisplayName())).orElseThrow();

    // Ensure VIN matches
    assertEquals(chosenOne.getVIN(), results.getVIN());
  }

  @RepeatedTest(50)
  void getVehiclesByVINSuccess(){

    // Build vehicles
    var vehicles = IntStream.range(0, (int) ((Math.random() * 5) + 3))
                            .mapToObj(i -> generateVehicle())
                            .collect(Collectors.toMap(Vehicle::getIdString, v -> v));

    // Set up a 'catch'
    VEHICLES_HANDLER.set(ctx -> {
      try{

        // Ensure no leftovers
        assertEquals(Collections.emptySet(), ctx.formParamMap().keySet());
        assertEquals(Collections.emptySet(), ctx.pathParamMap().keySet());
        assertEquals(Collections.emptySet(), ctx.queryParamMap().keySet());

        // Bearer must exist
        assertEquals("Bearer " + accessToken, ctx.header("Authorization"));

        // Set up response
        var response = new VehiclesResponse(new LinkedList<>(vehicles.values()), 0);

        // Send response
        ctx.status(200);
        ctx.result(writeToJson(GSON, response));

      }catch(Exception e){
        e.printStackTrace();
        ctx.status(500);
      }
      VEHICLES_HANDLER.set(DEFAULT_FAIL);
    });

    // Pick one vehicle
    var shuffled = new LinkedList<>(vehicles.values());
    Collections.shuffle(shuffled);
    var chosenOne = shuffled.getFirst();

    // Fire it
    var results = assertDoesNotThrow(() -> account.getVehicleByVIN(chosenOne.getVIN())).orElseThrow();

    // Ensure VIN matches
    assertEquals(chosenOne.getDisplayName(), results.getName());
  }

  @RepeatedTest(50)
  void getVehiclesById(){

    // Build vehicles
    var vehicle = generateVehicle();

    // Set up a 'catch'
    VEHICLE_HANDLER.set(ctx -> {
      getVehicleSuccess(ctx, accessToken, vehicle);
      VEHICLES_HANDLER.set(DEFAULT_FAIL);
    });

    // Fire it
    var results = assertDoesNotThrow(() -> account.getVehicleByID(vehicle.getIdString())).orElseThrow();

    // Ensure VIN matches
    assertEquals(vehicle.getDisplayName(), results.getName());
    assertEquals(vehicle.getVIN(), results.getVIN());
  }
}