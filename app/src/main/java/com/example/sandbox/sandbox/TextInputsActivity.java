package com.example.sandbox.sandbox;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sandbox.R;
import com.example.sandbox.utils.LoggerUtils;
import com.example.sandbox.utils.ValidationUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class TextInputsActivity extends AppCompatActivity {
    private static final String TAG = "SANDBOX_INPUTS";
    private TextInputLayout tilPlain, tilEmail, tilPhone, tilPostal, tilPass, tilPin;
    private TextInputEditText etPlain, etEmail, etPhone, etPostal, etPass, etPin;
    private TextView tvPreview;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_inputs);
        bind();
        findViewById(R.id.btnValidate).setOnClickListener(v -> validate());
        findViewById(R.id.btnClear).setOnClickListener(v -> clear());
        LoggerUtils.d(TAG, "TextInputs lab listo");
    }

    private void bind(){
        tilPlain=findViewById(R.id.tilPlain); tilEmail=findViewById(R.id.tilEmail);
        tilPhone=findViewById(R.id.tilPhone); tilPostal=findViewById(R.id.tilPostal);
        tilPass=findViewById(R.id.tilPass); tilPin=findViewById(R.id.tilPin);
        etPlain=findViewById(R.id.etPlain); etEmail=findViewById(R.id.etEmail);
        etPhone=findViewById(R.id.etPhone); etPostal=findViewById(R.id.etPostal);
        etPass=findViewById(R.id.etPass); etPin=findViewById(R.id.etPin);
        tvPreview=findViewById(R.id.tvPreview);
    }

    private void validate(){
        String plain=t(etPlain), email=t(etEmail), phone=t(etPhone), postal=t(etPostal), pass=t(etPass), pin=t(etPin);
        boolean ok=true;

        if(!ValidationUtils.isRequired(plain)){ tilPlain.setError(getString(R.string.common_err_required)); ok=false; } else tilPlain.setError(null);
        if(!ValidationUtils.isRequired(email)){ tilEmail.setError(getString(R.string.common_err_required)); ok=false; } else if(!ValidationUtils.isEmail(email)){ tilEmail.setError(getString(R.string.common_err_email)); ok=false; } else tilEmail.setError(null);
        if(!ValidationUtils.isRequired(phone)){ tilPhone.setError(getString(R.string.common_err_required)); ok=false; } else if(!ValidationUtils.isPhone(phone)){ tilPhone.setError(getString(R.string.common_err_phone)); ok=false; } else tilPhone.setError(null);
        if(!ValidationUtils.isRequired(postal)){ tilPostal.setError(getString(R.string.common_err_required)); ok=false; } else if(!ValidationUtils.isPostalCode(postal)){ tilPostal.setError(getString(R.string.common_err_postal)); ok=false; } else tilPostal.setError(null);
        if(!ValidationUtils.isRequired(pass)){ tilPass.setError(getString(R.string.common_err_required)); ok=false; } else if(!ValidationUtils.isPassword(pass)){ tilPass.setError(getString(R.string.common_err_pass_short)); ok=false; } else tilPass.setError(null);
        if(!ValidationUtils.isRequired(pin)){ tilPin.setError(getString(R.string.common_err_required)); ok=false; } else if(!ValidationUtils.isNumericPassword(pin)){ tilPin.setError(getString(R.string.common_err_pass_numeric)); ok=false; } else tilPin.setError(null);

        if(!ok){
            Toast.makeText(this,getString(R.string.common_msg_correct_errors),Toast.LENGTH_SHORT).show();
            LoggerUtils.e(TAG,"Validación Inputs fallida");
            tvPreview.setText(getString(R.string.actinp_preview_error));
            return;
        }
        String msg=getString(R.string.actinp_preview_ok, plain, email, phone);
        tvPreview.setText(msg);
        Toast.makeText(this,getString(R.string.common_toast_validation_ok),Toast.LENGTH_SHORT).show();
        LoggerUtils.i(TAG,"Inputs OK: "+msg);
    }

    private void clear(){
        etPlain.setText(""); etEmail.setText(""); etPhone.setText(""); etPostal.setText(""); etPass.setText(""); etPin.setText("");
        tilPlain.setError(null); tilEmail.setError(null); tilPhone.setError(null); tilPostal.setError(null); tilPass.setError(null); tilPin.setError(null);
        tvPreview.setText(getString(R.string.actinp_preview));
        LoggerUtils.d(TAG,"Campos limpiados");
    }
    private String t(TextInputEditText et){ return et.getText()!=null?et.getText().toString().trim():""; }
}
