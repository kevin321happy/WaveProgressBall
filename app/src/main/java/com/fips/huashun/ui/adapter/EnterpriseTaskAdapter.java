package com.fips.huashun.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.fips.huashun.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/24.
 */
public class EnterpriseTaskAdapter extends BaseAdapter
{
    private List<Map<String, Object>> list;
    private Context context;
    private LayoutInflater inflater;

    public EnterpriseTaskAdapter(Context context)
    {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }
    public EnterpriseTaskAdapter(Context context, List<Map<String, Object>> list)
    {
        this.list = list;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount()
    {
        return 5;
    }

    @Override
    public Object getItem(int position)
    {
        return position;
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
            convertView = inflater.inflate(R.layout.todaytask_item, null);
            holder = new ViewHolder();
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
    class ViewHolder
    {

    }
}
