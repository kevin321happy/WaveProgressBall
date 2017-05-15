package com.fips.huashun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fips.huashun.R;
import com.fips.huashun.db.dao.DepartmentDao;
import com.fips.huashun.modle.dbbean.DepartmentEntity;
import java.util.List;

public class EntCommunicateActivity extends BaseActivity {

  @Bind(R.id.ib_head_back)
  ImageButton mIbHeadBack;
  @Bind(R.id.tv_head_title)
  TextView mTvHeadTitle;
  @Bind(R.id.iv_head_single_chat)
  ImageView mIvHeadSingleChat;
  @Bind(R.id.iv_head_group_chat)
  ImageView mIvHeadGroupChat;
  @Bind(R.id.fl_content)
  FrameLayout mFlContent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_ent_communicate);
    ButterKnife.bind(this);
    initView();
  }

  @Override
  public void initView() {
    mTvHeadTitle.setText("企业通讯");
  }

  @Override
  public boolean isSystemBarTranclucent() {
    return false;
  }

  @OnClick({R.id.ib_head_back, R.id.iv_head_single_chat, R.id.iv_head_group_chat})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.ib_head_back:
        finish();
        break;
      case R.id.iv_head_single_chat:
        //发起单聊
        DepartmentDao departmentDao = new DepartmentDao(this);
        List<DepartmentEntity> departmentEntities = departmentDao.queryAllDepartments();
        for (DepartmentEntity departmentEntity : departmentEntities) {

        }
        startActivity(new Intent(this,EntOrganizationActivity.class));
        break;
      case R.id.iv_head_group_chat:
        Intent intent = new Intent(this, EntOrganizationActivity.class);
        intent.putExtra("type","1");
        startActivity(intent);

        //发起群聊
//        MemberDao memberDao = new MemberDao(this);
//        List<MemberEntity> memberEntityList = memberDao.queryAllMembers();
//        for (MemberEntity memberEntity : memberEntityList) {
//
//        }
        break;
    }
  }
}
