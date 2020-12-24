package com.p8.inspection.mvp.ui.main.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.p8.common.base.BaseStatusPagerFragment;
import com.p8.inspection.R;

/**
 * @author : WX.Y
 * date : 2020/11/6 11:20
 * description :设备详情
 */
public class DeviceDetailFragment extends BaseStatusPagerFragment {

    public static DeviceDetailFragment newInstance() {
        Bundle args = new Bundle();
        DeviceDetailFragment fragment = new DeviceDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onClick(int viewId) {
        switch (viewId) {
            case R.id.civ_car_owner_bug:
            case R.id.civ_land_owner_bug:
            case R.id.civ_system_owner_bug:
            case R.id.civ_other_bug:
                start(MalfunctionOrderFragment.newInstance());
            default:
                break;
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {
        bindClickListener(R.id.civ_car_owner_bug, R.id.civ_land_owner_bug, R.id.civ_system_owner_bug, R.id.civ_other_bug);
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_device_detail;
    }

    @Override
    public int setTitle() {
        return R.string.title_parking_detail;
    }
}

