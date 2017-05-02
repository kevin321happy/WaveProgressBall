package com.fips.huashun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.ToastUtil;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

public class MagicBeansActivity extends BaseActivity {
    // 魔豆
    private TextView beanPointsTv;
    private Button bt_chonzhi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magic_beans);
        initView();
        showLoadingDialog();
        initData();
    }

//    @Override
//    protected void onRestart()
//    {
//        super.onRestart();
//        initData();
//    }

    @Override
    public boolean isSystemBarTranclucent() {
        return false;
    }

    @Override
    protected void initView() {
        super.initView();
        NavigationBar navigationBar = (NavigationBar) findViewById(R.id.na_bar);
        navigationBar.setTitle("我的魔豆");
        navigationBar.setLeftImage(R.drawable.fanhui);
        navigationBar.setListener(new NavigationBar.NavigationListener() {
            @Override
            public void onButtonClick(int button) {
                if (button==NavigationBar.LEFT_VIEW){
                    finish();
                }
            }
        });
        beanPointsTv = (TextView) findViewById(R.id.tv_bean_points);
        bt_chonzhi = (Button) findViewById(R.id.bt_chonzhi);
        bt_chonzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MagicBeansActivity.this,RechargeBeanActivity.class);
                intent.putExtra("beanPoint",beanPointsTv.getText().toString());
                startActivity(intent);
            }
        });
    }

    /**
     * 功能：获取我的魔豆
     */
    private void initData()
    {
        RequestParams requestParams = new RequestParams();
        requestParams.put("userid", PreferenceUtils.getUserId());
        HttpUtil.post(Constants.SHOWMYBEANS_URL, requestParams, new LoadJsonHttpResponseHandler(this, new LoadDatahandler()
        {
            @Override
            public void onStart()
            {
                super.onStart();

            }

            @Override
            public void onSuccess(JSONObject data)
            {
                super.onSuccess(data);
                dimissLoadingDialog();
                try
                {
                    String suc = data.get("suc").toString();
                    String msg = data.get("msg").toString();
                    if ("y".equals(suc))
                    {
                        String beanPoints = data.get("data").toString();
                        beanPointsTv.setText(beanPoints);
                    } else
                    {
                        ToastUtil.getInstant().show(msg);
                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                    ToastUtil.getInstant().show("查询出错,请重试!");
                }
            }

            @Override
            public void onFailure(String error, String message)
            {
                super.onFailure(error, message);
                Log.e("onFailure","onFailure");
                dimissLoadingDialog();
                ToastUtil.getInstant().show("查询出错,请重试!");
            }
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MagicBeansActivity");
        initData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MagicBeansActivity");
    }
}
