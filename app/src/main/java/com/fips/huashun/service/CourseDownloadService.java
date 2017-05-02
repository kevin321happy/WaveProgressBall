package com.fips.huashun.service;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import com.fips.huashun.R;
import com.fips.huashun.db.dao.SectionDownloadDao;
import com.fips.huashun.modle.dbbean.CourseSectionEntity;
import com.fips.huashun.modle.event.ChangeDownLoadEvent;
import com.fips.huashun.modle.event.SectionDownloadEvent;
import com.fips.huashun.modle.event.SectionDownloadStateEvent;
import com.fips.huashun.ui.utils.ToastUtil;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.download.DownloadListener;
import com.yanzhenjie.nohttp.download.DownloadQueue;
import com.yanzhenjie.nohttp.download.DownloadRequest;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.ServerError;
import com.yanzhenjie.nohttp.error.StorageReadWriteError;
import com.yanzhenjie.nohttp.error.StorageSpaceNotEnoughError;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.error.URLError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;
import de.greenrobot.event.EventBus;
import java.io.File;

public class CourseDownloadService extends Service {

  private DownloadQueue mDownloadQueue;
  private String mPath;
  private SectionDownloadDao mSectionDownloadDao;
  private EventBus mEventBus;
  private ToastUtil mToastUtil;

  @Override
  public void onCreate() {
    super.onCreate();
    mEventBus = EventBus.getDefault();
    mEventBus.register(this);
    mDownloadQueue = NoHttp.newDownloadQueue(3);
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    //注册EvenBus

    return super.onStartCommand(intent, flags, startId);
  }

