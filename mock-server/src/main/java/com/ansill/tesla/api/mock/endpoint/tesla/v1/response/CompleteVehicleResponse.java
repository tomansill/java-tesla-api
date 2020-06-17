package com.ansill.tesla.api.mock.endpoint.tesla.v1.response;

import com.ansill.tesla.api.mock.model.MockVehicle;

public class CompleteVehicleResponse extends Response<MockVehicle>{
  public CompleteVehicleResponse(MockVehicle vehicle){
    super(vehicle);
  }
}
