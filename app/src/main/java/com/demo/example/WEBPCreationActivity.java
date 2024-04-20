package com.demo.example;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.example.adapters.Webfileadapter;
import com.demo.example.utils.AllAdsKey;
import com.demo.example.utils.FeedWebp;

import java.io.File;
import java.util.ArrayList;



public class WEBPCreationActivity extends AppCompatActivity {
    Context context;
    ImageView empty_view;
    private ArrayList<FeedWebp> feedWebps;
    private RecyclerView mRecylerWeb;
    Toolbar toolbar;
    private Webfileadapter webfileadapter;


    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_w_e_b_p_creation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.toolbar = toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.context = this;
        this.mRecylerWeb = (RecyclerView) findViewById(R.id.recylerWeb);
        this.empty_view = (ImageView) findViewById(R.id.empty_view);
        this.feedWebps = new ArrayList<>();
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/Image Compressor/Webp Image");
        if (file.exists()) {
            this.mRecylerWeb.setVisibility(View.VISIBLE);
            this.empty_view.setVisibility(View.GONE);
            String[] list = file.list();
            ArrayList arrayList = new ArrayList();
            for (String str : file.list()) {
                arrayList.add(str);
            }
            for (String str2 : list) {
                FeedWebp feedWebp = new FeedWebp();
                feedWebp.setMyfiles(str2);
                this.feedWebps.add(feedWebp);
            }
            this.mRecylerWeb.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            Webfileadapter webfileadapter = new Webfileadapter(this, this.feedWebps);
            this.webfileadapter = webfileadapter;
            this.mRecylerWeb.setAdapter(webfileadapter);
            return;
        }
        this.mRecylerWeb.setVisibility(View.GONE);
        this.empty_view.setVisibility(View.VISIBLE);
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
