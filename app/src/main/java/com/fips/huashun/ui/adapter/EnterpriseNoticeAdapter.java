package com.fips.huashun.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fips.huashun.R;
import com.fips.huashun.modle.bean.EnterpiseNotice;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/22.
 */
public class EnterpriseNoticeAdapter extends BaseAdapter
{
    private LayoutInflater mInflater;
    private List<EnterpiseNotice> items =new ArrayList<EnterpiseNotice>();


    private Context icontext;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    public EnterpriseNoticeAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        icontext = context;
    }
    public EnterpriseNoticeAdapter(Context context, List<EnterpiseNotice> ships) {

        this.icontext = context;
        this.items = ships;

    }
    public void addItem(EnterpiseNotice it) {
        items.add(it);
    }

    public void removeItems() {
        items.clear();
    }

    public void setListItems(List<EnterpiseNotice> lit) {
        items = lit;
    }

    public int getCount() {
        return  items==null?0:items.size();
    }
    public List<EnterpiseNotice> getAllListDate() {
        return this.items;
    }

    public EnterpiseNotice getItem(int position) {
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
        ViewHolder holder;
        if (convertView == null)
        {
            convertView = View.inflate(icontext, R.layout.enterprise_notice_item, null);
            holder = new ViewHolder();
            /* 得到各个控件的对象 */
            holder.titleTv = (TextView) convertView.findViewById(R.id.tv_notice_title);
            //holder.unreadImg = (ImageView) convertView.findViewById(R.id.iv_notice_unread);
            holder.createTimeTv = (TextView) convertView.findViewById(R.id.tv_notice_creattime);
            holder.contentTv = (TextView) convertView.findViewById(R.id.tv_notice_content);
            // 绑定ViewHolder对象
            convertView.setTag(holder);
        }
        else
        {
            // 取出ViewHolder对象
            holder = (ViewHolder) convertView.getTag();
        }
        holder.titleTv.setTextColor(icontext.getResources().getColor(R.color.title_color));
        holder.titleTv.setText(items.get(position).getTitle());
        holder.createTimeTv.setText(items.get(position).getAddtime());
        holder.contentTv.setText(items.get(position).getContent());
        return convertView;
    }

    class ViewHolder
    {
        // 未读公告红点
        // private ImageView unreadImg;
        // 标题，创建时间，内容
        private TextView titleTv,createTimeTv,contentTv;

    }
}
