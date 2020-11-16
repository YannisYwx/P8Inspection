package com.p8.inspection.mvp.presenter;

import com.blankj.utilcode.util.CacheDoubleUtils;
import com.orhanobut.logger.Logger;
import com.p8.common.base.mvp.BasePresenter;
import com.p8.common.http.HttpError;
import com.p8.common.http.HttpResponse;
import com.p8.common.rx.RxUtils;
import com.p8.inspection.data.Constants;
import com.p8.inspection.data.DataManager;
import com.p8.inspection.data.bean.LoginInfo;
import com.p8.inspection.data.net.observer.P8HttpSubscriber;
import com.p8.inspection.mvp.contract.LoginContract;

import javax.inject.Inject;

/**
 * @author : WX.Y
 * date : 2020/9/11 17:43
 * description :
 */
public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    DataManager mDataManager;

    @Inject
    public LoginPresenter(DataManager manager) {
        this.mDataManager = manager;
    }

    @Override
    public void doLoginByLandlord(int userType, String userName, String password) {
        mDataManager.doLoginByLandlord(userName, password)
                .compose(RxUtils.getDefaultOSchedulers())
                .as(bindLifecycle()).subscribe(new P8HttpSubscriber<HttpResponse<LoginInfo>>(mView) {
            @Override
            protected void onSuccess(HttpResponse<LoginInfo> loginInfoHttpResponse) {
                LoginInfo loginInfo = loginInfoHttpResponse.getData();
                loginInfo.userType = userType;
                loginInfo.password = password;
                Logger.e(loginInfo.toString());
                CacheDoubleUtils.getInstance().put(Constants.Key.LOGIN_INFO, loginInfo);
                String token = loginInfoHttpResponse.getData().getToken();
                mDataManager.saveToken(token);
                mView.onLoginSuccess();
            }

            @Override
            public boolean isShowProgressDialog() {
                return true;
            }
        });
    }

    @Override
    public void loginByLargeMaster(String userName, String password) {
        mDataManager.doLoginByLargeMaster(userName, password)
                .compose(RxUtils.getDefaultOSchedulers())
                .as(bindLifecycle()).subscribe(new P8HttpSubscriber<HttpResponse<LoginInfo>>(mView) {
            @Override
            protected void onSuccess(HttpResponse<LoginInfo> loginInfoHttpResponse) {
                LoginInfo loginInfo = loginInfoHttpResponse.getData();
                loginInfo.userType = Constants.UserType.LARGE;
                loginInfo.password = password;
                CacheDoubleUtils.getInstance().put(Constants.Key.LOGIN_INFO, loginInfo);
                String token = loginInfoHttpResponse.getData().getToken();
                mDataManager.saveToken(token);
                mView.onLoginSuccess();
            }

            @Override
            protected void onFail(HttpError httpError) {
                super.onFail(httpError);
                mView.onLoginError(httpError.msg);
            }

            @Override
            public boolean isShowProgressDialog() {
                return true;
            }
        });
    }

}

