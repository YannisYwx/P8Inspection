package com.p8.inspection.mvp.contract;

import com.p8.common.base.mvp.BaseContract;

/**
 * @author : WX.Y
 * date : 2020/9/7 16:32
 * description : 设备绑定业务
 */
public interface DeviceBindingContract {

    interface View extends BaseContract.IBaseView {


    }

    interface Presenter extends BaseContract.IBasePresenter<View> {
        /**
         * 绑定设备
         *
         * @param address
         * @param parkingNumber
         * @param lat
         * @param lng
         */
        void bindDevice(String address, String parkingNumber, String lat, String lng);
    }
}

