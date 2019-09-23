package com.sumanmyon.debugger;

import android.util.Log;

public class Utils {
    private static final String TAG = "SUPER_TAG";
    public static void debug(String message){
        Log.d(TAG, message);
    }

    public static void debugThrow(String message){
        Log.d(TAG, message, new Throwable());
    }
}
