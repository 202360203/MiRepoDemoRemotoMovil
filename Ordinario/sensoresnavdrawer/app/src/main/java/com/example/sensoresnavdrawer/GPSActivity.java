package com.example.sensoresnavdrawer;

import static android.content.Context.LOCATION_SERVICE;
import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;
import static androidx.core.content.ContextCompat.getSystemService;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class GPSActivity extends AppCompatActivity {

    private TextView txtLatitud, txtLongitud;
    private LocationManager locationManager;

    private final ActivityResultLauncher<String> permisoGPS =
            registerForActivityResult(
                    new ActivityResultContracts.RequestPermission(),
                    concedido -> {
                        if (concedido) {
                            obtenerUbicacion();
                        } else {
                            Toast.makeText(this,
                                    "Permiso de ubicación denegado",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        txtLatitud = findViewById(R.id.txtLatitud);
        txtLongitud = findViewById(R.id.txtLongitud);

        locationManager = (LocationManager)
                getSystemService(LOCATION_SERVICE);

        findViewById(R.id.btnUbicacion).setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                obtenerUbicacion();

            } else {
                permisoGPS.launch(
                        Manifest.permission.ACCESS_FINE_LOCATION);
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void obtenerUbicacion() {

        if (!locationManager.isProviderEnabled(
                LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this,
                    "Activa el GPS del dispositivo",
                    Toast.LENGTH_LONG).show();
            return;
        }

        locationManager.requestSingleUpdate(
                LocationManager.GPS_PROVIDER,
                location -> {
                    txtLatitud.setText(
                            "Latitud: " + location.getLatitude());

                    txtLongitud.setText(
                            "Longitud: " + location.getLongitude());
                },
                null);
    }
}