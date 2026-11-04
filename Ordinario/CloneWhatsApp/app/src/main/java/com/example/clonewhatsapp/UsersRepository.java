package com.example.clonewhatsapp;

import android.content.Context;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class UsersRepository {

    private UsersAPI api;
    private SessionManager session;
    private String apiKey;

    public UsersRepository(Context context) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SupabaseConfig.SUPABASE_URL)
                .addConverterFactory(
                        retrofit2.converter.gson.GsonConverterFactory.create()
                )
                .build();

        api = retrofit.create(UsersAPI.class);

        session = new SessionManager(context);

        apiKey = SupabaseConfig.SUPABASE_KEY;
    }

    // GUARDAR PERFIL
    public void guardarPerfil(
            String id,
            String nombre,
            String email,
            String fotoUrl,
            Callback<Void> callback) {

        api.guardarPerfil(
                apiKey,
                "Bearer " + session.obtenerAccessToken(),
                "resolution=merge-duplicates,return=minimal",
                new UserRequest(id, nombre, email, fotoUrl)
        ).enqueue(callback);
    }

    // CONSULTAR USUARIOS
    public void consultarUsuarios(
            Callback<List<User>> callback) {

        api.consultarUsuarios(
                apiKey,
                "Bearer " + session.obtenerAccessToken(),
                "id,name,email,avatar_url",
                "name.asc"
        ).enqueue(callback);
    }

    // BUSCAR USUARIOS
    public void buscarUsuarios(
            String nombre,
            Callback<List<User>> callback) {

        api.buscarUsuarios(
                apiKey,
                "Bearer " + session.obtenerAccessToken(),
                "id,name,email,avatar_url",
                "ilike.*" + nombre + "*"
        ).enqueue(callback);
    }
}
