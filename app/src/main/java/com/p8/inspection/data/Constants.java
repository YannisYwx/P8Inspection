package com.p8.inspection.data;

import android.os.Environment;

import com.p8.inspection.P8ParkingApplication;

import java.io.File;

/**
 * @author : WX.Y
 * date : 2020/9/8 17:06
 * description :
 */
public interface Constants {

    String SP_NAME = "P8_APP";

    String LOCATION_ADDRESS = "_location_address";

    /**
     * apk文件下载保存目录
     */
    String DOWNLOAD_FOLDER = "/P8_download/";

    String DOWNLOAD_PATH = P8ParkingApplication.sInstance.getExternalFilesDir(null) + "/p8_inspection/download/pdf/";


    int PAGE_SIZE = 10;

    /**
     * 移动端平台
     */
    int PLATFORM_CODE = 1;

    /**
     * 需要加载的视图
     */
//    interface LoadPagerRes {
//        int errorPager = R.layout.pager_error;
//        int emptyPager = R.layout.pager_empty;
//        int loadingPager = R.layout.pager_loading;
//        int btnRetry = R.id.btn_retry;
//    }

    String EXTRA = "_extra";

    String TRANSITION_ANIMATION_NEWS_PHOTOS = "transition_animation_news_photos";
    /**
     * 文章id
     */
    String ARTICLE_ID = "ARTICLE_ID";
    /**
     * 文章图片res
     */
    String ARTICLE_IMG_RES = "ARTICLE_IMG_RES";

    /**
     * android/data/com.pa.inspection/cache/data
     */
    String PATH_DATA = P8ParkingApplication.sInstance.getCacheDir().getAbsolutePath() + File.separator + "data";

    String PATH_CACHE = PATH_DATA + "/NetCache";

    String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "codeest" + File.separator + "GeekNews";


    interface SpConstants {
        String IS_FIRST_IN = "_isFirstIn";
    }

    interface DeviceCMD {
        /**
         * 上升和下降
         */
        String CMD_UP = "ma11e";
        String CMD_DOWN = "ma10e";

        /**
         * 泊车状态
         */
        String CMD_STALL = "mb11e";
        String CMD_NO_STALL = "mb10e";

        /**
         * 断电供电状态
         */
        String CMD_OUTAGE = "md11e";
        String CMD_POWER = "md10e";

        /**
         * 液位状态
         */
        String CMD_LIQUID_ALARM = "mf11e";
        String CMD_LIQUID_NORMAL = "mf10e";

        /**
         * 电池电压状态
         */
        String CMD_VOLTAGE_ALARM = "mg11e";
        String CMD_VOLTAGE_NORMAL = "mg10e";

        /**
         * 指示灯开关状态
         */
        String CMD_CLOSE = "mh10e";
        String CMD_OPEN = "mh11e";

        /**
         * 查询设备健康
         */
        String CMD_DEVICE_INFO_1 = "mc10e";
        String CMD_DEVICE_INFO_2 = "mc11e";
    }

    interface P8Code {
        int SUCCESS = 1;
        int FAILED = 0;
        int TOKEN_ERROR = 408;
    }
}
