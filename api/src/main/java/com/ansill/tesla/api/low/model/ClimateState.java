package com.ansill.tesla.api.low.model;

import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.measure.Quantity;
import javax.measure.quantity.Temperature;
import java.util.Optional;

@Immutable
public final class ClimateState{

  private final boolean batteryHeater;

  private final boolean batteryHeaterNoPower;

  private final String climateKeeperMode;

  @Nonnull
  private final Quantity<Temperature> driverTempSetting;

  private final int fanStatus;

  @Nonnull
  private final Quantity<Temperature> insideTemp;

  @Nullable
  private final Boolean isAutoConditioningOn;

  private final boolean isClimateOn;

  private final boolean isFrontDefrosterOn;

  private final boolean isPreconditioning;

  private final boolean isRearDefrosterOn;

  private final int leftTempDirection; // TODO ???

  @Nonnull
  private final Quantity<Temperature> maxAvailTemp;

  @Nonnull
  private final Quantity<Temperature> minAvailTemp;

  @Nonnull
  private final Quantity<Temperature> outsideTemp;

  @Nonnull
  private final Quantity<Temperature> passengerTempSetting;

  private final boolean remoteHeaterControlEnabled;

  private final int rightTempDirection; // TODO ???

  @Nullable
  private final Integer seatHeaterLeft; // Entry could disappear from JSON

  @Nullable
  private final Integer seatHeaterRearCenter; // Entry could disappear from JSON

  @Nullable
  private final Integer seatHeaterRearLeft; // Entry could disappear from JSON

  @Nullable
  private final Integer seatHeaterLeftBack; // Entry could disappear from JSON

  @Nullable
  private final Integer seatHeaterRearRight; // Entry could disappear from JSON

  @Nullable
  private final Integer seatHeaterRightBack; // Entry could disappear from JSON

  @Nullable
  private final Integer seatHeaterRight; // Entry could disappear from JSON

  @Nullable
  private final Boolean sideMirrorHeaters; // Entry could disappear from JSON

  private final long timestamp;

  @Nullable
  private final Boolean wiperBladeHeater; // Entry could disappear from JSON

  private final int defrostMode;

  public ClimateState(
    boolean batteryHeater,
    boolean batteryHeaterNoPower,
    @Nonnull String climateKeeperMode,
    @Nonnull Quantity<Temperature> driverTempSetting,
    int fanStatus,
    @Nonnull Quantity<Temperature> insideTemp,
    @Nullable Boolean isAutoConditioningOn,
    boolean isClimateOn,
    boolean isFrontDefrosterOn,
    boolean isPreconditioning,
    boolean isRearDefrosterOn,
    int leftTempDirection,
    @Nonnull Quantity<Temperature> maxAvailTemp,
    @Nonnull Quantity<Temperature> minAvailTemp,
    @Nonnull Quantity<Temperature> outsideTemp,
    @Nonnull Quantity<Temperature> passengerTempSetting,
    boolean remoteHeaterControlEnabled,
    int rightTempDirection,
    @Nullable Integer seatHeaterLeft,
    @Nullable Integer seatHeaterRight,
    @Nullable Integer seatHeaterRearLeft,
    @Nullable Integer seatHeaterRearCenter,
    @Nullable Integer seatHeaterLeftBack,
    @Nullable Integer seatHeaterRearRight,
    @Nullable Integer seatHeaterRightBack,
    @Nullable Boolean sideMirrorHeaters,
    long timestamp,
    @Nullable Boolean wiperBladeHeater,
    int defrostMode
  ){
    this.batteryHeater = batteryHeater;
    this.batteryHeaterNoPower = batteryHeaterNoPower;
    this.climateKeeperMode = climateKeeperMode;
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
    this.seatHeaterLeftBack = seatHeaterLeftBack;
    this.seatHeaterRearRight = seatHeaterRearRight;
    this.seatHeaterRightBack = seatHeaterRightBack;
    this.seatHeaterRight = seatHeaterRight;
    this.sideMirrorHeaters = sideMirrorHeaters;
    this.timestamp = timestamp;
    this.wiperBladeHeater = wiperBladeHeater;
    this.defrostMode = defrostMode;
  }

