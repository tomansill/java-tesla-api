package com.ansill.tesla.api.low.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.util.Objects;
import java.util.Optional;

import static com.ansill.utility.Utility.simpleToString;

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

  @Override
  public boolean equals(Object o){
    if(this == o) return true;
    if(o == null || getClass() != o.getClass()) return false;

    DriveState that = (DriveState) o;

    if(gps_as_of != that.gps_as_of) return false;
    if(heading != that.heading) return false;
    if(Double.compare(that.latitude, latitude) != 0) return false;
    if(Double.compare(that.longitude, longitude) != 0) return false;
    if(Double.compare(that.native_latitude, native_latitude) != 0) return false;
    if(native_location_supported != that.native_location_supported) return false;
    if(Double.compare(that.native_longitude, native_longitude) != 0) return false;
    if(power != that.power) return false;
    if(timestamp != that.timestamp) return false;
    if(!native_type.equals(that.native_type)) return false;
    if(!Objects.equals(shift_state, that.shift_state)) return false;
    return Objects.equals(speed, that.speed);
  }

  @Override
  public int hashCode(){
    int result;
    long temp;
    result = (int) (gps_as_of ^ (gps_as_of >>> 32));
    result = 31 * result + heading;
    temp = Double.doubleToLongBits(latitude);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(longitude);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(native_latitude);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + native_location_supported;
    temp = Double.doubleToLongBits(native_longitude);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + native_type.hashCode();
    result = 31 * result + power;
    result = 31 * result + Objects.hashCode(shift_state);
    result = 31 * result + Objects.hashCode(speed);
    result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
    return result;
  }

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
