package com.ansill.tesla.api.high.model;


import com.ansill.tesla.api.data.model.response.SuccessfulAuthenticationResponse;
import com.ansill.validation.Validation;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.time.Instant;

import static com.ansill.utility.Utility.simpleToString;

@Immutable
public final class AccountCredentials{

  @Nonnull
  private final String accessToken;

  @Nonnull
  private final String refreshToken;

  @Nonnull
  private final Instant expirationTime;

  public AccountCredentials(
    @Nonnull String accessToken,
    @Nonnull String refreshToken,
    @Nonnull Instant expirationTime
  ){

    // Save values
    this.accessToken = Validation.assertNonnull(accessToken, "accessToken");
    this.refreshToken = Validation.assertNonnull(refreshToken, "refreshToken");
    this.expirationTime = Validation.assertNonnull(expirationTime, "expirationTime");
  }

  @Nonnull
  public static AccountCredentials convert(@Nonnull com.ansill.tesla.api.low.model.AccountCredentials lowAccountCredentials){

    // Get expiration time
    var expirationTime = lowAccountCredentials.getCreatedAt().plus(lowAccountCredentials.getExpireAt());

    // Create object
    return new AccountCredentials(
      lowAccountCredentials.getAccessToken(),
      lowAccountCredentials.getRefreshToken(),
      expirationTime
    );
  }

  @Nonnull
  public static AccountCredentials convert(@Nonnull SuccessfulAuthenticationResponse response){
    return AccountCredentials.convert(com.ansill.tesla.api.low.model.AccountCredentials.convert(response));
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
  public Instant getExpirationTime(){
    return expirationTime;
  }

  @Override
  public boolean equals(Object o){
    if(this == o) return true;
    if(!(o instanceof AccountCredentials that)) return false;

    if(!accessToken.equals(that.accessToken)) return false;
    if(!refreshToken.equals(that.refreshToken)) return false;
    return expirationTime.equals(that.expirationTime);
  }

  @Override
  public int hashCode(){
    int result = accessToken.hashCode();
    result = 31 * result + refreshToken.hashCode();
    result = 31 * result + expirationTime.hashCode();
    return result;
  }

  @Override
  public String toString(){
    return simpleToString(this);
  }
}
