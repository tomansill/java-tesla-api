package com.ansill.tesla.med.model;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.measure.quantity.ElectricCurrent;
import javax.measure.quantity.ElectricPotential;
import javax.measure.quantity.Length;
import javax.measure.quantity.Power;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Immutable
public class ChargeState{
    private final boolean batteryHeaterOn;
    private final int batteryLevel;
    @Nonnull
    private final Length batteryRange;
    @Nonnull
    private final ElectricCurrent chargeCurrentRequest;
    @Nonnull
    private final ElectricCurrent chargeCurrentRequestMax;
    private final boolean chargeEnableRequest;
    @Nonnull
    private final Power chargeEnergyAdded;
    @Nonnegative
    private final int chargeLimitSOC;
    @Nonnegative
    private final int chargeLimitSOCMax;
    @Nonnegative
    private final int chargeLimitSOCMin;
    @Nonnegative
    private final int chargeLimitSOCSTD;
    @Nonnull
    private final Length chargeDistanceAddedIdeal;
    @Nonnull
    private final Length chargeDistanceAddedRated;
    private final boolean chargePortColdWeatherMode;
    private final boolean chargePortDoorOpen;
    @Nonnull
    private final Latch chargePortLatch;
    @Nonnull
    private final ElectricCurrent chargeRate;
    private final boolean chargeToMaxRange;
    @Nonnull
    private final ElectricCurrent chargerActualCurrent;
    @Nullable
    private final String chargerPhases; // TODO find all possible enums
    @Nonnull
    private final ElectricCurrent chargerPilotCurrent;
    @Nonnull
    private final Power chargerPower;
    @Nonnull
    private final ElectricPotential chargerVoltage;
    @Nonnull
    private final Object chargingState; // TODO find all possible enums - also, what is this?
    @Nonnull
    private final String connChargeCable;
    @Nonnull
    private final Length estimatedBatteryRange; // Originally "est_battery_range"
    @Nonnull
    private final String fastChargerBrand;
    private final boolean fastChargerPresent;
    @Nonnull
    private final String fastChargerType;
    @Nonnull
    private final Length idealBatteryRange;
    private final boolean managedChargingActive;
    @Nonnull
    private final String managedChargingStartTime; // TODO what is this
    private final boolean managedChargingUserCanceled;
    private final int maxRangeChargeCounter; // TODO what is this
    @Nonnull
    private final Duration durationToFullCharge;
    private final boolean notEnoughPowerToHeat;
    private final boolean scheduledChargingPending;
    @Nullable
    private final Instant scheduledChargingStartTime;
    @Nonnull
    private final Duration timeToFullCharge;
    @Nonnull
    private final Instant timestamp;
    private final boolean tripCharging;
    @Nonnegative
    private final int usableBatteryLevel;
    @Nonnull
    private final String userChargeEnableRequest; // TODO what is this

