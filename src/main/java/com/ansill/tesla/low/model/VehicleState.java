package com.ansill.tesla.low.model;


import javax.annotation.concurrent.Immutable;

@SuppressWarnings("unused")
@Immutable
public class VehicleState{

    private final int api_version;
    private final String autopark_state_v2;
    private final String autopark_style;
    private final boolean calendar_supported;
    private final String car_version;
    private final int center_display_state;
    private final int df;
    private final int dr;
    private final int ft;
    private final boolean homelink_nearby;
    private final boolean is_user_present;
    private final String last_autopark_error;
    private final boolean locked;
    private final MediaState media_state;
    private final boolean notifications_supported;
    private final double odometer;
    private final boolean parsed_calendar_supported;
    private final int pf;
    private final int pr;
    private final boolean remote_start;
    private final boolean remote_start_enabled;
    private final boolean remote_start_supported;
    private final int rt;
    private final boolean sentry_mode;
    private final SoftwareUpdate software_update;
    private final SpeedLimitMode speed_limit_mode;
    private final int sun_roof_percent_open;
    private final String sun_roof_state;
    private final long timestamp;
    private final boolean valet_mode;
    private final boolean valet_pin_needed;
    private final String vehicle_name;

    public VehicleState(
            int api_version,
            String autopark_state_v2,
            String autopark_style,
            boolean calendar_supported,
            String car_version,
            int center_display_state,
            int df,
            int dr,
            int ft,
            boolean homelink_nearby,
            boolean is_user_present,
            String last_autopark_error,
            boolean locked,
            MediaState media_state,
            boolean notifications_supported,
            double odometer,
            boolean parsed_calendar_supported,
            int pf,
            int pr,
            boolean remote_start,
            boolean remote_start_enabled,
            boolean remote_start_supported,
            int rt,
            boolean sentry_mode,
            SoftwareUpdate software_update,
            SpeedLimitMode speed_limit_mode,
            int sun_roof_percent_open,
            String sun_roof_state,
            long timestamp,
            boolean valet_mode,
            boolean valet_pin_needed,
            String vehicle_name
    ){
        this.api_version = api_version;
        this.autopark_state_v2 = autopark_state_v2;
        this.autopark_style = autopark_style;
        this.calendar_supported = calendar_supported;
        this.car_version = car_version;
        this.center_display_state = center_display_state;
        this.df = df;
        this.dr = dr;
        this.ft = ft;
        this.homelink_nearby = homelink_nearby;
        this.is_user_present = is_user_present;
        this.last_autopark_error = last_autopark_error;
        this.locked = locked;
        this.media_state = media_state;
        this.notifications_supported = notifications_supported;
        this.odometer = odometer;
        this.parsed_calendar_supported = parsed_calendar_supported;
        this.pf = pf;
        this.pr = pr;
        this.remote_start = remote_start;
        this.remote_start_enabled = remote_start_enabled;
        this.remote_start_supported = remote_start_supported;
        this.rt = rt;
        this.sentry_mode = sentry_mode;
        this.software_update = software_update;
        this.speed_limit_mode = speed_limit_mode;
        this.sun_roof_percent_open = sun_roof_percent_open;
        this.sun_roof_state = sun_roof_state;
        this.timestamp = timestamp;
        this.valet_mode = valet_mode;
        this.valet_pin_needed = valet_pin_needed;
        this.vehicle_name = vehicle_name;
    }

    public int getApiVersion(){
        return api_version;
    }

    public String getAutoparkStateV2(){
        return autopark_state_v2;
    }

    public String getAutoparkStyle(){
        return autopark_style;
    }

    public boolean getCalendarSupported(){
        return calendar_supported;
    }

    public String getCarVersion(){
        return car_version;
    }

    public int getCenterDisplayState(){
        return center_display_state;
    }

    public int getDf(){
        return df;
    }

    public int getDr(){
        return dr;
    }

    public int getFt(){
        return ft;
    }

    public boolean getHomelinkNearby(){
        return homelink_nearby;
    }

    public boolean getIsUserPresent(){
        return is_user_present;
    }

    public String getLastAutoparkError(){
        return last_autopark_error;
    }

    public boolean getLocked(){
        return locked;
    }

    public MediaState getMediaState(){
        return media_state;
    }

    public boolean getNotificationsSupported(){
        return notifications_supported;
    }

    public double getOdometer(){
        return odometer;
    }

    public boolean getParsed_calendarSupported(){
        return parsed_calendar_supported;
    }

    public int getPf(){
        return pf;
    }

    public int getPr(){
        return pr;
    }

    public boolean getRemoteStart(){
        return remote_start;
    }

    public boolean getRemoteStartEnabled(){
        return remote_start_enabled;
    }

    public boolean getRemoteStartSupported(){
        return remote_start_supported;
    }

    public int getRt(){
        return rt;
    }

    public boolean getSentryMode(){
        return sentry_mode;
    }

    public SoftwareUpdate getSoftwareUpdate(){
        return software_update;
    }

    public SpeedLimitMode getSpeedLimitMode(){
        return speed_limit_mode;
    }

    public int getSunRoofPercentOpen(){
        return sun_roof_percent_open;
    }

    public String getSunRoofState(){
        return sun_roof_state;
    }

    public long getTimestamp(){
        return timestamp;
    }

    public boolean getValetMode(){
        return valet_mode;
    }

    public boolean getValetPinNeeded(){
        return valet_pin_needed;
    }

    public String getVehicleName(){
        return vehicle_name;
    }

    public static class MediaState{

        private final boolean remote_control_enabled;

        public MediaState(boolean remote_control_enabled){
            this.remote_control_enabled = remote_control_enabled;
        }

        public boolean isRemoteControlEnabled(){
            return remote_control_enabled;
        }
    }

    public static class SoftwareUpdate{

        private final long expected_duration_sec;

        private final String status;

        private SoftwareUpdate(long expected_duration_sec, String status){
            this.expected_duration_sec = expected_duration_sec;
            this.status = status;
        }

        public long getExpectedDurationSec(){
            return expected_duration_sec;
        }

        public String getStatus(){
            return status;
        }
    }

    public static class SpeedLimitMode{

        private final boolean active;

        private final double current_limit_mph;

        private final int max_limit_mph;

        private final int min_limit_mph;

        private final boolean pin_code_set;

        public SpeedLimitMode(
                boolean active,
                double current_limit_mph,
                int max_limit_mph,
                int min_limit_mph,
                boolean pin_code_set
        ){
            this.active = active;
            this.current_limit_mph = current_limit_mph;
            this.max_limit_mph = max_limit_mph;
            this.min_limit_mph = min_limit_mph;
            this.pin_code_set = pin_code_set;
        }

        public boolean getActive(){
            return active;
        }

        public double getCurrentLimitMph(){
            return current_limit_mph;
        }

        public int getMaxLimitMph(){
            return max_limit_mph;
        }

        public int getMinLimitMph(){
            return min_limit_mph;
        }

        public boolean getPinCodeSet(){
            return pin_code_set;
        }
    }
}
