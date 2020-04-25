
package com.ansill.tesla.api.mock.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.ansill.tesla.api.mock.MockUtility.simpleToString;

@SuppressWarnings("unused")
public class MockVehicle{

  @Nonnull
  private final String vin;

  private long id;

  private long vehicle_id;

  private long user_id;

  @Nonnull
  private String display_name;

  @Nonnull
  private String option_codes;

  @Nonnull
  private String color;

  @Nonnull
  private List<String> tokens;

  @Nonnull
  private String state;

  private boolean in_service;

  @Nonnull
  private String id_s;

  private boolean calendar_enabled;

  private int api_version;

  @Nullable
  private String backseat_token; // TODO confirm String

  @Nullable
  private String backseat_token_updated_at; // TODO confirm String

  @Nonnull
  private DriveState drive_state;

  @Nonnull
  private ClimateState climate_state;

  @Nonnull
  private ChargeState charge_state;

  @Nonnull
  private GUISettings gui_settings;

  @Nonnull
  private VehicleState vehicle_state;

  @Nonnull
  private VehicleConfig vehicle_config;

  public MockVehicle(
    long id,
    long vehicle_id,
    long user_id,
    @Nonnull String vin,
    @Nonnull String display_name,
    @Nonnull String option_codes,
    @Nonnull String color,
    @Nonnull List<String> tokens,
    @Nonnull String state,
    boolean in_service,
    @Nonnull String id_s,
    boolean calendar_enabled,
    int api_version,
    @Nullable String backseat_token,
    @Nullable String backseat_token_updated_at,
    @Nonnull DriveState driveState,
    @Nonnull ClimateState climateState,
    @Nonnull ChargeState chargeState,
    @Nonnull GUISettings guiSettings,
    @Nonnull VehicleState vehicleState,
    @Nonnull VehicleConfig vehicleConfig
  ){
    this.id = id;
    this.vehicle_id = vehicle_id;
    this.user_id = user_id;
    this.vin = vin;
    this.display_name = display_name;
    this.option_codes = option_codes;
    this.color = color;
    this.tokens = tokens;
    this.state = state;
    this.in_service = in_service;
    this.id_s = id_s;
    this.calendar_enabled = calendar_enabled;
    this.api_version = api_version;
    this.backseat_token = backseat_token;
    this.backseat_token_updated_at = backseat_token_updated_at;
    this.drive_state = driveState;
    this.climate_state = climateState;
    this.charge_state = chargeState;
    this.gui_settings = guiSettings;
    this.vehicle_state = vehicleState;
    this.vehicle_config = vehicleConfig;
  }

  public long getUserId(){
    return user_id;
  }

  void setUserId(long user_id){
    this.user_id = user_id;
  }

  public long getId(){
    return id;
  }

  public void setId(long id){
    this.id = id;
  }

  public long getVehicleId(){
    return vehicle_id;
  }

  public void setVehicleId(long vehicle_id){
    this.vehicle_id = vehicle_id;
  }

  public String getVIN(){
    return vin;
  }

  public String getOptionCodes(){
    return option_codes;
  }

  public void setOptionCodes(@Nonnull String option_codes){
    this.option_codes = option_codes;
  }

  public String getColor(){
    return color;
  }

  public void setColor(@Nonnull String color){
    this.color = color;
  }

  public List<String> getTokens(){
    return Collections.unmodifiableList(tokens);
  }

  public void setTokens(@Nonnull List<String> tokens){
    this.tokens = tokens;
  }

  @Nonnull
  public String getState(){
    return state;
  }

  public void setState(@Nonnull String state){
    this.state = state;
  }

  public boolean isInService(){
    return in_service;
  }

  public void setInService(boolean in_service){
    this.in_service = in_service;
  }

  public String getIdString(){
    return id_s;
  }

  public boolean isCalendarEnabled(){
    return calendar_enabled;
  }

  public void setCalendarEnabled(boolean calendar_enabled){
    this.calendar_enabled = calendar_enabled;
  }

  public int getApiVersion(){
    return api_version;
  }

  public void setApiVersion(int api_version){
    this.api_version = api_version;
  }

  public String getBackseatToken(){
    return backseat_token;
  }

  public void setBackseatToken(@Nullable String backseat_token){
    this.backseat_token = backseat_token;
  }

