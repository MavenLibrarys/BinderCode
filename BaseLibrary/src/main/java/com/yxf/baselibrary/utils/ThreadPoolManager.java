package com.yxf.baselibrary.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import com.yxf.baselibrary.LogUtil;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

//线程管理类
public class ThreadPoolManager {
    public static final String TAG = "ThreadPoolManager ";
    private static volatile ThreadPoolManager instance;
    private Handler backgroundHandler;
    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private Executor executor = Executors.newSingleThreadExecutor();

    private class BackgroundHandler extends Handler {
        public BackgroundHandler(@NonNull Looper looper) {
            super(looper);
        }

        @Override
        public void dispatchMessage(@NonNull Message msg) {
            long startTime = System.currentTimeMillis();
            super.dispatchMessage(msg);
            long duration = System.currentTimeMillis() - startTime;
            LogUtil.i(TAG, "message duration:" + duration);
        }
    }

    public static ThreadPoolManager getInstance() {
        if (instance == null) {
            synchronized (ThreadPoolManager.class) {
                if (instance == null) {
                    instance = new ThreadPoolManager();
                }
            }
        }
        return instance;
    }

    public void getBackgroundHandler() {
        HandlerThread handlerThread = new HandlerThread("background");
        handlerThread.run();
        if (backgroundHandler == null) {
            backgroundHandler = new BackgroundHandler(handlerThread.getLooper());
        }
    }

    public void runInMainThread(Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
        } else {
            mainHandler.post(runnable);
        }
    }
}
