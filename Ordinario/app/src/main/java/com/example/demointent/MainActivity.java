package com.example.demointent;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = findViewById(R.id.btnActividad1);
        Button btn2 = findViewById(R.id.btnActividad2);
        Button btn3 = findViewById(R.id.btnActividad3);

        btn1.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this, Actividad1.class);

            intent.putExtra("numero1", 10);
            intent.putExtra("numero2", 20);

            startActivity(intent);
        });

        btn2.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this, Actividad2.class);

            intent.putExtra("nombre", "Carlos");

            startActivity(intent);
        });

        btn3.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this, Actividad3.class);

            intent.putExtra("mensaje",
                    "Bienvenido a mi aplicación");

            startActivity(intent);
        });
    }
}