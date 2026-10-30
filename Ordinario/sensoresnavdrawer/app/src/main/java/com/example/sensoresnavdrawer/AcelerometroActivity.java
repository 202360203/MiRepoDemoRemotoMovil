package com.example.sensoresnavdrawer;

import static androidx.core.content.ContextCompat.getSystemService;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AcelerometroActivity extends AppCompatActivity
        implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor acelerometro;
    private TextView txtX, txtY, txtZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acelerometro);

        txtX = findViewById(R.id.txtX);
        txtY = findViewById(R.id.txtY);
        txtZ = findViewById(R.id.txtZ);

        sensorManager = (SensorManager)
                getSystemService(SENSOR_SERVICE);

        acelerometro = sensorManager.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER);

        if (acelerometro == null) {
            txtX.setText("Acelerómetro no disponible");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (acelerometro != null) {
            sensorManager.registerListener(
                    this, acelerometro,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        txtX.setText("X: " + event.values[0] + " m/s²");
        txtY.setText("Y: " + event.values[1] + " m/s²");
        txtZ.setText("Z: " + event.values[2] + " m/s²");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}