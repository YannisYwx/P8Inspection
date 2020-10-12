package com.p8.inspection.mvp.presenter;

import com.p8.common.base.mvp.BasePresenter;
import com.p8.common.http.HttpResponse;
import com.p8.common.rx.RxUtils;
import com.p8.inspection.data.DataManager;
import com.p8.inspection.data.bean.Areas;
import com.p8.inspection.data.bean.Cities;
import com.p8.inspection.data.bean.Machines;
import com.p8.inspection.data.bean.Provinces;
import com.p8.inspection.data.bean.Streets;
import com.p8.inspection.data.net.observer.P8HttpSubscriber;
import com.p8.inspection.mvp.contract.MonitorContract;

import javax.inject.Inject;

/**
 * author : WX.Y
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
    public void getProvinces() {
        mDataManager.getProvinces()
                .compose(RxUtils.getDefaultOSchedulers())
                .as(bindLifecycle())
                .subscribe(new P8HttpSubscriber<HttpResponse<Provinces>>(mView) {
                    @Override
                    protected void onSuccess(HttpResponse<Provinces> provincesHttpResponse) {
                        mView.onGetProvincesSuccess(provincesHttpResponse.getData());
                    }
                });
    }

    @Override
    public void getCities(String id) {
        mDataManager.getCities(id)
                .compose(RxUtils.getDefaultOSchedulers())
                .as(bindLifecycle())
                .subscribe(new P8HttpSubscriber<HttpResponse<Cities>>(mView) {
                    @Override
                    protected void onSuccess(HttpResponse<Cities> citiesHttpResponse) {
                        mView.onGetCitiesSuccess(citiesHttpResponse.getData());
                    }
                });
    }

    @Override
    public void getAreas(String id) {
        mDataManager.getAreas(id)
                .compose(RxUtils.getDefaultOSchedulers())
                .as(bindLifecycle())
                .subscribe(new P8HttpSubscriber<HttpResponse<Areas>>(mView) {
                    @Override
                    protected void onSuccess(HttpResponse<Areas> citiesHttpResponse) {
                        mView.onGetAreasSuccess(citiesHttpResponse.getData());
                    }
                });
    }

    @Override
    public void getStreets(String id) {
        mDataManager.getStreets(id)
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
    public void getMachines(String address, int parkingState, int currentPage) {
        mDataManager.getMachines(address, parkingState, currentPage)
                .compose(RxUtils.getDefaultOSchedulers())
                .as(bindLifecycle())
                .subscribe(new P8HttpSubscriber<HttpResponse<Machines>>(mView) {

                    @Override
                    public boolean isShowProgressDialog() {
                        return false;
                    }

                    @Override
                    protected void onSuccess(HttpResponse<Machines> machinesHttpResponse) {
                        mView.onGetMachines(machinesHttpResponse.getData());
                    }
                });
    }
}

