package com.fips.huashun.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fips.huashun.R;
import com.fips.huashun.db.dao.DepartmentDao;
import com.fips.huashun.db.dao.MemberDao;
import com.fips.huashun.modle.dbbean.MemberEntity;
import io.rong.imkit.RongIM;
import io.rong.imkit.RongIM.UserInfoProvider;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import java.util.ArrayList;
import java.util.List;

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
public class ConversationListActivity extends FragmentActivity implements UserInfoProvider {
  @Bind(R.id.ib_head_back)
  ImageButton mIbHeadBack;
  @Bind(R.id.tv_head_title)
  TextView mTvHeadTitle;
  @Bind(R.id.iv_head_single_chat)
  ImageView mIvHeadSingleChat;
  @Bind(R.id.iv_head_group_chat)
  ImageView mIvHeadGroupChat;
  private Object mConversationList;
  private List<MemberEntity> mMembers;
  private List<UserInfo> mMembers1;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.conversationlist);
    //加载列表数据
    mMembers1 = getMembers();
    init();
    RongIM.setUserInfoProvider(this, true);
    ButterKnife.bind(this);
    initView();
  }

  private void init() {
//    融云自带会话列表（显示用户列表）
    ConversationListFragment fragment = new ConversationListFragment();
    Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
        .appendPath("conversationlist")
        .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(),
            "false") //设置私聊会话非聚合显示
        .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//设置群组会话聚合显示
        .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(),
            "false")//设置讨论组会话非聚合显示
        .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//设置系统会话非聚合显示
        .build();
    fragment.setUri(uri);
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    //rong_content 为你要加载的 id
    transaction.add(R.id.conversationlist, fragment);
    transaction.commit();

  }

  //界面可见的时候
  @Override
  protected void onResume() {
    super.onResume();
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



  public List<UserInfo> getMembers() {
    List<UserInfo> list = new ArrayList<>();
    MemberDao memberDao = new MemberDao(this);
    List<MemberEntity> memberEntityList = memberDao.queryAllMembers();
    if (memberEntityList==null){
      return list;
    }
    for (MemberEntity memberEntity : memberEntityList) {
      list.add(new UserInfo(memberEntity.getUid(), memberEntity.getMember_name(),
          Uri.parse(memberEntity.getHead_image())));
    }
    return list;
  }
// for (Friend friend : mFriends) {
//    if (friend.getUserid().equals(s)) {
//      //把用户信息返回给融云的 SDK
//      return new UserInfo(friend.getUserid(), friend.getUsername(), Uri.parse(friend.getPic()));
//    }
//  }
  @Override
  public UserInfo getUserInfo(String s) {
    for (UserInfo userInfo : mMembers1) {
      if (userInfo.getUserId().equals(s)){
        return userInfo;
      }
    }
    return null;
  }
}
