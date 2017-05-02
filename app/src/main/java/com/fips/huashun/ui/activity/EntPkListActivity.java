package com.fips.huashun.ui.activity;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.common.ConstantsCode;
import com.fips.huashun.holder.PkListHolder;
import com.fips.huashun.modle.bean.PkListModel;
import com.fips.huashun.modle.bean.PkListModel.RankInfoItem;
import com.fips.huashun.modle.event.PkLikeEvent;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.SystemBarTintManager;
import com.fips.huashun.ui.utils.ToastUtil;
import com.fips.huashun.ui.utils.Utils;
import com.fips.huashun.widgets.HeadTagIamge;
import com.fips.huashun.widgets.HkTextView;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lzy.okgo.OkGo;
import com.shuyu.common.CommonRecyclerAdapter;
import com.shuyu.common.CommonRecyclerManager;
import com.shuyu.common.model.RecyclerBaseModel;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import de.greenrobot.event.EventBus;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Response;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.EasyPermissions.PermissionCallbacks;

/**
 * 企业活动PK榜 公司：锦绣氘(武汉)科技有限公司 kevin
 */
public class EntPkListActivity extends BaseActivity implements PermissionCallbacks {
  private static final String TAG = "EntPkListActivity";
  @Bind(R.id.pk_back)
  ImageView mPkBack;
  @Bind(R.id.pk_rg)
  RadioGroup mPkRg;
  @Bind(R.id.pk_share)
  ImageView mPkShare;
  @Bind(R.id.pk_ll_top)
  LinearLayout mPkLlTop;
  @Bind(R.id.pk_icon_toptwo)
  HeadTagIamge mPkIconToptwo;
  @Bind(R.id.pk_icontwo)
  RelativeLayout mPkIcontwo;
  @Bind(R.id.pk_tv_twoname)
  HkTextView mPkTvTwoname;
  @Bind(R.id.pk_tv_twocommon)
  HkTextView mPkTvTwocommon;
  @Bind(R.id.pk_tv_twojob)
  HkTextView mPkTvTwojob;
  @Bind(R.id.pk_tv_dianzantwo)
  HkTextView mPkTvDianzantwo;
  @Bind(R.id.pk_icon_topone)
  HeadTagIamge mPkIconTopone;
  @Bind(R.id.pk_icon_one)
  RelativeLayout mPkIconOne;
  @Bind(R.id.pk_tv_onename)
  HkTextView mPkTvOnename;
  @Bind(R.id.pk_tv_onecommon)
  HkTextView mPkTvOnecommon;
  @Bind(R.id.pk_tv_onejob)
  HkTextView mPkTvOnejob;
  @Bind(R.id.pk_tv_dianzanone)
  HkTextView mPkTvDianzanone;
  @Bind(R.id.pk_icon_topthreee)
  HeadTagIamge mPkIconTopthreee;
  @Bind(R.id.pk_iconthree)
  RelativeLayout mPkIconthree;
  @Bind(R.id.pk_tv_threename)
  HkTextView mPkTvThreename;
  @Bind(R.id.pk_tv_threecommon)
  HkTextView mPkTvThreecommon;
  @Bind(R.id.pk_tv_threejob)
  HkTextView mPkTvThreejob;
  @Bind(R.id.pk_tv_dianzanthree)
  HkTextView mPkTvDianzanthree;
  @Bind(R.id.pk_tv_department)
  HkTextView mPkTvDepartment;
  @Bind(R.id.pk_tv_job)
  HkTextView mPkTvJob;
  @Bind(R.id.pk_xrcy_rank)
  XRecyclerView mPkXrcyRank;
  @Bind(R.id.pk_ll_toprank)
  LinearLayout mPkLlToprank;
  @Bind(R.id.pk_rank_title)
  ImageView mPkRankTitle;
  @Bind(R.id.pk_tv_rank)
  HkTextView mPkTvRank;
  @Bind(R.id.pk_ll_bottom)
  LinearLayout mPkLlBottom;
  @Bind(R.id.activity_ent_pk_list)
  RelativeLayout mActivityEntPkList;
  @Bind(R.id.ib_exam)
  ImageButton mIbExam;
  @Bind(R.id.ib_student)
  ImageButton mIbStudent;
  @Bind(R.id.ib_teacher)
  ImageButton mIbTeacher;
  @Bind(R.id.tag_common)
  HkTextView mTagCommon;
  @Bind(R.id.the_second)
  LinearLayout mTheSecond;
  @Bind(R.id.the_first)
  LinearLayout mTheFirst;
  @Bind(R.id.the_third)
  LinearLayout mTheThird;
  private CommonRecyclerManager mCommonRecyclerManager;
  private List<RecyclerBaseModel> datalist = new ArrayList<>();
  private List<RankInfoItem> mPkListModels = new ArrayList<>();
  private CommonRecyclerAdapter mCommonRecyclerAdapter;
  private String activityid;
  private Gson mGson;
  private ToastUtil mToastUtil;
  private String type = ConstantsCode.STRING_ONE;//类型考试成绩
  private String showtype = ConstantsCode.STRING_ZERO;//部门成绩1为部门名称0为职务名称
  private int currentRank;//当前排名
  private boolean topOneLiked;
  private boolean topTwoLiked;
  private boolean topThreeLiked;
  private int likeCountOne;
  private int likeCountTwo;
  private int likeCountThree;
  private List<RankInfoItem> mFirstthreelist = new ArrayList<>();
  private List<RankInfoItem> mRankList = new ArrayList<>();


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_ent_pk_list);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      setTranslucentStatus(true);
      SystemBarTintManager tintManager = new SystemBarTintManager(this);
      tintManager.setStatusBarTintEnabled(true);
      tintManager.setStatusBarTintResource(R.color.pk_bg_bule);//通知栏所需颜色
    }
    ButterKnife.bind(this);
    activityid = getIntent().getStringExtra("activityid");
    mGson = new Gson();
    EventBus.getDefault().register(this);
    mToastUtil = ToastUtil.getInstant();
    initView();
    //显示考试成绩按照部门来排序的
    initData(ConstantsCode.STRING_ONE, ConstantsCode.STRING_ZERO);
  }
  public void initView() {
    //列表数据
    mCommonRecyclerManager = new CommonRecyclerManager();
    mCommonRecyclerManager.addType(PkListHolder.ID, PkListHolder.class.getName());
    mCommonRecyclerAdapter = new CommonRecyclerAdapter(this, mCommonRecyclerManager, datalist);
    mPkXrcyRank.setLayoutManager(new LinearLayoutManager(this));
    mPkXrcyRank.setPullRefreshEnabled(false);//屏蔽下拉刷新
    mPkXrcyRank.setAdapter(mCommonRecyclerAdapter);
  }
  private void initData(final String type, final String showtype) {
    mPkListModels.clear();
    OkGo.post(Constants.LOCAL_RANKLIST)
        .params("active_id", activityid)
        .params("type", type)
        .params("show_type", showtype)
        .execute(new com.lzy.okgo.callback.StringCallback() {
          @Override
          public void onBefore(com.lzy.okgo.request.BaseRequest request) {
            super.onBefore(request);
            showLoadingDialog();
          }

          @Override
          public void onSuccess(String s, Call call, Response response) {
            Log.i("test","联网请求获得的数据为 ："+s);
            dimissLoadingDialog();
            PkListModel pkListModel = mGson.fromJson(s, PkListModel.class);
            mPkListModels = pkListModel.getData();
            if (mPkListModels.size() < 0) {
              return;
            }
            //显示排名信息
            for (int i = 0; i < mPkListModels.size(); i++) {
              RankInfoItem dataBean = mPkListModels.get(i);
              if (PreferenceUtils.getUserId().equals(dataBean.getUserid())) {
                currentRank = i + 1;
                break;
              }
            }
            mPkTvRank
                .setText(
                    "我当前的排名：第" + currentRank + "名已经超越了" + (mPkListModels.size() - currentRank)
                        + "名小伙伴");
            if (mPkListModels.size() >= 3) {
              for (int i = 0; i < mPkListModels.size(); i++) {
                if (i < 3) {
                  mFirstthreelist.add(mPkListModels.get(i));
                } else {
                  mRankList.add(mPkListModels.get(i));
                }
              }
            } else {
              mFirstthreelist = mPkListModels;
            }
            //更新界面显示前3名的
            if (mFirstthreelist.size() > 0) {
              UpDataUi(mFirstthreelist);
            }
            if (mRankList != null && mRankList.size() > 0) {
              for (RankInfoItem rankInfoItem : mRankList) {
                rankInfoItem.setResLayoutId(PkListHolder.ID);
              }
              setDatas(mRankList);
              if (mCommonRecyclerAdapter != null) {
                mPkXrcyRank.setVisibility(View.VISIBLE);
                mCommonRecyclerAdapter.notifyDataSetChanged();
              }
            } else {
              mPkXrcyRank.setVisibility(View.GONE);
            }
          }
          @Override
          public void onError(Call call, Response response, Exception e) {
            super.onError(call, response, e);
            dimissLoadingDialog();
          }
        });
  }

  public void setDatas(List datas) {
    this.datalist = datas;
    if (mCommonRecyclerAdapter != null) {
      mCommonRecyclerAdapter.setListData(datas);
    }
  }

  private void UpDataUi(List<RankInfoItem> firstthreelist) {
    for (int i = 0; i < firstthreelist.size(); i++) {
      RankInfoItem dataBean = firstthreelist.get(i);
      if (dataBean!=null){
        showRankInfo(dataBean, i);
      }
    }
  }
  //显示前三
  private void showRankInfo(RankInfoItem dataBean, int i) {
    String img_path = dataBean.getImg_path();
    String tag_name = dataBean.getTag_name();
    String name = dataBean.getName();
    String dep_name = dataBean.getDep_name();
    String praise = dataBean.getPraise();
    String score = dataBean.getScore();
    if (score != null) {
      score = score.length() > 3 ? score.substring(0, 3) : score;
    }
    if (i == 0) {
      mTheFirst.setVisibility(View.VISIBLE);
      likeCountOne = Integer.parseInt(praise==null?"0":praise);
      mTheFirst.setVisibility(View.VISIBLE);
      mPkIconTopone.setBorderWidth(2);
      mPkIconTopone.loadHeadIamge(getApplicationContext(), img_path);
      if (tag_name != null) {
        mPkIconTopone.setLableVisible(true);
        mPkIconTopone.setLableSize(25);
        mPkIconTopone.setLableText(tag_name);
      } else {
        mPkIconTopone.setLableVisible(false);
      }
      mPkTvOnecommon.setText(score == null ? "" : score);
      mPkTvOnename.setText(name == null ? "" : name);
      mPkTvOnejob.setText(dep_name == null ? "" : dep_name);
      mPkTvDianzanone.setText(praise == null ? "" : praise);
    }
    if (i == 1) {
      mTheSecond.setVisibility(View.VISIBLE);
      likeCountTwo = Integer.parseInt(praise==null?"0":praise);
      mTheSecond.setVisibility(View.VISIBLE);
      mPkIconToptwo.setBorderWidth(2);
      mPkIconToptwo.loadHeadIamge(this, img_path);
      if (tag_name != null) {
        mPkIconToptwo.setLableVisible(true);
        mPkIconToptwo.setLableSize(25);
        mPkIconToptwo.setLableText(dataBean.getTag_name());
      } else {
        mPkIconToptwo.setLableVisible(false);
      }
      mPkTvTwocommon.setText(score == null ? "" : score);
      mPkTvTwoname.setText(name == null ? "" : name);
      mPkTvTwojob.setText(dep_name == null ? "" : dep_name);
      mPkTvDianzantwo.setText(praise == null ? "" : praise);
    }
    if (i == 2) {
      mTheThird.setVisibility(View.VISIBLE);
      likeCountThree = Integer.parseInt(praise==null?"0":praise);
      mTheThird.setVisibility(View.VISIBLE);
      mPkIconTopthreee.setBorderWidth(2);
      mPkIconTopthreee.loadHeadIamge(this, img_path);
      if (tag_name != null) {
        mPkIconTopthreee.setLableVisible(true);
        mPkIconTopthreee.setLableSize(25);
        mPkIconTopthreee.setLableText(dataBean.getTag_name());
      } else {
        mPkIconTopthreee.setLableVisible(false);
      }
      mPkTvThreecommon.setText(score == null ? "" : score);
      mPkTvThreename.setText(name == null ? "" : name);
      mPkTvThreejob.setText(dep_name == null ? "" : dep_name);
      mPkTvDianzanthree.setText(praise == null ? "" : praise);
    }
  }

  @Override
  public boolean isSystemBarTranclucent() {
    return false;
  }
  //点击事件
  @OnClick({R.id.pk_back, R.id.pk_share, R.id.ib_exam, R.id.ib_student, R.id.ib_teacher,
      R.id.pk_tv_department, R.id.pk_tv_job, R.id.pk_tv_dianzanone, R.id.pk_tv_dianzantwo,
      R.id.pk_tv_dianzanthree})
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.pk_back:
        finish();
        break;
      case R.id.pk_share:
        //截屏
        //检查权限
        checkPermission();
        break;
      case R.id.ib_exam://考试成绩
        mTheFirst.setVisibility(View.INVISIBLE);
        mTheSecond.setVisibility(View.INVISIBLE);
        mTheThird.setVisibility(View.INVISIBLE);
        mTagCommon.setText("考试成绩");
        clearList();
        mPkRankTitle.setImageResource(R.drawable.test_scores_title);
        type = "1";
        initData(type, showtype);
        break;
      case R.id.ib_student://学员评分
       mTheFirst.setVisibility(View.INVISIBLE);
        mTheSecond.setVisibility(View.INVISIBLE);
        mTheThird.setVisibility(View.INVISIBLE);
        mTagCommon.setText("学员评分");
        clearList();
        mPkRankTitle.setImageResource(R.drawable.students_scores_title);
        type = "3";
        initData(type, showtype);
        break;
      case R.id.ib_teacher://讲师评分
        mTheFirst.setVisibility(View.INVISIBLE);
        mTheSecond.setVisibility(View.INVISIBLE);
        mTheThird.setVisibility(View.INVISIBLE);
        mTagCommon.setText("讲师评分");
        clearList();
