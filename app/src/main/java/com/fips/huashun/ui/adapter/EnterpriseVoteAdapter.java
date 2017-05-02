package com.fips.huashun.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fips.huashun.R;
import com.fips.huashun.modle.bean.ActivtyOptionBean;
import com.fips.huashun.widgets.AnimateHorizontalProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 功能：企业投票选项适配器
 * Created by Administrator on 2016/8/16.
 *
 * @author 张柳 创建时间：2016年8月16日16:25:22
 */
public class EnterpriseVoteAdapter extends BaseAdapter
{
    private LayoutInflater mInflater;
    private List<ActivtyOptionBean> items = new ArrayList<ActivtyOptionBean>();
    private Context icontext;
    private String isVoted;// 0 已投票 1未投票
    //投票总票数
    private int selectNum;
    private Random random = new Random();
    public EnterpriseVoteAdapter(Context context)
    {
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.icontext = context;

    }

    public EnterpriseVoteAdapter(Context context, String isVoted)
    {
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.icontext = context;
        this.isVoted = isVoted;
    }

    public EnterpriseVoteAdapter(Context context, List<ActivtyOptionBean> ships)
    {
        this.icontext = context;
        this.items = ships;
    }

    public void addItem(ActivtyOptionBean it)
    {
        items.add(it);
    }

    public void removeItems()
    {
        items.clear();
    }

    public void setListItems(List<ActivtyOptionBean> lit)
    {
        items = lit;
    }

    public void setIsVoted(String isVoted)
    {
        this.isVoted = isVoted;
    }

    public void setSelectNum(String selectNum)
    {
        if (TextUtils.isEmpty(selectNum))
        {
            this.selectNum = 0;
        } else
        {
            this.selectNum = Integer.valueOf(selectNum);
        }
    }

    public List<ActivtyOptionBean> getAllListDate()
    {
        return this.items;
    }

    public int getCount()
    {
        return items == null ? 0 : items.size();
    }

    public ActivtyOptionBean getItem(int position)
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
            if ("0".equals(isVoted))
            {// 已投票
                convertView = View.inflate(icontext, R.layout.enterprise_vote_item, null);
            } else
            {// 未投票
                convertView = View.inflate(icontext, R.layout.enterprise_vote_item, null);
            }
            holder = new ViewHolder();
//            holder.checkBox = (CheckBox) convertView.findViewById(R.id.rb_enterprise_vote);
            holder.optionTv = (TextView) convertView.findViewById(R.id.tv_vote_option);
            //需要投票
            holder.ll_had_vote = (LinearLayout) convertView.findViewById(R.id.ll_had_vote);
            //不需要投票
            holder.ll_no_vote = (RelativeLayout) convertView.findViewById(R.id.ll_no_vote);
            holder.animate_progress_bar = (AnimateHorizontalProgressBar) convertView.findViewById(R.id.pb_had_vote);
            holder.hadOptionTv = (TextView) convertView.findViewById(R.id.tv_hadvote_option);
            holder.optionNumberTv = (TextView) convertView.findViewById(R.id.tv_hadvote_option_number);
            holder.iv_chect = (ImageView) convertView.findViewById(R.id.iv_chect);
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        Log.e("isVoted",isVoted);
        if ("1".equals(isVoted)) {// 已投票，显示投票记录
            holder.ll_no_vote.setVisibility(View.VISIBLE);
            holder.ll_had_vote.setVisibility(View.GONE);
            holder.hadOptionTv.setText(String.valueOf((char)(items.get(position).getOptionlets()+'A')) + "." + items.get(position).getContent());
            holder.optionNumberTv.setText(items.get(position).getSelectNum() + "票");
            // 单个选项的投票总票数
            int danSelectNum = 0;
            if (!TextUtils.isEmpty(items.get(position).getSelectNum()+""))
            {
                danSelectNum = Integer.valueOf(items.get(position).getSelectNum());
//                danSelectNum = items.get(position).getSelectNum();
            }
            holder.animate_progress_bar.setProgress(danSelectNum);
            holder.animate_progress_bar.setMax(selectNum);
            holder.animate_progress_bar.setProgressWithAnim(danSelectNum);
        } else if ("0".equals(isVoted)){// 未投票，显示投票选项
            holder.ll_no_vote.setVisibility(View.GONE);
            holder.ll_had_vote.setVisibility(View.VISIBLE);
            holder.optionTv.setText(String.valueOf((char)(items.get(position).getOptionlets()+'A')) + "." + items.get(position).getContent());
            if (items.get(position).isChecked())
            {
                holder.iv_chect.setImageDrawable(icontext.getResources().getDrawable(R.drawable.vote_pitch_on));
            } else
            {
                holder.iv_chect.setImageDrawable(icontext.getResources().getDrawable(R.drawable.vote_unchecked));
            }
        }
        return convertView;
    }

    class ViewHolder
    {
        private LinearLayout ll_had_vote;
        private CheckBox checkBox;
        private TextView optionTv;
        private RelativeLayout ll_no_vote;
        private AnimateHorizontalProgressBar animate_progress_bar;
        private TextView hadOptionTv;
        private TextView optionNumberTv;
        private ImageView iv_chect;
    }
}
