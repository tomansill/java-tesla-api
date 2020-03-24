package com.ansill.tesla.test.fake.mock;

import com.google.gson.Gson;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicReference;

public class VehiclesEndpoint implements Handler{

  private final AtomicReference<MockModel> model;

  public VehiclesEndpoint(AtomicReference<MockModel> model){
    this.model = model;
  }

  @Override
  public void handle(@NotNull Context context) throws Exception{

    // Get authorization
    var authorization = context.header("Authorization");

    // Ensure that authorization exists
    if(authorization == null){
      context.status(401);
      return;
    }

    // Ensure bearer exists
    if(!authorization.startsWith("Bearer ")){
      context.status(401);
      return;
    }

    // Remove bearer
    authorization = authorization.substring("Bearer ".length());

    // Ensure that access token exists
    if(!model.get().accessTokenExists(authorization)){
      context.status(401);
      return;
    }

    // Get vehicles
    var vehicles = model.get().getVehicles(authorization);
    context.status(200);
    context.result(new Gson().toJson(vehicles));
  }
}
