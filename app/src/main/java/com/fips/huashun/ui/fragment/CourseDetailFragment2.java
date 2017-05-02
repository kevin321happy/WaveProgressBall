package com.fips.huashun.ui.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import com.fips.huashun.ApplicationEx;
import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.db.dao.SectionDownloadDao;
import com.fips.huashun.modle.bean.BeginEventType;
import com.fips.huashun.modle.bean.Coursrdetail;
import com.fips.huashun.modle.bean.CoursrdetailInfo;
import com.fips.huashun.modle.bean.CouseMuluInfo;
import com.fips.huashun.modle.bean.CouserMulu;
import com.fips.huashun.modle.dbbean.CourseSectionEntity;
import com.fips.huashun.modle.event.RecommendEvent;
import com.fips.huashun.modle.event.RefreshEvent;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.activity.CourseEvaluateActivity;
import com.fips.huashun.ui.activity.LoginActivity;
import com.fips.huashun.ui.activity.PdfActivity;
import com.fips.huashun.ui.activity.RechargeBeanActivity;
import com.fips.huashun.ui.activity.WebviewActivity;
import com.fips.huashun.ui.utils.AlertDialogUtils;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.ToastUtil;
import com.fips.huashun.ui.utils.Utils;
import com.fips.huashun.widgets.FlowLayout;
import com.fips.huashun.widgets.LoadingDialog;
import com.google.gson.Gson;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;
import de.greenrobot.event.EventBus;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * ���ﳵ
 *
 * @author hulin
 */
public class CourseDetailFragment2 extends Fragment implements View.OnClickListener {

