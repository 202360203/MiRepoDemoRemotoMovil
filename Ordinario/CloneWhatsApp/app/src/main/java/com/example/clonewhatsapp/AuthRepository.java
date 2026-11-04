package com.example.clonewhatsapp;

import android.content.Context;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {

    private final SupabaseAPI api;
    private final SessionManager session;
    private final String apiKey;

    public AuthRepository(Context context) {

        api = RetrofitClient.getAPI();

        session = new SessionManager(context);

        apiKey = SupabaseConfig.SUPABASE_KEY;
    }

    // REGISTRO
    public void registrar(
            String email,
            String password,
            Callback<AuthResponse> callback) {

        api.registrar(
                apiKey,
                new AuthRequest(email, password)
        ).enqueue(new Callback<AuthResponse>() {

            @Override
            public void onResponse(
                    Call<AuthResponse> call,
                    Response<AuthResponse> response) {

                if (response.isSuccessful() &&
                        response.body() != null &&
                        response.body().accessToken != null) {

                    session.guardarSesion(response.body());
                }

                callback.onResponse(call, response);
            }

            @Override
            public void onFailure(
                    Call<AuthResponse> call,
                    Throwable t) {

                callback.onFailure(call, t);
            }
        });
    }

    // INICIO DE SESIÓN
    public void iniciarSesion(
            String email,
            String password,
            Callback<AuthResponse> callback) {

        api.iniciarSesion(
                apiKey,
                "password",
                new AuthRequest(email, password)
        ).enqueue(new Callback<AuthResponse>() {

            @Override
            public void onResponse(
                    Call<AuthResponse> call,
                    Response<AuthResponse> response) {

                if (response.isSuccessful() &&
                        response.body() != null) {

                    session.guardarSesion(response.body());
                }

                callback.onResponse(call, response);
            }

            @Override
            public void onFailure(
                    Call<AuthResponse> call,
                    Throwable t) {

                callback.onFailure(call, t);
            }
        });
    }

    // CIERRE DE SESIÓN
    public void cerrarSesion(Callback<Void> callback) {

        String token = session.obtenerAccessToken();

        if (token == null) {
            session.eliminarSesion();
            return;
        }

        api.cerrarSesion(
                apiKey,
                "local",
                "Bearer " + token
        ).enqueue(new Callback<Void>() {

            @Override
            public void onResponse(
                    Call<Void> call,
                    Response<Void> response) {

                session.eliminarSesion();

                callback.onResponse(call, response);
            }

            @Override
            public void onFailure(
                    Call<Void> call,
                    Throwable t) {

                session.eliminarSesion();

                callback.onFailure(call, t);
            }
        });
    }

    // RECUPERAR CONTRASEÑA
    public void recuperarPassword(
            String email,
            Callback<Void> callback) {

        api.recuperarPassword(
                apiKey,
                "miapp://auth/reset-password",
                new EmailRequest(email)
        ).enqueue(callback);
    }

    // RENOVAR TOKEN
    public void renovarSesion(Callback<AuthResponse> callback) {

        String refreshToken = session.obtenerRefreshToken();

        if (refreshToken == null) {
            return;
        }

        api.renovarToken(
                apiKey,
                "refresh_token",
                new RefreshRequest(refreshToken)
        ).enqueue(new Callback<AuthResponse>() {

            @Override
            public void onResponse(
                    Call<AuthResponse> call,
                    Response<AuthResponse> response) {

                if (response.isSuccessful() &&
                        response.body() != null) {

                    session.guardarSesion(response.body());
                }

                callback.onResponse(call, response);
            }

            @Override
            public void onFailure(
                    Call<AuthResponse> call,
                    Throwable t) {

                callback.onFailure(call, t);
            }
        });
    }

    // ACTUALIZAR CONTRASEÑA
    public void actualizarPassword(
            String password,
            Callback<AuthUser> callback) {

        String token = session.obtenerAccessToken();

        if (token == null) {
            return;
        }

        api.actualizarPassword(
                apiKey,
                "Bearer " + token,
                new UpdatePasswordRequest(password)
        ).enqueue(callback);
    }

    // OBTENER TOKEN VÁLIDO
    public void obtenerTokenValido(SessionCallback callback) {

        String token = session.obtenerAccessToken();

        if (token == null) {
            callback.onError("No existe una sesión");
            return;
        }

        if (session.tokenVigente()) {
            callback.onResult(token);
            return;
        }

        renovarSesion(new Callback<AuthResponse>() {

            @Override
            public void onResponse(
                    Call<AuthResponse> call,
                    Response<AuthResponse> response) {

                if (response.isSuccessful() &&
                        response.body() != null &&
                        response.body().accessToken != null) {

                    callback.onResult(
                            response.body().accessToken
                    );

                } else {
                    if (response.code() == 400 ||
                            response.code() == 401) {
                        session.eliminarSesion();
                    }

                    callback.onError("No se pudo renovar la sesión");
                }
            }

            @Override
            public void onFailure(
                    Call<AuthResponse> call,
                    Throwable t) {

                callback.onError("Error de conexión");
            }
        });
    }
}