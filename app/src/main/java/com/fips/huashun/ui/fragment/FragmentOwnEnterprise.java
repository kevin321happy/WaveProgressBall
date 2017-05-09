package com.fips.huashun.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.fips.huashun.ApplicationEx;
import com.fips.huashun.R;
import com.fips.huashun.common.ACache;
import com.fips.huashun.common.CacheConstans;
import com.fips.huashun.common.Constants;
import com.fips.huashun.modle.bean.CourseInfo;
import com.fips.huashun.modle.bean.EnterpriseData;
import com.fips.huashun.modle.bean.EnterpriseInfo;
import com.fips.huashun.modle.bean.GridViewBean;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.activity.CourseDetailActivity;
import com.fips.huashun.ui.activity.EntCommunicateActivity;
import com.fips.huashun.ui.activity.EntMyCourseActivity;
import com.fips.huashun.ui.activity.EntOrganizationActivity;
import com.fips.huashun.ui.activity.EnterpriseActList;
import com.fips.huashun.ui.activity.EnterpriseCourseActivity;
import com.fips.huashun.ui.activity.EnterpriseNoticeActivity;
import com.fips.huashun.ui.activity.EnterprisePkActivity;
import com.fips.huashun.ui.activity.TaskActivity;
import com.fips.huashun.ui.activity.WebviewActivity;
import com.fips.huashun.ui.adapter.AllCourseAdapter;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.SPUtils;
import com.fips.huashun.ui.utils.ToastUtil;
import com.fips.huashun.widgets.AutoScrollTextView;
import com.fips.huashun.widgets.CircleImageView;
import com.fips.huashun.widgets.LoadingDialog;
import com.fips.huashun.widgets.MyGridView;
import com.fips.huashun.widgets.NoScrollListView;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 功能：企业信息展示（企业最新公告，企业名称，企业LOG，企业课程）
 *
 * @author 张柳 时间：2016年9月5日10:32:13
 */
public class FragmentOwnEnterprise extends Fragment implements OnClickListener {

