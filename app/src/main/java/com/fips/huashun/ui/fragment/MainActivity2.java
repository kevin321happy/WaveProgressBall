package com.fips.huashun.ui.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import com.fips.huashun.ApplicationEx;
import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.db.dao.CacheStudyTimeDao;
import com.fips.huashun.modle.dbbean.CacheStudyTimeEntity;
import com.fips.huashun.ui.activity.BaseActivity;
import com.fips.huashun.ui.activity.LoginActivity;
import com.fips.huashun.ui.utils.AlertDialogUtils;
import com.fips.huashun.ui.utils.AppManager;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.Utils;
import com.fips.huashun.widgets.DMFragmentTabHost;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.qihoo.appstore.common.updatesdk.lib.UpdateHelper;
import com.umeng.analytics.MobclickAgent;
import java.util.List;
import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by lgpeng on 2016/2/16 0016.
 */
public class MainActivity2 extends BaseActivity {

  /**
   * 指定当前在哪一页
   */
  public static int index;
  public static String Typeid;

  public static DMFragmentTabHost mTabHost = null;
  private String[] titles = null;
  private Class[] clazzs = {FragmentHome.class, FragmentClass.class, FragmentMe.class};
  private int[] imagesR = {R.drawable.table_image, R.drawable.table_image2,
      R.drawable.table_image3};
//    private String appversion = "1.0.1";
  /**
   * 按返回键时的时间
   */
  private long mExitTime;
  public static final String MAIN_PAGE_TAB_CHANGED = "main_page_tab_changed";
  public static final String MAIN_PAGE_INDEX = "main_page_index";
  /**
   * 加载进度
   */
  private ProgressDialog progressDialog;
  private String preTab;
  private String version;
  private CacheStudyTimeDao mStudyTimeDao;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    setContentView(R.layout.activity_main2);
    //权限申请
    updateApp();
    index = getIntent().getIntExtra("index", 0);
    mTabHost = (DMFragmentTabHost) findViewById(R.id.tabhost);
    mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
    mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
    titles = getResources().getStringArray(R.array.bottom_titles);
    // 添加tab名称和图标
    for (int i = 0; i < titles.length; i++) {
      String title = titles[i];
      Class clazz = clazzs[i];
      String tabSpec = "tabSpec" + i;
      View indicator = getIndicatorView(title, R.layout.common_tab_indicator, imagesR[i]);
      mTabHost.addTab(mTabHost.newTabSpec(tabSpec).setIndicator(indicator), clazz, null);
    }
    mTabHost.getTabWidget().getChildAt(2).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mTabHost.setCurrentTab(2);
      }
    });
    mTabHost.getTabWidget().getChildAt(1).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (!ApplicationEx.getInstance().isLogined()) {
          Intent intent = new Intent(MainActivity2.this, LoginActivity.class);
          intent.putExtra("page", "1");
          startActivityForResult(intent, 9);
        } else {
          mTabHost.setCurrentTab(1);
        }
      }
    });

    mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
      @Override
      public void onTabChanged(String tabId) {
        preTab = tabId;
        index = mTabHost.getCurrentTab();
      }
    });
    mTabHost.setCurrentTab(index);
    checkNetState();
  }

//    //权限申请的回调
//    @Override
//    public void onPermissionsGranted(int requestCode, List<String> perms) {
//        //成功
//       // updateApp();
//    }

