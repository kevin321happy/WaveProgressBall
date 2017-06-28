package com.fips.huashun.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import cn.bingoogolapple.bgabanner.BGABanner;
import com.fips.huashun.ApplicationEx;
import com.fips.huashun.R;
import com.fips.huashun.common.ACache;
import com.fips.huashun.common.CacheConstans;
import com.fips.huashun.common.Constants;

import com.fips.huashun.db.dao.SectionDownloadDao;

import com.fips.huashun.db.dao.MemberDao;
import com.fips.huashun.modle.bean.CourseInfo;
import com.fips.huashun.modle.bean.GridViewBean;
import com.fips.huashun.modle.bean.HomeInfo;
import com.fips.huashun.modle.bean.TopImgInfo;
import com.fips.huashun.modle.dbbean.CourseSectionEntity;


import com.fips.huashun.modle.dbbean.MemberEntity;

import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.activity.ActivityDetailActivity;
import com.fips.huashun.ui.activity.ActivityRecommendActivity;
import com.fips.huashun.ui.activity.AllCourseActivity;
import com.fips.huashun.ui.activity.CourseDetailActivity;
import com.fips.huashun.ui.activity.EnterpriseMycourseActivity;
import com.fips.huashun.ui.activity.LecturerAllActivity;
import com.fips.huashun.ui.activity.LecturerApplyActivity;
import com.fips.huashun.ui.activity.LecturerDetailActivity;
import com.fips.huashun.ui.activity.LoginActivity;
import com.fips.huashun.ui.activity.MoreDetailActivity;
import com.fips.huashun.ui.activity.MoreLecturerActivity;
import com.fips.huashun.ui.activity.MyMessageActivity;
import com.fips.huashun.ui.activity.SearchActivity;
import com.fips.huashun.ui.activity.WebviewActivity;
import com.fips.huashun.ui.adapter.ActivityRecommendAdapter;
import com.fips.huashun.ui.adapter.IndustryCourseAdapter;
import com.fips.huashun.ui.adapter.LecturerResultAdapter;
import com.fips.huashun.ui.adapter.TeacherCourseAdapter;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.ToastUtil;
import com.fips.huashun.widgets.MyGridView;
import com.fips.huashun.widgets.NoScrollListView;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * ��ҳ
 *
 * @author 首页
 */
public class FragmentHome extends Fragment implements View.OnClickListener {

