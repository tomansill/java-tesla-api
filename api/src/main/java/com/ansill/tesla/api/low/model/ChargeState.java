package com.ansill.tesla.api.low.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.util.Objects;
import java.util.Optional;

import static com.ansill.utility.Utility.simpleToString;

@SuppressWarnings("unused")
@Immutable
public final class ChargeState{
  private final boolean battery_heater_on;

  private final int battery_level;

  private final double battery_range;

  private final int charge_current_request;

  private final int charge_current_request_max;

  private final boolean charge_enable_request;

  private final double charge_energy_added;

  private final int charge_limit_soc;

  private final int charge_limit_soc_max;

  private final int charge_limit_soc_min;

  private final int charge_limit_soc_std;

  private final double charge_miles_added_ideal;

  private final double charge_miles_added_rated;

  private final boolean charge_port_cold_weather_mode;

  private final boolean charge_port_door_open;

  private final String charge_port_latch;

  private final double charge_rate;

  private final boolean charge_to_max_range;

  private final int charger_actual_current;

  private final String charger_phases;

  private final int charger_pilot_current;

  private final int charger_power;

  private final int charger_voltage;

  private final String charging_state;

  private final String conn_charge_cable;

  private final double est_battery_range;

  private final String fast_charger_brand;

  private final boolean fast_charger_present;

  private final long minutes_to_full_charge;

  private final String fast_charger_type;

  private final double ideal_battery_range;

  private final boolean managed_charging_active;

  private final String managed_charging_start_time;

  private final boolean managed_charging_user_canceled;

  private final int max_range_charge_counter;

  private final Boolean not_enough_power_to_heat;

  private final boolean scheduled_charging_pending;

  @Nullable
  private final String scheduled_charging_start_time;

  private final double time_to_full_charge;

  private final long timestamp;

  private final int usable_battery_level;

  private final String user_charge_enable_request;

  private final boolean trip_charging;

  @Override
  public boolean equals(Object o){
    if(this == o) return true;
    if(!(o instanceof ChargeState that)) return false;

    if(battery_heater_on != that.battery_heater_on) return false;
    if(battery_level != that.battery_level) return false;
    if(Double.compare(that.battery_range, battery_range) != 0) return false;
    if(charge_current_request != that.charge_current_request) return false;
    if(charge_current_request_max != that.charge_current_request_max) return false;
    if(charge_enable_request != that.charge_enable_request) return false;
    if(Double.compare(that.charge_energy_added, charge_energy_added) != 0) return false;
    if(charge_limit_soc != that.charge_limit_soc) return false;
    if(charge_limit_soc_max != that.charge_limit_soc_max) return false;
    if(charge_limit_soc_min != that.charge_limit_soc_min) return false;
    if(charge_limit_soc_std != that.charge_limit_soc_std) return false;
    if(Double.compare(that.charge_miles_added_ideal, charge_miles_added_ideal) != 0) return false;
    if(Double.compare(that.charge_miles_added_rated, charge_miles_added_rated) != 0) return false;
    if(charge_port_cold_weather_mode != that.charge_port_cold_weather_mode) return false;
    if(charge_port_door_open != that.charge_port_door_open) return false;
    if(Double.compare(that.charge_rate, charge_rate) != 0) return false;
    if(charge_to_max_range != that.charge_to_max_range) return false;
    if(charger_actual_current != that.charger_actual_current) return false;
    if(charger_pilot_current != that.charger_pilot_current) return false;
    if(charger_power != that.charger_power) return false;
    if(charger_voltage != that.charger_voltage) return false;
    if(Double.compare(that.est_battery_range, est_battery_range) != 0) return false;
    if(fast_charger_present != that.fast_charger_present) return false;
    if(minutes_to_full_charge != that.minutes_to_full_charge) return false;
    if(Double.compare(that.ideal_battery_range, ideal_battery_range) != 0) return false;
    if(managed_charging_active != that.managed_charging_active) return false;
    if(managed_charging_user_canceled != that.managed_charging_user_canceled) return false;
    if(max_range_charge_counter != that.max_range_charge_counter) return false;
    if(scheduled_charging_pending != that.scheduled_charging_pending) return false;
    if(Double.compare(that.time_to_full_charge, time_to_full_charge) != 0) return false;
    if(timestamp != that.timestamp) return false;
    if(usable_battery_level != that.usable_battery_level) return false;
    if(trip_charging != that.trip_charging) return false;
    if(!charge_port_latch.equals(that.charge_port_latch)) return false;
    if(!charger_phases.equals(that.charger_phases)) return false;
    if(!charging_state.equals(that.charging_state)) return false;
    if(!conn_charge_cable.equals(that.conn_charge_cable)) return false;
    if(!fast_charger_brand.equals(that.fast_charger_brand)) return false;
    if(!fast_charger_type.equals(that.fast_charger_type)) return false;
    if(!managed_charging_start_time.equals(that.managed_charging_start_time)) return false;
    if(!not_enough_power_to_heat.equals(that.not_enough_power_to_heat)) return false;
    if(!Objects.equals(scheduled_charging_start_time, that.scheduled_charging_start_time)) return false;
    return user_charge_enable_request.equals(that.user_charge_enable_request);
  }

