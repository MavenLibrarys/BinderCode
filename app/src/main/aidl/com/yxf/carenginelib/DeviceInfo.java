package com.yxf.carenginelib;

import android.os.Parcel;
import android.os.Parcelable;

public class DeviceInfo implements Parcelable {
    private int deviceId;
    private String deviceName;

    public DeviceInfo(int deviceId, String deviceName) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
    }

    protected DeviceInfo(Parcel in) {
        deviceId = in.readInt();
        deviceName = in.readString();
    }

    public int getDeviceId() {
        return deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public static final Creator<DeviceInfo> CREATOR = new Creator<DeviceInfo>() {
        @Override
        public DeviceInfo createFromParcel(Parcel in) {
            return new DeviceInfo(in);
        }

        @Override
        public DeviceInfo[] newArray(int size) {
            return new DeviceInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(deviceId);
        dest.writeString(deviceName);
    }
}
