package com.fips.huashun.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.fips.huashun.R;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.NavigationBar.NavigationListener;
import com.fips.huashun.widgets.CustomEditText;

/**
 * description: 企业组织架构 autour: Kevin company:锦绣氘(武汉)科技有限公司 date: 2017/4/20 18:53 update: 2017/4/20
 * version: 1.21 站在峰顶 看世界 落在谷底 思人生
 */

public class EntOrganizationActivity extends BaseActivity {

  @Bind(R.id.nb_orgainization)
  NavigationBar mNbOrgainization;
  @Bind(R.id.et_search_friend)
  CustomEditText mEtSearchFriend;
  @Bind(R.id.cb_check_all)
  CheckBox mCbCheckAll;
  @Bind(R.id.ll_member)
  LinearLayout mLlMember;
  @Bind(R.id.rcv_member)
  RecyclerView mRcvMember;
  @Bind(R.id.rcv_department)
  RecyclerView mRcvDepartment;
  @Bind(R.id.ll_department)
  LinearLayout mLlDepartment;
  @Bind(R.id.tv_choosed)
  TextView mTvChoosed;
  @Bind(R.id.btn_confirm_choose)
  Button mBtnConfirmChoose;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_ent_organization);
    ButterKnife.bind(this);
    initData();


  }

  private void initData() {



  }

  public void initView() {
    mNbOrgainization.setTitle("企业圈");
    mNbOrgainization.setLeftImage(R.drawable.ico_back);
    mNbOrgainization.setListener(new NavigationListener() {
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
}
