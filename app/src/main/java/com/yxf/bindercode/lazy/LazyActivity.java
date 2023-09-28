package com.yxf.bindercode.lazy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.yxf.bindercode.MainActivity;
import com.yxf.bindercode.R;
import com.yxf.bindercode.lazy.fragment.Fragment1;
import com.yxf.bindercode.lazy.fragment.Fragment2;
import com.yxf.bindercode.lazy.fragment.Fragment3;
import com.yxf.bindercode.lazy.fragment.Fragment4;

import java.util.ArrayList;
import java.util.List;

public class LazyActivity extends AppCompatActivity {
    private ViewPager2 viewpager;
    private HomeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lazy);
        viewpager = findViewById(R.id.idViewPager);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new Fragment1());
        fragments.add(new Fragment2());
        fragments.add(new Fragment3());
        fragments.add(new Fragment4());
        adapter = new HomeAdapter(this, fragments);
        viewpager.setAdapter(adapter);
    }

}