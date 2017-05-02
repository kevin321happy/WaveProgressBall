package com.fips.huashun.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.fips.huashun.R;
import com.fips.huashun.common.ACache;
import com.fips.huashun.common.CacheConstans;
import com.fips.huashun.common.Constants;
import com.fips.huashun.ui.fragment.MainActivity2;
import com.fips.huashun.ui.utils.AlertDialogUtils;
import com.fips.huashun.ui.utils.AlertDialogUtils.DialogClickInter;
import com.fips.huashun.ui.utils.DataCleanManager;
import com.fips.huashun.ui.utils.FirstEvent;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.SPUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.qihoo.appstore.common.updatesdk.lib.UpdateHelper;
import com.umeng.analytics.MobclickAgent;
import de.greenrobot.event.EventBus;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import okhttp3.Call;
import okhttp3.Response;
import org.json.JSONObject;

/**
 * 功能：系统设置 Created by Administrator on 2016/8/26.
 *
 * @author 张柳 时间：2016年8月26日11:07:13
 */
public class SystemSettingsActivity extends BaseActivity implements View.OnClickListener {

  private NavigationBar navigationBar;
  private LinearLayout updatePwdLayout, aboutAppLayout, scoreLayout, feedbackLayout, contactWeLayout, updateAppLayout;
  private TextView tv_cash;
  // 文件分隔符
  private static final String FILE_SEPARATOR = "/";
  // 外存sdcard存放路径
  private static final String FILE_PATH =
      Environment.getExternalStorageDirectory() + FILE_SEPARATOR + "autoupdate" + FILE_SEPARATOR;
  // 下载应用存放全路径
  private static final String FILE_NAME = FILE_PATH + "qiuma.apk";
  // 更新应用版本标记
  private static final int UPDARE_TOKEN = 0x29;
  // 准备安装新版本应用标记
  private static final int INSTALL_TOKEN = 0x31;
  private String message = "检测到本程序有新版本发布，建议您更新！";
  // 以华为天天聊hotalk.apk为例
  private String spec = "http://222.42.1.209:81/1Q2W3E4R5T6Y7U8I9O0P1Z2X3C4V5B/mt.hotalk.com:8080/release/hotalk1.9.17.0088.apk";
  // 下载应用的对话框
  private Dialog dialog;
  // 下载应用的进度条
  private ProgressBar progressBar;
  // 进度条的当前刻度值
  private int curProgress;
  // 用户是否取消下载
  private boolean isCancel;
  private String totalCacheSize;
  private TextView mTv_current_version;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_systemsettings);
    initView();
  }

  @Override
  protected void initView() {
    super.initView();
    //注册EventBus
    EventBus.getDefault().register(this);
    updatePwdLayout = (LinearLayout) findViewById(R.id.ll_updatepwd);
    aboutAppLayout = (LinearLayout) findViewById(R.id.ll_aboutapp);
    scoreLayout = (LinearLayout) findViewById(R.id.ll_score);
    feedbackLayout = (LinearLayout) findViewById(R.id.ll_feedback);
    contactWeLayout = (LinearLayout) findViewById(R.id.ll_contact_we);
    updateAppLayout = (LinearLayout) findViewById(R.id.ll_update_app);
    mTv_current_version = (TextView) findViewById(R.id.tv_current_version);
    navigationBar = (NavigationBar) findViewById(R.id.nb_systemsettings);
    navigationBar.setTitle("系统设置");
    navigationBar.setRightText("退出登录");
    navigationBar.setLeftImage(R.drawable.fanhui);
    //获取APP的Viewsionname
    String versionName = getAppVersionName();
    if (versionName!=null){
      mTv_current_version.setText("当前版本为: "+versionName);
    }else {
      mTv_current_version.setVisibility(View.GONE);
    }
    navigationBar.setListener(new NavigationBar.NavigationListener() {
      @Override
      public void onButtonClick(int button) {
        if (button == NavigationBar.LEFT_VIEW) {
          finish();
        } else if (button == NavigationBar.RIGHT_VIEW) {
          //清除设备Id
          clearRegistrationid(PreferenceUtils.getUserId());
          PreferenceUtils.setUserId("");
          PreferenceUtils.setUserInfoBean("");
          PreferenceUtils.setReadstatus("");
          //友盟统计退出登陆
          MobclickAgent.onProfileSignOff();
          SPUtils.clearInfo(SystemSettingsActivity.this,"entstate");
          //清除企业部分的缓存
          ACache.get(SystemSettingsActivity.this).remove(CacheConstans.OWNENTERPISE_INFO_JSON);
          JSONObject asJSONObject = ACache.get(SystemSettingsActivity.this)
              .getAsJSONObject(CacheConstans.OWNENTERPISE_INFO_JSON);
          finish();
          MainActivity2.index = 0;
        }
      }
    });
    tv_cash = (TextView) findViewById(R.id.tv_cash);
    updatePwdLayout.setOnClickListener(this);
    aboutAppLayout.setOnClickListener(this);
    scoreLayout.setOnClickListener(this);
    feedbackLayout.setOnClickListener(this);
    contactWeLayout.setOnClickListener(this);
    updateAppLayout.setOnClickListener(this);
    findViewById(R.id.ll_cleanCash).setOnClickListener(this);
    try {
      totalCacheSize = DataCleanManager.getTotalCacheSize(SystemSettingsActivity.this);
      tv_cash.setText(totalCacheSize);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  //退出时清除设备ID
  private void clearRegistrationid(String userId) {
    OkGo.post(Constants.LOCAL_RE_REGISTRATIONID)
        .params("uid", userId)
        .execute(new StringCallback() {
          @Override
          public void onSuccess(String s, Call call, Response response) {

          }
        });
  }
  /**
   * 返回当前程序版本名
   */
  public  String getAppVersionName() {
    String versionName = "";
    try {
      PackageManager pm = this.getPackageManager();
      PackageInfo pi = pm.getPackageInfo(this.getPackageName(), 0);
      versionName = pi.versionName;
      if (versionName == null || versionName.length() <= 0) {
        return "";
      }
    } catch (Exception e) {
    }
    return versionName;
  }

  @Override
  public void onClick(View v) {
    Intent intent = null;
    switch (v.getId()) {
      case R.id.ll_updatepwd:
        intent = new Intent(this, UpdatePwdActivity.class);
        startActivity(intent);
        break;
      case R.id.ll_aboutapp:
        intent = new Intent(this, WebviewActivity.class);
        intent.putExtra("key", 14);//关于我们
        startActivity(intent);
        break;
      case R.id.ll_score:
        intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://www.52qmct.com"));
        startActivity(intent);
        break;
      case R.id.ll_feedback:
        intent = new Intent(this, FeedbackActivity.class);
        startActivity(intent);
        break;
      case R.id.ll_update_app:
        //检查更新
        String packageName = "com.fips.huashun";
        if (!TextUtils.isEmpty(packageName)) {
          UpdateHelper.getInstance().init(getApplicationContext(), Color.parseColor("#4DB80E"));
          UpdateHelper.getInstance().setDebugMode(false);
          UpdateHelper.getInstance().manualUpdate(packageName);
        }
        break;
      case R.id.ll_cleanCash:
        try {
          if (!DataCleanManager.getTotalCacheSize(SystemSettingsActivity.this).equals("0.0Byte")) {
            DataCleanManager.clearAllCache(this);
          }
          tv_cash.setText(DataCleanManager.getTotalCacheSize(this));
        } catch (Exception e) {
          e.printStackTrace();
        }
        break;
      case R.id.ll_contact_we:
        AlertDialogUtils.showTowBtnDialog(this, "服务热线：027-83359162", "取消", "拨打",
            new DialogClickInter() {
              @Override
              public void leftClick(AlertDialog dialog) {
                dialog.dismiss();
              }

              @Override
              public void rightClick(AlertDialog dialog) {
                dialog.dismiss();
                contactwe();
              }
            });
        break;
    }

  }

  //联系我们
  private void contactwe() {
    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:027-83359162"));
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
        != PackageManager.PERMISSION_GRANTED) {
      // TODO: Consider calling
      //    ActivityCompat#requestPermissions
      // here to request the missing permissions, and then overriding
      //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
      //                                          int[] grantResults)
      // to handle the case where the user grants the permission. See the documentation
      // for ActivityCompat#requestPermissions for more details.
      return;
    }
    startActivity(intent);
  }

  public void onEventMainThread(FirstEvent event) {

    finish();
  }

  @Override
  public boolean isSystemBarTranclucent() {
    return false;
  }

  /**
   * 显示提示更新对话框
   */
  private void showNoticeDialog() {
    new AlertDialog.Builder(SystemSettingsActivity.this)
        .setTitle("软件版本更新")
        .setMessage(message)
        .setPositiveButton("下载", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            showDownloadDialog();
          }
        }).setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
      }
    }).create().show();
  }

  /**
   * 显示下载进度对话框
   */
  private void showDownloadDialog() {
    View view = LayoutInflater.from(SystemSettingsActivity.this)
        .inflate(R.layout.dailog_progressbar, null);
    progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
    AlertDialog.Builder builder = new AlertDialog.Builder(SystemSettingsActivity.this);
    builder.setTitle("软件版本更新");
    builder.setView(view);
    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
        isCancel = true;
      }
    });
    dialog = builder.create();
    dialog.show();
    downloadApp();
  }

  /**
   * 下载新版本应用
   */
  private void downloadApp() {
    new Thread(new Runnable() {
      @Override
      public void run() {
        URL url = null;
        InputStream in = null;
        FileOutputStream out = null;
        HttpURLConnection conn = null;
        try {
          url = new URL(spec);
          conn = (HttpURLConnection) url.openConnection();
          conn.connect();
          long fileLength = conn.getContentLength();
          in = conn.getInputStream();
          File filePath = new File(FILE_PATH);
          if (!filePath.exists()) {
            filePath.mkdir();
          }
          out = new FileOutputStream(new File(FILE_NAME));
          byte[] buffer = new byte[1024];
          int len = 0;
          long readedLength = 0l;
          while ((len = in.read(buffer)) != -1) {
            // 用户点击“取消”按钮，下载中断
            if (isCancel) {
              break;
            }
            out.write(buffer, 0, len);
            readedLength += len;
            curProgress = (int) (((float) readedLength / fileLength) * 100);
            handler.sendEmptyMessage(UPDARE_TOKEN);
            if (readedLength >= fileLength) {
              dialog.dismiss();
              // 下载完毕，通知安装
              handler.sendEmptyMessage(INSTALL_TOKEN);
              break;
            }
          }
          out.flush();
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          if (out != null) {
            try {
              out.close();
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
          if (in != null) {
            try {
              in.close();
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
          if (conn != null) {
            conn.disconnect();
          }
        }
      }
    }).start();
  }

  /**
   * 安装新版本应用
   */
  private void installApp() {
    File appFile = new File(FILE_NAME);
    if (!appFile.exists()) {
      return;
    }
    // 跳转到新版本应用安装页面
    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setDataAndType(Uri.parse("file://" + appFile.toString()),
        "application/vnd.android.package-archive");
    startActivity(intent);
  }

  private final Handler handler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      switch (msg.what) {
        case UPDARE_TOKEN:
          progressBar.setProgress(curProgress);
          break;

        case INSTALL_TOKEN:
          installApp();
          break;
      }
    }
  };

  @Override
  protected void onDestroy() {
    super.onDestroy();
    EventBus.getDefault().unregister(this);//反注册EventBus
  }

  @Override
  protected void onResume() {
    super.onResume();
    MobclickAgent.onPageStart("SystemSettingsActivity");
  }

  @Override
  protected void onPause() {
    super.onPause();
    MobclickAgent.onPageEnd("SystemSettingsActivity");
  }
}
