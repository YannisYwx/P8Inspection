package com.p8.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.p8.common.R;


/**
 * @author : Yannis.Ywx
 * createTime : 2017/12/1  17:40
 * description :
 */
public class MultiFunEditText extends ConstraintLayout implements View.OnClickListener, TextWatcher {

    private DeletableEditText detContent;
    private ImageView ivEye;
    private ImageView ivIcon;
    private TextView tvLabel;
    private CountDownTextView tvVCode;
    private TextChangedListener mListener;
    private View vLine;

    private OnCountDownButtonClickListener mOnCountDownButtonClickListener;

    private Drawable icon;
    private int inputType;
    private String hint;
    private String label;
    private boolean isShowEye;
    private boolean isShowBottomLine;
    private boolean mIsShow;
    private int mode;

    public MultiFunEditText(Context context) {
        super(context, null);
    }

    public MultiFunEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.view_multi_input, this);

        //读取属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MultiFunEditText);
        hint = typedArray.getString(R.styleable.MultiFunEditText_hint);
        label = typedArray.getString(R.styleable.MultiFunEditText_editLabel);
        isShowEye = typedArray.getBoolean(R.styleable.MultiFunEditText_isPswIcon, false);
        isShowBottomLine = typedArray.getBoolean(R.styleable.MultiFunEditText_isShowBottomLine, true);
        inputType = typedArray.getInt(R.styleable.MultiFunEditText_inputType, 0);
        mode = typedArray.getInt(R.styleable.MultiFunEditText_mode, 0);
        icon = typedArray.getDrawable(R.styleable.MultiFunEditText_icon);
        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //查找控件
        ivIcon = findViewById(R.id.iv_icon);
        ivEye = findViewById(R.id.iv_psw_icon);
        detContent = findViewById(R.id.det_input);
        tvVCode = findViewById(R.id.tv_v_code);
        vLine = findViewById(R.id.view_bottom_line);
        tvLabel = findViewById(R.id.tv_label);

        ivEye.setOnClickListener(this);
        tvVCode.setOnClickListener(this);


        tvVCode.setVisibility(mode == 0 ? (!isShowEye ? View.GONE : View.GONE) : View.VISIBLE);
        tvVCode.setText("获取验证码");
        ((ConstraintLayout.LayoutParams) detContent.getLayoutParams()).rightToLeft = mode == 1 ? R.id.tv_v_code : R.id.iv_psw_icon;
        vLine.setVisibility(isShowBottomLine ? View.VISIBLE : View.GONE);

        if (icon != null) {
            ivIcon.setImageDrawable(icon);
        } else {
            ivIcon.setVisibility(GONE);
        }

        if (TextUtils.isEmpty(label)) {
            tvLabel.setVisibility(GONE);
        } else {
            tvLabel.setText(label);
        }

        if (icon != null && !TextUtils.isEmpty(label)) {
            tvLabel.setVisibility(GONE);
            ivIcon.setVisibility(VISIBLE);
        }

        detContent.setHint(hint);
        detContent.setEditInputType(inputType);
        ivEye.setVisibility(!isShowEye || mode == 1 ? GONE : VISIBLE);
        detContent.addTextChangedListener(this);
    }

    public String getTextContent() {
        Editable editable = detContent.getText();
        if (editable != null) {
            return editable.toString();
        } else {
            return "";
        }
    }

    public void setTextContent(String textContent) {
        detContent.setText(textContent);
    }

    public void setEditSelection(int selection) {
        detContent.setSelection(selection);
    }

    @Override
    public void onClick(View v) {
        if (v == ivEye) {
            processPswHideOrShow();
        }

        if (v == tvVCode) {
            if (mOnCountDownButtonClickListener != null) {
                mOnCountDownButtonClickListener.onCountDownButtonClick();
            }
        }
    }

    public void startCountDown() {
        if (tvVCode.getVisibility() == View.VISIBLE) {
            tvVCode.startCountDown();
        }
    }

    public interface OnCountDownButtonClickListener {
        void onCountDownButtonClick();
    }


    public void setCountDownButtonClickListener(OnCountDownButtonClickListener listener) {
        this.mOnCountDownButtonClickListener = listener;
    }

    /**
     * 处理显示或隐藏
     */
    private void processPswHideOrShow() {
        ivEye.setSelected(!ivEye.isSelected());
        mIsShow = !mIsShow;
        String etString;
        if (detContent.getText() != null) {
            etString = detContent.getText().toString().trim();
        } else {
            etString = "";
        }
        if (!mIsShow) {
            detContent.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {//显示
            detContent.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        detContent.setSelection(etString.length());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (mListener != null) {
            mListener.onTextChanged(s, start, before, count);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (isShowBottomLine) {
            if (TextUtils.isEmpty(s.toString())) {
                vLine.setBackgroundColor(getResources().getColor(R.color.grey));
            } else {
                vLine.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
        }
    }

    public void setTextChangedListener(TextChangedListener listener) {
        mListener = listener;
    }

    public interface TextChangedListener {
        void onTextChanged(CharSequence s, int start, int before, int count);
    }
}
