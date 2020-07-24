package com.ansill.tesla.api.mock.endpoint.tesla.v1;

import com.ansill.tesla.api.data.model.response.CompleteVehicleDataResponse;
import com.ansill.tesla.api.data.model.response.GenericErrorResponse;
import com.ansill.tesla.api.data.model.response.VehicleResponse;
import com.ansill.tesla.api.data.model.response.VehiclesResponse;
import com.ansill.tesla.api.mock.model.MockAccount;
import com.ansill.tesla.api.mock.model.MockModel;
import com.ansill.tesla.api.mock.model.MockVehicle;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.http.Context;
import io.javalin.plugin.openapi.annotations.HttpMethod;
import io.javalin.plugin.openapi.annotations.OpenApi;
import io.javalin.plugin.openapi.annotations.OpenApiContent;
import io.javalin.plugin.openapi.annotations.OpenApiParam;
import io.javalin.plugin.openapi.annotations.OpenApiResponse;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

public class VehiclesEndpoint implements EndpointGroup{

  @Nonnull
  private final AtomicReference<MockModel> model;

  VehiclesEndpoint(@Nonnull AtomicReference<MockModel> model){
    this.model = model;
  }

  @Override
  public void addEndpoints(){
    get(this::vehicles);
    path(":vehicle_id", () -> {
      get(this::vehicle);
      get("vehicle_data", this::vehicleData);
      get("data", this::vehicleDataAlias);
      path("data_request", new DataRequestEndpoint(model, this));
      path("commands", new CommandsEndpoint(model));
    });
  }

  Optional<MockAccount> getAccountFromToken(@Nullable String authorization){

    // Check if null
    if(authorization == null) return Optional.empty();

    // Check if contains Bearer
    if(!authorization.startsWith("Bearer ")) return Optional.empty();

    // Get token
    var token = authorization.substring("Bearer ".length());

    // Finally check if it exists
    return model.get().getAccountByAccessToken(token);

  }

  @OpenApi(
    path = "/api/v1/vehicles",            // only necessary to include when using static method references
    method = HttpMethod.GET,    // only necessary to include when using static method references
    summary = "List of vehicles",
    description = "Returns list of vehicles registered to the account",
    operationId = "vehicles",
    tags = {"Vehicles"},
    headers = {
      @OpenApiParam(
        name = "Authorization",
        required = true,
        description = "Bearer {access_token}"
      )
    },
    responses = {
      @OpenApiResponse(status = "200", content = {@OpenApiContent(from = VehiclesResponse.class)}),
      @OpenApiResponse(status = "401")
    }
  )
  private void vehicles(Context context){

    // Get account
    var account = getAccountFromToken(context.header("Authorization"));

    // Check if account exists
    if(account.isEmpty()){
      context.status(401);
      context.contentType("application/json");
      return;
    }

    // Get vehicles
    context.json(new VehiclesResponse(model.get()
                                           .getVehicles(account.get())
                                           .stream()
                                           .map(MockVehicle::convert)
                                           .collect(Collectors.toList())));
    context.status(200);
  }

  @OpenApi(
    path = "/api/v1/vehicles/:vehicle_id",
    method = HttpMethod.GET,
    summary = "Simplified Vehicle Data",
    description = "Returns vehicle associated with vehicle id",
    operationId = "vehicle",
    tags = {"Vehicles"},
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
      @OpenApiResponse(status = "200", content = {@OpenApiContent(from = VehicleResponse.class)}),
      @OpenApiResponse(status = "401"),
      @OpenApiResponse(status = "404", content = {@OpenApiContent(from = GenericErrorResponse.class)})
    }
  )
  private void vehicle(Context context){

    // Get account
    var account = getAccountFromToken(context.header("Authorization"));

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
    context.json(new VehicleResponse(vehicle.get().convert()));
    context.status(200);
  }


  @OpenApi(
    path = "/api/v1/vehicles/:vehicle_id/data",
    method = HttpMethod.GET,
    summary = "Legacy Vehicle Data",
    description = "Returns vehicle with complete data associated with vehicle id. NOTE: This is same as vehicle_data endpoint.",
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
      @OpenApiResponse(status = "200", content = {@OpenApiContent(from = CompleteVehicleDataResponse.class)}),
      @OpenApiResponse(status = "401"),
      @OpenApiResponse(status = "404", content = {@OpenApiContent(from = GenericErrorResponse.class)})
    }
  )
  private void vehicleDataAlias(@Nonnull Context context){
    this.vehicleData(context);
  }

  @OpenApi(
    path = "/api/v1/vehicles/:vehicle_id/vehicle_data",
    method = HttpMethod.GET,
    summary = "Complete Vehicle Data",
    description = "Returns vehicle with complete data associated with vehicle id",
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
      @OpenApiResponse(status = "200", content = {@OpenApiContent(from = CompleteVehicleDataResponse.class)}),
      @OpenApiResponse(status = "401"),
      @OpenApiResponse(status = "404", content = {@OpenApiContent(from = GenericErrorResponse.class)})
    }
  )
  private void vehicleData(Context context){

    // Get account
    var account = getAccountFromToken(context.header("Authorization"));

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
    context.json(new CompleteVehicleDataResponse(vehicle.get().convertComplete()));
    context.status(200);
  }
}
