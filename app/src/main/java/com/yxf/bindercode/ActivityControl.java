package com.yxf.bindercode;

import android.app.Activity;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ActivityControl {
    private static final ActivityControl instance = new ActivityControl();
    private List<Activity> activities = new CopyOnWriteArrayList<>();

    public static ActivityControl getInstance() {
        return instance;
    }

    public void pushActivity(Activity activity) {
        if (activity == null) {
            return;
        }
        activities.add(activity);
    }

    public void popActivity(Activity activity) {
        if (activity == null) {
            return;
        }
        activities.remove(activity);
    }

    public void finishAll() {
        if (activities == null || activities.size() == 0) {
            return;
        }
        for (Activity activity : activities) {
            activity.finish();
        }
        activities.clear();
    }

}
