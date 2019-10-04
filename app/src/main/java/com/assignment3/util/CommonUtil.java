package com.assignment3.util;

import android.app.Activity;

import com.google.android.material.snackbar.Snackbar;

public class CommonUtil {
    public static void showSnackBar(Activity activity, String message){
        Snackbar.make(activity.getWindow().getDecorView().getRootView(),message,Snackbar.LENGTH_LONG).show();
    }
}
