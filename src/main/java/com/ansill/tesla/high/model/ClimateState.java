package com.ansill.tesla.high.model;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.measure.Quantity;
import javax.measure.quantity.Temperature;

/** Climate State */
@Immutable
public final class ClimateState{

    /** Fan speed from levels 0 and up */
    private final int fanSpeedStatus;

    /** Temperature inside the vehicle */
    @Nonnull
    private final Quantity<Temperature> insideTemperature;

    /** Temperature outside of the vehicle */
    @Nonnull
    private final Quantity<Temperature> outsideTemperature;

    /** Flag to indicate if auto conditioning is turned on or not */
    private final boolean isAutoConditioningTurnedOn;

    /** Flag to indicate if climate control is turned on or not */
    private final boolean isClimateControlTurnedOn;

    //private final boolean isPreconditioning; // TODO What is this, does this belong to climate state?

    /** Flag to indicate if rear window defroster is turned on or not */
    private final boolean isRearWindowDefrosterTurnedOn;

    /** Flag to indicate if front window defroster is turned on or not */
    private final boolean isFrontWindowDefrosterTurnedOn;

    /**
     * ClimateState constructor
     *
     * @param fanSpeedStatus                 Fan speed from levels 0 and up
     * @param insideTemperature              Temperature inside the vehicle
     * @param outsideTemperature             Temperature outside of the vehicle
     * @param isAutoConditioningTurnedOn     Flag to indicate if auto conditioning is turned on or not
     * @param isClimateControlTurnedOn       Flag to indicate if climate control is turned on or not
     * @param isRearWindowDefrosterTurnedOn  Flag to indicate if rear window defroster is turned on or not
     * @param isFrontWindowDefrosterTurnedOn Flag to indicate if front window defroster is turned on or not
     */
    ClimateState(
            int fanSpeedStatus,
            @Nonnull Quantity<Temperature> insideTemperature,
            @Nonnull Quantity<Temperature> outsideTemperature,
            boolean isAutoConditioningTurnedOn,
            boolean isClimateControlTurnedOn,
            boolean isRearWindowDefrosterTurnedOn,
            boolean isFrontWindowDefrosterTurnedOn
    ){
        this.fanSpeedStatus = fanSpeedStatus;
        this.insideTemperature = insideTemperature;
        this.outsideTemperature = outsideTemperature;
        this.isAutoConditioningTurnedOn = isAutoConditioningTurnedOn;
        this.isClimateControlTurnedOn = isClimateControlTurnedOn;
        this.isRearWindowDefrosterTurnedOn = isRearWindowDefrosterTurnedOn;
        this.isFrontWindowDefrosterTurnedOn = isFrontWindowDefrosterTurnedOn;
    }

    /**
     * Converts medium-level to high-level object
     *
     * @param state medium level object
     * @return high-level object
     */
    @Nonnull
    public static ClimateState convert(@Nonnull com.ansill.tesla.med.model.ClimateState state){
        return new ClimateState(
                state.getFanStatus(),
                state.getInsideTemp(),
                state.getOutsideTemp(),
                state.isAutoConditioningOn().orElse(false),
                state.isClimateOn(),
                state.isRearDefrosterOn(),
                state.isFrontDefrosterOn()
        );
    }

    /**
     * Returns fan speed from levels 0 and up
     *
     * @return integer indicating a level, could be 0 and up
     */
    public int getFanSpeedStatus(){
        return fanSpeedStatus;
    }

    /**
     * Returns temperature inside the vehicle
     *
     * @return temperature
     */
    @Nonnull
    public Quantity<Temperature> getInsideTemperature(){
        return insideTemperature;
    }

    /**
     * Returns temperature outside of the vehicle
     *
     * @return temperature
     */
    @Nonnull
    public Quantity<Temperature> getOutsideTemperature(){
        return outsideTemperature;
    }

    /**
     * Reports whether if auto conditioning is turned on or not
     *
     * @return true if it is turned on, false if it is not
     */
    public boolean isAutoConditioningTurnedOn(){
        return isAutoConditioningTurnedOn;
    }

    /**
     * Reports whether if climate control is turned on or not
     *
     * @return true if it is turned on, false if it is not
     */
    public boolean isClimateControlTurnedOn(){
        return isClimateControlTurnedOn;
    }

    /**
     * Reports whether if rear window defroster is turned on or not
     *
     * @return true if it is turned on, false if it is not
     */
    public boolean isRearWindowDefrosterTurnedOn(){
        return isRearWindowDefrosterTurnedOn;
    }

    /**
     * Reports whether if front window defroster is turned on or not
     *
     * @return true if it is turned on, false if it is not
     */
    public boolean isFrontWindowDefrosterTurnedOn(){
        return isFrontWindowDefrosterTurnedOn;
    }
}
