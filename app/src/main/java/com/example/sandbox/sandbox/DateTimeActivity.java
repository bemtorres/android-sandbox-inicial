package com.example.sandbox.sandbox;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sandbox.R;
import com.example.sandbox.utils.LoggerUtils;
import com.example.sandbox.utils.ValidationUtils;
import java.util.Calendar;
import java.util.Locale;

public class DateTimeActivity extends AppCompatActivity {
    private static final String TAG="SANDBOX_DATETIME";
    private TextView tvDate, tvTime;
    private Calendar selectedDate = Calendar.getInstance();
    private boolean dateChosen=false, timeChosen=false;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time);
        tvDate=findViewById(R.id.tvDate);
        tvTime=findViewById(R.id.tvTime);

        findViewById(R.id.btnPickDate).setOnClickListener(v->showDate());
        findViewById(R.id.btnPickTime).setOnClickListener(v->showTime());
        findViewById(R.id.btnValidateDate).setOnClickListener(v->validate());
        LoggerUtils.d(TAG,"DateTime lab listo");
    }

    private void showDate(){
        Calendar c=Calendar.getInstance();
        new DatePickerDialog(this,(view,year,month,day)->{
            selectedDate.set(year,month,day);
            dateChosen=true;
            String s=String.format(Locale.getDefault(),"%02d/%02d/%04d",day,month+1,year);
            tvDate.setText(getString(R.string.acdati_date_prefix, s));
            LoggerUtils.i(TAG,"Fecha elegida: "+s);
            Toast.makeText(this,getString(R.string.acdati_date_prefix, s),Toast.LENGTH_SHORT).show();
        },c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH)).show();
        LoggerUtils.d(TAG,"DatePickerDialog mostrado");
    }

    private void showTime(){
        Calendar c=Calendar.getInstance();
        new TimePickerDialog(this,(view,hour,minute)->{
            timeChosen=true;
            String s=String.format(Locale.getDefault(),"%02d:%02d",hour,minute);
            tvTime.setText(getString(R.string.acdati_time_prefix, s));
            LoggerUtils.i(TAG,"Hora elegida: "+s);
            Toast.makeText(this,getString(R.string.acdati_time_prefix, s),Toast.LENGTH_SHORT).show();
        },c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),true).show();
        LoggerUtils.d(TAG,"TimePickerDialog mostrado");
    }

    private void validate(){
        if(!dateChosen){ Toast.makeText(this,getString(R.string.acdati_msg_pick_date_first),Toast.LENGTH_SHORT).show(); LoggerUtils.w(TAG,"Validar sin fecha"); return; }
        boolean notFuture=ValidationUtils.isNotFuture(selectedDate.getTimeInMillis());
        if(notFuture){
            Toast.makeText(this,getString(R.string.acdati_msg_date_ok),Toast.LENGTH_SHORT).show();
            LoggerUtils.i(TAG,"Fecha validada OK no futura");
        } else {
            tvDate.setError(getString(R.string.common_err_date_future));
            Toast.makeText(this,getString(R.string.common_err_date_future),Toast.LENGTH_SHORT).show();
            LoggerUtils.e(TAG,"Fecha futura rechazada");
        }
        if (!timeChosen) { LoggerUtils.w(TAG,"Hora aún no elegida"); }
    }
}
