package com.p8.inspection.mvp.presenter;

import com.p8.common.base.mvp.BasePresenter;
import com.p8.inspection.data.DataManager;
import com.p8.inspection.mvp.contract.HistoryContract;

import javax.inject.Inject;

/**
 * @author : WX.Y
 * date : 2020/10/28 11:44
 * description :
 */
public class HistoryPresenter extends BasePresenter<HistoryContract.View> implements HistoryContract.Presenter {

    DataManager mDataManager;

    @Inject
    public HistoryPresenter(DataManager manager) {
        this.mDataManager = manager;
    }

    @Override
    public void getClockInRecord(String year, String month, int page) {

    }
}

