package com.fips.huashun.ui.activity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fips.huashun.R;
import com.fips.huashun.ui.utils.SystemBarTintManager;
import mabeijianxi.camera.MediaRecorderBase;
import mabeijianxi.camera.util.DeviceUtils;
import mabeijianxi.camera.util.StringUtils;
import mabeijianxi.camera.views.SurfaceVideoView;
import mabeijianxi.camera.views.SurfaceVideoView.OnPlayStateListener;

/**
 * description: 录制视频播放界面 autour: Kevin company:锦绣氘(武汉)科技有限公司 date: 2017/4/14 10:19 update: 2017/4/14
 * version: 1.21 站在峰顶 看世界 落在谷底 思人生
 */
//播放视频的界面
public class VideoPlayerActivity extends BaseActivity implements
    OnPlayStateListener, OnErrorListener,
    OnPreparedListener, OnCompletionListener,
    OnInfoListener {
  /**
   * 播放路径
   */
  private String mPath;
  /**
   * 是否需要回复播放
   */
  private boolean mNeedResume;
  @Bind(R.id.txt_right)
  TextView mTxtRight;
  @Bind(R.id.videoview)
  SurfaceVideoView mVideoview;
  @Bind(R.id.play_status)
  ImageView mPlayStatus;
  @Bind(R.id.loading)
  ProgressBar mLoading;
  @Bind(R.id.root)
  RelativeLayout mRoot;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //设置全屏模式
    getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN,
        LayoutParams.FLAG_FULLSCREEN);
    // 防止锁屏
    getWindow().addFlags(LayoutParams.FLAG_KEEP_SCREEN_ON);
    mPath = getIntent().getStringExtra("path");
//    mPath="http://v1.52qmct.com/uploadima/2017/22219201704171931.mp4";
    if (StringUtils.isEmpty(mPath)) {
      finish();
      return;
    }
    setContentView(R.layout.activity_videoplay);
    if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
      setTranslucentStatus(true);
      SystemBarTintManager tintManager = new SystemBarTintManager(this);
      tintManager.setStatusBarTintEnabled(true);
      tintManager.setStatusBarTintResource(R.color.text_black);//通知栏所需颜色
    }
    ButterKnife.bind(this);
    int screenWidth = getScreenWidth(this);
    int videoHight = (int) (screenWidth / (MediaRecorderBase.SMALL_VIDEO_WIDTH / (
        MediaRecorderBase.SMALL_VIDEO_HEIGHT * 1.0f)));
    mVideoview.getLayoutParams().height = videoHight;
    mVideoview.requestLayout();
    //视频播放监听
    mVideoview.setOnPreparedListener(this);
    mVideoview.setOnPlayStateListener(this);
    mVideoview.setOnErrorListener(this);
    mVideoview.setOnInfoListener(this);
    mVideoview.setOnCompletionListener(this);
    //设置视屏播放
    mVideoview.setVideoPath(mPath);
  }
  //获取屏幕的宽度
  public int getScreenWidth(Activity context) {
    DisplayMetrics mDisplayMetrics = new DisplayMetrics();
    context.getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
    int W = mDisplayMetrics.widthPixels;
    return W;
  }

  @Override
  public void onResume() {
    super.onResume();
    if (mVideoview != null && mNeedResume) {
      mNeedResume = false;
      if (mVideoview.isRelease())
        mVideoview.reOpen();
      else
        mVideoview.start();
    }
  }
  @Override
  public void onPause() {
    super.onPause();
    if (mVideoview != null) {
      if (mVideoview.isPlaying()) {
        mNeedResume = true;
        mVideoview.pause();
      }
    }
  }

  @Override
  public boolean isSystemBarTranclucent() {
    return false;
  }

  @Override
  protected void onDestroy() {
    if (mVideoview != null) {
      mVideoview.release();
      mVideoview = null;
    }
    super.onDestroy();
  }

  @Override
  public void onPrepared(MediaPlayer mp) {
    mVideoview.setVolume(SurfaceVideoView.getSystemVolumn(this));
    mVideoview.start();
    // new Handler().postDelayed(new Runnable() {
    // @SuppressWarnings("deprecation")
    // @Override
    // public void run() {
    // if (DeviceUtils.hasJellyBean()) {
    // mVideoView.setBackground(null);
    // } else {
    // mVideoView.setBackgroundDrawable(null);
    // }
    // }
    // }, 300);
    mLoading.setVisibility(View.GONE);
  }
  @Override
  public boolean dispatchKeyEvent(KeyEvent event) {
    switch (event.getKeyCode()) {// 跟随系统音量走
      case KeyEvent.KEYCODE_VOLUME_DOWN:
      case KeyEvent.KEYCODE_VOLUME_UP:
        mVideoview.dispatchKeyEvent(this, event);
        break;
    }
    return super.dispatchKeyEvent(event);
  }
  @Override
  public void onStateChanged(boolean isPlaying) {
    mPlayStatus.setVisibility(isPlaying ? View.GONE : View.VISIBLE);
  }
  @Override
  public void onCompletion(MediaPlayer mp) {
    if (!isFinishing())
      mVideoview.reOpen();
  }

  @Override
  public boolean onError(MediaPlayer mp, int what, int extra) {
    if (!isFinishing()) {
      // 播放失败
    }
    finish();
    return false;
  }

  @RequiresApi(api = VERSION_CODES.JELLY_BEAN)
  @Override
  public boolean onInfo(MediaPlayer mp, int what, int extra) {
    switch (what) {
      case MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:
        // 音频和视频数据不正确
        break;
      case MediaPlayer.MEDIA_INFO_BUFFERING_START:
        if (!isFinishing())
          mVideoview.pause();
        break;
      case MediaPlayer.MEDIA_INFO_BUFFERING_END:
        if (!isFinishing())
          mVideoview.start();
        break;
      case MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
        if (DeviceUtils.hasJellyBean()) {
          mVideoview.setBackground(null);
        } else {
          mVideoview.setBackgroundDrawable(null);
        }
        break;
    }
    return false;
  }




  @OnClick({R.id.videoview, R.id.root})
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.videoview:
        break;
      case R.id.root:
        //点击当前的屏幕关闭页面
        finish();
        break;
    }
  }
}
