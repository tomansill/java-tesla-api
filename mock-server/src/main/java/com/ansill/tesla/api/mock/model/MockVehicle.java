
package com.ansill.tesla.api.mock.model;

import com.ansill.tesla.api.data.model.CompleteVehicle;
import com.ansill.tesla.api.data.model.GuiSettings;
import com.ansill.tesla.api.data.model.Vehicle;
import com.ansill.utility.Utility;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static com.ansill.tesla.api.mock.MockUtility.generateLegibleString;
import static com.ansill.tesla.api.mock.MockUtility.generateString;
import static com.ansill.utility.Utility.simpleToString;

@SuppressWarnings("unused")
public class MockVehicle{

  private boolean hidden = false;

  public void suspend(){
    hidden = true;
  }

  public void unsuspend(){
    hidden = false;
  }

  public boolean isHidden(){
    return hidden;
  }

  @Nonnull
  private final String vin;

  private long id;

  private long vehicleId;

  private long userId;

  @Nonnull
  private String displayName;

  @Nonnull
  private String optionCodes;

  @Nonnull
  private String color;

  @Nonnull
  private List<String> tokens;

  @Nonnull
  private String state;

  private boolean inService;

  private boolean calendarEnabled;

  private int apiVersion;

  @Nullable
  private String backseatToken; // TODO confirm String

  @Nullable
  private String backseatTokenUpdatedAt; // TODO confirm String

  @Nonnull
  private DriveState driveState;

  @Nonnull
  private ClimateState climateState;

  @Nonnull
  private ChargeState chargeState;

  @Nonnull
  private GUISettings guiSettings;

  @Nonnull
  private VehicleState vehicleState;

  @Nonnull
  private VehicleConfig vehicleConfig;

  public MockVehicle(
    long id,
    long vehicleId,
    long userId,
    @Nonnull String vin,
    @Nonnull String displayName,
    @Nonnull String optionCodes,
    @Nonnull String color,
    @Nonnull List<String> tokens,
    @Nonnull String state,
    boolean inService,
    boolean calendarEnabled,
    int apiVersion,
    @Nullable String backseatToken,
    @Nullable String backseatTokenUpdatedAt,
    @Nonnull DriveState driveState,
    @Nonnull ClimateState climateState,
    @Nonnull ChargeState chargeState,
    @Nonnull GUISettings guiSettings,
    @Nonnull VehicleState vehicleState,
    @Nonnull VehicleConfig vehicleConfig
  ){
    this.id = id;
    this.vehicleId = vehicleId;
    this.userId = userId;
    this.vin = vin;
    this.displayName = displayName;
    this.optionCodes = optionCodes;
    this.color = color;
    this.tokens = tokens;
    this.state = state;
    this.inService = inService;
    this.calendarEnabled = calendarEnabled;
    this.apiVersion = apiVersion;
    this.backseatToken = backseatToken;
    this.backseatTokenUpdatedAt = backseatTokenUpdatedAt;
    this.driveState = driveState;
    this.climateState = climateState;
    this.chargeState = chargeState;
    this.guiSettings = guiSettings;
    this.vehicleState = vehicleState;
    this.vehicleConfig = vehicleConfig;
  }

  public static MockVehicle randomParked(String id){
    var random = new Random();
    return new MockVehicle(
      Math.abs(Long.parseLong(id)),
      Math.abs(random.nextLong()),
      Math.abs(random.nextLong()),
      generateString(32),
      generateLegibleString(),
      generateString(32),
      generateString(5),
      Collections.singletonList(generateString(32)),
      "online",
      false,
      true,
      com.ansill.tesla.api.data.model.VehicleState.SUPPORTED_API_VERSION,
      null,
      null,
      DriveState.generateParked(),
      ClimateState.generate(),
      ChargeState.generateUnplugged(),
      GUISettings.generate(),
      VehicleState.generate(),
      VehicleConfig.generate()
    );
  }

  public long getUserId(){
    return userId;
  }

  void setUserId(long userId){
    this.userId = userId;
  }

  public long getId(){
    return id;
  }

  public void setId(long id){
    this.id = id;
  }

  public long getVehicleId(){
    return vehicleId;
  }

  public void setVehicleId(long vehicleId){
    this.vehicleId = vehicleId;
  }

  public String getVIN(){
    return vin;
  }

  @Nonnull
  public String getOptionCodes(){
    return optionCodes;
  }

  public void setOptionCodes(@Nonnull String optionCodes){
    this.optionCodes = optionCodes;
  }

  @Nonnull
  public String getColor(){
    return color;
  }

  public void setColor(@Nonnull String color){
    this.color = color;
  }

  public List<String> getTokens(){
    return Utility.unmodifiableList(tokens);
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
    return inService;
  }

  public void setInService(boolean inService){
    this.inService = inService;
  }

  @Nonnull
  public String getIdString(){
    return id + "";
  }

  public boolean isCalendarEnabled(){
    return calendarEnabled;
  }

  public void setCalendarEnabled(boolean calendarEnabled){
    this.calendarEnabled = calendarEnabled;
  }

  public int getApiVersion(){
    return apiVersion;
  }

  public void setApiVersion(int apiVersion){
    this.apiVersion = apiVersion;
  }

  @Nullable
  public String getBackseatToken(){
    return backseatToken;
  }

  public void setBackseatToken(@Nullable String backseatToken){
    this.backseatToken = backseatToken;
  }

  @Nullable
  public String getBackseatTokenUpdatedAt(){
    return backseatTokenUpdatedAt;
  }

  public void setBackseatTokenUpdatedAt(@Nullable String backseatTokenUpdatedAt){
    this.backseatTokenUpdatedAt = backseatTokenUpdatedAt;
  }

  @Override
  public String toString(){
    return simpleToString(this);
  }

  @Nonnull
  public String getDisplayName(){
    return displayName;
  }

  public void setDisplayName(@Nonnull String displayName){
    this.displayName = displayName;
  }

  @Nonnull
  public DriveState getDriveState(){
    return driveState;
  }

  public void setDriveState(@Nonnull DriveState driveState){
    this.driveState = driveState;
  }

  @Nonnull
  public ClimateState getClimateState(){
    return climateState;
  }

  public void setClimateState(@Nonnull ClimateState climateState){
    this.climateState = climateState;
  }

  @Nonnull
  public ChargeState getChargeState(){
    return chargeState;
  }

  public void setChargeState(@Nonnull ChargeState chargeState){
    this.chargeState = chargeState;
  }

  @Nonnull
  public GUISettings getGuiSettings(){
    return guiSettings;
  }

  public void setGuiSettings(@Nonnull GUISettings guiSettings){
    this.guiSettings = guiSettings;
  }

  @Nonnull
  public VehicleState getVehicleState(){
    return vehicleState;
  }

  public void setVehicleState(@Nonnull VehicleState vehicleState){
    this.vehicleState = vehicleState;
  }

  @Nonnull
  public VehicleConfig getVehicleConfig(){
    return vehicleConfig;
  }

  public void setVehicleConfig(@Nonnull VehicleConfig vehicleConfig){
    this.vehicleConfig = vehicleConfig;
  }

  public Vehicle convert(){
    return new Vehicle(
      id,
      vehicleId,
      vin,
      displayName,
      optionCodes,
      color,
      tokens,
      state,
      inService,
      calendarEnabled,
      apiVersion,
      backseatToken,
      backseatTokenUpdatedAt
    );
  }

  public CompleteVehicle convertComplete(){
    return new CompleteVehicle(
      id,
      vehicleId,
      vin,
      displayName,
      optionCodes,
      color,
      tokens,
      state,
      inService,
      calendarEnabled,
      apiVersion,
      backseatToken,
      backseatTokenUpdatedAt,
      climateState.convert(),
      driveState.convert(),
      chargeState.convert(),
      guiSettings.convert(),
      vehicleConfig.convert(),
      vehicleState.convert()
    );
  }

  public static class DriveState{

    private long gpsAsOf;

    private int heading;

    private double latitude;

    private double longitude;

    private double nativeLatitude;

    private int nativeLocationSupported;

    private double nativeLongitude;

    private String nativeType;

    private int power;

    @Nullable
    private String shiftState;

    @Nullable
    private Long speed;

    private long timestamp;

