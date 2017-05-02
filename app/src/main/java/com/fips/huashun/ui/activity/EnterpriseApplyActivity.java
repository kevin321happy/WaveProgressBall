package com.fips.huashun.ui.activity;

import android.os.Bundle;

import com.fips.huashun.R;
import com.fips.huashun.ui.utils.NavigationBar;
import com.umeng.analytics.MobclickAgent;

/**
 * 功能：企业申请页面
 * Created by Administrator on 2016/9/16.
 * @author 张柳 时间：2016年9月16日10:38:39
 */
public class EnterpriseApplyActivity extends BaseActivity
{
    private NavigationBar navigationBar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterprise_apply);
        initView();
    }

    @Override
    protected void initView()
    {
        super.initView();
        navigationBar = (NavigationBar) findViewById(R.id.nb_course_evaluate_title);
        navigationBar.setTitle("企业申请");
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
    }
    @Override
    public boolean isSystemBarTranclucent()
    {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("EnterpriseApplyActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("EnterpriseApplyActivity");
    }
}
