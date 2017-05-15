package com.fips.huashun.ui.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import com.fips.huashun.R;
import com.fips.huashun.ui.utils.AppManager;
import com.fips.huashun.ui.utils.SystemBarTintManager;
import com.fips.huashun.widgets.LoadingDialog;
import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;


public abstract class BaseActivity extends FragmentActivity {
  /**
   * 保存上一次点击时间
   */
  public Gson mGson = new Gson();
  private SparseArray<Long> mLastClickTimes;
  private LoadingDialog loadingDialog;
  protected SystemBarTintManager tintManager;
  boolean isActive = true;// activity是否活动


  /**
   * 上一次退到后台时间
   */
  private long lastActive = 0L;
  protected Gson gson;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    //添加activity 到堆栈
    AppManager.getAppManager().addActivity(this);
    // setImmersionState(R.color.title_color);
    mLastClickTimes = new SparseArray<Long>();
    loadingDialog = new LoadingDialog(this);

    gson = new Gson();
    // 设置了全屏的界面需要排除
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

      if (isSystemBarTranclucent()) {

        initWindowFull();
      } else {

        initWindow();
      }
    }
  }

  public void setImmersionState(int res) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      //  window.setStatusBarColor(Color.TRANSPARENT);
      setTranslucentStatus(true);
      SystemBarTintManager tintManager = new SystemBarTintManager(this);
      //tintManager.setNavigationBarAlpha(0.9f);
      tintManager.setStatusBarTintEnabled(true);
//            if (this instanceof LoginActivity){
//                tintManager.setStatusBarTintResource(R.color.transparent);//通知栏所需颜色
//                return;
//            }
      tintManager.setStatusBarTintResource(res);//通知栏所需颜色
    }
  }
//			if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
//				String messge = intent.getStringExtra(KEY_MESSAGE);
//				String extras = intent.getStringExtra(KEY_EXTRAS);
//				StringBuilder showMsg = new StringBuilder();
//				showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
//				if (!ExampleUtil.isEmpty(extras)) {
//					showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
//				}
//				setCostomMsg(showMsg.toString());
//			}

  /**
   * APP字体大小，不随系统的字体大小的变化而变化的方法
   */
  @Override
  public Resources getResources() {
    Resources res = super.getResources();
    Configuration config = new Configuration();
    config.setToDefaults();
    res.updateConfiguration(config, res.getDisplayMetrics());
    return res;
  }

  @Override
  protected void onDestroy() {
    // 结束Activity&从堆栈中移除
    AppManager.getAppManager().finishActivity(this);
    mLastClickTimes = null;
    if (null != loadingDialog) {
      dimissLoadingDialog();
      loadingDialog = null; // 加快gc
    }
    super.onDestroy();

  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK) {
      finish();
    }
    return super.onKeyDown(keyCode, event);
  }

  @Override
  protected void onResume() {
    super.onResume();
    MobclickAgent.onResume(this);


  }

  @Override
  protected void onPause() {
    super.onPause();
    MobclickAgent.onPause(this);
  }

  /**
   * d 检查是否可执行点击操作 防重复点击
   *
   * @return 返回true则可执行
   */
  protected boolean checkClick(int id) {
    Long lastTime = mLastClickTimes.get(id);
    Long thisTime = System.currentTimeMillis();
    mLastClickTimes.put(id, thisTime);
    return !(lastTime != null && thisTime - lastTime < 800);
  }

  /**
   * 初始化页面元素
   */
  protected void initView() {
//		// 设置返回按钮事件
//        ((ImageView) findViewById(R.id.btn_back)).setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
  }

  public void showLoadingDialog() {
    if (null != loadingDialog) {
      loadingDialog.show(getString(R.string.loading));
    }
  }

  public void showLoadingDialog(String content) {
    if (null != loadingDialog) {
      loadingDialog.show(content);
    }
  }

  public void dimissLoadingDialog() {
    if (null != loadingDialog) {
      loadingDialog.dismiss();
    }
  }

  @TargetApi(19)
  public void setTranslucentStatus(boolean on) {
    Window win = getWindow();
    WindowManager.LayoutParams winParams = win.getAttributes();
    final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
    if (on) {
      winParams.flags |= bits;
    } else {
      winParams.flags &= ~bits;
    }
    win.setAttributes(winParams);
  }

  public abstract boolean isSystemBarTranclucent();

  @TargetApi(19)
  private void initWindowFull() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }
  }

  @TargetApi(19)
  private void initWindow() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
      tintManager = new SystemBarTintManager(this);
      tintManager.setStatusBarTintColor(getResources().getColor(R.color.title_color));
      tintManager.setStatusBarTintEnabled(true);
    }
  }

  //直接跳转Activity
  private void start(Class<? extends Activity> token) {
    Intent intent = new Intent(this, token);
    startActivity(intent);
  }
}
