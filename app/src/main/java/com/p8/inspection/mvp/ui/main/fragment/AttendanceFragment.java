package com.p8.inspection.mvp.ui.main.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.p8.common.widget.TitleBar;
import com.p8.inspection.R;
import com.p8.inspection.base.DaggerMvpFragment;
import com.p8.inspection.di.component.FragmentComponent;
import com.p8.inspection.mvp.contract.AttendanceContract;
import com.p8.inspection.mvp.presenter.AttendancePresenter;
import com.p8.inspection.widget.ClockView;

/**
 * @author : WX.Y
 * date : 2020/10/26 15:57
 * description : 签到签出
 */
public class AttendanceFragment extends DaggerMvpFragment<AttendancePresenter, AttendanceContract.View> implements AttendanceContract.View {

    ClockView mClockView;

    public static AttendanceFragment newInstance() {
        return new AttendanceFragment();
    }

    @Override
    public void initView(View view, @Nullable Bundle savedInstanceState) {
        mClockView = $(R.id.cv_clock);
        mTitleBar.setRightImageViewRes(R.mipmap.icon_calendar);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (mClockView != null) {
            mClockView.startTimer();
        }
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();

    }

    @Override
    public void onTitleBarRightClick() {
        start(HistoryFragment.newInstance());
    }

    @Override
    public int setTitle() {
        return R.string.title_attendance;
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void injectThis(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_attendance;
    }

    @Override
    public boolean hasTitleBar() {
        return true;
    }

    @Override
    public void onClockInSuccess() {

    }

    @Override
    public void onClockOutSuccess() {

    }

    @Override
    public void onClockFailed() {

    }
}

