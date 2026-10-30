package com.example.sensoresnavdrawer;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(
                android.R.drawable.ic_menu_sort_by_size);

        toolbar.setNavigationOnClickListener(v ->
                drawerLayout.openDrawer(
                        GravityCompat.START));

        navigationView.setNavigationItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_inicio) {
                // Permanecer en inicio
            } else if (id == R.id.nav_acelerometro) {
                abrirActividad(AcelerometroActivity.class);
            } else if (id == R.id.nav_pedometer) {
                abrirActividad(PedometerActivity.class);
            } else if (id == R.id.nav_giroscopio) {
                abrirActividad(GiroscopioActivity.class);
            } else if (id == R.id.nav_camara) {
                abrirActividad(CamaraActivity.class);
            } else if (id == R.id.nav_gps) {
                abrirActividad(GPSActivity.class);
            } else if (id == R.id.nav_magnetometro) {
                abrirActividad(MagnetometroActivity.class);
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void abrirActividad(Class<?> actividad) {
        startActivity(new Intent(this, actividad));
    }
}