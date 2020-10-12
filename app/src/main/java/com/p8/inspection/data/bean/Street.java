package com.p8.inspection.data.bean;

/**
 * author : WX.Y
 * date : 2020/9/23 16:01
 * description :
 */
public class Street {
    private String id="";
    private String address="";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Street{" +
                "id='" + id + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}

