package com.ansill.tesla.api.raw.exception;

/** Authentication Exception */
public class ReAuthenticationException extends Exception{

  /** Serial Version ID */
  private static final long serialVersionUID = 9045351916040952407L;

  /** Message */
  private static final String MESSAGE = "Failed to re-authenticate token";

  /**
   * Constructor that takes in emailAddress and formats exception message
   */
  public ReAuthenticationException(){
    super(MESSAGE);
  }
}
