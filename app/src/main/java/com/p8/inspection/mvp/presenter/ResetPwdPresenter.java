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
 * description : 重置密码
 */
public class ResetPwdPresenter extends BasePresenter<ResetPwdContract.View> implements ResetPwdContract.Presenter {

    DataManager mDataManager;

    @Inject
    public ResetPwdPresenter(DataManager manager) {
        this.mDataManager = manager;
    }


    @Override
    public void resetPassword(String url, String newPassword, String oldPassword) {
        mDataManager.resetPassword(url, newPassword, oldPassword)
                .compose(RxUtils.getDefaultOSchedulers())
                .as(bindLifecycle())
                .subscribe(new P8HttpSubscriber<HttpResponse<String>>(mView) {
                    @Override
                    protected void onSuccess(HttpResponse<String> stringHttpResponse) {
                        mView.resetPasswordSuccess();
                    }
                });
    }
}

