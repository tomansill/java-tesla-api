package com.ansill.tesla.api.mock.endpoint.tesla.v1.response;

import com.ansill.tesla.api.mock.model.MockVehicle;

public class GuiSettingsResponse extends Response<MockVehicle.GUISettings>{
  public GuiSettingsResponse(MockVehicle.GUISettings vehicle){
    super(vehicle);
  }
}
