package com.fips.huashun.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.fips.huashun.R;
import com.fips.huashun.modle.dbbean.CourseSectionEntity;
import com.fips.huashun.modle.event.ChangeDownLoadEvent;
import com.fips.huashun.ui.utils.Utils;
import com.fips.huashun.widgets.CustomDashedLineView;
import de.greenrobot.event.EventBus;
import java.util.List;
import java.util.Map;

/**
 * description: 课程下载的适配器
 * autour: Kevin
 * company:锦绣氘(武汉)科技有限公司
 * date: 2017/4/27 16:53
 * update: 2017/4/27
 * version: 1.21
 * 站在峰顶 看世界
 * 落在谷底 思人生
 */
public class HaveDownloadedAdapter extends BaseExpandableListAdapter {

  private List<String> mGroup;
  private Map<String, List<CourseSectionEntity>> mMap;

  private OnChildItemClickListener mOnChildItemClickListener;

  public OnChildItemClickListener getOnChildItemClickListener() {
    return mOnChildItemClickListener;
  }

  public void setOnChildItemClickListener(
      OnChildItemClickListener onChildItemClickListener) {
    mOnChildItemClickListener = onChildItemClickListener;
  }

  @Override
  public int getGroupCount() {
    return mGroup == null ? 0 : mGroup.size();
  }

  @Override
  public int getChildrenCount(int groupPosition) {
//父标题下孩子的数量
    String key = mGroup.get(groupPosition);
    List<CourseSectionEntity> sectionEntities = mMap.get(key);
    return sectionEntities.size();
  }

  @Override
  public Object getGroup(int groupPosition) {
    return mGroup.get(groupPosition);//当前父标题下面的值
  }

  @Override
  public Object getChild(int groupPosition, int childPosition) {
    String key = mGroup.get(groupPosition);
    List<CourseSectionEntity> sectionEntities = mMap.get(key);
    return sectionEntities.get(childPosition);
  }

  @Override
  public long getGroupId(int groupPosition) {
    return groupPosition;
  }

  @Override
  public long getChildId(int groupPosition, int childPosition) {
    return childPosition;
  }

  @Override
  public boolean hasStableIds() {
    return true;
  }

  //显示父标题
  @Override
  public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
      ViewGroup parent) {
    if (convertView == null) {
      convertView = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.havedown_course_parentitem, null);
    }
    String title = mGroup.get(groupPosition);
    TextView tv_title = (TextView) convertView.findViewById(R.id.tv_parent_title);
    ImageView iv_open = (ImageView) convertView.findViewById(R.id.iv_parent_open);
    tv_title.setText(title + "");
    if (isExpanded) {
      iv_open.setImageResource(R.drawable.course_result_fan);
    } else {
      iv_open.setImageResource(R.drawable.arrow_open);
    }
    return convertView;
  }

  @Override
  public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
      View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.havedown_course_childitem, null);
    }
    String key = mGroup.get(groupPosition);
    final List<CourseSectionEntity> sectionEntities = mMap.get(key);
    final CourseSectionEntity sectionEntity = sectionEntities.get(childPosition);
    TextView tv_name = (TextView) convertView.findViewById(R.id.tv_child_name);
    TextView tv_size = (TextView) convertView.findViewById(R.id.tv_child_size);
    TextView tv_state = (TextView) convertView.findViewById(R.id.tv_child_state);
    ImageView iv_delete = (ImageView) convertView.findViewById(R.id.iv_child_delete);
    Button bt_study = (Button) convertView.findViewById(R.id.bt_child_study);

    CustomDashedLineView cl_line = (CustomDashedLineView) convertView.findViewById(R.id.cl_line);
    if (isLastChild) {
      cl_line.setVisibility(View.GONE);
    }

    //绑定数据
    tv_name.setText(sectionEntity.getSectionname());
    final int state = sectionEntity.getState();
    if (state == 2) {
      tv_state.setText("已完成");
      tv_size.setText(Utils.FormentFileSize(Long.parseLong(sectionEntity.getFileSize())));
    } else if (state == 1) {
      tv_state.setText("下载中");
      tv_size.setText("当前已下载：" + sectionEntity.getProgress());
    } else if (state == -1) {
      tv_state.setText("下载失败");
      tv_size.setText("当前已下载：" + sectionEntity.getProgress());
    }

    //点击删除
    iv_delete.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        //如果没有下载完成,点击删除即是取消下载任务
        if (state != 2) {
          EventBus.getDefault().post(new ChangeDownLoadEvent(sectionEntity.getSectionId()));
        }
        if (mOnChildItemClickListener != null) {
          mOnChildItemClickListener
              .onDelete(sectionEntity.getLocalpath(), sectionEntity.getSectionId());
        }
      }
    });
    //点击去学习
    convertView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (mOnChildItemClickListener != null && state == 2) {
          mOnChildItemClickListener
              .onStudy(sectionEntity.getLocalpath(), sectionEntity.getSectionId(),
                  sectionEntity.getCourseid(), sectionEntity.getFileType());
        }
      }
    });

    return convertView;
  }

  @Override
  public boolean isChildSelectable(int groupPosition, int childPosition) {
    return true;
  }

  public void setData(List<String> group, Map<String, List<CourseSectionEntity>> map) {
    this.mGroup = group;
    this.mMap = map;
  }

  public interface OnChildItemClickListener {

    //点击去学习
    void onStudy(String localpath, String id, String sectionId, String courseid);

    //点击删除
    void onDelete(String localpath, String sectionId);
  }
}
