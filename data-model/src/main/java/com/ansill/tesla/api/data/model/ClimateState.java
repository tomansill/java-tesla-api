package com.ansill.tesla.api.data.model;

import com.ansill.tesla.api.data.utility.JacksonUtility;
import com.ansill.tesla.api.data.utility.SimpleSerializer;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.annotation.Nonnegative;
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
@JsonDeserialize(using = ClimateState.Deserializer.class)
@Immutable
public final class ClimateState{
  private final boolean batteryHeater;

  @Nullable
  private final Boolean batteryHeaterNoPower;

  @Nonnull
  private final String climateKeeperMode;

  @Nonnegative
  private final int defrostMode;

  private final double driverTempSetting;

  @Nonnegative
  private final int fanStatus;

  private final double insideTemp;

  private final boolean isAutoConditioningOn;

  private final boolean isClimateOn;

  private final boolean isFrontDefrosterOn;

  private final boolean isPreconditioning;

  private final boolean isRearDefrosterOn;

  @Nonnegative
  private final int leftTempDirection;

  private final double maxAvailTemp;

  private final double minAvailTemp;

  private final double outsideTemp;

  private final double passengerTempSetting;

  private final boolean remoteHeaterControlEnabled;

  @Nonnegative
  private final int rightTempDirection;

  @Nullable
  private final Integer seatHeaterRight;

  @Nullable
  private final Integer seatHeaterLeft;

  @Nullable
  private final Integer seatHeaterRearCenter;

  @Nullable
  private final Integer seatHeaterRearLeft;

  @Nullable
  private final Integer seatHeaterRearRight;

  @Nullable
  private final Integer seatHeaterBackLeft;

  @Nullable
  private final Integer seatHeaterBackRight;

  private final boolean sideMirrorHeaters;

  @Nonnegative
  private final long timestamp;

  private final boolean wiperBladeHeater;

  public ClimateState(
    boolean batteryHeater,
    @Nullable Boolean batteryHeaterNoPower,
    @Nonnull String climateKeeperMode,
    int defrostMode,
    double driverTempSetting,
    int fanStatus,
    double insideTemp,
    boolean isAutoConditioningOn,
    boolean isClimateOn,
    boolean isFrontDefrosterOn,
    boolean isPreconditioning,
    boolean isRearDefrosterOn,
    int leftTempDirection,
    double maxAvailTemp,
    double minAvailTemp,
    double outsideTemp,
    double passengerTempSetting,
    boolean remoteHeaterControlEnabled,
    int rightTempDirection,
    @Nullable Integer seatHeaterLeft,
    @Nullable Integer seatHeaterRearCenter,
    @Nullable Integer seatHeaterRearLeft,
    @Nullable Integer seatHeaterRearRight,
    @Nullable Integer seatHeaterRight,
    @Nullable Integer seatHeaterBackLeft,
    @Nullable Integer seatHeaterBackRight,
    boolean sideMirrorHeaters,
    long timestamp,
    boolean wiperBladeHeater
  ){
    this.batteryHeater = batteryHeater;
    this.batteryHeaterNoPower = batteryHeaterNoPower;
    this.climateKeeperMode = climateKeeperMode;
    this.defrostMode = defrostMode;
    this.driverTempSetting = driverTempSetting;
    this.fanStatus = fanStatus;
    this.insideTemp = insideTemp;
    this.isAutoConditioningOn = isAutoConditioningOn;
    this.isClimateOn = isClimateOn;
    this.isFrontDefrosterOn = isFrontDefrosterOn;
    this.isPreconditioning = isPreconditioning;
    this.isRearDefrosterOn = isRearDefrosterOn;
    this.leftTempDirection = leftTempDirection;
    this.maxAvailTemp = maxAvailTemp;
    this.minAvailTemp = minAvailTemp;
    this.outsideTemp = outsideTemp;
    this.passengerTempSetting = passengerTempSetting;
    this.remoteHeaterControlEnabled = remoteHeaterControlEnabled;
    this.rightTempDirection = rightTempDirection;
    this.seatHeaterLeft = seatHeaterLeft;
    this.seatHeaterRearCenter = seatHeaterRearCenter;
    this.seatHeaterRearLeft = seatHeaterRearLeft;
    this.seatHeaterRearRight = seatHeaterRearRight;
    this.seatHeaterRight = seatHeaterRight;
    this.seatHeaterBackLeft = seatHeaterBackLeft;
    this.seatHeaterBackRight = seatHeaterBackRight;
    this.sideMirrorHeaters = sideMirrorHeaters;
    this.timestamp = timestamp;
    this.wiperBladeHeater = wiperBladeHeater;
  }

