package com.fips.huashun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.modle.bean.ActivityDetail;
import com.fips.huashun.modle.bean.GridViewBean;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.ToastUtil;
import com.fips.huashun.widgets.MyGridView;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

public class BusinessActivity extends BaseActivity {
    private GridViewBean[] mGridViewBeans = {new GridViewBean(R.drawable.business_one, "通知")
            , new GridViewBean(R.drawable.business_two, "签到"), new GridViewBean(R.drawable.business_three, "课程")
            , new GridViewBean(R.drawable.business_four, "考试"), new GridViewBean(R.drawable.business_five, "评估"), new GridViewBean(R.drawable.business_six, "pk榜"),};

    private NavigationBar navigationBar;
    private ImageView logoIv;
    private TextView titleTv,startTimeTv,endTimeTv,contentTv;
    protected Gson gson;
    private Intent intent;
    private String activityId;
    private ToastUtil toastUtil;
    private ActivityDetail activityInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);
        gson = new Gson();
        intent = getIntent();
        activityId = intent.getStringExtra("activityId");
        Log.e("activityId",activityId);
        toastUtil =ToastUtil.getInstant();
        initView();
        initData();
    }

    @Override
    public boolean isSystemBarTranclucent() {
        return false;
    }

    @Override
    protected void initView() {
        super.initView();
        navigationBar = (NavigationBar) findViewById(R.id.na_bar);
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
        logoIv = (ImageView) findViewById(R.id.iv_enterprise_act_logo);
        titleTv = (TextView) findViewById(R.id.tv_enterprise_act_title);
        startTimeTv = (TextView) findViewById(R.id.tv_enterprise_act_starttime);
        endTimeTv = (TextView) findViewById(R.id.tv_enterprise_act_endtime);
        contentTv = (TextView) findViewById(R.id.tv_enterprise_act_content);
        MyGridView gv_view = (MyGridView) findViewById(R.id.gv_view);
        gv_view.setAdapter(new MyGridViewAdapter());
        gv_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =null;
                switch (position){
                    case 0:
                         intent = new Intent(BusinessActivity.this, BusinessNoticeActivity.class);
                        intent.putExtra("activityId",activityId);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(BusinessActivity.this, EnterpriseActSignActivity.class);
                        intent.putExtra("activityId",activityId);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(BusinessActivity.this, ActtivityCourseActivity.class);
                        intent.putExtra("activityId",activityId);
                        startActivity(intent);
                        break;
                    case 3:
                        intent= new Intent(BusinessActivity.this,WebviewActivity.class);
                        intent.putExtra("activityId",activityId);
                        intent.putExtra("key",9);//考试
                        startActivity(intent);
                        break;
                    case 4:
                        intent=new Intent(BusinessActivity.this,WebviewActivity.class);
                        intent.putExtra("activityId",activityId);
                        intent.putExtra("key",10);//评估
                        startActivity(intent);

                        break;
                    case 5:
                        Intent intentToActPk = new Intent(BusinessActivity.this, EnterpriseActPkActivity.class);
                        intentToActPk.putExtra("activityId",activityId);
                        startActivity(intentToActPk);
                        break;
                }
            }
        });
    }
    private class MyGridViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mGridViewBeans.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = View.inflate(BusinessActivity.this, R.layout.cottage_item, null);
            ImageView imagView = (ImageView) view.findViewById(R.id.iv_image);
            TextView textview = (TextView) view.findViewById(R.id.tv_text);
            imagView.setImageResource(mGridViewBeans[position].getImageRes());
            textview.setText(mGridViewBeans[position].getTitle());

            return view;
        }
    }
    private void initData()
    {
        RequestParams requestParams = new RequestParams();
        requestParams.put("activityid",activityId);
        HttpUtil.post(Constants.ACTIVITY_INFO_URL, requestParams, new LoadJsonHttpResponseHandler(BusinessActivity.this, new LoadDatahandler()
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
                        activityInfo = gson.fromJson(data.getString("data"),ActivityDetail.class);
                        ImageLoader.getInstance().displayImage(Constants.IMG_URL+activityInfo.getActivity().getActivityimg(),logoIv);
                        titleTv.setText(activityInfo.getActivity().getActivityname());
                        startTimeTv.setText(activityInfo.getActivity().getStarttime());
                        endTimeTv.setText(activityInfo.getActivity().getEndtime());
                        contentTv.setText(activityInfo.getActivity().getActivitycontent());
                    } else
                    {
                        toastUtil.show(msg);
                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                    Log.e("JSONException","JSONException");
                    dimissLoadingDialog();
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
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("BusinessActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("BusinessActivity");
    }
}
