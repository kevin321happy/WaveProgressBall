package com.fips.huashun.ui.adapter;

/**
 * Created by Administrator on 2016/3/14.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.fips.huashun.R;
import com.fips.huashun.db.dao.CourseNameDao;
import com.fips.huashun.db.dao.SectionDownloadDao;
import com.fips.huashun.modle.bean.CourseMuluTitle;
import com.fips.huashun.modle.bean.CouseMuluInfo;
import com.fips.huashun.modle.dbbean.CourseSectionEntity;
import com.fips.huashun.modle.event.SectionDownloadEvent;
import com.fips.huashun.ui.utils.AlertDialogUtils;
import com.fips.huashun.ui.utils.AlertDialogUtils.DialogClickInter;
import com.fips.huashun.ui.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseMuluAdapter extends BaseExpandableListAdapter {

  private LayoutInflater mInflater;
  List<CourseMuluTitle> mGroup = new ArrayList<>();
  Map<String, List<CouseMuluInfo>> mMap = new HashMap<>();
  String[] order = new String[]{"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二", "十三"};

  private Context mContext;
  private ImageLoader imageLoader = ImageLoader.getInstance();
  private boolean isDel = false;
  private CourseNameDao mCourseNameDao;
  private SectionDownloadDao mSectionDownloadDao;
  private OnSectionItemClickListener mOnSectionItemClickListener;

  public void setOnSectionItemClickListener(
      OnSectionItemClickListener onSectionItemClickListener) {
    mOnSectionItemClickListener = onSectionItemClickListener;
  }

  public interface OnSectionItemClickListener {

    void onSectionItemClick(int groupPosition, int childPosition);//点击章节条目
  }


  public CourseMuluAdapter(Context context) {
    getDaos(context);
    mContext = context;
  }

  //获取操作数据库的Dao对象
  public void getDaos(Context context) {
    if (mCourseNameDao == null) {
      mCourseNameDao = new CourseNameDao(context);
    }
    if (mSectionDownloadDao == null) {
      mSectionDownloadDao = new SectionDownloadDao(context);
    }
  }

  @Override
  public int getGroupCount() {
    return mGroup.size();
  }

  @Override
  public int getChildrenCount(int groupPosition) {
    String key = mGroup.get(groupPosition).getTitlename();
    List<CouseMuluInfo> sectionInfos = mMap.get(key);
    return sectionInfos.size();
  }

  @Override
  public Object getGroup(int groupPosition) {
    return mGroup.get(groupPosition);
  }

  @Override
  public Object getChild(int groupPosition, int childPosition) {
    String key = mGroup.get(groupPosition).getTitlename();
    List<CouseMuluInfo> sectionInfos = mMap.get(key);
    return sectionInfos.get(childPosition);
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

  @Override
  public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
      ViewGroup parent) {
    //父控件的视图
    CourseMuluTitle courseMuluTitle = mGroup.get(groupPosition);
    if (convertView == null) {
      convertView = View.inflate(parent.getContext(), R.layout.mulu_title_item, null);
    }
    TextView tv_title = (TextView) convertView.findViewById(R.id.tv_mulu_title);
    ImageView iv_arrow = (ImageView) convertView.findViewById(R.id.iv_mulu_arrow);
    String titleOrder = getTitleOrder(groupPosition);
    tv_title.setText(titleOrder +"、"+ courseMuluTitle.getTitlename() + "");
    if (isExpanded) {
      iv_arrow.setImageResource(R.drawable.course_result_fan);
    } else {
      iv_arrow.setImageResource(R.drawable.arrow_open);
    }
    return convertView;
  }

  @Override
  public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild,
      View convertView, ViewGroup parent) {
    String key = mGroup.get(groupPosition).getTitlename();
    List<CouseMuluInfo> sectionInfos = mMap.get(key);
    //章节信息
    final CouseMuluInfo sectionInfo = sectionInfos.get(childPosition);
    if (convertView == null) {
      convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_item, null);
    }
    TextView tv_course_section_type = (TextView) convertView
        .findViewById(R.id.tv_course_section_type);
    TextView tv_course_section_name = (TextView) convertView
        .findViewById(R.id.tv_course_section_name);
    View line = convertView.findViewById(R.id.course_section_line);
    TextView tv_download = (TextView) convertView.findViewById(R.id.tv_course_section_down);
    ImageView iv_download = (ImageView) convertView.findViewById(R.id.iv_course_section_down);
    tv_course_section_name.setText(sectionInfo.getSectionname() + "");
    String type = sectionInfo.getSessiontype();
    if ("1".equals(type)) {
      tv_course_section_type.setText("图文");
    } else if ("2".equals(type)) {
      tv_course_section_type.setText("视频");
    } else if ("3".equals(type)) {
      tv_course_section_type.setText("PDF");
    }
    if (isLastChild) {
      line.setVisibility(View.GONE);
    } else {
      line.setVisibility(View.VISIBLE);
    }
    //查询当前章节的状态状态
    //如果是企业课程,且为视频或pdf课程
    if (!sectionInfo.getEid().equals("-1") && !sectionInfo.getSessiontype().equals("1")) {
      //设置下载按钮可见
      tv_download.setVisibility(View.VISIBLE);
      //设置下载按钮的状态
      CourseSectionEntity sectionEntity = mSectionDownloadDao
          .querySectionBySectionId(sectionInfo.getSessonid());
      if (sectionEntity != null) {
        int state = sectionEntity.getState();
        changeState(state, tv_download, iv_download);
      } else {
        tv_download.setEnabled(true);
        //数据库中没有该数据,可能被删除
        tv_download.setVisibility(View.VISIBLE);
      }
    } else {
      tv_download.setVisibility(View.GONE);
    }
    //点击下载的监听
    iv_download.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (!Utils.isWifi(mContext)) {
          AlertDialogUtils.showTowBtnDialog((Activity) mContext, "当前非wifi环境,确定要下载吗？", "取消", "确定",
              new DialogClickInter() {
                @Override
                public void leftClick(AlertDialog dialog) {
                  dialog.dismiss();
                }

                @Override
                public void rightClick(AlertDialog dialog) {
                  dialog.dismiss();
                  String url =
                      sectionInfo.getSessiontype().equals("2") ? sectionInfo.getStanquality()
                          : sectionInfo.getCoursedoc();
                  SectionDownloadEvent sectionDownloadEvent = new SectionDownloadEvent();
                  sectionDownloadEvent.setSectionUrl(url);
                  sectionDownloadEvent.setSectionId(sectionInfo.getSessonid());
                  EventBus.getDefault().post(sectionDownloadEvent);
                }
              });
        } else {
          String url = sectionInfo.getSessiontype().equals("2") ? sectionInfo.getStanquality()
              : sectionInfo.getCoursedoc();
          SectionDownloadEvent sectionDownloadEvent = new SectionDownloadEvent();
          sectionDownloadEvent.setSectionUrl(url);
          sectionDownloadEvent.setSectionId(sectionInfo.getSessonid());
          EventBus.getDefault().post(sectionDownloadEvent);
        }
      }
    });
    //当点击条目
    convertView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (mOnSectionItemClickListener != null) {
          mOnSectionItemClickListener.onSectionItemClick(groupPosition, childPosition);
        }
      }
    });
    return convertView;
  }

  private void changeState(int state, TextView tv_download, ImageView iv_download) {
    switch (state) {
      case 0:
        iv_download.setEnabled(true);
        iv_download.setVisibility(View.VISIBLE);
        tv_download.setVisibility(View.GONE);
        break;
      case 1:
        iv_download.setVisibility(View.GONE);
        tv_download.setVisibility(View.VISIBLE);
        tv_download.setText("下载中");
        tv_download.setTextColor(mContext.getResources().getColor(R.color.bg_hui));
        break;
      case 2:
        iv_download.setVisibility(View.GONE);
        tv_download.setVisibility(View.VISIBLE);
        tv_download.setText("已下载");
        tv_download.setTextColor(mContext.getResources().getColor(R.color.enterprise_act__text));
        break;
      case -1:
        iv_download.setVisibility(View.GONE);
        tv_download.setVisibility(View.VISIBLE);
        tv_download.setText("下载中");
        tv_download.setTextColor(mContext.getResources().getColor(R.color.bg_hui));
        break;
      case 3:
        iv_download.setVisibility(View.GONE);
        tv_download.setVisibility(View.VISIBLE);
        tv_download.setText("暂停");
        tv_download.setTextColor(mContext.getResources().getColor(R.color.enterprise_act__text));
        break;
      default:
        iv_download.setEnabled(true);
        iv_download.setVisibility(View.VISIBLE);
        tv_download.setVisibility(View.GONE);
        break;
    }
  }

  @Override
  public boolean isChildSelectable(int groupPosition, int childPosition) {
    return true;
  }

  public void setData(List<CourseMuluTitle> group, Map<String, List<CouseMuluInfo>> map) {
    this.mGroup = group;
    this.mMap = map;
  }

  private String getTitleOrder(int groupPosition) {
    return order[groupPosition];
  }
}

