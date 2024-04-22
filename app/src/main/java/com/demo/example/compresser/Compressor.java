package com.demo.example.compresser;

import android.content.Context;
import android.graphics.Bitmap;
import java.io.File;
import java.io.IOException;
import java.util.Date;



public class Compressor {
    private String destinationDirectoryPath;
    private Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.JPEG;
    private int maxHeight = 816;
    private int maxWidth = 612;
    private int quality = 80;

    public Compressor(Context context) {
        this.destinationDirectoryPath = context.getCacheDir().getPath() + File.separator + "images";
    }

    public Compressor setMaxWidth(int i) {
        this.maxWidth = i;
        return this;
    }

    public Compressor setMaxHeight(int i) {
        this.maxHeight = i;
        return this;
    }

    public Compressor setCompressFormat(Bitmap.CompressFormat compressFormat) {
        this.compressFormat = compressFormat;
        return this;
    }

    public Compressor setQuality(int i) {
        this.quality = i;
        return this;
    }

    public Compressor setDestinationDirectoryPath(String str) {
        this.destinationDirectoryPath = str;
        return this;
    }

    public File compressToFile(File file) throws IOException {
        String substring = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("."));
        String valueOf = String.valueOf(new Date().getTime());
        return compressToFile(file, "IMG_" + valueOf + substring);
    }

    private File compressToFile(File file, String str) throws IOException {
        int i = this.maxWidth;
        int i2 = this.maxHeight;
        Bitmap.CompressFormat compressFormat = this.compressFormat;
        int i3 = this.quality;
        return ImageUtil.compressImage(file, i, i2, compressFormat, i3, this.destinationDirectoryPath + File.separator + str);
    }
}
