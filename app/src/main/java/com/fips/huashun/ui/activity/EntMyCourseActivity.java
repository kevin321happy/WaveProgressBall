package com.fips.huashun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fips.huashun.R;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.NavigationBar.NavigationListener;

/**
 * description: 企业我的课程 autour: Kevin company:锦绣氘(武汉)科技有限公司 date: 2017/4/20 9:33 update: 2017/4/20
 * version: 1.21 站在峰顶 看世界 落在谷底 思人生
 */

public class EntMyCourseActivity extends BaseActivity {

  @Bind(R.id.tv_unpass_counts)
  TextView mTvUnpassCounts;
  @Bind(R.id.tv_unfinish_points)
  TextView mTvUnfinishPoints;
  @Bind(R.id.iv_more_unfinish)
  ImageView mIvMoreUnfinish;
  @Bind(R.id.cd_unfinish)
  CardView mCdUnfinish;
  @Bind(R.id.tv_unfinish_counts)
  TextView mTvUnfinishCounts;
  @Bind(R.id.tv_unpass_points)
  TextView mTvUnpassPoints;
  @Bind(R.id.iv_more_unpass)
  ImageView mIvMoreUnpass;
  @Bind(R.id.cd_unpass)
  CardView mCdUnpass;
  @Bind(R.id.tv_finish_counts)
  TextView mTvFinishCounts;
  @Bind(R.id.tv_finish_points)
  TextView mTvFinishPoints;
  @Bind(R.id.iv_more_finish)
  ImageView mIvMoreFinish;
  @Bind(R.id.cd_finish)
  CardView mCdFinish;
  @Bind(R.id.tv_pass_counts)
  TextView mTvPassCounts;
  @Bind(R.id.tv_pass_points)
  TextView mTvPassPoints;
  @Bind(R.id.iv_more_pass)
  ImageView mIvMorePass;
  @Bind(R.id.cd_pass)
  CardView mCdPass;
  @Bind(R.id.tv_all_counts)
  TextView mTvAllCounts;
  @Bind(R.id.tv_all_points)
  TextView mTvAllPoints;
  @Bind(R.id.iv_more_all)
  ImageView mIvMoreAll;
  @Bind(R.id.tv_has_download)
  TextView mTvHasDownload;
  @Bind(R.id.iv_more_download)
  ImageView mIvMoreDownload;
  @Bind(R.id.cd_download)
  CardView mCdDownload;
  @Bind(R.id.NavigationBar)
  NavigationBar mNavigationBar;
  private String enterpriseId;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activtiy_entmycourseactivity);
    ButterKnife.bind(this);

    enterpriseId = getIntent().getStringExtra("enterpriseId");
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
  }

  @Override
  public boolean isSystemBarTranclucent() {
    return false;
  }

  //点击跳转不同的课程列表

  @OnClick({R.id.cd_unfinish, R.id.cd_unpass, R.id.cd_finish, R.id.cd_pass,
      R.id.iv_more_all, R.id.cd_download})
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.cd_unfinish:
        Intent intent = new Intent(this, EntMyCourseActivity.class);
        intent.putExtra("enterpriseId", enterpriseId);
        startActivity(new Intent(this, EnterpriseMycourseActivity.class));
        break;
      case R.id.cd_unpass:
        break;
      case R.id.cd_finish:
        break;
      case R.id.cd_pass:
        break;
      case R.id.iv_more_all:
        break;
      case R.id.cd_download:
        startActivity(new Intent(this, DownloadManageActivity.class));

        break;
    }
  }


}
