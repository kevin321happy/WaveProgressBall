package com.fips.huashun.ui.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.modle.bean.CourseInfo;
import com.fips.huashun.modle.bean.FilterData;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.adapter.AllCourseAdapter;
import com.fips.huashun.ui.adapter.FilterLeftAdapter;
import com.fips.huashun.ui.adapter.FilterOrderAdapter;
import com.fips.huashun.ui.adapter.FilterRightAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 功能：所有课程
 * Created by Administrator on 2016/8/16.
 *
 * @author 张柳 时间：2016年8月30日19:46:05
 */
public class AllCourseActivity extends BaseActivity implements OnClickListener {

  // 返回
  private LinearLayout backLl;
  // 上下拉的控件
  private PullToRefreshListView pullToRefreshListView;
  // 列表
  private ListView mListView;
  // 适配器
  private AllCourseAdapter allCourseAdapter;
  // 数据
  private List<CourseInfo> list;
  private PopupWindow pop;
  private LinearLayout ll_filter;
  private ListView lv_left;
  private ListView lv_right;
  protected Gson gson;
  private FilterLeftAdapter leftAdapter;
  private FilterRightAdapter rightAdapter;
  String type = "";
  String order = "";
  private LinearLayout ll_filter02;
  private TextView tv_category;
  private TextView tv_sort;
  private ImageView iv_sort_arrow;
  private ImageView iv_category_arrow;
  private TextView tv_dissmis;
  private FilterOrderAdapter filterOrderAdapter;
  private List<FilterData> items;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_course_all);
    initView();
