package com.ansill.tesla.api.low.model;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.util.Objects;
import java.util.Optional;

import static com.ansill.utility.Utility.simpleToString;

@SuppressWarnings("unused")
@Immutable
public final class VehicleState{

  private final int api_version;

  private final String autopark_state_v2;

  private final String autopark_style;

  private final boolean calendar_supported;

  private final String car_version;

  private final int center_display_state;

  private final int df;

  private final int dr;

  private final int ft;

  @Nullable
  private final Boolean homelink_nearby; // Could disappear from json

  @Nullable
  private final Integer homelink_device_count; // Could disappear from json

  @Nullable
  private final Integer sun_roof_percent_open;  // Could disappear from json

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

  private final String autopark_state_v3;

  private final int fd_window;

  private final int fp_window;

  private final int rd_window;

  private final int rp_window;

  private final boolean sentry_mode_available;

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
    @Nullable Boolean homelink_nearby,
    @Nullable Integer homelink_device_count,
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
    @Nullable Integer sun_roof_percent_open,
    String autopark_state_v3,
    int fd_window,
    int fp_window,
    int rd_window,
    int rp_window,
    boolean sentry_mode_available,
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
    this.homelink_device_count = homelink_device_count;
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
    this.autopark_state_v3 = autopark_state_v3;
    this.fd_window = fd_window;
    this.fp_window = fp_window;
    this.rd_window = rd_window;
    this.rp_window = rp_window;
    this.sentry_mode_available = sentry_mode_available;
    this.sun_roof_state = sun_roof_state;
    this.timestamp = timestamp;
    this.valet_mode = valet_mode;
    this.valet_pin_needed = valet_pin_needed;
    this.vehicle_name = vehicle_name;
  }

  public String getAutoparkStateV3(){
    return autopark_state_v3;
  }

  public int getFdWindow(){
    return fd_window;
  }

  public int getFpWindow(){
    return fp_window;
  }

  public int getRdWindow(){
    return rd_window;
  }

  public int getRpWindow(){
    return rp_window;
  }

  public boolean isSentryModeAvailable(){
    return sentry_mode_available;
  }

  @Nonnull
  public Optional<Integer> getHomelinkDeviceCount(){
    return Optional.ofNullable(homelink_device_count);
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

  @Nonnull
  public Optional<Boolean> getHomelinkNearby(){
    return Optional.ofNullable(homelink_nearby);
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

  @Nonnull
  public Optional<Integer> getSunRoofPercentOpen(){
    return Optional.ofNullable(sun_roof_percent_open);
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

  @Override
  public String toString(){
    return simpleToString(this);
  }

  @Override
  public boolean equals(Object o){
    if(this == o) return true;
    if(o == null || getClass() != o.getClass()) return false;

    VehicleState that = (VehicleState) o;

    if(api_version != that.api_version) return false;
    if(calendar_supported != that.calendar_supported) return false;
    if(center_display_state != that.center_display_state) return false;
    if(df != that.df) return false;
    if(dr != that.dr) return false;
    if(ft != that.ft) return false;
    if(is_user_present != that.is_user_present) return false;
    if(locked != that.locked) return false;
    if(notifications_supported != that.notifications_supported) return false;
    if(Double.compare(that.odometer, odometer) != 0) return false;
    if(parsed_calendar_supported != that.parsed_calendar_supported) return false;
    if(pf != that.pf) return false;
    if(pr != that.pr) return false;
    if(remote_start != that.remote_start) return false;
    if(remote_start_enabled != that.remote_start_enabled) return false;
    if(remote_start_supported != that.remote_start_supported) return false;
    if(rt != that.rt) return false;
    if(sentry_mode != that.sentry_mode) return false;
    if(fd_window != that.fd_window) return false;
    if(fp_window != that.fp_window) return false;
    if(rd_window != that.rd_window) return false;
    if(rp_window != that.rp_window) return false;
    if(sentry_mode_available != that.sentry_mode_available) return false;
    if(timestamp != that.timestamp) return false;
    if(valet_mode != that.valet_mode) return false;
    if(valet_pin_needed != that.valet_pin_needed) return false;
    if(!autopark_state_v2.equals(that.autopark_state_v2)) return false;
    if(!autopark_style.equals(that.autopark_style)) return false;
    if(!car_version.equals(that.car_version)) return false;
    if(!Objects.equals(homelink_nearby, that.homelink_nearby)) return false;
    if(!Objects.equals(homelink_device_count, that.homelink_device_count)) return false;
    if(!Objects.equals(sun_roof_percent_open, that.sun_roof_percent_open)) return false;
    if(!last_autopark_error.equals(that.last_autopark_error)) return false;
    if(!media_state.equals(that.media_state)) return false;
    if(!software_update.equals(that.software_update)) return false;
    if(!speed_limit_mode.equals(that.speed_limit_mode)) return false;
    if(!autopark_state_v3.equals(that.autopark_state_v3)) return false;
    if(!sun_roof_state.equals(that.sun_roof_state)) return false;
    return vehicle_name.equals(that.vehicle_name);
  }

  @Override
  public int hashCode(){
    int result;
    long temp;
    result = api_version;
    result = 31 * result + autopark_state_v2.hashCode();
    result = 31 * result + autopark_style.hashCode();
    result = 31 * result + (calendar_supported ? 1 : 0);
    result = 31 * result + car_version.hashCode();
    result = 31 * result + center_display_state;
    result = 31 * result + df;
    result = 31 * result + dr;
    result = 31 * result + ft;
    result = 31 * result + Objects.hashCode(homelink_nearby);
    result = 31 * result + Objects.hashCode(homelink_device_count);
    result = 31 * result + Objects.hashCode(sun_roof_percent_open);
    result = 31 * result + (is_user_present ? 1 : 0);
    result = 31 * result + last_autopark_error.hashCode();
    result = 31 * result + (locked ? 1 : 0);
    result = 31 * result + media_state.hashCode();
    result = 31 * result + (notifications_supported ? 1 : 0);
    temp = Double.doubleToLongBits(odometer);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + (parsed_calendar_supported ? 1 : 0);
    result = 31 * result + pf;
    result = 31 * result + pr;
    result = 31 * result + (remote_start ? 1 : 0);
    result = 31 * result + (remote_start_enabled ? 1 : 0);
    result = 31 * result + (remote_start_supported ? 1 : 0);
    result = 31 * result + rt;
    result = 31 * result + (sentry_mode ? 1 : 0);
    result = 31 * result + software_update.hashCode();
    result = 31 * result + speed_limit_mode.hashCode();
    result = 31 * result + autopark_state_v3.hashCode();
    result = 31 * result + fd_window;
    result = 31 * result + fp_window;
    result = 31 * result + rd_window;
    result = 31 * result + rp_window;
    result = 31 * result + (sentry_mode_available ? 1 : 0);
    result = 31 * result + sun_roof_state.hashCode();
    result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
    result = 31 * result + (valet_mode ? 1 : 0);
    result = 31 * result + (valet_pin_needed ? 1 : 0);
    result = 31 * result + vehicle_name.hashCode();
    return result;
  }

  public static class MediaState{

    private final boolean remote_control_enabled;

    public MediaState(boolean remote_control_enabled){
      this.remote_control_enabled = remote_control_enabled;
    }

    public boolean isRemoteControlEnabled(){
      return remote_control_enabled;
    }

    @Override
    public boolean equals(Object o){
      if(this == o) return true;
      if(!(o instanceof MediaState that)) return false;

      return remote_control_enabled == that.remote_control_enabled;
    }

    @Override
    public int hashCode(){
      return (remote_control_enabled ? 1 : 0);
    }
  }

  public static class SoftwareUpdate{

    private final int download_perc;

    private final int install_perc;

    private final String version;

    private final long expected_duration_sec;

    private final String status;

    public SoftwareUpdate(
      int download_perc,
      int install_perc,
      String version,
      long expected_duration_sec,
      String status
    ){
      this.download_perc = download_perc;
      this.install_perc = install_perc;
      this.version = version;
      this.expected_duration_sec = expected_duration_sec;
      this.status = status;
    }

    @Override
    public boolean equals(Object o){
      if(this == o) return true;
      if(o == null || getClass() != o.getClass()) return false;

      SoftwareUpdate that = (SoftwareUpdate) o;

      if(download_perc != that.download_perc) return false;
      if(install_perc != that.install_perc) return false;
      if(expected_duration_sec != that.expected_duration_sec) return false;
      if(!version.equals(that.version)) return false;
      return status.equals(that.status);
    }

    @Override
    public int hashCode(){
      int result = download_perc;
      result = 31 * result + install_perc;
      result = 31 * result + version.hashCode();
      result = 31 * result + (int) (expected_duration_sec ^ (expected_duration_sec >>> 32));
      result = 31 * result + status.hashCode();
      return result;
    }

    public int getDownloadPercent(){
      return download_perc;
    }

    public int getInstallPercent(){
      return install_perc;
    }

    public String getVersion(){
      return version;
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

    @Override
    public boolean equals(Object o){
      if(this == o) return true;
      if(o == null || getClass() != o.getClass()) return false;

      SpeedLimitMode that = (SpeedLimitMode) o;

      if(active != that.active) return false;
      if(Double.compare(that.current_limit_mph, current_limit_mph) != 0) return false;
      if(max_limit_mph != that.max_limit_mph) return false;
      if(min_limit_mph != that.min_limit_mph) return false;
      return pin_code_set == that.pin_code_set;
    }

    @Override
    public int hashCode(){
      int result;
      long temp;
      result = (active ? 1 : 0);
      temp = Double.doubleToLongBits(current_limit_mph);
      result = 31 * result + (int) (temp ^ (temp >>> 32));
      result = 31 * result + max_limit_mph;
      result = 31 * result + min_limit_mph;
      result = 31 * result + (pin_code_set ? 1 : 0);
      return result;
    }
  }
}
