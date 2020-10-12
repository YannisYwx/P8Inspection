package com.p8.inspection.mvp.ui.fragment;

import android.view.View;

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
    @Override
    public void injectThis(FragmentComponent fragmentComponent) {

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
    protected void refreshContentView(View view) {

    }

    @Override
    public int setLayoutId() {
        return 0;
    }

    @Override
    public void onComplete() {

    }

}

