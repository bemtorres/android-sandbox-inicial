package com.example.sandbox.sandbox;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sandbox.R;
import java.util.Locale;

/**
 * LAB 12 — Sensores de Hardware en Android (SensorManager)
 * Incluye un laboratorio experimental en vivo y monitoreo individual de los 10 sensores:
 * 1. Acelerómetro (TYPE_ACCELEROMETER)
 * 2. Giroscopio (TYPE_GYROSCOPE)
 * 3. Magnetómetro (TYPE_MAGNETIC_FIELD)
 * 4. Sensor de Luz (TYPE_LIGHT)
 * 5. Sensor de Proximidad (TYPE_PROXIMITY)
 * 6. Presión / Barómetro (TYPE_PRESSURE)
 * 7. Temperatura Ambiental (TYPE_AMBIENT_TEMPERATURE)
 * 8. Humedad Relativa (TYPE_RELATIVE_HUMIDITY)
 * 9. Gravedad (TYPE_GRAVITY)
 * 10. Vector de Rotación (TYPE_ROTATION_VECTOR)
 */
public class SensorsActivity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "SANDBOX_SENSORS";

    private SensorManager sensorManager;

    // Los 10 Sensores
    private Sensor sensorAccel;
    private Sensor sensorGyro;
    private Sensor sensorMag;
    private Sensor sensorLight;
    private Sensor sensorProx;
    private Sensor sensorPressure;
    private Sensor sensorTemp;
    private Sensor sensorHumidity;
    private Sensor sensorGravity;
    private Sensor sensorRotation;

    // UI Experimental en vivo
    private ProgressBar progressTiltX;
    private ProgressBar progressLight;
    private TextView badgeProximity;

    // TextViews para los 10 sensores individuales
    private TextView txtSensorSummary;
    private TextView txtValAccel;
    private TextView txtValGyro;
    private TextView txtValMag;
    private TextView txtValLight;
    private TextView txtValProx;
    private TextView txtValPressure;
    private TextView txtValTemp;
    private TextView txtValHumidity;
    private TextView txtValGravity;
    private TextView txtValRotation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);

        // Bindings UI Experimental
        progressTiltX = findViewById(R.id.progressTiltX);
        progressLight = findViewById(R.id.progressLight);
        badgeProximity = findViewById(R.id.badgeProximity);

        // Bindings Sensores
        txtSensorSummary = findViewById(R.id.txtSensorSummary);
        txtValAccel = findViewById(R.id.txtValAccel);
        txtValGyro = findViewById(R.id.txtValGyro);
        txtValMag = findViewById(R.id.txtValMag);
        txtValLight = findViewById(R.id.txtValLight);
        txtValProx = findViewById(R.id.txtValProx);
        txtValPressure = findViewById(R.id.txtValPressure);
        txtValTemp = findViewById(R.id.txtValTemp);
        txtValHumidity = findViewById(R.id.txtValHumidity);
        txtValGravity = findViewById(R.id.txtValGravity);
        txtValRotation = findViewById(R.id.txtValRotation);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        initSensors();
    }

    private void initSensors() {
        if (sensorManager == null) {
            txtSensorSummary.setText("SensorManager no disponible en este dispositivo");
            return;
        }

        int availableCount = 0;

        // 1. Acelerómetro
        sensorAccel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (sensorAccel != null) availableCount++;
        else txtValAccel.setText(R.string.acsens_not_available);

        // 2. Giroscopio
        sensorGyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if (sensorGyro != null) availableCount++;
        else txtValGyro.setText(R.string.acsens_not_available);

        // 3. Magnetómetro
        sensorMag = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (sensorMag != null) availableCount++;
        else txtValMag.setText(R.string.acsens_not_available);

        // 4. Luz
        sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (sensorLight != null) availableCount++;
        else txtValLight.setText(R.string.acsens_not_available);

        // 5. Proximidad
        sensorProx = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if (sensorProx != null) availableCount++;
        else txtValProx.setText(R.string.acsens_not_available);

        // 6. Presión
        sensorPressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        if (sensorPressure != null) availableCount++;
        else txtValPressure.setText(R.string.acsens_not_available);

        // 7. Temperatura
        sensorTemp = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        if (sensorTemp != null) availableCount++;
        else txtValTemp.setText(R.string.acsens_not_available);

        // 8. Humedad
        sensorHumidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        if (sensorHumidity != null) availableCount++;
        else txtValHumidity.setText(R.string.acsens_not_available);

        // 9. Gravedad
        sensorGravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        if (sensorGravity != null) availableCount++;
        else txtValGravity.setText(R.string.acsens_not_available);

        // 10. Vector de Rotación
        sensorRotation = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        if (sensorRotation != null) availableCount++;
        else txtValRotation.setText(R.string.acsens_not_available);

        txtSensorSummary.setText(getString(R.string.acsens_available_count, availableCount));
        Log.i(TAG, "Sensores inicializados: " + availableCount + " de 10 disponibles");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sensorManager == null) return;

        // Registrar sensores disponibles con frecuencia UI
        if (sensorAccel != null) sensorManager.registerListener(this, sensorAccel, SensorManager.SENSOR_DELAY_UI);
        if (sensorGyro != null) sensorManager.registerListener(this, sensorGyro, SensorManager.SENSOR_DELAY_UI);
        if (sensorMag != null) sensorManager.registerListener(this, sensorMag, SensorManager.SENSOR_DELAY_UI);
        if (sensorLight != null) sensorManager.registerListener(this, sensorLight, SensorManager.SENSOR_DELAY_UI);
        if (sensorProx != null) sensorManager.registerListener(this, sensorProx, SensorManager.SENSOR_DELAY_UI);
        if (sensorPressure != null) sensorManager.registerListener(this, sensorPressure, SensorManager.SENSOR_DELAY_UI);
        if (sensorTemp != null) sensorManager.registerListener(this, sensorTemp, SensorManager.SENSOR_DELAY_UI);
        if (sensorHumidity != null) sensorManager.registerListener(this, sensorHumidity, SensorManager.SENSOR_DELAY_UI);
        if (sensorGravity != null) sensorManager.registerListener(this, sensorGravity, SensorManager.SENSOR_DELAY_UI);
        if (sensorRotation != null) sensorManager.registerListener(this, sensorRotation, SensorManager.SENSOR_DELAY_UI);

        Log.d(TAG, "Sensor listeners registrados en onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Desregistrar para ahorro energético
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
            Log.d(TAG, "Sensor listeners desregistrados en onPause");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event == null || event.values == null || event.values.length == 0) return;

        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                txtValAccel.setText(String.format(Locale.getDefault(), "X: %.2f | Y: %.2f | Z: %.2f m/s²", x, y, z));
                
                // Actualizar barra experimental de inclinación (-10 a +10 mapeado a 0..200)
                int tiltProgress = (int) ((x + 10.0f) * 10);
                if (tiltProgress < 0) tiltProgress = 0;
                if (tiltProgress > 200) tiltProgress = 200;
                progressTiltX.setProgress(tiltProgress);
                break;

            case Sensor.TYPE_GYROSCOPE:
                txtValGyro.setText(String.format(Locale.getDefault(), "X: %.2f | Y: %.2f | Z: %.2f rad/s",
                        event.values[0], event.values[1], event.values[2]));
                break;

            case Sensor.TYPE_MAGNETIC_FIELD:
                txtValMag.setText(String.format(Locale.getDefault(), "X: %.1f | Y: %.1f | Z: %.1f µT",
                        event.values[0], event.values[1], event.values[2]));
                break;

            case Sensor.TYPE_LIGHT:
                float lux = event.values[0];
                txtValLight.setText(String.format(Locale.getDefault(), "Iluminación: %.1f Lux (lx)", lux));
                int lightProgress = Math.min((int) lux, 1000);
                progressLight.setProgress(lightProgress);
                break;

            case Sensor.TYPE_PROXIMITY:
                float dist = event.values[0];
                txtValProx.setText(String.format(Locale.getDefault(), "Distancia: %.1f cm", dist));
                if (sensorProx != null && dist < sensorProx.getMaximumRange()) {
                    badgeProximity.setText("🔴 Objeto Cerca (< " + dist + " cm)");
                } else {
                    badgeProximity.setText("🟢 Despejado / Lejos (" + dist + " cm)");
                }
                break;

            case Sensor.TYPE_PRESSURE:
                txtValPressure.setText(String.format(Locale.getDefault(), "Presión: %.2f hPa",
                        event.values[0]));
                break;

            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                txtValTemp.setText(String.format(Locale.getDefault(), "Temperatura: %.1f °C",
                        event.values[0]));
                break;

            case Sensor.TYPE_RELATIVE_HUMIDITY:
                txtValHumidity.setText(String.format(Locale.getDefault(), "Humedad: %.1f %%",
                        event.values[0]));
                break;

            case Sensor.TYPE_GRAVITY:
                txtValGravity.setText(String.format(Locale.getDefault(), "X: %.2f | Y: %.2f | Z: %.2f m/s²",
                        event.values[0], event.values[1], event.values[2]));
                break;

            case Sensor.TYPE_ROTATION_VECTOR:
                float cosVal = event.values.length > 3 ? event.values[3] : 0.0f;
                txtValRotation.setText(String.format(Locale.getDefault(), "X: %.2f | Y: %.2f | Z: %.2f | Cos: %.2f",
                        event.values[0], event.values[1], event.values[2], cosVal));
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.v(TAG, "Precisión modificada: " + (sensor != null ? sensor.getName() : "") + " = " + accuracy);
    }
}
