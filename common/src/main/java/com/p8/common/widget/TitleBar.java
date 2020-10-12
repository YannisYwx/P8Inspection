package com.p8.common.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import com.blankj.utilcode.util.BarUtils;
import com.p8.common.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * author : WX.Y
 * date : 2020/9/11 11:28
 * description :
 */
public class TitleBar extends RelativeLayout implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private ImageView ivLeft;
    private ImageView ivRight;
    private ImageView ivRTRight;
    private TextView tvTitle;
    private TextView tvLeft;
    private TextView tvRight;
    private RadioGroup rgGroup;
    private RadioButton rbLeft, rbRight;
    private Drawable leftDrawable, rightDrawable, rtRightDrawable;
    private String title, leftBtnText, rightBtnText, leftText, rightText;
    private int centerMode, rightIconVisibility, rtRightIconVisibility;
    private int titleColor, leftTextColor, rightTextColor;
    private OnEventTriggerListener mListener;

    @IntDef({Event.textLeft, Event.textRight, Event.textTitle, Event.imageLeft, Event.imageRight, Event.imageRight2, Event.buttonLeft, Event.buttonRight})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Event {
        int textLeft = 0;
        int textRight = 1;
        int textTitle = 2;
        int imageLeft = 3;
        int imageRight = 4;
        int imageRight2 = 5;
        int buttonLeft = 6;
        int buttonRight = 7;
    }

    public interface OnEventTriggerListener {
        void onEventTrigger(@Event int type);
    }

    public void setOnEventTriggerListener(@NonNull OnEventTriggerListener listener) {
        this.mListener = listener;
    }

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.view_title_bar_c, this, true);
        if (attrs == null) {
            return;
        }
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        leftDrawable = a.getDrawable(R.styleable.TitleBar_leftDrawable);
        rightDrawable = a.getDrawable(R.styleable.TitleBar_rightDrawable);
        rtRightDrawable = a.getDrawable(R.styleable.TitleBar_rtRightDrawable);
        title = a.getString(R.styleable.TitleBar_centerTitle);
        leftBtnText = a.getString(R.styleable.TitleBar_leftBtnText);
        rightBtnText = a.getString(R.styleable.TitleBar_rightBtnText);
        leftText = a.getString(R.styleable.TitleBar_lfText);
        rightText = a.getString(R.styleable.TitleBar_rtText);
        centerMode = a.getInt(R.styleable.TitleBar_centerMode, 0);
        titleColor = a.getColor(R.styleable.TitleBar_titleColor, Color.parseColor("#333333"));
        leftTextColor = a.getColor(R.styleable.TitleBar_leftTextColor, Color.parseColor("#666666"));
        rightTextColor = a.getColor(R.styleable.TitleBar_rightTextColor, Color.parseColor("#666666"));
        rightIconVisibility = a.getInt(R.styleable.TitleBar_rightIconVisibility, 0);
        rtRightIconVisibility = a.getInt(R.styleable.TitleBar_rtRightIconVisibility, 0);
        a.recycle();
    }

    private void setPaddingStatusBar(){
        setPadding(0,getStatusBarHeight(),0,0);
    }

    public void setLeftVisibility(boolean isVisible) {
        if (!isVisible) {
            ivLeft.setVisibility(View.GONE);
        }
    }

    public void setButtonChecked(boolean isLeft) {
        rbLeft.setChecked(isLeft);
        rbRight.setChecked(!isLeft);
        invalidate();
    }

    @Override
    protected void onFinishInflate() {
        ivLeft = findViewById(R.id.ib_titleBar_left);
        ivRight = findViewById(R.id.ib_titleBar_right);
        ivRTRight = findViewById(R.id.ib_titleBar_right_ot);
        tvTitle = findViewById(R.id.tv_titleBar_title);
        tvLeft = findViewById(R.id.tv_titleBar_left);
        tvRight = findViewById(R.id.tv_titleBar_Right);
        rgGroup = findViewById(R.id.rg_titleBar);
        rbLeft = findViewById(R.id.rb_titleBar_left);
        rbRight = findViewById(R.id.rb_titleBar_right);
        tvTitle.setTextColor(titleColor);
        tvLeft.setTextColor(leftTextColor);
        tvRight.setTextColor(rightTextColor);
        if (!TextUtils.isEmpty(leftText)) {
            tvLeft.setText(leftText);
        } else {
            tvLeft.setVisibility(GONE);
        }
        if (!TextUtils.isEmpty(rightText)) {
            tvRight.setText(rightText);
        } else {
            tvRight.setVisibility(GONE);
        }

        if (leftDrawable != null) {
            ivLeft.setVisibility(VISIBLE);
            ivLeft.setImageDrawable(leftDrawable);
        } else {
            ivLeft.setVisibility(GONE);
        }

        if (rightDrawable != null && rightIconVisibility == 0) {
            ivRight.setVisibility(VISIBLE);
            ivRight.setImageDrawable(rightDrawable);
        } else {
            ivRight.setVisibility(GONE);
        }

        if (rtRightDrawable != null && rtRightIconVisibility == 0) {
            ivRTRight.setVisibility(VISIBLE);
            ivRTRight.setImageDrawable(rtRightDrawable);
        } else {
            ivRTRight.setVisibility(GONE);
        }

        if (centerMode == 0) {
            rgGroup.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(title)) {
                tvTitle.setText(title);
            } else {
                tvTitle.setVisibility(GONE);
            }
        } else {
            rgGroup.setVisibility(View.VISIBLE);
            rbLeft.setText(TextUtils.isEmpty(leftBtnText) ? "" : leftBtnText);
            rbRight.setText(TextUtils.isEmpty(rightBtnText) ? "" : rightBtnText);
        }

        rgGroup.setOnCheckedChangeListener(this);

        ivLeft.setOnClickListener(this);
        ivRight.setOnClickListener(this);
        ivRTRight.setOnClickListener(this);
        tvLeft.setOnClickListener(this);
        tvRight.setOnClickListener(this);
        tvTitle.setOnClickListener(this);
        setPaddingStatusBar();
        super.onFinishInflate();
    }

    public TextView getRightTextView() {
        return tvRight;
    }

    public TextView getLeftTextView() {
        return tvLeft;
    }

    public ImageView getLeftImageView() {
        return ivLeft;
    }

    public ImageView getRightImageView() {
        return ivRight;
    }

    public TextView getTitleView() {
        return tvTitle;
    }

    public RadioGroup getCenterGroup() {
        return rgGroup;
    }

    public String getTitle() {
        return title;
    }

    public int getCenterMode() {
        return centerMode;
    }

    public void setRTRightVisible(boolean isVisible) {
        if (ivRTRight != null) {
            if (isVisible) {
                ivRTRight.setVisibility(View.VISIBLE);
            } else {
                ivRTRight.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void setRightImageViewRes(@DrawableRes int ivRes) {
        ivRight.setVisibility(VISIBLE);
        ivRight.setImageResource(ivRes);
        invalidate();
    }

    @Override
    public void onClick(View view) {
        if (mListener == null) {
            return;
        }
        int id = view.getId();
        if (id == R.id.tv_titleBar_left) {
            mListener.onEventTrigger(Event.textLeft);
        } else if (id == R.id.tv_titleBar_Right) {
            mListener.onEventTrigger(Event.textRight);
        } else if (id == R.id.tv_titleBar_title) {
            mListener.onEventTrigger(Event.textTitle);
        } else if (id == R.id.ib_titleBar_left) {
            mListener.onEventTrigger(Event.imageLeft);
        } else if (id == R.id.ib_titleBar_right) {
            mListener.onEventTrigger(Event.imageRight);
        } else if (id == R.id.ib_titleBar_right_ot) {
            mListener.onEventTrigger(Event.imageRight2);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
        if (checkedId == R.id.rb_titleBar_left) {
            rbLeft.setBackground(ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.shape_toggle_btn_fg_right, null));
            rbRight.setBackground(ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.shape_toggle_btn_fg_normal, null));
            if (mListener == null) {
                return;
            }
            mListener.onEventTrigger(Event.buttonLeft);

        } else if (checkedId == R.id.rb_titleBar_right) {
            rbRight.setBackground(ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.shape_toggle_btn_fg_left, null));
            rbLeft.setBackground(ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.shape_toggle_btn_fg_normal, null));
            if (mListener == null) {
                return;
            }
            mListener.onEventTrigger(Event.buttonRight);
        }
    }

    public void setEmptyTitle() {
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) this.getLayoutParams();
        params.height = getStatusBarHeight();
        this.setLayoutParams(params);
    }

    public void setLeftText(@NonNull String text) {
        ivLeft.setVisibility(GONE);
        tvLeft.setVisibility(VISIBLE);
        tvLeft.setText(text);
        invalidate();
    }

    public void setRightText(@NonNull String text) {
        tvRight.setVisibility(VISIBLE);
        tvRight.setText(text);
        invalidate();
    }

    public void setTitle(@NonNull String title) {
        tvTitle.setVisibility(VISIBLE);
        tvTitle.setText(title);
        invalidate();
    }

    public void setTitleColor(int color) {
        tvTitle.setTextColor(color);
    }

    public void setLeftTextColor(int color) {
        tvLeft.setTextColor(color);
    }

    public void setRightTextColor(int color) {
        tvRight.setTextColor(color);
    }

    public void setLeftDrawable(Drawable drawable) {
        ivLeft.setVisibility(VISIBLE);
        ivLeft.setImageDrawable(drawable);
        invalidate();
    }

    public void setLeftDrawable(int iconRes) {
        ivLeft.setVisibility(VISIBLE);
        ivLeft.setImageDrawable(getResources().getDrawable(iconRes, null));
        invalidate();
    }

    public void setRightDrawable(Drawable drawable) {
        ivRight.setVisibility(VISIBLE);
        ivRight.setImageDrawable(drawable);
        invalidate();
    }

    public void setRTRightDrawable(Drawable drawable) {
        ivRTRight.setVisibility(VISIBLE);
        ivRTRight.setImageDrawable(drawable);
        invalidate();
    }

    public static int getStatusBarHeight() {
        Resources resources = Resources.getSystem();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        int height_ = BarUtils.getStatusBarHeight();
        return Math.max(height_, height);
    }

}