package com.fips.huashun.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioButton;

import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.modle.bean.ExamTask;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.adapter.ExamTaskAdapter;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：任务中--我的考试
 * Created by Administrator on 2016/8/22.
 * @author 张柳 时间：2016年8月22日17:01:29
 */
public class ExamTaskFragment extends Fragment implements OnClickListener
{
    private View rootView;
    private ListView mListView;
    private ArrayList<RadioButton> rb;
    private RadioButton view1;
    private RadioButton view2;
    private ExamTaskAdapter examTaskAdapter;
    private List<ExamTask> examTaskList;
    private Gson gson;
    private int typeIndex = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (rootView == null)
        {
            rootView = inflater.inflate(R.layout.fragment_examtask, container, false);
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
        gson = new Gson();
        initView();
        initData();
    }

    private void initView()
    {
        view1 = (RadioButton) rootView.findViewById(R.id.rb_left_examtask);
        view2 = (RadioButton) rootView.findViewById(R.id.rb_right_examtask);
        view1.setOnClickListener(this);
        view2.setOnClickListener(this);
        rb = new ArrayList<RadioButton>();
        rb.add(view1);
        rb.add(view2);
        rb.get(0).setChecked(true);//设置进入页面第一次显示
        mListView = (ListView) rootView.findViewById(R.id.lv_examtask);
        examTaskAdapter = new ExamTaskAdapter(getActivity());
        examTaskAdapter.setTypeExam(1);
        mListView.setAdapter(examTaskAdapter);
    }
    private void initData()
    {
        RequestParams requestParams = new RequestParams();
        requestParams.put("userid", PreferenceUtils.getUserId());
        String url ;
        if (typeIndex ==1)
        {
            url = Constants.TASK_EXAM_PASS_URL;
        } else
        {
            url = Constants.TASK_EXAM_NEED_URL;
        }
        HttpUtil.post(url, requestParams, new LoadJsonHttpResponseHandler(getActivity(), new LoadDatahandler()
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
                        examTaskList = gson.fromJson(data.getString("data"),new TypeToken<List<ExamTask>>(){}.getType());
                        examTaskAdapter.setListItems(examTaskList);
                        examTaskAdapter.notifyDataSetChanged();
                    } else
                    {
                        ToastUtil.getInstant().show(msg);
                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                    Log.e("JSONException","JSONException");
                }
            }

            @Override
            public void onFailure(String error, String message)
            {
                super.onFailure(error, message);
                Log.e("onFailure","onFailure");
            }
        }));
    }

    @Override
    public void onHiddenChanged(boolean hidden)
    {
        super.onHiddenChanged(hidden);
        Log.e("onHiddenChanged ==","onHiddenChanged hidden="+hidden);
        if(!hidden)
        {
            initData();
        }
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.rb_left_examtask)
        {
            typeIndex = 1;
        } else if (v.getId() == R.id.rb_right_examtask)
        {
            typeIndex = 2;
        }
        examTaskAdapter.setTypeExam(typeIndex);
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("ExamTaskFragment");
        MobclickAgent.onResume(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("ExamTaskFragment");
        MobclickAgent.onPause(getActivity());
    }
}
