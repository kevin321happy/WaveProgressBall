package com.fips.huashun.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.common.ConstantsCode;
import com.fips.huashun.db.DataDownloadHelp;
import com.fips.huashun.db.dao.DataDownloadDao;
import com.fips.huashun.modle.bean.OnLineCourseModel;
import com.fips.huashun.modle.bean.OnLineCourseModel.RowBean;
import com.fips.huashun.modle.event.CacleDownloadEvent;
import com.fips.huashun.ui.adapter.StudyOnLineAdapter;
import com.fips.huashun.ui.utils.AlertDialogUtils;
import com.fips.huashun.ui.utils.AlertDialogUtils.DialogClickInter;
import com.fips.huashun.ui.utils.DisplayUtil;
import com.fips.huashun.ui.utils.GetOpenFileIntent;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.SPUtils;
import com.fips.huashun.ui.utils.ToastUtil;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.shuyu.gsyvideoplayer.utils.FileUtils;
import com.yanzhenjie.nohttp.download.DownloadQueue;
import de.greenrobot.event.EventBus;
import java.io.File;
import java.util.List;
import okhttp3.Call;
import okhttp3.Response;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by kevin on 2017/1/14. 在线学习
 */
public class StudyOnlineActivity extends BaseActivity implements OnItemClickListener,
    EasyPermissions.PermissionCallbacks {

  @Bind(R.id.na_bar_ent_studyonline)
  NavigationBar mNavigationBar;
  @Bind(R.id.lv_study_online)
  SwipeMenuListView mLv_study_online;
  private String activityid;
  private Gson mGson;
  private StudyOnLineAdapter mStudyOnLineAdapter;
  private DataDownloadDao mDataDownloadDao;
  private DataDownloadHelp mHelp;
  private String downloaddata;//下载资料
  private DownloadQueue mDownloadQueue;
  private String mPid;//资料的id;
  private List<RowBean> mRowBeanList;
  private String mName;
  private String mPath;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_study_online);
    ButterKnife.bind(this);
    // EventBus.getDefault().register(this);
    activityid = getIntent().getStringExtra("activityid");
    //创建文件目录(应用程序data下的目录)
    // File file = getExternalFilesDir("activitydata");
