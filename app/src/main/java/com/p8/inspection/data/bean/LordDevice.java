package com.p8.inspection.data.bean;

/**
 * @author : WX.Y
 * date : 2020/11/2 10:47
 * description : 大主设备信息
 */
public class LordDevice {
    private String name;
    private String parkingNumber;
    private String bindingTime;

    public LordDevice() {
    }

    public LordDevice(String name, String parkingNumber, String bindingTime) {
        this.name = name;
        this.parkingNumber = parkingNumber;
        this.bindingTime = bindingTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParkingNumber() {
        return parkingNumber;
    }

    public void setParkingNumber(String parkingNumber) {
        this.parkingNumber = parkingNumber;
    }

    public String getBindingTime() {
        return bindingTime;
    }

    public void setBindingTime(String bindingTime) {
        this.bindingTime = bindingTime;
    }

    @Override
    public String toString() {
        return "LordDevice{" +
                "name='" + name + '\'' +
                ", parkingNumber='" + parkingNumber + '\'' +
                ", bindingTime='" + bindingTime + '\'' +
                '}';
    }
}

