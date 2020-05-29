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

  private final int apiVersion;

  @Nullable
  private final String autoparkStateV2;

  private final String autoparkStyle;

  private final boolean calendarSupported;

  private final String carVersion;

  private final int centerDisplayState; // TODO what is this

  private final int df;

  private final int dr;

  private final int ft;

  private final int pf;

  private final int pr;

  private final int rt;

  @Nullable
  private final Boolean homelinkNearby; // Could disappear from json

  @Nullable
  private final Integer homelinkDeviceCount; // Could disappear from json

  @Nullable
  private final Integer sunRoofPercentOpen;  // Could disappear from json

  private final boolean isUserPresent;

  @Nullable
  private final String lastAutoparkError;

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

  @Nullable
  private final String autoparkStateV3;

  private final int frontDriverSideWindow;

  private final int frontPassengerSideWindow;

  private final int rearDriverSideWindow;

  private final int rearPassengerSideWindow;

  private final boolean sentryModeAvailable;

  public VehicleState(
    int apiVersion,
    @Nullable String autoparkStateV2,
    String autoparkStyle,
    boolean calendarSupported,
    String carVersion,
    int centerDisplayState,
    int df,
    int dr,
    int ft,
    int pf,
    int pr,
    int rt,
    @Nullable Boolean homelinkNearby,
    @Nullable Integer homelinkDeviceCount,
    @Nullable Integer sunRoofPercentOpen,
    boolean isUserPresent,
    @Nullable String lastAutoparkError,
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
    @Nullable String autoparkStateV3,
    int frontDriverSideWindow,
    int frontPassengerSideWindow,
    int rearDriverSideWindow,
    int rearPassengerSideWindow,
    boolean sentryModeAvailable
  ){
    this.apiVersion = apiVersion;
    this.autoparkStateV2 = autoparkStateV2;
    this.autoparkStyle = autoparkStyle;
    this.calendarSupported = calendarSupported;
    this.carVersion = carVersion;
    this.centerDisplayState = centerDisplayState;
    this.df = df;
    this.dr = dr;
    this.ft = ft;
    this.pf = pf;
    this.pr = pr;
    this.rt = rt;
    this.homelinkNearby = homelinkNearby;
    this.homelinkDeviceCount = homelinkDeviceCount;
    this.sunRoofPercentOpen = sunRoofPercentOpen;
    this.isUserPresent = isUserPresent;
    this.lastAutoparkError = lastAutoparkError;
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
    this.autoparkStateV3 = autoparkStateV3;
    this.frontDriverSideWindow = frontDriverSideWindow;
    this.frontPassengerSideWindow = frontPassengerSideWindow;
    this.rearDriverSideWindow = rearDriverSideWindow;
    this.rearPassengerSideWindow = rearPassengerSideWindow;
    this.sentryModeAvailable = sentryModeAvailable;
  }

  @Nonnull
  public static VehicleState convert(@Nonnull com.ansill.tesla.api.raw.model.VehicleState state){
    return new VehicleState(
      state.getApiVersion(),
      state.getAutoparkStateV2(),
      state.getAutoparkStyle(),
      state.getCalendarSupported(),
      state.getCarVersion(),
      state.getCenterDisplayState(),
      state.getDf(),
      state.getDr(),
      state.getFt(),
      state.getPf(),
      state.getPr(),
      state.getRt(),
      state.getHomelinkNearby().orElse(null),
      state.getHomelinkDeviceCount().orElse(null),
      state.getSunRoofPercentOpen().orElse(null),
      state.getIsUserPresent(),
      state.getLastAutoparkError(),
      state.getLocked(),
      MediaState.convert(state.getMediaState()),
      state.getNotificationsSupported(),
      Quantities.getQuantity(state.getOdometer(), ImperialUnits.MILE),
      state.getParsed_calendarSupported(),
      state.getRemoteStart(),
      state.getRemoteStartEnabled(),
      state.getRemoteStartSupported(),
      state.getSentryMode(),
      SoftwareUpdate.convert(state.getSoftwareUpdate()),
      SpeedLimitMode.convert(state.getSpeedLimitMode()),
      state.getAutoparkStateV3(),
      state.getFdWindow(),
      state.getFdWindow(),
      state.getFdWindow(),
      state.getFdWindow(),
      state.isSentryModeAvailable()
    );
  }

  public int getApiVersion(){
    return apiVersion;
  }

  @Nonnull
  public Optional<String> getAutoparkStateV2(){
    return Optional.ofNullable(autoparkStateV2);
  }

  public String getAutoparkStyle(){
    return autoparkStyle;
  }

  public boolean isCalendarSupported(){
    return calendarSupported;
  }

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

  public int getPf(){
    return pf;
  }

  public int getPr(){
    return pr;
  }

  public int getRt(){
    return rt;
  }

  @Nonnull
  public Optional<Boolean> getHomelinkNearby(){
    return Optional.ofNullable(homelinkNearby);
  }

  @Nonnull
  public Optional<Integer> getHomelinkDeviceCount(){
    return Optional.ofNullable(homelinkDeviceCount);
  }

  @Nonnull
  public Optional<Integer> getSunRoofPercentOpen(){
    return Optional.ofNullable(sunRoofPercentOpen);
  }

  public boolean isUserPresent(){
    return isUserPresent;
  }

  @Nonnull
  public Optional<String> getLastAutoparkError(){
    return Optional.ofNullable(lastAutoparkError);
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
    public static MediaState convert(@Nonnull com.ansill.tesla.api.raw.model.VehicleState.MediaState state){
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
    public static SoftwareUpdate convert(@Nonnull com.ansill.tesla.api.raw.model.VehicleState.SoftwareUpdate update){
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
    public static SpeedLimitMode convert(@Nonnull com.ansill.tesla.api.raw.model.VehicleState.SpeedLimitMode mode){
      return new SpeedLimitMode(
        mode.getActive(),
        Quantities.getQuantity(mode.getCurrentLimitMph(), ImperialUnits.MILE_PER_HOUR),
        Quantities.getQuantity(mode.getMaxLimitMph(), ImperialUnits.MILE_PER_HOUR),
        Quantities.getQuantity(mode.getMinLimitMph(), ImperialUnits.MILE_PER_HOUR),
        mode.getPinCodeSet()
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
