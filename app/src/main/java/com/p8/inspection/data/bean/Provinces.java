package com.p8.inspection.data.bean;

import java.util.List;

/**
 * author : WX.Y
 * date : 2020/9/23 18:01
 * description :
 */
public class Provinces {
    List<Province> list;

    public List<Province> getList() {
        return list;
    }

    public void setList(List<Province> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Provinces{" +
                "list=" + list +
                '}';
    }
}

