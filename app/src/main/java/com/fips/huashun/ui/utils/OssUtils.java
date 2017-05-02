package com.fips.huashun.ui.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.fips.huashun.common.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on 2017/2/8. Oss工具类
 */
public class OssUtils {

  private OSS oss;
  private String accessid = "LTAINK07qrftjidC";
  private String accesskey = "NjMjem14R9OfBtY0ZNOX4Db12djqkl";
  String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
  private String bucketName = "huashunoapp";
  private static OssUtils instance;
  private Context mContext;
  private OSSAsyncTask mTask;
  private OnLoadoSSListener mOnLoadoSSListener;
  // private  fileUrl;
  private List<String> fileUrls = new ArrayList<>();

  private OssUtils(Context context) {
    this.mContext = context;
    initOSS();
  }

  //单例模式
  public static OssUtils getInstance(Context context) {
    if (instance == null) {
      instance = new OssUtils(context);
    }
    return instance;
  }

  public void setOnLoadoSSListener(OnLoadoSSListener onLoadoSSListener) {
    mOnLoadoSSListener = onLoadoSSListener;
  }

  //初始化OSS配置
  private void initOSS() {
    OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessid,
        accesskey);
    ClientConfiguration conf = new ClientConfiguration();
    conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
    conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
    conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
    conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
    OSSLog.enableLog();
    oss = new OSSClient(mContext, endpoint, credentialProvider, conf);
  }

  //上传到OSS
  public void ossUpload(final List<String> urls) {
    new Thread(new Runnable() {
      @Override
      public void run() {
        if (urls.size() <= 0) {
          // 文件全部上传完毕，这里编写上传结束的逻辑，如果要在主线程操作，最好用Handler或runOnUiThead做对应逻辑
          return;// 这个return必须有，防止下面报越界异常
        }
        final String url = urls.get(0);
        if (TextUtils.isEmpty(url)) {
          urls.remove(0);
          // url为空就没必要上传了，这里做的是跳过它继续上传的逻辑。
          ossUpload(urls);
          return;
        }
        File file = new File(url);
        if (null == file || !file.exists()) {
          urls.remove(0);
          // 文件为空或不存在就没必要上传了，这里做的是跳过它继续上传的逻辑。
          ossUpload(urls);
          return;
        }
        // 文件后缀
        String fileSuffix = "";
        if (file.isFile()) {
          // 获取文件后缀名
          fileSuffix = file.getName().substring(file.getName().lastIndexOf("."));
          Log.i("9999", "fileSuffix后缀名" + fileSuffix);
        }
        // 文件标识符objectKey
        final String objectKey =
            Constants.OSS_PIC_URL + PreferenceUtils.getUserId() + Utils.getRandom(1000) + Utils
                .stampToDateString(System.currentTimeMillis() + "") + fileSuffix;
        fileUrls.add(Constants.DOMAIN_NAME_URL + objectKey);//添加文件的url到集合
        //下面3个参数依次为bucket名，ObjectKey名，上传文件路径
        PutObjectRequest put = new PutObjectRequest(bucketName, objectKey, url);
        // 设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
          @Override
          public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
            // 进度逻辑
            if (mOnLoadoSSListener != null) {
              mOnLoadoSSListener.onLoadProgress(currentSize / totalSize + "");
            }
          }
        });
        // 异步上传
        OSSAsyncTask task = oss.asyncPutObject(put,
            new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
              @Override
              public void onSuccess(PutObjectRequest request, PutObjectResult result) { // 上传成功
                urls.remove(0);
                Log.i("9999", "剩余 的文件数：" + urls.size() + "返回结果：" + result.toString());
                if (urls.size() == 0 && fileUrls != null) {
                  mOnLoadoSSListener.onLoadSuccess(fileUrls);
                  fileUrls.clear();
                }
                ossUpload(urls);// 递归同步效果
              }

              @Override
              public void onFailure(PutObjectRequest request, ClientException clientExcepion,
                  ServiceException serviceException) { // 上传失败
                // 请求异常
                if (clientExcepion != null) {
                  // 本地异常如网络异常等
                  clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                  // 服务异常
                  Log.e("ErrorCode", serviceException.getErrorCode());
                  Log.e("RequestId", serviceException.getRequestId());
                  Log.e("HostId", serviceException.getHostId());
                  Log.e("RawMessage", serviceException.getRawMessage());
                }
                if (mOnLoadoSSListener != null) {
                  mOnLoadoSSListener.onLoadFailure("网络异常!");//失败
                }
              }
            });
      }
    }).start();
  }

  //上传小视频文件
  public void ossVideoUpload(List<String> pathList) {
  final List<String> UrlLists=new ArrayList<>();
    for (int i=0;i<pathList.size();i++) {
      String path=pathList.get(i);
      // 文件标识符objectKey
      final String objectKey =
          Constants.OSS_PIC_URL + PreferenceUtils.getUserId() + Utils.getRandom(1000) + Utils
              .stampToDateString(System.currentTimeMillis() + "")+path.substring(path.lastIndexOf("."));
      UrlLists.add(Constants.DOMAIN_NAME_URL + objectKey);
// 构造上传请求
      PutObjectRequest put = new PutObjectRequest(bucketName, objectKey, path);
// 异步上传时可以设置进度回调
      put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
        @Override
        public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
          Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
          if (mOnLoadoSSListener != null) {
            mOnLoadoSSListener.onLoadProgress(currentSize / totalSize + "");
          }
        }
      });
      final int finalI = i;
      OSSAsyncTask task = oss
          .asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
              if (finalI ==1&&mOnLoadoSSListener != null) {
                mOnLoadoSSListener.onVideoLoadSuccess(UrlLists);
              }
              Log.i("test", "文件的路径 ：   " + Constants.DOMAIN_NAME_URL + objectKey);
              Log.d("PutObject", "UploadSuccess");
              Log.d("ETag", result.getETag());
              Log.d("RequestId", result.getRequestId());
            }
            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion,
                ServiceException serviceException) {
              if (mOnLoadoSSListener == null) {
                mOnLoadoSSListener.onLoadFailure("网络异常!");
              }
              // 请求异常
              if (clientExcepion != null) {
                // 本地异常如网络异常等
                clientExcepion.printStackTrace();
              }
              if (serviceException != null) {
                // 服务异常
                Log.e("ErrorCode", serviceException.getErrorCode());
                Log.e("RequestId", serviceException.getRequestId());
                Log.e("HostId", serviceException.getHostId());
                Log.e("RawMessage", serviceException.getRawMessage());
              }
            }
          });
    }
  }

  //上传oss的监听接口
  public interface OnLoadoSSListener {

    void onVideoLoadSuccess(List<String> s);//视频上传成功,将视频的URl回调出去

    void onLoadSuccess(List<String> fileUrls);//图片文件上传成功上传成功

    void onLoadProgress(String s);//将进度回调出去

    void onLoadFailure(String s);//上传失败

  }


}
