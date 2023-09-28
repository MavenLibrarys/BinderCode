package com.yxf.amap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.yxf.amap.hicar.NotificationBroadcast;
import com.yxf.carenginelib.Constants;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity ";
    //音频模块开发！！！！！！！！
    // https://developer.android.google.cn/guide/topics/media-apps/audio-app/building-a-mediabrowserservice?hl=en#mediastyle-notifications

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setPackage("com.yxf.amap");
                intent.setAction(Constants.OPERATOR_ACTION);
                sendBroadcast(intent);
            }
        },500);

        IntentFilter filter=new IntentFilter();
        filter.addAction(Constants.OPERATOR_ACTION);
        registerReceiver(new NotificationBroadcast(),filter);

    }
}