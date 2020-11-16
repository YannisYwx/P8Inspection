package com.p8.inspection.mvp.contract;

import com.p8.common.base.mvp.BaseContract;
import com.p8.inspection.data.bean.Agency;

/**
 * @author : WX.Y
 * date : 2020/10/12 15:38
 * description :
 */
public interface MainContract {

    interface View extends BaseContract.IBaseView {

        /**
         * 请求大主信息成功
         *
         * @param agency 大主
         */
        void onRequestAgencyInfoSuccess(Agency agency);
    }

    interface Presenter extends BaseContract.IBasePresenter<MainContract.View> {

        /**
         * 请求获取大主信息
         */
        void requestAgencyInfo();

    }
}

