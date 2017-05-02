package com.fips.huashun.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.fips.huashun.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：反馈图片显示
 * Created by Administrator on 2016/8/16.
 * @author 张柳 创建时间：2016年8月16日16:25:22
 */
public class FeedbackAdapter extends BaseAdapter
{

    private LayoutInflater mInflater;
    private List<String> items =new ArrayList<String>();


    private Context icontext;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    public FeedbackAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        icontext = context;
    }
    public FeedbackAdapter(Context context, List<String> ships) {

        this.icontext = context;
        this.items = ships;

    }
    public void addItem(String it) {
        items.add(it);
    }

    public void removeItems() {
        items.clear();
    }

    public void setListItems(List<String> lit) {

        if (lit.size()<3){
            lit.add("");
        }else if (lit.size()==3){
        }
        items=lit;
    }

    public int getCount() {
        Log.e("999","items.size()="+items.size());
        return  items==null?0:items.size();
    }
    public List<String> getAllListDate() {
        return this.items;
    }

    public String getItem(int position) {
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
            convertView = View.inflate(icontext, R.layout.leave_wall_item, null);
            holder = new ViewHolder();

            holder.feedBackImg = (ImageView) convertView.findViewById(R.id.iv_feedback);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        Log.e("==","position="+position+",--"+TextUtils.isEmpty(items.get(position)));
        if (TextUtils.isEmpty(items.get(position))) {

        }else {
            holder.feedBackImg.setImageURI(Uri.parse(items.get(position)));
        }
        return convertView;
    }

    class ViewHolder
    {
        //
       private ImageView feedBackImg;
    }
}
