package com.ansill.tesla.api.mock.endpoint.tesla.v1.response;

import java.util.List;

public class VehiclesResponse extends CountResponse<SimpleVehicle>{
  public VehiclesResponse(List<SimpleVehicle> list){
    super(list);
  }
}
