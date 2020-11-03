package com.p8.inspection.mvp.presenter;

import com.p8.common.base.mvp.BasePresenter;
import com.p8.inspection.data.DataManager;
import com.p8.inspection.mvp.contract.DeviceDebugContract;

import javax.inject.Inject;

/**
 * @author : WX.Y
 * date : 2020/10/29 10:31
 * description : 设备调试
 */
public class DeviceDebugPresenter extends BasePresenter<DeviceDebugContract.View> implements DeviceDebugContract.Presenter {

    DataManager mDataManager;

    @Inject
    public DeviceDebugPresenter(DataManager manager) {
        this.mDataManager = manager;
    }

    @Override
    public void getDeviceInfo(String deviceNumber) {

    }

    @Override
    public void deviceDemarcate() {

    }

    @Override
    public void deviceUp() {

    }

    @Override
    public void deviceDown() {

    }
}

