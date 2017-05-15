package com.fips.huashun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.fips.huashun.R;
import com.fips.huashun.ui.adapter.CourseCategoryAdapter;
import com.fips.huashun.ui.fragment.MyCourseFragment;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.NavigationBar.NavigationListener;

/**
 * description: 企业我的课程 autour: Kevin company:锦绣氘(武汉)科技有限公司 date: 2017/4/20 9:33 update: 2017/4/20
 * version: 1.21 站在峰顶 看世界 落在谷底 思人生
 */

public class EntMyCourseActivity extends BaseActivity implements OnItemClickListener {

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
