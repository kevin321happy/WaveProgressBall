package com.fips.huashun.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.common.ConstantsCode;
import com.fips.huashun.holder.ActResearchHolder;
import com.fips.huashun.modle.bean.ActivityResearchModel;
import com.fips.huashun.modle.bean.ActivityResearchModel.ResearchInfo;
import com.fips.huashun.ui.utils.DisplayUtil;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.NavigationBar.NavigationListener;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.widgets.pickerview.DividerItemDecoration;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lzy.okgo.OkGo;
import com.shuyu.common.CommonRecyclerAdapter;
import com.shuyu.common.CommonRecyclerManager;
import com.shuyu.common.model.RecyclerBaseModel;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by kevin on 2017/3/1. 邮箱：kevin321vip@126.com 公司：锦绣氘(武汉科技有限公司)
 */
//企业活动调研
public class ActResearchActivity extends BaseActivity {
  @Bind(R.id.navigationbar)
  NavigationBar mNavigationbar;
  @Bind(R.id.srearch_xrcy)
  XRecyclerView mSrearchXrcy;
  private CommonRecyclerManager mCommonRecyclerManager;
  private List<RecyclerBaseModel> datalist = new ArrayList<>();
  private CommonRecyclerAdapter mCommonRecyclerAdapter;
  private Gson mGson;
  private String activityid;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.act_research_activity);
    ButterKnife.bind(this);
    activityid=getIntent().getStringExtra("activityid");
    mGson = new Gson();
    initView();
    initData();
  }
  private void initData() {
    OkGo.post(Constants.LOCAL_SURVEY_LIST)
        .cacheKey(ConstantsCode.CACHE_RESEARCH)
        .cacheMode(com.lzy.okgo.cache.CacheMode.REQUEST_FAILED_READ_CACHE)
        .params("activityid",activityid)
        .params("uid", PreferenceUtils.getUserId())
        .execute(new com.lzy.okgo.callback.StringCallback() {
          @Override
          public void onSuccess(String s, Call call, Response response) {
            ActivityResearchModel researchModel = mGson
                .fromJson(s, ActivityResearchModel.class);
            List<ResearchInfo> researchModelRow = researchModel.getRow();
            for (ResearchInfo rowBean : researchModelRow) {
              rowBean.setResLayoutId(ActResearchHolder.ID);
            }
            setDatas(researchModelRow);
          }
        });
  }

  protected void initView() {
    mNavigationbar.setTitle("活动调研");
    mNavigationbar.setLeftImage(R.drawable.fanhui);
    mNavigationbar.setListener(new NavigationListener() {
      @Override
      public void onButtonClick(int button) {
        if (button == NavigationBar.LEFT_VIEW) {
          finish();
        }
      }
    });
    mCommonRecyclerManager = new CommonRecyclerManager();
    mCommonRecyclerManager.addType(ActResearchHolder.ID, ActResearchHolder.class.getName());
    mCommonRecyclerAdapter = new CommonRecyclerAdapter(this,
        mCommonRecyclerManager, datalist);
    mSrearchXrcy.setLayoutManager(new LinearLayoutManager(this));
    mSrearchXrcy.setPullRefreshEnabled(false);//屏蔽下拉刷新
    //设置分割线
    mSrearchXrcy.addItemDecoration(
        new DividerItemDecoration(DisplayUtil.dp2px(this, 10), 0, 0));
    mSrearchXrcy.setAdapter(mCommonRecyclerAdapter);
//    View view=View.inflate(this,R.layout.mulu_items,null);
//    mSrearchXrcy.setEmptyView(view);
  }

  public void setDatas(List datas) {
    this.datalist = datas;
    if (mCommonRecyclerAdapter != null) {
      mCommonRecyclerAdapter.setListData(datas);
    }
  }

  @Override
  public boolean isSystemBarTranclucent() {
    return false;
  }
}
