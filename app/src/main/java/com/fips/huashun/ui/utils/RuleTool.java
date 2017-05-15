package com.kevin.interdebug;

import android.content.Context;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RuleTool {
  private Map<String, String> signMap = new HashMap<>();
  //获取签名后的数据
  public static String[] getSignData(Context context,Map<String, String> map) {
    List<String> dataList = new ArrayList<>();
    StringBuffer signString = new StringBuffer();
    StringBuffer str = new StringBuffer();
    //传入信息放在数组中
    String[] signArray = new String[2];
    //遍历map集合将key值存入新的集合
    for (String key : map.keySet()) {
      dataList.add(key);
    }
    //将data集合排序
    // 升序
    Collections.sort(dataList);
    for (String s : dataList) {
      Log.i("test", "排完序之后 " + s);
      signString.append("&" + s + "=" + map.get(s));
      str.append(s + "|");
    }
    String s  = SPUtils.getString(context, "sk");

    String md5 = Md5Utils.MD5(s);
    signString.append("&" + md5.toLowerCase());
    String substring = signString.toString().substring(1);
    signArray[0] = Md5Utils.MD5(substring).toLowerCase();
    StringBuffer finStr = str.deleteCharAt(str.length() - 1);
    signArray[1] = finStr.toString();
    if (signArray != null && signArray.length > 0) {
      return signArray;
    }
    return null;
  }

  //清除原来集合
  public static void clearList() {

  }
  /*
  * appsecret='从服务器端获取'
    参数说明：
    key=value
    key1=value1
    key2=value2
    timestamp=13432123321
    sign升成规则如下:
        1、以key升序排列
        2、时间戳也在升序排列内
        3、appsecret需要md5.
    signData = md5(key=value&key1=value1&key2=value2&timestamp=13432123321&md5(appsecret));

    发送数据格式为：
    {
        key:value,
        key1:value1,
        key2:value2,
        timestamp:13432123321,
        sign:signData,
        str:key|key1|key2|timestamp
    }
  * */
}
