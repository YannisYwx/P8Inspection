package com.p8.inspection.mvp.ui.main.attendance.fragment;

import android.view.View;

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
 * description :
 */
public class AttendanceFragment extends DaggerMvpFragment<AttendancePresenter, AttendanceContract.View> implements AttendanceContract.View {

    ClockView mClockView;

    public static AttendanceFragment newInstance() {
        return new AttendanceFragment();
    }

    @Override
    public void initView(View view) {
        mClockView = $(R.id.cv_clock);
        mTitleBar.setRightText("历史记录");
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
    public void onEventTrigger(int type) {
        super.onEventTrigger(type);
        if (type == TitleBar.Event.TV_RIGHT) {
            startByParent(HistoryFragment.newInstance());
        }
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
    protected void triggerLoadData() {

    }

    @Override
    protected void refreshContentView(View view) {

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
    public boolean isTitleBarBackEnable() {
        return false;
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

