package com.ansill.tesla.api.mock;

import com.ansill.tesla.api.mock.endpoint.OAuthEndpoint;
import com.ansill.tesla.api.mock.endpoint.v1.APIEndpoint;
import com.ansill.tesla.api.mock.model.MockModel;
import io.javalin.Javalin;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicReference;

import static io.javalin.apibuilder.ApiBuilder.path;

public class MockServer implements AutoCloseable{

  @Nonnull
  private final AtomicReference<MockModel> model;

  @Nonnegative
  private final int port;

  @Nonnull
  private final Javalin server;

  private boolean closed = false;

  public MockServer(@Nonnull AtomicReference<MockModel> model){
    this.model = model;
    this.port = MockUtility.getNextOpenPort(1001);
    server = Javalin.create();
    this.run();
  }

  private void run(){

    // Set up routes
    server.routes(() -> {

      // OAuth endpoint
      path("oauth", new OAuthEndpoint(model));

      // API v1 endpoint
      path("api/1", new APIEndpoint(model));
    });

    // Start it
    server.start(port);
  }

  @Nonnegative
  public int getPort(){
    return port;
  }

  @Override
  public synchronized void close(){
    if(closed) return;
    server.stop();
    closed = true;
  }
}