  private View rootView;
  private int position;
  private PullToRefreshScrollView pullToRefreshScrollView;
  private BGABanner binner;
  private MyGridView gv_view;
  private GridViewBean[] mGridViewBeans = {new GridViewBean(R.drawable.home_one, "所有课程")
      , new GridViewBean(R.drawable.home_two, "所有讲师"),
      new GridViewBean(R.drawable.home_three, "活动专区")
      , new GridViewBean(R.drawable.home_four, "讲师申请")};
  private MyGridView gv_teacher;
  private Gson gson;
  private IndustryCourseAdapter mAdapter;
  private TeacherCourseAdapter teacherCourseAdapter;
  private IndustryCourseAdapter mAdapter2;
  private ActivityRecommendAdapter activityRecommendAdapter;
  private LecturerResultAdapter lecturerResultAdapter;
  private PullToRefreshScrollView scroollview;
  private LinearLayout rl_one;
  private ImageView iv_message;
  private ImageView iv_image_left;
  private ACache aCache;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    if (rootView == null) {
      rootView = inflater.inflate(R.layout.fragment_home, container, false);
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
    //实例化对象
    aCache = ACache.get(getActivity());
    initView();
    //判断缓存
    JSONObject json = aCache.getAsJSONObject(CacheConstans.HOME_INFO_JOSN);
    if (json != null) {
      try {
        String data = json.getString("data");
        HomeInfo homeInfo = gson.fromJson(data, HomeInfo.class);
        initHomeInfoData(homeInfo);
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }

    initData();
    getMsg();
  }

  private void initData() {
    RequestParams requestParams = new RequestParams();
    HttpUtil.post(Constants.HOME_URL, requestParams,
        new LoadJsonHttpResponseHandler(getActivity(), new LoadDatahandler() {
          @Override
          public void onStart() {
            super.onStart();
          }

          @Override
          public void onSuccess(JSONObject data) {
            super.onSuccess(data);
            //保存jsonObject数据,保存2天
            aCache.put(CacheConstans.HOME_INFO_JOSN, data, ACache.TIME_DAY * 2);
            scroollview.onRefreshComplete();
            try {
              HomeInfo homeInfo = gson.fromJson(data.getString("data").toString(), HomeInfo.class);
              initHomeInfoData(homeInfo);
            } catch (JSONException e) {
              scroollview.onRefreshComplete();
            }
          }

          @Override
          public void onFailure(String error, String message) {
            super.onFailure(error, message);
            scroollview.onRefreshComplete();
          }
        }));
  }

  /*初始化首页信息*/
  private void initHomeInfoData(HomeInfo homeInfo) {
    try {
      rl_one.setVisibility(View.VISIBLE);
      // Log.i("test11", "data数据" + data.getString("data"));
      final List<TopImgInfo> topimg = homeInfo.getTopImg();
      if (topimg.size() > 0) {
        List<String> list = new ArrayList<String>();
        for (int a = 0; a < topimg.size(); a++) {
          list.add(topimg.get(a).getCarimg());
        }
        binner.setAdapter(new BGABanner.Adapter() {
          @Override
          public void fillBannerItem(BGABanner banner, View view, Object model, int position) {

            ImageLoader.getInstance().displayImage(Constants.IMG_URL + model, (ImageView) view);
          }
        });

        binner.setData(list, null);
        binner.setOnItemClickListener(new BGABanner.OnItemClickListener() {
          @Override
          public void onBannerItemClick(BGABanner banner, View view, Object model, int position) {
            if (TextUtils.isEmpty(topimg.get(position).getLink())) {
              Intent intent = new Intent(getActivity(), WebviewActivity.class);
              intent.putExtra("key", 12);
              intent.putExtra("position", topimg.get(position).getCarid() + "");
              Log.e("XCVXCVVXC", topimg.get(position).getCarid());
              startActivity(intent);
            } else {
              Intent intent = new Intent(getActivity(), WebviewActivity.class);
              intent.putExtra("key", 12);
              intent.putExtra("link", topimg.get(position).getLink());
              startActivity(intent);
            }
          }
        });
      }

      List<CourseInfo> courseList1 = homeInfo.getCourseList1();
      mAdapter.setListItems(courseList1);
      mAdapter.notifyDataSetChanged();
      teacherCourseAdapter.setListItems(homeInfo.getCourseList4());
      teacherCourseAdapter.notifyDataSetChanged();
      mAdapter2.setListItems(homeInfo.getCourseList3());
      mAdapter2.notifyDataSetChanged();
      lecturerResultAdapter.setListItems(homeInfo.getTeacherList());
      lecturerResultAdapter.notifyDataSetChanged();
      activityRecommendAdapter.setListItems(homeInfo.getActivityList());
      activityRecommendAdapter.notifyDataSetChanged();

    } catch (Exception e) {
      e.printStackTrace();
      scroollview.onRefreshComplete();
    }
  }


  private void initView() {
    rl_one = (LinearLayout) rootView.findViewById(R.id.rl_one);
    rootView.findViewById(R.id.ll_message).setOnClickListener(this);
    rootView.findViewById(R.id.iv_image_left).setOnClickListener(this);
    iv_message = (ImageView) rootView.findViewById(R.id.iv_message);

    rootView.findViewById(R.id.tv_more1).setOnClickListener(this);
    rootView.findViewById(R.id.tv_more2).setOnClickListener(this);
    rootView.findViewById(R.id.tv_more3).setOnClickListener(this);
    rootView.findViewById(R.id.tv_more4).setOnClickListener(this);
    rootView.findViewById(R.id.tv_more5).setOnClickListener(this);
    binner = (BGABanner) rootView.findViewById(R.id.binner);
    gv_view = (MyGridView) rootView.findViewById(R.id.gv_view);
    gv_view.setAdapter(new MyGridViewAdapter());
    gv_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e("onItemClick", "position=" + position);
        if (position == 0) {
          Intent intent = new Intent(getActivity(), AllCourseActivity.class);
          startActivity(intent);
        } else if (position == 1) {
          Intent intent = new Intent(getActivity(), LecturerAllActivity.class);
          startActivity(intent);
        } else if (position == 2) {
          Intent intent = new Intent(getActivity(), ActivityRecommendActivity.class);
          startActivity(intent);
        } else if (position == 3) {
          Intent intent = new Intent(getActivity(), EnterpriseMycourseActivity.class);
          startActivity(intent);
        }
      }
    });
    binner.setAdapter(new BGABanner.Adapter() {
      @Override
      public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
        ((ImageView) view).setImageResource((int) model);

      }
    });
    binner.setData(Arrays.asList(R.drawable.binner, R.drawable.login_bg, R.drawable.binner), null);
    rootView.findViewById(R.id.tv_search).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        startActivity(new Intent(getActivity(), SearchActivity.class));
      }
    });
