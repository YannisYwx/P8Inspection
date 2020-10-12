package com.p8.inspection.mvp.contract;


import com.p8.common.base.mvp.BaseContract;

/**
 * author : WX.Y
 * date : 2020/9/7 16:32
 * description :
 */
public interface ResetPwdContract {

    interface View extends BaseContract.IBaseView {

        void getVCodeSuccess(String msg);

        void getVCodeFailed(String msg);

        void resetPasswordSuccess();

        void resetPasswordFailed(String msg);
    }

    interface Presenter extends BaseContract.IBasePresenter<View> {

        void getVCode(String phoneNum);

        void resetPassword(String phoneNumber, String vCode, String password);

    }
}

