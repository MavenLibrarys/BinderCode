package com.yxf.baselibrary.view.flow;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yxf.baselibrary.DensityUtil;
import com.yxf.baselibrary.LogUtil;
import com.yxf.baselibrary.R;
import com.yxf.baselibrary.utils.DrawableUtil;

import java.util.HashSet;
import java.util.Set;

public class FlowView extends ViewGroup {
    private static final String TAG = "FlowView ";
    private int mWidth;
    private int mHeight;
    private int marginLeft = DensityUtil.dp2px(10);
    private int marginTop = DensityUtil.dp2px(8);
    private int selectBgColor = Color.parseColor("#f08080");
    private int defaultBgColor = Color.parseColor("#dddddd");
    private int textDefaultColor = Color.parseColor("#333333");
    private int textSelectColor = Color.parseColor("#ffffff");
    private FlowAdapter flowAdapter;
    private Set<Integer> selectIdxSet = new HashSet<>();
    private IFlowSelectCallback flowSelectCallback;

    public FlowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setFlowSelectCallback(IFlowSelectCallback flowSelectCallback) {
        this.flowSelectCallback = flowSelectCallback;
    }

    public void setFlowAdapter(FlowAdapter adapter) {
        flowAdapter = adapter;
        if (flowAdapter != null) {
            flowAdapter.registerDataSetObserver(new FlowObserver());
        }
        addChildrenView();
    }

    public Set<Integer> getSelectIdxSet() {
        return selectIdxSet;
    }

    public void addChildrenView() {
        removeAllViews();
        if (flowAdapter == null) {
            LogUtil.i(TAG, "flowAdapter is null!!!");
            return;
        }
        for (int i = 0; i < flowAdapter.getCount(); i++) {
            View view = flowAdapter.getView(i, null, this);
            addView(view);
            int finalI = i;
            updateItemBg(view, selectIdxSet.contains(finalI));
            view.setOnClickListener(v -> {
                if (!selectIdxSet.contains(finalI)) {
                    selectIdxSet.add(finalI);
                } else {
                    selectIdxSet.remove(finalI);
                }
                updateItemBg(view, selectIdxSet.contains(finalI));
                if (flowSelectCallback != null) {
                    flowSelectCallback.onSelectList(selectIdxSet);
                }
            });
        }
    }

    private void updateItemBg(View view, boolean isSelected) {
        Drawable drawable = getResources().getDrawable(R.drawable.item_flow_shape);
        view.setBackground(DrawableUtil.tint(drawable, isSelected ? selectBgColor : defaultBgColor));
        ((TextView) view).setTextColor(isSelected ? textSelectColor : textDefaultColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        int childRow = 0;
        int tempW = 0;
        for (int i = 0; i < getChildCount(); i++) {
            if (i == 0) {
                childRow++;
            }
            int width = getChildAt(i).getMeasuredWidth();
            if (tempW + width + marginLeft > mWidth) {
                //换行
                childRow++;
                tempW = width + marginLeft;
            } else {
                tempW = tempW + width + marginLeft;
            }
        }
        if (childRow > 0) {
            mHeight = childRow * getChildAt(0).getMeasuredHeight();
        } else {
            mHeight = 0;
        }
        LogUtil.i(TAG, "onMeasure childRow:" + childRow + " ,mHeight:" + mHeight);
        setMeasuredDimension(mWidth, mHeight + marginTop + 20);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = 0;
        int top = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int childW = child.getMeasuredWidth();
            int childH = child.getMeasuredHeight();

            if (left + childW + marginLeft > mWidth) {
                //换行
                child.layout(marginLeft, top + childH + 2 * marginTop, marginLeft + childW, top + 2 * (childH + marginTop));
                left = marginLeft + childW;
                top = top + childH + marginTop;
            } else {
                child.layout(left + marginLeft, top + marginTop, left + childW + marginLeft, top + childH + marginTop);
                left = left + childW + marginLeft;
            }
        }
    }

    public interface IFlowSelectCallback {
        void onSelectList(Set<Integer> set);
    }

    private class FlowObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            LogUtil.i(TAG, "onChanged.");
            addChildrenView();
        }
    }

}
