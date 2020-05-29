package com.ansill.tesla.api.low.exception;

import javax.annotation.Nonnull;

import static com.ansill.utility.Utility.f;

/** Authentication Exception */
public class AuthenticationException extends Exception{

  /** Serial Version ID */
  private static final long serialVersionUID = 942254178971270714L;

  /** Message */
  private static final String MESSAGE = "Failed to authenticate user account \"{}\"";

  /** User account name */
  @Nonnull
  private final String emailAddress;

  /**
   * Constructor that takes in emailAddress and formats exception message
   *
   * @param emailAddress email address used to authenticate
   */
  public AuthenticationException(@Nonnull String emailAddress){
    super(f(MESSAGE, emailAddress));
    this.emailAddress = emailAddress;
  }

  /**
   * Returns account name
   *
   * @return account name
   */
  @Nonnull
  public String getAccountName(){
    return emailAddress;
  }
}
