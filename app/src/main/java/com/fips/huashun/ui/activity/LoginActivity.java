package com.fips.huashun.ui.activity;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
import com.fips.huashun.ApplicationEx;
import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.common.URLConstants;
import com.fips.huashun.modle.bean.UserInfo;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.fragment.MainActivity2;
import com.fips.huashun.ui.utils.AlertDialogUtils;
import com.fips.huashun.ui.utils.DeviceUtil;
import com.fips.huashun.ui.utils.Md5Utils;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.RuleTool;
import com.fips.huashun.ui.utils.SPUtils;
import com.fips.huashun.ui.utils.SystemBarTintManager;
import com.fips.huashun.ui.utils.ToastUtil;
import com.fips.huashun.ui.utils.Utils;
import com.loopj.android.http.RequestParams;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.Config;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import java.util.HashMap;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends BaseActivity implements OnClickListener {

  // 登录
  private Button loginBtn;
  // 免费注册，忘记密码
  private TextView registerTv, forgotPwdTv;
  // 手机号，密码
  private EditText phoneEt, passwordEt;
  // 返回
  private LinearLayout backLl;
  private ImageView qqIv, weixinIv, weiboIv;
  private ImageView pswShowIv;
  private UMShareAPI mShareAPI;
  private ToastUtil toastUtil;
  private String page;
  private long mExitTime;
  // 0 是隐藏 1是显示
  private int pswShowIndex = 0;
  private String mPhone;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    // setImmersionState(R.color.transparent);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      setTranslucentStatus(true);
      SystemBarTintManager tintManager = new SystemBarTintManager(this);
      tintManager.setStatusBarTintEnabled(true);
      tintManager.setStatusBarTintResource(R.color.transparent);//通知栏所需颜色
    }
    page = getIntent().getStringExtra("page");
    mShareAPI = UMShareAPI.get(this);
    initView();
  }

  public void setImmersionState(int res) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      //  window.setStatusBarColor(Color.TRANSPARENT);
      setTranslucentStatus(true);
      SystemBarTintManager tintManager = new SystemBarTintManager(this);
      //tintManager.setNavigationBarAlpha(0.9f);
      tintManager.setStatusBarTintEnabled(true);
      tintManager.setStatusBarTintResource(res);//通知栏所需颜色
    }
  }

  @Override
  protected void initView() {
    super.initView();
    toastUtil = ToastUtil.getInstant();
    loginBtn = (Button) findViewById(R.id.bt_login);
    backLl = (LinearLayout) findViewById(R.id.ll_back);
    registerTv = (TextView) findViewById(R.id.tv_register);
    forgotPwdTv = (TextView) findViewById(R.id.tv_forgotpwd);
    phoneEt = (EditText) findViewById(R.id.et_phone_number);
    passwordEt = (EditText) findViewById(R.id.et_password);
    qqIv = (ImageView) findViewById(R.id.iv_login_qq);
    weixinIv = (ImageView) findViewById(R.id.iv_login_weixin);
    weiboIv = (ImageView) findViewById(R.id.iv_login_weibo);
    pswShowIv = (ImageView) findViewById(R.id.iv_psw_show);
    pswShowIv.setOnClickListener(this);
    loginBtn.setOnClickListener(this);
    registerTv.setOnClickListener(this);
    forgotPwdTv.setOnClickListener(this);
    backLl.setOnClickListener(this);
    qqIv.setOnClickListener(this);
    weixinIv.setOnClickListener(this);
    weiboIv.setOnClickListener(this);
    checkNetState();
  }

  private void checkNetState() {
    if (!Utils.isNetWork(this)) {
      AlertDialogUtils.showTowBtnDialog(this, "当前网络不可用，是否打开网络设置?", "取消", "确认",
          new AlertDialogUtils.DialogClickInter() {
            @Override
            public void leftClick(android.app.AlertDialog dialog) {
              dialog.cancel();
            }

            @Override
            public void rightClick(android.app.AlertDialog dialog) {
              if (android.os.Build.VERSION.SDK_INT > 10) {
                startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
              } else {
                startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
              }
            }
          });
    }
  }

  @Override
  public boolean isSystemBarTranclucent() {
    return true;
  }


  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.bt_login:
        checkClick(R.id.bt_login);
        mPhone = phoneEt.getText().toString().trim();
        String password = passwordEt.getText().toString().trim();
        if (TextUtils.isEmpty(mPhone)) {
          // 账号不能为空
          toastUtil.show("账号不能为空");
          return;
        }
        if (TextUtils.isEmpty(password)) {
          // 密码不能为空
          toastUtil.show("密码不能为空");
          return;
        }
        // 密码MD5加密
        login(mPhone, Md5Utils.encodeBy32BitMD5(password));
        //友盟账号统计
        MobclickAgent.onProfileSignIn(mPhone);
        break;
      case R.id.tv_register:
        Intent intentToReg = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intentToReg);
        break;
      case R.id.tv_forgotpwd:
        Intent intentToForgotPsw = new Intent(getApplicationContext(), ForgotPwdActivity.class);
        startActivity(intentToForgotPsw);
        break;
      case R.id.ll_back:
        if (TextUtils.isEmpty(page)) {
          finish();
        } else {
          finish();
          MainActivity2.index = 0;
        }
        break;
      case R.id.iv_login_qq:
        checkClick(R.id.iv_login_qq);
        SHARE_MEDIA platform_qq = SHARE_MEDIA.QQ;
        MobclickAgent.onProfileSignIn("QQ", mPhone);
        mShareAPI.doOauthVerify(LoginActivity.this, platform_qq, umAuthListener);
        break;
      case R.id.iv_login_weixin:
        checkClick(R.id.iv_login_weixin);
        SHARE_MEDIA platform_weixin = SHARE_MEDIA.WEIXIN;
        MobclickAgent.onProfileSignIn("WX", mPhone);
        mShareAPI.doOauthVerify(LoginActivity.this, platform_weixin, umAuthListener);
        break;
      case R.id.iv_login_weibo:
        checkClick(R.id.iv_login_weibo);
        Config.REDIRECT_URL = "http://www.sina.com";
        SHARE_MEDIA platform_sina = SHARE_MEDIA.SINA;
        MobclickAgent.onProfileSignIn("WB", mPhone);
        mShareAPI.doOauthVerify(LoginActivity.this, platform_sina, umAuthListener);
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

  //友盟第三方登录的监听回调
  private UMAuthListener umAuthListener = new UMAuthListener() {
    @Override
    public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
      //Toast.makeText( getApplicationContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();
      toastUtil.show("Authorize succeed");
    }

    @Override
    public void onError(SHARE_MEDIA platform, int action, Throwable t) {
      //Toast.makeText( getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
      toastUtil.show("Authorize fail");
    }

    @Override
    public void onCancel(SHARE_MEDIA platform, int action) {
      //Toast.makeText( getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
      toastUtil.show("Authorize cancel");
    }
  };

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    mShareAPI.onActivityResult(requestCode, resultCode, data);
  }

  /**
   * 功能：登录
   *
   * @param phone 账号
   * @param password 密码
   * @author 张柳 时间：2016年8月18日16:36:15
   */
  private void login(String phone, String password) {
////    String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
////    String str=phone+"|"+password+"|"+timestamp;
////    //签名数据
    HashMap<String, String> signdata = new HashMap<>();
    long time = System.currentTimeMillis() / 1000;
    String timestamp = String.valueOf(time);
    signdata.put("phone", phone);
    signdata.put("password", password);
    signdata.put("timestamp", timestamp);
    String[] Data = RuleTool.getSignData(this, signdata);
    RequestParams requestParams = new RequestParams();
    requestParams.put("phone", phone);
    requestParams.put("password", password);
    requestParams.put("timestamp", timestamp);
    requestParams.put("str", Data[1]);
    requestParams.put("sign", Data[0]);
    requestParams.put("app_id", "qmct");
    HttpUtil.post(URLConstants.USER_LOGIN, requestParams,
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
              String msg = data.get("info").toString();
              if ("y".equals(suc)) {
                UserInfo userinfo = gson
                    .fromJson(data.getString("data").toString(), UserInfo.class);
                ApplicationEx.getInstance().setUserInfo(userinfo);
                String userid = userinfo.getUserid();
                PreferenceUtils.setUserId(userid);
                PreferenceUtils.setQM_Token(userinfo.getQmct_token());
                PreferenceUtils.setRY_Token(userinfo.getRy_token());
                long time = System.currentTimeMillis();
                PreferenceUtils.setLoginTime(String.valueOf(time));
                PreferenceUtils.setReadstatus(userinfo.getReadstatus());
                //登录成功，绑定极光
                SetRegistrationid(userid);
                Intent intent = new Intent();
                setResult(1024, intent);
                if (!TextUtils.isEmpty(page)) {
                  if (page.equals("2")) {
                    MainActivity2.index = 2;
                  } else if (page.equals("1")) {
                    MainActivity2.index = 1;
                  }
                }
                upLoadDeviceInfo();
                finish();

              } else {
                ToastUtil.getInstant().show(msg);
              }
            } catch (JSONException e) {
              e.printStackTrace();
            }
          }

          @Override
          public void onFailure(String error, String message) {
            super.onFailure(error, message);
//            dimissLoadingDialog();
          }
        }));
  }

  //上传设备信息
  private void upLoadDeviceInfo() {
    HashMap<String, String> signdata = new HashMap<>();
    long time = System.currentTimeMillis() / 1000;
    String timestamp = String.valueOf(time);
    signdata.put("userid", PreferenceUtils.getUserId());
    signdata.put("app_version", DeviceUtil.getVersionName(this));
    signdata.put("os_version", DeviceUtil.getBuildVersion());
    signdata.put("machine_type", DeviceUtil.getPhoneModel());
    signdata.put("timestamp", timestamp);
    String[] Data = RuleTool.getSignData(this, signdata);
    OkGo.post(URLConstants.UPLOAD_USER_VERSION)
        .params("userid", PreferenceUtils.getUserId())
        .params("app_version", DeviceUtil.getVersionName(this))
        .params("os_version", DeviceUtil.getBuildVersion())
        .params("machine_type", DeviceUtil.getPhoneModel())
        .params("timestamp", timestamp)
        .params("str", Data[1])
        .params("sign", Data[0])
        .params("qmct_token",PreferenceUtils.getQM_Token())
        .execute(new StringCallback() {
          @Override
          public void onSuccess(String s, Call call, Response response) {
            ToastUtil.getInstant().show("上传成功");
          }
        });


  }

  //极光
  private void SetRegistrationid(String userid) {
    String registrationId = SPUtils.getString(this, "registrationId");
    OkGo.post(Constants.LOCAL_SET_REGISTRATIONID)
        .params("uid", userid)
        .params("registrationid", registrationId)
        .execute(new com.lzy.okgo.callback.StringCallback() {
          @Override
          public void onSuccess(String s, Call call, Response response) {

          }
        });
  }

  @Override
  protected void onResume() {
    super.onResume();
    MobclickAgent.onPageStart("LoginActivity");
  }

  @Override
  protected void onPause() {
    super.onPause();
    MobclickAgent.onPageEnd("LoginActivity");
  }
}
