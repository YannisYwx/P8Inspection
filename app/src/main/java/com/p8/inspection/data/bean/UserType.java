package com.p8.inspection.data.bean;

/**
 * @author : WX.Y
 * date : 2020/10/20 17:23
 * description :
 */
public class UserType {
    public int id;
    public String type;
    public int icon;
    public int color;

    public UserType(int id, String type, int icon,int color) {
        this.id = id;
        this.type = type;
        this.icon = icon;
        this.color = color;
    }

}

