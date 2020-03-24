package com.ansill.tesla.api.low.model;

import javax.annotation.concurrent.Immutable;

import static com.ansill.tesla.api.utility.Utility.simpleToString;

@SuppressWarnings("unused")
@Immutable
public final class GuiSettings{

  private final boolean gui_24_hour_time;

  private final String gui_charge_rate_units;

  private final String gui_distance_units;

  private final String gui_range_display;

  private final String gui_temperature_units;

  private final long timestamp;

  private final boolean show_range_units;

  public GuiSettings(
    boolean gui_24_hour_time,
    String gui_charge_rate_units,
    String gui_distance_units,
    String gui_range_display,
    String gui_temperature_units,
    long timestamp,
    boolean show_range_units
  ){
    this.gui_24_hour_time = gui_24_hour_time;
    this.gui_charge_rate_units = gui_charge_rate_units;
    this.gui_distance_units = gui_distance_units;
    this.gui_range_display = gui_range_display;
    this.gui_temperature_units = gui_temperature_units;
    this.timestamp = timestamp;
    this.show_range_units = show_range_units;
  }

  public boolean isShowRangeUnits(){
    return show_range_units;
  }

  public boolean getGui24HourTime(){
    return gui_24_hour_time;
  }

  public String getGuiChargeRateUnits(){
    return gui_charge_rate_units;
  }

  public String getGuiDistanceUnits(){
    return gui_distance_units;
  }

  public String getGuiRangeDisplay(){
    return gui_range_display;
  }

  public String getGuiTemperatureUnits(){
    return gui_temperature_units;
  }

  public long getTimestamp(){
    return timestamp;
  }

  @Override
  public String toString(){
    return simpleToString(this);
  }
}
