package com.p8.inspection.data.bean;

/**
 * author : WX.Y
 * date : 2020/9/9 13:57
 * description :
 */
public class LoginBean {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "username='" + username + '\'' +
                ", icon_password='" + password + '\'' +
                '}';
    }
}

