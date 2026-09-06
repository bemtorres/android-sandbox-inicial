package com.example.sandbox;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sandbox.utils.LoggerUtils;
import com.example.sandbox.utils.Prefs;
import com.example.sandbox.utils.ValidationUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * LOGIN - punto de entrada (launcher).
 * 1. TextInputLayout + TextInputEditText (inputType email/password)
 * 2. MaterialButton onClick -> validar con ValidationUtils
 * 3. Si error -> til.setError(), Toast, Log.e
 * 4. Si ok -> Prefs.saveUser + Toast + Log.i + startActivity(Register)
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "SANDBOX_LOGIN";
    private TextInputLayout tilEmail, tilPass;
    private TextInputEditText etEmail, etPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tilEmail = findViewById(R.id.tilEmail);
        tilPass = findViewById(R.id.tilPass);
        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPass);
        MaterialButton btnLogin = findViewById(R.id.btnLogin);

        String saved = Prefs.getUser(this);
        if (!saved.isEmpty()) etEmail.setText(saved);

        btnLogin.setOnClickListener(v -> attemptLogin());
        findViewById(R.id.tvGoRegister).setOnClickListener(v -> {
            LoggerUtils.d(TAG, "Navegando a Registro sin validar");
            startActivity(new Intent(this, RegisterActivity.class));
        });

        LoggerUtils.demo(TAG);
    }

    private void attemptLogin() {
        String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
        String pass = etPass.getText() != null ? etPass.getText().toString() : "";

        tilEmail.setError(null);
        tilPass.setError(null);

        boolean ok = true;

        if (!ValidationUtils.isRequired(email)) {
            tilEmail.setError(getString(R.string.common_err_required));
            ok = false;
            LoggerUtils.w(TAG, "Email vacío");
        } else if (!ValidationUtils.isEmail(email)) {
            tilEmail.setError(getString(R.string.common_err_email));
            ok = false;
            LoggerUtils.w(TAG, "Email formato inválido: " + email);
        }

        if (!ValidationUtils.isRequired(pass)) {
            tilPass.setError(getString(R.string.common_err_required));
            ok = false;
        } else if (!ValidationUtils.isPassword(pass)) {
            tilPass.setError(getString(R.string.common_err_pass_short));
            ok = false;
            LoggerUtils.w(TAG, "Password corta");
        }

        if (!ok) {
            Toast.makeText(this, getString(R.string.common_msg_correct_errors), Toast.LENGTH_SHORT).show();
            LoggerUtils.e(TAG, "Login validación fallida");
            return;
        }

        Prefs.saveUser(this, email);
        LoggerUtils.i(TAG, "Login OK -> guardado en Prefs: " + email);
        Toast.makeText(this, R.string.common_msg_login_ok, Toast.LENGTH_SHORT).show();

        Intent i = new Intent(this, RegisterActivity.class);
        i.putExtra("email", email);
        startActivity(i);
    }
}
