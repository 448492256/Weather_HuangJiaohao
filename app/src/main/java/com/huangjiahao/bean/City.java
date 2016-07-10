package com.huangjiahao.bean;

/**
 * Created by ASUS on 2016/5/31.
 */
public class City {
    private String cityName;
    private String provinceName;

    public City(String cityName,String provinceName) {
        this.cityName = cityName;
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public String getProvinceName() {
        return provinceName;
    }

}
