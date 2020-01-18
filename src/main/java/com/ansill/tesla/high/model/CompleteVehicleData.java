package com.ansill.tesla.high.model;

import javax.annotation.Nonnull;

public final class CompleteVehicleData extends VehicleID{

    protected CompleteVehicleData(@Nonnull String id, @Nonnull String vin, @Nonnull Object state){
        super(id, vin);
    }

    @Nonnull
    public static CompleteVehicleData convert(com.ansill.tesla.low.model.CompleteVehicleData original){
        return null;
    }
}