  public String getBackseatTokenUpdatedAt(){
    return backseat_token_updated_at;
  }

  public void setBackseatTokenUpdatedAt(@Nullable String backseat_token_updated_at){
    this.backseat_token_updated_at = backseat_token_updated_at;
  }

  @Override
  public String toString(){
    return simpleToString(this);
  }

  public String getDisplayName(){
    return display_name;
  }

  public void setDisplayName(@Nonnull String display_name){
    this.display_name = display_name;
  }

  public void setIdS(@Nonnull String id_s){
    this.id_s = id_s;
  }

  @Nonnull
  public DriveState getDriveState(){
    return drive_state;
  }

  public void setDriveState(@Nonnull DriveState drive_state){
    this.drive_state = drive_state;
  }

  @Nonnull
  public ClimateState getClimateState(){
    return climate_state;
  }

  public void setClimate_state(@Nonnull ClimateState climate_state){
    this.climate_state = climate_state;
  }

  @Nonnull
  public ChargeState getChargeState(){
    return charge_state;
  }

  public void setChargeState(@Nonnull ChargeState chargeState){
    this.charge_state = chargeState;
  }

  @Nonnull
  public GUISettings getGuiSettings(){
    return gui_settings;
  }

  public void setGuiSettings(@Nonnull GUISettings guiSettings){
    this.gui_settings = guiSettings;
  }

  @Nonnull
  public VehicleState getVehicleState(){
    return vehicle_state;
  }

  public void setVehicleState(@Nonnull VehicleState vehicleState){
    this.vehicle_state = vehicleState;
  }

  @Nonnull
  public VehicleConfig getVehicleConfig(){
    return vehicle_config;
  }

  public void setVehicleConfig(@Nonnull VehicleConfig vehicleConfig){
    this.vehicle_config = vehicleConfig;
  }

  public static class DriveState{

    private long gps_as_of;

    private int heading;

    private double latitude;

    private double longitude;

    private double native_latitude;

    private int native_location_supported;

    private double native_longitude;

    private String native_type;

    private int power;

    @Nullable
    private String shift_state;

    @Nullable
    private Integer speed;

    private long timestamp;

    public DriveState(
      long gps_as_of,
      int heading,
      double latitude,
      double longitude,
      double native_latitude,
      int native_location_supported,
      double native_longitude,
      String native_type,
      int power,
      @Nullable String shift_state,
      @Nullable Integer speed,
      long timestamp
    ){
      this.gps_as_of = gps_as_of;
      this.heading = heading;
      this.latitude = latitude;
      this.longitude = longitude;
      this.native_latitude = native_latitude;
      this.native_location_supported = native_location_supported;
      this.native_longitude = native_longitude;
      this.native_type = native_type;
      this.power = power;
      this.shift_state = shift_state;
      this.speed = speed;
      this.timestamp = timestamp;
    }

    public long getGpsAsOf(){
      return gps_as_of;
    }

    public int getHeading(){
      return heading;
    }

    public void setHeading(int heading){
      this.heading = heading;
    }

    public double getLatitude(){
      return latitude;
    }

    public void setLatitude(double latitude){
      this.latitude = latitude;
    }

    public double getLongitude(){
      return longitude;
    }

    public void setLongitude(double longitude){
      this.longitude = longitude;
    }

    public double getNativeLatitude(){
      return native_latitude;
    }

    public int getNativeLocationSupported(){
      return native_location_supported;
    }

    public double getNativeLongitude(){
      return native_longitude;
    }

    public String getNativeType(){
      return native_type;
    }

    public int getPower(){
      return power;
    }

    public void setPower(int power){
      this.power = power;
    }

    @Nonnull
    public Optional<String> getShiftState(){
      return Optional.ofNullable(shift_state);
    }

    @Nonnull
    public Optional<Integer> getSpeed(){
      return Optional.ofNullable(speed);
    }

    public void setSpeed(@Nullable Integer speed){
      this.speed = speed;
    }

    public long getTimestamp(){
      return timestamp;
    }

    public void setTimestamp(long timestamp){
      this.timestamp = timestamp;
    }

    @Override
    public String toString(){
      return simpleToString(this);
    }

    public void setGps_as_of(long gps_as_of){
      this.gps_as_of = gps_as_of;
    }

