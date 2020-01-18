package com.ansill.tesla.high.model;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.measure.Unit;
import javax.measure.quantity.Length;

@SuppressWarnings("unused")
@Immutable
public class BatteryState{

    /** Flag to indicate that battery is heating */
    private final boolean batteryHeaterActive;
    /** Battery percentage from 0 to 100 */
    private final int batteryCapacityPercentage;
    /** Usable battery percentage from 0 to 100 */
    private final int usableBatteryCapacityPercentage;
    /** The vehicle's range with current battery capacity */
    @Nonnull
    private final Unit<Length> batteryRange;
    /** The vehicle's range with ideal battery capacity */
    @Nonnull
    private final Unit<Length> idealBatteryRange;
    /** The vehicle's range with estimated battery capacity */
    @Nonnull
    private final Unit<Length> estimatedBatteryRange;

    BatteryState(
            boolean batteryHeaterActive,
            int batteryCapacityPercentage,
            int usableBatteryCapacityPercentage,
            @Nonnull Unit<Length> batteryRange,
            @Nonnull Unit<Length> idealBatteryRange,
            @Nonnull Unit<Length> estimatedBatteryRange
    ){
        this.batteryHeaterActive = batteryHeaterActive;
        this.batteryCapacityPercentage = batteryCapacityPercentage;
        this.usableBatteryCapacityPercentage = usableBatteryCapacityPercentage;
        this.batteryRange = batteryRange;
        this.idealBatteryRange = idealBatteryRange;
        this.estimatedBatteryRange = estimatedBatteryRange;
    }

    public boolean isBatteryHeaterActive(){
        return batteryHeaterActive;
    }

    public int getBatteryCapacityPercentage(){
        return batteryCapacityPercentage;
    }

    public int getUsableBatteryCapacityPercentage(){
        return usableBatteryCapacityPercentage;
    }

    @Nonnull
    public Unit<Length> getBatteryRange(){
        return batteryRange;
    }

    @Nonnull
    public Unit<Length> getIdealBatteryRange(){
        return idealBatteryRange;
    }

    @Nonnull
    public Unit<Length> getEstimatedBatteryRange(){
        return estimatedBatteryRange;
    }

    /*
     public static class LimitSetting{

     private int chargeBatteryPercentageLimit; // Current selected percentage

     private int chargeBatteryPercentageStandardLimit; // The "recommended" percentage

     private int chargeBatteryPercentageMaxLimit; // Minimum percentage as allowed

     private int chargeBatteryPercentageMinLimit; // Maximum percentage as allowed

     }
     */
}
