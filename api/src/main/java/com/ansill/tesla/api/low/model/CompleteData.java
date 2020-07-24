package com.ansill.tesla.api.low.model;


import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

/** Complete data on Vehicle */
@Immutable
public final class CompleteData{

  /** Vehicle */
  @Nonnull
  private final Vehicle vehicle;

  /** Vehicle state */
  @Nonnull
  private final VehicleState vehicleState;

  /** Climate State */
  @Nonnull
  private final ClimateState climateState;

  /** Charge State */
  @Nonnull
  private final ChargeState chargeState;

  /** Vehicle Config */
  @Nonnull
  private final VehicleConfig vehicleConfig;

  /** GUI Settings */
  @Nonnull
  private final GuiSettings guiSettings;

  /** Drive State */
  @Nonnull
  private final DriveState driveState;

  /**
   * CompleteData constructor
   *
   * @param vehicle       vehicle
   * @param vehicleState  vehicle state
   * @param climateState  climate state
   * @param chargeState   charge state
   * @param vehicleConfig vehicle config
   * @param guiSettings   gui settings
   * @param driveState    drive state
   */
  CompleteData(
    @Nonnull Vehicle vehicle,
    @Nonnull VehicleState vehicleState,
    @Nonnull ClimateState climateState,
    @Nonnull ChargeState chargeState,
    @Nonnull VehicleConfig vehicleConfig,
    @Nonnull GuiSettings guiSettings,
    @Nonnull DriveState driveState
  ){
    this.vehicle = vehicle;
    this.vehicleState = vehicleState;
    this.climateState = climateState;
    this.chargeState = chargeState;
    this.vehicleConfig = vehicleConfig;
    this.guiSettings = guiSettings;
    this.driveState = driveState;
  }

  @Nonnull
  public static CompleteData convert(@Nonnull com.ansill.tesla.api.data.model.CompleteVehicle vehicle){
    return new CompleteData(
      Vehicle.convert(vehicle),
      VehicleState.convert(vehicle.getVehicleState()),
      ClimateState.convert(vehicle.getClimateState()),
      ChargeState.convert(vehicle.getChargeState()),
      VehicleConfig.convert(vehicle.getVehicleConfig()),
      GuiSettings.convert(vehicle.getGuiSettings()),
      DriveState.convert(vehicle.getDriveState())
    );
  }

  /**
   * Returns vehicle information
   *
   * @return vehicle
   */
  @Nonnull
  public Vehicle getVehicle(){
    return vehicle;
  }

  /**
   * Returns vehicle state
   *
   * @return state
   */
  @Nonnull
  public VehicleState getVehicleState(){
    return vehicleState;
  }

  /**
   * Returns climate state
   *
   * @return state
   */
  @Nonnull
  public ClimateState getClimateState(){
    return climateState;
  }

  /**
   * Returns charge state
   *
   * @return state
   */
  @Nonnull
  public ChargeState getChargeState(){
    return chargeState;
  }

  /**
   * Returns vehicle config
   *
   * @return config
   */
  @Nonnull
  public VehicleConfig getVehicleConfig(){
    return vehicleConfig;
  }

  /**
   * Returns GUI settings
   *
   * @return settings
   */
  @Nonnull
  public GuiSettings getGuiSettings(){
    return guiSettings;
  }

  /**
   * Returns drive state
   *
   * @return state
   */
  @Nonnull
  public DriveState getDriveState(){
    return driveState;
  }
}
