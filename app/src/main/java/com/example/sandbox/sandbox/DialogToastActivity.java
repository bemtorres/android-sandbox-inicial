package com.example.sandbox.sandbox;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sandbox.R;
import com.example.sandbox.utils.LoggerUtils;

public class DialogToastActivity extends AppCompatActivity {
    private static final String TAG="SANDBOX_DIALOG";
    private TextView tvLog;
    private final String[] items={"Java","Kotlin","Python"};

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_toast);
        tvLog=findViewById(R.id.tvLog);

        findViewById(R.id.btnToastShort).setOnClickListener(v->{
            Toast.makeText(this,getString(R.string.acdito_toast_short),Toast.LENGTH_SHORT).show();
            appendLog(getString(R.string.acdito_log_short)); LoggerUtils.d(TAG,"Toast short");
        });
        findViewById(R.id.btnToastLong).setOnClickListener(v->{
            Toast.makeText(this,getString(R.string.acdito_toast_long),Toast.LENGTH_LONG).show();
            appendLog(getString(R.string.acdito_log_long)); LoggerUtils.d(TAG,"Toast long");
        });
        findViewById(R.id.btnAlert).setOnClickListener(v-> showConfirmDialog());
        findViewById(R.id.btnAlertList).setOnClickListener(v-> showListDialog());
        findViewById(R.id.btnLoggerDemo).setOnClickListener(v->{
            LoggerUtils.demo(TAG);
            appendLog(getString(R.string.acdito_log_levels));
            Toast.makeText(this,getString(R.string.acdito_toast_logs),Toast.LENGTH_SHORT).show();
        });
    }

    private void showConfirmDialog(){
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.acdito_dialog_title))
                .setMessage(getString(R.string.acdito_dialog_msg))
                .setPositiveButton(getString(R.string.acdito_dialog_yes),(d,w)->{ appendLog(getString(R.string.acdito_log_yes)); Toast.makeText(this,getString(R.string.acdito_toast_yes),Toast.LENGTH_SHORT).show(); LoggerUtils.i(TAG,"Dialog positive"); })
                .setNegativeButton(getString(R.string.acdito_dialog_no),(d,w)->{ appendLog(getString(R.string.acdito_log_no)); LoggerUtils.w(TAG,"Dialog negative"); })
                .setNeutralButton(getString(R.string.acdito_dialog_cancel),null)
                .show();
        LoggerUtils.d(TAG,"AlertDialog mostrado");
    }

    private void showListDialog(){
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.acdito_dialog_list_title))
                .setItems(items,(d,which)->{
                    String sel=items[which];
                    appendLog(getString(R.string.acdito_log_list, sel));
                    Toast.makeText(this,getString(R.string.acdito_toast_chosen, sel),Toast.LENGTH_SHORT).show();
                    LoggerUtils.i(TAG,"Dialog lista: "+sel);
                })
                .show();
    }

    private void appendLog(String msg){
        tvLog.append(getString(R.string.acdito_log_append, msg));
    }
}
