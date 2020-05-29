package com.ansill.tesla.api.raw.model;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import static com.ansill.utility.Utility.simpleToString;

/** Complete Vehicle Data Response */
@Immutable
public final class VehicleResponse extends SimpleResponse<Vehicle>{

  /**
   * Response
   *
   * @param response response
   */
  public VehicleResponse(@Nonnull Vehicle response){
    super(response);
  }

  @Override
  public String toString(){
    return simpleToString(this);
  }
}