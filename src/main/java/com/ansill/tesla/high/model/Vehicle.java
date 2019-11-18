package com.ansill.tesla.high.model;

import javax.annotation.Nonnull;

public class Vehicle{

    @Nonnull
    private final String id;

    @Nonnull
    private final String vin;

    @Nonnull
    private final State state;

    protected Vehicle(@Nonnull String id, @Nonnull String vin, @Nonnull State state){
        this.id = id;
        this.vin = vin;
        this.state = state;
    }

    @Nonnull
    public static Vehicle convert(com.ansill.tesla.low.model.Vehicle original){
        return new Vehicle(
                original.getIdString(),
                original.getVIN(),
                State.valueOf(original.getState().toUpperCase())
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

    @Nonnull
    public State getState(){
        return state;
    }

    public enum State{
        ONLINE,
        OFFLINE // TODO confirm if there are other values
    }
}
