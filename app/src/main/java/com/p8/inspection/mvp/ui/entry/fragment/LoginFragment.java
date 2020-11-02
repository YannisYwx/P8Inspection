package com.p8.inspection.mvp.ui.entry.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.p8.inspection.R;
import com.p8.inspection.base.DaggerMvpFragment;
import com.p8.inspection.di.component.FragmentComponent;
import com.p8.inspection.mvp.contract.LoginContract;
import com.p8.inspection.mvp.presenter.LoginPresenter;
import com.p8.inspection.mvp.ui.MainActivity;
import com.p8.inspection.widget.TriangleIndicatorView;

/**
 * @author : WX.Y
 * date : 2020/10/22 11:10
 * description :
 */
public class LoginFragment extends DaggerMvpFragment<LoginPresenter, LoginContract.View> implements LoginContract.View {

    private TriangleIndicatorView tiv;
    private TextView tvLogin, tvRegister;
    private EditText etAccount, etPassword;
    private Button btnLogin;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void injectThis(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    public void initView(View view) {
        mTitleBar.setVisibility(View.GONE);
        tiv = $(R.id.v_tiv);
        tvLogin = $(R.id.tv_login);
        tvRegister = $(R.id.tv_register);
        etAccount = $(R.id.et_userName);
        etPassword = $(R.id.et_password);
        btnLogin = $(R.id.btn_login);
        tvLogin.setSelected(true);
        tvRegister.setSelected(false);
    }

    private void setSelectIndex(int index) {
        tvLogin.setSelected(index == 0);
        tvRegister.setSelected(index != 0);
        tiv.pointTo(index);
    }

    @Override
    public void initData() {
        etAccount.setText("wzh");
        etPassword.setText("123456");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_login:
                setSelectIndex(0);
                break;
            case R.id.tv_register:
                setSelectIndex(1);
                break;
            case R.id.btn_login:
                String account = etAccount.getText().toString();
                String password = etPassword.getText().toString();
                presenter.doLogin(account, password);
                break;
            default:
                break;
        }
    }

    @Override
    public void setListener() {
        tvLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    protected void refreshContentView(View view) {

    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_login_register;
    }

    @Override
    public void onLoginSuccess() {
        showMsg("登录成功");
        MainActivity.start(mContext);
    }

    @Override
    public void onLoginError(String errorMsg) {
        showMsg("登录失败 : " + errorMsg);
    }

    @Override
    public boolean hasTitleBar() {
        return false;
    }
}

