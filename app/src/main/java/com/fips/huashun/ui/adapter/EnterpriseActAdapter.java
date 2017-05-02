package com.fips.huashun.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fips.huashun.ApplicationEx;
import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.modle.bean.ActivityInfo;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/22.
 */
public class EnterpriseActAdapter extends BaseAdapter
{
    private LayoutInflater mInflater;
    private List<ActivityInfo> items =new ArrayList<ActivityInfo>();


    private Context icontext;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    public EnterpriseActAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        icontext = context;
    }
    public EnterpriseActAdapter(Context context, List<ActivityInfo> ships) {

        this.icontext = context;
        this.items = ships;

    }
    public void addItem(ActivityInfo it) {
        items.add(it);
    }

    public void removeItems() {
        items.clear();
    }

    public void setListItems(List<ActivityInfo> lit) {
        items = lit;
    }

    public int getCount() {
        return  items==null?0:items.size();
    }

    public ActivityInfo getItem(int position) {
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
            convertView = View.inflate(icontext, R.layout.enterprise_act_item, null);
            holder = new ViewHolder();
            holder.activityImg = (RoundedImageView) convertView.findViewById(R.id.iv_enterprise_act_logo);
            holder.finishLayout = (LinearLayout) convertView.findViewById(R.id.ll_finish_activity);
            holder.finishIv = (ImageView) convertView.findViewById(R.id.iv_enterprise_act_finish);
            holder.finishIv.setVisibility(View.INVISIBLE);
            holder.titleTv = (TextView) convertView.findViewById(R.id.tv_enterprise_act_title);
            holder.startTimeTv = (TextView) convertView.findViewById(R.id.tv_enterprise_act_starttime);
            holder.endTimeTv = (TextView) convertView.findViewById(R.id.tv_enterprise_act_endtime);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        imageLoader.displayImage(Constants.IMG_URL+items.get(position).getActivityimg(),holder.activityImg, ApplicationEx.enterprise_act_options);
        holder.titleTv.setText(items.get(position).getActivityname());
        holder.startTimeTv.setText("开始时间："+items.get(position).getStarttime());
        holder.endTimeTv.setText("结束时间："+items.get(position).getEndtime());
        //活动状态1进行中2已结束
        if("1".equals(items.get(position).getStatus()))
        {
            holder.finishIv.setVisibility(View.GONE);
            holder.finishLayout.setVisibility(View.GONE);
        } else if ("2".equals(items.get(position).getStatus()))
        {
            holder.finishIv.setVisibility(View.VISIBLE);
            holder.finishLayout.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    class ViewHolder
    {
        // 活动LOGO
        private RoundedImageView activityImg;
        // 已结束
        private ImageView finishIv;
        // 活动标题，活动开始时间，结束时间
        private TextView titleTv,startTimeTv,endTimeTv;
        private LinearLayout finishLayout;

    }
}
