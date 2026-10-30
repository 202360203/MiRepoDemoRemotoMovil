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

import java.util.Locale;

public class MagnetometroActivity extends AppCompatActivity
        implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor magnetometro;
    private Sensor acelerometro;

    private float[] gravedad = new float[3];
    private float[] campoMagnetico = new float[3];

    private TextView txtCampo, txtBrujula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magnetometro);

        txtCampo = findViewById(R.id.txtCampo);
        txtBrujula = findViewById(R.id.txtBrujula);

        sensorManager = (SensorManager)
                getSystemService(SENSOR_SERVICE);

        magnetometro = sensorManager.getDefaultSensor(
                Sensor.TYPE_MAGNETIC_FIELD);

        acelerometro = sensorManager.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (magnetometro != null) {
            sensorManager.registerListener(this,
                    magnetometro,
                    SensorManager.SENSOR_DELAY_UI);
        }

        if (acelerometro != null) {
            sensorManager.registerListener(this,
                    acelerometro,
                    SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() ==
                Sensor.TYPE_ACCELEROMETER) {

            gravedad = event.values.clone();

        } else if (event.sensor.getType() ==
                Sensor.TYPE_MAGNETIC_FIELD) {

            campoMagnetico = event.values.clone();

            txtCampo.setText(
                    "Campo magnético:\nX: " + campoMagnetico[0] +
                            "\nY: " + campoMagnetico[1] +
                            "\nZ: " + campoMagnetico[2] + " µT");
        }

        float[] matriz = new float[9];
        float[] orientacion = new float[3];

        boolean disponible = SensorManager.getRotationMatrix(
                matriz, null, gravedad, campoMagnetico);

        if (disponible) {
            SensorManager.getOrientation(matriz, orientacion);

            float grados = (float) Math.toDegrees(
                    orientacion[0]);

            grados = (grados + 360) % 360;

            txtBrujula.setText(
                    "Orientación: " +
                            String.format(Locale.getDefault(),
                                    "%.1f°", grados));
        }
    }

    @Override
    public void onAccuracyChanged(
            Sensor sensor, int accuracy) {
    }
}
