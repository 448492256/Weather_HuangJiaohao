package com.huangjiahao.util;

import com.huangjiahao.bean.City;

import java.util.ArrayList;

/**
 * Created by ASUS on 2016/6/2.
 */
public class ListChange {

    public static ArrayList<City> toGetProvince(ArrayList<City> arr) { //改变数据成为展示省份的方法
        ArrayList returnList = new ArrayList();
        for (int i = 0; i < arr.size(); i++) {
            if (i == 0) {
                returnList.add(arr.get(i));
            } else {
                if (!arr.get(i).getProvinceName().equals(arr.get(i - 1).getProvinceName())) {
                    returnList.add(arr.get(i));
                }
            }
        }
        return returnList;
    }

    public static ArrayList<City> toGetCityChange(ArrayList<City> arr,String provinceName) { //改变数据成为展示城市的方法

        ArrayList<City> list = new ArrayList();
        ArrayList<City> returnList = new ArrayList();
        for(int i=0; i<arr.size(); i++) {
            if(arr.get(i).getProvinceName().matches(provinceName)) {
                list.add(arr.get(i));
            }
        }

        for(int i=0; i<list.size(); i++) {
            if(i == 0) {
                returnList.add(list.get(i));
            }else {
                if(!list.get(i).getCityName().equals(list.get(i-1).getCityName())) {
                    returnList.add(list.get(i));
                }
            }
        }
        return returnList;
    }


}
