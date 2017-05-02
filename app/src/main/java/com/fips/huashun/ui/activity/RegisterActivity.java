package com.fips.huashun.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
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
 * 功能：注册页面（注册和获取验证码功能） Created by Administrator on 2016/8/12.
 *
 * @author 张柳 时间：2016年8月12日16:36:07
 */
public class RegisterActivity extends BaseActivity implements OnClickListener {

  // 注册
  private Button registerBtn;
  // 获取验证码
  private TextView verificationCodeTv;
  // 手机号码，验证码
  private EditText phoneEt, verificationCodeEt;
  // 密码
  private EditText passwordEt;
  private ImageView pswShowIv;
  // 返回
  private LinearLayout backLl;
  // 提示
  private ToastUtil toastUtil;
  // 0 是隐藏 1是显示
  private int pswShowIndex = 0;
  private CountDownTimerUtils mCountDownTimerUtils;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_regester);
    initView();
  }

  @Override
  protected void initView() {
    super.initView();
    toastUtil = ToastUtil.getInstant();
    registerBtn = (Button) findViewById(R.id.bt_register);
    backLl = (LinearLayout) findViewById(R.id.ll_back);
    verificationCodeTv = (TextView) findViewById(R.id.tv_verificationcode);
    phoneEt = (EditText) findViewById(R.id.et_phone_number);
    verificationCodeEt = (EditText) findViewById(R.id.et_verificationcode);
    passwordEt = (EditText) findViewById(R.id.et_password);
    pswShowIv = (ImageView) findViewById(R.id.iv_psw_show);
    registerBtn.setOnClickListener(this);
    verificationCodeTv.setOnClickListener(this);
    pswShowIv.setOnClickListener(this);
    backLl.setOnClickListener(this);
    // 网络状态提醒
    checkNetState();
  }

  /**
   * 功能：网络状态提醒
   */
  private void checkNetState() {
    if (!Utils.isNetWork(this)) {
      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setTitle("网络状态提醒");
      builder.setMessage("当前网络不可用，是否打开网络设置?");
      builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
        }
      });
      builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
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
  public boolean isSystemBarTranclucent() {
    return true;
  }

  @Override
  public void onClick(View v) {
    String phone = phoneEt.getText().toString().trim();
    String verificationCode = verificationCodeEt.getText().toString().trim();
    String password = passwordEt.getText().toString().trim();
    switch (v.getId()) {
      case R.id.bt_register:
        // 注册
        if (TextUtils.isEmpty(phone)) {
          toastUtil.show("手机号码不能为空");
        } else {
          if (TextUtils.isEmpty(verificationCode)) {
            toastUtil.show("验证码不能为空");
          } else {
            if (TextUtils.isEmpty(password)) {
              toastUtil.show("密码不能为空");
            } else {
              // 开始注册
              register(phone, verificationCode, password);
            }
          }
        }
        break;
      case R.id.tv_verificationcode:
        // 获取验证码
        if (TextUtils.isEmpty(phone)) {
          toastUtil.show("手机号码不能为空");
        } else {
          mCountDownTimerUtils = new CountDownTimerUtils(verificationCodeTv, 60000, 1000);
          getVerificationCode(phone);
        }
        break;
      case R.id.ll_back:
        finish();
        break;
      case R.id.iv_psw_show:
        Log.e("pswShowIndex", "pswShowIndex=" + pswShowIndex);
        if (pswShowIndex == 0) {
          pswShowIndex = 1;
          pswShowIv.setImageResource(R.drawable.password_show);
          passwordEt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
          passwordEt.setSelection(passwordEt.getText().toString().length());

        } else {
          pswShowIndex = 0;
          pswShowIv.setImageResource(R.drawable.password_hide);
          passwordEt
              .setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
          passwordEt.setSelection(passwordEt.getText().toString().length());
        }
        break;
    }
  }

  /**
   * 功能：注册
   *
   * @param phone 手机号码
   * @param verificationCode 验证码
   * @param password 密码
   * @author 张柳 时间：2016年8月18日16:36:15
   */
  private void register(String phone, String verificationCode, String password) {
    RequestParams requestParams = new RequestParams();
    // 账号（即手机号码）
    requestParams.put("phone", phone);
    // 验证码
    requestParams.put("code", verificationCode);
    // 密码MD5加密
    requestParams.put("password", Md5Utils.encodeBy32BitMD5(password));
    HttpUtil.post(Constants.REGISTER_URL, requestParams,
        new LoadJsonHttpResponseHandler(this, new LoadDatahandler() {
          @Override
          public void onStart() {
            super.onStart();
            showLoadingDialog();
          }

          @Override
          public void onSuccess(JSONObject data) {
            super.onSuccess(data);
            dimissLoadingDialog();
            try {
              String suc = data.get("suc").toString();
              if ("y".equals(suc)) {
                finish();
              }
            } catch (JSONException e) {
              e.printStackTrace();
            }
          }

          @Override
          public void onFailure(String error, String message) {
            super.onFailure(error, message);
            dimissLoadingDialog();
          }
        }));
  }

  /**
   * 功能：获取验证码 * @param phone 手机号码
   *
   * @author 张柳 时间：2016年8月18日16:36:15
   */
  private void getVerificationCode(String phone) {
    RequestParams requestParams = new RequestParams();
    requestParams.put("phone", phone);
    HttpUtil.post(Constants.REGISTER_VERIFICATIONCODE_URL, requestParams,
        new LoadJsonHttpResponseHandler(this, new LoadDatahandler() {
          @Override
          public void onStart() {
            super.onStart();
            showLoadingDialog();
          }
          @Override
          public void onSuccess(JSONObject data) {
            super.onSuccess(data);
            dimissLoadingDialog();
            try {
              String suc = data.get("suc").toString();
              String msg = data.get("msg").toString();
              if ("y".equals(suc)) {
                toastUtil.show("验证码已发送，请查收!");
                if (mCountDownTimerUtils != null) {
                  mCountDownTimerUtils.start();
                }
              } else {
                toastUtil.show(msg);

              }
            } catch (JSONException e) {
              e.printStackTrace();
            }
          }

          @Override
          public void onFailure(String error, String message) {
            super.onFailure(error, message);
            dimissLoadingDialog();
          }
        }));
  }

  @Override
  protected void onResume() {
    super.onResume();
    MobclickAgent.onPageStart("RegisterActivity");
  }

  @Override
  protected void onPause() {
    super.onPause();
    MobclickAgent.onPageEnd("RegisterActivity");
  }
}
