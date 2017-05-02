package com.fips.huashun.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.modle.bean.CourseInfo;
import com.fips.huashun.modle.event.RecommendEvent;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.activity.CourseDetailActivity;
import com.fips.huashun.ui.adapter.AllCourseAdapter;
import com.fips.huashun.ui.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;
import de.greenrobot.event.EventBus;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * ���ﳵ
 *
 * @author hulin
 */
public class CourseDetailFragment3 extends Fragment {

  private View rootView;
  private ListView lv_list;
  private Gson gson;
  private AllCourseAdapter allCourseAdapter;
  private String courseId;
  private String mReceivecourseid;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    if (rootView == null) {
      rootView = inflater.inflate(R.layout.fragment_course, container, false);
      EventBus.getDefault().register(this);
    }
    ViewGroup parent = (ViewGroup) rootView.getParent();
    if (parent != null) {
      parent.removeView(rootView);
    }
    return rootView;
  }

  public static CourseDetailFragment3 newInstance(String title) {
    CourseDetailFragment3 tabFragment = new CourseDetailFragment3();
    Bundle bundle = new Bundle();
    bundle.putString("22", title);
    tabFragment.setArguments(bundle);
    return tabFragment;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initView();
    gson = new Gson();
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
  }


  private void initView() {
    lv_list = (ListView) rootView.findViewById(R.id.lv_list);
    allCourseAdapter = new AllCourseAdapter(getActivity());
    lv_list.setAdapter(allCourseAdapter);
    lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), CourseDetailActivity.class);
        intent.putExtra("courseId", allCourseAdapter.getItem(position).getCourseId());
        startActivity(intent);
      }
    });
  }

  private void initData() {
    RequestParams requestParams = new RequestParams();
    requestParams.put("courseId", courseId);
    HttpUtil.post(Constants.GETRECOMMENDCOURSE, requestParams,
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
                // 获取数据
                List<CourseInfo> list = gson.fromJson(data.getJSONArray("data").toString(),
                    new TypeToken<List<CourseInfo>>() {
                    }.getType());
                allCourseAdapter.setListItems(list);
                allCourseAdapter.notifyDataSetChanged();
              } else {
                ToastUtil.getInstant().show(msg);
              }
              // 结束刷新
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
  public void onResume() {
    super.onResume();
    MobclickAgent.onPageStart("CourseDetailFragment3");
    MobclickAgent.onResume(getActivity());
  }

  public void onEventMainThread(RecommendEvent event) {
    mReceivecourseid = event.getCourseid();
  }

  @Override
  public void onPause() {
    super.onPause();
    MobclickAgent.onPageEnd("CourseDetailFragment3");
    MobclickAgent.onPause(getActivity());
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    EventBus.getDefault().unregister(this);
  }
}