    public void setNative_latitude(double native_latitude){
      this.native_latitude = native_latitude;
    }

    public void setNative_location_supported(int native_location_supported){
      this.native_location_supported = native_location_supported;
    }

    public void setNative_longitude(double native_longitude){
      this.native_longitude = native_longitude;
    }

    public void setNative_type(String native_type){
      this.native_type = native_type;
    }

    public void setShift_state(@Nullable String shift_state){
      this.shift_state = shift_state;
    }
  }

  public static class ClimateState{

    private boolean battery_heater;

    private boolean battery_heater_no_power;

    private String climate_keeper_mode;

    private double driver_temp_setting;

    private int fan_status;

    @Nullable
    private Double inside_temp;

    @Nullable
    private Boolean is_auto_conditioning_on;

    private boolean is_climate_on;

    private boolean is_front_defroster_on;

    private boolean is_preconditioning;

    private boolean is_rear_defroster_on;

    private String left_temp_direction;

    private double max_avail_temp;

    private double min_avail_temp;

    @Nullable
    private Double outside_temp;

    private double passenger_temp_setting;

    private boolean remote_heater_control_enabled;

    private String right_temp_direction;

    @Nullable
    private Integer seat_heater_left; // Entry could disapppear from JSON

    @Nullable
    private Integer seat_heater_rear_center; // Entry could disapppear from JSON

    @Nullable
    private Integer seat_heater_rear_left; // Entry could disapppear from JSON

    @Nullable
    private Integer seat_heater_left_back; // Entry could disapppear from JSON

    @Nullable
    private Integer seat_heater_rear_right; // Entry could disapppear from JSON

    @Nullable
    private Integer seat_heater_right_back; // Entry could disapppear from JSON

    @Nullable
    private Integer seat_heater_right; // Entry could disapppear from JSON

    @Nullable
    private Boolean side_mirror_heaters; // Entry could disappear from JSON

    @Nullable
    private Boolean smart_preconditioning; // Entry could disappear from JSON

    @Nullable
    private Boolean steering_wheel_heater; // Entry could disappear from JSON

    private long timestamp;

    @Nullable
    private Boolean wiper_blade_heater; // Entry could disappear from JSON

    private int defrost_mode;

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

    public void setTimestamp(long timestamp){
      this.timestamp = timestamp;
    }

    @Nonnull
    public Optional<Boolean> getWiperBladeHeater(){
      return Optional.ofNullable(wiper_blade_heater);
    }

    @Override
    public String toString(){
      return simpleToString(this);
    }

    public void setBattery_heater(boolean battery_heater){
      this.battery_heater = battery_heater;
    }

    public void setBattery_heater_no_power(boolean battery_heater_no_power){
      this.battery_heater_no_power = battery_heater_no_power;
    }

    public void setClimate_keeper_mode(String climate_keeper_mode){
      this.climate_keeper_mode = climate_keeper_mode;
    }

    public void setDriver_temp_setting(double driver_temp_setting){
      this.driver_temp_setting = driver_temp_setting;
    }

    public void setFan_status(int fan_status){
      this.fan_status = fan_status;
    }

    public void setInside_temp(@Nullable Double inside_temp){
      this.inside_temp = inside_temp;
    }

    public void setIs_auto_conditioning_on(@Nullable Boolean is_auto_conditioning_on){
      this.is_auto_conditioning_on = is_auto_conditioning_on;
    }

    public void setIs_climate_on(boolean is_climate_on){
      this.is_climate_on = is_climate_on;
    }

    public void setIs_front_defroster_on(boolean is_front_defroster_on){
      this.is_front_defroster_on = is_front_defroster_on;
    }

    public void setIs_preconditioning(boolean is_preconditioning){
      this.is_preconditioning = is_preconditioning;
    }

    public void setIs_rear_defroster_on(boolean is_rear_defroster_on){
      this.is_rear_defroster_on = is_rear_defroster_on;
    }

    public void setLeft_temp_direction(String left_temp_direction){
      this.left_temp_direction = left_temp_direction;
    }

    public void setMax_avail_temp(double max_avail_temp){
      this.max_avail_temp = max_avail_temp;
    }

    public void setMin_avail_temp(double min_avail_temp){
      this.min_avail_temp = min_avail_temp;
    }

