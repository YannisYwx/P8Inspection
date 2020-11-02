package com.p8.common.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.p8.common.R;
import com.p8.common.widget.TitleBar;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * @author : WX.Y
 * date : 2020/9/4 15:11
 * description : activity 基类用来做一些初始化工作
 */
public abstract class BaseActivity extends SupportActivity implements View.OnClickListener, TitleBar.OnEventTriggerListener {

    public static final String TAG = BaseActivity.class.getSimpleName();

    protected TitleBar mTitleBar;

    /**
     * 是否允许全屏
     **/
    private boolean mAllowFullScreen = true;
    /**
     * 是否禁止旋转屏幕
     **/
    private boolean isAllowScreenRotate = false;

    /**
     * 当前Activity渲染的视图View
     **/
    private View mContextView = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        initExtras(bundle);
        View mView = bindView();
        if (null == mView) {
            mContextView = LayoutInflater.from(this)
                    .inflate(bindLayout(), null);
        } else {
            mContextView = mView;
        }
        if (mAllowFullScreen) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        BarUtils.setStatusBarColor(this, getResources().getColor(R.color.transparent));
        setContentView(mContextView);
        if (!isAllowScreenRotate()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        initData();
        initView(mContextView);
        setListener();
        doBusiness(this);
        if (hasTitleBar()) {
            initTitleBar();
            BarUtils.setStatusBarLightMode(this, false);
        } else {
            BarUtils.setStatusBarLightMode(this, isLightMode());
        }

    }

    public void setStatusBarLightMode(boolean isLightMode) {
        BarUtils.setStatusBarLightMode(this, isLightMode);
    }

    public boolean isLightMode() {
        return true;
    }

    private void initTitleBar() {
        mTitleBar = $(getDefaultTitleBarId());
        mTitleBar.setOnEventTriggerListener(this);
        if (mTitleBar.getVisibility() == View.VISIBLE && TextUtils.isEmpty(mTitleBar.getTitle()) && mTitleBar.getCenterMode() == 0) {
            mTitleBar.setTitle(initTitle());
        }
    }

    public boolean hasTitleBar() {
        return false;
    }

    public String initTitle() {
        return "";
    }

    public int getDefaultTitleBarId() {
        return R.id.titleBar;
    }

    public void initExtras(Bundle bundle) {

    }

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 是否允许屏幕旋转
     *
     * @return true : 允许屏幕旋转，false : 不允许
     */
    public boolean isAllowScreenRotate() {
        return false;
    }


    /**
     * [绑定视图]
     *
     * @return
     */
    public View bindView() {
        return null;
    }

    /**
     * [绑定布局]
     *
     * @return
     */
    public abstract int bindLayout();

    /**
     * [初始化控件]
     *
     * @param view
     */
    public abstract void initView(final View view);

    /**
     * [绑定控件]
     *
     * @param resId
     * @return
     */
    protected <T extends View> T $(int resId) {
        return super.findViewById(resId);
    }

    /**
     * [设置监听]
     */
    public abstract void setListener();

    @Override
    public void onClick(View v) {
    }

    /**
     * [业务操作]
     *
     * @param mContext
     */
    public abstract void doBusiness(Context mContext);


    /**
     * [页面跳转]
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(BaseActivity.this, clz));
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ToastUtils.cancel();
    }

    /**
     * [简化Toast]
     *
     * @param msg
     */
    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEventTrigger(int type) {
        if (type == TitleBar.Event.imageLeft) {
            finish();
        }
    }

    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
                ActivityCompat.finishAfterTransition(this);
            } else {
                TOUCH_TIME = System.currentTimeMillis();
                //Toast获得activity的context对象导致内存泄漏
                ToastUtils.showShort(R.string.press_again_exit);
            }
        }
    }

    @Override
    public Resources getResources() {
        return AdaptScreenUtils.adaptWidth(super.getResources(), 1080);
    }
}

