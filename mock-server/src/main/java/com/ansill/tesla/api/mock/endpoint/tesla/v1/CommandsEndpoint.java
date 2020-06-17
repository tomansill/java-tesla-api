package com.ansill.tesla.api.mock.endpoint.tesla.v1;

import com.ansill.tesla.api.mock.endpoint.tesla.v1.response.CommandResponse;
import com.ansill.tesla.api.mock.endpoint.tesla.v1.response.GenericErrorResponse;
import com.ansill.tesla.api.mock.endpoint.tesla.v1.response.SimpleVehicleResponse;
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

import static io.javalin.apibuilder.ApiBuilder.post;

public class CommandsEndpoint implements EndpointGroup{
  @Nonnull
  private final AtomicReference<MockModel> model;

  public CommandsEndpoint(
    @Nonnull AtomicReference<MockModel> model,
    VehiclesEndpoint vehiclesEndpoint
  ){
    this.model = model;
  }

  @Override
  public void addEndpoints(){
    post("wake", this::wake);
    post("honk_horn", this::honkHorn);
    post("flash_lights", this::flashLights);
    post("door_unlock", this::unlockDoors);
    post("door_lock", this::lockDoors);
    post("remotestart", this::remotestart);
    post("homelink", this::homelink);
    post("speedlimit", this::speedlimit);
    post("valet", this::valet);
    post("trunk", this::trunk);
    post("windows", this::windows);
    post("sunroof", this::sunroof);
    post("charging", this::charging);
    post("climate", this::climate);
    post("media", this::media);
    post("sharing", this::sharing);
    post("softwareupdate", this::softwareupdate);
    post("sentrymode", this::sentrymode);
    post("calendar", this::calendar);
  }

  @OpenApi(
    path = "/api/v1/vehicles/:vehicle_id/commands/door_unlock",
    method = HttpMethod.POST,
    summary = "Unlocks the doors on vehicle",
    description = "Unlocks the doors on vehicle associated with vehicle id",
    operationId = "vehicle",
    tags = {"Vehicle Commands"},
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
      @OpenApiResponse(status = "200", content = {@OpenApiContent(from = CommandResponse.class)}),
      @OpenApiResponse(status = "401"),
      @OpenApiResponse(status = "404", content = {@OpenApiContent(from = GenericErrorResponse.class)})
    }
  )
  private void unlockDoors(Context context){
    throw new RuntimeException("Unimplemented");
  }

  @OpenApi(
    path = "/api/v1/vehicles/:vehicle_id/commands/door_lock",
    method = HttpMethod.POST,
    summary = "Locks the doors on vehicle",
    description = "Locks the doors on vehicle associated with vehicle id",
    operationId = "vehicle",
    tags = {"Vehicle Commands"},
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
      @OpenApiResponse(status = "200", content = {@OpenApiContent(from = CommandResponse.class)}),
      @OpenApiResponse(status = "401"),
      @OpenApiResponse(status = "404", content = {@OpenApiContent(from = GenericErrorResponse.class)})
    }
  )
  private void lockDoors(Context context){
    throw new RuntimeException("Unimplemented");
  }

  @OpenApi(
    path = "/api/v1/vehicles/:vehicle_id/commands/wake",
    method = HttpMethod.POST,
    summary = "Wakes vehicle up",
    description = "Wakes vehicle up associated with vehicle id",
    operationId = "vehicle",
    tags = {"Vehicle Commands"},
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
      @OpenApiResponse(status = "200", content = {@OpenApiContent(from = SimpleVehicleResponse.class)}),
      @OpenApiResponse(status = "401"),
      @OpenApiResponse(status = "404", content = {@OpenApiContent(from = GenericErrorResponse.class)})
    }
  )
  private void wake(Context context){
    throw new RuntimeException("Unimplemented");
  }

  @OpenApi(
    path = "/api/v1/vehicles/:vehicle_id/commands/honk_horn",
    method = HttpMethod.POST,
    summary = "Honks the horn on vehicle twice",
    description = "Honks the horn twice on vehicle associated with vehicle id",
    operationId = "vehicle",
    tags = {"Vehicle Commands"},
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
      @OpenApiResponse(status = "200", content = {@OpenApiContent(from = CommandResponse.class)}),
      @OpenApiResponse(status = "401"),
      @OpenApiResponse(status = "404", content = {@OpenApiContent(from = GenericErrorResponse.class)})
    }
  )
  private void honkHorn(Context context){
    throw new RuntimeException("Unimplemented");
  }

  @OpenApi(
    path = "/api/v1/vehicles/:vehicle_id/commands/flash_lights",
    method = HttpMethod.POST,
    summary = "Flashes the headlights on vehicle once",
    description = "Flashes the headlights once on vehicle associated with vehicle id",
    operationId = "vehicle",
    tags = {"Vehicle Commands"},
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
      @OpenApiResponse(status = "200", content = {@OpenApiContent(from = CommandResponse.class)}),
      @OpenApiResponse(status = "401"),
      @OpenApiResponse(status = "404", content = {@OpenApiContent(from = GenericErrorResponse.class)})
    }
  )
  private void flashLights(Context context){
    throw new RuntimeException("Unimplemented");
  }

  private void remotestart(Context context){
    throw new RuntimeException("Unimplemented");
  }

  private void homelink(Context context){
    throw new RuntimeException("Unimplemented");
  }

  private void speedlimit(Context context){
    throw new RuntimeException("Unimplemented");
  }

  private void valet(Context context){
    throw new RuntimeException("Unimplemented");
  }

  private void trunk(Context context){
    throw new RuntimeException("Unimplemented");
  }

  private void windows(Context context){
    throw new RuntimeException("Unimplemented");
  }

  private void sunroof(Context context){
    throw new RuntimeException("Unimplemented");
  }

  private void charging(Context context){
    throw new RuntimeException("Unimplemented");
  }

  private void climate(Context context){
    throw new RuntimeException("Unimplemented");
  }

  private void media(Context context){
    throw new RuntimeException("Unimplemented");
  }

  private void sharing(Context context){
    throw new RuntimeException("Unimplemented");
  }

  private void softwareupdate(Context context){
    throw new RuntimeException("Unimplemented");
  }

  private void sentrymode(Context context){
    throw new RuntimeException("Unimplemented");
  }

  private void calendar(Context context){
    throw new RuntimeException("Unimplemented");
  }
}
