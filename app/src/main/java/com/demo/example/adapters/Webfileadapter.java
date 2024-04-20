package com.demo.example.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.example.R;

import com.demo.example.utils.FeedWebp;

import java.io.File;
import java.util.ArrayList;




public class Webfileadapter extends RecyclerView.Adapter<Webfileadapter.MyViewholder> {
    public Context context;
    public File f808fD;
    public ArrayList<FeedWebp> feedWebps;



    public class MyViewholder extends RecyclerView.ViewHolder {
        public ImageView btnDel;
        public ImageView btnShare;
        ImageView file_icon;
        public TextView text_name;

        public MyViewholder(View view) {
            super(view);
            this.file_icon = (ImageView) view.findViewById(R.id.file_icon);
            this.text_name = (TextView) view.findViewById(R.id.txt_pdf_name);
            this.btnDel = (ImageView) view.findViewById(R.id.img_delete);
            this.btnShare = (ImageView) view.findViewById(R.id.img_share);
        }
    }

    public Webfileadapter(Context context, ArrayList<FeedWebp> arrayList) {
        this.context = context;
        this.feedWebps = arrayList;
    }

    public MyViewholder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return getViewHolder(viewGroup, LayoutInflater.from(viewGroup.getContext()));
    }

    private MyViewholder getViewHolder(ViewGroup viewGroup, LayoutInflater layoutInflater) {
        return new MyViewholder(layoutInflater.inflate(R.layout.item_webp_creation, viewGroup, false));
    }

    public void onBindViewHolder(MyViewholder myViewholder, final int i) {
        myViewholder.text_name.setText(this.feedWebps.get(i).getMyfiles());
        myViewholder.file_icon.setImageBitmap(BitmapFactory.decodeFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/Image Compressor/Webp Image/" + this.feedWebps.get(i).getMyfiles()));
        myViewholder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Webfileadapter webfileadapter = Webfileadapter.this;
                webfileadapter.f808fD = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/Image Compressor/Webp Image/" + Webfileadapter.this.feedWebps.get(i).getMyfiles());
                Uri uriForFile = FileProvider.getUriForFile(Webfileadapter.this.context, context.getPackageName() + ".fileprovider", Webfileadapter.this.f808fD);
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setDataAndType(uriForFile, "image/*");
                intent.setFlags(3);
                Webfileadapter.this.context.startActivity(intent);
            }
        });
        myViewholder.btnDel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final Dialog dialog = new Dialog(Webfileadapter.this.context);
                dialog.requestWindowFeature(1);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.delete_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                ((LinearLayout) dialog.findViewById(R.id.lin_no)).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view2) {
                        dialog.dismiss();
                    }
                });
                ((LinearLayout) dialog.findViewById(R.id.lin_yes)).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view2) {
                        dialog.dismiss();
                        Webfileadapter webfileadapter = Webfileadapter.this;
                        webfileadapter.f808fD = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/Image Compressor/Webp Image/" + Webfileadapter.this.feedWebps.get(i).getMyfiles());
                        if (Webfileadapter.this.f808fD.exists()) {
                            Webfileadapter.this.f808fD.delete();
                        }
                        Webfileadapter.this.feedWebps.remove(i);
                        Webfileadapter.this.notifyDataSetChanged();
                        if (Webfileadapter.this.feedWebps.size() == 0) {
                            Toast.makeText(Webfileadapter.this.context, "No file Found..", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                dialog.show();
            }
        });
        myViewholder.btnShare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Webfileadapter webfileadapter = Webfileadapter.this;
                webfileadapter.f808fD = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/Image Compressor/Webp Image/" + Webfileadapter.this.feedWebps.get(i).getMyfiles());
                Webfileadapter webfileadapter2 = Webfileadapter.this;
                webfileadapter2.shareImage(context.getString(R.string.app_name) + "Created By : https://play.google.com/store/apps/details?id=" + context.getPackageName(), String.valueOf(Webfileadapter.this.f808fD));
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.feedWebps.size();
    }

    public void shareImage(String str, String str2) {
        MediaScannerConnection.scanFile(this.context, new String[]{str2}, null, new MediaScannerConnection.OnScanCompletedListener() {
            public void onScanCompleted(String str3, Uri uri) {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("text/plain");
                intent.putExtra("android.intent.extra.TEXT", str3);
                intent.putExtra("android.intent.extra.STREAM", uri);
                intent.addFlags(524288);
                Webfileadapter.this.context.startActivity(Intent.createChooser(intent, "Share Image"));
            }
        });
    }
}
