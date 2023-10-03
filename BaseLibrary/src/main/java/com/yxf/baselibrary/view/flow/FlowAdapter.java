package com.yxf.baselibrary.view.flow;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yxf.baselibrary.DensityUtil;

import java.util.List;

public class FlowAdapter extends BaseAdapter {
    private List<String> list;

    public FlowAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(parent.getContext());
        textView.setText(list.get(position));
        int pd10 = DensityUtil.dp2px(10);
        int pd3 = DensityUtil.dp2px(3);
        textView.setPadding(pd10, pd3, pd10, pd3);
        return textView;
    }
}
