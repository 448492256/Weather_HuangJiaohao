package com.huangjiahao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huangjiahao.R;
import com.huangjiahao.bean.City;

import java.util.List;

/**
 * Created by ASUS on 2016/7/14.
 */
public class MyPickProvinceAdapter extends BaseAdapter {

    private LayoutInflater inflater = null;
    private List<City> cities = null;

    public MyPickProvinceAdapter(Context context,List<City> cities) {
        this.inflater = LayoutInflater.from(context);
        this.cities = cities;
    }

    public int getCount() {
        return (cities == null)?0:cities.size();
    }

    public Object getItem(int position) {
        return cities.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        City city = (City)getItem(position);
        ViewHolder viewHolder = null;
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.pick_province_item, null);
            viewHolder = new ViewHolder();
            viewHolder.provinceName = (TextView) convertView.findViewById(R.id.pick_province_tv);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.provinceName.setText(city.getProvinceName());
        return convertView;
    }

    class ViewHolder {
        TextView provinceName;
    }
}
