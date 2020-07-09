package com.ansill.tesla.api.test;

import com.ansill.tesla.api.data.model.response.SuccessfulAuthenticationResponse;
import com.ansill.tesla.api.high.Client;
import com.ansill.tesla.api.high.model.AccountCredentials;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static com.ansill.tesla.api.test.TestUtility.generateEmailAddress;
import static com.ansill.tesla.api.test.TestUtility.writeToJson;
import static com.ansill.utility.Utility.generateString;
import static org.junit.jupiter.api.Assertions.*;

class HighClientTest{

  private static final Consumer<Context> DEFAULT_FAIL = context -> context.status(600);

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

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
      ctx.result(writeToJson(OBJECT_MAPPER, response));

    }catch(Exception e){
      e.printStackTrace();
      ctx.status(600);
    }
  }

  static void refresh(
    Context ctx,
    String oldRefreshToken,
    Instant oldExpireAt,
    String client_id,
    String client_secret,
    String accessToken,
    String refreshToken,
    AtomicLong createdAt,
    AtomicLong expiresIn,
    Duration wait,
    Duration offset,
    String bearer
  ){

    try{
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

      // Check token's expiry
      if(!Instant.now().isBefore(oldExpireAt)){
        ctx.status(401);
        ctx.result("That token has been expired");
        return;
      }

      // Set up times
      createdAt.set(Instant.now().getEpochSecond());
      expiresIn.set(wait.plus(offset).getSeconds());

      // Set up response
      var response = new SuccessfulAuthenticationResponse(
        accessToken,
        bearer,
        expiresIn.get(),
        refreshToken,
        createdAt.get()
      );

      // Send response
      ctx.status(200);
      ctx.result(writeToJson(OBJECT_MAPPER, response));

    }catch(Exception e){
      e.printStackTrace();
      ctx.status(600);
    }
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
    AUTHENTICATION_HANDLER.set(DEFAULT_FAIL);
    REVOKE_HANDLER.set(DEFAULT_FAIL);
    VEHICLES_HANDLER.set(DEFAULT_FAIL);
    VEHICLE_HANDLER.set(DEFAULT_FAIL);
    VEHICLE_DATA_HANDLER.set(DEFAULT_FAIL);
  }

  @AfterEach
  void tearDownEach(){
  }

  @Test
  void testAuthenticationSuccessWithRefresh() throws InterruptedException{

    // Generate email address
    var emailAddress = generateEmailAddress();

    // Generate password
    var password = generateString(8);

    // Generate response content
    var offset = Duration.ofSeconds(5);
    var accessToken = generateString(32);
    var refreshToken = generateString(32);
    var createdAt = Instant.now().getEpochSecond();
    var wait = Duration.ofSeconds(10);
    int expiresIn = (int) wait.plus(offset).getSeconds();
    var bearer = generateString(8);

    // Set up 'catch' function
    AUTHENTICATION_HANDLER.set(ctx -> {
      auth(
        ctx,
        emailAddress,
        password,
        client_id,
        client_secret,
        accessToken,
        refreshToken,
        createdAt,
        expiresIn,
        bearer
      );
      AUTHENTICATION_HANDLER.set(ctx1 -> ctx1.status(700));
    });

    // Fire it
    var creds = new AtomicReference<AccountCredentials>();
    var cdl = new AtomicReference<>(new CountDownLatch(1));
    var result = assertDoesNotThrow(() -> client.authenticate(emailAddress, password, credentials -> {
      creds.set(credentials);
      cdl.get().countDown();
    }, error -> {
      cdl.get().countDown();
      fail(error);
    }, offset));

    // Check result
    assertTrue(result.isPresent());

    // Account is Closeable, ensure it gets closed
    try(var ignored = result.get()){

      // Check CDL, it should be counted down already
      assertEquals(0, cdl.get().getCount());

      // Get cred
      var cred = creds.getAndSet(null);

      // Ensure that creds are correct
      assertEquals(AccountCredentials.convert(new SuccessfulAuthenticationResponse(
        accessToken,
        bearer,
        expiresIn,
        refreshToken,
        createdAt
      )), cred);

      // Rebuild and prepare for refresh
      var accessToken1 = generateString(32);
      var refreshToken1 = generateString(32);
      var createdAt1 = new AtomicLong();
      var wait1 = Duration.ofSeconds(15);
      var expiresIn1 = new AtomicLong();
      cdl.set(new CountDownLatch(1));
      AUTHENTICATION_HANDLER.set(ctx -> {
        refresh(
          ctx,
          refreshToken,
          Instant.ofEpochSecond(createdAt).plus(Duration.ofSeconds(expiresIn)),
          client_id,
          client_secret,
          accessToken1,
          refreshToken1,
          createdAt1,
          expiresIn1,
          wait1,
          offset,
          bearer
        );
        AUTHENTICATION_HANDLER.set(ctx1 -> ctx1.status(700));
      });

      // Wait for refresh
      assertTrue(cdl.get().await(wait.getSeconds(), TimeUnit.SECONDS));

      // Get cred
      cred = creds.getAndSet(null);

      // Ensure that creds are correct
      assertEquals(AccountCredentials.convert(new SuccessfulAuthenticationResponse(
        accessToken1,
        bearer,
        expiresIn1.get(),
        refreshToken1,
        createdAt1.get()
      )), cred);

      // Repeat whole again one more time
      var accessToken2 = generateString(32);
      var refreshToken2 = generateString(32);
      var createdAt2 = new AtomicLong();
      var wait2 = Duration.ofMinutes(15);
      var expiresIn2 = new AtomicLong();
      cdl.set(new CountDownLatch(1));
      AUTHENTICATION_HANDLER.set(ctx -> {
        refresh(
          ctx,
          refreshToken1,
          Instant.ofEpochSecond(createdAt1.get()).plus(Duration.ofSeconds(expiresIn1.get())),
          client_id,
          client_secret,
          accessToken2,
          refreshToken2,
          createdAt2,
          expiresIn2,
          wait2, offset, bearer
        );
        AUTHENTICATION_HANDLER.set(ctx1 -> ctx1.status(700));
      });

      // Wait for refresh
      assertTrue(cdl.get().await(wait1.getSeconds(), TimeUnit.SECONDS));

      // Get cred
      cred = creds.getAndSet(null);

      // Ensure that creds are correct
      assertEquals(AccountCredentials.convert(new SuccessfulAuthenticationResponse(
        accessToken2,
        bearer,
        expiresIn2.get(),
        refreshToken2,
        createdAt2.get()
      )), cred);
    }
  }

  @Test
  void testRefreshSuccessWithRefresh() throws InterruptedException{

    // Generate old token
    var oldToken = generateString(32);

    // Generate response content
    var offset = Duration.ofSeconds(5);
    var accessToken = generateString(32);
    var refreshToken = generateString(32);
    var createdAt = new AtomicLong();
    var wait = Duration.ofSeconds(10);
    var expiresIn = new AtomicLong();
    var bearer = generateString(8);

    // Set up 'catch' function
    AUTHENTICATION_HANDLER.set(ctx -> {
      refresh(
        ctx,
        oldToken,
        Instant.now().plusSeconds(10),
        client_id,
        client_secret,
        accessToken,
        refreshToken,
        createdAt,
        expiresIn,
        wait,
        offset,
        bearer
      );
      AUTHENTICATION_HANDLER.set(ctx1 -> ctx1.status(700));
    });

    // Fire it
    var creds = new AtomicReference<AccountCredentials>();
    var cdl = new AtomicReference<>(new CountDownLatch(1));
    var result = assertDoesNotThrow(() -> client.authenticate(oldToken, credentials -> {
      creds.set(credentials);
      cdl.get().countDown();
    }, error -> {
      cdl.get().countDown();
      fail(error);
    }, offset));

    // Check result
    assertTrue(result.isPresent());

    // Account is Closeable, ensure it gets closed
    try(var ignored = result.get()){

      // Check CDL, it should be counted down already
      assertEquals(0, cdl.get().getCount());

      // Get cred
      var cred = creds.getAndSet(null);

      // Ensure that creds are correct
      assertEquals(AccountCredentials.convert(new SuccessfulAuthenticationResponse(
        accessToken,
        bearer,
        expiresIn.get(),
        refreshToken,
        createdAt.get()
      )), cred);

      // Rebuild and prepare for refresh
      var accessToken1 = generateString(32);
      var refreshToken1 = generateString(32);
      var createdAt1 = new AtomicLong();
      var wait1 = Duration.ofSeconds(8);
      var expiresIn1 = new AtomicLong();
      cdl.set(new CountDownLatch(1));
      AUTHENTICATION_HANDLER.set(ctx -> {
        refresh(
          ctx,
          refreshToken,
          Instant.ofEpochSecond(createdAt.get()).plus(Duration.ofSeconds(expiresIn.get())),
          client_id,
          client_secret,
          accessToken1,
          refreshToken1,
          createdAt1,
          expiresIn1,
          wait1,
          offset,
          bearer
        );
        AUTHENTICATION_HANDLER.set(ctx1 -> ctx1.status(700));
      });

      // Wait for refresh
      assertTrue(cdl.get().await(wait.getSeconds(), TimeUnit.SECONDS));

      // Get cred
      cred = creds.getAndSet(null);

      // Ensure that creds are correct
      assertEquals(AccountCredentials.convert(new SuccessfulAuthenticationResponse(
        accessToken1,
        bearer,
        expiresIn1.get(),
        refreshToken1,
        createdAt1.get()
      )), cred);

      // Repeat whole again one more time
      var accessToken2 = generateString(32);
      var refreshToken2 = generateString(32);
      var createdAt2 = new AtomicLong();
      var wait2 = Duration.ofMinutes(15);
      var expiresIn2 = new AtomicLong();
      cdl.set(new CountDownLatch(1));
      AUTHENTICATION_HANDLER.set(ctx -> {
        refresh(
          ctx,
          refreshToken1,
          Instant.ofEpochSecond(createdAt1.get()).plus(Duration.ofSeconds(expiresIn1.get())),
          client_id,
          client_secret,
          accessToken2,
          refreshToken2,
          createdAt2,
          expiresIn2,
          wait2, offset, bearer
        );
        AUTHENTICATION_HANDLER.set(ctx1 -> ctx1.status(700));
      });

      // Wait for refresh
      assertTrue(cdl.get().await(wait1.getSeconds(), TimeUnit.SECONDS));

      // Get cred
      cred = creds.getAndSet(null);

      // Ensure that creds are correct
      assertEquals(AccountCredentials.convert(new SuccessfulAuthenticationResponse(
        accessToken2,
        bearer,
        expiresIn2.get(),
        refreshToken2,
        createdAt2.get()
      )), cred);
    }
  }
}