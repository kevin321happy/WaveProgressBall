package com.fips.huashun.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fips.huashun.R;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/22.
 */
public class EnterprisePkFragment extends Fragment
{
    private RadioButton view1;
    private RadioButton view2;
    private RadioButton view3;
    private RadioButton view4;
    private View rootView;
    private ViewPager mPager;
    private ArrayList<RadioButton> rb;
    //rankOne(1 个人 2 部门 3 企业)
    public int enterpriseRank;
    private FragmentManager fragmentManager;

    private ArrayList<Fragment> fragmentList;

    private RadioGroup rg;
    private HorizontalScrollView scrollview;
    private TextView tv_indicator;
    private EnterprisePkFragmentItem enterprisePkFragmentItem1, enterprisePkFragmentItem2, enterprisePkFragmentItem3, enterprisePkFragmentItem4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (rootView == null)
        {
            rootView = inflater.inflate(R.layout.fragment_enterprise_pkpage, container, false);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null)
        {
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        fragmentManager = getActivity().getSupportFragmentManager();
        initView();
    }

    private void initView()
    {
        scrollview = (HorizontalScrollView) rootView.findViewById(R.id.scrollview);
        rg = (RadioGroup) rootView.findViewById(R.id.rg);
        mPager = (ViewPager) rootView.findViewById(R.id.vp_enterprise_pkpage);
        tv_indicator = (TextView) rootView.findViewById(R.id.tv_indicator);
        fragmentList = new ArrayList<Fragment>();
        enterprisePkFragmentItem1 = new EnterprisePkFragmentItem();
        enterprisePkFragmentItem1.rankOne = enterpriseRank;
        enterprisePkFragmentItem1.rankTwo = 1;
        enterprisePkFragmentItem2 = new EnterprisePkFragmentItem();
        enterprisePkFragmentItem2.rankOne = enterpriseRank;
        enterprisePkFragmentItem2.rankTwo = 2;
        enterprisePkFragmentItem3 = new EnterprisePkFragmentItem();
        enterprisePkFragmentItem3.rankOne = enterpriseRank;
        enterprisePkFragmentItem3.rankTwo = 3;
        enterprisePkFragmentItem4 = new EnterprisePkFragmentItem();
        enterprisePkFragmentItem4.rankOne = enterpriseRank;
        enterprisePkFragmentItem4.rankTwo = 4;
        fragmentList.add(enterprisePkFragmentItem1);
        fragmentList.add(enterprisePkFragmentItem2);
        fragmentList.add(enterprisePkFragmentItem3);
        fragmentList.add(enterprisePkFragmentItem4);

        view1 = (RadioButton) rootView.findViewById(R.id.rb1);
        view2 = (RadioButton) rootView.findViewById(R.id.rb2);
        view3 = (RadioButton) rootView.findViewById(R.id.rb3);
        view4 = (RadioButton) rootView.findViewById(R.id.rb4);

        view1.setOnClickListener(new txListener(0));
        view2.setOnClickListener(new txListener(1));
        view3.setOnClickListener(new txListener(2));
        view4.setOnClickListener(new txListener(3));
        rb = new ArrayList<RadioButton>();
        rb.add(view1);
        rb.add(view2);
        rb.add(view3);
        rb.add(view4);
        mPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(), fragmentList));
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());//页面变化时的监听器
        mPager.setCurrentItem(0);//设置当前显示标签页
        rb.get(0).setChecked(true);//设置进入页面第一次显示

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                //选中的RadioButton播放动画
                ScaleAnimation sAnim = new ScaleAnimation(1, 1.1f, 1, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                sAnim.setDuration(500);
                sAnim.setFillAfter(true);
                //遍历所有的RadioButton
                for (int i = 0; i < group.getChildCount(); i++)
                {
                    RadioButton radioBtn = (RadioButton) group.getChildAt(i);
                    if (radioBtn.isChecked())
                    {
                        radioBtn.startAnimation(sAnim);
                    } else
                    {
                        radioBtn.clearAnimation();
                    }
                }
            }
        });
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
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
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
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("EnterprisePkFragment");
        MobclickAgent.onResume(getActivity());

    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("EnterprisePkFragment");
        MobclickAgent.onPause(getActivity());
    }
}