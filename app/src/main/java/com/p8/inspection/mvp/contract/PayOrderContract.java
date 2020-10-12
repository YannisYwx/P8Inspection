package com.p8.inspection.mvp.contract;

import com.p8.common.base.mvp.BaseContract;

/**
 * author : WX.Y
 * date : 2020/9/11 9:59
 * description : 支付
 */
public interface PayOrderContract {
    interface View extends BaseContract.IBaseView {
        /**
         * 支付成功回调
         *
         * @param payType 支付方式 0 微信， 1 支付宝支付
         */
        void payOrderSuccess(int payType);

        /**
         * 支付失败回调
         *
         * @param payType 支付方式 0 微信， 1 支付宝支付
         */
        void payOrderFailed(int payType);
    }

    interface Presenter extends BaseContract.IBasePresenter<View> {



        void weiChatPay();

        void aliPay();
    }
}

