package com.ansill.tesla.api.high.model;

import com.ansill.tesla.api.low.model.ChargeState;
import com.ansill.utility.Utility;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.measure.Quantity;
import javax.measure.quantity.ElectricCurrent;
import javax.measure.quantity.ElectricPotential;
import java.time.Duration;

/** Charge session */
@Immutable
public final class ChargeSession{

  /** Requested charging current */
  @Nonnull
  private final Quantity<ElectricCurrent> requestedChargingCurrent;

  /** Maximum allowed current allowed by the charging provider */
  @Nonnull
  private final Quantity<ElectricCurrent> maximumAllowedChargingCurrent;

  /** Actual charge current */
  @Nonnull
  private final Quantity<ElectricCurrent> actualChargeCurrent;

  /** Actual charge potential (voltage) */
  @Nonnull
  private final Quantity<ElectricPotential> actualChargePotential;

  /** Charge connector type */
  @Nonnull
  private final String chargerConnectorType;

  /** Duration to full charge */
  @Nonnull
  private final Duration durationToFullCharge;

  /**
   * ChargeSession constructor
   *
   * @param requestedChargingCurrent      Requested charging current
   * @param maximumAllowedChargingCurrent Maximum allowed current allowed by the charging provider
   * @param actualChargeCurrent           Actual charge current
   * @param actualChargePotential         Actual charge voltage
   * @param chargerConnectorType          Charge connector type
   * @param durationToFullCharge          Duration to full charge
   */
  private ChargeSession(
    @Nonnull Quantity<ElectricCurrent> requestedChargingCurrent,
    @Nonnull Quantity<ElectricCurrent> maximumAllowedChargingCurrent,
    @Nonnull Quantity<ElectricCurrent> actualChargeCurrent,
    @Nonnull Quantity<ElectricPotential> actualChargePotential,
    @Nonnull String chargerConnectorType,
    @Nonnull Duration durationToFullCharge
  ){
    this.requestedChargingCurrent = requestedChargingCurrent;
    this.maximumAllowedChargingCurrent = maximumAllowedChargingCurrent;
    this.actualChargeCurrent = actualChargeCurrent;
    this.actualChargePotential = actualChargePotential;
    this.chargerConnectorType = chargerConnectorType;
    this.durationToFullCharge = durationToFullCharge;
  }

  /**
   * Converts medium-level ChargeState to high-level ChargeSession
   *
   * @param state medium-level ChargeState
   * @return ChargeSession
   */
  @Nonnull
  public static ChargeSession convert(@Nonnull ChargeState state){
    return new ChargeSession(
      state.getChargeCurrentRequest(),
      state.getChargeCurrentRequestMax(),
      state.getChargerActualCurrent(),
      state.getChargerVoltage(),
      state.getConnChargeCable(), // TODO Change so it'll work with both fastcharger and regualar
      state.getDurationToFullCharge()
    );
  }

  /**
   * Returns the requested charging current
   *
   * @return current
   */
  @Nonnull
  public Quantity<ElectricCurrent> getRequestedChargingCurrent(){
    return requestedChargingCurrent;
  }

  /**
   * Returns the maximum allowed charging current
   *
   * @return current
   */
  @Nonnull
  public Quantity<ElectricCurrent> getMaximumAllowedChargingCurrent(){
    return maximumAllowedChargingCurrent;
  }

  /**
   * Returns the actual charging current
   *
   * @return current
   */
  @Nonnull
  public Quantity<ElectricCurrent> getActualChargeCurrent(){
    return actualChargeCurrent;
  }

  /**
   * Returns the potential (voltage)
   *
   * @return electric potential
   */
  @Nonnull
  public Quantity<ElectricPotential> getActualChargePotential(){
    return actualChargePotential;
  }

  /**
   * Returns the connector type
   *
   * @return type
   */
  @Nonnull
  public String getChargerConnectorType(){
    return chargerConnectorType;
  }

  /**
   * Returns the duration to full charge
   *
   * @return duration
   */
  @Nonnull
  public Duration getDurationToFullCharge(){
    return durationToFullCharge;
  }

  @Override
  public String toString(){
    return Utility.simpleToString(this);
  }
}
