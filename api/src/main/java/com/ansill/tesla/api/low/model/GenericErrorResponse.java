package com.ansill.tesla.api.low.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.util.Optional;

import static com.ansill.tesla.api.utility.Utility.simpleToString;

/** Generic Error Response */
@Immutable
public final class GenericErrorResponse{

  /** String that describes type of error */
  @Nullable
  private final String error;

  /** String that describes the error */
  @Nullable
  private final String errorDescription;

  /** Response */
  @Nullable
  private final String response;

  public GenericErrorResponse(
    @Nullable String error,
    @Nullable String errorDescription,
    @Nullable String response
  ){
    this.error = error;
    this.errorDescription = errorDescription;
    this.response = response;
  }

  /**
   * Returns error type string if it exists
   *
   * @return optional object that may contain error type
   */
  @Nonnull
  public Optional<String> getError(){
    return Optional.ofNullable(error);
  }

  /**
   * Returns error description string if it exists
   *
   * @return optional object that may contain error description
   */
  @Nonnull
  public Optional<String> getErrorDescription(){
    return Optional.ofNullable(errorDescription);
  }

  /**
   * Returns response string if it exists
   *
   * @return optional object that may contain response
   */
  @Nonnull
  public Optional<String> getResponse(){
    return Optional.ofNullable(response);
  }

  @Override
  public String toString(){
    return simpleToString(this);
  }
}