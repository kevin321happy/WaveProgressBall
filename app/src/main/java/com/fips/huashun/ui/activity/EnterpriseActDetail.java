package com.fips.huashun.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.common.ConstantsCode;
import com.fips.huashun.modle.bean.ActivityDetailModel;
import com.fips.huashun.modle.bean.GridViewBean;
import com.fips.huashun.modle.bean.SignUpStateModel;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.ToastUtil;
import com.fips.huashun.ui.utils.Utils;
import com.fips.huashun.widgets.EnterActivityDialog;
import com.fips.huashun.widgets.MyGridView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.List;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by kevin on 2017/1/11. 企业活动详情
 */
public class EnterpriseActDetail extends BaseActivity {
  @Bind(R.id.na_bar_ent_activitydetail)
  NavigationBar mNaBarEntActivitydetail;
  @Bind(R.id.ent_activity_starttime)
  TextView mEntActivityStartTime;
  @Bind(R.id.ent_activity_endtime)
  TextView mEntActivityEndTime;
  @Bind(R.id.ent_actcivity_title)
  TextView mEntActivityTitle;
  @Bind(R.id.ent_activity_address)
  TextView mEntActivityAddress;
  @Bind(R.id.ent_activity_had_applyed_counts)
  TextView mEntActivityHadApplyedCounts;
  @Bind(R.id.ent_activity_ll)
  LinearLayout ent_activity_ll;
  @Bind(R.id.gv_ent_activity)
  MyGridView mGvEntActivity;
  @Bind(R.id.button_left_iv)
  ImageView mButtonLeftIv;
  @Bind(R.id.button_left)
  RelativeLayout mButtonLeft;
  @Bind(R.id.button_right)
  Button mButtonRight;
  @Bind(R.id.ll_button)
  LinearLayout mLlButton;
  @Bind(R.id.iv_user_icon1)
  ImageView iv_user_icon1;
  @Bind(R.id.iv_user_icon2)
  ImageView iv_user_icon2;
  @Bind(R.id.iv_user_icon3)
  ImageView iv_user_icon3;
  @Bind(R.id.apply_count)
  TextView apply_count;
  ImageView iv_detail;
  @Bind(R.id.ent_tv_detail)
  TextView mEntTvDetail;
  @Bind(R.id.ent_iv_detail)
  RoundedImageView mEntIvDetail;
  @Bind(R.id.ent_tv_leftnumbers)
  TextView mEntTvLeftnumbers;
  @Bind(R.id.ent_tv_overtime)
  TextView mEntTvOvertime;
  private Gson mGson;
  private String activityid;
  private ActivityDetailModel mActivityDetail;
  private ImageLoader mImageLoader;
  private int leftnum = 0;//剩余报名名额
  private boolean IS_SIGN_UP;
  private long currentTime;//当前时间
  private long startsignUpTime;//开始报名时间
  private long endsignUpTime;//结束报名时间
  private int mSign_up_total;
  private int mMaxnum;
  private ToastUtil mToastUtil;
  private WebView webview;
  private boolean ISSHOWREGISTERINFO;

