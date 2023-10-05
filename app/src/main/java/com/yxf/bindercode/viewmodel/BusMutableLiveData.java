package com.yxf.bindercode.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.yxf.baselibrary.LogUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

public class BusMutableLiveData<T> extends MutableLiveData<T> {
    public static final String TAG = "BusMutableLiveData ";

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
        super.observe(owner, observer);
        hook(observer);
    }

    private void hook(Observer<? super T> observer) {
        //去粘性,订阅成功供，重置mLastVersion=mVersion
        try {
            Class<?> cls = this.getClass().getSuperclass().getSuperclass();
            Field mVersionField = cls.getDeclaredField("mVersion");
            mVersionField.setAccessible(true);
            int mVersion = (int) mVersionField.get(this);
            LogUtil.i(TAG, "mVersion:" + mVersion);
            Field mObserversField = cls.getDeclaredField("mObservers");
            mObserversField.setAccessible(true);
            Object mObserversObject = mObserversField.get(this);
            // 得到map对象的class对象   private SafeIterableMap<Observer<? super T>, ObserverWrapper> mObservers = new SafeIterableMap<>();
            Class<?> mObserversClass = mObserversObject.getClass();
            // 获取到mObservers对象的get方法
            Method get = mObserversClass.getDeclaredMethod("get", Object.class);
            get.setAccessible(true);
            // 执行get方法
            Object invokeEntry = get.invoke(mObserversObject, observer);
            // 取到entry中的value
            Object observerWrapper = null;
            if (invokeEntry != null && invokeEntry instanceof Map.Entry) {
                observerWrapper = ((Map.Entry) invokeEntry).getValue();
            }
            if (observerWrapper == null) {
                throw new NullPointerException("observerWrapper is null");
            }
            // 得到observerWrapper的类对象
            // observerWrapper.getClass() 获取的是LifecycleBoundObserver类对象
            // observerWrapper类是LifecycleBoundObserver类的父类
            Class<?> supperClass = observerWrapper.getClass().getSuperclass();
            Field mLastVersion = supperClass.getDeclaredField("mLastVersion");
            mLastVersion.setAccessible(true);
            mLastVersion.set(observerWrapper, mVersion);
            LogUtil.i(TAG, "hook success!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
