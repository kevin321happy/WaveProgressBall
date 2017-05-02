package com.fips.huashun.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fips.huashun.R;
import com.fips.huashun.modle.bean.IntegralTaskInfo;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/29.
 */
public class IntegralTaskAdapter extends BaseAdapter
{
    private LayoutInflater mInflater;
    private List<IntegralTaskInfo> items = new ArrayList<IntegralTaskInfo>();


    private Context icontext;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public IntegralTaskAdapter(Context context)
    {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        icontext = context;
    }

    public IntegralTaskAdapter(Context context, List<IntegralTaskInfo> ships)
    {

        this.icontext = context;
        this.items = ships;

    }

    public void addItem(IntegralTaskInfo it)
    {
        items.add(it);
    }

    public void removeItems()
    {
        items.clear();
    }

    public void setListItems(List<IntegralTaskInfo> lit)
    {
        items = lit;
    }

    public int getCount()
    {
        return items == null ? 0 : items.size();
    }

    public List<IntegralTaskInfo> getAllListDate()
    {
        return this.items;
    }

    public IntegralTaskInfo getItem(int position)
    {
        return items.get(position);
    }

    public boolean areAllItemsSelectable()
    {
        return false;
    }

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
            convertView = View.inflate(icontext, R.layout.integraltask_item, null);
            holder = new ViewHolder();
            holder.titleTv = (TextView) convertView.findViewById(R.id.tv_tasktitle);
            holder.contentTv = (TextView) convertView.findViewById(R.id.tv_taskcontent);
            holder.isFinishTv = (TextView) convertView.findViewById(R.id.tv_goto_finish);
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.titleTv.setText(items.get(position).getTasktitle());
        holder.contentTv.setText(items.get(position).getTaskcontent());
        return convertView;
    }

    class ViewHolder
    {
        // 标题，介绍，完成度
        private TextView titleTv,contentTv,isFinishTv;
    }
}