    public void setOutside_temp(@Nullable Double outside_temp){
      this.outside_temp = outside_temp;
    }

    public void setPassenger_temp_setting(double passenger_temp_setting){
      this.passenger_temp_setting = passenger_temp_setting;
    }

    public void setRemote_heater_controlEnabled(boolean remote_heater_control_enabled){
      this.remote_heater_control_enabled = remote_heater_control_enabled;
    }

    public void setRight_temp_direction(String right_temp_direction){
      this.right_temp_direction = right_temp_direction;
    }

    public void setSeat_heater_left(@Nullable Integer seat_heater_left){
      this.seat_heater_left = seat_heater_left;
    }

    public void setSeat_heater_rear_center(@Nullable Integer seat_heater_rear_center){
      this.seat_heater_rear_center = seat_heater_rear_center;
    }

    public void setSeat_heater_rear_left(@Nullable Integer seat_heater_rear_left){
      this.seat_heater_rear_left = seat_heater_rear_left;
    }

    public void setSeat_heater_left_back(@Nullable Integer seat_heater_left_back){
      this.seat_heater_left_back = seat_heater_left_back;
    }

    public void setSeat_heater_rear_right(@Nullable Integer seat_heater_rear_right){
      this.seat_heater_rear_right = seat_heater_rear_right;
    }

    public void setSeat_heater_right_back(@Nullable Integer seat_heater_right_back){
      this.seat_heater_right_back = seat_heater_right_back;
    }

    public void setSeat_heater_right(@Nullable Integer seat_heater_right){
      this.seat_heater_right = seat_heater_right;
    }

    public void setSide_mirror_heaters(@Nullable Boolean side_mirror_heaters){
      this.side_mirror_heaters = side_mirror_heaters;
    }

    public void setSmart_preconditioning(@Nullable Boolean smart_preconditioning){
      this.smart_preconditioning = smart_preconditioning;
    }

    public void setSteering_wheel_heater(@Nullable Boolean steering_wheel_heater){
      this.steering_wheel_heater = steering_wheel_heater;
    }

    public void setWiper_blade_heater(@Nullable Boolean wiper_blade_heater){
      this.wiper_blade_heater = wiper_blade_heater;
    }

    public void setDefrost_mode(int defrost_mode){
      this.defrost_mode = defrost_mode;
    }
  }

  public static class ChargeState{
    private boolean battery_heater_on;

    private int battery_level;

    private double battery_range;

    private int charge_current_request;

    private int charge_current_request_max;

    private boolean charge_enable_request;

    private double charge_energy_added;

    private int charge_limit_soc;

    private int charge_limit_soc_max;

    private int charge_limit_soc_min;

    private int charge_limit_soc_std;

    private double charge_miles_added_ideal;

    private double charge_miles_added_rated;

    private boolean charge_port_cold_weather_mode;

    private boolean charge_port_door_open;

    private String charge_port_latch;

    private double charge_rate;

    private boolean charge_to_max_range;

    private int charger_actual_current;

    private String charger_phases;

    private int charger_pilot_current;

    private int charger_power;

    private int charger_voltage;

    private String charging_state;

    private String conn_charge_cable;

    private double est_battery_range;

    private String fast_charger_brand;

    private boolean fast_charger_present;

    private long minutes_to_full_charge;

    private String fast_charger_type;

    private double ideal_battery_range;

    private boolean managed_charging_active;

    private String managed_charging_start_time;

    private boolean managed_charging_user_canceled;

    private int max_range_charge_counter;

    private Boolean not_enough_power_to_heat;

    private boolean scheduled_charging_pending;

    @Nullable
    private String scheduled_charging_start_time;

    private double time_to_full_charge;

    private long timestamp;

    private int usable_battery_level;

    private String user_charge_enable_request;

    private boolean trip_charging;

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