  @Override
  public void onStart(Intent intent, int startId) {
    super.onStart(intent, startId);
    Log.i("test", "下载服务开启了");
    mPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/section";
    mToastUtil = ToastUtil.getInstant();
    File filedir = new File(mPath);
    if (!filedir.exists()) {
      filedir.mkdirs();
    }
  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public void onRebind(Intent intent) {
    super.onRebind(intent);
  }

  @Override
  public boolean onUnbind(Intent intent) {
    return super.onUnbind(intent);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    if (mEventBus != null) {
      mEventBus.unregister(this);
    }
    mDownloadQueue.cancelAll();
  }

  //当收到取消下载的
  public void onEventMainThread(ChangeDownLoadEvent event) {
    String pid = event.getPid();
    final String localPath = event.getLocalPath();
    //取消下载任务
    mDownloadQueue.cancelBySign(pid);
    mDownloadQueue.notify();
    Log.i("test", "下载任务取消");
  }


  //当收到了EventBus的下载的消息
  public void onEventMainThread(SectionDownloadEvent event) {
    if(mDownloadQueue.unFinishSize()>=3){
      ToastUtil.getInstant().show("亲，当前下载队列已满,请稍后尝试！");
      return;
    }
    ToastUtil.getInstant().show("已加入下载队列！");
    final String sectionUrl = event.getSectionUrl();
    final String sectionId = event.getSectionId();
    int pid = Integer.parseInt(sectionId);
    if (mSectionDownloadDao == null) {
      mSectionDownloadDao = new SectionDownloadDao(getApplication());
    }
    final String filename = sectionId + sectionUrl.substring(sectionUrl.lastIndexOf("."));
    CourseSectionEntity sectionEntity = mSectionDownloadDao
        .querySectionBySectionId(sectionId);
    sectionEntity.setLocalpath(mPath + filename);
    mSectionDownloadDao.upDataInfo(sectionEntity);
    //加入下载的队列中
    addToDownloadQueue(sectionId, sectionUrl, filename);
  }

  //开始下载
  public void addToDownloadQueue(String pid, String url, String name) {
    DownloadRequest downloadRequest = NoHttp
        .createDownloadRequest(url, mPath, name, true, true);
    //设置取消请求的标识
    downloadRequest.setCancelSign(pid);
    //将任务加入队列
    mDownloadQueue.add(Integer.parseInt(pid), downloadRequest, mDownloadListener);
  }

  //文件下载的监听
  private DownloadListener mDownloadListener = new DownloadListener() {
    @Override
    public void onDownloadError(int what, Exception exception) {
      CourseSectionEntity sectionEntity = mSectionDownloadDao
          .querySectionBySectionId(what + "");
      sectionEntity.setState(-1);
      mSectionDownloadDao.upDataInfo(sectionEntity);
      SectionDownloadStateEvent downloadStateEvent = new SectionDownloadStateEvent();
      downloadStateEvent.setState(1);
      //发送EvenBus下载中
      if (mEventBus != null) {
        mEventBus.post(downloadStateEvent);
      }
      //发送Evenbus
      //提示出错信息
      if (exception instanceof ServerError) {
        mToastUtil.show(R.string.download_error_server);
      } else if (exception instanceof NetworkError) {
        mToastUtil.show(R.string.download_error_network);
      } else if (exception instanceof StorageReadWriteError) {
        mToastUtil.show(R.string.download_error_storage);
      } else if (exception instanceof StorageSpaceNotEnoughError) {
        mToastUtil.show(R.string.download_error_space);
      } else if (exception instanceof TimeoutError) {
        mToastUtil.show(R.string.download_error_timeout);
      } else if (exception instanceof UnKnownHostError) {
        mToastUtil.show(R.string.download_error_un_know_host);
      } else if (exception instanceof URLError) {
        mToastUtil.show(R.string.download_error_url);
      } else {
        mToastUtil.show(R.string.download_error_un);
      }
    }

    @Override
    public void onStart(int what, boolean isResume, long rangeSize, Headers responseHeaders,
        long allCount) {
      //更新数据库
      if (mSectionDownloadDao != null) {
        CourseSectionEntity courseSectionEntity = mSectionDownloadDao
            .querySectionBySectionId(what + "");
        courseSectionEntity.setState(1);
//        courseSectionEntity.setLocalpath(mPath + filename);
        mSectionDownloadDao.upDataInfo(courseSectionEntity);
      }
      SectionDownloadStateEvent downloadStateEvent = new SectionDownloadStateEvent();
      downloadStateEvent.setState(1);
      //发送EvenBus下载中
      if (mEventBus != null) {
        mEventBus.post(downloadStateEvent);
      }
    }

    @Override
    public void onProgress(int what, int progress, long fileCount, long speed) {
      //保存进度
      CourseSectionEntity sectionEntity = mSectionDownloadDao
          .querySectionBySectionId(what + "");
      if (sectionEntity != null) {
        sectionEntity.setProgress(progress + "%");
        mSectionDownloadDao.upDataInfo(sectionEntity);
      }
    }

    @Override
    public void onFinish(int what, String filePath) {
      //更新数据库
      if (mSectionDownloadDao != null) {
        CourseSectionEntity courseSectionEntity = mSectionDownloadDao
            .querySectionBySectionId(what + "");
        if (courseSectionEntity != null) {
          courseSectionEntity.setState(2);
          courseSectionEntity.setLocalpath(filePath);
          mSectionDownloadDao.upDataInfo(courseSectionEntity);
        }
      }
      if (mEventBus != null) {
        SectionDownloadStateEvent downloadStateEvent = new SectionDownloadStateEvent();
        downloadStateEvent.setState(1);
        mEventBus.post(downloadStateEvent);
      }
    }

    @Override
    public void onCancel(int what) {
//        CheckDownloadQueueState();
    }
  };

  //检查下载队列中的状态
  private void CheckDownloadQueueState() {
    //队列中没有请求的,反注册服务
    if (mDownloadQueue.unFinishSize() == 0) {
      //停止服务
      stopSelf();
      if (mEventBus != null) {
        mEventBus.unregister(this);
      }
    }
  }
}
