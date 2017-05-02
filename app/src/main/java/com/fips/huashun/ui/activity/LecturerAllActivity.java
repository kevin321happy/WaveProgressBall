package com.fips.huashun.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.modle.bean.TeacherCourse;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.adapter.LecturerResultAdapter;
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
 * 功能：所有讲师
 * Created by Administrator on 2016/8/19.
 *
 * @author 张柳 时间：2016年8月19日16:50:01
 */
public class LecturerAllActivity extends BaseActivity implements OnClickListener,TextView.OnEditorActionListener
{
    private ImageView backIv,cancelIv;
    private EditText searchEt;
    //
    private PullToRefreshListView pullToRefreshListView;
    // 列表
    private ListView mListView;
    // 适配器
    private LecturerResultAdapter lecturerResultAdapter;
    // 数据集合
    private List<TeacherCourse> list;
    // 通知
    private ToastUtil toastUtil;
    // 1未搜索 2 搜索查询
    private String type;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_all);
        toastUtil = ToastUtil.getInstant();
        type = "1";
        initView();
        showLoadingDialog();
        // 获取所有讲师列表信息
        initData();
    }

    @Override
    protected void initView()
    {
        super.initView();

        backIv = (ImageView) findViewById(R.id.iv_back);
        backIv.setOnClickListener(this);
        cancelIv = (ImageView) findViewById(R.id.iv_cancle);
        cancelIv.setOnClickListener(this);
        searchEt = (EditText) findViewById(R.id.et_search);
        searchEt.setOnEditorActionListener(this);
        searchEt.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (s.length()>0)
                {
                    cancelIv.setVisibility(View.VISIBLE);
                }else {
                    cancelIv.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.pl_refesh);
        mListView = pullToRefreshListView.getRefreshableView();
        // 两端刷新
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        lecturerResultAdapter = new LecturerResultAdapter(LecturerAllActivity.this);
        mListView.setAdapter(lecturerResultAdapter);
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
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                // 加入上下拉控件后，position索引从1开始，而不是从0
                Intent intentToDetail = new Intent(LecturerAllActivity.this, LecturerDetailActivity.class);
                intentToDetail.putExtra("teacherId", String.valueOf(list.get(position-1).getTeacherId()));
                startActivity(intentToDetail);
            }
        });

    }

    /**
     * 功能：获取所有讲师列表信息
     * type 1为没有搜索 2为搜索查询
     */
    private void initData()
    {
        RequestParams requestParams = new RequestParams();
        String url = "";
        if("1".equals(type))
        {
            url = Constants.LECTURER_ALL_URL;
        } else
        {
            requestParams.put("userid", PreferenceUtils.getUserId());
            requestParams.put("condition",searchEt.getText().toString().trim());

            requestParams.put("type","2");
            url = Constants.LECTURER_ALL_SEARCH_URL;
        }
        HttpUtil.post(url,requestParams, new LoadJsonHttpResponseHandler(this, new LoadDatahandler()
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
                        if ("1".equals(type))
                        {
                            list = gson.fromJson(data.getString("teacherList"), new TypeToken<List<TeacherCourse>>() {}.getType());
                        } else
                        {
                            list = gson.fromJson(data.getString("data"), new TypeToken<List<TeacherCourse>>() {}.getType());
                        }
                        lecturerResultAdapter.setListItems(list);
                        lecturerResultAdapter.notifyDataSetChanged();
                    } else
                    {
                        toastUtil.show(msg);
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
    public boolean isSystemBarTranclucent()
    {
        return false;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
    {
        Log.e("EditorInfo","IME_ACTION_SEARCH 0x00000003="+actionId);
        if(actionId == EditorInfo.IME_ACTION_SEARCH)
        {
            // 先隐藏键盘
            ((InputMethodManager) searchEt.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(this
                                    .getCurrentFocus()
                                    .getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
            type = "2";
            initData();
            return true;
        }
        return false;
    }
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_cancle:
                searchEt.setText("");
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("LecturerAllActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("LecturerAllActivity");
    }
}
