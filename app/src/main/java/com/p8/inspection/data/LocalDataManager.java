package com.p8.inspection.data;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.blankj.utilcode.util.JsonUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.p8.inspection.R;
import com.p8.inspection.data.bean.Province;
import com.p8.inspection.data.bean.ProvinceBean;
import com.p8.inspection.data.bean.UserMenu;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : WX.Y
 * date : 2020/10/30 16:17
 * description : 本地数据中心
 */
public class LocalDataManager {

    private List<ProvinceBean> provinces = new ArrayList<>();
    private ArrayList<ArrayList<String>> cities = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> areas = new ArrayList<>();

    private static final class Holder {
        private static LocalDataManager INSTANCE = new LocalDataManager();
    }

    public static LocalDataManager getInstance() {
        return Holder.INSTANCE;
    }

    private LocalDataManager() {
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
     * @param userType 用户类型
     * @return 菜单集合
     */
    public static List<UserMenu> getUserMenus(@UserMenu.UserType int userType) {
        List<UserMenu> userMenus = new ArrayList<>();
        switch (userType) {
            case UserMenu.UserType.LARGE: {
                //大主菜单
                userMenus.add(new UserMenu(UserMenu.UserType.LARGE, "个人中心", R.mipmap.icon_user, 0));
                userMenus.add(new UserMenu(UserMenu.UserType.LARGE, "J架管理", R.mipmap.icon_settings, 1));
                userMenus.add(new UserMenu(UserMenu.UserType.LARGE, "配件管理", R.mipmap.icon_device_settings, 2));
                userMenus.add(new UserMenu(UserMenu.UserType.LARGE, "订单管理", R.mipmap.icon_order_manage, 3));
                userMenus.add(new UserMenu(UserMenu.UserType.LARGE, "财务管理", R.mipmap.icon_money_chart, 4));
                userMenus.add(new UserMenu(UserMenu.UserType.LARGE, "地主管理", R.mipmap.icon_user_manager, 5));
                userMenus.add(new UserMenu(UserMenu.UserType.LARGE, "工单管理", R.mipmap.icon_order, 6));
                userMenus.add(new UserMenu(UserMenu.UserType.LARGE, "公告管理", R.mipmap.icon_noitce, 7));
            }
            break;
            case UserMenu.UserType.MEDIUM:
                break;
            case UserMenu.UserType.SMALL:
                break;
            case UserMenu.UserType.LAND: {
                userMenus.add(new UserMenu(UserMenu.UserType.LAND, "个人中心", R.mipmap.icon_user, 0));
                userMenus.add(new UserMenu(UserMenu.UserType.LAND, "停车监控", R.mipmap.icon_parking_moniter, 1));
                userMenus.add(new UserMenu(UserMenu.UserType.LAND, "设备安装", R.mipmap.icon_device_settings, 2));
                userMenus.add(new UserMenu(UserMenu.UserType.LAND, "工单处理", R.mipmap.icon_order, 3));
                userMenus.add(new UserMenu(UserMenu.UserType.LAND, "签到签出", R.mipmap.icon_clock, 4));
            }
            break;
            case UserMenu.UserType.BUILD:
                break;
            case UserMenu.UserType.ONESELF:
                break;
            case UserMenu.UserType.OTHER:
                break;
            case UserMenu.UserType.PLACE:
                break;
            case UserMenu.UserType.PLATFORM:
                break;
            default:
                break;
        }

        return userMenus;
    }
}

