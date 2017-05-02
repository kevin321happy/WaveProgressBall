package com.fips.huashun.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.fips.huashun.ApplicationEx;
import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.ToastUtil;
import com.loopj.android.http.RequestParams;
import com.pingplusplus.android.Pingpp;
import com.pingplusplus.android.PingppLog;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

public class RechargeBeanActivity extends BaseActivity implements View.OnClickListener
{

    private RadioButton rb_01;
    private RadioButton rb_02;
    private RadioButton rb_03;
    private RadioButton rb_04;
    private EditText et_money;
    private TextView tv_charge_bean,tv_phone,tv_bean_point;
    private LinearLayout ll_money;
    private View cb_zfb,cb_wx;
    private Button bt_chonzhi;
    private Intent intent;
    private String beanPoint;
    private int payStyle = 0;

    /**
     * 微信支付渠道
     */
    private static final String CHANNEL_WECHAT = "wx";
    /**
     * 支付支付渠道
     */
    private static final String CHANNEL_ALIPAY = "alipay";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_bean);
        PingppLog.DEBUG = true;
        initView();
    }

    @Override
    public boolean isSystemBarTranclucent()
    {
        return false;
    }

    @Override
    protected void initView()
    {
        findViewById(R.id.ll_pay01).setOnClickListener(this);
        findViewById(R.id.ll_pay02).setOnClickListener(this);
        super.initView();
        intent = getIntent();
        beanPoint = intent.getStringExtra("beanPoint");
        NavigationBar navigationBar = (NavigationBar) findViewById(R.id.na_bar);
        navigationBar.setTitle("魔豆充值");
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
        rb_01 = (RadioButton) findViewById(R.id.rb_01);
        rb_02 = (RadioButton) findViewById(R.id.rb_02);
        rb_03 = (RadioButton) findViewById(R.id.rb_03);
        rb_04 = (RadioButton) findViewById(R.id.rb_04);
        et_money = (EditText) findViewById(R.id.et_money);
        tv_charge_bean = (TextView) findViewById(R.id.tv_charge_bean);
        tv_charge_bean.setText("0");
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_phone.setText(ApplicationEx.getInstance().getUserInfo().getPhone());
        tv_bean_point = (TextView) findViewById(R.id.tv_bean_point);
        tv_bean_point.setText(beanPoint+"魔豆");
        cb_zfb = findViewById(R.id.iv_pay_zfb);
        cb_wx = findViewById(R.id.iv_pay_weixin);
        bt_chonzhi = (Button) findViewById(R.id.bt_chonzhi);
        bt_chonzhi.setOnClickListener(this);
        ll_money = (LinearLayout) findViewById(R.id.ll_money);
        et_money.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                Log.e("et_money","hasFocus="+hasFocus);
                if (hasFocus)
                {
                    rb_01.setChecked(false);
                    rb_02.setChecked(false);
                    rb_03.setChecked(false);
                    rb_04.setChecked(true);
                    et_money.setCursorVisible(true);

                    if(TextUtils.isEmpty(et_money.getText().toString()))
                    {
                        tv_charge_bean.setText("0");
                    } else
                    {
                        tv_charge_bean.setText(et_money.getText().toString());
                    }
                    et_money.setBackgroundColor(getResources().getColor(R.color.blue_bg));
                    et_money.setTextColor(getResources().getColor(R.color.white));
                } else
                {
                    et_money.clearFocus();
                    et_money.setText("");
                    et_money.setBackgroundResource(R.drawable.rediaobutton_shape);
                    et_money.setTextColor(getResources().getColor(R.color.mycourse_text_blue));
                }

            }
        });

        rb_01.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    et_money.clearFocus();
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    if (inputMethodManager.isActive())
                    {
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    tv_charge_bean.setText(rb_01.getText().toString().replace("魔豆",""));
                }
            }
        });
        rb_02.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    et_money.clearFocus();
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    if (inputMethodManager.isActive())
                    {
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    tv_charge_bean.setText(rb_02.getText().toString().replace("魔豆",""));

                }
            }
        });
        rb_03.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    et_money.clearFocus();

                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    if (inputMethodManager.isActive())
                    {
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    tv_charge_bean.setText(rb_03.getText().toString().replace("魔豆",""));

                }
            }
        });
        et_money.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if(TextUtils.isEmpty(s.toString()))
                {
                    tv_charge_bean.setText("0");
                } else
                {
                    tv_charge_bean.setText(et_money.getText().toString());
                }

            }
        });
    }
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.bt_chonzhi:
                String charge_bean = tv_charge_bean.getText().toString();
                if(TextUtils.isEmpty(charge_bean) || "0".equals(charge_bean))
                {
                    ToastUtil.getInstant().show("充值魔豆不能为空");
                    return;
                }
                if(payStyle == 0)
                {
                    ToastUtil.getInstant().show("请选择充值方式");
                } else if(payStyle == 1)
                {// zfb
                    getCharge(CHANNEL_ALIPAY,String.valueOf(Integer.valueOf(charge_bean)*100));
                }else if(payStyle == 2)
                {// weixin
                    getCharge(CHANNEL_WECHAT,String.valueOf(Integer.valueOf(charge_bean)*100));
                }
                bt_chonzhi.setOnClickListener(null);
                break;
            case R.id.ll_pay01:
                payStyle = 1;
                cb_zfb.setVisibility(View.VISIBLE);
                cb_wx.setVisibility(View.GONE);
                break;
            case R.id.ll_pay02:
                payStyle = 2;
                cb_zfb.setVisibility(View.GONE);
                cb_wx.setVisibility(View.VISIBLE);
                break;
        }
    }
    /**
     * 功能：魔豆充值
     */
    private void rechargeMagicBean(String charge_bean)
    {
        RequestParams requestParams = new RequestParams();
        requestParams.put("userid", PreferenceUtils.getUserId());
        requestParams.put("benas", charge_bean);
        HttpUtil.post(Constants.ADDBEANS_URL, requestParams, new LoadJsonHttpResponseHandler(this, new LoadDatahandler()
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

                        ToastUtil.getInstant().show("充值成功");
                        finish();
                    } else
                    {
                        ToastUtil.getInstant().show("充值失败,请重试!");
                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                    ToastUtil.getInstant().show("充值失败,请重试!");
                }
            }

            @Override
            public void onFailure(String error, String message)
            {
                super.onFailure(error, message);
                Log.e("onFailure","onFailure");
                dimissLoadingDialog();
                ToastUtil.getInstant().show("充值失败,请重试!");
            }
        }));
    }


    private void  getCharge(String payType,String amount){
        RequestParams requestParams = new RequestParams();
        requestParams.put("amount", amount);
        requestParams.put("subject", "魔豆");
        requestParams.put("body", amount);
        requestParams.put("client_ip", "127.0.0.1");
        requestParams.put("channel", payType);
        requestParams.put("userid", PreferenceUtils.getUserId());
        HttpUtil.post(Constants.URL+"/toCreateCharge", requestParams, new LoadJsonHttpResponseHandler(this, new LoadDatahandler(){
            @Override
            public void onSuccess(JSONObject data) {
                super.onSuccess(data);
                try {
                    Pingpp.createPayment(RechargeBeanActivity.this, data.getString("data"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error, String message) {
                super.onFailure(error, message);
            }

            @Override
            public void onStart() {
                super.onStart();
            }
        }));
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

//        wechatButton.setOnClickListener(ClientSDKActivity.this);
//        alipayButton.setOnClickListener(ClientSDKActivity.this);
//        upmpButton.setOnClickListener(ClientSDKActivity.this);
//        bfbButton.setOnClickListener(ClientSDKActivity.this);
        bt_chonzhi.setOnClickListener(RechargeBeanActivity.this);
        //支付页面返回处理
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            if (resultCode == RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
                /* 处理返回值
                 * "success" - payment succeed
                 * "fail"    - payment failed
                 * "cancel"  - user canceld
                 * "invalid" - payment plugin not installed
                 */
                if(result.equals("success"))
                {
                    result="支付成功";

                } else if(result.equals("fail"))
                {
                    result="支付失败";
                } else if(result.equals("cancel"))
                {
                    result="支付取消";
                } else if(result.equals("invalid"))
                {
                    result="未安装支付宝客户端";
                }
                Log.e("result","result="+result);
                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
                Log.e("errorMsg","errorMsg="+errorMsg);
                String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
                Log.e("extraMsg","extraMsg="+extraMsg);
                showMsg(result, errorMsg, extraMsg);
                initBean();
            }
        }
    }
    public void showMsg(String title, String msg1, String msg2) {
        String str = title;
        /*if (null !=msg1 && msg1.length() != 0) {
            str += "\n" + msg1;
        }
        if (null !=msg2 && msg2.length() != 0) {
            str += "\n" + msg2;
        }*/
        AlertDialog.Builder builder = new AlertDialog.Builder(RechargeBeanActivity.this);
        builder.setMessage(str);
        builder.setTitle("提示");
        builder.setPositiveButton("确定", null);
        builder.create().show();
    }


    private void initBean()
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
                        tv_bean_point.setText(beanPoints+"魔豆");
//                        beanPointsTv.setText(beanPoints);
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
        MobclickAgent.onPageStart("RechargeBeanActivity");
//        initBean();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("RechargeBeanActivity");
    }
}
