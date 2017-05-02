package com.fips.huashun.ui.adapter;

/**
 * Created by Administrator on 2016/3/14.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fips.huashun.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class SearchHistoryAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<String> items =new ArrayList<String>();


    private Context icontext;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    public SearchHistoryAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        icontext = context;
    }
    public SearchHistoryAdapter(Context context, List<String> ships) {

        this.icontext = context;
        this.items = ships;

    }
    public void addItem(String it) {
        items.add(it);
    }

    public void removeItems() {
        items.clear();
    }

    public void setListItems(List<String> lit) {
        items = lit;
    }

    public int getCount() {
        return  items==null?0:items.size();
    }

    public String getItem(int position) {
        return items.get(position);
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
            convertView=View.inflate(icontext, R.layout.gridview_child_item, null);
            holder = new ViewHolder(convertView);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_child.setText(items.get(position));

        return convertView;
    }

    class ViewHolder {
        private	View convertView;

        private	TextView  tv_child ;


        ViewHolder(View convertView) {
            this.convertView=convertView;
            this. tv_child = (TextView) convertView.findViewById(R.id.tv_child);
            convertView.setTag(this);
        }
    }
}
