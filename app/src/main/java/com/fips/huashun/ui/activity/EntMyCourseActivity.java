package com.fips.huashun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fips.huashun.R;
import com.fips.huashun.widgets.CustomEditText;

/**
 * description: 企业我的课程 autour: Kevin company:锦绣氘(武汉)科技有限公司 date: 2017/4/20 9:33 update: 2017/4/20
 * version: 1.21 站在峰顶 看世界 落在谷底 思人生
 */

public class EntMyCourseActivity extends BaseActivity implements TextWatcher {



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

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activtiy_entmycourseactivity);
    ButterKnife.bind(this);

    enterpriseId = getIntent().getStringExtra("enterpriseId");
    initView();
  }

  public void initView() {
    mEtSearchCourse.addTextChangedListener(this);
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

  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    mLlSearch.setVisibility(View.GONE);

  }

  @Override
  public void onTextChanged(CharSequence s, int start, int before, int count) {
    // 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
  }

  @Override
  public void afterTextChanged(Editable s) {
    mLlSearch.setVisibility(View.VISIBLE);

  }
}
