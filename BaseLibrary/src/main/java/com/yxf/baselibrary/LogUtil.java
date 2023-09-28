package com.yxf.baselibrary;

import android.util.Log;

public class LogUtil {
    public static final String PREFIX = "yxfLog::";

    public static void i(String TAG, String msg) {
        Log.i(PREFIX + TAG, msg);
    }

    public static void d(String TAG, String msg) {
        Log.d(PREFIX + TAG, msg);
    }

    public static void w(String TAG, String msg) {
        Log.w(PREFIX + TAG, msg);
    }
}
