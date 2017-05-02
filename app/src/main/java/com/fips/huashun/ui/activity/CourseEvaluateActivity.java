package com.fips.huashun.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.fips.huashun.ApplicationEx;
import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.modle.bean.ActivityInfo;
import com.fips.huashun.modle.bean.CommentInfo;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.adapter.CourseEvaluateAdapter;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.ToastUtil;
import com.fips.huashun.widgets.FlowLayout;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 功能：课程评价
 * Created by Administrator on 2016/8/19.
 *
 * @author 张柳 时间：2016年8月19日11:16:06
 */
public class CourseEvaluateActivity extends BaseActivity implements OnClickListener
{
    private NavigationBar navigationBar;
    // 上下拉的控件
    private PullToRefreshListView pullToRefreshListView;
    // 显示评论列表
    private ListView mListView;
    // 好评率
    private TextView tv_goodcomment;
    // 评论适配器
    private CourseEvaluateAdapter mAdapter;
    // 数据保存
    private List<ActivityInfo> list;
    // 发表评论
    private Button pubishEvaluateBtn;
    // 课程ID
    private String courseId;
    // 评分
    private RatingBar ratingBar;
    // 写入评论内容
    private EditText contentEt;
    // 发送评论按钮
    private Button sendBtn;
    private View headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_evaluate);
        courseId = getIntent().getStringExtra("courseId");
        initView();
        showLoadingDialog();
        initData();
    }


    @Override
    protected void initView()
    {
        super.initView();
        navigationBar = (NavigationBar) findViewById(R.id.nb_course_evaluate_title);
        navigationBar.setLeftImage(R.drawable.course_reslt_back);
        navigationBar.setTitle("课程评价");
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
        pubishEvaluateBtn = (Button) findViewById(R.id.btn_course_evaluate);
        pubishEvaluateBtn.setOnClickListener(this);
        LayoutInflater lif = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        headerView = lif.inflate(R.layout.course_evaute_header,null);
        tv_goodcomment = (TextView) headerView.findViewById(R.id.tv_goodcomment);

        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.lv_course_evaluate);
        // 获取listview
        mListView = pullToRefreshListView.getRefreshableView();
        // 两端刷新
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);

        mAdapter = new CourseEvaluateAdapter(this);
        // 设置适配器
        mListView.setAdapter(mAdapter);
        mListView.setVisibility(View.INVISIBLE);
        mListView.addHeaderView(headerView,null,false);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

            }
        });
        // 设置上下拉事件
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
    }

    /**
     * 功能：点击事件-发表评论
     * @param v
     */
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_course_evaluate:
                //发表评论
                if(ApplicationEx.getInstance().isLogined())
                {
                    showDialog();
                } else
                {// 用户没有登录，跳到登录页面
                    Intent intentToLogin = new Intent(CourseEvaluateActivity.this, LoginActivity.class);
                    startActivity(intentToLogin);
                }

                break;
        }
    }

    @Override
    public boolean isSystemBarTranclucent()
    {
        return false;
    }

    private void initData()
    {
        RequestParams requestParams = new RequestParams();
        requestParams.put("userid",PreferenceUtils.getUserId());
        requestParams.put("courseid", courseId);
        HttpUtil.post(Constants.QUERYALLCOMMENTSBYCOURSEID, requestParams, new LoadJsonHttpResponseHandler(this, new LoadDatahandler()
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
                    String suc = data.getString("suc");
                    String msg = data.getString("msg");
                    if("y".equals(suc))
                    {

                        String dataString = data.getString("data");
                        JSONObject jsonObject = new JSONObject(dataString);
                        String welltoken = jsonObject.getString("welltoken");
                        List<CommentInfo> commentList = gson.fromJson(jsonObject.getString("commentList"), new TypeToken<List<CommentInfo>>() {}.getType());
                        if(null == commentList || commentList.size() == 0)
                        {
                            headerView.setVisibility(View.INVISIBLE);
                            mListView.setVisibility(View.INVISIBLE);
                        } else
                        {
                            headerView.setVisibility(View.VISIBLE);
                            mListView.setVisibility(View.VISIBLE);
                        }
                        mAdapter.setListItems(commentList);
                        mAdapter.notifyDataSetChanged();
                        tv_goodcomment.setText(welltoken);
                        pubishEvaluateBtn.setVisibility(View.VISIBLE);
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
    /**
     * 功能：评论弹出框
     */
    private void showDialog()
    {
        View view = getLayoutInflater().inflate(R.layout.couser_evaluate_dialog, null);
        final Dialog dialog = new Dialog(this, R.style.activityDialog);
        dialog.setContentView(view, new FlowLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams wl = window.getAttributes();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        ratingBar = (RatingBar) window.findViewById(R.id.rb_course_evaluate_dialog);
        contentEt = (EditText) window.findViewById(R.id.et_course_evaluate_dialog);
        sendBtn = (Button) window.findViewById(R.id.btn_course_evaluate_dialog);

        sendBtn.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               int pifen =(int)ratingBar.getRating();
                if(pifen == 0)
                {
                    ToastUtil.getInstant().show("请先给课程评分");
                    return;
                }
                String content = contentEt.getText().toString().trim();
                if(TextUtils.isEmpty(content))
                {
                    ToastUtil.getInstant().show("请先写下您的评价");
                    return;
                }
                publishCourseEvalue(pifen,content,dialog);
            }
        });
    }
    /**
     * 功能：发表课程评论
     * @param pifen  评分
     * @param content 评论内容
     * @param dialog 弹出框
     */
    private void publishCourseEvalue(int pifen , String content, final Dialog dialog)
    {
        RequestParams requestParams = new RequestParams();
        requestParams.put("userid", PreferenceUtils.getUserId());
        requestParams.put("courseid", courseId);
        // 评论内容
        requestParams.put("count", content);
        // 评分
        requestParams.put("num", String.valueOf(pifen));
        HttpUtil.post(Constants.PUBLISH_COURSE_EVALUATE_URL, requestParams, new LoadJsonHttpResponseHandler(this, new LoadDatahandler()
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
                    String suc = data.getString("suc");
                    String msg = data.getString("msg");
                    if("y".equals(suc))
                    {
                        dialog.dismiss();
                        ToastUtil.getInstant().show("评价成功");
                        // 刷新页面
                        initData();
                    } else
                    {
                        ToastUtil.getInstant().show(msg);
                    }

                } catch (JSONException e)
                {
                    e.printStackTrace();
                    ToastUtil.getInstant().show("评价失败，请重试");
                }
            }

            @Override
            public void onFailure(String error, String message)
            {
                super.onFailure(error, message);
                dimissLoadingDialog();
                ToastUtil.getInstant().show("评价失败，请重试");
            }
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("CourseEvaluateActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("CourseEvaluateActivity");
    }
}
