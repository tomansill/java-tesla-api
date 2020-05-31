package com.ansill.tesla.api.mock.test;

import com.ansill.tesla.api.mock.MockServer;
import com.ansill.tesla.api.mock.endpoint.tesla.v1.response.SuccessfulAuthenticationResponse;
import com.ansill.tesla.api.mock.model.MockModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static com.ansill.tesla.api.mock.MockUtility.generateEmailAddress;
import static com.ansill.utility.Utility.generateString;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MockServerTest{

  private static final AtomicReference<MockModel> MODEL = new AtomicReference<>();

  private static final ObjectMapper OM = new ObjectMapper();

  private static MockServer SERVER;

  @BeforeAll
  static void setUpServer(){
    SERVER = new MockServer(MODEL);
  }

  @AfterAll
  static void tearDownServer(){
    if(SERVER != null) SERVER.close();
    SERVER = null;
  }

  @BeforeEach
  void setUp(){
    MODEL.set(new MockModel(generateString(32), generateString(32)));
  }

  @Test
  void authenticate() throws IOException{

    // Email
    var email = generateEmailAddress();

    // Password
    var password = generateString(32);

    // Add account
    var acct = MODEL.get().createAccount(email, password);

    // Set up request
    var requestBody = new FormBody.Builder().add("grant_type", "password")
                                            .add("email", email)
                                            .add("password", password)
                                            .add("client_id", MODEL.get().getClientId())
                                            .add("client_secret", MODEL.get().getClientSecret())
                                            .build();
    var request = new Request.Builder().url("http://localhost:" + SERVER.getPort() + "/oauth/token")
                                       .addHeader("Content-Type", "application/x-www-form-urlencoded")
                                       .post(requestBody)
                                       .build();

    // Send it
    try(var response = new OkHttpClient().newCall(request).execute()){

      // Ensure 200
      assertEquals(200, response.code());

      // Parse
      var session = OM.readValue(
        Objects.requireNonNull(response.body()).string(),
        SuccessfulAuthenticationResponse.class
      );

      // Get session
      var mockSessions = MODEL.get().getSessions(acct).orElseThrow();
      assertEquals(1, mockSessions.size());
      var mockSession = mockSessions.iterator().next();
      assertEquals(mockSession.getAccessToken(), session.access_token);
      assertEquals(mockSession.getRefreshToken(), session.refresh_token);
      assertEquals(mockSession.getCreationTime().getEpochSecond(), session.created_at);
      assertEquals(mockSession.getExpiresIn().getSeconds(), session.expireIn);
    }
  }


  @Test
  void refresh() throws IOException{

    // Email
    var email = generateEmailAddress();

    // Password
    var password = generateString(32);

    // Add account
    var acct = MODEL.get().createAccount(email, password);

    // Get creds
    var cred = MODEL.get().authenticate(email, password).orElseThrow();

    // Set up request
    var requestBody = new FormBody.Builder().add("grant_type", "refresh_token")
                                            .add("refresh_token", cred.getRefreshToken())
                                            .add("client_id", MODEL.get().getClientId())
                                            .add("client_secret", MODEL.get().getClientSecret())
                                            .build();
    var request = new Request.Builder().url("http://localhost:" + SERVER.getPort() + "/oauth/token")
                                       .addHeader("Content-Type", "application/x-www-form-urlencoded")
                                       .post(requestBody)
                                       .build();

    // Send it
    try(var response = new OkHttpClient().newCall(request).execute()){

      // Ensure 200
      assertEquals(200, response.code());

      // Parse
      var session = OM.readValue(
        Objects.requireNonNull(response.body()).string(),
        SuccessfulAuthenticationResponse.class
      );

      // Get session
      var mockSessions = MODEL.get().getSessions(acct).orElseThrow();
      assertEquals(1, mockSessions.size());
      var mockSession = mockSessions.iterator().next();
      assertEquals(mockSession.getAccessToken(), session.access_token);
      assertEquals(mockSession.getRefreshToken(), session.refresh_token);
      assertEquals(mockSession.getCreationTime().getEpochSecond(), session.created_at);
      assertEquals(mockSession.getExpiresIn().getSeconds(), session.expireIn);
    }
  }
}