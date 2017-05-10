package com.fips.huashun.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on 2016/12/18.
 * 缓存常量
 */
public class CacheConstans {

  private static List mChooseMembers;

  private CacheConstans() {
  }

  /**
   * 单例获取已经选中的用户的集合
   */
  public static List getChooseMember() {
    if (mChooseMembers == null) {
      mChooseMembers = new ArrayList();
    }
    return mChooseMembers;
  }


  /*首页的json数据*/
  public static final java.lang.String HOME_INFO_JOSN = "homeinfo_json";

  /*搜索界面信息*/
  public static final java.lang.String SEARCHER_INFO_JSON = "searcherinfo_json";

  /*裘马界面信息*/
  public static final java.lang.String ME_INFO_JSON = "meinfo_json";

  /*有企业信息的草堂界面*/
  public static final java.lang.String OWNENTERPISE_INFO_JSON = "ownenterpise_info_json";

  /*课程视频的缓存*/
  public static final String VEDIO_CACHE = "coursevedio_caceh";

  /*课程详情界面*/
  public static final java.lang.String COURSEDETAIL_INFO_JSON = "coursedetail_info";

  /*课程详情界面课程播放路径*/
  public static final java.lang.String COURSEVEDIO_PLAY_INFO_JSON = "coursevedio_play_info";
}
