package com.fips.huashun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.fips.huashun.ApplicationEx;
import com.fips.huashun.R;
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

import static com.fips.huashun.R.layout;

/**
 * 功能：修改性别
 * Created by Administrator on 2016/8/27.
 *
 * @author 张柳 时间：2016年8月27日10:28:31
 */
public class UpdateSexActivity extends BaseActivity implements View.OnClickListener
{
    private NavigationBar navigationBar;
    private RadioGroup sexRg;
    private RadioButton boyRb, girlRb;
    private ArrayList<RadioButton> rb;
    private Intent intent;
    private ToastUtil toastUtil;
    private String sex;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_updatesex);
        intent = getIntent();
        initView();
    }

    @Override
    protected void initView()
    {
        super.initView();
        toastUtil = ToastUtil.getInstant();
        sex = intent.getStringExtra("sex");
        sexRg = (RadioGroup) findViewById(R.id.rg_sex);
        boyRb = (RadioButton) findViewById(R.id.rb_boy);
        girlRb = (RadioButton) findViewById(R.id.rb_girl);
        navigationBar = (NavigationBar) findViewById(R.id.nb_updatesex);
        navigationBar.setTitle("修改性别");
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
                    updateName(sex);
                }
            }
        });
        rb = new ArrayList<RadioButton>();
        rb.add(boyRb);
        rb.add(girlRb);
        Log.e("sex",String.valueOf(sex));
        if("2".equals(sex))
        {
            rb.get(1).setChecked(true);
        } else
        {
            sex = "1";
            rb.get(0).setChecked(true);
        }
        sexRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                if (checkedId == R.id.rb_boy)
                {
                    sex = "1";
                } else if (checkedId == R.id.rb_girl)
                {
                    sex = "2";
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
    private void updateName(final String sex)
    {
        Log.e("updateName", "修改性别");
        RequestParams requestParams = new RequestParams();
        requestParams.put("userid", ApplicationEx.getInstance().getUserInfo().getUserid());
        requestParams.put("sex",sex);
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
                        intentToBack.putExtra("sex",sex);
                        UserInfo userinfo = ApplicationEx.getInstance().getUserInfo();
                        userinfo.setSex(sex);
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
    public boolean isSystemBarTranclucent()
    {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("UpdateSexActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("UpdateSexActivity");
    }
}
