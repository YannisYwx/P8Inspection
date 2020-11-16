package com.p8.inspection.mvp.ui.entry.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.p8.inspection.R;
import com.p8.inspection.base.DaggerMvpFragment;
import com.p8.inspection.data.Constants;
import com.p8.inspection.di.component.FragmentComponent;
import com.p8.inspection.mvp.contract.LoginContract;
import com.p8.inspection.mvp.presenter.LoginPresenter;
import com.p8.inspection.mvp.ui.MainActivity;
import com.p8.inspection.widget.TriangleIndicatorView;

import static com.p8.inspection.data.Constants.USER_TYPE;

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
    @Constants.UserType
    private int userType;

    public static LoginFragment newInstance(@Constants.UserType int userType) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putInt(USER_TYPE, userType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void injectThis(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    public void initView(View view, @Nullable Bundle savedInstanceState) {
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
        Bundle bundle = getArguments();
        if (bundle != null) {
            userType = bundle.getInt(USER_TYPE);
        }
        etAccount.setText("15919835035");
        etPassword.setText("456789");
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
                String loginName = etAccount.getText().toString();
                String password = etPassword.getText().toString();
                login(loginName, password);
                break;
            default:
                break;
        }
    }

    private void login(String loginName, String password) {
        switch (userType) {
            case Constants.UserType.BUILD:
                break;
            case Constants.UserType.LAND:
                presenter.doLoginByLandlord(userType, loginName, password);
                break;
            case Constants.UserType.LARGE:
                presenter.loginByLargeMaster(loginName, password);
                break;
            case Constants.UserType.MEDIUM:
                break;
            case Constants.UserType.ONESELF:
                break;
            case Constants.UserType.OTHER:
                break;
            case Constants.UserType.PLACE:
                break;
            case Constants.UserType.PLATFORM:
                break;
            case Constants.UserType.SMALL:
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

