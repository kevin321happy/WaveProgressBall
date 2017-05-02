package com.fips.huashun.ui.activity;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.utils.CountDownTimerUtils;
import com.fips.huashun.ui.utils.Md5Utils;
import com.fips.huashun.ui.utils.ToastUtil;
import com.fips.huashun.ui.utils.Utils;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 功能：忘记密码页面（重置密码和获取验证码功能）
 * Created by Administrator on 2016/8/12.
 *
 * @author 张柳 时间：2016年8月12日16:36:07
 */
public class ForgotPwdActivity extends BaseActivity implements OnClickListener
{

    // 提交
    private Button submitBtn;
    // 获取验证码
    private TextView verificationCodeTv;
    // 手机号码，验证码，密码,确认密码
    private EditText phoneEt, verificationCodeEt, passwordEt, confirmPwdEt;
    // 返回
    private LinearLayout backLl;
    // 提示
    private ToastUtil toastUtil;
    private ImageView pswShowIv,pswConfirmShowIv;
    // 0 是隐藏 1是显示
    private int pswShowIndex = 0;
    // 0 是隐藏 1是显示
    private int pswConfirmShowIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpwd);
        initView();
    }

    @Override
    protected void initView()
    {
        super.initView();
        toastUtil = ToastUtil.getInstant();
        submitBtn = (Button) findViewById(R.id.bt_submit);
        backLl = (LinearLayout) findViewById(R.id.ll_back);
        verificationCodeTv = (TextView) findViewById(R.id.tv_verificationcode);
        phoneEt = (EditText) findViewById(R.id.et_phone_number);
        verificationCodeEt = (EditText) findViewById(R.id.et_verificationcode);
        passwordEt = (EditText) findViewById(R.id.et_password);
        confirmPwdEt = (EditText) findViewById(R.id.et_confimpwd);
        pswShowIv = (ImageView) findViewById(R.id.iv_psw_show);
        pswConfirmShowIv = (ImageView) findViewById(R.id.iv_confirmpsw_show);
        pswShowIv.setOnClickListener(this);
        pswConfirmShowIv.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
        verificationCodeTv.setOnClickListener(this);
        backLl.setOnClickListener(this);
    }

    @Override
    public boolean isSystemBarTranclucent()
    {
        return true;
    }

    @Override
    public void onClick(View v)
    {
        String phone = phoneEt.getText().toString().trim();
        String verificationCode = verificationCodeEt.getText().toString().trim();
        String password = passwordEt.getText().toString().trim();
        String confirmPwd = confirmPwdEt.getText().toString().trim();
        switch (v.getId())
        {
            case R.id.bt_submit:
            // 提交
                if (TextUtils.isEmpty(phone))
                {
                    toastUtil.show("手机号码不能为空");
                    return;
                }
//                if (!Utils.isMobileNO(phone))
//                {
//                    toastUtil.show("手机号码不正确");
//                    return;
//                }
                if (TextUtils.isEmpty(verificationCode))
                {
                    toastUtil.show("验证码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(password))
                {
                    toastUtil.show("新密码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(confirmPwd))
                {
                    toastUtil.show("确认新密码不能为空");
                    return;
                }
                if (!password.equals(confirmPwd))
                {
                    toastUtil.show("2次密码不一致");
                    return;
                }
                // 开始进行忘记密码请求
                forgorSubmit(phone, verificationCode, password);
                break;
            case R.id.tv_verificationcode:
            // 获取验证码
                if (TextUtils.isEmpty(phone))
                {
                    toastUtil.show("手机号码不能为空");
                    return;
                }
                if (!Utils.isnumber(phone))
                {
                    toastUtil.show("手机号码不正确");
                    return;
                }
                CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(verificationCodeTv, 60000, 1000);
                mCountDownTimerUtils.start();
                getVerificationCode(phone);
                break;
            case R.id.ll_back:
                finish();
                break;
            case R.id.iv_psw_show:
                if (pswShowIndex == 0)
                {
                    pswShowIndex = 1;
                    pswShowIv.setImageResource(R.drawable.password_show);
                    passwordEt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordEt.setSelection(passwordEt.getText().toString().length());

                } else
                {
                    pswShowIndex = 0;
                    pswShowIv.setImageResource(R.drawable.password_hide);
                    passwordEt.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordEt.setSelection(confirmPwdEt.getText().toString().length());
                }
                break;
            case R.id.iv_confirmpsw_show:
                if (pswConfirmShowIndex == 0)
                {
                    pswConfirmShowIndex = 1;
                    pswConfirmShowIv.setImageResource(R.drawable.password_show);
                    confirmPwdEt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    confirmPwdEt.setSelection(confirmPwdEt.getText().toString().length());

                } else
                {
                    pswConfirmShowIndex = 0;
                    pswConfirmShowIv.setImageResource(R.drawable.password_hide);
                    confirmPwdEt.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    confirmPwdEt.setSelection(confirmPwdEt.getText().toString().length());
                }
                break;
        }
    }

    /**
     * 功能：忘记密码提交请求
     *
     * @param phone            手机号码
     * @param verificationCode 验证码
     * @param password         密码
     * @author 张柳 时间：2016年8月18日16:36:15
     */
    private void forgorSubmit(String phone, String verificationCode, String password)
    {
        RequestParams requestParams = new RequestParams();
        requestParams.put("phone", phone);
        requestParams.put("code", verificationCode);
        requestParams.put("password", Md5Utils.encodeBy32BitMD5(password));
        HttpUtil.post(Constants.FORGOTPWD_URL, requestParams, new LoadJsonHttpResponseHandler(this, new LoadDatahandler()
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
                    toastUtil.show(msg);
                    if ("y".equals(suc))
                    {
                        finish();
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
            }
        }));
    }

    /**
     * 功能：忘记密码页面 -- 获取验证码
     *
     * @param phone 手机号码
     * @author 张柳 时间：2016年8月18日16:36:15
     */
    private void getVerificationCode(String phone)
    {
        RequestParams requestParams = new RequestParams();
        requestParams.put("phone", phone);
        HttpUtil.post(Constants.FORGOTPWD_VERIFICATIONCODE_URL, requestParams, new LoadJsonHttpResponseHandler(this, new LoadDatahandler()
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
                    String msg = data.get("msg").toString();
                    toastUtil.show(msg);
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
            }
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("ForgotPwdActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("ForgotPwdActivity");
    }
}