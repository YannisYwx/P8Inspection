package com.p8.inspection.mvp.ui.main.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.p8.inspection.R;
import com.p8.inspection.base.DaggerMvpFragment;
import com.p8.inspection.data.LocalDataManager;
import com.p8.inspection.di.component.FragmentComponent;
import com.p8.inspection.mvp.contract.ResetPwdContract;
import com.p8.inspection.mvp.presenter.ResetPwdPresenter;

import static com.p8.inspection.mvp.ui.entry.fragment.ResetPwdFragment.PASSWORD_REGEX;

/**
 * @author : WX.Y
 * date : 2020/10/27 18:05
 * description : 修改密码
 */
public class ResetPasswordFragment extends DaggerMvpFragment<ResetPwdPresenter, ResetPwdContract.View> implements ResetPwdContract.View {

    public static ResetPasswordFragment newInstance() {
        return new ResetPasswordFragment();
    }

    private EditText etOldPwd, etNewPwd, etVerifyPwd;

    @Override
    public void injectThis(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    public void initView(View view, @Nullable Bundle savedInstanceState) {
        mTitleBar.setRightText("完成");
        etOldPwd = $(R.id.et_old_pwd);
        etNewPwd = $(R.id.et_new_pwd);
        etVerifyPwd = $(R.id.et_verify_pwd);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {

    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_reset_password;
    }

    @Override
    public void onTitleBarRightClick() {
        super.onTitleBarRightClick();
        String oldPassword = etOldPwd.getText().toString();
        String newPassword = etNewPwd.getText().toString();
        String verifyPassword = etVerifyPwd.getText().toString();

        if (TextUtils.isEmpty(oldPassword)) {
            ToastUtils.showShort("请输入旧的密码");
            return;
        }

        if (TextUtils.isEmpty(newPassword)) {
            ToastUtils.showShort("请输入新的密码");
            return;
        }

        if (TextUtils.isEmpty(verifyPassword)) {
            ToastUtils.showShort("请再次输入新的密码");
            return;
        }

        if (!oldPassword.equals(LocalDataManager.getInstance().getLoginInfo().getPassword())) {
            ToastUtils.showShort("旧密码不正确");
            return;
        }

        if (!RegexUtils.isMatch(PASSWORD_REGEX, newPassword)) {
            ToastUtils.showShort("密码必须由6-16位字母、数字组成");
            return;
        }

        if (!newPassword.equals(verifyPassword)) {
            ToastUtils.showShort("新密码不一致");
            return;
        }

        presenter.resetPassword(LocalDataManager.getInstance().getResetPasswordUrl(), newPassword, oldPassword);
    }

    @Override
    public void resetPasswordSuccess() {
        ToastUtils.showShort("修改成功");
        reLogin();
    }

    @Override
    public void resetPasswordFailed(String msg) {

    }

    @Override
    public int setTitle() {
        return R.string.title_modify_password;
    }
}

