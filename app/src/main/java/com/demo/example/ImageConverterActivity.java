package com.demo.example;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.itextpdf.text.html.HtmlTags;

import com.demo.example.utils.AllAdsKey;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



public class ImageConverterActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_GALLERY = 200;
    int add;
    Bitmap bitmap;
    Calendar cal;
    Context context;
    int date;
    ImageView demo_img;
    private Dialog dialog;
    RadioButton dp3;
    RadioGroup dpi;
    EditText ed1;
    EditText ed2;
    Bitmap f782bm;
    File f784f;
    RadioGroup f785rg;
    int f786x;
    int hcm;
    int hin;
    int hmm;
    int hour;
    int hpx;
    ImageView image1;
    LinearLayout lin_jpg;
    LinearLayout lin_load_img;
    LinearLayout lin_png;
    LinearLayout lin_webp;
    int lod;
    int lon;
    int month;
    String mr1;
    RadioButton rb1;
    int req;
    Toolbar toolbar;
    TextView txt_load;
    int wcm;
    int win;
    int wmm;
    int wpx;
    int year;
    String f783ch = "pixel";
    String ch1 = "300 dpi(Normal Photo)";


    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_image_converter);

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
        this.lin_load_img = (LinearLayout) findViewById(R.id.lin_load_img);
        this.image1 = (ImageView) findViewById(R.id.img);
        this.demo_img = (ImageView) findViewById(R.id.demo_img);
        this.lin_jpg = (LinearLayout) findViewById(R.id.lin_jpg);
        this.lin_png = (LinearLayout) findViewById(R.id.lin_png);
        this.lin_webp = (LinearLayout) findViewById(R.id.lin_webp);
        this.txt_load = (TextView) findViewById(R.id.txt_load);
        this.lin_load_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageConverterActivity.this.demo_img.setVisibility(View.GONE);
                ImageConverterActivity.this.image1.setVisibility(View.VISIBLE);
                ImageConverterActivity.this.startActivityForResult(new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 200);
            }
        });
        this.lin_jpg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ImageConverterActivity.this.req == 1 && ImageConverterActivity.this.lod == 1) {
                    File externalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                    File file = new File(externalStorageDirectory.getAbsolutePath() + "/Image Compressor");
                    file.mkdirs();
                    String format = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    ImageConverterActivity.this.f784f = new File(file, format + ".jpg");
                    try {
                        ImageConverterActivity.this.f784f.createNewFile();
                    } catch (IOException unused) {
                    }
                    final String[] strArr = {""};
                    final Dialog dialog2 = new Dialog(ImageConverterActivity.this);
                    dialog2.requestWindowFeature(1);
                    dialog2.setCancelable(false);
                    dialog2.setContentView(R.layout.jpg_dialog);
                    dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    final LinearLayout linearLayout = (LinearLayout) dialog2.findViewById(R.id.lin_low);
                    final LinearLayout linearLayout2 = (LinearLayout) dialog2.findViewById(R.id.lin_normal);
                    final LinearLayout linearLayout3 = (LinearLayout) dialog2.findViewById(R.id.lin_high);
                    LinearLayout linearLayout4 = (LinearLayout) dialog2.findViewById(R.id.lin_ok);

                    linearLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view2) {
                            strArr[0] = "low";
                            linearLayout.setBackgroundResource(R.drawable.green_bg);
                            linearLayout2.setBackgroundResource(R.drawable.border_green_bg);
                            linearLayout3.setBackgroundResource(R.drawable.border_green_bg);
                        }
                    });
                    linearLayout2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view2) {
                            strArr[0] = HtmlTags.NORMAL;
                            linearLayout.setBackgroundResource(R.drawable.border_green_bg);
                            linearLayout2.setBackgroundResource(R.drawable.green_bg);
                            linearLayout3.setBackgroundResource(R.drawable.border_green_bg);
                        }
                    });
                    linearLayout3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view2) {
                            strArr[0] = "high";
                            linearLayout.setBackgroundResource(R.drawable.border_green_bg);
                            linearLayout2.setBackgroundResource(R.drawable.border_green_bg);
                            linearLayout3.setBackgroundResource(R.drawable.green_bg);
                        }
                    });
                    linearLayout4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view2) {
                            dialog2.dismiss();
                            if (strArr[0].equals("low")) {
                                ImageConverterActivity.this.lon = 1;
                                new LongOperation5().execute(new String[0]);
                            } else if (strArr[0].equals(HtmlTags.NORMAL)) {
                                ImageConverterActivity.this.lon = 2;
                                new LongOperation5().execute(new String[0]);
                            } else if (strArr[0].equals("high")) {
                                ImageConverterActivity.this.lon = 3;
                                new LongOperation5().execute(new String[0]);
                            }
                        }
                    });
                    dialog2.show();
                    return;
                }
                Toast.makeText(ImageConverterActivity.this, "Select Image first...", Toast.LENGTH_LONG).show();
            }
        });
        this.lin_png.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ImageConverterActivity.this.req == 1 && ImageConverterActivity.this.lod == 1) {
                    File externalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                    File file = new File(externalStorageDirectory.getAbsolutePath() + "/Image Compressor");
                    file.mkdirs();
                    String format = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    ImageConverterActivity.this.f784f = new File(file, format + ".png");
                    try {
                        ImageConverterActivity.this.f784f.createNewFile();
                    } catch (IOException unused) {
                    }
                    ImageConverterActivity.this.lon = 4;
                    new LongOperation5().execute(new String[0]);
                    return;
                }
                Toast.makeText(ImageConverterActivity.this, "Select Image first...", Toast.LENGTH_LONG).show();
            }
        });
        this.lin_webp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ImageConverterActivity.this.req == 1 && ImageConverterActivity.this.lod == 1) {
                    File externalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                    File file = new File(externalStorageDirectory.getAbsolutePath() + "/Image Compressor/Webp Image");
                    file.mkdirs();
                    String format = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    ImageConverterActivity.this.f784f = new File(file, format + ".webp");
                    try {
                        ImageConverterActivity.this.f784f.createNewFile();
                    } catch (IOException unused) {
                    }
                    final String[] strArr = {""};
                    final Dialog dialog2 = new Dialog(ImageConverterActivity.this);
                    dialog2.requestWindowFeature(1);
                    dialog2.setCancelable(false);
                    dialog2.setContentView(R.layout.webp_dialog);
                    dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    final LinearLayout linearLayout = (LinearLayout) dialog2.findViewById(R.id.lin_low);
                    final LinearLayout linearLayout2 = (LinearLayout) dialog2.findViewById(R.id.lin_normal);
                    final LinearLayout linearLayout3 = (LinearLayout) dialog2.findViewById(R.id.lin_high);
                    LinearLayout linearLayout4 = (LinearLayout) dialog2.findViewById(R.id.lin_ok);

                    linearLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view2) {
                            strArr[0] = "low";
                            linearLayout.setBackgroundResource(R.drawable.green_bg);
                            linearLayout2.setBackgroundResource(R.drawable.border_green_bg);
                            linearLayout3.setBackgroundResource(R.drawable.border_green_bg);
                        }
                    });
                    linearLayout2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view2) {
                            strArr[0] = HtmlTags.NORMAL;
                            linearLayout.setBackgroundResource(R.drawable.border_green_bg);
                            linearLayout2.setBackgroundResource(R.drawable.green_bg);
                            linearLayout3.setBackgroundResource(R.drawable.border_green_bg);
                        }
                    });
                    linearLayout3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view2) {
                            strArr[0] = "high";
                            linearLayout.setBackgroundResource(R.drawable.border_green_bg);
                            linearLayout2.setBackgroundResource(R.drawable.border_green_bg);
                            linearLayout3.setBackgroundResource(R.drawable.green_bg);
                        }
                    });
                    linearLayout4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view2) {
                            dialog2.dismiss();
                            if (strArr[0].equals("low")) {
                                ImageConverterActivity.this.lon = 5;
                                new LongOperation5().execute(new String[0]);
                            } else if (strArr[0].equals(HtmlTags.NORMAL)) {
                                ImageConverterActivity.this.lon = 6;
                                new LongOperation5().execute(new String[0]);
                            } else if (strArr[0].equals("high")) {
                                ImageConverterActivity.this.lon = 7;
                                new LongOperation5().execute(new String[0]);
                            }
                        }
                    });
                    dialog2.show();
                    return;
                }
                Toast.makeText(ImageConverterActivity.this, "Select Image first...", Toast.LENGTH_LONG).show();
            }
        });
        requestPermission();
        Calendar instance = Calendar.getInstance();
        this.cal = instance;
        this.date = instance.get(5);
        this.month = this.cal.get(2) + 1;
        this.year = this.cal.get(1);
        this.hour = this.cal.get(11);
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT < 23 || ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
            this.req = 1;
        } else {
            requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
        }
    }

    @Override

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (iArr.length <= 0) {
            Toast.makeText(this, "Read & Write Permission needed :(", Toast.LENGTH_LONG).show();
        } else if (i == 1 && iArr.length > 0 && iArr[0] == 0) {
            this.req = 1;
        } else if (i == 2 && iArr.length > 0) {
            int i2 = iArr[0];
        }
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 200 && i2 == -1 && intent != null && intent.getData() != null) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), intent.getData());
                this.f782bm = bitmap;
                Bitmap bit = bit(bitmap, 864, 1296);
                this.bitmap = bit;
                this.image1.setImageBitmap(bit);
                this.txt_load.setText("Choose Another");
                this.lod = 1;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Bitmap bit(Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(((float) i) / ((float) width), ((float) i2) / ((float) height));
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
    }



    private class LongOperation5 extends AsyncTask<String, Void, String> {
        public void onProgressUpdate(Void... voidArr) {
        }

        private LongOperation5() {
        }

        public String doInBackground(String... strArr) {
            FileOutputStream fileOutputStream;
            FileOutputStream fileOutputStream2;
            FileOutputStream fileOutputStream3;
            FileOutputStream fileOutputStream4;
            FileOutputStream fileOutputStream5;
            FileOutputStream fileOutputStream6;
            FileOutputStream fileOutputStream7;
            if (ImageConverterActivity.this.lon == 1) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ImageConverterActivity.this.bitmap.compress(Bitmap.CompressFormat.JPEG, 30, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                try {
                    fileOutputStream7 = new FileOutputStream(ImageConverterActivity.this.f784f);
                } catch (FileNotFoundException unused) {
                    fileOutputStream7 = null;
                }
                try {
                    fileOutputStream7.write(byteArray);
                    fileOutputStream7.flush();
                    fileOutputStream7.close();
                } catch (IOException unused2) {
                }
            }
            if (ImageConverterActivity.this.lon == 2) {
                ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                ImageConverterActivity.this.bitmap.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream2);
                byte[] byteArray2 = byteArrayOutputStream2.toByteArray();
                try {
                    fileOutputStream6 = new FileOutputStream(ImageConverterActivity.this.f784f);
                } catch (FileNotFoundException unused3) {
                    fileOutputStream6 = null;
                }
                try {
                    fileOutputStream6.write(byteArray2);
                    fileOutputStream6.flush();
                    fileOutputStream6.close();
                } catch (IOException unused4) {
                }
            }
            if (ImageConverterActivity.this.lon == 3) {
                ByteArrayOutputStream byteArrayOutputStream3 = new ByteArrayOutputStream();
                ImageConverterActivity.this.bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream3);
                byte[] byteArray3 = byteArrayOutputStream3.toByteArray();
                try {
                    fileOutputStream5 = new FileOutputStream(ImageConverterActivity.this.f784f);
                } catch (FileNotFoundException unused5) {
                    fileOutputStream5 = null;
                }
                try {
                    fileOutputStream5.write(byteArray3);
                    fileOutputStream5.flush();
                    fileOutputStream5.close();
                } catch (IOException unused6) {
                }
            }
            if (ImageConverterActivity.this.lon == 4) {
                ByteArrayOutputStream byteArrayOutputStream4 = new ByteArrayOutputStream();
                ImageConverterActivity.this.bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream4);
                byte[] byteArray4 = byteArrayOutputStream4.toByteArray();
                try {
                    fileOutputStream4 = new FileOutputStream(ImageConverterActivity.this.f784f);
                } catch (FileNotFoundException unused7) {
                    fileOutputStream4 = null;
                }
                try {
                    fileOutputStream4.write(byteArray4);
                    fileOutputStream4.flush();
                    fileOutputStream4.close();
                } catch (IOException unused8) {
                }
            }
            if (ImageConverterActivity.this.lon == 5) {
                ByteArrayOutputStream byteArrayOutputStream5 = new ByteArrayOutputStream();
                ImageConverterActivity.this.bitmap.compress(Bitmap.CompressFormat.WEBP, 30, byteArrayOutputStream5);
                byte[] byteArray5 = byteArrayOutputStream5.toByteArray();
                try {
                    fileOutputStream3 = new FileOutputStream(ImageConverterActivity.this.f784f);
                } catch (FileNotFoundException unused9) {
                    fileOutputStream3 = null;
                }
                try {
                    fileOutputStream3.write(byteArray5);
                    fileOutputStream3.flush();
                    fileOutputStream3.close();
                } catch (IOException unused10) {
                }
            }
            if (ImageConverterActivity.this.lon == 6) {
                ByteArrayOutputStream byteArrayOutputStream6 = new ByteArrayOutputStream();
                ImageConverterActivity.this.bitmap.compress(Bitmap.CompressFormat.WEBP, 60, byteArrayOutputStream6);
                byte[] byteArray6 = byteArrayOutputStream6.toByteArray();
                try {
                    fileOutputStream2 = new FileOutputStream(ImageConverterActivity.this.f784f);
                } catch (FileNotFoundException unused11) {
                    fileOutputStream2 = null;
                }
                try {
                    fileOutputStream2.write(byteArray6);
                    fileOutputStream2.flush();
                    fileOutputStream2.close();
                } catch (IOException unused12) {
                }
            }
            if (ImageConverterActivity.this.lon == 7) {
                ByteArrayOutputStream byteArrayOutputStream7 = new ByteArrayOutputStream();
                ImageConverterActivity.this.bitmap.compress(Bitmap.CompressFormat.WEBP, 100, byteArrayOutputStream7);
                byte[] byteArray7 = byteArrayOutputStream7.toByteArray();
                try {
                    fileOutputStream = new FileOutputStream(ImageConverterActivity.this.f784f);
                } catch (FileNotFoundException unused13) {
                    fileOutputStream = null;
                }
                try {
                    fileOutputStream.write(byteArray7);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException unused14) {
                }
            }
            return null;
        }

        public void onPostExecute(String str) {
            ImageConverterActivity.this.dialog.dismiss();
            ImageConverterActivity.this.alerts();
        }

        @Override
        public void onPreExecute() {
            ImageConverterActivity.this.dialog.show();
        }
    }

    public void alerts() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.done_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        ((LinearLayout) dialog.findViewById(R.id.lin_ok)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
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
