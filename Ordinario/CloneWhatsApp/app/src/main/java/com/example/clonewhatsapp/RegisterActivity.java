package com.example.clonewhatsapp;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnRegister;

    private AuthRepository authRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);

        authRepository = new AuthRepository(this);

        btnRegister.setOnClickListener(v -> registrarUsuario());
    }

    private void registrarUsuario() {

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this,
                    "Completa todos los campos",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this,
                    "La contraseña debe tener al menos 6 caracteres",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        btnRegister.setEnabled(false);

        authRepository.registrar(email, password,
                new Callback<AuthResponse>() {

                    @Override
                    public void onResponse(
                            Call<AuthResponse> call,
                            Response<AuthResponse> response) {

                        btnRegister.setEnabled(true);

                        if (response.isSuccessful()) {

                            Toast.makeText(
                                    RegisterActivity.this,
                                    "Registro exitoso. Revisa tu correo si requiere confirmación.",
                                    Toast.LENGTH_LONG
                            ).show();

                            startActivity(new Intent(
                                    RegisterActivity.this,
                                    LoginActivity.class
                            ));

                            finish();

                        } else {

                            Toast.makeText(
                                    RegisterActivity.this,
                                    "Error al registrar. Verifica el correo o la contraseña.",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<AuthResponse> call,
                            Throwable t) {

                        btnRegister.setEnabled(true);

                        Toast.makeText(
                                RegisterActivity.this,
                                "Error de conexión: " + t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }
}

