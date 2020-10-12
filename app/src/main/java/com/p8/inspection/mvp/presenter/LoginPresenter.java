package com.p8.inspection.mvp.presenter;

import com.p8.common.base.mvp.BasePresenter;
import com.p8.common.http.HttpResponse;
import com.p8.common.rx.RxUtils;
import com.p8.inspection.data.DataManager;
import com.p8.inspection.data.bean.LoginInfo;
import com.p8.inspection.data.bean.Provinces;
import com.p8.inspection.data.net.observer.P8HttpSubscriber;
import com.p8.inspection.mvp.contract.LoginContract;

import javax.inject.Inject;

/**
 * author : WX.Y
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
    public void doLogin(String username, String password) {
        mDataManager.doLogin(username, password)
                .compose(RxUtils.getDefaultOSchedulers())
                .as(bindLifecycle()).subscribe(new P8HttpSubscriber<HttpResponse<LoginInfo>>(mView) {
            @Override
            protected void onSuccess(HttpResponse<LoginInfo> loginInfoHttpResponse) {
                mView.onLoginSuccess();
                String token = loginInfoHttpResponse.getData().getToken();
                mDataManager.saveToken(token);
            }
        });

    }

    @Override
    public void getProvince() {
        mDataManager.getProvinces()
                .compose(RxUtils.getDefaultOSchedulers())
                .as(bindLifecycle())
                .subscribe(new P8HttpSubscriber<HttpResponse<Provinces>>(mView) {
                    @Override
                    protected void onSuccess(HttpResponse<Provinces> provincesHttpResponse) {
//                        for (Province province : provincesHttpResponse.getData().getList()) {
//                            Logger.e(province.toString());
//                        }
                    }
                });
    }

}

