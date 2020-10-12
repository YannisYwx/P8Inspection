package com.p8.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import com.p8.common.R;

/**
 * author : WX.Y
 * date : 2020/10/12 11:10
 * description :
 */
public class CommonItemView extends ConstraintLayout {

    private TextView tvLabel;
    private ImageView ivIcon, ivArrow;
    private View vLine;

    private String label;
    private Drawable icon, arrow;
    private boolean hasBottomLine;

    private int paddingLeft = 0;
    private int paddingRight = 0;
    private int dividerColor = Color.parseColor("#E3E3E3");
    private float dividerHeight = getResources().getDimension(R.dimen.dividerHeight);
    private Paint mPaint;

    public CommonItemView(@NonNull Context context) {
        this(context, null);
    }

    public CommonItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        View.inflate(context, R.layout.view_common_item, this);

        //读取属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CommonItemView);
        label = typedArray.getString(R.styleable.CommonItemView_label);
        hasBottomLine = typedArray.getBoolean(R.styleable.CommonItemView_hasBottomLine, true);
        dividerColor = typedArray.getColor(R.styleable.CommonItemView_dividerColor, Color.parseColor("#E3E3E3"));
        dividerHeight = typedArray.getDimension(R.styleable.CommonItemView_dividerHeight, getResources().getDimension(R.dimen.dividerHeight));
        icon = typedArray.getDrawable(R.styleable.CommonItemView_icon);
        arrow = typedArray.getDrawable(R.styleable.CommonItemView_arrow);
        paddingLeft = (int) typedArray.getDimension(R.styleable.CommonItemView_dividerPaddingLeft, 0);
        paddingRight = (int) typedArray.getDimension(R.styleable.CommonItemView_dividerPaddingRight, 0);
        typedArray.recycle();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(dividerColor);
        mPaint.setStrokeWidth(dividerHeight);
    }

    @Override
    protected void onFinishInflate() {
        setDataToUI();
        super.onFinishInflate();
    }

    private void setDataToUI() {
        tvLabel.setText(TextUtils.isEmpty(label) ? "" : label);

        if (icon != null) {
            ivIcon.setImageDrawable(icon);
            ivIcon.setVisibility(View.VISIBLE);
        } else {
            ivIcon.setVisibility(View.GONE);
        }

        if (arrow != null) {
            ivArrow.setImageDrawable(arrow);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (hasBottomLine) {
            canvas.drawLine(paddingLeft, getMeasuredHeight(), getMeasuredWidth() - paddingRight, getMeasuredHeight(), mPaint);
        }
    }

    public void setData(int iconRes, int arrowRes, String label, boolean hasShowBottomLine) {
        if (iconRes > 0)
            this.icon = ResourcesCompat.getDrawable(getResources(), iconRes, null);
        if (arrowRes > 0)
            this.arrow = ResourcesCompat.getDrawable(getResources(), arrowRes, null);
        this.hasBottomLine = hasShowBottomLine;
        if (label != null)
            this.label = label;
        setDataToUI();
        invalidate();
    }

    public void setLabel(String label) {
        setData(-1, -1, label, true);
    }

    public void setIcon(@DrawableRes int iconRes) {
        setData(iconRes, -1, null, true);
    }

    public void setArrow(@DrawableRes int arrowRes) {
        setData(arrowRes, arrowRes, null, true);
    }

    public void showBottomLine(boolean isShow) {
        setData(-1, -1, null, isShow);
    }
}

