package com.example.verthorizon;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "CicloVida";
    private static final String CLAVE_TEXTO = "texto_resultado";
    private static final String CLAVE_ENTRADA = "texto_entrada";

    private EditText edtValor;
    private Button btnMostrar;
    private TextView txtResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: Actividad creada");

        Toast.makeText(
                this,
                "onCreate: Actividad creada",
                Toast.LENGTH_SHORT
        ).show();

        edtValor = findViewById(R.id.edtValor);
        btnMostrar = findViewById(R.id.btnMostrar);
        txtResultado = findViewById(R.id.txtResultado);

        // Recuperar los textos guardados
        if (savedInstanceState != null) {
            String texto = savedInstanceState.getString(CLAVE_TEXTO);
            String entrada = savedInstanceState.getString(CLAVE_ENTRADA);

            if (texto != null) {
                txtResultado.setText(texto);
            }

            if (entrada != null) {
                edtValor.setText(entrada);
            }

            Log.d(TAG, "Texto recuperado correctamente");
        }

        btnMostrar.setOnClickListener(v -> {
            String valor = edtValor.getText().toString().trim();

            if (!valor.isEmpty()) {
                txtResultado.setText(
                        "El valor ingresado es: " + valor
                );
            } else {
                Toast.makeText(
                        MainActivity.this,
                        "Por favor, ingresa un valor",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Guardar el texto que aparece en pantalla
        outState.putString(
                CLAVE_TEXTO,
                txtResultado.getText().toString()
        );

        // Guardar también lo escrito en el EditText
        outState.putString(
                CLAVE_ENTRADA,
                edtValor.getText().toString()
        );

        Log.d(TAG, "onSaveInstanceState: Texto guardado");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(TAG, "onPause: Actividad pausada");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy: Actividad destruida");
    }
}