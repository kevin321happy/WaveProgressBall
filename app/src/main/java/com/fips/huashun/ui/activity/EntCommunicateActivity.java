package com.fips.huashun.ui.activity;

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
        break;
      case R.id.iv_head_group_chat:
        //发起群聊
        break;
    }
  }
}
