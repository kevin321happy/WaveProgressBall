package com.fips.huashun.ui.activity;

import android.Manifest.permission;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.common.ConstantsCode;
import com.fips.huashun.modle.bean.BackInfoModel;
import com.fips.huashun.modle.event.PostVideoEvent;
import com.fips.huashun.ui.adapter.PostStateGridAdapter;
import com.fips.huashun.ui.adapter.PostStateGridAdapter.Callback;
import com.fips.huashun.ui.utils.OssUtils;
import com.fips.huashun.ui.utils.OssUtils.OnLoadoSSListener;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.SystemBarTintManager;
import com.fips.huashun.ui.utils.ToastUtil;
import com.fips.huashun.widgets.ActionSheetDialog;
import com.fips.huashun.widgets.ActionSheetDialog.OnSheetItemClickListener;
import com.fips.huashun.widgets.ActionSheetDialog.SheetItemColor;
import com.fips.huashun.widgets.ToggleButton;
import com.fips.huashun.widgets.ToggleButton.OnToggleChanged;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.BaseRequest;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import mabeijianxi.camera.MediaRecorderActivity;
import mabeijianxi.camera.model.AutoVBRMode;
import mabeijianxi.camera.model.BaseMediaBitrateConfig;
import mabeijianxi.camera.model.MediaRecorderConfig;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by kevin on 2017/1/19. 发布留言\反馈
 */
public class PostStatusActivity extends BaseActivity {

  @Bind(R.id.head_iv_fanhui)
  ImageView mHeadIvFanhui;
  @Bind(R.id.tv_right)
  TextView mTvRight;
  @Bind(R.id.et_leave)
  EditText mEtLeave;
  @Bind(R.id.noScrollgridview)
  GridView mNoScrollgridview;
  @Bind(R.id.tg_allowcommon)
  ToggleButton mTgAllowcommon;
  @Bind(R.id.tv_allowcommon)
  TextView mTvAllowcommon;
  @Bind(R.id.tg_isanonymity)
  ToggleButton mTgIsanonymity;
  @Bind(R.id.tv_anonymity)
  TextView mTvAnonymity;


  private PostStateGridAdapter mGridAdapter;
  private ArrayList<String> mSelectPath = new ArrayList<>();
  private static final int REQUEST_IMAGE = 2;
  private static final int REQUEST_TAILOR = 200;
  private String mEt_leave;
  private ToastUtil mToastUtil;
  private String isdiscuss = ConstantsCode.CANDISCUSS;//允许评论
  private String iscryptonym = ConstantsCode.DISCRYPTONYM;//非匿名
  private String activityid;
  private Gson mGson;
  private int number = 0;
  private String fileSuffix;
  private OssUtils mOssUtils;
  private String picUrls;//图片的路径
  private BackInfoModel mBackInfo;
  private static final int REQUEST_CODE_PERMISSION_PHOTO_PREVIEW = 1;
  private static final int REQUEST_CODE_ADD_MOMENT = 1;
  private StringBuffer mBuffer;
  private String type;
  private EventBus mEventBus;

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EventBus.getDefault().register(this);
    setContentView(R.layout.activity_post_states);
    if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
      setTranslucentStatus(true);
      SystemBarTintManager tintManager = new SystemBarTintManager(this);
      tintManager.setStatusBarTintEnabled(true);
      tintManager.setStatusBarTintResource(R.color.line_hui);//通知栏所需颜色
    }
    ButterKnife.bind(this);
    mGson = new Gson();
    mEventBus = EventBus.getDefault();
    mBuffer = new StringBuffer();//圖片上傳
