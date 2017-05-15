package com.fips.huashun.ui.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.fips.huashun.ApplicationEx;


/**
 * Created by lgpeng on 2016/2/16 0016.
 */
public class PreferenceUtils {

  public static final String CHECK_TOKEN = "access_token";
  public static final String USER_ID = "member_id";
  public static final String LOGIN_TIME = "login_time";
  public static final String USER_INFO = "user_info_bean";
  public static final String USER_PWD = "pwd";
  public static final String USER_Name = "login_name";
  public static final String CITY_Name = "city";
  public static final String readstatus = "readstatus";
  public static final String Registerid_ID = "registerid";

  public static final String RY_TOKEN = "ry_token";
  public static final String QMCT_TOKEN = "qmct_token";
  public static final String APP_SECRET="app_secret";

  public static SharedPreferences getSharedPreferences(Context ctx) {
    return ctx.getSharedPreferences("easycode", Context.MODE_PRIVATE);
  }

  //设置融云的Token
  public static void setAPP_Secret(String token) {
    getSharedPreferences(ApplicationEx.getInstance()).edit().putString(APP_SECRET, token).apply();

  }

  //获取融云的Token
  public static String getAPP_Secret(String token) {
    return getSharedPreferences(ApplicationEx.getInstance()).getString(APP_SECRET, "");

  }

  //设置融云的Token
  public static void setRY_Token(String token) {
    getSharedPreferences(ApplicationEx.getInstance()).edit().putString(RY_TOKEN, token).apply();

  }

  //获取融云的Token
  public static String getRY_Token(String token) {
    return getSharedPreferences(ApplicationEx.getInstance()).getString(RY_TOKEN, "");

  }

  //设置裘马的Token
  public static void setQM_Token(String token) {
    getSharedPreferences(ApplicationEx.getInstance()).edit().putString(QMCT_TOKEN, token).apply();

  }

  //获取裘马的Token
  public static String getQM_Token() {
    return getSharedPreferences(ApplicationEx.getInstance()).getString(QMCT_TOKEN, "");

  }

  public static void setToken(String token) {
    getSharedPreferences(ApplicationEx.getInstance()).edit().putString(CHECK_TOKEN, token).apply();

  }

  public static String getToken() {
    return getSharedPreferences(ApplicationEx.getInstance()).getString(CHECK_TOKEN, "");
  }

  /**
   * 存放boolean值
   */
  public static void setBoolean(Context ctx, String key, boolean value) {
    getSharedPreferences(ctx).edit().putBoolean(key, value).apply();
  }

  /**
   * 取出boolean值
   */
  public static boolean getBoolean(Context ctx, String key) {
    return getSharedPreferences(ctx).getBoolean(key, false);
  }

  /**
   * 保存userId
   */
  public static void setUserId(String id) {
    getSharedPreferences(ApplicationEx.getInstance()).edit().putString(USER_ID, id).apply();

  }

  /**
   * 获取userId
   */
  public static String getUserId() {
    return getSharedPreferences(ApplicationEx.getInstance()).getString(USER_ID, "");
  }

  /**
   * 保存用户第一次登陆时间
   */
  public static void setLoginTime(String loginTime) {
    getSharedPreferences(ApplicationEx.getInstance()).edit().putString(LOGIN_TIME, loginTime)
        .apply();

  }

  /**
   * 获取用户第一次登陆时间
   */
  public static String getLoginTime() {
    return getSharedPreferences(ApplicationEx.getInstance()).getString(LOGIN_TIME, "");
  }

  /**
   * 去掉用户第一次登陆时间
   */
  public static void removeLoginTime() {
    getSharedPreferences(ApplicationEx.getInstance()).edit().remove("LOGIN_TIME").apply();

  }

  /**
   * 保存到userInfo
   */
  public static void setUserInfoBean(String str) {
    getSharedPreferences(ApplicationEx.getInstance()).edit().putString(USER_INFO, str).apply();

  }

  /**
   * 读取userInfo
   */
  public static String getUserInfoBean() {
    return getSharedPreferences(ApplicationEx.getInstance()).getString(USER_INFO, "");
  }

  /**
   * 保存pwd
   */
  public static void setPwd(String pwd) {
    getSharedPreferences(ApplicationEx.getInstance()).edit().putString(USER_PWD, pwd).apply();

  }

  /**
   * 获取pwd
   */
  public static String getPwd() {
    return getSharedPreferences(ApplicationEx.getInstance()).getString(USER_PWD, "");
  }

  /**
   * 城市
   */
  public static void setCity(String city) {
    getSharedPreferences(ApplicationEx.getInstance()).edit().putString(CITY_Name, city).apply();

  }

  /**
   * 获取城市
   */
  public static String getCity() {
    return getSharedPreferences(ApplicationEx.getInstance()).getString(CITY_Name, "");
  }

  /**
   * 获取是否有消息
   */
  public static void setReadstatus(String id) {
    getSharedPreferences(ApplicationEx.getInstance()).edit().putString(readstatus, id).apply();

  }

  /**
   * 获取是否有消息
   */
  public static String getReadstatus() {
    return getSharedPreferences(ApplicationEx.getInstance()).getString(readstatus, "");
  }

  public static void setLoginName(String id) {
    getSharedPreferences(ApplicationEx.getInstance()).edit().putString(USER_Name, id).apply();

  }

  public static String getLoginName() {
    return getSharedPreferences(ApplicationEx.getInstance()).getString(USER_Name, "");
  }

  public static void setRegisterid_ID(String id) {
    getSharedPreferences(ApplicationEx.getInstance()).edit().putString(Registerid_ID, id).apply();

  }

  public static String getRegisterid_ID() {
    return getSharedPreferences(ApplicationEx.getInstance()).getString(Registerid_ID, "");
  }
}
