package com.p8.inspection.data.bean;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author : WX.Y
 * date : 2020/11/3 14:06
 * description : 用户菜单
 */
public class UserMenu {
    private int userType;
    private String menuLabel;
    private int iconRes;
    private int id;

    public UserMenu(int userType, String menuLabel, int iconRes, int id) {
        this.userType = userType;
        this.menuLabel = menuLabel;
        this.iconRes = iconRes;
        this.id = id;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserMenu{" +
                "userType=" + userType +
                ", menuLabel='" + menuLabel + '\'' +
                ", iconRes='" + iconRes + '\'' +
                ", id=" + id +
                '}';
    }

    @IntDef({UserType.LARGE, UserType.MEDIUM, UserType.SMALL, UserType.LAND, UserType.PLATFORM,
            UserType.PLACE, UserType.BUILD, UserType.ONESELF, UserType.OTHER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface UserType {
        /**
         * 大主
         */
        int LARGE = 0;
        /**
         * 中主
         */
        int MEDIUM = 1;
        /**
         * 小主
         */
        int SMALL = 2;
        /**
         * 地主
         */
        int LAND = 3;
        /**
         * 台主
         */
        int PLATFORM = 4;
        /**
         * 场主
         */
        int PLACE = 5;
        /**
         * 自主
         */
        int ONESELF = 6;
        /**
         * 施主
         */
        int BUILD = 7;
        /**
         * 地主
         */
        int OTHER = 8;

    }
}

