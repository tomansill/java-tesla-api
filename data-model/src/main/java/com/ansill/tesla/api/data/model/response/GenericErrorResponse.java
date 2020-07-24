package com.ansill.tesla.api.data.model.response;

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
import java.util.HashSet;
import java.util.Optional;

import static com.ansill.tesla.api.data.utility.JacksonUtility.getStringNullable;
import static com.ansill.utility.Utility.simpleToString;

/** Generic Error Response */
@JsonSerialize(using = SimpleSerializer.class)
@JsonDeserialize(using = GenericErrorResponse.Deserializer.class)
@Immutable
public final class GenericErrorResponse{

  /** String that describes type of error */
  @Nullable
  private final String error;

  /** String that describes the error */
  @Nullable
  private final String errorDescription;

  /** Response */
  @Nullable
  private final String response;

  public GenericErrorResponse(
    @Nullable String error,
    @Nullable String errorDescription,
    @Nullable String response
  ){
    this.error = error;
    this.errorDescription = errorDescription;
    this.response = response;
  }

  public GenericErrorResponse(
    @Nullable String error,
    @Nullable String errorDescription
  ){
    this.error = error;
    this.errorDescription = errorDescription;
    this.response = null;
  }

  public GenericErrorResponse(
    @Nullable String response
  ){
    this.error = null;
    this.errorDescription = null;
    this.response = response;
  }

  /**
   * Returns error type string if it exists
   *
   * @return optional object that may contain error type
   */
  @Nonnull
  public Optional<String> getError(){
    return Optional.ofNullable(error);
  }

  /**
   * Returns error description string if it exists
   *
   * @return optional object that may contain error description
   */
  @Nonnull
  public Optional<String> getErrorDescription(){
    return Optional.ofNullable(errorDescription);
  }

  /**
   * Returns response string if it exists
   *
   * @return optional object that may contain response
   */
  @Nonnull
  public Optional<String> getResponse(){
    return Optional.ofNullable(response);
  }

  @Override
  public String toString(){
    return simpleToString(this);
  }

  public static class Deserializer extends StdDeserializer<GenericErrorResponse>{
    private static final long serialVersionUID = -768383065353931429L;

    public Deserializer(){
      super(GenericErrorResponse.class);
    }

    @Override
    public GenericErrorResponse deserialize(
      JsonParser jsonParser, DeserializationContext ctxt
    ) throws IOException{

      // Get node
      ObjectNode node = jsonParser.readValueAsTree();

      // Set up set of used keys
      var usedKeysSet = new HashSet<String>();

      // Get values
      var error = getStringNullable(node, "error", usedKeysSet);
      var errorDescription = getStringNullable(node, "error_description", usedKeysSet);
      var response = getStringNullable(node, "response", usedKeysSet);

      // Build and return
      return new GenericErrorResponse(
        error,
        errorDescription,
        response
      );
    }
  }
}