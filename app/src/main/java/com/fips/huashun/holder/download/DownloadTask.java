package com.fips.huashun.holder.download;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import com.fips.huashun.modle.dbbean.DataDownloadInfo;
import com.fips.huashun.ui.utils.ThreadPoolUtils;
import com.fips.huashun.ui.utils.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.request.BaseRequest;
import java.io.File;
import okhttp3.Call;
import okhttp3.Response;


/**
 * 下载任务
 */
public class DownloadTask extends ThreadPoolUtils.Task {

  private final DataDownloadInfo downloadInfo;

//  public DownloadTask(Long id) {
//    this.id = id;
//    downloadInfo = DownloadManager.DOWNLOAD_INFO_HASHMAP.get(id);
//  }

  public DownloadTask(DataDownloadInfo downloadInfo) {
    this.downloadInfo = downloadInfo;
  }

  /*  OkHttpUtils.getInstance().setCacheMode(CacheMode.IF_NONE_CACHE_REQUEST);
        OkHttpUtils.get(pdfurl)
            .cacheMode(CacheMode.IF_NONE_CACHE_REQUEST)
            .cacheKey(ConstantsCode.CACHE_PDF)
            .tag(this)
            .execute(new DownloadFileCallBack(Environment.getExternalStorageDirectory() +
                "/temp", "qcl.pdf"));//保存到sd卡
      }
  * */
  @Override
  protected void work() {
    //下载活动资料
    downloadActData();
  }

  //下载活动资料
  private void downloadActData() {
    String dowloadUrl = downloadInfo.getDowloadUrl();
    Log.i("test","路径 ："+dowloadUrl);
    ToastUtil.getInstant().show("路径 ："+dowloadUrl);
    //执行下载任务
    if (dowloadUrl != null) {
      OkGo.get(dowloadUrl)
//          .cacheMode(CacheMode.IF_NONE_CACHE_REQUEST)
//          .cacheKey("22")

          .execute(new DownloadFileCallBack(Environment.getExternalStorageDirectory() +
              "/kecheng", dowloadUrl.substring(dowloadUrl.length() - 6, dowloadUrl.length())));
    }
  }

  //文件下载的监听
  public class DownloadFileCallBack extends FileCallback {

    public DownloadFileCallBack(@NonNull String destFileDir, @NonNull String destFileName) {
      super(destFileDir, destFileName);
    }

    @Override
    public void onBefore(BaseRequest request) {
      super.onBefore(request);
    }

    @Override
    public void onSuccess(File file, Call call, Response response) {

    }

    //下载进度
    @Override
    public void downloadProgress(long currentSize, long totalSize, float progress,
        long networkSpeed) {
      super.downloadProgress(currentSize, totalSize, progress, networkSpeed);
      ToastUtil.getInstant().show("进度 ："+progress);
    }
    @Override
    public void onError(Call call, Response response, Exception e) {
      super.onError(call, response, e);
    }
  }

  @NonNull
  private String getUrl() {
    //http://localhost:8080/GooglePlayServer/download?name=app/com.itheima.www/com.itheima.www.apk&range=0
    return null;
  }

  public void setState(int state) {

  }
}