package com.fips.huashun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.modle.bean.PkInfo;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.adapter.EnterprisepkRangkAdapter;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.ToastUtil;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 功能：企业活动PK榜
 * Created by Administrator on 2016/8/23.
 *
 * @author 张柳 时间：2016年8月23日10:59:43
 */
public class EnterpriseActPkActivity extends BaseActivity
{
    // 上下拉的控件
    private PullToRefreshListView pullToRefreshListView;
    // 列表
    private ListView mListView;
    private NavigationBar navigationBar;
    private TextView rankTv,transcendTv;
    private EnterprisepkRangkAdapter enterprisepkRangkAdapter;
    private ToastUtil toastUtil;
    private Intent intent;
    private String activityId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erprise_actpk);
        initView();
        //showLoadingDialog();
        initData();
    }

    @Override
    protected void initView()
    {
        super.initView();
        gson = new Gson();
        toastUtil = ToastUtil.getInstant();
        intent = getIntent();
        activityId = intent.getStringExtra("activityId");
        rankTv = (TextView) findViewById(R.id.tv_myrank);
        transcendTv = (TextView) findViewById(R.id.tv_transcend);
        navigationBar = (NavigationBar) findViewById(R.id.na_bar_activity_pk);
        navigationBar.setTitle("PK");
        navigationBar.setLeftImage(R.drawable.fanhui);
        navigationBar.setListener(new NavigationBar.NavigationListener()
        {
            @Override
            public void onButtonClick(int button)
            {
                if (button == NavigationBar.LEFT_VIEW)
                {
                    finish();
                }
            }
        });
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.lv_enterprise_actpk);
        mListView = pullToRefreshListView.getRefreshableView();
        // 两端刷新
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        enterprisepkRangkAdapter = new EnterprisepkRangkAdapter(this,1);
        mListView.setAdapter(enterprisepkRangkAdapter);
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
     * 功能：获取活动PK榜
     */
    private void initData()
    {

        RequestParams requestParams = new RequestParams();
        requestParams.put("activityid", activityId);
        requestParams.put("userid", PreferenceUtils.getUserId());
        HttpUtil.post(Constants.ENTERPRISE_ACTPK_URL, requestParams, new LoadJsonHttpResponseHandler(this, new LoadDatahandler()
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
                try
                {
                    String suc = data.get("suc").toString();
                    String msg = data.get("msg").toString();
                    Log.e("525451","data.toString()="+data.toString());
                    if ("y".equals(suc))
                    {
                        PkInfo pkInfo = gson.fromJson(data.getString("data"),PkInfo.class);
                        rankTv.setText("我的当前排名：第"+pkInfo.getRank()+"名");
                        transcendTv.setText("已超越"+pkInfo.getTranscend()+"名同事");
                        enterprisepkRangkAdapter.setListItems(pkInfo.getPkList());
                        enterprisepkRangkAdapter.notifyDataSetChanged();
                    } else
                    {
                        ToastUtil.getInstant().show(msg);
                    }
                    // 结束刷新
                    pullToRefreshListView.onRefreshComplete();
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(String error, String message)
            {
                super.onFailure(error, message);
                // 结束刷新
                pullToRefreshListView.onRefreshComplete();
            }
        }));
    }

    @Override
    public boolean isSystemBarTranclucent()
    {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("EnterpriseActPkActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("EnterpriseActPkActivity");
    }
}
