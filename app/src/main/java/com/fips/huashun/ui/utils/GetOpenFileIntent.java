package com.fips.huashun.ui.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import java.io.File;
import java.util.List;

/**
 * Created by kevin on 2017/2/26.
 * 获取打开文件的intent
 */

public class GetOpenFileIntent {
  //android获取一个用于打开PPT文件的intent
  public static Intent getPptFileIntent( String param )
  {
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.addCategory("android.intent.category.DEFAULT");
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    Uri uri = Uri.fromFile(new File(param ));
    intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
    return intent;
  }

  //android获取一个用于打开Excel文件的intent
  public static Intent getExcelFileIntent( String param )
  {
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.addCategory("android.intent.category.DEFAULT");
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    Uri uri = Uri.fromFile(new File(param ));
    intent.setDataAndType(uri, "application/vnd.ms-excel");
    return intent;
  }

  //android获取一个用于打开Word文件的intent
  public static Intent getWordFileIntent( String param )
  {
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.addCategory("android.intent.category.DEFAULT");
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    Uri uri = Uri.fromFile(new File(param));
    intent.setDataAndType(uri, "application/msword");
    return intent;
  }

  //android获取一个用于打开CHM文件的intent
  public static Intent getChmFileIntent( String param )
  {
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.addCategory("android.intent.category.DEFAULT");
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    Uri uri = Uri.fromFile(new File(param ));
    intent.setDataAndType(uri, "application/x-chm");
    return intent;
  }

  //android获取一个用于打开文本文件的intent
  public static Intent getTextFileIntent( String param, boolean paramBoolean)
  {
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.addCategory("android.intent.category.DEFAULT");
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    if (paramBoolean)
    {
      Uri uri1 = Uri.parse(param );
      intent.setDataAndType(uri1, "text/plain");
    }
    else
    {
      Uri uri2 = Uri.fromFile(new File(param ));
      intent.setDataAndType(uri2, "text/plain");
    }
    return intent;
  }
  //android获取一个用于打开PDF文件的intent
  public static Intent getPdfFileIntent( String param )
  {
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.addCategory("android.intent.category.DEFAULT");
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    Uri uri = Uri.fromFile(new File(param ));
    intent.setDataAndType(uri, "application/pdf");
    return intent;
  }
  //android获取一个用于打开图片文件的intent
  public static Intent getIamgeFileIntent( String param )
  {
    Intent intent = new Intent();
    intent.setAction(android.content.Intent.ACTION_VIEW);
    intent.addCategory("android.intent.category.DEFAULT");
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    Uri uri = Uri.fromFile(new File(param ));
    intent.setDataAndType(uri, "image/*");
    return  intent;
  }

//判断该意图是否存在
  public   static  boolean isIntentAvailable(Context context, Intent intent) {
    final PackageManager packageManager = context.getPackageManager();
    List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent,0);
    return resolveInfos.size() > 0;
  }
}
