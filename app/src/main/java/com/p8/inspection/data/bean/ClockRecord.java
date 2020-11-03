package com.p8.inspection.data.bean;

/**
 * @author : WX.Y
 * date : 2020/10/28 14:47
 * description : 打卡记录
 */
public class ClockRecord {

    private String clockIn;
    private String clockOut;
    private String clockDate;

    public ClockRecord() {
    }

    public ClockRecord(String clockIn, String clockOut, String clockDate) {
        this.clockIn = clockIn;
        this.clockOut = clockOut;
        this.clockDate = clockDate;
    }

    public String getClockIn() {
        return clockIn;
    }

    public void setClockIn(String clockIn) {
        this.clockIn = clockIn;
    }

    public String getClockOut() {
        return clockOut;
    }

    public void setClockOut(String clockOut) {
        this.clockOut = clockOut;
    }

    public String getClockDate() {
        return clockDate;
    }

    public void setClockDate(String clockDate) {
        this.clockDate = clockDate;
    }
}

