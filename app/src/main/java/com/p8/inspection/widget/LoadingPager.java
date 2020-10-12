package com.p8.inspection.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.AttrRes;
import androidx.annotation.IdRes;
import androidx.annotation.IntDef;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.annotation.WorkerThread;

import com.p8.inspection.R;
import com.p8.inspection.utils.ThreadPoolManager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import pl.droidsonroids.gif.GifImageView;

import static com.p8.inspection.widget.LoadingPager.LoadingState.stateLoading;

/**
 * author : WX.Y
 * date : 2020/9/11 14:18
 * description : 加载页面
 */
public abstract class LoadingPager extends FrameLayout {
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

    private boolean cacheIsEmpty = true;

    private LoadDataTask mLoadDataTask = null;

    private GetDataFromCacheTask mCacheTask = null;

    @LoadingPager.LoadingState
    private int mCurrentState = stateLoading;


    @IntDef(value = {stateLoading, LoadingState.stateError, LoadingState.stateSuccess, LoadingState.stateEmpty, LoadingState.stateCacheEmpty, LoadingState.stateCacheSuccess})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LoadingState {
        /**
         * 加载中
         */
        int stateLoading = 0;
        /**
         * 成功
         */
        int stateError = 1;
        /**
         * 成功
         */
        int stateSuccess = 2;
        /**
         * 空（没有数据）
         */
        int stateEmpty = 3;
        /**
         * 缓存中没有数据
         */
        int stateCacheEmpty = 4;
        /**
         * 缓存中有数据
         */
        int stateCacheSuccess = 5;
    }

    public LoadingPager(@NonNull Context context) {
        super(context);
        loadingPager = R.layout.pager_loading;
        errorPager = R.layout.pager_error;
        emptyPager = R.layout.pager_empty;
        btnRetry = R.id.btn_error_retry;
        initLoadingPager(context);
    }

    public LoadingPager(@NonNull Context context, @LayoutRes int loadingPager, @LayoutRes int errorPager, @LayoutRes int emptyPager, @IdRes int btnRetry) {
        this(context, null, loadingPager, errorPager, emptyPager, btnRetry);
    }

    public LoadingPager(@NonNull Context context, @Nullable AttributeSet attrs, @LayoutRes int loadingPager, @LayoutRes int errorPager, @LayoutRes int emptyPager, @IdRes int btnRetry) {
        this(context, attrs, 0, loadingPager, errorPager, emptyPager, btnRetry);
    }

    public LoadingPager(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @LayoutRes int loadingPager, @LayoutRes int errorPager, @LayoutRes int emptyPager, @IdRes int btnRetry) {
        super(context, attrs, defStyleAttr);
        this.loadingPager = loadingPager;
        this.errorPager = errorPager;
        this.emptyPager = emptyPager;
        this.btnRetry = btnRetry;
        initLoadingPager(context);
    }

    private void initLoadingPager(Context context) {
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

        //4.从缓存中读取数据
        getDataFromCache();
    }


    public void showLoadingView(){
        mCurrentState = LoadingState.stateLoading;
        refreshUIByState();
    }

    public void showSuccessView(){
        mCurrentState = LoadingState.stateSuccess;
        refreshUIByState();
    }

    public void showEmptyView(){
        mCurrentState = LoadingState.stateEmpty;
        refreshUIByState();
    }

    public boolean isCacheIsEmpty() {
        return cacheIsEmpty;
    }

    public void refreshUIByState() {
        //同一时刻,只能对外提供一个视图
        mLoadingView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.GONE);
        mContentView.setVisibility(View.GONE);
        switch (mCurrentState) {
            case stateLoading:
                mLoadingView.setVisibility(VISIBLE);
                break;
            case LoadingState.stateError:
                mErrorView.setVisibility(VISIBLE);
                break;
            case LoadingState.stateEmpty:
                mEmptyView.setVisibility(VISIBLE);
                break;
            case LoadingState.stateSuccess:
                mContentView.setVisibility(VISIBLE);
                refreshContentView(mContentView);
                break;
            case LoadingState.stateCacheEmpty:
                //缓存中数据为空
                cacheIsEmpty = true;
                triggerLoadData();
                break;
            case LoadingState.stateCacheSuccess:
                //缓存中有数据  ->添加成功视图 ->初始化界面
                cacheIsEmpty = false;
                mContentView.setVisibility(VISIBLE);
                refreshContentView(mContentView);
                triggerLoadData();
                break;
            default:
                break;
        }
    }

    /**
     * 初始化内容视图 交由子类完成
     *
     * @return view
     */
    @UiThread
    protected abstract View initContentView();

    /**
     * 从缓存中加载数据 子线程工作
     *
     * @return 加载状态
     */
    @WorkerThread
    protected abstract
    @LoadingState
    int loadDataFromCache();

    /**
     * 加载数据
     *
     * @return 加载状态
     */
    @WorkerThread
    public abstract
    @LoadingState
    int loadData();

    /**
     * 加载数据成功刷新主视图
     *
     * @param contentView 主视图
     */
    protected abstract void refreshContentView(View contentView);

    public void triggerLoadData() {
        boolean isLoadData = (mCurrentState == LoadingState.stateError && mLoadDataTask == null) ||
                (mCurrentState == LoadingState.stateCacheEmpty && mLoadDataTask == null);
        if (isLoadData) {
            // 缓存中没有数据 or 加载错误
            //异步加载前,重置状态
            mCurrentState = stateLoading;
            refreshUIByState();
            //异步加载
            mLoadDataTask = new LoadDataTask();
            ThreadPoolManager.getInstance().getSinglePool().execute(mLoadDataTask);
        } else if (mCurrentState == LoadingState.stateCacheSuccess && mLoadDataTask == null) {
            //缓存中有数据
            //异步加载
            mLoadDataTask = new LoadDataTask();
            ThreadPoolManager.getInstance().getSinglePool().execute(mLoadDataTask);
        }
    }

    private void getDataFromCache() {
        mCacheTask = new LoadingPager.GetDataFromCacheTask();
        ThreadPoolManager.getInstance().getSinglePool().execute(mCacheTask);
    }

    /**
     * 加载数据任务
     */
    private class LoadDataTask implements Runnable {

        @Override
        public void run() {
            //在子线程中,得到具体数据
            mCurrentState = loadData();
            ThreadPoolManager.getInstance().runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    //刷新ui-->mCurState-->refreshUIByState()方法
                    refreshUIByState();
                }
            });
            //走到run方法体的最后相当于任务执行完成了.置空任务
            mLoadDataTask = null;
        }
    }

    private class GetDataFromCacheTask implements Runnable {
        @Override
        public void run() {
            //处理数据
            mCurrentState = loadDataFromCache();
            ThreadPoolManager.getInstance().runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    refreshUIByState();
                }
            });
            mCacheTask = null;
        }
    }
}

