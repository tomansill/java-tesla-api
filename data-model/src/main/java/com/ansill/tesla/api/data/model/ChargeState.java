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
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

@JsonSerialize(using = SimpleSerializer.class)
@JsonDeserialize(using = ChargeState.Deserializer.class)
@Immutable
public final class ChargeState{

  private final boolean batteryHeaterOn;

  private final int batteryLevel;

  private final double batteryRange;

  private final int chargeCurrentRequest;

  private final int chargeCurrentRequestMax;

  private final boolean chargeEnableRequest;

  private final double chargeEnergyAdded;

  private final int chargeLimitSoc;

  private final int chargeLimitSocMin;

  private final int chargeLimitSocMax;

  private final int chargeLimitSocStd;

  private final double chargeMilesAddedIdeal;

  private final double chargeMilesAddedRated;

  private final boolean chargePortColdWeatherMode;

  private final boolean chargePortDoorOpen;

  @Nonnull
  private final String chargePortLatch;

  private final double chargeRate;

  private final boolean chargeToMaxRange;

  @Nullable
  private final Integer chargerPhases;

  private final int chargerActualCurrent;

  private final int chargerPilotCurrent;

  private final int chargerPower;

  private final int chargerVoltage;

  @Nonnull
  private final String chargingState;

  @Nullable
  private final String connChargeCable;

  private final double estBatteryRange;

  @Nullable
  private final String fastChargerBrand;

  private final boolean fastChargerPresent;

  @Nullable
  private final String fastChargerType;

  private final double idealBatteryRange;

  private final boolean managedChargingActive;

  @Nullable
  private final String managedChargingStartTime;

  private final boolean managedChargingUserCanceled;

  private final int maxRangeChargeCounter;

  private final long minutesToFullCharge;

  @Nullable
  private final Boolean notEnoughPowerToHeat;

  private final boolean scheduledChargingPending;

  @Nullable
  private final String scheduledChargingStartTime;

  private final double timeToFullCharge;

  private final long timestamp;

  private final boolean tripCharging;

  private final int usableBatteryLevel;

