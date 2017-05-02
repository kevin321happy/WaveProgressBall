package com.fips.huashun.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fips.huashun.R;
import com.fips.huashun.modle.bean.ExamTask;
import com.fips.huashun.ui.activity.WebviewActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/22.
 */
public class ExamTaskAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<ExamTask> items = new ArrayList<ExamTask>();
    // 1 通过的考试列表展示 2 需要通过的考试列表展示
    private int typeIndex;

    private Context icontext;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public ExamTaskAdapter(Context context) {
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.icontext = context;
    }

    public ExamTaskAdapter(Context context, List<ExamTask> ships, int index) {
        this.items = ships;
        this.icontext = context;
    }

    public void setTypeExam(int typeIndex) {
        this.typeIndex = typeIndex;
    }

    public void addItem(ExamTask it) {
        items.add(it);
    }

    public void removeItems() {
        items.clear();
    }

    public void setListItems(List<ExamTask> list) {
        items = list;
    }

    public int getCount() {
        return items == null ? 0 : items.size();
    }

    public ExamTask getItem(int position) {
        return items.get(position);
    }

    public boolean areAllItemsSelectable() {
        return false;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(icontext, R.layout.examtask_item, null);
            holder = new ViewHolder();
            holder.requireExamLayout = (LinearLayout) convertView.findViewById(R.id.ll_examtask_require);
            holder.passExamLayout = (LinearLayout) convertView.findViewById(R.id.ll_examtask_pass);
            holder.nameTv = (TextView) convertView.findViewById(R.id.tv_exam_name);
            holder.passNameTv = (TextView) convertView.findViewById(R.id.tv_pass_exam_name);
            holder.timeTv = (TextView) convertView.findViewById(R.id.tv_exam_time);
            holder.passTimeTv = (TextView) convertView.findViewById(R.id.tv_pass_exam_time);
            holder.useTimeTv = (TextView) convertView.findViewById(R.id.tv_pass_exam_usetime);
            holder.rankTv = (TextView) convertView.findViewById(R.id.tv_pass_exam_rank);
            holder.trueExamTv = (TextView) convertView.findViewById(R.id.tv_true_exam_num);
            holder.errorTv = (TextView) convertView.findViewById(R.id.tv_error_exam_num);
            holder.countTv = (TextView) convertView.findViewById(R.id.tv_count_num);
            holder.goExamBtn = (Button) convertView.findViewById(R.id.btn_goexam);
            holder.showExamIv = (ImageView) convertView.findViewById(R.id.iv_exam_head);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (typeIndex == 1) {
            holder.requireExamLayout.setVisibility(View.GONE);
            holder.passExamLayout.setVisibility(View.VISIBLE);
            holder.passNameTv.setText(items.get(position).getTestname());
            holder.passTimeTv.setText("考试时间:" + items.get(position).getTestime());
            holder.useTimeTv.setText("用时:" + items.get(position).getShowtime());
            holder.rankTv.setText("排名:第" + items.get(position).getOrdernum() + "名");
            String trueCounts = items.get(position).getIsright();
            String wrongCounts = items.get(position).getIswrong();
            holder.trueExamTv.setText("答对:" + trueCounts + "题");
            holder.errorTv.setText("答错:" + items.get(position).getIswrong() + "题");
            holder.countTv.setText(items.get(position).getScore());

            //设置默认值
            if (trueCounts.equals("")) {
                trueCounts = "0";
            }
            if (wrongCounts.equals("")) {
                wrongCounts = "0";
            }
             // 总题数
            int examCounts = Integer.parseInt(trueCounts) + Integer.parseInt(wrongCounts);
            Double examRate = Double.parseDouble(trueCounts) / examCounts;
            if (examRate < 0.8) {
                holder.showExamIv.setImageResource(R.drawable.exam_sort);
            } else if (0.8 <= examRate && examRate < 0.9) {
                holder.showExamIv.setImageResource(R.drawable.exam_good);
            } else {
                holder.showExamIv.setImageResource(R.drawable.exam_outstanding);
            }
        } else {
            holder.requireExamLayout.setVisibility(View.VISIBLE);
            holder.passExamLayout.setVisibility(View.GONE);
            holder.nameTv.setText(items.get(position).getTestname());
            String testime = items.get(position).getTestime();
            if (TextUtils.isEmpty(testime)) {
                holder.timeTv.setText("考试时间:不限");
            } else {
                holder.timeTv.setText("考试时间:" + items.get(position).getTestime() + " 前");
            }
            final String paperid = items.get(position).getPaperid();
            holder.goExamBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(icontext, WebviewActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("activityId", paperid);
                    intent.putExtra("key", 11);//企业我的课程考试
                    icontext.startActivity(intent);
                }
            });
        }
        return convertView;
    }

    class ViewHolder {
        private ImageView showExamIv;
        private Button goExamBtn;
        private TextView nameTv, passNameTv, timeTv, passTimeTv, useTimeTv, rankTv, trueExamTv, errorTv, countTv;
        private LinearLayout requireExamLayout, passExamLayout;

    }
}
