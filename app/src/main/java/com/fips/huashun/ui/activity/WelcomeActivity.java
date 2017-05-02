package com.fips.huashun.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.fips.huashun.ApplicationEx;
import com.fips.huashun.R;
import com.fips.huashun.ui.fragment.MainActivity2;
import com.fips.huashun.ui.utils.AppManager;
import com.fips.huashun.ui.utils.SystemBarTintManager;
import com.umeng.analytics.MobclickAgent;

import java.io.InputStream;
import java.util.ArrayList;


public class WelcomeActivity extends BaseActivity implements View.OnClickListener {

    private ViewPager viewPager;

    private MyPagerAdapter myAdapter;

    private LayoutInflater mInflater;

    private ArrayList<View> views;


    private int[] images = {R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3,R.drawable.guide_4};

    private ImageView start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.welcome);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.transparent);//通知栏所需颜色
        }
        initView();
    }

    /**
     * 初始化视图
     */
    @SuppressLint("InflateParams")
    @Override
    protected void initView() {

        views = new ArrayList<View>();
        myAdapter = new MyPagerAdapter(views);
        viewPager = (ViewPager) findViewById(R.id.welcome_viewpager);
        mInflater = getLayoutInflater();
        for (int i = 0; i < 4; i++) {

            ImageView imageView = new ImageView(this);
            InputStream inputStream = this.getResources().openRawResource(images[i]);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inPurgeable = true;
            options.inInputShareable = true;
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream,null,options);
            imageView.setImageBitmap(bitmap);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            views.add(imageView);
        }

        start = (ImageView) findViewById(R.id.start_btn);
        start.setOnClickListener(this);
        viewPager.setAdapter(myAdapter);
        //初始化当前显示的view
        viewPager.setCurrentItem(0);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                if (arg0 == 3) {
                    start.setVisibility(View.VISIBLE);
                } else {
                    start.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!checkClick(v.getId())) {
            return;
        }
        switch (v.getId()) {

            case R.id.start_btn:
                gotoMainPage();
                break;
            case R.id.welcomeClose:
                gotoMainPage();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean isSystemBarTranclucent() {
        return true;
    }

    /**
     * 跳转到首页
     */
    private void gotoMainPage() {
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("isFirst", false).apply();
        ApplicationEx.getInstance().isFirstRun = false;
        //跳转到首页
        Intent intent = new Intent(WelcomeActivity.this, MainActivity2.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //退出应用
            AppManager.getAppManager().AppExit(this);
        }
        return super.onKeyDown(keyCode, event);
    }

    private class MyPagerAdapter extends PagerAdapter {
        // 界面列表
        private ArrayList<View> views;

        public MyPagerAdapter(ArrayList<View> views) {
            this.views = views;
        }

        /**
         * 获得当前界面数
         */
        @Override
        public int getCount() {
            if (views != null) {
                return views.size();
            }
            return 0;
        }

        /**
         * 初始化position位置的界面
         */
        @Override
        public Object instantiateItem(View view, int position) {
            ((ViewPager) view).addView(views.get(position), 0);
            View temp = views.get(position);
            temp.setVisibility(View.VISIBLE);
            return temp;
        }

        /**
         * 判断是否由对象生成界面
         */
        @Override
        public boolean isViewFromObject(View view, Object arg1) {
            return (view == arg1);
        }

        /**
         * 销毁position位置的界面
         */
        @Override
        public void destroyItem(View view, int position, Object arg2) {
            ((ViewPager) view).removeView(views.get(position));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("WelcomeActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("WelcomeActivity");
    }
}
