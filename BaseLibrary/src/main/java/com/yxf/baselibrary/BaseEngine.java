package com.yxf.baselibrary;

import android.content.Context;

public class BaseEngine {
    private Context mContext;
    private static BaseEngine instance = new BaseEngine();

    public static BaseEngine getInstance() {
        return instance;
    }

    public void init(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }
}
