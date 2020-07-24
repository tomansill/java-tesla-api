package com.ansill.tesla.api.data.model;

import com.ansill.tesla.api.data.utility.JacksonUtility;
import com.ansill.tesla.api.data.utility.SimpleSerializer;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import static com.ansill.tesla.api.data.utility.JacksonUtility.*;
import static com.ansill.utility.Utility.f;

@JsonSerialize(using = SimpleSerializer.class)
@JsonDeserialize(using = VehicleState.Deserializer.class)
@Immutable
public final class VehicleState{

  public static final int SUPPORTED_API_VERSION = 8;

  @Nullable
  private final String autoparkStateV3;

  private final boolean calendarSupported;

  @Nonnull
  private final String carVersion;

  private final int centerDisplayState;

  private final int df;

  private final int dr;

  private final int fdWindow;

  private final int fpWindow;

  private final int ft;

  private final boolean isUserPresent;

  private final boolean locked;

  @Nonnull
  private final MediaState mediaState;

  private final boolean notificationsSupported;

  private final double odometer;

  private final boolean parsedCalendarSupported;

  private final int pf;

  private final int pr;

  private final int rdWindow;

  private final boolean remoteStart;

  private final boolean remoteStartEnabled;

  private final boolean remoteStartSupported;

  private final int rpWindow;

  private final int rt;

  private final boolean sentryMode;

  private final boolean sentryModeAvailable;

  @Nonnull
  private final SoftwareUpdate softwareUpdate;

  @Nonnull
  private final SpeedLimitMode speedLimitMode;

  private final long timestamp;

  private final boolean valetMode;

  private final boolean valetPinNeeded;

  @Nonnull
  private final String vehicleName;

  @Nonnull
  private final Map<String,Optional<Object>> _unknownFields;

  public int apiVersion;

  public VehicleState(
    int apiVersion,
    @Nullable String autoparkStateV3,
    boolean calendarSupported,
    @Nonnull String carVersion,
    int centerDisplayState,
    int df,
    int dr,
    int fdWindow,
    int fpWindow,
    int ft,
    boolean isUserPresent,
    boolean locked,
    @Nonnull MediaState mediaState,
    boolean notificationsSupported,
    double odometer,
    boolean parsedCalendarSupported,
    int pf,
    int pr,
    int rdWindow,
    boolean remoteStart,
    boolean remoteStartEnabled,
    boolean remoteStartSupported,
    int rpWindow,
    int rt,
    boolean sentryMode,
    boolean sentryModeAvailable,
    @Nonnull SoftwareUpdate softwareUpdate,
    @Nonnull SpeedLimitMode speedLimitMode,
    long timestamp,
    boolean valetMode,
    boolean valetPinNeeded,
    @Nonnull String vehicleName
  ){
    this.apiVersion = apiVersion;
    this.autoparkStateV3 = autoparkStateV3;
    this.calendarSupported = calendarSupported;
    this.carVersion = carVersion;
    this.centerDisplayState = centerDisplayState;
    this.df = df;
    this.dr = dr;
    this.fdWindow = fdWindow;
    this.fpWindow = fpWindow;
    this.ft = ft;
    this.isUserPresent = isUserPresent;
    this.locked = locked;
    this.mediaState = mediaState;
    this.notificationsSupported = notificationsSupported;
    this.odometer = odometer;
    this.parsedCalendarSupported = parsedCalendarSupported;
    this.pf = pf;
    this.pr = pr;
    this.rdWindow = rdWindow;
    this.remoteStart = remoteStart;
    this.remoteStartEnabled = remoteStartEnabled;
    this.remoteStartSupported = remoteStartSupported;
    this.rpWindow = rpWindow;
    this.rt = rt;
    this.sentryMode = sentryMode;
    this.sentryModeAvailable = sentryModeAvailable;
    this.softwareUpdate = softwareUpdate;
    this.speedLimitMode = speedLimitMode;
    this.timestamp = timestamp;
    this.valetMode = valetMode;
    this.valetPinNeeded = valetPinNeeded;
    this.vehicleName = vehicleName;
    this._unknownFields = Collections.emptyMap();
  }