  private View rootView;
  private Gson gson;
  private ImageView buyCloseIv;
  //	private ImageView iv_dianzan;
  private ImageView iv_image_advertise;
  private TextView tv_good_comment;
  private TextView tv_collecte_number;
  private TextView tv_dianzha_number;
  private TextView tv_coursename;
  private TextView tag1;
  private TextView tag2;
  private TextView tv_bean;
  private TextView tv_jianjie;
  private TextView tv_jifen;
  private RatingBar ra_bar;
  private TextView tv_content;
  private TextView tv_jindu;
  private TextView tv_time;
  private TextView buyCourseBtn;
  private String courseId;
  private TextView tv_userName;
  private CoursrdetailInfo coursrdetailInfo;
  // 弹出框的组件
  private Dialog dialog;
  private TextView courseNameTv, coursePriceTv, beanPointTv, needPointTv;
  // 充值,购买课程
  private Button rechargeBtn;
  // 用户当前魔豆
  private int bean_point;
  private Button bt_buy_course;
  private LoadingDialog loadingDialog;
  private ScrollView scrollView;
  private LikeButton iv_dianzan;
  private LikeButton iv_collection;
  private LinearLayout ll_comment;
  private TextView tv_no_comment;
  private String type = "";
  private CouserMulu couseMulus;
  private String welltoken;
  private boolean isEnterpriseCourse;//是否是企业课程
  //private Boolean isBought=false;//是否购买了
  /**
   * 保存上一次点击时间
   */
  private SparseArray<Long> lastClickTimes;
  private String mReceivecourseid;
  private SectionDownloadDao mSectionDownloadDao;


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    if (rootView == null) {
      rootView = inflater.inflate(R.layout.fragment_cart, container, false);
      EventBus.getDefault().register(this);
    }
    ViewGroup parent = (ViewGroup) rootView.getParent();
    if (parent != null) {
      parent.removeView(rootView);
    }
    return rootView;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    gson = new Gson();
    initView();
    lastClickTimes = new SparseArray<Long>();
    loadingDialog = new LoadingDialog(getActivity());
    if (mReceivecourseid != null) {
      courseId = mReceivecourseid;
    } else {
      courseId = getActivity().getIntent().getStringExtra("courseId");
      String data = getActivity().getIntent().getDataString();
      if (null == data || TextUtils.isEmpty(data)) {
        courseId = getActivity().getIntent().getStringExtra("courseId");
      } else {
        String[] dataString = data.split("=");
        courseId = dataString[1].toString().trim();
      }
    }
    initData();
    getCoursection();
  }

  private void initView() {
    // isEnterpriseCourse= SharePreferenceUtil.getBooleanValue(courseId,false);
    rootView.findViewById(R.id.tv_lookall).setOnClickListener(this);
//		 rootView.findViewById(R.id.ll_collection).setOnClickListener(this);

    scrollView = (ScrollView) rootView.findViewById(R.id.scrollView);
    tv_coursename = (TextView) rootView.findViewById(R.id.tv_coursename);
    tag1 = (TextView) rootView.findViewById(R.id.tag1);
    tag2 = (TextView) rootView.findViewById(R.id.tag2);
    tv_bean = (TextView) rootView.findViewById(R.id.tv_bean);
    tv_dianzha_number = (TextView) rootView.findViewById(R.id.tv_dianzha_number);
    tv_collecte_number = (TextView) rootView.findViewById(R.id.tv_collecte_number);
    tv_jianjie = (TextView) rootView.findViewById(R.id.tv_jianjie);
    tv_jifen = (TextView) rootView.findViewById(R.id.tv_jifen);

    tv_good_comment = (TextView) rootView.findViewById(R.id.tv_good_comment);
    tv_no_comment = (TextView) rootView.findViewById(R.id.tv_no_comment);

    iv_collection = (LikeButton) rootView.findViewById(R.id.iv_collection);
    //评价
    iv_image_advertise = (ImageView) rootView.findViewById(R.id.iv_image_advertise);
    ll_comment = (LinearLayout) rootView.findViewById(R.id.ll_comment);
    tv_userName = (TextView) rootView.findViewById(R.id.tv_userName);
    ra_bar = (RatingBar) rootView.findViewById(R.id.ra_bar);
    tv_content = (TextView) rootView.findViewById(R.id.tv_content);
    tv_jindu = (TextView) rootView.findViewById(R.id.tv_jindu);
    tv_time = (TextView) rootView.findViewById(R.id.tv_time);
    iv_dianzan = (LikeButton) rootView.findViewById(R.id.iv_dianzan);

    buyCourseBtn = (TextView) rootView.findViewById(R.id.bt_buy);
    buyCourseBtn.setOnClickListener(this);
  }

  private void initData() {
    RequestParams requestParams = new RequestParams();
    requestParams.put("userid", PreferenceUtils.getUserId());
    requestParams.put("courseid", courseId);
    HttpUtil.post(Constants.CHECKCOURSEBASE, requestParams,
        new LoadJsonHttpResponseHandler(getActivity(), new LoadDatahandler() {
          @Override
          public void onStart() {
            super.onStart();
            loadingDialog.show();
          }

          @Override
          public void onSuccess(JSONObject data) {
            super.onSuccess(data);
            loadingDialog.dismiss();
            scrollView.setVisibility(View.VISIBLE);
            try {
              buyCourseBtn.setVisibility(View.VISIBLE);
              coursrdetailInfo = gson.fromJson(data.getString("data"), CoursrdetailInfo.class);
              String teacherid = coursrdetailInfo.getCoursrdetail().getTeacherid();
              if (!TextUtils.isEmpty(teacherid)) {
                isEnterpriseCourse = Integer.parseInt(teacherid) <= 0;
              } else {
                isEnterpriseCourse = true;
              }
              if (coursrdetailInfo.getIscollect().equals("1")) {
                iv_collection.setLiked(true);
                iv_collection.setEnabled(false);
              } else {
                iv_collection.setOnLikeListener(new OnLikeListener() {
                  @Override
                  public void liked(LikeButton likeButton) {
                    if (ApplicationEx.getInstance().isLogined()) {
                      addCollection();
                    } else {
                      iv_collection.setLiked(false);
                      Intent intent = new Intent(getActivity(), LoginActivity.class);
                      startActivityForResult(intent, 1024);
                    }
                  }

                  @Override
                  public void unLiked(LikeButton likeButton) {
                    likeButton.setEnabled(false);
                  }
                });
              }
              if (coursrdetailInfo.getIsriokin().equals("1")) {
                iv_dianzan.setLiked(true);
                iv_dianzan.setEnabled(false);
              } else {
                iv_dianzan.setOnLikeListener(new OnLikeListener() {
                  @Override
                  public void liked(LikeButton likeButton) {
                    if (ApplicationEx.getInstance().isLogined()) {
                      dianZhan();
                    } else {
                      iv_dianzan.setLiked(false);
                      Intent intent = new Intent(getActivity(), LoginActivity.class);
                      startActivityForResult(intent, 1024);
                    }
                  }

                  @Override
                  public void unLiked(LikeButton likeButton) {
                    likeButton.setEnabled(false);
                    ToastUtil.getInstant().show("请勿重复点赞");
                  }
                });
              }
              //TODO
              // buyCourseBtn.setText("开始学习");
              if ("0".equals(coursrdetailInfo.getBuytype()) && isEnterpriseCourse == false) {
                type = "0";//同时不是企业的课程菜显示购买
                buyCourseBtn.setText("购买课程");
              } else {
                type = "1";
                buyCourseBtn.setText("开始学习");
              }
              if (coursrdetailInfo.getCoursrdetail() != null) {
                setData(coursrdetailInfo);
              }

              EventBus.getDefault().post(new Coursrdetail());
            } catch (Exception e) {
              e.printStackTrace();
              loadingDialog.dismiss();
            }
          }

          @Override
          public void onFailure(String error, String message) {
            super.onFailure(error, message);
            loadingDialog.dismiss();
          }
        }));
  }

  public void onEventMainThread(RecommendEvent event) {
    mReceivecourseid = event.getCourseid();
  }

  @Override
  public void onClick(View v) {
    Intent intent = null;
    switch (v.getId()) {
      case R.id.ll_collection: //收藏
        break;
      case R.id.tv_lookall: //查看全部
        intent = new Intent(getActivity(), CourseEvaluateActivity.class);
        intent.putExtra("courseId", courseId);
        startActivityForResult(intent, 1024);
        break;
      case R.id.ll_dianzan:

        break;
      case R.id.bt_buy:
        if (checkClick(R.id.bt_buy)) {
          if (ApplicationEx.getInstance().isLogined()) {
            //TODO
            if (type.equals("0") && isEnterpriseCourse == false) {
              //不是企业课程
              searchBeanPoint();
            } else if (type.equals("0") && isEnterpriseCourse == true) {
              //请求购买接口
              startCourse();
              buyCourseById();
            } else if (type.equals("1")) {
              startCourse();
            }
          } else {
            intent = new Intent(getActivity(), LoginActivity.class);
            startActivityForResult(intent, 1024);
          }
        }
        break;
    }
  }

  //收藏
  private void addCollection() {
    RequestParams requestParams = new RequestParams();
    requestParams.put("userid", PreferenceUtils.getUserId());
    requestParams.put("collectid", courseId);
    requestParams.put("type", "1");
    HttpUtil.post(Constants.URL + "/addSelfCollectInfo", requestParams,
        new LoadJsonHttpResponseHandler(getActivity(), new LoadDatahandler() {
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
              if (suc.equals("y")) {
                iv_collection.setLiked(true);
                iv_collection.setEnabled(false);
                tv_collecte_number.setText(
                    (Integer.parseInt(coursrdetailInfo.getCoursrdetail().getCollection()) + 1)
                        + "");

              }
              ToastUtil.getInstant().show(msg);
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

  private void dianZhan() {
    RequestParams requestParams = new RequestParams();
    requestParams.put("userid", PreferenceUtils.getUserId());
    requestParams.put("thumbid", courseId);
    requestParams.put("type", "1");
    HttpUtil.post(Constants.URL + "/teachAndCourseThumbUp", requestParams,
        new LoadJsonHttpResponseHandler(getActivity(), new LoadDatahandler() {
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
              if (suc.equals("y")) {
                iv_dianzan.setLiked(true);
                iv_dianzan.setEnabled(false);
                tv_dianzha_number.setText(
                    (Integer.parseInt(coursrdetailInfo.getCoursrdetail().getRiokin()) + 1) + "");
              }
              ToastUtil.getInstant().show(msg);
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

  private void setData(CoursrdetailInfo coursrdetailInfo) {
    tv_coursename.setText(coursrdetailInfo.getCoursrdetail().getCourseName());
    if (!TextUtils.isEmpty(coursrdetailInfo.getCoursrdetail().getTip())) {
      String[] str = Utils.getStr(coursrdetailInfo.getCoursrdetail().getTip());
      if (!TextUtils.isEmpty(str[0])) {
        tag1.setText(str[0].toString());
        tag1.setVisibility(View.VISIBLE);
      }
      if (!TextUtils.isEmpty(str[1])) {
        tag2.setText(str[1]);
        tag2.setVisibility(View.VISIBLE);

      }
    }
    tv_bean.setText(coursrdetailInfo.getCoursrdetail().getCoursePrice() + "魔豆");
    tv_dianzha_number.setText(coursrdetailInfo.getCoursrdetail().getRiokin() + "");
    tv_collecte_number.setText(coursrdetailInfo.getCoursrdetail().getCollection() + "");
    tv_jianjie.setText(coursrdetailInfo.getCoursrdetail().getCourseintro());
    tv_jifen.setText(coursrdetailInfo.getCoursrdetail().getIntegral() + "积分");
    // 设置课程好评率
    String welltoken = coursrdetailInfo.getWelltoken();
    if (TextUtils.isEmpty(welltoken)) {
      tv_good_comment.setText("0");
    } else {

      tv_good_comment.setText(welltoken + "");
    }
    ImageLoader.getInstance()
        .displayImage(Constants.IMG + coursrdetailInfo.getComments().getUserimg(),
            iv_image_advertise, ApplicationEx.head_options);
    String score = coursrdetailInfo.getComments().getScore();
    if (TextUtils.isEmpty(score)) {
      ra_bar.setRating(0);
    } else {
      ra_bar.setRating(Integer.valueOf(score));
    }
    String sturate = coursrdetailInfo.getComments().getSturate();
    Log.e("sturate", sturate);
    if (TextUtils.isEmpty(sturate)) {
      tv_jindu.setText("学习进度:0%");
    } else {
      tv_jindu.setText("学习进度:" + sturate + "%");
    }
    if (TextUtils.isEmpty(coursrdetailInfo.getComments().getContext())) {
      ll_comment.setVisibility(View.GONE);
      tv_no_comment.setVisibility(View.VISIBLE);
    } else {
      ll_comment.setVisibility(View.VISIBLE);
      tv_no_comment.setVisibility(View.GONE);
      tv_content.setText(coursrdetailInfo.getComments().getContext());
      tv_time.setText(coursrdetailInfo.getComments().getCreate_date());
      tv_userName.setText(coursrdetailInfo.getComments().getUsername());
    }

  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 1024) {
      initData();
    }
  }

  /**
   * 功能：报名购买
   */
  private void showDialog() {
    View view = getActivity().getLayoutInflater().inflate(R.layout.buy_course_dialog, null);
    dialog = new Dialog(getActivity(), R.style.activityDialog);
    dialog.setContentView(view, new FlowLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT));
    Window window = dialog.getWindow();
    // 设置显示动画
    window.setWindowAnimations(R.style.main_menu_animstyle);
    WindowManager.LayoutParams wl = window.getAttributes();
    wl.x = 0;
    wl.y = getActivity().getWindowManager().getDefaultDisplay().getHeight();
    // 以下这两句是为了保证按钮可以水平满屏
    wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
    wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

    // 设置显示位置
    dialog.onWindowAttributesChanged(wl);
    // 设置点击外围解散
    dialog.setCanceledOnTouchOutside(true);
    dialog.show();
    courseNameTv = (TextView) window.findViewById(R.id.tv_course_name);
    coursePriceTv = (TextView) window.findViewById(R.id.tv_course_price);
    beanPointTv = (TextView) window.findViewById(R.id.tv_bean_point);
    needPointTv = (TextView) window.findViewById(R.id.tv_need_point);
    buyCloseIv = (ImageView) window.findViewById(R.id.iv_buy_close);

    rechargeBtn = (Button) window.findViewById(R.id.btn_recharge);
    bt_buy_course = (Button) window.findViewById(R.id.bt_buy_course);
    // 课程名称
    courseNameTv.setText(coursrdetailInfo.getCoursrdetail().getCourseName());
    beanPointTv.setText(bean_point + ".00魔豆");
    if (TextUtils.isEmpty(coursrdetailInfo.getCoursrdetail().getCoursePrice())) {
      coursePriceTv.setText("0.00魔豆");
      needPointTv.setText("0.00魔豆");
      rechargeBtn.setVisibility(View.INVISIBLE);
    } else {
      coursePriceTv.setText(coursrdetailInfo.getCoursrdetail().getCoursePrice() + "魔豆");
      int needPoint = bean_point - ((int) Double
          .parseDouble(coursrdetailInfo.getCoursrdetail().getCoursePrice()));
      if (needPoint < 0) {// 魔豆不足
        needPointTv.setText(needPoint + ".00魔豆");
        rechargeBtn.setVisibility(View.VISIBLE);

      } else {
        needPointTv.setText("0.00魔豆");
        rechargeBtn.setVisibility(View.INVISIBLE);
      }
    }
    rechargeBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {// 跳转进入魔豆充值页面
        Intent intentToRecharge = new Intent(getActivity(), RechargeBeanActivity.class);
        intentToRecharge.putExtra("beanPoint", String.valueOf(bean_point));
        startActivity(intentToRecharge);
        dialog.dismiss();
      }
    });
    bt_buy_course.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (!TextUtils.isEmpty(coursrdetailInfo.getCoursrdetail().getCoursePrice())) {
          if (Double.valueOf(coursrdetailInfo.getCoursrdetail().getCoursePrice()) == 0) {
            dialog.dismiss();
            buyCourseById();
          } else {
            showBuyCourseDialog();
          }
        }
      }
    });
    buyCloseIv.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dialog.dismiss();
      }
    });
  }

  /**
   * 功能：购买课程前，先查询用户当前魔豆
   */
  private void searchBeanPoint() {
    if (ApplicationEx.getInstance().isLogined()) {// 用户已登录
      RequestParams requestParams = new RequestParams();
      requestParams.put("userid", PreferenceUtils.getUserId());
      HttpUtil.post(Constants.SEARCH_BEANPOINT_URL, requestParams,
          new LoadJsonHttpResponseHandler(getActivity(), new LoadDatahandler() {
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
                if ("y".equals(suc)) {
                  bean_point = (int) data.get("bean_point");
                  showDialog();
                } else {
                  ToastUtil.getInstant().show("查询当前魔豆失败，请重试！");
                }
              } catch (JSONException e) {
                e.printStackTrace();
                ToastUtil.getInstant().show("查询当前魔豆失败，请重试！");
              }
            }

            @Override
            public void onFailure(String error, String message) {
              super.onFailure(error, message);

              ToastUtil.getInstant().show("查询当前魔豆失败，请重试！");
            }
          }));
    } else {// 用户没有登录，跳到登录页面
      Intent intentToLogin = new Intent(getActivity(), LoginActivity.class);
      startActivity(intentToLogin);
    }
  }

  /**
   * 功能：确认弹出框
   */
  private void showBuyCourseDialog() {
    AlertDialogUtils.showTowBtnDialog(getActivity(), "是否确认购买?", "取消", "确认",
        new AlertDialogUtils.DialogClickInter() {
          @Override
          public void leftClick(android.app.AlertDialog dialog) {
            dialog.cancel();
          }

          @Override
          public void rightClick(android.app.AlertDialog dialog) {
            dialog.cancel();
            buyCourseById();
          }
        });
  }

  /**
   * 功能：购买课程
   */
  private void buyCourseById() {
    if (ApplicationEx.getInstance().isLogined()) {// 用户已登录
      RequestParams requestParams = new RequestParams();
      requestParams.put("courseid", courseId);
      requestParams.put("userid", PreferenceUtils.getUserId());
      requestParams.put("coursePrice", coursrdetailInfo.getCoursrdetail().getCoursePrice());
      requestParams.put("coursename", coursrdetailInfo.getCoursrdetail().getCourseName());
      HttpUtil.post(Constants.BUY_0COURSE_URL, requestParams,
          new LoadJsonHttpResponseHandler(getActivity(), new LoadDatahandler() {
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
                ToastUtil.getInstant().show(msg);
                if (null != dialog) {
                  dialog.cancel();
                  EventBus.getDefault().post(new RefreshEvent() {
                  });
                }
                if ("y".equals(suc)) {
                  //购买成功了,友盟统计
                  Map map = new HashMap();
                  map.put("courseid", courseId);
                  map.put("coursePrice", coursrdetailInfo.getCoursrdetail().getCoursePrice());
                  map.put("userid", PreferenceUtils.getUserId());
                  map.put("coursename", coursrdetailInfo.getCoursrdetail().getCourseName());
                  MobclickAgent.onEvent(getActivity(), "BuyCourse", map);
                  type = "1";
                  buyCourseBtn.setText("开始学习");
                  //发送evenbus
                  BeginEventType beginEventType = new BeginEventType();
                  beginEventType.setSessonid(type);
                  beginEventType
                      .setCourseImage(coursrdetailInfo.getCoursrdetail().getCourseImage());
                  EventBus.getDefault().post(beginEventType);
                }
              } catch (JSONException e) {
                e.printStackTrace();
                ToastUtil.getInstant().show("购买课程异常，请重试！");
              }
            }

            @Override
            public void onFailure(String error, String message) {
              super.onFailure(error, message);
              ToastUtil.getInstant().show("购买课程失败，请重试！");
            }
          }));
    } else {// 用户没有登录，跳到登录页面
      Intent intentToLogin = new Intent(getActivity(), LoginActivity.class);
      startActivityForResult(intentToLogin, 1024);
    }
  }

  //获取课程目录数据
  private void getCoursection() {
    RequestParams requestParams = new RequestParams();
    requestParams.put("userid", PreferenceUtils.getUserId());
    requestParams.put("courseid", courseId);
    HttpUtil.post(Constants.CHECKCOURSECTION, requestParams,
        new LoadJsonHttpResponseHandler(getActivity(), new LoadDatahandler() {
          @Override
          public void onStart() {
            super.onStart();
          }

          @Override
          public void onSuccess(JSONObject data) {
            super.onSuccess(data);
            Log.e("111", data.toString());
            try {
              String suc = data.get("suc").toString();
              String msg = data.get("msg").toString();
              if (suc.equals("y")) {
                couseMulus = gson.fromJson(data.getString("data").toString(), CouserMulu.class);
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

  //开始学习
  private void startCourse() {
    if (mSectionDownloadDao == null) {
      mSectionDownloadDao = new SectionDownloadDao(getContext());
    }
    if (couseMulus != null && couseMulus.getData().size() > 0) {
      CouseMuluInfo couseMuluInfo = couseMulus.getData().get(0);
      String sessonid = couseMuluInfo.getSessonid();
      addStudyInfo(couseMuluInfo.getSessonid());
      if ("1".equals(couseMuluInfo.getSessiontype())) {// 文档
        Intent intent = new Intent(getActivity(), WebviewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("activityId", courseId);
        intent.putExtra("sessoinid", couseMuluInfo.getSessonid());
        intent.putExtra("key", 15);//课程章节文档
        startActivity(intent);
      } else if ("2".equals(couseMuluInfo.getSessiontype())) {// 视频
        //先查询是否已经下载
        String vedioUrl = getsectionUrl(couseMuluInfo);
        BeginEventType beginEventType = new BeginEventType();
        beginEventType.setCourseId(courseId);
        beginEventType.setSessonid(couseMuluInfo.getSessonid());
        beginEventType.setCouseMulusName(couseMuluInfo.getCourseName());
        beginEventType.setVideoUrl(vedioUrl);
        EventBus.getDefault().post(beginEventType);
      } else if ("3".equals(couseMuluInfo.getSessiontype())) {
        //pdf 文件
        String pdfUrl = null;
        String type;
        CourseSectionEntity sectionEntity = mSectionDownloadDao
            .querySectionBySectionId(couseMuluInfo.getSessonid());
        if (sectionEntity != null && sectionEntity.getState() == 2) {
          pdfUrl = sectionEntity.getLocalpath();
          type="3";
        } else {
          pdfUrl = Constants.IMG_URL+couseMuluInfo.getCoursedoc();
          type="0";
        }
        Intent intent = new Intent(getActivity(), PdfActivity.class);
        intent.putExtra("courseid", courseId);
        intent.putExtra("type", type);
        intent.putExtra("pdfurl", pdfUrl);
        startActivity(intent);
      }
    } else {
      ToastUtil.getInstant().show("该课程还没有目录");
    }
  }
  //获取视频/PDF的网络路径
  private String getsectionUrl(CouseMuluInfo CouseMuluInfo) {
    String vedioUrl = null;
    CourseSectionEntity sectionEntity = mSectionDownloadDao
        .querySectionBySectionId(CouseMuluInfo.getSessonid());
    if (sectionEntity != null && sectionEntity.getState() == 2) {
      //已下载
      vedioUrl = sectionEntity.getLocalpath();
    } else {
      vedioUrl = CouseMuluInfo.getSessiontype().equals("2") ? CouseMuluInfo.getStanquality() :
          CouseMuluInfo.getDocUrl();
    }
    return vedioUrl;
  }

  /**
   * 功能：添加学习记录
   *
   * @param sessonid 章节ID
   */
  private void addStudyInfo(String sessonid) {
    RequestParams requestParams = new RequestParams();
    requestParams.put("userid", PreferenceUtils.getUserId());
    requestParams.put("courseid", courseId);
    requestParams.put("sessonid", sessonid);
    HttpUtil.post(Constants.ADDSTUDYINFO, requestParams,
        new LoadJsonHttpResponseHandler(getActivity(), new LoadDatahandler() {
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

  protected boolean checkClick(int id) {
    Long lastTime = lastClickTimes.get(id);
    Long thisTime = System.currentTimeMillis();
    lastClickTimes.put(id, thisTime);
    return !(lastTime != null && thisTime - lastTime < 800);
  }

  @Override
  public void onResume() {
    super.onResume();
    MobclickAgent.onPageStart("CourseDetailFragment2");
    MobclickAgent.onResume(getActivity());
  }

  @Override
  public void onPause() {
    super.onPause();
    MobclickAgent.onPageEnd("CourseDetailFragment2");
    MobclickAgent.onPause(getActivity());
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    EventBus.getDefault().unregister(this);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
  }

}
