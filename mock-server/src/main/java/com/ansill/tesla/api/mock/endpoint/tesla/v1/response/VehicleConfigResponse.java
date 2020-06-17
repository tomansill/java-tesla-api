package com.ansill.tesla.api.mock.endpoint.tesla.v1.response;

import com.ansill.tesla.api.mock.model.MockVehicle;

public class VehicleConfigResponse extends Response<MockVehicle.VehicleConfig>{
  public VehicleConfigResponse(MockVehicle.VehicleConfig vehicle){
    super(vehicle);
  }
}
