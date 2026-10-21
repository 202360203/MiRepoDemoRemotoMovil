package com.example.listas;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PersonaAdapter
        extends RecyclerView.Adapter<PersonaViewHolder> {

    private final List<String> personas;

    public PersonaAdapter(List<String> personas) {
        this.personas = personas;
    }

    // Crear la vista de cada elemento
    @NonNull
    @Override
    public PersonaViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_persona, parent, false);

        return new PersonaViewHolder(vista);
    }

    // Vincular los datos con la vista
    @Override
    public void onBindViewHolder(
            @NonNull PersonaViewHolder holder,
            int position) {

        holder.txtNombre.setText(personas.get(position));
    }

    // Obtener el número de elementos
    @Override
    public int getItemCount() {
        return personas.size();
    }
}