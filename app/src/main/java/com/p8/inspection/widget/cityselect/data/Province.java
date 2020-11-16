package com.p8.inspection.widget.cityselect.data;

import java.util.List;

/**
 * @author : WX.Y
 * date : 2020/11/6 15:21
 * description : 省市区
 */
public class Province {
    public String name;

    public List<City> city;

    public static class City {
        public String name;
        public List<String> area;
    }
}

