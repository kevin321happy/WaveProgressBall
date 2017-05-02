package com.fips.huashun.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.modle.bean.IntegralTaskInfo;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.adapter.IntegralTaskAdapter;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 功能：积分任务
 * Created by Administrator on 2016/8/27.
 *
 * @author 张柳 时间：2016年8月27日17:01:29
 */
public class IntegralTaskFragment extends Fragment
{
    private View rootView;
    private ListView mListView;
    private IntegralTaskAdapter integralTaskAdapter;
    private Gson gson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (rootView == null)
        {
            rootView = inflater.inflate(R.layout.fragment_inregraltask, container, false);
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
        mListView = (ListView) rootView.findViewById(R.id.lv_integraltask);
        integralTaskAdapter = new IntegralTaskAdapter(getActivity());
        mListView.setAdapter(integralTaskAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String taskid = integralTaskAdapter.getItem(position).getTaskid();
                String state = integralTaskAdapter.getItem(position).getState();
                signGetIntegral(taskid,String.valueOf(Integer.valueOf(state)+1));
            }
        });
    }

    /**
     * 功能：获取积分任务
     */
    private void initData()
    {
        RequestParams requestParams = new RequestParams();
        requestParams.put("userid", PreferenceUtils.getUserId());
        HttpUtil.post(Constants.INTEGRAL_TASK_URL, requestParams, new LoadJsonHttpResponseHandler(getActivity(), new LoadDatahandler()
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
                        List<IntegralTaskInfo> taskList = gson.fromJson(data.getJSONArray("data").toString(), new TypeToken<List<IntegralTaskInfo>>() {}.getType());
                        integralTaskAdapter.setListItems(taskList);
                        integralTaskAdapter.notifyDataSetChanged();
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

    /**
     * 功能：我的签到
     * @param taskid 任务ID
     * @param state 状态
     */
    private void signGetIntegral(String taskid, String state)
    {
        RequestParams requestParams = new RequestParams();
        requestParams.put("userid", PreferenceUtils.getUserId());
        requestParams.put("taskid", taskid);
        requestParams.put("state", state);

        HttpUtil.post(Constants.SIGN_GETINTEGRAL_URL, requestParams, new LoadJsonHttpResponseHandler(getActivity(), new LoadDatahandler()
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
                    if ("y".equals(suc))
                    {
                        ToastUtil.getInstant().show("签到成功");
                        initData();
                    } else
                    {
                        ToastUtil.getInstant().show(msg);
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
                ToastUtil.getInstant().show("签到失败，请重试！");
            }
        }));
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("IntegralTaskFragment");
        MobclickAgent.onPause(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("IntegralTaskFragment");
        MobclickAgent.onPause(getActivity());
    }
}
