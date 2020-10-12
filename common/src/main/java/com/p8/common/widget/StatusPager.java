package com.p8.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.IdRes;
import androidx.annotation.IntDef;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.p8.common.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import pl.droidsonroids.gif.GifImageView;

/**
 * author : WX.Y
 * date : 2020/9/16 15:42
 * description : 展示多个中状态的页面
 */
public abstract class StatusPager extends FrameLayout {

    @LayoutRes
    private int loadingPager;
    @LayoutRes
    private int errorPager;
    @LayoutRes
    private int emptyPager;
    @IdRes
    private int btnRetry;

    /**
     * 加载中
     */
    private View mErrorView;
    /**
     * 错误的布局
     */
    private View mEmptyView;
    /**
     * 空数据视图
     */
    private View mContentView;
    /**
     * 内容视图
     */
    private View mLoadingView;


    @PagerState
    private int mCurrentState = PagerState.Success;

    @IntDef(value = {PagerState.Loading, PagerState.Error, PagerState.Success, PagerState.Empty})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PagerState {
        /**
         * 加载中
         */
        int Loading = 0;
        /**
         * 成功
         */
        int Error = 1;
        /**
         * 成功
         */
        int Success = 2;
        /**
         * 空（没有数据）
         */
        int Empty = 3;
    }

    public StatusPager(@NonNull Context context) {
        super(context);
        loadingPager = R.layout.pager_loading;
        errorPager = R.layout.pager_error;
        emptyPager = R.layout.pager_empty;
        btnRetry = R.id.btn_error_retry;
        initStatusPager(context);
    }

    public int initStatus() {
        mCurrentState = PagerState.Loading;
        return mCurrentState;
    }

    public StatusPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StatusPager(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initStatusPager(Context context) {
        //1.初始化3种 常规视图
        mLoadingView = View.inflate(context, loadingPager, null);
        mEmptyView = View.inflate(context, emptyPager, null);
        mErrorView = View.inflate(context, errorPager, null);
        GifImageView gifImageView = mLoadingView.findViewById(R.id.gifImageView);
        gifImageView.setImageResource(R.mipmap.loading);
        /*添加空内容视图*/
        if (mEmptyView != null) {
            addView(mEmptyView, new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));
        }
        /*添加错误视图*/
        if (mErrorView != null) {
            addView(mErrorView, new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));
            try {
                assert mEmptyView != null;
                mErrorView.findViewById(btnRetry).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        triggerLoadData();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        /*添加加载中视图*/
        if (mLoadingView != null) {
            addView(mLoadingView, new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));
        }
        //2.初始化内容布局
        mContentView = initContentView();
        addView(mContentView, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));

        //3.隐藏3种常规视图
        refreshUIByState();
    }

    public void showLoadingView() {
        mCurrentState = PagerState.Loading;
        refreshUIByState();
    }

    public void showSuccessView() {
        mCurrentState = PagerState.Success;
        refreshUIByState();
    }

    public void showEmptyView() {
        mCurrentState = PagerState.Empty;
        refreshUIByState();
    }

    public void showErrorView(){
        mCurrentState = PagerState.Error;
        refreshUIByState();
    }

    public void refreshUIByState() {
        //同一时刻,只能对外提供一个视图
        mLoadingView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.GONE);
        mContentView.setVisibility(View.GONE);
        switch (mCurrentState) {
            case PagerState.Loading:
                mLoadingView.setVisibility(VISIBLE);
                break;
            case PagerState.Error:
                mErrorView.setVisibility(VISIBLE);
                break;
            case PagerState.Empty:
                mEmptyView.setVisibility(VISIBLE);
                break;
            case PagerState.Success:
                mContentView.setVisibility(VISIBLE);
                refreshContentView(mContentView);
                break;
            default:
                break;
        }
    }

    /**
     * 触发加载数据
     */
    public abstract void triggerLoadData();

    /**
     * 加载成功后刷新UI
     *
     * @param view
     */
    public abstract void refreshContentView(View view);

    /**
     * 主视图界面
     *
     * @return 一般为fragment
     */
    public abstract View initContentView();

}

