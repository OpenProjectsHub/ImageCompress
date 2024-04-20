package com.demo.example;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;

import java.util.ArrayList;


public class ImageResizerActivity extends AppCompatActivity {
    FrameLayout BannerContainer;
    ImageView banner;
    private ArrayList<Image> images;
    LinearLayout lin_multiple;
    LinearLayout lin_single;


    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_image_resizer);
        AdAdmob adAdmob = new AdAdmob(this);
        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.bannerAd), this);
        adAdmob.FullscreenAd(this);
        this.lin_single = (LinearLayout) findViewById(R.id.lin_single);
        this.lin_multiple = (LinearLayout) findViewById(R.id.lin_multiple);
        this.lin_single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageResizerActivity.this.singleImagePicker();
            }
        });
        this.lin_multiple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageResizerActivity.this.multipleImagePicker();
            }
        });
    }

    public void singleImagePicker() {
        ImagePicker.with(this).setToolbarColor("#282A3B").setStatusBarColor("#6F7A92").setToolbarTextColor("#FFFFFF").setToolbarIconColor("#FFFFFF").setProgressBarColor("#ff5d3b").setBackgroundColor("#ffffff").setMultipleMode(true).setFolderMode(true).setShowCamera(false).setFolderTitle("Choose Image").setLimitMessage("Select only 1 photo").setMaxSize(1).setSavePath("Photo Compressor").setAlwaysShowDoneButton(false).setRequestCode(100).setKeepScreenOn(true).start();
    }

    public void multipleImagePicker() {
        ImagePicker.with(this).setToolbarColor("#282A3B").setStatusBarColor("#6F7A92").setToolbarTextColor("#FFFFFF").setToolbarIconColor("#FFFFFF").setProgressBarColor("#ff5d3b").setBackgroundColor("#ffffff").setMultipleMode(true).setFolderMode(true).setShowCamera(false).setFolderTitle("Choose Images").setImageTitle("Galleries").setDoneTitle("Done").setLimitMessage("You have reached selection limit").setMaxSize(10).setSavePath("Image Resizer").setAlwaysShowDoneButton(false).setRequestCode(100).setKeepScreenOn(true).start();
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 100 && i2 == -1 && intent != null) {
            this.images = intent.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
            Intent intent2 = new Intent(this, ImageCompressorActivity.class);
            intent2.putParcelableArrayListExtra("selectedimages", this.images);
            startActivity(intent2);
        }
    }
}
