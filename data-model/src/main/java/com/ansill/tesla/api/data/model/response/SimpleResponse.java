package com.ansill.tesla.api.data.model.response;

import com.ansill.tesla.api.data.utility.SimpleSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.annotation.concurrent.Immutable;

import static com.ansill.utility.Utility.simpleToString;

/** Simple Response */
@JsonSerialize(using = SimpleSerializer.class)
@Immutable
public class SimpleResponse<T>{

  /** Response */
  private T response;

  public SimpleResponse(){
  }

  /**
   * Response
   *
   * @param response response
   */
  public SimpleResponse(T response){
    this.response = response;
  }

  /**
   * Returns response
   *
   * @return response
   */
  public T getResponse(){
    return response;
  }

  @Override
  public String toString(){
    return simpleToString(this);
  }
}

