package com.p8.inspection.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.p8.inspection.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author : WX.Y
 * date : 2020/10/29 14:12
 * description : 打卡
 */
public class ClockView extends View {

    public static final float LABEL_SIZE_SCALE_OF_WIDTH = 1 / 8.0f;

    public static final float TIME_SIZE_SCALE_OF_LABEL = 4 / 5.0f;

    public static final float LABEL_Y_SCALE_OF_HEIGHT = 2 / 5.0f;

    private int width;
    private int height;

    private String[] labels = {"上班打卡", "下班打卡", "打卡成功"};

    private Paint mPaint;

    private int clockColor = Color.parseColor("#269CF3");
    private int timeColor = Color.parseColor("#FFFFFF");
    private int labelColor = Color.parseColor("#FFFFFF");
    private float labelSize;
    private float timeSize;
    private ScheduledExecutorService pool;

    private String currentTime = "00:00:00";


    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);

    public ClockView(Context context) {
        this(context, null);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ClockView);
            clockColor = a.getColor(R.styleable.ClockView_clockColor, Color.parseColor("#269CF3"));
            labelColor = a.getColor(R.styleable.ClockView_labelColor, Color.parseColor("#269CF3"));
            timeColor = a.getColor(R.styleable.ClockView_timeColor, Color.parseColor("#269CF3"));
            a.recycle();
        }
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(clockColor);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        currentTime = TimeUtils.date2String(new Date(System.currentTimeMillis()), "HH:mm:ss");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = w;
        labelSize = width * LABEL_SIZE_SCALE_OF_WIDTH;
        timeSize = labelSize * TIME_SIZE_SCALE_OF_LABEL;
        setMeasuredDimension(this.width, this.height);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //画背景圆圈
        mPaint.setColor(clockColor);
        canvas.drawCircle(width >> 1, height >> 1, width >> 1, mPaint);
        mPaint.setTextAlign(Paint.Align.CENTER);

        mPaint.setColor(labelColor);
        mPaint.setTextSize(labelSize);
        canvas.drawText(labels[0], width >> 1, height * 0.5f - labelSize * 0.6f - (mPaint.ascent() + mPaint.descent()) / 2.0f, mPaint);

        mPaint.setColor(timeColor);
        mPaint.setTextSize(timeSize);
        canvas.drawText(currentTime, width >> 1, height * 0.5f + timeSize * 0.6f - (mPaint.ascent() + mPaint.descent()) / 2.0f, mPaint);
    }

    public void startTimer() {
        if (pool == null) {
            pool = Executors.newScheduledThreadPool(1);
        }
        pool.scheduleAtFixedRate(() -> {
            currentTime = TimeUtils.date2String(new Date(System.currentTimeMillis()), "HH:mm:ss");
            invalidate();
        }, 1, 1000, TimeUnit.MILLISECONDS);
    }

    public void stopTimer() {
        if (pool != null && !pool.isTerminated()) {
            pool.shutdownNow();
        }
    }

}

