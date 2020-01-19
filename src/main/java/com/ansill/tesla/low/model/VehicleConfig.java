package com.ansill.tesla.low.model;

import javax.annotation.concurrent.Immutable;

import static com.ansill.tesla.utility.Utility.simpleToString;

@SuppressWarnings("unused")
@Immutable
public class VehicleConfig{

    private final boolean can_accept_navigation_requests;
    private final boolean can_actuate_trunks;
    private final String car_special_type;
    private final String car_type;
    private final String charge_port_type;
    private final boolean eu_vehicle;
    private final String exterior_color;
    private final boolean has_air_suspension;
    private final boolean has_ludicrous_mode;
    private final int key_version;
    private final boolean motorized_charge_port;
    private final String perf_config;
    private final boolean plg;
    private final int rear_seat_heaters;
    private final int rear_seat_type;
    private final boolean rhd;
    private final String roof_color;
    private final int seat_type;
    private final String spoiler_type;
    private final int sun_roof_installed;
    private final String third_row_seats;
    private final long timestamp;
    private final String trim_badging;
    private final String wheel_type;
    private final boolean use_range_badging;

    public VehicleConfig(
            boolean can_accept_navigation_requests,
            boolean can_actuate_trunks,
            String car_special_type,
            String car_type,
            String charge_port_type,
            boolean eu_vehicle,
            String exterior_color,
            boolean has_air_suspension,
            boolean has_ludicrous_mode,
            int key_version,
            boolean motorized_charge_port,
            String perf_config,
            boolean plg,
            int rear_seat_heaters,
            int rear_seat_type,
            boolean rhd,
            String roof_color,
            int seat_type,
            String spoiler_type,
            int sun_roof_installed,
            String third_row_seats,
            long timestamp,
            String trim_badging,
            String wheel_type,
            boolean use_range_badging
    ){
        this.can_accept_navigation_requests = can_accept_navigation_requests;
        this.can_actuate_trunks = can_actuate_trunks;
        this.car_special_type = car_special_type;
        this.car_type = car_type;
        this.charge_port_type = charge_port_type;
        this.eu_vehicle = eu_vehicle;
        this.exterior_color = exterior_color;
        this.has_air_suspension = has_air_suspension;
        this.has_ludicrous_mode = has_ludicrous_mode;
        this.key_version = key_version;
        this.motorized_charge_port = motorized_charge_port;
        this.perf_config = perf_config;
        this.plg = plg;
        this.rear_seat_heaters = rear_seat_heaters;
        this.rear_seat_type = rear_seat_type;
        this.rhd = rhd;
        this.roof_color = roof_color;
        this.seat_type = seat_type;
        this.spoiler_type = spoiler_type;
        this.sun_roof_installed = sun_roof_installed;
        this.third_row_seats = third_row_seats;
        this.timestamp = timestamp;
        this.trim_badging = trim_badging;
        this.wheel_type = wheel_type;
        this.use_range_badging = use_range_badging;
    }

    public boolean isUseRangeBadging(){
        return use_range_badging;
    }

    public boolean isCanAcceptNavigationRequests(){
        return can_accept_navigation_requests;
    }

    public boolean isCanActuateTrunks(){
        return can_actuate_trunks;
    }

    public String getCarSpecialType(){
        return car_special_type;
    }

    public String getCarType(){
        return car_type;
    }

    public String getChargePortType(){
        return charge_port_type;
    }

    public boolean getEuVehicle(){
        return eu_vehicle;
    }

    public String getExteriorColor(){
        return exterior_color;
    }

    public boolean getHasAirSuspension(){
        return has_air_suspension;
    }

    public boolean getHasLudicrousMode(){
        return has_ludicrous_mode;
    }

    public int getKeyVersion(){
        return key_version;
    }

    public boolean getMotorizedChargePort(){
        return motorized_charge_port;
    }

    public String getPerfConfig(){
        return perf_config;
    }

    public boolean isPlg(){
        return plg;
    }

    public int getRearSeatHeaters(){
        return rear_seat_heaters;
    }

    public int getRearSeatType(){
        return rear_seat_type;
    }

    public boolean getRhd(){
        return rhd;
    }

    public String getRoofColor(){
        return roof_color;
    }

    public int getSeatType(){
        return seat_type;
    }

    public String getSpoilerType(){
        return spoiler_type;
    }

    public int getSunRoofInstalled(){
        return sun_roof_installed;
    }

    public String getThirdRowSeats(){
        return third_row_seats;
    }

    public long getTimestamp(){
        return timestamp;
    }

    public String getTrimBadging(){
        return trim_badging;
    }

    public String getWheelType(){
        return wheel_type;
    }

    @Override
    public String toString(){
        return simpleToString(this);
    }
}
