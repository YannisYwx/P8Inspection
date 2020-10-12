package com.p8.inspection.di.module;

import android.app.Activity;

import com.p8.inspection.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * author : WX.Y
 * date : 2020/9/8 15:49
 * description :
 */
@Module
public class ActivityModule {
    Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    Activity provideActivity() {
        return activity;
    }
}

