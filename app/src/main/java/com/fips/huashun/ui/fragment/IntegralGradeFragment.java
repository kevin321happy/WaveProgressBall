package com.fips.huashun.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.modle.bean.IntegralLevel;
import com.fips.huashun.modle.bean.MyIntegralInfo;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.adapter.IntegralGradeAdapter;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;



/**
 * 功能：积分等级介绍
 * Created by Administrator on 2016/8/27.
 * @author 张柳 时间：2016年8月27日17:01:29
 */
public class IntegralGradeFragment extends Fragment
{
    private View rootView;
    private ListView mListView;
    private IntegralGradeAdapter integralGradeAdapter;
    private Gson gson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (rootView == null)
        {
            rootView = inflater.inflate(R.layout.fragment_inregralgrade, container, false);
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
        gson = new Gson();
        mListView = (ListView) rootView.findViewById(R.id.lv_integralgrade);
        integralGradeAdapter =new IntegralGradeAdapter(getActivity());
        mListView.setAdapter(integralGradeAdapter);
    }
    /**
     * 功能：获取积分任务
     */
    private void initData()
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
                    String suc = data.get("suc").toString();
                    String msg = data.get("msg").toString();
                    if (suc.equals("y"))
                    {
                        MyIntegralInfo myIntegralInfo = gson.fromJson(data.getString("data"), MyIntegralInfo.class);
                        List<IntegralLevel> integralLevelList = myIntegralInfo.getIntegralLevelList();
                        integralGradeAdapter.setListItems(integralLevelList);
                        integralGradeAdapter.notifyDataSetChanged();
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
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("IntegralGradeFragment");
        MobclickAgent.onResume(getActivity());

    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("IntegralGradeFragment");
        MobclickAgent.onPause(getActivity());
    }
}
