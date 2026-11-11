package com.example.custompassemailrecovery;

import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText email, password, confirm;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.etEmailRegister);
        password = findViewById(R.id.etPasswordRegister);
        confirm = findViewById(R.id.etConfirmPassword);
        register = findViewById(R.id.btnRegister);

        register.setOnClickListener(v -> createAccount());
    }

    private void createAccount() {

        String e = email.getText().toString().trim();
        String p = password.getText().toString();
        String c = confirm.getText().toString();

        if (e.isEmpty() || p.isEmpty() || c.isEmpty()) {
            Toast.makeText(this,
                    "Completa todos los campos",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(e).matches()) {
            email.setError("Correo inválido");
            return;
        }

        if (p.length() < 6) {
            password.setError("Mínimo 6 caracteres");
            return;
        }

        if (!p.equals(c)) {
            confirm.setError("Las contraseñas no coinciden");
            return;
        }

        SupabaseClient.register(e, p,
                new SupabaseClient.ResultCallback() {

                    @Override
                    public void onSuccess(org.json.JSONObject response) {
                        Toast.makeText(RegisterActivity.this,
                                "Registro solicitado. Revisa tu correo si requiere confirmación.",
                                Toast.LENGTH_LONG).show();
                        finish();
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(RegisterActivity.this,
                                "Error de registro: " + error,
                                Toast.LENGTH_LONG).show();
                    }
                });
    }
}

