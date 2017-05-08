package com.fips.huashun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioButton;
import butterknife.ButterKnife;
import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.common.ConstantsCode;
import com.fips.huashun.modle.bean.EnterMyCourseInfo;
import com.fips.huashun.modle.bean.EnterMyCourseInfo.EnterMyCourse;
import com.fips.huashun.ui.adapter.EnterpriseMycourseAdapter;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.NavigationBar.NavigationListener;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.ToastUtil;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.BaseRequest;
import com.umeng.analytics.MobclickAgent;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 功能：企业 我的课程展示 Created by Administrator on 2016/8/22.
 * @author 张柳 时间：2016年8月22日18:28:49
 */
public class EnterpriseMycourseActivity extends BaseActivity implements OnClickListener{
  private PullToRefreshListView pullToRefreshListView;
  //列表
  private ListView mListView;
  // 适配器
  private EnterpriseMycourseAdapter enterpriseMycourseAdapter;
  private ArrayList<RadioButton> rb;
  private RadioButton view1;
  private RadioButton view2;
  private Gson gson;
  private ToastUtil toastUtil;
  private Intent dataIntent;
  private String enterpriseId;
  // 1 必修课程   2选修课程
  private int type;
  private List<EnterMyCourse> mMycoursedata;
//  private List<EnterMyCourse> mMycoursedata;
//  private RadioButton view3;
//  private ExpandableListView mEx_listview;
//  private CourseNameDao mCourseNameDao;
//  private SectionDownloadDao mSectionDownloadDao;
//  private List<String> mGroup;
//  private Map<String, List<CourseSectionEntity>> mMap;
//  private HaveDownloadedAdapter mDownloadedAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_enterprise_mycourse);
    ButterKnife.bind(this);
    gson = new Gson();
    toastUtil = ToastUtil.getInstant();
    dataIntent = getIntent();
    enterpriseId = dataIntent.getStringExtra("enterpriseId");
    String s = dataIntent.getStringExtra("type");

    if (s != null) {
      type = s.equals("0") ? 0 : 1;
    }
    initView();
    showLoadingDialog();
    initData();
  }

  @Override
  protected void initView() {
    super.initView();
    NavigationBar navigationBar = (NavigationBar) findViewById(R.id.na_bar_enterprise_mycourse);
    navigationBar.setTitle("我的课程");
    navigationBar.setLeftImage(R.drawable.fanhui);
    navigationBar.setListener(new NavigationListener() {
      @Override
      public void onButtonClick(int button) {
        if (button == NavigationBar.LEFT_VIEW) {
          finish();
        }
      }
    });
    pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.lv_enterprise_mycourse);
//    mEx_listview = (ExpandableListView) findViewById(R.id.ex_listview);
//    mEx_listview.setGroupIndicator(null);//取消自带的箭头
    mListView = pullToRefreshListView.getRefreshableView();
    // 两端刷新
    pullToRefreshListView.setMode(Mode.BOTH);
    enterpriseMycourseAdapter = new EnterpriseMycourseAdapter(this);
    enterpriseMycourseAdapter.setType(type + "");
    mListView.setAdapter(enterpriseMycourseAdapter);
    pullToRefreshListView
        .setOnRefreshListener(new OnRefreshListener2<ListView>() {
          @Override
          public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
            initData();
          }

          @Override
          public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
            initData();
          }
        });
    mListView.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(EnterpriseMycourseActivity.this, CourseDetailActivity.class);
        intent.putExtra("courseId", enterpriseMycourseAdapter.getItem(position - 1).getCourseId());
        startActivity(intent);
      }
    });
    view1 = (RadioButton) findViewById(R.id.rb_left_enterprise_mycourse);
    view2 = (RadioButton) findViewById(R.id.rb_right_enterprise_mycourse);

    rb = new ArrayList<RadioButton>();
    rb.add(view1);
    rb.add(view2);
    rb.get(0).setChecked(true);//设置进入页面第一次显示
    view1.setOnClickListener(this);
    view2.setOnClickListener(this);
  }

  // 获取数据
  private void initData() {
    OkGo.post(Constants.ENTERPRISE_MUSTMYENTERPRISE_URL)
        .tag(this)
        .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
        .cacheKey(ConstantsCode.CACHE_ENT_MYCOURSE)
        .params("entid", enterpriseId)
        .params("userid", PreferenceUtils.getUserId())
        .params("type", String.valueOf(type))
        .execute(new StringCallback() {
          @Override
          public void onBefore(BaseRequest request) {
            super.onBefore(request);
          }

          @Override
          public void onSuccess(String s, Call call, Response response) {
            dimissLoadingDialog();
            EnterMyCourseInfo enterMyCourseInfo = mGson.fromJson(s, EnterMyCourseInfo.class);
            mMycoursedata = enterMyCourseInfo.getData();
            enterpriseMycourseAdapter.setListItems(mMycoursedata);
            enterpriseMycourseAdapter.setType(type + "");
            enterpriseMycourseAdapter.notifyDataSetChanged();
            // 结束刷新
            pullToRefreshListView.onRefreshComplete();
          }

          @Override
          public void onCacheSuccess(String s, Call call) {
            super.onCacheSuccess(s, call);
            EnterMyCourseInfo enterMyCourseInfo = mGson.fromJson(s, EnterMyCourseInfo.class);
            mMycoursedata = enterMyCourseInfo.getData();
            enterpriseMycourseAdapter.setListItems(mMycoursedata);
            enterpriseMycourseAdapter.setType(type + "");
            enterpriseMycourseAdapter.notifyDataSetChanged();
            // 结束刷新
            pullToRefreshListView.onRefreshComplete();
          }

          @Override
          public void onCacheError(Call call, Exception e) {
            super.onCacheError(call, e);
          }

          @Override
          public void onError(Call call, Response response, Exception e) {
            super.onError(call, response, e);
            dimissLoadingDialog();
          }
        });
  }

  /**
   * 根据输入框中的值来过滤数据并更新ListView
   */
//  private void filterData(String filterStr, List<PersonDto> list) {
//    List<PersonDto> filterDateList = new ArrayList<PersonDto>();
//    if (TextUtils.isEmpty(filterStr)) {
//      filterDateList = list;
//    } else {
//      filterDateList.clear();
//      for (PersonDto sortModel : list) {
//        String name = sortModel.getName();
//        String suoxie = sortModel.getSuoxie();
//        if (name.indexOf(filterStr.toString()) != -1 || suoxie.indexOf(filterStr.toString()) != -1
//            || characterParser.getSelling(name).startsWith(filterStr.toString())) {
//          filterDateList.add(sortModel);
//        }
//      }
//    }
  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.rb_left_enterprise_mycourse:
        pullToRefreshListView.setVisibility(View.VISIBLE);
        type = 0;
        showLoadingDialog();
        initData();
        break;
      case R.id.rb_right_enterprise_mycourse:
        pullToRefreshListView.setVisibility(View.VISIBLE);
        type = 1;
        showLoadingDialog();
        initData();
        break;

    }
  }

  @Override
  public boolean isSystemBarTranclucent() {
    return false;
  }

  @Override
  protected void onResume() {
    super.onResume();
    MobclickAgent.onPageStart("EnterpriseMycourseActivity");
  }

  @Override
  protected void onPause() {
    super.onPause();
    MobclickAgent.onPageEnd("EnterpriseMycourseActivity");
  }
}

