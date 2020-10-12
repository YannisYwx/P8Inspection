package com.p8.inspection.di.component;

import com.p8.inspection.P8ParkingApplication;
import com.p8.inspection.di.module.ActivityModule;
import com.p8.inspection.di.module.AppModule;
import com.p8.inspection.di.module.FragmentModule;
import com.p8.inspection.di.module.HttpModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * author : WX.Y
 * date : 2020/9/7 11:52
 * description :
 */
@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public abstract class AppComponent {
    private static AppComponent instance;

    public abstract ActivityComponent addSub(ActivityModule activityModule);

    public abstract FragmentComponent addSub(FragmentModule fragmentModule);

    public static AppComponent getInstance() {
        if (instance == null) {
            instance = DaggerAppComponent.builder().appModule(new AppModule(P8ParkingApplication.sInstance)).httpModule(new HttpModule()).build();
        }
        return instance;
    }

//    public abstract ActivityComponent.Builder activityComponent();
//
//    public abstract FragmentComponent.Builder fragmentComponent();

//    /**
//     * 获取依赖组件单例
//     *
//     * @return
//     */
//    public static AppComponent getInstance() {
//        if (instance == null) {
//            instance = DaggerAppComponent.builder().appModule(new AppModule(GameApp.getInstance())).httpModule(new HttpModule()).build();
//        }
//        return instance;
//    }
//    /**
//     * 提供全局的上下文
//     *
//     * @return context
//     */
//    GameApp getContext();
//
//    /**
//     * 对其他依赖的Component提供数据中心
//     *
//     * @return DataManager
//     */
//    DataManager getDataManager();
//
//    /**
//     * 对其他依赖的Component提供网络操作
//     *
//     * @return RetrofitHelper
//     */
//    RetrofitHelper retrofitHelper();
//
//    /**
//     * 对其他依赖的Component提供数据库操作
//     *
//     * @return RealmHelper
//     */
//    RealmHelper realmHelper();
//
//    /**
//     * 对其他依赖的Component提供Preferences操作
//     *
//     * @return AppPreferencesHelper
//     */
//    AppPreferencesHelper gamePreferencesHelper();

}