package com.p8.inspection.mvp.presenter;

import com.p8.common.base.mvp.BasePresenter;
import com.p8.common.http.HttpResponse;
import com.p8.common.rx.RxUtils;
import com.p8.inspection.data.DataManager;
import com.p8.inspection.data.bean.Machines;
import com.p8.inspection.data.bean.Streets;
import com.p8.inspection.data.net.observer.P8HttpSubscriber;
import com.p8.inspection.mvp.contract.MonitorContract;

import javax.inject.Inject;

/**
 * @author : WX.Y
 * date : 2020/9/27 16:43
 * description :
 */
public class MonitorPresenter extends BasePresenter<MonitorContract.View> implements MonitorContract.Presenter {

    DataManager mDataManager;

    @Inject
    public MonitorPresenter(DataManager manager) {
        this.mDataManager = manager;
    }

    @Override
    public void getStreets(String address) {
        mDataManager.getStreets(address)
                .compose(RxUtils.getDefaultOSchedulers())
                .as(bindLifecycle())
                .subscribe(new P8HttpSubscriber<HttpResponse<Streets>>(mView) {
                    @Override
                    protected void onSuccess(HttpResponse<Streets> streetsHttpResponse) {
                        mView.onGetStreetsSuccess(streetsHttpResponse.getData());
                    }
                });
    }

    @Override
    public void getMachines(String address, String parkingState, int currentPage) {
        mDataManager.getMachines(address, parkingState, currentPage)
                .compose(RxUtils.getDefaultOSchedulers())
                .as(bindLifecycle())
                .subscribe(new P8HttpSubscriber<HttpResponse<Machines>>(mView) {

                    @Override
                    protected void onSuccess(HttpResponse<Machines> machinesHttpResponse) {
                        mView.onGetMachines(machinesHttpResponse.getData());
                    }
                });
    }
}

