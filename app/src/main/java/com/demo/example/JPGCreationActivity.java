package com.demo.example;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.example.utils.AllAdsKey;
import com.demo.example.utils.Constants;
import com.demo.example.utils.MAGFileProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;



public class JPGCreationActivity extends AppCompatActivity {
    Context context;
    private ImageView noImage;
    int pos;
    private RecyclerView recyclerView;
    Toolbar toolbar;


    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_j_p_g_creation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.toolbar = toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.noImage = (ImageView) findViewById(R.id.empty_view);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        this.recyclerView = recyclerView;
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView.setHasFixedSize(true);
        setAdapter();
    }

    public void setAdapter() {
        fetchImage();
        Collections.sort(Constants.ImageGallery);
        Collections.reverse(Constants.ImageGallery);
        this.recyclerView.setAdapter(new MyAlbumAdapter(this, Constants.ImageGallery));
        if (Constants.ImageGallery.size() <= 0) {
            this.noImage.setVisibility(View.VISIBLE);
            this.recyclerView.setVisibility(View.GONE);
            return;
        }
        this.noImage.setVisibility(View.GONE);
        this.recyclerView.setVisibility(View.VISIBLE);
    }

    private void fetchImage() {
        Constants.ImageGallery.clear();
        Constants.listAllImages(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/Image Compressor"));
    }




    public class MyAlbumAdapter extends RecyclerView.Adapter<MyAlbumAdapter.MyViewHolder> {
        ArrayList<String> data;
        public Context mContext;




        public class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView imgPhoto;
            RelativeLayout layLast;
            TextView tvImageName;

            MyViewHolder(View view) {
                super(view);
                this.imgPhoto = (ImageView) view.findViewById(R.id.imgPhoto);
                this.layLast = (RelativeLayout) view.findViewById(R.id.layLast);
                this.tvImageName = (TextView) view.findViewById(R.id.tvImageName);
            }
        }

        MyAlbumAdapter(Context context, ArrayList<String> arrayList) {
            this.mContext = context;
            this.data = arrayList;
        }

        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_image_creation, viewGroup, false));
        }

        public void onBindViewHolder(MyViewHolder myViewHolder, final int i) {
            JPGCreationActivity.this.pos = i;
            myViewHolder.imgPhoto.setImageBitmap(BitmapFactory.decodeFile(this.data.get(i)));
            myViewHolder.tvImageName.setText(new File(this.data.get(i)).getName());
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(MyAlbumAdapter.this.mContext, FullScreenViewActivity.class);
                    intent.putExtra("ImagePosition", i);
                    intent.putStringArrayListExtra("arraylist", MyAlbumAdapter.this.data);
                    MyAlbumAdapter.this.mContext.startActivity(intent);
                }
            });
            myViewHolder.layLast.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(MyAlbumAdapter.this.mContext, view);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.menu_delete:
                                    final Dialog dialog = new Dialog(JPGCreationActivity.this);
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
                                            Log.e("data1234", "" + MyAlbumAdapter.this.data.get(i));
                                            File file = new File(MyAlbumAdapter.this.data.get(i));
                                            if (file.exists()) {
                                                file.delete();
                                                MyAlbumAdapter.this.data.remove(MyAlbumAdapter.this.data.get(i));
                                            }
                                            MyAlbumAdapter.this.notifyDataSetChanged();
                                            try {
                                                Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                                                intent.setData(Uri.fromFile(file));
                                                JPGCreationActivity.this.sendBroadcast(intent);
                                            } catch (Exception unused) {
                                            }
                                        }
                                    });
                                    dialog.show();
                                    break;
                                case R.id.menu_details:
                                    final Dialog dialog2 = new Dialog(JPGCreationActivity.this);
                                    dialog2.requestWindowFeature(1);
                                    dialog2.setCancelable(false);
                                    dialog2.setContentView(R.layout.info_dialog);
                                    dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                                    ((TextView) dialog2.findViewById(R.id.text_name)).setText(new File(MyAlbumAdapter.this.data.get(i)).getName());
                                    ((TextView) dialog2.findViewById(R.id.text_location)).setText(new File(MyAlbumAdapter.this.data.get(i)).getAbsolutePath());
                                    ((LinearLayout) dialog2.findViewById(R.id.lin_close)).setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View view2) {
                                            dialog2.dismiss();
                                        }
                                    });
                                    dialog2.show();
                                    break;
                                case R.id.menu_rename:
                                    final Dialog dialog3 = new Dialog(MyAlbumAdapter.this.mContext);
                                    dialog3.requestWindowFeature(1);
                                    dialog3.setContentView(R.layout.rename_dialog);
                                    dialog3.setCancelable(false);
                                    dialog3.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                                    final EditText editText = (EditText) dialog3.findViewById(R.id.etRename);
                                    ((LinearLayout) dialog3.findViewById(R.id.lin_rename)).setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View view2) {
                                            if (TextUtils.isEmpty(editText.getText().toString())) {
                                                editText.setError("Please Enter File Name");
                                                return;
                                            }
                                            dialog3.dismiss();
                                            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Image Compressor/");
                                            String str = null;
                                            if (file.exists()) {
                                                File[] listFiles = file.listFiles();
                                                int length = listFiles.length;
                                                Log.e("JPGG", " " + length);
                                                int i2 = 0;
                                                while (true) {
                                                    Log.e("JPGG", "11 " + i2);
                                                    if (i2 >= length) {
                                                        break;
                                                    }
                                                    File file2 = listFiles[i2];
                                                    if (file2.isFile() && ((file2.getName().contains(".jpeg") || file2.getName().contains(".jpg") || file2.getName().contains(".png")) && file2.getName().equalsIgnoreCase(new File(MyAlbumAdapter.this.data.get(JPGCreationActivity.this.pos)).getName()))) {
                                                        str = file2.getName();
                                                    }
                                                    i2++;
                                                }
                                                File file3 = new File(file, str);
                                                File file4 = new File(file, editText.getText().toString().trim() + ".jpeg");
                                                if (file3.exists()) {
                                                    file3.renameTo(file4);
                                                    JPGCreationActivity.this.setAdapter();
                                                }
                                            }
                                        }
                                    });
                                    ((LinearLayout) dialog3.findViewById(R.id.lin_cancel)).setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View view2) {
                                            dialog3.dismiss();
                                        }
                                    });
                                    dialog3.show();
                                    break;
                                case R.id.menu_share:
                                    Intent intent = new Intent("android.intent.action.SEND");
                                    intent.setType("image/*");
                                    intent.putExtra("android.intent.extra.TEXT", getString(R.string.app_name) + "\nCreated By : https://play.google.com/store/apps/details?id=" + getPackageName());
                                    intent.putExtra("android.intent.extra.STREAM", MAGFileProvider.getUriForFile(MyAlbumAdapter.this.mContext, context.getPackageName() + ".fileprovider", new File(MyAlbumAdapter.this.data.get(i))));
                                    MyAlbumAdapter.this.mContext.startActivity(Intent.createChooser(intent, "Share Image Using"));
                                    break;
                            }
                            return false;
                        }
                    });
                    popupMenu.inflate(R.menu.menu_more);
                    popupMenu.show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return this.data.size();
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
