package com.fips.huashun.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fips.huashun.ApplicationEx;
import com.fips.huashun.R;
import com.fips.huashun.common.ACache;
import com.fips.huashun.common.Constants;
import com.fips.huashun.common.ConstantsCode;
import com.fips.huashun.modle.bean.BeginEventType;
import com.fips.huashun.modle.bean.CourseDeailInfo1;
import com.fips.huashun.modle.bean.Coursrdetail;
import com.fips.huashun.modle.bean.CoursrdetailInfo;
import com.fips.huashun.modle.bean.CouseMuluInfo1;
import com.fips.huashun.modle.event.RecommendEvent;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.fragment.CourseDetailFragment1;
import com.fips.huashun.ui.fragment.CourseDetailFragment2;
import com.fips.huashun.ui.fragment.CourseDetailFragment3;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.SystemBarTintManager;
import com.fips.huashun.ui.utils.ToastUtil;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.lzy.okgo.OkGo;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shuyu.gsyvideoplayer.GSYPreViewManager;
import com.shuyu.gsyvideoplayer.GSYVideoPlayer;
import com.shuyu.gsyvideoplayer.listener.StandardVideoAllCallBack;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.CustomGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.Config;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import de.greenrobot.event.EventBus;
import java.io.File;
import java.util.ArrayList;
import okhttp3.Call;
import okhttp3.Response;
import org.json.JSONObject;

public class CourseDetailActivity extends AppCompatActivity implements View.OnClickListener {

  private RadioButton view1;
  private RadioButton view2;
  private RadioButton view3;

  private ViewPager mPager;
  private ArrayList<RadioButton> rb;
  private ArrayList<Fragment> fragmentList;
  private int currIndex;//当前页卡编号
  private int page;
  private CourseDetailFragment2 courseDetailFragment2;
  private String url;
  private NavigationBar navigationBar;
  private String courseId;
  private CoursrdetailInfo coursrdetailInfo;
  //分享课程的头像
  private UMImage image;
  private ImageView iv_tvBg;
  String sessonid = "";//课程是否已经学习
  private RadioGroup rg;
  private RelativeLayout rl_tv;
  private ImageView app_video_play;
  private TextView app_video_currentTime;
  private UMShareAPI umShareAPI;
  private ImageView iv_startPlaying;
  //private CouserMulu mMuludata;
  private ImageButton img_btn_navigation_right;
  //播放器
  private CustomGSYVideoPlayer mplay;
  //封面图片
  private ImageView mImageView;
  //目录下第一个课程类型
  private String mSessiontype;
  private boolean isPlay;
  private boolean isPause;
  private OrientationUtils orientationUtils;
  private Gson gson;
  private long mStartTime;//开始播放时间
  private long mEndTime;//停止播放时间
  private long mStudyTime;//学习时间
  private long mTotleTime;//总的学习时间
  private ImageView mIv_bg;
  private Boolean isBrought = false;//是否购买
  private File mVedioCacheFile;//视频缓存文件
  private ACache mACache;//数据缓存
  private String mBuytype;//课程购买类型
  private String VedioUrl;//视频路径
  private String mCourseName;//当前章节课程名字
  private CouseMuluInfo1.DataBean mData;//课程下的目录信息
  private CourseDeailInfo1 mCourseDeailInfo1;
  private String mCourseImageUrl;//课程图片的路径
  private boolean isEnterpriseCourse;//是否为企业课程
  protected SystemBarTintManager tintManager;
  private android.support.v4.app.FragmentTransaction mFt;
  private MyFragmentPagerAdapter mMyFragmentPagerAdapter;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    umShareAPI = UMShareAPI.get(this);
    setContentView(R.layout.activity_course_detail);
    //沉浸式状态栏
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
      tintManager = new SystemBarTintManager(this);
      tintManager.setStatusBarTintColor(getResources().getColor(R.color.title_color));
      tintManager.setStatusBarTintEnabled(true);
    }
    //权限请求