  @Override
  public int hashCode(){
    int result;
    long temp;
    result = (battery_heater_on ? 1 : 0);
    result = 31 * result + battery_level;
    temp = Double.doubleToLongBits(battery_range);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + charge_current_request;
    result = 31 * result + charge_current_request_max;
    result = 31 * result + (charge_enable_request ? 1 : 0);
    temp = Double.doubleToLongBits(charge_energy_added);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + charge_limit_soc;
    result = 31 * result + charge_limit_soc_max;
    result = 31 * result + charge_limit_soc_min;
    result = 31 * result + charge_limit_soc_std;
    temp = Double.doubleToLongBits(charge_miles_added_ideal);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(charge_miles_added_rated);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + (charge_port_cold_weather_mode ? 1 : 0);
    result = 31 * result + (charge_port_door_open ? 1 : 0);
    result = 31 * result + charge_port_latch.hashCode();
    temp = Double.doubleToLongBits(charge_rate);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + (charge_to_max_range ? 1 : 0);
    result = 31 * result + charger_actual_current;
    result = 31 * result + charger_phases.hashCode();
    result = 31 * result + charger_pilot_current;
    result = 31 * result + charger_power;
    result = 31 * result + charger_voltage;
    result = 31 * result + charging_state.hashCode();
    result = 31 * result + conn_charge_cable.hashCode();
    temp = Double.doubleToLongBits(est_battery_range);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + fast_charger_brand.hashCode();
    result = 31 * result + (fast_charger_present ? 1 : 0);
    result = 31 * result + (int) (minutes_to_full_charge ^ (minutes_to_full_charge >>> 32));
    result = 31 * result + fast_charger_type.hashCode();
    temp = Double.doubleToLongBits(ideal_battery_range);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + (managed_charging_active ? 1 : 0);
    result = 31 * result + managed_charging_start_time.hashCode();
    result = 31 * result + (managed_charging_user_canceled ? 1 : 0);
    result = 31 * result + max_range_charge_counter;
    result = 31 * result + not_enough_power_to_heat.hashCode();
    result = 31 * result + (scheduled_charging_pending ? 1 : 0);
    result = 31 * result + Objects.hashCode(scheduled_charging_start_time);
    temp = Double.doubleToLongBits(time_to_full_charge);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
    result = 31 * result + usable_battery_level;
    result = 31 * result + user_charge_enable_request.hashCode();
    result = 31 * result + (trip_charging ? 1 : 0);
    return result;
  }

  public ChargeState(
    boolean battery_heater_on,
    int battery_level,
    double battery_range,
    int charge_current_request,
    int charge_current_request_max,
    boolean charge_enable_request,
    double charge_energy_added,
    int charge_limit_soc,
    int charge_limit_soc_max,
    int charge_limit_soc_min,
    int charge_limit_soc_std,
    double charge_miles_added_ideal,
    double charge_miles_added_rated,
    boolean charge_port_cold_weather_mode,
    boolean charge_port_door_open,
    String charge_port_latch,
    double charge_rate,
    boolean charge_to_max_range,
    int charger_actual_current,
    String charger_phases,
    int charger_pilot_current,
    int charger_power,
    int charger_voltage,
    String charging_state,
    String conn_charge_cable,
    double est_battery_range,
    String fast_charger_brand,
    boolean fast_charger_present,
    long minutes_to_full_charge, String fast_charger_type,
    double ideal_battery_range,
    boolean managed_charging_active,
    String managed_charging_start_time,
    boolean managed_charging_user_canceled,
    int max_range_charge_counter,
    Boolean not_enough_power_to_heat,
    boolean scheduled_charging_pending,
    @Nullable String scheduled_charging_start_time,
    double time_to_full_charge,
    long timestamp,
    int usable_battery_level,
    String user_charge_enable_request,
    boolean trip_charging
  ){
    this.battery_heater_on = battery_heater_on;
    this.battery_level = battery_level;
    this.battery_range = battery_range;
    this.charge_current_request = charge_current_request;
    this.charge_current_request_max = charge_current_request_max;
    this.charge_enable_request = charge_enable_request;
    this.charge_energy_added = charge_energy_added;
    this.charge_limit_soc = charge_limit_soc;
    this.charge_limit_soc_max = charge_limit_soc_max;
    this.charge_limit_soc_min = charge_limit_soc_min;
    this.charge_limit_soc_std = charge_limit_soc_std;
    this.charge_miles_added_ideal = charge_miles_added_ideal;
    this.charge_miles_added_rated = charge_miles_added_rated;
    this.charge_port_cold_weather_mode = charge_port_cold_weather_mode;
    this.charge_port_door_open = charge_port_door_open;
    this.charge_port_latch = charge_port_latch;
    this.charge_rate = charge_rate;
    this.charge_to_max_range = charge_to_max_range;
    this.charger_actual_current = charger_actual_current;
    this.charger_phases = charger_phases;
    this.charger_pilot_current = charger_pilot_current;
    this.charger_power = charger_power;
    this.charger_voltage = charger_voltage;
    this.charging_state = charging_state;
    this.conn_charge_cable = conn_charge_cable;
    this.est_battery_range = est_battery_range;
    this.fast_charger_brand = fast_charger_brand;
    this.fast_charger_present = fast_charger_present;
    this.minutes_to_full_charge = minutes_to_full_charge;
    this.fast_charger_type = fast_charger_type;
    this.ideal_battery_range = ideal_battery_range;
    this.managed_charging_active = managed_charging_active;
    this.managed_charging_start_time = managed_charging_start_time;
    this.managed_charging_user_canceled = managed_charging_user_canceled;
    this.max_range_charge_counter = max_range_charge_counter;
    this.not_enough_power_to_heat = not_enough_power_to_heat;
    this.scheduled_charging_pending = scheduled_charging_pending;
    this.scheduled_charging_start_time = scheduled_charging_start_time;
    this.time_to_full_charge = time_to_full_charge;
    this.timestamp = timestamp;
    this.usable_battery_level = usable_battery_level;
    this.user_charge_enable_request = user_charge_enable_request;
    this.trip_charging = trip_charging;
  }

