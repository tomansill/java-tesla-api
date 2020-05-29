package com.ansill.tesla.api.low.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.util.Objects;
import java.util.Optional;

import static com.ansill.utility.Utility.simpleToString;

@SuppressWarnings("unused")
@Immutable
public final class ClimateState{

  private final boolean battery_heater;

  private final boolean battery_heater_no_power;

  private final String climate_keeper_mode;

  private final double driver_temp_setting;

  private final int fan_status;

  @Nullable
  private final Double inside_temp;

  @Nullable
  private final Boolean is_auto_conditioning_on;

  private final boolean is_climate_on;

  private final boolean is_front_defroster_on;

  private final boolean is_preconditioning;

  private final boolean is_rear_defroster_on;

  private final String left_temp_direction;

  private final double max_avail_temp;

  private final double min_avail_temp;

  @Nullable
  private final Double outside_temp;

  private final double passenger_temp_setting;

  private final boolean remote_heater_control_enabled;

  private final String right_temp_direction;

  @Nullable
  private final Integer seat_heater_left; // Entry could disapppear from JSON

  @Nullable
  private final Integer seat_heater_rear_center; // Entry could disapppear from JSON

  @Nullable
  private final Integer seat_heater_rear_left; // Entry could disapppear from JSON

  @Nullable
  private final Integer seat_heater_left_back; // Entry could disapppear from JSON

  @Nullable
  private final Integer seat_heater_rear_right; // Entry could disapppear from JSON

  @Nullable
  private final Integer seat_heater_right_back; // Entry could disapppear from JSON

  @Nullable
  private final Integer seat_heater_right; // Entry could disapppear from JSON

  @Nullable
  private final Boolean side_mirror_heaters; // Entry could disappear from JSON

  @Nullable
  private final Boolean smart_preconditioning; // Entry could disappear from JSON

  @Nullable
  private final Boolean steering_wheel_heater; // Entry could disappear from JSON

  private final long timestamp;

  @Nullable
  private final Boolean wiper_blade_heater; // Entry could disappear from JSON

  private final int defrost_mode;

  @Override
  public boolean equals(Object o){
    if(this == o) return true;
    if(o == null || getClass() != o.getClass()) return false;

    ClimateState that = (ClimateState) o;

    if(battery_heater != that.battery_heater) return false;
    if(battery_heater_no_power != that.battery_heater_no_power) return false;
    if(Double.compare(that.driver_temp_setting, driver_temp_setting) != 0) return false;
    if(fan_status != that.fan_status) return false;
    if(is_climate_on != that.is_climate_on) return false;
    if(is_front_defroster_on != that.is_front_defroster_on) return false;
    if(is_preconditioning != that.is_preconditioning) return false;
    if(is_rear_defroster_on != that.is_rear_defroster_on) return false;
    if(Double.compare(that.max_avail_temp, max_avail_temp) != 0) return false;
    if(Double.compare(that.min_avail_temp, min_avail_temp) != 0) return false;
    if(Double.compare(that.passenger_temp_setting, passenger_temp_setting) != 0) return false;
    if(remote_heater_control_enabled != that.remote_heater_control_enabled) return false;
    if(timestamp != that.timestamp) return false;
    if(defrost_mode != that.defrost_mode) return false;
    if(!climate_keeper_mode.equals(that.climate_keeper_mode)) return false;
    if(!Objects.equals(inside_temp, that.inside_temp)) return false;
    if(!Objects.equals(is_auto_conditioning_on, that.is_auto_conditioning_on)) return false;
    if(!left_temp_direction.equals(that.left_temp_direction)) return false;
    if(!Objects.equals(outside_temp, that.outside_temp)) return false;
    if(!right_temp_direction.equals(that.right_temp_direction)) return false;
    if(!Objects.equals(seat_heater_left, that.seat_heater_left)) return false;
    if(!Objects.equals(seat_heater_rear_center, that.seat_heater_rear_center)) return false;
    if(!Objects.equals(seat_heater_rear_left, that.seat_heater_rear_left)) return false;
    if(!Objects.equals(seat_heater_left_back, that.seat_heater_left_back)) return false;
    if(!Objects.equals(seat_heater_rear_right, that.seat_heater_rear_right)) return false;
    if(!Objects.equals(seat_heater_right_back, that.seat_heater_right_back)) return false;
    if(!Objects.equals(seat_heater_right, that.seat_heater_right)) return false;
    if(!Objects.equals(side_mirror_heaters, that.side_mirror_heaters)) return false;
    if(!Objects.equals(smart_preconditioning, that.smart_preconditioning)) return false;
    if(!Objects.equals(steering_wheel_heater, that.steering_wheel_heater)) return false;
    return Objects.equals(wiper_blade_heater, that.wiper_blade_heater);
  }

