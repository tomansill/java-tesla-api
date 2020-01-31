package com.ansill.tesla.api.high.model;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.measure.Quantity;
import javax.measure.quantity.Length;

/** Vehicle State */
@Immutable
public final class VehicleState{

    ///** Flag to indicate whether notifications is supported */
    //private final boolean notificationsSupport; // TODO do we need this?

    /** Sentry mode state */
    @Nonnull
    private final SentryModeState sentryModeState;

    /** Odometer */
    @Nonnull
    private final Quantity<Length> odometer;

    /** Flag to report if the vehicle is locked or not */
    private final boolean locked;

    /** Flag to report if user is in the car */
    private final boolean userPresent;

    /** Vehicle version */
    @Nonnull
    private final String vehicleVersion;

    /**
     * VehicleState constructor
     *
     * @param sentryModeState Sentry mode state
     * @param odometer        Odometer
     * @param locked          Flag to report if the vehicle is locked or not
     * @param userPresent     Flag to report if user is in the car
     * @param vehicleVersion  Vehicle version
     */
    private VehicleState(
            @Nonnull SentryModeState sentryModeState, @Nonnull Quantity<Length> odometer,
            boolean locked,
            boolean userPresent,
            @Nonnull String vehicleVersion
    ){
        this.sentryModeState = sentryModeState;
        this.odometer = odometer;
        this.locked = locked;
        this.userPresent = userPresent;
        this.vehicleVersion = vehicleVersion;
    }

    /**
     * Converts medium-level to high-level object
     *
     * @param state medium level object
     * @return high-level object
     */
    @Nonnull
    public static VehicleState convert(@Nonnull com.ansill.tesla.api.med.model.VehicleState state){
        return new VehicleState(
                !state.isSentryModeAvailable() ? SentryModeState.NOT_AVAILABLE : state.isSentryMode() ? SentryModeState.ACTIVE : SentryModeState.INACTIVE,
                state.getOdometer(),
                state.isLocked(),
                state.isUserPresent(),
                state.getCarVersion()
        );
    }

    /**
     * Returns vehicle's odometer
     *
     * @return odometer
     */
    @Nonnull
    public Quantity<Length> getOdometer(){
        return odometer;
    }

    /**
     * Reports whether if vehicle is locked
     *
     * @return true if locked, false if it is unlocked
     */
    public boolean isLocked(){
        return locked;
    }

    /**
     * Reports whether if user is in the car
     *
     * @return true if inside the car, otherwise false
     */
    public boolean isUserPresent(){
        return userPresent;
    }

    /**
     * Returns vehicle version
     *
     * @return version
     */
    @Nonnull
    public String getVehicleVersion(){
        return vehicleVersion;
    }

    /**
     * Returns sentry mode state
     *
     * @return state
     */
    @Nonnull
    public SentryModeState getSentryModeState(){
        return sentryModeState;
    }
}
