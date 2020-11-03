package com.p8.inspection.mvp.ui.main.me.fragment;

import android.view.View;

import com.p8.inspection.R;
import com.p8.inspection.base.DaggerMvpFragment;
import com.p8.inspection.di.component.FragmentComponent;
import com.p8.inspection.mvp.contract.DeviceDebugContract;
import com.p8.inspection.mvp.presenter.DeviceDebugPresenter;

/**
 * @author : WX.Y
 * date : 2020/10/29 10:24
 * description :
 */
public class DeviceDebugFragment extends DaggerMvpFragment<DeviceDebugPresenter, DeviceDebugContract.View> implements DeviceDebugContract.View {

    public static DeviceDebugFragment newInstance() {
        return new DeviceDebugFragment();
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

    }

    @Override
    protected void refreshContentView(View view) {

    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_device_debug;
    }

    @Override
    public void getDeviceInfoSuccess() {

    }

    @Override
    public int setTitle() {
        return R.string.title_device_debug;
    }
}

