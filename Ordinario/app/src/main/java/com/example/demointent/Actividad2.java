package com.example.demointent;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Actividad2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad2);

        TextView txtNombre = findViewById(R.id.txtNombre);
        Button btnRegresar = findViewById(R.id.btnRegresar);

        String nombre = getIntent().getStringExtra("nombre");

        txtNombre.setText("Hola, " + nombre);

        btnRegresar.setOnClickListener(v -> finish());
    }
}