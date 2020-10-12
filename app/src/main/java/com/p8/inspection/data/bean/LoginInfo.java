package com.p8.inspection.data.bean;

/**
 * 创建时间：2018/11/15
 * 编写人： chengxin
 * 功能描述：登录信息
 */
public class LoginInfo {

    public int merchantId;
    public String phone;
    public String token;
    public String loginName;

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

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
}
