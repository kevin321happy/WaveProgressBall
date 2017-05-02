package com.fips.huashun.ui.adapter;

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
import com.fips.huashun.modle.bean.TeacherCourse;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：讲师列表适配器
 * Created by Administrator on 2016/8/16.
 *
 * @author 张柳 创建时间：2016年8月16日16:25:22
 */
public class LecturerResultAdapter extends BaseAdapter
{
    private List<TeacherCourse> items = new ArrayList<TeacherCourse>();
    private Context icontext;
    private LayoutInflater mInflater;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public LecturerResultAdapter(Context context)
    {
        this.icontext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public LecturerResultAdapter(Context context, List<TeacherCourse> ships)
    {
        this.items = ships;
        this.icontext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public void addItem(TeacherCourse it)
    {
        items.add(it);
    }

    public void removeItems()
    {
        items.clear();
    }

    public void setListItems(List<TeacherCourse> lit)
    {
        items = lit;
    }

    public boolean areAllItemsSelectable()
    {
        return false;
    }

    public List<TeacherCourse> getAllListDate() {
        return this.items;
    }
    @Override
    public int getCount()
    {
        if (null == items)
        {
            return 0;
        }
        return items.size();
    }

    @Override
    public TeacherCourse getItem(int position)
    {
        return items.get(position);
    }

    @Override
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
            convertView = View.inflate(icontext, R.layout.lecturer_result_item, null);
            holder = new ViewHolder(convertView);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        imageLoader.displayImage(Constants.IMG_URL+items.get(position).getTeacherPhoto(), holder.headImg, ApplicationEx.home_teacher_options);
        holder.nameTv.setText(items.get(position).getTeacherName());
        holder.riokinTv.setText( items.get(position).getRiokin() + "人赞过");
        holder.fieldTv.setText("擅长领域：" + items.get(position).getField());
        holder.contentTv.setText("服务内容：" + items.get(position).getContent());
        holder.courseTv.setText("主讲：" + items.get(position).getCourses());
        return convertView;
    }

    class ViewHolder
    {
        private View convertView;
        private ImageView headImg;
        private TextView nameTv, riokinTv, fieldTv, contentTv, courseTv;

        ViewHolder(View convertView)
        {
            this.convertView = convertView;
            this.headImg = (ImageView) convertView.findViewById(R.id.iv_lecturer_head);
            this.nameTv = (TextView) convertView.findViewById(R.id.tv_lecturer_name);
            this.riokinTv = (TextView) convertView.findViewById(R.id.tv_lecturer_riokin);
            this.fieldTv = (TextView) convertView.findViewById(R.id.tv_lecturer_field);
            this.contentTv = (TextView) convertView.findViewById(R.id.tv_lecturer_content);
            this.courseTv = (TextView) convertView.findViewById(R.id.tv_lecturer_course);
            convertView.setTag(this);
        }
    }
}
