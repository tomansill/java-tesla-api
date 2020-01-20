package com.ansill.tesla.med.model;

import com.ansill.tesla.low.model.SuccessfulAuthenticationResponse;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.time.Duration;
import java.time.Instant;

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

    private AccountCredentials(
            @Nonnull String accessToken,
            @Nonnull String refreshToken,
            @Nonnull Instant creationTime,
            @Nonnull Instant expirationTime
    ){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.creationTime = creationTime;
        this.expirationTime = expirationTime;
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
}
