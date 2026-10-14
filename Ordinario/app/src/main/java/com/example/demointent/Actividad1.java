package com.example.demointent;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Actividad1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad1);

        TextView txtResultado = findViewById(R.id.txtResultado);
        Button btnRegresar = findViewById(R.id.btnRegresar);

        int numero1 = getIntent().getIntExtra("numero1", 0);
        int numero2 = getIntent().getIntExtra("numero2", 0);

        int suma = numero1 + numero2;

        txtResultado.setText(
                "La suma de " + numero1 + " + " +
                        numero2 + " = " + suma
        );

        btnRegresar.setOnClickListener(v -> finish());
    }
}