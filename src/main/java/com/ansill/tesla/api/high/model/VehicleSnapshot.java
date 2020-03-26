package com.ansill.tesla.api.high.model;

import com.ansill.tesla.api.model.ShiftState;
import com.ansill.tesla.api.utility.Utility;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.measure.Quantity;
import javax.measure.quantity.Power;
import javax.measure.quantity.Speed;

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

  @Nonnull
  private final ShiftState shiftState;

  @Nonnull
  private final Quantity<Power> powerUsage;

  @Nonnull
  private final Quantity<Speed> speed;

  @Nonnull
  private final Location location;

  /**
   * VehicleSnapshot constructor
   *
   * @param batteryState    battery state
   * @param chargeState     charge state
   * @param chargeSettings  charge settings
   * @param climateSettings climate settings
   * @param climateState    climate state
   * @param guiSettings     gui settings
   * @param vehicleConfig   vehicle config
   */
  private VehicleSnapshot(
    @Nonnull BatteryState batteryState,
    @Nonnull ChargeState chargeState,
    @Nonnull ChargeSettings chargeSettings,
    @Nonnull ClimateSettings climateSettings,
    @Nonnull ClimateState climateState,
    @Nonnull GUISettings guiSettings,
    @Nonnull VehicleConfig vehicleConfig,
    @Nonnull ShiftState shiftState,
    @Nonnull Quantity<Power> powerUsage,
    @Nonnull Quantity<Speed> speed,
    @Nonnull Location location
  ){
    this.batteryState = batteryState;
    this.chargeState = chargeState;
    this.chargeSettings = chargeSettings;
    this.climateSettings = climateSettings;
    this.climateState = climateState;
    this.guiSettings = guiSettings;
    this.vehicleConfig = vehicleConfig;
    this.shiftState = shiftState;
    this.powerUsage = powerUsage;
    this.speed = speed;
    this.location = location;
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
      ClimateSettings.convert(data.getClimateState()),
      ClimateState.convert(data.getClimateState()),
      GUISettings.convert(data.getGuiSettings()),
      VehicleConfig.convert(data.getVehicleConfig()),
      data.getDriveState().getShiftState(),
      data.getDriveState().getPower(),
      data.getDriveState().getSpeed(),
      new Location(
        data.getDriveState().getHeading(),
        data.getDriveState().getLatitude(),
        data.getDriveState().getLongitude()
      )
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

  @Override
  public String toString(){
    return Utility.simpleToString(this);
  }

  @Nonnull
  public Quantity<Power> getPowerUsage(){
    return powerUsage;
  }

  @Nonnull
  public ShiftState getShiftState(){
    return shiftState;
  }

  @Nonnull
  public Quantity<Speed> getSpeed(){
    return speed;
  }

  @Nonnull
  public Location getLocation(){
    return location;
  }
}
