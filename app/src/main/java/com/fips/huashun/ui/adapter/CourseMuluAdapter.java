package com.fips.huashun.ui.adapter;

/**
 * Created by Administrator on 2016/3/14.
 */

import android.app.Activity;
<<<<<<< HEAD
=======
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
>>>>>>> f8c163e9f9b16c6f8465981156b159495b4df8c8
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
<<<<<<< HEAD
import android.widget.BaseExpandableListAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
=======
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
>>>>>>> f8c163e9f9b16c6f8465981156b159495b4df8c8
import android.widget.TextView;
import com.fips.huashun.R;
import com.fips.huashun.db.dao.CourseNameDao;
import com.fips.huashun.db.dao.SectionDownloadDao;
<<<<<<< HEAD
import com.fips.huashun.modle.bean.CourseMuluTitle;
=======
>>>>>>> f8c163e9f9b16c6f8465981156b159495b4df8c8
import com.fips.huashun.modle.bean.CouseMuluInfo;
import com.fips.huashun.modle.dbbean.CourseSectionEntity;
import com.fips.huashun.modle.event.SectionDownloadEvent;
import com.fips.huashun.ui.utils.AlertDialogUtils;
import com.fips.huashun.ui.utils.AlertDialogUtils.DialogClickInter;
import com.fips.huashun.ui.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
<<<<<<< HEAD
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseMuluAdapter extends BaseExpandableListAdapter {
  private LayoutInflater mInflater;
  List<CourseMuluTitle> mGroup = new ArrayList<>();
  Map<String, List<CouseMuluInfo>> mMap = new HashMap<>();
  String[] order = new String[]{"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二", "十三"};

  private Context mContext;
=======
import java.util.List;

public class CourseMuluAdapter extends BaseAdapter {

  private LayoutInflater mInflater;
  private List<CouseMuluInfo> items = new ArrayList<CouseMuluInfo>();


  private Context icontext;
>>>>>>> f8c163e9f9b16c6f8465981156b159495b4df8c8
  private ImageLoader imageLoader = ImageLoader.getInstance();
  private boolean isDel = false;
  private CourseNameDao mCourseNameDao;
  private SectionDownloadDao mSectionDownloadDao;
<<<<<<< HEAD
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
=======
//  private boolean isWifi = true;


  public interface Callback {

    void click(View v, int position);
  }

  private Callback mCallback;

  public CourseMuluAdapter(Context context, Callback callback) {
    getDaos(context);
    mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    icontext = context;
    mCallback = callback;
  }

  public boolean isDel() {
    return isDel;
  }

  public void setDel(boolean isDel) {
    this.isDel = isDel;
  }

  public CourseMuluAdapter(Context context) {
    mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    icontext = context;
>>>>>>> f8c163e9f9b16c6f8465981156b159495b4df8c8
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

<<<<<<< HEAD
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
      iv_arrow.setImageResource(R.drawable.arrow_down);
    } else {
      iv_arrow.setImageResource(R.drawable.arrow_right);
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
    FrameLayout frameLayout = (FrameLayout) convertView.findViewById(R.id.fl_download);
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
     frameLayout.setVisibility(View.VISIBLE);
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
      frameLayout.setVisibility(View.GONE);
    }
    //点击下载的监听
    iv_download.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (!Utils.isWifi(mContext)) {
          AlertDialogUtils.showTowBtnDialog((Activity) mContext, "当前非wifi环境,确定要下载吗？", "取消", "确定",
=======
  public CourseMuluAdapter(Context context, List<CouseMuluInfo> ships) {
    this.icontext = context;
    this.items = ships;
//    isWifi = Utils.isWifi(icontext);

  }

  public List<CouseMuluInfo> getAllListDate() {
    return this.items;
  }

  public void addItem(CouseMuluInfo it) {
    items.add(it);
  }

  public void removeItems() {
    items.clear();
  }

  public void setListItems(List<CouseMuluInfo> lit) {
    items = lit;
  }

  @Override
  public int getCount() {
    return items == null ? 0 : items.size();
  }

  @Override
  public Object getItem(int position) {
    return items.get(position);
  }

  public boolean areAllItemsSelectable() {
    return false;
  }

  public long getItemId(int position) {
    return position;
  }


  @Override
  public View getView(final int position, View convertView, ViewGroup parent) {
    final CouseMuluInfo couseMuluInfo = items.get(position);
    ViewHolder holder = null;
    if (convertView == null) {
      convertView = View.inflate(icontext, R.layout.mulu_items, null);
      holder = new ViewHolder(convertView);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }
    if (couseMuluInfo.getSessiontype().equals("1")) {
      holder.tv_courseName.setText("【图文】" + couseMuluInfo.getSectionname());
      holder.iv_begin.setImageResource(R.drawable.mulu_document);
    } else if (couseMuluInfo.getSessiontype().equals("2")) {
      holder.tv_courseName.setText("【视频】" + couseMuluInfo.getSectionname());
      holder.iv_begin.setImageResource(R.drawable.video_begin);
    } else if (couseMuluInfo.getSessiontype().equals("3")) {
      holder.tv_courseName.setText("【PDF】" + couseMuluInfo.getSectionname());
      holder.iv_begin.setImageResource(R.drawable.mulu_document);
    }
    //如果是企业课程,且为视频或pdf课程
    if (!couseMuluInfo.getEid().equals("-1") && !couseMuluInfo.getSessiontype().equals("1")) {
      //设置下载按钮可见
      holder.iv_download.setVisibility(View.VISIBLE);
      //设置下载按钮的状态
      CourseSectionEntity sectionEntity = mSectionDownloadDao
          .querySectionBySectionId(couseMuluInfo.getSessonid());
      if (sectionEntity != null) {
        int state = sectionEntity.getState();
        changeState(state, holder.iv_download);
      }
    } else {
      holder.iv_download.setVisibility(View.GONE);
    }
    if (couseMuluInfo.isclik()) {
      holder.ll_out.setVisibility(View.VISIBLE);
      holder.iv_down.setImageResource(R.drawable.icon_up);
    } else {
      holder.ll_out.setVisibility(View.GONE);
      holder.iv_down.setImageResource(R.drawable.course_result_fan);
    }
    holder.ll_isGone.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        v.setTag(1);
        mCallback.click(v, position);
      }
    });
    holder.ll_out.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //点击开始播放视频
        v.setTag(2);
        mCallback.click(v, position);
      }
    });
    //点击了下载视频
    holder.iv_download.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (!Utils.isWifi(icontext)) {
          AlertDialogUtils.showTowBtnDialog((Activity) icontext, "当前非wifi环境,确定要下载吗？", "取消", "确定",
>>>>>>> f8c163e9f9b16c6f8465981156b159495b4df8c8
              new DialogClickInter() {
                @Override
                public void leftClick(AlertDialog dialog) {
                  dialog.dismiss();
                }
<<<<<<< HEAD

=======
>>>>>>> f8c163e9f9b16c6f8465981156b159495b4df8c8
                @Override
                public void rightClick(AlertDialog dialog) {
                  dialog.dismiss();
                  String url =
<<<<<<< HEAD
                      sectionInfo.getSessiontype().equals("2") ? sectionInfo.getStanquality()
                          : sectionInfo.getCoursedoc();
                  SectionDownloadEvent sectionDownloadEvent = new SectionDownloadEvent();
                  sectionDownloadEvent.setSectionUrl(url);
                  sectionDownloadEvent.setSectionId(sectionInfo.getSessonid());
=======
                      couseMuluInfo.getSessiontype().equals("2") ? couseMuluInfo.getStanquality()
                          : couseMuluInfo.getCoursedoc();
                  SectionDownloadEvent sectionDownloadEvent = new SectionDownloadEvent();
                  sectionDownloadEvent.setSectionUrl(url);
                  sectionDownloadEvent.setSectionId(couseMuluInfo.getSessonid());
>>>>>>> f8c163e9f9b16c6f8465981156b159495b4df8c8
                  EventBus.getDefault().post(sectionDownloadEvent);
                }
              });
        } else {
<<<<<<< HEAD
          String url = sectionInfo.getSessiontype().equals("2") ? sectionInfo.getStanquality()
              : sectionInfo.getCoursedoc();
          SectionDownloadEvent sectionDownloadEvent = new SectionDownloadEvent();
          sectionDownloadEvent.setSectionUrl(url);
          sectionDownloadEvent.setSectionId(sectionInfo.getSessonid());
=======
          String url = couseMuluInfo.getSessiontype().equals("2") ? couseMuluInfo.getStanquality()
              : couseMuluInfo.getCoursedoc();
          SectionDownloadEvent sectionDownloadEvent = new SectionDownloadEvent();
          sectionDownloadEvent.setSectionUrl(url);
          sectionDownloadEvent.setSectionId(couseMuluInfo.getSessonid());
>>>>>>> f8c163e9f9b16c6f8465981156b159495b4df8c8
          EventBus.getDefault().post(sectionDownloadEvent);
        }
      }
    });
