package com.fips.huashun.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.fips.huashun.R;
import com.fips.huashun.common.ConstantsCode;
import com.fips.huashun.db.dao.DataDownloadDao;
import com.fips.huashun.holder.download.ActDataState;
import com.fips.huashun.modle.bean.OnLineCourseModel;
import com.fips.huashun.modle.bean.OnLineCourseModel.RowBean;
import com.fips.huashun.modle.dbbean.DataDownloadInfo;
import com.fips.huashun.ui.utils.CommonViewHolder;
import com.fips.huashun.ui.utils.SPUtils;
import com.fips.huashun.ui.utils.ToastUtil;
import com.fips.huashun.ui.utils.Utils;
import com.fips.huashun.widgets.AnimateHorizontalProgressBar;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kevin on 2017/1/14. 在线学习
 */
public class StudyOnLineAdapter extends BaseAdapter {

  private static final String TAG = "StudyOnLineAdapter";
  private Context mContext;
  private List<OnLineCourseModel.RowBean> mData = new ArrayList<>();
  private DataDownloadDao mDataDownloadDao;
  private List<DataDownloadInfo> mDataDownloadInfos;
  private DataDownloadInfo mDataDownloadInfo;
  private TextView mTv_state;
  private int currentProgress = 0;//当前进度进度
  private Map<String, Integer> bindMap = new HashMap<>();
  private int currentcount;//当前下载任务的个数
  private String directory;
  //private String ext;//文件的拓展名
  private DownloadQueue mDownloadQueue = NoHttp.newDownloadQueue(3);
  private Map<String, DownloadRequest> mDownloadRequests = new HashMap<>();
  private ToastUtil mToastUtil;

  public StudyOnLineAdapter(Context context) {
    this.mContext = context;
    mToastUtil = ToastUtil.getInstant();
    mDataDownloadDao = new DataDownloadDao(context);
  }

  //设置数据
  public void setData(List<OnLineCourseModel.RowBean> data) {
    mData = data;
    String activityid = mData.get(0).getActivityid();
    directory = SPUtils.getString(mContext, activityid);
  }

  @Override
  public int getCount() {
    return mData == null ? 0 : mData.size();
  }

  @Override
  public Object getItem(int position) {
    return mData == null ? null : mData.get(position);
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }

