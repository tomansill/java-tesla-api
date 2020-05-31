package com.ansill.tesla.api.mock.endpoint.tesla.v1.response;

public class Response<T>{
  public T response;

  public Response(){
  }

  public Response(T response){
    this.response = response;
  }
}