  @Nullable
  private final String userChargeEnableRequest;

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
    @Nullable Integer chargerPhases,
    int chargerPilotCurrent,
    int chargerPower,
    int chargerVoltage,
    @Nonnull String chargingState,
    @Nullable String connChargeCable,
    double estBatteryRange,
    @Nullable String fastChargerBrand,
    boolean fastChargerPresent,
    @Nullable String fastChargerType,
    double idealBatteryRange,
    boolean managedChargingActive,
    @Nullable String managedChargingStartTime,
    boolean managedChargingUserCanceled,
    int maxRangeChargeCounter,
    long minutesToFullCharge,
    @Nullable Boolean notEnoughPowerToHeat,
    boolean scheduledChargingPending,
    @Nullable String scheduledChargingStartTime,
    double timeToFullCharge,
    long timestamp,
    boolean tripCharging,
    int usableBatteryLevel,
    @Nullable String userChargeEnableRequest
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
    this.fastChargerType = fastChargerType;
    this.idealBatteryRange = idealBatteryRange;
    this.managedChargingActive = managedChargingActive;
    this.managedChargingStartTime = managedChargingStartTime;
    this.managedChargingUserCanceled = managedChargingUserCanceled;
    this.maxRangeChargeCounter = maxRangeChargeCounter;
    this.minutesToFullCharge = minutesToFullCharge;
    this.notEnoughPowerToHeat = notEnoughPowerToHeat;
    this.scheduledChargingPending = scheduledChargingPending;
    this.scheduledChargingStartTime = scheduledChargingStartTime;
    this.timeToFullCharge = timeToFullCharge;
    this.timestamp = timestamp;
    this.tripCharging = tripCharging;
    this.usableBatteryLevel = usableBatteryLevel;
    this.userChargeEnableRequest = userChargeEnableRequest;
  }

  public boolean isBatteryHeaterOn(){
    return batteryHeaterOn;
  }

  public int getBatteryLevel(){
    return batteryLevel;
  }

  public double getBatteryRange(){
    return batteryRange;
  }

  public int getChargeCurrentRequest(){
    return chargeCurrentRequest;
  }

  public int getChargeCurrentRequestMax(){
    return chargeCurrentRequestMax;
  }

  public boolean isChargeEnableRequest(){
    return chargeEnableRequest;
  }

  public double getChargeEnergyAdded(){
    return chargeEnergyAdded;
  }

  public int getChargeLimitSoc(){
    return chargeLimitSoc;
  }

  public int getChargeLimitSocMin(){
    return chargeLimitSocMin;
  }

  public int getChargeLimitSocMax(){
    return chargeLimitSocMax;
  }

  public int getChargeLimitSocStd(){
    return chargeLimitSocStd;
  }

  public double getChargeMilesAddedIdeal(){
    return chargeMilesAddedIdeal;
  }

  public double getChargeMilesAddedRated(){
    return chargeMilesAddedRated;
  }

  public boolean hasChargePortColdWeatherMode(){
    return chargePortColdWeatherMode;
  }

  public boolean isChargePortDoorOpen(){
    return chargePortDoorOpen;
  }

  @Override
  public boolean equals(Object o){
    if(this == o) return true;
    if(!(o instanceof ChargeState that)) return false;

    if(isBatteryHeaterOn() != that.isBatteryHeaterOn()) return false;
    if(getBatteryLevel() != that.getBatteryLevel()) return false;
    if(Double.compare(that.getBatteryRange(), getBatteryRange()) != 0) return false;
    if(getChargeCurrentRequest() != that.getChargeCurrentRequest()) return false;
    if(getChargeCurrentRequestMax() != that.getChargeCurrentRequestMax()) return false;
    if(isChargeEnableRequest() != that.isChargeEnableRequest()) return false;
    if(Double.compare(that.getChargeEnergyAdded(), getChargeEnergyAdded()) != 0) return false;
    if(getChargeLimitSoc() != that.getChargeLimitSoc()) return false;
    if(getChargeLimitSocMin() != that.getChargeLimitSocMin()) return false;
    if(getChargeLimitSocMax() != that.getChargeLimitSocMax()) return false;
    if(getChargeLimitSocStd() != that.getChargeLimitSocStd()) return false;
    if(Double.compare(that.getChargeMilesAddedIdeal(), getChargeMilesAddedIdeal()) != 0) return false;
    if(Double.compare(that.getChargeMilesAddedRated(), getChargeMilesAddedRated()) != 0) return false;
    if(chargePortColdWeatherMode != that.chargePortColdWeatherMode) return false;
    if(isChargePortDoorOpen() != that.isChargePortDoorOpen()) return false;
    if(Double.compare(that.getChargeRate(), getChargeRate()) != 0) return false;
    if(chargeToMaxRange != that.chargeToMaxRange) return false;
    if(getChargerPhases() != that.getChargerPhases()) return false;
    if(getChargerActualCurrent() != that.getChargerActualCurrent()) return false;
    if(getChargerPilotCurrent() != that.getChargerPilotCurrent()) return false;
    if(getChargerPower() != that.getChargerPower()) return false;
    if(getChargerVoltage() != that.getChargerVoltage()) return false;
    if(Double.compare(that.getEstimatedBatteryRange(), getEstimatedBatteryRange()) != 0) return false;
    if(isFastChargerPresent() != that.isFastChargerPresent()) return false;
    if(Double.compare(that.getIdealBatteryRange(), getIdealBatteryRange()) != 0) return false;
    if(isManagedChargingActive() != that.isManagedChargingActive()) return false;
    if(isManagedChargingUserCanceled() != that.isManagedChargingUserCanceled()) return false;
    if(getMaxRangeChargeCounter() != that.getMaxRangeChargeCounter()) return false;
    if(getMinutesToFullCharge() != that.getMinutesToFullCharge()) return false;
    if(isScheduledChargingPending() != that.isScheduledChargingPending()) return false;
    if(Double.compare(that.getTimeToFullCharge(), getTimeToFullCharge()) != 0) return false;
    if(getTimestamp() != that.getTimestamp()) return false;
    if(isTripCharging() != that.isTripCharging()) return false;
    if(getUsableBatteryLevel() != that.getUsableBatteryLevel()) return false;
    if(!getChargePortLatch().equals(that.getChargePortLatch())) return false;
    if(!getChargingState().equals(that.getChargingState())) return false;
    if(!getConnectedChargeCable().equals(that.getConnectedChargeCable())) return false;
    if(!getFastChargerBrand().equals(that.getFastChargerBrand())) return false;
    if(!getFastChargerType().equals(that.getFastChargerType())) return false;
    if(!getManagedChargingStartTime().equals(that.getManagedChargingStartTime())) return false;
    if(!getNotEnoughPowerToHeat().equals(that.getNotEnoughPowerToHeat())) return false;
    if(!getScheduledChargingStartTime().equals(that.getScheduledChargingStartTime())) return false;
    return getUserChargeEnableRequest().equals(that.getUserChargeEnableRequest());
  }

  @Override
  public int hashCode(){
    int result;
    long temp;
    result = (isBatteryHeaterOn() ? 1 : 0);
    result = 31 * result + getBatteryLevel();
    temp = Double.doubleToLongBits(getBatteryRange());
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + getChargeCurrentRequest();
    result = 31 * result + getChargeCurrentRequestMax();
    result = 31 * result + (isChargeEnableRequest() ? 1 : 0);
    temp = Double.doubleToLongBits(getChargeEnergyAdded());
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + getChargeLimitSoc();
    result = 31 * result + getChargeLimitSocMin();
    result = 31 * result + getChargeLimitSocMax();
    result = 31 * result + getChargeLimitSocStd();
    temp = Double.doubleToLongBits(getChargeMilesAddedIdeal());
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(getChargeMilesAddedRated());
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + (chargePortColdWeatherMode ? 1 : 0);
    result = 31 * result + (isChargePortDoorOpen() ? 1 : 0);
    result = 31 * result + getChargePortLatch().hashCode();
    temp = Double.doubleToLongBits(getChargeRate());
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + (chargeToMaxRange ? 1 : 0);
    result = 31 * result + getChargerPhases().orElse(0);
    result = 31 * result + getChargerActualCurrent();
    result = 31 * result + getChargerPilotCurrent();
    result = 31 * result + getChargerPower();
    result = 31 * result + getChargerVoltage();
    result = 31 * result + getChargingState().hashCode();
    result = 31 * result + getConnectedChargeCable().hashCode();
    temp = Double.doubleToLongBits(getEstimatedBatteryRange());
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + getFastChargerBrand().hashCode();
    result = 31 * result + (isFastChargerPresent() ? 1 : 0);
    result = 31 * result + getFastChargerType().hashCode();
    temp = Double.doubleToLongBits(getIdealBatteryRange());
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + (isManagedChargingActive() ? 1 : 0);
    result = 31 * result + getManagedChargingStartTime().hashCode();
    result = 31 * result + (isManagedChargingUserCanceled() ? 1 : 0);
    result = 31 * result + getMaxRangeChargeCounter();
    result = 31 * result + (int) (getMinutesToFullCharge() ^ (getMinutesToFullCharge() >>> 32));
    result = 31 * result + getNotEnoughPowerToHeat().hashCode();
    result = 31 * result + (isScheduledChargingPending() ? 1 : 0);
    result = 31 * result + getScheduledChargingStartTime().hashCode();
    temp = Double.doubleToLongBits(getTimeToFullCharge());
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + (int) (getTimestamp() ^ (getTimestamp() >>> 32));
    result = 31 * result + (isTripCharging() ? 1 : 0);
    result = 31 * result + getUsableBatteryLevel();
    result = 31 * result + getUserChargeEnableRequest().hashCode();
    return result;
  }

  @Nonnull
  public String getChargePortLatch(){
    return chargePortLatch;
  }

  public double getChargeRate(){
    return chargeRate;
  }

  public boolean isChargingToMaxRange(){
    return chargeToMaxRange;
  }

  public int getChargerActualCurrent(){
    return chargerActualCurrent;
  }

  @Nonnull
  public Optional<Integer> getChargerPhases(){
    return Optional.ofNullable(chargerPhases);
  }

  public int getChargerPilotCurrent(){
    return chargerPilotCurrent;
  }

  public int getChargerPower(){
    return chargerPower;
  }

  public int getChargerVoltage(){
    return chargerVoltage;
  }

  @Nonnull
  public String getChargingState(){
    return chargingState;
  }

  @Nonnull
  public Optional<String> getConnectedChargeCable(){
    return Optional.ofNullable(connChargeCable);
  }

  public double getEstimatedBatteryRange(){
    return estBatteryRange;
  }

  @Nonnull
  public Optional<String> getFastChargerBrand(){
    return Optional.ofNullable(fastChargerBrand);
  }

  public boolean isFastChargerPresent(){
    return fastChargerPresent;
  }

  @Nonnull
  public Optional<String> getFastChargerType(){
    return Optional.ofNullable(fastChargerType);
  }

  public double getIdealBatteryRange(){
    return idealBatteryRange;
  }

  public boolean isManagedChargingActive(){
    return managedChargingActive;
  }

  @Nonnull
  public Optional<String> getManagedChargingStartTime(){
    return Optional.ofNullable(managedChargingStartTime);
  }

  public boolean isManagedChargingUserCanceled(){
    return managedChargingUserCanceled;
  }

  public int getMaxRangeChargeCounter(){
    return maxRangeChargeCounter;
  }

  public long getMinutesToFullCharge(){
    return minutesToFullCharge;
  }

  @Nonnull
  public Optional<Boolean> getNotEnoughPowerToHeat(){
    return Optional.ofNullable(notEnoughPowerToHeat);
  }

  public boolean isScheduledChargingPending(){
    return scheduledChargingPending;
  }

  @Nonnull
  public Optional<String> getScheduledChargingStartTime(){
    return Optional.ofNullable(scheduledChargingStartTime);
  }

  public double getTimeToFullCharge(){
    return timeToFullCharge;
  }

  public long getTimestamp(){
    return timestamp;
  }

  public boolean isTripCharging(){
    return tripCharging;
  }

  public int getUsableBatteryLevel(){
    return usableBatteryLevel;
  }

  @Nonnull
  public Optional<String> getUserChargeEnableRequest(){
    return Optional.ofNullable(userChargeEnableRequest);
  }

  public static class Deserializer extends StdDeserializer<ChargeState>{
    private static final long serialVersionUID = 4014081649185817664L;

    @Nonnull
    private final AtomicReference<Function<Map<String,Optional<Object>>,Boolean>> unknownFieldsFunction;

    public Deserializer(){
      super(ChargeState.class);
      unknownFieldsFunction = new AtomicReference<>();
    }

    public Deserializer(@Nonnull AtomicReference<Function<Map<String,Optional<Object>>,Boolean>> unknownFieldsFunction){
      super(ChargeState.class);
      this.unknownFieldsFunction = unknownFieldsFunction;
    }

    @Override
    public ChargeState deserialize(
      JsonParser jsonParser, DeserializationContext deserializationContext
    ) throws IOException{

      // Get node
      ObjectNode node = jsonParser.readValueAsTree();

      // Set up set of used keys
      var usedKeysSet = new HashSet<String>();

      // Get values
      var batteryHeaterOn = JacksonUtility.getBoolean(node, "battery_heater_on", usedKeysSet);
      var batteryLevel = JacksonUtility.getInteger(node, "battery_level", usedKeysSet);
      var batteryRange = JacksonUtility.getDouble(node, "battery_range", usedKeysSet);
      var chargeCurrentRequest = JacksonUtility.getInteger(node, "charge_current_request", usedKeysSet);
      var chargeCurrentRequestMax = JacksonUtility.getInteger(node, "charge_current_request_max", usedKeysSet);
      var chargeEnableRequest = JacksonUtility.getBoolean(node, "charge_enable_request", usedKeysSet);
      var chargeEnergyAdded = JacksonUtility.getDouble(node, "charge_energy_added", usedKeysSet);
      var chargeLimitSoc = JacksonUtility.getInteger(node, "charge_limit_soc", usedKeysSet);
      var chargeLimitSocMax = JacksonUtility.getInteger(node, "charge_limit_soc_max", usedKeysSet);
      var chargeLimitSocMin = JacksonUtility.getInteger(node, "charge_limit_soc_min", usedKeysSet);
      var chargeLimitSocStd = JacksonUtility.getInteger(node, "charge_limit_soc_std", usedKeysSet);
      var chargeMilesAddedIdeal = JacksonUtility.getDouble(node, "charge_miles_added_ideal", usedKeysSet);
      var chargeMilesAddedRated = JacksonUtility.getDouble(node, "charge_miles_added_rated", usedKeysSet);
      var chargePortColdWeatherMode = JacksonUtility.getBoolean(node, "charge_port_cold_weather_mode", usedKeysSet);
      var chargePortDoorOpen = JacksonUtility.getBoolean(node, "charge_port_door_open", usedKeysSet);
      var chargePortLatch = JacksonUtility.getString(node, "charge_port_latch", usedKeysSet);
      var chargeRate = JacksonUtility.getDouble(node, "charge_rate", usedKeysSet);
      var chargeToMaxRange = JacksonUtility.getBoolean(node, "charge_to_max_range", usedKeysSet);
      var chargerActualCurrent = JacksonUtility.getInteger(node, "charger_actual_current", usedKeysSet);
      var chargerPhases = JacksonUtility.getIntegerNullable(node, "charger_phases", usedKeysSet);
      var chargerPilotCurrent = JacksonUtility.getInteger(node, "charger_pilot_current", usedKeysSet);
      var chargerPower = JacksonUtility.getInteger(node, "charger_power", usedKeysSet);
      var chargerVoltage = JacksonUtility.getInteger(node, "charger_voltage", usedKeysSet);
      var chargingState = JacksonUtility.getString(node, "charging_state", usedKeysSet);
      var connChargeCable = JacksonUtility.getStringNullable(node, "conn_charge_cable", usedKeysSet);
      var estBatteryRange = JacksonUtility.getDouble(node, "est_battery_range", usedKeysSet);
      var fastChargerBrand = JacksonUtility.getStringNullable(node, "fast_charger_brand", usedKeysSet);
      var fastChargerPresent = JacksonUtility.getBoolean(node, "fast_charger_present", usedKeysSet);
      var fastChargerType = JacksonUtility.getStringNullable(node, "fast_charger_type", usedKeysSet);
      var idealBatteryRange = JacksonUtility.getDouble(node, "ideal_battery_range", usedKeysSet);
      var managedChargingActive = JacksonUtility.getBoolean(node, "managed_charging_active", usedKeysSet);
      var managedChargingStartTime = JacksonUtility.getStringNullable(node, "managed_charging_start_time", usedKeysSet);
      var managedChargingUserCanceled = JacksonUtility.getBoolean(node, "managed_charging_user_canceled", usedKeysSet);
      var maxRangeChargeCounter = JacksonUtility.getInteger(node, "max_range_charge_counter", usedKeysSet);
      var minutesToFullCharge = JacksonUtility.getLong(node, "minutes_to_full_charge", usedKeysSet);
      var notEnoughPowerToHeat = JacksonUtility.getBooleanNullable(node, "not_enough_power_to_heat", usedKeysSet);
      var scheduledChargingPending = JacksonUtility.getBoolean(node, "scheduled_charging_pending", usedKeysSet);
      var scheduledChargingStartTime = JacksonUtility.getStringNullable(
        node,
        "scheduled_charging_start_time",
        usedKeysSet
      );
      var timeToFullCharge = JacksonUtility.getDouble(node, "time_to_full_charge", usedKeysSet);
      var timestamp = JacksonUtility.getLong(node, "timestamp", usedKeysSet);
      var tripCharging = JacksonUtility.getBoolean(node, "trip_charging", usedKeysSet);
      var usableBatteryLevel = JacksonUtility.getInteger(node, "usable_battery_level", usedKeysSet);
      var userChargeEnableRequest = JacksonUtility.getStringNullable(node, "user_charge_enable_request", usedKeysSet);

      // Get unused fields map
      var unknownFields = JacksonUtility.createUnknownFieldsMap(node, jsonParser.getCodec(), usedKeysSet);

      // Report it
      var fnc = unknownFieldsFunction.get();
      if(fnc != null && fnc.apply(unknownFields)){
        throw new IllegalArgumentException("Thrown by the unknownFieldsFunction function");
      }

      // Build and return
      return new ChargeState(
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
        chargerPhases,
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
        tripCharging,
        usableBatteryLevel,
        userChargeEnableRequest
      );
    }
  }
}