  @Override
  public boolean equals(Object o){
    if(this == o) return true;
    if(!(o instanceof ClimateState that)) return false;

    if(batteryHeater != that.batteryHeater) return false;
    if(getDefrostMode() != that.getDefrostMode()) return false;
    if(Double.compare(that.getDriverTempSetting(), getDriverTempSetting()) != 0) return false;
    if(getFanStatus() != that.getFanStatus()) return false;
    if(Double.compare(that.getInsideTemp(), getInsideTemp()) != 0) return false;
    if(isAutoConditioningOn() != that.isAutoConditioningOn()) return false;
    if(isClimateOn() != that.isClimateOn()) return false;
    if(isFrontDefrosterOn() != that.isFrontDefrosterOn()) return false;
    if(isPreconditioning() != that.isPreconditioning()) return false;
    if(isRearDefrosterOn() != that.isRearDefrosterOn()) return false;
    if(getLeftTempDirection() != that.getLeftTempDirection()) return false;
    if(Double.compare(that.getMaxAvailTemp(), getMaxAvailTemp()) != 0) return false;
    if(Double.compare(that.getMinAvailTemp(), getMinAvailTemp()) != 0) return false;
    if(Double.compare(that.getOutsideTemp(), getOutsideTemp()) != 0) return false;
    if(Double.compare(that.getPassengerTempSetting(), getPassengerTempSetting()) != 0) return false;
    if(isRemoteHeaterControlEnabled() != that.isRemoteHeaterControlEnabled()) return false;
    if(getRightTempDirection() != that.getRightTempDirection()) return false;
    if(sideMirrorHeaters != that.sideMirrorHeaters) return false;
    if(getTimestamp() != that.getTimestamp()) return false;
    if(wiperBladeHeater != that.wiperBladeHeater) return false;
    if(!batteryHeaterNoPower.equals(that.batteryHeaterNoPower)) return false;
    if(!getClimateKeeperMode().equals(that.getClimateKeeperMode())) return false;
    if(!getSeatHeaterRight().equals(that.getSeatHeaterRight())) return false;
    if(!getSeatHeaterLeft().equals(that.getSeatHeaterLeft())) return false;
    if(!getSeatHeaterRearCenter().equals(that.getSeatHeaterRearCenter())) return false;
    if(!getSeatHeaterRearLeft().equals(that.getSeatHeaterRearLeft())) return false;
    if(!getSeatHeaterRearRight().equals(that.getSeatHeaterRearRight())) return false;
    if(!getSeatHeaterBackLeft().equals(that.getSeatHeaterBackLeft())) return false;
    return getSeatHeaterBackRight().equals(that.getSeatHeaterBackRight());
  }

  @Override
  public int hashCode(){
    int result;
    long temp;
    result = (batteryHeater ? 1 : 0);
    result = 31 * result + batteryHeaterNoPower.hashCode();
    result = 31 * result + getClimateKeeperMode().hashCode();
    result = 31 * result + getDefrostMode();
    temp = Double.doubleToLongBits(getDriverTempSetting());
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + getFanStatus();
    temp = Double.doubleToLongBits(getInsideTemp());
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + (isAutoConditioningOn() ? 1 : 0);
    result = 31 * result + (isClimateOn() ? 1 : 0);
    result = 31 * result + (isFrontDefrosterOn() ? 1 : 0);
    result = 31 * result + (isPreconditioning() ? 1 : 0);
    result = 31 * result + (isRearDefrosterOn() ? 1 : 0);
    result = 31 * result + getLeftTempDirection();
    temp = Double.doubleToLongBits(getMaxAvailTemp());
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(getMinAvailTemp());
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(getOutsideTemp());
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(getPassengerTempSetting());
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + (isRemoteHeaterControlEnabled() ? 1 : 0);
    result = 31 * result + getRightTempDirection();
    result = 31 * result + getSeatHeaterRight().hashCode();
    result = 31 * result + getSeatHeaterLeft().hashCode();
    result = 31 * result + getSeatHeaterRearCenter().hashCode();
    result = 31 * result + getSeatHeaterRearLeft().hashCode();
    result = 31 * result + getSeatHeaterRearRight().hashCode();
    result = 31 * result + getSeatHeaterBackLeft().hashCode();
    result = 31 * result + getSeatHeaterBackRight().hashCode();
    result = 31 * result + (sideMirrorHeaters ? 1 : 0);
    result = 31 * result + (int) (getTimestamp() ^ (getTimestamp() >>> 32));
    result = 31 * result + (wiperBladeHeater ? 1 : 0);
    return result;
  }

