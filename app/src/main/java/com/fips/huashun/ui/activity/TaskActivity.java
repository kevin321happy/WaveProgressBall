package com.fips.huashun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.fips.huashun.R;
import com.fips.huashun.ui.fragment.EnterpriseTaskFragment;
import com.fips.huashun.ui.fragment.ExamTaskFragment;
import com.fips.huashun.ui.fragment.VoteTaskFragment;
import com.fips.huashun.ui.utils.NavigationBar;
import com.umeng.analytics.MobclickAgent;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/23.
 */
public class TaskActivity extends BaseActivity
{
    private NavigationBar navigationBar;
    private RadioButton view1;
    private RadioButton view2;
    private RadioButton view3;
    private RadioButton view4;
    private LinearLayout tv_indicator;
    private HorizontalScrollView scrollview;
    private RadioGroup rg;
    private ViewPager mPager;
    private ArrayList<RadioButton> rb;
    private ArrayList<Fragment> fragmentList;
    private int currIndex;//当前页卡编号
    public String enterpriseId;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        initView();
    }

    @Override
    protected void initView()
    {
        super.initView();
        intent =getIntent();
        enterpriseId = intent.getStringExtra("enterpriseId");
        navigationBar = (NavigationBar) findViewById(R.id.na_bar_task);
        navigationBar.setTitle("任务");
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
        scrollview = (HorizontalScrollView) findViewById(R.id.scrollview);
        rg = (RadioGroup) findViewById(R.id.rg);
        mPager = (ViewPager) findViewById(R.id.vp_enterprise_pkpage);
        tv_indicator = (LinearLayout) findViewById(R.id.tv_indicator);
        fragmentList = new ArrayList<Fragment>();
//        TodayTaskFragment todayTaskFragment = new TodayTaskFragment();
        VoteTaskFragment voteTaskFragment = new VoteTaskFragment();
        voteTaskFragment.enterpriseId = enterpriseId;
        EnterpriseTaskFragment enterpriseTaskFragment = new EnterpriseTaskFragment();
        ExamTaskFragment examTaskFragment = new ExamTaskFragment();

//        fragmentList.add(todayTaskFragment);
        fragmentList.add(voteTaskFragment);
        fragmentList.add(enterpriseTaskFragment);
//        fragmentList.add(examTaskFragment);

//        view1 = (RadioButton) findViewById(R.id.rb1);
//        view2 = (RadioButton) findViewById(R.id.rb2);
        view3 = (RadioButton) findViewById(R.id.rb3);
        view4 = (RadioButton) findViewById(R.id.rb4);

//        view1.setOnClickListener(new txListener(0));
//        view2.setOnClickListener(new txListener(0));
        view3.setOnClickListener(new txListener(1));
        view4.setOnClickListener(new txListener(2));
        rb = new ArrayList<RadioButton>();
//        rb.add(view1);
//        rb.add(view2);
        rb.add(view3);
        rb.add(view4);
        mPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());//页面变化时的监听器
        mPager.setCurrentItem(0);//设置当前显示标签页
        rb.get(0).setChecked(true);//设置进入页面第一次显
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //选中的RadioButton播放动画
                ScaleAnimation sAnim = new ScaleAnimation(1, 1.1f, 1, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                sAnim.setDuration(500);
                sAnim.setFillAfter(true);
                //遍历所有的RadioButton
                for (int i = 0; i < group.getChildCount(); i++) {
                    RadioButton radioBtn = (RadioButton) group.getChildAt(i);
                    if (radioBtn.isChecked()) {
                        radioBtn.startAnimation(sAnim);
                    } else {
                        radioBtn.clearAnimation();
                    }
                }

            }
        });
    }

    public class txListener implements OnClickListener
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
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tv_indicator
                    .getLayoutParams();
            params.leftMargin = (int) ((arg0 + arg1) * params.width);
            tv_indicator.setLayoutParams(params);
        }

        @Override
        public void onPageScrollStateChanged(int arg0)
        {

        }

        @Override
        public void onPageSelected(int arg0)
        {
            // 获取对应位置的RadioButton
            RadioButton radioBtn = (RadioButton) rg.getChildAt(arg0);
            // 设置对应位置的RadioButton为选中的状态
            radioBtn.setChecked(true);
                /* 滚动HorizontalScrollView使选中的RadioButton处于屏幕中间位置 */
            //获取屏幕信息
            DisplayMetrics outMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
            //获取每一个RadioButton的宽度
            int radioBtnPiexls = radioBtn.getWidth();
            //计算滚动距离
            int distance = (int) ((arg0 + 0.5) * radioBtnPiexls - outMetrics.widthPixels / 2);
            //滚动
            scrollview.scrollTo(distance, 0);
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
        MobclickAgent.onPageStart("TaskActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("TaskActivity");
    }
}
