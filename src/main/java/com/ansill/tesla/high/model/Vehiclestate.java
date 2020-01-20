package com.ansill.tesla.high.model;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

/** Complete vehicle state */
@Immutable
public final class Vehiclestate{

    @Nonnull
    private final BatteryState batteryState;

    @Nonnull
    private final ChargeState chargeState;

    @Nonnull
    private final DriveState driveState;

    private Vehiclestate(
            @Nonnull BatteryState batteryState,
            @Nonnull ChargeState chargeState,
            @Nonnull DriveState driveState
    ){
        this.batteryState = batteryState;
        this.chargeState = chargeState;
        this.driveState = driveState;
    }

    @Nonnull
    public BatteryState getBatteryState(){
        return batteryState;
    }

    @Nonnull
    public ChargeState getChargeState(){
        return chargeState;
    }

    @Nonnull
    public DriveState getDriveState(){
        return driveState;
    }
}
