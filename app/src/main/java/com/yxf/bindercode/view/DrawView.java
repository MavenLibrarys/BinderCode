package com.yxf.bindercode.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.yxf.baselibrary.LogUtil;

public class DrawView extends View {
    public static final String TAG = "DrawView";

    private float preX;
    private float preY;

    private Paint mPaint;
    private Path mPath;

    private int width;
    private int height;

    private Bitmap cacheBitmap;
    private Canvas cacheCanvas = new Canvas();

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.parseColor("#333333"));
        mPath = new Path();
    }

    private void initCacheBitmap() {
        cacheBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        cacheCanvas.setBitmap(cacheBitmap);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        LogUtil.w(TAG, " width:" + width + " ,height=" + height);
        initCacheBitmap();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.w(TAG, "======>onTouchEvent");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                preX = event.getX();
                preY = event.getY();
                mPath.moveTo(preX, preY);
                break;
            case MotionEvent.ACTION_MOVE:
                preX = event.getX();
                preY = event.getY();
                mPath.lineTo(preX, preY);
                break;
            case MotionEvent.ACTION_UP:
                preX = 0;
                preY = 0;
                cacheCanvas.drawPath(mPath, mPaint);
                mPath.reset();
                break;
        }
        invalidate();
        return true;
    }

    public void erase() {
        mPath.reset();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LogUtil.w(TAG, "======>onDraw");
        canvas.drawBitmap(cacheBitmap, 0, 0, mPaint);
        canvas.drawPath(mPath, mPaint);
    }

}
