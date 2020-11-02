package com.p8.inspection.mvp.contract;

import com.p8.common.base.mvp.BaseContract;

/**
 * @author : WX.Y
 * date : 2020/10/27 16:28
 * description :
 */
public interface AttendanceContract {
    interface View extends BaseContract.IBaseView {
        /**
         * 上班打卡成功
         */
        void onClockInSuccess();

        /**
         * 下班打卡成功
         */
        void onClockOutSuccess();

        /**
         * 打卡失败
         */
        void onClockFailed();

    }

    interface Presenter extends BaseContract.IBasePresenter<AttendanceContract.View> {

        void clockIn();

        void clockOut();
    }
}
