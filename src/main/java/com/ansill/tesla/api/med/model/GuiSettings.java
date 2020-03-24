package com.ansill.tesla.api.med.model;

import com.ansill.tesla.api.utility.UnitUtility;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.measure.Unit;
import javax.measure.quantity.Speed;
import javax.measure.quantity.Temperature;
import java.time.Instant;

@Immutable
public final class GuiSettings{

  private final boolean use24HoursTime;

  @Nonnull
  private final Unit<Speed> chargeRateUnits;

  @Nonnull
  private final Unit<Speed> distanceUnits;

  @Nonnull
  private final Display rangeDisplay; // Find all possible enums one of them is "Rated"

  @Nonnull
  private final Unit<Temperature> temperatureUnits;

  @Nonnull
  private final Instant timestamp;

  private final boolean showRangeUnits;

  public GuiSettings(
    boolean use24HoursTime,
    @Nonnull Unit<Speed> chargeRateUnits,
    @Nonnull Unit<Speed> distanceUnits,
    @Nonnull Display rangeDisplay,
    @Nonnull Unit<Temperature> temperatureUnits,
    @Nonnull Instant timestamp,
    boolean showRangeUnits
  ){
    this.use24HoursTime = use24HoursTime;
    this.chargeRateUnits = chargeRateUnits;
    this.distanceUnits = distanceUnits;
    this.rangeDisplay = rangeDisplay;
    this.temperatureUnits = temperatureUnits;
    this.timestamp = timestamp;
    this.showRangeUnits = showRangeUnits;
  }

  @Nonnull
  public static GuiSettings convert(@Nonnull com.ansill.tesla.api.low.model.GuiSettings settings){
    return new GuiSettings(
      settings.getGui24HourTime(),
      UnitUtility.getSpeedUnit(settings.getGuiChargeRateUnits()),
      UnitUtility.getSpeedUnit(settings.getGuiDistanceUnits()),
      Display.valueOf(settings.getGuiRangeDisplay().toUpperCase()),
      UnitUtility.getTemperatureUnit(settings.getGuiTemperatureUnits()),
      Instant.ofEpochSecond(settings.getTimestamp()),
      settings.isShowRangeUnits()
    );
  }

  public boolean isUsing24HoursTime(){
    return use24HoursTime;
  }

  @Nonnull
  public Unit<Speed> getChargeRateUnits(){
    return chargeRateUnits;
  }

  @Nonnull
  public Unit<Speed> getDistanceUnits(){
    return distanceUnits;
  }

  @Nonnull
  public Display getRangeDisplay(){
    return rangeDisplay;
  }

  @Nonnull
  public Unit<Temperature> getTemperatureUnits(){
    return temperatureUnits;
  }

  @Nonnull
  public Instant getTimestamp(){
    return timestamp;
  }

  public boolean isShowingRangeUnits(){
    return showRangeUnits;
  }

  public enum Display{
    RATED
  }

}
