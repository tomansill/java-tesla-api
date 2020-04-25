package com.ansill.tesla.api.low.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.util.Optional;

import static com.ansill.tesla.api.utility.Utility.simpleToString;

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