  public VehicleState(
    int apiVersion,
    @Nullable String autoparkStateV3,
    boolean calendarSupported,
    @Nonnull String carVersion,
    int centerDisplayState,
    int df,
    int dr,
    int fdWindow,
    int fpWindow,
    int ft,
    boolean isUserPresent,
    boolean locked,
    @Nonnull MediaState mediaState,
    boolean notificationsSupported,
    double odometer,
    boolean parsedCalendarSupported,
    int pf,
    int pr,
    int rdWindow,
    boolean remoteStart,
    boolean remoteStartEnabled,
    boolean remoteStartSupported,
    int rpWindow,
    int rt,
    boolean sentryMode,
    boolean sentryModeAvailable,
    @Nonnull SoftwareUpdate softwareUpdate,
    @Nonnull SpeedLimitMode speedLimitMode,
    long timestamp,
    boolean valetMode,
    boolean valetPinNeeded,
    @Nonnull String vehicleName,
    @Nonnull Map<String,Optional<Object>> unknownFields
  ){
    this.apiVersion = apiVersion;
    this.autoparkStateV3 = autoparkStateV3;
    this.calendarSupported = calendarSupported;
    this.carVersion = carVersion;
    this.centerDisplayState = centerDisplayState;
    this.df = df;
    this.dr = dr;
    this.fdWindow = fdWindow;
    this.fpWindow = fpWindow;
    this.ft = ft;
    this.isUserPresent = isUserPresent;
    this.locked = locked;
    this.mediaState = mediaState;
    this.notificationsSupported = notificationsSupported;
    this.odometer = odometer;
    this.parsedCalendarSupported = parsedCalendarSupported;
    this.pf = pf;
    this.pr = pr;
    this.rdWindow = rdWindow;
    this.remoteStart = remoteStart;
    this.remoteStartEnabled = remoteStartEnabled;
    this.remoteStartSupported = remoteStartSupported;
    this.rpWindow = rpWindow;
    this.rt = rt;
    this.sentryMode = sentryMode;
    this.sentryModeAvailable = sentryModeAvailable;
    this.softwareUpdate = softwareUpdate;
    this.speedLimitMode = speedLimitMode;
    this.timestamp = timestamp;
    this.valetMode = valetMode;
    this.valetPinNeeded = valetPinNeeded;
    this.vehicleName = vehicleName;
    this._unknownFields = Collections.unmodifiableMap(unknownFields);
  }

  @Override
  public boolean equals(Object o){
    if(this == o) return true;
    if(!(o instanceof VehicleState)) return false;

    VehicleState that = (VehicleState) o;

    if(apiVersion != that.apiVersion) return false;
    if(isCalendarSupported() != that.isCalendarSupported()) return false;
    if(getCenterDisplayState() != that.getCenterDisplayState()) return false;
    if(getDf() != that.getDf()) return false;
    if(getDr() != that.getDr()) return false;
    if(getFdWindow() != that.getFdWindow()) return false;
    if(getFpWindow() != that.getFpWindow()) return false;
    if(getFt() != that.getFt()) return false;
    if(isUserPresent() != that.isUserPresent()) return false;
    if(isLocked() != that.isLocked()) return false;
    if(isNotificationsSupported() != that.isNotificationsSupported()) return false;
    if(Double.compare(that.getOdometer(), getOdometer()) != 0) return false;
    if(isParsedCalendarSupported() != that.isParsedCalendarSupported()) return false;
    if(getPf() != that.getPf()) return false;
    if(getPr() != that.getPr()) return false;
    if(getRdWindow() != that.getRdWindow()) return false;
    if(isRemoteStart() != that.isRemoteStart()) return false;
    if(isRemoteStartEnabled() != that.isRemoteStartEnabled()) return false;
    if(isRemoteStartSupported() != that.isRemoteStartSupported()) return false;
    if(getRpWindow() != that.getRpWindow()) return false;
    if(getRt() != that.getRt()) return false;
    if(sentryMode != that.sentryMode) return false;
    if(isSentryModeAvailable() != that.isSentryModeAvailable()) return false;
    if(getTimestamp() != that.getTimestamp()) return false;
    if(isValetMode() != that.isValetMode()) return false;
    if(isValetPinNeeded() != that.isValetPinNeeded()) return false;
    if(!getAutoparkStateV3().equals(that.getAutoparkStateV3())) return false;
    if(!getCarVersion().equals(that.getCarVersion())) return false;
    if(!getMediaState().equals(that.getMediaState())) return false;
    if(!getSoftwareUpdate().equals(that.getSoftwareUpdate())) return false;
    if(!getSpeedLimitMode().equals(that.getSpeedLimitMode())) return false;
    if(!getVehicleName().equals(that.getVehicleName())) return false;
    return _unknownFields.equals(that._unknownFields);
  }

