package com.fips.huashun.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fips.huashun.ApplicationEx;
import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.modle.bean.CourseInfo;
import com.fips.huashun.modle.bean.LecturerDetail;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.adapter.TeacherDetailCourseAdapter;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.ToastUtil;
import com.fips.huashun.widgets.CircleImageView;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.Config;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 功能：讲师详情
 * Created by Administrator on 2016/8/17.
 * @author  张柳 时间：2016年8月17日10:59:07
 */
public class LecturerDetailActivity extends BaseActivity implements OnClickListener,OnLikeListener
{
    private NavigationBar navigationBar;
    //
    private PullToRefreshListView pullToRefreshListView;
    // 列表
    private ListView mListView;
    private TeacherDetailCourseAdapter allCourseAdapter;
    // 讲师相关课程集合
    private List<CourseInfo> courseInfoList;
    private Gson gson;
    private Intent intent;
    private ToastUtil toastUtil;
    // 讲师ID
    private String teacherId;
    // 网络图片加载类
    private ImageLoader mImageLoader;
    // 数据集合
    private List<CourseInfo> mList;
    // 讲师头像
    private CircleImageView headIv;
    // 讲师姓名,介绍,访问网络之前空的
    private TextView nameTv,introductionTv,emptyTv,courseShowTv;
    // 点赞，收藏,分享
    private LikeButton likeIv,collectIv;
    // 分享讲师的默认头像
    private UMImage image;
    private   ImageView shareIv;
    private TextView tv_dianzha_number;
    private TextView tv_collecte_number;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_detail);
