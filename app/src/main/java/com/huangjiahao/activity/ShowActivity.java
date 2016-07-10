package com.huangjiahao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.huangjiahao.R;
import com.huangjiahao.util.HttpCallbackListener;
import com.huangjiahao.util.HttpUtil;
import com.huangjiahao.util.JsonDecode;
import com.huangjiahao.util.ToGetHttpAdress;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/*
主活动
 */

public class ShowActivity extends Activity implements View.OnClickListener{

    public static final  int SHOW_RESPONSE = 0; //用于handler判断要进行的操作
    public static final int ERROR_RESPONSE = 1;
    private String mSetCityNameText; //用于记录当前城市名称并用于设置mShowTVCityName的内容

    private TextView mShowTvNowInfo;
    private TextView mShowTvTodayInfo;
    private TextView mShowTvFutureInfo;
    private TextView mShowTVCityName;

    private Handler handler = new Handler() { //处理looper中的消息
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_RESPONSE: //表示网络请求通过，要求在控件展示内容
                    String response = (String) msg.obj;
                    ArrayList<String> returnList = JsonDecode.jsonDecode(response); //JSON解码
                    mShowTvNowInfo.setText(returnList.get(0)); //设置控件内容
                    mShowTvTodayInfo.setText(returnList.get(1));
                    mShowTvFutureInfo.setText(returnList.get(2));
                    mShowTVCityName.setText(mSetCityNameText); //设置标题内容
                    break;
                case ERROR_RESPONSE: //表示网络请求失败
                    Toast.makeText(ShowActivity.this,"网络请求失败",Toast.LENGTH_SHORT).show();
                break;
                default:
            }
        }
    };

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_bt_switch: //更换城市的按钮事件
                Intent intent = new Intent(ShowActivity.this, ChangeCityActivity.class); //启动另外一个活动
                startActivityForResult(intent, 1); //要求返回数据的启动方式
                break;
            case R.id.show_bt_refresh://更新天气的按钮事件
                Toast.makeText(ShowActivity.this, "刷新中.......", Toast.LENGTH_SHORT).show();
                final String adress = ToGetHttpAdress.excute(mSetCityNameText); //获取当前城市名进行刷新操作
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        HttpUtil.sendHttpRequest(adress, new HttpCallbackListener() {
                            @Override
                            public void onFinish(String response) {
                                Message message = new Message();
                                message.what = SHOW_RESPONSE;
                                message.obj = response.toString();
                                handler.sendMessage(message);//发送消息到消息队列
                            }

                            public void onError(Exception e) {
                                Message message = new Message();
                                message.what = ERROR_RESPONSE;
                                handler.sendMessage(message);//发送消息到消息队列
                            }
                        });
                    }
                }).start();
                break;
            default:
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String cityName;
        final String adress;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        findViewById(R.id.show_bt_switch).setOnClickListener(this);
        findViewById(R.id.show_bt_refresh).setOnClickListener(this);
        mShowTvNowInfo = (TextView) findViewById(R.id.show_tv_nowInfo);
        mShowTvTodayInfo = (TextView) findViewById(R.id.show_tv_todayInfo);
        mShowTvFutureInfo = (TextView) findViewById(R.id.show_tv_futureInfo);
        mShowTVCityName = (TextView) findViewById(R.id.show_tv_city_name);
        cityName = "广州"; //程序默认城市为广州
        adress = ToGetHttpAdress.excute(cityName);
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpUtil.sendHttpRequest(adress, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        Message message = new Message();
                        message.what = SHOW_RESPONSE;
                        message.obj = response.toString();
                        handler.sendMessage(message);
                        mSetCityNameText = cityName; //设置当前的城市名
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //获取ChangeCityActivity返回数据并进行处理的方法
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    final String returnData = data.getStringExtra("cityName"); //取出返回的数据
                    final String adress = ToGetHttpAdress.excute(returnData);//转换为发送格式
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            HttpUtil.sendHttpRequest(adress, new HttpCallbackListener() { //发送网络请求
                                @Override
                                public void onFinish(String response) {
                                    Message message = new Message();
                                    message.what = SHOW_RESPONSE;
                                    message.obj = response.toString();
                                    handler.sendMessage(message);
                                    mSetCityNameText = returnData; //设置当前城市名
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
                } else {
                    Toast.makeText(ShowActivity.this, "返回数据错误", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }


    }

}
