package com.fips.huashun.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.fips.huashun.R;
import com.fips.huashun.ui.fragment.EnterprisePkFragment;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/22.
 */
public class EnterprisePkActivity extends BaseActivity implements View.OnClickListener
{
    private RadioButton view1;
    private RadioButton view2;
    private RadioButton view3;
    private ViewPager mPager;
    private ArrayList<RadioButton> rb;
    private ArrayList<Fragment> fragmentList;
    private int currIndex;//当前页卡编号
    private int page;
    private EnterprisePkFragment enterprisePkFragment1,enterprisePkFragment2,enterprisePkFragment3;
    private LinearLayout backLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterprisepk_rangk);
        initView();
    }

    @Override
    protected void initView()
    {
        super.initView();
        backLayout = (LinearLayout) findViewById(R.id.ll_back);
        backLayout.setOnClickListener(this);
        mPager = (ViewPager) findViewById(R.id.vp_enterprise);
        fragmentList = new ArrayList<Fragment>();
        enterprisePkFragment1 = new EnterprisePkFragment();
        enterprisePkFragment1.enterpriseRank =1;
        enterprisePkFragment2 = new EnterprisePkFragment();
        enterprisePkFragment2.enterpriseRank =2;
        enterprisePkFragment3 = new EnterprisePkFragment();
        enterprisePkFragment3.enterpriseRank =3;
        fragmentList.add(enterprisePkFragment1);
        fragmentList.add(enterprisePkFragment2);
        fragmentList.add(enterprisePkFragment3);

        view1 = (RadioButton) findViewById(R.id.guid1);
        view2 = (RadioButton) findViewById(R.id.guid2);
        view3 = (RadioButton) findViewById(R.id.guid3);

        view1.setOnClickListener(new txListener(0));
        view2.setOnClickListener(new txListener(1));
        view3.setOnClickListener(new txListener(2));

        rb = new ArrayList<RadioButton>();
        rb.add(view1);
        rb.add(view2);
        rb.add(view3);

        mPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());//页面变化时的监听器
        mPager.setCurrentItem(0);//设置当前显示标签页
        rb.get(0).setChecked(true);//设置进入页面第一次显

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.ll_back:
                finish();
                break;
        }
    }

    public class txListener implements View.OnClickListener {
        private int index = 0;

        public txListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mPager.setCurrentItem(index);
        }
    }
    public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        ArrayList<Fragment> list;
        public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
            super(fm);
            this.list = list;

        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Fragment getItem(int arg0) {
            return list.get(arg0);
        }

    }
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageSelected(int arg0) {
            currIndex = arg0;
            int i = currIndex + 1;
            rb.get(arg0).setChecked(true);
        }
    }
    @Override
    public boolean isSystemBarTranclucent()
    {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("EnterprisePkActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("EnterprisePkActivity");
    }
}
