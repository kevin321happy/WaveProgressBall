package com.fips.huashun.ui.activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fips.huashun.R;
import com.fips.huashun.modle.event.PostVideoEvent;
import com.fips.huashun.ui.utils.OssUtils;
import com.fips.huashun.ui.utils.OssUtils.OnLoadoSSListener;
import com.fips.huashun.ui.utils.SystemBarTintManager;
import com.fips.huashun.ui.utils.ToastUtil;
import com.fips.huashun.widgets.ToggleButton;
import de.greenrobot.event.EventBus;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import mabeijianxi.camera.LocalMediaCompress;
import mabeijianxi.camera.MediaRecorderActivity;
import mabeijianxi.camera.model.AutoVBRMode;
import mabeijianxi.camera.model.LocalMediaConfig;
import mabeijianxi.camera.model.OnlyCompressOverBean;

//发送小视频界面
public class SendSmallVideoActivity extends BaseActivity {

  @Bind(R.id.tv_left)
  TextView mTvLeft;
  @Bind(R.id.tv_right)
  TextView mTvRight;
  @Bind(R.id.et_leave)
  EditText mEtLeave;
  @Bind(R.id.iv_video_screenshot)
  ImageView mIvVideoScreenshot;
  @Bind(R.id.tg_allowcommon)
  ToggleButton mTgAllowcommon;
  @Bind(R.id.tv_allowcommon)
  TextView mTvAllowcommon;
  @Bind(R.id.tg_isanonymity)
  ToggleButton mTgIsanonymity;
  @Bind(R.id.tv_anonymity)
  TextView mTvAnonymity;
  private String videoUri;
  private String videoScreenshot;//视频截图
  private ImageView iv_video_screenshot;//封面图片
  private AlertDialog mAlertDialog;
  private String video_path;
  private String PATH;//最终传到视频播放界面的path
  private OssUtils mOssUtils;
  private String mContent;
  private String activityId;
  private String type;
  private String words;
  private static final String SD_PATH = "/sdcard/qmct/video_pic/";
  private static final String IN_PATH = "/qmct/pic/";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_smallvideo_recorder);
    if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
      setTranslucentStatus(true);
      SystemBarTintManager tintManager = new SystemBarTintManager(this);
      tintManager.setStatusBarTintEnabled(true);
      tintManager.setStatusBarTintResource(R.color.line_hui);//通知栏所需颜色
    }
    ButterKnife.bind(this);
    initView();
    initData();
//    initListener();
  }

  //初始化界面
  public void initView() {
    mContent = mEtLeave.getText().toString().trim();
  }

  //初始化监听
  private void initListener() {
    mOssUtils = OssUtils.getInstance(getApplicationContext());
    mOssUtils.setOnLoadoSSListener(new OnLoadoSSListener() {
      @Override
      public void onVideoLoadSuccess(List<String> s) {

      }

      @Override
      public void onLoadSuccess(List<String> fileUrls) {

      }

      @Override
      public void onLoadProgress(String s) {

      }

      @Override
      public void onLoadFailure(String s) {

      }
    });
  }


  @Override
  public boolean isSystemBarTranclucent() {
    return false;
  }

  //初始化
  private void initData() {
    Intent intent = getIntent();
    //拍摄视频传过来的路径
    videoUri = intent.getStringExtra(MediaRecorderActivity.VIDEO_URI);
    //相册选择传过来的路径
    video_path = intent.getStringExtra("video_path");
    PATH = video_path == null ? videoUri : video_path;
    if (video_path != null) {
      //本地视频先进行压缩处理
      CompressLocalVideo(video_path);
      videoScreenshot = getFirstFrame(video_path);
      Bitmap bitmap = BitmapFactory.decodeFile(videoScreenshot);
      mIvVideoScreenshot.setImageBitmap(bitmap);
    } else {
      videoScreenshot = intent.getStringExtra(MediaRecorderActivity.VIDEO_SCREENSHOT);
      Bitmap bitmap = BitmapFactory.decodeFile(videoScreenshot);
      mIvVideoScreenshot.setImageBitmap(bitmap);
    }
  }

  /**
   * 获取视频文件的第一帧
   */
  private String getFirstFrame(String video_path) {
    //创建MediaMetadataRetriever对象
    MediaMetadataRetriever mmr = new MediaMetadataRetriever();
    //绑定资源
    mmr.setDataSource(video_path);
    //获取第一帧图像的bitmap对象
    Bitmap bitmap = mmr.getFrameAtTime();
    //并保存到本地文件
    return saveBitmap(this, bitmap);
  }

  @OnClick({R.id.tv_left, R.id.tv_right, R.id.iv_video_screenshot})
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.tv_left:
        hesitate();
        break;
      case R.id.tv_right:
        //开始上传
        //发送evenBus
        if (PATH == null) {
          ToastUtil.getInstant().show("当前没有选择视频");
          return;
        }
        PostVideoEvent postVideoEvent = new PostVideoEvent();
        postVideoEvent.setPath(PATH);
        postVideoEvent.setThumpath(videoScreenshot);
        postVideoEvent.setWords(mEtLeave.getText().toString().trim());
        EventBus.getDefault().post(postVideoEvent);
        startActivity(new Intent(this, InteractiveSpaceActivity.class));
        finish();
        break;
      case R.id.iv_video_screenshot:
        startActivity(new Intent(this, VideoPlayerActivity.class).putExtra(
            "path", PATH));
        break;
    }
  }

  /**
   * 保存bitmap到本地
   */
  public static String saveBitmap(Context context, Bitmap mBitmap) {
    String savePath;
    File filePic;
    if (Environment.getExternalStorageState().equals(
        Environment.MEDIA_MOUNTED)) {
      savePath = SD_PATH;
    } else {
      savePath = context.getApplicationContext().getFilesDir()
          .getAbsolutePath()
          + IN_PATH;
    }
    try {
      filePic = new File(savePath + generateFileName() + ".jpg");
      if (!filePic.exists()) {
        filePic.getParentFile().mkdirs();
        filePic.createNewFile();
      }
      FileOutputStream fos = new FileOutputStream(filePic);
      mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
      fos.flush();
      fos.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return null;
    }
    return filePic.getAbsolutePath();
  }

  /**
   * 随机生产文件名
   */
  private static String generateFileName() {
    return UUID.randomUUID().toString();
  }

  @Override
  public void onBackPressed() {
    hesitate();
  }

  private void hesitate() {
    if (mAlertDialog == null) {
      mAlertDialog = new Builder(this)
          .setTitle(R.string.hint)
          .setMessage(R.string.record_camera_exit_dialog_message)
          .setNegativeButton(
              R.string.record_camera_cancel_dialog_yes,
              new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog,
                    int which) {
                  finish();
                }
              })
          .setPositiveButton(R.string.record_camera_cancel_dialog_no,
              null).setCancelable(false).show();
    } else {
      mAlertDialog.show();
    }
  }

  //压缩本地视频
  // 选择本地视频压缩
  public void CompressLocalVideo(final String video_path) {
    new Thread(new Runnable() {
      @Override
      public void run() {
        LocalMediaConfig.Buidler buidler = new LocalMediaConfig.Buidler();
        final LocalMediaConfig config = buidler
            .setVideoPath(video_path)
            .captureThumbnailsTime(1)
            .doH264Compress(new AutoVBRMode())
            .setFramerate(15)
            .build();
        OnlyCompressOverBean onlyCompressOverBean = new LocalMediaCompress(config).startCompress();
      }
    }).start();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }
}
