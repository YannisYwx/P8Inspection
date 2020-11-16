package com.p8.inspection.mvp.contract;

import com.p8.common.base.mvp.BaseContract;
import com.p8.inspection.data.bean.Areas;
import com.p8.inspection.data.bean.Cities;
import com.p8.inspection.data.bean.City;
import com.p8.inspection.data.bean.Machines;
import com.p8.inspection.data.bean.Provinces;
import com.p8.inspection.data.bean.Streets;

/**
 * @author : WX.Y
 * date : 2020/9/7 16:32
 * description :
 */
public interface MonitorContract {

    interface View extends BaseContract.IBaseView {

        void onGetStreetsSuccess(Streets streets);

        void onGetLocationFail(String errorMsg, int type, String id);

        void onGetMachines(Machines machines);

    }

    interface Presenter extends BaseContract.IBasePresenter<View> {

        /**
         * 获取街道
         *
         * @param address 省市区
         */
        void getStreets(String address);

        /**
         * 获取设备
         *
         * @param address      地址
         * @param parkingState 泊位状态
         * @param currentPage  当前页面
         */
        void getMachines(String address, String parkingState, int currentPage);
    }
}

