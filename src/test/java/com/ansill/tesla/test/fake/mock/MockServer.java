package com.ansill.tesla.test.fake.mock;

import io.javalin.Javalin;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicReference;

import static com.ansill.tesla.test.TestUtility.getNextOpenPort;
import static io.javalin.apibuilder.ApiBuilder.*;

public class MockServer implements AutoCloseable{

  @Nonnull
  private final AtomicReference<MockModel> model;

  @Nonnull
  private final String clientId;

  @Nonnull
  private final String clientSecret;

  @Nonnegative
  private final int port;

  private Javalin server;

  public MockServer(
    @Nonnull AtomicReference<MockModel> model,
    @Nonnull String clientId,
    @Nonnull String clientSecret
  ){
    this.model = model;
    this.clientId = clientId;
    this.clientSecret = clientSecret;
    this.port = getNextOpenPort(1001);
    server = Javalin.create();
    this.run();
  }

  private void run(){

    server.routes(() -> {

      // OAuth Token
      path("oauth/token", () -> post(new OAuthTokenEndpoint(model, clientId, clientSecret)));

      // OAuth Revoke
      path("oauth/revoke", () -> post(new OAuthRevokeEndpoint(model)));

      // Vehicles
      path("api/1/vehicles", () -> {
        get(new VehiclesEndpoint(model));
        path(":id", () -> get(new VehicleEndpoint(model)));
      });
    });


    // Start it
    server.start(port);
  }

  @Nonnegative
  public int getPort(){
    return port;
  }

  @Override
  public void close(){
    server.stop();
  }
}
