package com.fips.huashun.ui.fragment;


import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.fips.huashun.ApplicationEx;
import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.db.dao.CourseNameDao;
import com.fips.huashun.db.dao.SectionDownloadDao;
import com.fips.huashun.modle.bean.BeginEventType;
import com.fips.huashun.modle.bean.CoursrdetailInfo;
import com.fips.huashun.modle.bean.CouseMuluInfo;
import com.fips.huashun.modle.bean.CouserMulu;
import com.fips.huashun.modle.dbbean.CourseEntity;
import com.fips.huashun.modle.dbbean.CourseSectionEntity;
import com.fips.huashun.modle.event.RecommendEvent;
import com.fips.huashun.modle.event.RefreshEvent;
import com.fips.huashun.modle.event.SectionDownloadStateEvent;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.service.CourseDownloadService;
import com.fips.huashun.ui.activity.LoginActivity;
import com.fips.huashun.ui.activity.PdfActivity;
import com.fips.huashun.ui.activity.WebviewActivity;
import com.fips.huashun.ui.adapter.CourseMuluAdapter;
import com.fips.huashun.ui.adapter.CourseMuluAdapter.Callback;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.ToastUtil;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;
import de.greenrobot.event.EventBus;
import java.io.File;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author hulin 课程目录界面
 */
public class CourseDetailFragment1 extends Fragment implements Callback, OnClickListener {

  private View rootView;
  private Gson gson;
  private ListView lv_list;
  private CourseMuluAdapter adapter;
  private String courseId;
  private CouserMulu couseMulus;
  private String mTeacherid;
  private String mBuytype;
  private boolean mIsEnterpriseCourse;
  private String mReceivecourseid;
  private CoursrdetailInfo mCoursrdetailInfo;
  private SectionDownloadDao mSectionDownloadDao;
  private CourseNameDao mCourseNameDao;
  private String mCoursename;
  private LinearLayout mLl_download_manage;
  private TextView mTv_leftroom;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EventBus.getDefault().register(this);
    if (mSectionDownloadDao == null) {
      mSectionDownloadDao = new SectionDownloadDao(getContext());
      mCourseNameDao = new CourseNameDao(getContext());
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    //开启服务
    //先判断服务是否开启,没开启就开启
    //开启服务
    boolean serviceWork = isServiceWork(getActivity(),
        "com.fips.huashun.service.CourseDownloadService");
    if (serviceWork == false) {
      Intent it = new Intent(getActivity(), CourseDownloadService.class);
      getActivity().startService(it);
    }
    if (rootView == null) {
      rootView = inflater.inflate(R.layout.fragment_mylu, container, false);
    }
    ViewGroup parent = (ViewGroup) rootView.getParent();
    if (parent != null) {
      parent.removeView(rootView);
    }
    return rootView;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initView();
    gson = new Gson();
    if (mReceivecourseid != null) {
      courseId = mReceivecourseid;
    } else {
      courseId = getActivity().getIntent().getStringExtra("courseId");
      String data = getActivity().getIntent().getDataString();
      if (null == data || TextUtils.isEmpty(data)) {
        courseId = getActivity().getIntent().getStringExtra("courseId");
      } else {
        String[] dataString = data.split("=");
        courseId = dataString[1].toString().trim();
      }
    }
    gson = new Gson();
    initData(courseId);
  }

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

