package com.fips.huashun.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fips.huashun.R;
import com.fips.huashun.modle.event.ActListLoadEvent;
import com.fips.huashun.ui.pager.EnterpriseActPager;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.ToastUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by kevin on 2017/1/10.
 */
public class EnterpriseActList extends BaseActivity {
    @Bind(R.id.na_bar_entact)
    NavigationBar mNaBarEntact;
    @Bind(R.id.tv_act_before)
    TextView mTvActBefore;
    @Bind(R.id.tv_act_progress)
    TextView mTvActProgress;
    @Bind(R.id.tv_act_end)
    TextView mTvActEnd;
    @Bind(R.id.find_ll)
    RelativeLayout mFindLl;
    @Bind(R.id.vp_act_list)
    ViewPager mVpActList;

    private List<String> mStringList = new ArrayList<>();
    private int position = 0;
    private int currIndex = 0;// 当前页编号
    private int one;
    private int x = 0;
    private int sum;
    private int dx;// 动画图片偏移量
    private Intent intent;
    private String enterpriseId;
    private ToastUtil toastUtil;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterpriseactivity1);
        ButterKnife.bind(this);
        gson = new Gson();
        EventBus.getDefault().register(this);
        intent = getIntent();
        enterpriseId = intent.getStringExtra("enterpriseId");
        toastUtil = ToastUtil.getInstant();
        initView();
        setTabMove();//设置底部的指示器滑动
    }

    private void setTabMove() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) mFindLl.getLayoutParams();
        linearParams.width = width / 3;
        Display currDisplay = getWindowManager().getDefaultDisplay();//获取屏幕当前分辨率
        int displayWidth = currDisplay.getWidth();
        one = displayWidth / 3; //设置水平动画平移大小
    }

    //EventBus列表加载
    public void onEventMainThread(ActListLoadEvent event) {
        if (event.isLoading()) {
            showLoadingDialog();
        } else {
            dimissLoadingDialog();
        }
    }
    /**
     * 初始化界面
     */
    @Override
    protected void initView() {
        mNaBarEntact.setTitle("企业活动");
        mNaBarEntact.setLeftImage(R.drawable.fanhui);
        mNaBarEntact.setListener(new NavigationBar.NavigationListener() {
            @Override
            public void onButtonClick(int button) {
                if (button == NavigationBar.LEFT_VIEW) {
                    finish();
                }
            }
        });
        mVpActList.setOnPageChangeListener(mOnPageChangeListener);
        mVpActList.setAdapter(new ActPagerAdapter());
    }

    /**
     * Viewpager 滑动监听
     */
    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            EnterpriseActList.this.position = position;
            Animation animation = null;
            sum++;
            if (position - currIndex > 1) {
                dx = x + one * 2;
            } else if (position > currIndex) {
                dx = x + one;
            } else if (position - currIndex < -1) {
                dx = x - one * 2;
            } else {
                dx = x - one;
            }
            switch (position) {
                case 0:
                    mTvActBefore.setTextColor(getResources().getColor(R.color.enterprise_act__text));
                    mTvActProgress.setTextColor(getResources().getColor(R.color.enterprise_act__defaulttext));
                    mTvActEnd.setTextColor(getResources().getColor(R.color.enterprise_act__defaulttext));
                    animation = new TranslateAnimation(x, dx, 0, 0);
                    mTvActProgress.clearAnimation();
                    mTvActEnd.clearAnimation();
                    showScaAnim(mTvActBefore);
                    break;
                case 1:
                    mTvActBefore.setTextColor(getResources().getColor(R.color.enterprise_act__defaulttext));
                    mTvActProgress.setTextColor(getResources().getColor(R.color.enterprise_act__text));
                    mTvActEnd.setTextColor(getResources().getColor(R.color.enterprise_act__defaulttext));
                    animation = new TranslateAnimation(x, dx, 0, 0);
                    mTvActBefore.clearAnimation();
                    showScaAnim(mTvActProgress);
                    mTvActEnd.clearAnimation();
                    break;
                case 2:
                    mTvActBefore.setTextColor(getResources().getColor(R.color.enterprise_act__defaulttext));
                    mTvActProgress.setTextColor(getResources().getColor(R.color.enterprise_act__defaulttext));
                    mTvActEnd.setTextColor(getResources().getColor(R.color.enterprise_act__text));
                    animation = new TranslateAnimation(x, dx, 0, 0);
                    mTvActBefore.clearAnimation();
                    mTvActProgress.clearAnimation();
                    showScaAnim(mTvActEnd);
                    break;
            }
            if (position - currIndex > 1) {
                x += one * 2;
            } else if (position > currIndex) {
                x += one;
            } else if (position - currIndex < -1) {
                x -= one * 2;
            } else {
                x -= one;
            }
            currIndex = position;
            animation.setDuration(100);
            animation.setFillAfter(true);//
            mFindLl.startAnimation(animation);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    /**
     * 被选中是开始缩放动画
     *
     * @param view
     */
    public void showScaAnim(View view) {
        //选中的RadioButton播放动画
        ScaleAnimation sAnim = new ScaleAnimation(1, 1.2f, 1, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        sAnim.setDuration(500);
        sAnim.setFillAfter(true);
        view.startAnimation(sAnim);
    }

    @OnClick({R.id.tv_act_before, R.id.tv_act_progress, R.id.tv_act_end})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_act_before:
                mVpActList.setCurrentItem(0);
                break;
            case R.id.tv_act_progress:
                mVpActList.setCurrentItem(1);
                break;
            case R.id.tv_act_end:
                mVpActList.setCurrentItem(2);
                break;
        }
    }

    private class ActPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //加载pager的数据
            EnterpriseActPager actPager = new EnterpriseActPager(container.getContext(), position, enterpriseId);
            container.addView(actPager.getView());
            return actPager.getView();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    @Override
    public boolean isSystemBarTranclucent() {
        return false;
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
