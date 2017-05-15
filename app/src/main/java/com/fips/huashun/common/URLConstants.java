package com.fips.huashun.common;


public class URLConstants {


  public static final String BASE_URL = "http://172.16.3.30/";
  /**
   * 获取app_secret接口
   */
  public static final String APP_GETSECRET = BASE_URL+"app/getSecret";
  /**
   * 用户登录
   */
  public static final String USER_LOGIN = BASE_URL+"user_login";
  /**
   * 上传用户版本信息
   */
  public static final String UPLOAD_USER_VERSION = BASE_URL+"uploadUserVersion";
  /**
   * 获取短信验证
   */
  public static final String GET_PHONECODE = BASE_URL+"getPhoneCode";
  /**
   * 获取组织架构
   */
  public static final String GET_ORGANIZATIONLIST =BASE_URL+ "organ/getOrganizationList";

}
