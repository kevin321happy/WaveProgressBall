package com.fips.huashun.ui.adapter;

/**
 * Created by Administrator on 2016/3/14.
 */

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fips.huashun.ApplicationEx;
import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.modle.bean.CourseInfo;
import com.fips.huashun.ui.activity.CourseDetailActivity;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class IndustryCourseAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<CourseInfo> items =new ArrayList<CourseInfo>();


    private Context icontext;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private boolean isDel = false;

    public boolean isDel() {
        return isDel;
    }

    public void setDel(boolean isDel) {
        this.isDel = isDel;
    }
    public IndustryCourseAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        icontext = context;
    }
    public IndustryCourseAdapter(Context context, List<CourseInfo> ships) {

        this.icontext = context;
        this.items = ships;

    }
    public List<CourseInfo> getAllListDate() {
        return this.items;
    }
    public void addItem(CourseInfo it) {
        items.add(it);
    }

    public void removeItems() {
        items.clear();
    }

 public void setListItems(List<CourseInfo> lit) {
        items = lit;
    }
    @Override
    public int getCount() {
      return  items==null?0:items.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }



    public boolean areAllItemsSelectable() {
        return false;
    }

    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView=View.inflate(icontext, R.layout.home_course_items, null);
            holder = new ViewHolder(convertView);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        Log.e("IMG_URL",items.get(position).getCourseImage());
        imageLoader.displayImage(Constants.IMG_URL+items.get(position).getCourseImage(),holder.round_image, ApplicationEx.home_course_options);
//        if (!TextUtils.isEmpty(items.get(position).getTip())){
//            String tip = items.get(position).getTip();
//            String[] split = tip.split(", ");
//            if (!TextUtils.isEmpty(split[0])){
//                holder.tv_tag1.setText(split[0]);
//            }
//            if (!TextUtils.isEmpty(split[1])){
//                holder.tv_tag2.setText(split[1]);
//            }
//
//        }
        String tip = items.get(position).getTip();
        if (null != tip && !TextUtils.isEmpty(tip))
        {
            String[] tipString = tip.split(",");
            if (tipString.length == 1)
            {
                holder.tv_tag1.setText(tipString[0]);
                holder.tv_tag1.setVisibility(View.VISIBLE);
            } else if(tipString.length == 2)
            {
                holder.tv_tag1.setText(tipString[0]);
                holder.tv_tag1.setVisibility(View.VISIBLE);
                holder.tv_tag2.setText(tipString[1]);
                holder.tv_tag2.setVisibility(View.VISIBLE);
            }
        }
        holder.tv_name.setText(items.get(position).getCourseName());
        holder.tv_describe.setText(items.get(position).getCourseIntro());
        if ("裘马讲师".equals(items.get(position).getTeacherName())){
            holder.tv_teacherName.setVisibility(View.INVISIBLE);
        }else {
            holder.tv_teacherName.setText(items.get(position).getTeacherName());
        }
        holder.tv_modou.setText(items.get(position).getCoursePrice()+"魔豆");
        holder.tv_shuliang.setText(items.get(position).getStudyNum()+"人已学习");
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(icontext, CourseDetailActivity.class);
                intent.putExtra("courseId",items.get(position).getCourseId()+"");
                Log.e("333",items.get(position).getCourseId()+"");
                icontext.startActivity(intent);
            }
        });

        return convertView;
    }

    class ViewHolder {
        private	View convertView;
        private  RoundedImageView round_image;
        private  TextView tv_tag1;
        private  TextView tv_tag2;
        private  TextView tv_name;
        private  TextView tv_describe;
        private  TextView tv_teacherName;
        private  TextView tv_modou;
        private  TextView tv_shuliang;


        ViewHolder(View convertView) {
            this.convertView=convertView;
            this. round_image =  (RoundedImageView) convertView.findViewById(R.id.round_image);
            this. tv_tag1 =  (TextView) convertView.findViewById(R.id.tv_tag1);
            this. tv_tag2 =  (TextView) convertView.findViewById(R.id.tv_tag2);
            this. tv_name =  (TextView) convertView.findViewById(R.id.tv_name);
            this. tv_describe =  (TextView) convertView.findViewById(R.id.tv_describe);
            this. tv_teacherName =  (TextView) convertView.findViewById(R.id.tv_teacherName);
            this. tv_modou =  (TextView) convertView.findViewById(R.id.tv_modou);
            this. tv_shuliang =  (TextView) convertView.findViewById(R.id.tv_shuliang);

            convertView.setTag(this);
        }
    }


}