//    @Override
//    public void onPermissionsDenied(int requestCode, List<String> perms) {
//        ToastUtil.getInstant().show("不具备权限，无法更新");
//    }


  @Override
  protected void onResume() {
    super.onResume();
    mTabHost.setCurrentTab(index);
    MobclickAgent.onPageStart("MainActivity2");
  }

  protected void onPause() {
    super.onPause();
    MobclickAgent.onPageEnd("MainActivity2");
  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    if (intent != null) {
      index = intent.getIntExtra("index", 0);
    }
    mTabHost.setCurrentTab(index);
  }

  private View getIndicatorView(String name, int layoutId, int drawableId) {
    View v = getLayoutInflater().inflate(layoutId, null);
    TextView tv = (TextView) v.findViewById(R.id.tabText);
    tv.setText(name);
    ImageView imageView = (ImageView) v.findViewById(R.id.tabImage);
    imageView.setImageResource(drawableId);
    return v;
  }

  @Override
  public boolean isSystemBarTranclucent() {
    return false;
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    //按两次返回退出应用程序
    if (keyCode == KeyEvent.KEYCODE_BACK) {
      if ((System.currentTimeMillis() - mExitTime) > 2000) {
        Toast.makeText(getApplicationContext(), R.string.app_exit, Toast.LENGTH_SHORT).show();
        mExitTime = System.currentTimeMillis();
      } else {
        PreferenceUtils.setBoolean(this, "isShowedAuth", false);
        AppManager.getAppManager().AppExit(this);
      }
      return true;
    }
    return super.onKeyDown(keyCode, event);
  }

  private void checkNetState() {
    if (!Utils.isNetWork(this)) {
      AlertDialogUtils.showTowBtnDialog(this, "当前网络不可用，是否打开网络设置?", "取消", "确认",
          new AlertDialogUtils.DialogClickInter() {
            @Override
            public void leftClick(android.app.AlertDialog dialog) {
              dialog.cancel();
            }
            @Override
            public void rightClick(android.app.AlertDialog dialog) {
              if (android.os.Build.VERSION.SDK_INT > 10) {
                startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
              } else {
                startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
              }
            }
          });
    } else {
      //检查学习缓存
      checkStudyCache();
//      Log.i("test","网络可用的");
    }
  }

  private void checkStudyCache() {
    new Thread(new Runnable() {
      @Override
      public void run() {
        Log.i("test","网络可用的1");
        if (mStudyTimeDao==null){
          mStudyTimeDao = new CacheStudyTimeDao(getApplicationContext());
          List<CacheStudyTimeEntity> cacheStudyTimeEntities = mStudyTimeDao.queryAllRecord();
          Log.i("test","网络可用的2");
          if (cacheStudyTimeEntities!=null&&cacheStudyTimeEntities.size()>0){
            Log.i("test","网络可用的3");
            for (CacheStudyTimeEntity timeEntity : mStudyTimeDao.queryAllRecord()) {
              Log.i("test","网络可用的4");
              Log.i("test",timeEntity.toString());
            }
          }
        }
        List<CacheStudyTimeEntity> studyTimeEntities = mStudyTimeDao.queryAllRecord();
        if (studyTimeEntities != null && studyTimeEntities.size() > 0.) {
          for (CacheStudyTimeEntity timeEntity : studyTimeEntities) {
            sendStudyTime(timeEntity);
          }
        }
      }
    }).start();
  }

  //发送学习时长
  private void sendStudyTime(final CacheStudyTimeEntity timeEntity) {
    String studytime = timeEntity.getStudyTime();
    String uid = timeEntity.getUid();
    final String courseid = timeEntity.getCourseId();
    OkGo.post(Constants.ADDSTUINFO)
        .params("userid", uid)
        .params("courseid", courseid)
        .params("stutime", studytime)
        .execute(new StringCallback() {
          @Override
          public void onSuccess(String s, Call call, Response response) {
            Log.i("test","发送缓存数据成功"+timeEntity.toString());
            //删除缓存记录
            mStudyTimeDao.deleteRecord(timeEntity);
          }
        });


  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

  }

  private void updateApp() {
    String packageName = "com.fips.huashun";
    if (!TextUtils.isEmpty(packageName)) {
      UpdateHelper.getInstance().init(getApplicationContext(), Color.parseColor("#4DB80E"));
      UpdateHelper.getInstance().setDebugMode(false);
      long intervalMillis = 10 * 1000L;           //第一次调用startUpdateSilent出现弹窗后，如果10秒内进行第二次调用不会查询更新
      UpdateHelper.getInstance().autoUpdate(packageName, false, intervalMillis);
    }
  }
}

