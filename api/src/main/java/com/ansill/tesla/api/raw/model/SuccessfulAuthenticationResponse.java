package com.ansill.tesla.api.raw.model;


import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import static com.ansill.utility.Utility.simpleToString;

@Immutable
public final class SuccessfulAuthenticationResponse{

  @Nonnull
  private final String access_token;

  @Nonnull
  private final String token_type;

  @Nonnegative
  private final long expires_in;

  @Nonnull
  private final String refresh_token;

  @Nonnegative
  private final long created_at;

  public SuccessfulAuthenticationResponse(
    @NotNull String access_token,
    @NotNull String token_type,
    @Nonnegative long expires_in,
    @NotNull String refresh_token,
    @Nonnegative long created_at
  ){
    this.access_token = access_token;
    this.token_type = token_type;
    this.expires_in = expires_in;
    this.refresh_token = refresh_token;
    this.created_at = created_at;
  }

  @Nonnull
  public String getAccessToken(){
    return access_token;
  }

  @Nonnull
  public String getTokenType(){
    return token_type;
  }

  @Nonnegative
  public long getExpiresIn(){
    return expires_in;
  }

  @Nonnull
  public String getRefreshToken(){
    return refresh_token;
  }

  @Nonnegative
  public long getCreatedAt(){
    return created_at;
  }

  @Override
  public String toString(){
    return simpleToString(this);
  }
}
