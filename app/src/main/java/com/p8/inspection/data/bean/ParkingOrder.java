package com.p8.inspection.data.bean;

/**
 * author : WX.Y
 * date : 2020/9/10 17:58
 * description :
 */
public class ParkingOrder {
    private int currentPage;
    private int pageSize;
    private String id;
    private String openId;
    private String serialNumber;
    private String payNumber;
    private String merchantId;
    private String parkingNumber;
    private long startTime;
    private long endTime;
    private String duration;
    private double payMoney;
    private double profit;
    private String descr;
    private long createTime;
    private String ruleValue;
    private String address;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getPayNumber() {
        return payNumber;
    }

    public void setPayNumber(String payNumber) {
        this.payNumber = payNumber;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getParkingNumber() {
        return parkingNumber;
    }

    public void setParkingNumber(String parkingNumber) {
        this.parkingNumber = parkingNumber;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(double payMoney) {
        this.payMoney = payMoney;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getRuleValue() {
        return ruleValue;
    }

    public void setRuleValue(String ruleValue) {
        this.ruleValue = ruleValue;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "ParkingOrder{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", id='" + id + '\'' +
                ", openId='" + openId + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", payNumber='" + payNumber + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", parkingNumber='" + parkingNumber + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", duration='" + duration + '\'' +
                ", payMoney=" + payMoney +
                ", profit=" + profit +
                ", descr='" + descr + '\'' +
                ", createTime=" + createTime +
                ", ruleValue='" + ruleValue + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}

