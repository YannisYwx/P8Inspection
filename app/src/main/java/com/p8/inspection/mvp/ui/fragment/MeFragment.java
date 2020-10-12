package com.p8.inspection.mvp.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.p8.inspection.R;
import com.p8.inspection.base.DaggerMVPFragment;
import com.p8.inspection.di.component.FragmentComponent;
import com.p8.inspection.mvp.contract.MeContract;
import com.p8.inspection.mvp.presenter.MePresenter;

/**
 * author : WX.Y
 * date : 2020/10/12 15:37
 * description :
 */
public class MeFragment extends DaggerMVPFragment<MePresenter, MeContract.View> implements MeContract.View {


    public static MeFragment newInstance() {
        Bundle args = new Bundle();
        MeFragment fragment = new MeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void injectThis(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {
        $(R.id.civ_berth_monitor).setOnClickListener(this);
        $(R.id.civ_device_update).setOnClickListener(this);
        $(R.id.civ_order).setOnClickListener(this);
        $(R.id.civ_sign).setOnClickListener(this);
        $(R.id.civ_user).setOnClickListener(this);
        $(R.id.civ_user_center).setOnClickListener(this);
    }

    @Override
    protected void refreshContentView(View view) {

    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    public void onComplete() {

    }

}

