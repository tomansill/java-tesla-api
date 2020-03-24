package com.ansill.tesla.api.low.exception;

import javax.annotation.Nonnull;

import static com.ansill.tesla.api.utility.Utility.f;

/** Authentication Exception */
public class VehicleIDNotFoundException extends Exception{

  /** Serial Version ID */
  private static final long serialVersionUID = 8332046980821041119L;

  /** Message */
  private static final String MESSAGE = "Vehicle with id \"{}\" cannot be found";

  /**
   * Constructor that takes in idString and formats exception message
   *
   * @param idString vehicle id
   */
  public VehicleIDNotFoundException(@Nonnull String idString){
    super(f(MESSAGE, idString));
  }
}
