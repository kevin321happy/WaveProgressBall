package com.fips.huashun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.modle.bean.EnterpriseActNotice;
import com.fips.huashun.modle.bean.EnterpriseActNoticeInfo;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.adapter.BusinessNoticeAdapter;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.ToastUtil;
import com.fips.huashun.ui.utils.Utility;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

public class BusinessNoticeActivity extends BaseActivity {

    private NavigationBar navigationBar;
    private Intent intent;
    private ListView mListView;
    private BusinessNoticeAdapter businessNoticeAdapter;
    // 活动ID
    private String activityId;
    // 活动时间，活动地址，授课讲师，授课内容，住宿，课堂纪律，项目负责人，联系方式
    private TextView timeTv,addressTv,teacherNameTv,actInfoTv,acserverTv,
            acrequireTv,leaderNameTv,phoneTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_notice);
        initView();
        initData();
    }

    @Override
    public boolean isSystemBarTranclucent() {
        return false;
    }

    @Override
    protected void initView() {
        super.initView();
        gson = new Gson();
        intent = getIntent();
        activityId = intent.getStringExtra("activityId");
        Log.e("activityId",activityId);
        navigationBar = (NavigationBar) findViewById(R.id.na_bar);
        navigationBar.setTitle("通知");
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
        timeTv = (TextView)findViewById(R.id.tv_enterprise_act_time);
        addressTv = (TextView)findViewById(R.id.tv_enterprise_act_address);
        teacherNameTv = (TextView)findViewById(R.id.tv_enterprise_act_teacher);
        actInfoTv = (TextView)findViewById(R.id.tv_enterprise_act_info);
        acserverTv = (TextView)findViewById(R.id.tv_enterprise_act_acserver);
        acrequireTv = (TextView)findViewById(R.id.tv_enterprise_act_acrequire);
        leaderNameTv = (TextView)findViewById(R.id.tv_enterprise_act_leader);
        phoneTv = (TextView)findViewById(R.id.tv_enterprise_act_phone);
        mListView = (ListView) findViewById(R.id.lv_list);
        businessNoticeAdapter = new BusinessNoticeAdapter(this);
        mListView.setAdapter(businessNoticeAdapter);
        mListView.setDividerHeight(0);
    }
    /**
     * 功能：获取所有活动列表信息
     */
    private void initData()
    {
        RequestParams requestParams = new RequestParams();
        requestParams.put("activityid",activityId);
        HttpUtil.post(Constants.ENTERPRISE_ACTIVITY_NOTICE_URL, requestParams, new LoadJsonHttpResponseHandler(BusinessNoticeActivity.this, new LoadDatahandler()
        {
            @Override
            public void onStart()
            {
                super.onStart();
                showLoadingDialog();
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
                        EnterpriseActNotice enterpriseActNotice = gson.fromJson(data.getString("data"),EnterpriseActNotice.class);

                        if (enterpriseActNotice.getActivityRequ()!=null){
                            EnterpriseActNoticeInfo enterpriseActNoticeInfo = enterpriseActNotice.getActivityRequ();
                            timeTv.setText("开始时间：" + enterpriseActNoticeInfo.getActivitytime()+"\n结束时间：" + enterpriseActNoticeInfo.getEndtime());
                            addressTv.setText(enterpriseActNoticeInfo.getAddress());
                            teacherNameTv.setText(enterpriseActNoticeInfo.getTeacherName());
                            actInfoTv.setText(enterpriseActNoticeInfo.getActivityinfo());
                            acserverTv.setText(enterpriseActNoticeInfo.getAcserver());
                            acrequireTv.setText(enterpriseActNoticeInfo.getAcrequire());
                            leaderNameTv.setText(enterpriseActNoticeInfo.getLeaderName());
                            phoneTv.setText(enterpriseActNoticeInfo.getPhone());
                        }

                        businessNoticeAdapter.setListItems(enterpriseActNotice.getAcplanList());
                        businessNoticeAdapter.notifyDataSetChanged();
                        Utility.setListViewHeightBasedOnChildren(mListView);
                    } else
                    {
                        ToastUtil.getInstant().show(msg);
                    }
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
            }
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("BusinessNoticeActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("BusinessNoticeActivity");
    }
}
