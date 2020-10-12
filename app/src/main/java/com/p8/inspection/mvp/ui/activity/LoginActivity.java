package com.p8.inspection.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.View;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.p8.common.base.BaseActivity;
import com.p8.inspection.R;
import com.p8.inspection.mvp.ui.fragment.LoginFragment;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * author : WX.Y
 * date : 2020/9/16 17:22
 * description :
 */
public class LoginActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @Override
    public void initData() {

    }

    @Override
    public int bindLayout() {
        return R.layout.fl_container;
    }

    @Override
    public void initView(View view) {
        if (findFragment(LoginFragment.class) == null) {
            loadRootFragment(R.id.fl_container, LoginFragment.newInstance());
        }
    }

    @Override
    public void setListener() {

    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
    }

    @Override
    public Resources getResources() {
        return AdaptScreenUtils.adaptWidth(super.getResources(), 1080);
    }
}

