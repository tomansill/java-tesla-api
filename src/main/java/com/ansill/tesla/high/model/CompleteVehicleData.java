package com.ansill.tesla.high.model;

import javax.annotation.Nonnull;

public final class CompleteVehicleData extends Vehicle{

    protected CompleteVehicleData(@Nonnull String id, @Nonnull String vin, @Nonnull Vehicle.State state){
        super(id, vin, state);
    }

    @Nonnull
    public static CompleteVehicleData convert(com.ansill.tesla.low.model.CompleteVehicleData original){
        return null;
    }
}
