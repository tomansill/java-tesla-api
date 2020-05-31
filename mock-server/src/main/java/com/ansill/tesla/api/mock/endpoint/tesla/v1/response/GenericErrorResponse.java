package com.ansill.tesla.api.mock.endpoint.tesla.v1.response;

public class GenericErrorResponse extends Response<String>{
  public String response;

  public String error;

  public String error_description;

  public GenericErrorResponse(String response, String error, String error_description){
    super(response);
    this.error = error;
    this.error_description = error_description;
  }

  public GenericErrorResponse(String response){
    super(response);
  }

  public GenericErrorResponse(String error, String error_description){
    super();
    this.error = error;
    this.error_description = error_description;
  }
}
