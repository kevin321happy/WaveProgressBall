package com.fips.huashun.ui.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

/**
 * 获取设备信息的工具类
 */
public class DeviceUtil {

  public static DeviceUtil mInstance;

  public DeviceUtil() {

  }
  /**
   * 获取设备宽度（px）
   */
  public static int deviceWidth(Context context) {
    return context.getResources().getDisplayMetrics().widthPixels;
  }

  /**
   * 获取设备高度（px）
   */
  public static int deviceHeight(Context context) {
    return context.getResources().getDisplayMetrics().heightPixels;
  }

  /**
   * 返回版本名字 对应build.gradle中的versionName
   */
  public static String getVersionName(Context context) {
    String versionName = null;
    try {
      PackageManager packageManager = context.getPackageManager();
      PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
      versionName = packInfo.versionName;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return versionName;
  }

  /**
   * 返回版本号 对应build.gradle中的versionCode
   */
  public static String getVersionCode(Context context) {
    String versionCode = null;
    try {
      PackageManager packageManager = context.getPackageManager();
      PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
      versionCode = String.valueOf(packInfo.versionCode);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return versionCode;
  }

  /**
   * 获取设备的唯一标识，deviceId
   */
  public static String getDeviceId(Context context) {
    TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    String deviceId = tm.getDeviceId();
    if (deviceId == null) {
      return "-";
    } else {
      return deviceId;
    }
  }

  /**
   * 获取手机品牌
   */
  public static String getPhoneBrand() {
    return android.os.Build.BRAND;
  }
  /**
   * 获取手机型号
   * @return
   */
  public static String getPhoneModel() {
    return android.os.Build.MODEL;
  }


  /**
   * 获取手机Android API等级（22、23 ...）
   */
  public static int getBuildLevel() {
    return android.os.Build.VERSION.SDK_INT;
  }

  /**
   * 获取手机Android 版本（4.4、5.0、5.1 ...）
   */
  public static String getBuildVersion() {
    return android.os.Build.VERSION.RELEASE;
  }

  public static DeviceUtil getInstance() {
    if (mInstance == null) {
      mInstance = new DeviceUtil();
    }
    return mInstance;
  }
}