  @Nonnull
  public static ClimateState convert(@Nonnull com.ansill.tesla.api.data.model.ClimateState state){
    return new ClimateState(
      state.hasBatteryHeater(),
      state.hasPowerInBatteryHeater().orElse(false), // TODO
      state.getClimateKeeperMode(),
      Quantities.getQuantity(state.getDriverTempSetting(), Units.CELSIUS),
      state.getFanStatus(),
      Quantities.getQuantity(state.getInsideTemp(), Units.CELSIUS),
      state.isAutoConditioningOn(),
      state.isClimateOn(),
      state.isFrontDefrosterOn(),
      state.isPreconditioning(),
      state.isRearDefrosterOn(),
      state.getLeftTempDirection(),
      Quantities.getQuantity(state.getMaxAvailTemp(), Units.CELSIUS),
      Quantities.getQuantity(state.getMinAvailTemp(), Units.CELSIUS),
      Quantities.getQuantity(state.getOutsideTemp(), Units.CELSIUS),
      Quantities.getQuantity(state.getPassengerTempSetting(), Units.CELSIUS),
      state.isRemoteHeaterControlEnabled(),
      state.getRightTempDirection(),
      state.getSeatHeaterLeft().orElse(null),
      state.getSeatHeaterRight().orElse(null),
      state.getSeatHeaterRearLeft().orElse(null),
      state.getSeatHeaterRearCenter().orElse(null),
      state.getSeatHeaterRearRight().orElse(null),
      state.getSeatHeaterBackLeft().orElse(null),
      state.getSeatHeaterBackRight().orElse(null),
      state.hasSideMirrorHeaters(),
      state.getTimestamp(),
      state.hasWiperBladeHeater(),
      state.getDefrostMode()
    );
  }

  public boolean isBatteryHeater(){
    return batteryHeater;
  }

  public boolean isBatteryHeaterNoPower(){
    return batteryHeaterNoPower;
  }

  public String getClimateKeeperMode(){
    return climateKeeperMode;
  }

  @Nonnull
  public Quantity<Temperature> getDriverTempSetting(){
    return driverTempSetting;
  }

  public int getFanStatus(){
    return fanStatus;
  }

  @Nonnull
  public Quantity<Temperature> getInsideTemp(){
    return insideTemp;
  }

  @Nonnull
  public Optional<Boolean> isAutoConditioningOn(){
    return Optional.ofNullable(isAutoConditioningOn);
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

  @Nonnull
  public Quantity<Temperature> getMaxAvailTemp(){
    return maxAvailTemp;
  }

  @Nonnull
  public Quantity<Temperature> getMinAvailTemp(){
    return minAvailTemp;
  }

  @Nonnull
  public Quantity<Temperature> getOutsideTemp(){
    return outsideTemp;
  }

  @Nonnull
  public Quantity<Temperature> getPassengerTempSetting(){
    return passengerTempSetting;
  }

  public boolean isRemoteHeaterControlEnabled(){
    return remoteHeaterControlEnabled;
  }

  public int getRightTempDirection(){
    return rightTempDirection;
  }

  @Nullable
  public Integer getSeatHeaterLeft(){
    return seatHeaterLeft;
  }

  @Nullable
  public Integer getSeatHeaterRearCenter(){
    return seatHeaterRearCenter;
  }

  @Nullable
  public Integer getSeatHeaterRearLeft(){
    return seatHeaterRearLeft;
  }

  @Nullable
  public Integer getSeatHeaterLeftBack(){
    return seatHeaterLeftBack;
  }

  @Nullable
  public Integer getSeatHeaterRearRight(){
    return seatHeaterRearRight;
  }

  @Nullable
  public Integer getSeatHeaterRightBack(){
    return seatHeaterRightBack;
  }

  @Nullable
  public Integer getSeatHeaterRight(){
    return seatHeaterRight;
  }

  @Nullable
  public Boolean getSideMirrorHeaters(){
    return sideMirrorHeaters;
  }

  public long getTimestamp(){
    return timestamp;
  }

  @Nullable
  public Boolean getWiperBladeHeater(){
    return wiperBladeHeater;
  }

  public int getDefrostMode(){
    return defrostMode;
  }
}
