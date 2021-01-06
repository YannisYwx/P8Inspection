package com.p8.inspection.data.net.observer;

import androidx.annotation.NonNull;

import com.orhanobut.logger.Logger;
import com.p8.common.base.mvp.BaseContract;
import com.p8.common.dialog.YDialog;
import com.p8.common.http.HttpError;
import com.p8.common.http.HttpResponse;
import com.p8.common.rx.ObservableSubscriber;
import com.p8.inspection.P8ParkingApplication;
import com.p8.inspection.data.Constants;
import com.p8.inspection.widget.DialogUtils;

/**
 * @author : WX.Y
 * date : 2020/9/18 14:19
 * description :
 */
public abstract class P8HttpSubscriber<T> extends ObservableSubscriber<T> {

    private String msg;

    BaseContract.IBaseView mView;

    private YDialog mMsgDialog;

    public P8HttpSubscriber(@NonNull BaseContract.IBaseView view) {
        this.msg = "网络请求中...";
        this.mView = view;
    }

    public P8HttpSubscriber(BaseContract.IBaseView view, String msg) {
        this.msg = msg;
        this.mView = view;
    }

    @Override
    protected void onStart() {
        if (isShowProgressDialog()) {
            mMsgDialog = DialogUtils.createProgressDialog(mView.getContext(), msg);
        }
        super.onStart();
    }

    @Override
    protected void onEnd() {
        if (isShowProgressDialog()) {
            DialogUtils.closeLoadingDialog(mView.getContext());
            if(mMsgDialog != null) {
                mMsgDialog.dismiss();
                mMsgDialog = null;
            }
        }
        super.onEnd();
    }

    public boolean isShowProgressDialog() {
        return false;
    }

    @Override
    protected void onFail(HttpError httpError) {
        if (mView != null) {
            mView.showMsg(httpError.msg);
            if (httpError.body instanceof Throwable) {
                String msg = (((Throwable) httpError.body)).getMessage();
                Logger.e(msg);
            }
        }
    }

    @Override
    public void onNext(T t) {
        if (t instanceof HttpResponse<?>) {
            HttpResponse tr = (HttpResponse) t;
            int code = ((HttpResponse) t).getCode();
            Logger.d(tr.toString());
            switch (code) {
                case Constants.P8Code.SUCCESS:
                    onSuccess(t);
                    break;
                case Constants.P8Code.FAILED:
                    onFail(new HttpError(tr.getMsg()));
                    break;
                case Constants.P8Code.TOKEN_ERROR:
                    mView.onTokenInvalid(tr.getMsg());
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 执行成功回调
     *
     * @param t 返回数据
     */
    protected abstract void onSuccess(T t);

}

