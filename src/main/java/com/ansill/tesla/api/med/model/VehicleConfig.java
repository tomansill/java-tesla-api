package com.ansill.tesla.api.med.model;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.time.Instant;

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

  private final String perfConfig; // TODO what is this

  private final boolean plg;

  private final int rearSeatHeaters;

  private final int rearSeatType;

  private final boolean isRightHandDrive;

  private final String roofColor;

  private final int seatType; // TODO What are the types?

  private final String spoilerType;

  private final int sunRoofInstalled;

  private final String thirdRowSeats;

  @Nonnull
  private final Instant timestamp;

  private final String trimBadging;

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
    String perfConfig,
    boolean plg,
    int rearSeatHeaters,
    int rearSeatType,
    boolean isRightHandDrive,
    String roofColor,
    int seatType,
    String spoilerType,
    int sunRoofInstalled,
    String thirdRowSeats,
    @Nonnull Instant timestamp,
    String trimBadging,
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
    this.perfConfig = perfConfig;
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
    this.trimBadging = trimBadging;
    this.wheelType = wheelType;
    this.useRangeBadging = useRangeBadging;
  }

  @Nonnull
  public static VehicleConfig convert(@Nonnull com.ansill.tesla.api.low.model.VehicleConfig config){
    return new VehicleConfig(
      config.isCanAcceptNavigationRequests(),
      config.isCanActuateTrunks(),
      config.getCarSpecialType(),
      config.getCarType(),
      config.getChargePortType(),
      config.getEuVehicle(),
      config.getExteriorColor(),
      config.getHasAirSuspension(),
      config.getHasLudicrousMode(),
      config.getKeyVersion(),
      config.getMotorizedChargePort(),
      config.getPerfConfig(),
      config.isPlg(),
      config.getRearSeatHeaters(),
      config.getRearSeatType(),
      config.isCanAcceptNavigationRequests(),
      config.getRoofColor(),
      config.getSeatType(),
      config.getSpoilerType(),
      config.getSunRoofInstalled(),
      config.getThirdRowSeats(),
      Instant.ofEpochSecond(config.getTimestamp()),
      config.getTrimBadging(),
      config.getWheelType(),
      config.isUseRangeBadging()
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

  public String getPerfConfig(){
    return perfConfig;
  }

  public boolean isPlg(){
    return plg;
  }

  public int getRearSeatHeaters(){
    return rearSeatHeaters;
  }

  public int getRearSeatType(){
    return rearSeatType;
  }

  public boolean isRightHandDrive(){
    return isRightHandDrive;
  }

  public String getRoofColor(){
    return roofColor;
  }

  public int getSeatType(){
    return seatType;
  }

  public String getSpoilerType(){
    return spoilerType;
  }

  public int getSunRoofInstalled(){
    return sunRoofInstalled;
  }

  public String getThirdRowSeats(){
    return thirdRowSeats;
  }

  @Nonnull
  public Instant getTimestamp(){
    return timestamp;
  }

  public String getTrimBadging(){
    return trimBadging;
  }

  public String getWheelType(){
    return wheelType;
  }

  public boolean isUseRangeBadging(){
    return useRangeBadging;
  }
}
