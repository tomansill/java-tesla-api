package com.ansill.tesla.api.high.model;

import com.ansill.utility.Utility;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.measure.Quantity;
import javax.measure.quantity.Speed;

/** Speed Limit Mode Settings */
@Immutable
public final class SpeedLimitModeSettings{

  /** Flag to indicate whether speed limit mode is enabled or not */
  private final boolean active;

  /** Current selected speed limit */
  @Nonnull
  private final Quantity<Speed> currentSpeedLimit;

  /** Maximum allowed speed limit */
  @Nonnull
  private final Quantity<Speed> maximumSpeedLimit;

  /** Minimum allowed speed limit */
  @Nonnull
  private final Quantity<Speed> minimumSpeedLimit;

  /** Flag to indicate whether pin code is set to toggle speed limit mode */
  private final boolean pinCodeSet;

  /**
   * SpeedLimitModeSettings constructor
   *
   * @param active            Flag to indicate whether speed limit mode is enabled or not
   * @param currentSpeedLimit Current selected speed limit
   * @param maximumSpeedLimit Maximum allowed speed limit
   * @param minimumSpeedLimit Minimum allowed speed limit
   * @param pinCodeSet        Flag to indicate whether pin code is set to toggle speed limit mode
   */
  SpeedLimitModeSettings(
    boolean active,
    @Nonnull Quantity<Speed> currentSpeedLimit,
    @Nonnull Quantity<Speed> maximumSpeedLimit,
    @Nonnull Quantity<Speed> minimumSpeedLimit,
    boolean pinCodeSet
  ){
    this.active = active;
    this.currentSpeedLimit = currentSpeedLimit;
    this.maximumSpeedLimit = maximumSpeedLimit;
    this.minimumSpeedLimit = minimumSpeedLimit;
    this.pinCodeSet = pinCodeSet;
  }

  /**
   * Converts medium-level to high-level object
   *
   * @param state medium level object
   * @return high-level object
   */
  @Nonnull
  public static SpeedLimitModeSettings convert(@Nonnull com.ansill.tesla.api.med.model.VehicleState.SpeedLimitMode state){
    return new SpeedLimitModeSettings(
      state.isActive(),
      state.getCurrentSpeedLimit(),
      state.getMaximumSpeedLimit(),
      state.getMinimumSpeedLimit(),
      state.isPinCodeSet()
    );
  }

  /**
   * Reports whether the speed limit mode is enforced or not
   *
   * @return true if it is active, false if it is not
   */
  public boolean isActive(){
    return active;
  }

  /**
   * Returns current selected speed limit
   *
   * @return speed limit
   */
  @Nonnull
  public Quantity<Speed> getCurrentSpeedLimit(){
    return currentSpeedLimit;
  }

  /**
   * Returns maximum allowed speed limit
   *
   * @return speed limit
   */
  @Nonnull
  public Quantity<Speed> getMaximumSpeedLimit(){
    return maximumSpeedLimit;
  }

  /**
   * Returns minimum allowed speed limit
   *
   * @return speed limit
   */
  @Nonnull
  public Quantity<Speed> getMinimumSpeedLimit(){
    return minimumSpeedLimit;
  }

  /**
   * Reports whether the pin code is set or not
   *
   * @return true if it is set, false if it is not set
   */
  public boolean isPinCodeSet(){
    return pinCodeSet;
  }

  @Override
  public String toString(){
    return Utility.simpleToString(this);
  }
}
