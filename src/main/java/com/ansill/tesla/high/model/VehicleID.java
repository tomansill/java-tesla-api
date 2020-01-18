package com.ansill.tesla.high.model;

import javax.annotation.Nonnull;

public class VehicleID{

    @Nonnull
    private final String id;

    @Nonnull
    private final String vin;

    protected VehicleID(@Nonnull String id, @Nonnull String vin){
        this.id = id;
        this.vin = vin;
    }

    @Nonnull
    public static VehicleID convert(com.ansill.tesla.low.model.Vehicle original){
        return new VehicleID(
                original.getIdString(),
                original.getVIN()
        );
    }

    @Nonnull
    public String getId(){
        return id;
    }

    @Nonnull
    public String getVIN(){
        return vin;
    }
}
