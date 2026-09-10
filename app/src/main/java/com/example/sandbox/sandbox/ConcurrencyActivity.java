package com.example.sandbox.sandbox;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sandbox.R;

/**
 * LAB 11 — Threads & AsyncTask (Concurrencia en Android)
 * Demuestra:
 * 1. Worker Thread nativo + runOnUiThread para evitar bloquear el UI Thread.
 * 2. Patrón AsyncTask con reporte de porcentaje iterativo y cancelación segura.
 */
public class ConcurrencyActivity extends AppCompatActivity {

    private static final String TAG = "SANDBOX_CONCURRENCY";

    private ProgressBar progressThread;
    private TextView txtThreadStatus;
    private Button btnStartThread;

    private ProgressBar progressAsync;
    private TextView txtAsyncStatus;
    private Button btnStartAsync;
    private Button btnCancelAsync;

    private HeavyCalculationTask currentTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concurrency);

        progressThread = findViewById(R.id.progressThread);
        txtThreadStatus = findViewById(R.id.txtThreadStatus);
        btnStartThread = findViewById(R.id.btnStartThread);

        progressAsync = findViewById(R.id.progressAsync);
        txtAsyncStatus = findViewById(R.id.txtAsyncStatus);
        btnStartAsync = findViewById(R.id.btnStartAsync);
        btnCancelAsync = findViewById(R.id.btnCancelAsync);

        // 1. Worker Thread
        btnStartThread.setOnClickListener(v -> executeWorkerThread());

        // 2. AsyncTask
        btnStartAsync.setOnClickListener(v -> executeAsyncTask());
        btnCancelAsync.setOnClickListener(v -> {
            if (currentTask != null) {
                currentTask.cancel(true);
            }
        });

        Log.d(TAG, "ConcurrencyActivity cargada correctamente");
    }

    private void executeWorkerThread() {
        btnStartThread.setEnabled(false);
        progressThread.setProgress(0);
        txtThreadStatus.setText("Hilo secundario en ejecución...");
        Log.i(TAG, "Iniciando Worker Thread...");

        new Thread(() -> {
            for (int i = 1; i <= 100; i += 10) {
                try {
                    Thread.sleep(250); // Carga pesada simulada
                } catch (InterruptedException e) {
                    Log.w(TAG, "Thread interrumpido");
                    return;
                }
                final int percent = i;
                runOnUiThread(() -> {
                    progressThread.setProgress(percent);
                    txtThreadStatus.setText(String.format("Progreso: %d%%", percent));
                });
            }

            runOnUiThread(() -> {
                progressThread.setProgress(100);
                txtThreadStatus.setText("¡Worker Thread finalizado exitosamente!");
                btnStartThread.setEnabled(true);
                Toast.makeText(ConcurrencyActivity.this, "Hilo completado", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "Worker Thread completado");
            });
        }).start();
    }

    private void executeAsyncTask() {
        if (currentTask != null) {
            currentTask.cancel(true);
        }
        currentTask = new HeavyCalculationTask();
        currentTask.execute(100);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (currentTask != null && !currentTask.isCancelled()) {
            currentTask.cancel(true);
        }
    }

    /**
     * AsyncTask paramétrico:
     * Params: Integer (límite superior)
     * Progress: Integer (porcentaje 0-100)
     * Result: String (mensaje de conclusión)
     */
    @SuppressWarnings("deprecation")
    private class HeavyCalculationTask extends AsyncTask<Integer, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            btnStartAsync.setEnabled(false);
            btnCancelAsync.setEnabled(true);
            progressAsync.setProgress(0);
            txtAsyncStatus.setText("Iniciando AsyncTask en segundo plano...");
            Log.i(TAG, "AsyncTask onPreExecute");
        }

        @Override
        protected String doInBackground(Integer... params) {
            int total = (params.length > 0 && params[0] != null) ? params[0] : 100;
            for (int i = 1; i <= total; i += 10) {
                if (isCancelled()) {
                    Log.d(TAG, "AsyncTask cancelado durante doInBackground");
                    return "Tarea cancelada por el usuario";
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    return "Interrumpido";
                }
                publishProgress(i);
            }
            return "¡AsyncTask completado al 100%!";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (values.length > 0) {
                int p = values[0];
                progressAsync.setProgress(p);
                txtAsyncStatus.setText(String.format("AsyncTask: %d%%", p));
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            txtAsyncStatus.setText(result);
            progressAsync.setProgress(100);
            btnStartAsync.setEnabled(true);
            btnCancelAsync.setEnabled(false);
            Toast.makeText(ConcurrencyActivity.this, result, Toast.LENGTH_SHORT).show();
            Log.i(TAG, "AsyncTask onPostExecute: " + result);
        }

        @Override
        protected void onCancelled(String result) {
            super.onCancelled(result);
            txtAsyncStatus.setText(result != null ? result : "Tarea cancelada");
            btnStartAsync.setEnabled(true);
            btnCancelAsync.setEnabled(false);
            Log.w(TAG, "AsyncTask onCancelled");
        }
    }
}
