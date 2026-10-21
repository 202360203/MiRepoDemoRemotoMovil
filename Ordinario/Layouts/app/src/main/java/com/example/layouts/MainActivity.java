package com.example.layouts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLinear = findViewById(R.id.btnLinear);
        Button btnRelative = findViewById(R.id.btnRelative);
        Button btnFrame = findViewById(R.id.btnFrame);
        Button btnTable = findViewById(R.id.btnTable);

        btnLinear.setOnClickListener(v ->
                abrirActividad(LinearLayoutActivity.class));

        btnRelative.setOnClickListener(v ->
                abrirActividad(RelativeLayoutActivity.class));

        btnFrame.setOnClickListener(v ->
                abrirActividad(FrameLayoutActivity.class));

        btnTable.setOnClickListener(v ->
                abrirActividad(TableLayoutActivity.class));
    }

    private void abrirActividad(Class<?> actividad) {
        Intent intent = new Intent(this, actividad);
        startActivity(intent);
    }
}