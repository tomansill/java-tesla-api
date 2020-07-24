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

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static com.ansill.utility.Utility.simpleToString;

@JsonSerialize(using = SimpleSerializer.class)
@JsonDeserialize(using = VehiclesResponse.Deserializer.class)
@Immutable
public final class VehiclesResponse{

  /** Response */
  @Nonnull
  private final List<Vehicle> response;

  /** Count */
  private final int count;

  /**
   * Constructor
   *
   * @param response response
   * @param count    count
   */
  public VehiclesResponse(@Nonnull List<Vehicle> response, @Nonnegative int count){
    this.response = response;
    this.count = count;
  }

  /**
   * Constructor
   *
   * @param response response
   */
  public VehiclesResponse(@Nonnull List<Vehicle> response){
    this.response = response;
    this.count = response.size();
  }

  /**
   * Returns count of vehicles
   *
   * @return count
   */
  @Nonnegative
  public int getCount(){
    return count;
  }

  /**
   * Returns response item
   *
   * @return response
   */
  @Nonnull
  public List<Vehicle> getResponse(){
    return Collections.unmodifiableList(response);
  }

  @Override
  public String toString(){
    return simpleToString(this);
  }

  public static class Deserializer extends StdDeserializer<VehiclesResponse>{
    private static final long serialVersionUID = -3784645711507330578L;

    public Deserializer(){
      super(VehiclesResponse.class);
    }

    @Override
    public VehiclesResponse deserialize(
      JsonParser jsonParser, DeserializationContext ctxt
    ) throws IOException{

      // Get node
      ObjectNode node = jsonParser.readValueAsTree();

      // Set up set of used keys
      var usedKeysSet = new HashSet<String>();

      // Get value
      Vehicle[] vehicle = JacksonUtility.getObject(
        jsonParser.getCodec(),
        "response",
        node,
        usedKeysSet,
        Vehicle[].class
      );
      var count = JacksonUtility.getInteger(node, "count", usedKeysSet);

      // Return it
      return new VehiclesResponse(Arrays.asList(vehicle), count);
    }
  }
}