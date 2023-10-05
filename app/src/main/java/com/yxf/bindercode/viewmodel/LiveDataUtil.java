package com.yxf.bindercode.viewmodel;

public class LiveDataUtil {
    private static final LiveDataUtil instance = new LiveDataUtil();

    public static LiveDataUtil getInstance() {
        return instance;
    }

    public BusMutableLiveData<String> data = new BusMutableLiveData<>();
}
