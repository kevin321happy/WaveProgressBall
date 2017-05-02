package com.fips.huashun.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.fips.huashun.ApplicationEx;
import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.modle.bean.CommentInfo;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：课程评价适配器
 * Created by Administrator on 2016/8/19.
 * @author 张柳 时间：2016年8月19日11:56:34
 */
public class CourseEvaluateAdapter extends BaseAdapter
{

    private LayoutInflater mInflater;
    private List<CommentInfo> items =new ArrayList<CommentInfo>();


    private Context icontext;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    public CourseEvaluateAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        icontext = context;
    }
    public CourseEvaluateAdapter(Context context, List<CommentInfo> ships) {

        this.icontext = context;
        this.items = ships;

    }
    public void addItem(CommentInfo it) {
        items.add(it);
    }

    public void removeItems() {
        items.clear();
    }

    public void setListItems(List<CommentInfo> lit) {
        items = lit;
    }

    public int getCount() {
        return  items==null?0:items.size();
    }

    public CommentInfo getItem(int position) {
        return items.get(position);
    }

    public boolean areAllItemsSelectable() {
        return false;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView=View.inflate(icontext, R.layout.course_evaluate_item, null);
            holder = new ViewHolder(convertView);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        imageLoader.displayImage(Constants.IMG+items.get(position).getUserimg(),holder.iv_image_advertise, ApplicationEx.head_options);
        holder.tv_userName.setText(items.get(position).getUsername());
        holder.ra_bar.setRating(Integer.valueOf(items.get(position).getScore()));
        holder.tv_jindu.setText("学习进度"+items.get(position).getSturate()+"%");
        holder.tv_content.setText(items.get(position).getContext());
        holder.tv_time.setText(items.get(position).getCreate_date());

        return convertView;
    }

    class ViewHolder {
        private	View convertView;
        private ImageView iv_image_advertise ;
        private	TextView  tv_userName ;
        private	RatingBar  ra_bar ;
        private	TextView  tv_time ;
        private	TextView  tv_content ;
        private	TextView  tv_jindu ;


        ViewHolder(View convertView) {
            this.convertView=convertView;
            this.iv_image_advertise = (ImageView) convertView.findViewById(R.id.iv_image_advertise);
            this.tv_userName = (TextView) convertView.findViewById(R.id.tv_userName);
            this.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            this.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            this.tv_jindu = (TextView) convertView.findViewById(R.id.tv_jindu);
            this.ra_bar = (RatingBar) convertView.findViewById(R.id.ra_bar);
            convertView.setTag(this);
        }
    }
}