  private void initData(final String courseId) {
    RequestParams requestParams = new RequestParams();
    requestParams.put("userid", PreferenceUtils.getUserId());
    requestParams.put("courseid", courseId);
    HttpUtil.post(Constants.CHECKCOURSECTION, requestParams,
        new LoadJsonHttpResponseHandler(getActivity(), new LoadDatahandler() {
          @Override
          public void onStart() {
            super.onStart();
          }

          @Override
          public void onSuccess(JSONObject data) {
            super.onSuccess(data);
            try {
              String suc = data.get("suc").toString();
              String msg = data.get("msg").toString();
              if (suc.equals("y")) {
                couseMulus = gson.fromJson(data.getString("data").toString(), CouserMulu.class);
                List<CouseMuluInfo> couseMuluInfos = couseMulus.getData();
                if (couseMuluInfos.size() == 0) {
                  return;
                }
                String eid = couseMuluInfos.get(0).getEid();
                mCoursename = couseMuluInfos.get(0).getCourseName();
                if (eid.equals("-1")) {//-1为非企业的课程
                  mIsEnterpriseCourse = false;
                } else {
                  mIsEnterpriseCourse = true;
                  //如果为企业课程保存课程信息到数据库
                  //保存数据到数据库
                  saveToDb(couseMuluInfos);
                }
                adapter.setListItems(couseMuluInfos);
                adapter.notifyDataSetChanged();
              }
            } catch (JSONException e) {
              e.printStackTrace();
            }
          }

          @Override
          public void onFailure(String error, String message) {
            super.onFailure(error, message);
          }
        }));
  }

  //保存课程id到章节表
  private void saveToDb(final List<CouseMuluInfo> couseMuluInfos) {
    new Thread(new Runnable() {
      @Override
      public void run() {
        CouseMuluInfo couseMuluInfo1 = couseMuluInfos.get(0);
        if (couseMuluInfo1 != null) {
          mCourseNameDao.addCourse(
              new CourseEntity(couseMuluInfo1.getCourseId(), couseMuluInfo1.getCourseName()));
        }
        for (CouseMuluInfo couseMuluInfo : couseMuluInfos) {
          CourseSectionEntity courseSectionEntity = new CourseSectionEntity();
          if (mSectionDownloadDao.querySectionBySectionId(courseSectionEntity.getSectionId())
              == null) {
            String url;
            String type = couseMuluInfo.getSessiontype();
            if (couseMuluInfo.getSessiontype().equals("2")) {
              url = couseMuluInfo.getStanquality();
            } else if (couseMuluInfo.getSessiontype().equals("3")) {
              url = couseMuluInfo.getDocUrl();
            } else {
              return;
            }
            courseSectionEntity.setState(0);//状态
            courseSectionEntity.setSectionId(couseMuluInfo.getSessonid());//章节ID
            courseSectionEntity.setSectionUrl(url);//章节下载路径
            courseSectionEntity.setCourseId(couseMuluInfo.getCourseId());//课程ID
            courseSectionEntity.setSectionname(couseMuluInfo.getSectionname());//节名
            courseSectionEntity.setFileSize(couseMuluInfo.getFileSize());//文件大小
            courseSectionEntity.setFileType(type);//文件的类型
            mSectionDownloadDao.add(courseSectionEntity);
          }
        }
      }
    }).start();
  }


  private void initView() {
    lv_list = (ListView) rootView.findViewById(R.id.lv_list);
    mLl_download_manage = (LinearLayout) rootView.findViewById(R.id.ll_download_manage);
    mTv_leftroom = (TextView) rootView.findViewById(R.id.tv_left_room);
    mTv_leftroom.setText(getMemoryInfo());
    mLl_download_manage.setOnClickListener(this);
    adapter = new CourseMuluAdapter(getActivity(), this);
    lv_list.setAdapter(adapter);
  }

  @Override
  public void click(View v, int position) {
    switch ((int) v.getTag()) {
      case 1:
        for (int a = 0; a < adapter.getAllListDate().size(); a++) {
          if (a == position) {
            if (adapter.getAllListDate().get(a).isclik()) {
              adapter.getAllListDate().get(a).setIsclik(false);
            } else {
              adapter.getAllListDate().get(a).setIsclik(true);
            }
          } else {
            adapter.getAllListDate().get(a).setIsclik(false);
          }
        }
        adapter.notifyDataSetChanged();
        break;
      case 2:
        CouseMuluInfo item = (CouseMuluInfo) adapter.getItem(position);
        if (ApplicationEx.getInstance().isLogined()) {
          if (couseMulus != null && couseMulus.getBuytype().equals("1")) {
            startLearnCourse(item);
          } else {
            if (mIsEnterpriseCourse) {
              startLearnCourse(item);
              //TODO 模拟购买课程
              buyCourseById();
            } else {
              ToastUtil.getInstant().show("未购买课程");
            }
          }
        } else {
          Intent toLogin = new Intent(getActivity(), LoginActivity.class);
          startActivityForResult(toLogin, 1024);
        }
        break;
    }
  }

