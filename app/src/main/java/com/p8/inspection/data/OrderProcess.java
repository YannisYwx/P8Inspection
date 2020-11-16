package com.p8.inspection.data;

/**
 * @author : WX.Y
 * date : 2020/11/6 11:59
 * description : 工单处理进度
 */
public class OrderProcess {
    private int index;
    private String info;
    private String date;
    private int flag;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "OrderProcess{" +
                "index=" + index +
                ", info='" + info + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}

