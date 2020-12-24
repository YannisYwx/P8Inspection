package com.p8.inspection;

import android.app.Application;
import android.os.Environment;

import com.blankj.utilcode.util.Utils;
import com.p8.common.utils.ActUtils;
import com.p8.inspection.data.AliOssManager;
import com.p8.inspection.data.LocalDataManager;
import com.p8.inspection.utils.LocationManager;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import java.io.File;

import me.yokeyword.fragmentation.Fragmentation;

/**
 * @author : WX.Y
 * date : 2020/9/4 16:42
 * description :
 */
public class P8ParkingApplication extends Application {

    public static P8ParkingApplication sInstance;

    public static RefWatcher sRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        //内存泄漏检测工具
        if (!LeakCanary.isInAnalyzerProcess(this)) {
            sRefWatcher = LeakCanary.install(this);
        }
        //activity集合
        ActUtils.init(this);
        //AndroidUtils
        Utils.init(this);
        //定位初始化
        LocationManager.getInstance().initLocation(this);
        LocalDataManager.getInstance().init(this);
        //友盟初始化
        PlatformConfig.setWeixin("wxce15640ec18fdac0", "c9ed0da507cffddfae38b274f4f52243");
        UMConfigure.init(this, "5da586503fc195af81000885", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, null);
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        //阿里oss云初始化
        AliOssManager.getInstance().init(this);
        //Fragmentation初始化
        Fragmentation.builder() // 设置 栈视图 模式为 （默认）悬浮球模式   SHAKE: 摇一摇唤出  NONE：隐藏， 仅在Debug环境生效
                .stackViewMode(Fragmentation.NONE)
                // 实际场景建议.debug(BuildConfig.DEBUG)
                .debug(true)
                .install();
    }

    @Override
    public File getCacheDir() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File cacheDir = getExternalCacheDir();
            if (cacheDir != null) {
                if (cacheDir.exists() || cacheDir.mkdirs()) {
                    return cacheDir;
                }
            }
        }
        return super.getCacheDir();
    }

//    @Override
//    public Resources getResources() {
//        return AdaptScreenUtils.adaptWidth(super.getResources(), 1080);
//    }
}

