package com.yxf.bindercode.activity;

import android.app.PendingIntent;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.yxf.baselibrary.LogUtil;
import com.yxf.baselibrary.NotifyUtil;
import com.yxf.bindercode.BaseActivity;
import com.yxf.bindercode.MainActivity;
import com.yxf.bindercode.R;
import com.yxf.bindercode.constants.NotificationConstants;
import com.yxf.bindercode.databinding.ActivityNotificationLayoutBinding;
import com.yxf.bindercode.reflect.AutoWired;
import com.yxf.bindercode.reflect.InjectUtil;
import com.yxf.bindercode.viewmodel.LiveDataUtil;

import java.lang.reflect.Field;

public class NotificationActivity extends BaseActivity<ActivityNotificationLayoutBinding> {
    public static final String TAG = "NotificationActivity ";

    @AutoWired
    public int id;

    @AutoWired("notify_name")
    public String name;

    @Override
    public int layoutID() {
        return R.layout.activity_notification_layout;
    }

    @Override
    public void initView() {
        super.initView();
        InjectUtil.injectView(this);
        binding.idSendNotification.setOnClickListener(v -> {
            String channelId = NotificationConstants.CHANNEL_ID_NOTIFY_MSG;
            int notifyId = NotificationConstants.NOTIFICATION_ID_NOTIFY;
            NotifyUtil.getInstance().sendNotification(channelId,
                    R.drawable.heart,
                    R.drawable.hearted,
                    "My notification",
                    "Much longer text that cannot fit one line，Much longer text that cannot fit one line...",
                    notifyId);
        });

        binding.idAction.setOnClickListener(v -> {
            String channelId = NotificationConstants.CHANNEL_ID_NOTIFY_MSG;
            int notifyId = NotificationConstants.NOTIFICATION_ID_NOTIFY;
            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
            NotificationCompat.Action action = new NotificationCompat.Action(R.drawable.hearted, "断开连接", pendingIntent);
            NotifyUtil.getInstance().sendNotification(channelId,
                    R.drawable.heart,
                    "My notification",
                    "Much longer text that cannot fit one line",
                    notifyId,
                    action);
        });
        try {
            Class<? extends MutableLiveData> cls = LiveDataUtil.getInstance().data.getClass();
            Field versionField = cls.getSuperclass().getDeclaredField("mVersion");
            versionField.setAccessible(true);
            try {
                Object version = versionField.get(LiveDataUtil.getInstance().data);
                LogUtil.i(TAG, "mVersion:" + version);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        LiveDataUtil.getInstance().data.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                LogUtil.i(TAG, "onChanged s:" + s);
            }
        });
    }
}
