package com.example.sandbox;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sandbox.utils.LoggerUtils;
import com.example.sandbox.utils.Prefs;
import com.example.sandbox.utils.ValidationUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "SANDBOX_REGISTER";
    private TextInputLayout tilName, tilEmail, tilPhone, tilPostal, tilPass, tilPin;
    private TextInputEditText etName, etEmail, etPhone, etPostal, etPass, etPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tilName = findViewById(R.id.tilName);
        tilEmail = findViewById(R.id.tilEmail);
        tilPhone = findViewById(R.id.tilPhone);
        tilPostal = findViewById(R.id.tilPostal);
        tilPass = findViewById(R.id.tilPass);
        tilPin = findViewById(R.id.tilPin);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etPostal = findViewById(R.id.etPostal);
        etPass = findViewById(R.id.etPass);
        etPin = findViewById(R.id.etPin);

        String incoming = getIntent().getStringExtra("email");
        if (incoming == null) { incoming = Prefs.getUser(this); }
        if (incoming != null && !incoming.isEmpty()) { etEmail.setText(incoming); }

        etEmail.addTextChangedListener(new SimpleWatcher() {
            @Override public void afterTextChanged(Editable s) {
                if (ValidationUtils.isEmail(s.toString())) { tilEmail.setError(null); }
            }
        });
        etPostal.addTextChangedListener(new SimpleWatcher() {
            @Override public void afterTextChanged(Editable s) {
                if (ValidationUtils.isPostalCode(s.toString())) { tilPostal.setError(null); }
            }
        });

        findViewById(R.id.btnRegister).setOnClickListener(v -> validateAndContinue());
        findViewById(R.id.tvGoLogin).setOnClickListener(v -> finish());

        LoggerUtils.d(TAG, "RegisterActivity creada");
    }

    private void validateAndContinue() {
        String name = text(etName);
        String email = text(etEmail);
        String phone = text(etPhone);
        String postal = text(etPostal);
        String pass = text(etPass);
        String pin = text(etPin);

        boolean ok = true;
        if (!ValidationUtils.isRequired(name)) { tilName.setError(getString(R.string.common_err_required)); ok=false; }
        else { tilName.setError(null); }

        if (!ValidationUtils.isRequired(email)) { tilEmail.setError(getString(R.string.common_err_required)); ok=false; }
        else if (!ValidationUtils.isEmail(email)) { tilEmail.setError(getString(R.string.common_err_email)); ok=false; }
        else { tilEmail.setError(null); }

        if (!ValidationUtils.isRequired(phone)) { tilPhone.setError(getString(R.string.common_err_required)); ok=false; }
        else if (!ValidationUtils.isPhone(phone)) { tilPhone.setError(getString(R.string.common_err_phone)); ok=false; }
        else { tilPhone.setError(null); }

        if (!ValidationUtils.isRequired(postal)) { tilPostal.setError(getString(R.string.common_err_required)); ok=false; }
        else if (!ValidationUtils.isPostalCode(postal)) { tilPostal.setError(getString(R.string.common_err_postal)); ok=false; }
        else { tilPostal.setError(null); }

        if (!ValidationUtils.isRequired(pass)) { tilPass.setError(getString(R.string.common_err_required)); ok=false; }
        else if (!ValidationUtils.isPassword(pass)) { tilPass.setError(getString(R.string.common_err_pass_short)); ok=false; }
        else { tilPass.setError(null); }

        if (!ValidationUtils.isRequired(pin)) { tilPin.setError(getString(R.string.common_err_required)); ok=false; }
        else if (!ValidationUtils.isNumericPassword(pin)) { tilPin.setError(getString(R.string.common_err_pass_numeric)); ok=false; }
        else { tilPin.setError(null); }

        if (!ok) {
            Toast.makeText(this, getString(R.string.common_msg_check_fields), Toast.LENGTH_SHORT).show();
            LoggerUtils.e(TAG, "Registro fallido validación");
            return;
        }

        Prefs.saveUser(this, email);
        LoggerUtils.i(TAG, "Registro OK: " + name + " / " + email);
        Toast.makeText(this, R.string.common_msg_register_ok, Toast.LENGTH_SHORT).show();

        Intent i = new Intent(this, IntroActivity.class);
        i.putExtra("name", name);
        i.putExtra("email", email);
        startActivity(i);
    }

    private String text(TextInputEditText et) {
        return et.getText() != null ? et.getText().toString().trim() : "";
    }

    abstract static class SimpleWatcher implements TextWatcher {
        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
    }
}