  @Override
  public int hashCode(){
    int result;
    long temp;
    result = (battery_heater ? 1 : 0);
    result = 31 * result + (battery_heater_no_power ? 1 : 0);
    result = 31 * result + climate_keeper_mode.hashCode();
    temp = Double.doubleToLongBits(driver_temp_setting);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + fan_status;
    result = 31 * result + Objects.hashCode(inside_temp);
    result = 31 * result + Objects.hashCode(is_auto_conditioning_on);
    result = 31 * result + (is_climate_on ? 1 : 0);
    result = 31 * result + (is_front_defroster_on ? 1 : 0);
    result = 31 * result + (is_preconditioning ? 1 : 0);
    result = 31 * result + (is_rear_defroster_on ? 1 : 0);
    result = 31 * result + left_temp_direction.hashCode();
    temp = Double.doubleToLongBits(max_avail_temp);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(min_avail_temp);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + Objects.hashCode(outside_temp);
    temp = Double.doubleToLongBits(passenger_temp_setting);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + (remote_heater_control_enabled ? 1 : 0);
    result = 31 * result + right_temp_direction.hashCode();
    result = 31 * result + Objects.hashCode(seat_heater_left);
    result = 31 * result + Objects.hashCode(seat_heater_rear_center);
    result = 31 * result + Objects.hashCode(seat_heater_rear_left);
    result = 31 * result + Objects.hashCode(seat_heater_left_back);
    result = 31 * result + Objects.hashCode(seat_heater_rear_right);
    result = 31 * result + Objects.hashCode(seat_heater_right_back);
    result = 31 * result + Objects.hashCode(seat_heater_right);
    result = 31 * result + Objects.hashCode(side_mirror_heaters);
    result = 31 * result + Objects.hashCode(smart_preconditioning);
    result = 31 * result + Objects.hashCode(steering_wheel_heater);
    result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
    result = 31 * result + Objects.hashCode(wiper_blade_heater);
    result = 31 * result + defrost_mode;
    return result;
  }

  public ClimateState(
    boolean battery_heater,
    boolean battery_heater_no_power,
    String climate_keeper_mode,
    double driver_temp_setting,
    int fan_status,
    @Nullable Double inside_temp,
    @Nullable Boolean is_auto_conditioning_on,
    boolean is_climate_on,
    boolean is_front_defroster_on,
    boolean is_preconditioning,
    boolean is_rear_defroster_on,
    String left_temp_direction,
    double max_avail_temp,
    double min_avail_temp,
    @Nullable Double outside_temp,
    double passenger_temp_setting,
    boolean remote_heater_control_enabled,
    String right_temp_direction,
    @Nullable Integer seat_heater_left,
    @Nullable Integer seat_heater_rear_center,
    @Nullable Integer seat_heater_rear_left,
    @Nullable Integer seat_heater_left_back,
    @Nullable Integer seat_heater_rear_right,
    @Nullable Integer seat_heater_right_back,
    @Nullable Integer seat_heater_right,
    @Nullable Boolean side_mirror_heaters,
    @Nullable Boolean smart_preconditioning,
    @Nullable Boolean steering_wheel_heater,
    long timestamp,
    @Nullable Boolean wiper_blade_heater,
    int defrost_mode
  ){
    this.battery_heater = battery_heater;
    this.battery_heater_no_power = battery_heater_no_power;
    this.climate_keeper_mode = climate_keeper_mode;
    this.driver_temp_setting = driver_temp_setting;
    this.fan_status = fan_status;
    this.inside_temp = inside_temp;
    this.is_auto_conditioning_on = is_auto_conditioning_on;
    this.is_climate_on = is_climate_on;
    this.is_front_defroster_on = is_front_defroster_on;
    this.is_preconditioning = is_preconditioning;
    this.is_rear_defroster_on = is_rear_defroster_on;
    this.left_temp_direction = left_temp_direction;
    this.max_avail_temp = max_avail_temp;
    this.min_avail_temp = min_avail_temp;
    this.outside_temp = outside_temp;
    this.passenger_temp_setting = passenger_temp_setting;
    this.remote_heater_control_enabled = remote_heater_control_enabled;
    this.right_temp_direction = right_temp_direction;
    this.seat_heater_left = seat_heater_left;
    this.seat_heater_rear_center = seat_heater_rear_center;
    this.seat_heater_rear_left = seat_heater_rear_left;
    this.seat_heater_left_back = seat_heater_left_back;
    this.seat_heater_rear_right = seat_heater_rear_right;
    this.seat_heater_right_back = seat_heater_right_back;
    this.seat_heater_right = seat_heater_right;
    this.side_mirror_heaters = side_mirror_heaters;
    this.smart_preconditioning = smart_preconditioning;
    this.steering_wheel_heater = steering_wheel_heater;
    this.timestamp = timestamp;
    this.wiper_blade_heater = wiper_blade_heater;
    this.defrost_mode = defrost_mode;
  }

