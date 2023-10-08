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

    public static void list(String TAG, int[] arr) {
        if (arr == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            if (i == 0) {
                sb.append("" + arr[i]);
            } else {
                sb.append("," + arr[i]);
            }
        }
        i(TAG, sb.toString());
    }
}
