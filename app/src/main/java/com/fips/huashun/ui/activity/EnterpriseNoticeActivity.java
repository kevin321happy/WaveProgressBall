package com.fips.huashun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.modle.bean.EnterpiseNotice;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.adapter.EnterpriseNoticeAdapter;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 功能：企业公告
 * Created by Administrator on 2016/8/23.
 * @author 张柳 时间：2016年8月23日10:37:08
 */
public class EnterpriseNoticeActivity extends BaseActivity implements OnClickListener
{
    private NavigationBar navigationBar;
    // 上下拉的控件
    private PullToRefreshListView pullToRefreshListView;
    private ListView mListView;
    // 适配器
    private EnterpriseNoticeAdapter enterpriseNoticeAdapter;
    // 数据
    private List<EnterpiseNotice> list;
    protected Gson gson;
    private Intent intent;
    private String enterpriseId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterprise_notice);
        gson = new Gson();
        intent = getIntent();
        enterpriseId = intent.getStringExtra("enterpriseId");
        initView();
        showLoadingDialog();
        initData();
    }

    @Override
    protected void initView()
    {
        super.initView();

        navigationBar = (NavigationBar) findViewById(R.id.nb_enterprise_notice);
        navigationBar.setTitle("公告");
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
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.lv_enterprise_notice);
        mListView = pullToRefreshListView.getRefreshableView();
        // 两端刷新
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        enterpriseNoticeAdapter =new EnterpriseNoticeAdapter(EnterpriseNoticeActivity.this);
        mListView.setAdapter(enterpriseNoticeAdapter);
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
    @Override
    public void onClick(View v)
    {

    }
    public void  initData()
    {
        RequestParams requestParams = new RequestParams();
        requestParams.put("entid",enterpriseId);
        HttpUtil.post(Constants.ENTERPRISE_NOTICE_URL, requestParams, new LoadJsonHttpResponseHandler(this, new LoadDatahandler()
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
                        list = gson.fromJson(data.getString("noticeList"), new TypeToken<List<EnterpiseNotice>>(){}.getType());
                        enterpriseNoticeAdapter.setListItems(list);
                        enterpriseNoticeAdapter.notifyDataSetChanged();
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
                dimissLoadingDialog();
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
        MobclickAgent.onPageStart("EnterpriseNoticeActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("EnterpriseNoticeActivity");
    }
}
