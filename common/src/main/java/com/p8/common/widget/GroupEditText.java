package com.p8.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.p8.common.R;
import com.p8.common.utils.TimerCount;


/**
 * @author : Yannis.Ywx
 * createTime : 2017/12/1  17:40
 * description :
 */
public class GroupEditText extends RelativeLayout implements View.OnClickListener, TextWatcher {

    private DeletableEditText detContent;
    private ImageView ivEye;
    private TextView tvVCode;
    private TextView tvLabel;
    private boolean mIsShow;
    private int mode;
    private TextChangedListener mListener;

    private String text;
    private int inputType;
    private String hint;
    private boolean isShowEye;
    private boolean deletable;

    private TimerCount mCount;

    public GroupEditText(Context context) {
        super(context, null);
    }

    public GroupEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.view_normal_edittext, this);

        //读取属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GroupEditText);
        text = typedArray.getString(R.styleable.GroupEditText_text);
        hint = typedArray.getString(R.styleable.GroupEditText_hint);
        isShowEye = typedArray.getBoolean(R.styleable.GroupEditText_isPswIcon, false);
        deletable = typedArray.getBoolean(R.styleable.GroupEditText_hint, true);
        inputType = typedArray.getInt(R.styleable.GroupEditText_inputType, 0);
        mode = typedArray.getInt(R.styleable.GroupEditText_mode, 0);
        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //查找控件
        tvLabel = findViewById(R.id.tv_label);
        ivEye = findViewById(R.id.iv_psw_icon);
        detContent = findViewById(R.id.det);
        tvVCode = findViewById(R.id.tv_v_code);
        ivEye.setOnClickListener(this);
        tvVCode.setOnClickListener(this);
        tvVCode.setVisibility(mode == 0 ? View.INVISIBLE : View.VISIBLE);
        if (mode == 0) {
            ((LayoutParams) detContent.getLayoutParams()).addRule(RelativeLayout.START_OF, R.id.iv_psw_icon);
        }

        //赋值
        tvLabel.setText(text);
        detContent.setHint(hint);
        detContent.setEditInputType(inputType);
        ivEye.setVisibility(isShowEye ? VISIBLE : GONE);
        detContent.addTextChangedListener(this);

        if (mode == 1) {
            ivEye.setVisibility(View.GONE);
            mCount = new TimerCount(tvVCode, "获取验证码", 60 * 1_000, 1_000);
        }
    }

    public String getTextContent() {
        return detContent.getText().toString().trim();
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
            if (mCount != null) {
                mCount.start();
            }
        }
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

    }

    public void setTextChangedListener(TextChangedListener listener) {
        mListener = listener;
    }

    public interface TextChangedListener {
        void onTextChanged(CharSequence s, int start, int before, int count);
    }
}
