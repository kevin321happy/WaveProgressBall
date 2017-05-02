package com.fips.huashun.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.db.dao.CacheStudyTimeDao;
import com.fips.huashun.modle.dbbean.CacheStudyTimeEntity;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.SystemBarTintManager;
import com.fips.huashun.ui.utils.Utils;
import com.loopj.android.http.RequestParams;
import com.shuyu.gsyvideoplayer.GSYPreViewManager;
import com.shuyu.gsyvideoplayer.GSYVideoPlayer;
import com.shuyu.gsyvideoplayer.listener.StandardVideoAllCallBack;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.CustomGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import org.json.JSONObject;

//视频音频播放
public class VedioPlayActivity extends Activity implements OnClickListener {

  private CustomGSYVideoPlayer mPlayer;
  private OrientationUtils mOrientationUtils;
  private String url;
  private String filename;
  protected SystemBarTintManager tintManager;
  private long mStartTime;//开始播放时间
  private long mEndTime;//停止播放时间
  private long mStudyTime;//学习时间
  private long mTotleTime;//总的学习时间
  private String courseid;
  private boolean isPlay;//是否正在播放
  private boolean isPause;//是否暂停


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_vedio_play);
    //当前时间为开始播放时间
    mStartTime = System.currentTimeMillis();
    //沉浸式状态栏
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
      tintManager = new SystemBarTintManager(this);
      tintManager.setStatusBarTintColor(getResources().getColor(R.color.transparent));
      tintManager.setStatusBarTintEnabled(true);
    }
    Intent intent = getIntent();
    url = intent.getStringExtra("url");
    filename = intent.getStringExtra("filename");
    courseid = intent.getStringExtra("courseid");
    mPlayer = (CustomGSYVideoPlayer) findViewById(R.id.CustomGSYVideoPlayer);
    initView();
  }

  protected void initView() {
    mOrientationUtils = new OrientationUtils(this, mPlayer);
    mOrientationUtils.resolveByClick();
    try {
      mPlayer.setUp(url, false, "");
      mPlayer.setNeedShowWifiTip(true);
      mPlayer.setIsTouchWiget(true);
      ImageView backButton = mPlayer.getBackButton();
      backButton.setVisibility(View.VISIBLE);
      backButton.setOnClickListener(this);
      if (isPause==false) {
        mPlayer.startPlayLogic();
      }
    } catch (Exception e) {
    }
    //播放器的监听
    mPlayer.setStandardVideoAllCallBack(new StandardVideoAllCallBack() {
      @Override
      public void onClickStartThumb(String url, Object... objects) {
      }

      @Override
      public void onClickBlank(String url, Object... objects) {

      }

      @Override
      public void onClickBlankFullscreen(String url, Object... objects) {

      }

      @Override
      public void onPrepared(String url, Object... objects) {
        //当前时间为开始播放时间
        mStartTime = System.currentTimeMillis();
        isPlay = true;
        Log.i("test", "开始播放，当前时间： " + mStartTime / 100);
      }

      @Override
      public void onClickStartIcon(String url, Object... objects) {

      }

      @Override
      public void onClickStartError(String url, Object... objects) {

      }

      @Override
      public void onClickStop(String url, Object... objects) {
        isPause = true;
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

      @Override
      public void onClickStopFullscreen(String url, Object... objects) {
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

      @Override
      public void onClickResume(String url, Object... objects) {
        isPause = false;
        //重新取开始时间
        mStartTime = System.currentTimeMillis();
        Log.i("test", "重新播放开始时间：  " + mStartTime / 100);
      }

      @Override
      public void onClickResumeFullscreen(String url, Object... objects) {
        mStartTime = System.currentTimeMillis();
        Log.i("test", "全屏重新播放开始时间：  " + mStartTime / 100);
      }

      @Override
      public void onClickSeekbar(String url, Object... objects) {

      }

      @Override
      public void onClickSeekbarFullscreen(String url, Object... objects) {

      }

      @Override
      public void onAutoComplete(String url, Object... objects) {
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
        if (mOrientationUtils != null) {
          mOrientationUtils.backToProtVideo();
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

  @Override
  protected void onPause() {
    mPlayer.onVideoPause();
    isPause = true;//当前播放暂停了
    super.onPause();
  }

  @Override
  protected void onStop() {
    super.onStop();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mEndTime = System.currentTimeMillis();
    //学习时间
    if (mEndTime - mStartTime > 0) {
      mStudyTime = mEndTime - mStartTime;
      mTotleTime += mStudyTime;
      Log.i("test", "全屏暂停播放了1，当前时间： " + mEndTime / 100 + "学习时间：  " + mStudyTime / 100 + "当前学习时长"
          + mTotleTime / 100);
      mStudyTime = 0;
    }
    if (mTotleTime > 1000 * 30) {
      studyTimeSend(String.valueOf(mTotleTime / 60000));
    }
    //注销播放器
    isPause=false;
    mPlayer.release();
    GSYVideoPlayer.releaseAllVideos();
    GSYPreViewManager.instance().releaseMediaPlayer();
    if (mOrientationUtils != null) {
      mOrientationUtils.releaseListener();
    }
  }

  //点击返回键
  @Override
  public void onBackPressed() {
    if (mOrientationUtils != null) {
      mOrientationUtils.backToProtVideo();
    }
    if (mPlayer.backFromWindowFull(this)) {
      return;
    }
    isPause=false;
    super.onBackPressed();
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    //如果旋转了就全屏
    if (isPlay && !isPause) {
      if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
        if (!mPlayer.isIfCurrentIsFullscreen()) {
          mPlayer.startWindowFullscreen(VedioPlayActivity.this, true, true);
        }
      } else {
        //新版本isIfCurrentIsFullscreen的标志位内部提前设置了，所以不会和手动点击冲突
        if (mPlayer.isIfCurrentIsFullscreen()) {
          StandardGSYVideoPlayer.backFromWindowFull(this);
        }
        if (mOrientationUtils != null) {
          mOrientationUtils.setEnable(true);
        }
      }
    }
  }

  //发送学习时长
  private void studyTimeSend(String stutime) {
    if (courseid == null) {
      return;
    }
    //如果网络不可用,保存数据到本地
    if (!Utils.isNetWork(this)) {
      CacheStudyTimeDao studyTimeDao = new CacheStudyTimeDao(this);
      CacheStudyTimeEntity studyTimeEntity = new CacheStudyTimeEntity();
      studyTimeEntity.setCourseId(courseid);
      studyTimeEntity.setUid(PreferenceUtils.getUserId());
      studyTimeEntity.setStudyTime(stutime);
      studyTimeDao.addRedord(studyTimeEntity);
      Log.i("test", "保存了学习记录： " + studyTimeEntity.toString());
    } else {
      RequestParams requestParams = new RequestParams();
      requestParams.put("userid", PreferenceUtils.getUserId());
      requestParams.put("courseid", courseid);
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
              Log.i("test", "发送学习时间成功了");
            }

            @Override
            public void onFailure(String error, String message) {
              super.onFailure(error, message);
              Log.i("test", "发送学习时间失败了");
            }
          }));
    }
  }

  @Override
  public void onClick(View v) {
    finish();
  }
}
