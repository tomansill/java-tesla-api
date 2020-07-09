package com.ansill.tesla.api.mock.endpoint.tesla.v1.response;

import com.ansill.tesla.api.data.model.DriveState;
import com.ansill.tesla.api.data.model.response.SimpleResponse;

public class DriveStateResponse extends SimpleResponse<DriveState>{
  public DriveStateResponse(DriveState vehicle){
    super(vehicle);
  }
}
