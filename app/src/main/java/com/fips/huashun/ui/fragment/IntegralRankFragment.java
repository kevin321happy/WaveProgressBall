package com.fips.huashun.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.modle.bean.IntegralInfo;
import com.fips.huashun.modle.bean.MyIntegralInfo;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 功能：积分排名
 * Created by Administrator on 2016/8/27.
 * @author 张柳 时间：2016年8月27日17:01:29
 */
public class IntegralRankFragment extends Fragment
{
    private View rootView;
    // 本周排名，公司排名
    private TextView weekRankTv,enterpriseRankTv;
    private LinearLayout enterpriseRankLayout;
    private Gson gson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (rootView == null)
        {
            rootView = inflater.inflate(R.layout.fragment_inregralrank, container, false);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null)
        {
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    private void initView()
    {
        gson =new Gson();
        weekRankTv = (TextView) rootView.findViewById(R.id.tv_week_rank);
        enterpriseRankTv = (TextView) rootView.findViewById(R.id.tv_enterprise_rank);
        enterpriseRankLayout = (LinearLayout) rootView.findViewById(R.id.ll_enterprise_rank);
    }
    /**
     * 功能：获取数据
     */
    private void  initData()
    {
        {
            RequestParams requestParams = new RequestParams();
            requestParams.put("userid", PreferenceUtils.getUserId());
            HttpUtil.post(Constants.INTEGRALINFO_URL, requestParams, new LoadJsonHttpResponseHandler(getActivity(), new LoadDatahandler()
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
                    try
                    {
                        String suc = data.getString("suc");
                        String msg = data.getString("msg");
                        if ("y".equals(suc))
                        {
                            MyIntegralInfo myIntegralInfo = gson.fromJson(data.getString("data"), MyIntegralInfo.class);
                            IntegralInfo integralInfo = myIntegralInfo.getIntegralInfo();
                            weekRankTv.setText("第"+integralInfo.getWeekRank()+"名");
                            if(TextUtils.isEmpty(integralInfo.getEnterpriseRangk()))
                            {
                                enterpriseRankLayout.setVisibility(View.GONE);
                            } else
                            {
                                enterpriseRankTv.setText("第"+integralInfo.getEnterpriseRangk()+"名");
                                enterpriseRankLayout.setVisibility(View.VISIBLE);
                            }
                        } else
                        {

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
                }
            }));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("IntegralRankFragment");
        MobclickAgent.onResume(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("IntegralRankFragment");
        MobclickAgent.onPause(getActivity());
    }
}
