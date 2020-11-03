package com.p8.inspection.mvp.ui.main.home.fragment;

import android.view.View;

import com.p8.common.base.BaseStatusPagerFragment;
import com.p8.inspection.R;

/**
 * @author : WX.Y
 * date : 2020/10/26 15:57
 * description :
 */
public class HomeFragment extends BaseStatusPagerFragment {

    public static HomeFragment newInstance(){
        return new HomeFragment();
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {

    }

    @Override
    protected void triggerLoadData() {

    }

    @Override
    protected void refreshContentView(View view) {

    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public boolean hasTitleBar() {
        return false;
    }
}

