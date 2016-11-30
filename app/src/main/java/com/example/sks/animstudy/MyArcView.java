package com.example.sks.animstudy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

/**
 * Created by sks on 2016/11/29.
 */
public class MyArcView extends View {

    private String TAG = getClass().getSimpleName();
    private Paint mInternalPaint;
    private Paint mOutSidePaint;
    private float density;
    private float STROKE_WIDTH;

    public MyArcView(Context context) {
        super(context);
    }

    public MyArcView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        density = displayMetrics.density;
        STROKE_WIDTH = density * 3;

        mInternalPaint = new Paint();
        mInternalPaint.setAntiAlias(true);
        mInternalPaint.setColor(Color.TRANSPARENT);

        mOutSidePaint = new Paint();
        mOutSidePaint.setStyle(Paint.Style.STROKE);
        mOutSidePaint.setStrokeCap(Paint.Cap.ROUND);
        mOutSidePaint.setAntiAlias(true);
        mOutSidePaint.setStrokeWidth(density * 3);
        mOutSidePaint.setColor(Color.RED);
    }

    public MyArcView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw: x:" + getX());
        Log.d(TAG, "onDraw: y:" + getY());
        RectF rectF = new RectF(0 + STROKE_WIDTH, 0 + STROKE_WIDTH, getMeasuredWidth() - STROKE_WIDTH,  getMeasuredHeight() - STROKE_WIDTH);

        canvas.drawArc(rectF, -90f, 212f, false, mOutSidePaint);
    }
}
