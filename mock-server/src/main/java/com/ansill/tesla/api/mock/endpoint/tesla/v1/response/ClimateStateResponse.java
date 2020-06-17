package com.ansill.tesla.api.mock.endpoint.tesla.v1.response;

import com.ansill.tesla.api.mock.model.MockVehicle;

public class ClimateStateResponse extends Response<MockVehicle.ClimateState>{
  public ClimateStateResponse(MockVehicle.ClimateState vehicle){
    super(vehicle);
  }
}
