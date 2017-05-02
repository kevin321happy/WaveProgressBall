package com.fips.huashun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.fips.huashun.ApplicationEx;
import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.fragment.MainActivity2;
import com.fips.huashun.ui.utils.FirstEvent;
import com.fips.huashun.ui.utils.Md5Utils;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.ToastUtil;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;

/**
 * 功能：修改密码
 * Created by Administrator on 2016/8/26.
 *
 * @author 张柳 时间：2016年8月26日11:07:13
 */
public class UpdatePwdActivity extends BaseActivity implements OnClickListener
{
    private NavigationBar navigationBar;
    // 原密码，新密码，确认密码
    private TextView oldPswTv, newPswTv, confirmnPswTv;
    // 提交
    private Button saveBtn;
    // 通知
    private ToastUtil toastUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatepwd);
        initView();
    }

    @Override
    protected void initView()
    {
        super.initView();
        toastUtil = ToastUtil.getInstant();
        navigationBar = (NavigationBar) findViewById(R.id.nb_updatepwd);
        navigationBar.setTitle("修改密码");
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
        oldPswTv = (TextView) findViewById(R.id.et_old_pwd);
        newPswTv = (TextView) findViewById(R.id.et_new_pwd);
        confirmnPswTv = (TextView) findViewById(R.id.et_confirm_new_pwd);
        saveBtn = (Button) findViewById(R.id.bt_save);
        saveBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.bt_save:
                updatePassword();
                break;
        }
    }

    /**
     * 功能：修改密码
     */
    private void updatePassword()
    {
        String oldPsw = oldPswTv.getText().toString().trim();
        String newPsw = newPswTv.getText().toString().trim();
        String confirmPsw = confirmnPswTv.getText().toString().trim();
        if (TextUtils.isEmpty(oldPsw))
        {
            toastUtil.show("原密码不能为空！");
            return;
        }
        if (TextUtils.isEmpty(newPsw))
        {
            toastUtil.show("新密码不能为空！");
            return;
        }
        if (TextUtils.isEmpty(confirmPsw))
        {
            toastUtil.show("确认密码不能为空！");
            return;
        }
        if (!newPsw.equals(confirmPsw))
        {
            toastUtil.show("2次密码不一致！");
            return;
        }
        RequestParams requestParams = new RequestParams();
        requestParams.put("phone",ApplicationEx.getInstance().getUserInfo().getPhone());
        requestParams.put("oldpassword", Md5Utils.encodeBy32BitMD5(oldPsw));
        requestParams.put("newpassword",Md5Utils.encodeBy32BitMD5(newPsw));
        HttpUtil.post(Constants.UPDATEPWD_URL, requestParams, new LoadJsonHttpResponseHandler(this, new LoadDatahandler()
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
                    String suc = data.get("suc").toString();
                    String msg = data.get("msg").toString();
                    if ("y".equals(suc))
                    {
                        // 修改密码成功
                        PreferenceUtils.setUserId("");
                        PreferenceUtils.setUserInfoBean("");
                        Intent intentToLogin = new Intent(UpdatePwdActivity.this,LoginActivity.class);
                        startActivity(intentToLogin);
                        finish();
                        MainActivity2.index=0;
                        EventBus.getDefault().post(new FirstEvent(""));
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
                toastUtil.show("");
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
        MobclickAgent.onPageStart("UpdatePwdActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("UpdatePwdActivity");
    }
}