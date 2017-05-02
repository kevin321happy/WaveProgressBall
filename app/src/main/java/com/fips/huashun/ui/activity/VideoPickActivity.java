package com.fips.huashun.ui.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Video.Media;
import android.provider.MediaStore.Video.Thumbnails;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.fips.huashun.R;
import com.fips.huashun.modle.bean.VideoInfo;
import com.fips.huashun.ui.adapter.VideoRecycleAdapter;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.NavigationBar.NavigationListener;
import com.fips.huashun.ui.utils.ToastUtil;
import java.util.ArrayList;
import java.util.List;

//视频选择界面
public class VideoPickActivity extends BaseActivity {


  TextView mTvRight;
  @Bind(R.id.recycle)
  RecyclerView mRecycler;
  @Bind(R.id.nb_video_picker)
  NavigationBar mNbVideoPicker;
  private List<VideoInfo> mVideoInfoList = new ArrayList<>();
  private Handler mHandler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      if (msg.what == 1) {
        mRecycler.setLayoutManager(new GridLayoutManager(VideoPickActivity.this, 4));
        mRecycleAdapter = new VideoRecycleAdapter(mVideoInfoList);
        mRecycler.setAdapter(mRecycleAdapter);
      }
    }
  };
  private VideoRecycleAdapter mRecycleAdapter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_video_pick);
    ButterKnife.bind(this);
    new SearchVedio().start();
    initView();
  }

  public void initView() {
    mNbVideoPicker.setLeftText("取消");
    mNbVideoPicker.setRightText("确定");
    mNbVideoPicker.setTitle("视频选择");
    mNbVideoPicker.setListener(new NavigationListener() {
      @Override
      public void onButtonClick(int button) {
        //取消返回
        if (button == NavigationBar.LEFT_VIEW) {
          finish();
        }
        //确定,返回携带参数
        if (button == NavigationBar.RIGHT_VIEW) {
          if (mRecycleAdapter != null) {
            List<VideoInfo> chooseVideos = mRecycleAdapter.getChooseVideos();
            if (chooseVideos.size() < 1) {
              ToastUtil.getInstant().show("请选择视频");
              return;
            }
            VideoInfo videoInfo = chooseVideos.get(0);
            Intent intent = new Intent(VideoPickActivity.this,SendSmallVideoActivity.class);
            intent.putExtra("video_path",videoInfo.getPath());
            startActivity(intent);
            finish();
          }
        }
      }
    });

  }

  @Override
  public boolean isSystemBarTranclucent() {
    return false;
  }


  //获取格式化之后的时间
  public String getFormatTime(long time) {
    String s = null;
    long hour = time / (60 * 60 * 1000);
    long minute = (time - hour * 60 * 60 * 1000) / (60 * 1000);
    long second = (time - hour * 60 * 60 * 1000 - minute * 60 * 1000) / 1000;
    if (second >= 60) {
      second = second % 60;
      minute += second / 60;
    }
    if (minute >= 60) {
      minute = minute % 60;
      hour += minute / 60;
    }
    String sh = " ";
    String sm = " ";
    String ss = " ";
    if (hour < 10) {
      sh = "0 " + String.valueOf(hour);
    } else {
      sh = String.valueOf(hour);
    }
    if (minute < 10) {
      sm = "0 " + String.valueOf(minute);
    } else {
      sm = String.valueOf(minute);
    }
    if (second < 10) {
      ss = "0 " + String.valueOf(second);
    } else {
      ss = String.valueOf(second);
    }
    s = sh.substring(1) + ":" + sm + ":" + ss;
    System.out.println(sh + sm + ss);
    System.out.println(hour + "a " + minute + "a " + second + "a ");
    return s;
  }

  //点击监听
//  @Override
//  public void onClick(View v) {
//    switch (v.getId()){
//      case  R.id.tv_left://取消
//        break;
//      case R.id.tv_right://确定
//        List<VideoInfo> chooseVideos = mRecycleAdapter.getChooseVideos();
//        if (chooseVideos.size()==0){
//          Toast.makeText(this,"请选择视频", Toast.LENGTH_SHORT).show();
//          return;
//        }else {
//          //跳转界面
//          Toast.makeText(this,"视频的时间为 ："+chooseVideos.get(0).getTotletime(), Toast.LENGTH_SHORT).show();
//        }
//        break;
//    }
//
//  }


  //查找本地视频
  class SearchVedio extends Thread {

    @Override
    public void run() {
      // 如果有sd卡（外部存储卡）
      if (Environment.getExternalStorageState()
          .equals(Environment.MEDIA_MOUNTED)) {
        Uri originalUri = Media.EXTERNAL_CONTENT_URI;
        ContentResolver cr = VideoPickActivity.this.getApplicationContext().getContentResolver();
        Cursor cursor = cr.query(originalUri, null, null, null, null);
        if (cursor == null) {
          return;
        }
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
          String title = cursor
              .getString(cursor.getColumnIndexOrThrow(Media.TITLE));
          String path = cursor.getString(cursor.getColumnIndexOrThrow(Media.DATA));
          long size = cursor.getLong(cursor.getColumnIndexOrThrow(Media.SIZE));
          long duration = cursor
              .getInt(cursor.getColumnIndexOrThrow(Media.DURATION));
          //获取当前Video对应的Id，然后根据该ID获取其缩略图的uri
          int id = cursor.getInt(cursor.getColumnIndexOrThrow(Media._ID));
          String[] selectionArgs = new String[]{id + ""};
          String[] thumbColumns = new String[]{Thumbnails.DATA,
              Thumbnails.VIDEO_ID};
          String selection = Thumbnails.VIDEO_ID + "=?";
          String uri_thumb = "";
          Cursor thumbCursor = (VideoPickActivity.this.getApplicationContext().getContentResolver())
              .query(
                  Thumbnails.EXTERNAL_CONTENT_URI, thumbColumns, selection,
                  selectionArgs,
                  null);
          if (thumbCursor != null && thumbCursor.moveToFirst()) {
            uri_thumb = thumbCursor
                .getString(thumbCursor.getColumnIndexOrThrow(Thumbnails.DATA));
          }
          String time = getFormatTime(duration);
          VideoInfo videoInfo = new VideoInfo(path, title, time, uri_thumb);
          if (!videoInfo.getThumb().equals("")) {
            mVideoInfoList.add(videoInfo);
          }
        }
        if (cursor != null) {
          cursor.close();
          mHandler.sendEmptyMessage(1);
        }
      }
    }
  }

  /**
   * 获取所有的视频列表
   */
  @RequiresApi(api = VERSION_CODES.M)
  public List<VideoInfo> getListVideos() {
    return mVideoInfoList;
  }
}