  public boolean hasBatteryHeater(){
    return batteryHeater;
  }

  @Nonnull
  public Optional<Boolean> hasPowerInBatteryHeater(){
    return Optional.ofNullable(batteryHeaterNoPower);
  }

  @Nonnull
  public String getClimateKeeperMode(){
    return climateKeeperMode;
  }

  public int getDefrostMode(){
    return defrostMode;
  }

  public double getDriverTempSetting(){
    return driverTempSetting;
  }

  public int getFanStatus(){
    return fanStatus;
  }

  public double getInsideTemp(){
    return insideTemp;
  }

  public boolean isAutoConditioningOn(){
    return isAutoConditioningOn;
  }

  public boolean isClimateOn(){
    return isClimateOn;
  }

  public boolean isFrontDefrosterOn(){
    return isFrontDefrosterOn;
  }

  public boolean isPreconditioning(){
    return isPreconditioning;
  }

  public boolean isRearDefrosterOn(){
    return isRearDefrosterOn;
  }

  public int getLeftTempDirection(){
    return leftTempDirection;
  }

  public double getMaxAvailTemp(){
    return maxAvailTemp;
  }

  public double getMinAvailTemp(){
    return minAvailTemp;
  }

  public double getOutsideTemp(){
    return outsideTemp;
  }

  public double getPassengerTempSetting(){
    return passengerTempSetting;
  }

  public boolean isRemoteHeaterControlEnabled(){
    return remoteHeaterControlEnabled;
  }

  public int getRightTempDirection(){
    return rightTempDirection;
  }

  @Nonnull
  public Optional<Integer> getSeatHeaterLeft(){
    return Optional.ofNullable(seatHeaterLeft);
  }

  @Nonnull
  public Optional<Integer> getSeatHeaterRearCenter(){
    return Optional.ofNullable(seatHeaterRearCenter);
  }

  @Nonnull
  public Optional<Integer> getSeatHeaterRearLeft(){
    return Optional.ofNullable(seatHeaterRearLeft);
  }

  @Nonnull
  public Optional<Integer> getSeatHeaterRearRight(){
    return Optional.ofNullable(seatHeaterRearRight);
  }

  @Nonnull
  public Optional<Integer> getSeatHeaterRight(){
    return Optional.ofNullable(seatHeaterRight);
  }

  @Nonnull
  public Optional<Integer> getSeatHeaterBackLeft(){
    return Optional.ofNullable(seatHeaterBackLeft);
  }

  @Nonnull
  public Optional<Integer> getSeatHeaterBackRight(){
    return Optional.ofNullable(seatHeaterBackRight);
  }

  public boolean hasSideMirrorHeaters(){
    return sideMirrorHeaters;
  }

  public long getTimestamp(){
    return timestamp;
  }

  public boolean hasWiperBladeHeater(){
    return wiperBladeHeater;
  }

  public static class Deserializer extends StdDeserializer<ClimateState>{

    private static final long serialVersionUID = 2589088406513324539L;

    @Nonnull
    private final AtomicReference<Function<Map<String,Optional<Object>>,Boolean>> unknownFieldsFunction;

    public Deserializer(){
      super(ClimateState.class);
      unknownFieldsFunction = new AtomicReference<>();
    }

    public Deserializer(@Nonnull AtomicReference<Function<Map<String,Optional<Object>>,Boolean>> unknownFieldsFunction){
      super(ClimateState.class);
      this.unknownFieldsFunction = unknownFieldsFunction;
    }

