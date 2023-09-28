package com.yxf.bindercode;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yxf.baselibrary.BaseEngine;
import com.yxf.baselibrary.NotifyUtil;
import com.yxf.bindercode.constants.NotificationConstants;

public class CarApplication extends Application {
    private static CarApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        registerActivityLife();
        BaseEngine.getInstance().init(this);
        initNotification();
    }

    private void initNotification() {
        NotifyUtil.getInstance().createNotificationChannel(NotificationConstants.CHANNEL_ID_NOTIFY_MSG, NotificationConstants.CHANNEL_NAME_NOTIFY_MSG, NotificationConstants.CHANNEL_DESCRIPTION_NOTIFY_MSG);
        NotifyUtil.getInstance().createNotificationChannel(NotificationConstants.CHANNEL_ID_ADV_MSG, NotificationConstants.CHANNEL_NAME_ADV_MSG, NotificationConstants.CHANNEL_DESCRIPTION_ADV_MSG);
    }

    private void registerActivityLife() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                ActivityControl.getInstance().pushActivity(activity);
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                ActivityControl.getInstance().popActivity(activity);
            }
        });
    }

    public static CarApplication getInstance() {
        return instance;
    }
}
