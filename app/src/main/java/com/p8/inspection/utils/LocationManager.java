package com.p8.inspection.utils;

import android.content.Context;
import android.location.LocationListener;
import android.text.TextUtils;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationQualityReport;
import com.orhanobut.logger.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author : WX.Y
 * date : 2020/9/10 11:09
 * description :
 */
public class LocationManager {
    private AMapLocationClient mLocationClient = null;
    private AMapLocationListener mLocationListener = null;

    public static LocationManager getInstance() {
        return LocationManagerHolder.sLocationManager;
    }

    private static final class LocationManagerHolder {
        private static LocationManager sLocationManager = new LocationManager();
    }

    public void initLocation(Context context) {
        if (mLocationClient != null) {
            return;
        }

        //初始化client
        mLocationClient = new AMapLocationClient(context.getApplicationContext());
        AMapLocationClientOption locationClientOption = getDefaultOption();
        //设置定位参数
        mLocationClient.setLocationOption(locationClientOption);
        // 设置定位监听
        mLocationClient.setLocationListener(locationListener);
    }

    /**
     * 默认的定位参数
     *
     * @return opt
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        //可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setGpsFirst(true);
        //可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setHttpTimeOut(30000);
        //可选，设置定位间隔。默认为2秒
        mOption.setInterval(2000);
        //可选，设置是否返回逆地理地址信息。默认是true
        mOption.setNeedAddress(true);
        //可选，设置是否单次定位。默认是false
        mOption.setOnceLocation(false);
        //可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        mOption.setOnceLocationLatest(false);
        //可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);
        //可选，设置是否使用传感器。默认是false
        mOption.setSensorEnable(false);
        //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setWifiScan(true);
        //可选，设置是否使用缓存定位，默认为true
        mOption.setLocationCacheEnable(true);
        //可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
        mOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);
        return mOption;
    }

    public void startLocation() {
        // 启动定位
        stopLocation();
        mLocationClient.startLocation();
    }

    public void stopLocation() {
        mLocationClient.stopLocation();
    }

    public interface OnLocationListener {
        void onLocationSuccess(AMapLocation location, String locationInfo);

        void onLocationError(int errorCode, String errorInfo);
    }

    private List<OnLocationListener> mListeners = new ArrayList<>();

    public void registerLocationListener(OnLocationListener locationListener) {
        if (!mListeners.contains(locationListener)) {
            mListeners.add(locationListener);
        }
    }

    public void unregisterLocationListener(OnLocationListener locationListener) {
        mListeners.remove(locationListener);
    }

    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            if (null != location) {

                StringBuffer sb = new StringBuffer();
                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                if (location.getErrorCode() == 0) {
                    sb.append("定位成功" + "\n");
                    sb.append("定位类型: ").append(location.getLocationType()).append("\n");
                    sb.append("经    度    : ").append(location.getLongitude()).append("\n");
                    sb.append("纬    度    : ").append(location.getLatitude()).append("\n");
                    sb.append("精    度    : ").append(location.getAccuracy()).append("米").append("\n");
                    sb.append("提供者    : ").append(location.getProvider()).append("\n");

                    sb.append("速    度    : ").append(location.getSpeed()).append("米/秒").append("\n");
                    sb.append("角    度    : ").append(location.getBearing()).append("\n");
                    // 获取当前提供定位服务的卫星个数
                    sb.append("星    数    : ").append(location.getSatellites()).append("\n");
                    sb.append("国    家    : ").append(location.getCountry()).append("\n");
                    sb.append("省            : ").append(location.getProvince()).append("\n");
                    sb.append("市            : ").append(location.getCity()).append("\n");
                    sb.append("城市编码 : ").append(location.getCityCode()).append("\n");
                    sb.append("区            : ").append(location.getDistrict()).append("\n");
                    sb.append("区域 码   : ").append(location.getAdCode()).append("\n");
                    sb.append("街    道    : ").append(location.getStreet()).append("\n");
                    sb.append("地    址    : ").append(location.getAddress()).append("\n");
                    sb.append("兴趣点    : ").append(location.getPoiName()).append("\n");
                    location.getStreet();
                    //定位完成的时间
                    sb.append("定位时间: ").append(formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss")).append("\n");
                    for (OnLocationListener locationListener : mListeners) {
                        locationListener.onLocationSuccess(location, sb.toString());
                    }

                } else {
                    //定位失败
                    sb.append("定位失败" + "\n");
                    sb.append("错误码:").append(location.getErrorCode()).append("\n");
                    sb.append("错误信息:").append(location.getErrorInfo()).append("\n");
                    sb.append("错误描述:").append(location.getLocationDetail()).append("\n");
                for (OnLocationListener locationListener : mListeners) {
                    locationListener.onLocationError(location.getErrorCode(), location.getErrorInfo());
                }
                }
                sb.append("***定位质量报告***").append("\n");
                sb.append("* WIFI开关：").append(location.getLocationQualityReport().isWifiAble() ? "开启" : "关闭").append("\n");
                sb.append("* GPS状态：").append(getGPSStatusString(location.getLocationQualityReport().getGPSStatus())).append("\n");
                sb.append("* GPS星数：").append(location.getLocationQualityReport().getGPSSatellites()).append("\n");
                sb.append("* 网络类型：").append(location.getLocationQualityReport().getNetworkType()).append("\n");
                sb.append("* 网络耗时：").append(location.getLocationQualityReport().getNetUseTime()).append("\n");
                sb.append("****************").append("\n");
                //定位之后的回调时间
                sb.append("回调时间: ").append(formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss")).append("\n");

                //解析定位结果，
                String result = sb.toString();
                Logger.d(result);
            } else {
                Logger.d("定位失败，loc is null");
            }
        }
    };
    private static SimpleDateFormat sdf = null;

    public static String formatUTC(long l, String strPattern) {
        if (TextUtils.isEmpty(strPattern)) {
            strPattern = "yyyy-MM-dd HH:mm:ss";
        }
        if (sdf == null) {
            try {
                sdf = new SimpleDateFormat(strPattern, Locale.CHINA);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        } else {
            sdf.applyPattern(strPattern);
        }
        return sdf == null ? "NULL" : sdf.format(l);
    }

    /**
     * 获取GPS状态的字符串
     *
     * @param statusCode GPS状态码
     * @return
     */
    private String getGPSStatusString(int statusCode) {
        String str = "";
        switch (statusCode) {
            case AMapLocationQualityReport.GPS_STATUS_OK:
                str = "GPS状态正常";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPROVIDER:
                str = "手机中没有GPS Provider，无法进行GPS定位";
                break;
            case AMapLocationQualityReport.GPS_STATUS_OFF:
                str = "GPS关闭，建议开启GPS，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_MODE_SAVING:
                str = "选择的定位模式中不包含GPS定位，建议选择包含GPS定位的模式，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPERMISSION:
                str = "没有GPS定位权限，建议开启gps定位权限";
                break;
            default:
                break;
        }
        return str;
    }
}

