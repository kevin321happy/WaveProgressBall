package com.fips.huashun;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.SDKInitializer;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.fips.huashun.common.Constants;
import com.fips.huashun.modle.bean.UserInfo;
import com.fips.huashun.ui.utils.CrashHandler;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.SPUtils;
import com.fips.huashun.ui.utils.SharePreferenceUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.OkHttpNetworkExecutor;
import com.yanzhenjie.nohttp.cache.DBCacheStore;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import java.io.File;
import mabeijianxi.camera.VCamera;
import mabeijianxi.camera.util.DeviceUtils;
import okhttp3.Call;
import okhttp3.Response;

public class ApplicationEx extends Application {

  private static ApplicationEx mInstance = null;
  private static ApplicationEx mApplicationEx;
  private SharePreferenceUtil mSPUtil;
  private static Handler handler;
  private static RequestQueue mRequestQueue;
  /**
   * 用户信息
   */
  private UserInfo userInfo;
  public static DisplayImageOptions options, head_options, home_teacher_options, home_activity_options, lecturer_info_options, enterprise_act_options, all_course_options, home_course_options,
      activity_detail_options;
  public static BDLocation location = null;
  /**
   * 是否安装后第一次使用
   */
  public boolean isFirstRun = true;
  private String registrationId;

  // private HttpResponseCache MultiDex;
  @Override
  public void onCreate() {
    super.onCreate();
    mApplicationEx = this;
    handler = new Handler();
    CrashHandler.getInstance().init(this);//初始化全局异常管理
    mInstance = this;
    UMShareAPI.get(this);
    isFirstRun();
    initImageLoader(getApplicationContext());
    initNoHttp();
    initOkGo();
    initFresco();
    SDKInitializer.initialize(this);//百度初始化
    initUmeng();
    initJPush();
//    initRecord();
    // initText();//加载字体
  }

  //初始化视频录制配置
  private void initRecord() {
    //设置拍摄视屏的缓存路径
    File recordFeil = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    //检测是否是中心的手机
    if (DeviceUtils.isZte()) {
      if (recordFeil.exists()) {
        VCamera.setVideoCachePath(recordFeil + "/smallvideo/");
      } else {
        VCamera.setVideoCachePath(recordFeil.getPath().replace("/sdcard/",
            "/sdcard-ext/")
            + "/smallvideo/");
      }
    } else {
      VCamera.setVideoCachePath(recordFeil + "/smallvideo/");
    }
    VCamera.setDebugMode(true);
    VCamera.initialize(this);
  }
  //初始化友盟(分享,統計)
  private void initUmeng() {
    mSPUtil = new SharePreferenceUtil(this, Config.SharePreferenceName);
    //微信 appid appsecret
    PlatformConfig.setWeixin("wxeb3dd68d3477ccae", "0890596d1e9902cbc3934debdf3307f4");
    //新浪微博 appkey appsecret
    PlatformConfig.setSinaWeibo("2703054333", "86215b93805bc58481c3cb556a93fbcc");
    // QQ和Qzone appid appkey
    PlatformConfig.setQQZone("1105648823", "eTHzuML8N5J5FdYv");
    com.umeng.socialize.Config.REDIRECT_URL = "http://sns.whalecloud.com/sina2/callback";
    //友盟统计
    MobclickAgent.setDebugMode(true);//打开调试模式
    MobclickAgent.setScenarioType(getApplicationContext(), MobclickAgent.EScenarioType.E_UM_NORMAL);
    MobclickAgent.startWithConfigure(new MobclickAgent.UMAnalyticsConfig(getApplicationContext(),
        "585ca2e5677baa515600144d", "${UMENG_CHANNEL_VALUE})",
        MobclickAgent.EScenarioType.E_UM_NORMAL));
    MobclickAgent.openActivityDurationTrack(false);//禁止默认的页面统计方式
    MobclickAgent.enableEncrypt(false);//日志加密
  }

