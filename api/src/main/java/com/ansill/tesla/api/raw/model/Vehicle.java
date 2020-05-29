package com.ansill.tesla.api.raw.model;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.util.Collections;
import java.util.List;

import static com.ansill.utility.Utility.simpleToString;

@SuppressWarnings({"WeakerAccess", "unused"})
@Immutable
public class Vehicle{
  protected final long id;

  protected final long vehicle_id;

  protected final long user_id;

  protected final String vin;

  protected final String display_name;

  protected final String option_codes;

  protected final String color;

  protected final List<String> tokens;

  protected final String state;

  protected final boolean in_service;

  protected final String id_s;

  protected final boolean calendar_enabled;

  protected final int api_version;

  @Nullable
  protected final String backseat_token; // TODO confirm String

  @Nullable
  protected final String backseat_token_updated_at; // TODO confirm String

  public Vehicle(
    long id,
    long vehicle_id,
    long user_id,
    String vin,
    String display_name,
    String option_codes,
    String color,
    List<String> tokens,
    String state,
    boolean in_service,
    String id_s,
    boolean calendar_enabled,
    int api_version,
    @Nullable String backseat_token,
    @Nullable String backseat_token_updated_at
  ){
    this.id = id;
    this.vehicle_id = vehicle_id;
    this.user_id = user_id;
    this.vin = vin;
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

  public long getUserId(){
    return user_id;
  }

  public long getId(){
    return id;
  }

  public long getVehicleId(){
    return vehicle_id;
  }

  public String getVIN(){
    return vin;
  }

  public String getOptionCodes(){
    return option_codes;
  }

  public String getColor(){
    return color;
  }

  public List<String> getTokens(){
    return Collections.unmodifiableList(tokens);
  }

  public String getState(){
    return state;
  }

  public boolean isInService(){
    return in_service;
  }

  public String getIdString(){
    return id_s;
  }

  public boolean isCalendarEnabled(){
    return calendar_enabled;
  }

  public int getApiVersion(){
    return api_version;
  }

  public String getBackseatToken(){
    return backseat_token;
  }

  public String getBackseatTokenUpdatedAt(){
    return backseat_token_updated_at;
  }

  @Override
  public String toString(){
    return simpleToString(this);
  }

  public String getDisplayName(){
    return display_name;
  }

  @Override
  public boolean equals(Object o){
    if(this == o) return true;
    if(o == null || getClass() != o.getClass()) return false;

    Vehicle vehicle = (Vehicle) o;

    if(id != vehicle.id) return false;
    if(vehicle_id != vehicle.vehicle_id) return false;
    if(user_id != vehicle.user_id) return false;
    if(in_service != vehicle.in_service) return false;
    if(calendar_enabled != vehicle.calendar_enabled) return false;
    if(api_version != vehicle.api_version) return false;
    if(!vin.equals(vehicle.vin)) return false;
    if(!display_name.equals(vehicle.display_name)) return false;
    if(!option_codes.equals(vehicle.option_codes)) return false;
    if(!color.equals(vehicle.color)) return false;
    if(!tokens.equals(vehicle.tokens)) return false;
    if(!state.equals(vehicle.state)) return false;
    if(!id_s.equals(vehicle.id_s)) return false;
    if(backseat_token != null ? !backseat_token.equals(vehicle.backseat_token) : vehicle.backseat_token != null)
      return false;
    return backseat_token_updated_at !=
           null ? backseat_token_updated_at.equals(vehicle.backseat_token_updated_at) : vehicle.backseat_token_updated_at ==
                                                                                        null;
  }

  @Override
  public int hashCode(){
    int result = (int) (id ^ (id >>> 32));
    result = 31 * result + (int) (vehicle_id ^ (vehicle_id >>> 32));
    result = 31 * result + (int) (user_id ^ (user_id >>> 32));
    result = 31 * result + vin.hashCode();
    result = 31 * result + display_name.hashCode();
    result = 31 * result + option_codes.hashCode();
    result = 31 * result + color.hashCode();
    result = 31 * result + tokens.hashCode();
    result = 31 * result + state.hashCode();
    result = 31 * result + (in_service ? 1 : 0);
    result = 31 * result + id_s.hashCode();
    result = 31 * result + (calendar_enabled ? 1 : 0);
    result = 31 * result + api_version;
    result = 31 * result + (backseat_token != null ? backseat_token.hashCode() : 0);
    result = 31 * result + (backseat_token_updated_at != null ? backseat_token_updated_at.hashCode() : 0);
    return result;
  }
}
