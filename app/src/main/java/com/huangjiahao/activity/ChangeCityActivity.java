package com.huangjiahao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.huangjiahao.R;
import com.huangjiahao.adapter.CityAdapter;
import com.huangjiahao.bean.City;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择城市的活动
 */
public class ChangeCityActivity extends Activity implements View.OnClickListener {

    private EditText mChangeCityEt; //输入城市名称的编辑栏
    private ListView mChangeCityLv; //展示一些城市的控件
    private List<City> cityList = new ArrayList<City>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_city);
        findViewById(R.id.change_city_bt_search).setOnClickListener(this);
        findViewById(R.id.change_city_bt_pick).setOnClickListener(this);
        initCities(); //初始化一些城市的数据
        CityAdapter adapter = new CityAdapter(ChangeCityActivity.this, R.layout.city_item, cityList);
        mChangeCityEt = (EditText) findViewById(R.id.change_city_et_write);
        mChangeCityLv = (ListView) findViewById(R.id.change_city_lv);
        mChangeCityLv.setAdapter(adapter);
        mChangeCityLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //ListView控件事件的监听器
                City city = cityList.get(position); //点击控件后返回城市姓名并关闭活动
                String returnData = city.getCityName();
                Intent intent = new Intent();
                intent.putExtra("cityName",returnData);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_city_bt_pick: //跳到选择城市的按钮
                Intent intent1 = new Intent(ChangeCityActivity.this,PickProvinceActivity.class);
                startActivityForResult(intent1,1);
                break;
            case R.id.change_city_bt_search: //输入后查找城市的按钮
                String str = mChangeCityEt.getText().toString().trim(); //得到编辑栏的信息
                if(str == null){
                    Toast.makeText(ChangeCityActivity.this,"请输入城市信息",Toast.LENGTH_SHORT).show();
                }else if(!str.matches("[\\u4e00-\\u9fa5]+")){ //正则表达式检测输入是否中文
                    Toast.makeText(ChangeCityActivity.this,"您输入的信息格式有误，请重试！",Toast.LENGTH_SHORT).show();
                }
                else {  //向上一个活动传递信息
                    Intent intent = new Intent();
                    intent.putExtra("cityName",str);
                    setResult(RESULT_OK,intent);
                    finish();
                }
                break;
            default:
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //接收上个活动传回来的信息并关闭本活动并向上一个活动传递数据
        switch (requestCode) {
            case 1:
                if(resultCode == RESULT_OK) {
                    String returnData = data.getStringExtra("data_return");
                    Intent intent = new Intent();
                    intent.putExtra("cityName",returnData);
                    setResult(RESULT_OK,intent);
                    finish();
                }
                break;
            default:
        }
    }

    private void initCities() {
        City beijing = new City("北京","");
        cityList.add(beijing);
        City guangzhou = new City("广州","");
        cityList.add(guangzhou);
        City huizhou = new City("惠州","");
        cityList.add(huizhou);
        City shanghai = new City("上海","");
        cityList.add(shanghai);
        City chengdu = new City("成都","");
        cityList.add(chengdu);
        City xiamen = new City("厦门","");
        cityList.add(xiamen);
        City shenzhen = new City("深圳","");
        cityList.add(shenzhen);
        City hongkong = new City("香港","");
        cityList.add(hongkong);
        City hangzhou = new City("杭州","");
        cityList.add(hangzhou);
    }
}