    public ChargeState(
            boolean batteryHeaterOn,
            int batteryLevel,
            @Nonnull Length batteryRange,
            @Nonnull ElectricCurrent chargeCurrentRequest,
            @Nonnull ElectricCurrent chargeCurrentRequestMax,
            boolean chargeEnableRequest,
            @Nonnull Power chargeEnergyAdded,
            int chargeLimitSOC,
            int chargeLimitSOCMax,
            int chargeLimitSOCMin,
            int chargeLimitSOCSTD,
            @Nonnull Length chargeDistanceAddedIdeal,
            @Nonnull Length chargeDistanceAddedRated,
            boolean chargePortColdWeatherMode,
            boolean chargePortDoorOpen,
            @Nonnull Latch chargePortLatch,
            @Nonnull ElectricCurrent chargeRate,
            boolean chargeToMaxRange,
            @Nonnull ElectricCurrent chargerActualCurrent,
            @Nullable String chargerPhases,
            @Nonnull ElectricCurrent chargerPilotCurrent,
            @Nonnull Power chargerPower,
            @Nonnull ElectricPotential chargerVoltage,
            @Nonnull Object chargingState,
            @Nonnull String connChargeCable,
            @Nonnull Length estimatedBatteryRange,
            @Nonnull String fastChargerBrand,
            boolean fastChargerPresent,
            @Nonnull String fastChargerType,
            @Nonnull Length idealBatteryRange,
            boolean managedChargingActive,
            @Nonnull String managedChargingStartTime,
            boolean managedChargingUserCanceled,
            int maxRangeChargeCounter,
            @Nonnull Duration durationToFullCharge,
            boolean notEnoughPowerToHeat,
            boolean scheduledChargingPending,
            @Nullable Instant scheduledChargingStartTime,
            @Nonnull Duration timeToFullCharge,
            @Nonnull Instant timestamp,
            boolean tripCharging,
            int usableBatteryLevel,
            @Nonnull String userChargeEnableRequest
    ){
        this.batteryHeaterOn = batteryHeaterOn;
        this.batteryLevel = batteryLevel;
        this.batteryRange = batteryRange;
        this.chargeCurrentRequest = chargeCurrentRequest;
        this.chargeCurrentRequestMax = chargeCurrentRequestMax;
        this.chargeEnableRequest = chargeEnableRequest;
        this.chargeEnergyAdded = chargeEnergyAdded;
        this.chargeLimitSOC = chargeLimitSOC;
        this.chargeLimitSOCMax = chargeLimitSOCMax;
        this.chargeLimitSOCMin = chargeLimitSOCMin;
        this.chargeLimitSOCSTD = chargeLimitSOCSTD;
        this.chargeDistanceAddedIdeal = chargeDistanceAddedIdeal;
        this.chargeDistanceAddedRated = chargeDistanceAddedRated;
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
        this.estimatedBatteryRange = estimatedBatteryRange;
        this.fastChargerBrand = fastChargerBrand;
        this.fastChargerPresent = fastChargerPresent;
        this.fastChargerType = fastChargerType;
        this.idealBatteryRange = idealBatteryRange;
        this.managedChargingActive = managedChargingActive;
        this.managedChargingStartTime = managedChargingStartTime;
        this.managedChargingUserCanceled = managedChargingUserCanceled;
        this.maxRangeChargeCounter = maxRangeChargeCounter;
        this.durationToFullCharge = durationToFullCharge;
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

    @Nonnegative
    public int getBatteryLevel(){
        return batteryLevel;
    }

    @Nonnull
    public Length getBatteryRange(){
        return batteryRange;
    }

    @Nonnull
    public ElectricCurrent getChargeCurrentRequest(){
        return chargeCurrentRequest;
    }

    @Nonnull
    public ElectricCurrent getChargeCurrentRequestMax(){
        return chargeCurrentRequestMax;
    }

    public boolean isChargeEnableRequest(){
        return chargeEnableRequest;
    }

    @Nonnull
    public Power getChargeEnergyAdded(){
        return chargeEnergyAdded;
    }

    @Nonnegative
    public int getChargeLimitSOC(){
        return chargeLimitSOC;
    }

    @Nonnegative
    public int getChargeLimitSOCMax(){
        return chargeLimitSOCMax;
    }

    @Nonnegative
    public int getChargeLimitSOCMin(){
        return chargeLimitSOCMin;
    }

    @Nonnegative
    public int getChargeLimitSOCSTD(){
        return chargeLimitSOCSTD;
    }

    @Nonnull
    public Length getChargeDistanceAddedIdeal(){
        return chargeDistanceAddedIdeal;
    }

    @Nonnull
    public Length getChargeDistanceAddedRated(){
        return chargeDistanceAddedRated;
    }

    public boolean isChargePortColdWeatherMode(){
        return chargePortColdWeatherMode;
    }

    public boolean isChargePortDoorOpen(){
        return chargePortDoorOpen;
    }

    @Nonnull
    public Latch getChargePortLatch(){
        return chargePortLatch;
    }

    @Nonnull
    public ElectricCurrent getChargeRate(){
        return chargeRate;
    }

    public boolean isChargeToMaxRange(){
        return chargeToMaxRange;
    }

    @Nonnull
    public ElectricCurrent getChargerActualCurrent(){
        return chargerActualCurrent;
    }

    @Nonnull
    public Optional<String> getChargerPhases(){
        return Optional.ofNullable(chargerPhases);
    }

    @Nonnull
    public ElectricCurrent getChargerPilotCurrent(){
        return chargerPilotCurrent;
    }

    @Nonnull
    public Power getChargerPower(){
        return chargerPower;
    }

    @Nonnull
    public ElectricPotential getChargerVoltage(){
        return chargerVoltage;
    }

    @Nonnull
    public Object getChargingState(){
        return chargingState;
    }

    @Nonnull
    public String getConnChargeCable(){
        return connChargeCable;
    }

    @Nonnull
    public Length getEstimatedBatteryRange(){
        return estimatedBatteryRange;
    }

    @Nonnull
    public String getFastChargerBrand(){
        return fastChargerBrand;
    }

    public boolean isFastChargerPresent(){
        return fastChargerPresent;
    }

    @Nonnull
    public String getFastChargerType(){
        return fastChargerType;
    }

    @Nonnull
    public Length getIdealBatteryRange(){
        return idealBatteryRange;
    }

    public boolean isManagedChargingActive(){
        return managedChargingActive;
    }

    @Nonnull
    public String getManagedChargingStartTime(){
        return managedChargingStartTime;
    }

    public boolean isManagedChargingUserCanceled(){
        return managedChargingUserCanceled;
    }

    @Nonnegative
    public int getMaxRangeChargeCounter(){
        return maxRangeChargeCounter;
    }

    @Nonnull
    public Duration getDurationToFullCharge(){
        return durationToFullCharge;
    }

    public boolean isNotEnoughPowerToHeat(){
        return notEnoughPowerToHeat;
    }

    public boolean isScheduledChargingPending(){
        return scheduledChargingPending;
    }

    @Nonnull
    public Optional<Instant> getScheduledChargingStartTime(){
        return Optional.ofNullable(scheduledChargingStartTime);
    }

    @Nonnull
    public Duration getTimeToFullCharge(){
        return timeToFullCharge;
    }

    @Nonnull
    public Instant getTimestamp(){
        return timestamp;
    }

    public boolean isTripCharging(){
        return tripCharging;
    }

    public int getUsableBatteryLevel(){
        return usableBatteryLevel;
    }

    @Nonnull
    public String getUserChargeEnableRequest(){
        return userChargeEnableRequest;
    }

    public enum Latch{ // TODO check if this is exhaustive list
        DISENGAGED,
        ENGAGED
    }
}
