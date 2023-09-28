package com.yxf.baselibrary;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotifyUtil {
    public static final String TAG = "NotifyUtil ";

    private static final NotifyUtil instance = new NotifyUtil();

    static {
        LogUtil.i(TAG, "=========aaaaaaaaaaa============");
    }

    public static NotifyUtil getInstance() {
        return instance;
    }

    private Context getContext() {
        return BaseEngine.getInstance().getContext();
    }

    public void createNotificationChannel(String channelId, String channelName, String channelDescription) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            channel.setDescription(channelDescription);
            // Register the channel with the system. You can't change the importance
            // or other notification behaviors after this.
            NotificationManager notificationManager = getContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void openChannelSetting(String packageName, String channelId) {
        Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName);
        intent.putExtra(Settings.EXTRA_CHANNEL_ID, channelId);
        getContext().startActivity(intent);
    }

    public void deleteChannelId(String channelId) {
        LogUtil.i(TAG, "deleteChannelId channelId:" + channelId);
        NotificationManager notificationManager =
                (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.deleteNotificationChannel(channelId);
        } else {
            LogUtil.i(TAG, "deleteChannelId fail,SDK_VERSION is lower 26.");
        }
    }

    public void sendNotification(String channelId, int icon, @Nullable int bigIconId, String title, String content, int notificationId) {
        LogUtil.i(TAG, "SDK_INT :" + Build.VERSION.SDK_INT);
        Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), bigIconId);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), channelId)
                .setSmallIcon(icon)
                .setLargeIcon(bitmap)
                .setContentTitle(title)
                .setContentText(content)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(content));
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, builder.build());
    }

    public void sendNotification(String channelId, int icon, String title, String content, int notificationId, NotificationCompat.Action action) {
        LogUtil.i(TAG, "SDK_INT :" + Build.VERSION.SDK_INT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), channelId)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setContentText(content)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(content))
                .addAction(action);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, builder.build());
    }
}
