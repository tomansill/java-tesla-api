package com.ansill.tesla.api.data.model;

import com.ansill.tesla.api.data.utility.JacksonUtility;
import com.ansill.tesla.api.data.utility.SimpleSerializer;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import static com.ansill.tesla.api.data.utility.JacksonUtility.*;

@JsonSerialize(using = SimpleSerializer.class)
@JsonDeserialize(using = SpeedLimitMode.Deserializer.class)
@Immutable
public final class SpeedLimitMode{
  private final boolean active;

  private final double currentLimitMph;

  private final int maxLimitMph;

  private final int minLimitMph;

  private final boolean pinCodeSet;

  public SpeedLimitMode(boolean active, double currentLimitMph, int maxLimitMph, int minLimitMph, boolean pinCodeSet){
    this.active = active;
    this.currentLimitMph = currentLimitMph;
    this.maxLimitMph = maxLimitMph;
    this.minLimitMph = minLimitMph;
    this.pinCodeSet = pinCodeSet;
  }

  @Override
  public boolean equals(Object o){
    if(this == o) return true;
    if(!(o instanceof SpeedLimitMode)) return false;

    SpeedLimitMode that = (SpeedLimitMode) o;

    if(isActive() != that.isActive()) return false;
    if(Double.compare(that.getCurrentLimitMph(), getCurrentLimitMph()) != 0) return false;
    if(getMaxLimitMph() != that.getMaxLimitMph()) return false;
    if(getMinLimitMph() != that.getMinLimitMph()) return false;
    return isPinCodeSet() == that.isPinCodeSet();
  }

  @Override
  public int hashCode(){
    int result;
    long temp;
    result = (isActive() ? 1 : 0);
    temp = Double.doubleToLongBits(getCurrentLimitMph());
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + getMaxLimitMph();
    result = 31 * result + getMinLimitMph();
    result = 31 * result + (isPinCodeSet() ? 1 : 0);
    return result;
  }

  public boolean isActive(){
    return active;
  }

  public double getCurrentLimitMph(){
    return currentLimitMph;
  }

  public int getMaxLimitMph(){
    return maxLimitMph;
  }

  public int getMinLimitMph(){
    return minLimitMph;
  }

  public boolean isPinCodeSet(){
    return pinCodeSet;
  }

  public static class Deserializer extends StdDeserializer<SpeedLimitMode>{
    private static final long serialVersionUID = 9103846562766115809L;

    @Nonnull
    private final AtomicReference<Function<Map<String,Optional<Object>>,Boolean>> unknownFieldsFunction;

    public Deserializer(){
      super(SpeedLimitMode.class);
      this.unknownFieldsFunction = new AtomicReference<>();
    }

    public Deserializer(@Nonnull AtomicReference<Function<Map<String,Optional<Object>>,Boolean>> unknownFieldsFunction){
      super(SpeedLimitMode.class);
      this.unknownFieldsFunction = unknownFieldsFunction;
    }

    @Override
    public SpeedLimitMode deserialize(
      JsonParser jsonParser, DeserializationContext deserializationContext
    ) throws IOException{

      // Get node
      ObjectNode node = jsonParser.readValueAsTree();

      // Set up set of used keys
      var usedKeysSet = new HashSet<String>();

      // Get values
      var active = getBoolean(node, "active", usedKeysSet);
      var currentLimitMph = getDouble(node, "current_limit_mph", usedKeysSet);
      var maxLimitMph = getInteger(node, "max_limit_mph", usedKeysSet);
      var minLimitMph = getInteger(node, "min_limit_mph", usedKeysSet);
      var pinCodeSet = getBoolean(node, "pin_code_set", usedKeysSet);

      // Get unused fields map
      var unknownFields = JacksonUtility.createUnknownFieldsMap(node, jsonParser.getCodec(), usedKeysSet);

      // Report it
      var fnc = unknownFieldsFunction.get();
      if(fnc != null && fnc.apply(unknownFields)){
        throw new IllegalArgumentException("Thrown by the unknownFieldsFunction function");
      }

      // Build and return
      return new SpeedLimitMode(
        active,
        currentLimitMph,
        maxLimitMph,
        minLimitMph,
        pinCodeSet
      );
    }
  }
}
