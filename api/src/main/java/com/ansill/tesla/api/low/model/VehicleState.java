package com.ansill.tesla.api.low.model;

import com.ansill.tesla.api.model.ImperialUnits;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.measure.Quantity;
import javax.measure.quantity.Dimensionless;
import javax.measure.quantity.Length;
import javax.measure.quantity.Speed;
import java.util.Optional;

@Immutable
public final class VehicleState{

  @Nullable
  private final String autoparkStateV3;

  private final int apiVersion;

  @Nonnull
  private final CenterDisplayState centerDisplayState;

  private final boolean calendarSupported;

  private final String carVersion;

  private final int driverFront;

  private final int driverRear;

  private final int frontTrunk;

  private final int passengerFront;

  private final int passengerRear;

  private final int rearTrunk;

  public VehicleState(
    int apiVersion,
    @Nullable String autoparkStateV2,
    boolean calendarSupported,
    String carVersion,
    CenterDisplayState centerDisplayState,
    int driverFront,
    int driverRear,
    int frontTrunk,
    int passengerFront,
    int passengerRear,
    int rearTrunk,
    boolean isUserPresent,
    boolean locked,
    @Nonnull MediaState mediaState,
    boolean notificationsSupported,
    Quantity<Length> odometer,
    boolean parsedCalendarSupported,
    boolean remoteStart,
    boolean remoteStartEnabled,
    boolean remoteStartSupported,
    boolean sentryMode,
    @Nonnull SoftwareUpdate softwareUpdate,
    @Nonnull SpeedLimitMode speedLimitMode,
    int frontDriverSideWindow,
    int frontPassengerSideWindow,
    int rearDriverSideWindow,
    int rearPassengerSideWindow,
    boolean sentryModeAvailable
  ){
    this.apiVersion = apiVersion;
    this.autoparkStateV3 = autoparkStateV2;
    this.calendarSupported = calendarSupported;
    this.carVersion = carVersion;
    this.centerDisplayState = centerDisplayState;
    this.driverFront = driverFront;
    this.driverRear = driverRear;
    this.frontTrunk = frontTrunk;
    this.passengerFront = passengerFront;
    this.passengerRear = passengerRear;
    this.rearTrunk = rearTrunk;
    this.isUserPresent = isUserPresent;
    this.locked = locked;
    this.mediaState = mediaState;
    this.notificationsSupported = notificationsSupported;
    this.odometer = odometer;
    this.parsedCalendarSupported = parsedCalendarSupported;
    this.remoteStart = remoteStart;
    this.remoteStartEnabled = remoteStartEnabled;
    this.remoteStartSupported = remoteStartSupported;
    this.sentryMode = sentryMode;
    this.softwareUpdate = softwareUpdate;
    this.speedLimitMode = speedLimitMode;
    this.frontDriverSideWindow = frontDriverSideWindow;
    this.frontPassengerSideWindow = frontPassengerSideWindow;
    this.rearDriverSideWindow = rearDriverSideWindow;
    this.rearPassengerSideWindow = rearPassengerSideWindow;
    this.sentryModeAvailable = sentryModeAvailable;
  }

  private final boolean isUserPresent;

  private final boolean locked;

  @Nonnull
  private final MediaState mediaState;

  private final boolean notificationsSupported;

  private final Quantity<Length> odometer;

  private final boolean parsedCalendarSupported;

  private final boolean remoteStart;

  private final boolean remoteStartEnabled;

  private final boolean remoteStartSupported;

  private final boolean sentryMode;

  @Nonnull
  private final SoftwareUpdate softwareUpdate;

  @Nonnull
  private final SpeedLimitMode speedLimitMode;

  private final int frontDriverSideWindow;

  private final int frontPassengerSideWindow;

  private final int rearDriverSideWindow;

  private final int rearPassengerSideWindow;

  private final boolean sentryModeAvailable;

  @Nonnull
  public static VehicleState convert(@Nonnull com.ansill.tesla.api.data.model.VehicleState state){
    return new VehicleState(
      state.getAPIVersion(),
      state.getAutoparkStateV3().orElse(null),
      state.isCalendarSupported(),
      state.getCarVersion(),
      CenterDisplayState.valueOfLabel(state.getCenterDisplayState()),
      state.getDf(),
      state.getDr(),
      state.getFt(),
      state.getPf(),
      state.getPr(),
      state.getRt(),
      state.isUserPresent(),
      state.isLocked(),
      MediaState.convert(state.getMediaState()),
      state.isNotificationsSupported(),
      Quantities.getQuantity(state.getOdometer(), ImperialUnits.MILE),
      state.isParsedCalendarSupported(),
      state.isRemoteStart(),
      state.isRemoteStartEnabled(),
      state.isRemoteStartSupported(),
      state.isSentryModeEnabled(),
      SoftwareUpdate.convert(state.getSoftwareUpdate()),
      SpeedLimitMode.convert(state.getSpeedLimitMode()),
      state.getFdWindow(),
      state.getFdWindow(),
      state.getFdWindow(),
      state.getFdWindow(),
      state.isSentryModeAvailable()
    );
  }

  @Nonnull
  public CenterDisplayState getCenterDisplayState(){
    return centerDisplayState;
  }

  public int getApiVersion(){
    return apiVersion;
  }


  public boolean isCalendarSupported(){
    return calendarSupported;
  }

  public String getCarVersion(){
    return carVersion;
  }

  public int getDriverFront(){
    return driverFront;
  }

  public int getDriverRear(){
    return driverRear;
  }

  public int getFrontTrunk(){
    return frontTrunk;
  }

