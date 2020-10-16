package com.p8.inspection;

import android.app.Application;
import android.os.Environment;

import com.blankj.utilcode.util.Utils;
import com.p8.common.utils.AppUtils;
import com.p8.inspection.core.AliOssManager;
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

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        AppUtils.init(this);
        Utils.init(this);
        PlatformConfig.setWeixin("wxce15640ec18fdac0", "c9ed0da507cffddfae38b274f4f52243");
        UMConfigure.init(this, "5da586503fc195af81000885", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, null);
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        AliOssManager.getInstance().init(this);
        Fragmentation.builder() // 设置 栈视图 模式为 （默认）悬浮球模式   SHAKE: 摇一摇唤出  NONE：隐藏， 仅在Debug环境生效
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(true) // 实际场景建议.debug(BuildConfig.DEBUG)
//                .animation(R.anim.v_fragment_enter, R.anim.v_fragment_pop_exit, R.anim.v_fragment_pop_enter, R.anim.v_fragment_exit) //设置默认动画
                .install();
    }

    @Override
    public File getCacheDir() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File cacheDir = getExternalCacheDir();
            if (cacheDir != null){
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

