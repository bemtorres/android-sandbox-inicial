package com.example.sandbox;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sandbox.sandbox.ButtonsActivity;
import com.example.sandbox.sandbox.ContactRecyclerActivity;
import com.example.sandbox.sandbox.DateTimeActivity;
import com.example.sandbox.sandbox.DialogToastActivity;
import com.example.sandbox.sandbox.FeedbackActivity;
import com.example.sandbox.sandbox.LayoutsActivity;
import com.example.sandbox.sandbox.SelectionActivity;
import com.example.sandbox.sandbox.SimpleScrollListActivity;
import com.example.sandbox.sandbox.TextInputsActivity;
import com.example.sandbox.sandbox.WebViewActivity;

/**
 * HOME - Hub del Sandbox.
 * Cada CardView tiene onClick que hace startActivity(Intent).
 * Es el lugar para entender navegación entre Activities.
 * Operación interna de un botón:
 *   1. findViewById(R.id.cardInputs)
 *   2. setOnClickListener(v -> startActivity(new Intent(this, TextInputsActivity.class)))
 *   3. Toast.makeText + Log.d para trazar
 */
public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "SANDBOX_HOME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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

        Log.d(TAG, "Home cargado - 10 laboratorios disponibles");
    }

    private void go(Class<?> cls, String label) {
        Toast.makeText(this, getString(R.string.achome_toast_opening, label), Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Navegando a " + cls.getSimpleName());
        startActivity(new Intent(this, cls));
    }
}
