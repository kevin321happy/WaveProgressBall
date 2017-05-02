package com.fips.huashun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.fips.huashun.ApplicationEx;
import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.modle.bean.UserInfo;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.utils.MyEditText;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.ToastUtil;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 功能：修改姓名
 * Created by Administrator on 2016/8/27.
 * @author 张柳 时间：2016年8月27日10:28:31
 */
public class UpdateNameActivity extends BaseActivity implements View.OnClickListener
{
    private NavigationBar navigationBar;
    private MyEditText myEditText;
    private Intent intent;
    private ToastUtil toastUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatename);
        intent = getIntent();
        initView();
    }

    @Override
    protected void initView()
    {
        super.initView();
        toastUtil = ToastUtil.getInstant();
        myEditText = (MyEditText) findViewById(R.id.et_updatename);
        myEditText.setText(intent.getStringExtra("name"));
        navigationBar = (NavigationBar) findViewById(R.id.nb_updatename);
        navigationBar.setTitle("修改姓名");
        navigationBar.setRightText("保存");
        navigationBar.setLeftImage(R.drawable.fanhui);
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
                    String name = myEditText.getText().toString().trim();
                    if (null == name || TextUtils.isEmpty(name))
                    {
                        toastUtil.show("请输入姓名!");
                    } else
                    {
                        updateName(name);
                    }
                }
            }
        });
    }
    @Override
    public void onClick(View v)
    {

    }
    /**
     * 功能：修改姓名
     */
    private void updateName(final String name)
    {
        Log.e("updateName", "修改姓名");
        RequestParams requestParams = new RequestParams();
        requestParams.put("userid", ApplicationEx.getInstance().getUserInfo().getUserid());
        requestParams.put("truename",name);
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
                        intentToBack.putExtra("truename",name);
                        UserInfo userinfo = ApplicationEx.getInstance().getUserInfo();
                        userinfo.setTruename(name);
                        ApplicationEx.getInstance().setUserInfo(userinfo);
                        setResult(1,intentToBack);
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        if (inputMethodManager.isActive())
                        {
                            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        }
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
    public boolean isSystemBarTranclucent()
    {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("UpdateNameActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("UpdateNameActivity");
    }
}
