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
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;


@JsonSerialize(using = SimpleSerializer.class)
@JsonDeserialize(using = VehicleConfig.Deserializer.class)
@Immutable
public final class VehicleConfig{

  private final boolean canAcceptNavigationRequests;

  private final boolean canActuateTrunks;

  @Nonnull
  private final String carSpecialType;

  @Nonnull
  private final String carType;

  @Nonnull
  private final String chargePortType;

  private final boolean eceRestrictions;

  private final boolean euVehicle;

  @Nonnull
  private final String exteriorColor;

  private final boolean hasAirSuspension;

  private final boolean hasLudicrousMode;

  private final int keyVersion;

  private final boolean motorizedChargePort;

  private final boolean plg;

  private final int rearSeatHeaters;

  @Nullable
  private final String rearSeatType;

  private final boolean rhd;

  @Nonnull
  private final String roofColor;

  @Nullable
  private final String seatType;

  @Nonnull
  private final String spoilerType;

  @Nullable
  private final String sunRoofInstalled;

  @Nonnull
  private final String thirdRowSeats;

  private final long timestamp;

  private final boolean useRangeBadging;

  @Nonnull
  private final String wheelType;

  public VehicleConfig(
    boolean canAcceptNavigationRequests,
    boolean canActuateTrunks,
    @Nonnull String carSpecialType,
    @Nonnull String carType,
    @Nonnull String chargePortType,
    boolean eceRestrictions,
    boolean euVehicle,
    @Nonnull String exteriorColor,
    boolean hasAirSuspension,
    boolean hasLudicrousMode,
    int keyVersion,
    boolean motorizedChargePort,
    boolean plg,
    int rearSeatHeaters,
    @Nullable String rearSeatType,
    boolean rhd,
    @Nonnull String roofColor,
    @Nullable String seatType,
    @Nonnull String spoilerType,
    @Nullable String sunRoofInstalled,
    @Nonnull String thirdRowSeats,
    long timestamp,
    boolean useRangeBadging,
    @Nonnull String wheelType
  ){
    this.canAcceptNavigationRequests = canAcceptNavigationRequests;
    this.canActuateTrunks = canActuateTrunks;
    this.carSpecialType = carSpecialType;
    this.carType = carType;
    this.chargePortType = chargePortType;
    this.eceRestrictions = eceRestrictions;
    this.euVehicle = euVehicle;
    this.exteriorColor = exteriorColor;
    this.hasAirSuspension = hasAirSuspension;
    this.hasLudicrousMode = hasLudicrousMode;
    this.keyVersion = keyVersion;
    this.motorizedChargePort = motorizedChargePort;
    this.plg = plg;
    this.rearSeatHeaters = rearSeatHeaters;
    this.rearSeatType = rearSeatType;
    this.rhd = rhd;
    this.roofColor = roofColor;
    this.seatType = seatType;
    this.spoilerType = spoilerType;
    this.sunRoofInstalled = sunRoofInstalled;
    this.thirdRowSeats = thirdRowSeats;
    this.timestamp = timestamp;
    this.useRangeBadging = useRangeBadging;
    this.wheelType = wheelType;
  }

  @Override
  public boolean equals(Object o){
    if(this == o) return true;
    if(!(o instanceof VehicleConfig that)) return false;

    if(isCanAcceptNavigationRequests() != that.isCanAcceptNavigationRequests()) return false;
    if(isCanActuateTrunks() != that.isCanActuateTrunks()) return false;
    if(eceRestrictions != that.eceRestrictions) return false;
    if(isEuVehicle() != that.isEuVehicle()) return false;
    if(hasAirSuspension != that.hasAirSuspension) return false;
    if(hasLudicrousMode != that.hasLudicrousMode) return false;
    if(getKeyVersion() != that.getKeyVersion()) return false;
    if(motorizedChargePort != that.motorizedChargePort) return false;
    if(isPlg() != that.isPlg()) return false;
    if(getRearSeatHeaters() != that.getRearSeatHeaters()) return false;
    if(isRhd() != that.isRhd()) return false;
    if(getTimestamp() != that.getTimestamp()) return false;
    if(useRangeBadging != that.useRangeBadging) return false;
    if(!getCarSpecialType().equals(that.getCarSpecialType())) return false;
    if(!getCarType().equals(that.getCarType())) return false;
    if(!getChargePortType().equals(that.getChargePortType())) return false;
    if(!getExteriorColor().equals(that.getExteriorColor())) return false;
    if(!getRearSeatType().equals(that.getRearSeatType())) return false;
    if(!getRoofColor().equals(that.getRoofColor())) return false;
    if(!getSeatType().equals(that.getSeatType())) return false;
    if(!getSpoilerType().equals(that.getSpoilerType())) return false;
    if(!getSunRoofInstalled().equals(that.getSunRoofInstalled())) return false;
    if(!getThirdRowSeats().equals(that.getThirdRowSeats())) return false;
    return getWheelType().equals(that.getWheelType());
  }

