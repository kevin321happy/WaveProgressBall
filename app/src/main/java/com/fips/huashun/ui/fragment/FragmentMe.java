package com.fips.huashun.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fips.huashun.ApplicationEx;
import com.fips.huashun.R;
import com.fips.huashun.common.ACache;
import com.fips.huashun.common.CacheConstans;
import com.fips.huashun.common.Constants;
import com.fips.huashun.modle.bean.UserInfo;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.activity.LoginActivity;
import com.fips.huashun.ui.activity.MagicBeansActivity;
import com.fips.huashun.ui.activity.MyBeansDetailActivity;
import com.fips.huashun.ui.activity.MyCollectionActivity;
import com.fips.huashun.ui.activity.MyCourseActivity;
import com.fips.huashun.ui.activity.MyIntegralActivity;
import com.fips.huashun.ui.activity.MyMedalActivity;
import com.fips.huashun.ui.activity.MyMessageActivity;
import com.fips.huashun.ui.activity.MyRankActivity;
import com.fips.huashun.ui.activity.PersonalInfoActivity;
import com.fips.huashun.ui.activity.SystemSettingsActivity;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.ToastUtil;
import com.fips.huashun.widgets.CircleImageView;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * @author hulin
 */
public class FragmentMe extends Fragment implements View.OnClickListener {
    RelativeLayout rlPhone;
    LinearLayout myCollectionLayout, myCourseLayout, myMessageLayout, myIntegralLayout,
            systemSettingsLayout, myMedalLayout, personalInfoLayout, myBeansDetailLayout;
    private View rootView, emptyView;
    // 头像
    private CircleImageView headIv;
    // 姓名，个性签名，等级，积分，当前魔豆
    private TextView nameTv, signatureTv, levelNameTv, pointsTv, beanPointsTv;
    // 解析
    protected Gson gson;
    // 个人信息
    private UserInfo userinfo;
    //缓存
    private ACache mACache;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_me, container, false);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gson = new Gson();
        //初始化缓存对象
        mACache = ACache.get(getActivity());
        initview();
    }
    private void initview() {
        NavigationBar navigationBar = (NavigationBar) rootView.findViewById(R.id.na_bar);
        navigationBar.setTitle("个人");
        rootView.findViewById(R.id.my_beans).setOnClickListener(this);
        rootView.findViewById(R.id.my_rank).setOnClickListener(this);
        headIv = (CircleImageView) rootView.findViewById(R.id.iv_head);
        nameTv = (TextView) rootView.findViewById(R.id.tv_name);
        signatureTv = (TextView) rootView.findViewById(R.id.tv_signature);
        levelNameTv = (TextView) rootView.findViewById(R.id.tv_levelname);
        pointsTv = (TextView) rootView.findViewById(R.id.tv_points);
        beanPointsTv = (TextView) rootView.findViewById(R.id.tv_bean_points);
        systemSettingsLayout = (LinearLayout) rootView.findViewById(R.id.ll_systemsettings);
        myMedalLayout = (LinearLayout) rootView.findViewById(R.id.ll_mymedal);
        personalInfoLayout = (LinearLayout) rootView.findViewById(R.id.ll_personalinfo);
        myCollectionLayout = (LinearLayout) rootView.findViewById(R.id.ll_order3);
        myCourseLayout = (LinearLayout) rootView.findViewById(R.id.ll_order2);
        myMessageLayout = (LinearLayout) rootView.findViewById(R.id.ll_order1);
        myIntegralLayout = (LinearLayout) rootView.findViewById(R.id.ll_myintegral);
        myBeansDetailLayout = (LinearLayout) rootView.findViewById(R.id.ll_mybeans_detail);
        myMedalLayout.setOnClickListener(this);
        systemSettingsLayout.setOnClickListener(this);
        myCollectionLayout.setOnClickListener(this);
        myCourseLayout.setOnClickListener(this);
        myMessageLayout.setOnClickListener(this);
        myIntegralLayout.setOnClickListener(this);
        personalInfoLayout.setOnClickListener(this);
        myBeansDetailLayout.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("FragmentMe");
        MobclickAgent.onResume(getActivity());
        Log.e("FragmentMe", "onResume()");
        if (ApplicationEx.getInstance().isLogined()) {
//            JSONObject meinfodata = mACache.getAsJSONObject(CacheConstans.ME_INFO_JSON);
//            if (meinfodata != null) {
//                showMeInfo(meinfodata);
//            }
            initData();
        } else {
            nameTv.setText("");
            levelNameTv.setText("Lv0");
            pointsTv.setText("0");
            beanPointsTv.setText("0");
            signatureTv.setText("您还没有登录，请登录");
//          signatureTv.setText(String.valueOf((char)(0+'A')));
            headIv.setImageResource(R.drawable.user_head_default);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("FragmentMe");
        MobclickAgent.onPause(getActivity());
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (ApplicationEx.getInstance().isLogined()) {
                initData();
            }
        }
    }

    /**
     * 功能：获取个人信息
     */
    private void initData() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("userid", PreferenceUtils.getUserId());
        HttpUtil.post(Constants.SHOWSELFINFO_URL, requestParams, new LoadJsonHttpResponseHandler(getActivity(), new LoadDatahandler() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onSuccess(JSONObject data) {
                super.onSuccess(data);
                //缓存
                mACache.put(CacheConstans.ME_INFO_JSON, data);
                showMeInfo(data);
            }
            @Override
            public void onFailure(String error, String message) {
                super.onFailure(error, message);
                JSONObject meinfodata = mACache.getAsJSONObject(CacheConstans.ME_INFO_JSON);
                if (meinfodata != null) {
                    showMeInfo(meinfodata);
                }
                Log.e("onFailure", "onFailure");
            }
        }));
    }

    /**
     * 显示我的信息
     */
    private void showMeInfo(JSONObject data) {
        try {
            String suc = data.get("suc").toString();
            String msg = data.get("msg").toString();
            if ("y".equals(suc)) {
                userinfo = gson.fromJson(data.getString("data").toString(), UserInfo.class);
                ImageLoader.getInstance().displayImage(Constants.IMG + userinfo.getFilepath(), headIv, ApplicationEx.head_options);
                Log.e("url", Constants.IMG + userinfo.getFilepath());
                nameTv.setText(userinfo.getUser_name());
                signatureTv.setText(userinfo.getSignature());
                levelNameTv.setText("Lv" + userinfo.getLevelname()==null?"":userinfo.getLevelname());
                pointsTv.setText(String.valueOf(userinfo.getPoints()));
                beanPointsTv.setText(String.valueOf(userinfo.getBean_points()));
            } else {
                ToastUtil.getInstant().show(msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("JSONException", "JSONException");
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            //我的魔豆
            case R.id.my_beans:
                if (ApplicationEx.getInstance().isLogined()) {
                    intent = new Intent(getActivity(), MagicBeansActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, 1024);
                }
                break;
            // 系统设置
            case R.id.ll_systemsettings:
                if (ApplicationEx.getInstance().isLogined()) {

                    intent = new Intent(getActivity(), SystemSettingsActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, 1024);
                }

                break;
            // 我的勋章
            case R.id.ll_mymedal:
                if (ApplicationEx.getInstance().isLogined()) {

                    intent = new Intent(getActivity(), MyMedalActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, 1024);
                }

                break;
            // 个人信息
            case R.id.ll_personalinfo:
                if (ApplicationEx.getInstance().isLogined()) {

                    intent = new Intent(getActivity(), PersonalInfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("userinfo", userinfo);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, 1024);
                }

                break;
            case R.id.ll_order1:
                if (ApplicationEx.getInstance().isLogined()) {

                    intent = new Intent(getActivity(), MyMessageActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, 1024);
                }

                break;
            case R.id.ll_order2:
                if (ApplicationEx.getInstance().isLogined()) {
                    intent = new Intent(getActivity(), MyCourseActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, 1024);
                }
                break;
            case R.id.ll_order3:
                if (ApplicationEx.getInstance().isLogined()) {

                    intent = new Intent(getActivity(), MyCollectionActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, 1024);
                }

                break;

            case R.id.my_rank:
                if (ApplicationEx.getInstance().isLogined()) {

                    intent = new Intent(getActivity(), MyRankActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, 1024);
                }
                break;
            case R.id.ll_myintegral:
                if (ApplicationEx.getInstance().isLogined()) {
                    intent = new Intent(getActivity(), MyIntegralActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, 1024);
                }
                break;
            case R.id.ll_mybeans_detail:
                if (ApplicationEx.getInstance().isLogined()) {
                    intent = new Intent(getActivity(), MyBeansDetailActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, 1024);
                }
                break;
        }
    }


}
