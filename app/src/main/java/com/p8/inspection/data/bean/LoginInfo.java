package com.p8.inspection.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.p8.inspection.data.Constants;

/**
 * @author : WX.Y
 * date : 2020/10/9 15:27
 * description : 登录信息
 */
public class LoginInfo implements Parcelable {

    private String id;
    private String name;
    private String phone;
    private String token;
    private String loginName;
    private String facadeImg;

    public String password;
    @Constants.UserType
    public int userType;

    protected LoginInfo(Parcel in) {
        id = in.readString();
        name = in.readString();
        phone = in.readString();
        token = in.readString();
        loginName = in.readString();
        facadeImg = in.readString();
        password = in.readString();
        userType = in.readInt();
    }

    public static final Creator<LoginInfo> CREATOR = new Creator<LoginInfo>() {
        @Override
        public LoginInfo createFromParcel(Parcel in) {
            return new LoginInfo(in);
        }

        @Override
        public LoginInfo[] newArray(int size) {
            return new LoginInfo[size];
        }
    };

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(@Constants.UserType int userType) {
        this.userType = userType;
    }

    public String getFacadeImg() {
        return facadeImg;
    }

    public void setFacadeImg(String facadeImg) {
        this.facadeImg = facadeImg;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", token='" + token + '\'' +
                ", loginName='" + loginName + '\'' +
                ", facadeImg='" + facadeImg + '\'' +
                ", id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", userType=" + userType +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(phone);
        dest.writeString(token);
        dest.writeString(loginName);
        dest.writeString(facadeImg);
        dest.writeString(password);
        dest.writeString(id);
        dest.writeString(name);
        dest.writeInt(userType);
    }
}