//        showLoadingDialog();
//        initData(type,order);
//        getFilterData();
  }

  @Override
  protected void initView() {
    super.initView();
    gson = new Gson();
    backLl = (LinearLayout) findViewById(R.id.ll_back);
    ll_filter = (LinearLayout) findViewById(R.id.ll_filter);
    findViewById(R.id.ll_filter01).setOnClickListener(this);
    findViewById(R.id.ll_filter02).setOnClickListener(this);
    tv_category = (TextView) findViewById(R.id.tv_category);
    tv_sort = (TextView) findViewById(R.id.tv_sort);
    iv_category_arrow = (ImageView) findViewById(R.id.iv_category_arrow);
    iv_sort_arrow = (ImageView) findViewById(R.id.iv_sort_arrow);
    pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.lv_course_all);
    mListView = pullToRefreshListView.getRefreshableView();
    // 两端刷新
    pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
    allCourseAdapter = new AllCourseAdapter(getApplicationContext());
    mListView.setAdapter(allCourseAdapter);
    pullToRefreshListView
        .setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
          @Override
          public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
            initData(type, order);
          }

          @Override
          public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
            initData(type, order);
          }
        });

    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(AllCourseActivity.this, CourseDetailActivity.class);
        intent.putExtra("courseId", allCourseAdapter.getItem(position - 1).getCourseId());
        startActivity(intent);
      }
    });
    backLl.setOnClickListener(this);

    leftAdapter = new FilterLeftAdapter(this);
    rightAdapter = new FilterRightAdapter(this);
    filterOrderAdapter = new FilterOrderAdapter(this);
    //排序 数据
    items = new ArrayList<FilterData>();
    FilterData filterData = new FilterData();
    filterData.setCoursetypename("推荐排序");
    filterData.setSelected(true);
    FilterData filterData1 = new FilterData();
    filterData1.setCoursetypename("按好评率排序");
    FilterData filterData2 = new FilterData();
    filterData2.setCoursetypename("按学习次数排序");
    FilterData filterData3 = new FilterData();
    filterData3.setCoursetypename("按魔豆价格排序");
    items.add(filterData);
    items.add(filterData1);
    items.add(filterData2);
    items.add(filterData3);
  }

  /**
   * 功能：获取所有课程列表信息
   */
  private void initData(String type, String order) {
//        RequestParams requestParams = new RequestParams();
//        requestParams.put("type",type);
//        requestParams.put("order",order);
//        HttpUtil.post(Constants.URL+"/checkCourseInfoBytype", requestParams, new LoadJsonHttpResponseHandler(this, new LoadDatahandler()
//        {
//            @Override
//            public void onStart()
//            {
//                super.onStart();
//
//            }
//
//            @Override
//            public void onSuccess(JSONObject data)
//            {
//                super.onSuccess(data);
//                dimissLoadingDialog();
//                try
//                {
//                    String suc = data.get("suc").toString();
//                    String msg = data.get("msg").toString();
//                    if ("y".equals(suc))
//                    {
//                        list = gson.fromJson(data.getString("data"), new TypeToken<List<CourseInfo>>(){}.getType());
//                        allCourseAdapter.setListItems(list);
//                        allCourseAdapter.notifyDataSetChanged();
//                    } else
//                    {
//                        ToastUtil.getInstant().show(msg);
//                    }
//                    // 结束刷新
//                    pullToRefreshListView.onRefreshComplete();
//                } catch (JSONException e)
//                {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(String error, String message)
//            {
//                super.onFailure(error, message);
//                dimissLoadingDialog();
//                // 结束刷新
//                pullToRefreshListView.onRefreshComplete();
//            }
//        }));
  }

  @Override
  public boolean isSystemBarTranclucent() {
    return false;
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.ll_back:
        finish();
        break;
      case R.id.ll_filter01:
        InitPopwindow();
//                if (pop.isShowing()){
        tv_category.setTextColor(getResources().getColor(R.color.title_color));
        iv_category_arrow.setImageDrawable(getResources().getDrawable(R.drawable.home_up_arrow));
//                }else {
//                    tv_category.setTextColor(getResources().getColor(R.color.text_bg));
//                    iv_category_arrow.setImageDrawable(getResources().getDrawable(R.drawable.course_result_fan));
//                }
        break;
      case R.id.ll_filter02:
        initPop2();
        tv_sort.setTextColor(getResources().getColor(R.color.title_color));
        iv_sort_arrow.setImageDrawable(getResources().getDrawable(R.drawable.home_up_arrow));
        break;
      case R.id.tv_dissmis:
        pop.dismiss();
        break;
    }

  }

  private void InitPopwindow() {
    pop = new PopupWindow(this);
    View view = getLayoutInflater().inflate(
        R.layout.layout_filter_pop, null);
//        ll_popup = view.findViewById(R.id.ll_popup);
    pop.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
    pop.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
    pop.setBackgroundDrawable(new BitmapDrawable());
    pop.setFocusable(true);
    pop.setOutsideTouchable(true);
    pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
      @Override
      public void onDismiss() {
        tv_category.setTextColor(getResources().getColor(R.color.text_bg));
        tv_sort.setTextColor(getResources().getColor(R.color.text_bg));
        iv_category_arrow
            .setImageDrawable(getResources().getDrawable(R.drawable.course_result_fan));
        iv_sort_arrow.setImageDrawable(getResources().getDrawable(R.drawable.course_result_fan));
      }
    });
    pop.setContentView(view);
