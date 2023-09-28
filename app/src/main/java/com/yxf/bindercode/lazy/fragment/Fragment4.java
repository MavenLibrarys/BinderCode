package com.yxf.bindercode.lazy.fragment;

import com.yxf.bindercode.R;

public class Fragment4 extends LazyFragment{
    @Override
    public int layoutId() {
        return R.layout.fragment_me_layout;
    }

    @Override
    public void initView() {

    }

    @Override
    public String getTAG() {
        return "Fragment4";
    }
}
