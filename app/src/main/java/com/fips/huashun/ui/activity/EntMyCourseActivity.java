package com.fips.huashun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fips.huashun.R;
import com.fips.huashun.ui.adapter.CourseCategoryAdapter;
import com.fips.huashun.ui.fragment.MyCourseFragment;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.NavigationBar.NavigationListener;
import com.fips.huashun.widgets.CustomEditText;

/**
 * description: 企业我的课程 autour: Kevin company:锦绣氘(武汉)科技有限公司 date: 2017/4/20 9:33 update: 2017/4/20
 * version: 1.21 站在峰顶 看世界 落在谷底 思人生
 */

public class EntMyCourseActivity extends BaseActivity implements OnItemClickListener {




  @Bind(R.id.tv_unpass_counts)
  TextView mTvUnpassCounts;
  @Bind(R.id.tv_unfinish_points)
  TextView mTvUnfinishPoints;
  @Bind(R.id.iv_more_unfinish)
  ImageView mIvMoreUnfinish;
  @Bind(R.id.tv_unfinish_counts)
  TextView mTvUnfinishCounts;
  @Bind(R.id.tv_unpass_points)
  TextView mTvUnpassPoints;
  @Bind(R.id.iv_more_unpass)
  ImageView mIvMoreUnpass;
  @Bind(R.id.tv_finish_counts)
  TextView mTvFinishCounts;
  @Bind(R.id.tv_finish_points)
  TextView mTvFinishPoints;
  @Bind(R.id.iv_more_finish)
  ImageView mIvMoreFinish;
  @Bind(R.id.tv_pass_counts)
  TextView mTvPassCounts;
  @Bind(R.id.tv_pass_points)
  TextView mTvPassPoints;
  @Bind(R.id.iv_more_pass)
  ImageView mIvMorePass;
  @Bind(R.id.tv_all_counts)
  TextView mTvAllCounts;
  @Bind(R.id.tv_all_points)
  TextView mTvAllPoints;
  @Bind(R.id.iv_more_all)
  ImageView mIvMoreAll;
  @Bind(R.id.iv_back)
  ImageView mIvBack;
  @Bind(R.id.et_search_course)
  CustomEditText mEtSearchCourse;
  @Bind(R.id.ll_search)
  LinearLayout mLlSearch;

  @Bind(R.id.tv_has_download)
  TextView mTvHasDownload;
  @Bind(R.id.iv_more_download)
  ImageView mIvMoreDownload;

  private String enterpriseId;

  private String[] strs = {"待学习","待考试", "全部课程", "下载管理"};
  @Bind(R.id.NavigationBar)
  NavigationBar mNavigationBar;
  @Bind(R.id.listview)
  ListView mListview;
  @Bind(R.id.fragment_container)
  FrameLayout mFragmentContainer;
  public static int mPosition;
  private CourseCategoryAdapter mCategoryAdapter;
  private MyCourseFragment myFragment;//我的课程的Fragment
  private String study_type = "0";


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activtiy_entmycourseactivity);
    ButterKnife.bind(this);
    initView();
  }

  public void initView() {
    mNavigationBar.setTitle("我的课程");
    mNavigationBar.setLeftImage(R.drawable.fanhui);
    mNavigationBar.setListener(new NavigationListener() {
      @Override
      public void onButtonClick(int button) {
        if (button == NavigationBar.LEFT_VIEW) {
          finish();
        }
      }
    });
    mCategoryAdapter = new CourseCategoryAdapter(this, strs);
    mListview.setAdapter(mCategoryAdapter);
    mListview.setOnItemClickListener(this);
  }

  @Override
  public boolean isSystemBarTranclucent() {
    return false;
  }



  //点击跳转不同的课程列表



  //点击跳转不同的课程列表
  @OnClick({R.id.iv_more_unfinish, R.id.iv_more_unpass, R.id.iv_more_finish, R.id.iv_more_pass,
      R.id.iv_more_all,R.id.iv_back})

  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.iv_more_unfinish:
        Intent intent = new Intent(this, EntMyCourseActivity.class);
        intent.putExtra("enterpriseId", enterpriseId);
        startActivity(new Intent(this, EnterpriseMycourseActivity.class));
        break;
      case R.id.iv_more_unpass:
        break;
      case R.id.iv_more_finish:
        break;
      case R.id.iv_more_pass:
        break;
      case R.id.iv_more_all:
        break;

      case R.id.iv_more_download:
        startActivity(new Intent(this, DownloadManageActivity.class));

      case R.id.iv_back:
        finish();
        break;
    }
  }


  //当点击左侧的导航条目

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    //拿到当前的位置刷新列表
    mPosition = position;
    mCategoryAdapter.notifyDataSetChanged();
    showCourseFragment(mPosition);
  }
//显示课程的fragment
  private void showCourseFragment(int position) {
    myFragment = new MyCourseFragment();
    FragmentTransaction fragmentTransaction = getSupportFragmentManager()
        .beginTransaction();
    fragmentTransaction.replace(R.id.fragment_container, myFragment);
    Bundle bundle = new Bundle();
    if (position == 0) {
      //待学习
      bundle.putString("course_type", "1");
//      bundle.putString("study_type", study_type);
      myFragment.setArguments(bundle);
      fragmentTransaction.commit();
    } else if (position == 1) {
      //待考试
      bundle.putString("course_type", "2");
//      bundle.putString("study_type", study_type);
      myFragment.setArguments(bundle);
      fragmentTransaction.commit();
    } else if (position == 2) {
      //全部
      bundle.putString("course_type", "3");
//      bundle.putString("study_type", study_type);
      myFragment.setArguments(bundle);
      fragmentTransaction.commit();
    } else if (position == 3) {
      //下载管理,跳转到下载管理界面
      Intent intent = new Intent(this, DownloadManageActivity.class);
      startActivity(intent);
    }
  }
}
