package com.yxf.carenginelib;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

public abstract class AbstractCarOperatorService extends Service {
    private static final String TAG = "AbstractCarOperatorService ";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ICarOperatorMgr.Stub() {
            @Override
            public void callback(String packageName, Bundle bundle) {
                LogUtil.i(TAG, "callback packageName:" + packageName + " ,Pid:" + Binder.getCallingPid());
                if (bundle == null) {
                    LogUtil.i(TAG, "callback bundle is null.");
                    return;
                }
                int state = bundle.getInt("nav_state");
                AbstractCarOperatorService.this.doCallback(packageName, state);
            }

            @Override
            public void initDeviceInfo(DeviceInfo deviceInfo) throws RemoteException {
                LogUtil.i(TAG, "initDeviceInfo device:" + deviceInfo.getDeviceName());
                onInitDeviceInfo(deviceInfo);
            }
        };
    }

    public abstract void doCallback(String packageName, int navState);

    public void onInitDeviceInfo(DeviceInfo deviceInfo) {

    }
}
