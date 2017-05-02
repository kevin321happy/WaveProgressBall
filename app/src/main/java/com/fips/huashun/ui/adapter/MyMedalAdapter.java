package com.fips.huashun.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.modle.bean.MyMedal;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/24.
 */
public class MyMedalAdapter extends BaseAdapter
{
    private LayoutInflater mInflater;
    private List<MyMedal> items =new ArrayList<MyMedal>();


    private Context icontext;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    public MyMedalAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        icontext = context;
    }
    public MyMedalAdapter(Context context, List<MyMedal> ships) {

        this.icontext = context;
        this.items = ships;

    }
    public void addItem(MyMedal it) {
        items.add(it);
    }

    public void removeItems() {
        items.clear();
    }

    public void setListItems(List<MyMedal> lit) {
        items = lit;
    }

    public int getCount() {
        return  items==null?0:items.size();
    }
    public List<MyMedal> getAllListDate() {
        return this.items;
    }

    public MyMedal getItem(int position) {
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
            convertView = View.inflate(icontext, R.layout.mymedal_item, null);
            holder = new ViewHolder();
            holder.headImg = (ImageView) convertView.findViewById(R.id.iv_mymedal);
            holder.nameTv = (TextView) convertView.findViewById(R.id.tv_mymedal_name);
            holder.infoTv = (TextView) convertView.findViewById(R.id.tv_mymedal_info);
            holder.goTv = (TextView) convertView.findViewById(R.id.tv_mymedal_go);
            holder.goTv.setVisibility(View.GONE);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        imageLoader.displayImage(Constants.IMG_URL+items.get(position).getMedalimg(),holder.headImg);
        holder.nameTv.setText(items.get(position).getMedalname());
        holder.infoTv.setText(items.get(position).getMedinfo());
        String state = items.get(position).getState();
        //获得状态 0未完成 1已完成
        if("0".equals(state))
        {
            holder.nameTv.setTextColor(icontext.getResources().getColor(R.color.text_hui));
            holder.infoTv.setTextColor(icontext.getResources().getColor(R.color.text_hui));
            holder.goTv.setVisibility(View.VISIBLE);
        } else
        {
            holder.nameTv.setTextColor(icontext.getResources().getColor(R.color.title_color));
            holder.infoTv.setTextColor(icontext.getResources().getColor(R.color.title_color));
            holder.goTv.setVisibility(View.GONE);
        }
        return convertView;
    }
    class ViewHolder
    {
        private ImageView headImg;
        private TextView nameTv,infoTv,goTv;
    }
}
