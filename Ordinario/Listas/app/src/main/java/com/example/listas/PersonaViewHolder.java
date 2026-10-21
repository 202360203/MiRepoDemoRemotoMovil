package com.example.listas;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class PersonaViewHolder extends RecyclerView.ViewHolder {

    TextView txtNombre;

    public PersonaViewHolder(View itemView) {
        super(itemView);

        txtNombre = itemView.findViewById(R.id.txtNombre);
    }
}