package com.fips.huashun.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fips.huashun.R;
import com.fips.huashun.db.dao.CourseNameDao;
import com.fips.huashun.db.dao.SectionDownloadDao;
import com.fips.huashun.modle.dbbean.CourseEntity;
import com.fips.huashun.modle.dbbean.CourseSectionEntity;
import com.fips.huashun.modle.event.SectionDownloadStateEvent;
import com.fips.huashun.service.CourseDownloadService;
import com.fips.huashun.ui.adapter.HaveDownloadedAdapter;
import com.fips.huashun.ui.adapter.HaveDownloadedAdapter.OnChildItemClickListener;
import com.fips.huashun.ui.adapter.OnDownloadAdapter;
import com.fips.huashun.ui.adapter.OnDownloadAdapter.OnSectionDownloadListener;
import com.fips.huashun.ui.utils.AlertDialogUtils;
import com.fips.huashun.ui.utils.AlertDialogUtils.DialogClickInter;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.NavigationBar.NavigationListener;
import com.fips.huashun.ui.utils.ToastUtil;
import com.fips.huashun.ui.utils.Utils;
import com.fips.huashun.widgets.CustomExpandableListView;
import com.fips.huashun.widgets.NoScrollListView;
import com.shuyu.gsyvideoplayer.utils.FileUtils;
import de.greenrobot.event.EventBus;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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

