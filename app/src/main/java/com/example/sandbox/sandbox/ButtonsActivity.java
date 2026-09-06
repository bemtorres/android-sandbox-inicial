package com.example.sandbox.sandbox;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sandbox.R;
import com.example.sandbox.utils.LoggerUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.ChipGroup;

public class ButtonsActivity extends AppCompatActivity {
    private static final String TAG="SANDBOX_BUTTONS";
    private TextView tvStatus;
    private MaterialButton btnNormal;
    private long lastClick=0;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buttons);
        tvStatus=findViewById(R.id.tvStatus);
        btnNormal=findViewById(R.id.btnNormal);
        MaterialButton btnToggle=findViewById(R.id.btnDisabledDemo);
        ChipGroup chipGroup=findViewById(R.id.chipGroup);

        btnNormal.setOnClickListener(v->{
            long now=System.currentTimeMillis();
            if(now-lastClick<500){ LoggerUtils.w(TAG,"Click ignorado por debounce"); return; }
            lastClick=now;
            tvStatus.setText(getString(R.string.acbutt_status_button, String.valueOf(now)));
            Toast.makeText(this,getString(R.string.acbutt_toast_button),Toast.LENGTH_SHORT).show();
            LoggerUtils.d(TAG,"Button normal click");
        });

        btnToggle.setOnClickListener(v->{
            boolean enabled=btnNormal.isEnabled();
            btnNormal.setEnabled(!enabled);
            tvStatus.setText(getString(enabled?R.string.acbutt_status_disabled:R.string.acbutt_status_enabled));
            Toast.makeText(this,getString(enabled?R.string.acbutt_toast_disabled:R.string.acbutt_toast_enabled),Toast.LENGTH_SHORT).show();
            LoggerUtils.i(TAG,"Toggle enabled="+!enabled);
        });

        findViewById(R.id.imgBtn).setOnClickListener(v->{
            tvStatus.setText(getString(R.string.acbutt_status_image));
            Toast.makeText(this,getString(R.string.acbutt_toast_image),Toast.LENGTH_SHORT).show();
            LoggerUtils.d(TAG,"ImageButton click");
        });

        chipGroup.setOnCheckedStateChangeListener((group, checkedIds)->{
            if(checkedIds.isEmpty()){ tvStatus.setText(getString(R.string.acbutt_status_chip_none)); return; }
            int id=checkedIds.get(0);
            String label="";
            if(id==R.id.chip1) label=getString(R.string.acbutt_chip_android);
            else if(id==R.id.chip2) label=getString(R.string.acbutt_chip_java);
            else if(id==R.id.chip3) label=getString(R.string.acbutt_chip_kotlin);
            else if(id==R.id.chip4) label=getString(R.string.acbutt_chip_compose);
            tvStatus.setText(getString(R.string.acbutt_status_chip, label));
            Toast.makeText(this,getString(R.string.achome_toast_opening, label),Toast.LENGTH_SHORT).show();
            LoggerUtils.i(TAG,"Chip seleccionado: "+label);
        });

        LoggerUtils.demo(TAG);
    }
}
