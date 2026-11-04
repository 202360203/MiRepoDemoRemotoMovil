package com.example.clonewhatsapp;

import com.google.gson.annotations.SerializedName;

public class AuthResponse {

    @SerializedName("access_token")
    public String accessToken;

    @SerializedName("refresh_token")
    public String refreshToken;

    @SerializedName("expires_in")
    public long expiresIn;

    @SerializedName("token_type")
    public String tokenType;

    public AuthUser user;
}