//        行业课程
    NoScrollListView lv_list = (NoScrollListView) rootView.findViewById(R.id.lv_list);
    mAdapter = new IndustryCourseAdapter(getActivity());
    lv_list.setAdapter(mAdapter);
//        老师课程
    gv_teacher = (MyGridView) rootView.findViewById(R.id.gv_teacher);
    teacherCourseAdapter = new TeacherCourseAdapter(getActivity());
    gv_teacher.setAdapter(teacherCourseAdapter);
    gv_teacher.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(getActivity(), CourseDetailActivity.class);
        intent.putExtra("courseId",
            String.valueOf(teacherCourseAdapter.getItem(position).getCourseId()));
        startActivity(intent);
      }
    });
//        热门课程
    NoScrollListView lv_list2 = (NoScrollListView) rootView.findViewById(R.id.lv_list2);
    mAdapter2 = new IndustryCourseAdapter(getActivity());
    lv_list2.setAdapter(mAdapter2);
//        推荐讲师
    NoScrollListView lv_list3 = (NoScrollListView) rootView.findViewById(R.id.lv_list3);
    lecturerResultAdapter = new LecturerResultAdapter(getActivity());
    lv_list3.setAdapter(lecturerResultAdapter);
    lv_list3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(getActivity(), LecturerDetailActivity.class);
        intent.putExtra("teacherId",
            String.valueOf(lecturerResultAdapter.getItem(position).getTeacherId()));
        startActivity(intent);
      }
    });
