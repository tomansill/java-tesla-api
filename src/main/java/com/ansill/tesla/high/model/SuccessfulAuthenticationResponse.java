package com.ansill.tesla.high.model;


import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.time.Instant;

@SuppressWarnings("unused")
@Immutable
public class SuccessfulAuthenticationResponse{

    @Nonnull
    private final String accessToken;

    @Nonnull
    private final Instant expirationDate;

    @Nonnull
    private final String refreshToken;

    @Nonnull
    private final Instant createdAt;

    private SuccessfulAuthenticationResponse(
            @Nonnull String accessToken,
            @Nonnull Instant expirationDate,
            @Nonnull String refreshToken,
            @Nonnull Instant createdAt
    ){
        this.accessToken = accessToken;
        this.expirationDate = expirationDate;
        this.refreshToken = refreshToken;
        this.createdAt = createdAt;
    }

    @Nonnull
    public static SuccessfulAuthenticationResponse convert(com.ansill.tesla.low.model.SuccessfulAuthenticationResponse original){
        return new SuccessfulAuthenticationResponse(
                original.getAccessToken(),
                Instant.ofEpochSecond(original.getExpiresIn()),
                original.getRefreshToken(),
                Instant.ofEpochSecond(original.getCreatedAt())
        );
    }

    @Nonnull
    public String getAccessToken(){
        return accessToken;
    }

    @Nonnull
    public Instant getExpirationDate(){
        return expirationDate;
    }

    @Nonnull
    public String getRefreshToken(){
        return refreshToken;
    }

    @Nonnull
    public Instant getCreatedAt(){
        return createdAt;
    }
}
