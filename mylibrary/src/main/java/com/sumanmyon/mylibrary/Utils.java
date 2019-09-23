package com.sumanmyon.mylibrary;

import android.content.Context;
import android.widget.Toast;

public class Utils {
    public static void showToastShort(Context context, String string){
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }

    public static void showToastLong(Context context, String string){
        Toast.makeText(context, string, Toast.LENGTH_LONG).show();
    }
}
