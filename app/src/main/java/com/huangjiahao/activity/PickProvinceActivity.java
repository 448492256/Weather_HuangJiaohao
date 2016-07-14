package com.huangjiahao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.huangjiahao.R;
import com.huangjiahao.adapter.MyPickProvinceAdapter;
import com.huangjiahao.adapter.PickProvinceAdapter;
import com.huangjiahao.bean.City;
import com.huangjiahao.util.HttpCallbackListener;
import com.huangjiahao.util.HttpUtil;
import com.huangjiahao.util.JsonDecode;
import com.huangjiahao.util.ListChange;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2016/6/2.
 */
public class PickProvinceActivity extends Activity { //选择城市的活动

    public static final  int SHOW_RESPONSE = 0;//用于handler判断要进行的操作
    public static final int ERROR_RESPONSE = 1;

    private ListView mPickPronviceLv;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case SHOW_RESPONSE: //网络请求成功在控件展示内容
                    String response = (String)msg.obj;
                    ArrayList<City> arr = JsonDecode.CityDecode(response);
                    final List<City> list = ListChange.toGetProvince(arr);
                    mPickPronviceLv = (ListView) findViewById(R.id.pick_province_lv);
                    MyPickProvinceAdapter adapter = new MyPickProvinceAdapter(PickProvinceActivity.this, list);
                    mPickPronviceLv.setAdapter(adapter);
                    mPickPronviceLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //设置点击响应事件：开启选择城市的活动并把点击的省份名字传送过去
                            City city = list.get(position);
                            String provinceName = city.getProvinceName();
                            Intent intent = new Intent(PickProvinceActivity.this,PickCityActivity.class);
                            intent.putExtra("putData",provinceName);
                            startActivityForResult(intent,1);
                        }
                    });
                    break;
                case ERROR_RESPONSE:
                    Toast.makeText(PickProvinceActivity.this, "网络请求失败",Toast.LENGTH_SHORT).show();
                    break;
                default:
            }
        }
    };

    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_pick_province);
        final String adress = "http://v.juhe.cn/weather/citys?key=8ee574b503af6d884abefb9b34529edf"; //发送网络请求返回城市列表的地址

        new Thread(new Runnable() {
            @Override
            public void run() { //开启线程发送网络请求
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


    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //接收上个活动返回的数据并返回给上一个活动同时关闭本活动

        switch (requestCode) {
            case 1:
                if(resultCode == RESULT_OK) {
                    String returnData = data.getStringExtra("data_return");
                    Intent intent = new Intent();
                    intent.putExtra("data_return",returnData);
                    setResult(RESULT_OK,intent);
                    finish();
                }
                break;
            default:
        }
    }


}