//    EventBus aDefault = EventBus.getDefault();
    Intent intent = getIntent();
    activityid = intent.getStringExtra("activityid");
    type = intent.getStringExtra("type");//类型,0为留言墙
    mToastUtil = ToastUtil.getInstant();
    mOssUtils = OssUtils.getInstance(getApplicationContext());
    initView();
    initListener();
    setResult(type.equals(ConstantsCode.STRING_ZERO) ? ConstantsCode.LEAVE_WALL_POST
        : ConstantsCode.FEED_BACK_POST);
  }

  //初始化监听事件
  private void initListener() {
    //点击允许评论
    mTgAllowcommon.setOnToggleChanged(new OnToggleChanged() {
      @Override
      public void onToggle(boolean on) {
        isdiscuss = on ? ConstantsCode.STRING_ONE : ConstantsCode.STRING_ZERO;//1为不允许评论
      }
    });
    //点击允许匿名
    mTgIsanonymity.setOnToggleChanged(new OnToggleChanged() {
      @Override
      public void onToggle(boolean on) {
        iscryptonym = on ? ConstantsCode.STRING_ONE : ConstantsCode.STRING_ZERO;//0不匿名,1为匿名的
      }
    });
    //Oss监听
    mOssUtils.setOnLoadoSSListener(mOnLoadoSSListener);
  }

  public void initView() {
    ImageView iv_fanhui = (ImageView) mHeadIvFanhui.findViewById(R.id.head_iv_fanhui);
    iv_fanhui.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    if (type.equals(ConstantsCode.STRING_ONE)) {
      mTgIsanonymity.setVisibility(View.GONE);
      mTvAnonymity.setVisibility(View.GONE);
    }
    mGridAdapter = new PostStateGridAdapter(this, mSelectPath);
    mNoScrollgridview.setAdapter(mGridAdapter);
    //条目点击监听
    mNoScrollgridview.setOnItemClickListener(mOnGridItemClickListener);
    mGridAdapter.setCallback(new Callback() {
      @Override
      public void click(View v, int posstion) {
        if (mSelectPath != null && mSelectPath.size() > 0) {
          mSelectPath.remove(posstion);
        }
      }
    });
  }

  //动态申请访问相机相册的权限
  private void showContacts() {
    if (ActivityCompat.checkSelfPermission(this, permission.CAMERA)
        != PackageManager.PERMISSION_GRANTED
        || ActivityCompat.checkSelfPermission(this, permission.WRITE_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED
        ) {
      Toast.makeText(getApplicationContext(), "没有权限,请手动访问相机权限", Toast.LENGTH_SHORT).show();
      // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
      ActivityCompat.requestPermissions(PostStatusActivity.this,
          new String[]{permission.CAMERA, permission.WRITE_EXTERNAL_STORAGE},
          ConstantsCode.ACCESS_CAMERA);
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, String[] permissions,
      int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    doNext(requestCode, grantResults);
  }

  private void doNext(int requestCode, int[] grantResults) {
    if (requestCode == ConstantsCode.ACCESS_CAMERA) {
      if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        // 获取到了权限
        getMultiImageSelector(true);
      } else {
        Toast.makeText(this, "请在应用管理中打开“相机”访问权限！", Toast.LENGTH_LONG).show();
        finish();
      }
    }
  }

  //gridview条目点击监听
  private OnItemClickListener mOnGridItemClickListener = new OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
      //点击了添加按钮6.0判断权限
      //权限判断
      if (VERSION.SDK_INT >= 23) {
        showContacts();
      }
      showChooseDialog();
    }
  };

  //底部弹窗提示
  private void showChooseDialog() {
    //弹出底部弹窗
    new ActionSheetDialog(this)
        .builder()
        .setCancelable(false)
        .setCanceledOnTouchOutside(false)
        .addSheetItem("选择照片", SheetItemColor.Green, new OnSheetItemClickListener() {
          @Override
          public void onClick(int which) {
            //去图片选择器
            getMultiImageSelector(true);
          }
        })
        .addSheetItem("拍摄视频", SheetItemColor.Green, new OnSheetItemClickListener() {
          @Override
          public void onClick(int which) {
            if (mSelectPath != null && mSelectPath.size() > 0) {
              ToastUtil.getInstant().show("不能同时选择视频和照片");
              return;
            }
            //发送EvenBus
            //设置录制视频的配置参数
            MediaRecorderConfig config = new MediaRecorderConfig.Buidler()
                .doH264Compress(new AutoVBRMode()
                    .setVelocity(BaseMediaBitrateConfig.Velocity.ULTRAFAST)
                )
                .setMediaBitrateConfig(new AutoVBRMode()
                    .setVelocity(BaseMediaBitrateConfig.Velocity.ULTRAFAST)
                )
                .smallVideoWidth(480)
                .smallVideoHeight(360)
                .recordTimeMax(10 * 1000)
                .maxFrameRate(20)
                .captureThumbnailsTime(1)
                .recordTimeMin((int) (1.5 * 1000))
                .build();
            //跳转到视频的录制界面
            Intent intent = new Intent();
            MediaRecorderActivity.goSmallVideoRecorder(PostStatusActivity.this,
                SendSmallVideoActivity.class.getName(), config);
          }
        })
        .addSheetItem("选择相册视频", SheetItemColor.Green, new OnSheetItemClickListener() {
          @Override
          public void onClick(int which) {
            if (mSelectPath != null && mSelectPath.size() > 0) {
              ToastUtil.getInstant().show("不能同时选择视频和照片");
              return;
            }
            Intent intent = new Intent(PostStatusActivity.this, VideoPickActivity.class);
            startActivity(intent);
          }
        }).show();

  }

  @Override
  public boolean isSystemBarTranclucent() {
    return false;
  }

  //获取图片
  public void getMultiImageSelector(boolean isShowCamera) {
    Intent intent = new Intent(this, MultiImageSelectorActivity.class);
    // 是否显示拍摄图片
    intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, isShowCamera);
    // 最大可选择图片数量
    intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 9);
    // 选择模式
    intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA,
        MultiImageSelectorActivity.MODE_MULTI);
    // 默认选择
    if (mSelectPath != null && mSelectPath.size() > 0) {
      intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
    }
    startActivityForResult(intent, REQUEST_IMAGE);
  }
  //接受回传的数据
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_IMAGE) {
      if (resultCode == RESULT_OK) {
        mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
        StringBuilder sb = new StringBuilder();
        mGridAdapter.setListItems(mSelectPath);
        mGridAdapter.notifyDataSetChanged();
      }
    }
  }

  @OnClick(R.id.tv_right)
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.tv_right:
        mEt_leave = mEtLeave.getText().toString().trim();
        if (TextUtils.isEmpty(mEt_leave)) {
          mToastUtil.show("亲,内容不能为空哦！");
          return;
        }
        showLoadingDialog("正在发布...");
        //发布
        postStatusContent();
        startActivity(new Intent(this,InteractiveSpaceActivity.class));
        break;
    }
  }

  //发布状态内容
  private void postStatusContent() {
    if (mSelectPath != null && mSelectPath.size() > 0) {
      mOssUtils.ossUpload(mSelectPath);
      mToastUtil.show("亲,上传图片了！");
    } else {//仅仅文字上传
      postTextcontent(mEt_leave);
    }
  }

  //Oss上传文件的监听
  private OnLoadoSSListener mOnLoadoSSListener = new OnLoadoSSListener() {
    @Override
    public void onVideoLoadSuccess(List<String> s) {
      postVideocontent(s);
    }

    @Override
    public void onLoadSuccess(List<String> fileUrls) {
      //同时上传文字和图片
      PostContent(fileUrls);
    }

    @Override
    public void onLoadProgress(String s) {
      //showLoadingDialog("正在发布...");
    }

    @Override
    public void onLoadFailure(String s) {
      ToastUtil.getInstant().show(s + "");
      dimissLoadingDialog();
    }
  };

  //上传文字内容
  private void postTextcontent(String et_leave) {
    OkGo.post(Constants.LOCAL_ADD_WORDS)
        .params("activityid", activityid)
        .params("publishid", PreferenceUtils.getUserId())
        .params("words", et_leave)
        .params("type", type)
        .params("isdiscuss", isdiscuss)
        .params("iscryptonym", iscryptonym)
        .execute(new StringCallback() {
          @Override
          public void onBefore(BaseRequest request) {
            super.onBefore(request);
            showLoadingDialog();
          }

          @Override
          public void onSuccess(String s, Call call, Response response) {
            dimissLoadingDialog();
            mBackInfo = mGson.fromJson(s, BackInfoModel.class);
            if (mBackInfo.getSuc().equals("y")) {
              mToastUtil.show(mBackInfo.getInfo() + "");
              iscryptonym = ConstantsCode.STRING_ZERO;
              finish();
            }
          }

          @Override
          public void onError(Call call, Response response, Exception e) {
            super.onError(call, response, e);
            dimissLoadingDialog();
          }
        });
  }

  //发布带图片的状态到服务器
  private void PostContent(List<String> fileUrls) {
    if (mBuffer.length() > 0) {
      mBuffer.delete(0, mBuffer.length());
    }
    for (String fileUrl : fileUrls) {
      mBuffer.append(fileUrl + ";");
    }
    mBuffer.deleteCharAt(mBuffer.length() - 1);//去掉最后一个图片路径的";"
    picUrls = mBuffer.toString();
    OkGo.post(Constants.LOCAL_ADD_WORDS)
        .params("activityid", activityid)
        .params("publishid", PreferenceUtils.getUserId())
        .params("words", mEt_leave)
        .params("type", type)
        .params("files", picUrls)
        .params("isdiscuss", isdiscuss)
        .params("iscryptonym", iscryptonym)
        .execute(new StringCallback() {
          @Override
          public void onBefore(BaseRequest request) {
            super.onBefore(request);
            showLoadingDialog();
          }
          @Override
          public void onSuccess(String s, Call call, Response response) {
            mBackInfo = mGson.fromJson(s, BackInfoModel.class);
            dimissLoadingDialog();
            if (mBackInfo.getSuc().equals("y")) {
              mToastUtil.show(mBackInfo.getInfo() + "");
              mSelectPath.clear();
              finish();
            }
          }
          @Override
          public void onError(Call call, Response response, Exception e) {
            super.onError(call, response, e);
            dimissLoadingDialog();
          }
        });
  }
  //EvenBus接受Activity里面的点击只看我的
  public void onEventMainThread(PostVideoEvent event) {
  List<String> mlist=new ArrayList<>();
    //加载只看我的第一页数据
    String words = event.getWords();
    String path = event.getPath();
    mEt_leave = words == null ? mEtLeave.getText().toString().trim() : words;
    String thumpath = event.getThumpath();
    mlist.add(thumpath);
    mlist.add(path);
    //上传视频
    mOssUtils.setOnLoadoSSListener(mOnLoadoSSListener);
    mOssUtils.ossVideoUpload(mlist);
  }

  //上传小视频
  private void postVideocontent(List<String> pathList) {
    StringBuffer str = new StringBuffer();
    for (int i = 0; i < pathList.size(); i++) {
      if (i==0){
        str.append(pathList.get(i)+";");
      }else {
        str.append(pathList.get(i));
      }
    }
    String path = str.toString();
    OkGo.post(Constants.LOCAL_ADD_WORDS)
        .params("activityid", activityid)
        .params("publishid", PreferenceUtils.getUserId())
        .params("words", mEt_leave)
        .params("type", type)
        .params("files", path)
        .params("isdiscuss", isdiscuss)
        .params("iscryptonym", iscryptonym)
        .execute(new StringCallback() {
          @Override
          public void onError(Call call, Response response, Exception e) {
            super.onError(call, response, e);
          }
          @Override
          public void onSuccess(String s, Call call, Response response) {
          mToastUtil.show("发布成功");
            finish();
          }
        });
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    EventBus.getDefault().unregister(this);
  }
}
