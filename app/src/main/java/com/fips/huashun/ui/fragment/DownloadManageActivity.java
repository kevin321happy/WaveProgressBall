package com.fips.huashun.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.fips.huashun.R;
import com.fips.huashun.db.dao.CourseNameDao;
import com.fips.huashun.db.dao.SectionDownloadDao;
import com.fips.huashun.modle.dbbean.CourseEntity;
import com.fips.huashun.modle.dbbean.CourseSectionEntity;
import com.fips.huashun.modle.event.SectionDownloadStateEvent;
import com.fips.huashun.ui.activity.BaseActivity;
import com.fips.huashun.ui.adapter.HaveDownloadedAdapter;
import com.fips.huashun.ui.adapter.OnDownloadAdapter;
import com.fips.huashun.widgets.CustomExpandableListView;
import com.fips.huashun.widgets.NoScrollListView;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description: 课程下载管理界面
 * autour: Kevin
 * company:锦绣氘(武汉)科技有限公司
 * date: 2017/5/4 13:13
 * update: 2017/5/4
 * version: 1.21
 * 站在峰顶 看世界
 * 落在谷底 思人生
 */

public class DownloadManageActivity extends BaseActivity {


  @Bind(R.id.lv_download_queue)
  NoScrollListView mNoScrollListView;
  @Bind(R.id.ll_download_finish)
  LinearLayout mLlDownloadFinish;
  @Bind(R.id.ll_download_queue)
  LinearLayout mLlDownloadQueue;
  @Bind(R.id.lv_download_finish)
  CustomExpandableListView mLvDownloadFinish;
  private CourseNameDao mCourseNameDao;
  private SectionDownloadDao mSectionDownloadDao;
  private List<String> mGroup;
  private Map<String, List<CourseSectionEntity>> mMap;
  private HaveDownloadedAdapter mDownloadedAdapter;
  private OnDownloadAdapter mOnDownloadAdapter;
  private Map<String, Integer> bindmap = new HashMap();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_download_manage);
    ButterKnife.bind(this);
    mGroup = new ArrayList<>();
    mMap = new HashMap<>();
    mDownloadedAdapter = new HaveDownloadedAdapter();
    mOnDownloadAdapter = new OnDownloadAdapter(this);
    //设置适配器
    mLvDownloadFinish.setAdapter(mDownloadedAdapter);
    mNoScrollListView.setAdapter(mOnDownloadAdapter);
    getDaos();
    initDownQueueCourse();
    initDownloadFinishCourse();
  }

  @Override
  protected void onStart() {
    super.onStart();
    //注册evenbus
    EventBus.getDefault().register(this);
  }

  //获取在下载队列中的课程
  private void initDownQueueCourse() {
    //获取正在下载中的课程
    List<CourseSectionEntity> sectionEntities = mSectionDownloadDao.querySectionOnDownload();
    if (sectionEntities == null || sectionEntities.size() == 0) {
      mLlDownloadQueue.setVisibility(View.GONE);
      return;
    }
    Log.d("DownloadManageActivity", "查到的章节长度" + sectionEntities.size());
    for (int i = 0; i < sectionEntities.size(); i++) {
      //保存到集合
      bindmap.put(sectionEntities.get(i).getSectionId(), i);

    }
    mOnDownloadAdapter.setData(sectionEntities);
    mOnDownloadAdapter.notifyDataSetChanged();
  }


  @Override
  public boolean isSystemBarTranclucent() {
    return false;
  }

  //获取已经下载的数据
  private void initDownloadFinishCourse() {
    mMap.clear();
    mGroup.clear();
    List<CourseEntity> entityList = mCourseNameDao.queryAll();
    if (entityList == null) {
      return;
    }
    for (CourseEntity courseEntity : entityList) {
      String courseid = courseEntity.getCourseid();
      String coursename = courseEntity.getCoursename();
      //根据课程的Id来查询该课程下面的下载状态为2的课程
      List<CourseSectionEntity> sectionEntities = mSectionDownloadDao
          .querySectionByCourseId(courseid);
//      Log.i("test", "查到的结果长度为 ：" + sectionEntities.size());
      if (sectionEntities != null && sectionEntities.size() > 0) {
        mGroup.add(coursename);
        //如果课程下面有下载了的章节,保存到MAP集合
        mMap.put(coursename, sectionEntities);
      }
    }
    //设置数据,刷新适配器
    mDownloadedAdapter.setData(mGroup, mMap);
    mDownloadedAdapter.notifyDataSetChanged();
  }

  //获取操作数据库的dao
  public void getDaos() {
    if (mCourseNameDao == null) {
      mCourseNameDao = new CourseNameDao(this);
    }
    if (mSectionDownloadDao == null) {
      mSectionDownloadDao = new SectionDownloadDao(this);
    }
  }

  //收到了章节正在下载的事件
  public void onEventMainThread(SectionDownloadStateEvent event) {

    int state = event.getState();
    if (state == 0) {
      //开始下载
    } else if (state == 1) {
      //下载中列表指定条目刷新
      String what = event.getWhat();
      //获取当前的位置
      Integer position = bindmap.get(what);
      //局部刷新
      updateView(position);
    } else if (state == 2) {
      //下载完成
      initDownloadFinishCourse();
      String what = event.getWhat();
      updateView(bindmap.get(what));
    } else {
      return;
    }
  }

  //刷新局部的数据
  private void updateView(int itemIndex) {
    //得到第一个可显示控件的位置，
    int visiblePosition = mNoScrollListView.getFirstVisiblePosition();
    //只有当要更新的view在可见的位置时才更新，不可见时，跳过不更新
    if (itemIndex - visiblePosition >= 0) {
      //得到要更新的item的view
      View view = mNoScrollListView.getChildAt(itemIndex - visiblePosition);
      //调用adapter更新界面
      Log.i("test1000", "指定条目位置 ：" + itemIndex);
      mOnDownloadAdapter.updateView(view, itemIndex);
    }
  }

  @Override
  protected void onStop() {
    super.onStop();
    //反注册evenbus
    EventBus.getDefault().unregister(this);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

  }
}
