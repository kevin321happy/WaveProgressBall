package com.fips.huashun.ui.fragment;

import static android.support.v7.app.AlertDialog.OnClickListener;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.widget.Toast;
import com.fips.huashun.R;
import com.fips.huashun.ui.activity.BaseActivity;
import com.fips.huashun.ui.utils.AppManager;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.Utils;
import com.umeng.analytics.MobclickAgent;

/**
 * @author hulin
 */
public class MainActivity extends BaseActivity {
    private long mExitTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       initView();
    }

    @Override
    public boolean isSystemBarTranclucent() {
        return false;
    }

    @Override
    protected void initView() {
        super.initView();
        checkNetState();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //按两次返回退出应用程序
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mExitTime = System.currentTimeMillis();
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                mExitTime = System.currentTimeMillis();
//				UiUtils.showToast(this,);
                Toast.makeText(getApplicationContext(), R.string.app_exit, Toast.LENGTH_SHORT).show();
//                ToastUtil.getInstant().show(R.string.app_exit);

            } else {

                PreferenceUtils.setBoolean(this, "isShowedAuth", false);
                AppManager.getAppManager().AppExit(this);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void checkNetState() {
        if (!Utils.isNetWork(this)) {
            AlertDialog. Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("网络状态提醒");
            builder.setMessage("当前网络不可用，是否打开网络设置?");
            builder.setNeutralButton("取消", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.setNegativeButton("确定", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (android.os.Build.VERSION.SDK_INT > 10) {
                        startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                    } else {
                        startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                    }
                }
            });
            builder.create().show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MianActivity");
    }
    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MianActivity");
    }
}
