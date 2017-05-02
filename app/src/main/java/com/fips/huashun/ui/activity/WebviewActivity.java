package com.fips.huashun.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.SystemBarTintManager;
import com.umeng.analytics.MobclickAgent;


public class WebviewActivity extends BaseActivity {

  private WebView webview;
  private int key;
  private String entid;
  private String activityId;
  // 课程章节ID
  private String sessoinid;
  private NavigationBar navigationBar;
  String condition = "";
  private String posstion;
  private String link;
  private String type;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      setTranslucentStatus(true);
      SystemBarTintManager tintManager = new SystemBarTintManager(this);
      tintManager.setStatusBarTintEnabled(true);
      tintManager.setStatusBarTintResource(R.color.transparent);//通知栏所需颜色
    }
    setContentView(R.layout.activity_webview);
    key = getIntent().getIntExtra("key", -1);
    entid = getIntent().getStringExtra("entid");
    activityId = getIntent().getStringExtra("activityId");
    sessoinid = getIntent().getStringExtra("sessoinid");
    posstion = getIntent().getStringExtra("position");
    link = getIntent().getStringExtra("link");
    type = getIntent().getStringExtra("type");
    initView();
  }

  @Override
  public boolean isSystemBarTranclucent() {
    return false;
  }

  @Override
  protected void initView() {
    super.initView();
    navigationBar = (NavigationBar) findViewById(R.id.na_bar);

    navigationBar.setLeftImage(R.drawable.course_reslt_back);
    navigationBar.setListener(new NavigationBar.NavigationListener() {
      @Override
      public void onButtonClick(int button) {
        if (button == NavigationBar.LEFT_VIEW) {
          if (webview.canGoBack()) {
            webview.goBack();
          } else {
            finish();
          }
        }
      }
    });
    webview = (WebView) findViewById(R.id.webview);
    WebSettings webSettings = webview.getSettings();
    webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
    webSettings.setUseWideViewPort(true);
    webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
    webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
    webSettings.setLoadWithOverviewMode(true);
    webview.setWebChromeClient(new WebChromeClient());
    if (key != -1) {
      switch (key) {
        case 0:
          navigationBar.setTitle("企业文化");
          Log.e("entid==", "entid=" + entid);
          webview.loadUrl(Constants.H5_URL + "companyCulture.html?entid=" + entid);
          break;
        case 1:
          navigationBar.setTitle("组织架构");
          webview.loadUrl(Constants.H5_URL + "companyArchite.html?entid=" + entid);
          break;
        case 2:
          navigationBar.setTitle("企业部门");
          webview.loadUrl(
              Constants.H5_URL + "department.html" + "?entid=" + entid + "&deptid=" + activityId);
          break;
        case 6:
          //navigationBar.setTitle("学习地图");
          navigationBar.setVisibility(View.GONE);
          webview.loadUrl(
              Constants.H5_URL + "studyMap.html?userid=" + PreferenceUtils.getUserId() + "&entid="
                  + entid);
          break;
        case 8:
          navigationBar.setTitle("能力模型");
          webview.loadUrl(
              Constants.H5_URL + "capacity_model.html?userid=" + PreferenceUtils.getUserId());
          break;
        case 9:
          navigationBar.setVisibility(View.GONE);
          Log.e("activityId", "考试=" + activityId);
          webview.loadUrl(Constants.H5_URL + "start_exam.html?userid=" + PreferenceUtils.getUserId()
              + "&activityid=" + activityId);
          break;
        case 10: //评估
          navigationBar.setVisibility(View.GONE);
          webview.loadUrl(
              Constants.H5_URL + "questionnaire.html?userid=" + PreferenceUtils.getUserId()
                  + "&activityid=" + activityId);
          break;
        case 11:
          //企业我的课程考试
          navigationBar.setVisibility(View.GONE);
          Log.e("activityId", "企业我的课程考试=" + activityId);
          webview.loadUrl(Constants.H5_URL + "start_exam.html?userid=" + PreferenceUtils.getUserId()
              + "&activityid=" + activityId + "&courseid=" + sessoinid);//sessoinid其实就是courseid
          break;
        case 12:
          //banner
          if (TextUtils.isEmpty(link)) {
            webview.loadUrl(Constants.H5_URL + "banner.html?cardid=" + posstion);
            Log.e("URL", Constants.H5_URL + "banner.html?cardid=" + posstion);
          } else {
            webview.loadUrl(link);
            Log.e("URL", link);
          }
          break;
        case 13:
          webview.loadUrl("http://www.52qmct.com");
          break;
        case 14:
          //关于我们
          navigationBar.setTitle("关于我们");
          webview.loadUrl(Constants.H5_URL + "aboutUs.html");
          break;
        case 15:
          // 课程章节文档
          if (sessoinid.equals("652")) {
            navigationBar.setTitle("直播");
            //直播
            webview.loadUrl("http://www.ainemo.com/live/v/ff80808158d62e4f01594e1311060a21");
            return;
          }
          if (sessoinid.equals("653")) {
            navigationBar.setTitle("找茬");
            //游戏
            webview.loadUrl("http://lanny.applinzi.com");
            return;
          }
          if (sessoinid.equals("654")) {
            navigationBar.setTitle("找茬2");
            webview.loadUrl("http://2.lanny.applinzi.com");
            return;
          }
          navigationBar.setTitle("课程章节");
          webview.loadUrl(
              Constants.H5_URL + "chapterInfo.html?courseid=" + activityId + "&sessoinid="
                  + sessoinid);
          Log.e("url", Constants.H5_URL + "chapterInfo.html?courseid=" + activityId + "&sessoinid="
              + sessoinid);
          break;
        case 16:
          //问卷的id
          navigationBar.setVisibility(View.GONE);
          String url =
              Constants.H5_URL + "research.html?userid=" + PreferenceUtils.getUserId() + "&id="
                  + sessoinid + "&type=" + type;
          webview.loadUrl(
              Constants.H5_URL + "research.html?userid=" + PreferenceUtils.getUserId() + "&id="
                  + sessoinid + "&type=" + type);
          break;
      }
    }
    webview.setWebViewClient(new WebViewClient() {
      @Override
      public void onPageFinished(WebView view, String url) {
//                webview.loadUrl("javascript:App.resize(document.body.getBoundingClientRect().height)");
        super.onPageFinished(view, url);
        dimissLoadingDialog();
        if (14 == key || 2 == key) {
          navigationBar.setVisibility(View.GONE);
        }
      }

      @Override
      public boolean shouldOverrideUrlLoading(
          WebView view, String url) {
        view.loadUrl(url);
        Log.e("333", url);
        if (url.contains("www.app.com")) {
          view.loadUrl(
              Constants.H5_URL + "capacity_model.html?userid=" + PreferenceUtils.getUserId());
          int indexStart = url.indexOf("=");
          condition = url.substring(indexStart + 1);
          Intent intent = new Intent(WebviewActivity.this, CourseResultActivity.class);
          intent.putExtra("page", "1"); //方便 课程页面的返回调用
          intent.putExtra("condition", condition);
          intent.putExtra("eid", entid);
          startActivityForResult(intent, 1024);
        }
        if (url.contains("www.course.com")) {
          int indexStart = url.indexOf("=");
          String courseid = url.substring(indexStart + 1);
          Intent intent = new Intent(WebviewActivity.this, CourseDetailActivity.class);
          intent.putExtra("courseId", courseid); //方便 课程页面的返回调用
          startActivity(intent);
          finish();
        }
        if (url.contains("www.baidu.com")) {
          finish();
        }
        return true;
      }

      @Override
      public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
         showLoadingDialog();
      }
    });
//        webview.loadUrl(Constants.H5_URL+"companyCulture.html"+"?entid=15");
//        webview.loadUrl(Constants.H5_URL+"companyArchite.html"+"?entid=15");
//        webview.loadUrl(Constants.H5_URL+"department.html"+"?enid=15"+"&depid=2");
//        webview.loadUrl(Constants.H5_URL+"studyMap.html"+"?uid=4");
//        webview.loadUrl(Constants.H5_URL+"capacity_model.html"+"?uesrid=4");

  }

  @Override
  protected void onResume() {
    super.onResume();
    MobclickAgent.onPageStart("WebviewActivity");
    webview.onResume();
  }

  @Override
  protected void onPause() {
    super.onPause();
    MobclickAgent.onPageEnd("WebviewActivity");
    webview.onPause();
//        if (0==key){
//            webview.reload();
//        }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    webview.destroy();
//        webview.clearCache(true);
  }

  @Override
  public void finish() {
    ViewGroup view = (ViewGroup) getWindow().getDecorView();
    view.removeAllViews();
    super.finish();
  }
}

