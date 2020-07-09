package com.ansill.tesla.api.mock.endpoint.tesla.v1.response;

import com.ansill.tesla.api.data.model.ClimateState;
import com.ansill.tesla.api.data.model.response.SimpleResponse;
import com.ansill.tesla.api.data.utility.SimpleSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = SimpleSerializer.class)
public class ClimateStateResponse extends SimpleResponse<ClimateState>{
  public ClimateStateResponse(ClimateState vehicle){
    super(vehicle);
  }
}
