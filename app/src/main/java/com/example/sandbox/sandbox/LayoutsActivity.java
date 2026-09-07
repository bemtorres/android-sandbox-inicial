package com.example.sandbox.sandbox;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sandbox.R;
import com.example.sandbox.utils.LoggerUtils;

/**
 * Lab 8 — Layouts: ConstraintLayout solo, Linear vertical/horizontal, TableLayout.
 * Cada sección explica operación interna en la card superior.
 * No hay lógica, solo visualización de cómo cada layout posiciona hijos.
 */
public class LayoutsActivity extends AppCompatActivity {
    private static final String TAG = "SANDBOX_LAYOUTS";
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layouts);
        LoggerUtils.d(TAG, "Layouts lab: Constraint vs Linear vs Table mostrado");
    }
}
