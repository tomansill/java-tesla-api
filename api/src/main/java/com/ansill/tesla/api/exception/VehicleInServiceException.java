package com.ansill.tesla.api.exception;

public class VehicleInServiceException extends RuntimeException{

  /** Serial Version ID */
  private static final long serialVersionUID = -144385432021096541L;

  /** Message */
  private static final String MESSAGE = "Vehicle is in service";

  /**
   * Constructor that formats exception message
   */
  public VehicleInServiceException(){
    super(MESSAGE);
  }
}
