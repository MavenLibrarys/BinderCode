package com.yxf.baidumap.hicar;

import com.yxf.baselibrary.LogUtil;
import com.yxf.carenginelib.AbstractCarOperatorService;
import com.yxf.carenginelib.DeviceInfo;
import com.yxf.carenginelib.NavState;

public class BaiDuHicarOperatorService extends AbstractCarOperatorService {
    private static final String TAG = "BaiDuHicarOperatorService ";

    @Override
    public void doCallback(String packageName, int navState) {
        LogUtil.i(TAG, "package:" + packageName + " ,navState:" + navState);
        NavState state = NavState.getState(navState);
        if (state == NavState.START_NAV) {
            LogUtil.i(TAG, "百度地图发起导航。");
        } else if (state == NavState.STOP_NAV) {
            LogUtil.i(TAG, "百度地图停止导航。");
        } else {
            LogUtil.i(TAG, "参数有误，联系客户方.");
        }
    }

    @Override
    public void onInitDeviceInfo(DeviceInfo deviceInfo) {
        super.onInitDeviceInfo(deviceInfo);
        LogUtil.i(TAG, "百度地图deviceInfo:" + deviceInfo.getDeviceName());
    }
}
