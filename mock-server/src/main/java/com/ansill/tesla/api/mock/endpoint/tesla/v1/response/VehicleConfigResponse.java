package com.ansill.tesla.api.mock.endpoint.tesla.v1.response;

import com.ansill.tesla.api.data.model.VehicleConfig;
import com.ansill.tesla.api.data.model.response.SimpleResponse;

public class VehicleConfigResponse extends SimpleResponse<VehicleConfig>{
  public VehicleConfigResponse(VehicleConfig vehicle){
    super(vehicle);
  }
}