    public void setTimestamp(long timestamp){
      this.timestamp = timestamp;
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

    public void setBattery_heater_on(boolean battery_heater_on){
      this.battery_heater_on = battery_heater_on;
    }

    public void setBattery_level(int battery_level){
      this.battery_level = battery_level;
    }

    public void setBattery_range(double battery_range){
      this.battery_range = battery_range;
    }

    public void setCharge_current_request(int charge_current_request){
      this.charge_current_request = charge_current_request;
    }

    public void setCharge_current_request_max(int charge_current_request_max){
      this.charge_current_request_max = charge_current_request_max;
    }

    public void setCharge_enable_request(boolean charge_enable_request){
      this.charge_enable_request = charge_enable_request;
    }

    public void setCharge_energy_added(double charge_energy_added){
      this.charge_energy_added = charge_energy_added;
    }

    public void setCharge_limit_soc(int charge_limit_soc){
      this.charge_limit_soc = charge_limit_soc;
    }

    public void setCharge_limit_soc_max(int charge_limit_soc_max){
      this.charge_limit_soc_max = charge_limit_soc_max;
    }

    public void setCharge_limit_soc_min(int charge_limit_soc_min){
      this.charge_limit_soc_min = charge_limit_soc_min;
    }

    public void setCharge_limit_soc_std(int charge_limit_soc_std){
      this.charge_limit_soc_std = charge_limit_soc_std;
    }

    public void setCharge_miles_added_ideal(double charge_miles_added_ideal){
      this.charge_miles_added_ideal = charge_miles_added_ideal;
    }

    public void setCharge_miles_added_rated(double charge_miles_added_rated){
      this.charge_miles_added_rated = charge_miles_added_rated;
    }

    public void setCharge_port_cold_weather_mode(boolean charge_port_cold_weather_mode){
      this.charge_port_cold_weather_mode = charge_port_cold_weather_mode;
    }

    public void setCharge_port_door_open(boolean charge_port_door_open){
      this.charge_port_door_open = charge_port_door_open;
    }

    public void setCharge_port_latch(String charge_port_latch){
      this.charge_port_latch = charge_port_latch;
    }

    public void setCharge_rate(double charge_rate){
      this.charge_rate = charge_rate;
    }

    public void setCharge_to_max_range(boolean charge_to_max_range){
      this.charge_to_max_range = charge_to_max_range;
    }

    public void setCharger_actual_current(int charger_actual_current){
      this.charger_actual_current = charger_actual_current;
    }

    public void setCharger_phases(String charger_phases){
      this.charger_phases = charger_phases;
    }

    public void setCharger_pilot_current(int charger_pilot_current){
      this.charger_pilot_current = charger_pilot_current;
    }

    public void setCharger_power(int charger_power){
      this.charger_power = charger_power;
    }

    public void setCharger_voltage(int charger_voltage){
      this.charger_voltage = charger_voltage;
    }

    public void setCharging_state(String charging_state){
      this.charging_state = charging_state;
    }

    public void setConn_charge_cable(String conn_charge_cable){
      this.conn_charge_cable = conn_charge_cable;
    }

    public void setEst_battery_range(double est_battery_range){
      this.est_battery_range = est_battery_range;
    }

    public void setFast_charger_brand(String fast_charger_brand){
      this.fast_charger_brand = fast_charger_brand;
    }

    public void setFast_charger_present(boolean fast_charger_present){
      this.fast_charger_present = fast_charger_present;
    }

    public void setMinutes_to_full_charge(long minutes_to_full_charge){
      this.minutes_to_full_charge = minutes_to_full_charge;
    }

    public void setFast_charger_type(String fast_charger_type){
      this.fast_charger_type = fast_charger_type;
    }

    public void setIdeal_battery_range(double ideal_battery_range){
      this.ideal_battery_range = ideal_battery_range;
    }

    public void setManaged_charging_active(boolean managed_charging_active){
      this.managed_charging_active = managed_charging_active;
    }

    public void setManaged_charging_start_time(String managed_charging_start_time){
      this.managed_charging_start_time = managed_charging_start_time;
    }

    public void setManaged_charging_user_canceled(boolean managed_charging_user_canceled){
      this.managed_charging_user_canceled = managed_charging_user_canceled;
    }

    public void setMax_range_charge_counter(int max_range_charge_counter){
      this.max_range_charge_counter = max_range_charge_counter;
    }

    public void setNot_enough_power_to_heat(Boolean not_enough_power_to_heat){
      this.not_enough_power_to_heat = not_enough_power_to_heat;
    }

    public void setScheduled_charging_pending(boolean scheduled_charging_pending){
      this.scheduled_charging_pending = scheduled_charging_pending;
    }

    public void setScheduled_charging_start_time(@Nullable String scheduled_charging_start_time){
      this.scheduled_charging_start_time = scheduled_charging_start_time;
    }

    public void setTime_to_full_charge(double time_to_full_charge){
      this.time_to_full_charge = time_to_full_charge;
    }

    public void setUsable_battery_level(int usable_battery_level){
      this.usable_battery_level = usable_battery_level;
    }

    public void setUser_charge_enable_request(String user_charge_enable_request){
      this.user_charge_enable_request = user_charge_enable_request;
    }

    public void setTrip_charging(boolean trip_charging){
      this.trip_charging = trip_charging;
    }
  }

