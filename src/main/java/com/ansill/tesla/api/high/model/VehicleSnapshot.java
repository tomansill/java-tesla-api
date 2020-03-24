package com.ansill.tesla.api.high.model;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

/** Complete vehicle state */
@Immutable
public final class VehicleSnapshot{

  /** Battery state */
  @Nonnull
  private final BatteryState batteryState;

  /** Charge state */
  @Nonnull
  private final ChargeState chargeState;

  /** Charge settings */
  @Nonnull
  private final ChargeSettings chargeSettings;

  /** Drive state */
  @Nonnull
  private final DriveState driveState;

  /** Climate settings */
  @Nonnull
  private final ClimateSettings climateSettings;

  /** Climate State */
  @Nonnull
  private final ClimateState climateState;

  /** GUI Settings */
  @Nonnull
  private final GUISettings guiSettings;

  /** Vehicle Config */
  @Nonnull
  private final VehicleConfig vehicleConfig;

  /**
   * VehicleSnapshot constructor
   *
   * @param batteryState    battery state
   * @param chargeState     charge state
   * @param chargeSettings  charge settings
   * @param driveState      drive state
   * @param climateSettings climate settings
   * @param climateState    climate state
   * @param guiSettings     gui settings
   * @param vehicleConfig   vehicle config
   */
  private VehicleSnapshot(
    @Nonnull BatteryState batteryState,
    @Nonnull ChargeState chargeState,
    @Nonnull ChargeSettings chargeSettings, @Nonnull DriveState driveState,
    @Nonnull ClimateSettings climateSettings,
    @Nonnull ClimateState climateState,
    @Nonnull GUISettings guiSettings,
    @Nonnull VehicleConfig vehicleConfig
  ){
    this.batteryState = batteryState;
    this.chargeState = chargeState;
    this.chargeSettings = chargeSettings;
    this.driveState = driveState;
    this.climateSettings = climateSettings;
    this.climateState = climateState;
    this.guiSettings = guiSettings;
    this.vehicleConfig = vehicleConfig;
  }


  /**
   * Converts medium-level to high-level object
   *
   * @param data medium level object
   * @return high-level object
   */
  @Nonnull
  public static VehicleSnapshot convert(@Nonnull com.ansill.tesla.api.med.model.CompleteData data){
    return new VehicleSnapshot(
      BatteryState.convert(data.getChargeState()),
      ChargeState.convert(data.getChargeState()),
      ChargeSettings.convert(data.getChargeState()),
      DriveState.convert(data.getDriveState()),
      ClimateSettings.convert(data.getClimateState()),
      ClimateState.convert(data.getClimateState()),
      GUISettings.convert(data.getGuiSettings()),
      VehicleConfig.convert(data.getVehicleConfig())
    );
  }

  /**
   * Returns battery state
   *
   * @return state
   */
  @Nonnull
  public BatteryState getBatteryState(){
    return batteryState;
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
   * Returns drive state
   *
   * @return state
   */
  @Nonnull
  public DriveState getDriveState(){
    return driveState;
  }

  /**
   * Returns climate settings
   *
   * @return settings
   */
  @Nonnull
  public ClimateSettings getClimateSettings(){
    return climateSettings;
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
   * Returns GUI settings
   *
   * @return settings
   */
  @Nonnull
  public GUISettings getGUISettings(){
    return guiSettings;
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
   * Returns charge settings
   *
   * @return settings
   */
  @Nonnull
  public ChargeSettings getChargeSettings(){
    return chargeSettings;
  }
}
