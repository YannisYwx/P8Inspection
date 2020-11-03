package com.p8.inspection.mvp.presenter;

import com.p8.common.base.mvp.BasePresenter;
import com.p8.common.http.HttpResponse;
import com.p8.common.rx.RxUtils;
import com.p8.inspection.data.DataManager;
import com.p8.inspection.data.bean.VCode;
import com.p8.inspection.data.net.observer.P8HttpSubscriber;
import com.p8.inspection.mvp.contract.ResetPwdContract;

import javax.inject.Inject;

/**
 * @author : WX.Y
 * date : 2020/9/11 17:43
 * description :
 */
public class ResetPwdPresenter extends BasePresenter<ResetPwdContract.View> implements ResetPwdContract.Presenter {

    DataManager mDataManager;

    @Inject
    public ResetPwdPresenter(DataManager manager) {
        this.mDataManager = manager;
    }

    @Override
    public void getVCode(String phoneNum) {
        mDataManager.getVCode(phoneNum)
                .compose(RxUtils.getDefaultOSchedulers())
                .as(bindLifecycle())
                .subscribe(new P8HttpSubscriber<HttpResponse<VCode>>(mView) {
                    @Override
                    protected void onSuccess(HttpResponse<VCode> vCodeHttpResponse) {
                        if (vCodeHttpResponse.getData() != null) {
                            mView.showMsg(vCodeHttpResponse.getData().getMsg());
                        }
                    }
                });
    }

    @Override
    public void resetPassword(String phoneNumber, String vCode, String password) {
        mDataManager.resetPassword(phoneNumber, vCode, password)
                .compose(RxUtils.getDefaultOSchedulers())
                .as(bindLifecycle())
                .subscribe(new P8HttpSubscriber<HttpResponse<String>>(mView) {
                    @Override
                    protected void onSuccess(HttpResponse<String> stringHttpResponse) {

                    }
                });
    }
}

