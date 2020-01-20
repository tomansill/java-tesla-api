package com.ansill.tesla.high.model;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.measure.Quantity;
import javax.measure.quantity.Length;

/** Vehicle State */
@Immutable
public final class VehicleState{

    ///** Flag to indicate whether notifications is supporte */
    //private final boolean notificationsSupport; // TODO do we need this?

    /** Flag to report if Sentry is turned on */
    private final boolean sentryMode;

    /** Flag to report if Sentry is available */
    private final boolean sentryModeAvailable;

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
     * @param sentryMode          Flag to report if Sentry is turned on
     * @param sentryModeAvailable Flag to report if Sentry is available
     * @param odometer            Odometer
     * @param locked              Flag to report if the vehicle is locked or not
     * @param userPresent         Flag to report if user is in the car
     * @param vehicleVersion      Vehicle version
     */
    VehicleState(
            boolean sentryMode,
            boolean sentryModeAvailable,
            @Nonnull Quantity<Length> odometer,
            boolean locked,
            boolean userPresent,
            @Nonnull String vehicleVersion
    ){
        this.sentryMode = sentryMode;
        this.sentryModeAvailable = sentryModeAvailable;
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
    public static VehicleState convert(@Nonnull com.ansill.tesla.med.model.VehicleState state){
        return new VehicleState(
                state.isSentryMode(),
                state.isSentryModeAvailable(),
                state.getOdometer(),
                state.isLocked(),
                state.isUserPresent(),
                state.getCarVersion()
        );
    }

    /**
     * Reports whether if Sentry is active
     *
     * @return true if active, false if it is inactive
     */
    public boolean isSentryActive(){
        return sentryMode;
    }

    /**
     * Reports whether if Sentry mode is available
     *
     * @return true if available, false if it is unavailable
     */
    public boolean isSentryModeAvailable(){
        return sentryModeAvailable;
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
}
