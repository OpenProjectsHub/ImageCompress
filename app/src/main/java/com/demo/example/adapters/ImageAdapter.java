package com.demo.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.demo.example.R;

import com.demo.example.interfaces.ItemClickListener;
import com.demo.example.utils.Constants;

import java.io.File;
import java.util.ArrayList;




public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private Context context;
    public ItemClickListener itemClickListener;
    private ArrayList<String> paths;




    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        ViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.imageView);
            this.textView = (TextView) view.findViewById(R.id.textView);
            this.imageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view2) {
                    ImageAdapter.this.itemClickListener.onItemClick(view2, ViewHolder.this.getAdapterPosition());
                }
            });
        }
    }

    public ImageAdapter(ArrayList<String> arrayList, Context context) {
        this.paths = arrayList;
        this.context = context;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.image_item, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        String str = this.paths.get(i);
        viewHolder.textView.setText(Constants.getFileSize(new File(str).length()));
        Glide.with(this.context).load(str).into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        ArrayList<String> arrayList = this.paths;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