  public int getPassengerFront(){
    return passengerFront;
  }

  public int getPassengerRear(){
    return passengerRear;
  }

  public int getRearTrunk(){
    return rearTrunk;
  }

  public enum CenterDisplayState{
    UNKNOWN(-1),
    OFF(0),
    NORMAL_ON(2),
    CHARGING_SCREEN(3),
    SENTRY_MODE(7),
    DOG_MODE(8);
    // TODO find out camp mode

    final int value;

    CenterDisplayState(int value){
      this.value = value;
    }

    @Nonnull
    static CenterDisplayState valueOfLabel(int value){

      for(var e : values()){
        if(e == UNKNOWN) continue;
        if(e.value == value) return e;
      }


      // Otherwise return unknown
      return UNKNOWN;
    }
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

  public Quantity<Length> getOdometer(){
    return odometer;
  }

  public boolean isParsedCalendarSupported(){
    return parsedCalendarSupported;
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

  public boolean isSentryMode(){
    return sentryMode;
  }

  @Nonnull
  public SoftwareUpdate getSoftwareUpdate(){
    return softwareUpdate;
  }

  @Nonnull
  public SpeedLimitMode getSpeedLimitMode(){
    return speedLimitMode;
  }

  @Nonnull
  public Optional<String> getAutoparkStateV3(){
    return Optional.ofNullable(autoparkStateV3);
  }

  public int getFrontDriverSideWindow(){
    return frontDriverSideWindow;
  }

  public int getFrontPassengerSideWindow(){
    return frontPassengerSideWindow;
  }

  public int getRearDriverSideWindow(){
    return rearDriverSideWindow;
  }

  public int getRearPassengerSideWindow(){
    return rearPassengerSideWindow;
  }

  public boolean isSentryModeAvailable(){
    return sentryModeAvailable;
  }

  @Immutable
  public static class MediaState{

    private final boolean remoteControlEnabled;

    public MediaState(boolean remoteControlEnabled){
      this.remoteControlEnabled = remoteControlEnabled;
    }

    @Nonnull
    public static MediaState convert(@Nonnull com.ansill.tesla.api.data.model.MediaState state){
      return new MediaState(state.isRemoteControlEnabled());
    }

    public boolean isRemoteControlEnabled(){
      return remoteControlEnabled;
    }
  }

  @Immutable
  public static class SoftwareUpdate{

    @Nonnull
    private final Quantity<Dimensionless> downloadPercent;

    @Nonnull
    private final Quantity<Dimensionless> installPercent;

    @Nonnull
    private final String version;

    public SoftwareUpdate(
      @Nonnull Quantity<Dimensionless> downloadPercent,
      @Nonnull Quantity<Dimensionless> installPercent,
      @Nonnull String version
    ){
      this.downloadPercent = downloadPercent;
      this.installPercent = installPercent;
      this.version = version;
    }

    @Nonnull
    public static SoftwareUpdate convert(@Nonnull com.ansill.tesla.api.data.model.SoftwareUpdate update){
      return new SoftwareUpdate(
        Quantities.getQuantity(update.getDownloadPercent(), Units.PERCENT),
        Quantities.getQuantity(update.getInstallPercent(), Units.PERCENT),
        update.getVersion()
      );
    }

    @Nonnull
    public Quantity<Dimensionless> getInstallPercent(){
      return installPercent;
    }

    @Nonnull
    public Quantity<Dimensionless> getDownloadPercent(){
      return downloadPercent;
    }

    @Nonnull
    public String getVersion(){
      return version;
    }
  }

  @Immutable
  public static class SpeedLimitMode{

    private final boolean active;

    @Nonnull
    private final Quantity<Speed> currentSpeedLimit;

    @Nonnull
    private final Quantity<Speed> maximumSpeedLimit;

    @Nonnull
    private final Quantity<Speed> minimumSpeedLimit;

    private final boolean pinCodeSet;

    public SpeedLimitMode(
      boolean active,
      @Nonnull Quantity<Speed> currentSpeedLimit,
      @Nonnull Quantity<Speed> maximumSpeedLimit,
      @Nonnull Quantity<Speed> minimumSpeedLimit,
      boolean pinCodeSet
    ){
      this.active = active;
      this.currentSpeedLimit = currentSpeedLimit;
      this.maximumSpeedLimit = maximumSpeedLimit;
      this.minimumSpeedLimit = minimumSpeedLimit;
      this.pinCodeSet = pinCodeSet;
    }

    @Nonnull
    public static SpeedLimitMode convert(@Nonnull com.ansill.tesla.api.data.model.SpeedLimitMode mode){
      return new SpeedLimitMode(
        mode.isActive(),
        Quantities.getQuantity(mode.getCurrentLimitMph(), ImperialUnits.MILE_PER_HOUR),
        Quantities.getQuantity(mode.getMaxLimitMph(), ImperialUnits.MILE_PER_HOUR),
        Quantities.getQuantity(mode.getMinLimitMph(), ImperialUnits.MILE_PER_HOUR),
        mode.isPinCodeSet()
      );
    }

    public boolean isActive(){
      return active;
    }

    @Nonnull
    public Quantity<Speed> getCurrentSpeedLimit(){
      return currentSpeedLimit;
    }

    @Nonnull
    public Quantity<Speed> getMaximumSpeedLimit(){
      return maximumSpeedLimit;
    }

    @Nonnull
    public Quantity<Speed> getMinimumSpeedLimit(){
      return minimumSpeedLimit;
    }

    public boolean isPinCodeSet(){
      return pinCodeSet;
    }
  }

}
