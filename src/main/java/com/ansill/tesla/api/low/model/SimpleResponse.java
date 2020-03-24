package com.ansill.tesla.api.low.model;

import com.ansill.validation.Validation;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import static com.ansill.tesla.api.utility.Utility.simpleToString;

/** Simple Response */
@Immutable
public class SimpleResponse<T>{

  /** Response */
  @Nonnull
  private final T response;

  /**
   * Response
   *
   * @param response response
   */
  protected SimpleResponse(@Nonnull T response){
    this.response = Validation.assertNonnull(response);
  }

  /**
   * Returns response
   *
   * @return response
   */
  @Nonnull
  public T getResponse(){
    return response;
  }

  @Override
  public String toString(){
    return simpleToString(this);
  }
}

