package com.fips.huashun.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fips.huashun.R;

import java.util.List;
import java.util.Map;

/**
 * 功能：企业课程适配器
 * Created by Administrator on 2016/8/16.
 * @author 张柳 创建时间：2016年8月16日16:25:22
 */
public class CompanyCourseAdapter extends BaseAdapter
{
    private List<Map<String, Object>> list;
    private Context context;
    private LayoutInflater inflater;

    public CompanyCourseAdapter(Context context)
    {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }
    public CompanyCourseAdapter(Context context, List<Map<String, Object>> list)
    {
        this.list = list;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount()
    {
        // TODO Auto-generated method stub
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
            convertView = inflater.inflate(R.layout.company_course_item, null);
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
       private ImageView headImg;
        private TextView nameTv;

    }
}
