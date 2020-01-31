package com.ansill.tesla.api.high.model;

import com.ansill.tesla.api.model.ChargingState;
import com.ansill.tesla.api.model.LatchState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.util.Optional;

/** Charge state */
@Immutable
public final class ChargeState{

    /** Report whether door is open or not */
    private final boolean isChargePortDoorOpen;

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
     * @param isChargePortColdWeatherModeEnabled Flag to indicate if charge port is currently on cold weather mode
     * @param latchState                         Latch mode
     * @param chargingState                      Charging state
     * @param chargeSession                      Charge session - null if not currently charging
     */
    private ChargeState(
            boolean isChargePortDoorOpen,
            boolean isChargePortColdWeatherModeEnabled,
            @Nonnull LatchState latchState,
            @Nonnull ChargingState chargingState,
            @Nullable ChargeSession chargeSession
    ){
        this.isChargePortDoorOpen = isChargePortDoorOpen;
        this.isChargePortColdWeatherModeEnabled = isChargePortColdWeatherModeEnabled;
        this.latchState = latchState;
        this.chargingState = chargingState;
        this.chargeSession = chargeSession;
    }

    /**
     * Converts medium-level to high-level object
     *
     * @param state medium level object
     * @return high-level object
     */
    @Nonnull
    public static ChargeState convert(@Nonnull com.ansill.tesla.api.med.model.ChargeState state){
        return new ChargeState(
                state.isChargePortDoorOpen(),
                state.isChargePortColdWeatherMode(),
                state.getChargePortLatch(),
                state.getChargingState(),
                ChargeSession.convert(state)
        );
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
