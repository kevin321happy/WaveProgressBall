package com.fips.huashun.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fips.huashun.R;
import com.fips.huashun.modle.bean.BeansInfo;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：魔豆明细适配器
 * Created by Administrator on 2016/9/28.
 * @author 张柳 时间：2016年9月28日10:48:33
 */
public class MyBeansDetailAdapter extends BaseAdapter
{
    private List<BeansInfo> items = new ArrayList<BeansInfo>();
    private Context icontext;
    private LayoutInflater mInflater;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public MyBeansDetailAdapter(Context context)
    {
        this.icontext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public MyBeansDetailAdapter(Context context, List<BeansInfo> ships)
    {
        this.items = ships;
        this.icontext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public void addItem(BeansInfo it)
    {
        items.add(it);
    }

    public void removeItems()
    {
        items.clear();
    }

    public void setListItems(List<BeansInfo> lit)
    {
        items = lit;
    }

    public boolean areAllItemsSelectable()
    {
        return false;
    }

    public List<BeansInfo> getAllListDate()
    {
        return this.items;
    }

    @Override
    public int getCount()
    {
        if (null == items)
        {
            return 0;
        }
        return items.size();
    }

    @Override
    public BeansInfo getItem(int position)
    {
        return items.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null;
        if (convertView == null)
        {
            convertView = View.inflate(icontext, R.layout.mybeans_detail_item, null);
            holder = new ViewHolder(convertView);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        String type = items.get(position).getType();
        if ("1".equals(type))
        {
            holder.titleTv.setText(items.get(position).getReason());
            holder.addOrReduceTv.setText("+");
            holder.addOrReduceTv.setTextColor(icontext.getResources().getColor(R.color.title_color));
            holder.changeBeanTv.setTextColor(icontext.getResources().getColor(R.color.title_color));
        } else
        {
            holder.titleTv.setText(items.get(position).getReason() + "-" + items.get(position).getBuyinfo());
            holder.addOrReduceTv.setText("-");
            holder.addOrReduceTv.setTextColor(icontext.getResources().getColor(R.color.text_hui));
            holder.changeBeanTv.setTextColor(icontext.getResources().getColor(R.color.text_hui));
        }
        holder.changeBeanTv.setText(items.get(position).getChangebean());
        holder.timeTv.setText(items.get(position).getAddtime());
        holder.currentBeanTv.setText("余额:" + items.get(position).getCurrentbean());
        return convertView;
    }

    class ViewHolder
    {
        private View convertView;
        private TextView titleTv, timeTv, addOrReduceTv, changeBeanTv, currentBeanTv;

        ViewHolder(View convertView)
        {
            this.convertView = convertView;
            this.titleTv = (TextView) convertView.findViewById(R.id.tv_mybeans_title);
            this.timeTv = (TextView) convertView.findViewById(R.id.tv_mybeans_time);
            this.addOrReduceTv = (TextView) convertView.findViewById(R.id.tv_mybeans_type);
            this.changeBeanTv = (TextView) convertView.findViewById(R.id.tv_mybeans_changebean);
            this.currentBeanTv = (TextView) convertView.findViewById(R.id.tv_my_currentbean);
            convertView.setTag(this);
        }
    }
}
