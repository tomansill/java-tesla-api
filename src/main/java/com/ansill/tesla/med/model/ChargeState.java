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
    private final Object chargePortLatch; // TODO get all possible enums

    @Nonnull
    private final Object chargeRate; // TODO find the rate

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
    private final Object chargingState; // TODO find all possible enums

    @Nonnull
    private final String connChargeCable; // TODO find values

    @Nonnull
    private final Length estimatedBatteryRange;

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
            @Nonnull Object chargePortLatch,
            @Nonnull Object chargeRate,
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
}
