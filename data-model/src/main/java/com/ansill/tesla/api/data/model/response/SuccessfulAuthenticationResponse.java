package com.ansill.tesla.api.data.model.response;


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
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import static com.ansill.utility.Utility.simpleToString;

@JsonSerialize(using = SimpleSerializer.class)
@JsonDeserialize(using = SuccessfulAuthenticationResponse.Deserializer.class)
@Immutable
public final class SuccessfulAuthenticationResponse{

  @Nonnull
  private final String accessToken;

  @Nonnull
  private final String tokenType;

  @Nonnegative
  private final long expiresIn;

  @Nonnull
  private final String refreshToken;

  @Nonnegative
  private final long createdAt;

  public SuccessfulAuthenticationResponse(
    @Nonnull String accessToken,
    @Nonnull String tokenType,
    @Nonnegative long expiresIn,
    @Nonnull String refreshToken,
    @Nonnegative long createdAt
  ){
    this.accessToken = accessToken;
    this.tokenType = tokenType;
    this.expiresIn = expiresIn;
    this.refreshToken = refreshToken;
    this.createdAt = createdAt;
  }

  @Nonnull
  public String getAccessToken(){
    return accessToken;
  }

  @Nonnull
  public String getTokenType(){
    return tokenType;
  }

  @Nonnegative
  public long getExpiresIn(){
    return expiresIn;
  }

  @Nonnull
  public String getRefreshToken(){
    return refreshToken;
  }

  @Nonnegative
  public long getCreatedAt(){
    return createdAt;
  }

  @Override
  public String toString(){
    return simpleToString(this);
  }

  public static class Deserializer extends StdDeserializer<SuccessfulAuthenticationResponse>{
    private static final long serialVersionUID = 7204680106819046008L;

    @Nonnull
    private final AtomicReference<Function<Map<String,Optional<Object>>,Boolean>> unknownFieldsFunction;

    public Deserializer(){
      super(SuccessfulAuthenticationResponse.class);
      unknownFieldsFunction = new AtomicReference<>();
    }

    public Deserializer(@Nonnull AtomicReference<Function<Map<String,Optional<Object>>,Boolean>> unknownFieldsFunction){
      super(SuccessfulAuthenticationResponse.class);
      this.unknownFieldsFunction = unknownFieldsFunction;
    }

    @Override
    public SuccessfulAuthenticationResponse deserialize(
      JsonParser jsonParser, DeserializationContext ctxt
    ) throws IOException{

      // Get node
      ObjectNode node = jsonParser.readValueAsTree();

      // Set up set of used keys
      var usedKeysSet = new HashSet<String>();

      // Get values
      var tokenType = JacksonUtility.getString(node, "token_type", usedKeysSet);
      var accessToken = JacksonUtility.getString(node, "access_token", usedKeysSet);
      var refreshToken = JacksonUtility.getString(node, "refresh_token", usedKeysSet);
      var createdAt = JacksonUtility.getLong(node, "created_at", usedKeysSet);
      var expireAt = JacksonUtility.getLong(node, "expires_in", usedKeysSet);

      // Get unused fields map
      var unknownFields = JacksonUtility.createUnknownFieldsMap(node, jsonParser.getCodec(), usedKeysSet);

      // Report it
      var fnc = unknownFieldsFunction.get();
      if(fnc != null && fnc.apply(unknownFields)){
        throw new IllegalArgumentException("Thrown by the unknownFieldsFunction function");
      }

      // Return it
      return new SuccessfulAuthenticationResponse(accessToken, tokenType, expireAt, refreshToken, createdAt);
    }
  }
}
