package com.ansill.tesla.api.raw.model;

import javax.annotation.concurrent.Immutable;

import static com.ansill.utility.Utility.simpleToString;

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

  @Override
  public boolean equals(Object o){
    if(this == o) return true;
    if(o == null || getClass() != o.getClass()) return false;

    GuiSettings that = (GuiSettings) o;

    if(gui_24_hour_time != that.gui_24_hour_time) return false;
    if(timestamp != that.timestamp) return false;
    if(show_range_units != that.show_range_units) return false;
    if(!gui_charge_rate_units.equals(that.gui_charge_rate_units)) return false;
    if(!gui_distance_units.equals(that.gui_distance_units)) return false;
    if(!gui_range_display.equals(that.gui_range_display)) return false;
    return gui_temperature_units.equals(that.gui_temperature_units);
  }

  @Override
  public int hashCode(){
    int result = (gui_24_hour_time ? 1 : 0);
    result = 31 * result + gui_charge_rate_units.hashCode();
    result = 31 * result + gui_distance_units.hashCode();
    result = 31 * result + gui_range_display.hashCode();
    result = 31 * result + gui_temperature_units.hashCode();
    result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
    result = 31 * result + (show_range_units ? 1 : 0);
    return result;
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
