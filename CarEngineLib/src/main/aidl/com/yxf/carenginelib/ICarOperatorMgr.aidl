// ICarOperatorMgr.aidl
package com.yxf.carenginelib;
import com.yxf.carenginelib.DeviceInfo;

// Declare any non-default types here with import statements

interface ICarOperatorMgr {
    void callback(String packageName,in Bundle bundle);

    void initDeviceInfo(in DeviceInfo deviceInfo);
}