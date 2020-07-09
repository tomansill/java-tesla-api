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

import static com.ansill.tesla.api.data.utility.JacksonUtility.getBoolean;

@JsonSerialize(using = SimpleSerializer.class)
@JsonDeserialize(using = MediaState.Deserializer.class)
@Immutable
public final class MediaState{

  private final boolean remoteControlEnabled;

  public MediaState(boolean remoteControlEnabled){
    this.remoteControlEnabled = remoteControlEnabled;
  }

  @Override
  public boolean equals(Object o){
    if(this == o) return true;
    if(!(o instanceof MediaState)) return false;

    MediaState that = (MediaState) o;

    return isRemoteControlEnabled() == that.isRemoteControlEnabled();
  }

  @Override
  public int hashCode(){
    return (isRemoteControlEnabled() ? 1 : 0);
  }

  public boolean isRemoteControlEnabled(){
    return remoteControlEnabled;
  }

  public static class Deserializer extends StdDeserializer<MediaState>{

    private static final long serialVersionUID = -3515608018957211689L;

    @Nonnull
    private final AtomicReference<Function<Map<String,Optional<Object>>,Boolean>> unknownFieldsFunction;

    public Deserializer(){
      super(MediaState.class);
      unknownFieldsFunction = new AtomicReference<>();
    }

    public Deserializer(@Nonnull AtomicReference<Function<Map<String,Optional<Object>>,Boolean>> unknownFieldsFunction){
      super(MediaState.class);
      this.unknownFieldsFunction = unknownFieldsFunction;
    }

    @Override
    public MediaState deserialize(
      JsonParser jsonParser, DeserializationContext deserializationContext
    ) throws IOException{

      // Get node
      ObjectNode node = jsonParser.readValueAsTree();

      // Set up set of used keys
      var usedKeysSet = new HashSet<String>();

      // Get values
      var remoteControlEnabled = getBoolean(node, "remote_control_enabled", usedKeysSet);

      // Get unused fields map
      var unknownFields = JacksonUtility.createUnknownFieldsMap(node, jsonParser.getCodec(), usedKeysSet);

      // Report it
      var fnc = unknownFieldsFunction.get();
      if(fnc != null && fnc.apply(unknownFields)){
        throw new IllegalArgumentException("Thrown by the unknownFieldsFunction function");
      }

      // Build and return
      return new MediaState(remoteControlEnabled);
    }
  }
}
