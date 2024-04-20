package com.demo.example;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class WebActivity extends AppCompatActivity {
    public static String Privacy_link = "https://www.google.com/";
    ImageView imgback;
    private WebView webPrivacyPolicy;

    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_web);
        this.webPrivacyPolicy = (WebView) findViewById(R.id.wvPrivacyPolicy);
        this.imgback = (ImageView) findViewById(R.id.imgBack);
        WebSettings settings = this.webPrivacyPolicy.getSettings();
        settings.setBuiltInZoomControls(true);
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        this.imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebActivity.this.onBackPressed();
            }
        });
        this.webPrivacyPolicy.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                if (str.startsWith("http:") || str.startsWith("https:")) {
                    return false;
                }
                WebActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
                return true;
            }

            @Override
            public void onReceivedError(WebView webView, int i, String str, String str2) {
                Toast.makeText(WebActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });
        this.webPrivacyPolicy.loadUrl(Privacy_link);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
