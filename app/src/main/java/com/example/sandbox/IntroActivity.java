package com.example.sandbox;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sandbox.utils.LoggerUtils;
import com.example.sandbox.utils.Prefs;

/**
 * INTRO - mensaje de bienvenida después de login+registro.
 * Muestra cómo pasar datos vía Intent y SharedPreferences y mostrar en TextView.
 */
public class IntroActivity extends AppCompatActivity {
    private static final String TAG = "SANDBOX_INTRO";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");
        if (email == null) email = Prefs.getUser(this);
        if (name == null || name.isEmpty()) name = email;

        TextView tvUser = findViewById(R.id.tvUser);
        tvUser.setText(getString(R.string.common_msg_hello, name));

        LoggerUtils.i(TAG, "Bienvenida mostrada a: " + name + " (" + email + ")");

        findViewById(R.id.btnGoHome).setOnClickListener(v -> {
            LoggerUtils.d(TAG, "Click Ir al Home -> startActivity HomeActivity");
            startActivity(new Intent(this, HomeActivity.class));
        });
    }
}
