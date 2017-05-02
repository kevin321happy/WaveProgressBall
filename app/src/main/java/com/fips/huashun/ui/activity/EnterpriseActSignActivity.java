package com.fips.huashun.ui.activity;

import static com.fips.huashun.common.ConstantsCode.BAIDU_READ_PHONE_STATE;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.fips.huashun.ApplicationEx;
import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.common.ConstantsCode;
import com.fips.huashun.modle.bean.BackInfoModel;
import com.fips.huashun.modle.bean.SignInfoModel;
import com.fips.huashun.ui.adapter.ActivitySigninAdapter;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.ToastUtil;
import com.fips.huashun.ui.utils.Utils;
import com.fips.huashun.widgets.NoScrollListView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.umeng.analytics.MobclickAgent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 功能：活动签到 Created by Administrator on 2016/8/23
 *
 * @author 张柳 时间：2016年8月23日20:43:09
 */
public class EnterpriseActSignActivity extends BaseActivity {

  @Bind(R.id.nb_enterpriseactsign)
  NavigationBar mNbEnterpriseactsign;
  @Bind(R.id.baidu_map)
  MapView mMapView;
  @Bind(R.id.tv_signup_title)
  TextView mTvSignupTitle;
  @Bind(R.id.tv_signup_address)
  TextView mTvSignupAddress;
  @Bind(R.id.lv_singup)
  NoScrollListView mLvSingup;
  @Bind(R.id.tv_signup_starttime)
  TextView mTvSignupStarttime;
  @Bind(R.id.tv_signup_endtime)
  TextView mTvSignupEndtime;
  private BaiduMap mBaiduMap;
  boolean isFirstLoc = true;// 是否首次定位
  LocationClient mLocClient;
  // MyLocationListenner myListener ;
  // 签到按钮
  private ActivitySigninAdapter mActivitySigninAdapter;
  //签到信息
  private List<SignInfoModel.SignInfo> mSingInfoList = new ArrayList<>();
  private String activityid;
  private ToastUtil mToastUtil;
  private Gson mGson;
  private String type;//签到类型
  private String sign_address;
  private String signx;//经度
  private String signy;//纬度
  private boolean mIsDetermineSignTime;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_entactivity_sign);
    ButterKnife.bind(this);
    mGson = new Gson();
    mToastUtil = ToastUtil.getInstant();
    this.activityid = getIntent().getStringExtra("activityid");
    //申请权限
    initView();
    if (Build.VERSION.SDK_INT >= 23) {
      showContacts();
    } else {
      initData();
    }
  }

  //提示权限申请
  private void showContacts() {
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED
        || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED
        || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
        != PackageManager.PERMISSION_GRANTED) {
      Toast.makeText(getApplicationContext(), "没有权限,请手动开启定位权限", Toast.LENGTH_SHORT).show();
      // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
      ActivityCompat.requestPermissions(EnterpriseActSignActivity.this,
          new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
              Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE},
          BAIDU_READ_PHONE_STATE);
    } else {
      initData();
    }
  }

  //Android6.0申请权限的回调方法
  @Override
  public void onRequestPermissionsResult(int requestCode, String[] permissions,
      int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    switch (requestCode) {
      // requestCode即所声明的权限获取码，在checkSelfPermission时传入
      case ConstantsCode.BAIDU_READ_PHONE_STATE:
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
          initData();
        } else {
          // 没有获取到权限，做特殊处理
          Toast.makeText(getApplicationContext(), "获取位置权限失败，请手动开启", Toast.LENGTH_SHORT).show();
        }
        break;
      default:
        break;
    }
  }

  //初始化数据
  private void initData() {
    OkGo.post(Constants.LOCAL_SIGNINFO)
        .params("activityid", activityid)
        .params("uid", PreferenceUtils.getUserId())
        .execute(new com.lzy.okgo.callback.StringCallback() {
          @Override
          public void onBefore(com.lzy.okgo.request.BaseRequest request) {
            super.onBefore(request);
            showLoadingDialog();
          }

          @Override
          public void onSuccess(String s, Call call, Response response) {
            dimissLoadingDialog();
            SignInfoModel signInfoModel = mGson.fromJson(s, SignInfoModel.class);
            UpDataUI(signInfoModel);
          }

          @Override
          public void onError(Call call, Response response, Exception e) {
            super.onError(call, response, e);
            dimissLoadingDialog();
            mToastUtil.show(response.toString());
          }
        });
  }

  private void UpDataUI(SignInfoModel signInfoModel) {
    if (signInfoModel == null) {
      return;
    }
    mTvSignupTitle.setText(signInfoModel.getActivitytitle() + "");
    mTvSignupStarttime.setText(Utils.formatDateString(signInfoModel.getActivitytime()));
    mTvSignupEndtime.setText(Utils.formatDateString(signInfoModel.getActivityendtime()));
    mTvSignupAddress.setText(signInfoModel.getActivityaddre() + "");
    //设置Adapter数据
    mActivitySigninAdapter.setData(signInfoModel.getRow());
    mActivitySigninAdapter.notifyDataSetChanged();
  }

  //当点击了签到
  private ActivitySigninAdapter.OnSignInClickListener mOnSignInClickListener = new ActivitySigninAdapter.OnSignInClickListener() {
    @Override
    public void onSignInClick(int position, String pid, String requiredtime) {
//            try {
//                //发起签到
//                boolean determineSignTime = isDetermineSignTime(requiredtime);
//                if (position == 1) {
//                    // 判断是否在签到范围
//                    type = "1";
//                    if (!determineSignTime) {
//                        mToastUtil.show("请在规定时间内进行签到");
//                        return;
//                    }
//                }
//                if (position == 0) {
//                    // 判断是否在签退范围
//                    type = "2";
//                    if (!determineSignTime) {
//                        mToastUtil.show("请在规定时间内进行签退");
//                        return;
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
      type = position == 1 ? "1" : "2";
      signIn(type, pid);
    }
  };

  //判断签到时间是否在规定范围
  private boolean isDetermineSignTime(String requiredtime) {
    try {
      long currentTimeMillis = System.currentTimeMillis();//当前时间
      long toStamp = Utils.dateToStamp(requiredtime);//要求的时间
      long differTime = toStamp - currentTimeMillis;
      long abs = Math.abs(differTime);
      if (abs <= 3600000 * 2) {
        mIsDetermineSignTime = true;
      } else {
        mIsDetermineSignTime = false;
      }
      //mIsDetermineSignTime = abs <= 3600000 * 2;
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return mIsDetermineSignTime;
  }

  //签到、签退请求
  private void signIn(String type, String pid) {
    OkGo.post(Constants.LOCAL_SIGN)
        .params("activityid", activityid)
        .params("activitysign_testid", pid)
        .params("type", type)
        .params("user_id", PreferenceUtils.getUserId())
        .params("sign_address", sign_address)
        .params("signx", signx)
        .params("signy", signy)
        .execute(new StringCallback() {
          @Override
          public void onSuccess(String s, Call call, Response response) {
            BackInfoModel inBackModel = mGson.fromJson(s, BackInfoModel.class);
            String backInfo = inBackModel.getInfo();
            ToastUtil.getInstant().show(backInfo);
            initData();
          }
        });
  }

  @Override
  public boolean isSystemBarTranclucent() {
    return false;
  }

  @Override
  protected void initView() {
    super.initView();
    initMap();
    mLvSingup.setFocusable(false);
    mNbEnterpriseactsign.setTitle("签到");
    mNbEnterpriseactsign.setLeftImage(R.drawable.fanhui);
    mNbEnterpriseactsign.setListener(new NavigationBar.NavigationListener() {
      @Override
      public void onButtonClick(int button) {
        if (button == NavigationBar.LEFT_VIEW) {
          finish();
        }
      }
    });
    mActivitySigninAdapter = new ActivitySigninAdapter(getApplicationContext());
    mLvSingup.setAdapter(mActivitySigninAdapter);
    mActivitySigninAdapter.setOnSignInClickListener(mOnSignInClickListener);
  }

  //初始化百度地图
  private void initMap() {
    mBaiduMap = mMapView.getMap();
    // 隐藏比例尺控件
    //隐藏缩放按钮
    mMapView.showZoomControls(false);
    mMapView.showScaleControl(false);
    MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
    mBaiduMap.setMapStatus(msu);
    mBaiduMap.setMyLocationEnabled(true);
    mLocClient = new LocationClient(this);
    mLocClient.registerLocationListener(new MyLocationListenner());
    LocationClientOption option = new LocationClientOption();
    option.setOpenGps(true);// 打开gps
    option.setIsNeedAddress(true);
    option.setCoorType("bd09ll"); // 设置坐标类型
    option.setScanSpan(10000);
    mLocClient.setLocOption(option);
    mLocClient.start();
  }

  /**
   * 定位SDK监听函数
   */
  public class MyLocationListenner implements BDLocationListener {

    @Override
    public void onReceiveLocation(BDLocation location) {
      signx = location.getLongitude() + "";
      signy = location.getLatitude() + "";
      // map view 销毁后不在处理新接收的位置
      if (location == null || mMapView == null) {
        return;
      } else {
        MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
            // 此处设置开发者获取到的方向信息，顺时针0-360
            .direction(100).latitude(location.getLatitude()).longitude(location.getLongitude())
            .build();
        mBaiduMap.setMyLocationData(locData);
        if (isFirstLoc) {
          isFirstLoc = false;
          LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
          MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
          mBaiduMap.animateMapStatus(u);
          if (location != null) {
            sign_address = location.getAddrStr().substring(2);
          }
          ApplicationEx.location = location;
        }
      }
    }

    public void onReceivePoi(BDLocation poiLocation) {
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    MobclickAgent.onPageStart("EnterpriseActSignActivity");
  }

  @Override
  public void onPause() {
    mMapView.onPause();
    super.onPause();
    MobclickAgent.onPageEnd("EnterpriseActSignActivity");
  }

  @Override
  public void onDestroy() {
    // 退出时销毁定位
    mLocClient.stop();
    // 关闭定位图层
    mBaiduMap.setMyLocationEnabled(false);
    mMapView.onDestroy();
    mMapView = null;
    super.onDestroy();
  }
}
