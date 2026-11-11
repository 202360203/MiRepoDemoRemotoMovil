
package com.example.custompassemailrecovery;

import android.os.Bundle;
import android.content.Intent;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin;
    TextView tvRegister, tvRecovery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        tvRecovery = findViewById(R.id.tvRecovery);

        btnLogin.setOnClickListener(v -> login());

        tvRegister.setOnClickListener(v ->
                startActivity(new Intent(this,
                        RegisterActivity.class)));

        tvRecovery.setOnClickListener(v ->
                startActivity(new Intent(this,
                        RecoveryActivity.class)));
    }

    private void login() {

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this,
                    "Completa todos los campos",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        SupabaseClient.login(email, password,
                new SupabaseClient.ResultCallback() {

                    @Override
                    public void onSuccess(org.json.JSONObject response) {
                        Toast.makeText(LoginActivity.this,
                                "Inicio de sesión exitoso",
                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(LoginActivity.this,
                                "Error al iniciar sesión: " + error,
                                Toast.LENGTH_LONG).show();
                    }
                });
    }
}
