package com.fips.huashun.receiver;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

/**
 * description: 自定义消息接受器,接受消息后跳转到聊天界面
 * autour: Kevin
 * company:锦绣氘(武汉)科技有限公司
 * date: 2017/5/11 16:35
 * update: 2017/5/11
 * version: 1.21
 * 站在峰顶 看世界
 * 落在谷底 思人生
*/
public class RongNotificationReceiver extends PushMessageReceiver {

  @Override
  public boolean onNotificationMessageArrived(Context context,
      PushNotificationMessage pushNotificationMessage) {
    return false;////设为true时，当有推送消息来临,点击通知消息会自动进入聊天
  }

  @Override
  public boolean onNotificationMessageClicked(Context context,
      PushNotificationMessage pushNotificationMessage) {
    //跳转到聊天界面
    Intent intent = new Intent();
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    Uri.Builder builder = Uri.parse("rong://" + context.getPackageName()).buildUpon();
    builder.appendPath("conversation")
        .appendPath(pushNotificationMessage.getConversationType().getName())
        .appendQueryParameter("targetId", pushNotificationMessage.getTargetId())
        .appendQueryParameter("title", pushNotificationMessage.getTargetUserName());
    Uri uri = builder.build();
    intent.setData(uri);
    context.startActivity(intent);
    return true;//设为true时,当有消息推送来临，自定义推送消息+点击事件
  }
}
