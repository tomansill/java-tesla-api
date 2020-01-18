package com.ansill.tesla.high.model;

import com.ansill.tesla.high.TeslaAccount;

import javax.annotation.Nonnull;

public class Vehicle{

    @Nonnull
    private final String id;

    @Nonnull
    private final String vin;

    @Nonnull
    private final TeslaAccount parent;

    Vehicle(@Nonnull String id, @Nonnull String vin, @Nonnull TeslaAccount parent){
        this.id = id;
        this.vin = vin;
        this.parent = parent;
    }

}