  private GridViewBean[] mGridViewBeans = {new GridViewBean(R.drawable.signin_1, "活动签到")
      , new GridViewBean(R.drawable.learning_2, "在线学习"),
      new GridViewBean(R.drawable.research_3, "活动调研")
      , new GridViewBean(R.drawable.interact_4, "互动空间"),
      new GridViewBean(R.drawable.examine_5, "活动考核")
      , new GridViewBean(R.drawable.pk_6, "PK榜")};


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_entactivitydetail);
    ButterKnife.bind(this);
    activityid = getIntent().getStringExtra("activityid");
    mToastUtil = ToastUtil.getInstant();
    mGson = new Gson();
    currentTime = System.currentTimeMillis();//获取当前时间
    mImageLoader = ImageLoader.getInstance();
    initView();
    initData();
  }

  private void initData() {
    OkGo.post(Constants.LOCAL_GET_DETAIL)
        .cacheKey(ConstantsCode.CACHE_ENTDETAIL)
        .cacheMode(com.lzy.okgo.cache.CacheMode.REQUEST_FAILED_READ_CACHE)
        .params("activityid", activityid)
        .params("uid", PreferenceUtils.getUserId())
        .execute(new com.lzy.okgo.callback.StringCallback() {
          @Override
          public void onBefore(com.lzy.okgo.request.BaseRequest request) {
            super.onBefore(request);
            showLoadingDialog();
          }

          @Override
          public void onSuccess(String s, Call call, Response response) {
            mActivityDetail = mGson.fromJson(s, ActivityDetailModel.class);
            //更新界面
            UpDataUI(mActivityDetail);
          }

          @Override
          public void onError(Call call, Response response, Exception e) {
            super.onError(call, response, e);
            dimissLoadingDialog();
          }

          @Override
          public void onAfter(@Nullable String s, @Nullable Exception e) {
            super.onAfter(s, e);
            dimissLoadingDialog();
          }
        });
  }

  //更新界面
  private void UpDataUI(ActivityDetailModel activityDetail) {
    if (activityDetail == null) {
      return;
    }
    ISSHOWREGISTERINFO =
        activityDetail.getIsshowregisterinfo().equals(ConstantsCode.STRING_ONE);
    mEntActivityTitle.setText(activityDetail.getActivitytitle() + "");
    mEntActivityStartTime
        .setText(Utils.formatDateString(activityDetail.getActivitytime()) + "");//活動開始時間
    mEntActivityEndTime
        .setText("  " + Utils.formatDateString(activityDetail.getActivityendtime()));//活動結束時間
    mEntActivityAddress.setText(activityDetail.getActivityaddre()==null?"":activityDetail.getActivityaddre());
    mEntTvDetail.setText(activityDetail.getActivitycontent()==null?"":activityDetail.getActivitycontent());
    mImageLoader.displayImage(activityDetail.getImgpath(), mEntIvDetail);
    mEntTvOvertime.setText(activityDetail.getRegisterenddate()==null?"" :activityDetail.getRegisterenddate()+ "截止");
    try {
      mMaxnum = Integer.parseInt(activityDetail.getMaxnum());
      mSign_up_total = Integer.parseInt(activityDetail.getSign_up_total());
      if (mMaxnum >= mSign_up_total) {
        leftnum = mMaxnum - mSign_up_total;
      }
      mEntTvLeftnumbers.setText("剩余" + leftnum + "个名额");//剩余人数
      startsignUpTime = Utils.dateToStamp(activityDetail.getRegisterstartdate());
      endsignUpTime = Utils.dateToStamp(activityDetail.getRegisterenddate());
      if (activityDetail.getUsr_sign_up().equals(ConstantsCode.STRING_ONE)) {
        mButtonRight.setBackgroundColor(Color.parseColor("#999999"));
        mButtonRight.setText("已报名");
        mButtonRight.setEnabled(false);
      } else {
        //在未报名的时候,当还没到报名时间也不能点击报名
        if (currentTime - startsignUpTime < 0 || currentTime - endsignUpTime > 0) {
          mButtonRight.setBackgroundColor(Color.parseColor("#999999"));
          mButtonRight.setText("我要报名");
          mButtonRight.setEnabled(false);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    List<ActivityDetailModel.SignUpBean> signUpBeen = activityDetail.getSign_up();
    String signUpTotal = activityDetail.getSign_up_total();
    initApplyCount(signUpBeen, signUpTotal);
  }

  //报名人数信息
  private void initApplyCount(List<ActivityDetailModel.SignUpBean> signUpBeen, String signUpTotal) {
    int signUpBeens = signUpBeen.size();
    if (signUpBeens > 3) {
      signUpBeens = 3;
    }
    for (int i = signUpBeens; i > 0; i--) {
      if (i > 0) {
        apply_count.setVisibility(View.VISIBLE);
        apply_count.setText("" + signUpTotal);
      }
      if (i == 3) {
        iv_user_icon3.setVisibility(View.VISIBLE);
        mImageLoader.displayImage(signUpBeen.get(2).getHeadpath(), iv_user_icon3);
      }
      if (i == 2) {
        iv_user_icon2.setVisibility(View.VISIBLE);
        mImageLoader.displayImage(signUpBeen.get(1).getHeadpath(), iv_user_icon2);
      }
      if (i == 1) {
        iv_user_icon1.setVisibility(View.VISIBLE);
        mImageLoader.displayImage(signUpBeen.get(0).getHeadpath(), iv_user_icon1);
      }
    }
  }

  @Override
  protected void initView() {
    mNaBarEntActivitydetail.setTitle("活动详情");
    mNaBarEntActivitydetail.setLeftImage(R.drawable.course_reslt_back);
    mNaBarEntActivitydetail.setListener(new NavigationBar.NavigationListener() {
      @Override
      public void onButtonClick(int button) {
        if (button == NavigationBar.LEFT_VIEW) {
          finish();
        }
      }
    });
    mGvEntActivity.setAdapter(new MyGridViewAdapter());
    mGvEntActivity.setOnItemClickListener(mOnItemClickListener);
  }
  /**
   * 活动模块条目点击事件
   */
  private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
      Intent intent = null;
      switch (position) {
        case 0:
          //活动签到
          String issign = mActivityDetail.getIssign();
          if (issign.equals(ConstantsCode.STRING_ONE)) {
            intent = new Intent(EnterpriseActDetail.this, EnterpriseActSignActivity.class);
            intent.putExtra("activityid", activityid);
            startActivity(intent);
          } else {
            mToastUtil.show("当前模块暂未开放");
            return;
          }
          break;
        case 1:
          //在线学习
          if (mActivityDetail.getIsonline().equals(ConstantsCode.STRING_ONE)) {
            intent = new Intent(EnterpriseActDetail.this, StudyOnlineActivity.class);
            intent.putExtra("activityid", activityid);
            startActivity(intent);
          } else {
            mToastUtil.show("当前模块暂未开放");
          }
          break;
        case 2:
          //活动调研
          if (mActivityDetail.getIssurvey().equals(ConstantsCode.STRING_ONE)) {
            intent = new Intent(EnterpriseActDetail.this, ActResearchActivity.class);
            intent.putExtra("activityid", activityid);
            startActivity(intent);
          } else {
            mToastUtil.show("当前模块暂未开放");
            return;
          }
          break;
        case 3:
          //互动空间
          if (mActivityDetail.getIssetwords().equals(ConstantsCode.STRING_ZERO) && mActivityDetail
              .getIssettickling().equals(ConstantsCode.STRING_ZERO)) {
            mToastUtil.show("当前模块暂未开放");
          } else {
            intent = new Intent(EnterpriseActDetail.this, InteractiveSpaceActivity.class);
            intent.putExtra("activityid", activityid);
            intent.putExtra("leavewall", mActivityDetail.getIssetwords());
            intent.putExtra("feedback", mActivityDetail.getIssettickling());
            startActivity(intent);
          }
          break;
        case 4:
          //互动考核
          if (mActivityDetail.getIspaper().equals(ConstantsCode.STRING_ONE)) {
            //企业文化
            intent = new Intent(EnterpriseActDetail.this, WebviewActivity.class);
            intent.putExtra("key", 9);
            intent.putExtra("activityId", activityid);
            startActivity(intent);
          } else {
            mToastUtil.show("当前模块暂未开放");
          }
          break;
        case 5:
          //PK榜
          if (mActivityDetail.getIspaper().equals(ConstantsCode.STRING_ZERO) &&
              mActivityDetail.getIssetwords().equals(ConstantsCode.STRING_ZERO) && mActivityDetail
              .getIssettickling().equals(ConstantsCode.STRING_ZERO)) {
            mToastUtil.show("当前模块暂未开放");
          } else {
            intent = new Intent(EnterpriseActDetail.this, EntPkListActivity.class);
            intent.putExtra("activityid", activityid);
            startActivity(intent);
            break;
          }
      }
    }
  };

  //弹出报名对话框
  public void ShowApplyDialog() {
    EnterActivityDialog enterActivityDialog = new EnterActivityDialog(this);
    enterActivityDialog.setTitle("您即将参加活动，是否确认报名？");
    enterActivityDialog.setPositiveListener("确认报名", mOnPositiveListener);
    enterActivityDialog.show();
  }

  //点击确认报名
  private EnterActivityDialog.OnPositiveListener mOnPositiveListener = new EnterActivityDialog.OnPositiveListener() {
    @Override
    public void onClick(EnterActivityDialog dialog) {
      signup();
    }
  };

  //报名活动
  private void signup() {
    OkGo.post(Constants.LOCAL_SIGN_UP)
        .params("activityid", activityid)
        .params("uid", PreferenceUtils.getUserId())
        .execute(new com.lzy.okgo.callback.StringCallback() {
          @Override
          public void onSuccess(String s, Call call, Response response) {
            SignUpStateModel signUpState = mGson.fromJson(s, SignUpStateModel.class);
            if (signUpState.isStatus() == true) {
              initData();
            }
            ToastUtil.getInstant().show(signUpState.getInfo() + "");
          }
        });
  }

  @Override
  public boolean isSystemBarTranclucent() {
    return false;
  }

  @OnClick({R.id.ent_activity_ll, R.id.button_right})
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.ent_activity_ll:
        //跳转报名列表
        if (!ISSHOWREGISTERINFO) {
          mToastUtil.show("当前报名详情不允许查看");
          return;
        }
        Intent intent = new Intent(this, HadSignUpActivity.class);
        intent.putExtra("activityid", activityid);
        startActivity(intent);
        break;
      case R.id.button_right:
        ShowApplyDialog();
        break;
    }
  }

  @Override
  public void onStart() {
    super.onStart();
  }

  //设置就宫格
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
    public View getView(int position, View convertView, ViewGroup parent) {
      View view = View
          .inflate(EnterpriseActDetail.this, R.layout.enterprise_act_cottage_item, null);
      ImageView imagView = (ImageView) view.findViewById(R.id.iv_image);
      TextView textview = (TextView) view.findViewById(R.id.tv_text);
      imagView.setImageResource(mGridViewBeans[position].getImageRes());
      textview.setText(mGridViewBeans[position].getTitle());
      return view;
    }
  }
}
