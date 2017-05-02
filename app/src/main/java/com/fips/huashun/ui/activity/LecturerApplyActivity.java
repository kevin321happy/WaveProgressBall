package com.fips.huashun.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.fips.huashun.R;
import com.fips.huashun.ui.utils.NavigationBar;
import com.umeng.analytics.MobclickAgent;


/**
 * 功能：讲师申请
 * Created by Administrator on 2016/8/27.
 *
 * @author 张柳 时间：2016年8月27日16:50:01
 */
public class LecturerApplyActivity extends BaseActivity implements View.OnClickListener
{
    private NavigationBar navigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturerapply);
        initView();
    }

    @Override
    protected void initView()
    {
        super.initView();
        navigationBar = (NavigationBar) findViewById(R.id.nb_lecturerapply);
        navigationBar.setTitle("讲师申请");
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
        MobclickAgent.onPageStart("LecturerApplyActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("LecturerApplyActivity");
    }
}
