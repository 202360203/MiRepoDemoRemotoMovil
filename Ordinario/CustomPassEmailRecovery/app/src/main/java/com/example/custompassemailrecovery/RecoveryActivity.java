package com.example.custompassemailrecovery;

import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class RecoveryActivity extends AppCompatActivity {

    EditText email;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery);

        email = findViewById(R.id.etRecoveryEmail);
        send = findViewById(R.id.btnSendRecovery);

        send.setOnClickListener(v -> sendRecovery());
    }

    private void sendRecovery() {

        String e = email.getText().toString().trim();

        if (e.isEmpty()) {
            email.setError("Ingresa tu correo");
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(e).matches()) {
            email.setError("Correo inválido");
            return;
        }

        send.setEnabled(false);

        SupabaseClient.recover(e,
                new SupabaseClient.ResultCallback() {

                    @Override
                    public void onSuccess(org.json.JSONObject response) {

                        send.setEnabled(true);

                        Toast.makeText(RecoveryActivity.this,
                                "Si la cuenta existe, recibirás un correo de recuperación.",
                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(String error) {

                        send.setEnabled(true);

                        Toast.makeText(RecoveryActivity.this,
                                "No se pudo enviar el correo: " + error,
                                Toast.LENGTH_LONG).show();
                    }
                });
    }
}

