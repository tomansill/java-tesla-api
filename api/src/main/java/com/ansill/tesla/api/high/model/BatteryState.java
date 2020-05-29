package com.ansill.tesla.api.high.model;

import com.ansill.tesla.api.low.model.ChargeState;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.measure.Quantity;
import javax.measure.quantity.Dimensionless;
import javax.measure.quantity.Length;

/** Battery State class */
@SuppressWarnings("unused")
@Immutable
public final class BatteryState{

  /** Battery heating state */
  @Nonnull
  private final BatteryHeatingState batteryHeatingState;

  /** Battery percentage from 0 to 100 */
  @Nonnull
  private final Quantity<Dimensionless> batteryCapacityPercentage;

  /** Usable battery percentage from 0 to 100 */
  @Nonnull
  private final Quantity<Dimensionless> usableBatteryCapacityPercentage;

  /** The vehicle's range with current battery capacity */
  @Nonnull
  private final Quantity<Length> batteryRange;

  /** The vehicle's range with ideal battery capacity */
  @Nonnull
  private final Quantity<Length> idealBatteryRange;

  /** The vehicle's range with estimated battery capacity */
  @Nonnull
  private final Quantity<Length> estimatedBatteryRange;

  /**
   * BatteryState constructor
   *
   * @param batteryHeatingState             battery heating state
   * @param batteryCapacityPercentage       battery capacity percentage
   * @param usableBatteryCapacityPercentage usable battery capacity percentage
   * @param batteryRange                    battery range
   * @param idealBatteryRange               ideal battery range
   * @param estimatedBatteryRange           estimated battery range
   */
  private BatteryState(
    @Nonnull BatteryHeatingState batteryHeatingState,
    @Nonnull Quantity<Dimensionless> batteryCapacityPercentage,
    @Nonnull Quantity<Dimensionless> usableBatteryCapacityPercentage,
    @Nonnull Quantity<Length> batteryRange,
    @Nonnull Quantity<Length> idealBatteryRange,
    @Nonnull Quantity<Length> estimatedBatteryRange
  ){
    this.batteryHeatingState = batteryHeatingState;
    this.batteryCapacityPercentage = batteryCapacityPercentage;
    this.usableBatteryCapacityPercentage = usableBatteryCapacityPercentage;
    this.batteryRange = batteryRange;
    this.idealBatteryRange = idealBatteryRange;
    this.estimatedBatteryRange = estimatedBatteryRange;
  }

  /**
   * Converts medium-level ChargeState to high-level BatteryState
   *
   * @param state medium-level ChargeState
   * @return BatteryState
   */
  @Nonnull
  public static BatteryState convert(@Nonnull ChargeState state){

    // Determine which state for battery heating state
    BatteryHeatingState heatingState = state.isBatteryHeaterOn() ? BatteryHeatingState.HEATING : BatteryHeatingState.INACTIVE;
    if(state.isNotEnoughPowerToHeat().isPresent() && state.isNotEnoughPowerToHeat().get())
      heatingState = BatteryHeatingState.NOT_ENOUGH_POWER;
    else heatingState = BatteryHeatingState.UNAVAILABLE;

    return new BatteryState(
      heatingState,
      state.getBatteryLevel(),
      state.getUsableBatteryLevel(),
      state.getBatteryRange(),
      state.getIdealBatteryRange(),
      state.getEstimatedBatteryRange()
    );
  }

  /**
   * Returns battery heating state
   *
   * @return state
   */
  @Nonnull
  public BatteryHeatingState getBatteryHeatingState(){
    return batteryHeatingState;
  }

  /**
   * Returns percentage of battery capacity
   *
   * @return percentage
   */
  @Nonnull
  public Quantity<Dimensionless> getBatteryCapacityPercentage(){
    return batteryCapacityPercentage;
  }

  /**
   * Returns percentage of usable battery capacity
   *
   * @return percentage
   */
  @Nonnull
  public Quantity<Dimensionless> getUsableBatteryCapacityPercentage(){
    return usableBatteryCapacityPercentage;
  }

  /**
   * Returns battery range
   *
   * @return battery range
   */
  @Nonnull
  public Quantity<Length> getBatteryRange(){
    return batteryRange;
  }

  /**
   * Returns ideal battery range
   *
   * @return battery range
   */
  @Nonnull
  public Quantity<Length> getIdealBatteryRange(){
    return idealBatteryRange;
  }

  /**
   * Returns ideal battery range
   *
   * @return battery range
   */
  @Nonnull
  public Quantity<Length> getEstimatedBatteryRange(){
    return estimatedBatteryRange;
  }

}