public class DownloadManageActivity extends BaseActivity implements OnChildItemClickListener,
    OnSectionDownloadListener {

  @Bind(R.id.lv_download_queue)
  NoScrollListView mNoScrollListView;
  @Bind(R.id.ll_download_finish)
  LinearLayout mLlDownloadFinish;
  @Bind(R.id.ll_download_queue)
  LinearLayout mLlDownloadQueue;
  @Bind(R.id.lv_download_finish)
  CustomExpandableListView mLvDownloadFinish;
  @Bind(R.id.NavigationBar)
  NavigationBar mNavigationBar;
  @Bind(R.id.tv_delete_all)
  TextView mTvDeleteAll;
  private CourseNameDao mCourseNameDao;
  private SectionDownloadDao mSectionDownloadDao;
  private List<String> mGroup;
  private Map<String, List<CourseSectionEntity>> mMap;
  private HaveDownloadedAdapter mDownloadedAdapter;
  private OnDownloadAdapter mOnDownloadAdapter;
  private Map<String, Integer> bindmap = new HashMap();
  private EventBus mEventBus;
  private List<CourseEntity> mEntityList;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_download_manage);
    boolean serviceWork = Utils
        .isServiceWork(this, "com.fips.huashun.service.CourseDownloadService");
    if (serviceWork == false) {
      Intent it = new Intent(this, CourseDownloadService.class);
      this.startService(it);
    }
    ButterKnife.bind(this);
    mEventBus = EventBus.getDefault();
    initView();
    getDaos();
  }

  @Override
  public void initView() {
    mNavigationBar.setTitle("下载管理");
    mNavigationBar.setLeftImage(R.drawable.fanhui);
    mNavigationBar.setListener(new NavigationListener() {
      @Override
      public void onButtonClick(int button) {
        if (button == NavigationBar.LEFT_VIEW) {
          finish();
        }
      }
    });
    mGroup = new ArrayList<>();
    mMap = new HashMap<>();
    mDownloadedAdapter = new HaveDownloadedAdapter();
    mOnDownloadAdapter = new OnDownloadAdapter(this);
    mLvDownloadFinish.setGroupIndicator(null);//取消自带的指示箭头
    //设置适配器
    mLvDownloadFinish.setAdapter(mDownloadedAdapter);
    mNoScrollListView.setAdapter(mOnDownloadAdapter);
    mDownloadedAdapter.setOnChildItemClickListener(this);
    mOnDownloadAdapter.setOnSectionDownloadListener(this);
  }

  @Override
  protected void onStart() {
    super.onStart();
    //注册evenbus
    EventBus.getDefault().register(this);
  }

  //界面可见
  @Override
  protected void onResume() {
    super.onResume();
    //在视图可见的时候刷新,当从后台进入的时候也会刷新
    initDownQueueCourse();
    initDownloadFinishCourse();
  }

  @Override
  public boolean isSystemBarTranclucent() {
    return false;
  }

  //获取在下载队列中的课程
  private void initDownQueueCourse() {
    //获取正在下载中的课程
    Observable.create(new OnSubscribe<List<CourseSectionEntity>>() {
      @Override
      public void call(Subscriber<? super List<CourseSectionEntity>> subscriber) {
        List<CourseSectionEntity> sectionEntities = mSectionDownloadDao.querySectionOnDownload();
        //发出
        subscriber.onNext(sectionEntities);
      }
    }).subscribeOn(Schedulers.io())//查询数据放IO线程
        .observeOn(AndroidSchedulers.mainThread())//回调在主线程
        .subscribe(new Observer<List<CourseSectionEntity>>() {
          @Override
          public void onCompleted() {

          }

          @Override
          public void onError(Throwable e) {

          }

          @Override
          public void onNext(List<CourseSectionEntity> sectionEntities) {
            if (sectionEntities == null || sectionEntities.size() == 0) {
              mLlDownloadQueue.setVisibility(View.GONE);
              return;
            }
            for (int i = 0; i < sectionEntities.size(); i++) {
              //保存到集合
              bindmap.put(sectionEntities.get(i).getSectionId(), i);
            }
            mOnDownloadAdapter.setData(sectionEntities);
            mOnDownloadAdapter.notifyDataSetChanged();
          }
        });
  }
  //获取已经下载的数据
  private void initDownloadFinishCourse() {
    mMap.clear();
    mGroup.clear();
    mEntityList = mCourseNameDao.queryAll();
    if (mEntityList == null || mEntityList.size() == 0) {
      return;
    }
    for (CourseEntity courseEntity : mEntityList) {
      String courseid = courseEntity.getCourseid();
      String coursename = courseEntity.getCoursename();
      //根据课程的Id来查询该课程下面的下载状态为2的课程
      List<CourseSectionEntity> sectionEntities = mSectionDownloadDao
          .querySectionByCourseId(courseid);
      if (sectionEntities != null && sectionEntities.size() > 0) {
        mGroup.add(coursename);
        //如果课程下面有下载了的章节,保存到MAP集合
        mMap.put(coursename, sectionEntities);
      }
      //已下载课程为0门
      if (mGroup.size() == 0) {
        mTvDeleteAll.setTextColor(getResources().getColor(R.color.bg_hui));
        mTvDeleteAll.setEnabled(false);
      } else {
        mTvDeleteAll.setTextColor(getResources().getColor(R.color.enterprise_act__text));
        mTvDeleteAll.setEnabled(true);
      }
    }

    //设置数据,刷新适配器
    mDownloadedAdapter.setData(mGroup, mMap);
    for (int i = 0; i < mGroup.size(); i++) {
      mLvDownloadFinish.expandGroup(i);
    }
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
    } else if (state == 1 || state == 3) {
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

  //点击已下载的课程的监听
  @Override
  public void onStudy(String localpath, String sectionId, String courseid, String type) {
    if (type.equals("2")) {
      //跳转到视频播放
      Intent intent = new Intent(this, VedioPlayActivity.class);
      intent.putExtra("courseid", courseid);
      intent.putExtra("url", localpath);
      startActivity(intent);
    } else if (type.equals("3")) {
      //跳转到pdf学习
      Intent intent = new Intent(this, PdfActivity.class);
      intent.putExtra("courseid", courseid);
      intent.putExtra("type", "3");
      intent.putExtra("pdfurl", localpath);
      startActivity(intent);
    }
  }

  //删除已下载的章节
  @Override
  public void onDelete(String localpath, String sectionId) {
    Suggestdelete(localpath, sectionId);
  }

  //删除正在下载的章节
  @Override
  public void deleteOnDownloadSection(String localpath, String sectionId) {
    Suggestdelete(localpath, sectionId);
  }

  //当章节下载完成了
  @Override
  public void onFinishDownloadSection() {
    //刷新正在下载的列表
    initDownQueueCourse();
  }

  //删除给用户提示
  private void Suggestdelete(final String localpath, final String sectionId) {
    AlertDialogUtils.showTowBtnDialog(DownloadManageActivity.this, "确定删除已下载的文件？", "取消", "确定",
        new DialogClickInter() {
          @Override
          public void leftClick(AlertDialog dialog) {
            dialog.dismiss();
          }

          @Override
          public void rightClick(AlertDialog dialog) {
            deleteCourseFile(localpath, sectionId);
            dialog.dismiss();
          }
        });
  }

  //删除已下载的课程
  private void deleteCourseFile(final String localpath, final String sectionId) {
    //rxjava
    Observable.create(new OnSubscribe<String>() {
      @Override
      public void call(Subscriber<? super String> subscriber) {
        //将状态设置为未下载,并更新更新数据看
        CourseSectionEntity sectionEntity = mSectionDownloadDao
            .querySectionBySectionId(sectionId);
        sectionEntity.setState(0);
        mSectionDownloadDao.upDataInfo(sectionEntity);
        File file = new File(localpath);
        if (file.exists() && file.length() > 0) {
          FileUtils.deleteFiles(file);
        }
        subscriber.onNext("删除成功！");
      }
    }).subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<String>() {
          @Override
          public void onCompleted() {

          }

          @Override
          public void onError(Throwable e) {

          }

          @Override
          public void onNext(String s) {
            ToastUtil.getInstant().show(s);
            //发送EvenBus刷新目录界面
            SectionDownloadStateEvent stateEvent = new SectionDownloadStateEvent();
            stateEvent.setState(-1);
            mEventBus.post(stateEvent);
            //刷新
            initDownQueueCourse();
            initDownloadFinishCourse();
          }
        });
  }

  @OnClick(R.id.tv_delete_all)
  public void onViewClicked() {
    if (mEntityList == null || mEntityList.size() == 0) {
      return;
    }
    //删除所有
    AlertDialogUtils.showTowBtnDialog(DownloadManageActivity.this, "确定删除所有已下载的文件？", "取消", "确定",
        new DialogClickInter() {
          @Override
          public void leftClick(AlertDialog dialog) {
            dialog.dismiss();
          }

          @Override
          public void rightClick(AlertDialog dialog) {
            deleteAll();
            dialog.dismiss();
          }
        });
  }

  //删除所有的已下载
  private void deleteAll() {
    Observable.create(new OnSubscribe<String>() {
      @Override
      public void call(Subscriber<? super String> subscriber) {
        //删除操作
        List<CourseSectionEntity> sectionEntities = mSectionDownloadDao.queryAllHaveDownload();
        //循环删除
        if (sectionEntities != null && sectionEntities.size() > 0) {
          for (CourseSectionEntity sectionEntity : sectionEntities) {
            sectionEntity.setState(0);
            mSectionDownloadDao.upDataInfo(sectionEntity);
            //删除
            File file = new File(sectionEntity.getLocalpath());
            if (file.exists() && file.length() > 0) {
              FileUtils.deleteFiles(file);
            }
            subscriber.onNext("删除成功！");
          }
        }
      }
    }).subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
        .observeOn(AndroidSchedulers.mainThread())//在主线程回调
        .subscribe(new Observer<String>() {
          @Override
          public void onCompleted() {

          }

          @Override
          public void onError(Throwable e) {

          }

          @Override
          public void onNext(String s) {
            initDownloadFinishCourse();
            //发送evenbus
            //发送EvenBus刷新目录界面
            SectionDownloadStateEvent stateEvent = new SectionDownloadStateEvent();
            stateEvent.setState(-1);
            mEventBus.post(stateEvent);
          }
        });
  }
}
