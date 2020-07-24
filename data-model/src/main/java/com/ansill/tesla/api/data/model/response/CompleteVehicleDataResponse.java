package com.ansill.tesla.api.data.model.response;

import com.ansill.tesla.api.data.model.CompleteVehicle;
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

import static com.ansill.utility.Utility.simpleToString;

/** Complete Vehicle Data Response */

@JsonSerialize(using = SimpleSerializer.class)
@JsonDeserialize(using = CompleteVehicleDataResponse.Deserializer.class)
@Immutable
public final class CompleteVehicleDataResponse extends SimpleResponse<CompleteVehicle>{

  /**
   * Response
   *
   * @param response response
   */
  public CompleteVehicleDataResponse(@Nonnull CompleteVehicle response){
    super(response);
  }

  @Override
  public String toString(){
    return simpleToString(this);
  }

  public static class Deserializer extends StdDeserializer<CompleteVehicleDataResponse>{
    private static final long serialVersionUID = -1074579346245030863L;

    @Nonnull
    private final AtomicReference<Function<Map<String,Optional<Object>>,Boolean>> unknownFieldsFunction;

    public Deserializer(){
      super(CompleteVehicleDataResponse.class);
      this.unknownFieldsFunction = new AtomicReference<>();
    }

    public Deserializer(@Nonnull AtomicReference<Function<Map<String,Optional<Object>>,Boolean>> unknownFieldsFunction){
      super(CompleteVehicleDataResponse.class);
      this.unknownFieldsFunction = unknownFieldsFunction;
    }

    @Override
    public CompleteVehicleDataResponse deserialize(JsonParser jsonParser, DeserializationContext ctxt)
    throws IOException{

      // Get node
      ObjectNode node = jsonParser.readValueAsTree();

      // Set up set of used keys
      var usedKeysSet = new HashSet<String>();

      // Get values
      CompleteVehicle item = JacksonUtility.getObject(
        jsonParser.getCodec(),
        "response",
        node,
        usedKeysSet,
        CompleteVehicle.class
      );

      // Get unused fields map
      var unknownFields = JacksonUtility.createUnknownFieldsMap(node, jsonParser.getCodec(), usedKeysSet);

      // Report it
      var fnc = unknownFieldsFunction.get();
      if(fnc != null && fnc.apply(unknownFields)){
        throw new IllegalArgumentException("Thrown by the unknownFieldsFunction function");
      }

      // Return object
      return new CompleteVehicleDataResponse(item);
    }
  }
}