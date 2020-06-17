package com.ansill.tesla.api.mock.endpoint.tesla.v1.response;

import com.ansill.tesla.api.mock.model.MockVehicle;

public class ChargeStateResponse extends Response<MockVehicle.ChargeState>{
  public ChargeStateResponse(MockVehicle.ChargeState vehicle){
    super(vehicle);
  }
}