  @Override
  public int hashCode(){
    int result = (isCanAcceptNavigationRequests() ? 1 : 0);
    result = 31 * result + (isCanActuateTrunks() ? 1 : 0);
    result = 31 * result + getCarSpecialType().hashCode();
    result = 31 * result + getCarType().hashCode();
    result = 31 * result + getChargePortType().hashCode();
    result = 31 * result + (eceRestrictions ? 1 : 0);
    result = 31 * result + (isEuVehicle() ? 1 : 0);
    result = 31 * result + getExteriorColor().hashCode();
    result = 31 * result + (hasAirSuspension ? 1 : 0);
    result = 31 * result + (hasLudicrousMode ? 1 : 0);
    result = 31 * result + getKeyVersion();
    result = 31 * result + (motorizedChargePort ? 1 : 0);
    result = 31 * result + (isPlg() ? 1 : 0);
    result = 31 * result + getRearSeatHeaters();
    result = 31 * result + getRearSeatType().hashCode();
    result = 31 * result + (isRhd() ? 1 : 0);
    result = 31 * result + getRoofColor().hashCode();
    result = 31 * result + getSeatType().hashCode();
    result = 31 * result + getSpoilerType().hashCode();
    result = 31 * result + getSunRoofInstalled().hashCode();
    result = 31 * result + getThirdRowSeats().hashCode();
    result = 31 * result + (int) (getTimestamp() ^ (getTimestamp() >>> 32));
    result = 31 * result + (useRangeBadging ? 1 : 0);
    result = 31 * result + getWheelType().hashCode();
    return result;
  }

  public boolean isCanAcceptNavigationRequests(){
    return canAcceptNavigationRequests;
  }

  public boolean isCanActuateTrunks(){
    return canActuateTrunks;
  }

  @Nonnull
  public String getCarSpecialType(){
    return carSpecialType;
  }

  @Nonnull
  public String getCarType(){
    return carType;
  }

  @Nonnull
  public String getChargePortType(){
    return chargePortType;
  }

  public boolean hasEceRestrictions(){
    return eceRestrictions;
  }

  public boolean isEuVehicle(){
    return euVehicle;
  }

  @Nonnull
  public String getExteriorColor(){
    return exteriorColor;
  }

  public boolean hasAirSuspension(){
    return hasAirSuspension;
  }

  public boolean hasLudicrousMode(){
    return hasLudicrousMode;
  }

  public int getKeyVersion(){
    return keyVersion;
  }

  public boolean hasMotorizedChargePort(){
    return motorizedChargePort;
  }

  public boolean isPlg(){
    return plg;
  }

  public int getRearSeatHeaters(){
    return rearSeatHeaters;
  }

  @Nonnull
  public Optional<String> getRearSeatType(){
    return Optional.ofNullable(rearSeatType);
  }

  public boolean isRhd(){
    return rhd;
  }

  @Nonnull
  public String getRoofColor(){
    return roofColor;
  }

  @Nonnull
  public Optional<String> getSeatType(){
    return Optional.ofNullable(seatType);
  }

  @Nonnull
  public String getSpoilerType(){
    return spoilerType;
  }

  @Nonnull
  public Optional<String> getSunRoofInstalled(){
    return Optional.ofNullable(sunRoofInstalled);
  }

  @Nonnull
  public String getThirdRowSeats(){
    return thirdRowSeats;
  }

  public long getTimestamp(){
    return timestamp;
  }

  public boolean usesRangeBadging(){
    return useRangeBadging;
  }

  @Nonnull
  public String getWheelType(){
    return wheelType;
  }

  public static class Deserializer extends StdDeserializer<VehicleConfig>{
    private static final long serialVersionUID = 3396928908112140232L;

