package com.fips.huashun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.modle.bean.CourseInfo;
import com.fips.huashun.modle.bean.FilterData;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.adapter.AllCourseAdapter;
import com.fips.huashun.ui.adapter.FilterLeftAdapter;
import com.fips.huashun.ui.adapter.FilterOrderAdapter;
import com.fips.huashun.ui.adapter.FilterRightAdapter;
import com.fips.huashun.ui.adapter.IndustryCourseAdapter;
import com.fips.huashun.ui.utils.NavigationBar;
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
 * 功能：所有课程
 * Created by Administrator on 2016/8/16.
 * @author 张柳 时间：2016年8月30日19:46:05
 */
public class MoreDetailActivity extends BaseActivity implements OnClickListener
{
    // 返回
    private LinearLayout backLl;
    // 上下拉的控件
    private PullToRefreshListView pullToRefreshListView;
    // 列表
    private ListView mListView;
    // 适配器
    private AllCourseAdapter allCourseAdapter;
    // 数据
    private PopupWindow pop;
    private LinearLayout ll_filter;
    private ListView lv_left;
    private ListView lv_right;
    private FilterLeftAdapter leftAdapter;
    private FilterRightAdapter rightAdapter;
    String type="";
    String order="";
    private LinearLayout ll_filter02;
    private TextView tv_category;
    private TextView tv_sort;
    private ImageView iv_sort_arrow;
    private ImageView iv_category_arrow;
    private TextView tv_dissmis;
    private FilterOrderAdapter filterOrderAdapter;
    private List<FilterData> items;
    private NavigationBar navigationBar;
    private String key;
    private IndustryCourseAdapter courseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_home);
        key = getIntent().getStringExtra("key");
        initView();
        initData(key);
    }

    @Override
    public boolean isSystemBarTranclucent() {
        return false;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void initView() {
        super.initView();
        navigationBar = (NavigationBar) findViewById(R.id.na_bar);

        navigationBar.setLeftImage(R.drawable.course_reslt_back);

        navigationBar.setListener(new NavigationBar.NavigationListener() {
            @Override
            public void onButtonClick(int button) {
                if (button==NavigationBar.LEFT_VIEW){
                    finish();
                }
            }
        });
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.pl_refesh);
        mListView = pullToRefreshListView.getRefreshableView();
        // 两端刷新
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        allCourseAdapter = new AllCourseAdapter(MoreDetailActivity.this);
        mListView.setAdapter(allCourseAdapter);
        if (key.equals("showMoreCourseList")){

            navigationBar.setTitle("行业课程");
        }else if (key.equals("showMoreHotCourseList")){
//            mListView.setAdapter(courseAdapter);
            navigationBar.setTitle("热门课程");
        }else if (key.equals("showMoreTeachCourseList")){
            navigationBar.setTitle("老师课程");
        }

        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>()
        {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
            {
                initData(key);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
            {
                initData(key);
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                // 加入上下拉控件后，position索引从1开始，而不是从0
//                if (key.equals("showMoreCourseList")){
                    CourseInfo item = allCourseAdapter.getItem(position-1);
                    Intent intentToDetail = new Intent(MoreDetailActivity.this, CourseDetailActivity.class);

                    intentToDetail.putExtra("courseId", item.getCourseId());
                    startActivity(intentToDetail);
//                }else if (key.equals("showMoreHotCourseList")){
//
//                }

            }
        });

    }

    private void initData(String key) {
        RequestParams requestParams = new RequestParams();
        HttpUtil.post(Constants.URL+"/"+key, requestParams, new LoadJsonHttpResponseHandler(this, new LoadDatahandler()
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
                        // 获取数据
                        List<CourseInfo>   list = gson.fromJson(data.getJSONArray("data").toString(), new TypeToken<List<CourseInfo>>(){}.getType());
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
        MobclickAgent.onPageStart("MoreDetailActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MoreDetailActivity");
    }
}
