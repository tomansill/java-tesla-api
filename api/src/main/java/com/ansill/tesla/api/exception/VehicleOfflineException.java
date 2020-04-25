package com.ansill.tesla.api.exception;

public class VehicleOfflineException extends RuntimeException{

  /** Message */
  private static final String MESSAGE = "Vehicle offline";

  /** Serial Version ID */
  private static final long serialVersionUID = -3730589533341781228L;

  /**
   * Constructor that formats exception message
   */
  public VehicleOfflineException(){
    super(MESSAGE);
  }
}
