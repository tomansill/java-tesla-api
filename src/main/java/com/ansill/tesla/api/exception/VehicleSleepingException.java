package com.ansill.tesla.api.exception;

public class VehicleSleepingException extends RuntimeException{

  /** Serial Version ID */
  private static final long serialVersionUID = -144385432021096541L;

  /** Message */
  private static final String MESSAGE = "Vehicle is asleep";

  /**
   * Constructor that formats exception message
   */
  public VehicleSleepingException(){
    super(MESSAGE);
  }
}