//    String path = file.getPath();
//    mPath =path+"/"+activityid;
    mPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/activitydata" + "/"
        + activityid;
    File filedir = new File(mPath);
    SPUtils.put(this, activityid, mPath);
    if (!filedir.exists()) {
      filedir.mkdirs();
    }
    mGson = new Gson();
    //创建数据库
    mDataDownloadDao = new DataDownloadDao(this);
    mHelp = new DataDownloadHelp(this);
    initView();
    checkPermission();
    initData();
  }

  //检查权限
  private void checkPermission() {
    String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE};
    if (EasyPermissions.hasPermissions(this, permission)) {
      initData();
    } else {
      EasyPermissions.requestPermissions(this, "在线学习需要打开读写手机内存卡权限",
          ConstantsCode.DOWN_LOAD_PERMISSION, permission);
    }
  }
  //权限申请的回调
  @Override
  public void onPermissionsGranted(int requestCode, List<String> perms) {
    //成功
    initData();
  }

  @Override
  public void onPermissionsDenied(int requestCode, List<String> perms) {
    AlertDialog dialog = new AlertDialog.Builder(this)
        .setMessage("在线学习需要赋予访问存储的权限，不开启将无法正常工作！")
        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            Uri packageURI = Uri.parse("package:" + StudyOnlineActivity.this.getPackageName());
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
            startActivity(intent);
            finish();
          }
        })
        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            finish();
          }
        }).create();
    dialog.show();
    return;
  }

  //在线学习列表
  private void initData() {
    OkGo.post(Constants.LOCAL_ONLINE_COURSE_LIST)
        .params("activityid", activityid)
        .execute(new com.lzy.okgo.callback.StringCallback() {
          @Override
          public void onBefore(com.lzy.okgo.request.BaseRequest request) {
            super.onBefore(request);
            showLoadingDialog();
          }

          @Override
          public void onSuccess(String s, Call call, Response response) {
            dimissLoadingDialog();
            OnLineCourseModel onLineCourseModel = mGson.fromJson(s, OnLineCourseModel.class);
            mRowBeanList = onLineCourseModel.getRow();
            //saveToDb(mRowBeanList);
            if (mStudyOnLineAdapter != null) {
              mStudyOnLineAdapter.setData(mRowBeanList);
              mStudyOnLineAdapter.notifyDataSetChanged();
            }
          }

          @Override
          public void onError(Call call, Response response, Exception e) {
            super.onError(call, response, e);
            dimissLoadingDialog();
          }
        });
  }

  //保存到数据库中
  private void saveToDb(final List<RowBean> rowBeanList) {
//    mDataDownloadDao.deleteAll();

  }

  //初始化界面
  @Override
  public void initView() {
    mNavigationBar.setTitle("在线学习");
    mNavigationBar.setLeftImage(R.drawable.fanhui);
    mNavigationBar.setListener(new NavigationBar.NavigationListener() {
      @Override
      public void onButtonClick(int button) {
        if (button == NavigationBar.LEFT_VIEW) {
          finish();
        }
      }
    });
    mStudyOnLineAdapter = new StudyOnLineAdapter(this);
    mLv_study_online.setAdapter(mStudyOnLineAdapter);
    mLv_study_online.setOnItemClickListener(this);
    mLv_study_online.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
    // AccelerateDecelerateInterpolator 在动画开始与结束的地方速率改变比较慢，在中间的时候加速
    mLv_study_online.setOpenInterpolator(new AccelerateDecelerateInterpolator());
    //AnticipateInterpolator 开始的时候向后然后向前甩
    mLv_study_online.setCloseInterpolator(new AnticipateInterpolator());

    SwipeMenuCreator creator = new SwipeMenuCreator() {
      @Override
      public void create(SwipeMenu menu) {
        SwipeMenuItem deleteItem = new SwipeMenuItem(
            getApplicationContext());
        deleteItem.setBackground(getResources().getDrawable(R.color.bg_red));
        deleteItem.setWidth(DisplayUtil.dp2px(StudyOnlineActivity.this, 60));
        deleteItem.setIcon(R.drawable.clsc);
        deleteItem.setTitle("删除");
        menu.addMenuItem(deleteItem);
      }
    };
    mLv_study_online.setMenuCreator(creator);
    mLv_study_online.setOnMenuItemClickListener(mOnMenuItemClickListener);
  }

  //当点击侧滑菜单
  private OnMenuItemClickListener mOnMenuItemClickListener = new OnMenuItemClickListener() {
    @Override
    public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
      //删除文件,删除数据库记录,刷新适配器
      final RowBean rowBean = mRowBeanList.get(position);
      AlertDialogUtils.showTowBtnDialog(StudyOnlineActivity.this, "确定删除已下载的文件？", "取消", "确定",
          new DialogClickInter() {
            @Override
            public void leftClick(AlertDialog dialog) {
              dialog.dismiss();
            }

            @Override
            public void rightClick(AlertDialog dialog) {
              deleteLocalFile(rowBean);
              dialog.dismiss();
            }
          });
      //  deleteLocalFile(rowBean);
      return false;
    }
  };

  //删除本地已经下载的文件
  private void deleteLocalFile(RowBean rowBean) {
    String pid = rowBean.getPid();
    String size = rowBean.getSize();
    String path = rowBean.getPath();
    String ext = rowBean.getExt();
    String filepath = mPath + "/" + pid + "." + ext;
    File file = new File(filepath);
    if (file.exists() && file.length() > 0) {
      //deleteFile(filepath);
      FileUtils.deleteFiles(file);
      mDataDownloadDao.deleteDataByPid(pid);
      ToastUtil.getInstant().show("删除成功");
    }

    mStudyOnLineAdapter.setData(mRowBeanList);
    mStudyOnLineAdapter.notifyDataSetChanged();
  }


  @Override
  public boolean isSystemBarTranclucent() {
    return false;
  }

  //当条目被点击
  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    //获取文件的拓展名
    RowBean rowBean = mRowBeanList.get(position);
    String ext = rowBean.getExt();
    String pid = rowBean.getPid();
    String size = rowBean.getSize();
    String path = rowBean.getPath();
    mName = rowBean.getName();
    Long sizeLong = Long.valueOf(size);
