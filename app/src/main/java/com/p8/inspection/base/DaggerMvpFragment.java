package com.p8.inspection.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.p8.common.base.BaseStatusPagerFragment;
import com.p8.common.base.mvp.BaseContract;
import com.p8.common.base.mvp.BasePresenter;
import com.p8.inspection.R;
import com.p8.inspection.data.LocalDataManager;
import com.p8.inspection.di.component.AppComponent;
import com.p8.inspection.di.component.FragmentComponent;
import com.p8.inspection.di.module.FragmentModule;
import com.p8.inspection.mvp.ui.EnterActivity;

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
        reLogin();
    }

    @Nullable
    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void onComplete() {

    }

    /**
     * 重新登录
     */
    public void reLogin(){
        LocalDataManager.clear();
        ThreadUtils.runOnUiThreadDelayed(() -> {
            EnterActivity.start(mContext);
            _mActivity.finish();
        }, 500);
    }

    /**
     * 根节点替换fragment 适用于多层级的fragment跳转
     *
     * @param fragment
     */
    public void startByParent(ISupportFragment fragment) {
        if (getParentFragment() != null) {
            ((SupportFragment) getParentFragment()).start(fragment);
        }
    }
}

