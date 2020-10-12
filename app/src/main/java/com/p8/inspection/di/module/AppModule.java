package com.p8.inspection.di.module;

import com.p8.inspection.P8ParkingApplication;
import com.p8.inspection.data.DataManager;
import com.p8.inspection.data.db.DBHelper;
import com.p8.inspection.data.db.RealmHelper;
import com.p8.inspection.data.net.HttpHelper;
import com.p8.inspection.data.net.RetrofitHelper;
import com.p8.inspection.data.prefs.AppPreferencesHelper;
import com.p8.inspection.data.prefs.PreferencesHelper;
import com.p8.inspection.di.scope.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * author : WX.Y
 * date : 2020/9/7 11:53
 * description :
 */
@Module
public class AppModule {
    private final P8ParkingApplication application;

    public AppModule(P8ParkingApplication application) {
        this.application = application;
    }

    /**
     * 提供全局上下文依赖
     *
     * @return Context
     */
    @Singleton
    @Provides
    @ApplicationContext
    P8ParkingApplication provideApplicationContext() {
        return application;
    }

    @Singleton
    @Provides
    HttpHelper providerHttpHelp(RetrofitHelper retrofitHelper) {
        return retrofitHelper;
    }

    /**
     * 提供全局的数据库操作依赖
     *
     * @param realmHelper DBHelper
     * @return DBHelper
     */
    @Singleton
    @Provides
    DBHelper provideDBHelper(RealmHelper realmHelper) {
        return realmHelper;
    }

    /**
     * 提供全局的PreferencesHelper依赖
     *
     * @param appPreferencesHelper PreferencesHelper
     * @return PreferencesHelper
     */
    @Singleton
    @Provides
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    /**
     * 提供一个全局的数据中心操作依赖
     *
     * @param preferencesHelper Preferences
     * @param dbHelper          数据库
     * @param httpHelper        网络
     * @return 数据中心
     */
    @Singleton
    @Provides
    DataManager provideDataManager(PreferencesHelper preferencesHelper, DBHelper dbHelper, HttpHelper httpHelper) {
        return new DataManager(preferencesHelper, dbHelper, httpHelper);
    }
}

