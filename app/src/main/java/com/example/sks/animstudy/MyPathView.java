package com.example.sks.animstudy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by sks on 2016/11/30.
 */
public class MyPathView extends LinearLayout {

    private static final String TAG = "MyPathView";
    private Path mPath;
    private Paint paint;
    private RectF rectF;
    private float density;
    private final int DEFAULT_TRIANGLE_HEIGHT = 6;

    public MyPathView(Context context) {
        this(context, null);
    }

    public MyPathView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyPathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        density = getResources().getDisplayMetrics().density;

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        //paint.setStyle(Paint.Style.STROKE);
        //paint.setStrokeWidth(6);


        mPath = new Path();

        rectF = new RectF();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "onSizeChanged: ");
        rectF.left = 0;
        rectF.right = w;
        rectF.top = getPaddingTop();
        rectF.bottom = h - getPaddingBottom() - DEFAULT_TRIANGLE_HEIGHT * 3;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {

        caculatePath();
        canvas.drawRoundRect(rectF, DEFAULT_TRIANGLE_HEIGHT, DEFAULT_TRIANGLE_HEIGHT, paint);
        canvas.drawPath(mPath, paint);
        Log.d(TAG, "dispatchDraw: ");
        super.dispatchDraw(canvas);

    }

    private void caculatePath() {
        mPath.moveTo(rectF.width()/ 3 - density * DEFAULT_TRIANGLE_HEIGHT, rectF.bottom);
        mPath.lineTo(rectF.width()/ 3, rectF.bottom + density * DEFAULT_TRIANGLE_HEIGHT);
        mPath.lineTo(rectF.width()/ 3 + density * DEFAULT_TRIANGLE_HEIGHT, rectF.bottom);
        mPath.close();
    }

    //值得注意的是ViewGroup容器组件的绘制，当它没有背景时直接调用的是dispatchDraw()方法,
    //而绕过了draw()方法，当它有背景的时候就调用draw()方法，而draw()方法里包含了dispatchDraw()方法的调用
    //因此要在ViewGroup上绘制东西的时候往往重写的是dispatchDraw()方法而不是onDraw()方法
    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw: ");
        super.onDraw(canvas);
    }
}
