package com.fips.huashun.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.fips.huashun.R;
import com.fips.huashun.modle.bean.ActivtyVoteBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：投票适配器
 * Created by Administrator on 2016/8/24.
 */
public class VoteTaskAdapter extends BaseAdapter
{
    private LayoutInflater mInflater;
    private List<ActivtyVoteBean> items = new ArrayList<ActivtyVoteBean>();
    private Context icontext;

    public VoteTaskAdapter(Context context)
    {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        icontext = context;
    }

    public VoteTaskAdapter(Context context, List<ActivtyVoteBean> ships)
    {
        this.icontext = context;
        this.items = ships;
    }

    public void addItem(ActivtyVoteBean it)
    {
        items.add(it);
    }

    public void removeItems()
    {
        items.clear();
    }

    public void setListItems(List<ActivtyVoteBean> lit)
    {
        items = lit;
    }

    public int getCount()
    {
        return items == null ? 0 : items.size();
    }

    public ActivtyVoteBean getItem(int position)
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
            convertView = View.inflate(icontext, R.layout.vote_item, null);
            holder = new ViewHolder();
            holder.titleTv = (TextView) convertView.findViewById(R.id.tv_title);
            holder.timeTv = (TextView) convertView.findViewById(R.id.tv_content);
            holder.goButton = (Button) convertView.findViewById(R.id.bt_submit);
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.titleTv.setText(items.get(position).getBigtitle());
        holder.timeTv.setText("投票时间：" + items.get(position).getAddtime() + "至" + items.get(position).getEndtime());
        //是否已投票（0：已投票；1：未投票）
        String isvote = items.get(position).getIsVote();
        if ("1".equals(isvote))
        {
            holder.goButton.setText("已投票");
            holder.goButton.setBackgroundResource(R.drawable.task_voted_button);
        } else
        {
            holder.goButton.setText("投票");
            holder.goButton.setBackgroundResource(R.drawable.task_novoted_button);
        }
        return convertView;
    }

    class ViewHolder
    {
        // 标题，时间
        private TextView titleTv, timeTv;
        // 进入投票界面按钮
        private Button goButton;
    }
}
