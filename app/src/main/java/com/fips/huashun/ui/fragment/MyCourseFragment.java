package com.fips.huashun.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.common.ConstantsCode;
import com.fips.huashun.modle.bean.EnterMyCourseInfo;
import com.fips.huashun.modle.bean.EnterMyCourseInfo.EnterMyCourse;
import com.fips.huashun.ui.adapter.EnterpriseMycourseAdapter;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.BaseRequest;
import java.util.List;
import okhttp3.Call;
import okhttp3.Response;

/**
 * description: 企业,我的课程
 * autour: Kevin
 * company:锦绣氘(武汉)科技有限公司
 * date: 2017/5/15 15:51
 * update: 2017/5/15
 * version: 1.21
 * 站在峰顶 看世界
 * 落在谷底 思人生
 */
public class MyCourseFragment extends BaseFragment {

  private ListView mLv_myCourse;
  private String course_type = "0";
  private String study_type = "1";//学习类型,1必修2选修
  private String mCourse_type;
  private Gson mGson;
  private List<EnterMyCourse> mMyCourses;
  // 适配器
  private EnterpriseMycourseAdapter enterpriseMycourseAdapter;
  private EnterpriseMycourseAdapter mMycourseAdapter;
  private Context mContext;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    mContext = container.getContext();
    View view = View.inflate(container.getContext(), R.layout.fragment_mycourse, null);
    mLv_myCourse = (ListView) view.findViewById(R.id.lv_mycourse);
    mCourse_type = getArguments().getString("course_type");
    mGson = new Gson();
    return view;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mMycourseAdapter = new EnterpriseMycourseAdapter(mContext);
    mLv_myCourse.setAdapter(mMycourseAdapter);
    initData();
  }

  //初始化数据
  private void initData() {
    //获取课程的列表
    OkGo.post(Constants.ENTERPRISE_MUSTMYENTERPRISE_URL)
        .tag(this)
        .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
        .cacheKey(ConstantsCode.CACHE_ENT_MYCOURSE)
        .params("entid", "8")
        .params("userid","22")
        .params("type", "1")
        .execute(new StringCallback() {
          @Override
          public void onBefore(BaseRequest request) {
            super.onBefore(request);
          }

          @Override
          public void onSuccess(String s, Call call, Response response) {
            dimissLoadingDialog();
            EnterMyCourseInfo enterMyCourseInfo = mGson.fromJson(s, EnterMyCourseInfo.class);
            mMyCourses = enterMyCourseInfo.getData();
            mMycourseAdapter.setListItems(mMyCourses);
            mMycourseAdapter.setType(1+ "");
            mMycourseAdapter.notifyDataSetChanged();
            // 结束刷新
//            pullToRefreshListView.onRefreshComplete();
          }

          @Override
          public void onCacheSuccess(String s, Call call) {
            super.onCacheSuccess(s, call);
            EnterMyCourseInfo enterMyCourseInfo = mGson.fromJson(s, EnterMyCourseInfo.class);
//            mMycoursedata = enterMyCourseInfo.getData();
//            enterpriseMycourseAdapter.setListItems(mMycoursedata);
//            enterpriseMycourseAdapter.setType(type + "");
//            enterpriseMycourseAdapter.notifyDataSetChanged();
            // 结束刷新
//            pullToRefreshListView.onRefreshComplete();
          }

          @Override
          public void onCacheError(Call call, Exception e) {
            super.onCacheError(call, e);
          }

          @Override
          public void onError(Call call, Response response, Exception e) {
            super.onError(call, response, e);
            dimissLoadingDialog();
          }
        });


  }

  @Override
  public View getContentView() {
    return null;
  }
}
