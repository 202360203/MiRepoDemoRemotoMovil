package com.example.clonewhatsapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin, btnGoRegister;
    private TextView tvRecover;

    private AuthRepository authRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        btnLogin = findViewById(R.id.btnLogin);
        btnGoRegister = findViewById(R.id.btnGoRegister);
        tvRecover = findViewById(R.id.tvRecover);

        authRepository = new AuthRepository(this);

        // Iniciar sesión
        btnLogin.setOnClickListener(v -> iniciarSesion());

        // Ir al registro
        btnGoRegister.setOnClickListener(v -> {
            startActivity(new Intent(
                    LoginActivity.this,
                    RegisterActivity.class
            ));
        });

        // Recuperar contraseña
        tvRecover.setOnClickListener(v -> recuperarPassword());
    }

    private void iniciarSesion() {

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this,
                    "Completa todos los campos",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        btnLogin.setEnabled(false);

        authRepository.iniciarSesion(
                email,
                password,
                new Callback<AuthResponse>() {

                    @Override
                    public void onResponse(
                            Call<AuthResponse> call,
                            Response<AuthResponse> response) {

                        btnLogin.setEnabled(true);

                        if (response.isSuccessful()
                                && response.body() != null
                                && response.body().accessToken != null) {

                            Toast.makeText(
                                    LoginActivity.this,
                                    "Bienvenido",
                                    Toast.LENGTH_SHORT
                            ).show();

                            Intent intent = new Intent(
                                    LoginActivity.this,
                                    ContactsActivity.class
                            );

                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(
                                    LoginActivity.this,
                                    "No se pudo iniciar sesión",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<AuthResponse> call,
                            Throwable t) {

                        btnLogin.setEnabled(true);

                        Toast.makeText(
                                LoginActivity.this,
                                "Error: " + t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        );
    }

    private void recuperarPassword() {

        String email = etEmail.getText().toString().trim();

        if (email.isEmpty()) {
            etEmail.setError("Escribe tu correo electrónico");
            etEmail.requestFocus();
            return;
        }

        tvRecover.setEnabled(false);

        authRepository.recuperarPassword(
                email,
                new Callback<Void>() {

                    @Override
                    public void onResponse(
                            Call<Void> call,
                            Response<Void> response) {

                        tvRecover.setEnabled(true);

                        if (response.isSuccessful()) {

                            Toast.makeText(
                                    LoginActivity.this,
                                    "Solicitud enviada. Revisa tu correo electrónico.",
                                    Toast.LENGTH_LONG
                            ).show();

                        } else {

                        String detalle = "";

                        try {
                            if (response.errorBody() != null) {
                                detalle = response.errorBody().string();
                            }
                        } catch (Exception e) {
                            detalle = e.getMessage();
                        }

                        Toast.makeText(
                                LoginActivity.this,
                                "Error " + response.code() + ": " + detalle,
                                Toast.LENGTH_LONG
                        ).show();

                        android.util.Log.e(
                                "RECUPERAR_PASSWORD",
                                "Error Supabase: " + detalle
                        );
                    }
                    }

                    @Override
                    public void onFailure(
                            Call<Void> call,
                            Throwable t) {

                        tvRecover.setEnabled(true);

                        Toast.makeText(
                                LoginActivity.this,
                                "Error de conexión: " + t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        );
    }
}
