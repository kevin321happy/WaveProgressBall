package com.fips.huashun.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.fips.huashun.R;
import com.fips.huashun.ui.utils.NavigationBar;
import com.umeng.analytics.MobclickAgent;


/**
 * 功能：前往考试
 * Created by Administrator on 2016/8/23.
 * @author 张柳 时间：2016年8月23日10:37:08
 */
public class ExamGoActivity extends BaseActivity implements OnClickListener
{
    private NavigationBar navigationBar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goexam);
        initView();
    }

    @Override
    protected void initView()
    {
        super.initView();

        navigationBar = (NavigationBar) findViewById(R.id.na_bar_goexam);
        navigationBar.setTitle("考试");
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
        MobclickAgent.onPageStart("ExamGoActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("ExamGoActivity");
    }
}
