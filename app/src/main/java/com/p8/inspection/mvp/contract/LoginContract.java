package com.p8.inspection.mvp.contract;

import com.p8.common.base.mvp.BaseContract;
import com.p8.inspection.data.Constants;

/**
 * @author : WX.Y
 * date : 2020/9/7 16:32
 * description :
 */
public interface LoginContract {

    interface View extends BaseContract.IBaseView {
        /**
         * 登陆成功
         */
        void onLoginSuccess();

        /**
         * 登录失败
         *
         * @param errorMsg
         */
        void onLoginError(String errorMsg);

    }

    interface Presenter extends BaseContract.IBasePresenter<View> {

        /**
         * 通用登录
         *
         * @param userType
         * @param userName
         * @param password
         */
        void doLoginByLandlord(@Constants.UserType int userType, String userName, String password);

        /**
         * 大主登录
         *
         * @param userName
         * @param password
         */
        void loginByLargeMaster(String userName, String password);
    }
}

