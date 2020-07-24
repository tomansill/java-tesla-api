package com.ansill.tesla.api.mock.model;

import com.ansill.validation.Validation;

import javax.annotation.Nonnull;

public class MockAccount{

  @Nonnull
  private final String emailAddress;

  @Nonnull
  private String password;

  public MockAccount(@Nonnull String emailAddress, @Nonnull String password){
    this.emailAddress = Validation.assertValidEmailAddress(emailAddress, "emailAddress");
    this.password = Validation.assertNonemptyString(password, "password");
  }

  @Nonnull
  public String getPassword(){
    return password;
  }

  public void setPassword(@Nonnull String password){
    this.password = Validation.assertNonemptyString(password, "password");
  }

  @Nonnull
  public String getEmailAddress(){
    return emailAddress;
  }
}