  public boolean isTripCharging(){
    return trip_charging;
  }


  public long getMinutesToFullCharge(){
    return minutes_to_full_charge;
  }

  public double getBatteryRange(){
    return battery_range;
  }

  public int getChargeCurrentRequest(){
    return charge_current_request;
  }

  public int getChargeCurrentRequestMax(){
    return charge_current_request_max;
  }

  public boolean isChargeEnableRequest(){
    return charge_enable_request;
  }

  public double getChargeEnergyAdded(){
    return charge_energy_added;
  }

  public int getChargeLimitSoc(){
    return charge_limit_soc;
  }

  public int getChargeLimitSocMax(){
    return charge_limit_soc_max;
  }

  public int getChargeLimitSocMin(){
    return charge_limit_soc_min;
  }

  public int getChargeLimitSocStd(){
    return charge_limit_soc_std;
  }

  public double getChargeMilesAddedIdeal(){
    return charge_miles_added_ideal;
  }

  public double getChargeMilesAddedRated(){
    return charge_miles_added_rated;
  }

  public boolean getChargePortColdWeatherMode(){
    return charge_port_cold_weather_mode;
  }

  public boolean getChargePortDoorOpen(){
    return charge_port_door_open;
  }

  public String getChargePortLatch(){
    return charge_port_latch;
  }

  public double getChargeRate(){
    return charge_rate;
  }

  public boolean getChargeToMaxRange(){
    return charge_to_max_range;
  }

  public int getChargerActualCurrent(){
    return charger_actual_current;
  }

  public String getChargerPhases(){
    return charger_phases;
  }

  public int getChargePilotCurrent(){
    return charger_pilot_current;
  }

  public int getChargerPower(){
    return charger_power;
  }

  public int getChargerVoltage(){
    return charger_voltage;
  }

  public String getChargingState(){
    return charging_state;
  }

  public String getConnChargeCable(){
    return conn_charge_cable;
  }

  public double getEstBatteryRange(){
    return est_battery_range;
  }

  public String getFastChargerBrand(){
    return fast_charger_brand;
  }

  public boolean getFastChargerPresent(){
    return fast_charger_present;
  }

  public String getFastChargerType(){
    return fast_charger_type;
  }

  public double getIdealBatteryRange(){
    return ideal_battery_range;
  }

  public boolean getManagedChargingActive(){
    return managed_charging_active;
  }

  public String getManagedChargingStartTime(){
    return managed_charging_start_time;
  }

  public boolean getManagedChargingUserCanceled(){
    return managed_charging_user_canceled;
  }

  public int getMaxRangeChargerCounter(){
    return max_range_charge_counter;
  }

  @Nonnull
  public Optional<Boolean> getNotEnoughPowerToHeat(){
    return Optional.ofNullable(not_enough_power_to_heat);
  }

  public boolean getScheduledChargingPending(){
    return scheduled_charging_pending;
  }

  @Nonnull
  public Optional<String> getScheduledChargingStartTime(){
    return Optional.ofNullable(scheduled_charging_start_time);
  }

  public double getTimeToFullCharge(){
    return time_to_full_charge;
  }

  public long getTimestamp(){
    return timestamp;
  }

  public int getUsableBatteryLevel(){
    return usable_battery_level;
  }

  public String getUserChargeEnableRequest(){
    return user_charge_enable_request;
  }

  public int getBatteryLevel(){
    return battery_level;
  }

  public boolean getBatteryHeaterOn(){
    return battery_heater_on;
  }

  @Override
  public String toString(){
    return simpleToString(this);
  }
}
