package com.fips.huashun.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fips.huashun.R;
import com.fips.huashun.modle.bean.MyMessage;
import com.fips.huashun.ui.activity.ActivityDetailActivity;
import com.fips.huashun.ui.activity.BusinessActivity;
import com.fips.huashun.ui.activity.CourseDetailActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/24.
 */
public class MyMessageAdapter extends BaseAdapter
{
    private LayoutInflater mInflater;
    private List<MyMessage> items = new ArrayList<MyMessage>();
    private Context icontext;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public MyMessageAdapter(Context context)
    {
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.icontext = context;
    }

    public MyMessageAdapter(Context context, List<MyMessage> ships)
    {
        this.icontext = context;
        this.items = ships;
    }
    public void addItem(MyMessage it)
    {
        items.add(it);
    }

    public void removeItems()
    {
        items.clear();
    }

    public void setListItems(List<MyMessage> lit)
    {
        items = lit;
    }

    public boolean areAllItemsSelectable()
    {
        return false;
    }

    public List<MyMessage> getAllListDate()
    {
        return this.items;
    }

    @Override
    public int getCount()
    {
        return items == null ? 0 : items.size();
    }

    @Override
    public MyMessage getItem(int position)
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
            convertView = mInflater.inflate(R.layout.mymessage_item, null);
            holder = new ViewHolder();
            holder.headImg = (ImageView) convertView.findViewById(R.id.iv_head);
            holder.pointImg = (ImageView) convertView.findViewById(R.id.iv_point);
            holder.nameTv = (TextView) convertView.findViewById(R.id.tv_name);
            holder.timeTv = (TextView) convertView.findViewById(R.id.tv_time);
            holder.contentTv = (TextView) convertView.findViewById(R.id.tv_content);
            holder.goDetailLayout = (LinearLayout) convertView.findViewById(R.id.ll_go_detail);
            holder.goDetailTv = (TextView) convertView.findViewById(R.id.tv_go_detail);
            holder.goDetailLayout.setVisibility(View.GONE);
            holder.goDetailTv.setVisibility(View.GONE);
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        if (items.get(position).getMsgOpen())
        {
            holder.contentTv.setLines(4);
            holder.contentTv.setSingleLine(false);
            holder.contentTv.setEllipsize(TextUtils.TruncateAt.END);
            // 消息类型 1系统消息/2课程消息/3活动消息/4企业活动
            final String msgtype = items.get(position).getMsgtype();
            final String detailid = items.get(position).getDetailid();
            Log.e("msgtype","msgtype="+msgtype+",detailid="+detailid);
            if("3".equals(msgtype))
            {
                holder.goDetailLayout.setVisibility(View.GONE);
                holder.goDetailTv.setVisibility(View.GONE);
            } else
            {
                holder.goDetailLayout.setVisibility(View.VISIBLE);
                holder.goDetailTv.setVisibility(View.VISIBLE);
                holder.goDetailTv.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if("1".equals(msgtype))
                        {
                            Intent toCourse = new Intent(icontext, CourseDetailActivity.class);
                            toCourse.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            toCourse.putExtra("courseId",detailid);
                            icontext.startActivity(toCourse);
                        } else if("2".equals(msgtype))
                        {
                            Intent toActivity = new Intent(icontext, ActivityDetailActivity.class);
                            toActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            toActivity.putExtra("activityId",detailid);
                            icontext.startActivity(toActivity);
                        } else if("4".equals(msgtype))
                        {
                            Intent toEnterpriseAct = new Intent(icontext, BusinessActivity.class);
                            toEnterpriseAct.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            toEnterpriseAct.putExtra("activityId",detailid);
                            icontext.startActivity(toEnterpriseAct);
                        }

                    }
                });
            }
        } else
        {
            holder.goDetailLayout.setVisibility(View.GONE);
            holder.goDetailTv.setVisibility(View.GONE);
            holder.contentTv.setLines(1);
            holder.contentTv.setEllipsize(TextUtils.TruncateAt.END);
        }
        if("0".equals(items.get(position).getMsgstate()))
        {// 消息未读
            holder.pointImg.setVisibility(View.VISIBLE);
        } else
        {// 消息已读
            holder.pointImg.setVisibility(View.INVISIBLE);
        }
        holder.nameTv.setText(items.get(position).getMsgtitle());
        holder.timeTv.setText(items.get(position).getMsgtime());
        holder.contentTv.setText(items.get(position).getMsgcontent());
        return convertView;
    }

    class ViewHolder
    {
        private LinearLayout goDetailLayout;
        private ImageView headImg, pointImg;
        // 消息标题,消息时间,消息内容
        private TextView nameTv, timeTv, contentTv,goDetailTv;
    }
}
