package com.yxf.carenginelib;

public enum NavState {
    ERROR,
    START_NAV,
    STOP_NAV;

    public static NavState getState(int state) {
        if (state == 1) {
            return START_NAV;
        } else if (state == 0) {
            return STOP_NAV;
        } else {
            return ERROR;
        }
    }
}
