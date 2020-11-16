package com.p8.inspection.mvp.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.p8.common.base.BaseActivity;
import com.p8.inspection.R;
import com.p8.inspection.mvp.ui.entry.fragment.LoginFragment;
import com.p8.inspection.mvp.ui.main.fragment.MainFragment;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * @author : WX.Y
 * date : 2020/10/23 18:21
 * description :
 */
public class MainActivity extends BaseActivity {

    public static void start(Context context){
        context.startActivity(new Intent(context,MainActivity.class));
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
            loadRootFragment(R.id.fl_container, MainFragment.newInstance());
        }
    }


    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
    }

    @Override
    public void setListener() {

    }

    @Override
    public void doBusiness(Context mContext) {

    }
}

