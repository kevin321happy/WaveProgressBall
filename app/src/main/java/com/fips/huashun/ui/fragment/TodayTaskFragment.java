package com.fips.huashun.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fips.huashun.R;
import com.fips.huashun.ui.adapter.TodayTaskAdapter;
import com.umeng.analytics.MobclickAgent;


/**
 * 功能：任务中--今日任务
 * Created by Administrator on 2016/8/24.
 * @author 张柳 时间：2016年8月24日17:01:29
 */
public class TodayTaskFragment extends Fragment
{
    private View rootView;
    private ListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (rootView == null)
        {
            rootView = inflater.inflate(R.layout.fragment_todaytask, container, false);
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
    }

    private void initView()
    {
        mListView = (ListView) rootView.findViewById(R.id.lv_todaytask);
        mListView.setAdapter(new TodayTaskAdapter(getActivity()));
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("TodayTaskFragment");
        MobclickAgent.onResume(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("TodayTaskFragment");
        MobclickAgent.onPause(getActivity());
    }
}
