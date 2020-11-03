package com.p8.common.base.mvp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author : WX.Y
 * date : 2020/9/7 16:30
 * description :
 */
public class BasePresenter<V extends BaseContract.IBaseView> implements BaseContract.IBasePresenter<V>, DefaultLifecycleObserver {

    protected V mView;

    protected LifecycleOwner lifecycleOwner;

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        this.lifecycleOwner = owner;
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        onDetachView();
        if (lifecycleOwner != null) {
            lifecycleOwner.getLifecycle().removeObserver(this);
            lifecycleOwner = null;
        }
    }

    protected <T> AutoDisposeConverter<T> bindLifecycle() {
        if (null == lifecycleOwner) {
            throw new NullPointerException("lifecycleOwner == null");
        }
        //默认指定在ON_DESTROY生命周期的时候解除订阅
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner, Lifecycle.Event.ON_DESTROY));
    }


    @Override
    public void onAttachView(V view) {
        this.mView = view;
    }

    @Override
    public void onDetachView() {
        this.mView = null;
    }

    public V getView() {
        return mView;
    }

    public Context getContext() {
        if (mView instanceof SupportFragment) {
            return ((SupportFragment) mView).getSupportDelegate().getActivity();
        } else {
            return ((SupportActivity) mView);
        }
    }
}

