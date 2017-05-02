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
import com.fips.huashun.modle.bean.ActivtyVoteBean;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.activity.EnterpriseVoteActivity;
import com.fips.huashun.ui.adapter.VoteTaskAdapter;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.ToastUtil;
import com.fips.huashun.widgets.LoadingDialog;
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
 * 功能：任务中--投票调研
 * Created by Administrator on 2016/8/24.
 * @author 张柳 时间：2016年8月24日17:01:29
 */
public class VoteTaskFragment extends Fragment
{
    private View rootView;
    // 上下拉的控件
    private PullToRefreshListView pullToRefreshListView;
    // 列表
    private ListView mListView;
    // 适配器
    private VoteTaskAdapter voteTaskAdapter;
    // 数据保存
    private List<ActivtyVoteBean> list;
    private Gson gson;
    public String enterpriseId;
    private LoadingDialog loadingDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (rootView == null)
        {
            rootView = inflater.inflate(R.layout.fragment_votetask, container, false);
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
        loadingDialog = new LoadingDialog(getActivity());
        showLoadingDialog("正在加载");
        enterpriseId = getActivity().getIntent().getStringExtra("enterpriseId");
        Log.e("enterpriseId",enterpriseId);

    }

    @Override
    public void onResume()
    {
        super.onResume();
        initData();
        MobclickAgent.onPageStart("VoteTaskFragment");
        MobclickAgent.onResume(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("VoteTaskFragment");
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

    private void initView()
    {
        gson = new Gson();
        pullToRefreshListView = (PullToRefreshListView) rootView.findViewById(R.id.lv_votetask);
        // 获取listview
        mListView = pullToRefreshListView.getRefreshableView();
        // 两端刷新
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        voteTaskAdapter = new VoteTaskAdapter(getActivity());
        // 设置适配器
        mListView.setAdapter(voteTaskAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intentToDetal = new Intent(getActivity(),EnterpriseVoteActivity.class);
                intentToDetal.putExtra("pid",String.valueOf(list.get(position-1).getPid()));
                intentToDetal.putExtra("isvote",String.valueOf(list.get(position-1).getIsVote()));
                Log.e("intentToDetal","intentToDetal");
                startActivity(intentToDetal);
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
    /**
     * 功能：获取投票列表
      */
    private void initData()
    {
        RequestParams requestParams = new RequestParams();
//      requestParams.put("enid",enterpriseId);
        requestParams.put("userId", PreferenceUtils.getUserId());
        HttpUtil.post(Constants.PHP_URL+"/get_vote_list", requestParams, new LoadJsonHttpResponseHandler(getActivity(), new LoadDatahandler()
        {
            @Override
            public void onStart()
            {
                super.onStart();
            }

            @Override
            public void onSuccess(JSONObject data) {
                super.onSuccess(data);
                dimissLoadingDialog();
                try
                {
                    String suc = data.get("suc").toString();
                    String msg = data.get("msg").toString();
                    if ("y".equals(suc))
                    {
                        list = gson.fromJson(data.getString("data"),new TypeToken<List<ActivtyVoteBean>>(){}.getType());
                        voteTaskAdapter.setListItems(list);
                        voteTaskAdapter.notifyDataSetChanged();
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
                    dimissLoadingDialog();
                }
            }

            @Override
            public void onFailure(String error, String message)
            {
                super.onFailure(error, message);
                dimissLoadingDialog();
                Log.e("onFailure","onFailure");
                // 结束刷新
                pullToRefreshListView.onRefreshComplete();
            }
        }));
    }

    public void showLoadingDialog(String content) {
        loadingDialog.show(content);
    }

    public void dimissLoadingDialog() {
        loadingDialog.dismiss();
    }

}
