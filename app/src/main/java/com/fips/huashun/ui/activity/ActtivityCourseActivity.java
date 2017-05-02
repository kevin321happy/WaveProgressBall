package com.fips.huashun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fips.huashun.R;
import com.fips.huashun.ui.adapter.AllCourseAdapter;

import com.fips.huashun.common.Constants;
import com.fips.huashun.modle.bean.CourseInfo;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.utils.NavigationBar;
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
 * 功能：项目课程
 * Created by Administrator on 2016/8/16.
 *
 * @author 张柳 时间：2016年8月30日19:46:05
 */
public class ActtivityCourseActivity extends BaseActivity
{
    // 上下拉的控件
    private PullToRefreshListView pullToRefreshListView;
    // 列表
    private ListView mListView;
    // 适配器
    private AllCourseAdapter allCourseAdapter;
    // 数据

    private List<CourseInfo> list;
    // 提示
    private ToastUtil toastUtil;
    //
    private NavigationBar navigationBar;
    private  Intent intent;
    private String activityId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterprise_course);
        initView();
        showLoadingDialog();
        initData();
    }

    @Override
    protected void initView()
    {
        super.initView();
        gson = new Gson();
        toastUtil = ToastUtil.getInstant();
        intent = getIntent();
        activityId = intent.getStringExtra("activityId");
        navigationBar = (NavigationBar) findViewById(R.id.nb_enterprise_course);
        navigationBar.setTitle("企业课程");
        navigationBar.setLeftImage(R.drawable.fanhui);
        navigationBar.setListener(new NavigationBar.NavigationListener()
        {
            @Override
            public void onButtonClick(int button)
            {
                if (button == NavigationBar.LEFT_VIEW)
                {
                    finish();
                }
            }
        });
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.lv_enterprise_course);
        mListView = pullToRefreshListView.getRefreshableView();
        // 两端刷新
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        allCourseAdapter = new AllCourseAdapter(getApplicationContext());
        mListView.setAdapter(allCourseAdapter);
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
//        pullToRefreshListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
//
//            @Override
//            public void onLastItemVisible() {
//                Toast.makeText(getApplication(), "没有更多数据", Toast.LENGTH_SHORT).show();
//            }
//        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Log.e("6666",allCourseAdapter.getItem(position - 1).getCourseId());
                Intent intent = new Intent(ActtivityCourseActivity.this, CourseDetailActivity.class);
                intent.putExtra("courseId", allCourseAdapter.getItem(position - 1).getCourseId());
                startActivity(intent);
            }
        });
    }

    /**
     * 功能：获取项目课程列表信息
     */
    private void initData()
    {
        RequestParams requestParams = new RequestParams();
        requestParams.put("activityid",activityId);
        HttpUtil.post(Constants.ENTERPRISE_ACTIVITY_COURSE_URL, requestParams, new LoadJsonHttpResponseHandler(this, new LoadDatahandler()
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
                dimissLoadingDialog();
                try
                {
                    String suc = data.get("suc").toString();
                    String msg = data.get("msg").toString();
                    if ("y".equals(suc))
                    {
                        list = gson.fromJson(data.getString("data"), new TypeToken<List<CourseInfo>>(){}.getType());
                        allCourseAdapter.setListItems(list);
                        allCourseAdapter.notifyDataSetChanged();
                    } else
                    {
                        ToastUtil.getInstant().show(msg);
                    }
                    // 结束刷新
                    pullToRefreshListView.onRefreshComplete();
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error, String message)
            {
                super.onFailure(error, message);
                dimissLoadingDialog();
                // 结束刷新
                pullToRefreshListView.onRefreshComplete();
            }
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("ActtivityCourseActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("ActtivityCourseActivity");
    }

    @Override
    public boolean isSystemBarTranclucent()
    {
        return false;
    }
}
