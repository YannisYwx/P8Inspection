package com.p8.inspection.data.bean;

import androidx.annotation.IntDef;

import com.p8.inspection.data.Constants;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author : WX.Y
 * date : 2020/11/3 14:06
 * description : 用户菜单
 */
public class UserMenu {
    @Constants.UserType
    private int userType;
    private String menuLabel;
    private int iconRes;
    @MenuType
    private int menuTye;

    public UserMenu(@Constants.UserType int userType, String menuLabel, int iconRes, @MenuType int menuTye) {
        this.userType = userType;
        this.menuLabel = menuLabel;
        this.iconRes = iconRes;
        this.menuTye = menuTye;
    }

    @Constants.UserType
    public int getUserType() {
        return userType;
    }

    public void setUserType(@Constants.UserType int userType) {
        this.userType = userType;
    }

    public String getMenuLabel() {
        return menuLabel;
    }

    public void setMenuLabel(String menuLabel) {
        this.menuLabel = menuLabel;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    @MenuType
    public int getMenuTye() {
        return menuTye;
    }

    public void setMenuTye(@MenuType int menuTye) {
        this.menuTye = menuTye;
    }

    @Override
    public String toString() {
        return "UserMenu{" +
                "userType=" + userType +
                ", menuLabel='" + menuLabel + '\'' +
                ", iconRes='" + iconRes + '\'' +
                ", menuTye=" + menuTye +
                '}';
    }

    @IntDef({MenuType.USER_CENTER, MenuType.PARKING_MONITOR, MenuType.DEVICE_BINDING,
            MenuType.WORK_ORDER_PROCESSING, MenuType.CLOCK, MenuType.J_MANAGE,
            MenuType.MOUNTINGS_MANAGE, MenuType.FINANCE_MANAGE, MenuType.LAND_MANAGE,
            MenuType.NOTICE_MANAGE, MenuType.ORDER_MANAGE, MenuType.MODIFY_PASSWORD,
            MenuType.APP_UPDATE, MenuType.SETTINGS, MenuType.MESSAGE_CENTER,
            MenuType.CLEAR_CACHE, MenuType.DEVICE_DEBUG})
    @Retention(RetentionPolicy.SOURCE)
    public @interface MenuType {
        /**
         * 用户中心
         */
        int USER_CENTER = 0;
        /**
         * 停车监控
         */
        int PARKING_MONITOR = 1;
        /**
         * 设备绑定
         */
        int DEVICE_BINDING = 2;
        /**
         * 设备调试
         */
        int DEVICE_DEBUG = 3;
        /**
         * 工单处理
         */
        int WORK_ORDER_PROCESSING = 4;
        /**
         * J架管理
         */
        int J_MANAGE = 5;
        /**
         * 配件管理
         */
        int MOUNTINGS_MANAGE = 6;
        /**
         * 财务管理
         */
        int FINANCE_MANAGE = 7;
        /**
         * 地主管理
         */
        int LAND_MANAGE = 8;
        /**
         * 公告管理
         */
        int NOTICE_MANAGE = 9;
        /**
         * 订单管理
         */
        int ORDER_MANAGE = 10;
        /**
         * 修改密码
         */
        int MODIFY_PASSWORD = 11;
        /**
         * App更新
         */
        int APP_UPDATE = 12;
        /**
         * 设置
         */
        int SETTINGS = 13;
        /**
         * 消息中心
         */
        int MESSAGE_CENTER = 14;
        /**
         * 清理缓存
         */
        int CLEAR_CACHE = 15;
        /**
         * 签到签出
         */
        int CLOCK = 16;
    }
}

