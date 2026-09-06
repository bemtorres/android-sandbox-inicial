package com.example.sandbox.sandbox;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sandbox.R;
import com.example.sandbox.utils.LoggerUtils;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class SelectionActivity extends AppCompatActivity {
    private static final String TAG="SANDBOX_SELECTION";
    private CheckBox cbTerms, cbNewsletter;
    private RadioGroup rg;
    private SwitchMaterial swNotif, swDark;
    private TextView tvResult;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        cbTerms=findViewById(R.id.cbTerms);
        cbNewsletter=findViewById(R.id.cbNewsletter);
        rg=findViewById(R.id.rgOptions);
        swNotif=findViewById(R.id.switchNotif);
        swDark=findViewById(R.id.switchDark);
        tvResult=findViewById(R.id.tvSelectionResult);

        cbTerms.setOnCheckedChangeListener((b,c)-> LoggerUtils.d(TAG,"cbTerms="+c));
        cbNewsletter.setOnCheckedChangeListener((b,c)-> LoggerUtils.d(TAG,"cbNewsletter="+c));
        rg.setOnCheckedChangeListener((g,id)-> LoggerUtils.d(TAG,"Radio checkedId="+id));
        swNotif.setOnCheckedChangeListener((b,c)-> LoggerUtils.d(TAG,"switchNotif="+c));
        swDark.setOnCheckedChangeListener((b,c)-> LoggerUtils.d(TAG,"switchDark="+c));

        findViewById(R.id.btnValidateSelection).setOnClickListener(v->validate());
        LoggerUtils.d(TAG,"Selection lab listo");
    }

    private void validate(){
        boolean terms=cbTerms.isChecked();
        boolean newsletter=cbNewsletter.isChecked();
        int radio=rg.getCheckedRadioButtonId();
        boolean notif=swNotif.isChecked();
        boolean dark=swDark.isChecked();

        if(!terms){
            Toast.makeText(this,getString(R.string.acsele_toast_terms),Toast.LENGTH_SHORT).show();
            LoggerUtils.e(TAG,"Validación fallida: términos no aceptados");
            tvResult.setText(getString(R.string.acsele_result_terms_fail));
            return;
        }
        if(radio==-1){
            Toast.makeText(this,getString(R.string.acsele_toast_plan),Toast.LENGTH_SHORT).show();
            LoggerUtils.w(TAG,"Sin radio seleccionado");
            tvResult.setText(getString(R.string.acsele_result_plan_fail));
            return;
        }
        String plan= radio==R.id.rbFree?getString(R.string.acsele_rb_free): radio==R.id.rbPro?getString(R.string.acsele_rb_pro):getString(R.string.acsele_rb_ent);
        // Para el detalle usamos valores cortos
        String res=getString(R.string.acsele_result_detail, plan, newsletter?getString(R.string.acdito_dialog_yes):getString(R.string.acdito_dialog_no), notif?"on":"off", dark?"on":"off");
        tvResult.setText(getString(R.string.acsele_result_ok, res));
        Toast.makeText(this,getString(R.string.acsele_toast_ok),Toast.LENGTH_SHORT).show();
        LoggerUtils.i(TAG,res);
    }
}
