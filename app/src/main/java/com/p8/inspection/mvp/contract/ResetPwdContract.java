package com.p8.inspection.mvp.contract;


import com.p8.common.base.mvp.BaseContract;

/**
 * @author : WX.Y
 * date : 2020/9/7 16:32
 * description : 重置密码
 */
public interface ResetPwdContract {

    interface View extends BaseContract.IBaseView {

        void resetPasswordSuccess();

        void resetPasswordFailed(String msg);
    }

    interface Presenter extends BaseContract.IBasePresenter<View> {

        /**
         * 重置密码
         *
         * @param url         后台地址 不同的登录用户对应不同的地址
         * @param newPassword 新密码
         * @param oldPassword 旧密码
         */
        void resetPassword(String url, String newPassword, String oldPassword);

    }
}

