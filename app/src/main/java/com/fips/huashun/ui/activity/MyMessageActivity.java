package com.fips.huashun.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.modle.bean.MyMessage;
import com.fips.huashun.modle.bean.MyMessageList;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.adapter.MyMessageAdapter;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.ToastUtil;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 功能：我的消息（系统消息/课程消息/活动消息）
 * Created by Administrator on 2016/8/25.
 *
 * @author 张柳 时间：2016年8月25日10:07:13
 */
public class MyMessageActivity extends BaseActivity implements View.OnClickListener
{
    private NavigationBar navigationBar;
    // 上下拉的控件
    private PullToRefreshListView pullToRefreshListView;
    // 列表
    private ListView mListView;
    private MyMessageAdapter myMessageAdapter;
    private RelativeLayout systemLayout, courseLayout, actLayout;
    private TextView systemTv, courseTv, actTv;
    // 提示红点
    private ImageView systemIv, courseIv, actIv;
    private Gson gson;
    private ToastUtil toastUtil;
    // 服务器返回的数据
    private MyMessageList myMsgAll;
    // 消息具体数据
    private List<MyMessage> myMsgList;
    // type 表示查询哪类消息 1 系统消息，2 课程消息，3 活动消息
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymessage);
        gson = new Gson();
        toastUtil = ToastUtil.getInstant();
        initView();
        showLoadingDialog();
        type = "1";
        initData(type);
    }

    @Override
    protected void initView()
    {
        super.initView();
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.lv_mymessage);
        mListView = pullToRefreshListView.getRefreshableView();
        // 两端刷新
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        myMessageAdapter = new MyMessageAdapter(getApplicationContext());
        mListView.setAdapter(myMessageAdapter);
        systemLayout = (RelativeLayout) findViewById(R.id.rl_system_msg);
        systemLayout.setSelected(true);
        courseLayout = (RelativeLayout) findViewById(R.id.rl_course_msg);
        actLayout = (RelativeLayout) findViewById(R.id.rl_act_msg);
        systemTv = (TextView) findViewById(R.id.tv_system_msg);
        systemTv.setTextColor(getResources().getColor(R.color.white));
        courseTv = (TextView) findViewById(R.id.tv_course_msg);
        actTv = (TextView) findViewById(R.id.tv_act_msg);
        systemIv = (ImageView) findViewById(R.id.iv_system_msg);
        courseIv = (ImageView) findViewById(R.id.iv_course_msg);
        actIv = (ImageView) findViewById(R.id.iv_act_msg);
        navigationBar = (NavigationBar) findViewById(R.id.nb_mymessage);
        navigationBar.setTitle("我的消息");
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
        systemLayout.setOnClickListener(this);
        courseLayout.setOnClickListener(this);
        actLayout.setOnClickListener(this);
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
                if(myMsgList.get(position-1).getMsgOpen())
                {
                    myMsgList.get(position-1).setMsgOpen(false);
                } else
                {
                    myMsgList.get(position-1).setMsgOpen(true);
                    if("0".equals(myMsgList.get(position-1).getMsgstate()))
                    {
                        // 向后台设置为已读
                        hadCheckMsg(myMsgList.get(position-1).getMsgid(),type,(position-1));
                    }
                }
                myMessageAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 初始化数据
     * @param type
     */
    public void initData(final String type)
    {
        RequestParams requestParams = new RequestParams();
        requestParams.put("userid", PreferenceUtils.getUserId());
        String url = null;
        if("1".equals(type))
        {// 系统消息
            url = Constants.MYSYSTEMMSG_URL;
        } else if("2".equals(type))
        {// 课程消息
            url = Constants.MYCOURSEMSG_URL;
        }else if("3".equals(type))
        {// 活动消息
            url = Constants.MYACTIVITYMSG_URL;
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
                        myMsgAll = gson.fromJson(data.getString("data"), MyMessageList.class);
                        if("0".equals(myMsgAll.getSysMsgCount()))
                        {
                            systemIv.setVisibility(View.GONE);
                        }
                        else
                        {
                            systemIv.setVisibility(View.VISIBLE);
                        }
                        if("0".equals(myMsgAll.getCourseMsgCount()))
                        {
                            courseIv.setVisibility(View.GONE);
                        }
                        else
                        {
                            courseIv.setVisibility(View.VISIBLE);
                        }
                        if("0".equals(myMsgAll.getActMsgCount()))
                        {
                            actIv.setVisibility(View.GONE);
                        }
                        else
                        {
                            actIv.setVisibility(View.VISIBLE);
                        }
                        myMsgList = myMsgAll.getMsgList();
                        myMessageAdapter.setListItems(myMsgList);
                        myMessageAdapter.notifyDataSetChanged();
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
    public void onClick(View v)
    {
        if (v.getId() == R.id.rl_system_msg)
        {
            systemLayout.setSelected(true);
            courseLayout.setSelected(false);
            actLayout.setSelected(false);
            systemTv.setTextColor(getResources().getColor(R.color.white));
            courseTv.setTextColor(getResources().getColor(R.color.mycourse_text_blue));
            actTv.setTextColor(getResources().getColor(R.color.mycourse_text_blue));
            type = "1";
        } else if (v.getId() == R.id.rl_course_msg)
        {
            systemLayout.setSelected(false);
            courseLayout.setSelected(true);
            actLayout.setSelected(false);
            systemTv.setTextColor(getResources().getColor(R.color.mycourse_text_blue));
            courseTv.setTextColor(getResources().getColor(R.color.white));
            actTv.setTextColor(getResources().getColor(R.color.mycourse_text_blue));
            type = "2";
        } else if (v.getId() == R.id.rl_act_msg)
        {
            systemLayout.setSelected(false);
            courseLayout.setSelected(false);
            actLayout.setSelected(true);
            systemTv.setTextColor(getResources().getColor(R.color.mycourse_text_blue));
            courseTv.setTextColor(getResources().getColor(R.color.mycourse_text_blue));
            actTv.setTextColor(getResources().getColor(R.color.white));
            type = "3";
        }
        showLoadingDialog();
        initData(type);
    }

    /**
     * 功能：消息设置成已读
     * @param msgid 消息ID
     * @param type 消息类型
     */
    private void hadCheckMsg(String msgid, String type, final int position)
    {
        RequestParams requestParams = new RequestParams();
        requestParams.put("userid", PreferenceUtils.getUserId());
        requestParams.put("msgid", msgid);
        requestParams.put("type", type);
        HttpUtil.post(Constants.HADCHECKMSG_URL, requestParams, new LoadJsonHttpResponseHandler(this, new LoadDatahandler()
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
                        String sysMsgCount = data.get("sysMsgCount").toString();
                        String courseMsgCount = data.get("courseMsgCount").toString();
                        String actMsgCount = data.get("actMsgCount").toString();
                        myMsgList.get(position).setMsgstate("1");
                        if("0".equals(sysMsgCount))
                        {
                            systemIv.setVisibility(View.GONE);
                        } else
                        {
                            systemIv.setVisibility(View.VISIBLE);
                        }
                        if("0".equals(courseMsgCount))
                        {
                            courseIv.setVisibility(View.GONE);
                        } else
                        {
                            courseIv.setVisibility(View.VISIBLE);
                        }
                        if("0".equals(actMsgCount))
                        {
                            actIv.setVisibility(View.GONE);
                        } else
                        {
                            actIv.setVisibility(View.VISIBLE);
                        }
                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error, String message)
            {
                super.onFailure(error, message);
            }
        }));
    }

    @Override
    public boolean isSystemBarTranclucent()
    {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MyMessageActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MyMessageActivity");
    }
}
