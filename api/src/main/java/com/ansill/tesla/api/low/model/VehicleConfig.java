package com.ansill.tesla.api.low.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.time.Instant;
import java.util.Optional;

@Immutable
public final class VehicleConfig{
  private final boolean canAcceptNavigationRequests;

  private final boolean canActuateTrunks;

  private final String carSpecialType;

  private final String carType;

  private final String chargePortType;

  private final boolean isEUVehicle;

  private final String exteriorColor;

  private final boolean hasAirSuspension;

  private final boolean hasLudicrousMode;

  private final int keyVersion;

  private final boolean motorizedChargePort;

  private final boolean plg;

  private final int rearSeatHeaters;

  private final String rearSeatType;

  private final boolean isRightHandDrive;

  private final String roofColor;

  @Nullable
  private final String seatType; // TODO What are the types?

  private final String spoilerType;

  private final String sunRoofInstalled;

  private final String thirdRowSeats;

  @Nonnull
  private final Instant timestamp;

  private final String wheelType;

  private final boolean useRangeBadging;

  public VehicleConfig(
    boolean canAcceptNavigationRequests,
    boolean canActuateTrunks,
    String carSpecialType,
    String carType,
    String chargePortType,
    boolean isEUVehicle,
    String exteriorColor,
    boolean hasAirSuspension,
    boolean hasLudicrousMode,
    int keyVersion,
    boolean motorizedChargePort,
    boolean plg,
    int rearSeatHeaters,
    @Nullable String rearSeatType,
    boolean isRightHandDrive,
    String roofColor,
    @Nullable String seatType,
    String spoilerType,
    @Nullable String sunRoofInstalled,
    String thirdRowSeats,
    @Nonnull Instant timestamp,
    String wheelType,
    boolean useRangeBadging
  ){
    this.canAcceptNavigationRequests = canAcceptNavigationRequests;
    this.canActuateTrunks = canActuateTrunks;
    this.carSpecialType = carSpecialType;
    this.carType = carType;
    this.chargePortType = chargePortType;
    this.isEUVehicle = isEUVehicle;
    this.exteriorColor = exteriorColor;
    this.hasAirSuspension = hasAirSuspension;
    this.hasLudicrousMode = hasLudicrousMode;
    this.keyVersion = keyVersion;
    this.motorizedChargePort = motorizedChargePort;
    this.plg = plg;
    this.rearSeatHeaters = rearSeatHeaters;
    this.rearSeatType = rearSeatType;
    this.isRightHandDrive = isRightHandDrive;
    this.roofColor = roofColor;
    this.seatType = seatType;
    this.spoilerType = spoilerType;
    this.sunRoofInstalled = sunRoofInstalled;
    this.thirdRowSeats = thirdRowSeats;
    this.timestamp = timestamp;
    this.wheelType = wheelType;
    this.useRangeBadging = useRangeBadging;
  }

  @Nonnull
  public static VehicleConfig convert(@Nonnull com.ansill.tesla.api.data.model.VehicleConfig config){
    return new VehicleConfig(
      config.isCanAcceptNavigationRequests(),
      config.isCanActuateTrunks(),
      config.getCarSpecialType(),
      config.getCarType(),
      config.getChargePortType(),
      config.isEuVehicle(),
      config.getExteriorColor(),
      config.hasAirSuspension(),
      config.hasLudicrousMode(),
      config.getKeyVersion(),
      config.hasMotorizedChargePort(),
      config.isPlg(),
      config.getRearSeatHeaters(),
      config.getRearSeatType().orElse(null),
      config.isCanAcceptNavigationRequests(),
      config.getRoofColor(),
      config.getSeatType().orElse(null),
      config.getSpoilerType(),
      config.getSunRoofInstalled().orElse(null),
      config.getThirdRowSeats(),
      Instant.ofEpochSecond(config.getTimestamp()),
      config.getWheelType(),
      config.usesRangeBadging()
    );
  }

  public boolean isCanAcceptNavigationRequests(){
    return canAcceptNavigationRequests;
  }

  public boolean isCanActuateTrunks(){
    return canActuateTrunks;
  }

  public String getCarSpecialType(){
    return carSpecialType;
  }

  public String getCarType(){
    return carType;
  }

  public String getChargePortType(){
    return chargePortType;
  }

  public boolean isEUVehicle(){
    return isEUVehicle;
  }

  public String getExteriorColor(){
    return exteriorColor;
  }

  public boolean isHasAirSuspension(){
    return hasAirSuspension;
  }

  public boolean isHasLudicrousMode(){
    return hasLudicrousMode;
  }

  public int getKeyVersion(){
    return keyVersion;
  }

  public boolean isMotorizedChargePort(){
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

  public boolean isRightHandDrive(){
    return isRightHandDrive;
  }

  public String getRoofColor(){
    return roofColor;
  }

  @Nonnull
  public Optional<String> getSeatType(){
    return Optional.ofNullable(seatType);
  }

  public String getSpoilerType(){
    return spoilerType;
  }

  @Nonnull
  public Optional<String> getSunRoofInstalled(){
    return Optional.ofNullable(sunRoofInstalled);
  }

  public String getThirdRowSeats(){
    return thirdRowSeats;
  }

  @Nonnull
  public Instant getTimestamp(){
    return timestamp;
  }

  public String getWheelType(){
    return wheelType;
  }

  public boolean isUseRangeBadging(){
    return useRangeBadging;
  }
}
