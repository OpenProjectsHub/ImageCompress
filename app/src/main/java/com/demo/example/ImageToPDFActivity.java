package com.demo.example;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ImageToPDFActivity extends AppCompatActivity {
    private int avg_height;
    private int avg_width;
    private Dialog dialog;
    private String download_folder_path;
    private ArrayList<Image> images;
    LinearLayout lin_pick_img;
    private int num_items;
    private int tot_height;
    private int tot_width;
    private List<String> resiv_list = new ArrayList();
    public String file_nam = "temp";


    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_image_to_p_d_f);

        AdAdmob adAdmob = new AdAdmob(this);
        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.bannerAd), this);


        this.download_folder_path = getIntent().getStringExtra("path");
        Dialog dialog = new Dialog(this);
        this.dialog = dialog;
        dialog.requestWindowFeature(1);
        this.dialog.setCancelable(false);
        this.dialog.setContentView(R.layout.loading_dialog);
        this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.lin_pick_img);
        this.lin_pick_img = linearLayout;
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageToPDFActivity.this.multipleImagePicker();
            }
        });
        this.tot_height = 0;
        this.tot_width = 0;
        this.num_items = 0;
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 100 && i2 == -1 && intent != null) {
            this.images = intent.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
            this.dialog.show();
            int size = this.images.size();
            ArrayList arrayList = new ArrayList();
            for (int i3 = 0; i3 < size; i3++) {
                arrayList.add(images.get(i3).getPath());
            }
            this.resiv_list = arrayList;
            for (int i4 = 0; i4 < this.resiv_list.size(); i4++) {

                Bitmap decodeFile = BitmapFactory.decodeFile(this.resiv_list.get(i4));
                int width = decodeFile.getWidth();
                int height = decodeFile.getHeight();
                this.tot_width += width;
                this.tot_height += height;
                this.num_items++;
            }
            int i5 = this.tot_height;
            int i6 = this.num_items;
            this.avg_height = i5 / i6;
            this.avg_width = this.tot_width / i6;
            try {
                createpdf();
            } catch (Exception e3) {
                e3.printStackTrace();

            }
            this.dialog.dismiss();
            Intent intent2 = new Intent(this, PDFCreationActivity.class);
            intent2.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent2.putExtra("path", this.download_folder_path);
            startActivity(intent2);
        }
    }

    public void createpdf() throws Exception {


        com.itextpdf.text.Image instance = com.itextpdf.text.Image.getInstance(this.resiv_list.get(0));
        instance.setCompressionLevel(9);
        Document document = new Document(instance);
        date_nam();
        new ByteArrayOutputStream();
        PdfWriter instance2 = PdfWriter.getInstance(document, new FileOutputStream(this.download_folder_path + this.file_nam));
        instance2.setFullCompression();
        document.open();
        for (String str : this.resiv_list) {

            com.itextpdf.text.Image instance3 = com.itextpdf.text.Image.getInstance(str);
            char c = (instance3.getWidth() <= ((float) this.avg_width) || instance3.getHeight() > ((float) this.avg_height)) ? (char) 0 : 1;
            if (instance3.getWidth() <= ((float) this.avg_width) && instance3.getHeight() > ((float) this.avg_height)) {
                c = 2;
            }
            if (instance3.getWidth() > ((float) this.avg_width) && instance3.getHeight() > ((float) this.avg_height)) {
                c = 3;
            }
            if (c == 1) {
                instance3.scaleToFit((float) this.avg_width, instance3.getHeight());
            } else if (c == 2) {
                instance3.scaleToFit(instance3.getWidth(), (float) this.avg_height);
            } else if (c == 3) {
                instance3.scaleToFit((float) this.avg_width, (float) this.avg_height);
            }
            document.newPage();
            instance3.setAlignment(1);
            document.add(instance3);
        }

        document.close();
        instance2.close();
        MediaScannerConnection.scanFile(this, new String[]{this.download_folder_path + this.file_nam}, new String[]{"application/pdf"}, null);

    }

    private void date_nam() {
        long time = new Date().getTime();
        this.file_nam = "Image2Pdf" + time + ".pdf";
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void multipleImagePicker() {
        ImagePicker.with(this).setToolbarColor("#282A3B").setStatusBarColor("#6F7A92").setToolbarTextColor("#FFFFFF").setToolbarIconColor("#FFFFFF").setProgressBarColor("#ff5d3b").setBackgroundColor("#ffffff").setMultipleMode(true).setFolderMode(true).setShowCamera(false).setFolderTitle("Choose Images").setImageTitle("Galleries").setDoneTitle("Done").setLimitMessage("You have reached selection limit").setMaxSize(10).setSavePath("Image Resizer").setAlwaysShowDoneButton(false).setRequestCode(100).setKeepScreenOn(true).start();
    }
}
