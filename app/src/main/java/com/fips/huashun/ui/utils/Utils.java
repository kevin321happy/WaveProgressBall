package com.fips.huashun.ui.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.StatFs;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utils {

  public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
  //验证手机号

  /**
   * 验证手机格式
   */

  public static boolean isnumber(String s) {
    Pattern p = Pattern.compile("1\\d{10}");
    Matcher m = p.matcher(s);
    boolean b = m.matches();
    return b;
  }

  //判断是否有网络
  public static boolean isNetWork(Context context) {
    //得到网络的管理者
    ConnectivityManager manager = (ConnectivityManager) context
        .getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo info = manager.getActiveNetworkInfo();
    return info != null;
  }

  /**
   * make true current connect service is wifi
   */
  public static boolean isWifi(Context mContext) {
    ConnectivityManager connectivityManager = (ConnectivityManager) mContext
        .getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
    if (activeNetInfo != null
        && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
      return true;
    }
    return false;
  }

  /**
   * 判断服务是否开启
   */
  public static boolean isServiceWork(Context context, String ServiceName) {
    boolean isWork = false;
    ActivityManager am = (ActivityManager) ((Activity) context)
        .getSystemService(Context.ACTIVITY_SERVICE);
    List<RunningServiceInfo> runningServices = am.getRunningServices(40);
    if (runningServices.size() < 0) {
      return false;
    }
    for (int i = 0; i < runningServices.size(); i++) {
      String mName = runningServices.get(i).service.getClassName().toString();
      if (mName.equals(ServiceName)) {
        isWork = true;
        break;
      }
    }
    return isWork;
  }

  /**
   * 检查设备是否存在SDCard的工具方法
   */
  public static boolean hasSdcard() {
    String state = Environment.getExternalStorageState();
    // 有存储的SDCard
    return state.equals(Environment.MEDIA_MOUNTED);
  }

  //保存图片
  public static void saveMyBitmap(Bitmap mBitmap, String pic_pathload) {
    File f = new File("storage/sdcard0/" + pic_pathload);

    FileOutputStream fOut = null;
    try {
      fOut = new FileOutputStream(f);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
    try {
      fOut.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      fOut.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 长整型数字转日期, 返回字符串形式的日期
   */
  public static String getTime(long timeMillis) {
    return sdf.format(new Date(timeMillis));
  }

  public static String getMoney(String money) {
    BigDecimal bd = new BigDecimal(money);
    bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
    return bd.toString();
  }

  public static String[] getStr(String a) {
    String[] split = a.split(",");
    return split;
  }

  /*需要17才能使用*/
  public static Bitmap blurBitmap(Bitmap bitmap, Context context) {

    //Let's create an empty bitmap with the same size of the bitmap we want to blur
    Bitmap outBitmap = Bitmap
        .createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

    //Instantiate a new Renderscript
    RenderScript rs = RenderScript.create(context);

    //Create an Intrinsic Blur Script using the Renderscript
    ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

    //Create the Allocations (in/out) with the Renderscript and the in/out bitmaps
    Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
    Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);

    //Set the radius of the blur
    blurScript.setRadius(10.f);

    //Perform the Renderscript
    blurScript.setInput(allIn);
    blurScript.forEach(allOut);

    //Copy the final bitmap created by the out Allocation to the outBitmap
    allOut.copyTo(outBitmap);

    //recycle the original bitmap
    bitmap.recycle();

    //After finishing everything, we destroy the Renderscript.
    rs.destroy();

    return outBitmap;


  }

  public static int ScreenOrient(Activity activity) {
    int orient = activity.getRequestedOrientation();
    if (orient != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        && orient != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
      WindowManager windowManager = activity.getWindowManager();
      Display display = windowManager.getDefaultDisplay();
      int screenWidth = display.getWidth();
      int screenHeight = display.getHeight();
      orient = screenWidth < screenHeight ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
          : ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
    }
    return orient;
  }

  public static int compareString(String a, String b) {
    double a1 = Double.parseDouble(a);
    double b1 = Double.parseDouble(b);
    BigDecimal a2 = BigDecimal.valueOf(a1);
    BigDecimal b2 = BigDecimal.valueOf(b1);
    int c = a2.compareTo(b2);
    return c;
  }

  /*
 * 将时间转换为时间戳
 */
  public static long dateToStamp(String s) throws ParseException {
    String res;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = simpleDateFormat.parse(s);
    long ts = date.getTime();
    // res = String.valueOf(ts);
    return ts;
  }

  /*
* 将时间戳转化为时间格式的文件路径
*
*/
  public static String stampToDateString(String s) {
    String res;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    long lt = new Long(s);
    Date date = new Date(lt);
    res = simpleDateFormat.format(date);
    String s1 = res.substring(0, res.length() - 2).replaceAll("-", "").replaceAll(":", "")
        .replaceAll(" ", "");
    return s1;
  }

  /*
 * 将时间2016-02-01转化为2016年02月01日
 */
  public static String formatDateString(String activitytime) {
    Log.i("test", activitytime);
    StringBuffer stringBuffer = new StringBuffer(activitytime);
    stringBuffer.replace(4, 5, "年");
    stringBuffer.replace(7, 8, "月");
    return stringBuffer.toString();
  }

  //获取随机数
  public static int getRandom(int a) {
    int val = (int) (Math.random() * a + 1);
    return val; //第一种
  }

  //获取文件的拓展名
  public static String getFileExt(String fileName) {
    String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
    return prefix;
  }

  /**
   * 转换文件大小
   */
  public static String FormentFileSize(long fileS) {
    DecimalFormat df = new DecimalFormat("#.00");
    String fileSizeString = "";
    if (fileS < 1024) {
      fileSizeString = df.format((double) fileS) + "B";
    } else if (fileS < 1048576) {
      fileSizeString = df.format((double) fileS / 1024) + "K";
    } else if (fileS < 1073741824) {
      fileSizeString = df.format((double) fileS / 1048576) + "M";
    } else {
      fileSizeString = df.format((double) fileS / 1073741824) + "G";
    }
    return fileSizeString;
  }

  /**
   * 加载本地图片
   * http://bbs.3gstdy.com
   */
  public static Bitmap getLoacalBitmap(String url) {
    try {
      FileInputStream fis = new FileInputStream(url);
      return BitmapFactory.decodeStream(fis);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 获取当前时间戳的秒值,String类型的
   */
  public static String getCurrentTimestamp() {
    long time = System.currentTimeMillis() / 1000;
    String timestamp = String.valueOf(time);
    return timestamp;
  }
}
