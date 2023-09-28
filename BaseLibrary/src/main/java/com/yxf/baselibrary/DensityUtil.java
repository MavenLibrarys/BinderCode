package com.yxf.baselibrary;

import android.content.res.Resources;
import android.util.DisplayMetrics;

public class DensityUtil {
    public static int getScreenWidth() {
        Resources resources = BaseEngine.getInstance().getContext().getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    public static int getScreenHeight() {
        Resources resources = BaseEngine.getInstance().getContext().getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    public static int px2dp(int px) {
        Resources resources = BaseEngine.getInstance().getContext().getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        float density = displayMetrics.density;
        return (int) (px / density + 0.5f);
    }

    public static int dp2px(int dp) {
        Resources resources = BaseEngine.getInstance().getContext().getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        float density = displayMetrics.density;
        return (int) (dp * density + 0.5f);
    }

}
