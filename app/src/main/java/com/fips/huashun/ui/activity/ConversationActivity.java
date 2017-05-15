package com.fips.huashun.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.fips.huashun.R;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.NavigationBar.NavigationListener;
import com.fips.huashun.ui.utils.SPUtils;
import com.fips.huashun.ui.utils.ToastUtil;
import io.rong.imkit.RongIM;
import io.rong.imkit.RongIM.ConversationBehaviorListener;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.MessageTag;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.RongIMClient.ConnectCallback;
import io.rong.imlib.RongIMClient.ErrorCode;
import io.rong.imlib.RongIMClient.TypingStatusListener;
import io.rong.imlib.TypingMessage.TypingStatus;
import io.rong.imlib.model.Conversation.ConversationType;
import io.rong.imlib.model.UserInfo;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;
import java.util.Collection;
import java.util.Iterator;
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
//  PRIVATE   DISCUSSION

  private final int SET_TEXT_TYPING_TITLE = 100;//正在输入
  private final int SET_VOICE_TYPING_TITLE = 200;//正在讲话
  private final int SET_TARGETID_TITLE = 300;//显示标题
  Handler mHandler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      switch (msg.what) {
        case SET_TEXT_TYPING_TITLE:
          ToastUtil.getInstant().show("对方正在输入...");
          mNavigationBar.setTitle("对方正在输入...");
          break;
        case SET_VOICE_TYPING_TITLE:
          mNavigationBar.setTitle("对方正在讲话...");
          ToastUtil.getInstant().show("对方正在讲话...");
          break;
        case SET_TARGETID_TITLE:
          mNavigationBar.setTitle(mTitle1);
          break;
      }
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.conversation);
    ButterKnife.bind(this);
    Intent intent = getIntent();
    getIntentData(intent);
    initView();
    initListener();
    initTypingStatusListener();
    isReconnect(intent);


  }

  //初始化监听
  private void initListener() {
//设置会话界面的操作的监听器
    RongIM.setConversationBehaviorListener(new OnConversationBehaviorListener());
  }

  //获取传递过来的数据
  private void getIntentData(Intent intent) {
    mTargetId = intent.getData().getQueryParameter("targetId");
    mTargetIds = intent.getData().getQueryParameter("targetIds");
//    intent.getData().getQueryParameter()
    mTitle1 = intent.getData().getQueryParameter("title");
//    ToastUtil.getInstant().show("传递进来的ID:" + mTargetId + "群聊的ID ：" + mTargetIds);
    mConversationType = ConversationType
        .valueOf(intent.getData().getLastPathSegment().toUpperCase(Locale.getDefault()));
    //个人会话还是群会话
    enterFragment(mConversationType, mTargetId);
  }
  private void enterFragment(ConversationType conversationType, String targetId) {
    ConversationFragment fragment = (ConversationFragment) getSupportFragmentManager()
        .findFragmentById(R.id.conversation);
    Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
        .appendPath("conversation").appendPath(mConversationType.getName().toLowerCase())
        .appendQueryParameter("targetId", mTargetId).build();
    fragment.setUri(uri);
  }

  private void initView() {
    mNavigationBar.setTitle(mTitle1);
    if (mConversationType == ConversationType.DISCUSSION) {
      mNavigationBar.setRightButton(R.drawable.rc_ext_tab_add);
    }
    mNavigationBar.setLeftImage(R.drawable.fanhui);
    mNavigationBar.setListener(new NavigationListener() {
      @Override
      public void onButtonClick(int button) {
        if (button == NavigationBar.LEFT_VIEW) {
          finish();
        } else if (button == NavigationBar.RIGHT_VIEW) {
          Intent intent = new Intent(ConversationActivity.this, EntOrganizationActivity.class);
          intent.putExtra("type", "2");
          intent.putExtra("targetId", mTargetId);
          startActivity(intent);
        }
      }
    });
  }

  //用户行为监听
  class OnConversationBehaviorListener implements ConversationBehaviorListener {

    //点击用户头像执行
    @Override
    public boolean onUserPortraitClick(Context context, ConversationType conversationType,
        UserInfo userInfo) {
      //跳转到用户详情
//      Intent intent = new Intent(ConversationActivity.this, UserDetailActivtiy.class);
//      Bundle bundle = new Bundle();
//      bundle.putParcelable("user",userInfo);
//      intent.putExtra("bundle",bundle);
//      startActivity(intent);
//      Logger.d("点击了用户头像");
      return true;
    }

    //长按用户头像执行
    @Override
    public boolean onUserPortraitLongClick(Context context, ConversationType conversationType,
        UserInfo userInfo) {
      return false;
    }

    //点击消息执行
    @Override
    public boolean onMessageClick(Context context, View view, io.rong.imlib.model.Message message) {
      return false;
    }

    //点击消息链接执行
    @Override
    public boolean onMessageLinkClick(Context context, String s) {
      return false;
    }

    //长按消息执行
    @Override
    public boolean onMessageLongClick(Context context, View view,
        io.rong.imlib.model.Message message) {
      return false;
    }
  }

  //对方输入状态的监听
  private void initTypingStatusListener() {
    RongIMClient.setTypingStatusListener(new TypingStatusListener() {
      @Override
      public void onTypingStatusChanged(ConversationType type, String targetId,
          Collection<TypingStatus> typingStatusSet) {
        //当输入状态的会话类型和targetID与当前会话一致时，才需要显示
        if (type.equals(mConversationType) && targetId.equals(mTargetId)) {
          int count = typingStatusSet.size();
          //count表示当前会话中正在输入的用户数量，目前只支持单聊，所以判断大于0就可以给予显示了
          if (count > 0) {
            Iterator iterator = typingStatusSet.iterator();
            TypingStatus status = (TypingStatus) iterator.next();
            String objectName = status.getTypingContentType();
            MessageTag textTag = TextMessage.class.getAnnotation(MessageTag.class);
            MessageTag voiceTag = VoiceMessage.class.getAnnotation(MessageTag.class);
            //匹配对方正在输入的是文本消息还是语音消息
            if (objectName.equals(textTag.value())) {
              Log.i("test", "正在输入....");
              mHandler.sendEmptyMessage(SET_TEXT_TYPING_TITLE);
            } else if (objectName.equals(voiceTag.value())) {
              Log.i("test", "正在讲话....");
              mHandler.sendEmptyMessage(SET_VOICE_TYPING_TITLE);
            }
          } else {//当前会话没有用户正在输入，标题栏仍显示原来标题
            mHandler.sendEmptyMessage(SET_TARGETID_TITLE);
          }
        }
      }
    });
  }

  //判断消息是否是push消息
  private void isReconnect(final Intent intent) {
    //目标的token
    String token = SPUtils.getString(this, "token");
    //push或通知过来的
    if (intent != null && intent.getData() != null && intent.getData().getScheme().equals("rong")) {
      //通过intent.getData().getQueryParameter("push") 为true，判断是否是push消息，发起重连
      if (intent.getData().getQueryParameter("push") != null && intent.getData()
          .getQueryParameter("push").equals("true")) {
        reconnect(token);
      } else {
        //程序进入后台，收到消息后点击进入,会执行这里
        if (RongIM.getInstance() == null || RongIM.getInstance().getRongIMClient() == null) {
          reconnect(token);
        } else {
          enterFragment(mConversationType, mTargetId);
        }
      }
    }
//    Observable.just(PreferenceUtils.getUserId())
//        .map(new Func1<String, String>() {
//          @Override
//          public String call(String s) {
//            String ry_token = new MemberDao(ConversationActivity.this).querMemmberByUid(s)
//                .getRy_token();
//            return ry_token;
//          }
//        }).subscribe(new Action1<String>() {
//      @Override
//      public void call(String ry_token) {
//        //push或通知过来的
//        if (intent != null && intent.getData() != null && intent.getData().getScheme()
//            .equals("rong")) {
//          //通过intent.getData().getQueryParameter("push") 为true，判断是否是push消息，发起重连
//          if (intent.getData().getQueryParameter("push") != null && intent.getData()
//              .getQueryParameter("push").equals("true")) {
//            reconnect(ry_token);
//          } else {
//            //程序进入后台，收到消息后点击进入,会执行这里
//            if (RongIM.getInstance() == null || RongIM.getInstance().getRongIMClient() == null) {
//              reconnect(ry_token);
//            } else {
//              enterFragment(mConversationType, mTargetId);
//            }
//          }
//        }
//      }
//    });
  }

  //重连
  private void reconnect(String token) {
    RongIMClient.connect(token, new ConnectCallback() {
      @Override
      public void onTokenIncorrect() {
        Toast.makeText(ConversationActivity.this, "重连token出错", Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onSuccess(String s) {
        Toast.makeText(ConversationActivity.this, "重连成功", Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onError(ErrorCode errorCode) {
        Toast.makeText(ConversationActivity.this, "重连错误", Toast.LENGTH_SHORT).show();
      }
    });
  }
}
