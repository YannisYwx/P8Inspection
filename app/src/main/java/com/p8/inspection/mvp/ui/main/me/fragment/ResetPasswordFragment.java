package com.p8.inspection.mvp.ui.main.me.fragment;

import android.view.View;

import com.p8.inspection.R;
import com.p8.inspection.base.DaggerMvpFragment;
import com.p8.inspection.di.component.FragmentComponent;
import com.p8.inspection.mvp.contract.ResetPwdContract;
import com.p8.inspection.mvp.presenter.ResetPwdPresenter;

/**
 * @author : WX.Y
 * date : 2020/10/27 18:05
 * description :
 */
public class ResetPasswordFragment extends DaggerMvpFragment<ResetPwdPresenter, ResetPwdContract.View> implements ResetPwdContract.View {

    public static ResetPasswordFragment newInstance() {
        return new ResetPasswordFragment();
    }

    @Override
    public void injectThis(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {

    }

    @Override
    protected void refreshContentView(View view) {

    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_reset_password;
    }

    @Override
    public void getVCodeSuccess(String msg) {

    }

    @Override
    public void getVCodeFailed(String msg) {

    }

    @Override
    public void resetPasswordSuccess() {

    }

    @Override
    public void resetPasswordFailed(String msg) {

    }

    @Override
    public int setTitle() {
        return R.string.title_modify_password;
    }
}

