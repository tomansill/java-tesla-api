package com.ansill.tesla.api.high.model;

import com.ansill.tesla.api.model.Coordinate;
import com.ansill.tesla.api.model.ShiftState;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.measure.Quantity;
import javax.measure.quantity.Angle;
import javax.measure.quantity.Power;
import javax.measure.quantity.Speed;

/** Drive State */
@Immutable
public final class DriveState{

    /** Shift state */
    @Nonnull
    private final ShiftState shiftState;

    /** Power usage */
    @Nonnull
    private final Quantity<Power> powerUsage;

    /** Speed */
    @Nonnull
    private final Quantity<Speed> speed;

    /** Location */
    @Nonnull
    private final Coordinate location;

    /** Heading */
    @Nonnull
    private final Quantity<Angle> heading;

    /**
     * DriveState constructor
     *
     * @param shiftState shift state
     * @param powerUsage power usage
     * @param speed      speed
     * @param location   location
     * @param heading    heading
     */
    private DriveState(
            @Nonnull ShiftState shiftState,
            @Nonnull Quantity<Power> powerUsage,
            @Nonnull Quantity<Speed> speed,
            @Nonnull Coordinate location,
            @Nonnull Quantity<Angle> heading
    ){
        this.shiftState = shiftState;
        this.powerUsage = powerUsage;
        this.speed = speed;
        this.location = location;
        this.heading = heading;
    }

    /**
     * Converts medium-level to high-level object
     *
     * @param state medium level object
     * @return high-level object
     */
    @Nonnull
    public static DriveState convert(@Nonnull com.ansill.tesla.api.med.model.DriveState state){
        return new DriveState(
                state.getShiftState(),
                state.getPower(),
                state.getSpeed(),
                new Coordinate(state.getLatitude(), state.getLongitude()),
                state.getHeading()
        );
    }

    /**
     * Returns shift state
     *
     * @return state
     */
    @Nonnull
    public ShiftState getShiftState(){
        return shiftState;
    }

    /**
     * Returns power usage
     *
     * @return power
     */
    @Nonnull
    public Quantity<Power> getPowerUsage(){
        return powerUsage;
    }

    /**
     * Returns speed
     *
     * @return speed
     */
    @Nonnull
    public Quantity<Speed> getSpeed(){
        return speed;
    }

    /**
     * Returns location
     *
     * @return location as an coordinate
     */
    @Nonnull
    public Coordinate getLocation(){
        return location;
    }

    /**
     * Returns heading
     *
     * @return heading in angle
     */
    @Nonnull
    public Quantity<Angle> getHeading(){
        return heading;
    }
}
