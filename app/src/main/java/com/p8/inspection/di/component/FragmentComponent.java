package com.p8.inspection.di.component;

import com.p8.inspection.di.module.FragmentModule;
import com.p8.inspection.mvp.ui.entry.fragment.LoginFragment;
import com.p8.inspection.mvp.ui.main.me.fragment.DeviceBindingFragment;
import com.p8.inspection.mvp.ui.main.me.fragment.ParkingMonitorFragment;
import com.p8.inspection.mvp.ui.entry.fragment.ResetPwdFragment;
import com.p8.inspection.mvp.ui.fragment.TestFragment;
import com.p8.inspection.mvp.ui.main.attendance.fragment.AttendanceFragment;
import com.p8.inspection.mvp.ui.main.attendance.fragment.HistoryFragment;
import com.p8.inspection.mvp.ui.main.me.fragment.DeviceDebugFragment;
import com.p8.inspection.mvp.ui.main.me.fragment.MeFragment;
import com.p8.inspection.mvp.ui.main.me.fragment.ResetPasswordFragment;
import com.p8.inspection.mvp.ui.main.me.fragment.SearchLordFragment;

import dagger.Subcomponent;

/**
 * @author : WX.Y
 * date : 2020/9/8 15:47
 * description : 注入器
 */
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {
    @Deprecated
    void inject(ResetPwdFragment resetPwdFragment);

    void inject(MeFragment meFragment);

    void inject(LoginFragment meFragment);

    void inject(AttendanceFragment attendanceFragment);

    void inject(ResetPasswordFragment resetPasswordFragment);

    void inject(ParkingMonitorFragment parkingMonitorFragment);

    void inject(HistoryFragment historyFragment);

    void inject(DeviceDebugFragment debugFragment);

    void inject(DeviceBindingFragment bindingFragment);

    void inject(SearchLordFragment searchLordFragment);

    void inject(TestFragment testFragment);

/*    @Subcomponent.Builder
    interface Builder {
        FragmentComponent build();
    }*/
}

