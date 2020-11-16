package com.p8.inspection.mvp.presenter;

import com.p8.common.base.mvp.BasePresenter;
import com.p8.common.http.HttpResponse;
import com.p8.common.rx.RxUtils;
import com.p8.inspection.data.DataManager;
import com.p8.inspection.data.bean.Agency;
import com.p8.inspection.data.bean.Landlord;
import com.p8.inspection.data.bean.Landlords;
import com.p8.inspection.data.net.observer.P8HttpSubscriber;
import com.p8.inspection.mvp.contract.LandlordManageContract;
import com.p8.inspection.mvp.contract.SubmitOrderContract;

import javax.inject.Inject;

/**
 * @author : WX.Y
 * date : 2020/10/12 15:41
 * description : 地主管理业务
 */
public class LandlordManagePresenter extends BasePresenter<LandlordManageContract.View> implements LandlordManageContract.Presenter {
    DataManager mDataManager;

    @Inject
    public LandlordManagePresenter(DataManager manager) {
        this.mDataManager = manager;
    }

    @Override
    public void requestLandlordList(int currentPage, int pageSize) {
        mDataManager.getLandlords(currentPage, pageSize).compose(RxUtils.getDefaultOSchedulers())
                .as(bindLifecycle()).subscribe(new P8HttpSubscriber<HttpResponse<Landlords>>(mView) {
            @Override
            protected void onSuccess(HttpResponse<Landlords> landlordsHttpResponse) {
                if (landlordsHttpResponse.getData() != null) {
                    mView.onRequestLandlordSuccess(landlordsHttpResponse.getData());
                }
            }
        });
    }

    @Override
    public void deleteLandlord(int landlordId) {

    }

    @Override
    public void modifyLandlord(Landlord landlord) {

    }

    @Override
    public void addLandlord(Landlord landlord) {

    }
}

