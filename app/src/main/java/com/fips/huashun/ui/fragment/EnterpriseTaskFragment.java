package com.fips.huashun.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.common.ConstantsCode;
import com.fips.huashun.modle.bean.QuestionInfo;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.activity.WebviewActivity;
import com.fips.huashun.ui.adapter.QuestionAdapter;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 功能：任务中--企业任务
 * Created by Administrator on 2016/8/24.
 * @author 张柳 时间：2016年8月24日17:01:29
 */
public class EnterpriseTaskFragment extends Fragment
{
    private View rootView;
    private ListView mListView;
    private PullToRefreshListView pullToRefreshListView;
    private QuestionAdapter questionAdapter;
    private Gson gson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (rootView == null)
        {
            rootView = inflater.inflate(R.layout.fragment_enterprisetask, container, false);
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
        pullToRefreshListView = (PullToRefreshListView) rootView.findViewById(R.id.pull_list);
        // 获取listview
        mListView = pullToRefreshListView.getRefreshableView();
        // 两端刷新
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        questionAdapter = new QuestionAdapter(getActivity());
        // 设置适配器
        mListView.setAdapter(questionAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(getActivity(),WebviewActivity.class);
                intent.putExtra("sessoinid", questionAdapter.getItem(position-1).getId()+"");
                intent.putExtra("type", ConstantsCode.STRING_ONE);
                intent.putExtra("key", 16);

                startActivity(intent);
            }
        });
        // 设置上下拉事件
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>()
        {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
            {
                initData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
            {
                initData();
            }
        });
    }

    private void initData(){
        RequestParams requestParams = new RequestParams();
//        requestParams.put("enid",enterpriseId);
        requestParams.put("user_id", PreferenceUtils.getUserId());
        Log.e("user_id",PreferenceUtils.getUserId());
        HttpUtil.post(Constants.PHP_URL+"/app_get_questionnaire_list", requestParams, new LoadJsonHttpResponseHandler(getActivity(), new LoadDatahandler(){

            @Override
            public void onSuccess(JSONObject data) {
                super.onSuccess(data);
                try
                {
                    String suc = data.get("suc").toString();
                    String msg = data.get("msg").toString();
                    if ("y".equals(suc))
                    {
                        List<QuestionInfo> list = gson.fromJson(data.getString("data"), new TypeToken<List<QuestionInfo>>() {
                        }.getType());
                        questionAdapter.setListItems(list);
                        questionAdapter.notifyDataSetChanged();
                    } else
                    {
                        ToastUtil.getInstant().show(msg);
                    }
                    // 结束刷新
                    pullToRefreshListView.onRefreshComplete();
                } catch (JSONException e)
                {
                    e.printStackTrace();
                    Log.e("JSONException","JSONException");

                }
            }




            @Override
            public void onFailure(String error, String message) {
                super.onFailure(error, message);
                pullToRefreshListView.onRefreshComplete();

            }
        }));
    }
    @Override
    public void onResume()
    {
        super.onResume();
        MobclickAgent.onPageStart("EnterpriseTaskFragment");
        MobclickAgent.onResume(getActivity());
        initData();
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("EnterpriseTaskFragment");
        MobclickAgent.onPause(getActivity());
    }

    @Override
    public void onHiddenChanged(boolean hidden)
    {
        super.onHiddenChanged(hidden);
        Log.e("vote","onHiddenChanged hidden="+hidden);
        if (!hidden)
        {
            initData();
        }
    }

}
