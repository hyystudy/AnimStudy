package com.example.sks.animstudy;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

/**
 * Created by sks on 2016/11/30.
 */
public class CircleProgress extends View {

    private static final String TAG = "CircleProgress";
    private Paint mBgCirclePaint, mProgressPaint;
    private Context mContext;
    private float DEFAULT_BG_CIRCLE_WIDTH = 6, DEFAULT_PROGRESS_WIDTH = 4;
    private float DEFAULT_SWEEP_ANGLE = 240;
    private float DEFAULT_PERCENT_TEXT_SIZE = 25;
    private float mBgCircleWidth, mProgressWidth;
    private int mBgCircleColor, mProgressColor, mPercentTextColor;
    private float mStartAngle, mSweepAngle, mProgress;
    private float mPercentTextSize;
    private RectF mCircleBounds;
    private boolean mShowPercentText, mShowPercent;
    private Paint mTextPaint;

    public CircleProgress(Context context) {
        this(context, null);
        initView(null);
    }

    public CircleProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public CircleProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        mContext = getContext();
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        float density = displayMetrics.density;

        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.CircleProgress);
        mBgCircleWidth = a.getDimension(R.styleable.CircleProgress_bg_circular_width, DEFAULT_BG_CIRCLE_WIDTH * density);
        mProgressWidth = a.getDimension(R.styleable.CircleProgress_progress_width, DEFAULT_PROGRESS_WIDTH * density);
        mBgCircleColor = a.getColor(R.styleable.CircleProgress_bg_circular_color, Color.parseColor("#33ffffff"));
        mProgressColor = a.getColor(R.styleable.CircleProgress_progress_color, Color.RED);
        mSweepAngle = a.getFloat(R.styleable.CircleProgress_sweep_angle, DEFAULT_SWEEP_ANGLE);
        mStartAngle = a.getFloat(R.styleable.CircleProgress_start_angle,-(mSweepAngle / 2 + 90f));
        mProgress = a.getFloat(R.styleable.CircleProgress_progress, 0.6f);
        mShowPercentText = a.getBoolean(R.styleable.CircleProgress_show_percent_text, true);
        mShowPercent = a.getBoolean(R.styleable.CircleProgress_show_percent, true);
        mPercentTextSize = a.getDimension(R.styleable.CircleProgress_percent_text_size, dp2px(DEFAULT_PERCENT_TEXT_SIZE));
        mPercentTextColor = a.getColor(R.styleable.CircleProgress_percent_text_color, Color.WHITE);
        a.recycle();

        //初始化背景圆弧 画笔
        mBgCirclePaint = new Paint();
        mBgCirclePaint.setAntiAlias(true);
        mBgCirclePaint.setStyle(Paint.Style.STROKE);
        mBgCirclePaint.setStrokeCap(Paint.Cap.ROUND);
        mBgCirclePaint.setStrokeWidth(mBgCircleWidth);
        mBgCirclePaint.setColor(mBgCircleColor);

        //初始化progress 的画笔
        mProgressPaint = new Paint();
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setStrokeCap(Paint.Cap.ROUND);
        mProgressPaint.setStrokeWidth(mProgressWidth);
        mProgressPaint.setColor(mProgressColor);

        //初始化 text 的画笔
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mPercentTextColor);
        mTextPaint.setTextSize(mPercentTextSize);

        //初始化 画圆弧 的矩形区域
        mCircleBounds = new RectF();
    }

    private int dp2px(float dp) {
        float density = getResources().getDisplayMetrics().density;

        return (int) (dp * density + .5f);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCircleBounds.left = mBgCircleWidth/ 2 + .5f ;
        mCircleBounds.right = w - mBgCircleWidth / 2 - .5f;
        mCircleBounds.top = mBgCircleWidth / 2 + .5f;
        mCircleBounds.bottom = h - mBgCircleWidth / 2 - .5f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawArc(mCircleBounds, mStartAngle, mSweepAngle, false, mBgCirclePaint);
        canvas.drawArc(mCircleBounds, mStartAngle, mSweepAngle * mProgress, false, mProgressPaint);
        if (mShowPercentText) {//是否显示 文字
            String text = mShowPercent ? getProgressStringWithPercent() : getProgressStringWithOutPercent();
            float x = (getWidth() - mTextPaint.measureText(text)) / 2;
            //y 指的是 baseline 的 高度
            float baseline = (getHeight() - getTextHeight()) / 2 + getTextLeading();
            Log.d(TAG, "onDraw: height" + getHeight());
            Log.d(TAG, "onDraw: text height" + getHeight());
            Log.d(TAG, "onDraw: baseline" + baseline);
            canvas.drawText(text, x, baseline, mTextPaint);
        }
    }

    private float getTextHeight() {
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        Log.d(TAG, "getTextHeight: ascent " + fontMetrics.ascent);
        Log.d(TAG, "getTextHeight: descent " + fontMetrics.descent);
        Log.d(TAG, "getTextHeight: leading " + fontMetrics.leading);
        Log.d(TAG, "getTextHeight: bottom " + fontMetrics.bottom);
        Log.d(TAG, "getTextHeight: top " + fontMetrics.top);
        return fontMetrics.descent - fontMetrics.ascent;
    }

    private float getTextLeading(){
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        return fontMetrics.leading - fontMetrics.ascent;
    }

    private String getProgressStringWithOutPercent() {
        int progress = mProgress < 1f ? (int) (mProgress * 100) : 100;
        return String.valueOf(progress);
    }

    private String getProgressStringWithPercent() {
        String text;
        int progress = mProgress < 1f ? (int) (mProgress * 100) : 100;
        text = progress + "%";
        return text;
    }

    public void setBgCircleColor(int bgCircleColor) {
        this.mBgCircleColor = bgCircleColor;
        mBgCirclePaint.setColor(mBgCircleColor);
        invalidate();
    }

    public void setProgressColor(int progressColor) {
        this.mProgressColor = progressColor;
        mProgressPaint.setColor(mProgressColor);
        invalidate();
    }

    public void setBgCircleWidth(float bgCircleWidth) {
        this.mBgCircleWidth = bgCircleWidth;
        mBgCirclePaint.setStrokeWidth(bgCircleWidth);
        invalidate();
    }

    public void setProgressWidth(int width) {
        this.mProgressWidth = width;
        mProgressPaint.setStrokeWidth(width);
        invalidate();
    }

    public void setProgress(float progress) {
        this.mProgress = progress;
        invalidate();
    }
}
