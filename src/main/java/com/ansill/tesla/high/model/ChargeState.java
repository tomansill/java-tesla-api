package com.ansill.tesla.high.model;

import com.ansill.tesla.model.ChargingState;
import com.ansill.tesla.model.LatchState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.measure.Quantity;
import javax.measure.quantity.Energy;
import javax.measure.quantity.Length;
import java.util.Optional;

/** Charge state */
@Immutable
public final class ChargeState{

    /** Report whether door is open or not */
    private final boolean isChargePortDoorOpen;

    /** Charge settings */
    @Nonnull
    private final ChargeSettings chargeSettings;

    /** Amount of energy added in last charge (or current if currently charging */
    @Nonnull
    private final Quantity<Energy> energyAddedInLastCharge;

    /** Amount of ideal distance added in last charge (or current if currently charging */
    @Nonnull
    private final Quantity<Length> idealDistanceAddedInLastCharge;

    /** Amount of rated distance added in last charge (or current if currently charging */
    @Nonnull
    private final Quantity<Length> ratedDistanceAddedInLastCharge;

    /** Flag to indicate if charge port is currently on cold weather mode */
    private final boolean isChargePortColdWeatherModeEnabled;

    /** Latch mode */
    @Nonnull
    private final LatchState latchState;

    /** Charging state */
    @Nonnull
    private final ChargingState chargingState;

    /** Charge session - null if not currently charging */
    @Nullable
    private final ChargeSession chargeSession;

    /**
     * ChargeState constructor
     *
     * @param isChargePortDoorOpen               Report whether door is open or not
     * @param chargeSettings                     Charge settings
     * @param energyAddedInLastCharge            Amount of energy added in last charge (or current if currently charging
     * @param idealDistanceAddedInLastCharge     Amount of ideal distance added in last charge (or current if currently charging
     * @param ratedDistanceAddedInLastCharge     Amount of rated distance added in last charge (or current if currently charging
     * @param isChargePortColdWeatherModeEnabled Flag to indicate if charge port is currently on cold weather mode
     * @param latchState                         Latch mode
     * @param chargingState                      Charging state
     * @param chargeSession                      Charge session - null if not currently charging
     */
    private ChargeState(
            boolean isChargePortDoorOpen,
            @Nonnull ChargeSettings chargeSettings,
            @Nonnull Quantity<Energy> energyAddedInLastCharge,
            @Nonnull Quantity<Length> idealDistanceAddedInLastCharge,
            @Nonnull Quantity<Length> ratedDistanceAddedInLastCharge,
            boolean isChargePortColdWeatherModeEnabled,
            @Nonnull LatchState latchState,
            @Nonnull ChargingState chargingState,
            @Nullable ChargeSession chargeSession
    ){
        this.isChargePortDoorOpen = isChargePortDoorOpen;
        this.chargeSettings = chargeSettings;
        this.energyAddedInLastCharge = energyAddedInLastCharge;
        this.idealDistanceAddedInLastCharge = idealDistanceAddedInLastCharge;
        this.ratedDistanceAddedInLastCharge = ratedDistanceAddedInLastCharge;
        this.isChargePortColdWeatherModeEnabled = isChargePortColdWeatherModeEnabled;
        this.latchState = latchState;
        this.chargingState = chargingState;
        this.chargeSession = chargeSession;
    }

    /**
     * Report whether door is open or not
     *
     * @return true if door is open, otherwise false
     */
    public boolean isChargePortDoorOpen(){
        return isChargePortDoorOpen;
    }

    /**
     * Returns charge settings
     *
     * @return settings
     */
    @Nonnull
    public ChargeSettings getChargeSettings(){
        return chargeSettings;
    }

    /**
     * Returns amount of energy added in last charge (or current charge if currently charging)
     *
     * @return amount of energy
     */
    @Nonnull
    public Quantity<Energy> getEnergyAddedInLastCharge(){
        return energyAddedInLastCharge;
    }

    /**
     * Returns ideal distance added in last charge (or current charge if currently charging)
     *
     * @return distance
     */
    @Nonnull
    public Quantity<Length> getIdealDistanceAddedInLastCharge(){
        return idealDistanceAddedInLastCharge;
    }

    /**
     * Returns rated distance added in last charge (or current charge if currently charging)
     *
     * @return distance
     */
    @Nonnull
    public Quantity<Length> getRatedDistanceAddedInLastCharge(){
        return ratedDistanceAddedInLastCharge;
    }

    /**
     * Reports whether the charge port door is on cold weather mode or not
     *
     * @return true if door is on cold weather mode, otherwise false
     */
    public boolean isChargePortColdWeatherModeEnabled(){
        return isChargePortColdWeatherModeEnabled;
    }

    /**
     * Returns latch state
     *
     * @return state
     */
    @Nonnull
    public LatchState getLatchState(){
        return latchState;
    }

    /**
     * Returns charging state
     *
     * @return state
     */
    @Nonnull
    public ChargingState getChargingState(){
        return chargingState;
    }

    /**
     * Returns charge session if currently charging
     *
     * @return optional object that contains charge session if vehicle is currently charging, otherwise empty
     */
    @Nonnull
    public Optional<ChargeSession> getChargeSession(){
        return Optional.ofNullable(chargeSession);
    }
}
