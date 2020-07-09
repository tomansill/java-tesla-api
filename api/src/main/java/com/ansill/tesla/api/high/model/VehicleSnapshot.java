package com.ansill.tesla.api.high.model;

import com.ansill.tesla.api.data.model.ShiftState;
import com.ansill.utility.Utility;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.measure.Quantity;
import javax.measure.quantity.Length;
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
  private final ChargeAdded chargeAdded;

  @Nonnull
  private final ShiftState shiftState;

  @Nonnull
  private final Quantity<Power> powerUsage;

  @Nonnull
  private final Quantity<Speed> speed;

  @Nonnull
  private final Quantity<Length> odometer;

  private final boolean isLocked;

  private final boolean isUserPresent;

  @Nonnull
  private final SentryModeState sentryModeState;

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
   * @param chargeAdded     charge added
   * @param odometer        odometer
   * @param isLocked        lock state - true if vehicle is locked, false if vehicle is unlocked
   * @param isUserPresent   user present state - true if user is in the vehicle, false if user is not in the vehicle
   * @param sentryModeState sentry mode state
   */
  private VehicleSnapshot(
    @Nonnull BatteryState batteryState,
    @Nonnull ChargeState chargeState,
    @Nonnull ChargeSettings chargeSettings,
    @Nonnull ClimateSettings climateSettings,
    @Nonnull ClimateState climateState,
    @Nonnull GUISettings guiSettings,
    @Nonnull VehicleConfig vehicleConfig,
    @Nonnull ChargeAdded chargeAdded, @Nonnull ShiftState shiftState,
    @Nonnull Quantity<Power> powerUsage,
    @Nonnull Quantity<Speed> speed,
    @Nonnull Quantity<Length> odometer,
    boolean isLocked,
    boolean isUserPresent,
    @Nonnull SentryModeState sentryModeState,
    @Nonnull Location location
  ){
    this.batteryState = batteryState;
    this.chargeState = chargeState;
    this.chargeSettings = chargeSettings;
    this.climateSettings = climateSettings;
    this.climateState = climateState;
    this.guiSettings = guiSettings;
    this.vehicleConfig = vehicleConfig;
    this.chargeAdded = chargeAdded;
    this.shiftState = shiftState;
    this.powerUsage = powerUsage;
    this.speed = speed;
    this.odometer = odometer;
    this.isLocked = isLocked;
    this.isUserPresent = isUserPresent;
    this.sentryModeState = sentryModeState;
    this.location = location;
  }

  /**
   * Converts medium-level to high-level object
   *
   * @param data medium level object
   * @return high-level object
   */
  @Nonnull
  public static VehicleSnapshot convert(@Nonnull com.ansill.tesla.api.low.model.CompleteData data){
    return new VehicleSnapshot(
      BatteryState.convert(data.getChargeState()),
      ChargeState.convert(data.getChargeState()),
      ChargeSettings.convert(data.getChargeState()),
      ClimateSettings.convert(data.getClimateState()),
      ClimateState.convert(data.getClimateState()),
      GUISettings.convert(data.getGuiSettings()),
      VehicleConfig.convert(data.getVehicleConfig()),
      ChargeAdded.convert(data.getChargeState()),
      data.getDriveState().getShiftState(),
      data.getDriveState().getPower(),
      data.getDriveState().getSpeed(),
      data.getVehicleState().getOdometer(),
      data.getVehicleState().isLocked(),
      data.getVehicleState().isUserPresent(),
      data.getVehicleState().isSentryModeAvailable() ? data.getVehicleState()
                                                           .isSentryMode() ? SentryModeState.ACTIVE : SentryModeState.INACTIVE : SentryModeState.NOT_AVAILABLE,
      new Location(
        data.getDriveState().getHeading(),
        data.getDriveState().getLatitude(),
        data.getDriveState().getLongitude()
      )
    );
  }


  /**
   * Returns gui settings
   *
   * @return settings
   */
  @Nonnull
  public GUISettings getGuiSettings(){
    return guiSettings;
  }


  /**
   * Returns odometer
   *
   * @return odometer
   */
  @Nonnull
  public Quantity<Length> getOdometer(){
    return odometer;
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

  public boolean isVehicleLocked(){
    return isLocked;
  }

  public boolean isUserPresent(){
    return isUserPresent;
  }

  @Nonnull
  public SentryModeState getSentryModeState(){
    return sentryModeState;
  }

  @Nonnull
  public ChargeAdded getChargeAdded(){
    return chargeAdded;
  }
}
