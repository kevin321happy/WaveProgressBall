package com.fips.huashun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：我的课程（已完成和未完成）
 * Created by Administrator on 2016/8/25.
 *
 * @author 张柳 时间：2016年8月25日10:07:13
 */
public class MyCourseActivity extends BaseActivity implements View.OnClickListener
{
    private NavigationBar navigationBar;
    // 上下拉的控件
    private PullToRefreshListView pullToRefreshListView;
    // 列表
    private ListView mListView;
    // 适配器
    private AllCourseAdapter allCourseAdapter;
    private RadioButton finishCourseRb, unFinishCourseRb;
    private RadioGroup rg;
    private ArrayList<RadioButton> rb;
    // 数据
    private List<CourseInfo> myCourseInfoList;
    // type 表示查询哪类课程 1 已完成课程，2 未完成课程
    private String type;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycourse);
        initView();
        showLoadingDialog();
        type = "1";
        initData(type);
    }

    @Override
    protected void initView()
    {
        super.initView();
        finishCourseRb = (RadioButton) findViewById(R.id.rb_left_mycourse);
        unFinishCourseRb = (RadioButton) findViewById(R.id.rb_right_mycourse);
        navigationBar = (NavigationBar) findViewById(R.id.nb_mycourse);
        navigationBar.setTitle("我的课程");
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
        finishCourseRb.setOnClickListener(this);
        unFinishCourseRb.setOnClickListener(this);
        rg = (RadioGroup) findViewById(R.id.rb_mucourse);
        rb = new ArrayList<RadioButton>();
        rb.add(finishCourseRb);
        rb.add(unFinishCourseRb);
        rb.get(0).setChecked(true);//设置进入页面第一次显示
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.lv_mycourse);
        mListView = pullToRefreshListView.getRefreshableView();
        // 两端刷新
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        allCourseAdapter = new AllCourseAdapter(MyCourseActivity.this);
        mListView.setAdapter(allCourseAdapter);
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>()
        {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
            {
                initData(type);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
            {
                initData(type);
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent =new Intent(MyCourseActivity.this,CourseDetailActivity.class);
                intent.putExtra("courseId",  allCourseAdapter.getItem(position-1).getCourseId());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean isSystemBarTranclucent()
    {
        return false;
    }

    /**
     * 初始化数据
     * @param type
     */
    public void initData(String type)
    {
        RequestParams requestParams = new RequestParams();
        requestParams.put("userid", PreferenceUtils.getUserId());
        String url = null;
        if ("1".equals(type))
        {// 已完成课程
            url = Constants.SHOWMYHADCOURSE_URL;
        } else if ("2".equals(type))
        {// 未完成课程
            url = Constants.SHOWMYNOCOURSE_URL;
        }
        HttpUtil.post(url, requestParams, new LoadJsonHttpResponseHandler(this, new LoadDatahandler()
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
                        myCourseInfoList = gson.fromJson(data.getString("data"), new TypeToken<List<CourseInfo>>()
                        {
                        }.getType());
                        allCourseAdapter.setListItems(myCourseInfoList);
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
    public void onClick(View v)
    {
        if (v.getId() == R.id.rb_left_mycourse)
        {
            showLoadingDialog();
            type = "1";
            initData(type);
        } else if (v.getId() == R.id.rb_right_mycourse)
        {
            showLoadingDialog();
            type = "2";
            initData(type);
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MyCourseActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MyCourseActivity");
    }
}
