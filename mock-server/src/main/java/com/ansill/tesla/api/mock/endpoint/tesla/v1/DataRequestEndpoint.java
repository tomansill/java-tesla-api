package com.ansill.tesla.api.mock.endpoint.tesla.v1;

import com.ansill.tesla.api.data.model.response.GenericErrorResponse;
import com.ansill.tesla.api.mock.endpoint.tesla.v1.response.ChargeStateResponse;
import com.ansill.tesla.api.mock.endpoint.tesla.v1.response.ClimateStateResponse;
import com.ansill.tesla.api.mock.endpoint.tesla.v1.response.DriveStateResponse;
import com.ansill.tesla.api.mock.endpoint.tesla.v1.response.GuiSettingsResponse;
import com.ansill.tesla.api.mock.endpoint.tesla.v1.response.VehicleConfigResponse;
import com.ansill.tesla.api.mock.endpoint.tesla.v1.response.VehicleStateResponse;
import com.ansill.tesla.api.mock.model.MockModel;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.http.Context;
import io.javalin.plugin.openapi.annotations.HttpMethod;
import io.javalin.plugin.openapi.annotations.OpenApi;
import io.javalin.plugin.openapi.annotations.OpenApiContent;
import io.javalin.plugin.openapi.annotations.OpenApiParam;
import io.javalin.plugin.openapi.annotations.OpenApiResponse;

import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicReference;

import static io.javalin.apibuilder.ApiBuilder.get;

public class DataRequestEndpoint implements EndpointGroup{

  @Nonnull
  private final AtomicReference<MockModel> model;

  @Nonnull
  private final VehiclesEndpoint vehiclesEndpoint;

