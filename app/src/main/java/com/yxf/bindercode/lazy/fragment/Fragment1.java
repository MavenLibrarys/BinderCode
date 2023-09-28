package com.yxf.bindercode.lazy.fragment;

import com.yxf.baselibrary.LogUtil;
import com.yxf.bindercode.R;

public class Fragment1 extends LazyFragment {
    @Override
    public int layoutId() {
        return R.layout.fragment_home_layout;
    }

    @Override
    public void initView() {
        rootView.setOnClickListener(v -> {
            LogUtil.i(getTAG(), "--------01");
        });
    }

    @Override
    public String getTAG() {
        return "Fragment1";
    }
}
