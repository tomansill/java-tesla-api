package com.ansill.tesla.low.model;


import javax.annotation.concurrent.Immutable;

@Immutable
public class SuccessfulAuthenticationResponse{

    private final String access_token;

    private final String token_type;

    private final long expires_in;

    private final String refresh_token;

    private final long created_at;

    public SuccessfulAuthenticationResponse(
            String access_token,
            String token_type,
            long expires_in,
            String refresh_token,
            long created_at
    ){
        this.access_token = access_token;
        this.token_type = token_type;
        this.expires_in = expires_in;
        this.refresh_token = refresh_token;
        this.created_at = created_at;
    }

    public String getAccessToken(){
        return access_token;
    }

    public String getTokenType(){
        return token_type;
    }

    public long getExpiresIn(){
        return expires_in;
    }

    public String getRefreshToken(){
        return refresh_token;
    }

    public long getCreatedAt(){
        return created_at;
    }
}
