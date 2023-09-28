package com.yxf.bindercode.hicar;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import com.yxf.baselibrary.LogUtil;
import com.yxf.bindercode.CarApplication;
import com.yxf.carenginelib.DeviceInfo;
import com.yxf.carenginelib.ICarOperatorMgr;

import java.util.concurrent.LinkedBlockingQueue;

public class ThirdAppConnector implements ServiceConnection {
    private static final String TAG = "ThirdAppConnector ";
    private ICarOperatorMgr carOperatorMgr;
    private LinkedBlockingQueue<Runnable> requestQuequ = new LinkedBlockingQueue();

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        LogUtil.i(TAG, "onServiceConnected name:" + name.getPackageName());
        carOperatorMgr = ICarOperatorMgr.Stub.asInterface(service);
        if (carOperatorMgr != null) {
            doPendingRequest();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        LogUtil.i(TAG, "onServiceDisconnected name:" + name.getPackageName());
        carOperatorMgr = null;
    }

    @Override
    public void onNullBinding(ComponentName name) {
        LogUtil.i(TAG, "onNullBinding name:" + name.getPackageName());
    }

    @Override
    public void onBindingDied(ComponentName name) {
        LogUtil.i(TAG, "onBindingDied name:" + name.getPackageName());
    }

    public boolean isConnected() {
        return carOperatorMgr != null;
    }

    public void callback(String packageName, Bundle bundle) {
        if (carOperatorMgr != null) {
            try {
                carOperatorMgr.callback(packageName, bundle);
                carOperatorMgr.initDeviceInfo(new DeviceInfo(1000, "车载智慧屏"));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void bindService(String action, String packageName, Bundle bundle) {
        if (!isNeedBindService(packageName, bundle)) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction(action);
        intent.setPackage(packageName);
        intent.putExtras(bundle);
        CarApplication.getInstance().bindService(intent, this, Context.BIND_AUTO_CREATE);
    }

    private boolean isNeedBindService(String packageName, Bundle bundle) {
        if (isConnected()) {
            callback(packageName, bundle);
            return false;
        }
        LogUtil.i(TAG, "isNeedBindService package:" + packageName);
        requestQuequ.offer(() -> callback(packageName, bundle));
        return true;
    }

    private void doPendingRequest() {
        LogUtil.i(TAG, "doPendingRequest size:" + requestQuequ.size());
        for (Runnable runnable : requestQuequ) {
            runnable.run();
        }
        requestQuequ.clear();
    }
}
