package com.p8.inspection.mvp.presenter;

import com.p8.common.base.mvp.BasePresenter;
import com.p8.common.http.HttpResponse;
import com.p8.common.rx.RxUtils;
import com.p8.inspection.data.DataManager;
import com.p8.inspection.data.net.observer.P8HttpSubscriber;
import com.p8.inspection.mvp.contract.DeviceBindingContract;
import com.p8.inspection.mvp.contract.DeviceDebugContract;

import javax.inject.Inject;

/**
 * @author : WX.Y
 * date : 2020/10/29 10:31
 * description : 设备绑定
 */
public class DeviceBindingPresenter extends BasePresenter<DeviceBindingContract.View> implements DeviceBindingContract.Presenter {

    DataManager mDataManager;

    @Inject
    public DeviceBindingPresenter(DataManager manager) {
        this.mDataManager = manager;
    }

    @Override
    public void bindDevice(String address, String parkingNumber, String lat, String lng) {
        mDataManager.bindDevice(address, parkingNumber, lat, lng).compose(RxUtils.getDefaultOSchedulers())
                .as(bindLifecycle())
                .subscribe(new P8HttpSubscriber<HttpResponse<Object>>(mView) {
                    @Override
                    protected void onSuccess(HttpResponse<Object> stringHttpResponse) {
                        mView.showMsg("修改成功");
                    }
                });
    }
}

