package com.example.sandbox.sandbox;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sandbox.R;
import com.example.sandbox.utils.LoggerUtils;

public class WebViewActivity extends AppCompatActivity {
    private static final String TAG="SANDBOX_WEBVIEW";
    private WebView webView;
    private ProgressBar progress;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        webView=findViewById(R.id.webView);
        progress=findViewById(R.id.wvProgress);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override public void onPageStarted(WebView view, String url, Bitmap favicon){
                progress.setVisibility(View.VISIBLE);
                LoggerUtils.d(TAG,"onPageStarted: "+url);
            }
            @Override public void onPageFinished(WebView view, String url){
                progress.setVisibility(View.GONE);
                LoggerUtils.i(TAG,"onPageFinished: "+url);
            }
        });

        findViewById(R.id.btnLoad).setOnClickListener(v->{
            String url="https://developer.android.com";
            webView.loadUrl(url);
            Toast.makeText(this,getString(R.string.acwebv_toast_loading, url),Toast.LENGTH_SHORT).show();
            LoggerUtils.i(TAG,"loadUrl: "+url);
        });
        findViewById(R.id.btnBack).setOnClickListener(v->{
            if(webView.canGoBack()){ webView.goBack(); LoggerUtils.d(TAG,"WebView goBack"); }
            else Toast.makeText(this,getString(R.string.common_msg_no_history),Toast.LENGTH_SHORT).show();
        });
        LoggerUtils.d(TAG,"WebView lab listo");
    }

    @Override public void onBackPressed(){
        if(webView.canGoBack()) webView.goBack();
        else super.onBackPressed();
    }
}
