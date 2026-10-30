package com.example.sensoresnavdrawer;

import static android.content.Context.SENSOR_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GiroscopioActivity extends AppCompatActivity
        implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor giroscopio;

    private TextView txtX, txtY, txtZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giroscopio);

        txtX = findViewById(R.id.txtX);
        txtY = findViewById(R.id.txtY);
        txtZ = findViewById(R.id.txtZ);

        sensorManager = (SensorManager)
                getSystemService(SENSOR_SERVICE);

        giroscopio = sensorManager.getDefaultSensor(
                Sensor.TYPE_GYROSCOPE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (giroscopio != null) {
            sensorManager.registerListener(
                    this, giroscopio,
                    SensorManager.SENSOR_DELAY_UI);
        } else {
            txtX.setText("Giroscopio no disponible");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        txtX.setText("Rotación X: " + event.values[0]);
        txtY.setText("Rotación Y: " + event.values[1]);
        txtZ.setText("Rotación Z: " + event.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
