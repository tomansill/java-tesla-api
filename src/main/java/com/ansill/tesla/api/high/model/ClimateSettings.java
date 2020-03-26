package com.ansill.tesla.api.high.model;

import com.ansill.tesla.api.utility.Utility;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.measure.Quantity;
import javax.measure.quantity.Temperature;

/** Climate Settings */
@Immutable
public final class ClimateSettings{

  /** Temperature for driver's side */
  @Nonnull
  private final Quantity<Temperature> driverTemperature;

  /** Temperature for passenger's side */
  @Nonnull
  private final Quantity<Temperature> passengerTemperature;

  /** Maximum available temperature allowed by the car */
  @Nonnull
  private final Quantity<Temperature> maximumAvailableTemperature;

  /** Minimum available temperature allowed by the car */
  @Nonnull
  private final Quantity<Temperature> minimumAvailableTemperature;

  /** Seat heater settings */
  @Nonnull
  private final SeatHeaterSettings seatHeaterSettings;

  // TODO right and left temp direction

  /**
   * ClimateSettings constructor
   *
   * @param driverTemperature           Temperature for driver's side
   * @param passengerTemperature        Temperature for passenger's side
   * @param maximumAvailableTemperature Maximum available temperature allowed
   * @param minimumAvailableTemperature Minimum available temperature allowed
   * @param seatHeaterSettings          Seat heater settings
   */
  private ClimateSettings(
    @Nonnull Quantity<Temperature> driverTemperature,
    @Nonnull Quantity<Temperature> passengerTemperature,
    @Nonnull Quantity<Temperature> maximumAvailableTemperature,
    @Nonnull Quantity<Temperature> minimumAvailableTemperature,
    @Nonnull SeatHeaterSettings seatHeaterSettings
  ){
    this.driverTemperature = driverTemperature;
    this.passengerTemperature = passengerTemperature;
    this.maximumAvailableTemperature = maximumAvailableTemperature;
    this.minimumAvailableTemperature = minimumAvailableTemperature;
    this.seatHeaterSettings = seatHeaterSettings;
  }

  /**
   * Converts medium-level to high-level object
   *
   * @param state medium level object
   * @return high-level object
   */
  @Nonnull
  public static ClimateSettings convert(@Nonnull com.ansill.tesla.api.med.model.ClimateState state){
    return new ClimateSettings(
      state.getDriverTempSetting(),
      state.getPassengerTempSetting(),
      state.getMaxAvailTemp(),
      state.getMinAvailTemp(),
      SeatHeaterSettings.convert(state)
    );
  }

  /**
   * Temperature for driver's side
   *
   * @return temperature
   */
  @Nonnull
  public Quantity<Temperature> getDriverTemperature(){
    return driverTemperature;
  }

  /**
   * Temperature for passenger's side
   *
   * @return temperature
   */
  @Nonnull
  public Quantity<Temperature> getPassengerTemperature(){
    return passengerTemperature;
  }

  /**
   * Maximum available temperature allowed
   *
   * @return temperature
   */
  @Nonnull
  public Quantity<Temperature> getMaximumAvailableTemperature(){
    return maximumAvailableTemperature;
  }

  /**
   * Minimum available temperature allowed
   *
   * @return temperature
   */
  @Nonnull
  public Quantity<Temperature> getMinimumAvailableTemperature(){
    return minimumAvailableTemperature;
  }

  /**
   * Seat heater settings
   *
   * @return settinsg
   */
  @Nonnull
  public SeatHeaterSettings getSeatHeaterSettings(){
    return seatHeaterSettings;
  }

  @Override
  public String toString(){
    return Utility.simpleToString(this);
  }
}
