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
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

@JsonSerialize(using = SimpleSerializer.class)
@JsonDeserialize(using = Vehicle.Deserializer.class)
@Immutable
public final class Vehicle extends AbstractVehicle{

  public Vehicle(
    long id,
    long userId,
    long vehicleId,
    @Nonnull String vin,
    @Nonnull String displayName,
    @Nonnull String optionCodes,
    @Nullable String color,
    @Nonnull List<String> tokens,
    @Nonnull String state,
    boolean inService,
    boolean calendarEnabled,
    int apiVersion,
    @Nullable String backseatToken,
    @Nullable String backseatTokenUpdatedAt
  ){
    super(
      id,
      userId,
      vehicleId,
      vin,
      displayName,
      optionCodes,
      color,
      tokens,
      state,
      inService,
      calendarEnabled,
      apiVersion,
      backseatToken,
      backseatTokenUpdatedAt,
      Collections.emptyMap()
    );
  }

  public Vehicle(
    long id,
    long userId,
    long vehicleId,
    @Nonnull String vin,
    @Nonnull String displayName,
    @Nonnull String optionCodes,
    @Nullable String color,
    @Nonnull List<String> tokens,
    @Nonnull String state,
    boolean inService,
    boolean calendarEnabled,
    int apiVersion,
    @Nullable String backseatToken,
    @Nullable String backseatTokenUpdatedAt,
    @Nonnull Map<String,Optional<Object>> unknownFields
  ){
    super(
      id,
      userId,
      vehicleId,
      vin,
      displayName,
      optionCodes,
      color,
      tokens,
      state,
      inService,
      calendarEnabled,
      apiVersion,
      backseatToken,
      backseatTokenUpdatedAt,
      unknownFields
    );
  }

  public static class Deserializer extends StdDeserializer<Vehicle>{

    private static final long serialVersionUID = -275515316149812793L;

    private final AtomicReference<Function<Map<String,Optional<Object>>,Boolean>> unknownFieldsFunction;

    public Deserializer(){
      super(Vehicle.class);
      this.unknownFieldsFunction = new AtomicReference<>(null);
    }

    public Deserializer(@Nonnull AtomicReference<Function<Map<String,Optional<Object>>,Boolean>> unknownFieldsFunction){
      super(Vehicle.class);
      this.unknownFieldsFunction = unknownFieldsFunction;
    }

    @Override
    public Vehicle deserialize(
      JsonParser jsonParser, DeserializationContext deserializationContext
    ) throws IOException{

      // Get node
      ObjectNode node = jsonParser.readValueAsTree();

      // Set up set of used keys
      var usedKeysSet = new HashSet<String>();

      // Deserialize
      var vehicle = AbstractVehicle.deserialize(node, jsonParser.getCodec(), usedKeysSet);

      // Get unused fields map
      var unknownFields = JacksonUtility.createUnknownFieldsMap(node, jsonParser.getCodec(), usedKeysSet);

      // Report it
      var fnc = unknownFieldsFunction.get();
      if(fnc != null && fnc.apply(unknownFields)){
        throw new IllegalArgumentException("Thrown by the unknownFieldsFunction function");
      }

      // Return vehicle
      return new Vehicle(
        vehicle.getId(),
        vehicle.getUserId(),
        vehicle.getVehicleId(),
        vehicle.getVIN(),
        vehicle.getDisplayName(),
        vehicle.getOptionCodes(),
        vehicle.getColor().orElse(null),
        vehicle.getTokens(),
        vehicle.getState(),
        vehicle.isInService(),
        vehicle.isCalendarEnabled(),
        vehicle.getApiVersion(),
        vehicle.getBackseatToken().orElse(null),
        vehicle.getBackseatTokenUpdatedAt().orElse(null),
        unknownFields
      );
    }
  }
}
