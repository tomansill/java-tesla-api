package com.ansill.tesla.api.mock.endpoint.tesla.v1.response;

import com.ansill.tesla.api.mock.model.MockVehicle;

import javax.annotation.Nonnull;
import java.util.List;

public class SimpleVehicle{

  public String vin;

  public long id;

  public long vehicle_id;

  public long user_id;

  public String display_name;

  public String option_codes;

  public String color;

  public List<String> tokens;

  public String state;

  public boolean in_service;

  public String id_s;

  public boolean calendar_enabled;

  public int api_version;

  public String backseat_token;

  public String backseat_token_updated_at;

  public SimpleVehicle(
    String vin,
    long id,
    long vehicle_id,
    long user_id,
    String display_name,
    String option_codes,
    String color,
    List<String> tokens,
    String state,
    boolean in_service,
    String id_s,
    boolean calendar_enabled,
    int api_version,
    String backseat_token,
    String backseat_token_updated_at
  ){
    this.vin = vin;
    this.id = id;
    this.vehicle_id = vehicle_id;
    this.user_id = user_id;
    this.display_name = display_name;
    this.option_codes = option_codes;
    this.color = color;
    this.tokens = tokens;
    this.state = state;
    this.in_service = in_service;
    this.id_s = id_s;
    this.calendar_enabled = calendar_enabled;
    this.api_version = api_version;
    this.backseat_token = backseat_token;
    this.backseat_token_updated_at = backseat_token_updated_at;
  }

  public static SimpleVehicle convert(@Nonnull MockVehicle mockVehicle){
    return new SimpleVehicle(
      mockVehicle.getVIN(),
      mockVehicle.getId(),
      mockVehicle.getVehicleId(),
      mockVehicle.getUserId(),
      mockVehicle.getDisplayName(),
      mockVehicle.getOptionCodes(),
      mockVehicle.getColor(),
      mockVehicle.getTokens(),
      mockVehicle.getState(),
      mockVehicle.isInService(),
      mockVehicle.getOptionCodes(),
      mockVehicle.isCalendarEnabled(),
      mockVehicle.getApiVersion(),
      mockVehicle.getBackseatToken(),
      mockVehicle.getBackseatTokenUpdatedAt()
    );
  }
}
