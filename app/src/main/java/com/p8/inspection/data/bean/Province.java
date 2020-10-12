package com.p8.inspection.data.bean;

/**
 * author : WX.Y
 * date : 2020/9/23 16:01
 * description : 省会
 */
public class Province {
    private String id;
    private String provinceId;
    private String province;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Override
    public String toString() {
        return "Province{" +
                "id='" + id + '\'' +
                ", provinceId='" + provinceId + '\'' +
                ", province='" + province + '\'' +
                '}';
    }
}

