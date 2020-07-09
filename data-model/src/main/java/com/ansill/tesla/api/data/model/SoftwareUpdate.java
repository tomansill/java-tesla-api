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

import static com.ansill.tesla.api.data.utility.JacksonUtility.getInteger;
import static com.ansill.tesla.api.data.utility.JacksonUtility.getString;

@JsonSerialize(using = SoftwareUpdate.Serializer.class)
@JsonDeserialize(using = SoftwareUpdate.Deserializer.class)
@Immutable
public final class SoftwareUpdate{
  private final int downloadPercent;

  private final long expectedDurationSeconds;

  private final int installPercent;

  @Nonnull
  private final String status;

  @Nonnull
  private final String version;

  public SoftwareUpdate(
    int downloadPercent,
    long expectedDurationSeconds,
    int installPercent,
    @Nonnull String status,
    @Nonnull String version
  ){
    this.downloadPercent = downloadPercent;
    this.expectedDurationSeconds = expectedDurationSeconds;
    this.installPercent = installPercent;
    this.status = status;
    this.version = version;
  }

  @Override
  public boolean equals(Object o){
    if(this == o) return true;
    if(!(o instanceof SoftwareUpdate that)) return false;

    if(getDownloadPercent() != that.getDownloadPercent()) return false;
    if(getExpectedDurationSeconds() != that.getExpectedDurationSeconds()) return false;
    if(getInstallPercent() != that.getInstallPercent()) return false;
    if(!getStatus().equals(that.getStatus())) return false;
    return getVersion().equals(that.getVersion());
  }

  @Override
  public int hashCode(){
    int result = getDownloadPercent();
    result = 31 * result + (int) getExpectedDurationSeconds();
    result = 31 * result + getInstallPercent();
    result = 31 * result + getStatus().hashCode();
    result = 31 * result + getVersion().hashCode();
    return result;
  }

  public int getDownloadPercent(){
    return downloadPercent;
  }

  public long getExpectedDurationSeconds(){
    return expectedDurationSeconds;
  }

  public int getInstallPercent(){
    return installPercent;
  }

  @Nonnull
  public String getStatus(){
    return status;
  }

  @Nonnull
  public String getVersion(){
    return version;
  }

  public static class Deserializer extends StdDeserializer<SoftwareUpdate>{

    private static final long serialVersionUID = 3653901122135554901L;

    @Nonnull
    private final AtomicReference<Function<Map<String,Optional<Object>>,Boolean>> unknownFieldsFunction;

    public Deserializer(){
      super(SoftwareUpdate.class);
      this.unknownFieldsFunction = new AtomicReference<>();
    }

    public Deserializer(@Nonnull AtomicReference<Function<Map<String,Optional<Object>>,Boolean>> unknownFieldsFunction){
      super(SoftwareUpdate.class);
      this.unknownFieldsFunction = unknownFieldsFunction;
    }

    @Override
    public SoftwareUpdate deserialize(
      JsonParser jsonParser, DeserializationContext deserializationContext
    ) throws IOException{

      // Get node
      ObjectNode node = jsonParser.readValueAsTree();

      // Set up set of used keys
      var usedKeysSet = new HashSet<String>();

      // Get values
      var downloadPercent = getInteger(node, "download_perc", usedKeysSet);
      var expectedDurationSeconds = getInteger(node, "expected_duration_sec", usedKeysSet);
      var installPercent = getInteger(node, "install_perc", usedKeysSet);
      var status = getString(node, "status", usedKeysSet);
      var version = getString(node, "version", usedKeysSet);

      // Get unused fields map
      var unknownFields = JacksonUtility.createUnknownFieldsMap(node, jsonParser.getCodec(), usedKeysSet);

      // Report it
      var fnc = unknownFieldsFunction.get();
      if(fnc != null && fnc.apply(unknownFields)){
        throw new IllegalArgumentException("Thrown by the unknownFieldsFunction function");
      }

      // Build and return
      return new SoftwareUpdate(
        downloadPercent,
        expectedDurationSeconds,
        installPercent,
        status,
        version
      );
    }
  }

  public static class Serializer extends StdSerializer<SoftwareUpdate>{
    private static final long serialVersionUID = 7869623355058269048L;

    public Serializer(){
      super(SoftwareUpdate.class);
    }

    @Override
    public void serialize(
      SoftwareUpdate value, JsonGenerator gen, SerializerProvider provider
    ) throws IOException{
      gen.writeStartObject();
      gen.writeObjectField("download_perc", value.downloadPercent);
      gen.writeObjectField("expected_duration_sec", value.expectedDurationSeconds);
      gen.writeObjectField("install_perc", value.installPercent);
      gen.writeObjectField("status", value.status);
      gen.writeObjectField("version", value.version);
      gen.writeEndObject();
    }
  }
}
