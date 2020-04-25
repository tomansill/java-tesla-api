package com.ansill.tesla.api.high.exception;

import javax.annotation.Nonnull;

import static com.ansill.tesla.api.utility.Utility.f;

public class VehicleNotFoundException extends RuntimeException{

  /** Serial Version ID */
  private static final long serialVersionUID = 4148725510027450445L;

  /** Message */
  private static final String MESSAGE = "Vehicle under ID '{}' cannot be found";

  /**
   * VehicleNotFoundException constructor
   *
   * @param id vehicle id
   */
  public VehicleNotFoundException(@Nonnull String id){
    super(f(MESSAGE, id));
  }
}
