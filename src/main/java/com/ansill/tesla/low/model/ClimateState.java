package com.ansill.tesla.low.model;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import static com.ansill.tesla.utility.Utility.simpleToString;

@SuppressWarnings("unused")
@Immutable
public class ClimateState{

    private final boolean battery_heater;
    private final boolean battery_heater_no_power;
    private final String climate_keeper_mode;
    private final double driver_temp_setting;
    private final int fan_status;
    private final String inside_temp;
    private final String is_auto_conditioning_on;
    private final boolean is_climate_on;
    private final boolean is_front_defroster_on;
    private final boolean is_preconditioning;
    private final boolean is_rear_defroster_on;
    private final String left_temp_direction;
    private final double max_avail_temp;
    private final double min_avail_temp;
    private final String outside_temp;
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

    public ClimateState(
            boolean battery_heater,
            boolean battery_heater_no_power,
            String climate_keeper_mode,
            double driver_temp_setting,
            int fan_status,
            String inside_temp,
            String is_auto_conditioning_on,
            boolean is_climate_on,
            boolean is_front_defroster_on,
            boolean is_preconditioning,
            boolean is_rear_defroster_on,
            String left_temp_direction,
            double max_avail_temp,
            double min_avail_temp,
            String outside_temp,
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
            @Nullable Boolean wiper_blade_heater
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
    }

    public boolean getBatteryHeader(){
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

    public String getInsideTemp(){
        return inside_temp;
    }

    public String getIsAutoConditioningOn(){
        return is_auto_conditioning_on;
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

    public String getOutsideTemp(){
        return outside_temp;
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

    @Nullable
    public Integer getSeatHeaterLeft(){
        return seat_heater_left;
    }

    @Nullable
    public Integer getSeatHeaterRear_center(){
        return seat_heater_rear_center;
    }

    @Nullable
    public Integer getSeatHeaterRearLeft(){
        return seat_heater_rear_left;
    }

    @Nullable
    public Integer getSeatHeaterLeftBack(){
        return seat_heater_left_back;
    }

    @Nullable
    public Integer getSeatHeaterRearRight(){
        return seat_heater_rear_right;
    }

    @Nullable
    public Integer getSeatHeaterRightBack(){
        return seat_heater_right_back;
    }

    @Nullable
    public Integer getSeatHeaterRight(){
        return seat_heater_right;
    }

    @Nullable
    public Boolean getSideMirrorHeaters(){
        return side_mirror_heaters;
    }

    @Nullable
    public Boolean getSmartPreconditioning(){
        return smart_preconditioning;
    }

    @Nullable
    public Boolean getSteeringWheelHeater(){
        return steering_wheel_heater;
    }

    public long getTimestamp(){
        return timestamp;
    }

    @Nullable
    public Boolean getWiperBladeHeater(){
        return wiper_blade_heater;
    }

    @Override
    public String toString(){
        return simpleToString(this);
    }
}
