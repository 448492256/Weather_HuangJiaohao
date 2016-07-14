package com.huangjiahao.util;

import android.util.Log;

import java.net.URLEncoder;

/**
 * 对传入变量修饰成可以发送请求的格式类
 */
public class ToGetHttpAdress {

    public  static  String toURLEncoded(String cityName) {
        if (cityName == null || cityName.equals("")) {
            return "";
        }
        try {
            String str = URLEncoder.encode(cityName, "UTF-8"); //进行Encoder操作
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String excute(String cityName){
        String city = toURLEncoded(cityName);
        String url= "http://v.juhe.cn/weather/index?cityname="+city+"&dtype=json&format=2&key=e3f3e3e2887d9512713dea4dfcfa5786"; //拼装成所需格式
        return url;
    }

}
