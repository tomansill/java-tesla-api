package com.ansill.tesla.med.model;

import com.ansill.tesla.model.ChargingState;
import com.ansill.tesla.model.LatchState;
import com.ansill.tesla.model.USUnits;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.measure.Quantity;
import javax.measure.quantity.Dimensionless;
import javax.measure.quantity.ElectricCurrent;
import javax.measure.quantity.ElectricPotential;
import javax.measure.quantity.Length;
import javax.measure.quantity.Power;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Immutable
public final class ChargeState{

    private final boolean batteryHeaterOn;

    @Nonnull
    private final Quantity<Dimensionless> batteryLevel;

    @Nonnull
    private final Quantity<Length> batteryRange;

    @Nonnull
    private final Quantity<ElectricCurrent> chargeCurrentRequest;

    @Nonnull
    private final Quantity<ElectricCurrent> chargeCurrentRequestMax;

    private final boolean chargeEnableRequest;

    @Nonnull
    private final Quantity<Power> chargeEnergyAdded;

    @Nonnull
    private final Quantity<Dimensionless> chargeLimitSOC;
    @Nonnull
    private final Quantity<Dimensionless> chargeLimitSOCMax;
    @Nonnull
    private final Quantity<Dimensionless> chargeLimitSOCMin;
    @Nonnull
    private final Quantity<Dimensionless> chargeLimitSOCStandard;
    @Nonnull
    private final Quantity<Length> chargeDistanceAddedIdeal;
    @Nonnull
    private final Quantity<Length> chargeDistanceAddedRated;
    private final boolean chargePortColdWeatherMode;
    private final boolean chargePortDoorOpen;
    @Nonnull
    private final LatchState chargePortLatch;
    @Nonnull
    private final Quantity<ElectricCurrent> chargeRate;
    private final boolean chargeToMaxRange;
    @Nonnull
    private final Quantity<ElectricCurrent> chargerActualCurrent;
    @Nullable
    private final String chargerPhases; // TODO find all possible enums
    @Nonnull
    private final Quantity<ElectricCurrent> chargerPilotCurrent;
    @Nonnull
    private final Quantity<Power> chargerPower;
    @Nonnull
    private final Quantity<ElectricPotential> chargerVoltage;
    @Nonnull
    private final ChargingState chargingState;
    @Nonnull
    private final String connChargeCable;
    @Nonnull
    private final Quantity<Length> estimatedBatteryRange; // Originally "est_battery_range"
    @Nonnull
    private final String fastChargerBrand;
    private final boolean fastChargerPresent;
    @Nonnull
    private final String fastChargerType;
    @Nonnull
    private final Quantity<Length> idealBatteryRange;
    private final boolean managedChargingActive;
    @Nonnull
    private final String managedChargingStartTime; // TODO what is this
    private final boolean managedChargingUserCanceled;
    private final int maxRangeChargeCounter; // TODO what is this
    @Nonnull
    private final Duration durationToFullCharge;

    @Nullable
    private final Boolean notEnoughPowerToHeat;

    private final boolean scheduledChargingPending;

    @Nullable
    private final Instant scheduledChargingStartTime;

    @Nonnull
    private final Duration timeToFullCharge;

    @Nonnull
    private final Instant timestamp;

    private final boolean tripCharging;

    @Nonnull
    private final Quantity<Dimensionless> usableBatteryLevel;

    @Nonnull
    private final String userChargeEnableRequest; // TODO what is this