  //初始化OkGo
  private void initOkGo() {
//必须调用初始化
    OkGo.init(this);
    //以下设置的所有参数是全局参数,同样的参数可以在请求的时候再设置一遍,那么对于该请求来讲,请求中的参数会覆盖全局参数
    //好处是全局参数统一,特定请求可以特别定制参数
    try {
      //以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
      OkGo.getInstance()
          //打开该调试开关,控制台会使用 红色error 级别打印log,并不是错误,是为了显眼,不需要就不要加入该行
          .debug("OkGo")
          //如果使用默认的 60秒,以下三行也不需要传
          .setConnectTimeout(OkGo.DEFAULT_MILLISECONDS)  //全局的连接超时时间
          .setReadTimeOut(OkGo.DEFAULT_MILLISECONDS)     //全局的读取超时时间
          .setWriteTimeOut(OkGo.DEFAULT_MILLISECONDS)    //全局的写入超时时间
          //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍 https://github.com/jeasonlzy/
//          .setCacheMode(CacheMode.NO_CACHE)
          //可以全局统一设置缓存时间,默认永不过期,具体使用方法看 github 介绍
          .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
          //如果不想让框架管理cookie,以下不需要
//                .setCookieStore(new MemoryCookieStore())                //cookie使用内存缓存（app退出后，cookie消失）
          .setCookieStore(
              new com.lzy.okgo.cookie.store.PersistentCookieStore()); //cookie持久化存储，如果cookie不过期，则一直有效
      //可以设置https的证书,以下几种方案根据需要自己设置,不需要不用设置
//                    .setCertificates()                                  //方法一：信任所有证书
//                    .setCertificates(getAssets().open("srca.cer"))      //方法二：也可以自己设置https证书
//                    .setCertificates(getAssets().open("aaaa.bks"), "123456", getAssets().open("srca.cer"))//方法三：传入bks证书,密码,和cer证书,支持双向加密
      //可以添加全局拦截器,不会用的千万不要传,错误写法直接导致任何回调不执行
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        return chain.proceed(chain.request());
//                    }
//                })

      //这两行同上,不需要就不要传
//          .addCommonHeaders(headers)                                         //设置全局公共头
//          .addCommonParams(params);                                          //设置全局公共参数
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  //初始化fresco
  private void initFresco() {
    //简单的初始化
    Fresco.initialize(this);
  }

  private void initText() {
    Constants.TF_HANYI_FONT = Typeface.createFromAsset(this.getAssets(), "fonts/hanyi.ttf");
    Constants.TF_HKHB_FONT = Typeface.createFromAsset(this.getAssets(), "fonts/hkhb.ttf");
    Constants.TC_HKWW_FONT = Typeface.createFromAsset(this.getAssets(), "fonts/hkww.ttc");
  }

  //极光推送
  private void initJPush() {
    //初始化极光推送
    JPushInterface.init(this);
    JPushInterface.setDebugMode(true);
    registrationId = JPushInterface.getRegistrationID(this);
    String userId = PreferenceUtils.getUserId();
    if (userId != null) {
      SetRegistrationid(userId, registrationId);
    }
    SPUtils.put(this, "registrationId", registrationId);
  }

  //极光发送registrationId
  private void SetRegistrationid(String uid, String registrationId) {
    OkGo.post(Constants.LOCAL_SET_REGISTRATIONID)
        .params("uid", uid)
        .params("registrationid", registrationId)
        .execute(new com.lzy.okgo.callback.StringCallback() {
          @Override
          public void onSuccess(String s, Call call, Response response) {
            Log.i("test", s);
          }
        });
  }

  //初始化nohttp配置
  private void initNoHttp() {
    RequestQueue requestQueue = NoHttp.newRequestQueue();
    NoHttp.initialize(this, new NoHttp.Config()
        .setNetworkExecutor(new OkHttpNetworkExecutor())//底层为okhttp
        .setCacheStore(new DBCacheStore(this).setEnable(true))//数据库缓存
        .setConnectTimeout(30 * 1000)
        .setReadTimeout(10 * 1000)
    );
  }

  //单例模式获取队列对象
  public static RequestQueue getQueueInstant() {
    if (mRequestQueue == null) {
      mRequestQueue = new RequestQueue(3);
    }
    return mRequestQueue;
  }

  public static void initImageLoader(Context context) {
    // 用户默认头像
    head_options = new DisplayImageOptions.Builder()
        .showImageOnLoading(R.drawable.user_head_default)
        .showImageForEmptyUri(R.drawable.user_head_default)
        .showImageOnFail(R.drawable.user_head_default).cacheInMemory(true)
        .cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565).build();
    //
    options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.icon_image)
        .showImageForEmptyUri(R.drawable.icon_image).showImageOnFail(R.drawable.icon_image)
        .cacheInMemory(true)
        .cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565).build();
    // 讲师列表默认头像
    home_teacher_options = new DisplayImageOptions.Builder()
        .showImageOnLoading(R.drawable.home_teacher_head_default)
        .showImageForEmptyUri(R.drawable.home_teacher_head_default)
        .showImageOnFail(R.drawable.home_teacher_head_default).cacheInMemory(true)
        .cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565).build();
    //
    home_activity_options = new DisplayImageOptions.Builder()
        .showImageOnLoading(R.drawable.home_activtiy_default)
        .showImageForEmptyUri(R.drawable.home_activtiy_default)
        .showImageOnFail(R.drawable.home_activtiy_default).cacheInMemory(true)
        .cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565).build();
    // 首页行业课程
    home_course_options = new DisplayImageOptions.Builder()
        .showImageOnLoading(R.drawable.home_industrycourse_head_default)
        .showImageForEmptyUri(R.drawable.home_industrycourse_head_default)
        .showImageOnFail(R.drawable.home_industrycourse_head_default).cacheInMemory(true)
        .cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565).build();
    // 讲师详情头像
    lecturer_info_options = new DisplayImageOptions.Builder()
        .showImageOnLoading(R.drawable.lecturer_info_head_default)
        .showImageForEmptyUri(R.drawable.lecturer_info_head_default)
        .showImageOnFail(R.drawable.lecturer_info_head_default).cacheInMemory(true)
        .cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565).build();
    //
    enterprise_act_options = new DisplayImageOptions.Builder()
        .showImageOnLoading(R.drawable.enterprise_act_head_default)
        .showImageForEmptyUri(R.drawable.enterprise_act_head_default)
        .showImageOnFail(R.drawable.enterprise_act_head_default).cacheInMemory(true)
        .cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565).build();
    // 所有和搜索课程
    all_course_options = new DisplayImageOptions.Builder()
        .showImageOnLoading(R.drawable.all_course_head_default)
        .showImageForEmptyUri(R.drawable.all_course_head_default)
        .showImageOnFail(R.drawable.all_course_head_default).cacheInMemory(true)
        .cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565).build();
    // 活动专区详情
    activity_detail_options = new DisplayImageOptions.Builder()
        .showImageOnLoading(R.drawable.activity_detail_heand_default)
        .showImageForEmptyUri(R.drawable.activity_detail_heand_default)
        .showImageOnFail(R.drawable.activity_detail_heand_default).cacheInMemory(true)
        .cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565).build();
    //参数配置管理二级缓存和三级缓存
    ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
        .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
        .memoryCacheSizePercentage(20).diskCacheFileCount(100)
        .memoryCache(new LruMemoryCache(2 * 1024 * 1024)).memoryCacheSize(2 * 1024 * 1024)
        .discCacheSize(50 * 1024 * 1024).discCacheFileCount(100).defaultDisplayImageOptions(options)
        .discCacheFileNameGenerator(new Md5FileNameGenerator())
        .tasksProcessingOrder(QueueProcessingType.LIFO)
        .build();
    ImageLoader.getInstance().init(config);
  }

  /**
   * ApplicationEx
   */
  public static ApplicationEx getInstance() {
    return mInstance;
  }

  /**
   * SharePreference
   */
  public synchronized SharePreferenceUtil getSPUtil() {
    if (mSPUtil == null) {
      mSPUtil = new SharePreferenceUtil(this, Config.SharePreferenceName);
    }
    return mSPUtil;
  }

  // 判断是否联网
  public boolean isNetworkConnected(Context context) {
    if (context != null) {
      ConnectivityManager mConnectivityManager = (ConnectivityManager) context
          .getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
      if (mNetworkInfo != null) {
        return mNetworkInfo.isAvailable();
      }
    }
    return false;
  }

  public boolean isLogined() {
    boolean isLogined = false;
    String token = PreferenceUtils.getUserId();
    isLogined = !TextUtils.isEmpty(token);
    return isLogined;
  }

  public UserInfo getUserInfo() {
    if (userInfo != null) {
      return userInfo;
    } else {
      if (isLogined()) {
        //将userInfo 从文件中读出来
        String userInfoBeanStr = PreferenceUtils.getUserInfoBean();
        if (!TextUtils.isEmpty(userInfoBeanStr)) {
          Gson gson = new Gson();
          return gson.fromJson(PreferenceUtils.getUserInfoBean(), new TypeToken<UserInfo>() {
          }.getType());
        }
      }
      return null;
    }
  }

  public void setUserInfo(UserInfo userInfo) {
    this.userInfo = userInfo;
    SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
    sharedPreferences.edit().putString("userId", userInfo.getUserid()).apply();
    Gson gson = new Gson();
    String userInfoStr = gson.toJson(userInfo);
    PreferenceUtils.setUserInfoBean(userInfoStr); //将userinfo 序列化到本地
  }

  /**
   * 是否第一次使用App
   */
  private void isFirstRun() {
    SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
    isFirstRun = sharedPreferences.getBoolean("isFirst", true);
  }

  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    MultiDex.install(this);
//    MultiDex.install(this);
  }

  public static Handler getHandler() {
    return handler;
  }

  public static ApplicationEx get() {
    return mApplicationEx;
  }
}
