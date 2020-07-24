package com.ansill.tesla.api.mock.endpoint.tesla.v1;

import com.ansill.tesla.api.mock.model.MockModel;
import io.javalin.apibuilder.EndpointGroup;

import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicReference;

import static io.javalin.apibuilder.ApiBuilder.path;

public class APIEndpoint implements EndpointGroup{
  @Nonnull
  private final AtomicReference<MockModel> model;

  public APIEndpoint(@Nonnull AtomicReference<MockModel> model){
    this.model = model;
  }

  @Override
  public void addEndpoints(){
    path("/vehicles", new VehiclesEndpoint(model));
  }
}
