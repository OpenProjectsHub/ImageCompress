package com.demo.example;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MyCreationActivity extends AppCompatActivity {
    FrameLayout BannerContainer;
    ImageView banner;
    public String download_folder_path;
    LinearLayout lin_pdf_create;
    LinearLayout lin_png_create;
    LinearLayout lin_webp_create;


    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_my_creation);


        AdAdmob adAdmob = new AdAdmob(this);
        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.bannerAd), this);
        adAdmob.FullscreenAd(this);

        this.lin_png_create = (LinearLayout) findViewById(R.id.lin_png_create);
        this.lin_webp_create = (LinearLayout) findViewById(R.id.lin_webp_create);
        this.lin_pdf_create = (LinearLayout) findViewById(R.id.lin_pdf_create);
        GetPath();
        this.lin_png_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyCreationActivity.this, JPGCreationActivity.class);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                MyCreationActivity.this.startActivity(intent);
            }
        });
        this.lin_webp_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyCreationActivity.this, WEBPCreationActivity.class);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                MyCreationActivity.this.startActivity(intent);
            }
        });
        this.lin_pdf_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyCreationActivity.this, PDFCreationActivity.class);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.putExtra("path", MyCreationActivity.this.download_folder_path + "/");
                MyCreationActivity.this.startActivity(intent);
            }
        });
    }

    private void GetPath() {
        this.download_folder_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/Image Compressor/Image Compressor";
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
