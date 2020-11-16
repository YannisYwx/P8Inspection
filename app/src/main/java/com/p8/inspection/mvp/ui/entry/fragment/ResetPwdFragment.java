package com.p8.inspection.mvp.ui.entry.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.RegexUtils;
import com.p8.common.widget.MultiFunEditText;
import com.p8.inspection.R;
import com.p8.inspection.base.DaggerMvpFragment;
import com.p8.inspection.di.component.FragmentComponent;
import com.p8.inspection.mvp.contract.ResetPwdContract;
import com.p8.inspection.mvp.presenter.ResetPwdPresenter;

/**
 * @author : WX.Y
 * date : 2020/9/16 18:00
 * description :
 */
public class ResetPwdFragment extends DaggerMvpFragment<ResetPwdPresenter, ResetPwdContract.View> implements ResetPwdContract.View {

    MultiFunEditText mfePhone;
    MultiFunEditText mfeVCode;
    MultiFunEditText mfePassword;
    MultiFunEditText mfePasswordConfirm;
    Button btnRegister;

    public static ResetPwdFragment newInstance() {
        Bundle args = new Bundle();
        ResetPwdFragment fragment = new ResetPwdFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void injectThis(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    public void initView(View view, @Nullable Bundle savedInstanceState) {
        mfePhone = $(R.id.mfe_phone);
        mfeVCode = $(R.id.mfe_v_code);
        mfePassword = $(R.id.mfe_password);
        mfePasswordConfirm = $(R.id.mfe_password_confirm);
        btnRegister = $(R.id.btn_reset);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {
        btnRegister.setOnClickListener(this);
        mfeVCode.setCountDownButtonClickListener(() -> {
            String phoneNum = mfePhone.getTextContent();
            if (TextUtils.isEmpty(phoneNum)) {
                showMsg("输入的手机号不能为空");
                return;
            }
            if (!RegexUtils.isMobileExact(phoneNum)) {
                showMsg("请输入正确的手机号");
                return;
            }
        });
    }

    @Override
    protected void refreshContentView(View view) {

    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_reset_pwd;
    }


    @Override
    public void resetPasswordSuccess() {
        showMsg("密码重置成功");
        pop();
    }

    @Override
    public void resetPasswordFailed(String msg) {
        showMsg(msg);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        String phoneNumber = mfePhone.getTextContent();
        String vCode = mfeVCode.getTextContent();
        String password = mfePassword.getTextContent();
        String passwordConfirm = mfePasswordConfirm.getTextContent();
        if (checkInformation(phoneNumber, vCode, password, passwordConfirm)) {
            presenter.resetPassword(phoneNumber, vCode, password);
        }
    }

    public static final String PASSWORD_REGEX = "^[a-zA-Z0-9]{6,16}$";

    private boolean checkInformation(String phoneNumber, String vCode, String password, String passwordConfirm) {
        if (TextUtils.isEmpty(phoneNumber)) {
            showMsg("手机号码不能为空");
            return false;
        } else if (!RegexUtils.isMobileExact(phoneNumber)) {
            showMsg("手机号码格式不正确");
            return false;
        } else if (TextUtils.isEmpty(vCode)) {
            showMsg("验证码不能为空");
            return false;
        } else if (TextUtils.isEmpty(password) || TextUtils.isEmpty(passwordConfirm)) {
            showMsg("密码不能为空");
            return false;
        } else if (!RegexUtils.isMatch(PASSWORD_REGEX, password) || !RegexUtils.isMatch(PASSWORD_REGEX, passwordConfirm)) {
            showMsg("密码必须由6-16位字母、数字组成");
            return false;
        } else if (!password.equals(passwordConfirm)) {
            showMsg("两次密码输入不同");
            return false;
        }
        return true;
    }

    @Override
    public boolean hasTitleBar() {
        return false;
    }
}

