package com.ansill.tesla.api.utility;

import okhttp3.Response;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Optional;

import static com.ansill.utility.Utility.simpleToString;

/** Reusable version of Response - allows us to read response body many times */
public class ReusableResponse implements AutoCloseable{

  /** Original response object */
  @Nonnull
  private final Response response;

  /** Body string */
  @Nullable
  private String bodyString = null;

  /**
   * Creates reusable response from response
   *
   * @param response response
   */
  ReusableResponse(@Nonnull Response response){
    this.response = response;
  }

  /**
   * HTTP response status code
   *
   * @return code
   */
  public int code(){
    return response.code();
  }

  /**
   * Retrieve body as string
   *
   * @return optional string containing body string
   * @throws IOException thrown if cannot read body
   */
  @Nonnull
  public synchronized Optional<String> getBodyAsString() throws IOException{
    if(bodyString != null) return Optional.of(bodyString);
    bodyString = HTTPUtility.getStringFromResponseBody(response).orElse(null);
    return Optional.ofNullable(bodyString);
  }

  @Override
  public void close(){
    bodyString = null;
    response.close();
  }

  @Override
  public String toString(){
    try{
      getBodyAsString();
    }catch(IOException e){
      e.printStackTrace();
    }
    return simpleToString(this);
  }
}
