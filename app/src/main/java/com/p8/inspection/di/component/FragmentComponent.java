package com.p8.inspection.di.component;

import com.p8.inspection.di.module.FragmentModule;
import com.p8.inspection.mvp.ui.fragment.LoginFragment;
import com.p8.inspection.mvp.ui.fragment.MonitorFragment;
import com.p8.inspection.mvp.ui.fragment.ResetPwdFragment;
import com.p8.inspection.mvp.ui.fragment.TestFragment;

import dagger.Subcomponent;

/**
 * author : WX.Y
 * date : 2020/9/8 15:47
 * description :
 */
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {

    void inject(LoginFragment loginFragment);

    void inject(ResetPwdFragment resetPwdFragment);

    void inject(MonitorFragment monitorFragment);

    void inject(TestFragment testFragment);

//    @Subcomponent.Builder
//    interface Builder {
//        FragmentComponent build();
//    }
}

