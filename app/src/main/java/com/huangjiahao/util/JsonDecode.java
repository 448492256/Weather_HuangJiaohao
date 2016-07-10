package com.huangjiahao.util;

import android.util.Log;

import com.huangjiahao.bean.City;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于解码JSON数据的类
 */
public class JsonDecode {

    public static ArrayList<String> jsonDecode(String response) { //解码的方法

        StringBuilder tamp = new StringBuilder(); //临时放置数据的变量
        ArrayList<String> returnList = new ArrayList<String>(); //返回数据的数组

        try {
            JSONObject object = new JSONObject(response);
            JSONObject result = object.getJSONObject("result");

            JSONObject sk = result.getJSONObject("sk");
            String temp = "温度：" + sk.getString("temp")+"\n";
            tamp.append(temp);
            String wind_direction = "风向：" + sk.getString("wind_direction")+"\n";
            tamp.append(wind_direction);
            String wind_strength = "风力：" +sk.getString("wind_strength")+"\n";
            tamp.append(wind_strength);
            String humidity = "当前湿度：" + sk.getString("humidity") +"\n";
            tamp.append(humidity);
            String time = "发布时间：" + sk.getString("time") +"\n";
            tamp.append(time).append("\n").append("\n");
            returnList.add(tamp.toString());
            tamp.setLength(0);//清空tamp变量的值以便于下面的代码继续使用

            JSONObject today = result.getJSONObject("today");
            String city = "城市：" + today.getString("city") + "\n";
            tamp.append(city);
            String date_y = "日期：" + today.getString("date_y") + today.getString("week")+"\n";
            tamp.append(date_y);
            String temperature = "今天温度：" + today.getString("temperature") + "\n";
            tamp.append(temperature);
            String weather = "今日天气：" + today.getString("weather") +"\n";
            tamp.append(weather).append("\n").append("\n");
            returnList.add(tamp.toString());
            tamp.setLength(0);

            String future = result.getString("future");
            JSONArray jsonArray = new JSONArray(future);
            for(int i=0;i< jsonArray.length(); i++) {
                JSONObject jb = jsonArray.getJSONObject(i);
                String future_temperature = "温度：" + jb.getString("temperature") + "\n";
                String future_weather = "天气：" + jb.getString("weather")+ "\n";
                String future_wind = "风向风强：" + jb.getString("wind") + "\n";
                String future_date = "日期：" + jb.getString("week") + "\n";
                String allinfo = future_temperature + future_weather + future_wind +future_date ;
                tamp.append(allinfo).append("\n").append("\n");
            }
            returnList.add(tamp.toString());
            tamp.setLength(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnList;
    }

    public static ArrayList<City> CityDecode(String response) { //将返回为城市列表的数据解码的方法
        ArrayList returnList = new ArrayList();

        try{
            JSONObject obj = new JSONObject(response);
            String result = obj.getString("result");
            JSONArray arr = new JSONArray(result);
            for(int i=0; i<arr.length(); i++) {
                String provinceName = arr.getJSONObject(i).getString("province");
                String cityName = arr.getJSONObject(i).getString("city");
                returnList.add(new City(cityName,provinceName));
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return returnList;
    }

}
