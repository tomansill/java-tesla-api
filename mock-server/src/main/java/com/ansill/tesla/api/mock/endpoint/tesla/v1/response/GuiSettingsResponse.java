package com.ansill.tesla.api.mock.endpoint.tesla.v1.response;

import com.ansill.tesla.api.data.model.GuiSettings;
import com.ansill.tesla.api.data.model.response.SimpleResponse;

public class GuiSettingsResponse extends SimpleResponse<GuiSettings>{
  public GuiSettingsResponse(GuiSettings vehicle){
    super(vehicle);
  }
}