//        String[] mPermissionList = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS};
//        ActivityCompat.requestPermissions(LecturerDetailActivity.this,mPermissionList, 100);
        initView();
        showLoadingDialog();
        initData();

    }
    @Override
    protected void initView()
    {
        super.initView();
        toastUtil = ToastUtil.getInstant();
        mImageLoader = ImageLoader.getInstance();
        gson = new Gson();
        intent = getIntent();
        String data = getIntent().getDataString();
        if(null == data || TextUtils.isEmpty(data))
        {
            teacherId = getIntent().getStringExtra("teacherId");
        } else
        {
            String[] dataString = data.split("=");
            teacherId = dataString[1];
        }
        Log.e("teacherId",teacherId);
        navigationBar = (NavigationBar) findViewById(R.id.nb_lecturer_detail);
        navigationBar.setTitle("讲师详情");
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
        LayoutInflater lif = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View headerView = lif.inflate(R.layout.activity_lecturer_detail_header,null);
        headIv = (CircleImageView) headerView.findViewById(R.id.iv_lecturer_head);
        nameTv = (TextView) headerView.findViewById(R.id.tv_lecturer_name);
        tv_dianzha_number = (TextView) headerView.findViewById(R.id.tv_dianzha_number);
        tv_collecte_number = (TextView) headerView.findViewById(R.id.tv_collecte_number);

        introductionTv = (TextView) headerView.findViewById(R.id.tv_lecturer_introduction);
        courseShowTv = (TextView) headerView.findViewById(R.id.tv_lecturer_course_show);
        likeIv = (LikeButton) headerView.findViewById(R.id.iv_lecturer_like);
        collectIv = (LikeButton) headerView.findViewById(R.id.iv_lecturer_collect);
        shareIv = (ImageView) headerView.findViewById(R.id.iv_lecturer_share);
        emptyTv = (TextView) findViewById(R.id.tv_empty);
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.lv_lecturer_detail_course);
        mListView = pullToRefreshListView.getRefreshableView();
        // 两端刷新
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        allCourseAdapter = new TeacherDetailCourseAdapter(LecturerDetailActivity.this);
        mListView.setAdapter(allCourseAdapter);
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
                Intent intent =new Intent(LecturerDetailActivity.this,CourseDetailActivity.class);
                intent.putExtra("courseId",  allCourseAdapter.getItem(position-2).getCourseId());
                startActivity(intent);
            }
        });
        mListView.addHeaderView(headerView,null,false);
        mListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        likeIv.setOnLikeListener(this);
        collectIv.setOnLikeListener(this);
        shareIv.setOnClickListener(this);
    }
    /**
     * 功能：获取讲师信息
     */
    private void initData()
    {
        RequestParams requestParams = new RequestParams();
        requestParams.put("teacherid", teacherId);
        requestParams.put("userid", PreferenceUtils.getUserId());
        HttpUtil.post(Constants.LECTURER_INFO_URL, requestParams, new LoadJsonHttpResponseHandler(this, new LoadDatahandler()
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
                        emptyTv.setVisibility(View.GONE);
                        pullToRefreshListView.setVisibility(View.VISIBLE);
                        mListView.setVisibility(View.VISIBLE);
                        LecturerDetail lecturerDetail = gson.fromJson(data.getString("data"),LecturerDetail.class);
                        // 头像
                        mImageLoader.displayImage(Constants.IMG_URL+lecturerDetail.getTeacherinfo().getTeacherPhoto(),headIv,ApplicationEx.lecturer_info_options);
                        image = new UMImage(LecturerDetailActivity.this,Constants.IMG_URL+lecturerDetail.getTeacherinfo().getTeacherPhoto());
                        // 姓名
                        nameTv.setText(lecturerDetail.getTeacherinfo().getTeacherName());
                        // 介绍 TextView首行缩进
                        introductionTv.setText("\u3000\u3000"+lecturerDetail.getTeacherinfo().getIntroduction());
                        tv_collecte_number.setText(lecturerDetail.getTeacherinfo().getCollection());
                        tv_dianzha_number.setText(lecturerDetail.getTeacherinfo().getRiokin());

                        // 相关讲师课程列表
                        courseInfoList = lecturerDetail.getCourseList();
                        if(null == courseInfoList || courseInfoList.size() == 0)
                        {
                            courseShowTv.setVisibility(View.INVISIBLE);
                        }
                        allCourseAdapter.setListItems(courseInfoList);
                        allCourseAdapter.notifyDataSetChanged();
                        if(ApplicationEx.getInstance().isLogined())
                        {
                            // 收藏
                            String iscollect = lecturerDetail.getIscollect();
                            // 点赞
                            String isroick = lecturerDetail.getIsroick();
                            if ("1".equals(isroick))
                            {// 已点赞
                                likeIv.setLiked(true);
                                likeIv.setEnabled(false);
                            }else {

                            }
                            if ("1".equals(iscollect))
                            {// 已收藏
                                collectIv.setLiked(true);
                                collectIv.setEnabled(false);
                            }
                        }
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
    public void onClick(View v)
    {
        switch (v.getId()){
//            case R.id.iv_lecturer_like:
//                // 点赞
//                if(ApplicationEx.getInstance().isLogined())
//                {
//                    dianZhan();
//                } else
//                {
//                    Intent intentToLogin = new Intent(LecturerDetailActivity.this,LoginActivity.class);
//                    startActivity(intentToLogin);
//                }
//                break;
//            case R.id.iv_lecturer_collect:
//                // 收藏
//                if(ApplicationEx.getInstance().isLogined())
//                {
//                    addCollection();
//                } else
//                {
//                    Intent intentToLogin = new Intent(LecturerDetailActivity.this,LoginActivity.class);
//                    startActivity(intentToLogin);
//                }
//                break;
            case R.id.iv_lecturer_share:
                Config.REDIRECT_URL="http://sns.whalecloud.com/sina2/callback";
                new ShareAction(LecturerDetailActivity.this).setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.QZONE,SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setShareboardclickCallback(new ShareBoardlistener() {
                            @Override
                            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                                    new ShareAction(LecturerDetailActivity.this)
                                            .setPlatform(share_media)
                                            .withTitle(nameTv.getText().toString())
                                            .withText(introductionTv.getText().toString())
                                            .withTargetUrl(Constants.H5_URL+"teacher_share.html?userid="+ PreferenceUtils.getUserId()+"&teacherid="+teacherId)
                                            .withMedia(image)
                                            .setCallback(umShareListener)
                                            .share();

                            }
                        }).open();
                break;
        }
    }
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat","platform"+platform);
            if(platform.name().equals("WEIXIN_FAVORITE")){
                ToastUtil.getInstant().show("收藏成功啦");
            }else{
                ToastUtil.getInstant().show("分享成功啦");
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Log.e("sina",platform+"=========");
            Log.e("sina",t+"=====1====");
            ToastUtil.getInstant().show("分享失败啦");
            if(t!=null){
                Log.d("throw","throw:"+t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtil.getInstant().show("分享取消了");
        }
    };

    private  void dianZhan(){
        RequestParams requestParams =new RequestParams();
        requestParams.put("userid", PreferenceUtils.getUserId());
        requestParams.put("thumbid",teacherId);
        requestParams.put("type","2");
        HttpUtil.post(Constants.URL+"/teachAndCourseThumbUp",requestParams,new LoadJsonHttpResponseHandler(this,new LoadDatahandler(){
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onSuccess(JSONObject data) {
                super.onSuccess(data);
                try {
                    String suc = data.get("suc").toString();
                    String msg = data.get("msg").toString();
                    if (suc.equals("y")){
                        likeIv.setLiked(true);
                        likeIv.setEnabled(false);
                        tv_dianzha_number.setText((Integer.parseInt(tv_dianzha_number.getText().toString())+1)+"");
                        ToastUtil.getInstant().show(msg);
                    }else {
                        ToastUtil.getInstant().show(msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error, String message) {
                super.onFailure(error, message);
            }
        }));
    }
    private  void addCollection(){
        RequestParams requestParams =new RequestParams();
        requestParams.put("userid",PreferenceUtils.getUserId());
        requestParams.put("collectid",teacherId);
        requestParams.put("type","2");
        HttpUtil.post(Constants.URL+"/addSelfCollectInfo",requestParams,new LoadJsonHttpResponseHandler(this,new LoadDatahandler(){
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onSuccess(JSONObject data) {
                super.onSuccess(data);
                try {
                    String suc = data.get("suc").toString();
                    String msg = data.get("msg").toString();
                    if (suc.equals("y")){
                        collectIv.setLiked(true);
                        collectIv.setEnabled(false);
                        tv_collecte_number.setText((Integer.parseInt(tv_collecte_number.getText().toString())+1)+"");
                        ToastUtil.getInstant().show(msg);
                    }else {
                        ToastUtil.getInstant().show(msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error, String message) {
                super.onFailure(error, message);
            }
        }));
    }

    @Override
    public void liked(LikeButton likeButton) {
        switch (likeButton.getId()){
            case R.id.iv_lecturer_collect:
                if(ApplicationEx.getInstance().isLogined())
                {
                    addCollection();
                } else
                {
                    collectIv.setLiked(false);
                    Intent intentToLogin = new Intent(LecturerDetailActivity.this,LoginActivity.class);
                    startActivityForResult(intentToLogin,1024);
                }
                break;

            case R.id.iv_lecturer_like:
                if(ApplicationEx.getInstance().isLogined())
                {
                    dianZhan();
                } else
                {
                    likeIv.setLiked(false);
                    Intent intentToLogin = new Intent(LecturerDetailActivity.this,LoginActivity.class);
                    startActivityForResult(intentToLogin,1024);
                }
                break;
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

        initData();

    }
    @Override
    public void unLiked(LikeButton likeButton) {
        likeButton.setEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("LecturerDetailActivity");
        initData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("LecturerDetailActivity");
    }
}