    @Override
    public ClimateState deserialize(
      JsonParser jsonParser, DeserializationContext deserializationContext
    ) throws IOException{

      // Get node
      ObjectNode node = jsonParser.readValueAsTree();

      // Set up set of used keys
      var usedKeysSet = new HashSet<String>();

      // Get values
      var batteryHeater = JacksonUtility.getBoolean(node, "battery_heater", usedKeysSet);
      var batteryHeaterNoPower = JacksonUtility.getBooleanNullable(node, "battery_heater_no_power", usedKeysSet);
      var climateKeeperMode = JacksonUtility.getString(node, "climate_keeper_mode", usedKeysSet);
      var defrostMode = JacksonUtility.getInteger(node, "defrost_mode", usedKeysSet);
      var driverTempSetting = JacksonUtility.getDouble(node, "driver_temp_setting", usedKeysSet);
      var fanStatus = JacksonUtility.getInteger(node, "fan_status", usedKeysSet);
      var insideTemp = JacksonUtility.getDouble(node, "inside_temp", usedKeysSet);
      var isAutoConditioningOn = JacksonUtility.getBoolean(node, "is_auto_conditioning_on", usedKeysSet);
      var isClimateOn = JacksonUtility.getBoolean(node, "is_climate_on", usedKeysSet);
      var isFrontDefrosterOn = JacksonUtility.getBoolean(node, "is_front_defroster_on", usedKeysSet);
      var isPreconditioning = JacksonUtility.getBoolean(node, "is_preconditioning", usedKeysSet);
      var isRearDefrosterOn = JacksonUtility.getBoolean(node, "is_rear_defroster_on", usedKeysSet);
      var leftTempDirection = JacksonUtility.getInteger(node, "left_temp_direction", usedKeysSet);
      var maxAvailTemp = JacksonUtility.getDouble(node, "max_avail_temp", usedKeysSet);
      var minAvailTemp = JacksonUtility.getDouble(node, "min_avail_temp", usedKeysSet);
      var outsideTemp = JacksonUtility.getDouble(node, "outside_temp", usedKeysSet);
      var passengerTempSetting = JacksonUtility.getDouble(node, "passenger_temp_setting", usedKeysSet);
      var remoteHeaterControlEnabled = JacksonUtility.getBoolean(node, "remote_heater_control_enabled", usedKeysSet);
      var rightTempDirection = JacksonUtility.getInteger(node, "right_temp_direction", usedKeysSet);
      var seatHeaterLeft = JacksonUtility.getInteger(node, "seat_heater_left", usedKeysSet);
      var seatHeaterRearCenter = JacksonUtility.getInteger(node, "seat_heater_rear_center", usedKeysSet);
      var seatHeaterRearLeft = JacksonUtility.getInteger(node, "seat_heater_rear_left", usedKeysSet);
      var seatHeaterRearRight = JacksonUtility.getInteger(node, "seat_heater_rear_right", usedKeysSet);
      var seatHeaterBackLeft = JacksonUtility.getInteger(node, "seat_heater_back_left", usedKeysSet);
      var seatHeaterBackRight = JacksonUtility.getInteger(node, "seat_heater_back_right", usedKeysSet);
      var seatHeaterRight = JacksonUtility.getInteger(node, "seat_heater_right", usedKeysSet);
      var sideMirrorHeaters = JacksonUtility.getBoolean(node, "side_mirror_heaters", usedKeysSet);
      var timestamp = JacksonUtility.getLong(node, "timestamp", usedKeysSet);
      var wiperBladeHeater = JacksonUtility.getBoolean(node, "wiper_blade_heater", usedKeysSet);

      // Get unused fields map
      var unknownFields = JacksonUtility.createUnknownFieldsMap(node, jsonParser.getCodec(), usedKeysSet);

      // Report it
      var fnc = unknownFieldsFunction.get();
      if(fnc != null && fnc.apply(unknownFields)){
        throw new IllegalArgumentException("Thrown by the unknownFieldsFunction function");
      }

      // Build and return
      return new ClimateState(
        batteryHeater,
        batteryHeaterNoPower,
        climateKeeperMode,
        defrostMode,
        driverTempSetting,
        fanStatus,
        insideTemp,
        isAutoConditioningOn,
        isClimateOn,
        isFrontDefrosterOn,
        isPreconditioning,
        isRearDefrosterOn,
        leftTempDirection,
        maxAvailTemp,
        minAvailTemp,
        outsideTemp,
        passengerTempSetting,
        remoteHeaterControlEnabled,
        rightTempDirection,
        seatHeaterLeft,
        seatHeaterRearCenter,
        seatHeaterRearLeft,
        seatHeaterRearRight,
        seatHeaterRight,
        seatHeaterBackLeft,
        seatHeaterBackRight,
        sideMirrorHeaters,
        timestamp,
        wiperBladeHeater
      );
    }
  }
}
