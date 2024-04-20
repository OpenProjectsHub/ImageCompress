package com.demo.example;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.demo.example.utils.CustomViewFlipper;

import java.io.File;


public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;
    FrameLayout BannerContainer;
    ImageView banner;
    public String download_folder_path;
    int[] gallery_grid_Images = {R.drawable.main_image_comp, R.drawable.main_image_conv, R.drawable.main_image_pdf};
    LinearLayout lin_converter;
    LinearLayout lin_creation;
    LinearLayout lin_pdf;
    LinearLayout lin_resize;
    CustomViewFlipper viewFlipper;


    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);


        AdAdmob adAdmob = new AdAdmob(this);
        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.bannerAd), this);


        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(Color.parseColor("#6F7A92"));
        }

        this.viewFlipper = (CustomViewFlipper) findViewById(R.id.flipper);
        int i = 0;
        while (true) {
            int[] iArr = this.gallery_grid_Images;
            if (i >= iArr.length) {
                break;
            }
            setFlipperImage(iArr[i]);
            i++;
        }
        this.lin_resize = (LinearLayout) findViewById(R.id.lin_resize);
        this.lin_converter = (LinearLayout) findViewById(R.id.lin_converter);
        this.lin_pdf = (LinearLayout) findViewById(R.id.lin_pdf);
        this.lin_creation = (LinearLayout) findViewById(R.id.lin_creation);
        if (Build.VERSION.SDK_INT < 23) {
            CreateFolder();
        } else if (checkPermission()) {
            CreateFolder();
        } else {
            requestPermission();
        }
        this.viewFlipper.setAutoStart(true);
        this.viewFlipper.setFlipInterval(3000);
        this.viewFlipper.startFlipping();
        this.viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.left_right));
        this.viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.right_left));
        this.lin_resize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ImageResizerActivity.class);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                MainActivity.this.startActivity(intent);
            }
        });
        this.lin_converter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ImageConverterActivity.class);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                MainActivity.this.startActivity(intent);
            }
        });
        this.lin_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ImageToPDFActivity.class);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.putExtra("path", MainActivity.this.download_folder_path + "/");
                MainActivity.this.startActivity(intent);
            }
        });
        this.lin_creation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MyCreationActivity.class);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                MainActivity.this.startActivity(intent);
            }
        });
    }

    private void setFlipperImage(int i) {
        Log.i("Set Filpper Called", i + "");
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setBackgroundResource(i);
        this.viewFlipper.addView(imageView);
    }

    private void CreateFolder() {
        this.download_folder_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/Image Compressor/Image Compressor";
        File file = new File(this.download_folder_path);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }
    }

    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0 || ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") == 0;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, 1);
    }

    @Override
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 1 && iArr.length > 0 && iArr[0] == 0 && iArr[1] == 0) {
            CreateFolder();
        }
    }


}
