package com.example.sandbox.sandbox;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.example.sandbox.R;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.Locale;

/**
 * LAB 13 — Speech to Text (STT) & Text to Speech (TTS) en Android
 * Demuestra:
 * 1. TextToSpeech: Síntesis de voz con control de tono (pitch), velocidad (speech rate) y callbacks.
 * 2. SpeechRecognizer / RecognizerIntent: Reconocimiento de voz con ActivityResultLauncher.
 */
public class SpeechActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private static final String TAG = "SANDBOX_SPEECH";
    private static final String UTTERANCE_ID = "SANDBOX_TTS_ID";

    // Text To Speech
    private TextToSpeech textToSpeech;
    private TextInputEditText etTtsInput;
    private Slider sliderPitch, sliderSpeed;
    private TextView lblPitch, lblSpeed, txtTtsStatus;
    private Button btnSpeak, btnStopTts;
    private boolean isTtsReady = false;

    // Speech To Text
    private Button btnRecognizeIntent;
    private TextView txtSttResult;

    // ActivityResultLauncher para RecognizerIntent
    private final ActivityResultLauncher<Intent> speechLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    ArrayList<String> matches = result.getData().getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (matches != null && !matches.isEmpty()) {
                        String recognized = matches.get(0);
                        txtSttResult.setText(recognized);
                        Toast.makeText(this, "Voz reconocida: " + recognized, Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "STT Éxito: " + recognized);
                    }
                } else {
                    txtSttResult.setText("Reconocimiento cancelado o sin audio detectado");
                    Log.w(TAG, "STT cancelado o sin datos");
                }
            }
    );

    // Permission launcher para RECORD_AUDIO
        private final ActivityResultLauncher<String> permissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isGranted -> {
                if (isGranted) {
                    launchSpeechRecognizerIntent();
                } else {
                    Toast.makeText(this, "Se requiere permiso de micrófono para reconocer voz", Toast.LENGTH_LONG).show();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech);

        // Bindings TTS
        etTtsInput = findViewById(R.id.etTtsInput);
        sliderPitch = findViewById(R.id.sliderPitch);
        sliderSpeed = findViewById(R.id.sliderSpeed);
        lblPitch = findViewById(R.id.lblPitch);
        lblSpeed = findViewById(R.id.lblSpeed);
        txtTtsStatus = findViewById(R.id.txtTtsStatus);
        btnSpeak = findViewById(R.id.btnSpeak);
        btnStopTts = findViewById(R.id.btnStopTts);

        // Bindings STT
        btnRecognizeIntent = findViewById(R.id.btnRecognizeIntent);
        txtSttResult = findViewById(R.id.txtSttResult);

        // Inicializar motor TTS
        textToSpeech = new TextToSpeech(this, this);

        setupTtsControls();
        setupSttControls();
    }

    private void setupTtsControls() {
        sliderPitch.addOnChangeListener((slider, value, fromUser) ->
                lblPitch.setText(String.format(Locale.getDefault(), "Tono de Voz (Pitch): %.1fx", value)));

        sliderSpeed.addOnChangeListener((slider, value, fromUser) ->
                lblSpeed.setText(String.format(Locale.getDefault(), "Velocidad (Speech Rate): %.1fx", value)));

        btnSpeak.setOnClickListener(v -> speakText());

        btnStopTts.setOnClickListener(v -> {
            if (textToSpeech != null && textToSpeech.isSpeaking()) {
                textToSpeech.stop();
                txtTtsStatus.setText(R.string.acspee_status_idle);
                Log.d(TAG, "TTS detenido manualmente");
            }
        });
    }

    private void setupSttControls() {
        btnRecognizeIntent.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                    == PackageManager.PERMISSION_GRANTED) {
                launchSpeechRecognizerIntent();
            } else {
                permissionLauncher.launch(Manifest.permission.RECORD_AUDIO);
            }
        });
    }

    private void speakText() {
        if (!isTtsReady || textToSpeech == null) {
            Toast.makeText(this, "El motor TTS aún no está listo", Toast.LENGTH_SHORT).show();
            return;
        }

        String text = etTtsInput.getText() != null ? etTtsInput.getText().toString().trim() : "";
        if (text.isEmpty()) {
            etTtsInput.setError("Escribe algún texto para hablar");
            return;
        }

        float pitch = sliderPitch.getValue();
        float speed = sliderSpeed.getValue();

        textToSpeech.setPitch(pitch);
        textToSpeech.setSpeechRate(speed);

        // QUEUE_FLUSH detiene cualquier audio anterior y reproduce inmediatamente
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, UTTERANCE_ID);
        Log.i(TAG, "Reproduciendo TTS: " + text);
    }

    private void launchSpeechRecognizerIntent() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Habla ahora para transcribir...");

        try {
            speechLauncher.launch(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Tu dispositivo no cuenta con servicio de reconocimiento de voz", Toast.LENGTH_LONG).show();
            Log.e(TAG, "Error lanzando RecognizerIntent: " + e.getMessage());
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.getDefault());
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Fallback a Español o Inglés
                textToSpeech.setLanguage(new Locale("es", "ES"));
            }

            textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onStart(String utteranceId) {
                    runOnUiThread(() -> txtTtsStatus.setText(R.string.acspee_status_speaking));
                }

                @Override
                public void onDone(String utteranceId) {
                    runOnUiThread(() -> txtTtsStatus.setText(R.string.acspee_status_ready));
                }

                @Override
                public void onError(String utteranceId) {
                    runOnUiThread(() -> txtTtsStatus.setText("Error en la síntesis"));
                }
            });

            isTtsReady = true;
            txtTtsStatus.setText(R.string.acspee_status_ready);
            Log.i(TAG, "TextToSpeech inicializado correctamente");
        } else {
            txtTtsStatus.setText("Error al inicializar TextToSpeech");
            Log.e(TAG, "Fallo al inicializar TextToSpeech");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
            Log.d(TAG, "TextToSpeech liberado en onDestroy");
        }
    }
}
