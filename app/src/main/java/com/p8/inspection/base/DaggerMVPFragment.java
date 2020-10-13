package com.p8.inspection.base;

import android.content.Context;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.p8.common.base.BaseStatusPagerFragment;
import com.p8.common.base.mvp.BaseContract;
import com.p8.common.base.mvp.BasePresenter;
import com.p8.common.utils.ToastUtils;
import com.p8.inspection.R;
import com.p8.inspection.di.component.AppComponent;
import com.p8.inspection.di.component.FragmentComponent;
import com.p8.inspection.di.module.FragmentModule;

import javax.inject.Inject;

/**
 * author : WX.Y
 * date : 2020/9/16 16:44
 * description :
 */
public abstract class DaggerMVPFragment<P extends BasePresenter<V>, V extends BaseContract.IBaseView>
        extends BaseStatusPagerFragment
        implements BaseContract.IBaseView {

    @Inject
    public P presenter;

    @Override
    public void onAttach(@NonNull Context context) {
        //开始注入目标
        injectThis(getFragmentComponent());
        if (presenter != null) {
            presenter.onAttachView((V) this);
            getLifecycle().addObserver(presenter);
        }
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        presenter.onDetachView();
    }

    /**
     * 注入目标
     *
     * @param fragmentComponent
     */
    public abstract void injectThis(FragmentComponent fragmentComponent);

    /**
     * 获取注入组件
     *
     * @return FragmentComponent
     */
    private FragmentComponent getFragmentComponent() {
        return AppComponent.getInstance().addSub(new FragmentModule(this));
    }

    @Override
    public void showMsg(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    protected void triggerLoadData() {

    }

    @Override
    public void onTokenInvalid(String msg) {
        showMsg(msg);
    }

    @Nullable
    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        if (hasTitleBar()) {
            mTitleBar.setLeftDrawable(R.mipmap.nav_button_search_back);
            mTitleBar.setBackgroundColor(mContext.getResources().getColor(R.color.main_default_color));
            mTitleBar.getTitleView().setTextColor(Color.WHITE);
        }
    }

    @Override
    public void onComplete() {

    }
}

