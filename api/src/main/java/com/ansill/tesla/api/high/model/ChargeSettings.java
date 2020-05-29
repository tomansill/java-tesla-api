package com.ansill.tesla.api.high.model;

import com.ansill.tesla.api.med.model.ChargeState;
import com.ansill.utility.Utility;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.measure.Quantity;
import javax.measure.quantity.Dimensionless;

/** Settings of Charging */
@Immutable
public final class ChargeSettings{

  /** Current battery limit */
  @Nonnull
  private final Quantity<Dimensionless> currentBatteryLimit; // TODO move me out

  /** Maximum allowed battery limit */
  @Nonnull
  private final Quantity<Dimensionless> maximumBatteryLimit;

  /** Minimum allowed battery limit */
  @Nonnull
  private final Quantity<Dimensionless> minimumBatteryLimit;

  /** "Recommended" battery limit */
  @Nonnull
  private final Quantity<Dimensionless> standardBatteryLimit;

  // TODO add scheduled charging time

  /**
   * ChargeSettings Constructor
   *
   * @param currentBatteryLimit  Current battery limit
   * @param maximumBatteryLimit  Maximum allowed battery limit
   * @param minimumBatteryLimit  Minimum allowed battery limit
   * @param standardBatteryLimit "Recommended" battery limit
   */
  private ChargeSettings(
    @Nonnull Quantity<Dimensionless> currentBatteryLimit,
    @Nonnull Quantity<Dimensionless> maximumBatteryLimit,
    @Nonnull Quantity<Dimensionless> minimumBatteryLimit,
    @Nonnull Quantity<Dimensionless> standardBatteryLimit
  ){
    this.currentBatteryLimit = currentBatteryLimit;
    this.maximumBatteryLimit = maximumBatteryLimit;
    this.minimumBatteryLimit = minimumBatteryLimit;
    this.standardBatteryLimit = standardBatteryLimit;
  }

  /**
   * Converts medium-level to high-level object
   *
   * @param state medium level object
   * @return high-level object
   */
  @Nonnull
  public static ChargeSettings convert(@Nonnull ChargeState state){
    return new ChargeSettings(
      state.getChargeLimitSOC(),
      state.getChargeLimitSOCMax(),
      state.getChargeLimitSOCMin(),
      state.getChargeLimitSOCStandard()
    );
  }

  /**
   * Returns current battery limit
   *
   * @return limit in percentage
   */
  @Nonnull
  public Quantity<Dimensionless> getCurrentBatteryLimit(){
    return currentBatteryLimit;
  }

  /**
   * Returns maximum allowed battery limit
   *
   * @return limit in percentage
   */
  @Nonnull
  public Quantity<Dimensionless> getMaximumBatteryLimit(){
    return maximumBatteryLimit;
  }

  /**
   * Returns minimum allowed battery limit
   *
   * @return limit in percentage
   */
  @Nonnull
  public Quantity<Dimensionless> getMinimumBatteryLimit(){
    return minimumBatteryLimit;
  }

  /**
   * Returns recommended battery limit
   *
   * @return limit in percentage
   */
  @Nonnull
  public Quantity<Dimensionless> getStandardBatteryLimit(){
    return standardBatteryLimit;
  }

  @Override
  public String toString(){
    return Utility.simpleToString(this);
  }
}
