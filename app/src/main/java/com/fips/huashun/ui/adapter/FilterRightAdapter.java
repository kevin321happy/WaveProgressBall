package com.fips.huashun.ui.adapter;

/**
 * Created by Administrator on 2016/3/14.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fips.huashun.R;
import com.fips.huashun.modle.bean.FilterData;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class FilterRightAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<FilterData> items =new ArrayList<FilterData>();


    private Context icontext;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    public FilterRightAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        icontext = context;
    }
    public FilterRightAdapter(Context context, List<FilterData> ships) {

        this.icontext = context;
        this.items = ships;

    }
    private boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
    public void addItem(FilterData it) {
        items.add(it);
    }

    public void removeItems() {
        items.clear();
    }

    public void setListItems(List<FilterData> lit) {
        items = lit;
    }

    public int getCount() {
        return  items==null?0:items.size();
    }

    public FilterData getItem(int position) {
        return items.get(position);
    }
    public List<FilterData> getAllListDate() {
        return this.items;
    }
    public boolean areAllItemsSelectable() {
        return false;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView=View.inflate(icontext, R.layout.item_filter_left, null);
            holder = new ViewHolder(convertView);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_title.setText(items.get(position).getCoursetypename());
//        if (items.get(position).isSelected()){
//            holder.tv_title.setTextColor(icontext.getResources().getColor(R.color.title_color));
//            holder.iv_image.setVisibility(View.VISIBLE);
//        }else {
            holder.tv_title.setTextColor(icontext.getResources().getColor(R.color.text_bg));
//            holder.iv_image.setVisibility(View.GONE);
//        }
        return convertView;
    }

    class ViewHolder {
        private	View convertView;
        private ImageView iv_image ;
        private	TextView  tv_title ;
        private	TextView  creat_time ;
        private	TextView  comment ;


        ViewHolder(View convertView) {
            this.convertView=convertView;
            this. tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            this. iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
            convertView.setTag(this);
        }
    }
}
