package com.fips.huashun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.modle.bean.ActivityInfo;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.adapter.ActivityRecommendAdapter;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.ToastUtil;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 功能：活动专区
 * Created by Administrator on 2016/8/19.
 */
public class ActivityRecommendActivity extends BaseActivity
{

    // 上下拉的控件
    private PullToRefreshListView pullToRefreshListView;
    // 列表
    private ListView mListView;
    // 适配器
    private ActivityRecommendAdapter activityRecommendAdapter;
    // 数据保存
    private List<ActivityInfo> list;
    private String key;
    // 解析

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_recommend);
        key = getIntent().getStringExtra("key");
        if (TextUtils.isEmpty(key)){
            key="queryAllActivity";
        }
        initView();
        showLoadingDialog();
        initData();
    }

    @Override
    protected void initView()
    {
        super.initView();
        NavigationBar navigationBar = (NavigationBar) findViewById(R.id.na_bar);
        navigationBar.setTitle("活动");
        navigationBar.setLeftImage(R.drawable.course_reslt_back);
        navigationBar.setListener(new NavigationBar.NavigationListener() {
            @Override
            public void onButtonClick(int button) {
                if (button==NavigationBar.LEFT_VIEW){
                    finish();
                }
            }
        });
        if (key.equals("showMoreRecommendActList")){
            navigationBar.setTitle("推荐活动");
        }
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.lv_activity_recommend);
        // 获取listview
        mListView = pullToRefreshListView.getRefreshableView();
        // 两端刷新
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        activityRecommendAdapter = new ActivityRecommendAdapter(ActivityRecommendActivity.this);
        // 设置适配器
        mListView.setAdapter(activityRecommendAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intentToDetal = new Intent(ActivityRecommendActivity.this,ActivityDetailActivity.class);
                intentToDetal.putExtra("activityId",String.valueOf(list.get(position-1).getActivityid()));
                startActivity(intentToDetal);
            }
        });
        // 设置上下拉事件
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>()
        {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
            {
                initData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
            {
                initData();
            }
        });
    }

    /**
     * 功能：获取所有活动列表信息
     */
    private void initData()
    {
        RequestParams requestParams = new RequestParams();
        HttpUtil.post(Constants.URL+"/"+key, requestParams, new LoadJsonHttpResponseHandler(ActivityRecommendActivity.this, new LoadDatahandler()
        {
            @Override
            public void onStart()
            {
                super.onStart();
            }

            @Override
            public void onSuccess(JSONObject data)
            {
                super.onSuccess(data);
                dimissLoadingDialog();
                try
                {
                    String suc = data.get("suc").toString();
                    String msg = data.get("msg").toString();
                    if ("y".equals(suc))
                    {

                        list = gson.fromJson(data.getString("data"),new TypeToken<List<ActivityInfo>>(){}.getType());
                        activityRecommendAdapter.setListItems(list);
                        activityRecommendAdapter.notifyDataSetChanged();

                    } else
                    {
                        ToastUtil.getInstant().show(msg);
                    }
                    // 结束刷新
                    pullToRefreshListView.onRefreshComplete();
                } catch (JSONException e)
                {
                    e.printStackTrace();
                    Log.e("JSONException","JSONException");
                    dimissLoadingDialog();
                }
            }

            @Override
            public void onFailure(String error, String message)
            {
                super.onFailure(error, message);
                Log.e("onFailure","onFailure");
                dimissLoadingDialog();
                // 结束刷新
                pullToRefreshListView.onRefreshComplete();
            }
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("ActivityRecommendActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("ActivityRecommendActivity");
    }

    @Override
    public boolean isSystemBarTranclucent()
    {
        return false;
    }
}
