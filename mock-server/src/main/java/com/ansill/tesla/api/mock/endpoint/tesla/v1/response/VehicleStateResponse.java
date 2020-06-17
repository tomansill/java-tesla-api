package com.ansill.tesla.api.mock.endpoint.tesla.v1.response;

import com.ansill.tesla.api.mock.model.MockVehicle;

public class VehicleStateResponse extends Response<MockVehicle.VehicleState>{
  public VehicleStateResponse(MockVehicle.VehicleState vehicle){
    super(vehicle);
  }
}
