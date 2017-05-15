package com.fips.huashun.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.fips.huashun.R;
import com.fips.huashun.ui.activity.EntMyCourseActivity;

/**
 * description: 课程分类的Adapter
 * autour: Kevin
 * company:锦绣氘(武汉)科技有限公司
 * date: 2017/5/15 13:48
 * update: 2017/5/15
 * version: 1.21
 * 站在峰顶 看世界
 * 落在谷底 思人生
 */
public class CourseCategoryAdapter extends BaseAdapter {

  private Context context;
  private String[] strings;
  public static int mPosition;

  public CourseCategoryAdapter(Context context, String[] strs) {
    this.context=context;
    this.strings = strs;
  }

  @Override
  public int getCount() {
    return strings.length==0?0:strings.length;
  }

  @Override
  public Object getItem(int position) {
    return strings[position];
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
      convertView= LayoutInflater.from(context).inflate(R.layout.course_category_item,null);
    TextView tv_category_name = (TextView) convertView.findViewById(R.id.tv_category_name);
    mPosition=position;
    tv_category_name.setText(strings[position]);
    if (position== EntMyCourseActivity.mPosition){
      convertView.setBackgroundResource(R.drawable.bg_course_category);
    }else {
      convertView.setBackgroundColor(Color.parseColor("#f4f4f4"));
    }
    return convertView;
  }
}
