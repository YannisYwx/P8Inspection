package com.p8.inspection.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.p8.common.base.BaseStatusPagerFragment;
import com.p8.common.base.mvp.BaseContract;
import com.p8.common.base.mvp.BasePresenter;
import com.p8.inspection.R;
import com.p8.inspection.di.component.AppComponent;
import com.p8.inspection.di.component.FragmentComponent;
import com.p8.inspection.di.module.FragmentModule;

import javax.inject.Inject;

import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author : WX.Y
 * date : 2020/9/16 16:44
 * description :
 */
public abstract class DaggerMvpFragment<P extends BasePresenter<V>, V extends BaseContract.IBaseView>
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
        if (presenter != null) {
            presenter.onDetachView();
        }
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        loadData();
    }

    public void loadData() {

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
            if (isTitleBarBackEnable()) {
                mTitleBar.setLeftDrawable(R.mipmap.nav_button_search_back);
            }
            mTitleBar.setBackgroundColor(mContext.getResources().getColor(R.color.main_default_color));
            mTitleBar.getTitleView().setTextColor(Color.WHITE);
            mTitleBar.setTitle(setTitle() == 0 ? "" : getString(setTitle()));
        }
    }

    public boolean isTitleBarBackEnable() {
        return true;
    }

    @Override
    public void onComplete() {

    }

    /**
     * 设置标题 默认为空
     *
     * @return 页面title
     */
    public @StringRes
    int setTitle() {
        return 0;
    }

    public void startByParent(ISupportFragment fragment) {
        if (getParentFragment() != null) {
            ((SupportFragment) getParentFragment()).start(fragment);
        }
    }
}