  @Override
  public int hashCode(){
    int result;
    long temp;
    result = apiVersion;
    result = 31 * result + (getAutoparkStateV3() != null ? getAutoparkStateV3().hashCode() : 0);
    result = 31 * result + (isCalendarSupported() ? 1 : 0);
    result = 31 * result + getCarVersion().hashCode();
    result = 31 * result + getCenterDisplayState();
    result = 31 * result + getDf();
    result = 31 * result + getDr();
    result = 31 * result + getFdWindow();
    result = 31 * result + getFpWindow();
    result = 31 * result + getFt();
    result = 31 * result + (isUserPresent() ? 1 : 0);
    result = 31 * result + (isLocked() ? 1 : 0);
    result = 31 * result + getMediaState().hashCode();
    result = 31 * result + (isNotificationsSupported() ? 1 : 0);
    temp = Double.doubleToLongBits(getOdometer());
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + (isParsedCalendarSupported() ? 1 : 0);
    result = 31 * result + getPf();
    result = 31 * result + getPr();
    result = 31 * result + getRdWindow();
    result = 31 * result + (isRemoteStart() ? 1 : 0);
    result = 31 * result + (isRemoteStartEnabled() ? 1 : 0);
    result = 31 * result + (isRemoteStartSupported() ? 1 : 0);
    result = 31 * result + getRpWindow();
    result = 31 * result + getRt();
    result = 31 * result + (sentryMode ? 1 : 0);
    result = 31 * result + (isSentryModeAvailable() ? 1 : 0);
    result = 31 * result + getSoftwareUpdate().hashCode();
    result = 31 * result + getSpeedLimitMode().hashCode();
    result = 31 * result + (int) (getTimestamp() ^ (getTimestamp() >>> 32));
    result = 31 * result + (isValetMode() ? 1 : 0);
    result = 31 * result + (isValetPinNeeded() ? 1 : 0);
    result = 31 * result + getVehicleName().hashCode();
    result = 31 * result + _unknownFields.hashCode();
    return result;
  }

  @Nonnull
  public Map<String,Optional<Object>> getUnknownFields(){
    return _unknownFields;
  }

  @Nonnull
  public Optional<String> getAutoparkStateV3(){
    return Optional.ofNullable(autoparkStateV3);
  }

  public boolean isCalendarSupported(){
    return calendarSupported;
  }

  @Nonnull
  public String getCarVersion(){
    return carVersion;
  }

