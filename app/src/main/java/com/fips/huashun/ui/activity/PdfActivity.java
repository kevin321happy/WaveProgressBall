package com.fips.huashun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.common.ConstantsCode;
import com.fips.huashun.db.dao.CacheStudyTimeDao;
import com.fips.huashun.modle.dbbean.CacheStudyTimeEntity;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.Utils;
import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnPageChangeListener;
import com.loopj.android.http.RequestParams;
import com.lzy.okgo.OkGo;
import com.umeng.analytics.MobclickAgent;
import java.io.File;
import java.util.Random;
import okhttp3.Call;
import okhttp3.Response;
import org.json.JSONObject;

public class PdfActivity extends BaseActivity implements OnPageChangeListener {

  private String pdfDirectory;
  private String pdfName;
  private String courseId;
  private PDFView pdfView;
  private TextView text;
  private String pdfurl;
  private long startTime;
  private long endTime;

  private long studyTime;
  private long totalTime;
  private String url;
  private String type;
  private Handler mHandler;
  public Random random = new Random();
  private int mRandomTime1;
  private int mRandomTime;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_pdf);
    initView();
    Intent intent = getIntent();
    pdfurl = intent.getStringExtra("pdfurl");
    courseId = intent.getStringExtra("courseid");
    type = intent.getStringExtra("type");
    //目录
    pdfDirectory = Environment.getExternalStorageDirectory() +
        "/temp";
    //pdf文件名
    pdfName = courseId + "type" + type + ".pdf";
    startTime = System.currentTimeMillis();
    initData();
    //发送学习时间
    sendRandomTime();
  }

  //每个5到十分钟发送学习时间
  private void sendRandomTime() {
    if (type.equals(ConstantsCode.STRING_ZERO) || type.equals("3")) {
      Log.i("test", "发送吧少年");
      mHandler = new Handler();
      mHandler.postDelayed(runnable, mRandomTime1);
    } else {
      return;
    }
  }

  Runnable runnable = new Runnable() {
    @Override
    public void run() {
      mRandomTime1 = random.nextInt(50) + 50;
      studyTimeSend(mRandomTime / 10 + "");
      mHandler.postDelayed(this, mRandomTime1 * 6000);
      mRandomTime = mRandomTime1;
      startTime = System.currentTimeMillis();
    }
  };

  private void initData() {
    //本地已下载的pdf文件
    if (type.equals("3")) {
      displayLocalFile(pdfurl);
    } else {
      if (!TextUtils.isEmpty(pdfurl)) {
        OkGo.get(pdfurl)
//          .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
            .cacheMode(com.lzy.okgo.cache.CacheMode.FIRST_CACHE_THEN_REQUEST)
            .cacheKey(ConstantsCode.CACHE_PDF)
            .tag(this)
//          .execute(new DownloadFileCallBack(pdfDirectory, pdfName));//保存到sd卡
            .execute(new DownloadFileCallBack(pdfDirectory, pdfName));
      }
    }
  }

  @Override
  protected void initView() {
    super.initView();
    pdfView = (PDFView) findViewById(R.id.pdfView);
    text = (TextView) findViewById(R.id.text);
  }

  @Override
  public boolean isSystemBarTranclucent() {
    return false;
  }

  private void display(String assetFileName, boolean jumpToFirstPage) {
    if (jumpToFirstPage) {
      setTitle(pdfDirectory = assetFileName);
    }
    File file = new File(assetFileName, pdfName);
    if (file.exists()) {
      pdfView.fromFile(file)
          .defaultPage(1)//默认展示第一页
          .onPageChange(this)//监听页面切换
          .load();
    }
  }

  //加载本地的pdf文件
  private void displayLocalFile(String Filename) {
    File file = new File(Filename);
    if (file.exists()) {
      pdfView.fromFile(file)
          .defaultPage(1)//默认展示第一页
          .onPageChange(this)//监听页面切换
          .load();
    }
  }

  @Override
  public void onPageChanged(int page, int pageCount) {
    text.setText(page + "/" + pageCount);
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
  }

  private class DownloadFileCallBack extends com.lzy.okgo.callback.FileCallback {

    public DownloadFileCallBack(String destFileDir, String destFileName) {
      super(destFileDir, destFileName);
    }


    @Override
    public void onBefore(com.lzy.okgo.request.BaseRequest request) {
      super.onBefore(request);
      showLoadingDialog();
    }

    @Override
    public void onSuccess(File file, Call call, Response response) {
      dimissLoadingDialog();
      display(pdfDirectory, false);
    }

    @Override
    public void downloadProgress(long currentSize, long totalSize, float progress,
        long networkSpeed) {
      showLoadingDialog();
      Log.i("test", "当前的进度：" + currentSize / totalSize * 100);
    }

    @Override
    public void onError(Call call, Response response, Exception e) {
      super.onError(call, response, e);
      dimissLoadingDialog();
      display(pdfDirectory, false);
    }

    @Override
    public void onCacheError(Call call, Exception e) {
      super.onCacheError(call, e);
      dimissLoadingDialog();
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    MobclickAgent.onPageStart("PdfActivity");
  }

  @Override
  protected void onPause() {
    super.onPause();
    MobclickAgent.onPageEnd("PdfActivity");
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    endTime = System.currentTimeMillis();
    if (endTime - startTime > 0) {
      startTime = endTime - startTime;
      totalTime += startTime;
    }
    //发送学习时间
    if (totalTime > 1000 * 30){
      if (type.equals(ConstantsCode.STRING_ZERO) || type.equals("3")) {
        if (totalTime > 1000 * 60 * 60) {
          totalTime = 1000 * 60 * 60;
        }
        studyTimeSend(String.valueOf(totalTime / 60000));
      }
    }
    startTime = 0;
    startTime = 0;
    endTime = 0;
    //移除任务
    if (mHandler != null) {
      mHandler.removeCallbacks(runnable);
    }
  }

  private void studyTimeSend(final String stutime) {
    if (courseId == null) {
      return;
    }
    //如果网络不可用,保存数据到本地
    if (!Utils.isNetWork(this)) {
      CacheStudyTimeDao studyTimeDao = new CacheStudyTimeDao(this);
      CacheStudyTimeEntity studyTimeEntity = new CacheStudyTimeEntity();
      studyTimeEntity.setCourseId(courseId);
      studyTimeEntity.setUid(PreferenceUtils.getUserId());
      studyTimeEntity.setStudyTime(stutime);
      studyTimeDao.addRedord(studyTimeEntity);
      Log.i("test", "保存时间了" + studyTimeEntity.toString());

    } else {
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
  }
}
