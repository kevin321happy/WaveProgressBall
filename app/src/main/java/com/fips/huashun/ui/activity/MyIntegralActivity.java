package com.fips.huashun.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.modle.bean.IntegralInfo;
import com.fips.huashun.modle.bean.MyIntegralInfo;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.fragment.IntegralGradeFragment;
import com.fips.huashun.ui.fragment.IntegralInfoFragment;
import com.fips.huashun.ui.fragment.IntegralRankFragment;
import com.fips.huashun.ui.fragment.IntegralTaskFragment;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.widgets.ColorArcProgressBar;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 功能：我的积分等级
 * Created by Administrator on 2016/8/25.
 *
 * @author 张柳 时间：2016年8月25日10:07:13
 */
public class MyIntegralActivity extends BaseActivity implements View.OnClickListener
{
    private NavigationBar navigationBar;
    private RadioGroup radioGroup;
    private ViewPager mPager;
    private ArrayList<RadioButton> radioButtonList;
    private ArrayList<Fragment> fragmentList;
    private RadioButton view1;
    private RadioButton view2;
    private RadioButton view3;
    private RadioButton view4;
    private TextView tv_indicator;
    private int currIndex;//当前页卡编号
    private ColorArcProgressBar bar1;
    private TextView integralLevelTv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myintegral);
        initView();
        InitBar();
        initData();
    }

    @Override
    protected void initView()
    {
        super.initView();
        navigationBar = (NavigationBar) findViewById(R.id.nb_myintegral);
        navigationBar.setTitle("我的积分");
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
        integralLevelTv = (TextView) findViewById(R.id.tv_myintegral_grade);
        bar1 = (ColorArcProgressBar) findViewById(R.id.bar1);
        bar1.setDiameter(100);
        radioGroup = (RadioGroup) findViewById(R.id.rg_myintegral);
        mPager = (ViewPager) findViewById(R.id.vp_myintergral);

        fragmentList = new ArrayList<Fragment>();
        IntegralTaskFragment integralTaskFragment = new IntegralTaskFragment();
        IntegralInfoFragment integralInfoFragment = new IntegralInfoFragment();
        IntegralRankFragment integralrankFragment = new IntegralRankFragment();
        IntegralGradeFragment integralgradeFragment = new IntegralGradeFragment();

        fragmentList.add(integralTaskFragment);
        fragmentList.add(integralInfoFragment);
        fragmentList.add(integralrankFragment);
        fragmentList.add(integralgradeFragment);

        view1 = (RadioButton) findViewById(R.id.rb1);
        view2 = (RadioButton) findViewById(R.id.rb2);
        view3 = (RadioButton) findViewById(R.id.rb3);
        view4 = (RadioButton) findViewById(R.id.rb4);

        view1.setOnClickListener(new txListener(0));
        view2.setOnClickListener(new txListener(1));
        view3.setOnClickListener(new txListener(2));
        view4.setOnClickListener(new txListener(3));
        radioButtonList = new ArrayList<RadioButton>();
        radioButtonList.add(view1);
        radioButtonList.add(view2);
        radioButtonList.add(view3);
        radioButtonList.add(view4);
        mPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());//页面变化时的监听器
        mPager.setCurrentItem(0);//设置当前显示标签页
        radioButtonList.get(0).setChecked(true);//设置进入页面第一次显

    }

    /**
     * 功能：获取初始数据
     */
    private void initData()
    {
        RequestParams requestParams = new RequestParams();
        requestParams.put("userid", PreferenceUtils.getUserId());
        HttpUtil.post(Constants.INTEGRALINFO_URL, requestParams, new LoadJsonHttpResponseHandler(this, new LoadDatahandler()
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
                    String suc = data.getString("suc");
                    String msg = data.getString("msg");
                    if ("y".equals(suc))
                    {
                        MyIntegralInfo myIntegralInfo = gson.fromJson(data.getString("data"), MyIntegralInfo.class);
                        IntegralInfo integralInfo = myIntegralInfo.getIntegralInfo();
                        integralLevelTv.setText("Lv " + integralInfo.getLevelcode() + "  " + integralInfo.getLevelname());
                        bar1.setMaxValues(integralInfo.getLevelpointsmax());
                        bar1.setCurrentValues(integralInfo.getTotal_points());
                    } else
                    {

                    }
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
            }
        }));
    }

    /**
     * 初始化图片的位移像素
     */
    public void InitBar()
    {
        tv_indicator = (TextView) findViewById(R.id.tv_indicator_myintegral);
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        // 得到显示屏宽度
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        // 1/4屏幕宽度
        int tabLineLength = metrics.widthPixels / 4;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tv_indicator.getLayoutParams();
        lp.width = tabLineLength;
        tv_indicator.setLayoutParams(lp);
    }

    public class txListener implements View.OnClickListener
    {
        private int index = 0;

        public txListener(int i)
        {
            index = i;
        }

        @Override
        public void onClick(View v)
        {
            mPager.setCurrentItem(index);
        }
    }

    public class MyFragmentPagerAdapter extends FragmentPagerAdapter
    {

        ArrayList<Fragment> list;

        public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> list)
        {
            super(fm);
            this.list = list;

        }

        @Override
        public int getCount()
        {
            return list.size();
        }

        @Override
        public Fragment getItem(int arg0)
        {
            return list.get(arg0);
        }

    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener
    {
        public void onPageScrolled(int arg0, float arg1, int arg2)
        {
            // 取得该控件的实例

            LinearLayout.LayoutParams ll = (LinearLayout.LayoutParams) tv_indicator
                    .getLayoutParams();

            if (currIndex == arg0)
            {
                ll.leftMargin = (int) (currIndex * tv_indicator.getWidth() + arg1
                        * tv_indicator.getWidth());
            } else if (currIndex > arg0)
            {
                ll.leftMargin = (int) (currIndex * tv_indicator.getWidth() - (1 - arg1) * tv_indicator.getWidth());
            }
            tv_indicator.setLayoutParams(ll);
        }

        @Override
        public void onPageScrollStateChanged(int arg0)
        {

        }

        @Override
        public void onPageSelected(int arg0)
        {
            currIndex = arg0;
            radioButtonList.get(arg0).setChecked(true);
        }
    }

    @Override
    public boolean isSystemBarTranclucent()
    {
        return false;
    }

    @Override
    public void onClick(View v)
    {

    }



    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MyIntegralActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MyIntegralActivity");
    }
}
