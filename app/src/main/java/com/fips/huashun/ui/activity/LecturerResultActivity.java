package com.fips.huashun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.modle.bean.TeacherCourse;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.adapter.LecturerResultAdapter;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 功能：讲师搜索结果列表展示
 * Created by Administrator on 2016/8/17.
 * @author 张柳 时间：2016年8月17日16:50:01
 */
public class LecturerResultActivity extends BaseActivity implements OnClickListener
{
    private PullToRefreshListView pullToRefreshListView;
    private ListView mListView;
    private LecturerResultAdapter allCourseAdapter;
    private String condition;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_result);
        condition = getIntent().getStringExtra("condition");
        initView();
        initData(condition);
    }

    @Override
    protected void initView()
    {
        super.initView();
        NavigationBar navigationBar = (NavigationBar) findViewById(R.id.na_bar);
        navigationBar.setTitle("讲师");
        navigationBar.setLeftImage(R.drawable.course_reslt_back);
        navigationBar.setListener(new NavigationBar.NavigationListener() {
            @Override
            public void onButtonClick(int button) {
                if (button==NavigationBar.LEFT_VIEW){
                    finish();
                }
            }
        });
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.lv_course_all);
        mListView = pullToRefreshListView.getRefreshableView();
        // 两端刷新
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        allCourseAdapter = new LecturerResultAdapter(this);
        mListView.setAdapter(allCourseAdapter);
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>()
        {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
            {
                initData(condition);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
            {
                initData(condition);
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent =new Intent(LecturerResultActivity.this,LecturerDetailActivity.class);
                intent.putExtra("teacherId",  allCourseAdapter.getItem(position-1).getTeacherId()+"");
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean isSystemBarTranclucent() {
        return false;
    }

    @Override
    public void onClick(View v) {

    }
    /**
     * 功能：获取所有课程列表信息
     */
    private void initData(String condition) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("condition",condition );
        requestParams.put("userid", PreferenceUtils.getUserId());
        requestParams.put("type","2");
        HttpUtil.post(Constants.URL+"/serachByCondition", requestParams, new LoadJsonHttpResponseHandler(this, new LoadDatahandler()
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
                        List<TeacherCourse>  list=gson.fromJson(data.getString("data"), new TypeToken<List<TeacherCourse>>(){}.getType());
                        allCourseAdapter.setListItems(list);
                        allCourseAdapter.notifyDataSetChanged();
                    } else
                    {

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
//                dimissLoadingDialog();
                // 结束刷新
                pullToRefreshListView.onRefreshComplete();
            }
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("LecturerResultActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("LecturerResultActivity");
    }
}
