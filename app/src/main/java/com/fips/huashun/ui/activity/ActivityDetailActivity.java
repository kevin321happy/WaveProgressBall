package com.fips.huashun.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fips.huashun.ApplicationEx;
import com.fips.huashun.R;
import com.fips.huashun.modle.bean.ActivityDetail;
import com.fips.huashun.modle.bean.ActivityInfo;
import com.fips.huashun.modle.bean.ActivityJoinInfo;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.adapter.ActivityJoinAdapter;
import com.fips.huashun.ui.utils.AlertDialogUtils;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.Utility;

import com.fips.huashun.common.Constants;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.ui.utils.ToastUtil;
import com.fips.huashun.widgets.FlowLayout;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
/**
 * 功能：活动详情
 * Created by Administrator on 2016/8/19.
 */
public class ActivityDetailActivity extends BaseActivity implements OnClickListener
{
    private ScrollView mScrollView;
    private TextView emptyTv;
    private NavigationBar navigationBar;
    private Intent intent;
    private String activityId;
    // 活动LOGO,关闭
    private ImageView activityImg,buyCloseIv;
    // 活动标题，开始时间，结束时间，地点，报名人数，费用，内容
    private TextView titleTv, startTimeTv, endTimeTv, addressTv, joinNumTv, priceTv, contentTv;
    // 已报名人展示
    private ListView mListView;
    // 报名按钮
    private Button joinActBtn;
    // 弹出框的组件
    private Dialog dialog;
    // 充值,报名
    private Button rechargeBtn,actJoinBtn;
    private TextView actNameTv,actPriceTv,beanPointTv,needPointTv;
    protected Gson gson;
    private ToastUtil toastUtil;
    // 数据
    private ActivityInfo activityInfo;
    // 报名人集合
    private List<ActivityJoinInfo> activityJoinInfos;
    // 用户是否报名
    private String joinstate;
    // 用户当前魔豆
    private int bean_point;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_detail);
        initView();
        initData();
    }

    @Override
    protected void initView()
    {
        super.initView();
        gson = new Gson();
        intent = getIntent();
        activityId = intent.getStringExtra("activityId");
        toastUtil = ToastUtil.getInstant();
        navigationBar = (NavigationBar) findViewById(R.id.nb_activity_detail);
        navigationBar.setTitle("活动详情");
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
        mScrollView = (ScrollView) findViewById(R.id.sv_activity_detail);
        emptyTv = (TextView) findViewById(R.id.tv_empty);
        activityImg = (ImageView) findViewById(R.id.iv_activity_detail);
        titleTv = (TextView) findViewById(R.id.tv_activity_title);
        startTimeTv = (TextView) findViewById(R.id.tv_activity_starttime);
        endTimeTv = (TextView) findViewById(R.id.tv_activity_endtime);
        addressTv = (TextView) findViewById(R.id.tv_activity_address);
        joinNumTv = (TextView) findViewById(R.id.tv_activity_joinnum);
        priceTv = (TextView) findViewById(R.id.tv_activity_price);
        contentTv = (TextView) findViewById(R.id.tv_activity_content);
        mListView = (ListView) findViewById(R.id.lv_activity_join);
        mListView.setFocusable(false);
        joinActBtn = (Button) findViewById(R.id.bt_activity_join);
        joinActBtn.setOnClickListener(this);
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        initData();
    }

    // 获取活动详情信息
    private void initData()
    {
        RequestParams requestParams = new RequestParams();
        requestParams.put("activityid", activityId);
        String userid ="";
        if(ApplicationEx.getInstance().isLogined())
        {
            userid = PreferenceUtils.getUserId();
        }
        requestParams.put("userid",userid);
        HttpUtil.post(Constants.ACTIVITY_INFO_URL, requestParams, new LoadJsonHttpResponseHandler(getApplicationContext(), new LoadDatahandler()
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
                    Log.e("suc", "suc=" + data.toString());
                    if ("y".equals(suc))
                    {
                        mScrollView.setVisibility(View.VISIBLE);
                        emptyTv.setVisibility(View.GONE);
                        ActivityDetail activityDetail = gson.fromJson(data.getString("data"),ActivityDetail.class);
                        activityInfo = activityDetail.getActivity();
                        activityJoinInfos = activityDetail.getJoinnerList();
                        joinstate = activityDetail.getJoinstate();
                        ImageLoader.getInstance().displayImage(Constants.IMG_URL + activityInfo.getActivityimg(), activityImg,ApplicationEx.activity_detail_options);
                        titleTv.setText(activityInfo.getActivityname());
                        startTimeTv.setText(activityInfo.getStarttime());
                        endTimeTv.setText(activityInfo.getEndtime());
                        addressTv.setText(activityInfo.getActivityaddre());
                        joinNumTv.setText(activityInfo.getJoinnum() + "人");
                        if (TextUtils.isEmpty(activityInfo.getActivityprice()))
                        {
                            priceTv.setText("免费");
                        } else
                        {
                            priceTv.setText(activityInfo.getActivityprice() + "魔豆");
                        }
                        contentTv.setText(activityInfo.getActivitycontent());

                        ActivityJoinAdapter activityJoinAdapter = new ActivityJoinAdapter(getApplicationContext(), activityJoinInfos);
                        mListView.setDividerHeight(0);
                        mListView.setAdapter(activityJoinAdapter);
                        Utility.setListViewHeightBasedOnChildren(mListView);
                        joinActBtn.setVisibility(View.VISIBLE);
                        if(ApplicationEx.getInstance().isLogined())
                        {
                            if ("0".equals(joinstate))
                            {
                                joinActBtn.setText("我要报名");
                                joinActBtn.setBackgroundResource(R.drawable.selector_join_activity);
                                joinActBtn.setEnabled(true);
                            } else
                            {
                                joinActBtn.setText("已报名");
                                joinActBtn.setBackgroundResource(R.color.text_hui);
                                joinActBtn.setEnabled(false);
                            }
                        }
                    } else
                    {
                        ToastUtil.getInstant().show(msg);
                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                    dimissLoadingDialog();
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
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.bt_activity_join:
                searchBeanPoint();
                break;
        }
    }

    /**
     * 功能：报名弹出框
     */
    private void showDialog()
    {
        View view = getLayoutInflater().inflate(R.layout.buy_activity_dialog, null);
        dialog = new Dialog(this, R.style.activityDialog);
        dialog.setContentView(view, new FlowLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        actNameTv = (TextView) window.findViewById(R.id.tv_activity_name);
        actPriceTv = (TextView) window.findViewById(R.id.tv_activity_price);
        beanPointTv = (TextView) window.findViewById(R.id.tv_bean_point);
        needPointTv = (TextView) window.findViewById(R.id.tv_need_point);
        buyCloseIv = (ImageView) window.findViewById(R.id.iv_buy_close);
        rechargeBtn = (Button) window.findViewById(R.id.btn_recharge);
        actJoinBtn = (Button) window.findViewById(R.id.bt_activity_join_add);
        actNameTv.setText(activityInfo.getActivityname());
        beanPointTv.setText(bean_point+".00魔豆");
        if(TextUtils.isEmpty(activityInfo.getActivityprice()))
        {
            actPriceTv.setText("0.00魔豆");
            needPointTv.setText("0.00魔豆");
            rechargeBtn.setVisibility(View.INVISIBLE);
        } else
        {
            actPriceTv.setText(activityInfo.getActivityprice()+"魔豆");
            int needPoint = bean_point-((int)Double.parseDouble(activityInfo.getActivityprice()));
            if(needPoint < 0)
            {// 魔豆不足
                needPointTv.setText(needPoint+".00魔豆");
                rechargeBtn.setVisibility(View.VISIBLE);

            } else
            {
                needPointTv.setText("0.00魔豆");
                rechargeBtn.setVisibility(View.INVISIBLE);
            }
        }
        rechargeBtn.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {// 跳转进入魔豆充值页面
                Intent intentToRecharge = new Intent(ActivityDetailActivity.this,RechargeBeanActivity.class);
                intentToRecharge.putExtra("beanPoint",String.valueOf(bean_point));
                startActivity(intentToRecharge);
                dialog.dismiss();
            }
        });
        actJoinBtn.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if (Double.valueOf(activityInfo.getActivityprice()) == 0)
                {
                    dialog.dismiss();
                    joinActivity();
                } else
                {
                    showJoinActivityDialog();
                }

            }
        });
        buyCloseIv.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });
    }
    /**
     * 功能：用户查询当前魔豆
     */
    private void searchBeanPoint()
    {
        if (ApplicationEx.getInstance().isLogined())
        {// 用户已登录
            RequestParams requestParams = new RequestParams();
            requestParams.put("activityid", activityId);
            requestParams.put("userid",PreferenceUtils.getUserId());
            HttpUtil.post(Constants.SEARCH_BEANPOINT_URL, requestParams, new LoadJsonHttpResponseHandler(getApplicationContext(), new LoadDatahandler()
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
                        if("y".equals(suc))
                        {
                            bean_point = (int) data.get("bean_point");
                            showDialog();
                        } else
                        {
                            toastUtil.show("查询当前魔豆失败，请重试！");
                        }
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                        toastUtil.show("查询当前魔豆失败，请重试！");
                    }
                }
                @Override
                public void onFailure(String error, String message)
                {
                    super.onFailure(error, message);
                    dimissLoadingDialog();
                    toastUtil.show("查询当前魔豆失败，请重试！");
                }
            }));
        } else
        {// 用户没有登录，跳到登录页面
            Intent intentToLogin = new Intent(ActivityDetailActivity.this, LoginActivity.class);
            startActivity(intentToLogin);
        }
    }
    /**
     *  功能：确认报名弹出框
     */
    private void showJoinActivityDialog()
    {
        AlertDialogUtils.showTowBtnDialog(this, "是否确认报名?", "取消", "确认", new AlertDialogUtils.DialogClickInter() {
            @Override
            public void leftClick(android.app.AlertDialog dialog) {
                dialog.cancel();
            }

            @Override
            public void rightClick(android.app.AlertDialog dialog) {
                dialog.cancel();
                joinActivity();
            }
        });
    }
    /**
     * 功能：活动报名
     */
    private void joinActivity()
    {
        if (ApplicationEx.getInstance().isLogined())
        {// 用户已登录
            RequestParams requestParams = new RequestParams();
            requestParams.put("activityid", activityId);
            requestParams.put("userid", PreferenceUtils.getUserId());
            requestParams.put("beannum",activityInfo.getActivityprice());
            HttpUtil.post(Constants.ACTIVITY_JOIN_URL, requestParams, new LoadJsonHttpResponseHandler(getApplicationContext(), new LoadDatahandler()
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
                        toastUtil.show(msg);
                        if("y".equals(suc))
                        {
                            if(null != dialog)
                            {
                                dialog.dismiss();
                            }
                            initData();
                        } else
                        {

                        }
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                        toastUtil.show("报名异常，请重试！");
                    }
                }
                @Override
                public void onFailure(String error, String message)
                {
                    super.onFailure(error, message);
                    dimissLoadingDialog();
                    toastUtil.show("报名失败，请重试！");
                }
            }));
        } else
        {// 用户没有登录，跳到登录页面
            Intent intentToLogin = new Intent(ActivityDetailActivity.this, LoginActivity.class);
            startActivity(intentToLogin);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("ActivityDetailActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("ActivityDetailActivity");
    }

    @Override
    public boolean isSystemBarTranclucent()
    {
        return false;
    }
}