  private View rootView, emptyView;
  private ScrollView allScrollView;
  private LoadingDialog loadingDialog;
  // 企业LOGO
  private CircleImageView headIv;
  // 企业课程 查看更多
  private RelativeLayout moreCourseLayout;
  // 企业名称,企业最新公告
  private TextView nameTv, noticeTv;
  // 查看企业全部公告
  private LinearLayout allNoticeLayout;
  // 企业文化，组织架构，企业部门，PK榜，我的课程，企业天地，学习地图，任务，能力模型放在MyGridView中
  private MyGridView myGridView;
  // 企业课程Listview
  private NoScrollListView mListView;
  private GridViewBean[] mGridViewBeans = {new GridViewBean(R.drawable.coggage_one, "企业文化")
      , new GridViewBean(R.drawable.coggage_two, "组织架构"),
      new GridViewBean(R.drawable.coggage_three, "企业通讯")
      , new GridViewBean(R.drawable.coggage_four, "PK榜"),
      new GridViewBean(R.drawable.coggage_five, "我的课程")
      , new GridViewBean(R.drawable.coggage_six, "企业活动"),
      new GridViewBean(R.drawable.coggage_seven, "学习地图")
      , new GridViewBean(R.drawable.coggage_eight, "任务"),
      new GridViewBean(R.drawable.coggage_nine, "能力模型")};
  private AllCourseAdapter allCourseAdapter;
  private String enterpriseId;
  // 0 游客体验 1 企业用户
  public String type;
  private EnterpriseInfo entinfo;
  private List<CourseInfo> entCourseList;
  private Gson gson;
  private AutoScrollTextView noticeTv1;
  private NavigationBar navigationBar;
  private ImageView iv_imaeBG;
  private ACache mACache;


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    if (rootView == null) {
      rootView = inflater.inflate(R.layout.fragment_ownenterprise, container, false);
    }
    ViewGroup parent = (ViewGroup) rootView.getParent();
    if (parent != null) {
      parent.removeView(rootView);
    }
    return rootView;
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    initActivity();
    //初始化缓存
    mACache = ACache.get(getActivity());
    initListener();
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    gson = new Gson();
    loadingDialog = new LoadingDialog(getActivity());
    Log.e("onViewCreated", "onViewCreated() type=" + type);
    initView();
    initData();
  }

  /**
   * 功能：初始化
   */
  private void initView() {
    navigationBar = (NavigationBar) rootView.findViewById(R.id.nb_enterprise_title);
    navigationBar.setTitle("企业");
    emptyView = rootView.findViewById(R.id.view_empty);
    emptyView.setVisibility(View.VISIBLE);
    allScrollView = (ScrollView) rootView.findViewById(R.id.sv_enterprise_info);
    allScrollView.setVisibility(View.GONE);
    if ("0".equals(type)) {
      navigationBar.setLeftImage(R.drawable.course_reslt_back);
    } else {
      navigationBar.setHideLeftButton();
    }
    navigationBar.setListener(new NavigationBar.NavigationListener() {
      @Override
      public void onButtonClick(int button) {
        if (button == NavigationBar.LEFT_VIEW) {
          // 返回到企业注册页面
          Intent mIntent = new Intent("com.fipx.huashun.enterprise");
          // 0 游客体验 1 申请验证企业成功 2 返回到企业注册页面
          mIntent.putExtra("type", "2");
          //发送广播
          getActivity().sendBroadcast(mIntent);
        }
      }
    });
    headIv = (CircleImageView) rootView.findViewById(R.id.iv_enterprise_head);
    iv_imaeBG = (ImageView) rootView.findViewById(R.id.iv_imaeBG);
    nameTv = (TextView) rootView.findViewById(R.id.tv_enterprise_name);
    noticeTv1 = (AutoScrollTextView) rootView.findViewById(R.id.tv_enterprise_notice);
    noticeTv1.init(getActivity().getWindowManager());
    noticeTv1.startScroll();
    noticeTv1.setOnClickListener(this);
    allNoticeLayout = (LinearLayout) rootView.findViewById(R.id.ll_enterprise_notice);
    allNoticeLayout.setOnClickListener(this);
    moreCourseLayout = (RelativeLayout) rootView.findViewById(R.id.rl_enterprise_more_cousre);
    moreCourseLayout.setOnClickListener(this);
    myGridView = (MyGridView) rootView.findViewById(R.id.gv_enterprise_info);
    mListView = (NoScrollListView) rootView.findViewById(R.id.lv_enterprise_info);
    allCourseAdapter = new AllCourseAdapter(getActivity());
    mListView.setAdapter(allCourseAdapter);
    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), CourseDetailActivity.class);
        intent.putExtra("courseId", allCourseAdapter.getItem(position).getCourseId());
        intent.putExtra("welltoken", allCourseAdapter.getItem(position).getWelltoken());
        startActivity(intent);
      }
    });
    myGridView = (MyGridView) rootView.findViewById(R.id.gv_enterprise_info);
    myGridView.setAdapter(new MyGridViewAdapter());
    myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = null;
        switch (position) {
          case 0:
            // 企业文化
            intent = new Intent(getActivity(), WebviewActivity.class);
            intent.putExtra("key", 0);
            intent.putExtra("entid", enterpriseId);
            startActivity(intent);

            break;
          case 1:
            // 组织架构
//            intent = new Intent(getActivity(), WebviewActivity.class);
//            intent.putExtra("key", 1);
//            intent.putExtra("entid", enterpriseId);
//            startActivity(intent);
            startActivity(new Intent(getActivity(), EntOrganizationActivity.class));
            break;
          case 2:
            //Pk榜
            // 企业部门
            //跳转到企业通讯界面
            intent=new Intent(getActivity(),EntCommunicateActivity.class);
//            intent = new Intent(getActivity(), WebviewActivity.class);
//            intent.putExtra("key", 2);
//            intent.putExtra("entid", enterpriseId);
//            intent.putExtra("activityId", entinfo.getDeptid());//depid
            startActivity(intent);
            break;
          case 3:
            // PK榜
            intent = new Intent(getActivity(), EnterprisePkActivity.class);
            intent.putExtra("enterpriseId", enterpriseId);
            startActivity(intent);
            break;
          case 4:
            // 我的课程
            intent = new Intent(getActivity(), EntMyCourseActivity.class);
            intent.putExtra("enterpriseId", enterpriseId);
            startActivity(intent);
            break;
          case 5:
            // 企业活动
            intent = new Intent(getActivity(), EnterpriseActList.class);
            intent.putExtra("enterpriseId", enterpriseId);
            startActivity(intent);
            break;
          case 6:
            // 学习地图
            intent = new Intent(getActivity(), WebviewActivity.class);
            intent.putExtra("entid", enterpriseId);
            intent.putExtra("key", 6);
            startActivity(intent);
            break;
          case 7:
            // 任务
            intent = new Intent(getActivity(), TaskActivity.class);
            intent.putExtra("enterpriseId", enterpriseId);
            startActivity(intent);
            break;
          case 8:
            // 能力模型
            intent = new Intent(getActivity(), WebviewActivity.class);
            intent.putExtra("key", 8);
            intent.putExtra("enterpriseId", enterpriseId);
            startActivity(intent);
            break;
        }
      }
    });
  }

  /**
   * 功能：获取企业信息
   */
  private void initData() {

    RequestParams requestParams = new RequestParams();

    requestParams.put("type", type);
    requestParams.put("userid", PreferenceUtils.getUserId());
    HttpUtil.getClient().setTimeout(2000);
    HttpUtil.post(Constants.STUDY_URL, requestParams,
        new LoadJsonHttpResponseHandler(getActivity(), new LoadDatahandler() {
          @Override
          public void onStart() {
            super.onStart();
            JSONObject mACacheAsJSONObject = mACache
                .getAsJSONObject(CacheConstans.OWNENTERPISE_INFO_JSON);
            if (mACacheAsJSONObject != null) {
              showOwnEnterprise(mACacheAsJSONObject);
            }
          }

          @Override
          public void onSuccess(JSONObject data) {
            super.onSuccess(data);
            mACache.put(CacheConstans.OWNENTERPISE_INFO_JSON, data, ACache.TIME_DAY * 2);
            showOwnEnterprise(data);
          }

          @Override
          public void onFailure(String error, String message) {
            super.onFailure(error, message);
            dimissLoadingDialog();
            JSONObject mACacheAsJSONObject = mACache
                .getAsJSONObject(CacheConstans.OWNENTERPISE_INFO_JSON);
            if (mACacheAsJSONObject != null) {
              showOwnEnterprise(mACacheAsJSONObject);
            }
          }
        }));
  }

  /**
   * 显示有企业的信息
   */
  private void showOwnEnterprise(JSONObject data) {
    try {
      String suc = data.get("suc").toString();
      String msg = data.get("msg").toString();
      if ("y".equals(suc)) {
        emptyView.setVisibility(View.GONE);
        allScrollView.setVisibility(View.VISIBLE);
        EnterpriseData enterpriseData = gson.fromJson(data.getString("data"), EnterpriseData.class);
        entinfo = enterpriseData.getEnterpriseinfo();
        entCourseList = enterpriseData.getEntCourseList();
        enterpriseId = String.valueOf(entinfo.getEnid());
        //保存企业Id
        if (enterpriseId != null) {
          String entid = SPUtils.getString(getActivity(), "enterprise");
          if (entid != null) {
            SPUtils.clearInfo(getActivity(), "enterprise");
          }
          SPUtils.put(getActivity(), "enterprise", entinfo.getEnid());
        }
        ImageLoader.getInstance().displayImage(Constants.IMG_URL + entinfo.getElogo(), headIv,
            ApplicationEx.head_options);
        ImageLoader.getInstance().displayImage(Constants.IMG_URL + entinfo.getElogo(), iv_imaeBG,
            ApplicationEx.head_options);
        nameTv.setText(entinfo.getEname());
        noticeTv1.setText(entinfo.getEnotice());
        noticeTv1.init(getActivity().getWindowManager());
        if (null == entCourseList || entCourseList.size() == 0) {
          moreCourseLayout.setVisibility(View.INVISIBLE);
        } else {
          moreCourseLayout.setVisibility(View.VISIBLE);
        }
        allCourseAdapter.setListItems(entCourseList);
        allCourseAdapter.notifyDataSetChanged();
      } else {
        ToastUtil.getInstant().show(msg);
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  private void initListener() {
  }

  private void initActivity() {
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.tv_enterprise_notice:
        Intent intentNotice = new Intent(getActivity(), EnterpriseNoticeActivity.class);
        intentNotice.putExtra("enterpriseId", enterpriseId);
        startActivity(intentNotice);
        break;
      case R.id.ll_enterprise_notice:
        Intent intentToAllNotice = new Intent(getActivity(), EnterpriseNoticeActivity.class);
        intentToAllNotice.putExtra("enterpriseId", enterpriseId);
        startActivity(intentToAllNotice);
        break;
      case R.id.rl_enterprise_more_cousre:
        Intent intentToMoreCourse = new Intent(getActivity(), EnterpriseCourseActivity.class);
        intentToMoreCourse.putExtra("enterpriseId", enterpriseId);
        startActivity(intentToMoreCourse);
        break;
    }
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
    public View getView(int position, View convertView, ViewGroup parent) {
      View view = View.inflate(getActivity(), R.layout.cottage_item, null);
      ImageView imagView = (ImageView) view.findViewById(R.id.iv_image);
      TextView textview = (TextView) view.findViewById(R.id.tv_text);
      imagView.setImageResource(mGridViewBeans[position].getImageRes());
      textview.setText(mGridViewBeans[position].getTitle());
      return view;
    }
  }

  public void showLoadingDialog() {
    if (null != loadingDialog) {
      loadingDialog.show(getString(R.string.loading));
    }
  }

  public void showLoadingDialog(String content) {
    if (null != loadingDialog) {
      loadingDialog.show(content);
    }
  }

  public void dimissLoadingDialog() {
    if (null != loadingDialog) {
      loadingDialog.dismiss();
    }
  }

  @Override
  public void onDestroy() {
    if (null != loadingDialog) {
      dimissLoadingDialog();
      loadingDialog = null; // 加快gc
    }
    super.onDestroy();
  }

  @Override
  public void onPause() {
    super.onPause();
    MobclickAgent.onPageEnd("FragmentOwnEnterprise");
    MobclickAgent.onPause(getActivity());
  }

  @Override
  public void onResume() {
    super.onResume();
    MobclickAgent.onPageStart("FragmentOwnEnterprise");
    MobclickAgent.onResume(getActivity());
  }
}
