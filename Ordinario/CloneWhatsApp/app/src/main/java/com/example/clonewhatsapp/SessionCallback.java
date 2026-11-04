package com.example.clonewhatsapp;

public interface SessionCallback {

    void onResult(String accessToken);

    void onError(String error);
}
