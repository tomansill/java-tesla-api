package com.ansill.tesla.high;

import javax.annotation.Nonnull;
import javax.measure.Quantity;
import javax.measure.quantity.Length;

public class Vehicle{

    @Nonnull
    private final String id;

    @Nonnull
    private final String vin;

    @Nonnull
    private final Account parent;

    Vehicle(@Nonnull String id, @Nonnull String vin, @Nonnull Account parent){
        this.id = id;
        this.vin = vin;
        this.parent = parent;
    }

    @Nonnull
    public Quantity<Length> getOdometer(){
        return null;
    }
}
