package com.fips.huashun.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fips.huashun.ApplicationEx;
import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.modle.bean.ActivityJoinInfo;
import com.fips.huashun.widgets.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 功能：活动加入者列表适配器
 * Created by Administrator on 2016/8/16.
 * @author 张柳 创建时间：2016年8月16日16:25:22
 */
public class ActivityJoinAdapter extends BaseAdapter
{
    private List<ActivityJoinInfo> list;
    private Context context;
    private LayoutInflater inflater;

    public ActivityJoinAdapter(Context context)
    {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }
    public ActivityJoinAdapter(Context context, List<ActivityJoinInfo> list)
    {
        this.list = list;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public boolean isEnabled(int position)
    {
        return false;
    }

    @Override
    public int getCount()
    {
        if(null == list )
        {
            return 0;
        }
        return list.size();
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
            convertView = inflater.inflate(R.layout.activity_join_item, null);
            holder = new ViewHolder();
            holder.headImg = (CircleImageView) convertView.findViewById(R.id.iv_activity_join_item);
            holder.nameTv = (TextView) convertView.findViewById(R.id.tv_activity_joinname_item);
            holder.phoneTv = (TextView) convertView.findViewById(R.id.tv_activity_joinphone_item);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(Constants.IMG +list.get(position).getJoinnerimg(), holder.headImg, ApplicationEx.head_options);
        holder.nameTv.setText(list.get(position).getJoinnername());
        String joinnerphone = list.get(position).getJoinnerphone();
        if (!TextUtils.isEmpty(joinnerphone)){
            holder.phoneTv.setText(joinnerphone.substring(0,3)+"***"+joinnerphone.substring(joinnerphone.length()-4,joinnerphone.length()));
        }
        return convertView;
    }

    class ViewHolder
    {
        // 加入者头像
       private CircleImageView headImg;
        // 姓名，手机号码
        private TextView nameTv,phoneTv;

    }
}