  //开始学习课程
  private void startLearnCourse(CouseMuluInfo item) {
    if ("1".equals(item.getSessiontype())) {// 文档
      addStudy(courseId, item.getSessonid());
      Intent intent = new Intent(getActivity(), WebviewActivity.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      intent.putExtra("activityId", courseId);
      intent.putExtra("sessoinid", item.getSessonid());
      intent.putExtra("key", 15);//课程章节文档
      startActivity(intent);
    } else if ("2".equals(item.getSessiontype())) {// 视频
      addStudy(courseId, item.getSessonid());
      //查询是否已经下载了
      String vedioUrl = null;
      CourseSectionEntity sectionEntity = mSectionDownloadDao
          .querySectionBySectionId(item.getSessonid());
      if (sectionEntity != null && sectionEntity.getState() == 2) {
        vedioUrl = sectionEntity.getLocalpath();
      } else {
        vedioUrl = item.getStanquality();
      }
      BeginEventType beginEventType = new BeginEventType();
      beginEventType.setCourseId(courseId);
      beginEventType.setSessonid(item.getSessonid());
      beginEventType.setCouseMulusName(item.getCourseName());
      Log.e("url", item.getStanquality());
      beginEventType.setVideoUrl(vedioUrl);
      EventBus.getDefault().post(beginEventType);

    } else if ("3".equals(item.getSessiontype())) {  //pdf 文件
      //查询是否已经下载了
      String pdfUrl = null;
      String type;
      CourseSectionEntity sectionEntity = mSectionDownloadDao
          .querySectionBySectionId(item.getSessonid());
      if (sectionEntity != null && sectionEntity.getState() == 2) {
        pdfUrl = sectionEntity.getLocalpath();
        type = "3";
      } else {
        pdfUrl = Constants.IMG_URL + item.getCoursedoc();
        type = "0";
      }
      addStudy(courseId, item.getSessonid());
      Intent intent = new Intent(getActivity(), PdfActivity.class);
      intent.putExtra("courseid", courseId);
      intent.putExtra("type", type);
      intent.putExtra("pdfurl", pdfUrl);
      startActivity(intent);
    }
  }

  //EvenBus接受消息
  public void onEventMainThread(RefreshEvent event) {
    if (event != null) {
      initData(courseId);
    }
  }

  public void onEventMainThread(RecommendEvent event) {
    mReceivecourseid = event.getCourseid();
  }
  //收到课程下载的EventBus
  public void onEventMainThread(SectionDownloadStateEvent event) {
    //只要不是下载中就刷新
    if (event.getState() != 1) {
      if (adapter != null) {
        adapter.notifyDataSetChanged();
      }
    }
  }

  /**
   * 功能：添加学习记录
   *
   * @param sessonid 章节ID
   */
  private void addStudyInfo(String sessonid) {
    RequestParams requestParams = new RequestParams();
    requestParams.put("userid", PreferenceUtils.getUserId());
    requestParams.put("courseid", courseId);
    requestParams.put("sessonid", sessonid);
    HttpUtil.post(Constants.ADD_STUDYINFO_URL, requestParams,
        new LoadJsonHttpResponseHandler(getActivity(), new LoadDatahandler() {
          @Override
          public void onStart() {
            super.onStart();
          }

          @Override
          public void onSuccess(JSONObject data) {
            super.onSuccess(data);
            try {
              String suc = data.get("suc").toString();
              String msg = data.get("msg").toString();
            } catch (JSONException e) {
              e.printStackTrace();
            }
          }

          @Override
          public void onFailure(String error, String message) {
            super.onFailure(error, message);
          }
        }));
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    EventBus.getDefault().unregister(this);
  }


  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    initData(courseId);
  }

