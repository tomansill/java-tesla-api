package com.ansill.tesla.api.low.exception;

import javax.annotation.Nonnull;

import static com.ansill.utility.Utility.f;

/** Client-related Exception */
public class ClientException extends RuntimeException{

  /** Serial Version ID */
  private static final long serialVersionUID = -7581302568335317752L;

  /** Message */
  private static final String MESSAGE = "An error has occurred inside of the client";

  /** Extra Message */
  private static final String EXTRA_MESSAGE = MESSAGE + ". Reason: {}";

  /**
   * Constructor that formats exception message
   */
  public ClientException(){
    super(MESSAGE);
  }

  /**
   * Constructor that takes in error message and formats exception message
   *
   * @param message message that describes the error
   */
  public ClientException(@Nonnull String message){
    super(f(EXTRA_MESSAGE, message));
  }


  /**
   * Constructor that formats exception message
   *
   * @param exception underlying exception that caused the exception
   */
  public ClientException(@Nonnull Exception exception){
    super(MESSAGE, exception);
  }

  /**
   * Constructor that takes in error message and formats exception message
   *
   * @param message   message that describes the error
   * @param exception underlying exception that caused the exception
   */
  public ClientException(@Nonnull String message, @Nonnull Exception exception){
    super(f(EXTRA_MESSAGE, message), exception);
  }
}
