package com.p8.inspection.data;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;

import com.blankj.utilcode.util.CacheDoubleUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.p8.inspection.R;
import com.p8.inspection.data.bean.LoginInfo;
import com.p8.inspection.data.bean.ProvinceBean;
import com.p8.inspection.data.bean.UserMenu;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.p8.inspection.data.Constants.FLAG_NO_ICON;

/**
 * @author : WX.Y
 * date : 2020/10/30 16:17
 * description : 本地数据中心
 */
public class LocalDataManager {

    private List<ProvinceBean> provinces = new ArrayList<>();
    private ArrayList<ArrayList<String>> cities = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> areas = new ArrayList<>();

    /**
     * 当前登录的信息
     */
    private LoginInfo mLoginInfo;

    private static final class Holder {
        private static LocalDataManager INSTANCE = new LocalDataManager();
    }

    public static LocalDataManager getInstance() {
        return Holder.INSTANCE;
    }

    private LocalDataManager() {
    }

    /**
     * 清空本地数据
     */
    public static void clear() {
        SPUtils.getInstance().clear();
        CacheDoubleUtils.getInstance().clear();
    }

    public void init(Application application) {
        initProvinces(application);
    }

    public List<ProvinceBean> getProvinces() {
        return provinces;
    }

    public ArrayList<ArrayList<String>> getCities() {
        return cities;
    }

    public ArrayList<ArrayList<ArrayList<String>>> getAreas() {
        return areas;
    }

    public int[] getSelectAddressIndex(String province, String city, String area) {
        int[] index = new int[3];
        for (int i = 0; i < provinces.size(); i++) {
            if (provinces.get(i).getName().equals(province)) {
                index[0] = i;
                List<ProvinceBean.CityBean> cities = provinces.get(i).getCityList();
                for (int j = 0; j < cities.size(); j++) {
                    if (cities.get(j).getName().equals(city)) {
                        index[1] = j;
                        List<String> areas = cities.get(j).getArea();
                        for (int k = 0; k < areas.size(); k++) {
                            if (area.equals(areas.get(k))) {
                                index[2] = k;
                                return index;
                            }
                        }
                    }
                }
            }
        }

        return index;
    }

    /**
     * 初始化省市区数据
     *
     * @param context
     */
    public void initProvinces(Context context) {
        //获取assets目录下的json文件数据
        ThreadUtils.getSinglePool().execute(() -> {
            String jsonData = getJson(context);
            //用Gson 转成实体
            provinces = parseData(jsonData);
            for (int i = 0; i < provinces.size(); i++) {//遍历省份
                ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
                ArrayList<ArrayList<String>> provinceAreaList = new ArrayList<>();//该省的所有地区列表（第三极）

                for (int c = 0; c < provinces.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                    String cityName = provinces.get(i).getCityList().get(c).getName();
                    cityList.add(cityName);//添加城市

                    //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/
                    //该城市的所有地区列表
                    ArrayList<String> cityAreaList = new ArrayList<>(provinces.get(i).getCityList().get(c).getArea());
                    provinceAreaList.add(cityAreaList);//添加该省所有地区数据
                }

                cities.add(cityList);

                areas.add(provinceAreaList);
            }
        });

    }

