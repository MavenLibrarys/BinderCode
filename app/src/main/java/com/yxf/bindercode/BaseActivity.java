package com.yxf.bindercode;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity {
    public T binding;

    public abstract int layoutID();


    public void init() {
    }

    public void initView() {
        TextView txtTitle = findViewById(R.id.txtTitle);
        if (txtTitle != null) {
            txtTitle.setText(getTopTxt());
        }

    }

    public void initData() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutID = layoutID();
        if (layoutID != 0) {
            binding = DataBindingUtil.setContentView(this, layoutID);
        }
        init();
        initView();
        initData();
    }

    public String getTopTxt() {
        return " ";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
