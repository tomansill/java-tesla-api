package com.ansill.tesla.api.raw.model;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import static com.ansill.utility.Utility.simpleToString;

/** Complete Vehicle Data Response */
@Immutable
public final class CompleteVehicleDataResponse extends SimpleResponse<CompleteVehicleData>{

  /**
   * Response
   *
   * @param response response
   */
  public CompleteVehicleDataResponse(@Nonnull CompleteVehicleData response){
    super(response);
  }

  @Override
  public String toString(){
    return simpleToString(this);
  }
}