<<<<<<< HEAD
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
      case -2:
        iv_download.setVisibility(View.GONE);
        tv_download.setVisibility(View.VISIBLE);
        tv_download.setText("等待中");
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
=======
    holder.tv_time.setText(items.get(position).getSessiontime() + "分钟");
    return convertView;
  }

  private void changeState(int state, RelativeLayout iv_download) {
    ImageView iv_icon = (ImageView) iv_download.getChildAt(0);
    TextView tv_down = (TextView) iv_download.getChildAt(1);
    switch (state) {
      case 0:
        iv_icon.setVisibility(View.VISIBLE);
        tv_down.setVisibility(View.GONE);
        break;
      case 1:
        iv_icon.setVisibility(View.GONE);
        tv_down.setVisibility(View.VISIBLE);
        iv_download.setEnabled(false);
        break;
      case 2:
        iv_icon.setVisibility(View.VISIBLE);
        iv_icon.setImageResource(R.drawable.finish_d);
        tv_down.setVisibility(View.GONE);
        iv_download.setEnabled(false);
        break;
      case -1:
        iv_icon.setVisibility(View.VISIBLE);
        iv_icon.setImageResource(R.drawable.again_d);
        tv_down.setVisibility(View.GONE);
        break;
      default:
        iv_icon.setVisibility(View.VISIBLE);
        tv_down.setVisibility(View.GONE);
>>>>>>> f8c163e9f9b16c6f8465981156b159495b4df8c8
        break;
    }
  }