    private String getJson(Context context) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open("province.json")));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public ArrayList<ProvinceBean> parseData(String result) {
        //Gson 解析
        ArrayList<ProvinceBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                ProvinceBean entity = gson.fromJson(data.optJSONObject(i).toString(), ProvinceBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    /**
     * 根据登入类型获取菜单选项
     *
     * @return 菜单集合
     */
    public List<UserMenu> getUserMenus() {
        int userType = getUserType();
        List<UserMenu> userMenus = new ArrayList<>();
        Logger.e("getUserMenus ====== " + userType);
        switch (userType) {
            case Constants.UserType.LARGE: {
                //大主菜单
                userMenus.add(new UserMenu(Constants.UserType.LARGE, "个人中心", R.mipmap.icon_user, UserMenu.MenuType.USER_CENTER));
                userMenus.add(new UserMenu(Constants.UserType.LARGE, "J架管理", R.mipmap.icon_settings, UserMenu.MenuType.J_MANAGE));
                userMenus.add(new UserMenu(Constants.UserType.LARGE, "配件管理", R.mipmap.icon_device_settings, UserMenu.MenuType.MOUNTINGS_MANAGE));
                userMenus.add(new UserMenu(Constants.UserType.LARGE, "订单管理", R.mipmap.icon_order_manage, UserMenu.MenuType.ORDER_MANAGE));
                userMenus.add(new UserMenu(Constants.UserType.LARGE, "财务管理", R.mipmap.icon_money_chart, UserMenu.MenuType.FINANCE_MANAGE));
                userMenus.add(new UserMenu(Constants.UserType.LARGE, "地主管理", R.mipmap.icon_user_manager, UserMenu.MenuType.LAND_MANAGE));
                userMenus.add(new UserMenu(Constants.UserType.LARGE, "工单处理", R.mipmap.icon_order, UserMenu.MenuType.WORK_ORDER_PROCESSING));
                userMenus.add(new UserMenu(Constants.UserType.LARGE, "公告管理", R.mipmap.icon_notice, UserMenu.MenuType.NOTICE_MANAGE));
            }
            break;
            case Constants.UserType.MEDIUM:
                break;
            case Constants.UserType.SMALL:
                break;
            case Constants.UserType.LAND: {
                userMenus.add(new UserMenu(Constants.UserType.LAND, "个人中心", R.mipmap.icon_user, UserMenu.MenuType.USER_CENTER));
                userMenus.add(new UserMenu(Constants.UserType.LAND, "停车监控", R.mipmap.icon_parking_moniter, UserMenu.MenuType.PARKING_MONITOR));
                userMenus.add(new UserMenu(Constants.UserType.LAND, "设备绑定", R.mipmap.icon_system_bug, UserMenu.MenuType.DEVICE_BINDING));
                userMenus.add(new UserMenu(Constants.UserType.LAND, "设备调试", R.mipmap.icon_device_settings, UserMenu.MenuType.DEVICE_DEBUG));
                userMenus.add(new UserMenu(Constants.UserType.LAND, "工单处理", R.mipmap.icon_order, UserMenu.MenuType.WORK_ORDER_PROCESSING));
                userMenus.add(new UserMenu(Constants.UserType.LAND, "签到签出", R.mipmap.icon_clock, UserMenu.MenuType.CLOCK));
            }
            break;
            case Constants.UserType.BUILD:
                break;
            case Constants.UserType.ONESELF:
                break;
            case Constants.UserType.OTHER:
                break;
            case Constants.UserType.PLACE:
                break;
            case Constants.UserType.PLATFORM:
                break;
            default:
                break;
        }

        return userMenus;
    }

    public List<UserMenu> getUserCenterMenu() {
        List<UserMenu> userMenus = new ArrayList<>();
        userMenus.add(new UserMenu(Constants.UserType.LAND, "修改密码", FLAG_NO_ICON, UserMenu.MenuType.MODIFY_PASSWORD));
        userMenus.add(new UserMenu(Constants.UserType.LAND, "App更新", FLAG_NO_ICON, UserMenu.MenuType.APP_UPDATE));
        int userType = getUserType();
        switch (userType) {
            case Constants.UserType.BUILD:
                break;
            case Constants.UserType.LAND:
                userMenus.add(new UserMenu(Constants.UserType.LAND, "清理缓存", FLAG_NO_ICON, UserMenu.MenuType.MODIFY_PASSWORD));
                break;
            case Constants.UserType.LARGE:
                userMenus.add(new UserMenu(Constants.UserType.LARGE, "清理缓存", FLAG_NO_ICON, UserMenu.MenuType.MODIFY_PASSWORD));
                break;
            case Constants.UserType.MEDIUM:
                break;
            case Constants.UserType.ONESELF:
                break;
            case Constants.UserType.OTHER:
                break;
            case Constants.UserType.PLACE:
                break;
            case Constants.UserType.PLATFORM:
                break;
            case Constants.UserType.SMALL:
                break;
            default:
                break;
        }
        return userMenus;
    }

    @Constants.UserType
    public int getUserType() {
        if (mLoginInfo == null) {
            mLoginInfo = CacheDoubleUtils.getInstance().getParcelable(Constants.Key.LOGIN_INFO, LoginInfo.CREATOR);
        }
        return mLoginInfo == null ? Constants.UserType.LAND : mLoginInfo.userType;
    }

    public LoginInfo getLoginInfo() {
        if (mLoginInfo == null) {
            mLoginInfo = CacheDoubleUtils.getInstance().getParcelable(Constants.Key.LOGIN_INFO, LoginInfo.CREATOR);
        }
        return mLoginInfo;
    }

    /**
     * 获取主页标题
     *
     * @return 主页标题
     */
    public String getMainTitle() {
        String title = "地主";
        switch (getUserType()) {
            case Constants.UserType.LAND:
                title = "大主";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + getUserType());
            case Constants.UserType.BUILD:
                break;
            case Constants.UserType.LARGE:
                break;
            case Constants.UserType.MEDIUM:
                break;
            case Constants.UserType.ONESELF:
                break;
            case Constants.UserType.OTHER:
                break;
            case Constants.UserType.PLACE:
                break;
            case Constants.UserType.PLATFORM:
                break;
            case Constants.UserType.SMALL:
                break;
        }
        return title;
    }

    public String getResetPasswordUrl() {
        String url = "/app_agency/change_pwd.html";
        switch (getUserType()) {
            case Constants.UserType.LAND:
                url = "/app_inspect/change_pwd.html";
                break;
            case Constants.UserType.BUILD:
                break;
            case Constants.UserType.LARGE:
                url = "/app_agency/change_pwd.html";
                break;
            case Constants.UserType.MEDIUM:
                break;
            case Constants.UserType.ONESELF:
                break;
            case Constants.UserType.OTHER:
                break;
            case Constants.UserType.PLACE:
                break;
            case Constants.UserType.PLATFORM:
                break;
            case Constants.UserType.SMALL:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + getUserType());
        }
        return url;
    }
}

