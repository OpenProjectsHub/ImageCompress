package com.demo.example.adapters;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.util.Log;
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

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class PDFListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    public Context context;
    public List<File> flist;
    private boolean isLoadingAdded = false;
    int pos;


    protected class LoadingVH extends RecyclerView.ViewHolder {
        public LoadingVH(View view) {
            super(view);
        }
    }


    public class MovieVH extends RecyclerView.ViewHolder {
        public ImageView btnDel;
        public ImageView btnShare;
        public TextView text_date;
        public TextView text_name;
        public TextView text_size;

        public MovieVH(View view) {
            super(view);
            this.text_name = (TextView) view.findViewById(R.id.txt_pdf_name);
            this.text_date = (TextView) view.findViewById(R.id.created_date);
            this.text_size = (TextView) view.findViewById(R.id.size_file);
            this.btnDel = (ImageView) view.findViewById(R.id.img_delete);
            this.btnShare = (ImageView) view.findViewById(R.id.img_share);
        }
    }

    public PDFListAdapter(Context context, List<File> list) {
        this.context = context;
        this.flist = list;
    }

    private List<File> getFlist() {
        return this.flist;
    }

    private void setFlist(List<File> list) {
        this.flist = list;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return getViewHolder(viewGroup, LayoutInflater.from(viewGroup.getContext()));
    }

    private RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, LayoutInflater layoutInflater) {
        return new MovieVH(layoutInflater.inflate(R.layout.item_pdf_creation, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        final File file = this.flist.get(i);
        this.pos = i;
        String format = new SimpleDateFormat("dd/MM/yy  hh:mm:ss a").format(new Date(file.lastModified()));
        int parseInt = Integer.parseInt(String.valueOf(file.length() / 1024));
        String str = parseInt + "KB";
        if (parseInt >= 1024) {
            str = Float.parseFloat(String.valueOf(parseInt / 1024)) + "MB";
        }
        if (getItemViewType(i) == 0) {
            MovieVH movieVH = (MovieVH) viewHolder;
            movieVH.text_name.setText(file.getName());
            movieVH.text_date.setText(format);
            movieVH.text_size.setText(str);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    Uri data = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.setDataAndType(data, "application/pdf");
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    try {
                        PDFListAdapter.this.context.startActivity(Intent.createChooser(intent, "Open file"));
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                    }


                }
            });
            movieVH.btnDel.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    final Dialog dialog = new Dialog(PDFListAdapter.this.context);
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
                            File file2 = new File(String.valueOf(PDFListAdapter.this.flist.get(PDFListAdapter.this.pos)));
                            if (file2.exists()) {
                                file2.delete();
                            }
                            PDFListAdapter.this.flist.remove(PDFListAdapter.this.pos);
                            PDFListAdapter.this.notifyDataSetChanged();
                            if (PDFListAdapter.this.flist.size() == 0) {
                                Toast.makeText(PDFListAdapter.this.context, "No file Found..", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    dialog.show();
                }
            });
            movieVH.btnShare.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    PDFListAdapter.this.shareImage("Image Compressor &amp; Image To PDF Converter By : https://play.google.com/store/apps/details?id=" + context.getPackageName(), String.valueOf(PDFListAdapter.this.flist.get(PDFListAdapter.this.pos)));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        Log.i("arch_adapter", "flist_siz=" + this.flist.size());
        List<File> list = this.flist;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override
    public int getItemViewType(int i) {
        Log.i("arch_adapter", "position=" + i);
        return (i != this.flist.size() - 1 || !this.isLoadingAdded) ? 0 : 1;
    }

    public void shareImage(String str, String str2) {
        MediaScannerConnection.scanFile(this.context, new String[]{str2}, null, new MediaScannerConnection.OnScanCompletedListener() {
            public void onScanCompleted(String str3, Uri uri) {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("application/pdf");
                intent.putExtra("android.intent.extra.TEXT", str3);
                intent.putExtra("android.intent.extra.STREAM", uri);
                intent.addFlags(524288);
                PDFListAdapter.this.context.startActivity(Intent.createChooser(intent, "Share PDF"));
            }
        });
    }
}
