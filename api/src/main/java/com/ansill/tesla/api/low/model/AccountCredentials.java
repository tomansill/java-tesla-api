package com.ansill.tesla.api.low.model;

import com.ansill.tesla.api.data.model.response.SuccessfulAuthenticationResponse;
import com.ansill.validation.Validation;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.time.Duration;
import java.time.Instant;

import static com.ansill.utility.Utility.simpleToString;

@Immutable
public final class AccountCredentials{

  @Nonnull
  private final String accessToken;

  @Nonnull
  private final String refreshToken;

  @Nonnull
  private final Instant createdAt;

  @Nonnull
  private final Duration expireAt;

  public AccountCredentials(
    @Nonnull String accessToken,
    @Nonnull String refreshToken,
    @Nonnull Instant createdAt,
    @Nonnull Duration expireAt
  ){

    // Save values
    this.accessToken = Validation.assertNonnull(accessToken, "accessToken");
    this.refreshToken = Validation.assertNonnull(refreshToken, "refreshToken");
    this.createdAt = Validation.assertNonnull(createdAt, "createdAt");
    this.expireAt = Validation.assertNonnull(expireAt, "expireAt");
  }

  @Nonnull
  public static AccountCredentials convert(@Nonnull SuccessfulAuthenticationResponse response){

    // Convert epoch to instant
    Instant creationTime = Instant.ofEpochSecond(response.getCreatedAt());

    // Convert seconds to duration
    Duration lifetime = Duration.ofSeconds(response.getExpiresIn());

    // Create object
    return new AccountCredentials(
      response.getAccessToken(),
      response.getRefreshToken(),
      creationTime,
      lifetime
    );
  }

  @Nonnull
  public String getAccessToken(){
    return accessToken;
  }

  @Nonnull
  public String getRefreshToken(){
    return refreshToken;
  }

  @Nonnull
  public Instant getCreatedAt(){
    return createdAt;
  }

  @Nonnull
  public Duration getExpireAt(){
    return expireAt;
  }

  @Override
  public boolean equals(Object o){
    if(this == o) return true;
    if(!(o instanceof AccountCredentials that)) return false;

    if(!accessToken.equals(that.accessToken)) return false;
    if(!refreshToken.equals(that.refreshToken)) return false;
    if(!createdAt.equals(that.createdAt)) return false;
    return expireAt.equals(that.expireAt);
  }

  @Override
  public int hashCode(){
    int result = accessToken.hashCode();
    result = 31 * result + refreshToken.hashCode();
    result = 31 * result + createdAt.hashCode();
    result = 31 * result + expireAt.hashCode();
    return result;
  }

  @Override
  public String toString(){
    return simpleToString(this);
  }
}