<<<<<<< HEAD
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

=======
  /**
   * 判断服务是否开启
   */
  public boolean isServiceWork(Context context, String ServiceName) {
    boolean isWork = false;
    ActivityManager am = (ActivityManager) ((Activity) context)
        .getSystemService(Context.ACTIVITY_SERVICE);
    List<RunningServiceInfo> runningServices = am.getRunningServices(40);
    if (runningServices.size() < 0) {
      return false;
    }
    for (int i = 0; i < runningServices.size(); i++) {
      String mName = runningServices.get(i).service.getClassName().toString();
      if (mName.equals(ServiceName)) {
        isWork = true;
        break;
      }
    }
    return isWork;
  }


  class ViewHolder {

    private View convertView;
    private TextView tv_courseName;
    private ImageView iv_down;
    private LinearLayout ll_isGone;
    private LinearLayout ll_out;
    private ImageView iv_begin;
    private TextView tv_time;
    private RelativeLayout iv_download;
    private ImageView iv_downloadicon;
    private TextView tv_download;


    ViewHolder(View convertView) {
      this.convertView = convertView;
      this.tv_courseName = (TextView) convertView.findViewById(R.id.tv_courseName);
      this.iv_down = (ImageView) convertView.findViewById(R.id.iv_down);
      this.ll_out = (LinearLayout) convertView.findViewById(R.id.ll_out);
      this.iv_begin = (ImageView) convertView.findViewById(R.id.iv_begin);
      this.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
      this.ll_isGone = (LinearLayout) convertView.findViewById(R.id.ll_isGone);
      this.iv_download = (RelativeLayout) convertView.findViewById(R.id.iv_download);
      this.iv_downloadicon = ((ImageView) convertView.findViewById(R.id.iv_downicon));
      this.tv_download = (TextView) convertView.findViewById(R.id.tv_download);
      convertView.setTag(this);
    }
  }


}
>>>>>>> f8c163e9f9b16c6f8465981156b159495b4df8c8
