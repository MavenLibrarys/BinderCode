package com.yxf.bindercode.reflect;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.yxf.baselibrary.LogUtil;

import java.lang.reflect.Field;

public class InjectUtil {
    public static final String TAG = "InjectUtil ";

    public static void injectView(Activity activity) {
        Class<? extends Activity> cls = activity.getClass();
        Field[] declaredFields = cls.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            Field declaredField = declaredFields[i];
            boolean annotationPresent = declaredField.isAnnotationPresent(InjectView.class);
            boolean autoWiredPresent = declaredField.isAnnotationPresent(AutoWired.class);
            declaredField.setAccessible(true);
            //自动findViewById
            if (annotationPresent) {
                InjectView injectView = declaredField.getAnnotation(InjectView.class);
                int id = injectView.value();
                View view = activity.findViewById(id);
                try {
                    declaredField.set(activity, view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            //intent传值
            if (autoWiredPresent) {
                String annotationValue = declaredField.getAnnotation(AutoWired.class).value();
                String key = !TextUtils.isEmpty(annotationValue) ? annotationValue : declaredField.getName();
                Bundle extras = activity.getIntent().getExtras();
                if (extras.containsKey(key)) {
                    Object value = extras.get(key);
                    LogUtil.i(TAG, "key: " + key + " ,value:" + value);
                    try {
                        declaredField.set(activity, value);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

    }
}
