package com.p8.inspection.mvp.presenter;

import com.p8.common.base.mvp.BasePresenter;
import com.p8.common.http.HttpResponse;
import com.p8.common.rx.RxUtils;
import com.p8.inspection.data.DataManager;
import com.p8.inspection.data.bean.Agency;
import com.p8.inspection.data.bean.Inspection;
import com.p8.inspection.data.net.observer.P8HttpSubscriber;
import com.p8.inspection.mvp.contract.MainContract;

import javax.inject.Inject;

/**
 * @author : WX.Y
 * date : 2020/10/12 15:41
 * description : 主页业务
 */
public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {
    DataManager mDataManager;

    @Inject
    public MainPresenter(DataManager manager) {
        this.mDataManager = manager;
    }

    @Override
    public void requestAgencyInfo() {
        mDataManager.getAgencyInfo().compose(RxUtils.getDefaultOSchedulers())
                .as(bindLifecycle()).subscribe(new P8HttpSubscriber<HttpResponse<Agency>>(mView) {
            @Override
            protected void onSuccess(HttpResponse<Agency> agencyHttpResponse) {
                if (agencyHttpResponse.getData() != null) {
                    mView.onRequestAgencyInfoSuccess(agencyHttpResponse.getData());
                }
            }
        });
    }

    @Override
    public void requestInspectionInfo() {
        mDataManager.getInspectInfo().compose(RxUtils.getDefaultOSchedulers())
                .as(bindLifecycle()).subscribe(new P8HttpSubscriber<HttpResponse<Inspection>>(mView) {
            @Override
            protected void onSuccess(HttpResponse<Inspection> inspectionHttpResponse) {
                if (inspectionHttpResponse.getData() != null) {
                    mView.onRequestInspectionSuccess(inspectionHttpResponse.getData());
                }
            }
        });
    }
}