  public static class GUISettings{

    private boolean gui_24_hour_time;

    private String gui_charge_rate_units;

    private String gui_distance_units;

    private String gui_range_display;

    private String gui_temperature_units;

    private long timestamp;

    private boolean show_range_units;

    public GUISettings(
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

    public void setTimestamp(long timestamp){
      this.timestamp = timestamp;
    }

    @Override
    public String toString(){
      return simpleToString(this);
    }

    public void setGui_24_hour_time(boolean gui_24_hour_time){
      this.gui_24_hour_time = gui_24_hour_time;
    }

    public void setGui_charge_rate_units(String gui_charge_rate_units){
      this.gui_charge_rate_units = gui_charge_rate_units;
    }

    public void setGui_distance_units(String gui_distance_units){
      this.gui_distance_units = gui_distance_units;
    }

    public void setGui_range_display(String gui_range_display){
      this.gui_range_display = gui_range_display;
    }

    public void setGui_temperature_units(String gui_temperature_units){
      this.gui_temperature_units = gui_temperature_units;
    }

    public void setShow_range_units(boolean show_range_units){
      this.show_range_units = show_range_units;
    }
  }

  public static class VehicleState{

    private int api_version;

    private String autopark_state_v2;

    private String autopark_style;

    private boolean calendar_supported;

    private String car_version;

    private int center_display_state;

    private int df;

    private int dr;

    private int ft;

    @Nullable
    private Boolean homelink_nearby; // Could disappear from json

    @Nullable
    private Integer homelink_device_count; // Could disappear from json

    @Nullable
    private Integer sun_roof_percent_open;  // Could disappear from json

    private boolean is_user_present;

    private String last_autopark_error;

    private boolean locked;

    private MediaState media_state;

    private boolean notifications_supported;

    private double odometer;

    private boolean parsed_calendar_supported;

    private int pf;

    private int pr;

    private boolean remote_start;

    private boolean remote_start_enabled;

    private boolean remote_start_supported;

    private int rt;

    private boolean sentry_mode;

    private SoftwareUpdate software_update;

    private SpeedLimitMode speed_limit_mode;

    private String autopark_state_v3;

    private int fd_window;

    private int fp_window;

    private int rd_window;

    private int rp_window;

    private boolean sentry_mode_available;

    private String sun_roof_state;

    private long timestamp;

    private boolean valet_mode;

    private boolean valet_pin_needed;

    private String vehicle_name;

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