    @Nonnull
    private final AtomicReference<Function<Map<String,Optional<Object>>,Boolean>> unknownFieldsFunction;

    public Deserializer(){
      super(VehicleConfig.class);
      this.unknownFieldsFunction = new AtomicReference<>();
    }

    public Deserializer(@Nonnull AtomicReference<Function<Map<String,Optional<Object>>,Boolean>> unknownFieldsFunction){
      super(VehicleConfig.class);
      this.unknownFieldsFunction = unknownFieldsFunction;
    }

    @Override
    public VehicleConfig deserialize(
      JsonParser jsonParser, DeserializationContext deserializationContext
    ) throws IOException{

      // Get node
      ObjectNode node = jsonParser.readValueAsTree();

      // Set up set of used keys
      var usedKeysSet = new HashSet<String>();

      // Get values
      var canAcceptNavigationRequests = JacksonUtility.getBoolean(node, "can_accept_navigation_requests", usedKeysSet);
      var canActuateTrunks = JacksonUtility.getBoolean(node, "can_actuate_trunks", usedKeysSet);
      var carSpecialType = JacksonUtility.getString(node, "car_special_type", usedKeysSet);
      var carType = JacksonUtility.getString(node, "car_type", usedKeysSet);
      var chargePortType = JacksonUtility.getString(node, "charge_port_type", usedKeysSet);
      var eceRestrictions = JacksonUtility.getBoolean(node, "ece_restrictions", usedKeysSet);
      var euVehicle = JacksonUtility.getBoolean(node, "eu_vehicle", usedKeysSet);
      var exteriorColor = JacksonUtility.getString(node, "exterior_color", usedKeysSet);
      var hasAirSuspension = JacksonUtility.getBoolean(node, "has_air_suspension", usedKeysSet);
      var hasLudicrousMode = JacksonUtility.getBoolean(node, "has_ludicrous_mode", usedKeysSet);
      var keyVersion = JacksonUtility.getInteger(node, "key_version", usedKeysSet);
      var motorizedChargePort = JacksonUtility.getBoolean(node, "motorized_charge_port", usedKeysSet);
      var plg = JacksonUtility.getBoolean(node, "plg", usedKeysSet);
      var rearSeatHeaters = JacksonUtility.getInteger(node, "rear_seat_heaters", usedKeysSet);
      var rearSeatType = JacksonUtility.getStringNullable(node, "rear_seat_type", usedKeysSet);
      var rhd = JacksonUtility.getBoolean(node, "rhd", usedKeysSet);
      var roofColor = JacksonUtility.getString(node, "roof_color", usedKeysSet);
      var seatType = JacksonUtility.getStringNullable(node, "seat_type", usedKeysSet);
      var spoilerType = JacksonUtility.getString(node, "spoiler_type", usedKeysSet);
      var sunRoofInstalled = JacksonUtility.getStringNullable(node, "sun_roof_installed", usedKeysSet);
      var thirdRowSeats = JacksonUtility.getString(node, "third_row_seats", usedKeysSet);
      var timestamp = JacksonUtility.getLong(node, "timestamp", usedKeysSet);
      var useRangeBadging = JacksonUtility.getBoolean(node, "use_range_badging", usedKeysSet);
      var wheelType = JacksonUtility.getString(node, "wheel_type", usedKeysSet);

      // Get unused fields map
      var unknownFields = JacksonUtility.createUnknownFieldsMap(node, jsonParser.getCodec(), usedKeysSet);

      // Report it
      var fnc = unknownFieldsFunction.get();
      if(fnc != null && fnc.apply(unknownFields)){
        throw new IllegalArgumentException("Thrown by the unknownFieldsFunction function");
      }

      // Build and return
      return new VehicleConfig(
        canAcceptNavigationRequests,
        canActuateTrunks,
        carSpecialType,
        carType,
        chargePortType,
        eceRestrictions,
        euVehicle,
        exteriorColor,
        hasAirSuspension,
        hasLudicrousMode,
        keyVersion,
        motorizedChargePort,
        plg,
        rearSeatHeaters,
        rearSeatType,
        rhd,
        roofColor,
        seatType,
        spoilerType,
        sunRoofInstalled,
        thirdRowSeats,
        timestamp,
        useRangeBadging,
        wheelType
      );
    }
  }
}
