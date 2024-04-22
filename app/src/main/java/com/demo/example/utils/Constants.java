package com.demo.example.utils;

import android.util.Log;


import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;



public class Constants {
    public static ArrayList<String> ImageGallery = new ArrayList<>();

    public static void listAllImages(File file) {
        File[] listFiles = file.listFiles();
        if (listFiles != null) {
            for (int length = listFiles.length - 1; length >= 0; length--) {
                String file2 = listFiles[length].toString();
                File file3 = new File(file2);
                Log.d("" + file3.length(), "" + file3.length());
                if (file3.length() <= 1024) {
                    Log.i("Invalid Image", "Delete Image");
                } else if (file3.toString().contains(".jpg") || file3.toString().contains(".png") || file3.toString().contains(".jpeg")) {
                    ImageGallery.add(file2);
                }
            }
            return;
        }
        System.out.println("Empty Folder");
    }

    public static String getFileSize(long j) {
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
}
