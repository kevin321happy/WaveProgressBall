package com.fips.huashun.receiver;

//import static android.content.ContentValues.TAG;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;
import com.fips.huashun.common.ConstantsCode;
import com.fips.huashun.ui.activity.EnterpriseActDetail;
import com.fips.huashun.ui.activity.EnterpriseActList;
import com.fips.huashun.ui.activity.EnterpriseMycourseActivity;
import com.fips.huashun.ui.activity.WebviewActivity;
import com.fips.huashun.ui.fragment.MainActivity;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.SPUtils;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 接受极光推送的消息 kevin
 */

public class PushNewsReceiver extends BroadcastReceiver {

  public static final String TAG = "PushNewsReceiver";
  private Context mContext;

  @Override
  public void onReceive(Context context, Intent intent) {
    this.mContext = context;
    Bundle bundle = intent.getExtras();
    Log.d(TAG, "onReceive - " + intent.getAction());
    if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
    } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
      System.out.println("收到了自定义消息。消息内容是：" + bundle.getString(JPushInterface.EXTRA_MESSAGE));
      // 自定义消息不会展示在通知栏，完全要开发者写代码去处理
    } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
      System.out.println("收到了通知");
      Log.i("jp", "用户收到了通知");
      // 在这里可以做些统计，或者做些其他工作
    } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
      //点击了通知
      String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
      System.out.println("附加字段：" + extras);
      try {
        JSONObject jsonObject = new JSONObject(extras);
        String type = jsonObject.get("type") + "";
        if (type.equals("1")) {
          //活动ID，跳转到活动列表
          String activityid = (String) jsonObject.get("activityid");
          Intent intent1 = new Intent(context, EnterpriseActDetail.class);
          intent1.putExtra("activityid", activityid);
          intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          context.startActivity(intent1);
        }
        if (type.equals("2")) {
          //跳转到调研
          String vote_questionnaire_id = jsonObject.get("vote_questionnaire_id") + "";
          Intent intent2 = new Intent(context, WebviewActivity.class);
          intent2.putExtra("sessoinid", vote_questionnaire_id);
          intent2.putExtra("type", ConstantsCode.STRING_ONE);
          intent2.putExtra("key", 16);
          intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          context.startActivity(intent2);
        }
        if (type.equals("3")) {
          //跳转到企业我的课程
          String is_must = jsonObject.get("is_must") + "";
          Intent intent3 = new Intent(context, EnterpriseMycourseActivity.class);

          String enterpriseid = SPUtils.getString(context, "enterprise");
          if (enterpriseid!=null){
            intent3.putExtra("enterpriseId",enterpriseid);
            intent3.putExtra("type", is_must);
            intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent3);
          }

        }
      } catch (JSONException e) {
        e.printStackTrace();
      }
    } else {
      Log.d(TAG, "Unhandled intent - " + intent.getAction());
    }
  }

  // 打印所有的 intent extra 数据
  private static String printBundle(Bundle bundle) {
    StringBuilder sb = new StringBuilder();
    for (String key : bundle.keySet()) {
      if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
        sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
      } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
        sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
      } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
        if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
          Log.i(TAG, "This message has no Extra data");
          continue;
        }
        try {
          JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
          Iterator<String> it = json.keys();

          while (it.hasNext()) {
            String myKey = it.next().toString();
            sb.append("\nkey:" + key + ", value: [" +
                myKey + " - " + json.optString(myKey) + "]");
          }
        } catch (JSONException e) {
          Log.e(TAG, "Get message extra JSON error!");
        }

      } else {
        sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
      }
    }
    return sb.toString();
  }
}
