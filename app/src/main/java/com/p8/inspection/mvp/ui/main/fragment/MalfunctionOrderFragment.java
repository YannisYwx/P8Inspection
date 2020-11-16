package com.p8.inspection.mvp.ui.main.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.p8.common.base.BaseStatusPagerFragment;
import com.p8.inspection.R;

/**
 * @author : WX.Y
 * date : 2020/10/27 17:34
 * description : 故障工单
 */
public class MalfunctionOrderFragment extends BaseStatusPagerFragment {

    public static MalfunctionOrderFragment newInstance() {
        return new MalfunctionOrderFragment();
    }

    @Override
    public void initView(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {
        bindClickListener(R.id.civ_qr_code_invalid, R.id.civ_baffle_invalid, R.id.civ_other_problem);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.civ_qr_code_invalid:
                //二维码失效
                break;
            case R.id.civ_baffle_invalid:
                //挡板不下降
                break;
            case R.id.civ_other_problem:
                //其他问题
                start(SubmitWorkOrderFragment.newInstance());
                break;
            default:
                break;
        }
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_malfunction_order;
    }

    @Override
    public int setTitle() {
        return R.string.title_malfunction_order;
    }
}