  public int getCenterDisplayState(){
    return centerDisplayState;
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

  public boolean isUserPresent(){
    return isUserPresent;
  }

  public boolean isLocked(){
    return locked;
  }

  @Nonnull
  public MediaState getMediaState(){
    return mediaState;
  }

  public boolean isNotificationsSupported(){
    return notificationsSupported;
  }

  public int getAPIVersion(){
    return apiVersion;
  }

  public double getOdometer(){
    return odometer;
  }

  public boolean isParsedCalendarSupported(){
    return parsedCalendarSupported;
  }

  public int getPf(){
    return pf;
  }

  public int getPr(){
    return pr;
  }

  public int getRdWindow(){
    return rdWindow;
  }

  public boolean isRemoteStart(){
    return remoteStart;
  }

  public boolean isRemoteStartEnabled(){
    return remoteStartEnabled;
  }

  public boolean isRemoteStartSupported(){
    return remoteStartSupported;
  }

  public int getRpWindow(){
    return rpWindow;
  }

  public int getRt(){
    return rt;
  }

  public boolean isSentryModeEnabled(){
    return sentryMode;
  }

  public boolean isSentryModeAvailable(){
    return sentryModeAvailable;
  }

  @Nonnull
  public SoftwareUpdate getSoftwareUpdate(){
    return softwareUpdate;
  }

  @Nonnull
  public SpeedLimitMode getSpeedLimitMode(){
    return speedLimitMode;
  }

  public long getTimestamp(){
    return timestamp;
  }

  public boolean isValetMode(){
    return valetMode;
  }

  public boolean isValetPinNeeded(){
    return valetPinNeeded;
  }

  @Nonnull
  public String getVehicleName(){
    return vehicleName;
  }

  public int getFdWindow(){
    return fdWindow;
  }

  public int getFpWindow(){
    return fpWindow;
  }

  public static class Deserializer extends StdDeserializer<VehicleState>{

    private static final long serialVersionUID = -8277462100663470614L;

    @Nonnull
    private final AtomicReference<Function<Map<String,Optional<Object>>,Boolean>> unknownFieldsFunction;

    public Deserializer(){
      super(VehicleState.class);
      this.unknownFieldsFunction = new AtomicReference<>();
    }

    public Deserializer(@Nonnull AtomicReference<Function<Map<String,Optional<Object>>,Boolean>> unknownFieldsFunction){
      super(VehicleState.class);
      this.unknownFieldsFunction = unknownFieldsFunction;
    }

    @Override
    public VehicleState deserialize(
      JsonParser jsonParser, DeserializationContext deserializationContext
    ) throws IOException{

      // Get node
      ObjectNode node = jsonParser.readValueAsTree();

      // Set up set of used keys
      var usedKeysSet = new HashSet<String>();

      // Get values
      var apiVersion = getInteger(node, "api_version", usedKeysSet);

      // Warn if api version is not 8
      if(apiVersion != SUPPORTED_API_VERSION){
        // TODO warn
        System.out.println(f("API Version is '{}'. Supported API version is '{}'", apiVersion, SUPPORTED_API_VERSION));
      }

      var autoparkStateV3 = getStringNullable(node, "autopark_state_v3", usedKeysSet);
      var calendarSupported = getBoolean(node, "calendar_supported", usedKeysSet);
      var carVersion = getString(node, "car_version", usedKeysSet);
      var centerDisplayState = getInteger(node, "center_display_state", usedKeysSet);
      var df = getInteger(node, "df", usedKeysSet);
      var dr = getInteger(node, "dr", usedKeysSet);
      var fdWindow = getInteger(node, "fd_window", usedKeysSet);
      var fpWindow = getInteger(node, "fp_window", usedKeysSet);
      var ft = getInteger(node, "ft", usedKeysSet);
      var isUserPresent = getBoolean(node, "is_user_present", usedKeysSet);
      var locked = getBoolean(node, "locked", usedKeysSet);
      MediaState mediaState = getObject(jsonParser.getCodec(), "media_state", node, usedKeysSet, MediaState.class);
      var notificationsSupported = getBoolean(node, "notifications_supported", usedKeysSet);
      var odometer = getDouble(node, "odometer", usedKeysSet);
      var parsedCalendarSupported = getBoolean(node, "parsed_calendar_supported", usedKeysSet);
      var pf = getInteger(node, "pf", usedKeysSet);
      var pr = getInteger(node, "pr", usedKeysSet);
      var rdWindow = getInteger(node, "rd_window", usedKeysSet);
      var remoteStart = getBoolean(node, "remote_start", usedKeysSet);
      var remoteStartEnabled = getBoolean(node, "remote_start_enabled", usedKeysSet);
      var remoteStartSupported = getBoolean(node, "remote_start_supported", usedKeysSet);
      var rpWindow = getInteger(node, "rp_window", usedKeysSet);
      var rt = getInteger(node, "rt", usedKeysSet);
      var sentryMode = getBoolean(node, "sentry_mode", usedKeysSet);
      var sentryModeAvailable = getBoolean(node, "sentry_mode_available", usedKeysSet);
      SoftwareUpdate softwareUpdate = getObject(
        jsonParser.getCodec(),
        "software_update",
        node,
        usedKeysSet,
        SoftwareUpdate.class
      );
      SpeedLimitMode speedLimitMode = getObject(
        jsonParser.getCodec(),
        "speed_limit_mode",
        node,
        usedKeysSet,
        SpeedLimitMode.class
      );
      var timestamp = getLong(node, "timestamp", usedKeysSet);
      var valetMode = getBoolean(node, "valet_mode", usedKeysSet);
      var valetPinNeeded = getBoolean(node, "valet_pin_needed", usedKeysSet);
      var vehicleName = getString(node, "vehicle_name", usedKeysSet);

      // Get unused fields map
      var unknownFields = JacksonUtility.createUnknownFieldsMap(node, jsonParser.getCodec(), usedKeysSet);

      // Report it
      var fnc = unknownFieldsFunction.get();
      if(fnc != null && fnc.apply(unknownFields)){
        throw new IllegalArgumentException("Thrown by the unknownFieldsFunction function");
      }

      // Build and return
      return new VehicleState(
        apiVersion,
        autoparkStateV3,
        calendarSupported,
        carVersion,
        centerDisplayState,
        df,
        dr,
        fdWindow,
        fpWindow,
        ft,
        isUserPresent,
        locked,
        mediaState,
        notificationsSupported,
        odometer,
        parsedCalendarSupported,
        pf,
        pr,
        rdWindow,
        remoteStart,
        remoteStartEnabled,
        remoteStartSupported,
        rpWindow,
        rt,
        sentryMode,
        sentryModeAvailable,
        softwareUpdate,
        speedLimitMode,
        timestamp,
        valetMode,
        valetPinNeeded,
        vehicleName,
        unknownFields
      );
    }
  }
}
