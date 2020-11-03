package com.p8.common.base.mvp;

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
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.p8.common.R;
import com.p8.common.base.BaseActivity;
import com.p8.common.widget.StatusPager;
import com.p8.common.widget.TitleBar;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author : WX.Y
 * date : 2020/9/16 14:47
 * description :
 */
public abstract class BaseFragment extends SupportFragment implements View.OnClickListener, TitleBar.OnEventTriggerListener {
    public static final String EXTRA = "_extra";

    protected Context mContext;
    protected View contentView;

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
            contentView = inflater.inflate(setLayoutId(), container,false);
        }
        return contentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
        setListener();
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

    @Override
    public void onClick(View v) {

    }

    protected <T extends View> T $(int resId) {
        if (contentView == null) {
            throw new NullPointerException("contentView can't be null");
        }
        return contentView.findViewById(resId);
    }

    @Override
    public void onEventTrigger(int type) {
        if (type == TitleBar.Event.IV_LEFT) {
            onTitleBarLeftClick();
        } else if (type == TitleBar.Event.IV_RIGHT) {
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

