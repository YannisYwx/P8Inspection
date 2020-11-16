package com.p8.inspection.mvp.presenter;

import com.p8.common.base.mvp.BasePresenter;
import com.p8.inspection.data.DataManager;
import com.p8.inspection.mvp.contract.SubmitOrderContract;

import javax.inject.Inject;

/**
 * @author : WX.Y
 * date : 2020/10/12 15:41
 * description : 提交工单，地主向大主提交工单
 */
public class SubmitOrderPresenter extends BasePresenter<SubmitOrderContract.View> implements SubmitOrderContract.Presenter {
    DataManager mDataManager;

    @Inject
    public SubmitOrderPresenter(DataManager manager) {
        this.mDataManager = manager;
    }
}

