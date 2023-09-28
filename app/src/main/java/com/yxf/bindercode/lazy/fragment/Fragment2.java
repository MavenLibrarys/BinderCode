package com.yxf.bindercode.lazy.fragment;

import com.yxf.bindercode.R;

public class Fragment2 extends LazyFragment{
    @Override
    public int layoutId() {
        return R.layout.fragment_record_layout;
    }

    @Override
    public void initView() {

    }

    @Override
    public String getTAG() {
        return "Fragment2";
    }
}
