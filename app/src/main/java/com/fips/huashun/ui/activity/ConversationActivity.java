package com.fips.huashun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.fips.huashun.R;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.ToastUtil;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Conversation.ConversationType;
import java.util.Locale;

/**
 * description: 聊天界面
 * autour: Kevin
 * company:锦绣氘(武汉)科技有限公司
 * date: 2017/5/10 19:16
 * update: 2017/5/10
 * version: 1.21
 * 站在峰顶 看世界
 * 落在谷底 思人生
 */
public class ConversationActivity extends FragmentActivity {

  @Bind(R.id.NavigationBar)
  NavigationBar mNavigationBar;
  private String mTargetId;
  private String mTargetIds;
  private String mTitle1;
  private ConversationType mConversationType;
//  @Bind(R.id.conversation)
//  android.widget.fragment mConversation;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.conversation);
    ButterKnife.bind(this);
   Intent intent= getIntent();
    mTargetId = intent.getData().getQueryParameter("targetId");
    mTargetIds = intent.getData().getQueryParameter("targetIds");
    mTitle1 = intent.getData().getQueryParameter("title");
    mConversationType = Conversation.ConversationType
        .valueOf(intent.getData().getLastPathSegment().toUpperCase(Locale.getDefault()));
    initView();
  }

  private void initView() {
    mNavigationBar.setTitle(mTitle1);
    mNavigationBar.setRightButton(R.drawable.icon_single_chat);
    mNavigationBar.setLeftImage(R.drawable.fanhui);
    mNavigationBar.setListener(new NavigationBar.NavigationListener()
    {
      @Override
      public void onButtonClick(int button)
      {
        if (button == NavigationBar.LEFT_VIEW)
        {
          finish();
        } else if (button == NavigationBar.RIGHT_VIEW)
        {
          ToastUtil.getInstant().show("单身");
        }
      }
    });

  }
}
