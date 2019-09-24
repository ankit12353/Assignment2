package com.example.assignment2.util;

import android.app.Activity;

import com.google.android.material.snackbar.Snackbar;

public class CommanUtil {
    public static void showSnackBar(Activity activity,String message){
        Snackbar.make(activity.getWindow().getDecorView().getRootView(),message,Snackbar.LENGTH_LONG).show();
    }
}
