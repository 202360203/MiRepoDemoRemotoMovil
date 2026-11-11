package com.example.custompassemailrecovery;

import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

public class NewPasswordActivity extends AppCompatActivity {

    EditText password, confirm;
    Button update;

    String tokenHash;
    String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        password = findViewById(R.id.etNewPassword);
        confirm = findViewById(R.id.etConfirmNewPassword);
        update = findViewById(R.id.btnUpdatePassword);

        Uri uri = getIntent().getData();

        if (uri == null) {
            Toast.makeText(this,
                    "Enlace de recuperación inválido",
                    Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        tokenHash = uri.getQueryParameter("token_hash");

        if (tokenHash == null || tokenHash.isEmpty()) {
            Toast.makeText(this,
                    "El enlace no contiene un token válido",
                    Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        update.setEnabled(false);

        SupabaseClient.verifyRecovery(tokenHash,
                new SupabaseClient.ResultCallback() {

                    @Override
                    public void onSuccess(JSONObject response) {

                        accessToken = response.optString("access_token", "");

                        if (accessToken.isEmpty()) {
                            Toast.makeText(NewPasswordActivity.this,
                                    "No se pudo validar la recuperación",
                                    Toast.LENGTH_LONG).show();
                            finish();
                            return;
                        }

                        update.setEnabled(true);
                    }

                    @Override
                    public void onError(String error) {

                        Toast.makeText(NewPasswordActivity.this,
                                "Enlace inválido o expirado",
                                Toast.LENGTH_LONG).show();
                        finish();
                    }
                });

        update.setOnClickListener(v -> changePassword());
    }

    private void changePassword() {

        String p = password.getText().toString();
        String c = confirm.getText().toString();

        if (p.length() < 6) {
            password.setError("Mínimo 6 caracteres");
            return;
        }

        if (!p.equals(c)) {
            confirm.setError("Las contraseñas no coinciden");
            return;
        }

        update.setEnabled(false);

        SupabaseClient.updatePassword(accessToken, p,
                new SupabaseClient.ResultCallback() {

                    @Override
                    public void onSuccess(JSONObject response) {

                        Toast.makeText(NewPasswordActivity.this,
                                "Contraseña actualizada correctamente",
                                Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(
                                NewPasswordActivity.this,
                                LoginActivity.class);

                        intent.setFlags(
                                Intent.FLAG_ACTIVITY_NEW_TASK |
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(String error) {

                        update.setEnabled(true);

                        Toast.makeText(NewPasswordActivity.this,
                                "Error al cambiar contraseña: " + error,
                                Toast.LENGTH_LONG).show();
                    }
                });
    }
}
