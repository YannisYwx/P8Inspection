package com.p8.inspection.mvp.presenter;

import com.p8.common.base.mvp.BasePresenter;
import com.p8.inspection.data.DataManager;
import com.p8.inspection.mvp.contract.MeContract;

import javax.inject.Inject;

/**
 * @author : WX.Y
 * date : 2020/10/12 15:41
 * description :
 */
public class MePresenter extends BasePresenter<MeContract.View> implements MeContract.Presenter {
    DataManager mDataManager;

    @Inject
    public MePresenter(DataManager manager) {
        this.mDataManager = manager;
    }
}

