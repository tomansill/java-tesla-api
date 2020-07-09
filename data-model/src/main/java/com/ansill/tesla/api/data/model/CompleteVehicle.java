package com.ansill.tesla.api.data.model;

import com.ansill.tesla.api.data.utility.JacksonUtility;
import com.ansill.tesla.api.data.utility.SimpleSerializer;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

@JsonSerialize(using = SimpleSerializer.class)
@JsonDeserialize(using = CompleteVehicle.Deserializer.class)
@Immutable
public final class CompleteVehicle extends AbstractVehicle{
  @Nonnull
  private final ClimateState climateState;

  @Nonnull
  private final DriveState driveState;

  @Nonnull
  private final ChargeState chargeState;

  @Nonnull
  private final GuiSettings guiSettings;

  @Nonnull
  private final VehicleConfig vehicleConfig;

  @Nonnull
  private final VehicleState vehicleState;

  public CompleteVehicle(
    long id,
    long userId,
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
    @Nonnull ClimateState climateState,
    @Nonnull DriveState driveState,
    @Nonnull ChargeState chargeState,
    @Nonnull GuiSettings guiSettings,
    @Nonnull VehicleConfig vehicleConfig,
    @Nonnull VehicleState vehicleState
  ){
    this(
      id,
      userId,
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
      Collections.emptyMap(),
      climateState, driveState,
      chargeState,
      guiSettings,
      vehicleConfig,
      vehicleState
    );
  }

  public CompleteVehicle(
    long id,
    long userId,
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
    @Nonnull Map<String,Optional<Object>> unknownFields,
    @Nonnull ClimateState climateState,
    @Nonnull DriveState driveState,
    @Nonnull ChargeState chargeState,
    @Nonnull GuiSettings guiSettings,
    @Nonnull VehicleConfig vehicleConfig,
    @Nonnull VehicleState vehicleState
  ){
    super(
      id,
      userId,
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
      unknownFields
    );
    this.climateState = climateState;
    this.driveState = driveState;
    this.chargeState = chargeState;
    this.guiSettings = guiSettings;
    this.vehicleConfig = vehicleConfig;
    this.vehicleState = vehicleState;
  }

  @Override
  public boolean equals(Object o){
    if(this == o) return true;
    if(!(o instanceof CompleteVehicle that)) return false;

    if(!getClimateState().equals(that.getClimateState())) return false;
    if(!getDriveState().equals(that.getDriveState())) return false;
    if(!getChargeState().equals(that.getChargeState())) return false;
    if(!getGuiSettings().equals(that.getGuiSettings())) return false;
    if(!getVehicleConfig().equals(that.getVehicleConfig())) return false;
    return getVehicleState().equals(that.getVehicleState());
  }

  @Override
  public int hashCode(){
    int result = getClimateState().hashCode();
    result = 31 * result + getDriveState().hashCode();
    result = 31 * result + getChargeState().hashCode();
    result = 31 * result + getGuiSettings().hashCode();
    result = 31 * result + getVehicleConfig().hashCode();
    result = 31 * result + getVehicleState().hashCode();
    return result;
  }

  @Nonnull
  public VehicleState getVehicleState(){
    return vehicleState;
  }

  @Nonnull
  public ClimateState getClimateState(){
    return climateState;
  }

  @Nonnull
  public DriveState getDriveState(){
    return driveState;
  }

  @Nonnull
  public ChargeState getChargeState(){
    return chargeState;
  }

  @Nonnull
  public GuiSettings getGuiSettings(){
    return guiSettings;
  }

  @Nonnull
  public VehicleConfig getVehicleConfig(){
    return vehicleConfig;
  }

  public static class Deserializer extends StdDeserializer<CompleteVehicle>{

    private static final long serialVersionUID = -275515316149812793L;

    @Nonnull
    private final AtomicReference<Function<Map<String,Optional<Object>>,Boolean>> unknownFieldsFunction;

    public Deserializer(){
      super(CompleteVehicle.class);
      unknownFieldsFunction = new AtomicReference<>();
    }

    public Deserializer(@Nonnull AtomicReference<Function<Map<String,Optional<Object>>,Boolean>> unknownFieldsFunction){
      super(CompleteVehicle.class);
      this.unknownFieldsFunction = unknownFieldsFunction;
    }

    @Override
    public CompleteVehicle deserialize(
      JsonParser jsonParser, DeserializationContext deserializationContext
    ) throws IOException{

      // Get node
      ObjectNode node = jsonParser.readValueAsTree();

      // Set up set of used keys
      var usedKeysSet = new HashSet<String>();

      // Deserialize
      var vehicle = AbstractVehicle.deserialize(node, jsonParser.getCodec(), usedKeysSet);

      // Get rest of values
      ClimateState climateState = JacksonUtility.getObject(
        jsonParser.getCodec(),
        "climate_state",
        node,
        usedKeysSet,
        ClimateState.class
      );
      DriveState driveState = JacksonUtility.getObject(
        jsonParser.getCodec(),
        "drive_state",
        node,
        usedKeysSet,
        DriveState.class
      );
      ChargeState chargeState = JacksonUtility.getObject(
        jsonParser.getCodec(),
        "charge_state",
        node,
        usedKeysSet,
        ChargeState.class
      );
      GuiSettings guiSettings = JacksonUtility.getObject(
        jsonParser.getCodec(),
        "gui_settings",
        node,
        usedKeysSet,
        GuiSettings.class
      );
      VehicleConfig vehicleConfig = JacksonUtility.getObject(
        jsonParser.getCodec(),
        "vehicle_config",
        node,
        usedKeysSet,
        VehicleConfig.class
      );
      VehicleState vehicleState = JacksonUtility.getObject(
        jsonParser.getCodec(),
        "vehicle_state",
        node,
        usedKeysSet,
        VehicleState.class
      );

      // Get unused fields map
      var unknownFields = JacksonUtility.createUnknownFieldsMap(node, jsonParser.getCodec(), usedKeysSet);

      // Report it
      var fnc = unknownFieldsFunction.get();
      if(fnc != null && fnc.apply(unknownFields)){
        throw new IllegalArgumentException("Thrown by the unknownFieldsFunction function");
      }

      // Return vehicle
      return new CompleteVehicle(
        vehicle.getId(),
        vehicle.getUserId(),
        vehicle.getVehicleId(),
        vehicle.getVIN(),
        vehicle.getDisplayName(),
        vehicle.getOptionCodes(),
        vehicle.getColor().orElse(null),
        vehicle.getTokens(),
        vehicle.getState(),
        vehicle.isInService(),
        vehicle.isCalendarEnabled(),
        vehicle.getApiVersion(),
        vehicle.getBackseatToken().orElse(null),
        vehicle.getBackseatTokenUpdatedAt().orElse(null),
        unknownFields,
        climateState,
        driveState,
        chargeState,
        guiSettings,
        vehicleConfig,
        vehicleState
      );
    }
  }
}
