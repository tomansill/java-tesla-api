package com.ansill.tesla.api.mock.endpoint.tesla.v1.response;

import com.ansill.tesla.api.data.model.VehicleState;
import com.ansill.tesla.api.data.model.response.SimpleResponse;

public class VehicleStateResponse extends SimpleResponse<VehicleState>{
  public VehicleStateResponse(VehicleState vehicle){
    super(vehicle);
  }
}
