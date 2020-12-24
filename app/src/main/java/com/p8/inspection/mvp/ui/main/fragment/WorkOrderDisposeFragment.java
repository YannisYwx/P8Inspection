package com.p8.inspection.mvp.ui.main.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.p8.common.base.BaseStatusPagerFragment;
import com.p8.inspection.R;

/**
 * @author : WX.Y
 * date : 2020/11/6 11:20
 * description :工单处理
 */
public class WorkOrderDisposeFragment extends BaseStatusPagerFragment {

    public static WorkOrderDisposeFragment newInstance() {
        Bundle args = new Bundle();
        WorkOrderDisposeFragment fragment = new WorkOrderDisposeFragment();
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
//        bindClickListener(R.id.civ_car_owner_bug, R.id.civ_land_owner_bug, R.id.civ_system_owner_bug, R.id.civ_other_bug);
    }


    @Override
    public int setLayoutId() {
        return R.layout.fragment_work_order_dispose;
    }

    @Override
    public int setTitle() {
        return R.string.title_work_order_dispose;
    }
}

