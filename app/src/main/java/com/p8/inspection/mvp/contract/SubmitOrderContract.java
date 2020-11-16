package com.p8.inspection.mvp.contract;

import com.p8.common.base.mvp.BaseContract;

/**
 * @author : WX.Y
 * date : 2020/10/12 15:38
 * description : 提交工单业务
 */
public interface SubmitOrderContract {

    interface View extends BaseContract.IBaseView {

    }

    interface Presenter extends BaseContract.IBasePresenter<SubmitOrderContract.View> {

    }
}

