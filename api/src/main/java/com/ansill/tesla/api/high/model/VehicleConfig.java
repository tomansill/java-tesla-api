package com.ansill.tesla.api.high.model;

import com.ansill.utility.Utility;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

@Immutable
public final class VehicleConfig{

  /** Reports if vehicle can accept navigation request */
  private final boolean canAcceptNavigationRequest;

  /** Reports if vehicle can move trunks */
  private final boolean canAcutateTrunks;

  /** Vehicle's type */
  @Nonnull
  private final String type; // car_special_type // TODO always base?

  /** Vehicle model */
  @Nonnull
  private final String model; // car_type

  /** Charge port type */
  @Nonnull
  private final String chargePortType;

  /** Reports if vehicle is an EU vehicle */
  private final boolean isEUVehicle;

  /** Exterior color */
  @Nonnull
  private final String exteriorColor;

  /** Reports if vehicle has air suspension */
  private final boolean hasAirSuspension;

  /** Reports if vehicle has ludicrous mode */
  private final boolean hasLudicrousMode;

  /** Key version */
  private final int keyVersion;

  /** Reports if vehicle has motorized charge port door */
  private final boolean hasMotorizedChargePortDoor;

  /** Roof color */
  @Nonnull
  private final String roofColor;

  /** Spoiler type */
  @Nonnull
  private final String spoilerType;

  /** Wheel type */
  @Nonnull
  private final String wheelType;

  /**
   * VehicleConfig constructor
   *
   * @param canAcceptNavigationRequest true if vehicle can accept navigation request
   * @param canAcutateTrunks           true if vehicle can move trunks
   * @param type                       Vehicle's type
   * @param model                      Vehicle model
   * @param chargePortType             Charge port type
   * @param isEUVehicle                true if vehicle is an EU vehicle
   * @param exteriorColor              Exterior color
   * @param hasAirSuspension           true if vehicle has air suspension
   * @param hasLudicrousMode           true if vehicle has ludicrous mode
   * @param keyVersion                 Key version
   * @param hasMotorizedChargePortDoor true if vehicle has motorized charge port door
   * @param roofColor                  Roof color
   * @param spoilerType                Spoiler type
   * @param wheelType                  Wheel type
   */
  private VehicleConfig(
    boolean canAcceptNavigationRequest,
    boolean canAcutateTrunks,
    @Nonnull String type,
    @Nonnull String model,
    @Nonnull String chargePortType,
    boolean isEUVehicle,
    @Nonnull String exteriorColor,
    boolean hasAirSuspension,
    boolean hasLudicrousMode,
    int keyVersion,
    boolean hasMotorizedChargePortDoor,
    @Nonnull String roofColor,
    @Nonnull String spoilerType,
    @Nonnull String wheelType
  ){
    this.canAcceptNavigationRequest = canAcceptNavigationRequest;
    this.canAcutateTrunks = canAcutateTrunks;
    this.type = type;
    this.model = model;
    this.chargePortType = chargePortType;
    this.isEUVehicle = isEUVehicle;
    this.exteriorColor = exteriorColor;
    this.hasAirSuspension = hasAirSuspension;
    this.hasLudicrousMode = hasLudicrousMode;
    this.keyVersion = keyVersion;
    this.hasMotorizedChargePortDoor = hasMotorizedChargePortDoor;
    this.roofColor = roofColor;
    this.spoilerType = spoilerType;
    this.wheelType = wheelType;
  }

  /**
   * Converts medium-level to high-level object
   *
   * @param config medium level object
   * @return high-level object
   */
  @Nonnull
  public static VehicleConfig convert(@Nonnull com.ansill.tesla.api.low.model.VehicleConfig config){
    return new VehicleConfig(
      config.isCanAcceptNavigationRequests(),
      config.isCanActuateTrunks(),
      config.getCarSpecialType(),
      config.getCarType(),
      config.getChargePortType(),
      config.isEUVehicle(),
      config.getExteriorColor(),
      config.isHasAirSuspension(),
      config.isHasLudicrousMode(),
      config.getKeyVersion(),
      config.isMotorizedChargePort(),
      config.getRoofColor(),
      config.getSpoilerType(),
      config.getWheelType()
    );
  }

  /**
   * Reports if vehicle can accept navigation request
   *
   * @return true if vehicle can accept navigation request
   */
  public boolean canAcceptNavigationRequest(){
    return canAcceptNavigationRequest;
  }

  /**
   * Reports if vehicle can move trunks
   *
   * @return true if vehicle can move trunks
   */
  public boolean canAcutateTrunks(){
    return canAcutateTrunks;
  }

  /**
   * Returns vehicle type
   *
   * @return type
   */
  @Nonnull
  public String getType(){
    return type;
  }

  /**
   * Returns vehicle model
   *
   * @return model
   */
  @Nonnull
  public String getModel(){
    return model;
  }

  /**
   * Returns charge port type
   *
   * @return type
   */
  @Nonnull
  public String getChargePortType(){
    return chargePortType;
  }

  /**
   * Reports if vehicle is an EU vehicle
   *
   * @return true if vehicle is an EU vehicle
   */
  public boolean isEUVehicle(){
    return isEUVehicle;
  }

  /**
   * Returns exterior color
   *
   * @return color
   */
  @Nonnull
  public String getExteriorColor(){
    return exteriorColor;
  }

  /**
   * Reports whether if vehicle has an air suspension
   *
   * @return true if vehicle has an air suspension
   */
  public boolean hasAirSuspension(){
    return hasAirSuspension;
  }

  /**
   * Reports whether if vehicle has a ludicrous mode
   *
   * @return true if vehicle has a ludicrous mode
   */
  public boolean hasLudicrousMode(){
    return hasLudicrousMode;
  }

  /**
   * Returns key version
   *
   * @return version
   */
  public int getKeyVersion(){
    return keyVersion;
  }

  /**
   * Reports whether if vehicle has a motorized charge port door
   *
   * @return true if vehicle has a motorized charge port door
   */
  public boolean hasMotorizedChargePortDoor(){
    return hasMotorizedChargePortDoor;
  }

  /**
   * Returns roof color
   *
   * @return color
   */
  @Nonnull
  public String getRoofColor(){
    return roofColor;
  }

  /**
   * Returns spoiler type
   *
   * @return type
   */
  @Nonnull
  public String getSpoilerType(){
    return spoilerType;
  }

  /**
   * Returns wheel type
   *
   * @return type
   */
  @Nonnull
  public String getWheelType(){
    return wheelType;
  }

  @Override
  public String toString(){
    return Utility.simpleToString(this);
  }
}