//        推荐活动
    NoScrollListView lv_list4 = (NoScrollListView) rootView.findViewById(R.id.lv_list4);
    activityRecommendAdapter = new ActivityRecommendAdapter(getActivity());
    lv_list4.setAdapter(activityRecommendAdapter);
    scroollview = (PullToRefreshScrollView) rootView.findViewById(R.id.scroollview);
    scroollview.setMode(PullToRefreshBase.Mode.PULL_DOWN_TO_REFRESH);
    scroollview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
      @Override
      public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
        initData();
        getMsg();

      }
    });
    lv_list4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), ActivityDetailActivity.class);
        intent.putExtra("activityId",
            String.valueOf(activityRecommendAdapter.getItem(position).getActivityid()));
        startActivity(intent);
      }
    });
  }


  @Override
  public void onDestroyView() {
    super.onDestroyView();

  }

  @Override
  public void onClick(View v) {
    Intent intent = null;
    switch (v.getId()) {
      case R.id.iv_image_left:
        ToastUtil.getInstant().show("暂未开放！");

        SectionDownloadDao downloadDao = new SectionDownloadDao(getActivity());
        List<CourseSectionEntity> sectionEntities = downloadDao.queryAll();
        for (CourseSectionEntity sectionEntity : sectionEntities) {
          Log.i("test999",sectionEntity.toString());
        }

        MemberDao memberDao = new MemberDao(getActivity());
        int count = memberDao.queryAllChooseCount();
//        Log.i("tet", "选中的个数 ："+count);
        List<MemberEntity> memberEntityList =
            memberDao.queryAllChooseMembers();
        for (MemberEntity memberEntity : memberEntityList) {
          memberEntity.setIntroduce("0");
          memberDao.upDataMember(memberEntity);
        }
//

        return;
      case R.id.ll_message:
        if (ApplicationEx.getInstance().isLogined()) {
          intent = new Intent(getActivity(), MyMessageActivity.class);
          startActivity(intent);
        } else {
          intent = new Intent(getActivity(), LoginActivity.class);
          startActivityForResult(intent, 100);
        }
        break;
      case R.id.tv_more1:
        intent = new Intent(getActivity(), MoreDetailActivity.class);
        intent.putExtra("key", "showMoreCourseList");
        startActivity(intent);
        break;
      case R.id.tv_more2:
        intent = new Intent(getActivity(), MoreDetailActivity.class);
        intent.putExtra("key", "showMoreTeachCourseList");
        startActivity(intent);
        break;
      case R.id.tv_more3:
        intent = new Intent(getActivity(), MoreDetailActivity.class);
        intent.putExtra("key", "showMoreHotCourseList");
        startActivity(intent);
        break;
      case R.id.tv_more4:
        intent = new Intent(getActivity(), MoreLecturerActivity.class);
        intent.putExtra("key", "showMoreRecommendTeacherList");
        startActivity(intent);
        break;
      case R.id.tv_more5:
        intent = new Intent(getActivity(), ActivityRecommendActivity.class);
        intent.putExtra("key", "showMoreRecommendActList");
        startActivity(intent);
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
    public View getView(final int position, View convertView, ViewGroup parent) {
      View view = View.inflate(getActivity(), R.layout.categry_item, null);
      ImageView imagView = (ImageView) view.findViewById(R.id.iv_image);
      TextView textview = (TextView) view.findViewById(R.id.tv_text);
      imagView.setImageResource(mGridViewBeans[position].getImageRes());
      textview.setText(mGridViewBeans[position].getTitle());
      view.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (position == 0) {
            Intent intent = new Intent(getActivity(), AllCourseActivity.class);
            startActivity(intent);
          } else if (position == 1) {
            Intent intent = new Intent(getActivity(), LecturerAllActivity.class);
            startActivity(intent);
          } else if (position == 2) {
            Intent intent = new Intent(getActivity(), ActivityRecommendActivity.class);
            startActivity(intent);
          } else if (position == 3) {
            Intent intent = new Intent(getActivity(), LecturerApplyActivity.class);
            startActivity(intent);
          }

        }
      });
      return view;
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    getMsg();
  }

  private void getMsg() {
    RequestParams requestParams = new RequestParams();
    requestParams.put("userid", PreferenceUtils.getUserId());
    HttpUtil.post(Constants.URL + "/getMsg", requestParams,
        new LoadJsonHttpResponseHandler(getActivity(), new LoadDatahandler() {
          @Override
          public void onStart() {
            super.onStart();
          }

          @Override
          public void onSuccess(JSONObject data) {
            super.onSuccess(data);
            Log.e("data", data.toString());
            try {
              String suc = data.get("suc").toString();
              if ("y".equals(suc)) {
                String status = data.getString("data");
                JSONObject jsonObject = new JSONObject(status);
                String readstatus = jsonObject.getString("readstatus");
                Log.e("readstatus", readstatus);
                if (readstatus.equals("0")) {
                  iv_message.setImageResource(R.drawable.xinxi);
                } else {
                  iv_message.setImageResource(R.drawable.xiaoxi_you);
                }
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
  public void onPause() {
    super.onPause();
    MobclickAgent.onPageEnd("FragmentHome");
    MobclickAgent.onPause(getActivity());
  }

  @Override
  public void onResume() {
    super.onResume();
    MobclickAgent.onPageStart("FragmentHome");
    MobclickAgent.onResume(getActivity());

  }

  @Override
  public void onStop() {
    super.onStop();
  }
}