    private ChargeState(
            boolean batteryHeaterOn,
            @Nonnull Quantity<Dimensionless> batteryLevel,
            @Nonnull Quantity<Length> batteryRange,
            @Nonnull Quantity<ElectricCurrent> chargeCurrentRequest,
            @Nonnull Quantity<ElectricCurrent> chargeCurrentRequestMax,
            boolean chargeEnableRequest,
            @Nonnull Quantity<Power> chargeEnergyAdded,
            @Nonnull Quantity<Dimensionless> chargeLimitSOC,
            @Nonnull Quantity<Dimensionless> chargeLimitSOCMax,
            @Nonnull Quantity<Dimensionless> chargeLimitSOCMin,
            @Nonnull Quantity<Dimensionless> chargeLimitSOCStandard,
            @Nonnull Quantity<Length> chargeDistanceAddedIdeal,
            @Nonnull Quantity<Length> chargeDistanceAddedRated,
            boolean chargePortColdWeatherMode,
            boolean chargePortDoorOpen,
            @Nonnull LatchState chargePortLatch,
            @Nonnull Quantity<ElectricCurrent> chargeRate,
            boolean chargeToMaxRange,
            @Nonnull Quantity<ElectricCurrent> chargerActualCurrent,
            @Nullable String chargerPhases,
            @Nonnull Quantity<ElectricCurrent> chargerPilotCurrent,
            @Nonnull Quantity<Power> chargerPower,
            @Nonnull Quantity<ElectricPotential> chargerVoltage,
            @Nonnull ChargingState chargingState,
            @Nonnull String connChargeCable,
            @Nonnull Quantity<Length> estimatedBatteryRange,
            @Nonnull String fastChargerBrand,
            boolean fastChargerPresent,
            @Nonnull String fastChargerType,
            @Nonnull Quantity<Length> idealBatteryRange,
            boolean managedChargingActive,
            @Nonnull String managedChargingStartTime,
            boolean managedChargingUserCanceled,
            int maxRangeChargeCounter,
            @Nonnull Duration durationToFullCharge,
            @Nullable Boolean notEnoughPowerToHeat,
            boolean scheduledChargingPending,
            @Nullable Instant scheduledChargingStartTime,
            @Nonnull Duration timeToFullCharge,
            @Nonnull Instant timestamp,
            boolean tripCharging,
            @Nonnull Quantity<Dimensionless> usableBatteryLevel,
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
        this.chargeLimitSOCStandard = chargeLimitSOCStandard;
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

    @Nonnull
    public static ChargeState convert(@Nonnull com.ansill.tesla.low.model.ChargeState chargeState){
        return new ChargeState(
                chargeState.getBatteryHeaterOn(),
                Quantities.getQuantity(chargeState.getBatteryLevel(), Units.PERCENT),
                Quantities.getQuantity(chargeState.getBatteryRange(), USUnits.MILE),
                Quantities.getQuantity(chargeState.getChargeCurrentRequest(), Units.AMPERE),
                Quantities.getQuantity(chargeState.getChargeCurrentRequestMax(), Units.AMPERE),
                chargeState.isChargeEnableRequest(),
                Quantities.getQuantity(
                        chargeState.getChargeEnergyAdded(),
                        Units.WATT.multiply(1000.00).asType(Power.class)
                ),
                Quantities.getQuantity(chargeState.getChargeLimitSoc(), Units.PERCENT),
                Quantities.getQuantity(chargeState.getChargeLimitSocMax(), Units.PERCENT),
                Quantities.getQuantity(chargeState.getChargeLimitSocMin(), Units.PERCENT),
                Quantities.getQuantity(chargeState.getChargeLimitSocStd(), Units.PERCENT),
                Quantities.getQuantity(chargeState.getChargeMilesAddedIdeal(), USUnits.MILE),
                Quantities.getQuantity(chargeState.getChargeMilesAddedRated(), USUnits.MILE),
                chargeState.getChargePortColdWeatherMode(),
                chargeState.getChargePortDoorOpen(),
                LatchState.valueOf(chargeState.getChargePortLatch().toUpperCase()),
                Quantities.getQuantity(chargeState.getChargeRate(), Units.AMPERE),
                chargeState.getChargeToMaxRange(),
                Quantities.getQuantity(chargeState.getChargerActualCurrent(), Units.AMPERE),
                chargeState.getChargerPhases(),
                Quantities.getQuantity(chargeState.getChargePilotCurrent(), Units.AMPERE),
                Quantities.getQuantity(chargeState.getChargerPower(), Units.WATT),
                Quantities.getQuantity(chargeState.getChargerVoltage(), Units.VOLT),
                ChargingState.valueOf(chargeState.getChargingState().toUpperCase()),
                chargeState.getConnChargeCable(),
                Quantities.getQuantity(chargeState.getEstBatteryRange(), USUnits.MILE),
                chargeState.getFastChargerBrand(),
                chargeState.getFastChargerPresent(),
                chargeState.getFastChargerType(),
                Quantities.getQuantity(chargeState.getIdealBatteryRange(), USUnits.MILE),
                chargeState.getManagedChargingActive(),
                chargeState.getManagedChargingStartTime(),
                chargeState.getManagedChargingUserCanceled(),
                chargeState.getMaxRangeChargerCounter(),
                Duration.ofMinutes(chargeState.getMinutesToFullCharge()),
                chargeState.getNotEnoughPowerToHeat().orElse(null),
                chargeState.getScheduledChargingPending(),
                chargeState.getScheduledChargingStartTime().map(Instant::parse).orElse(null),
                Duration.ofMinutes((int) (chargeState.getTimeToFullCharge() * 60)),
                Instant.ofEpochSecond(chargeState.getTimestamp()),
                chargeState.isTripCharging(),
                Quantities.getQuantity(chargeState.getUsableBatteryLevel(), Units.PERCENT),
                chargeState.getUserChargeEnableRequest()
        );
    }

    public boolean isBatteryHeaterOn(){
        return batteryHeaterOn;
    }

    @Nonnull
    public Quantity<Dimensionless> getBatteryLevel(){
        return batteryLevel;
    }

    @Nonnull
    public Quantity<Length> getBatteryRange(){
        return batteryRange;
    }

    @Nonnull
    public Quantity<ElectricCurrent> getChargeCurrentRequest(){
        return chargeCurrentRequest;
    }

    @Nonnull
    public Quantity<ElectricCurrent> getChargeCurrentRequestMax(){
        return chargeCurrentRequestMax;
    }

    public boolean isChargeEnableRequest(){
        return chargeEnableRequest;
    }

    @Nonnull
    public Quantity<Power> getChargeEnergyAdded(){
        return chargeEnergyAdded;
    }

    @Nonnull
    public Quantity<Dimensionless> getChargeLimitSOC(){
        return chargeLimitSOC;
    }

    @Nonnull
    public Quantity<Dimensionless> getChargeLimitSOCMax(){
        return chargeLimitSOCMax;
    }

    @Nonnull
    public Quantity<Dimensionless> getChargeLimitSOCMin(){
        return chargeLimitSOCMin;
    }

    @Nonnull
    public Quantity<Dimensionless> getChargeLimitSOCStandard(){
        return chargeLimitSOCStandard;
    }

    @Nonnull
    public Quantity<Length> getChargeDistanceAddedIdeal(){
        return chargeDistanceAddedIdeal;
    }

    @Nonnull
    public Quantity<Length> getChargeDistanceAddedRated(){
        return chargeDistanceAddedRated;
    }

    public boolean isChargePortColdWeatherMode(){
        return chargePortColdWeatherMode;
    }

    public boolean isChargePortDoorOpen(){
        return chargePortDoorOpen;
    }

    @Nonnull
    public LatchState getChargePortLatch(){
        return chargePortLatch;
    }

    @Nonnull
    public Quantity<ElectricCurrent> getChargeRate(){
        return chargeRate;
    }

    public boolean isChargeToMaxRange(){
        return chargeToMaxRange;
    }

    @Nonnull
    public Quantity<ElectricCurrent> getChargerActualCurrent(){
        return chargerActualCurrent;
    }

    @Nonnull
    public Optional<String> getChargerPhases(){
        return Optional.ofNullable(chargerPhases);
    }

    @Nonnull
    public Quantity<ElectricCurrent> getChargerPilotCurrent(){
        return chargerPilotCurrent;
    }

    @Nonnull
    public Quantity<Power> getChargerPower(){
        return chargerPower;
    }

    @Nonnull
    public Quantity<ElectricPotential> getChargerVoltage(){
        return chargerVoltage;
    }

    @Nonnull
    public ChargingState getChargingState(){
        return chargingState;
    }

    @Nonnull
    public String getConnChargeCable(){
        return connChargeCable;
    }

    @Nonnull
    public Quantity<Length> getEstimatedBatteryRange(){
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
    public Quantity<Length> getIdealBatteryRange(){
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

    @Nonnull
    public Optional<Boolean> isNotEnoughPowerToHeat(){
        return Optional.ofNullable(notEnoughPowerToHeat);
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

    @Nonnull
    public Quantity<Dimensionless> getUsableBatteryLevel(){
        return usableBatteryLevel;
    }

    @Nonnull
    public String getUserChargeEnableRequest(){
        return userChargeEnableRequest;
    }
}
