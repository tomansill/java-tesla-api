package com.ansill.tesla.high.model;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.measure.Unit;
import javax.measure.quantity.Speed;
import javax.measure.quantity.Temperature;

/** GUI Settings */
@Immutable
public final class GUISettings{

    /** True to use 24 hours, false to use 12 hours */
    private final boolean using24HoursTime;

    /** Charging Rate unit */
    @Nonnull
    private final Unit<Speed> chargingRateUnit;

    /** Movement speed unit */
    @Nonnull
    private final Unit<Speed> movementSpeedUnit;

    /** Temperature unit */
    @Nonnull
    private final Unit<Temperature> temperatureUnit;

    // TODO is show_range_units useful?

    /**
     * GUISettings constructor
     *
     * @param using24HoursTime  True to use 24 hours, false to use 12 hours
     * @param chargingRateUnit  Charging Rate unit
     * @param movementSpeedUnit Movement speed unit
     * @param temperatureUnit   Temperature unit
     */
    private GUISettings(
            boolean using24HoursTime,
            @Nonnull Unit<Speed> chargingRateUnit,
            @Nonnull Unit<Speed> movementSpeedUnit,
            @Nonnull Unit<Temperature> temperatureUnit
    ){
        this.using24HoursTime = using24HoursTime;
        this.chargingRateUnit = chargingRateUnit;
        this.movementSpeedUnit = movementSpeedUnit;
        this.temperatureUnit = temperatureUnit;
    }

    /**
     * Converts medium-level to high-level object
     *
     * @param settings medium level object
     * @return high-level object
     */
    @Nonnull
    public static GUISettings convert(@Nonnull com.ansill.tesla.med.model.GuiSettings settings){
        return new GUISettings(
                settings.isUsing24HoursTime(),
                settings.getChargeRateUnits(),
                settings.getDistanceUnits(),
                settings.getTemperatureUnits()
        );
    }

    /**
     * Reports whether if 24 hours time is used
     *
     * @return true if 24 hours time is used, false if 12 hours time is used
     */
    public boolean isUsing24HoursTime(){
        return using24HoursTime;
    }

    /**
     * Charging Rate unit
     *
     * @return unit
     */
    @Nonnull
    public Unit<Speed> getChargingRateUnit(){
        return chargingRateUnit;
    }

    /**
     * Movement speed unit
     *
     * @return unit
     */
    @Nonnull
    public Unit<Speed> getMovementSpeedUnit(){
        return movementSpeedUnit;
    }

    /**
     * Temperature unit
     *
     * @return unit
     */
    @Nonnull
    public Unit<Temperature> getTemperatureUnit(){
        return temperatureUnit;
    }
}
