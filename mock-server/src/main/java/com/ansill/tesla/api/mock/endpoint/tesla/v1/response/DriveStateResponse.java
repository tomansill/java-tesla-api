package com.ansill.tesla.api.mock.endpoint.tesla.v1.response;

import com.ansill.tesla.api.mock.model.MockVehicle;

public class DriveStateResponse extends Response<MockVehicle.DriveState>{
  public DriveStateResponse(MockVehicle.DriveState vehicle){
    super(vehicle);
  }
}
