package com.ansill.tesla.api.mock.endpoint.tesla.v1.response;

import com.ansill.tesla.api.data.model.ChargeState;
import com.ansill.tesla.api.data.model.response.SimpleResponse;

public class ChargeStateResponse extends SimpleResponse<ChargeState>{
  public ChargeStateResponse(ChargeState vehicle){
    super(vehicle);
  }
}
