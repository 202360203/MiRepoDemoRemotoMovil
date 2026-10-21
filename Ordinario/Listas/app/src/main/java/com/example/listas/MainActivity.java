package com.example.listas;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerPersonas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerPersonas =
                findViewById(R.id.recyclerPersonas);

        // 1. Crear la lista de datos
        List<String> personas = Arrays.asList(
                "Ana",
                "Carlos",
                "María",
                "José",
                "Laura",
                "Pedro"
        );

        // 2. Definir el posicionamiento vertical
        recyclerPersonas.setLayoutManager(
                new LinearLayoutManager(this)
        );

        // 3. Asignar el Adapter
        PersonaAdapter adapter =
                new PersonaAdapter(personas);

        recyclerPersonas.setAdapter(adapter);
    }
}