package com.ansill.tesla.api.mock.endpoint.tesla.v1;

import com.ansill.tesla.api.mock.endpoint.tesla.v1.response.GenericErrorResponse;
import com.ansill.tesla.api.mock.endpoint.tesla.v1.response.Response;
import com.ansill.tesla.api.mock.model.MockModel;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.http.Context;

import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicReference;

import static io.javalin.apibuilder.ApiBuilder.get;

public class DataRequestEndpoint implements EndpointGroup{

  @Nonnull
  private final AtomicReference<MockModel> model;

  @Nonnull
  private final VehiclesEndpoint vehiclesEndpoint;

  public DataRequestEndpoint(
    @Nonnull AtomicReference<MockModel> model,
    @Nonnull VehiclesEndpoint vehiclesEndpoint
  ){
    this.model = model;
    this.vehiclesEndpoint = vehiclesEndpoint;
  }

  @Override
  public void addEndpoints(){
    get("/climate_state", this::vehicleClimateState);
    get("/charge_state", this::vehicleChargeState);
    get("/drive_state", this::vehicleDriveState);
    get("/gui_settings", this::vehicleGuiSettings);
    get("/vehicle_state", this::vehicleVehicleState);
    get("/vehicle_config", this::vehicleVehicleConfig);
  }

  private void vehicleClimateState(Context context){

    // Get account
    var account = vehiclesEndpoint.getAccountFromToken(context.header("Authorization"));

    // Check if account exists
    if(account.isEmpty()){
      context.status(401);
      return;
    }

    // Get id
    var id = context.pathParam("vehicle_id");

    // Get vehicle
    var vehicle = model.get().getVehicle(account.get(), id);

    // Check if account exists
    if(vehicle.isEmpty()){
      context.status(404);
      context.json(new GenericErrorResponse(null, "not_found", ""));
      return;
    }

    // Get vehicles
    context.json(new Response<>(vehicle.get().getClimateState()));
    context.status(200);
  }

  private void vehicleChargeState(Context context){

    // Get account
    var account = vehiclesEndpoint.getAccountFromToken(context.header("Authorization"));

    // Check if account exists
    if(account.isEmpty()){
      context.status(401);
      return;
    }

    // Get id
    var id = context.pathParam("vehicle_id");

    // Get vehicle
    var vehicle = model.get().getVehicle(account.get(), id);

    // Check if account exists
    if(vehicle.isEmpty()){
      context.status(404);
      context.json(new GenericErrorResponse(null, "not_found", ""));
      return;
    }

    // Get vehicles
    context.json(new Response<>(vehicle.get().getChargeState()));
    context.status(200);
  }

  private void vehicleDriveState(Context context){

    // Get account
    var account = vehiclesEndpoint.getAccountFromToken(context.header("Authorization"));

    // Check if account exists
    if(account.isEmpty()){
      context.status(401);
      return;
    }

    // Get id
    var id = context.pathParam("vehicle_id");

    // Get vehicle
    var vehicle = model.get().getVehicle(account.get(), id);

    // Check if account exists
    if(vehicle.isEmpty()){
      context.status(404);
      context.json(new GenericErrorResponse(null, "not_found", ""));
      return;
    }

    // Get vehicles
    context.json(new Response<>(vehicle.get().getDriveState()));
    context.status(200);
  }

  private void vehicleGuiSettings(Context context){

    // Get account
    var account = vehiclesEndpoint.getAccountFromToken(context.header("Authorization"));

    // Check if account exists
    if(account.isEmpty()){
      context.status(401);
      return;
    }

    // Get id
    var id = context.pathParam("vehicle_id");

    // Get vehicle
    var vehicle = model.get().getVehicle(account.get(), id);

    // Check if account exists
    if(vehicle.isEmpty()){
      context.status(404);
      context.json(new GenericErrorResponse(null, "not_found", ""));
      return;
    }

    // Get vehicles
    context.json(new Response<>(vehicle.get().getGuiSettings()));
    context.status(200);
  }

  private void vehicleVehicleState(Context context){

    // Get account
    var account = vehiclesEndpoint.getAccountFromToken(context.header("Authorization"));

    // Check if account exists
    if(account.isEmpty()){
      context.status(401);
      return;
    }

    // Get id
    var id = context.pathParam("vehicle_id");

    // Get vehicle
    var vehicle = model.get().getVehicle(account.get(), id);

    // Check if account exists
    if(vehicle.isEmpty()){
      context.status(404);
      context.json(new GenericErrorResponse(null, "not_found", ""));
      return;
    }

    // Get vehicles
    context.json(new Response<>(vehicle.get().getVehicleState()));
    context.status(200);
  }

  private void vehicleVehicleConfig(Context context){

    // Get account
    var account = vehiclesEndpoint.getAccountFromToken(context.header("Authorization"));

    // Check if account exists
    if(account.isEmpty()){
      context.status(401);
      return;
    }

    // Get id
    var id = context.pathParam("vehicle_id");

    // Get vehicle
    var vehicle = model.get().getVehicle(account.get(), id);

    // Check if account exists
    if(vehicle.isEmpty()){
      context.status(404);
      context.json(new GenericErrorResponse(null, "not_found", ""));
      return;
    }

    // Get vehicles
    context.json(new Response<>(vehicle.get().getVehicleConfig()));
    context.status(200);
  }
}
