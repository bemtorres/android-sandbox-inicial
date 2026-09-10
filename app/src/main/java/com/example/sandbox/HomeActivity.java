package com.example.sandbox;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sandbox.sandbox.ButtonsActivity;
import com.example.sandbox.sandbox.ConcurrencyActivity;
import com.example.sandbox.sandbox.ContactRecyclerActivity;
import com.example.sandbox.sandbox.DateTimeActivity;
import com.example.sandbox.sandbox.DialogToastActivity;
import com.example.sandbox.sandbox.FeedbackActivity;
import com.example.sandbox.sandbox.LayoutsActivity;
import com.example.sandbox.sandbox.SelectionActivity;
import com.example.sandbox.sandbox.SensorsActivity;
import com.example.sandbox.sandbox.SimpleScrollListActivity;
import com.example.sandbox.sandbox.SpeechActivity;
import com.example.sandbox.sandbox.TextInputsActivity;
import com.example.sandbox.sandbox.WebViewActivity;

/**
 * HOME - Hub del Sandbox.
 * Organizado en 2 grandes módulos formativos:
 * - Módulo 1: Conoce Android (Labs 1 al 10 - Vistas y Widgets)
 * - Módulo 2: Funcionalidades Especiales (Labs 11, 12 y 13 - Concurrencia, Sensores y Speech)
 */
public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "SANDBOX_HOME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Módulo 1: Conoce Android (Labs 1 al 10)
        findViewById(R.id.cardInputs).setOnClickListener(v -> go(TextInputsActivity.class, "TextInputs"));
        findViewById(R.id.cardDateTime).setOnClickListener(v -> go(DateTimeActivity.class, "DateTime"));
        findViewById(R.id.cardButtons).setOnClickListener(v -> go(ButtonsActivity.class, "Buttons"));
        findViewById(R.id.cardSelection).setOnClickListener(v -> go(SelectionActivity.class, "Selection"));
        findViewById(R.id.cardFeedback).setOnClickListener(v -> go(FeedbackActivity.class, "Feedback"));
        findViewById(R.id.cardWebView).setOnClickListener(v -> go(WebViewActivity.class, "WebView"));
        findViewById(R.id.cardDialogs).setOnClickListener(v -> go(DialogToastActivity.class, "Dialogs"));
        findViewById(R.id.cardLayouts).setOnClickListener(v -> go(LayoutsActivity.class, "Layouts"));
        findViewById(R.id.cardScroll).setOnClickListener(v -> go(SimpleScrollListActivity.class, "ScrollList"));
        findViewById(R.id.cardRecycler).setOnClickListener(v -> go(ContactRecyclerActivity.class, "Recycler"));

        // Módulo 2: Funcionalidades Especiales (Labs 11, 12 y 13)
        findViewById(R.id.cardConcurrency).setOnClickListener(v -> go(ConcurrencyActivity.class, "Concurrencia (Threads/AsyncTask)"));
        findViewById(R.id.cardSensors).setOnClickListener(v -> go(SensorsActivity.class, "Sensores Android (SensorManager)"));
        findViewById(R.id.cardSpeech).setOnClickListener(v -> go(SpeechActivity.class, "Speech (TTS / STT)"));

        Log.d(TAG, "Home cargado - 13 laboratorios en 2 módulos disponibles");
    }

    private void go(Class<?> cls, String label) {
        Toast.makeText(this, getString(R.string.achome_toast_opening, label), Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Navegando a " + cls.getSimpleName());
        startActivity(new Intent(this, cls));
    }
}
