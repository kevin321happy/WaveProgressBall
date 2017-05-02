package com.fips.huashun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.modle.bean.CourseInfo;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.adapter.AllCourseAdapter;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.ToastUtil;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2016/8/16.
 */
public class CourseResultActivity extends BaseActivity implements OnClickListener
{

    private PullToRefreshListView pullToRefreshListView;
    private ListView mListView;
    private AllCourseAdapter allCourseAdapter;
    private String condition;
    private String page;
    private NavigationBar navigationBar;
    private String eid;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_result);
        condition = getIntent().getStringExtra("condition");
        eid = getIntent().getStringExtra("eid");
        page = getIntent().getStringExtra("page");
        initView();
        if (!TextUtils.isEmpty(page)&&page.equals("1")){
                initCourse(condition,eid);
        }else {
            initData(condition);
        }
    }

    @Override
    protected void initView()
    {
        super.initView();
        navigationBar = (NavigationBar) findViewById(R.id.na_bar);
        navigationBar.setTitle("课程");
        navigationBar.setLeftImage(R.drawable.fanhui);
        navigationBar.setListener(new NavigationBar.NavigationListener() {
            @Override
            public void onButtonClick(int button) {
                if (button==NavigationBar.LEFT_VIEW){
                    if (!TextUtils.isEmpty(page)){
                        if (page.equals("1")){
                           setResult(1);
                        }
                    }
                    finish();
                }
            }
        });
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.lv_course_all);
        mListView = pullToRefreshListView.getRefreshableView();
        // 两端刷新
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        allCourseAdapter = new AllCourseAdapter(this);
        mListView.setAdapter(allCourseAdapter);
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>()
        {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
            {
                if (!TextUtils.isEmpty(page)&&page.equals("1")){
                    initCourse(condition,eid);
                }else {
                    initData(condition);
                }

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
            {
                if (!TextUtils.isEmpty(page)&&page.equals("1")){
                    initCourse(condition,eid);
                }else {
                    initData(condition);
                }
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent =new Intent(CourseResultActivity.this,CourseDetailActivity.class);
                intent.putExtra("courseId",  allCourseAdapter.getItem(position-1).getCourseId());
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
        requestParams.put("type","1");
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
                        List<CourseInfo>  list = gson.fromJson(data.getString("data"), new TypeToken<List<CourseInfo>>(){}.getType());
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
/*能力模型跳转过来的数据*/
    private void initCourse(String condition,String eid ) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("abilitytype",condition );
        requestParams.put("eid",eid );
        HttpUtil.post(Constants.URL+"/getAbilityModel", requestParams, new LoadJsonHttpResponseHandler(this, new LoadDatahandler()
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
                        List<CourseInfo>  list = gson.fromJson(data.getString("data"), new TypeToken<List<CourseInfo>>(){}.getType());
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
        MobclickAgent.onPageStart("CourseResultActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("CourseResultActivity");
    }
}
