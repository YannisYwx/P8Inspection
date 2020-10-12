package com.p8.inspection.base;

import android.content.Context;

import com.p8.common.base.BaseActivity;
import com.p8.common.base.mvp.BaseContract;
import com.p8.common.base.mvp.BasePresenter;
import com.p8.inspection.di.component.ActivityComponent;
import com.p8.inspection.di.component.AppComponent;
import com.p8.inspection.di.module.ActivityModule;

import javax.inject.Inject;

/**
 * author : WX.Y
 * date : 2020/9/7 14:51
 * description :
 */
public abstract class DaggerMVPActivity<P extends BasePresenter<V>, V extends BaseContract.IBaseView> extends BaseActivity implements BaseContract.IBaseView {

    @Inject
    public P presenter;

    @Override
    public void initData() {
        bindMvp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindMvp();
    }

    private void bindMvp() {
        injectThis(getActivityComponent());
        if (presenter != null) {
            presenter.onAttachView((V) this);
            getLifecycle().addObserver(presenter);
        }
    }

    private void unbindMvp() {
        if (presenter != null) {
            presenter.onDetachView();
            presenter = null;
        }
    }

    public ActivityComponent getActivityComponent(){
        return AppComponent.getInstance().addSub(new ActivityModule(this));
    }

    public abstract void injectThis(ActivityComponent component);

    @Override
    public void onTokenInvalid(String msg) {
        showMsg(msg);
    }

    @Override
    public Context getContext() {
        return this;
    }
}

