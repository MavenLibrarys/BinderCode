package com.yxf.bindercode.lazy.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yxf.baselibrary.LogUtil;

public abstract class LazyFragment extends Fragment {
    public View rootView = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i(getTAG(), "onCreate.");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = LayoutInflater.from(getContext()).inflate(layoutId(), container, false);
        }
        initView();
        //先显示空白页面
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.i(getTAG(), "onResume.");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.i(getTAG(), "onPause.");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtil.i(getTAG(), "setUserVisibleHint isVisibleToUser:" + isVisibleToUser);
        dispatchUserVisibleHint(isVisibleToUser);
    }

    public void dispatchUserVisibleHint(boolean isVisibleToUser) {

    }

    public abstract int layoutId();

    public abstract void initView();

    public abstract String getTAG();
}
