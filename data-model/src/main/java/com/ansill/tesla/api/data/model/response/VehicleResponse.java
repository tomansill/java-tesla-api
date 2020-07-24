package com.ansill.tesla.api.data.model.response;

import com.ansill.tesla.api.data.model.Vehicle;
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

import static com.ansill.utility.Utility.simpleToString;

/** Complete Vehicle Data Response */
@JsonSerialize(using = SimpleSerializer.class)
@JsonDeserialize(using = VehicleResponse.Deserializer.class)
@Immutable
public final class VehicleResponse extends SimpleResponse<Vehicle>{

  /**
   * Response
   *
   * @param response response
   */
  public VehicleResponse(@Nonnull Vehicle response){
    super(response);
  }

  @Override
  public String toString(){
    return simpleToString(this);
  }

  public static class Deserializer extends StdDeserializer<VehicleResponse>{
    private static final long serialVersionUID = -3784645711507330578L;

    public Deserializer(){
      super(VehiclesResponse.class);
    }

    @Override
    public VehicleResponse deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException{

      // Get node
      ObjectNode node = jsonParser.readValueAsTree();

      // Set up set of used keys
      var usedKeysSet = new HashSet<String>();

      // Get value
      Vehicle vehicle = JacksonUtility.getObject(jsonParser.getCodec(), "response", node, usedKeysSet, Vehicle.class);

      // Return it
      return new VehicleResponse(vehicle);
    }
  }
}