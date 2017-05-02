package com.fips.huashun.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.fips.huashun.R;
import com.umeng.analytics.MobclickAgent;


/**
 * 功能：企业--我的工作职责
 * Created by Administrator on 2016/8/20.
 * @author 张柳 时间：2016年8月20日15:30:45
 */
public class MyOperatingDutyActivity extends BaseActivity implements OnClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myoperating_duty);
        initView();
    }
    @Override
    protected void initView()
    {
        super.initView();
    }

    @Override
    public void onClick(View v)
    {

    }

    @Override
    public boolean isSystemBarTranclucent()
    {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MyOperatingDutyActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MyOperatingDutyActivity");
    }
}
