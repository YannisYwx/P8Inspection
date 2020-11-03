package com.p8.inspection.mvp.presenter;

import com.p8.common.base.mvp.BasePresenter;
import com.p8.inspection.data.DataManager;
import com.p8.inspection.mvp.contract.AttendanceContract;
import com.p8.inspection.mvp.contract.LoginContract;

import javax.inject.Inject;

/**
 * @author : WX.Y
 * date : 2020/10/12 15:41
 * description : 考勤打卡
 */
public class AttendancePresenter extends BasePresenter<AttendanceContract.View> implements AttendanceContract.Presenter {
    DataManager mDataManager;

    @Inject
    public AttendancePresenter(DataManager manager) {
        this.mDataManager = manager;
    }

    @Override
    public void clockIn() {

    }

    @Override
    public void clockOut() {

    }


}