//        WindowManager.LayoutParams params=getWindow().getAttributes();
//        params.alpha=0.7f;
//
//        getWindow().setAttributes(params);
    pop.showAsDropDown(ll_filter);
    //设置数据

    lv_left = (ListView) view.findViewById(R.id.lv_left);
    lv_right = (ListView) view.findViewById(R.id.lv_right);
    tv_dissmis = (TextView) view.findViewById(R.id.tv_dissmis);
    tv_dissmis.setOnClickListener(this);
    lv_left.setAdapter(leftAdapter);
    lv_right.setAdapter(rightAdapter);
    lv_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
          type = "";
          order = "";
          initData(type, order);
          rightAdapter.removeItems();
          pop.dismiss();

        } else {
          List<FilterData> filterDatas = leftAdapter.getAllListDate();
          getChildFilter(filterDatas.get(position).getType_id());
        }

        for (int a = 0; a < leftAdapter.getAllListDate().size(); a++) {
          if (a == position) {
            leftAdapter.getAllListDate().get(a).setSelected(true);
          } else {
            leftAdapter.getAllListDate().get(a).setSelected(false);
          }
        }
        leftAdapter.notifyDataSetChanged();
      }
    });
    lv_right.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        type = rightAdapter.getItem(position).getType_id();
        initData(type, order);
        pop.dismiss();
      }
    });
  }

  private void getFilterData() {
    RequestParams requestParams = new RequestParams();
    HttpUtil.post(Constants.URL + "/showParentinfo", requestParams,
        new LoadJsonHttpResponseHandler(this, new LoadDatahandler() {
          @Override
          public void onStart() {
            super.onStart();
          }

          @Override
          public void onSuccess(JSONObject data) {
            super.onSuccess(data);
            String suc = null;
            try {
              suc = data.get("suc").toString();
              if ("y".equals(suc)) {
                List<FilterData> filterList = gson
                    .fromJson(data.getString("data"), new TypeToken<List<FilterData>>() {
                    }.getType());
                FilterData filterData = new FilterData();
                filterData.setCoursetypename("全部");
                filterList.add(0, filterData);
                filterList.get(0).setSelected(true);
                leftAdapter.setListItems(filterList);
                leftAdapter.notifyDataSetChanged();


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

  private void getChildFilter(String id) {
    RequestParams requestParams = new RequestParams();
    requestParams.put("parentid", id);
    HttpUtil.post(Constants.URL + "/showChildinfo", requestParams,
        new LoadJsonHttpResponseHandler(this, new LoadDatahandler() {
          @Override
          public void onStart() {
            super.onStart();
          }

          @Override
          public void onSuccess(JSONObject data) {
            super.onSuccess(data);
            try {
              if (data.getString("suc").equals("y")) {
                List<FilterData> filterList = gson
                    .fromJson(data.getString("data"), new TypeToken<List<FilterData>>() {
                    }.getType());
                rightAdapter.setListItems(filterList);
                rightAdapter.notifyDataSetChanged();

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


  private void initPop2() {
    pop = new PopupWindow(this);
    View view = getLayoutInflater().inflate(
        R.layout.layout_filter2_pop, null);
//        ll_popup = view.findViewById(R.id.ll_popup);
    pop.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
    pop.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
    pop.setBackgroundDrawable(new BitmapDrawable());
    pop.setFocusable(true);
    pop.setOutsideTouchable(true);
    pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
      @Override
      public void onDismiss() {
        tv_category.setTextColor(getResources().getColor(R.color.text_bg));
        tv_sort.setTextColor(getResources().getColor(R.color.text_bg));
        iv_category_arrow
            .setImageDrawable(getResources().getDrawable(R.drawable.course_result_fan));
        iv_sort_arrow.setImageDrawable(getResources().getDrawable(R.drawable.course_result_fan));
      }
    });
    pop.setContentView(view);
    pop.showAsDropDown(ll_filter);
    lv_right = (ListView) view.findViewById(R.id.lv_right);
    view.findViewById(R.id.tv_dissmis).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        pop.dismiss();
      }
    });
//        lv_right.setAdapter(new s);
    filterOrderAdapter.setListItems(items);
    lv_right.setAdapter(filterOrderAdapter);
    lv_right.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        for (int a = 0; a < filterOrderAdapter.getAllListDate().size(); a++) {
          if (a == position) {
            filterOrderAdapter.getAllListDate().get(a).setSelected(true);
            pop.dismiss();
            if (a == 0) {
              order = "";
            } else {
              order = position + "";
            }
            initData(type, order);
          } else {
            filterOrderAdapter.getAllListDate().get(a).setSelected(false);
          }
        }
        filterOrderAdapter.notifyDataSetChanged();
      }
    });
  }

  @Override
  protected void onResume() {
    super.onResume();
    MobclickAgent.onPageStart("AllCourseActivity");
  }

  @Override
  protected void onPause() {
    super.onPause();
    MobclickAgent.onPageEnd("AllCourseActivity");
  }
}
