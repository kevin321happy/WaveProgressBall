package com.fips.huashun.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import com.fips.huashun.ApplicationEx;
import com.fips.huashun.R;
import com.fips.huashun.ui.fragment.MainActivity2;
import com.umeng.analytics.MobclickAgent;
//程序入口处

public class StartActivity extends BaseActivity {
    private Intent mIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //点击安装包进行安装，安装结束后不点击完成，而是点击打开应用，应用启动后，再回到桌面，从桌面点击应用图标会造成反复重启应用的现象。
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            this.finish();
            return;
        }
        setContentView(R.layout.activity_start);
        try {
            PackageInfo info = getPackageManager().getPackageInfo(StartActivity.this.getPackageName(), 0);
            int currentVersion = info.versionCode;
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(StartActivity.this);
            int lastVersion = prefs.getInt("is_version", 0);


            if (currentVersion > lastVersion) {
                prefs.edit().putInt("is_version", currentVersion).commit();
//                App.lastVersionCode = lastVersion;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                redirectTo();
            }
        }, 1500);


    }

    private void redirectTo() {
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        if (ApplicationEx.getInstance().isFirstRun) {
            //第一次安装使用显示欢迎、指引页
            Intent intent = new Intent(StartActivity.this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            //跳转到首页
            Intent intent = new Intent(StartActivity.this, MainActivity2.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean isSystemBarTranclucent() {
        return true;
    }

    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("StartActivity");

    }

    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("StartActivity");

    }

    protected void onDestroy() {
        super.onDestroy();
        if (mIntent != null) {
            stopService(mIntent);
            mIntent = null;
        }
    }
}
