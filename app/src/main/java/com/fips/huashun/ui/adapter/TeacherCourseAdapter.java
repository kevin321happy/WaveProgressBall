package com.fips.huashun.ui.adapter;

/**
 * Created by Administrator on 2016/3/14.
 */

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

public class TeacherCourseAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<CourseInfo> items =new ArrayList<CourseInfo>();


    private Context icontext;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    public TeacherCourseAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        icontext = context;
    }
    public TeacherCourseAdapter(Context context, List<CourseInfo> ships) {

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
    public View getView( int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView=View.inflate(icontext, R.layout.layout_teacher_course, null);
            holder = new ViewHolder(convertView);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        imageLoader.displayImage(Constants.IMG_URL+items.get(position).getCourseImage(),holder.course_img, ApplicationEx.home_course_options);

        holder.tv_teacherName.setText(items.get(position).getTeacherName());
        holder.tv_describe.setText(items.get(position).getTypeName());
        holder.tv_course.setText(items.get(position).getCourseName());
//        String teachCourse = items.get(position).getTeachCourse();
        holder.tv_people.setText(items.get(position).getStudyNum()+"");
        holder.tv_comment.setText(items.get(position).getCourseClickNum()+"");

//        holder.tv_course.setText(Html.fromHtml("精品课程:<font color='#616161'>"+teachCourse+"</font"));
//        holder.tv_course.setText(teachCourse);
        return convertView;
    }

    class ViewHolder {
        private	View convertView;
        private ImageView course_img ;
        private	TextView  tv_teacherName ;
        private	TextView  tv_describe ;
        private	TextView  tv_course ;
        private	TextView  tv_people ;
        private	TextView  tv_comment ;


        ViewHolder(View convertView) {
            this.convertView=convertView;
            this. course_img = (ImageView) convertView.findViewById(R.id.course_img);
            this. tv_teacherName = (TextView) convertView.findViewById(R.id.tv_teacherName);
            this. tv_describe = (TextView) convertView.findViewById(R.id.tv_describe);
            this. tv_course = (TextView) convertView.findViewById(R.id.tv_course);
            this. tv_people = (TextView) convertView.findViewById(R.id.tv_people);
            this. tv_comment = (TextView) convertView.findViewById(R.id.tv_comment);
            convertView.setTag(this);
        }
    }
}