  DataRequestEndpoint(
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

  @OpenApi(
    path = "/api/v1/vehicles/:vehicle_id/data_request/climate_state",
    method = HttpMethod.GET,
    summary = "Vehicle Climate State",
    description = "Returns vehicle climate state data associated with vehicle id",
    operationId = "vehicle",
    tags = {"Vehicle Data"},
    headers = {
      @OpenApiParam(
        name = "Authorization",
        required = true,
        description = "Bearer {access_token}"
      )
    },
    pathParams = {
      @OpenApiParam(
        name = "vehicle_id",
        required = true,
        description = "Vehicle Id"
      )
    },
    responses = {
      @OpenApiResponse(status = "200", content = {@OpenApiContent(from = ClimateStateResponse.class)}),
      @OpenApiResponse(status = "401"),
      @OpenApiResponse(status = "404", content = {@OpenApiContent(from = GenericErrorResponse.class)})
    }
  )
  private void vehicleClimateState(Context context){

    // Get account
    var account = vehiclesEndpoint.getAccountFromToken(context.header("Authorization"));

    // Check if account exists
    if(account.isEmpty()){
      context.status(401);
      context.contentType("application/json");
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
    context.json(new ClimateStateResponse(vehicle.get().getClimateState().convert()));
    context.status(200);
  }

  @OpenApi(
    path = "/api/v1/vehicles/:vehicle_id/data_request/charge_state",
    method = HttpMethod.GET,
    summary = "Vehicle Charge State",
    description = "Returns vehicle charge state data associated with vehicle id",
    operationId = "vehicle",
    tags = {"Vehicle Data"},
    headers = {
      @OpenApiParam(
        name = "Authorization",
        required = true,
        description = "Bearer {access_token}"
      )
    },
    pathParams = {
      @OpenApiParam(
        name = "vehicle_id",
        required = true,
        description = "Vehicle Id"
      )
    },
    responses = {
      @OpenApiResponse(status = "200", content = {@OpenApiContent(from = ChargeStateResponse.class)}),
      @OpenApiResponse(status = "401"),
      @OpenApiResponse(status = "404", content = {@OpenApiContent(from = GenericErrorResponse.class)})
    }
  )
  private void vehicleChargeState(Context context){

    // Get account
    var account = vehiclesEndpoint.getAccountFromToken(context.header("Authorization"));

    // Check if account exists
    if(account.isEmpty()){
      context.status(401);
      context.contentType("application/json");
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
    context.json(new ChargeStateResponse(vehicle.get().getChargeState().convert()));
    context.status(200);
  }

  @OpenApi(
    path = "/api/v1/vehicles/:vehicle_id/data_request/drive_state",
    method = HttpMethod.GET,
    summary = "Vehicle Drive State",
    description = "Returns vehicle drive state data associated with vehicle id",
    operationId = "vehicle",
    tags = {"Vehicle Data"},
    headers = {
      @OpenApiParam(
        name = "Authorization",
        required = true,
        description = "Bearer {access_token}"
      )
    },
    pathParams = {
      @OpenApiParam(
        name = "vehicle_id",
        required = true,
        description = "Vehicle Id"
      )
    },
    responses = {
      @OpenApiResponse(status = "200", content = {@OpenApiContent(from = DriveStateResponse.class)}),
      @OpenApiResponse(status = "401"),
      @OpenApiResponse(status = "404", content = {@OpenApiContent(from = GenericErrorResponse.class)})
    }
  )
  private void vehicleDriveState(Context context){

    // Get account
    var account = vehiclesEndpoint.getAccountFromToken(context.header("Authorization"));

    // Check if account exists
    if(account.isEmpty()){
      context.status(401);
      context.contentType("application/json");
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
    context.json(new DriveStateResponse(vehicle.get().getDriveState().convert()));
    context.status(200);
  }


  @OpenApi(
    path = "/api/v1/vehicles/:vehicle_id/data_request/gui_settings",
    method = HttpMethod.GET,
    summary = "Vehicle GUI Settings",
    description = "Returns vehicle GUI settings data associated with vehicle id",
    operationId = "vehicle",
    tags = {"Vehicle Data"},
    headers = {
      @OpenApiParam(
        name = "Authorization",
        required = true,
        description = "Bearer {access_token}"
      )
    },
    pathParams = {
      @OpenApiParam(
        name = "vehicle_id",
        required = true,
        description = "Vehicle Id"
      )
    },
    responses = {
      @OpenApiResponse(status = "200", content = {@OpenApiContent(from = GuiSettingsResponse.class)}),
      @OpenApiResponse(status = "401"),
      @OpenApiResponse(status = "404", content = {@OpenApiContent(from = GenericErrorResponse.class)})
    }
  )
  private void vehicleGuiSettings(Context context){

    // Get account
    var account = vehiclesEndpoint.getAccountFromToken(context.header("Authorization"));

    // Check if account exists
    if(account.isEmpty()){
      context.status(401);
      context.contentType("application/json");
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
    context.json(new GuiSettingsResponse(vehicle.get().getGuiSettings().convert()));
    context.status(200);
  }

  @OpenApi(
    path = "/api/v1/vehicles/:vehicle_id/data_request/vehicle_state",
    method = HttpMethod.GET,
    summary = "Vehicle State",
    description = "Returns vehicle state data associated with vehicle id",
    operationId = "vehicle",
    tags = {"Vehicle Data"},
    headers = {
      @OpenApiParam(
        name = "Authorization",
        required = true,
        description = "Bearer {access_token}"
      )
    },
    pathParams = {
      @OpenApiParam(
        name = "vehicle_id",
        required = true,
        description = "Vehicle Id"
      )
    },
    responses = {
      @OpenApiResponse(status = "200", content = {@OpenApiContent(from = VehicleStateResponse.class)}),
      @OpenApiResponse(status = "401"),
      @OpenApiResponse(status = "404", content = {@OpenApiContent(from = GenericErrorResponse.class)})
    }
  )
  private void vehicleVehicleState(Context context){

    // Get account
    var account = vehiclesEndpoint.getAccountFromToken(context.header("Authorization"));

    // Check if account exists
    if(account.isEmpty()){
      context.status(401);
      context.contentType("application/json");
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
    context.json(new VehicleStateResponse(vehicle.get().getVehicleState().convert()));
    context.status(200);
  }

  @OpenApi(
    path = "/api/v1/vehicles/:vehicle_id/data_request/vehicle_config",
    method = HttpMethod.GET,
    summary = "Vehicle Config",
    description = "Returns vehicle config data associated with vehicle id",
    operationId = "vehicle",
    tags = {"Vehicle Data"},
    headers = {
      @OpenApiParam(
        name = "Authorization",
        required = true,
        description = "Bearer {access_token}"
      )
    },
    pathParams = {
      @OpenApiParam(
        name = "vehicle_id",
        required = true,
        description = "Vehicle Id"
      )
    },
    responses = {
      @OpenApiResponse(status = "200", content = {@OpenApiContent(from = VehicleConfigResponse.class)}),
      @OpenApiResponse(status = "401"),
      @OpenApiResponse(status = "404", content = {@OpenApiContent(from = GenericErrorResponse.class)})
    }
  )
  private void vehicleVehicleConfig(Context context){

    // Get account
    var account = vehiclesEndpoint.getAccountFromToken(context.header("Authorization"));

    // Check if account exists
    if(account.isEmpty()){
      context.status(401);
      context.contentType("application/json");
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
    context.json(new VehicleConfigResponse(vehicle.get().getVehicleConfig().convert()));
    context.status(200);
  }
}
