package com.fips.huashun.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fips.huashun.ApplicationEx;
import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.modle.bean.ActivityInfo;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：活动适配器
 * Created by Administrator on 2016/8/19.
 * @author 张柳 创建时间：2016年8月19日16:12:39
 */
public class ActivityRecommendAdapter extends BaseAdapter
{
    private LayoutInflater mInflater;
    private List<ActivityInfo> items =new ArrayList<ActivityInfo>();

    private Context icontext;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    public ActivityRecommendAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        icontext = context;
    }
    public ActivityRecommendAdapter(Context context, List<ActivityInfo> ships) {

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
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null;
        if (convertView == null)
        {
            convertView = View.inflate(icontext, R.layout.activity_recommend_item, null);
            holder = new ViewHolder();
            holder.activityImg = (ImageView) convertView.findViewById(R.id.iv_activity_recommend);
            holder.titleTv = (TextView) convertView.findViewById(R.id.activity_recommend_title);
            holder.startTimeTv = (TextView) convertView.findViewById(R.id.activity_recommend_starttime);
            holder.endTimeTv = (TextView) convertView.findViewById(R.id.activity_recommend_endtime);
            holder.joinNumTv = (TextView) convertView.findViewById(R.id.activity_recommend_joinnum);
            holder.priceTv = (TextView) convertView.findViewById(R.id.activity_recommend_price);
            holder.labelOneTv= (TextView) convertView.findViewById(R.id.tv_activity_recommend_label_one);
            holder.labelOneTv.setVisibility(View.GONE);
            holder.labelTwoTv= (TextView) convertView.findViewById(R.id.tv_activity_recommend_label_two);
            holder.labelTwoTv.setVisibility(View.GONE);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        imageLoader.displayImage(Constants.IMG_URL+items.get(position).getActivityimg(),holder.activityImg, ApplicationEx.home_activity_options);
        holder.titleTv.setText(items.get(position).getActivityname());
        String activitytip = items.get(position).getActivitytip();
//        if (null != activitytip && !TextUtils.isEmpty(activitytip))
//        {
//            String[] tipString = activitytip.split(",");
//            if (tipString.length == 1)
//            {
//                holder.labelOneTv.setText(tipString[0]);
//                holder.labelOneTv.setVisibility(View.VISIBLE);
//            } else if(tipString.length == 2)
//            {
//                holder.labelOneTv.setText(tipString[0]);
//                holder.labelOneTv.setVisibility(View.VISIBLE);
//                holder.labelTwoTv.setText(tipString[1]);
//                holder.labelTwoTv.setVisibility(View.VISIBLE);
//            }
//        }
        if (TextUtils.isEmpty(items.get(position).getLabel1())){
            holder.labelOneTv.setVisibility(View.GONE);
        }else {
            holder.labelOneTv.setVisibility(View.VISIBLE);
            holder.labelOneTv.setText(items.get(position).getLabel1());
        }
        if (TextUtils.isEmpty(items.get(position).getLabel2())){
            holder.labelTwoTv.setVisibility(View.GONE);
        }else {
            holder.labelTwoTv.setVisibility(View.VISIBLE);
            holder.labelTwoTv.setText(items.get(position).getLabel1());
        }
        holder.startTimeTv.setText("开始时间："+items.get(position).getStarttime());
        holder.endTimeTv.setText("结束时间："+items.get(position).getEndtime());
        if(TextUtils.isEmpty(items.get(position).getJoinnum()))
        {
            holder.joinNumTv.setText("参与人数：0人");
        } else
        {
            holder.joinNumTv.setText("参与人数："+items.get(position).getJoinnum()+"人");
        }
        holder.priceTv.setText(items.get(position).getActivityprice()+"魔豆");
        return convertView;
    }

    class ViewHolder
    {
        // 活动LOGO
        private ImageView activityImg;
        // 活动标题，活动开始时间，结束时间，加入人数，活动费用
        private TextView titleTv,startTimeTv,endTimeTv,joinNumTv,priceTv,labelOneTv,labelTwoTv;
    }
}
