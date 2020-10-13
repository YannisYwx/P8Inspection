package com.p8.inspection.mvp.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.p8.common.widget.MultiFunEditText;
import com.p8.inspection.R;
import com.p8.inspection.base.DaggerMVPFragment;
import com.p8.inspection.di.component.FragmentComponent;
import com.p8.inspection.mvp.contract.LoginContract;
import com.p8.inspection.mvp.presenter.LoginPresenter;
import com.p8.inspection.widget.DialogUtils;

/**
 * author : WX.Y
 * date : 2020/9/16 17:06
 * description :
 */
public class LoginFragment extends DaggerMVPFragment<LoginPresenter, LoginContract.View> implements LoginContract.View {
    TextView tvInfo;
    MultiFunEditText mfeAccount;
    MultiFunEditText mfePassword;
    Button btnLogin;

    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void injectThis(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    public void initView(View view) {
        mfeAccount = $(R.id.mfe_account);
        mfePassword = $(R.id.mfe_password);
        btnLogin = $(R.id.btn_login);
        tvInfo = $(R.id.tv_info);
        mfeAccount.setTextContent("wzh");
        mfePassword.setTextContent("123456");
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {
        $(R.id.btn_login).setOnClickListener(this);
        $(R.id.iv_wechat).setOnClickListener(this);
        $(R.id.tv_forget_pwd).setOnClickListener(this);
    }

    @Override
    protected void triggerLoadData() {

    }

    @Override
    protected void refreshContentView(View view) {

    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void onLoginSuccess() {
        showMsg("登录成功");
        start(TestFragment.newInstance());
    }

    @Override
    public void onLoginError(String errorMsg) {
        tvInfo.setText(errorMsg);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.btn_login) {
            String username = mfeAccount.getTextContent();
            String password = mfePassword.getTextContent();

            if (TextUtils.isEmpty(username)) {
                showMsg("账号不能为空");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                showMsg("密码不能为空");
                return;
            }
            presenter.doLogin(username, password);
        }

        if (v.getId() == R.id.tv_forget_pwd) {
            start(ResetPwdFragment.newInstance());
        }
    }

    @Override
    public boolean hasTitleBar() {
        return false;
    }
}

