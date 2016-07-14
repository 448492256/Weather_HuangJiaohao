package com.huangjiahao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.huangjiahao.R;
import com.huangjiahao.adapter.MyPickCityAdapter;
import com.huangjiahao.adapter.PickCityAdapter;
import com.huangjiahao.bean.City;
import com.huangjiahao.util.HttpCallbackListener;
import com.huangjiahao.util.HttpUtil;
import com.huangjiahao.util.JsonDecode;
import com.huangjiahao.util.ListChange;

import java.util.ArrayList;

/**
 * Created by ASUS on 2016/6/3.
 */
public class PickCityActivity extends Activity { //选择城市的类

    public static final  int SHOW_RESPONSE = 0; //用于判断handler要进行的操作
    public static final int ERROR_RESPONSE = 1;
    private String provinceName;
    private ListView mPickCityLv;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case SHOW_RESPONSE: //网络响应成功，控件展示城市列表
                    String response = (String) msg.obj;
                    ArrayList<City> arr = JsonDecode.CityDecode(response);
                    final ArrayList<City> list = ListChange.toGetCityChange(arr,provinceName);
                    MyPickCityAdapter adapter = new MyPickCityAdapter(PickCityActivity.this, list);
                    mPickCityLv = (ListView) findViewById(R.id.pick_city_lv);
                    mPickCityLv.setAdapter(adapter);
                    mPickCityLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {//点击响应事件，返回城市名称给上一个活动并关闭本活动
                            City city = list.get(position);
                            String cityName = city.getCityName();
                            Intent intent = new Intent();
                            intent.putExtra("data_return",cityName);
                            setResult(RESULT_OK,intent);
                            finish();
                        }
                    });
                    break;
                case ERROR_RESPONSE:
                    Toast.makeText(PickCityActivity.this,"网络请求错误",Toast.LENGTH_SHORT).show();
                    break;
                default:
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_city);
        Intent intent = getIntent();
        provinceName = intent.getStringExtra("putData");
        final String adress = "http://v.juhe.cn/weather/citys?key=8ee574b503af6d884abefb9b34529edf"; //请求得到城市列表的地址
        new Thread(new Runnable() {
            @Override
            public void run() { //开启线程发起网络请求
                HttpUtil.sendHttpRequest(adress, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        Message message = new Message();
                        message.what = SHOW_RESPONSE;
                        message.obj = response.toString();
                        handler.sendMessage(message);//发送消息到消息队列
                    }

                    @Override
                    public void onError(Exception e) {
                        Message message = new Message();
                        message.what = ERROR_RESPONSE;
                        handler.sendMessage(message);
                    }
                });
            }
        }).start();
    }




}
