package com.ansill.tesla.api.data.model;

import com.ansill.tesla.api.data.utility.JacksonUtility;
import com.ansill.tesla.api.data.utility.SimpleSerializer;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

@JsonSerialize(using = SimpleSerializer.class)
@JsonDeserialize(using = DriveState.Deserializer.class)
@Immutable
public final class DriveState{
  private final long gpsAsOf;

  private final int heading;

  private final double latitude;

  private final double longitude;

  private final double nativeLatitude;

  private final int nativeLocationSupported;

  private final double nativeLongitude;

  private final String nativeType;

  private final long power;

  @Nullable
  private final String shiftState;

  @Nullable
  private final Long speed;

  @Nonnegative
  private final long timestamp;

  public DriveState(
    long gpsAsOf,
    int heading,
    double latitude,
    double longitude,
    double nativeLatitude,
    int nativeLocationSupported,
    double nativeLongitude,
    @Nonnull String nativeType,
    long power,
    @Nullable String shiftState,
    @Nullable Long speed,
    long timestamp
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

  @Override
  public boolean equals(Object o){
    if(this == o) return true;
    if(!(o instanceof DriveState that)) return false;

    if(getGpsAsOf() != that.getGpsAsOf()) return false;
    if(getHeading() != that.getHeading()) return false;
    if(Double.compare(that.getLatitude(), getLatitude()) != 0) return false;
    if(Double.compare(that.getLongitude(), getLongitude()) != 0) return false;
    if(Double.compare(that.getNativeLatitude(), getNativeLatitude()) != 0) return false;
    if(getNativeLocationSupported() != that.getNativeLocationSupported()) return false;
    if(Double.compare(that.getNativeLongitude(), getNativeLongitude()) != 0) return false;
    if(getPower() != that.getPower()) return false;
    if(getTimestamp() != that.getTimestamp()) return false;
    if(!getNativeType().equals(that.getNativeType())) return false;
    if(!getShiftState().equals(that.getShiftState())) return false;
    return getSpeed().equals(that.getSpeed());
  }

  @Override
  public int hashCode(){
    int result;
    long temp;
    result = (int) (getGpsAsOf() ^ (getGpsAsOf() >>> 32));
    result = 31 * result + getHeading();
    temp = Double.doubleToLongBits(getLatitude());
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(getLongitude());
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(getNativeLatitude());
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + getNativeLocationSupported();
    temp = Double.doubleToLongBits(getNativeLongitude());
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + getNativeType().hashCode();
    result = 31 * result + (int) (getPower() ^ (getPower() >>> 32));
    result = 31 * result + getShiftState().hashCode();
    result = 31 * result + getSpeed().hashCode();
    result = 31 * result + (int) (getTimestamp() ^ (getTimestamp() >>> 32));
    return result;
  }

  public long getGpsAsOf(){
    return gpsAsOf;
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
    return nativeLatitude;
  }

  public int getNativeLocationSupported(){
    return nativeLocationSupported;
  }

  public double getNativeLongitude(){
    return nativeLongitude;
  }

  @Nonnull
  public String getNativeType(){
    return nativeType;
  }

  public long getPower(){
    return power;
  }

  @Nonnull
  public Optional<String> getShiftState(){
    return Optional.ofNullable(shiftState);
  }

  @Nonnull
  public OptionalLong getSpeed(){
    if(speed == null) return OptionalLong.empty();
    return OptionalLong.of(speed);
  }

  public long getTimestamp(){
    return timestamp;
  }

  public static class Deserializer extends StdDeserializer<DriveState>{

    private static final long serialVersionUID = -2087692110688911824L;

    @Nonnull
    private final AtomicReference<Function<Map<String,Optional<Object>>,Boolean>> unknownFieldsFunction;

    public Deserializer(){
      super(DriveState.class);
      unknownFieldsFunction = new AtomicReference<>();
    }

    public Deserializer(@Nonnull AtomicReference<Function<Map<String,Optional<Object>>,Boolean>> unknownFieldsFunction){
      super(DriveState.class);
      this.unknownFieldsFunction = unknownFieldsFunction;
    }

    @Override
    public DriveState deserialize(
      JsonParser jsonParser, DeserializationContext deserializationContext
    ) throws IOException{

      // Get node
      ObjectNode node = jsonParser.readValueAsTree();

      // Set up set of used keys
      var usedKeysSet = new HashSet<String>();

      // Get values
      var gpsAsOf = JacksonUtility.getLong(node, "gps_as_of", usedKeysSet);
      var heading = JacksonUtility.getInteger(node, "heading", usedKeysSet);
      var latitude = JacksonUtility.getDouble(node, "latitude", usedKeysSet);
      var longitude = JacksonUtility.getDouble(node, "longitude", usedKeysSet);
      var nativeLatitude = JacksonUtility.getDouble(node, "native_latitude", usedKeysSet);
      var nativeLocationSupported = JacksonUtility.getInteger(node, "native_location_supported", usedKeysSet);
      var nativeLongitude = JacksonUtility.getDouble(node, "native_longitude", usedKeysSet);
      var nativeType = JacksonUtility.getString(node, "native_type", usedKeysSet);
      var power = JacksonUtility.getLong(node, "power", usedKeysSet);
      var shiftState = JacksonUtility.getStringNullable(node, "shift_state", usedKeysSet);
      Long speed = JacksonUtility.getLongNullable(node, "speed", usedKeysSet);
      var timestamp = JacksonUtility.getLong(node, "timestamp", usedKeysSet);

      // Get unused fields map
      var unknownFields = JacksonUtility.createUnknownFieldsMap(node, jsonParser.getCodec(), usedKeysSet);

      // Report it
      var fnc = unknownFieldsFunction.get();
      if(fnc != null && fnc.apply(unknownFields)){
        throw new IllegalArgumentException("Thrown by the unknownFieldsFunction function");
      }

      // Build and return
      return new DriveState(
        gpsAsOf,
        heading,
        latitude,
        longitude,
        nativeLatitude,
        nativeLocationSupported,
        nativeLongitude,
        nativeType,
        power,
        shiftState,
        speed,
        timestamp
      );
    }
  }
}
