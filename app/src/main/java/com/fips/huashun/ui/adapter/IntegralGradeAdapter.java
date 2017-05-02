package com.fips.huashun.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fips.huashun.R;
import com.fips.huashun.modle.bean.IntegralLevel;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/29.
 */
public class IntegralGradeAdapter extends BaseAdapter
{
    private LayoutInflater mInflater;
    private List<IntegralLevel> items =new ArrayList<IntegralLevel>();


    private Context icontext;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    public IntegralGradeAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        icontext = context;
    }
    public IntegralGradeAdapter(Context context, List<IntegralLevel> ships) {

        this.icontext = context;
        this.items = ships;

    }
    public void addItem(IntegralLevel it) {
        items.add(it);
    }

    public void removeItems() {
        items.clear();
    }

    public void setListItems(List<IntegralLevel> lit) {
        items = lit;
    }

    public int getCount() {
        return  items==null?0:items.size();
    }
    public List<IntegralLevel> getAllListDate() {
        return this.items;
    }

    public IntegralLevel getItem(int position) {
        return items.get(position);
    }

    public boolean areAllItemsSelectable() {
        return false;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null;
        if (convertView == null)
        {
            convertView =  View.inflate(icontext, R.layout.integralgrade_item, null);
            holder = new ViewHolder();
            holder.levleTv = (TextView) convertView.findViewById(R.id.tv_integral_level);
            holder.nameTv = (TextView) convertView.findViewById(R.id.tv_integral_name);
            holder.rangeTv = (TextView) convertView.findViewById(R.id.tv_integral_range);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.levleTv.setText(items.get(position).getLevelcode());
        holder.nameTv.setText(items.get(position).getLevelname());
        holder.rangeTv.setText(items.get(position).getLevelpointsmin()+"-"+items.get(position).getLevelpointsmax());
        return convertView;
    }
    class ViewHolder
    {
        // 等级，名称，范围
        private TextView levleTv,nameTv,rangeTv;
    }
}
