package com.example.clonewhatsapp;

import retrofit2.Call;
import retrofit2.http.*;

public interface SupabaseAPI {

    // REGISTRO
    @POST("auth/v1/signup")
    Call<AuthResponse> registrar(
            @Header("apikey") String apiKey,
            @Body AuthRequest request
    );

    // INICIO DE SESIÓN
    @POST("auth/v1/token")
    Call<AuthResponse> iniciarSesion(
            @Header("apikey") String apiKey,
            @Query("grant_type") String grantType,
            @Body AuthRequest request
    );

    // CIERRE DE SESIÓN
    @POST("auth/v1/logout")
    Call<Void> cerrarSesion(
            @Header("apikey") String apiKey,
            @Query("scope") String scope,
            @Header("Authorization") String token
    );

    // RECUPERACIÓN DE CONTRASEÑA
    @POST("auth/v1/recover")
    Call<Void> recuperarPassword(
            @Header("apikey") String apiKey,
            @Query("redirect_to") String redirectTo,
            @Body EmailRequest request
    );

    // RENOVAR JWT
    @POST("auth/v1/token")
    Call<AuthResponse> renovarToken(
            @Header("apikey") String apiKey,
            @Query("grant_type") String grantType,
            @Body RefreshRequest request
    );

    // ACTUALIZAR CONTRASEÑA
    @PUT("auth/v1/user")
    Call<AuthUser> actualizarPassword(
            @Header("apikey") String apiKey,
            @Header("Authorization") String token,
            @Body UpdatePasswordRequest request
    );

    // OBTENER USUARIO ACTUAL
    @GET("auth/v1/user")
    Call<AuthUser> obtenerUsuario(
            @Header("apikey") String apiKey,
            @Header("Authorization") String token
    );
}
