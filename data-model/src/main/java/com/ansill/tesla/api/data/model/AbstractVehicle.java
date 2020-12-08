package com.ansill.tesla.api.data.model;

import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.ansill.tesla.api.data.utility.JacksonUtility.*;

public class AbstractVehicle{

  @Nonnegative
  private final long id;

  @Nonnegative
  private final long vehicleId;

  @Nonnull
  private final String vin;

  @Nonnull
  private final String displayName;

  @Nonnull
  private final String optionCodes;

  @Nullable
  private final String color;

  @Nonnull
  private final List<String> tokens;

  @Nonnull
  private final String state;

  private final boolean inService;

  private final boolean calendarEnabled;

  @Nonnegative
  private final int apiVersion;

  @Nullable
  private final String backseatToken;

  @Nullable
  private final String backseatTokenUpdatedAt;

  @Nonnull
  private final Map<String,Optional<Object>> _unknownFields;

  protected AbstractVehicle(
    long id,
    long vehicleId,
    @Nonnull String vin,
    @Nonnull String displayName,
    @Nonnull String optionCodes,
    @Nullable String color,
    @Nonnull List<String> tokens,
    @Nonnull String state,
    boolean inService,
    boolean calendarEnabled,
    int apiVersion,
    @Nullable String backseatToken,
    @Nullable String backseatTokenUpdatedAt,
    @Nonnull Map<String,Optional<Object>> unknownFields
  ){
    this.id = id;
    this.vehicleId = vehicleId;
    this.vin = vin;
    this.displayName = displayName;
    this.optionCodes = optionCodes;
    this.color = color;
    this.tokens = tokens;
    this.state = state;
    this.inService = inService;
    this.calendarEnabled = calendarEnabled;
    this.apiVersion = apiVersion;
    this.backseatToken = backseatToken;
    this.backseatTokenUpdatedAt = backseatTokenUpdatedAt;
    this._unknownFields = Collections.unmodifiableMap(unknownFields);
  }

  @Nonnull
  public static AbstractVehicle deserialize(
    @Nonnull ObjectNode node,
    @Nonnull ObjectCodec codec,
    @Nonnull Set<String> usedKeys
  ) throws IOException{

    // Get id
    var id = getLong(node, "id", usedKeys);

    // Get vehicleId
    var vehicleId = getLong(node, "vehicle_id", usedKeys);

    // Get VIN
    var vin = getString(node, "vin", usedKeys);

    // Get Display name
    var displayName = getString(node, "display_name", usedKeys);

    // Get Option Codes
    var optionCodes = getString(node, "option_codes", usedKeys);

    // Get color
    var color = getStringNullable(node, "color", usedKeys);

    // Get Tokens
    List<String> tokens = getObject(codec, "tokens", node, usedKeys, new TypeReference<>(){
    });

    // Get State
    var state = getString(node, "state", usedKeys);

    // Get InService
    var inService = getBoolean(node, "in_service", usedKeys);

    // Get idString
    var idStringNode = node.get("id_s");
    var idString = idStringNode != null ? getStringNullable(node, "id_s", usedKeys) : id + "";

    // Assert that id string is correct
    assert idString == null || idString.equals(id + ""); // TODO add in option to become lenient

    // Get CalendarEnabled
    var calendarEnabled = getBoolean(node, "calendar_enabled", usedKeys);

    // Get APIVersion
    var apiVersion = getInteger(node, "api_version", usedKeys);

    // Get backseat token
    var backseatToken = getStringNullable(node, "backseat_token", usedKeys);

    // Get backseat token updated at
    var backseatTokenUpdatedAt = getStringNullable(node, "backseat_token_updated_at", usedKeys);

    // Build and return
    return new AbstractVehicle(
      id,
      vehicleId,
      vin,
      displayName,
      optionCodes,
      color,
      tokens,
      state,
      inService,
      calendarEnabled,
      apiVersion,
      backseatToken,
      backseatTokenUpdatedAt,
      Collections.emptyMap()
    );
  }

  public long getId(){
    return id;
  }

  @Nonnull
  public String getIdString(){
    return id + "";
  }

  public long getVehicleId(){
    return vehicleId;
  }

  @Nonnull
  public String getVIN(){
    return vin;
  }

  @Nonnull
  public String getDisplayName(){
    return displayName;
  }

  @Nonnull
  public String getOptionCodes(){
    return optionCodes;
  }

  @Nonnull
  public Optional<String> getColor(){
    return Optional.ofNullable(color);
  }

  @Nonnull
  public List<String> getTokens(){
    return tokens;
  }

  @Nonnull
  public String getState(){
    return state;
  }

  public boolean isInService(){
    return inService;
  }

  public boolean isCalendarEnabled(){
    return calendarEnabled;
  }

  public int getApiVersion(){
    return apiVersion;
  }

  @Nonnull
  public Map<String,Optional<Object>> getUnknownFields(){
    return _unknownFields;
  }

  @Override
  public boolean equals(Object o){
    if(this == o) return true;
    if(!(o instanceof AbstractVehicle that)) return false;

    if(getId() != that.getId()) return false;
    if(getVehicleId() != that.getVehicleId()) return false;
    if(isInService() != that.isInService()) return false;
    if(isCalendarEnabled() != that.isCalendarEnabled()) return false;
    if(getApiVersion() != that.getApiVersion()) return false;
    if(!vin.equals(that.vin)) return false;
    if(!getDisplayName().equals(that.getDisplayName())) return false;
    if(!getOptionCodes().equals(that.getOptionCodes())) return false;
    if(!getColor().equals(that.getColor())) return false;
    if(!getTokens().equals(that.getTokens())) return false;
    if(!getState().equals(that.getState())) return false;
    if(!getBackseatToken().equals(that.getBackseatToken())) return false;
    if(!getBackseatTokenUpdatedAt().equals(that.getBackseatTokenUpdatedAt())) return false;
    return _unknownFields.equals(that._unknownFields);
  }

  @Override
  public int hashCode(){
    int result = (int) (getId() ^ (getId() >>> 32));
    result = 31 * result + (int) (getVehicleId() ^ (getVehicleId() >>> 32));
    result = 31 * result + vin.hashCode();
    result = 31 * result + getDisplayName().hashCode();
    result = 31 * result + getOptionCodes().hashCode();
    result = 31 * result + getColor().hashCode();
    result = 31 * result + getTokens().hashCode();
    result = 31 * result + getState().hashCode();
    result = 31 * result + (isInService() ? 1 : 0);
    result = 31 * result + (isCalendarEnabled() ? 1 : 0);
    result = 31 * result + getApiVersion();
    result = 31 * result + getBackseatToken().hashCode();
    result = 31 * result + getBackseatTokenUpdatedAt().hashCode();
    result = 31 * result + _unknownFields.hashCode();
    return result;
  }

  @Nonnull
  public Optional<String> getBackseatToken(){
    return Optional.ofNullable(backseatToken);
  }

  @Nonnull
  public Optional<String> getBackseatTokenUpdatedAt(){
    return Optional.ofNullable(backseatTokenUpdatedAt);
  }
}
