package com.fips.huashun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.modle.bean.ActivtyOptionBean;
import com.fips.huashun.modle.bean.ActivtyVoteBean;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.adapter.EnterpriseVoteAdapter;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.ToastUtil;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 功能：活动投票
 * Created by Administrator on 2016/8/23
 *
 * @author 张柳 时间：2016年8月23日20:43:09
 */
public class EnterpriseVoteActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener
{

    private NavigationBar navigationBar;
    private ListView mListView;
    private TextView titleTv, timeTv, ruleTv, contentTv;
    private EnterpriseVoteAdapter enterpriseVoteAdapter;
    private Button submitBtn;
    private List<ActivtyOptionBean> list;
    // 投票活动ID,是否已投票（0：已投票；1：未投票）
    private String pid, isvote;
    private Intent intent;
    private Gson gson;
    private ActivtyVoteBean activtyVoteBean;
    private String selectOption = null;
    private String numerical = "0";
    private ToastUtil toastUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterprise_vote);
        initView();
//        initDate();

        getVoteDate();
    }

    @Override
    protected void initView()
    {
        super.initView();
        toastUtil = ToastUtil.getInstant();
        gson = new Gson();
        intent = getIntent();
        pid = intent.getStringExtra("pid");
        isvote = intent.getStringExtra("isvote");
        titleTv = (TextView) findViewById(R.id.tv_vote_title);
        timeTv = (TextView) findViewById(R.id.tv_vote_time);
        ruleTv = (TextView) findViewById(R.id.tv_vote_rule);
        contentTv = (TextView) findViewById(R.id.tv_vote_content);
        mListView = (ListView) findViewById(R.id.lv_enterprise_vote);
        enterpriseVoteAdapter = new EnterpriseVoteAdapter(this,isvote);
        mListView.setAdapter(enterpriseVoteAdapter);
//        mListView.setOnItemClickListener(this);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if("0".equals(isvote)) {
                    List<ActivtyOptionBean> allListDate = enterpriseVoteAdapter.getAllListDate();
                    selectOption = allListDate.get(position).getContent();
                    for (int a = 0; a < allListDate.size(); a++) {
                        if (a == position) {
                            allListDate.get(a).setChecked(true);
                            enterpriseVoteAdapter.notifyDataSetChanged();

                        } else {
                            allListDate.get(a).setChecked(false);
                            enterpriseVoteAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
        navigationBar = (NavigationBar) findViewById(R.id.nb_enterprise_vote);
        navigationBar.setTitle("投票");
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
        submitBtn = (Button) findViewById(R.id.bt_enterprise_vote);
        submitBtn.setVisibility(View.GONE);
        submitBtn.setOnClickListener(this);
    }
    /**
     * 功能：获取数据
     */
    private void initDate()
    {
        RequestParams requestParams = new RequestParams();
        String url;
        if ("0".equals(isvote))
        {// 已投票
            url = Constants.GET_PROJECTVOTERESULT_URL;
        } else
        {// 未投票
            url = Constants.GET_PROJECTVOTEDETAIL_URL;
        }
        requestParams.put("pid",pid);
        HttpUtil.post(url, requestParams, new LoadJsonHttpResponseHandler(this, new LoadDatahandler()
        {
            @Override
            public void onStart()
            {
                super.onStart();
                showLoadingDialog();
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
                        activtyVoteBean = gson.fromJson(data.getString("data"),ActivtyVoteBean.class);
                        titleTv.setText(activtyVoteBean.getBigtitle());
                        timeTv.setText("截止时间："+activtyVoteBean.getEndtime());
                        ruleTv.setText("投票规则："+activtyVoteBean.getExplaincontent());
                        contentTv.setText(activtyVoteBean.getVotetitle());
                        enterpriseVoteAdapter.setListItems(activtyVoteBean.getOptionList());
                        if("1".equals(isvote)) {
                            submitBtn.setVisibility(View.GONE);
                            enterpriseVoteAdapter.setIsVoted(isvote);
                            enterpriseVoteAdapter.setSelectNum(activtyVoteBean.getSelectNum()+"");
                        } else {
                            submitBtn.setVisibility(View.VISIBLE);
                        }
                        enterpriseVoteAdapter.notifyDataSetChanged();
                    } else {
                        toastUtil.show(msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("JSONException","JSONException");
                }
            }

            @Override
            public void onFailure(String error, String message)
            {
                super.onFailure(error, message);
                Log.e("onFailure","onFailure");
                dimissLoadingDialog();
            }
        }));
    }

    @Override
    public void onClick(View v)
    {
            switch (v.getId())
            {
                case R.id.bt_enterprise_vote:
                    submitProjectVote();
                    break;
            }
    }
    /**
     * 功能：点击投票
     */
    private void submitProjectVote()
    {
        if(TextUtils.isEmpty(selectOption))
        {
            toastUtil.show("请选择投票选项!");
        } else
        {
            RequestParams requestParams = new RequestParams();
            requestParams.put("pid", activtyVoteBean.getPid());
            requestParams.put("userId", PreferenceUtils.getUserId());
            requestParams.put("selectOption", selectOption);
            HttpUtil.post(Constants.PHP_URL+"/java_set_vote", requestParams, new LoadJsonHttpResponseHandler(this, new LoadDatahandler()
            {
                @Override
                public void onStart()
                {
                    super.onStart();
                    showLoadingDialog();
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
                            toastUtil.show(msg);
                            isvote = "1";
                            getVoteDate();
                        } else
                        {
                            toastUtil.show(msg);
                        }
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                        Log.e("JSONException", "JSONException");
                        dimissLoadingDialog();
                    }
                }

                @Override
                public void onFailure(String error, String message)
                {
                    super.onFailure(error, message);
                    Log.e("onFailure", "onFailure");

                    dimissLoadingDialog();
                }
            }));
        }
    }
    @Override
    public boolean isSystemBarTranclucent()
    {
        return false;
    }



    private void getVoteDate(){
        RequestParams requestParams = new RequestParams();
        /*PHP 接口*/
        requestParams.put("id",pid);
        requestParams.put("user_id",PreferenceUtils.getUserId());
        HttpUtil.post(Constants.PHP_URL+"/java_get_vote_info", requestParams, new LoadJsonHttpResponseHandler(this, new LoadDatahandler(){
            @Override
            public void onSuccess(JSONObject data) {
                super.onSuccess(data);
                Log.e("data",data.toString());
                try
                {
                    String suc = data.get("suc").toString();
                    String msg = data.get("msg").toString();
                    if ("y".equals(suc))
                    {
                        activtyVoteBean = gson.fromJson(data.getString("data"),ActivtyVoteBean.class);
                        titleTv.setText(activtyVoteBean.getBigtitle());
                        timeTv.setText("截止时间："+activtyVoteBean.getEndtime());
                        ruleTv.setText("投票规则："+activtyVoteBean.getExplaincontent());
                        contentTv.setText(activtyVoteBean.getVotetitle());
                        //未投票
                        if("0".equals(isvote)) {
                            submitBtn.setVisibility(View.VISIBLE);
                            enterpriseVoteAdapter.setIsVoted(isvote);

                        } else {
                            enterpriseVoteAdapter.setIsVoted(isvote);
                            submitBtn.setVisibility(View.GONE);
                            enterpriseVoteAdapter.setSelectNum(activtyVoteBean.getSelectNum());
                        }
                        enterpriseVoteAdapter.setListItems(activtyVoteBean.getOptionList());
                        enterpriseVoteAdapter.notifyDataSetChanged();
                    } else {
                        toastUtil.show(msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("JSONException","JSONException");
                }

            }

            @Override
            public void onFailure(String error, String message) {
                super.onFailure(error, message);
            }
        }));
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        if ()

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("EnterpriseVoteActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("EnterpriseVoteActivity");
    }
}
