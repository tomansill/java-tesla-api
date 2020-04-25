package com.ansill.tesla.api.mock.endpoint.v1;

import com.ansill.tesla.api.mock.old.MockModel;
import io.javalin.apibuilder.EndpointGroup;

import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicReference;

public class VehiclesEndpoint implements EndpointGroup{

  @Nonnull
  private final AtomicReference<MockModel> model;

  public VehiclesEndpoint(@Nonnull AtomicReference<MockModel> model){
    this.model = model;
  }

  @Override
  public void addEndpoints(){

  }
}
