package com.fips.huashun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.fips.huashun.ApplicationEx;
import com.fips.huashun.R.drawable;
import com.fips.huashun.R.id;
import com.fips.huashun.R.layout;
import com.fips.huashun.common.Constants;
import com.fips.huashun.modle.bean.UserInfo;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.ToastUtil;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 功能：修改婚恋状态
 * Created by Administrator on 2016/8/27.
 *
 * @author 张柳 时间：2016年8月27日10:28:31
 */
public class UpdateWedlockActivity extends BaseActivity implements View.OnClickListener
{
    private NavigationBar navigationBar;
    private RadioGroup marryRg;
    private RadioButton rb1, rb2;
    private ArrayList<RadioButton> rb;
    private Intent intent;
    private ToastUtil toastUtil;
    private String marry;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_updatewedlock);
        intent = getIntent();
        initView();
    }

    @Override
    protected void initView()
    {
        super.initView();
        toastUtil = ToastUtil.getInstant();
        marry = intent.getStringExtra("marry");
        Log.e("1234","marry="+marry);
        marryRg = (RadioGroup) findViewById(id.rg_wedlock);
        rb1 = (RadioButton) findViewById(id.rb1);
        rb2 = (RadioButton) findViewById(id.rb2);
        navigationBar = (NavigationBar) findViewById(id.nb_updatewedlock);
        navigationBar.setTitle("修改婚恋状态");
        navigationBar.setRightText("保存");
        navigationBar.setLeftImage(drawable.fanhui);
        navigationBar.setListener(new NavigationBar.NavigationListener()
        {
            @Override
            public void onButtonClick(int button)
            {
                if (button == NavigationBar.LEFT_VIEW)
                {
                    Intent intentBack = new Intent();
                    setResult(2,intentBack);
                    finish();
                } else if (button == NavigationBar.RIGHT_VIEW)
                {
                    updateMarry(marry);
                }
            }
        });
        rb = new ArrayList<RadioButton>();
        rb.add(rb1);
        rb.add(rb2);
        if("2".equals(marry))
        {
            rb.get(1).setChecked(true);
        } else
        {
            marry = "1";
            rb.get(0).setChecked(true);
        }
        marryRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                if (checkedId == id.rb1)
                {
                    marry = "1";
                } else if (checkedId == id.rb2)
                {
                    marry = "2";
                }
            }
        });
    }

    /**
     * 功能：修改婚恋状态
     */
    private void updateMarry(final String marry)
    {
        Log.e("updateName", "修改性别");
        RequestParams requestParams = new RequestParams();
        requestParams.put("userid", ApplicationEx.getInstance().getUserInfo().getUserid());
        requestParams.put("marry",marry);
        HttpUtil.post(Constants.UPDATE_USERINFO_URL, requestParams, new LoadJsonHttpResponseHandler(this, new LoadDatahandler()
        {
            @Override
            public void onStart()
            {
                super.onStart();
                Log.e("onStart", "onStart");
                showLoadingDialog();
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
                    ToastUtil.getInstant().show(msg);
                    if ("y".equals(suc))
                    {
                        ToastUtil.getInstant().show(msg);
                        Intent intentToBack = new Intent();
                        intentToBack.putExtra("marry",marry);
                        UserInfo userinfo = ApplicationEx.getInstance().getUserInfo();
                        userinfo.setMarry(marry);
                        ApplicationEx.getInstance().setUserInfo(userinfo);
                        setResult(1,intentToBack);
                        finish();
                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                    Log.e("JSONException", e.getMessage());
                }
            }

            @Override
            public void onFailure(String error, String message)
            {
                super.onFailure(error, message);
                dimissLoadingDialog();
                Log.e("message", message);
            }
        }));
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
        MobclickAgent.onPageStart("UpdateWedlockActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("UpdateWedlockActivity");
    }
}
