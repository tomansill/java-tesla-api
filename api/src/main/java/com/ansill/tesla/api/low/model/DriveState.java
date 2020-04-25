package com.ansill.tesla.api.low.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.util.Optional;

import static com.ansill.tesla.api.utility.Utility.simpleToString;

@SuppressWarnings("unused")
@Immutable
public final class DriveState{

  private final long gps_as_of;

  private final int heading;

  private final double latitude;

  private final double longitude;

  private final double native_latitude;

  private final int native_location_supported;

  private final double native_longitude;

  private final String native_type;

  private final int power;

  @Nullable
  private final String shift_state;

  @Nullable
  private final Integer speed;

  private final long timestamp;

  public DriveState(
    long gps_as_of,
    int heading,
    double latitude,
    double longitude,
    double native_latitude,
    int native_location_supported,
    double native_longitude,
    String native_type,
    int power,
    @Nullable String shift_state,
    @Nullable Integer speed,
    long timestamp
  ){
    this.gps_as_of = gps_as_of;
    this.heading = heading;
    this.latitude = latitude;
    this.longitude = longitude;
    this.native_latitude = native_latitude;
    this.native_location_supported = native_location_supported;
    this.native_longitude = native_longitude;
    this.native_type = native_type;
    this.power = power;
    this.shift_state = shift_state;
    this.speed = speed;
    this.timestamp = timestamp;
  }

  public long getGpsAsOf(){
    return gps_as_of;
  }

  public int getHeading(){
    return heading;
  }

  public double getLatitude(){
    return latitude;
  }

  public double getLongitude(){
    return longitude;
  }

  public double getNativeLatitude(){
    return native_latitude;
  }

  public int getNativeLocationSupported(){
    return native_location_supported;
  }

  public double getNativeLongitude(){
    return native_longitude;
  }

  public String getNativeType(){
    return native_type;
  }

  public int getPower(){
    return power;
  }

  @Nonnull
  public Optional<String> getShiftState(){
    return Optional.ofNullable(shift_state);
  }

  @Nonnull
  public Optional<Integer> getSpeed(){
    return Optional.ofNullable(speed);
  }

  public long getTimestamp(){
    return timestamp;
  }

  @Override
  public String toString(){
    return simpleToString(this);
  }
}
