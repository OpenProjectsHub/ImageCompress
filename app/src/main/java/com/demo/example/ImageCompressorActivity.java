package com.demo.example;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.nguyenhoanglam.imagepicker.helper.ImageHelper;
import com.nguyenhoanglam.imagepicker.model.Image;

import com.demo.example.adapters.ImageAdapter;
import com.demo.example.utils.AllAdsKey;
import com.demo.example.compresser.Compressor;
import com.demo.example.interfaces.ItemClickListener;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;



public class ImageCompressorActivity extends AppCompatActivity implements ItemClickListener {
    private BottomSheetBehavior bottomSheetBehavior;
    BitmapFactory.Options bounds;
    LinearLayout btnCompress;
    Context context;
    private Dialog dialog;
    public EditText etHeight;
    public EditText etWidth;
    public File file;
    String finalPath;
    private ImageView imageView;
    ArrayList<Image> images;
    LinearLayout lin_compressed;
    LinearLayout lin_share;
    private File outputFile;
    public RecyclerView recyclerView;
    private SeekBar sbQuality;
    String theight;
    Toolbar toolbar;
    String twidth;
    private TextView txtFileLocation;
    private TextView txtImageSize;
    public TextView txtQuality;
    private TextView txtRequiredHeightWidth;
    TextView txt_size_compressed;
    private Bitmap.CompressFormat bimapCompressFormat = Bitmap.CompressFormat.WEBP;
    int check = 0;
    ArrayList<Image> compressedImagesList = new ArrayList<>();
    int count = 0;
    public int maxHeight = 816;
    public int maxWidth = 612;
    public int quality = 50;


    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_image_compressor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.toolbar = toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Dialog dialog = new Dialog(this);
        this.dialog = dialog;
        dialog.requestWindowFeature(1);
        this.dialog.setCancelable(false);
        this.dialog.setContentView(R.layout.loading_dialog);
        this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
            }
        });
        this.btnCompress = (LinearLayout) findViewById(R.id.lin_compress);
        this.lin_share = (LinearLayout) findViewById(R.id.lin_share);
        this.lin_compressed = (LinearLayout) findViewById(R.id.lin_compressed);
        this.txtImageSize = (TextView) findViewById(R.id.txt_size);
        this.txtFileLocation = (TextView) findViewById(R.id.txt_location);
        this.txtQuality = (TextView) findViewById(R.id.txt_quality);
        this.txtRequiredHeightWidth = (TextView) findViewById(R.id.txt_required_height_width);
        this.txt_size_compressed = (TextView) findViewById(R.id.txt_size_compressed);
        this.sbQuality = (SeekBar) findViewById(R.id.sb_quality);
        this.etHeight = (EditText) findViewById(R.id.et_height);
        this.etWidth = (EditText) findViewById(R.id.et_width);
        BottomSheetBehavior from = BottomSheetBehavior.from(findViewById(R.id.compress_bottom_sheet));
        this.bottomSheetBehavior = from;
        from.setState(BottomSheetBehavior.STATE_HIDDEN);
        this.lin_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ImageCompressorActivity.this.getApplicationContext(), ImageShareActivity.class);
                intent.putParcelableArrayListExtra("compressedImagesList", ImageCompressorActivity.this.compressedImagesList);
                ImageCompressorActivity.this.startActivity(intent);
                ImageCompressorActivity.this.finish();
            }
        });
        ((LinearLayout) findViewById(R.id.btn_done)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageCompressorActivity.this.bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                ImageCompressorActivity.this.dialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (ImageCompressorActivity.this.check == 2) {
                            for (int i = 0; i < ImageCompressorActivity.this.images.size(); i++) {
                                EditText editText = ImageCompressorActivity.this.etWidth;
                                editText.setText("" + ImageCompressorActivity.this.maxWidth);
                                EditText editText2 = ImageCompressorActivity.this.etHeight;
                                editText2.setText("" + ImageCompressorActivity.this.maxHeight);
                                ImageCompressorActivity.this.file = new File(ImageCompressorActivity.this.images.get(i).getPath());
                                ImageCompressorActivity.this.compressPhoto();
                            }
                            return;
                        }
                        ImageCompressorActivity.this.file = new File(ImageCompressorActivity.this.images.get(0).getPath());
                        ImageCompressorActivity.this.compressPhoto();
                    }
                }, 2500);
            }
        });
        ((LinearLayout) findViewById(R.id.btn_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageCompressorActivity.this.bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });
        this.btnCompress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageCompressorActivity.this.bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
        BitmapFactory.Options options = new BitmapFactory.Options();
        this.bounds = options;
        options.inJustDecodeBounds = true;
        this.imageView = (ImageView) findViewById(R.id.imageView);
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        setQuality();
        saveImageTo();
        ArrayList<Image> parcelableArrayListExtra = getIntent().getParcelableArrayListExtra("selectedimages");
        this.images = parcelableArrayListExtra;
        if (parcelableArrayListExtra.size() == 1) {
            singleView();
            commonView();
            return;
        }
        multipleView();
        commonView();
    }

    private void setQuality() {
        this.sbQuality.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                ImageCompressorActivity.this.quality = i;
                ImageCompressorActivity.this.txtQuality.setText(String.valueOf(ImageCompressorActivity.this.quality) + " %");
                Log.d("MY_DEBUGGING_TAG", "" + ImageCompressorActivity.this.quality);
            }
        });
    }

    private void saveImageTo() {
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
        File file = new File(path, "/Image Compressor");
        if (file.exists()) {
            String absolutePath = file.getAbsolutePath();
            this.finalPath = absolutePath;
            Log.d("MY_DEBUGGING_TAG", absolutePath);
            return;
        }
        file.mkdirs();
        String absolutePath2 = file.getAbsolutePath();
        this.finalPath = absolutePath2;
        Log.d("MY_DEBUGGING_TAG", absolutePath2);
    }

    private void singleView() {
        this.recyclerView.setVisibility(View.GONE);
        String path = this.images.get(0).getPath();
        Glide.with((FragmentActivity) this).load(path).apply((BaseRequestOptions<?>) new RequestOptions().placeholder(R.drawable.loading)).into(this.imageView);
        EditText editText = this.etWidth;
        editText.setText("" + this.maxWidth);
        EditText editText2 = this.etHeight;
        editText2.setText("" + this.maxHeight);
        this.txtImageSize.setText(fileSize(new File(path).length()));
        this.check = 1;
    }

    public String fileSize(long j) {
        if (j <= 0) {
            return "0";
        }
        double d = (double) j;
        int log10 = (int) (Math.log10(d) / Math.log10(1024.0d));
        StringBuilder sb = new StringBuilder();
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.#");
        double pow = Math.pow(1024.0d, (double) log10);
        Double.isNaN(d);
        Double.isNaN(d);
        Double.isNaN(d);
        Double.isNaN(d);
        sb.append(decimalFormat.format(d / pow));
        sb.append(" ");
        sb.append(new String[]{"B", "KB", "MB", "GB", "TB"}[log10]);
        return sb.toString();
    }

    private void multipleView() {
        this.recyclerView.setVisibility(View.VISIBLE);
        this.imageView.setVisibility(View.GONE);
        this.txtImageSize.setVisibility(View.GONE);
        this.etHeight.setVisibility(View.GONE);
        this.etWidth.setVisibility(View.GONE);
        this.txtRequiredHeightWidth.setVisibility(View.GONE);
        ArrayList arrayList = new ArrayList();
        Iterator<Image> it = this.images.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getPath());
        }
        this.check = 2;
        this.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        ImageAdapter imageAdapter = new ImageAdapter(arrayList, this);
        imageAdapter.setClickListener(this);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setAdapter(imageAdapter);
    }

    @Override
    public void onItemClick(View view, final int i) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.remove_menu_popup, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.itm_info:
                        FragmentManager supportFragmentManager = ImageCompressorActivity.this.getSupportFragmentManager();
                        FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
                        Fragment findFragmentByTag = supportFragmentManager.findFragmentByTag("dialog");
                        if (findFragmentByTag != null) {
                            beginTransaction.remove(findFragmentByTag);
                        }
                        beginTransaction.addToBackStack(null);
                        Bundle bundle = new Bundle();
                        String path = ImageCompressorActivity.this.images.get(i).getPath();
                        bundle.putString("fileLocation", path);
                        bundle.putString("fileName", path.substring(path.lastIndexOf("/") + 1));
                        final Dialog dialog = new Dialog(ImageCompressorActivity.this);
                        dialog.requestWindowFeature(1);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.info_dialog);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                        TextView textView = (TextView) dialog.findViewById(R.id.text_name);
                        TextView textView2 = (TextView) dialog.findViewById(R.id.text_location);
                        LinearLayout linearLayout = (LinearLayout) dialog.findViewById(R.id.lin_close);
                        textView.setText(path.substring(path.lastIndexOf("/") + 1));
                        textView2.setText(path);
                        linearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view2) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                        break;
                    case R.id.itm_remove:
                        ImageCompressorActivity.this.images.remove(i);
                        ArrayList arrayList = new ArrayList();
                        Iterator<Image> it = ImageCompressorActivity.this.images.iterator();
                        while (it.hasNext()) {
                            arrayList.add(it.next().getPath());
                        }
                        ImageCompressorActivity.this.recyclerView.setLayoutManager(new GridLayoutManager(ImageCompressorActivity.this, 2));
                        ImageAdapter imageAdapter = new ImageAdapter(arrayList, ImageCompressorActivity.this);
                        imageAdapter.setClickListener(ImageCompressorActivity.this);
                        ImageCompressorActivity.this.recyclerView.setHasFixedSize(true);
                        ImageCompressorActivity.this.recyclerView.setAdapter(imageAdapter);
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    private void commonView() {
        if (this.check == 1) {
            this.txtFileLocation.setText("Photo will save to :" + this.finalPath);
            return;
        }
        this.txtFileLocation.setText("Photos will save to :" + this.finalPath);
    }

    public void compressPhoto() {
        this.twidth = String.valueOf(this.etWidth.getText());
        this.theight = String.valueOf(this.etHeight.getText());
        if (TextUtils.isEmpty(this.twidth)) {
            this.etWidth.setError("enter height");
        } else if (TextUtils.isEmpty(this.theight)) {
            this.etHeight.setError("enter height");
        } else {
            if (this.quality > 90) {
                this.quality = 90;
            }
            if (this.quality < 10) {
                this.quality = 10;
            }
            setAction();
        }
    }

    private void setAction() {
        new MyCompressor(this, this.quality, this.file, Integer.valueOf(this.twidth).intValue(), Integer.valueOf(this.theight).intValue(), this.outputFile, this.bimapCompressFormat, this.finalPath).execute(new Void[0]);
    }



    public class MyCompressor extends AsyncTask<Void, Void, File> {
        private Bitmap.CompressFormat compressFormat;
        private Compressor compressor;
        private File inputFile;
        private File outputFile;
        private String path;
        private int quality;
        private int reqHeight;
        private int reqWidth;

        MyCompressor(Activity activity, int i, File file, int i2, int i3, File file2, Bitmap.CompressFormat compressFormat, String str) {
            this.path = "";
            this.quality = i;
            this.inputFile = file;
            this.outputFile = file2;
            this.reqHeight = i3;
            this.reqWidth = i2;
            this.compressor = new Compressor(activity);
            this.compressFormat = compressFormat;
            this.path = str;
        }

        @Override
        public void onPreExecute() {
            super.onPreExecute();
        }

        public File doInBackground(Void... voidArr) {
            try {
                this.outputFile = this.compressor.setMaxWidth(this.reqWidth).setMaxHeight(this.reqHeight).setQuality(this.quality).setCompressFormat(this.compressFormat).setDestinationDirectoryPath(this.path).compressToFile(this.inputFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return this.outputFile;
        }

        public void onPostExecute(File file) {
            super.onPostExecute( file);
            ImageCompressorActivity.this.compressedImagesList.add(new Image(0, ImageHelper.getNameFromFilePath(file.getAbsolutePath()), file.getAbsolutePath()));
            if (ImageCompressorActivity.this.check == 1) {
                ImageCompressorActivity.this.dismissDialog();
            }
            ImageCompressorActivity.this.getCompressedFile(file);
        }
    }

    public void dismissDialog() {
        Dialog dialog = this.dialog;
        if (dialog != null && dialog.isShowing()) {
            this.dialog.dismiss();
        }
    }

    public void getCompressedFile(File file) {
        this.count++;
        MediaScannerConnection.scanFile(this, new String[]{file.toString()}, null, new MediaScannerConnection.MediaScannerConnectionClient() {
            @Override
            public void onMediaScannerConnected() {
            }

            @Override
            public void onScanCompleted(String str, Uri uri) {
            }
        });
        this.btnCompress.setVisibility(View.GONE);
        this.lin_compressed.setVisibility(View.VISIBLE);
        this.lin_share.setVisibility(View.VISIBLE);
        if (this.check == 1) {
            this.txtFileLocation.setText("Photo is successfully compressed and saved to :" + this.finalPath);
            this.txt_size_compressed.setText(fileSize(file.length()));
            return;
        }
        this.txtFileLocation.setText(this.count + "/" + this.images.size() + " Photos are successfully compressed and saved to : " + this.finalPath);
        if (this.count == this.images.size()) {
            dismissDialog();
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