  //添加学习
  private void addStudy(String id, String sessonid) {
    RequestParams requestParams = new RequestParams();
    requestParams.put("userid", PreferenceUtils.getUserId());
    requestParams.put("courseid", id);
    requestParams.put("sessonid", sessonid);
    HttpUtil.post(Constants.ADDSTUDYINFO, requestParams,
        new LoadJsonHttpResponseHandler(getActivity(), new LoadDatahandler() {
          @Override
          public void onStart() {
            super.onStart();
          }

          @Override
          public void onFailure(String error, String message) {
            super.onFailure(error, message);
          }

          @Override
          public void onSuccess(JSONObject data) {
            super.onSuccess(data);
            Log.e("data", data.toString());
          }
        }));
  }

  @Override
  public void onResume() {
    super.onResume();
    MobclickAgent.onPageStart("CourseDetailFragment1");
    MobclickAgent.onResume(getActivity());
  }

  @Override
  public void onPause() {
    super.onPause();
    MobclickAgent.onPageEnd("CourseDetailFragment1");
    MobclickAgent.onPause(getActivity());
  }

  /**
   * 功能：购买课程
   */
  private void buyCourseById() {
    if (ApplicationEx.getInstance().isLogined()) {// 用户已登录
      RequestParams requestParams = new RequestParams();
      requestParams.put("courseid", courseId);
      requestParams.put("userid", PreferenceUtils.getUserId());
      requestParams.put("coursePrice", "0");
      requestParams.put("coursename", mCoursename);
      HttpUtil.post(Constants.BUY_0COURSE_URL, requestParams,
          new LoadJsonHttpResponseHandler(getActivity(), new LoadDatahandler() {
            @Override
            public void onStart() {
              super.onStart();
            }

            @Override
            public void onSuccess(JSONObject data) {
              super.onSuccess(data);
              try {
                String suc = data.get("suc").toString();
                String msg = data.get("msg").toString();
//                ToastUtil.getInstant().show(msg);
              } catch (JSONException e) {
                e.printStackTrace();
//                  ToastUtil.getInstant().show("购买课程异常，请重试！");
              }
            }

            @Override
            public void onFailure(String error, String message) {
              super.onFailure(error, message);
              //ToastUtil.getInstant().show("购买课程失败，请重试！");
            }
          }));
    } else {// 用户没有登录，跳到登录页面
      Intent intentToLogin = new Intent(getActivity(), LoginActivity.class);
      startActivityForResult(intentToLogin, 1024);
    }
  }

  /**
   * 根据路径获取内存状态
   */
  private String getMemoryInfo() {
    // 获得手机内部存储控件的状态
    File dataFileDir = Environment.getDataDirectory();
    // 获得一个磁盘状态对象
    StatFs stat = new StatFs(dataFileDir.getPath());

    long blockSize = stat.getBlockSize();   // 获得一个扇区的大小

    long totalBlocks = stat.getBlockCount();    // 获得扇区的总数

    long availableBlocks = stat.getAvailableBlocks();   // 获得可用的扇区数量

    // 总空间
    String totalMemory = Formatter.formatFileSize(getActivity(), totalBlocks * blockSize);
    // 可用空间
    String availableMemory = Formatter.formatFileSize(getActivity(), availableBlocks * blockSize);
    return "剩余空间: " + availableMemory;
  }

  //点击跳转到下载管理
  @Override
  public void onClick(View v) {
    startActivity(new Intent(getActivity(),DownloadManageActivity.class));

  }
}

