package com.example.clonewhatsapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private final SharedPreferences preferences;

    public SessionManager(Context context) {

        preferences = context.getSharedPreferences(
                "user_session",
                Context.MODE_PRIVATE
        );
    }

    public void guardarSesion(AuthResponse response) {

        if (response == null ||
                response.accessToken == null ||
                response.refreshToken == null) {
            return;
        }

        long expiracion = System.currentTimeMillis()
                + response.expiresIn * 1000L;

        preferences.edit()
                .putString("access_token", response.accessToken)
                .putString("refresh_token", response.refreshToken)
                .putLong("expires_at", expiracion)
                .putString("user_id",
                        response.user != null ? response.user.id : null)
                .apply();
    }

    public String obtenerAccessToken() {
        return preferences.getString("access_token", null);
    }

    public String obtenerRefreshToken() {
        return preferences.getString("refresh_token", null);
    }

    public String obtenerUserId() {
        return preferences.getString("user_id", null);
    }

    public boolean haySesion() {
        return obtenerRefreshToken() != null;
    }

    public boolean tokenVigente() {

        long expiracion = preferences.getLong("expires_at", 0);

        return System.currentTimeMillis() < expiracion - 60000;
    }

    public void eliminarSesion() {
        preferences.edit().clear().apply();
    }
}