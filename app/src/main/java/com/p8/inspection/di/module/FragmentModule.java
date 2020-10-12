package com.p8.inspection.di.module;

import androidx.fragment.app.Fragment;

import com.p8.inspection.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * author : WX.Y
 * date : 2020/9/8 15:49
 * description :
 */
@Module
public class FragmentModule {
    Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @FragmentScope
    Fragment provideActivity() {
        return fragment;
    }
}

