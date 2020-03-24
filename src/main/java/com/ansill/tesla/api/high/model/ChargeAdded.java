package com.ansill.tesla.api.high.model;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.measure.Quantity;
import javax.measure.quantity.Length;
import javax.measure.quantity.Power;

/** ChargeAdded class */
@Immutable
public final class ChargeAdded{

  /** Amount of energy added (or current if currently charging */
  @Nonnull
  private final Quantity<Power> energy;

  /** Amount of ideal distance added (or current if currently charging */
  @Nonnull
  private final Quantity<Length> idealDistance;

  /** Amount of rated distance added (or current if currently charging */
  @Nonnull
  private final Quantity<Length> ratedDistance;

  /**
   * ChargeAdded constructor
   *
   * @param energy        Amount of energy added
   * @param idealDistance Amount of ideal distance added
   * @param ratedDistance Amount of rated distance added
   */
  ChargeAdded(
    @Nonnull Quantity<Power> energy,
    @Nonnull Quantity<Length> idealDistance,
    @Nonnull Quantity<Length> ratedDistance
  ){
    this.energy = energy;
    this.idealDistance = idealDistance;
    this.ratedDistance = ratedDistance;
  }

  /**
   * Converts medium-level to high-level object
   *
   * @param state medium level object
   * @return high-level object
   */
  @Nonnull
  public static ChargeAdded convert(@Nonnull com.ansill.tesla.api.med.model.ChargeState state){
    return new ChargeAdded(
      state.getChargeEnergyAdded(),
      state.getChargeDistanceAddedIdeal(),
      state.getChargeDistanceAddedRated()
    );
  }

  /**
   * Returns amount of energy added
   *
   * @return amount of energy
   */
  @Nonnull
  public Quantity<Power> getEnergy(){
    return energy;
  }

  /**
   * Returns ideal distance added
   *
   * @return distance
   */
  @Nonnull
  public Quantity<Length> getIdealDistance(){
    return idealDistance;
  }

  /**
   * Returns rated distance added
   *
   * @return distance
   */
  @Nonnull
  public Quantity<Length> getRatedDistance(){
    return ratedDistance;
  }
}