    public void setApiVersion(int api_version){
      this.api_version = api_version;
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

    public void setCarVersion(String car_version){
      this.car_version = car_version;
    }

    public int getCenterDisplayState(){
      return center_display_state;
    }

    public int getDf(){
      return df;
    }

    public void setDf(int df){
      this.df = df;
    }

    public int getDr(){
      return dr;
    }

    public void setDr(int dr){
      this.dr = dr;
    }

    public int getFt(){
      return ft;
    }

    public void setFt(int ft){
      this.ft = ft;
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

    public void setLocked(boolean locked){
      this.locked = locked;
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

    public void setOdometer(double odometer){
      this.odometer = odometer;
    }

    public boolean getParsed_calendarSupported(){
      return parsed_calendar_supported;
    }

    public int getPf(){
      return pf;
    }

    public void setPf(int pf){
      this.pf = pf;
    }

    public int getPr(){
      return pr;
    }

    public void setPr(int pr){
      this.pr = pr;
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

    public void setRt(int rt){
      this.rt = rt;
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

    public void setTimestamp(long timestamp){
      this.timestamp = timestamp;
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

    public void setVehicleName(String vehicle_name){
      this.vehicle_name = vehicle_name;
    }

    @Override
    public String toString(){
      return simpleToString(this);
    }

    public void setAutopark_state_v2(String autopark_state_v2){
      this.autopark_state_v2 = autopark_state_v2;
    }

    public void setAutopark_style(String autopark_style){
      this.autopark_style = autopark_style;
    }

    public void setCalendar_supported(boolean calendar_supported){
      this.calendar_supported = calendar_supported;
    }

    public void setCenter_display_state(int center_display_state){
      this.center_display_state = center_display_state;
    }

    public void setHomelink_nearby(@Nullable Boolean homelink_nearby){
      this.homelink_nearby = homelink_nearby;
    }

    public void setHomelink_device_count(@Nullable Integer homelink_device_count){
      this.homelink_device_count = homelink_device_count;
    }

    public void setSun_roof_percent_open(@Nullable Integer sun_roof_percent_open){
      this.sun_roof_percent_open = sun_roof_percent_open;
    }

    public void setIs_user_present(boolean is_user_present){
      this.is_user_present = is_user_present;
    }

    public void setLast_autopark_error(String last_autopark_error){
      this.last_autopark_error = last_autopark_error;
    }

    public void setMedia_state(MediaState media_state){
      this.media_state = media_state;
    }

    public void setNotifications_supported(boolean notifications_supported){
      this.notifications_supported = notifications_supported;
    }

    public void setParsed_calendar_supported(boolean parsed_calendar_supported){
      this.parsed_calendar_supported = parsed_calendar_supported;
    }

    public void setRemote_start(boolean remote_start){
      this.remote_start = remote_start;
    }

    public void setRemote_startEnabled(boolean remote_start_enabled){
      this.remote_start_enabled = remote_start_enabled;
    }

    public void setRemote_start_supported(boolean remote_start_supported){
      this.remote_start_supported = remote_start_supported;
    }

    public void setSentry_mode(boolean sentry_mode){
      this.sentry_mode = sentry_mode;
    }

    public void setSoftware_update(SoftwareUpdate software_update){
      this.software_update = software_update;
    }

    public void setSpeed_limit_mode(SpeedLimitMode speed_limit_mode){
      this.speed_limit_mode = speed_limit_mode;
    }

    public void setAutopark_state_v3(String autopark_state_v3){
      this.autopark_state_v3 = autopark_state_v3;
    }

    public void setFd_window(int fd_window){
      this.fd_window = fd_window;
    }

    public void setFp_window(int fp_window){
      this.fp_window = fp_window;
    }

    public void setRd_window(int rd_window){
      this.rd_window = rd_window;
    }

    public void setRp_window(int rp_window){
      this.rp_window = rp_window;
    }

    public void setSentry_mode_available(boolean sentry_mode_available){
      this.sentry_mode_available = sentry_mode_available;
    }

    public void setSun_roof_state(String sun_roof_state){
      this.sun_roof_state = sun_roof_state;
    }

    public void setValet_mode(boolean valet_mode){
      this.valet_mode = valet_mode;
    }

    public void setValet_pin_needed(boolean valet_pin_needed){
      this.valet_pin_needed = valet_pin_needed;
    }

    public static class MediaState{

      private boolean remote_control_enabled;

      public MediaState(boolean remote_control_enabled){
        this.remote_control_enabled = remote_control_enabled;
      }

      public boolean isRemoteControlEnabled(){
        return remote_control_enabled;
      }

      public void setRemote_control_enabled(boolean remote_control_enabled){
        this.remote_control_enabled = remote_control_enabled;
      }
    }

    public static class SoftwareUpdate{

      private int download_perc;

      private int install_perc;

      private String version;

      private long expected_duration_sec;

      private String status;

      private SoftwareUpdate(
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

      public int getDownloadPercent(){
        return download_perc;
      }

      public int getInstallPercent(){
        return install_perc;
      }

      public String getVersion(){
        return version;
      }

      public void setVersion(String version){
        this.version = version;
      }

      public long getExpectedDurationSec(){
        return expected_duration_sec;
      }

      public String getStatus(){
        return status;
      }

      public void setStatus(String status){
        this.status = status;
      }

      public void setDownload_perc(int download_perc){
        this.download_perc = download_perc;
      }

      public void setInstall_perc(int install_perc){
        this.install_perc = install_perc;
      }

      public void setExpected_duration_sec(long expected_duration_sec){
        this.expected_duration_sec = expected_duration_sec;
      }
    }

    public static class SpeedLimitMode{

      private boolean active;

      private double current_limit_mph;

      private int max_limit_mph;

      private int min_limit_mph;

      private boolean pin_code_set;

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

      public void setActive(boolean active){
        this.active = active;
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

      public void setCurrent_limit_mph(double current_limit_mph){
        this.current_limit_mph = current_limit_mph;
      }

      public void setMax_limit_mph(int max_limit_mph){
        this.max_limit_mph = max_limit_mph;
      }

      public void setMin_limit_mph(int min_limit_mph){
        this.min_limit_mph = min_limit_mph;
      }

      public void setPin_code_set(boolean pin_code_set){
        this.pin_code_set = pin_code_set;
      }
    }
  }

  public static class VehicleConfig{

    private boolean can_accept_navigation_requests;

    private boolean can_actuate_trunks;

    private String car_special_type;

    private String car_type;

    private String charge_port_type;

    private boolean eu_vehicle;

    private String exterior_color;

    private boolean has_air_suspension;

    private boolean has_ludicrous_mode;

    private int key_version;

    private boolean motorized_charge_port;

    private String perf_config;

    private boolean plg;

    private int rear_seat_heaters;

    private int rear_seat_type;

    private boolean rhd;

    private String roof_color;

    private int seat_type;

    private String spoiler_type;

    private int sun_roof_installed;

    private String third_row_seats;

    private long timestamp;

    private String trim_badging;

    private String wheel_type;

    private boolean use_range_badging;

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

    public void setKeyVersion(int key_version){
      this.key_version = key_version;
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

    public void setPlg(boolean plg){
      this.plg = plg;
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

    public void setRhd(boolean rhd){
      this.rhd = rhd;
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

    public void setTimestamp(long timestamp){
      this.timestamp = timestamp;
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

    public void setCan_accept_navigation_requests(boolean can_accept_navigation_requests){
      this.can_accept_navigation_requests = can_accept_navigation_requests;
    }

    public void setCan_actuate_trunks(boolean can_actuate_trunks){
      this.can_actuate_trunks = can_actuate_trunks;
    }

    public void setCar_special_type(String car_special_type){
      this.car_special_type = car_special_type;
    }

    public void setCar_type(String car_type){
      this.car_type = car_type;
    }

    public void setCharge_port_type(String charge_port_type){
      this.charge_port_type = charge_port_type;
    }

    public void setEu_vehicle(boolean eu_vehicle){
      this.eu_vehicle = eu_vehicle;
    }

    public void setExterior_color(String exterior_color){
      this.exterior_color = exterior_color;
    }

    public void setHas_air_suspension(boolean has_air_suspension){
      this.has_air_suspension = has_air_suspension;
    }

    public void setHas_ludicrous_mode(boolean has_ludicrous_mode){
      this.has_ludicrous_mode = has_ludicrous_mode;
    }

    public void setMotorized_charge_port(boolean motorized_charge_port){
      this.motorized_charge_port = motorized_charge_port;
    }

    public void setPerf_config(String perf_config){
      this.perf_config = perf_config;
    }

    public void setRear_seat_heaters(int rear_seat_heaters){
      this.rear_seat_heaters = rear_seat_heaters;
    }

    public void setRear_seat_type(int rear_seat_type){
      this.rear_seat_type = rear_seat_type;
    }

    public void setRoof_color(String roof_color){
      this.roof_color = roof_color;
    }

    public void setSeat_type(int seat_type){
      this.seat_type = seat_type;
    }

    public void setSpoiler_type(String spoiler_type){
      this.spoiler_type = spoiler_type;
    }

    public void setSun_roof_installed(int sun_roof_installed){
      this.sun_roof_installed = sun_roof_installed;
    }

    public void setThird_row_seats(String third_row_seats){
      this.third_row_seats = third_row_seats;
    }

    public void setTrim_badging(String trim_badging){
      this.trim_badging = trim_badging;
    }

    public void setWheel_type(String wheel_type){
      this.wheel_type = wheel_type;
    }

    public void setUse_range_badging(boolean use_range_badging){
      this.use_range_badging = use_range_badging;
    }
  }
}
