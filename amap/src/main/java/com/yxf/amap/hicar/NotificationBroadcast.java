package com.yxf.amap.hicar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yxf.baselibrary.LogUtil;

public class NotificationBroadcast extends BroadcastReceiver {
    public static final String TAG = "amap::NotificationBroadcast::";

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.i(TAG, "onReceive action:" + intent.getAction());
    }
}
