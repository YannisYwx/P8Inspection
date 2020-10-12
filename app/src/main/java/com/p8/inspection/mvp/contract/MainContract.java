package com.p8.inspection.mvp.contract;

import com.p8.common.base.mvp.BaseContract;

/**
 * author : WX.Y
 * date : 2020/9/7 16:32
 * description :
 */
public interface MainContract {

    interface View extends BaseContract.IBaseView {

        void onLoginSuccess(String name, String pwd);

        void onLoginError(String errorMsg, int errorCode);

    }

    interface Presenter extends BaseContract.IBasePresenter<View> {

        void doLogin(String userName, String password);
    }
}

