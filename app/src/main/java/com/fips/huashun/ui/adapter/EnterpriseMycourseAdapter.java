package com.fips.huashun.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.fips.huashun.ApplicationEx;
import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.modle.bean.EnterMyCourseInfo.EnterMyCourse;
import com.fips.huashun.ui.activity.WebviewActivity;
import com.fips.huashun.ui.utils.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能：企业课程适配器
 * Created by Administrator on 2016/9/13.
 * @author 张柳 时间：2016年9月13日14:07:34
 */
public class EnterpriseMycourseAdapter extends BaseAdapter
{
    private LayoutInflater mInflater;
    private List<EnterMyCourse> items = new ArrayList<EnterMyCourse>();
    private Context icontext;
    private String type ;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public EnterpriseMycourseAdapter(Context context)
    {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        icontext = context;
    }

    public EnterpriseMycourseAdapter(Context context, List<EnterMyCourse> ships)
    {
        this.icontext = context;
        this.items = ships;

    }
    public EnterpriseMycourseAdapter(Context context, String type)
    {
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.icontext = context;
        this.type = type;
    }
    public void setType(String type)
    {
        this.type = type;
    }
    public void addItem(EnterMyCourse it)
    {
        items.add(it);
    }

    public void removeItems()
    {
        items.clear();
    }

    public void setListItems(List<EnterMyCourse> lit)
    {
        items = lit;
    }

    public int getCount()
    {
        return items == null ? 0 : items.size();
    }

    public List<EnterMyCourse> getAllListDate()
    {
        return this.items;
    }

    public EnterMyCourse getItem(int position)
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
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null;
        if (convertView == null)
        {
            convertView = View.inflate(icontext, R.layout.ent_mycourse_item, null);
            holder = new ViewHolder();
            holder.courseImg = (ImageView) convertView.findViewById(R.id.iv_enterprise_course_item_img);
            holder.courseNameTv = (TextView) convertView.findViewById(R.id.tv_enterprise_course_item_title);
            holder.courseLabelOneTv = (TextView) convertView.findViewById(R.id.tv_enterprise_course_item_label_one);
            holder.courseLabelOneTv.setVisibility(View.GONE);
            holder.courseLabelTwoTv = (TextView) convertView.findViewById(R.id.tv_enterprise_course_item_label_two);
            holder.courseLabelTwoTv.setVisibility(View.GONE);
            holder.courseIntroduceTv = (TextView) convertView.findViewById(R.id.tv_enterprise_course_item_introduce);
            holder.courseLecturerTv = (TextView) convertView.findViewById(R.id.tv_enterprise_course_item_lecturer);
            holder.isFinishTv = (TextView) convertView.findViewById(R.id.tv_enterprise_course_item_finish);
            holder.examTv = (TextView) convertView.findViewById(R.id.tv_enterprise_course_item_exam);
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        imageLoader.displayImage(Constants.IMG_URL+items.get(position).getCourseImage(),holder.courseImg, ApplicationEx.all_course_options);
        holder.courseImg.setScaleType(ImageView.ScaleType.FIT_XY);
        holder.courseNameTv.setText(items.get(position).getCourseName());
        final String result=items.get(position).getResult()+"";
        if("0".equals(result))
        {
            holder.isFinishTv.setText("未完成");
        } else
        {
            holder.isFinishTv.setText("已完成");
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
        holder.courseIntroduceTv.setText("简介："+items.get(position).getCourseInfo());
        holder.courseLecturerTv.setText("讲师："+items.get(position).getTeacherName());
        final String paperid = items.get(position).getPaperid();
        Log.e("type",type);
        holder.examTv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if ("2".equals(type)){
                    Intent intent = new Intent(icontext, WebviewActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("activityId", paperid);
                    intent.putExtra("sessoinid", items.get(position).getCourseId());
                    intent.putExtra("key", 11);//企业我的课程考试
                    icontext.startActivity(intent);
                }else {
                    if("0".equals(result))
                    {
                        ToastUtil.getInstant().show("请先完成课程学习!");
                    } else
                    {
                        if(TextUtils.isEmpty(paperid)|| paperid.equals("0"))
                        {
                            ToastUtil.getInstant().show("暂无考试试卷，请耐心等待!");
                        } else
                        {
                            Intent intent = new Intent(icontext, WebviewActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("activityId", paperid);
                            intent.putExtra("sessoinid", items.get(position).getCourseId());
                            intent.putExtra("key", 11);//企业我的课程考试
                            icontext.startActivity(intent);
                        }
                    }
                }

            }
        });
        return convertView;
    }

    class ViewHolder
    {
        // 课程图片
        private ImageView courseImg;
        // 课程名，课程标签(0,1)，课程介绍，课程讲师
        private TextView courseNameTv,courseLabelOneTv,courseLabelTwoTv,
                courseIntroduceTv,courseLecturerTv,isFinishTv,examTv;
    }
}