//    String url =
//        Environment.getExternalStorageDirectory() + "/qmct/" + "/activitydata/" + activityid + "/"
//            + pid + "."
//            + ext;
    String url = mPath + "/" + pid + "." + ext;
    //先判断文件是否存在
    //如果是pdf，跳转到pdf阅读器
    if (ext.equals(ConstantsCode.STRING_PDF)) {
      Intent intent = new Intent(StudyOnlineActivity.this, PdfActivity.class);
      intent.putExtra("courseid", pid);
      intent.putExtra("type", ConstantsCode.STRING_ONE);
      intent.putExtra("pdfurl", path);
      startActivity(intent);
    }
    File file = new File(url);
    long length = file.length();
    if (file.exists() && file.length() == sizeLong) {
      //文件已下载，直接打开
      openFile(ext, url);
    } else {
      //文件没下载,先判断是否为视频和音频
      if (ext.equals(ConstantsCode.STRING_MPT) || ext.equals(ConstantsCode.STRING_MPF)) {
        //直接跳转到播放器打开
        Intent intent = new Intent(StudyOnlineActivity.this, VedioPlayActivity.class);
        intent.putExtra("url", path);
        intent.putExtra("filename", mName);
        startActivity(intent);
      } else {
        //非视频音频,先下载,然后打开
        DownLoadFile(path, pid, ext);
      }
    }
  }

  private void DownLoadFile(String path, final String pid, final String ext) {
    //文件目录
    // final String url = Environment.getExternalStorageDirectory() + "/activitydata/" + activityid;
    OkGo.get(path)
        .tag(this)
        .execute(new com.lzy.okgo.callback.FileCallback(mPath, pid + "." + ext) {
          @Override
          public void onSuccess(File file, Call call, Response response) {
            openFile(ext, mPath + "/" + pid + "." + ext);
          }
        });
  }

  //打开文件
  private void openFile(String ext, String url) {
    if (ext.equals(ConstantsCode.STRING_DOC) || ext.equals(ConstantsCode.STRING_DOCX)) {
      Intent wordFileIntent = GetOpenFileIntent.getWordFileIntent(url);
      boolean intentAvailable = GetOpenFileIntent.isIntentAvailable(this, wordFileIntent);
      if (!intentAvailable) {
        ToastUtil.getInstant().show("亲,请先安装office");
        return;
      } else {
        startActivity(wordFileIntent);
      }
      return;
    }
    if (ext.equals(ConstantsCode.STRING_PPT) || ext.equals(ConstantsCode.STRING_PPTX)) {
      Intent pptFileIntent = GetOpenFileIntent.getPptFileIntent(url);
      boolean available = GetOpenFileIntent.isIntentAvailable(this, pptFileIntent);
      if (!available) {
        ToastUtil.getInstant().show("亲,请先安装office");
        return;
      } else {
        startActivity(pptFileIntent);
      }
      return;
    }
    //如果为图片
    if (ext.equals(ConstantsCode.STRING_JPG) || ext.equals(ConstantsCode.STRING_PNG)) {
      Intent iamgeFileIntent = GetOpenFileIntent.getIamgeFileIntent(url);
      startActivity(iamgeFileIntent);
    }
    if (ext.equals(ConstantsCode.STRING_XLSX)) {
      Intent excelFileIntent = GetOpenFileIntent.getExcelFileIntent(url);
      boolean available = GetOpenFileIntent.isIntentAvailable(this, excelFileIntent);
      if (!available) {
        ToastUtil.getInstant().show("亲,请先安装office");
        return;
      } else {
        startActivity(excelFileIntent);
      }
      return;
    }
    if (ext.equals(ConstantsCode.STRING_MPT) || ext.equals(ConstantsCode.STRING_MPF)) {
      //视频音频播放
      Intent intent = new Intent(StudyOnlineActivity.this, VedioPlayActivity.class);
      intent.putExtra("url", url);
      intent.putExtra("filename", mName);
      startActivity(intent);
      return;
    }
    if (!ext.equals(ConstantsCode.STRING_PDF)) {
      ToastUtil.getInstant().show("该文件暂不知打开！");
      return;
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mStudyOnLineAdapter.cacleAllRequest();
    EventBus.getDefault().post(new CacleDownloadEvent(true));
    mHelp.close();//释放资源
  }
}
