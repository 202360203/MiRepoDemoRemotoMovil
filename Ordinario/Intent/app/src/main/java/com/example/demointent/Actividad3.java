package com.example.demointent;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Actividad3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad3);

        TextView txtMensaje = findViewById(R.id.txtMensaje);
        Button btnRegresar = findViewById(R.id.btnRegresar);

        String mensaje = getIntent().getStringExtra("mensaje");

        txtMensaje.setText(mensaje);

        btnRegresar.setOnClickListener(v -> finish());
    }
}
