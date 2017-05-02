package com.fips.huashun.ui.adapter;

/**
 * Created by Administrator on 2016/3/14.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fips.huashun.R;
import com.fips.huashun.modle.bean.AcplanInfo;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class BusinessNoticeAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<AcplanInfo> items =new ArrayList<AcplanInfo>();


    private Context icontext;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    public BusinessNoticeAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        icontext = context;
    }
    public BusinessNoticeAdapter(Context context, List<AcplanInfo> ships) {

        this.icontext = context;
        this.items = ships;

    }
    public void addItem(AcplanInfo it) {
        items.add(it);
    }

    public void removeItems() {
        items.clear();
    }

    public void setListItems(List<AcplanInfo> lit) {
        items = lit;
    }

    public int getCount() {
        return  items==null?0:items.size();
    }

    public AcplanInfo getItem(int position) {
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
            convertView=View.inflate(icontext, R.layout.business_notice_items, null);
            holder = new ViewHolder(convertView);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.acdateTv.setText(items.get(position).getAcdate());
        holder.timeTv.setText(items.get(position).getStartime()+"-"+items.get(position).getStoptime());
        holder.studyInfoTv.setText(items.get(position).getStudyInfo());
        return convertView;
    }

    class ViewHolder {
        private	View convertView;
        private	TextView  acdateTv;
        private	TextView  timeTv;
        private	TextView  studyInfoTv ;


        ViewHolder(View convertView) {
            this.convertView = convertView;
            acdateTv = (TextView) convertView.findViewById(R.id.item_actnotice_acdate);
            timeTv = (TextView) convertView.findViewById(R.id.item_actnotice_time);
            studyInfoTv = (TextView) convertView.findViewById(R.id.item_actnotice_studyinfo);

            convertView.setTag(this);
        }
    }
}
