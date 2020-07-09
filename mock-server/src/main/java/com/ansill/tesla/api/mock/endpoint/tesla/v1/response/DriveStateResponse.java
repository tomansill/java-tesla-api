package com.ansill.tesla.api.mock.endpoint.tesla.v1.response;

import com.ansill.tesla.api.data.model.DriveState;
import com.ansill.tesla.api.data.model.response.SimpleResponse;
import com.ansill.tesla.api.data.utility.SimpleSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = SimpleSerializer.class)
public class DriveStateResponse extends SimpleResponse<DriveState>{
  public DriveStateResponse(DriveState vehicle){
    super(vehicle);
  }
}
