package com.example.sks.animstudy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by sks on 2016/11/30.
 */
public class MyPathView extends LinearLayout {

    private Path mPath;
    private Paint paint;

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
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(6);
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPath.lineTo(200, 200);
        canvas.drawPath(mPath, paint);
    }
}
