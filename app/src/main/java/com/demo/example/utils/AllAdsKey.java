package com.demo.example.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;


import com.demo.example.R;


public class AllAdsKey {

    public static String enableInternet = "Please Enable Internet Connection.";


    public static void share(Activity activity) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.SUBJECT", activity.getResources().getString(R.string.app_name));
        intent.putExtra("android.intent.extra.TEXT", activity.getResources().getString(R.string.app_name) + "\nHey I am Using this Amazing Application.Get it from here: \n\nhttps://play.google.com/store/apps/appdetails?id=" + activity.getPackageName());
        activity.startActivity(Intent.createChooser(intent, "choose one"));
    }

    public static void Rate(Activity activity) {
        try {
            activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + activity.getPackageName())));
        } catch (ActivityNotFoundException unused) {
            activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + activity.getPackageName())));
        }
    }

    public static boolean isOnline(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
