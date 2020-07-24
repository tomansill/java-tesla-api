package com.ansill.tesla.api.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public abstract class ClientBuilder<T>{

  /** Url */
  protected String url;

  /** Client ID */
  protected String clientId;

  /** Client secret */
  protected String clientSecret;

  /** Debugging prints consumer */
  protected Function<Map<String,Optional<Object>>,Boolean> unknownFieldsFunction;

  /** Connection timeout duration */
  protected Duration connectTimeoutDuration;

  /** Read timeout duration */
  protected Duration readTimeoutDuration;

  protected ClientBuilder(){

  }

  /**
   * Sets URL
   *
   * @param url desired URL or null to use default
   * @return updated builder
   */
  @Nonnull
  public ClientBuilder<T> setUrl(@Nullable String url){
    this.url = url;
    return this;
  }

  /**
   * Sets client ID
   *
   * @param clientId desired client id or null to use default
   * @return updated builder
   */
  @Nonnull
  public ClientBuilder<T> setClientId(@Nullable String clientId){
    this.clientId = clientId;
    return this;
  }

  /**
   * Sets client secret
   *
   * @param clientSecret desired client secret or null to use default
   * @return updated builder
   */
  @Nonnull
  public ClientBuilder<T> setClientSecret(@Nullable String clientSecret){
    this.clientSecret = clientSecret;
    return this;
  }

  /**
   * Sets debugging function - function that will be called when deserializer encounters strange property, returning true in the function will tell deserializer to do a hard-fail, returning false tells the deserializer to ignore the strange properties and continue
   *
   * @param unknownFieldsFunction debugging function - null will yield a lenient function
   * @return updated builder
   */
  @Nonnull
  public ClientBuilder<T> setUnknownFieldsFunction(@Nullable Function<Map<String,Optional<Object>>,Boolean> unknownFieldsFunction){
    this.unknownFieldsFunction = unknownFieldsFunction;
    return this;
  }

  /**
   * Sets connection timeout duration
   *
   * @param timeout desired client secret or null to use default
   * @return updated builder
   */
  @Nonnull
  public ClientBuilder<T> setConnectionTimeoutDuration(@Nullable Duration timeout){
    this.connectTimeoutDuration = timeout;
    return this;
  }


  /**
   * Sets read timeout duration
   *
   * @param timeout desired client secret or null to use default
   * @return updated builder
   */
  @Nonnull
  public ClientBuilder<T> setReadTimeoutDuration(@Nullable Duration timeout){
    this.readTimeoutDuration = timeout;
    return this;
  }

  /**
   * Builds the Client
   *
   * @return Client
   */
  @Nonnull
  public abstract T build();
}
