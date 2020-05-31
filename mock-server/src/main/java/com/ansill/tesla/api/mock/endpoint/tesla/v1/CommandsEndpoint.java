package com.ansill.tesla.api.mock.endpoint.tesla.v1;

import com.ansill.tesla.api.mock.model.MockModel;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.http.Context;

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
    post("alerts", this::alerts);
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

  private void wake(Context context){
  }

  private void alerts(Context context){
  }

  private void remotestart(Context context){
  }

  private void homelink(Context context){
  }

  private void speedlimit(Context context){
  }

  private void valet(Context context){
  }

  private void trunk(Context context){
  }

  private void windows(Context context){
  }

  private void sunroof(Context context){
  }

  private void charging(Context context){
  }

  private void climate(Context context){
  }

  private void media(Context context){
  }

  private void sharing(Context context){
  }

  private void softwareupdate(Context context){
  }

  private void sentrymode(Context context){
  }

  private void calendar(Context context){
  }
}
