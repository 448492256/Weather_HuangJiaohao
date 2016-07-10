package com.huangjiahao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.huangjiahao.R;
import com.huangjiahao.bean.City;

import java.util.List;

/**
 * Created by ASUS on 2016/6/3.
 */
public class PickProvinceAdapter extends ArrayAdapter<City> { //用于省份选择
    private int mResourcedId;

    public PickProvinceAdapter(Context context, int textViewResourceId, List<City> objects) {
        super(context,textViewResourceId,objects);
        mResourcedId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        City city = getItem(position); //获取当前的City实例
        View view;
        ViewHolder viewHolder;
        if(convertView == null) { //convertView为空则将viewHolder存储在view中
            view = LayoutInflater.from(getContext()).inflate(mResourcedId, null);
            viewHolder = new ViewHolder();
            viewHolder.ProvinceName = (TextView) view.findViewById(R.id.pick_province_tv);
            view.setTag(viewHolder); //将viewHolder存储在View中
        } else { //convertView不为空的时候直接把viewHolder取出
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.ProvinceName.setText(city.getProvinceName());
        return view;
    }

    class ViewHolder {
        TextView ProvinceName; //内部类，用于对控件的缓存
    }


}
