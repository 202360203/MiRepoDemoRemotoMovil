package com.example.sensoresnavdrawer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PedometerActivity extends AppCompatActivity
        implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor contadorPasos;
    private TextView txtPasos;

    private static final int REQUEST_PERMISSION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedometer);

        txtPasos = findViewById(R.id.txtPasos);

        sensorManager = (SensorManager)
                getSystemService(SENSOR_SERVICE);

        contadorPasos = sensorManager.getDefaultSensor(
                Sensor.TYPE_STEP_COUNTER);

        if (contadorPasos == null) {
            txtPasos.setText("Podómetro no disponible");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (contadorPasos == null) return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q &&
                checkSelfPermission(
                        Manifest.permission.ACTIVITY_RECOGNITION)
                        != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{
                            Manifest.permission.ACTIVITY_RECOGNITION
                    },
                    REQUEST_PERMISSION);

        } else {
            iniciarContador();
        }
    }

    private void iniciarContador() {
        sensorManager.registerListener(
                this, contadorPasos,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, String[] permissions,
            int[] grantResults) {

        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION &&
                grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            iniciarContador();
        } else {
            txtPasos.setText("Permiso de actividad denegado");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int pasos = (int) event.values[0];
        txtPasos.setText("Pasos registrados: " + pasos);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}