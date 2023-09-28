package com.yxf.bindercode.hicar;

import android.os.Bundle;
import android.text.TextUtils;

import com.yxf.baselibrary.LogUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ThirdAppConnectorMgr {
    private static final String TAG = "ThirdAppConnectorMgr ";
    private static final ThirdAppConnectorMgr instance = new ThirdAppConnectorMgr();
    private Map<String, ThirdAppConnector> connectorMap = new ConcurrentHashMap<>();

    public static ThirdAppConnectorMgr getInstance() {
        return instance;
    }

    private ThirdAppConnector getThirdConnector(String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            LogUtil.i(TAG, "packageName is null.");
            return null;
        }
        if (!connectorMap.containsKey(packageName)) {
            connectorMap.put(packageName, new ThirdAppConnector());
        }
        return connectorMap.get(packageName);
    }

    public void callback(String action, String packageName, Bundle bundle) {
        ThirdAppConnector thirdConnector = getThirdConnector(packageName);
        LogUtil.i(TAG, "callback connector :" + thirdConnector);
        if (thirdConnector.isConnected()) {
            thirdConnector.callback(packageName, bundle);
            return;
        }
        thirdConnector.bindService(action, packageName, bundle);
    }

    public void destroy() {
        connectorMap.clear();
    }
}
