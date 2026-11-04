package com.example.clonewhatsapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface UsersAPI {

    // GUARDAR O ACTUALIZAR PERFIL
    @POST("rest/v1/profiles")
    Call<Void> guardarPerfil(
            @Header("apikey") String apiKey,
            @Header("Authorization") String token,
            @Header("Prefer") String prefer,
            @Body UserRequest user
    );

    // CONSULTAR TODOS LOS USUARIOS
    @GET("rest/v1/profiles")
    Call<List<User>> consultarUsuarios(
            @Header("apikey") String apiKey,
            @Header("Authorization") String token,
            @Query("select") String select,
            @Query("order") String order
    );

    // BUSCAR USUARIOS POR NOMBRE
    @GET("rest/v1/profiles")
    Call<List<User>> buscarUsuarios(
            @Header("apikey") String apiKey,
            @Header("Authorization") String token,
            @Query("select") String select,
            @Query("name") String nombre
    );
}
