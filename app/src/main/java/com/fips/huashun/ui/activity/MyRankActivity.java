package com.fips.huashun.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.modle.bean.RankInfo;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

public class MyRankActivity extends BaseActivity {

    private TextView tv_rank1;
    private TextView tv_rank2;
    private TextView tv_rank3;
    private TextView tv_time1;
    private TextView tv_time2;
    private TextView tv_time3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rank);
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
        NavigationBar navigationBar = (NavigationBar) findViewById(R.id.na_bar);
        navigationBar.setTitle("我的排名");
        navigationBar.setLeftImage(R.drawable.fanhui);
        navigationBar.setListener(new NavigationBar.NavigationListener() {
            @Override
            public void onButtonClick(int button) {
                if (button==NavigationBar.LEFT_VIEW){
                    finish();
                }
            }
        });

        tv_rank1 = (TextView) findViewById(R.id.tv_rank1);
        tv_rank2 = (TextView) findViewById(R.id.tv_rank2);
        tv_rank3 = (TextView) findViewById(R.id.tv_rank3);
        tv_time1 = (TextView) findViewById(R.id.tv_time1);
        tv_time2 = (TextView) findViewById(R.id.tv_time2);
        tv_time3 = (TextView) findViewById(R.id.tv_time3);
    }
    private void initData() {
        RequestParams requestParams =new RequestParams();
        requestParams.put("userid", PreferenceUtils.getUserId());
        HttpUtil.post(Constants.URL+"/getUserStudyTimeRanking",requestParams,new LoadJsonHttpResponseHandler(this,new LoadDatahandler(){
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onSuccess(JSONObject data) {
                super.onSuccess(data);
                try {
                    String suc = data.getString("suc");
                    if (suc.equals("y")){
                        RankInfo rankInfo = gson.fromJson(data.getString("data"), RankInfo.class);
                        tv_rank1.setText(rankInfo.getDayRanking());
                        tv_time1.setText(rankInfo.getDayStudyTime()+"分钟");
                        tv_rank2.setText(rankInfo.getWeekRanking());
                        tv_time2.setText(rankInfo.getWeekStudyTime()+"分钟");
                        tv_rank3.setText(rankInfo.getAllRanking());
                        tv_time3.setText(rankInfo.getAllStudyTime()+"分钟");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String error, String message) {
                super.onFailure(error, message);
            }
        }));


    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MyRankActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MyRankActivity");
    }
}
