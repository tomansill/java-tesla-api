package com.ansill.tesla.api.low.model;

import com.ansill.tesla.api.raw.model.SuccessfulAuthenticationResponse;
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
  private final Instant creationTime;

  @Nonnull
  private final Instant expirationTime;

  public AccountCredentials(
    @Nonnull String accessToken,
    @Nonnull String refreshToken,
    @Nonnull Instant creationTime,
    @Nonnull Instant expirationTime
  ){

    // Save values
    this.accessToken = Validation.assertNonnull(accessToken, "accessToken");
    this.refreshToken = Validation.assertNonnull(refreshToken, "refreshToken");
    this.creationTime = Validation.assertNonnull(creationTime, "creationTime");
    this.expirationTime = Validation.assertNonnull(expirationTime, "expirationTime");

    // Ensure that creation and expiration times are valid
    if(expirationTime.isBefore(creationTime) || expirationTime.equals(creationTime)){
      throw new IllegalArgumentException("The expiration time is on or before creation time");
    }
  }

  @Nonnull
  public static AccountCredentials convert(@Nonnull SuccessfulAuthenticationResponse response){

    // Convert epoch to instant
    Instant creationTime = Instant.ofEpochSecond(response.getCreatedAt());

    // Convert seconds to duration
    Duration lifetime = Duration.ofSeconds(response.getExpiresIn());

    // Create expiry date
    Instant expirationTime = creationTime.plus(lifetime);

    // Create object
    return new AccountCredentials(
      response.getAccessToken(),
      response.getRefreshToken(),
      creationTime,
      expirationTime
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
  public Instant getCreationTime(){
    return creationTime;
  }

  @Nonnull
  public Instant getExpirationTime(){
    return expirationTime;
  }

  @Override
  public boolean equals(Object o){
    if(this == o) return true;
    if(!(o instanceof AccountCredentials that)) return false;

    if(!accessToken.equals(that.accessToken)) return false;
    if(!refreshToken.equals(that.refreshToken)) return false;
    if(!creationTime.equals(that.creationTime)) return false;
    return expirationTime.equals(that.expirationTime);
  }

  @Override
  public int hashCode(){
    int result = accessToken.hashCode();
    result = 31 * result + refreshToken.hashCode();
    result = 31 * result + creationTime.hashCode();
    result = 31 * result + expirationTime.hashCode();
    return result;
  }

  @Override
  public String toString(){
    return simpleToString(this);
  }
}
