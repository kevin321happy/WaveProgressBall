package com.fips.huashun.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fips.huashun.R;
import com.fips.huashun.db.dao.DepartmentDao;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

/**
 * description: 会话列表
 * autour: Kevin
 * company:锦绣氘(武汉)科技有限公司
 * date: 2017/5/10 19:43
 * update: 2017/5/10
 * version: 1.21
 * 站在峰顶 看世界
 * 落在谷底 思人生
 */

public class ConversationListActivity extends FragmentActivity {

  @Bind(R.id.ib_head_back)
  ImageButton mIbHeadBack;
  @Bind(R.id.tv_head_title)
  TextView mTvHeadTitle;
  @Bind(R.id.iv_head_single_chat)
  ImageView mIvHeadSingleChat;
  @Bind(R.id.iv_head_group_chat)
  ImageView mIvHeadGroupChat;
  private Object mConversationList;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.conversationlist);
    ButterKnife.bind(this);
    initView();
  }

  //界面可见的时候
  @Override
  protected void onResume() {
    super.onResume();
    //获取融云的会话列表
    getConversationList();
  }

  private void initView() {
    mTvHeadTitle.setText("企业通讯");
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
//        List<DepartmentEntity> departmentEntities = departmentDao.queryAllDepartments();
//        for (DepartmentEntity departmentEntity : departmentEntities) {
//
//        }
        startActivity(new Intent(this, EntOrganizationActivity.class));
        break;
      case R.id.iv_head_group_chat:
        Intent intent = new Intent(this, EntOrganizationActivity.class);
        intent.putExtra("type", "1");
        startActivity(intent);
        break;
    }
  }

  public void getConversationList() {
    // 融云自带会话列表（显示用户列表）
    ConversationListFragment fragment = new ConversationListFragment();
    Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
        .appendPath("conversationlist")
        .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(),
            "ture") //设置私聊会话非聚合显示
        .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//设置群组会话聚合显示
        .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(),
            "false")//设置讨论组会话非聚合显示
        .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//设置系统会话非聚合显示
        .build();
    fragment.setUri(uri);
  }
}