  public int getDefrostMode(){
    return defrost_mode;
  }

  public boolean getBatteryHeater(){
    return battery_heater;
  }

  public boolean getBatteryHeaterNoPower(){
    return battery_heater_no_power;
  }

  public String getClimateKeeperMode(){
    return climate_keeper_mode;
  }

  public double getDriverTempSetting(){
    return driver_temp_setting;
  }

  public int getFanStatus(){
    return fan_status;
  }

  @Nonnull
  public Optional<Double> getInsideTemp(){
    return Optional.ofNullable(inside_temp);
  }

  public Optional<Boolean> getIsAutoConditioningOn(){
    return Optional.ofNullable(is_auto_conditioning_on);
  }

  public boolean getIsClimateOn(){
    return is_climate_on;
  }

  public boolean getIsFrontDefrosterOn(){
    return is_front_defroster_on;
  }

  public boolean getIsPreconditioning(){
    return is_preconditioning;
  }

  public boolean getIsRearDefrosterOn(){
    return is_rear_defroster_on;
  }

  public String getLeftTempDirection(){
    return left_temp_direction;
  }

  public double getMaxAvailTemp(){
    return max_avail_temp;
  }

  public double getMinAvailTemp(){
    return min_avail_temp;
  }

  @Nonnull
  public Optional<Double> getOutsideTemp(){
    return Optional.ofNullable(outside_temp);
  }

  public double getPassengerTempSetting(){
    return passenger_temp_setting;
  }

  public boolean getRemoteHeaterControlEnabled(){
    return remote_heater_control_enabled;
  }

  public String getRightTempDirection(){
    return right_temp_direction;
  }

  @Nonnull
  public Optional<Integer> getSeatHeaterLeft(){
    return Optional.ofNullable(seat_heater_left);
  }

  @Nonnull
  public Optional<Integer> getSeatHeaterRearCenter(){
    return Optional.ofNullable(seat_heater_rear_center);
  }

  @Nonnull
  public Optional<Integer> getSeatHeaterRearLeft(){
    return Optional.ofNullable(seat_heater_rear_left);
  }

  @Nonnull
  public Optional<Integer> getSeatHeaterLeftBack(){
    return Optional.ofNullable(seat_heater_left_back);
  }

  @Nonnull
  public Optional<Integer> getSeatHeaterRearRight(){
    return Optional.ofNullable(seat_heater_rear_right);
  }

  @Nonnull
  public Optional<Integer> getSeatHeaterRightBack(){
    return Optional.ofNullable(seat_heater_right_back);
  }

  @Nonnull
  public Optional<Integer> getSeatHeaterRight(){
    return Optional.ofNullable(seat_heater_right);
  }

  @Nonnull
  public Optional<Boolean> getSideMirrorHeaters(){
    return Optional.ofNullable(side_mirror_heaters);
  }

  @Nonnull
  public Optional<Boolean> getSmartPreconditioning(){
    return Optional.ofNullable(smart_preconditioning);
  }

  @Nonnull
  public Optional<Boolean> getSteeringWheelHeater(){
    return Optional.ofNullable(steering_wheel_heater);
  }

  public long getTimestamp(){
    return timestamp;
  }

  @Nonnull
  public Optional<Boolean> getWiperBladeHeater(){
    return Optional.ofNullable(wiper_blade_heater);
  }

  @Override
  public String toString(){
    return simpleToString(this);
  }
}
