package com.ansill.tesla.api.data.model;

import com.ansill.tesla.api.data.utility.JacksonUtility;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

@JsonSerialize(using = GuiSettings.Serializer.class)
@JsonDeserialize(using = GuiSettings.Deserializer.class)
@Immutable
public final class GuiSettings{

  private final boolean gui24HoursTime;

  @Nonnull
  private final String guiChargeRateUnits;

  @Nonnull
  private final String guiDistanceUnits;

  @Nonnull
  private final String guiRangeDisplay;

  @Nonnull
  private final String guiTemperatureUnits;

  private final boolean showRangeUnits;

  private final long timestamp;

  public GuiSettings(
    boolean gui24HoursTime,
    @Nonnull String guiChargeRateUnits,
    @Nonnull String guiDistanceUnits,
    @Nonnull String guiRangeDisplay,
    @Nonnull String guiTemperatureUnits,
    boolean showRangeUnits,
    long timestamp
  ){
    this.gui24HoursTime = gui24HoursTime;
    this.guiChargeRateUnits = guiChargeRateUnits;
    this.guiDistanceUnits = guiDistanceUnits;
    this.guiRangeDisplay = guiRangeDisplay;
    this.guiTemperatureUnits = guiTemperatureUnits;
    this.showRangeUnits = showRangeUnits;
    this.timestamp = timestamp;
  }

  public boolean isGuiUsing24HoursTime(){
    return gui24HoursTime;
  }

  @Nonnull
  public String getGuiChargeRateUnits(){
    return guiChargeRateUnits;
  }

  @Nonnull
  public String getGuiDistanceUnits(){
    return guiDistanceUnits;
  }

  @Nonnull
  public String getGuiRangeDisplay(){
    return guiRangeDisplay;
  }

  @Override
  public boolean equals(Object o){
    if(this == o) return true;
    if(!(o instanceof GuiSettings that)) return false;

    if(gui24HoursTime != that.gui24HoursTime) return false;
    if(showRangeUnits != that.showRangeUnits) return false;
    if(getTimestamp() != that.getTimestamp()) return false;
    if(!getGuiChargeRateUnits().equals(that.getGuiChargeRateUnits())) return false;
    if(!getGuiDistanceUnits().equals(that.getGuiDistanceUnits())) return false;
    if(!getGuiRangeDisplay().equals(that.getGuiRangeDisplay())) return false;
    return getGuiTemperatureUnits().equals(that.getGuiTemperatureUnits());
  }

  @Override
  public int hashCode(){
    int result = (gui24HoursTime ? 1 : 0);
    result = 31 * result + getGuiChargeRateUnits().hashCode();
    result = 31 * result + getGuiDistanceUnits().hashCode();
    result = 31 * result + getGuiRangeDisplay().hashCode();
    result = 31 * result + getGuiTemperatureUnits().hashCode();
    result = 31 * result + (showRangeUnits ? 1 : 0);
    result = 31 * result + (int) (getTimestamp() ^ (getTimestamp() >>> 32));
    return result;
  }

  @Nonnull
  public String getGuiTemperatureUnits(){
    return guiTemperatureUnits;
  }

  public boolean isShowingRangeUnits(){
    return showRangeUnits;
  }

  public long getTimestamp(){
    return timestamp;
  }

  public static class Deserializer extends StdDeserializer<GuiSettings>{
    private static final long serialVersionUID = 6949851268494259050L;

    @Nonnull
    private final AtomicReference<Function<Map<String,Optional<Object>>,Boolean>> unknownFieldsFunction;

    public Deserializer(){
      super(GuiSettings.class);
      unknownFieldsFunction = new AtomicReference<>();
    }

    public Deserializer(@Nonnull AtomicReference<Function<Map<String,Optional<Object>>,Boolean>> unknownFieldsFunction){
      super(GuiSettings.class);
      this.unknownFieldsFunction = unknownFieldsFunction;
    }

    @Override
    public GuiSettings deserialize(
      JsonParser jsonParser, DeserializationContext deserializationContext
    ) throws IOException{

      // Get node
      ObjectNode node = jsonParser.readValueAsTree();

      // Set up set of used keys
      var usedKeysSet = new HashSet<String>();

      // Get values
      var gui24HourTime = JacksonUtility.getBoolean(node, "gui_24_hour_time", usedKeysSet);
      var guiChargeRateUnits = JacksonUtility.getString(node, "gui_charge_rate_units", usedKeysSet);
      var guiDistanceUnits = JacksonUtility.getString(node, "gui_distance_units", usedKeysSet);
      var guiRangeDisplay = JacksonUtility.getString(node, "gui_range_display", usedKeysSet);
      var guiTemperatureUnits = JacksonUtility.getString(node, "gui_temperature_units", usedKeysSet);
      var showRangeUnits = JacksonUtility.getBoolean(node, "show_range_units", usedKeysSet);
      var timestamp = JacksonUtility.getLong(node, "timestamp", usedKeysSet);

      // Get unused fields map
      var unknownFields = JacksonUtility.createUnknownFieldsMap(node, jsonParser.getCodec(), usedKeysSet);

      // Report it
      var fnc = unknownFieldsFunction.get();
      if(fnc != null && fnc.apply(unknownFields)){
        throw new IllegalArgumentException("Thrown by the unknownFieldsFunction function");
      }

      // Build and return
      return new GuiSettings(
        gui24HourTime,
        guiChargeRateUnits,
        guiDistanceUnits,
        guiRangeDisplay,
        guiTemperatureUnits,
        showRangeUnits,
        timestamp
      );
    }
  }

  public static class Serializer extends StdSerializer<GuiSettings>{
    private static final long serialVersionUID = -6868444185313874371L;

    public Serializer(){
      super(GuiSettings.class);
    }

    @Override
    public void serialize(
      GuiSettings value, JsonGenerator gen, SerializerProvider provider
    ) throws IOException{

      gen.writeStartObject();
      gen.writeObjectField("gui_24_hour_time", value.gui24HoursTime);
      gen.writeObjectField("gui_charge_rate_units", value.guiChargeRateUnits);
      gen.writeObjectField("gui_distance_units", value.guiDistanceUnits);
      gen.writeObjectField("gui_range_display", value.guiRangeDisplay);
      gen.writeObjectField("gui_temperature_units", value.guiTemperatureUnits);
      gen.writeObjectField("show_range_units", value.showRangeUnits);
      gen.writeObjectField("timestamp", value.timestamp);
      gen.writeEndObject();

    }
  }
}
