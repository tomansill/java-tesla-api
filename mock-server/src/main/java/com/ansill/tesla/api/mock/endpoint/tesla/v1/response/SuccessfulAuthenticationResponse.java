package com.ansill.tesla.api.mock.endpoint.tesla.v1.response;

import com.ansill.tesla.api.mock.model.MockSession;

public class SuccessfulAuthenticationResponse{
  public String access_token;

  public String refresh_token;

  public long created_at;

  public long expireIn;

  public static SuccessfulAuthenticationResponse convert(MockSession session){
    var response = new SuccessfulAuthenticationResponse();
    response.access_token = session.getAccessToken();
    response.refresh_token = session.getRefreshToken();
    response.created_at = session.getCreationTime().getEpochSecond();
    response.expireIn = session.getExpiresIn().getSeconds();
    return response;
  }
}
