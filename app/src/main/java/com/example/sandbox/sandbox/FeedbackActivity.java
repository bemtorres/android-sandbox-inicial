package com.example.sandbox.sandbox;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sandbox.R;
import com.example.sandbox.utils.LoggerUtils;

public class FeedbackActivity extends AppCompatActivity {
    private static final String TAG="SANDBOX_FEEDBACK";
    private ProgressBar progressHorizontal, progressIndeterminate;
    private RatingBar ratingBar;
    private TextView tvRating, tvSpinnerResult;
    private Handler handler=new Handler(Looper.getMainLooper());
    private Runnable progressRunnable;
    private int progress=30;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        progressHorizontal=findViewById(R.id.progressHorizontal);
        progressIndeterminate=findViewById(R.id.progressIndeterminate);
        ratingBar=findViewById(R.id.ratingBar);
        tvRating=findViewById(R.id.tvRating);
        tvSpinnerResult=findViewById(R.id.tvSpinnerResult);
        Spinner spinner=findViewById(R.id.spinner);

        progressHorizontal.setProgress(progress);

        findViewById(R.id.btnStartProgress).setOnClickListener(v->startProgress());
        findViewById(R.id.btnResetProgress).setOnClickListener(v->resetProgress());

        ratingBar.setOnRatingBarChangeListener((bar, rating, fromUser)->{
            tvRating.setText(getString(R.string.acfeed_rating, String.valueOf(rating)));
            if(fromUser){ Toast.makeText(this,getString(R.string.acfeed_toast_rating, String.valueOf(rating)),Toast.LENGTH_SHORT).show(); LoggerUtils.i(TAG,"Rating="+rating); }
        });

        String[] items={getString(R.string.acfeed_spinner_hint),"Java","Kotlin","Python","Dart"};
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override public void onItemSelected(AdapterView<?> p, View v, int pos, long id){
                String sel=items[pos];
                tvSpinnerResult.setText(getString(R.string.acfeed_spinner_selected, sel));
                if(pos!=0){ LoggerUtils.d(TAG,"Spinner seleccionado: "+sel); }
            }
            @Override public void onNothingSelected(AdapterView<?> p){}
        });
        LoggerUtils.d(TAG,"Feedback lab listo");
    }

    private void startProgress(){
        progressIndeterminate.setVisibility(View.VISIBLE);
        handler.removeCallbacks(progressRunnable);
        progressRunnable=new Runnable(){
            @Override public void run(){
                if(progress>=100){
                    progressIndeterminate.setVisibility(View.GONE);
                    Toast.makeText(FeedbackActivity.this,getString(R.string.acfeed_toast_complete),Toast.LENGTH_SHORT).show();
                    LoggerUtils.i(TAG,"Progress 100% completado");
                    return;
                }
                progress+=10;
                progressHorizontal.setProgress(progress);
                LoggerUtils.d(TAG,"Progress="+progress);
                handler.postDelayed(this,400);
            }
        };
        handler.post(progressRunnable);
        LoggerUtils.i(TAG,"Iniciando Progress simulado");
    }

    private void resetProgress(){
        handler.removeCallbacks(progressRunnable);
        progress=0;
        progressHorizontal.setProgress(0);
        progressIndeterminate.setVisibility(View.GONE);
        LoggerUtils.d(TAG,"Progress reseteado");
    }

    @Override protected void onDestroy(){ super.onDestroy(); handler.removeCallbacks(progressRunnable); }
}