//        mPkListModels.clear();
        mPkRankTitle.setImageResource(R.drawable.pk_lecturermark_title);
        type = "2";
        initData(type, showtype);
        break;
      case R.id.pk_tv_department://岗位
        clearList();
        //  mPkTvJob.setBackgroundResource(0);
        mPkTvJob.setTextColor(getResources().getColor(R.color.white));
        // mPkTvDepartment.setBackgroundResource(R.drawable.pk_switchjob);
        mPkTvDepartment.setTextColor(getResources().getColor(R.color.enterprise_act__text));
        initData(type, "0");
        break;
      case R.id.pk_tv_job://部门
        clearList();
        // mPkTvJob.setBackgroundResource(R.drawable.pk_switchjob);
        mPkTvJob.setTextColor(getResources().getColor(R.color.enterprise_act__text));
        //mPkTvDepartment.setBackgroundResource(0);
        mPkTvDepartment.setTextColor(getResources().getColor(R.color.white));
        initData(type, "1");
        break;
      case R.id.pk_tv_dianzanone:
        if (topOneLiked) {
          return;
        }
        if (mPkListModels.size()>0&&mPkListModels.get(0) != null) {
          mPkTvDianzanone.setBackgroundResource(R.drawable.pk_dianzanhou);
          likeCountOne++;
          mPkTvDianzanone.setText(likeCountOne + "");
          sendLike(mPkListModels.get(0).getUserid());
          topOneLiked = true;
        } else {
          return;
        }
        break;
      case R.id.pk_tv_dianzantwo:
        if (topTwoLiked) {
          return;
        }
        if (mPkListModels.get(1) != null) {
          mPkTvDianzantwo.setBackgroundResource(R.drawable.pk_dianzanhou);
          likeCountTwo++;
          mPkTvDianzantwo.setText(likeCountTwo + "");
          sendLike(mPkListModels.get(1).getUserid());
          topTwoLiked = true;
        } else {
          return;
        }
        break;
      case R.id.pk_tv_dianzanthree:
        if (topThreeLiked) {
          return;
        }
        if (mPkListModels.get(2) != null) {
          mPkTvDianzanthree.setBackgroundResource(R.drawable.pk_dianzanhou);
          likeCountThree++;
          mPkTvDianzanthree.setText(likeCountThree+"");
          sendLike(mPkListModels.get(2).getUserid());
          topThreeLiked = true;
        } else {
          return;
        }
        break;
    }
  }
  //检查权限
  private void checkPermission() {
    String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE};
    if (EasyPermissions.hasPermissions(this, permission)) {
      GetandSaveCurrentImage();
    } else {
      EasyPermissions.requestPermissions(this, "分享功能需要您开启读写内存卡权限",
          ConstantsCode.DOWN_LOAD_PERMISSION, permission);
    }
  }

  @Override
  public void onPermissionsGranted(int requestCode, List<String> perms) {
    GetandSaveCurrentImage();
  }

  @Override
  public void onPermissionsDenied(int requestCode, List<String> perms) {

  }


  /**
   * 获取和保存当前屏幕的截图
   */
  private void GetandSaveCurrentImage() {
    //1.构建Bitmap
    WindowManager windowManager = getWindowManager();
    Display display = windowManager.getDefaultDisplay();
    int w = display.getWidth();
    int h = display.getHeight();
    Bitmap Bmp = Bitmap.createBitmap(w, h, Config.ARGB_8888);
    //2.获取屏幕
    View decorview = this.getWindow().getDecorView();
    decorview.setDrawingCacheEnabled(true);
    Bmp = decorview.getDrawingCache();
    String SavePath = getSDCardPath() + "/qmct/ScreenImage/" + activityid;
    //3.保存Bitmap
    try {
      File path = new File(SavePath);
      //文件
      String filepath = SavePath + "/Screen_" + Utils.getRandom(100) + ".png";
      File file = new File(filepath);
      if (!path.exists()) {
        path.mkdirs();
      }
      if (!file.exists()) {
        file.createNewFile();
      }
      FileOutputStream fos = null;
      fos = new FileOutputStream(file);
      if (null != fos) {
        Bmp.compress(CompressFormat.PNG, 90, fos);
        fos.flush();
        fos.close();
        sharePkIamge(filepath);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  //分享本地图片
  private void sharePkIamge(final String filepath) {
    com.umeng.socialize.Config.REDIRECT_URL = "http://sns.whalecloud.com/sina2/callback";
    new ShareAction(EntPkListActivity.this)
        .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE,
            SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
        .setShareboardclickCallback(new ShareBoardlistener() {
          @Override
          public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
            File file = new File(filepath);
            UMImage image = new UMImage(EntPkListActivity.this, file);
            new ShareAction(EntPkListActivity.this)
                .setPlatform(share_media)
                .withTitle("小伙伴们快来看我在PK榜中的成绩，当前排名：" + currentRank + "名")
                .withMedia(image)
                .setCallback(umShareListener)
                .share();
          }
        }).open();
  }

  public void onEventMainThread(PkLikeEvent event) {
    //当点击了点赞
    String uid = event.getUid();
    sendLike(uid);
  }

  //发送点赞
  private void sendLike(String uid) {
    OkGo.post(Constants.LOCAL_SETPRISE)
        .params("uid", uid)
        .execute(new com.lzy.okgo.callback.StringCallback() {
          @Override
          public void onSuccess(String s, Call call, Response response) {
            Log.i("dianzan",s.toString());
          }
        });
  }

  /**
   * 友盟分享
   */
  private UMShareListener umShareListener = new UMShareListener() {
    @Override
    public void onResult(SHARE_MEDIA platform) {
      Log.d("plat", "platform" + platform);
      if (platform.name().equals("WEIXIN_FAVORITE")) {
        ToastUtil.getInstant().show("收藏成功啦");
      } else {
        ToastUtil.getInstant().show("分享成功啦");
      }
    }
    @Override
    public void onError(SHARE_MEDIA platform, Throwable t) {
      ToastUtil.getInstant().show("分享失败啦");
      if (t != null) {
        Log.d("throw", "throw:" + t.getMessage());
      }
    }
    @Override
    public void onCancel(SHARE_MEDIA platform) {
      ToastUtil.getInstant().show("分享取消了");
    }
  };

  /**
   * 获取SDCard的目录路径功能
   */
  private String getSDCardPath() {
    File sdcardDir = null;
    //判断SDCard是否存在
    boolean sdcardExist = Environment.getExternalStorageState()
        .equals(Environment.MEDIA_MOUNTED);
    if (sdcardExist) {
      sdcardDir = Environment.getExternalStorageDirectory();
    }
    return sdcardDir.toString();
  }

  //清除原来集合
  private void clearList() {
    if (mRankList!=null&&mRankList.size() > 0) {
      mRankList.clear();
    }
    if (mFirstthreelist!=null&&mFirstthreelist.size() > 0) {
      mFirstthreelist.clear();
    }
    if (mPkListModels!=null&&mPkListModels.size()>0){
      mPkListModels.clear();
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    clearList();
    EventBus.getDefault().unregister(this);
  }
}

