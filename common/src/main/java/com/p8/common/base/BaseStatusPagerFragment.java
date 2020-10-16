package com.p8.common.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.p8.common.R;
import com.p8.common.utils.StatusBarUtils;
import com.p8.common.widget.StatusPager;
import com.p8.common.widget.TitleBar;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author : WX.Y
 * date : 2020/9/16 14:47
 * description :
 */
public abstract class BaseStatusPagerFragment extends SupportFragment implements View.OnClickListener, TitleBar.OnEventTriggerListener {
    public static final String EXTRA = "_extra";

    protected ConstraintLayout mRootView;
    protected Context mContext;
    protected View contentView;
    protected StatusPager mStatusPager;
    protected TitleBar mTitleBar;
    @LayoutRes
    private int loadingPager;
    @LayoutRes
    private int errorPager;
    @LayoutRes
    private int emptyPager;
    @IdRes
    private int btnRetry;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (contentView == null) {
            contentView = inflater.inflate(setLayoutId(), null);
        }
        mRootView = (ConstraintLayout) inflater.inflate(com.p8.common.R.layout.fragment_toolbar, container, false);
        createStatusPager(contentView);
        addContentView(mStatusPager);
        return mRootView;
    }

    private void addContentView(View contentView) {
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.topToBottom = R.id.titleBar;
        layoutParams.leftToLeft = R.id.layout_toolbar;
        layoutParams.rightToRight = R.id.layout_toolbar;
        layoutParams.bottomToBottom = R.id.layout_toolbar;
        layoutParams.height = getContentViewHeight();
        contentView.setLayoutParams(layoutParams);
        mRootView.addView(contentView);
    }

    private int getContentViewHeight() {
        return ScreenUtils.getScreenHeight() - StatusBarUtils.getStatusBarHeight(this.mContext) - (hasTitleBar() ? 0 : 1) * AdaptScreenUtils.pt2Px(144);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTitleBar();
        initView(view);
        initData();
        setListener();
    }

    public void initTitleBar() {
        mTitleBar = mRootView.findViewById(R.id.titleBar);
        mTitleBar.setOnEventTriggerListener(this);
        if (hasTitleBar()) {
            mTitleBar.setPadding(0, StatusBarUtils.getStatusBarHeight(this.mContext), 0, 0);
        } else {
            dismissTitleBar();
        }
    }

    public void dismissTitleBar() {
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.height = StatusBarUtils.getStatusBarHeight(mContext);
        mTitleBar.setLayoutParams(params);
    }

    public boolean hasTitleBar() {
        return true;
    }

    /**
     * 初始化控件
     *
     * @param view
     */
    public abstract void initView(View view);

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 设置监听
     */
    public abstract void setListener();

    private void createStatusPager(View contentView1) {
        mStatusPager = new StatusPager(mContext) {

            @Override
            public void triggerLoadData() {
                BaseStatusPagerFragment.this.triggerLoadData();
            }

            @Override
            public void refreshContentView(View view) {
                BaseStatusPagerFragment.this.refreshContentView(view);
            }

            @Override
            public View initContentView() {
                return contentView1;
            }
        };
    }

    private View createView() {
        mStatusPager = new StatusPager(mContext) {

            @Override
            public void triggerLoadData() {
                BaseStatusPagerFragment.this.triggerLoadData();
            }

            @Override
            public void refreshContentView(View view) {
                BaseStatusPagerFragment.this.refreshContentView(view);
            }

            @Override
            public View initContentView() {
                return contentView;
            }
        };
        return mStatusPager;
    }

    /**
     * 触发加载数据
     */
    protected abstract void triggerLoadData();

    /**
     * 刷新内容视图
     *
     * @param view
     */
    protected abstract void refreshContentView(View view);

    /**
     * 设置布局文件
     *
     * @return
     */
    public abstract int setLayoutId();

    public void showEmptyPager() {
        mStatusPager.showEmptyView();
    }

    public void showSuccessPager() {
        mStatusPager.showSuccessView();
    }

    public void showLoadingPager() {
        mStatusPager.showLoadingView();
    }

    public void showErrorPager() {
        mStatusPager.showErrorView();
    }

    @Override
    public void onClick(View v) {

    }

    protected <T extends View> T $(int resId) {
        if (contentView == null) throw new NullPointerException("contentView can't be null");
        return contentView.findViewById(resId);
    }

    @Override
    public void onEventTrigger(int type) {
        if (type == TitleBar.Event.imageLeft) {
            onTitleBarLeftClick();
        } else if (type == TitleBar.Event.imageRight) {
            onTitleBarRightClick();
        }
    }

    /**
     * title bar 左边点击事件
     */
    public void onTitleBarLeftClick() {
        pop();
    }

    /**
     * title bar 右边点击事件
     */
    public void onTitleBarRightClick() {

    }

    public void setStatusBarLightMode(boolean lightMode) {
        ((BaseActivity) _mActivity).setStatusBarLightMode(lightMode);
    }
}

