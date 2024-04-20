package com.demo.example;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;

import com.nguyenhoanglam.imagepicker.model.Image;

import com.demo.example.utils.AllAdsKey;
import com.demo.example.mytouch.TouchImageView;
import com.demo.example.utils.Constants;

import java.io.File;
import java.util.ArrayList;



public class ImageShareActivity extends AppCompatActivity {
    private LinearLayout btnvisitAlbum;
    public CompressedImageAdapter compressedImageAdapter;
    public ArrayList<Image> compressedImagesList;
    Context context;
    public String currentImageUri;
    private ViewPager imagePager;
    private LinearLayout llWhatsApp;
    private LinearLayout llfacebook;
    private LinearLayout llinstagram;
    private LinearLayout llmore;
    public int photoPosition;
    private Uri photoURI;
    String str = " Created By : ";
    String str2 = "android.intent.extra.TEXT";
    String str3 = "image/*";
    String str4 = "android.intent.action.SEND";
    String str5;
    String str6 = "android.intent.extra.STREAM";
    Toolbar toolbar;
    public TextView tvPhotoNo;


    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_image_share);


        AdAdmob adAdmob = new AdAdmob(this);
        adAdmob.FullscreenAd(this);

        str5 = getPackageName() + ".fileprovider";

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.toolbar = toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        this.tvPhotoNo = (TextView) findViewById(R.id.tvPhotoNo);
        this.imagePager = (ViewPager) findViewById(R.id.imagePager);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.lin_myalbum);
        this.btnvisitAlbum = linearLayout;
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ImageShareActivity.this, JPGCreationActivity.class);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                ImageShareActivity.this.startActivity(intent);
            }
        });
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.llWhatsApp);
        this.llWhatsApp = linearLayout2;
        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageShareActivity imageShareActivity = ImageShareActivity.this;
                imageShareActivity.photoURI = FileProvider.getUriForFile(imageShareActivity, imageShareActivity.str5, new File(ImageShareActivity.this.currentImageUri));
                Intent intent = new Intent(ImageShareActivity.this.str4);
                intent.setType(ImageShareActivity.this.str3);
                intent.putExtra(ImageShareActivity.this.str2, getString(R.string.app_name) + ImageShareActivity.this.str + "https://play.google.com/store/apps/details?id=" + getPackageName());
                if (Build.VERSION.SDK_INT >= 24) {
                    intent.putExtra(ImageShareActivity.this.str6, ImageShareActivity.this.photoURI);
                } else {
                    intent.putExtra(ImageShareActivity.this.str6, Uri.fromFile(new File(ImageShareActivity.this.currentImageUri)));
                }
                try {
                    intent.setPackage("com.whatsapp");
                    ImageShareActivity.this.startActivity(intent);
                } catch (Exception unused) {
                    Toast.makeText(ImageShareActivity.this.getApplicationContext(), "Facebook doesn't installed", Toast.LENGTH_LONG).show();
                }
            }
        });
        LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.llfacebook);
        this.llfacebook = linearLayout3;
        linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageShareActivity imageShareActivity = ImageShareActivity.this;
                imageShareActivity.photoURI = FileProvider.getUriForFile(imageShareActivity, imageShareActivity.str5, new File(ImageShareActivity.this.currentImageUri));
                Intent intent = new Intent(ImageShareActivity.this.str4);
                intent.setType(ImageShareActivity.this.str3);
                intent.putExtra(ImageShareActivity.this.str2, getString(R.string.app_name) + ImageShareActivity.this.str + "https://play.google.com/store/apps/details?id=" + getPackageName());
                if (Build.VERSION.SDK_INT >= 24) {
                    intent.putExtra(ImageShareActivity.this.str6, ImageShareActivity.this.photoURI);
                } else {
                    intent.putExtra(ImageShareActivity.this.str6, Uri.fromFile(new File(ImageShareActivity.this.currentImageUri)));
                }
                try {
                    intent.setPackage("com.facebook.katana");
                    ImageShareActivity.this.startActivity(intent);
                } catch (Exception unused) {
                    Toast.makeText(ImageShareActivity.this.getApplicationContext(), "Facebook doesn't installed", Toast.LENGTH_LONG).show();
                }
            }
        });
        LinearLayout linearLayout4 = (LinearLayout) findViewById(R.id.llinstagram);
        this.llinstagram = linearLayout4;
        linearLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageShareActivity imageShareActivity = ImageShareActivity.this;
                imageShareActivity.photoURI = FileProvider.getUriForFile(imageShareActivity, imageShareActivity.str5, new File(ImageShareActivity.this.currentImageUri));
                Intent intent = new Intent(ImageShareActivity.this.str4);
                intent.setType(ImageShareActivity.this.str3);
                intent.putExtra(ImageShareActivity.this.str2, getString(R.string.app_name) + ImageShareActivity.this.str + "https://play.google.com/store/apps/details?id=" + getPackageName());
                if (Build.VERSION.SDK_INT >= 24) {
                    intent.putExtra(ImageShareActivity.this.str6, ImageShareActivity.this.photoURI);
                } else {
                    intent.putExtra(ImageShareActivity.this.str6, Uri.fromFile(new File(ImageShareActivity.this.currentImageUri)));
                }
                try {
                    intent.setPackage("com.instagram.android");
                    ImageShareActivity.this.startActivity(intent);
                } catch (Exception unused) {
                    Toast.makeText(ImageShareActivity.this.getApplicationContext(), "Facebook doesn't installed", Toast.LENGTH_LONG).show();
                }
            }
        });
        LinearLayout linearLayout5 = (LinearLayout) findViewById(R.id.llmore);
        this.llmore = linearLayout5;
        linearLayout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ImageShareActivity.this.str4);
                intent.setType(ImageShareActivity.this.str3);
                intent.putExtra(ImageShareActivity.this.str2, getString(R.string.app_name) + "\nCreated By : https://play.google.com/store/apps/details?id=" + getPackageName());
                intent.putExtra(ImageShareActivity.this.str6, FileProvider.getUriForFile(ImageShareActivity.this.getApplicationContext(), ImageShareActivity.this.str5, new File(ImageShareActivity.this.currentImageUri)));
                ImageShareActivity.this.startActivity(Intent.createChooser(intent, "Share Image using"));
            }
        });
        this.compressedImagesList = getIntent().getParcelableArrayListExtra("compressedImagesList");
        CompressedImageAdapter compressedImageAdapter = new CompressedImageAdapter(this, this.compressedImagesList);
        this.compressedImageAdapter = compressedImageAdapter;
        this.imagePager.setAdapter(compressedImageAdapter);
        this.imagePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int i) {
            }

            @Override
            public void onPageSelected(int i) {
            }

            @Override
            public void onPageScrolled(int i, float f, int i2) {
                ImageShareActivity.this.photoPosition = i;
                TextView textView = ImageShareActivity.this.tvPhotoNo;
                textView.setText("Images: " + String.format("%d/%d", Integer.valueOf(ImageShareActivity.this.photoPosition + 1), Integer.valueOf(ImageShareActivity.this.compressedImageAdapter.getCount())));
                ImageShareActivity imageShareActivity = ImageShareActivity.this;
                imageShareActivity.currentImageUri = imageShareActivity.compressedImagesList.get(i).getPath();
            }
        });
    }



    public class CompressedImageAdapter extends PagerAdapter {
        private Activity _activity;
        private TouchImageView imgDisplay;
        private ArrayList<Image> list;
        private TextView tvPhotoSize;

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        CompressedImageAdapter(Activity activity, ArrayList<Image> arrayList) {
            this._activity = activity;
            this.list = arrayList;
        }

        @Override
        public int getCount() {
            return this.list.size();
        }

        @Override
        public Object instantiateItem(ViewGroup viewGroup, int i) {
            View inflate = ((LayoutInflater) this._activity.getSystemService("layout_inflater")).inflate(R.layout.item_images_share, viewGroup, false);
            this.imgDisplay = (TouchImageView) inflate.findViewById(R.id.imgDisplay);
            this.tvPhotoSize = (TextView) inflate.findViewById(R.id.tvPhotoSize);
            try {
                Glide.with(this._activity).load(this.list.get(i).getPath()).placeholder((int) R.drawable.loading).into(this.imgDisplay);
                this.tvPhotoSize.setText(Constants.getFileSize(new File(String.valueOf(this.list.get(i).getPath())).length()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            viewGroup.addView(inflate);
            return inflate;
        }

        @Override
        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView((RelativeLayout) obj);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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
                finish();
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