    public DriveState(
      long gpsAsOf,
      int heading,
      double latitude,
      double longitude,
      double nativeLatitude,
      int nativeLocationSupported,
      double nativeLongitude,
      String nativeType,
      int power,
      @Nullable String shiftState,
      @Nullable Long speed,
      long timestamp
    ){
      this.gpsAsOf = gpsAsOf;
      this.heading = heading;
      this.latitude = latitude;
      this.longitude = longitude;
      this.nativeLatitude = nativeLatitude;
      this.nativeLocationSupported = nativeLocationSupported;
      this.nativeLongitude = nativeLongitude;
      this.nativeType = nativeType;
      this.power = power;
      this.shiftState = shiftState;
      this.speed = speed;
      this.timestamp = timestamp;
    }

    @Nonnull
    public static DriveState generateParked(){
      var rand = new Random();
      return new DriveState(
        rand.nextLong(),
        rand.nextInt(360),
        rand.nextDouble(),
        rand.nextDouble(),
        rand.nextDouble(),
        0,
        rand.nextDouble(),
        "wgs",
        0,
        null,
        null,
        Instant.now().toEpochMilli()
      );
    }

    public long getGpsAsOf(){
      return gpsAsOf;
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

    public void setGpsAsOf(long gpsAsOf){
      this.gpsAsOf = gpsAsOf;
    }

    public double getNativeLatitude(){
      return nativeLatitude;
    }

    public void setNativeLatitude(double nativeLatitude){
      this.nativeLatitude = nativeLatitude;
    }

    public int getNativeLocationSupported(){
      return nativeLocationSupported;
    }

    public int getPower(){
      return power;
    }

    public void setPower(int power){
      this.power = power;
    }

    public void setNativeLocationSupported(int nativeLocationSupported){
      this.nativeLocationSupported = nativeLocationSupported;
    }

    public double getNativeLongitude(){
      return nativeLongitude;
    }

    public void setNativeLongitude(double nativeLongitude){
      this.nativeLongitude = nativeLongitude;
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

    public String getNativeType(){
      return nativeType;
    }

    public void setNativeType(String nativeType){
      this.nativeType = nativeType;
    }

    @Nonnull
    public Optional<String> getShiftState(){
      return Optional.ofNullable(shiftState);
    }

    public void setShiftState(@Nullable String shiftState){
      this.shiftState = shiftState;
    }

    @Nonnull
    public Optional<Long> getSpeed(){
      return Optional.ofNullable(speed);
    }

    public void setSpeed(@Nullable Long speed){
      this.speed = speed;
    }

    public com.ansill.tesla.api.data.model.DriveState convert(){
      return new com.ansill.tesla.api.data.model.DriveState(
        gpsAsOf,
        heading,
        latitude,
        longitude,
        nativeLatitude,
        nativeLocationSupported,
        nativeLongitude,
        nativeType,
        power,
        shiftState,
        speed,
        timestamp
      );
    }
  }

  public static class ClimateState{

    private boolean batteryHeater;

    private boolean batteryHeaterNoPower;

    private String climateKeeperMode;

    private double driverTempSetting;

    private int fanStatus;

    private double insideTemp;

    @Nullable
    private Boolean isAutoConditioningOn;

    private boolean isClimateOn;

    private boolean isFrontDefrosterOn;

    private boolean isPreconditioning;

    private boolean isRearDefrosterOn;

    private int leftTempDirection;

    private double maxAvailTemp;

    private double minAvailTemp;

    private double outsideTemp;

    private double passengerTempSetting;

    private boolean remoteHeaterControlEnabled;

    private int rightTempDirection;

    @Nullable
    private Integer seatHeaterLeft; // Entry could disapppear from JSON

    @Nullable
    private Integer seatHeaterRearCenter; // Entry could disapppear from JSON

    @Nullable
    private Integer seatHeaterRearLeft; // Entry could disapppear from JSON

    @Nullable
    private Integer seatHeaterLeftBack; // Entry could disapppear from JSON

    @Nullable
    private Integer seatHeaterRearRight; // Entry could disapppear from JSON

    @Nullable
    private Integer seatHeaterRightBack; // Entry could disapppear from JSON

    @Nullable
    private Integer seatHeaterRight; // Entry could disapppear from JSON

    @Nullable
    private Boolean sideMirrorHeaters; // Entry could disappear from JSON

    @Nullable
    private Boolean smartPreconditioning; // Entry could disappear from JSON

    @Nullable
    private Boolean steeringWheelHeater; // Entry could disappear from JSON

    private long timestamp;

    @Nullable
    private Boolean wiperBladeHeater; // Entry could disappear from JSON

    private int defrostMode;

    public ClimateState(
      boolean batteryHeater,
      boolean batteryHeaterNoPower,
      String climateKeeperMode,
      double driverTempSetting,
      int fanStatus,
      double insideTemp,
      @Nullable Boolean isAutoConditioningOn,
      boolean isClimateOn,
      boolean isFrontDefrosterOn,
      boolean isPreconditioning,
      boolean isRearDefrosterOn,
      int leftTempDirection,
      double maxAvailTemp,
      double minAvailTemp,
      double outsideTemp,
      double passengerTempSetting,
      boolean remoteHeaterControlEnabled,
      int rightTempDirection,
      @Nullable Integer seatHeaterLeft,
      @Nullable Integer seatHeaterRearCenter,
      @Nullable Integer seatHeaterRearLeft,
      @Nullable Integer seatHeaterLeftBack,
      @Nullable Integer seatHeaterRearRight,
      @Nullable Integer seatHeaterRightBack,
      @Nullable Integer seatHeaterRight,
      @Nullable Boolean sideMirrorHeaters,
      @Nullable Boolean smartPreconditioning,
      @Nullable Boolean steeringWheelHeater,
      long timestamp,
      @Nullable Boolean wiperBladeHeater,
      int defrostMode
    ){
      this.batteryHeater = batteryHeater;
      this.batteryHeaterNoPower = batteryHeaterNoPower;
      this.climateKeeperMode = climateKeeperMode;
      this.driverTempSetting = driverTempSetting;
      this.fanStatus = fanStatus;
      this.insideTemp = insideTemp;
      this.isAutoConditioningOn = isAutoConditioningOn;
      this.isClimateOn = isClimateOn;
      this.isFrontDefrosterOn = isFrontDefrosterOn;
      this.isPreconditioning = isPreconditioning;
      this.isRearDefrosterOn = isRearDefrosterOn;
      this.leftTempDirection = leftTempDirection;
      this.maxAvailTemp = maxAvailTemp;
      this.minAvailTemp = minAvailTemp;
      this.outsideTemp = outsideTemp;
      this.passengerTempSetting = passengerTempSetting;
      this.remoteHeaterControlEnabled = remoteHeaterControlEnabled;
      this.rightTempDirection = rightTempDirection;
      this.seatHeaterLeft = seatHeaterLeft;
      this.seatHeaterRearCenter = seatHeaterRearCenter;
      this.seatHeaterRearLeft = seatHeaterRearLeft;
      this.seatHeaterLeftBack = seatHeaterLeftBack;
      this.seatHeaterRearRight = seatHeaterRearRight;
      this.seatHeaterRightBack = seatHeaterRightBack;
      this.seatHeaterRight = seatHeaterRight;
      this.sideMirrorHeaters = sideMirrorHeaters;
      this.smartPreconditioning = smartPreconditioning;
      this.steeringWheelHeater = steeringWheelHeater;
      this.timestamp = timestamp;
      this.wiperBladeHeater = wiperBladeHeater;
      this.defrostMode = defrostMode;
    }

    public static ClimateState generate(){
      var random = new Random();
      return new ClimateState(
        false,
        false,
        "off",
        0,
        0,
        random.nextDouble(),
        random.nextBoolean(),
        random.nextBoolean(),
        random.nextBoolean(),
        random.nextBoolean(),
        random.nextBoolean(),
        random.nextInt(),
        random.nextDouble(),
        random.nextDouble(),
        random.nextDouble(),
        random.nextDouble(),
        random.nextBoolean(),
        random.nextInt(),
        random.nextInt(),
        random.nextInt(),
        random.nextInt(),
        random.nextInt(),
        random.nextInt(),
        random.nextInt(),
        random.nextInt(),
        random.nextBoolean(),
        random.nextBoolean(),
        random.nextBoolean(),
        random.nextLong(),
        random.nextBoolean(),
        random.nextInt()
      );
    }

    public int getDefrostMode(){
      return defrostMode;
    }

    public void setDefrostMode(int defrostMode){
      this.defrostMode = defrostMode;
    }

    public boolean getBatteryHeater(){
      return batteryHeater;
    }

    public void setBatteryHeater(boolean batteryHeater){
      this.batteryHeater = batteryHeater;
    }

    public boolean getBatteryHeaterNoPower(){
      return batteryHeaterNoPower;
    }

    public void setBatteryHeaterNoPower(boolean batteryHeaterNoPower){
      this.batteryHeaterNoPower = batteryHeaterNoPower;
    }

    public String getClimateKeeperMode(){
      return climateKeeperMode;
    }

    public void setClimateKeeperMode(String climateKeeperMode){
      this.climateKeeperMode = climateKeeperMode;
    }

    public double getDriverTempSetting(){
      return driverTempSetting;
    }

    public void setDriverTempSetting(double driverTempSetting){
      this.driverTempSetting = driverTempSetting;
    }

    public int getFanStatus(){
      return fanStatus;
    }

    public void setFanStatus(int fanStatus){
      this.fanStatus = fanStatus;
    }

    @Nonnull
    public Optional<Double> getInsideTemp(){
      return Optional.ofNullable(insideTemp);
    }

    public void setInsideTemp(double insideTemp){
      this.insideTemp = insideTemp;
    }

    public Optional<Boolean> getIsAutoConditioningOn(){
      return Optional.ofNullable(isAutoConditioningOn);
    }

    public void setIsAutoConditioningOn(@Nullable Boolean isAutoConditioningOn){
      this.isAutoConditioningOn = isAutoConditioningOn;
    }

    public boolean getIsClimateOn(){
      return isClimateOn;
    }

    public void setIsClimateOn(boolean isClimateOn){
      this.isClimateOn = isClimateOn;
    }

    public boolean getIsFrontDefrosterOn(){
      return isFrontDefrosterOn;
    }

    public void setIsFrontDefrosterOn(boolean isFrontDefrosterOn){
      this.isFrontDefrosterOn = isFrontDefrosterOn;
    }

    public boolean getIsPreconditioning(){
      return isPreconditioning;
    }

    public void setIsPreconditioning(boolean isPreconditioning){
      this.isPreconditioning = isPreconditioning;
    }

    public boolean getIsRearDefrosterOn(){
      return isRearDefrosterOn;
    }

    public void setIsRearDefrosterOn(boolean isRearDefrosterOn){
      this.isRearDefrosterOn = isRearDefrosterOn;
    }

    public int getLeftTempDirection(){
      return leftTempDirection;
    }

    public void setLeftTempDirection(int leftTempDirection){
      this.leftTempDirection = leftTempDirection;
    }

    public double getMaxAvailTemp(){
      return maxAvailTemp;
    }

    public void setMaxAvailTemp(double maxAvailTemp){
      this.maxAvailTemp = maxAvailTemp;
    }

    public double getMinAvailTemp(){
      return minAvailTemp;
    }

    public long getTimestamp(){
      return timestamp;
    }

    public void setTimestamp(long timestamp){
      this.timestamp = timestamp;
    }

    public void setMinAvailTemp(double minAvailTemp){
      this.minAvailTemp = minAvailTemp;
    }

    @Override
    public String toString(){
      return simpleToString(this);
    }

    @Nonnull
    public Optional<Double> getOutsideTemp(){
      return Optional.ofNullable(outsideTemp);
    }

    public void setOutsideTemp(@Nullable Double outsideTemp){
      this.outsideTemp = outsideTemp;
    }

    public double getPassengerTempSetting(){
      return passengerTempSetting;
    }

    public void setPassengerTempSetting(double passengerTempSetting){
      this.passengerTempSetting = passengerTempSetting;
    }

    public boolean getRemoteHeaterControlEnabled(){
      return remoteHeaterControlEnabled;
    }

    public void setRemoteHeaterControlEnabled(boolean remoteHeaterControlEnabled){
      this.remoteHeaterControlEnabled = remoteHeaterControlEnabled;
    }

    public int getRightTempDirection(){
      return rightTempDirection;
    }

    public void setRightTempDirection(int rightTempDirection){
      this.rightTempDirection = rightTempDirection;
    }

    @Nonnull
    public Optional<Integer> getSeatHeaterLeft(){
      return Optional.ofNullable(seatHeaterLeft);
    }

    public void setSeatHeaterLeft(@Nullable Integer seatHeaterLeft){
      this.seatHeaterLeft = seatHeaterLeft;
    }

    @Nonnull
    public Optional<Integer> getSeatHeaterRearCenter(){
      return Optional.ofNullable(seatHeaterRearCenter);
    }

    public void setSeatHeaterRearCenter(@Nullable Integer seatHeaterRearCenter){
      this.seatHeaterRearCenter = seatHeaterRearCenter;
    }

    @Nonnull
    public Optional<Integer> getSeatHeaterRearLeft(){
      return Optional.ofNullable(seatHeaterRearLeft);
    }

    public void setSeatHeaterRearLeft(@Nullable Integer seatHeaterRearLeft){
      this.seatHeaterRearLeft = seatHeaterRearLeft;
    }

    @Nonnull
    public Optional<Integer> getSeatHeaterLeftBack(){
      return Optional.ofNullable(seatHeaterLeftBack);
    }

    public void setSeatHeaterLeftBack(@Nullable Integer seatHeaterLeftBack){
      this.seatHeaterLeftBack = seatHeaterLeftBack;
    }

    @Nonnull
    public Optional<Integer> getSeatHeaterRearRight(){
      return Optional.ofNullable(seatHeaterRearRight);
    }

    public void setSeatHeaterRearRight(@Nullable Integer seatHeaterRearRight){
      this.seatHeaterRearRight = seatHeaterRearRight;
    }

    @Nonnull
    public Optional<Integer> getSeatHeaterRightBack(){
      return Optional.ofNullable(seatHeaterRightBack);
    }

    public void setSeatHeaterRightBack(@Nullable Integer seatHeaterRightBack){
      this.seatHeaterRightBack = seatHeaterRightBack;
    }

    @Nonnull
    public Optional<Integer> getSeatHeaterRight(){
      return Optional.ofNullable(seatHeaterRight);
    }

    public void setSeatHeaterRight(@Nullable Integer seatHeaterRight){
      this.seatHeaterRight = seatHeaterRight;
    }

    @Nonnull
    public Optional<Boolean> getSideMirrorHeaters(){
      return Optional.ofNullable(sideMirrorHeaters);
    }

    public void setSideMirrorHeaters(@Nullable Boolean sideMirrorHeaters){
      this.sideMirrorHeaters = sideMirrorHeaters;
    }

    @Nonnull
    public Optional<Boolean> getSmartPreconditioning(){
      return Optional.ofNullable(smartPreconditioning);
    }

    public void setSmartPreconditioning(@Nullable Boolean smartPreconditioning){
      this.smartPreconditioning = smartPreconditioning;
    }

    @Nonnull
    public Optional<Boolean> getSteeringWheelHeater(){
      return Optional.ofNullable(steeringWheelHeater);
    }

    public void setSteeringWheelHeater(@Nullable Boolean steeringWheelHeater){
      this.steeringWheelHeater = steeringWheelHeater;
    }

    @Nonnull
    public Optional<Boolean> getWiperBladeHeater(){
      return Optional.ofNullable(wiperBladeHeater);
    }

    public void setWiperBladeHeater(@Nullable Boolean wiperBladeHeater){
      this.wiperBladeHeater = wiperBladeHeater;
    }

    public com.ansill.tesla.api.data.model.ClimateState convert(){
      return new com.ansill.tesla.api.data.model.ClimateState(
        batteryHeater,
        batteryHeaterNoPower,
        climateKeeperMode,
        defrostMode,
        driverTempSetting,
        fanStatus,
        insideTemp,
        isAutoConditioningOn,
        isClimateOn,
        isFrontDefrosterOn,
        isPreconditioning,
        isRearDefrosterOn,
        leftTempDirection,
        maxAvailTemp,
        minAvailTemp,
        outsideTemp,
        passengerTempSetting,
        remoteHeaterControlEnabled,
        rightTempDirection,
        seatHeaterLeft,
        seatHeaterRearCenter,
        seatHeaterRearLeft,
        seatHeaterRearRight,
        seatHeaterRight,
        seatHeaterLeftBack,
        seatHeaterRightBack,
        sideMirrorHeaters,
        timestamp,
        wiperBladeHeater
      );
    }
  }

  public static class ChargeState{
    private boolean batteryHeaterOn;

    private int batteryLevel;

    private double batteryRange;

    private int chargeCurrentRequest;

    private int chargeCurrentRequestMax;

    private boolean chargeEnableRequest;

    private double chargeEnergyAdded;

    private int chargeLimitSoc;

    private int chargeLimitSocMax;

    private int chargeLimitSocMin;

    private int chargeLimitSocStd;

    private double chargeMilesAddedIdeal;

    private double chargeMilesAddedRated;

    private boolean chargePortColdWeatherMode;

    private boolean chargePortDoorOpen;

    private String chargePortLatch;

    private double chargeRate;

    private boolean chargeToMaxRange;

    private int chargerActualCurrent;

    private String chargerPhases;

    private int chargerPilotCurrent;

    private int chargerPower;

    private int chargerVoltage;

    private String chargingState;

    private String connChargeCable;

    private double estBatteryRange;

    private String fastChargerBrand;

    private boolean fastChargerPresent;

    private long minutesToFullCharge;

    private String fastChargerType;

    private double idealBatteryRange;

    private boolean managedChargingActive;

    private String managedChargingStartTime;

    private boolean managedChargingUserCanceled;

    private int maxRangeChargeCounter;

    private Boolean notEnoughPowerToHeat;

    private boolean scheduledChargingPending;

    @Nullable
    private String scheduledChargingStartTime;

    private double timeToFullCharge;

    private long timestamp;

    private int usableBatteryLevel;

    private String userChargeEnableRequest;

    private boolean tripCharging;

    public ChargeState(
      boolean batteryHeaterOn,
      int batteryLevel,
      double batteryRange,
      int chargeCurrentRequest,
      int chargeCurrentRequestMax,
      boolean chargeEnableRequest,
      double chargeEnergyAdded,
      int chargeLimitSoc,
      int chargeLimitSocMax,
      int chargeLimitSocMin,
      int chargeLimitSocStd,
      double chargeMilesAddedIdeal,
      double chargeMilesAddedRated,
      boolean chargePortColdWeatherMode,
      boolean chargePortDoorOpen,
      @Nonnull String chargePortLatch,
      double chargeRate,
      boolean chargeToMaxRange,
      int chargerActualCurrent,
      @Nullable String chargerPhases,
      int chargerPilotCurrent,
      int chargerPower,
      int chargerVoltage,
      @Nullable String chargingState,
      @Nullable String connChargeCable,
      double estBatteryRange,
      @Nullable String fastChargerBrand,
      boolean fastChargerPresent,
      long minutesToFullCharge, String fastChargerType,
      double idealBatteryRange,
      boolean managedChargingActive,
      @Nullable String managedChargingStartTime,
      boolean managedChargingUserCanceled,
      int maxRangeChargeCounter,
      @Nullable Boolean notEnoughPowerToHeat,
      boolean scheduledChargingPending,
      @Nullable String scheduledChargingStartTime,
      double timeToFullCharge,
      long timestamp,
      int usableBatteryLevel,
      @Nullable String userChargeEnableRequest,
      boolean tripCharging
    ){
      this.batteryHeaterOn = batteryHeaterOn;
      this.batteryLevel = batteryLevel;
      this.batteryRange = batteryRange;
      this.chargeCurrentRequest = chargeCurrentRequest;
      this.chargeCurrentRequestMax = chargeCurrentRequestMax;
      this.chargeEnableRequest = chargeEnableRequest;
      this.chargeEnergyAdded = chargeEnergyAdded;
      this.chargeLimitSoc = chargeLimitSoc;
      this.chargeLimitSocMax = chargeLimitSocMax;
      this.chargeLimitSocMin = chargeLimitSocMin;
      this.chargeLimitSocStd = chargeLimitSocStd;
      this.chargeMilesAddedIdeal = chargeMilesAddedIdeal;
      this.chargeMilesAddedRated = chargeMilesAddedRated;
      this.chargePortColdWeatherMode = chargePortColdWeatherMode;
      this.chargePortDoorOpen = chargePortDoorOpen;
      this.chargePortLatch = chargePortLatch;
      this.chargeRate = chargeRate;
      this.chargeToMaxRange = chargeToMaxRange;
      this.chargerActualCurrent = chargerActualCurrent;
      this.chargerPhases = chargerPhases;
      this.chargerPilotCurrent = chargerPilotCurrent;
      this.chargerPower = chargerPower;
      this.chargerVoltage = chargerVoltage;
      this.chargingState = chargingState;
      this.connChargeCable = connChargeCable;
      this.estBatteryRange = estBatteryRange;
      this.fastChargerBrand = fastChargerBrand;
      this.fastChargerPresent = fastChargerPresent;
      this.minutesToFullCharge = minutesToFullCharge;
      this.fastChargerType = fastChargerType;
      this.idealBatteryRange = idealBatteryRange;
      this.managedChargingActive = managedChargingActive;
      this.managedChargingStartTime = managedChargingStartTime;
      this.managedChargingUserCanceled = managedChargingUserCanceled;
      this.maxRangeChargeCounter = maxRangeChargeCounter;
      this.notEnoughPowerToHeat = notEnoughPowerToHeat;
      this.scheduledChargingPending = scheduledChargingPending;
      this.scheduledChargingStartTime = scheduledChargingStartTime;
      this.timeToFullCharge = timeToFullCharge;
      this.timestamp = timestamp;
      this.usableBatteryLevel = usableBatteryLevel;
      this.userChargeEnableRequest = userChargeEnableRequest;
      this.tripCharging = tripCharging;
    }

    public static ChargeState generateUnplugged(){
      var random = new Random();
      return new ChargeState(
        false,
        random.nextInt(100),
        random.nextDouble(),
        0,
        0,
        true,
        80,
        100,
        50,
        80,
        0,
        0,
        0,
        false,
        false,
        "ENGAGED",
        0,
        false,
        0,
        "",
        0,
        0,
        0,
        "disconnected",
        null,
        random.nextInt(),
        null,
        false,
        0,
        null,
        random.nextDouble(),
        false,
        null,
        false,
        0,
        false,
        false,
        null,
        0,
        Instant.now().toEpochMilli(),
        random.nextInt(),
        null,
        false
      );
    }

    public boolean isTripCharging(){
      return tripCharging;
    }

    public void setTripCharging(boolean tripCharging){
      this.tripCharging = tripCharging;
    }

    public long getMinutesToFullCharge(){
      return minutesToFullCharge;
    }

    public void setMinutesToFullCharge(long minutesToFullCharge){
      this.minutesToFullCharge = minutesToFullCharge;
    }

    public double getBatteryRange(){
      return batteryRange;
    }

    public void setBatteryRange(double batteryRange){
      this.batteryRange = batteryRange;
    }

    public int getChargeCurrentRequest(){
      return chargeCurrentRequest;
    }

    public void setChargeCurrentRequest(int chargeCurrentRequest){
      this.chargeCurrentRequest = chargeCurrentRequest;
    }

    public int getChargeCurrentRequestMax(){
      return chargeCurrentRequestMax;
    }

    public void setChargeCurrentRequestMax(int chargeCurrentRequestMax){
      this.chargeCurrentRequestMax = chargeCurrentRequestMax;
    }

    public boolean isChargeEnableRequest(){
      return chargeEnableRequest;
    }

    public void setChargeEnableRequest(boolean chargeEnableRequest){
      this.chargeEnableRequest = chargeEnableRequest;
    }

    public double getChargeEnergyAdded(){
      return chargeEnergyAdded;
    }

    public void setChargeEnergyAdded(double chargeEnergyAdded){
      this.chargeEnergyAdded = chargeEnergyAdded;
    }

    public int getChargeLimitSoc(){
      return chargeLimitSoc;
    }

    public void setChargeLimitSoc(int chargeLimitSoc){
      this.chargeLimitSoc = chargeLimitSoc;
    }

    public int getChargeLimitSocMax(){
      return chargeLimitSocMax;
    }

    public void setChargeLimitSocMax(int chargeLimitSocMax){
      this.chargeLimitSocMax = chargeLimitSocMax;
    }

    public int getChargeLimitSocMin(){
      return chargeLimitSocMin;
    }

    public void setChargeLimitSocMin(int chargeLimitSocMin){
      this.chargeLimitSocMin = chargeLimitSocMin;
    }

    public int getChargeLimitSocStd(){
      return chargeLimitSocStd;
    }

    public void setChargeLimitSocStd(int chargeLimitSocStd){
      this.chargeLimitSocStd = chargeLimitSocStd;
    }

    public double getChargeMilesAddedIdeal(){
      return chargeMilesAddedIdeal;
    }

    public void setChargeMilesAddedIdeal(double chargeMilesAddedIdeal){
      this.chargeMilesAddedIdeal = chargeMilesAddedIdeal;
    }

    public double getChargeMilesAddedRated(){
      return chargeMilesAddedRated;
    }

    public void setChargeMilesAddedRated(double chargeMilesAddedRated){
      this.chargeMilesAddedRated = chargeMilesAddedRated;
    }

    public boolean getChargePortColdWeatherMode(){
      return chargePortColdWeatherMode;
    }

    public void setChargePortColdWeatherMode(boolean chargePortColdWeatherMode){
      this.chargePortColdWeatherMode = chargePortColdWeatherMode;
    }

    public boolean getChargePortDoorOpen(){
      return chargePortDoorOpen;
    }

    public void setChargePortDoorOpen(boolean chargePortDoorOpen){
      this.chargePortDoorOpen = chargePortDoorOpen;
    }

    public String getChargePortLatch(){
      return chargePortLatch;
    }

    public void setChargePortLatch(String chargePortLatch){
      this.chargePortLatch = chargePortLatch;
    }

    public double getChargeRate(){
      return chargeRate;
    }

    public void setChargeRate(double chargeRate){
      this.chargeRate = chargeRate;
    }

    public boolean getChargeToMaxRange(){
      return chargeToMaxRange;
    }

    public void setChargeToMaxRange(boolean chargeToMaxRange){
      this.chargeToMaxRange = chargeToMaxRange;
    }

    public int getChargerActualCurrent(){
      return chargerActualCurrent;
    }

    public void setChargerActualCurrent(int chargerActualCurrent){
      this.chargerActualCurrent = chargerActualCurrent;
    }

    public long getTimestamp(){
      return timestamp;
    }

    public void setTimestamp(long timestamp){
      this.timestamp = timestamp;
    }

    public String getChargerPhases(){
      return chargerPhases;
    }

    public void setChargerPhases(String chargerPhases){
      this.chargerPhases = chargerPhases;
    }

    public int getChargePilotCurrent(){
      return chargerPilotCurrent;
    }

    public int getChargerPower(){
      return chargerPower;
    }

    @Override
    public String toString(){
      return simpleToString(this);
    }

    public void setChargerPower(int chargerPower){
      this.chargerPower = chargerPower;
    }

    public int getChargerVoltage(){
      return chargerVoltage;
    }

    public void setChargerVoltage(int chargerVoltage){
      this.chargerVoltage = chargerVoltage;
    }

    public String getChargingState(){
      return chargingState;
    }

    public void setChargingState(String chargingState){
      this.chargingState = chargingState;
    }

    public String getConnChargeCable(){
      return connChargeCable;
    }

    public void setConnChargeCable(String connChargeCable){
      this.connChargeCable = connChargeCable;
    }

    public double getEstBatteryRange(){
      return estBatteryRange;
    }

    public void setEstBatteryRange(double estBatteryRange){
      this.estBatteryRange = estBatteryRange;
    }

    public String getFastChargerBrand(){
      return fastChargerBrand;
    }

    public void setFastChargerBrand(String fastChargerBrand){
      this.fastChargerBrand = fastChargerBrand;
    }

    public boolean getFastChargerPresent(){
      return fastChargerPresent;
    }

    public void setFastChargerPresent(boolean fastChargerPresent){
      this.fastChargerPresent = fastChargerPresent;
    }

    public String getFastChargerType(){
      return fastChargerType;
    }

    public void setFastChargerType(String fastChargerType){
      this.fastChargerType = fastChargerType;
    }

    public double getIdealBatteryRange(){
      return idealBatteryRange;
    }

    public void setIdealBatteryRange(double idealBatteryRange){
      this.idealBatteryRange = idealBatteryRange;
    }

    public boolean getManagedChargingActive(){
      return managedChargingActive;
    }

    public void setManagedChargingActive(boolean managedChargingActive){
      this.managedChargingActive = managedChargingActive;
    }

    public String getManagedChargingStartTime(){
      return managedChargingStartTime;
    }

    public void setManagedChargingStartTime(String managedChargingStartTime){
      this.managedChargingStartTime = managedChargingStartTime;
    }

    public boolean getManagedChargingUserCanceled(){
      return managedChargingUserCanceled;
    }

    public void setManagedChargingUserCanceled(boolean managedChargingUserCanceled){
      this.managedChargingUserCanceled = managedChargingUserCanceled;
    }

    public int getMaxRangeChargerCounter(){
      return maxRangeChargeCounter;
    }

    @Nonnull
    public Optional<Boolean> getNotEnoughPowerToHeat(){
      return Optional.ofNullable(notEnoughPowerToHeat);
    }

    public void setNotEnoughPowerToHeat(Boolean notEnoughPowerToHeat){
      this.notEnoughPowerToHeat = notEnoughPowerToHeat;
    }

    public boolean getScheduledChargingPending(){
      return scheduledChargingPending;
    }

    public void setScheduledChargingPending(boolean scheduledChargingPending){
      this.scheduledChargingPending = scheduledChargingPending;
    }

    @Nonnull
    public Optional<String> getScheduledChargingStartTime(){
      return Optional.ofNullable(scheduledChargingStartTime);
    }

    public void setScheduledChargingStartTime(@Nullable String scheduledChargingStartTime){
      this.scheduledChargingStartTime = scheduledChargingStartTime;
    }

    public double getTimeToFullCharge(){
      return timeToFullCharge;
    }

    public void setTimeToFullCharge(double timeToFullCharge){
      this.timeToFullCharge = timeToFullCharge;
    }

    public int getUsableBatteryLevel(){
      return usableBatteryLevel;
    }

    public void setUsableBatteryLevel(int usableBatteryLevel){
      this.usableBatteryLevel = usableBatteryLevel;
    }

    public String getUserChargeEnableRequest(){
      return userChargeEnableRequest;
    }

    public void setUserChargeEnableRequest(String userChargeEnableRequest){
      this.userChargeEnableRequest = userChargeEnableRequest;
    }

    public int getBatteryLevel(){
      return batteryLevel;
    }

    public void setBatteryLevel(int batteryLevel){
      this.batteryLevel = batteryLevel;
    }

    public boolean getBatteryHeaterOn(){
      return batteryHeaterOn;
    }

    public void setBatteryHeaterOn(boolean batteryHeaterOn){
      this.batteryHeaterOn = batteryHeaterOn;
    }

    public void setChargerPilotCurrent(int chargerPilotCurrent){
      this.chargerPilotCurrent = chargerPilotCurrent;
    }

    public void setMaxRangeChargeCounter(int maxRangeChargeCounter){
      this.maxRangeChargeCounter = maxRangeChargeCounter;
    }

    public com.ansill.tesla.api.data.model.ChargeState convert(){
      return new com.ansill.tesla.api.data.model.ChargeState(
        batteryHeaterOn,
        batteryLevel,
        batteryRange,
        chargeCurrentRequest,
        chargeCurrentRequestMax,
        chargeEnableRequest,
        chargeEnergyAdded,
        chargeLimitSoc,
        chargeLimitSocMax,
        chargeLimitSocMin,
        chargeLimitSocStd,
        chargeMilesAddedIdeal,
        chargeMilesAddedRated,
        chargePortColdWeatherMode,
        chargePortDoorOpen,
        chargePortLatch,
        chargeRate,
        chargeToMaxRange,
        chargerActualCurrent,
        chargerActualCurrent,
        chargerPilotCurrent,
        chargerPower,
        chargerVoltage,
        chargingState,
        connChargeCable,
        estBatteryRange,
        fastChargerBrand,
        fastChargerPresent,
        fastChargerType,
        idealBatteryRange,
        managedChargingActive,
        managedChargingStartTime,
        managedChargingUserCanceled,
        maxRangeChargeCounter,
        minutesToFullCharge,
        notEnoughPowerToHeat,
        scheduledChargingPending,
        scheduledChargingStartTime,
        timeToFullCharge,
        timestamp,
        true,
        usableBatteryLevel,
        userChargeEnableRequest
      );
    }
  }

  public static class GUISettings{

    private boolean gui24HourTime;

    private String guiChargeRateUnits;

    private String guiDistanceUnits;

    private String guiRangeDisplay;

    private String guiTemperatureUnits;

    private long timestamp;

    private boolean showRangeUnits;

    public GUISettings(
      boolean gui24HourTime,
      String guiChargeRateUnits,
      String guiDistanceUnits,
      String guiRangeDisplay,
      String guiTemperatureUnits,
      long timestamp,
      boolean showRangeUnits
    ){
      this.gui24HourTime = gui24HourTime;
      this.guiChargeRateUnits = guiChargeRateUnits;
      this.guiDistanceUnits = guiDistanceUnits;
      this.guiRangeDisplay = guiRangeDisplay;
      this.guiTemperatureUnits = guiTemperatureUnits;
      this.timestamp = timestamp;
      this.showRangeUnits = showRangeUnits;
    }

    public static GUISettings generate(){
      return new GUISettings(
        true,
        "mi/hr",
        "mi/hr",
        "Rated",
        "F",
        Instant.now().toEpochMilli(),
        false
      );
    }

    public boolean isShowRangeUnits(){
      return showRangeUnits;
    }

    public void setShowRangeUnits(boolean showRangeUnits){
      this.showRangeUnits = showRangeUnits;
    }

    public boolean getGui24HourTime(){
      return gui24HourTime;
    }

    public void setGui24HourTime(boolean gui24HourTime){
      this.gui24HourTime = gui24HourTime;
    }

    public String getGuiChargeRateUnits(){
      return guiChargeRateUnits;
    }

    public void setGuiChargeRateUnits(String guiChargeRateUnits){
      this.guiChargeRateUnits = guiChargeRateUnits;
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

    public String getGuiDistanceUnits(){
      return guiDistanceUnits;
    }

    public void setGuiDistanceUnits(String guiDistanceUnits){
      this.guiDistanceUnits = guiDistanceUnits;
    }

    public String getGuiRangeDisplay(){
      return guiRangeDisplay;
    }

    public void setGuiRangeDisplay(String guiRangeDisplay){
      this.guiRangeDisplay = guiRangeDisplay;
    }

    public String getGuiTemperatureUnits(){
      return guiTemperatureUnits;
    }

    public void setGuiTemperatureUnits(String guiTemperatureUnits){
      this.guiTemperatureUnits = guiTemperatureUnits;
    }

    public GuiSettings convert(){
      return new GuiSettings(
        gui24HourTime,
        guiChargeRateUnits,
        guiDistanceUnits,
        guiRangeDisplay,
        guiTemperatureUnits,
        showRangeUnits,
        timestamp
      );
    }
  }

  public static class VehicleState{

    private int apiVersion;

    private String autoparkStateV2;

    private String autoparkStyle;

    private boolean calendarSupported;

    private String carVersion;

    private int centerDisplayState;

    private int df;

    private int dr;

    private int ft;

    @Nullable
    private Boolean homelinkNearby; // Could disappear from json

    @Nullable
    private Integer homelinkDeviceCount; // Could disappear from json

    @Nullable
    private Integer sunRoofPercentOpen;  // Could disappear from json

    private boolean isUserPresent;

    private String lastAutoparkError;

    private boolean locked;

    private MediaState mediaState;

    private boolean notificationsSupported;

    private double odometer;

    private boolean parsedCalendarSupported;

    private int pf;

    private int pr;

    private boolean remoteStart;

    private boolean remoteStartEnabled;

    private boolean remoteStartSupported;

    private int rt;

    private boolean sentryMode;

    private SoftwareUpdate softwareUpdate;

    private SpeedLimitMode speedLimitMode;

    private String autoparkStateV3;

    private int fdWindow;

    private int fpWindow;

    private int rdWindow;

    private int rpWindow;

    private boolean sentryModeAvailable;

    private String sunRoofState;

    private long timestamp;

    private boolean valetMode;

    private boolean valetPinNeeded;

    private String vehicleName;

    public VehicleState(
      int apiVersion,
      String autoparkStateV2,
      String autoparkStyle,
      boolean calendarSupported,
      String carVersion,
      int centerDisplayState,
      int df,
      int dr,
      int ft,
      @Nullable Boolean homelinkNearby,
      @Nullable Integer homelinkDeviceCount,
      boolean isUserPresent,
      String lastAutoparkError,
      boolean locked,
      MediaState mediaState,
      boolean notificationsSupported,
      double odometer,
      boolean parsedCalendarSupported,
      int pf,
      int pr,
      boolean remoteStart,
      boolean remoteStartEnabled,
      boolean remoteStartSupported,
      int rt,
      boolean sentryMode,
      SoftwareUpdate softwareUpdate,
      SpeedLimitMode speedLimitMode,
      @Nullable Integer sunRoofPercentOpen,
      String autoparkStateV3,
      int fdWindow,
      int fpWindow,
      int rdWindow,
      int rpWindow,
      boolean sentryModeAvailable,
      String sunRoofState,
      long timestamp,
      boolean valetMode,
      boolean valetPinNeeded,
      String vehicleName
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
      this.homelinkNearby = homelinkNearby;
      this.homelinkDeviceCount = homelinkDeviceCount;
      this.isUserPresent = isUserPresent;
      this.lastAutoparkError = lastAutoparkError;
      this.locked = locked;
      this.mediaState = mediaState;
      this.notificationsSupported = notificationsSupported;
      this.odometer = odometer;
      this.parsedCalendarSupported = parsedCalendarSupported;
      this.pf = pf;
      this.pr = pr;
      this.remoteStart = remoteStart;
      this.remoteStartEnabled = remoteStartEnabled;
      this.remoteStartSupported = remoteStartSupported;
      this.rt = rt;
      this.sentryMode = sentryMode;
      this.softwareUpdate = softwareUpdate;
      this.speedLimitMode = speedLimitMode;
      this.sunRoofPercentOpen = sunRoofPercentOpen;
      this.autoparkStateV3 = autoparkStateV3;
      this.fdWindow = fdWindow;
      this.fpWindow = fpWindow;
      this.rdWindow = rdWindow;
      this.rpWindow = rpWindow;
      this.sentryModeAvailable = sentryModeAvailable;
      this.sunRoofState = sunRoofState;
      this.timestamp = timestamp;
      this.valetMode = valetMode;
      this.valetPinNeeded = valetPinNeeded;
      this.vehicleName = vehicleName;
    }

    public static VehicleState generate(){
      var random = new Random();
      return new VehicleState(
        com.ansill.tesla.api.data.model.VehicleState.SUPPORTED_API_VERSION,
        null,
        null,
        true,
        generateString(32),
        random.nextInt(),
        random.nextInt(),
        random.nextInt(),
        random.nextInt(),
        random.nextBoolean(),
        random.nextInt(),
        random.nextBoolean(),
        null,
        random.nextBoolean(),
        MediaState.generate(),
        true,
        random.nextDouble(),
        true,
        random.nextInt(),
        random.nextInt(),
        random.nextBoolean(),
        random.nextBoolean(),
        random.nextBoolean(),
        random.nextInt(),
        random.nextBoolean(),
        SoftwareUpdate.generate(),
        SpeedLimitMode.generate(),
        random.nextInt(),
        "unavailable",
        random.nextInt(),
        random.nextInt(),
        random.nextInt(),
        random.nextInt(),
        random.nextBoolean(),
        null,
        Instant.now().toEpochMilli(),
        false,
        false,
        generateString(32)
      );
    }

    public String getAutoparkStateV3(){
      return autoparkStateV3;
    }

    public void setAutoparkStateV3(String autoparkStateV3){
      this.autoparkStateV3 = autoparkStateV3;
    }

    public int getFdWindow(){
      return fdWindow;
    }

    public void setFdWindow(int fdWindow){
      this.fdWindow = fdWindow;
    }

    public int getFpWindow(){
      return fpWindow;
    }

    public void setFpWindow(int fpWindow){
      this.fpWindow = fpWindow;
    }

    public int getRdWindow(){
      return rdWindow;
    }

    public void setRdWindow(int rdWindow){
      this.rdWindow = rdWindow;
    }

    public int getRpWindow(){
      return rpWindow;
    }

    public void setRpWindow(int rpWindow){
      this.rpWindow = rpWindow;
    }

    public boolean isSentryModeAvailable(){
      return sentryModeAvailable;
    }

    public void setSentryModeAvailable(boolean sentryModeAvailable){
      this.sentryModeAvailable = sentryModeAvailable;
    }

    @Nonnull
    public Optional<Integer> getHomelinkDeviceCount(){
      return Optional.ofNullable(homelinkDeviceCount);
    }

    public void setHomelinkDeviceCount(@Nullable Integer homelinkDeviceCount){
      this.homelinkDeviceCount = homelinkDeviceCount;
    }

    public int getApiVersion(){
      return apiVersion;
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

    public void setApiVersion(int apiVersion){
      this.apiVersion = apiVersion;
    }

    public String getAutoparkStateV2(){
      return autoparkStateV2;
    }

    public void setAutoparkStateV2(String autoparkStateV2){
      this.autoparkStateV2 = autoparkStateV2;
    }

    public boolean getLocked(){
      return locked;
    }

    public void setLocked(boolean locked){
      this.locked = locked;
    }

    public String getAutoparkStyle(){
      return autoparkStyle;
    }

    public void setAutoparkStyle(String autoparkStyle){
      this.autoparkStyle = autoparkStyle;
    }

    public double getOdometer(){
      return odometer;
    }

    public void setOdometer(double odometer){
      this.odometer = odometer;
    }

    public boolean getCalendarSupported(){
      return calendarSupported;
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

    public void setCalendarSupported(boolean calendarSupported){
      this.calendarSupported = calendarSupported;
    }

    public String getCarVersion(){
      return carVersion;
    }

    public void setCarVersion(String carVersion){
      this.carVersion = carVersion;
    }

    public int getRt(){
      return rt;
    }

    public void setRt(int rt){
      this.rt = rt;
    }

    public int getCenterDisplayState(){
      return centerDisplayState;
    }

    public void setCenterDisplayState(int centerDisplayState){
      this.centerDisplayState = centerDisplayState;
    }

    @Nonnull
    public Optional<Boolean> getHomelinkNearby(){
      return Optional.ofNullable(homelinkNearby);
    }

    public void setHomelinkNearby(@Nullable Boolean homelinkNearby){
      this.homelinkNearby = homelinkNearby;
    }

    public boolean getIsUserPresent(){
      return isUserPresent;
    }

    public long getTimestamp(){
      return timestamp;
    }

    public void setTimestamp(long timestamp){
      this.timestamp = timestamp;
    }

    public void setIsUserPresent(boolean isUserPresent){
      this.isUserPresent = isUserPresent;
    }

    public String getLastAutoparkError(){
      return lastAutoparkError;
    }

    public void setLastAutoparkError(String lastAutoparkError){
      this.lastAutoparkError = lastAutoparkError;
    }

    public MediaState getMediaState(){
      return mediaState;
    }

    @Override
    public String toString(){
      return simpleToString(this);
    }

    public void setMediaState(MediaState mediaState){
      this.mediaState = mediaState;
    }

    public boolean getNotificationsSupported(){
      return notificationsSupported;
    }

    public void setNotificationsSupported(boolean notificationsSupported){
      this.notificationsSupported = notificationsSupported;
    }

    public boolean getParsedCalendarSupported(){
      return parsedCalendarSupported;
    }

    public void setParsedCalendarSupported(boolean parsedCalendarSupported){
      this.parsedCalendarSupported = parsedCalendarSupported;
    }

    public boolean getRemoteStart(){
      return remoteStart;
    }

    public void setRemoteStart(boolean remoteStart){
      this.remoteStart = remoteStart;
    }

    public boolean getRemoteStartEnabled(){
      return remoteStartEnabled;
    }

    public void setRemoteStartEnabled(boolean remoteStartEnabled){
      this.remoteStartEnabled = remoteStartEnabled;
    }

    public boolean getRemoteStartSupported(){
      return remoteStartSupported;
    }

    public void setRemoteStartSupported(boolean remoteStartSupported){
      this.remoteStartSupported = remoteStartSupported;
    }

    public boolean getSentryMode(){
      return sentryMode;
    }

    public void setSentryMode(boolean sentryMode){
      this.sentryMode = sentryMode;
    }

    public SoftwareUpdate getSoftwareUpdate(){
      return softwareUpdate;
    }

    public void setSoftwareUpdate(SoftwareUpdate softwareUpdate){
      this.softwareUpdate = softwareUpdate;
    }

    public SpeedLimitMode getSpeedLimitMode(){
      return speedLimitMode;
    }

    public void setSpeedLimitMode(SpeedLimitMode speedLimitMode){
      this.speedLimitMode = speedLimitMode;
    }

    @Nonnull
    public Optional<Integer> getSunRoofPercentOpen(){
      return Optional.ofNullable(sunRoofPercentOpen);
    }

    public void setSunRoofPercentOpen(@Nullable Integer sunRoofPercentOpen){
      this.sunRoofPercentOpen = sunRoofPercentOpen;
    }

    public String getSunRoofState(){
      return sunRoofState;
    }

    public void setSunRoofState(String sunRoofState){
      this.sunRoofState = sunRoofState;
    }

    public boolean getValetMode(){
      return valetMode;
    }

    public void setValetMode(boolean valetMode){
      this.valetMode = valetMode;
    }

    public boolean getValetPinNeeded(){
      return valetPinNeeded;
    }

    public void setValetPinNeeded(boolean valetPinNeeded){
      this.valetPinNeeded = valetPinNeeded;
    }

    public String getVehicleName(){
      return vehicleName;
    }

    public void setVehicleName(String vehicleName){
      this.vehicleName = vehicleName;
    }

    public com.ansill.tesla.api.data.model.VehicleState convert(){
      return new com.ansill.tesla.api.data.model.VehicleState(
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
        mediaState.convert(),
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
        softwareUpdate.convert(),
        speedLimitMode.convert(),
        timestamp,
        valetMode,
        valetPinNeeded,
        vehicleName
      );
    }

    public static class MediaState{

      private boolean remoteControlEnabled;

      public MediaState(boolean remoteControlEnabled){
        this.remoteControlEnabled = remoteControlEnabled;
      }

      public static MediaState generate(){
        return new MediaState(Math.random() < 0.5);
      }

      public boolean isRemoteControlEnabled(){
        return remoteControlEnabled;
      }

      public void setRemoteControlEnabled(boolean remoteControlEnabled){
        this.remoteControlEnabled = remoteControlEnabled;
      }

      public com.ansill.tesla.api.data.model.MediaState convert(){
        return new com.ansill.tesla.api.data.model.MediaState(remoteControlEnabled);
      }
    }

    public static class SoftwareUpdate{

      private int downloadPerc;

      private int installPerc;

      private String version;

      private long expectedDurationSec;

      private String status;

      private SoftwareUpdate(
        int downloadPerc,
        int installPerc,
        String version,
        long expectedDurationSec,
        String status
      ){
        this.downloadPerc = downloadPerc;
        this.installPerc = installPerc;
        this.version = version;
        this.expectedDurationSec = expectedDurationSec;
        this.status = status;
      }

      public static SoftwareUpdate generate(){
        var random = new Random();
        return new SoftwareUpdate(
          random.nextInt(),
          random.nextInt(),
          generateString(32),
          random.nextLong(),
          ""
        );
      }

      public int getDownloadPercent(){
        return downloadPerc;
      }

      public int getInstallPercent(){
        return installPerc;
      }

      public String getVersion(){
        return version;
      }

      public void setVersion(String version){
        this.version = version;
      }

      public long getExpectedDurationSec(){
        return expectedDurationSec;
      }

      public String getStatus(){
        return status;
      }

      public void setStatus(String status){
        this.status = status;
      }

      public void setExpectedDurationSec(long expectedDurationSec){
        this.expectedDurationSec = expectedDurationSec;
      }

      public void setDownloadPerc(int downloadPerc){
        this.downloadPerc = downloadPerc;
      }

      public void setInstallPerc(int installPerc){
        this.installPerc = installPerc;
      }

      public com.ansill.tesla.api.data.model.SoftwareUpdate convert(){
        return new com.ansill.tesla.api.data.model.SoftwareUpdate(
          downloadPerc,
          expectedDurationSec,
          installPerc,
          status,
          version
        );
      }
    }

    public static class SpeedLimitMode{

      private boolean active;

      private double currentLimitMph;

      private int maxLimitMph;

      private int minLimitMph;

      private boolean pinCodeSet;

      public SpeedLimitMode(
        boolean active,
        double currentLimitMph,
        int maxLimitMph,
        int minLimitMph,
        boolean pinCodeSet
      ){
        this.active = active;
        this.currentLimitMph = currentLimitMph;
        this.maxLimitMph = maxLimitMph;
        this.minLimitMph = minLimitMph;
        this.pinCodeSet = pinCodeSet;
      }

      public static SpeedLimitMode generate(){
        var random = new Random();
        return new SpeedLimitMode(
          false,
          random.nextDouble(),
          random.nextInt(),
          random.nextInt(),
          false
        );
      }

      public boolean getActive(){
        return active;
      }

      public void setActive(boolean active){
        this.active = active;
      }

      public double getCurrentLimitMph(){
        return currentLimitMph;
      }

      public void setCurrentLimitMph(double currentLimitMph){
        this.currentLimitMph = currentLimitMph;
      }

      public int getMaxLimitMph(){
        return maxLimitMph;
      }

      public void setMaxLimitMph(int maxLimitMph){
        this.maxLimitMph = maxLimitMph;
      }

      public int getMinLimitMph(){
        return minLimitMph;
      }

      public void setMinLimitMph(int minLimitMph){
        this.minLimitMph = minLimitMph;
      }

      public boolean getPinCodeSet(){
        return pinCodeSet;
      }

      public void setPinCodeSet(boolean pinCodeSet){
        this.pinCodeSet = pinCodeSet;
      }

      public com.ansill.tesla.api.data.model.SpeedLimitMode convert(){
        return new com.ansill.tesla.api.data.model.SpeedLimitMode(
          active,
          currentLimitMph,
          maxLimitMph,
          minLimitMph,
          pinCodeSet
        );
      }
    }
  }

  public static class VehicleConfig{

    private boolean canAcceptNavigationRequests;

    private boolean canActuateTrunks;

    private String carSpecialType;

    private String carType;

    private String chargePortType;

    private boolean euVehicle;

    private String exteriorColor;

    private boolean hasAirSuspension;

    private boolean hasLudicrousMode;

    private int keyVersion;

    private boolean motorizedChargePort;

    private String perfConfig;

    private boolean plg;

    private int rearSeatHeaters;

    private String rearSeatType;

    private boolean rhd;

    private String roofColor;

    private String seatType;

    private String spoilerType;

    private String sunRoofInstalled;

    private String thirdRowSeats;

    private long timestamp;

    private String trimBadging;

    private String wheelType;

    private boolean useRangeBadging;

    private boolean eceRestrictions;

    public VehicleConfig(
      boolean canAcceptNavigationRequests,
      boolean canActuateTrunks,
      String carSpecialType,
      String carType,
      String chargePortType,
      boolean euVehicle,
      String exteriorColor,
      boolean hasAirSuspension,
      boolean hasLudicrousMode,
      int keyVersion,
      boolean motorizedChargePort,
      String perfConfig,
      boolean plg,
      int rearSeatHeaters,
      String rearSeatType,
      boolean rhd,
      String roofColor,
      String seatType,
      String spoilerType,
      String sunRoofInstalled,
      String thirdRowSeats,
      long timestamp,
      String trimBadging,
      String wheelType,
      boolean useRangeBadging
    ){
      this.canAcceptNavigationRequests = canAcceptNavigationRequests;
      this.canActuateTrunks = canActuateTrunks;
      this.carSpecialType = carSpecialType;
      this.carType = carType;
      this.chargePortType = chargePortType;
      this.euVehicle = euVehicle;
      this.exteriorColor = exteriorColor;
      this.hasAirSuspension = hasAirSuspension;
      this.hasLudicrousMode = hasLudicrousMode;
      this.keyVersion = keyVersion;
      this.motorizedChargePort = motorizedChargePort;
      this.perfConfig = perfConfig;
      this.plg = plg;
      this.rearSeatHeaters = rearSeatHeaters;
      this.rearSeatType = rearSeatType;
      this.rhd = rhd;
      this.roofColor = roofColor;
      this.seatType = seatType;
      this.spoilerType = spoilerType;
      this.sunRoofInstalled = sunRoofInstalled;
      this.thirdRowSeats = thirdRowSeats;
      this.timestamp = timestamp;
      this.trimBadging = trimBadging;
      this.wheelType = wheelType;
      this.useRangeBadging = useRangeBadging;
    }

    public static VehicleConfig generate(){
      var random = new Random();
      return new VehicleConfig(
        true,
        random.nextBoolean(),
        "base",
        "model3",
        "US",
        false,
        generateString(32),
        false,
        false,
        2,
        true,
        null,
        false,
        random.nextInt(),
        null,
        false,
        "Glass",
        null,
        "None",
        null,
        "<invalid>",
        Instant.now().toEpochMilli(),
        "Long Range",
        "Pinwheel18",
        random.nextBoolean()
      );
    }

    public boolean isUseRangeBadging(){
      return useRangeBadging;
    }

    public void setUseRangeBadging(boolean useRangeBadging){
      this.useRangeBadging = useRangeBadging;
    }

    public boolean isCanAcceptNavigationRequests(){
      return canAcceptNavigationRequests;
    }

    public void setCanAcceptNavigationRequests(boolean canAcceptNavigationRequests){
      this.canAcceptNavigationRequests = canAcceptNavigationRequests;
    }

    public boolean isCanActuateTrunks(){
      return canActuateTrunks;
    }

    public void setCanActuateTrunks(boolean canActuateTrunks){
      this.canActuateTrunks = canActuateTrunks;
    }

    public String getCarSpecialType(){
      return carSpecialType;
    }

    public void setCarSpecialType(String carSpecialType){
      this.carSpecialType = carSpecialType;
    }

    public String getCarType(){
      return carType;
    }

    public void setCarType(String carType){
      this.carType = carType;
    }

    public String getChargePortType(){
      return chargePortType;
    }

    public void setChargePortType(String chargePortType){
      this.chargePortType = chargePortType;
    }

    public boolean getEuVehicle(){
      return euVehicle;
    }

    public void setEuVehicle(boolean euVehicle){
      this.euVehicle = euVehicle;
    }

    public boolean isPlg(){
      return plg;
    }

    public void setPlg(boolean plg){
      this.plg = plg;
    }

    public String getExteriorColor(){
      return exteriorColor;
    }

    public void setExteriorColor(String exteriorColor){
      this.exteriorColor = exteriorColor;
    }

    public boolean getRhd(){
      return rhd;
    }

    public void setRhd(boolean rhd){
      this.rhd = rhd;
    }

    public boolean getHasAirSuspension(){
      return hasAirSuspension;
    }

    public void setHasAirSuspension(boolean hasAirSuspension){
      this.hasAirSuspension = hasAirSuspension;
    }

    public boolean getHasLudicrousMode(){
      return hasLudicrousMode;
    }

    public void setHasLudicrousMode(boolean hasLudicrousMode){
      this.hasLudicrousMode = hasLudicrousMode;
    }

    public int getKeyVersion(){
      return keyVersion;
    }

    public long getTimestamp(){
      return timestamp;
    }

    public void setTimestamp(long timestamp){
      this.timestamp = timestamp;
    }

    public void setKeyVersion(int keyVersion){
      this.keyVersion = keyVersion;
    }

    public boolean getMotorizedChargePort(){
      return motorizedChargePort;
    }

    @Override
    public String toString(){
      return simpleToString(this);
    }

    public void setMotorizedChargePort(boolean motorizedChargePort){
      this.motorizedChargePort = motorizedChargePort;
    }

    public String getPerfConfig(){
      return perfConfig;
    }

    public void setPerfConfig(String perfConfig){
      this.perfConfig = perfConfig;
    }

    public int getRearSeatHeaters(){
      return rearSeatHeaters;
    }

    public void setRearSeatHeaters(int rearSeatHeaters){
      this.rearSeatHeaters = rearSeatHeaters;
    }

    public String getRearSeatType(){
      return rearSeatType;
    }

    public void setRearSeatType(String rearSeatType){
      this.rearSeatType = rearSeatType;
    }

    public String getRoofColor(){
      return roofColor;
    }

    public void setRoofColor(String roofColor){
      this.roofColor = roofColor;
    }

    public String getSeatType(){
      return seatType;
    }

    public void setSeatType(String seatType){
      this.seatType = seatType;
    }

    public String getSpoilerType(){
      return spoilerType;
    }

    public void setSpoilerType(String spoilerType){
      this.spoilerType = spoilerType;
    }

    public String getSunRoofInstalled(){
      return sunRoofInstalled;
    }

    public void setSunRoofInstalled(String sunRoofInstalled){
      this.sunRoofInstalled = sunRoofInstalled;
    }

    public String getThirdRowSeats(){
      return thirdRowSeats;
    }

    public void setThirdRowSeats(String thirdRowSeats){
      this.thirdRowSeats = thirdRowSeats;
    }

    public String getTrimBadging(){
      return trimBadging;
    }

    public void setTrimBadging(String trimBadging){
      this.trimBadging = trimBadging;
    }

    public String getWheelType(){
      return wheelType;
    }

    public void setWheelType(String wheelType){
      this.wheelType = wheelType;
    }

    public com.ansill.tesla.api.data.model.VehicleConfig convert(){
      return new com.ansill.tesla.api.data.model.VehicleConfig(
        canAcceptNavigationRequests,
        canActuateTrunks,
        carSpecialType,
        carType,
        chargePortType,
        eceRestrictions,
        euVehicle,
        exteriorColor,
        hasAirSuspension,
        hasLudicrousMode,
        keyVersion,
        motorizedChargePort,
        plg,
        rearSeatHeaters,
        rearSeatType,
        rhd,
        roofColor,
        seatType,
        spoilerType,
        sunRoofInstalled,
        thirdRowSeats,
        timestamp,
        useRangeBadging,
        wheelType
      );
    }
  }
}
