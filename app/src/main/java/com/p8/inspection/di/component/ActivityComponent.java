package com.p8.inspection.di.component;

import com.p8.inspection.MainActivity;
import com.p8.inspection.di.module.ActivityModule;
import com.p8.inspection.di.scope.ActivityScope;
import com.p8.inspection.mvp.ui.activity.LoginActivity;

import dagger.Subcomponent;

/**
 * author : WX.Y
 * date : 2020/9/8 15:47
 * description :
 */
@ActivityScope
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

//    void inject(MainActivity mainActivity);

    void inject(LoginActivity loginActivity);

//    @Subcomponent.Builder
//    interface Builder {
//        ActivityComponent build();
//    }
}

