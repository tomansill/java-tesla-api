package com.ansill.tesla.med.model;

import com.ansill.tesla.model.ShiftState;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.measure.Quantity;
import javax.measure.quantity.Angle;
import javax.measure.quantity.Power;
import javax.measure.quantity.Speed;
import java.time.Instant;

@Immutable
public class DriveState{

    private final long gpsAsOf; // TODO What is this

    @Nonnull
    private final Quantity<Angle> heading;

    @Nonnull
    private final Quantity<Angle> latitude;

    @Nonnull
    private final Quantity<Angle> longitude;

    @Nonnull
    private final Quantity<Angle> nativeLatitude;

    private final int nativeLocationSupported; // TODO What are the possible values?

    @Nonnull
    private final Quantity<Angle> nativeLongitude;

    @Nonnull
    private final String nativeType; // TODO what are the possible values?

    @Nonnull
    private final Quantity<Power> power;

    @Nonnull
    private final ShiftState shiftState;

    @Nonnull
    private final Quantity<Speed> speed;

    @Nonnull
    private final Instant timestamp;

    public DriveState(
            long gpsAsOf,
            @Nonnull Quantity<Angle> heading,
            @Nonnull Quantity<Angle> latitude,
            @Nonnull Quantity<Angle> longitude,
            @Nonnull Quantity<Angle> nativeLatitude,
            int nativeLocationSupported,
            @Nonnull Quantity<Angle> nativeLongitude,
            @Nonnull String nativeType,
            @Nonnull Quantity<Power> power,
            @Nonnull ShiftState shiftState,
            @Nonnull Quantity<Speed> speed,
            @Nonnull Instant timestamp
    ){
        this.gpsAsOf = gpsAsOf;
        this.heading = heading;
        this.latitude = latitude;
        this.longitude = longitude;
        this.nativeLatitude = nativeLatitude;
        this.nativeLocationSupported = nativeLocationSupported;
        this.nativeLongitude = nativeLongitude;
        this.nativeType = nativeType;
        this.power = power;
        this.shiftState = shiftState;
        this.speed = speed;
        this.timestamp = timestamp;
    }

    @Nonnull
    public static DriveState convert(com.ansill.tesla.low.model.DriveState vehicleDriveState){
        return null; // TODO dodododo
    }

    public long getGpsAsOf(){
        return gpsAsOf;
    }

    @Nonnull
    public Quantity<Angle> getHeading(){
        return heading;
    }

    @Nonnull
    public Quantity<Angle> getLatitude(){
        return latitude;
    }

    @Nonnull
    public Quantity<Angle> getLongitude(){
        return longitude;
    }

    @Nonnull
    public Quantity<Angle> getNativeLatitude(){
        return nativeLatitude;
    }

    public int getNativeLocationSupported(){
        return nativeLocationSupported;
    }

    @Nonnull
    public Quantity<Angle> getNativeLongitude(){
        return nativeLongitude;
    }

    @Nonnull
    public String getNativeType(){
        return nativeType;
    }

    @Nonnull
    public Quantity<Power> getPower(){
        return power;
    }

    @Nonnull
    public ShiftState getShiftState(){
        return shiftState;
    }

    @Nonnull
    public Quantity<Speed> getSpeed(){
        return speed;
    }

    @Nonnull
    public Instant getTimestamp(){
        return timestamp;
    }
}
