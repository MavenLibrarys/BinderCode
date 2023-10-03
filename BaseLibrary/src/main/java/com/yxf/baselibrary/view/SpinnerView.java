package com.yxf.baselibrary.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yxf.baselibrary.DensityUtil;
import com.yxf.baselibrary.LogUtil;
import com.yxf.baselibrary.R;

import java.util.List;

public class SpinnerView extends androidx.appcompat.widget.AppCompatTextView {
    public static final String TAG = "SpinnerView ";
    private PopupWindow popupWindow;
    private List<String> list;
    private int mWidth;
    private int mHeight;
    private int maxSpinnerHeight;

    public void setList(List<String> list) {
        this.list = list;
        initPopup();

    }

    public SpinnerView(@NonNull Context context) {
        super(context);
    }

    public SpinnerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setClickable(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (list != null) {
            maxSpinnerHeight = list.size() * mHeight;
        }
        if (popupWindow != null) {
            popupWindow.setWidth(mWidth);
            popupWindow.setHeight(maxSpinnerHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int screenHeight = DensityUtil.getScreenHeight();
        LogUtil.i(TAG, "onLayout top:" + top + " ,bottom:" + bottom + " ,screenHeight:" + screenHeight);
        maxSpinnerHeight = screenHeight - bottom;
        if (popupWindow != null) {
            popupWindow.setWidth(mWidth);
            popupWindow.setHeight(maxSpinnerHeight);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            LogUtil.i(TAG, "onTouchEvent expand.");
            expand();
        }
        return super.onTouchEvent(event);
    }

    private void expand() {
        if (popupWindow == null) {
            initPopup();
        }
        popupWindow.showAsDropDown(this);
    }

    private void initPopup() {
        popupWindow = new PopupWindow();
        RecyclerView recyclerView = new RecyclerView(getContext());
        recyclerView.addItemDecoration(new MyItemDecoration());
        recyclerView.setBackgroundColor(Color.parseColor("#f5f5f5"));
        popupWindow.setContentView(recyclerView);
        recyclerView.setAdapter(new SpinnerAdapter(list));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    private class SpinnerAdapter extends RecyclerView.Adapter<SpinnerAdapter.VH> {
        private List<String> data;

        public SpinnerAdapter(List<String> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.ms__list_item, parent, false);
            return new VH(view);
        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, int position) {
            holder.textView.setText(list.get(position));
            holder.textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mHeight));
        }

        @Override
        public int getItemCount() {
            return data != null ? data.size() : 0;
        }

        private class VH extends RecyclerView.ViewHolder {
            TextView textView;

            public VH(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.tv_tinted_spinner);
                textView.setBackgroundColor(Color.parseColor("#dddddd"));
            }
        }
    }

    private class MyItemDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(15, 10, 15, 0);
        }
    }

}
