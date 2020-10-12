package com.p8.inspection.data.bean;

import java.util.List;

/**
 * author : WX.Y
 * date : 2020/9/25 14:29
 * description :
 */
public class Streets {
    List<Street> list;

    public List<Street> getList() {
        return list;
    }

    public void setList(List<Street> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Streets{" +
                "list=" + list +
                '}';
    }
}