  @Override
  public View getView(final int position, View convertView, ViewGroup parent) {
    CommonViewHolder viewHolder = CommonViewHolder
        .createCVH(convertView, R.layout.ent_studyonline_item, parent);
    //获得控件
    final ImageView courseIamge = viewHolder.getIv(R.id.iv_materials_image);
    TextView courseTitle = viewHolder.getTv(R.id.tv_materials_title);
    TextView courseContent = viewHolder.getTv(R.id.tv_materials_content);
    TextView tv_totlesize = viewHolder.getTv(R.id.tv_totlesize);
    TextView tv_speed = viewHolder.getTv(R.id.tv_speed);
    final ImageView coursedownload = viewHolder.getIv(R.id.iv_download);
    AnimateHorizontalProgressBar progressBar = (AnimateHorizontalProgressBar) viewHolder
        .getView(R.id.pb_progress);
    final OnLineCourseModel.RowBean courseModel = mData.get(position);
    final String pid = courseModel.getPid();
    final int state = courseModel.getState();
    final boolean isdownliading = courseModel.isdownliading();
    if (!isdownliading) {
      //先读取本地数据库信息
      DataDownloadInfo downloadInfo = mDataDownloadDao.queryDataByPid(pid);
      if (downloadInfo == null) {
        coursedownload.setImageResource(
            courseModel.getIsdownload().equals(ConstantsCode.STRING_ONE) ? R.drawable.can_d
                : R.drawable.cannot_down);
        addToDb(courseModel);
      } else {
        //从数据库中取出来设置状态
        ChangeDownloadUi(downloadInfo.getState(), coursedownload);
        //设置进度
        int progress = (int) downloadInfo.getDownloadsize();
        if (0 < progress && progress < 100) {
          progressBar.setVisibility(View.VISIBLE);
          progressBar.setProgress(progress);
        } else {
          progressBar.setVisibility(View.GONE);
          tv_speed.setVisibility(View.GONE);
        }
      }
    } else {//正在下载
      progressBar.setVisibility(View.VISIBLE);
      progressBar.setProgress(courseModel.getProgerss());
      tv_speed.setVisibility(View.VISIBLE);
      tv_speed.setText(Utils.FormentFileSize(Long.parseLong(courseModel.getSpeed() + "")) + "/s");
    }
    final String url = courseModel.getPath();
    String ext = courseModel.getExt();
    final String size = courseModel.getSize();
    final String name = pid + "." + ext;//文件名字
    String path = courseModel.getPath();
    String fileExt = Utils.getFileExt(path);
    if (fileExt.equals(ConstantsCode.STRING_MPF)) {
      courseIamge.setImageResource(R.drawable.v);
    }
    if (fileExt.equals(ConstantsCode.STRING_MPT)) {
      courseIamge.setImageResource(R.drawable.m);
    }
    if (fileExt.equals(ConstantsCode.STRING_PPT) || fileExt.equals(ConstantsCode.STRING_PPTX)) {
      courseIamge.setImageResource(R.drawable.p);
    }
    if (fileExt.equals(ConstantsCode.STRING_DOC) || fileExt.equals(ConstantsCode.STRING_DOCX)) {
      courseIamge.setImageResource(R.drawable.w);
    }
    if (fileExt.equals(ConstantsCode.STRING_XLSX)) {
      courseIamge.setImageResource(R.drawable.x);
    }
    if (fileExt.equals(ConstantsCode.STRING_PDF)) {
      courseIamge.setImageResource(R.drawable.f);
    }
    if (fileExt.equals("jpg") || fileExt.equals("png")) {
      courseIamge.setImageResource(R.drawable.i);
    }
    courseTitle.setText(courseModel.getName());
    courseContent.setText(courseModel.getExplain());
    tv_totlesize.setText(Utils.FormentFileSize(Long.parseLong(courseModel.getSize())));
    //点击开始下载
    coursedownload.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        //下载的多种状态,执行不同的操作
        bindMap.put(pid, position);
        //查询一下当前的状态
        int state;
        //判断当前的下载队列是否已经满了
        if (!(mDownloadQueue.unFinishSize() < 3)) {
          //设置等待
          coursedownload.setImageResource(R.drawable.waite_d);
          //并加入下载队列
          addToDownloadQueue(pid, url, name);
          coursedownload.setEnabled(false);
          return;
        }
        if (isdownliading) {
          state = courseModel.getState();
        } else {
          state = mDataDownloadDao.queryStateByPid(pid);
        }
        switch (state) {
          case ActDataState.DOWNLOAD_NOT:
            ToastUtil.getInstant().show("当前即将开始下载");
            //加入下载队列
            addToDownloadQueue(pid, url, name);
            break;
          case ActDataState.DOWNLOAD_COMPLETED:
            ToastUtil.getInstant().show("当前已经下载完成");
            break;
          case ActDataState.DOWNLOAD_ERROR:
            coursedownload.setEnabled(true);
            ToastUtil.getInstant().show("开始重新下载");
            //加入下载队列
            addToDownloadQueue(pid, url, name);
            break;
          case ActDataState.DOWNLOADING:
            courseIamge.setEnabled(true);
            //正在下载的时候点击就暂停
            mDownloadQueue.cancelBySign(pid);
            break;
          case ActDataState.DOWNLOAD_STOP:
            //加入下载队列
            addToDownloadQueue(pid, url, name);
            break;
        }
      }
    });
    return viewHolder.convertView;
  }
  //将条目信息保存到数据库中
  private void addToDb(RowBean courseModel) {
    DataDownloadInfo dataDownloadInfo = new DataDownloadInfo();
    dataDownloadInfo.setActivityid(courseModel.getActivityid());
    dataDownloadInfo.setDowloadUrl(courseModel.getPath());
    dataDownloadInfo.setPid(courseModel.getPid());
    dataDownloadInfo.setDownloadsize(courseModel.getProgerss());
    dataDownloadInfo.setSize(Long.parseLong(courseModel.getSize()));
    dataDownloadInfo.setState(courseModel.getIsdownload().equals(ConstantsCode.STRING_ONE) ? 1 : 0);
    mDataDownloadDao.add(dataDownloadInfo);
  }

  private void ChangeDownloadUi(int state, ImageView coursedownload) {
    switch (state) {
      case ActDataState.DOWNLOAD_WAIT:
        coursedownload.setImageResource(R.drawable.waite_d);//等待下载
        break;
      case ActDataState.DOWNLOAD_ERROR:
        coursedownload.setImageResource(R.drawable.again_d);//重新下载
        break;
      case ActDataState.DOWNLOAD_NOT:
        coursedownload.setImageResource(R.drawable.can_d);//可以下载
        break;
      case ActDataState.DOWNLOAD_STOP:
        coursedownload.setImageResource(R.drawable.can_d);//可以下载
        break;
      case ActDataState.DOWNLOADING:
        coursedownload.setImageResource(R.drawable.stop_d);//停止
        break;
      case ActDataState.DOWNLOAD_BAN://0
        coursedownload.setImageResource(R.drawable.cannot_down);//禁止下载
        break;
      case ActDataState.DOWNLOAD_COMPLETED:
        coursedownload.setImageResource(R.drawable.finish_d);//下载完成
        break;
    }
  }

  //开始下载
  public void addToDownloadQueue(String pid, String url, String name) {
    DownloadRequest downloadRequest = NoHttp
        .createDownloadRequest(url, directory, name, true, false);
    //设置取消请求的标识
    downloadRequest.setCancelSign(pid);
    mDownloadRequests.put(pid, downloadRequest);
    //将任务加入队列
    mDownloadQueue.add(Integer.parseInt(pid), downloadRequest, mDownloadListener);
  }

  //文件下载的监听
  private DownloadListener mDownloadListener = new DownloadListener() {
    @Override
    public void onDownloadError(int what, Exception exception) {
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
      int position = bindMap.get(what + "").intValue();
      //保存状态到数据库
      DataDownloadInfo downloadInfo = mDataDownloadDao.queryDataByPid(what + "");
      downloadInfo.setState(ActDataState.DOWNLOAD_ERROR);
      downloadInfo.setDownloadsize(Long.valueOf(currentProgress + ""));
      mDataDownloadDao.upDataInfo(downloadInfo);//更新数据库信息
      UpDataState(ActDataState.DOWNLOAD_ERROR, position, false);//下载错误
    }

    @Override
    public void onStart(int what, boolean isResume, long rangeSize, Headers responseHeaders,
        long allCount) {
      Integer integer = bindMap.get(what + "");
      int position = integer.intValue();
      //保存到数据库
      DataDownloadInfo downloadInfo = mDataDownloadDao.queryDataByPid(what + "");
      downloadInfo.setDownloadsize(currentProgress);
      downloadInfo.setState(ActDataState.DOWNLOADING);
      mDataDownloadDao.upDataInfo(downloadInfo);//更新数据库信息
      UpDataState(ActDataState.DOWNLOADING, position, false);//状态为下载中
    }

    @Override
    public void onProgress(int what, int progress, long fileCount, long speed) {
      currentProgress = progress;
      Integer integer = bindMap.get(what + "");
      int position = integer.intValue();
      RowBean bean = mData.get(position);
      bean.setProgerss(progress);
      bean.setIsdownliading(true);
      bean.setState(ActDataState.DOWNLOADING);
      bean.setSpeed(speed);
      notifyDataSetChanged();
    }

    @Override
    public void onFinish(int what, String filePath) {
      currentProgress = 100;
      DataDownloadInfo downloadInfo = mDataDownloadDao.queryDataByPid(what + "");
      downloadInfo.setDownloadsize(100);
      downloadInfo.setState(ActDataState.DOWNLOAD_COMPLETED);
      mDataDownloadDao.upDataInfo(downloadInfo);//更新数据库信息
      int position = bindMap.get(what + "").intValue();
      UpDataState(ActDataState.DOWNLOAD_COMPLETED, position, false);
    }

    @Override
    public void onCancel(int what) {
      int position = bindMap.get(what + "").intValue();
      DataDownloadInfo downloadInfo = mDataDownloadDao.queryDataByPid(what + "");
      downloadInfo.setDownloadsize(currentProgress);
      downloadInfo.setState(ActDataState.DOWNLOAD_STOP);
      mDataDownloadDao.upDataInfo(downloadInfo);
      UpDataState(ActDataState.DOWNLOAD_STOP, position, false);
    }
  };

  //取消当前所有请求
  public void cacleAllRequest() {
    mDownloadQueue.cancelAll();
  }

  //更新下载状态
  private void UpDataState(int state, int position, boolean b) {
    mData.get(position).setState(state);
    mData.get(position).setIsdownliading(b);
    notifyDataSetChanged();
  }
}
