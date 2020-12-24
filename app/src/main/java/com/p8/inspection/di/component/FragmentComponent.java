package com.p8.inspection.di.component;

import com.p8.inspection.di.module.FragmentModule;
import com.p8.inspection.mvp.ui.entry.fragment.LoginFragment;
import com.p8.inspection.mvp.ui.main.fragment.DeviceBindingFragment;
import com.p8.inspection.mvp.ui.main.fragment.LandlordInfoFragment;
import com.p8.inspection.mvp.ui.main.fragment.LandlordManageFragment;
import com.p8.inspection.mvp.ui.main.fragment.MainFragment;
import com.p8.inspection.mvp.ui.main.fragment.NoticeManageFragment;
import com.p8.inspection.mvp.ui.main.fragment.OrderManageFragment;
import com.p8.inspection.mvp.ui.main.fragment.ParkingMonitorFragment;
import com.p8.inspection.mvp.ui.entry.fragment.ResetPwdFragment;
import com.p8.inspection.mvp.ui.main.fragment.SubmitWorkOrderFragment;
import com.p8.inspection.mvp.ui.main.fragment.TestFragment;
import com.p8.inspection.mvp.ui.main.fragment.AttendanceFragment;
import com.p8.inspection.mvp.ui.main.fragment.HistoryFragment;
import com.p8.inspection.mvp.ui.main.fragment.DeviceDebugFragment;
import com.p8.inspection.mvp.ui.main.fragment.ResetPasswordFragment;
import com.p8.inspection.mvp.ui.main.fragment.SearchLordFragment;
import com.p8.inspection.mvp.ui.main.fragment.WorkOrderFragment;

import org.jetbrains.annotations.TestOnly;

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

    /**
     * 主页
     *
     * @param mainFragment
     */
    void inject(MainFragment mainFragment);

    /**
     * 登录
     *
     * @param loginFragment
     */
    void inject(LoginFragment loginFragment);

    /**
     * 签到签出
     *
     * @param attendanceFragment
     */
    void inject(AttendanceFragment attendanceFragment);

    void inject(ResetPasswordFragment resetPasswordFragment);

    void inject(ParkingMonitorFragment parkingMonitorFragment);

    void inject(HistoryFragment historyFragment);

    void inject(DeviceDebugFragment debugFragment);

    void inject(DeviceBindingFragment bindingFragment);

    void inject(SearchLordFragment searchLordFragment);

    void inject(SubmitWorkOrderFragment submitWorkOrderFragment);

    void inject(LandlordManageFragment landlordManageFragment);

    void inject(LandlordInfoFragment landlordInfoFragment);

    void inject(NoticeManageFragment noticeManageFragment);

    void inject(OrderManageFragment orderManageFragment);

    void inject(WorkOrderFragment workOrderFragment);

    @TestOnly
    void inject(TestFragment testFragment);

}

