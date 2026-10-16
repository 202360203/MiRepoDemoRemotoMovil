package com.example.fragmentado;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    Button btnInicio, btnPerfil, btnAjustes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInicio = findViewById(R.id.btnInicio);
        btnPerfil = findViewById(R.id.btnPerfil);
        btnAjustes = findViewById(R.id.btnAjustes);

        // Mostrar Inicio al abrir la aplicación
        if (savedInstanceState == null) {
            mostrarFragmento(new InicioFragment());
        }

        // Botón Inicio
        btnInicio.setOnClickListener(v -> {
            mostrarFragmento(new InicioFragment());
        });

        // Botón Perfil
        btnPerfil.setOnClickListener(v -> {
            mostrarFragmento(new PerfilFragment());
        });

        // Botón Ajustes
        btnAjustes.setOnClickListener(v -> {
            mostrarFragmento(new AjustesFragment());
        });
    }

    private void mostrarFragmento(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedor, fragment)
                .commit();
    }
}