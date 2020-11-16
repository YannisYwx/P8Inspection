package com.p8.common.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.p8.common.R;
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

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        BarUtils.setStatusBarLightMode(_mActivity, !hasTitleBar());
    }

    /**
     * 添加主视图
     * 因为ConstraintLayout的一些缘故 需要重新计算主视图的高度
     *
     * @param contentView 主视图
     */
    private void addContentView(View contentView) {
//        int sh = ScreenUtils.getScreenHeight();
//        int sbh = BarUtils.getStatusBarHeight();
//        int nah = BarUtils.getNavBarHeight();
//        int ch = getContentViewHeight();
//        Logger.e("ScreenHeight + " + sh + "\nStatusBarHeight = " + sbh + "\nNavBarHeight = " + nah + "\nContentViewHeight = " + ch);
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

    /**
     * 获取主视图高度
     *
     * @return 屏幕高度 - 系统状态栏高度 - 自定义titleBar高度 - 系统导航栏高度
     */
    private int getContentViewHeight() {
        return ScreenUtils.getScreenHeight()
                - BarUtils.getStatusBarHeight()
                - (hasTitleBar() ? 1 : 0) * AdaptScreenUtils.pt2Px(144)
                - (!BarUtils.isNavBarVisible(_mActivity) ? 0 : BarUtils.getNavBarHeight());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        beforeInitView();
        initTitleBar();
        initView(view, savedInstanceState);
        initData();
        setListener();
    }

    /**
     * 一些需要在初始化UI之前需要做的事 比如根据数据改变title bar内容
     */
    public void beforeInitView() {

    }

    public void initTitleBar() {
        mTitleBar = mRootView.findViewById(R.id.titleBar);
        if (hasTitleBar()) {
            mTitleBar.setOnEventTriggerListener(this);
            //需要填充状态栏
            mTitleBar.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0);
            mTitleBar.setTitleColor(Color.WHITE);
            mTitleBar.setRightTextColor(Color.WHITE);
            mTitleBar.setLeftTextColor(Color.WHITE);

            if (isTitleBarBackEnable()) {
                mTitleBar.setLeftDrawable(R.mipmap.icon_back);
            }
            mTitleBar.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
            mTitleBar.getTitleView().setTextColor(Color.WHITE);
            mTitleBar.setTitle(setTitle() == 0 ? "" : getString(setTitle()));
        } else {
            dismissTitleBar();
        }
    }

    public boolean isTitleBarBackEnable() {
        return true;
    }


    /**
     * 设置标题 默认为空
     *
     * @return 页面title
     */
    @StringRes
    public int setTitle() {
        return 0;
    }

    public void dismissTitleBar() {
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.height = BarUtils.getStatusBarHeight();
        mTitleBar.setLayoutParams(params);
        mTitleBar.setVisibility(View.GONE);
    }

    public boolean hasTitleBar() {
        return true;
    }

    /**
     * 初始化控件
     *
     * @param view
     * @param savedInstanceState
     */
    public abstract void initView(View view, @Nullable Bundle savedInstanceState);

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 设置监听
     */
    public abstract void setListener();

    private void createStatusPager(View contentView) {
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
    }

    /**
     * 触发加载数据
     */
    protected void triggerLoadData() {
    }

    /**
     * 刷新内容视图
     *
     * @param view
     */
    protected void refreshContentView(View view) {
    }

    ;

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
        onClick(v.getId());
    }

    public void onClick(@IdRes int viewId) {

    }

    protected <T extends View> T $(int resId) {
        if (contentView == null) {
            throw new NullPointerException("contentView can't be null");
        }
        return contentView.findViewById(resId);
    }

    @Override
    public void onEventTrigger(int type) {
        if (type == TitleBar.Event.IV_LEFT || type == TitleBar.Event.TV_LEFT) {
            onTitleBarLeftClick();
        } else if (type == TitleBar.Event.IV_RIGHT || type == TitleBar.Event.TV_RIGHT) {
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

    public void bindClickListener(int... ids) {
        for (int id : ids) {
            $(id).setOnClickListener(this);
        }
    }

}

