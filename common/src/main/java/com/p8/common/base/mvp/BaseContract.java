package com.p8.common.base.mvp;

import android.content.Context;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

/**
 * @author : WX.Y
 * date : 2020/9/7 14:55
 * description :
 */
public interface BaseContract {

    interface IBaseView extends LifecycleOwner {
        /**
         * 数据加载完成的回调
         */
        void onComplete();

        /**
         * 提示信息
         *
         * @param msg
         */
        void showMsg(String msg);

        /**
         * token失效
         *
         * @param msg
         */
        void onTokenInvalid(String msg);

        /**
         * 获得上下文
         *
         * @return
         */
        Context getContext();
    }


    interface IBasePresenter<T extends BaseContract.IBaseView> {
        /**
         * 绑定视图层
         *
         * @param view UI
         */
        void onAttachView(T view);

        /**
         * 解绑视图层
         */
        void onDetachView();
    }

    interface IMode {
        /**
         * 开始加载数据
         */
        void startLoad();

        /**
         * 加载成功
         */
        void loadSuccess();

        /**
         * 加载失败
         */
        void loadFailed();
    }
}
