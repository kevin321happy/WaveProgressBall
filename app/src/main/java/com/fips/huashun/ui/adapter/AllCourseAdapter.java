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
import com.fips.huashun.modle.bean.CourseInfo;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能：企业课程适配器
 * Created by Administrator on 2016/8/16.
 * @author 张柳 创建时间：2016年8月16日16:25:22
 */
public class AllCourseAdapter extends BaseAdapter
{

    private LayoutInflater mInflater;
    private List<CourseInfo> items =new ArrayList<CourseInfo>();


    private Context icontext;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    public AllCourseAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        icontext = context;
    }
    public AllCourseAdapter(Context context, List<CourseInfo> ships) {

        this.icontext = context;
        this.items = ships;

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

    public int getCount() {
        return  items==null?0:items.size();
    }
    public List<CourseInfo> getAllListDate() {
        return this.items;
    }

    public CourseInfo getItem(int position) {
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
            convertView = View.inflate(icontext, R.layout.all_course_item, null);
            holder = new ViewHolder();
            holder.courseImg = (ImageView) convertView.findViewById(R.id.iv_all_course_item_img);
            holder.hotImg = (ImageView) convertView.findViewById(R.id.iv__all_course_item_hot);
            holder.courseNameTv = (TextView) convertView.findViewById(R.id.tv__all_course_item_title);
            holder.courseLabelOneTv = (TextView) convertView.findViewById(R.id.tv_all_course_item_label_one);
            holder.courseLabelTwoTv = (TextView) convertView.findViewById(R.id.tv_all_course_item_label_two);
            holder.courseIntroduceTv = (TextView) convertView.findViewById(R.id.tv_all_course_item_introduce);
            holder.courseLecturerTv = (TextView) convertView.findViewById(R.id.tv_all_course_item_lecturer);
            holder.coursePriceTv = (TextView) convertView.findViewById(R.id.tv_all_course_item_price);
            holder.courseStudyNumTv = (TextView) convertView.findViewById(R.id.tv_all_course_item_study_num);
            holder.goodCommentTv = (TextView) convertView.findViewById(R.id.tv_all_course_item_good_comment);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        imageLoader.displayImage(Constants.IMG_URL+items.get(position).getCourseImage(),holder.courseImg,ApplicationEx.all_course_options);
        holder.courseImg.setScaleType(ImageView.ScaleType.FIT_XY);
        holder.courseNameTv.setText(items.get(position).getCourseName());
        holder.courseIntroduceTv.setText("简介："+items.get(position).getCourseIntro());
        if ("裘马讲师".equals(items.get(position).getTeacherName())){
            holder.courseLecturerTv.setVisibility(View.INVISIBLE);
        }else {
            holder.courseLecturerTv.setText("讲师："+items.get(position).getTeacherName());
        }
        holder.coursePriceTv.setText(items.get(position).getCoursePrice()+"魔豆");
        holder.courseStudyNumTv.setText(items.get(position).getStudyNum()+"人已学习");
        String welltoken = items.get(position).getWelltoken();
        if(TextUtils.isEmpty(welltoken))
        {
            holder.goodCommentTv.setText("评分:0");
        } else     {
//            String substring = welltoken.substring(0,3);
            DecimalFormat df   = new DecimalFormat("######0.0");


            holder.goodCommentTv.setText("评分:" +  df.format(Double.parseDouble(welltoken)));
        }
        String tip = items.get(position).getTip();
        if (null != tip && !TextUtils.isEmpty(tip))
        {
            String[] tipString = tip.split(",");
            if (tipString.length == 1)
            {
                holder.courseLabelOneTv.setText(tipString[0]);
                holder.courseLabelOneTv.setVisibility(View.VISIBLE);
            } else if(tipString.length == 2)
            {
                holder.courseLabelOneTv.setText(tipString[0]);
                holder.courseLabelOneTv.setVisibility(View.VISIBLE);
                holder.courseLabelTwoTv.setText(tipString[1]);
                holder.courseLabelTwoTv.setVisibility(View.VISIBLE);
            }
        }
//        holder.ll_laout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent =new Intent(icontext, CourseDetailActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//                icontext  .startActivity(intent);
//            }
//        });
        return convertView;
    }

    class ViewHolder
    {
        // 课程图片，火
       private ImageView courseImg,hotImg;
        // 课程名，课程标签(0,1)，课程介绍，课程讲师，课程价格，课程已学习人数
        private TextView courseNameTv,courseLabelOneTv,courseLabelTwoTv,
                courseIntroduceTv,courseLecturerTv,coursePriceTv,courseStudyNumTv;
        // 好评率
        private TextView goodCommentTv;
    }
}
