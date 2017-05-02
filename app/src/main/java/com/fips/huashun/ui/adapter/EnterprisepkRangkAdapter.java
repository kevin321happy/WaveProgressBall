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
import com.fips.huashun.modle.bean.PkList;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：企业PK适配器
 * Created by Administrator on 2016/8/22.
 */
public class EnterprisepkRangkAdapter extends BaseAdapter
{
    private LayoutInflater mInflater;
    private List<PkList> items = new ArrayList<PkList>();
    private int localIndex;
    private Context icontext;
    private ImageLoader imageLoader = ImageLoader.getInstance();


    public EnterprisepkRangkAdapter(Context context, int localIndex)
    {
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.icontext = context;
        this.localIndex = localIndex;
    }

    public EnterprisepkRangkAdapter(Context context, List<PkList> ships)
    {
        this.icontext = context;
        this.items = ships;
    }

    public void addItem(PkList it)
    {
        items.add(it);
    }

    public void removeItems()
    {
        items.clear();
    }

    public void setListItems(List<PkList> lit)
    {
        items = lit;
    }

    public int getCount()
    {
        return items == null ? 0 : items.size();
    }

    public List<PkList> getAllListDate()
    {
        return this.items;
    }

    public PkList getItem(int position)
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
            convertView = View.inflate(icontext, R.layout.enterprisepk_rangk_item, null);
            holder = new ViewHolder();
            holder.nameLayout = (LinearLayout) convertView.findViewById(R.id.ll_name);
            holder.rankImg = (ImageView) convertView.findViewById(R.id.iv_rank_img);
            holder.rankImg.setVisibility(View.VISIBLE);
            holder.headImg = (ImageView) convertView.findViewById(R.id.iv_head);
            holder.rankTv = (TextView) convertView.findViewById(R.id.tv_rank);
            holder.rankTv.setVisibility(View.GONE);
            holder.nameTv = (TextView) convertView.findViewById(R.id.tv_name);
            holder.workNumTv = (TextView) convertView.findViewById(R.id.tv_worknum);
            holder.countsTv = (TextView) convertView.findViewById(R.id.tv_counts);
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        String rank = items.get(position).getRank();
        if ("1".equals(rank))
        {
            holder.rankImg.setImageResource(R.drawable.gold_medal);
            holder.rankTv.setTextColor(icontext.getResources().getColor(R.color.label_color));
            holder.workNumTv.setTextColor(icontext.getResources().getColor(R.color.label_color));
            holder.countsTv.setTextColor(icontext.getResources().getColor(R.color.label_color));
            holder.nameTv.setTextColor(icontext.getResources().getColor(R.color.label_color));
            holder.rankImg.setVisibility(View.VISIBLE);
            holder.rankTv.setVisibility(View.GONE);
        } else if ("2".equals(rank))
        {
            holder.rankImg.setImageResource(R.drawable.silver_medal);
            holder.rankTv.setTextColor(icontext.getResources().getColor(R.color.enterprise_text_color));
            holder.workNumTv.setTextColor(icontext.getResources().getColor(R.color.enterprise_text_color));
            holder.countsTv.setTextColor(icontext.getResources().getColor(R.color.enterprise_text_color));
            holder.nameTv.setTextColor(icontext.getResources().getColor(R.color.enterprise_text_color));
            holder.rankImg.setVisibility(View.VISIBLE);
            holder.rankTv.setVisibility(View.GONE);
        } else if ("3".equals(rank))
        {
            holder.rankImg.setImageResource(R.drawable.bronze_medal);
            holder.rankTv.setTextColor(icontext.getResources().getColor(R.color.mycourse_text_blue));
            holder.workNumTv.setTextColor(icontext.getResources().getColor(R.color.mycourse_text_blue));
            holder.countsTv.setTextColor(icontext.getResources().getColor(R.color.mycourse_text_blue));
            holder.nameTv.setTextColor(icontext.getResources().getColor(R.color.mycourse_text_blue));
            holder.rankImg.setVisibility(View.VISIBLE);
            holder.rankTv.setVisibility(View.GONE);
        } else
        {
            holder.rankTv.setTextColor(icontext.getResources().getColor(R.color.text_black));
            holder.workNumTv.setTextColor(icontext.getResources().getColor(R.color.text_black));
            holder.countsTv.setTextColor(icontext.getResources().getColor(R.color.text_black));
            holder.nameTv.setTextColor(icontext.getResources().getColor(R.color.text_black));
            holder.rankImg.setVisibility(View.GONE);
            holder.rankTv.setVisibility(View.VISIBLE);
            holder.rankTv.setText(rank);
        }
        if (localIndex != 3)
        {
            holder.headImg.setVisibility(View.VISIBLE);
            imageLoader.displayImage(Constants.IMG + items.get(position).getFilepath(), holder.headImg, ApplicationEx.head_options);
            holder.nameTv.setText(items.get(position).getTruename());
            holder.workNumTv.setText(items.get(position).getDepname());
        } else
        {
            holder.headImg.setVisibility(View.INVISIBLE);
            holder.nameTv.setText(items.get(position).getDepname());
            holder.workNumTv.setText(items.get(position).getPeoplenum());
        }
        holder.countsTv.setText(items.get(position).getTotal_points());

        return convertView;
    }

    class ViewHolder
    {

        private ImageView rankImg, headImg;
        private TextView rankTv, nameTv, workNumTv, countsTv;
        private LinearLayout nameLayout;

    }
}
