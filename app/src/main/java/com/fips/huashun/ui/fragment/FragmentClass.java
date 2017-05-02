package com.fips.huashun.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fips.huashun.ApplicationEx;
import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.modle.bean.UserInfo;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.activity.LoginActivity;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.ToastUtil;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author hulin
 */
public class FragmentClass extends Fragment {

  private View rootView;
  // 有企业的布局
  private FragmentOwnEnterprise fragmentOwnEnterprise;
  // 没有企业的布局
  private FragmentNoEnterprise fragmentNoEnterprise;
  private String type = "";
  private Gson mGson;
  private UserInfo userinfo;
  private boolean HASENTERPRISE;//是否有企业

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    Log.e("CLASS onCreateView", "onCreateView");
    if (rootView == null) {
      rootView = inflater.inflate(R.layout.fragment_enterprise, container, false);
    }
    ViewGroup parent = (ViewGroup) rootView.getParent();
    if (parent != null) {
      parent.removeView(rootView);
    }
    registerBoradcastReceiver();
    return rootView;
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    Log.e("FragmentClass", "onViewCreated");
    mGson = new Gson();
//    getUserInfo();
    initData();
    registerBoradcastReceiver();
  }

  @Override
  public void onResume() {
    super.onResume();
    MobclickAgent.onPageStart("FragmentClass");
    MobclickAgent.onResume(getActivity());
  }

  @Override
  public void onPause() {
    super.onPause();
    MobclickAgent.onPageEnd("FragmentClass");
    MobclickAgent.onPause(getActivity());
  }

  private void initData()
  {
    Log.e("FragmentClass", "initData");
    // 判断是否登录
    if (ApplicationEx.getInstance().isLogined()) {
      if (null == type || TextUtils.isEmpty(type)) {
        if (ApplicationEx.getInstance().getUserInfo().getStatus().equals("0")) {
//        if (!HASENTERPRISE){
          Log.e("FragmentClass", "没有企业");
          // 0 没有企业
          fragmentNoEnterprise = new FragmentNoEnterprise();
          FragmentTransaction transaction = getActivity().getSupportFragmentManager()
              .beginTransaction();
          transaction.replace(R.id.fl_noenterprise, fragmentNoEnterprise);
          // 提交
          transaction.commit();
        } else {
          Log.e("FragmentClass", "有企业");
          // 有企业
          fragmentOwnEnterprise = new FragmentOwnEnterprise();
          fragmentOwnEnterprise.type = "1";
          FragmentTransaction transaction = getActivity().getSupportFragmentManager()
              .beginTransaction();
          transaction.replace(R.id.fl_noenterprise, fragmentOwnEnterprise);
          //提交
          transaction.commit();
        }
      } else if (type.equals("0")) {
        Log.e("FragmentClass", "游客体验");
        // 游客体验
        fragmentOwnEnterprise = new FragmentOwnEnterprise();
        fragmentOwnEnterprise.type = "0";
        FragmentTransaction transaction = getActivity().getSupportFragmentManager()
            .beginTransaction();
        transaction.replace(R.id.fl_noenterprise, fragmentOwnEnterprise);
        // 提交
        transaction.commitAllowingStateLoss();
      } else if (type.equals("1")) {
        Log.e("FragmentClass", "type有企业");
        // 有企业
        fragmentOwnEnterprise = new FragmentOwnEnterprise();
        fragmentOwnEnterprise.type = "1";
        FragmentTransaction transaction = getActivity().getSupportFragmentManager()
            .beginTransaction();
        transaction.replace(R.id.fl_noenterprise, fragmentOwnEnterprise);
        // 提交
        transaction.commitAllowingStateLoss();
      } else if (type.equals("2")) {
        Log.e("FragmentClass", "回到企业注册页面");
        // 有企业
        fragmentNoEnterprise = new FragmentNoEnterprise();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager()
            .beginTransaction();
        transaction.replace(R.id.fl_noenterprise, fragmentNoEnterprise);
        // 提交
        transaction.commitAllowingStateLoss();
      }
    } else {
      Intent intentToLogin = new Intent(getActivity(), LoginActivity.class);
      startActivity(intentToLogin);
    }
  }

  private void initListener() {
  }
  private void initActivity() {
  }
  // 注册广播
  public void registerBoradcastReceiver() {
    IntentFilter myIntentFilter = new IntentFilter();
    myIntentFilter.addAction("com.fipx.huashun.enterprise");
    // 注册广播
    getActivity().registerReceiver(mBroadcastReceiver, myIntentFilter);
  }

  // 广播接收器
  private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      String action = intent.getAction();
      if (action.equals("com.fipx.huashun.enterprise")) {
        // 0 游客体验 1 申请验证企业成功
        type = intent.getStringExtra("type");
        Log.e("BroadcastReceiver", "type=" + type);
        initData();
      }
    }
  };

  @Override
  public void onHiddenChanged(boolean hidden) {
    super.onHiddenChanged(hidden);
    Log.e("FragmentClass", "onHiddenChanged hidden=" + hidden);
    if (!hidden) {
      initData();
    }
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    // 注销广播
    getActivity().unregisterReceiver(mBroadcastReceiver);
  }

  //联网获取用户信息
  public void getUserInfo() {
    RequestParams requestParams = new RequestParams();
    requestParams.put("userid", PreferenceUtils.getUserId());
    HttpUtil.post(Constants.SHOWSELFINFO_URL, requestParams,
        new LoadJsonHttpResponseHandler(getActivity(), new LoadDatahandler() {
          @Override
          public void onStart() {
            super.onStart();
          }

          @Override
          public void onSuccess(JSONObject data) {
            super.onSuccess(data);
            try {
              String suc = data.get("suc").toString();
              String msg = data.get("msg").toString();
              if ("y".equals(suc)) {
                userinfo = mGson.fromJson(data.getString("data").toString(), UserInfo.class);

                HASENTERPRISE = userinfo.equals("")?false:true;
                Log.i("test","是否有企业 ："+HASENTERPRISE);
              } else {
                ToastUtil.getInstant().show(msg);
              }
            } catch (JSONException e) {
              e.printStackTrace();
              Log.e("JSONException", "JSONException");
            }
          }

          @Override
          public void onFailure(String error, String message) {
            super.onFailure(error, message);
            Log.e("onFailure", "onFailure");
          }
        }));
  }
}
