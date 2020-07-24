package com.ansill.tesla.api.mock.test;

import com.ansill.tesla.api.data.model.AbstractVehicle;
import com.ansill.tesla.api.data.model.ChargeState;
import com.ansill.tesla.api.data.model.ClimateState;
import com.ansill.tesla.api.data.model.DriveState;
import com.ansill.tesla.api.data.model.GuiSettings;
import com.ansill.tesla.api.data.model.VehicleConfig;
import com.ansill.tesla.api.data.model.VehicleState;
import com.ansill.tesla.api.data.model.response.CompleteVehicleDataResponse;
import com.ansill.tesla.api.data.model.response.SimpleResponse;
import com.ansill.tesla.api.data.model.response.SuccessfulAuthenticationResponse;
import com.ansill.tesla.api.data.model.response.VehiclesResponse;
import com.ansill.tesla.api.mock.MockServer;
import com.ansill.tesla.api.mock.model.MockModel;
import com.fasterxml.jackson.core.type.TypeReference;
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
import java.util.stream.Collectors;

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
      assertEquals(mockSession.getAccessToken(), session.getAccessToken());
      assertEquals(mockSession.getRefreshToken(), session.getRefreshToken());
      assertEquals(mockSession.getCreationTime().getEpochSecond(), session.getCreatedAt());
      assertEquals(mockSession.getExpiresIn().getSeconds(), session.getExpiresIn());
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
      assertEquals(mockSession.getAccessToken(), session.getAccessToken());
      assertEquals(mockSession.getRefreshToken(), session.getRefreshToken());
      assertEquals(mockSession.getCreationTime().getEpochSecond(), session.getCreatedAt());
      assertEquals(mockSession.getExpiresIn().getSeconds(), session.getExpiresIn());
    }
  }

  @Test
  void vehicles() throws IOException{

    // Email
    var email = generateEmailAddress();

    // Password
    var password = generateString(32);

    // Add account
    var acct = MODEL.get().createAccount(email, password);

    // Get creds
    var cred = MODEL.get().authenticate(email, password).orElseThrow();

    // Add vehicles
    var vehicleOne = MODEL.get().createVehicle(acct);
    var vehicleTwo = MODEL.get().createVehicle(acct);

    // Set up request
    var request = new Request.Builder().url("http://localhost:" + SERVER.getPort() + "/api/1/vehicles")
                                       .addHeader("Authorization", "Bearer " + cred.getAccessToken())
                                       .get()
                                       .build();

    // Send it
    try(var response = new OkHttpClient().newCall(request).execute()){

      // Ensure 200
      assertEquals(200, response.code());

      var string = Objects.requireNonNull(response.body()).string();

      System.out.println(string);

      // Parse
      var vehicles = OM.readValue(
        string,
        VehiclesResponse.class
      );

      // Compare
      assertEquals(2, vehicles.getCount());
      var map = vehicles.getResponse().stream().collect(Collectors.toMap(AbstractVehicle::getVIN, item -> item));
      assertEquals(vehicleOne.convert(), map.get(vehicleOne.getVIN()));
      assertEquals(vehicleTwo.convert(), map.get(vehicleTwo.getVIN()));
    }
  }

  @Test
  void vehicleData() throws IOException{

    // Email
    var email = generateEmailAddress();

    // Password
    var password = generateString(32);

    // Add account
    var acct = MODEL.get().createAccount(email, password);

    // Get creds
    var cred = MODEL.get().authenticate(email, password).orElseThrow();

    // Add vehicles
    MODEL.get().createVehicle(acct);
    var vehicle = MODEL.get().createVehicle(acct);
    MODEL.get().createVehicle(acct);

    // Set up request
    var request = new Request.Builder().url("http://localhost:" +
                                            SERVER.getPort() +
                                            "/api/1/vehicles/" +
                                            vehicle.getIdString() +
                                            "/data")
                                       .addHeader("Authorization", "Bearer " + cred.getAccessToken())
                                       .get()
                                       .build();

    // Send it
    try(var response = new OkHttpClient().newCall(request).execute()){

      // Ensure 200
      assertEquals(200, response.code());

      var string = Objects.requireNonNull(response.body()).string();

      System.out.println(string);

      // Parse
      var vehicleResponse = OM.readValue(
        string,
        CompleteVehicleDataResponse.class
      );

      // Compare
      assertEquals(vehicle.convertComplete(), vehicleResponse.getResponse());
    }
  }

  @Test
  void vehicleDataChargeState() throws IOException{

    // Email
    var email = generateEmailAddress();

    // Password
    var password = generateString(32);

    // Add account
    var acct = MODEL.get().createAccount(email, password);

    // Get creds
    var cred = MODEL.get().authenticate(email, password).orElseThrow();

    // Add vehicles
    MODEL.get().createVehicle(acct);
    var vehicle = MODEL.get().createVehicle(acct);
    MODEL.get().createVehicle(acct);

    // Set up request
    var request = new Request.Builder().url("http://localhost:" +
                                            SERVER.getPort() +
                                            "/api/1/vehicles/" +
                                            vehicle.getIdString() +
                                            "/data_request/charge_state")
                                       .addHeader("Authorization", "Bearer " + cred.getAccessToken())
                                       .get()
                                       .build();

    // Send it
    try(var response = new OkHttpClient().newCall(request).execute()){

      // Ensure 200
      assertEquals(200, response.code());

      var string = Objects.requireNonNull(response.body()).string();

      System.out.println(string);

      // Parse
      var simpleResponse = OM.readValue(
        string,
        new TypeReference<SimpleResponse<ChargeState>>(){
        }
      );

      // Compare
      assertEquals(vehicle.convertComplete().getChargeState(), simpleResponse.getResponse());
    }
  }

  @Test
  void vehicleDataClimateState() throws IOException{

    // Email
    var email = generateEmailAddress();

    // Password
    var password = generateString(32);

    // Add account
    var acct = MODEL.get().createAccount(email, password);

    // Get creds
    var cred = MODEL.get().authenticate(email, password).orElseThrow();

    // Add vehicles
    MODEL.get().createVehicle(acct);
    var vehicle = MODEL.get().createVehicle(acct);
    MODEL.get().createVehicle(acct);

    // Set up request
    var request = new Request.Builder().url("http://localhost:" +
                                            SERVER.getPort() +
                                            "/api/1/vehicles/" +
                                            vehicle.getIdString() +
                                            "/data_request/climate_state")
                                       .addHeader("Authorization", "Bearer " + cred.getAccessToken())
                                       .get()
                                       .build();

    // Send it
    try(var response = new OkHttpClient().newCall(request).execute()){

      // Ensure 200
      assertEquals(200, response.code());

      var string = Objects.requireNonNull(response.body()).string();

      System.out.println(string);

      // Parse
      var simpleResponse = OM.readValue(
        string,
        new TypeReference<SimpleResponse<ClimateState>>(){
        }
      );

      // Compare
      assertEquals(vehicle.convertComplete().getClimateState(), simpleResponse.getResponse());
    }
  }

  @Test
  void vehicleDataDriveState() throws IOException{

    // Email
    var email = generateEmailAddress();

    // Password
    var password = generateString(32);

    // Add account
    var acct = MODEL.get().createAccount(email, password);

    // Get creds
    var cred = MODEL.get().authenticate(email, password).orElseThrow();

    // Add vehicles
    MODEL.get().createVehicle(acct);
    var vehicle = MODEL.get().createVehicle(acct);
    MODEL.get().createVehicle(acct);

    // Set up request
    var request = new Request.Builder().url("http://localhost:" +
                                            SERVER.getPort() +
                                            "/api/1/vehicles/" +
                                            vehicle.getIdString() +
                                            "/data_request/drive_state")
                                       .addHeader("Authorization", "Bearer " + cred.getAccessToken())
                                       .get()
                                       .build();

    // Send it
    try(var response = new OkHttpClient().newCall(request).execute()){

      // Ensure 200
      assertEquals(200, response.code());

      var string = Objects.requireNonNull(response.body()).string();

      System.out.println(string);

      // Parse
      var simpleResponse = OM.readValue(
        string,
        new TypeReference<SimpleResponse<DriveState>>(){
        }
      );

      // Compare
      assertEquals(vehicle.convertComplete().getDriveState(), simpleResponse.getResponse());
    }
  }

  @Test
  void vehicleDataGuiSettings() throws IOException{

    // Email
    var email = generateEmailAddress();

    // Password
    var password = generateString(32);

    // Add account
    var acct = MODEL.get().createAccount(email, password);

    // Get creds
    var cred = MODEL.get().authenticate(email, password).orElseThrow();

    // Add vehicles
    MODEL.get().createVehicle(acct);
    var vehicle = MODEL.get().createVehicle(acct);
    MODEL.get().createVehicle(acct);

    // Set up request
    var request = new Request.Builder().url("http://localhost:" +
                                            SERVER.getPort() +
                                            "/api/1/vehicles/" +
                                            vehicle.getIdString() +
                                            "/data_request/gui_settings")
                                       .addHeader("Authorization", "Bearer " + cred.getAccessToken())
                                       .get()
                                       .build();

    // Send it
    try(var response = new OkHttpClient().newCall(request).execute()){

      // Ensure 200
      assertEquals(200, response.code());

      var string = Objects.requireNonNull(response.body()).string();

      System.out.println(string);

      // Parse
      var simpleResponse = OM.readValue(
        string,
        new TypeReference<SimpleResponse<GuiSettings>>(){
        }
      );

      // Compare
      assertEquals(vehicle.convertComplete().getGuiSettings(), simpleResponse.getResponse());
    }
  }

  @Test
  void vehicleDataVehicleConfig() throws IOException{

    // Email
    var email = generateEmailAddress();

    // Password
    var password = generateString(32);

    // Add account
    var acct = MODEL.get().createAccount(email, password);

    // Get creds
    var cred = MODEL.get().authenticate(email, password).orElseThrow();

    // Add vehicles
    MODEL.get().createVehicle(acct);
    var vehicle = MODEL.get().createVehicle(acct);
    MODEL.get().createVehicle(acct);

    // Set up request
    var request = new Request.Builder().url("http://localhost:" +
                                            SERVER.getPort() +
                                            "/api/1/vehicles/" +
                                            vehicle.getIdString() +
                                            "/data_request/vehicle_config")
                                       .addHeader("Authorization", "Bearer " + cred.getAccessToken())
                                       .get()
                                       .build();

    // Send it
    try(var response = new OkHttpClient().newCall(request).execute()){

      // Ensure 200
      assertEquals(200, response.code());

      var string = Objects.requireNonNull(response.body()).string();

      System.out.println(string);

      // Parse
      var vehicleConfigResponse = OM.readValue(
        string,
        new TypeReference<SimpleResponse<VehicleConfig>>(){
        }
      );

      // Compare
      assertEquals(vehicle.convertComplete().getVehicleConfig(), vehicleConfigResponse.getResponse());
    }
  }

  @Test
  void vehicleDataVehicleState() throws IOException{

    // Email
    var email = generateEmailAddress();

    // Password
    var password = generateString(32);

    // Add account
    var acct = MODEL.get().createAccount(email, password);

    // Get creds
    var cred = MODEL.get().authenticate(email, password).orElseThrow();

    // Add vehicles
    MODEL.get().createVehicle(acct);
    var vehicle = MODEL.get().createVehicle(acct);
    MODEL.get().createVehicle(acct);

    // Set up request
    var request = new Request.Builder().url("http://localhost:" +
                                            SERVER.getPort() +
                                            "/api/1/vehicles/" +
                                            vehicle.getIdString() +
                                            "/data_request/vehicle_state")
                                       .addHeader("Authorization", "Bearer " + cred.getAccessToken())
                                       .get()
                                       .build();

    // Send it
    try(var response = new OkHttpClient().newCall(request).execute()){

      // Ensure 200
      assertEquals(200, response.code());

      var string = Objects.requireNonNull(response.body()).string();

      System.out.println(string);

      // Parse
      var simpleResponse = OM.readValue(
        string,
        new TypeReference<SimpleResponse<VehicleState>>(){
        }
      );

      // Compare
      assertEquals(vehicle.convertComplete().getVehicleState(), simpleResponse.getResponse());
    }
  }
}