//    String[] mPermissionList = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS};
//    ActivityCompat.requestPermissions(CourseDetailActivity.this, mPermissionList, 100);
    String data = getIntent().getDataString();
    if (null == data || TextUtils.isEmpty(data)) {
      courseId = getIntent().getStringExtra("courseId");
    } else {
      String[] dataString = data.split("=");
      courseId = dataString[1].toString().trim();
    }
    EventBus.getDefault().post(new RecommendEvent(courseId));
    //初始化Gson
    gson = new Gson();
    EventBus.getDefault().register(this);
    initView();
    //初始化网络数据
    getPalyingUrl();
    initData();
  }


  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);

  }
  protected void initView() {
    //super.initView();
    navigationBar = (NavigationBar) findViewById(R.id.na_bar);
    navigationBar.setTitle("课程详情");
    navigationBar.setLeftImage(R.drawable.course_reslt_back);
    img_btn_navigation_right = (ImageButton) findViewById(R.id.img_btn_navigation_right);
    img_btn_navigation_right.setOnClickListener(this);
    navigationBar.setListener(new NavigationBar.NavigationListener() {
      @Override
      public void onButtonClick(int button) {
        if (button == NavigationBar.LEFT_VIEW) {
          finish();
        } else if (button == NavigationBar.RIGHT_VIEW) {// 分享
          Config.REDIRECT_URL = "http://sns.whalecloud.com/sina2/callback";
          new ShareAction(CourseDetailActivity.this)
              .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE,
                  SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
              .setShareboardclickCallback(new ShareBoardlistener() {
                @Override
                public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {

                  new ShareAction(CourseDetailActivity.this)
                      .setPlatform(share_media)
                      .withTitle(coursrdetailInfo.getCoursrdetail().getCourseName())
                      .withText(coursrdetailInfo.getCoursrdetail().getCourseintro())
                      .withTargetUrl(Constants.H5_URL + "class_share.html?userid=" + PreferenceUtils
                          .getUserId() + "&courseid=" + courseId)
                      .withMedia(new UMImage(CourseDetailActivity.this,
                          R.drawable.home_industrycourse_head_default))
                      .setCallback(umShareListener)
                      .share();
                  Log.e("url",
                      Constants.H5_URL + "class_share.html?userid=" + PreferenceUtils.getUserId()
                          + "&courseid=" + courseId);
                }
              }).open();
        }
      }
    });
    rg = (RadioGroup) findViewById(R.id.rg);
    mplay = (CustomGSYVideoPlayer) findViewById(R.id.CustomGSYVideoPlayer);
    //初始化播放器设置
    initGSYPlaySetting(mplay);
    mIv_bg = (ImageView) findViewById(R.id.iv_tvBg);
    mPager = (ViewPager) findViewById(R.id.viewpager);
    fragmentList = new ArrayList<Fragment>();
    CourseDetailFragment1 courseDetailFragment1 = new CourseDetailFragment1();
    CourseDetailFragment2  courseDetailFragment2 = new CourseDetailFragment2();
    CourseDetailFragment3 courseDetailFragment3 = new CourseDetailFragment3();
    //顺序依次为详情、目录、推荐课程
    fragmentList.add(courseDetailFragment2);
    fragmentList.add(courseDetailFragment1);
    fragmentList.add(courseDetailFragment3);

    view1 = (RadioButton) findViewById(R.id.guid1);
    view2 = (RadioButton) findViewById(R.id.guid2);
    view3 = (RadioButton) findViewById(R.id.guid3);

    view1.setOnClickListener(new txListener(0));
    view2.setOnClickListener(new txListener(1));
    view3.setOnClickListener(new txListener(2));

    rb = new ArrayList<RadioButton>();
    rb.add(view1);
    rb.add(view2);
    rb.add(view3);

    rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {
        //选中的RadioButton播放动画
        ScaleAnimation sAnim = new ScaleAnimation(1, 1.2f, 1, 1.2f, Animation.RELATIVE_TO_SELF,
            0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        sAnim.setDuration(500);
        sAnim.setFillAfter(true);
        //遍历所有的RadioButton
        for (int i = 0; i < group.getChildCount(); i++) {
          RadioButton radioBtn = (RadioButton) group.getChildAt(i);
          if (radioBtn.isChecked()) {
            radioBtn.startAnimation(sAnim);
          } else {
            radioBtn.clearAnimation();
          }
        }
      }
    });
    mMyFragmentPagerAdapter = new MyFragmentPagerAdapter(
        getSupportFragmentManager(), fragmentList);
    mPager.setAdapter(mMyFragmentPagerAdapter);
    mPager.setOnPageChangeListener(new MyOnPageChangeListener());//页面变化时的监听器
    mPager.setCurrentItem(0);//设置当前显示标签页
    rb.get(0).setChecked(true);//设置进入页面第一次显
  }
  /**
   * 初始化播放器的设置
   */
  private void initGSYPlaySetting(final CustomGSYVideoPlayer mplay) {
    orientationUtils = new OrientationUtils(this, mplay);
    //初始化不打开外部的旋转
    orientationUtils.setEnable(false);
    mplay.setIsTouchWiget(true);
    //打开自动旋转
    mplay.setRotateViewAuto(true);
    mplay.setLockLand(false);
    mplay.setShowFullAnimation(false);
    //点击全屏按钮
    mplay.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //直接横屏
        orientationUtils.resolveByClick();
        // 第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
        mplay.startWindowFullscreen(CourseDetailActivity.this, true, false);
      }
    });
    //播放器的监听
    mplay.setStandardVideoAllCallBack(new StandardVideoAllCallBack() {
      //当点击封面
      @Override
      public void onClickStartThumb(String url, Object... objects) {

      }

      //点击了播放中的空白区域
      @Override
      public void onClickBlank(String url, Object... objects) {

      }

      @Override
      public void onClickBlankFullscreen(String url, Object... objects) {

      }

      //开始播放
      @Override
      public void onPrepared(String url, Object... objects) {
        //当前时间为开始播放时间
        mStartTime = System.currentTimeMillis();
        Log.i("test", "开始播放，当前时间： " + mStartTime / 100);
        orientationUtils.setEnable(true);
        isPlay = true;
        if (mData != null && mData.getData().size() > 0) {
          //课程已经学习
          setTV(mData.getData().get(0).getSessonid());
        }
      }

      @Override
      public void onClickStartIcon(String url, Object... objects) {
        Log.d("test", "点击了开始播放");

      }

      @Override
      public void onClickStartError(String url, Object... objects) {

      }

      //播放状态下点击了暂停
      @Override
      public void onClickStop(String url, Object... objects) {
        isPlay = false;
        //点击了暂挺当前时间为结束时间
        mEndTime = System.currentTimeMillis();
        //学习时间
        if (mEndTime - mStartTime > 0) {
          mStudyTime = mEndTime - mStartTime;
          mTotleTime += mStudyTime;
          Log.i("test", "暂停播放了1，当前时间： " + mEndTime / 100 + "学习时间：  " + mStudyTime / 100 + "当前学习时长"
              + mTotleTime / 100);
          mStudyTime = 0;
        }
      }

      //全屏播放状态下点击了暂停
      @Override
      public void onClickStopFullscreen(String url, Object... objects) {
        isPlay = false;
        //点击了暂挺当前时间为结束时间
        mEndTime = System.currentTimeMillis();
        //学习时间
        if (mEndTime - mStartTime > 0) {
          mStudyTime = mEndTime - mStartTime;
          mTotleTime += mStudyTime;
          Log.i("test", "全屏暂停播放了1，当前时间： " + mEndTime / 100 + "学习时间：  " + mStudyTime / 100 + "当前学习时长"
              + mTotleTime / 100);
          mStudyTime = 0;
        }
      }

      //点击了暂停状态点击了播放
      @Override
      public void onClickResume(String url, Object... objects) {
        isPlay = true;
        //重新取开始时间
        mStartTime = System.currentTimeMillis();
        Log.i("test", "重新播放开始时间：  " + mStartTime / 100);
      }

      //全屏状态下暂停状态点击了播放
      @Override
      public void onClickResumeFullscreen(String url, Object... objects) {
        isPlay = true;
        mStartTime = System.currentTimeMillis();
        Log.i("test", "全屏重新播放开始时间：  " + mStartTime / 100);
      }

      @Override
      public void onClickSeekbar(String url, Object... objects) {

      }

      @Override
      public void onClickSeekbarFullscreen(String url, Object... objects) {
      }

      //视频播放结束
      @Override
      public void onAutoComplete(String url, Object... objects) {
        isPlay = false;
        mEndTime = System.currentTimeMillis();
        //学习时间
        if (mEndTime - mStartTime > 0) {
          mStudyTime = mEndTime - mStartTime;
          mTotleTime += mStudyTime;
          Log.i("test", "全屏暂停播放了1，当前时间： " + mEndTime / 100 + "学习时间：  " + mStudyTime / 100 + "当前学习时长"
              + mTotleTime / 100);
          mStudyTime = 0;
        }
      }

      @Override
      public void onEnterFullscreen(String url, Object... objects) {

      }

      @Override
      public void onQuitFullscreen(String url, Object... objects) {
        if (orientationUtils != null) {
          orientationUtils.backToProtVideo();
        }
      }

      @Override
      public void onQuitSmallWidget(String url, Object... objects) {

      }

      @Override
      public void onEnterSmallWidget(String url, Object... objects) {

      }

      @Override
      public void onTouchScreenSeekVolume(String url, Object... objects) {

      }

      @Override
      public void onTouchScreenSeekPosition(String url, Object... objects) {

      }

      @Override
      public void onTouchScreenSeekLight(String url, Object... objects) {

      }
    });
  }

  /**
   * 友盟分享
   */
  private UMShareListener umShareListener = new UMShareListener() {
    @Override
    public void onResult(SHARE_MEDIA platform) {
      Log.d("plat", "platform" + platform);
      if (platform.name().equals("WEIXIN_FAVORITE")) {
        ToastUtil.getInstant().show("收藏成功啦");
      } else {
        ToastUtil.getInstant().show("分享成功啦");
      }
    }

    @Override
    public void onError(SHARE_MEDIA platform, Throwable t) {
      ToastUtil.getInstant().show("分享失败啦");
      if (t != null) {
        Log.d("throw", "throw:" + t.getMessage());
      }
    }
    @Override
    public void onCancel(SHARE_MEDIA platform) {
      ToastUtil.getInstant().show("分享取消了");
    }
  };
  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      /**
       * 点击了分享
       */
      case R.id.img_btn_navigation_right:
        Config.REDIRECT_URL = "http://sns.whalecloud.com/sina2/callback";
        new ShareAction(CourseDetailActivity.this)
            .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN,
                SHARE_MEDIA.WEIXIN_CIRCLE)
            .setShareboardclickCallback(new ShareBoardlistener() {
              @Override
              public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                new ShareAction(CourseDetailActivity.this)
                    .setPlatform(share_media)
                    .withTitle(mCourseDeailInfo1.getData().getCoursrdetail().getCourseName())
                    .withText(mCourseDeailInfo1.getData().getCoursrdetail().getCourseintro())
                    .withTargetUrl(
                        Constants.H5_URL + "class_share.html?userid=" + PreferenceUtils.getUserId()
                            + "&courseid=" + courseId)
                    .withMedia(new UMImage(CourseDetailActivity.this,
                        R.drawable.home_industrycourse_head_default))
                    .setCallback(umShareListener)
                    .share();
                Log.e("url",
                    Constants.H5_URL + "class_share.html?userid=" + PreferenceUtils.getUserId()
                        + "&courseid=" + courseId);
              }
            }).open();
        break;
    }
  }

  public class txListener implements View.OnClickListener {

    private int index = 0;

    public txListener(int i) {
      index = i;
    }

    @Override
    public void onClick(View v) {
      mPager.setCurrentItem(index);
    }
  }

  public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> list;

    public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
      super(fm);
      mFt = fm.beginTransaction();
      this.list = list;

    }

    @Override
    public int getCount() {
      return list.size();
    }

    @Override
    public Fragment getItem(int arg0) {
      return list.get(arg0);
    }

  }

  public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageSelected(int arg0) {
      currIndex = arg0;
      int i = currIndex + 1;
      rb.get(arg0).setChecked(true);

    }
  }

  private void initData() {
    //指定缓存
    OkGo.post(Constants.CHECKCOURSEBASE)
        .params("userid", PreferenceUtils.getUserId())
        .cacheMode(com.lzy.okgo.cache.CacheMode.FIRST_CACHE_THEN_REQUEST)
        .cacheKey(ConstantsCode.CACHE_COURSE_DETAIL)
        .params("courseid", courseId)
        .execute(new com.lzy.okgo.callback.StringCallback() {
          @Override
          public void onSuccess(String s, Call call, Response response) {
            mCourseDeailInfo1 = gson.fromJson(s, CourseDeailInfo1.class);
            CourseDeailInfo1.DataBean data = mCourseDeailInfo1.getData();
            showCourseInfo(data);
          }
        });
  }
  /**
   * 课程详情
   */
  private void showCourseInfo(CourseDeailInfo1.DataBean data) {
    try {
      mBuytype = data.getBuytype();
      isBrought = mBuytype.equals("1");//是否购买
      if (data.getCoursrdetail() != null) {
        String teacherid = data.getCoursrdetail().getTeacherid();
        //判断是否企业课程
        if (!TextUtils.isEmpty(teacherid)) {
          if (Integer.parseInt(teacherid) > 0) {
            isEnterpriseCourse = false;//非企业课程
            img_btn_navigation_right.setVisibility(View.VISIBLE);
            img_btn_navigation_right.setImageResource(R.drawable.share);
          } else {
            isEnterpriseCourse = true;
            img_btn_navigation_right.setVisibility(View.INVISIBLE);
          }
        } else {
          img_btn_navigation_right.setVisibility(View.INVISIBLE);
          mplay.getDrawingTime();
        }
        mCourseImageUrl = Constants.IMG_URL + data.getCoursrdetail().getCourseImage();
        ImageLoader.getInstance().displayImage(mCourseImageUrl, mIv_bg
            , ApplicationEx.home_course_options);
        //设置播放路径
        if (mSessiontype == null) {
          return;
        }
        //显示已经购买的课程
        if (mSessiontype.equals("2")) {
          //TODO 视频课程
          if (isBrought || isEnterpriseCourse) {
            //如果购买了或是企业课程
            showBroughtCourse(VedioUrl, mCourseImageUrl);
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 显示已经购买的课程
   */
  private void showBroughtCourse(String vedioUrl, String pUrl) {
    //mIv_bg.setVisibility(g);
    mImageView = new ImageView(CourseDetailActivity.this);
    //播放器背景图片
    mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
    ImageLoader.getInstance().displayImage(pUrl, mImageView
        , ApplicationEx.home_course_options);
    mplay.setThumbImageView(mImageView);
    mIv_bg.setVisibility(View.GONE);
    mplay.setUp(vedioUrl, true, "");
  }

  @Override
  protected void onPause() {
    super.onPause();
    MobclickAgent.onPageEnd("CourseDetailActivity");
    MobclickAgent.onPause(this);
    isPause = true;
  }

  @Override
  protected void onResume() {
    super.onResume();
    MobclickAgent.onPageStart("CourseDetailActivity");
    MobclickAgent.onResume(this);
    isPause = false;
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    //TODO
    EventBus.getDefault().unregister(this);
    //发送总的学习时间,如果时间大于半分钟
    //判断是否在播放
    if (isPlay) {
      mEndTime = System.currentTimeMillis();
      //学习时间
      if (mEndTime - mStartTime > 0) {
        mStudyTime = mEndTime - mStartTime;
        mTotleTime += mStudyTime;
        Log.i("test", "全屏暂停播放了1，当前时间： " + mEndTime / 100 + "学习时间：  " + mStudyTime / 100 + "当前学习时长"
            + mTotleTime / 100);
        mStudyTime = 0;
      }
    }
    if (mTotleTime > 1000 * 30) {
      studyTimeSend(String.valueOf(mTotleTime / 60000));
    }
    mplay.release();
    GSYVideoPlayer.releaseAllVideos();
    GSYPreViewManager.instance().releaseMediaPlayer();
    if (orientationUtils != null) {
      orientationUtils.releaseListener();
    }
  }
  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    //如果旋转了就全屏
    if (isPlay && !isPause) {
      if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
        if (!mplay.isIfCurrentIsFullscreen()) {
          mplay.startWindowFullscreen(CourseDetailActivity.this, true, true);
        }
      } else {
        //isIfCurrentIsFullscreen的标志位内部提前设置了，所以不会和手动点击冲突
        if (mplay.isIfCurrentIsFullscreen()) {
          StandardGSYVideoPlayer.backFromWindowFull(this);
        }
      }
    }
  }

  @Override
  public void onBackPressed() {
    if (orientationUtils != null) {
      orientationUtils.backToProtVideo();
    }
    if (StandardGSYVideoPlayer.backFromWindowFull(this)) {
      return;
    }
    finish();
  }

  public void onEventMainThread(BeginEventType event) {
    if (event != null) {
      sessonid = event.getSessonid();
      mCourseImageUrl = event.getCourseImage();
      if (sessonid.equals("1") && mSessiontype.equals("2")) {
        Log.i("test", "收到了EvenBus的消息" + sessonid);
        mIv_bg.setVisibility(View.GONE);
        //显示已购买的课程
        showBroughtCourse(VedioUrl, mCourseImageUrl);
        isBrought = true;
      }
      String courseVedioUrl = event.getVideoUrl();
      mplay.setUp(courseVedioUrl, true, "");
      mplay.refreshVideo();
      int visibility = mIv_bg.getVisibility();
      if (visibility == visibility) {
        mIv_bg.setVisibility(View.GONE);
      }
      //开始播放逻辑
      mplay.startPlayLogic();
      //课程学完了
      setTV(sessonid);
    }
  }
  //点击开始按钮  这个课程算已经学习
  private void setTV(String sessonid) {
    RequestParams requestParams = new RequestParams();
    requestParams.put("userid", PreferenceUtils.getUserId());
    requestParams.put("courseid", courseId);
    requestParams.put("sessonid", sessonid);
    HttpUtil.post(Constants.ADDSTUDYINFO, requestParams,
        new LoadJsonHttpResponseHandler(this, new LoadDatahandler() {
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
          }
        }));
  }

  public void onEventMainThread(Coursrdetail event) {
    rg.setVisibility(View.VISIBLE);
  }

  /**
   * 友盟分享回调
   */
  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
  }

  /**
   * 功能：判断学习时间大于一分钟
   */
  private void sendStutyTime(String time) {
    Log.e("time", "time=" + time);
    if (!TextUtils.isEmpty(time) && !TextUtils.equals("00:00", time)) {
      String[] string = time.split(":");
      Long times;
      if (string.length == 2) {
        times = Long.valueOf(string[0]);
      } else {
        times = Long.valueOf(string[0]) * 60 + Long.valueOf(string[1]);
      }
      Log.e("times", "times=" + times);
      if (times > 0) {
        //发送学习时间
        studyTimeSend(String.valueOf(times));
      }
    }
  }

  /**
   * 功能：学习时间
   */
  private void studyTimeSend(String stutime) {
    RequestParams requestParams = new RequestParams();
    requestParams.put("userid", PreferenceUtils.getUserId());
    requestParams.put("courseid", courseId);
    requestParams.put("stutime", stutime);
    HttpUtil.post(Constants.ADDSTUINFO, requestParams,
        new LoadJsonHttpResponseHandler(this, new LoadDatahandler() {
          @Override
          public void onStart() {
            super.onStart();
          }

          @Override
          public void onSuccess(JSONObject data) {
            super.onSuccess(data);
          }

          @Override
          public void onFailure(String error, String message) {
            super.onFailure(error, message);
          }
        }));
  }

  /**
   * 获取到播放路径后的逻辑操作
   */
  private void getPalyingUrl() {
    //指定缓存
    OkGo.post(Constants.CHECKCOURSECTION)
        .params("userid", PreferenceUtils.getUserId())
        .params("courseid", courseId)
        .execute(new com.lzy.okgo.callback.StringCallback() {
          @Override
          public void onSuccess(String s, Call call, Response response) {
            CouseMuluInfo1 couseMuluInfo1 = gson.fromJson(s, CouseMuluInfo1.class);
            mData = couseMuluInfo1.getData();
            if (mData.getData().size() > 0) {
              mSessiontype = mData.getData().get(0).getSessiontype();
              VedioUrl = mData.getData().get(0).getStanquality();
              mCourseName = mData.getData().get(0).getCourseName();
              if (!("2".equals(mSessiontype))) {
                //如果不为视频课程就隐藏播放图标
                mplay.hideAllWidget();
                mplay.dismissBrightnessDialog();
                mplay.dismissVolumeDialog();
              }
            }
          }
        });
  }
  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    rb.clear();
    fragmentList.clear();
    courseId = intent.getStringExtra("courseId");
    RecommendEvent recommendEvent = new RecommendEvent();
    recommendEvent.setCourseid(courseId);
    EventBus.getDefault().post(recommendEvent);
    initView();
    //发送EventBus
    //初始化网络数据
    getPalyingUrl();
    initData();
    mMyFragmentPagerAdapter.notifyDataSetChanged();
  }
}



