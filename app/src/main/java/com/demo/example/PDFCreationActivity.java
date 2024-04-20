package com.demo.example;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itextpdf.text.xml.xmp.PdfSchema;

import com.demo.example.adapters.PDFListAdapter;
import com.demo.example.utils.AllAdsKey;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class PDFCreationActivity extends AppCompatActivity {
    private PDFListAdapter adapter;
    Context context;
    private String download_folder_path;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView pdf_recycle;
    Toolbar toolbar;
    public final String[] EXTERNAL_PERMS = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"};
    private List<File> pdf_list = new ArrayList();


    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_p_d_f_creation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.toolbar = toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.download_folder_path = getIntent().getStringExtra("path");
        requestForPermission();
        this.pdf_recycle = (RecyclerView) findViewById(R.id.recyc_file);
        scanfolder();
        Collections.reverse(this.pdf_list);
        Log.i("hstry_frag", "reverse_size=" + this.pdf_list.size());
        this.adapter = new PDFListAdapter(this, this.pdf_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        this.linearLayoutManager = linearLayoutManager;
        linearLayoutManager.canScrollVertically();
        this.pdf_recycle.setLayoutManager(this.linearLayoutManager);
        this.pdf_recycle.setItemAnimator(new DefaultItemAnimator());
        this.pdf_recycle.setAdapter(this.adapter);
    }

    public boolean requestForPermission() {
        if (Build.VERSION.SDK_INT < 23 || canAccessExternalSd()) {
            return true;
        }
        requestPermissions(this.EXTERNAL_PERMS, 138);
        return false;
    }

    public boolean canAccessExternalSd() {
        return hasPermission("android.permission.WRITE_EXTERNAL_STORAGE");
    }

    private boolean hasPermission(String str) {
        return ContextCompat.checkSelfPermission(this, str) == 0;
    }

    private void scanfolder() {
        File[] listFiles = new File(this.download_folder_path).listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                if (file.isFile()) {
                    String path = file.getPath();
                    if (path.substring(path.lastIndexOf(".") + 1).equalsIgnoreCase(PdfSchema.DEFAULT_XPATH_ID)) {
                        this.pdf_list.add(file);
                    }
                }
            }
        }
        Log.i("hstry_frag", "pdf_list_size=" + this.pdf_list.size());
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
