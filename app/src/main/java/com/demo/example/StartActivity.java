package com.demo.example;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.demo.example.utils.AllAdsKey;


public class StartActivity extends AppCompatActivity {
    public LinearLayout btn_start;
    Context context;

    public LinearLayout privacy;
    public LinearLayout share;
    Toolbar toolbar;


    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.start_activity);


        AdAdmob adAdmob = new AdAdmob(this);
        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.bannerAd), this);



        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(Color.parseColor("#8BDAAC"));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.toolbar = toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.context = this;
        this.btn_start = (LinearLayout) findViewById(R.id.btn_start);
        this.share = (LinearLayout) findViewById(R.id.share);
        this.privacy = (LinearLayout) findViewById(R.id.privacy);
        this.btn_start.startAnimation(AnimationUtils.loadAnimation(this, R.anim.blink_anim));

        this.btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                StartActivity.this.startActivity(intent);
            }
        });
        this.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllAdsKey.share(StartActivity.this);
            }
        });
        this.privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AllAdsKey.isOnline(StartActivity.this)) {
                    Intent intent = new Intent(StartActivity.this, WebActivity.class);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    StartActivity.this.startActivity(intent);
                    return;
                }
                Toast makeText = Toast.makeText(StartActivity.this.getApplicationContext(), AllAdsKey.enableInternet, Toast.LENGTH_LONG);
                makeText.setGravity(17, 0, 0);
                makeText.show();
            }
        });
    }


    @Override
    public void onBackPressed() {

        finishAffinity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:

                finishAffinity();
                return true;

            case R.id.rate:
                if (AllAdsKey.isOnline(this)) {
                    AllAdsKey.Rate(this);
                } else {
                    Toast makeText2 = Toast.makeText(getApplicationContext(), AllAdsKey.enableInternet, Toast.LENGTH_LONG);
                    makeText2.setGravity(17, 0, 0);
                    makeText2.show();
                }
                return true;
            case R.id.share:
                AllAdsKey.share(this);
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }
}
