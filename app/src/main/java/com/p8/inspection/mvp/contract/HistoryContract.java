package com.p8.inspection.mvp.contract;

import com.p8.common.base.mvp.BaseContract;

/**
 * @author : WX.Y
 * date : 2020/9/7 16:32
 * description : 上下班打卡业务
 */
public interface HistoryContract {

    interface View extends BaseContract.IBaseView {

        /**
         * 获取上下班打卡的记录成功
         */
        void onClockInRecordGetSuccess();

    }

    interface Presenter extends BaseContract.IBasePresenter<View> {

        /**
         * 获取上下班打卡的记录
         *
         * @param year  年
         * @param month 月
         * @param page  当前的页数
         */
        void getClockInRecord(String year, String month, int page);
    }
}

