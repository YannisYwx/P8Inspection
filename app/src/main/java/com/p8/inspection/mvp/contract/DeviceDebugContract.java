package com.p8.inspection.mvp.contract;

import com.p8.common.base.mvp.BaseContract;

/**
 * @author : WX.Y
 * date : 2020/9/7 16:32
 * description : 设备调试业务
 */
public interface DeviceDebugContract {

    interface View extends BaseContract.IBaseView {

        /**
         * 获取设备信息成功
         */
        void getDeviceInfoSuccess();

    }

    interface Presenter extends BaseContract.IBasePresenter<View> {

        /**
         * 获取设备信息
         *
         * @param deviceNumber 设备号
         */
        void getDeviceInfo(String deviceNumber);

        /**
         * 标定
         */
        void deviceDemarcate();

        /**
         * 上升
         */
        void deviceUp();

        /**
         * 下降
         */
        void deviceDown();
    }
}

