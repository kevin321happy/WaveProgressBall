package com.fips.huashun.ui.utils;

import android.R.integer;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SPUtils {


  // 万能的put方法
  public static void put(Context context, String key, Object values) {
    SharedPreferences sp = context.getSharedPreferences("targetfile", Context.MODE_PRIVATE);
    Editor edit = sp.edit();
    if (values instanceof String) {
      edit.putString(key, (String) values);
    } else if (values instanceof integer) {
      edit.putInt(key, (Integer) values);
    } else if (values instanceof Boolean) {
      edit.putBoolean(key, (Boolean) values);
    }
    edit.commit();

  }

  //清除原来的信息
  public static void clearInfo(Context context, String key) {
    //SharedPreferences sp = context.getSharedPreferences("targetfile", Context.MODE_PRIVATE);
    String string = getString(context, key);
    put(context, key, "");
  }

  public static String getString(Context context, String key) {
    SharedPreferences sp = context.getSharedPreferences("targetfile", Context.MODE_PRIVATE);

    return sp.getString(key, "");
  }

  public static int getInt(Context context, String key) {
    SharedPreferences sp = context.getSharedPreferences("targetfile", Context.MODE_PRIVATE);
    return sp.getInt(key, 0);
  }

  public static boolean getBoolean(Context context, String key) {
    SharedPreferences sp = context.getSharedPreferences("targetfile", Context.MODE_PRIVATE);
    return sp.getBoolean(key, false);
  }


}
