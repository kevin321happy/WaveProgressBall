package com.fips.huashun.holder;

import android.Manifest.permission;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout.Delegate;
import com.facebook.drawee.view.SimpleDraweeView;
import com.fips.huashun.R;
import com.fips.huashun.common.ConstantsCode;
import com.fips.huashun.modle.bean.FilesPathInfo;
import com.fips.huashun.modle.bean.LeavaItemInfo;
import com.fips.huashun.modle.event.LeaveWallEvent;
import com.fips.huashun.ui.activity.VideoPlayerActivity;
import com.fips.huashun.ui.utils.DisplayUtil;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.ToastUtil;
import com.fips.huashun.widgets.HeadTagIamge;
import com.google.gson.Gson;
import com.shuyu.common.RecyclerBaseHolder;
import com.shuyu.common.model.RecyclerBaseModel;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by kevin on 2017/1/19. 留言墙
 */
public class LeaveWallHolder extends RecyclerBaseHolder implements Delegate,
    OnClickListener {

  public final static int ID = R.layout.leave_wall_item;//留言墙布局文件
  @Bind(R.id.leave_iv_image)
  HeadTagIamge mLeaveIvImage;
  @Bind(R.id.leave_tv_name)
  TextView mLeaveTvName;
  @Bind(R.id.leave_tv_time)
  TextView mLeaveTvTime;
  @Bind(R.id.leave_tv_content)
  TextView mLeaveTvContent;
  @Bind(R.id.leave_division_top)
  View mLeaveDivisionTop;
  @Bind(R.id.tv_forwardcount)
  TextView mForwardCount;
  @Bind(R.id.tv_commentcount)
  TextView mCommentCount;
  @Bind(R.id.tv_likecount)
  TextView mLikeCount;
  /*活動的九宮格控件*/
  @Bind(R.id.npl_item_moment_photos)
  BGANinePhotoLayout mNpl_item_moment_photos;
  @Bind(R.id.leave_iv_zhuanfa)
  ImageView mLeaveIvZhuanfa;
  @Bind(R.id.leave_iv_pinglun)
  ImageView mLeaveIvPinglun;
  @Bind(R.id.leave_iv_dianzan)
  ImageView mLeaveIvDianzan;
  @Bind(R.id.leave_tv_delete)
  TextView mLeaveTvDelete;
  @Bind(R.id.leave_zfbg)
  LinearLayout mLeaveZfBg;
  @Bind(R.id.leave_tv_zftitle)
  TextView mZfTitle;
  @Bind(R.id.leave_first_publish)
  TextView mLeaveFirstPublish;
  @Bind(R.id.iv_thum_image)
  SimpleDraweeView mIvThumImage;//视频的封面
  @Bind(R.id.video_image)
  FrameLayout mVideoImage;


  private List<String> mList = new ArrayList<String>();
  private static final int REQUEST_CODE_PERMISSION_PHOTO_PREVIEW = 1;
  private static final int REQUEST_CODE_ADD_MOMENT = 1;
  private Context mContext;
  private EventBus mEventBus;
  private String mPid;
  private int mGoods;//点赞数
  private boolean mIsLike;//是否点赞
  private ToastUtil mToastUtil;
  private String content;
  private String publicname;
  private List<String> filepaths;//文件路径
  private LeavaItemInfo mLeavaItemInfo;
  private Gson mGson;
  private boolean mAllowCommon;//允许评论
  private boolean mAnonymity;//匿名
  private String mWords;
  private String isforward;
  private String mUid;

  public LeaveWallHolder(Context context, View v) {
    super(context, v);
    this.mContext = context;
    mToastUtil = ToastUtil.getInstant();
    mGson = new Gson();
  }

  @Override
  public void createView(View v) {
    ButterKnife.bind(this, v);
  }

  //view创建好,绑定数据处理
  @Override
  public void onBind(RecyclerBaseModel model, int position) {
    mVideoImage.setVisibility(View.GONE);
    mNpl_item_moment_photos.setVisibility(View.GONE);
    //初始化显示
    mLeavaItemInfo = (LeavaItemInfo) model;
    String isintransit = mLeavaItemInfo.getIsintransit();//非转发
    //是否允许评论、匿名
    mAllowCommon = mLeavaItemInfo.getIsdiscuss().equals(ConstantsCode.STRING_ZERO);
    mAnonymity = mLeavaItemInfo.getIscryptonym().equals(ConstantsCode.STRING_ZERO);
    //绑定数据
    mLeaveTvName.setText(mAnonymity ? mLeavaItemInfo.getPubname() + "" : "匿名");
    mLeaveIvImage.setBorderWidth(0);
    mLeaveIvImage.loadHeadIamge(mContext,
        mAnonymity ? mLeavaItemInfo.getFilepath() : ConstantsCode.DEFAULT_ICON);
    //显示标签
    if (mLeavaItemInfo.getLabel() != null) {
      mLeaveIvImage.setLableVisible(true);
      mLeaveIvImage.setLableSize(25);
      mLeaveIvImage.setLableText(mLeavaItemInfo.getLabel());
    } else {
      mLeaveIvImage.setLableVisible(false);
    }
    String dataTime = mLeavaItemInfo.getAddtime();
    mLeaveTvTime.setText(dataTime + "");
    mLeaveTvTime.setText(mLeavaItemInfo.getAddtime() + "");

    mLeaveTvTime.setText(dataTime + "");
    mWords = mLeavaItemInfo.getWords();
    mLeaveTvDelete.setVisibility(
        mLeavaItemInfo.getUid().equals(PreferenceUtils.getUserId()) ? View.VISIBLE : View.GONE);
    //留言的pid和点赞数获取接受数据
    mPid = mLeavaItemInfo.getPid();
    content = mLeavaItemInfo.getWords();
    publicname = mLeavaItemInfo.getPubname();
    isforward = mLeavaItemInfo.getIsintransit();
    mUid = mLeavaItemInfo.getUid();
    //底部列表的评论点赞数
    mCommentCount.setText(mLeavaItemInfo.getDiscuss());
    mIsLike = mLeavaItemInfo.getIsgoods().equals(ConstantsCode.STRING_ONE);
    mLeaveIvDianzan.setImageResource(
        mLeavaItemInfo.getIsgoods().equals(ConstantsCode.STRING_ONE) ? R.drawable.dianzan_hou
            : R.drawable.dian_zan);
    mLikeCount.setText(mLeavaItemInfo.getGoods());
    mGoods = Integer.parseInt(mLeavaItemInfo.getGoods());
    mLeaveIvPinglun.setImageResource(
        mLeavaItemInfo.getNotreads().equals(ConstantsCode.STRING_ZERO) ? R.drawable.ping_lun
            : R.drawable.xin_xiaoxi);
    mList.clear();//清除集合

    List<FilesPathInfo> files = mLeavaItemInfo.getFiles();
    for (FilesPathInfo file : files) {
      mList.add(file.getUploadpath());
    }

    if (mList.size() > 0 && mList.size() == files.size()) {
      if (mList.size() == 2) {
        FilesPathInfo pathInfo = files.get(1);
        final String uploadpath = pathInfo.getUploadpath();
        if (uploadpath.substring(uploadpath.lastIndexOf(".")).equals(".mp4")) {
          mNpl_item_moment_photos.setVisibility(View.GONE);
          mVideoImage.setVisibility(View.VISIBLE);
          mIvThumImage.setImageURI(files.get(0).getUploadpath());
          mVideoImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
              mContext.startActivity(new Intent(mContext, VideoPlayerActivity.class).putExtra(
                  "path", uploadpath));
            }
          });
        } else {
          mVideoImage.setVisibility(View.GONE);
          mNpl_item_moment_photos.setVisibility(View.VISIBLE);
          mNpl_item_moment_photos.setData((ArrayList<String>) mList);
          mNpl_item_moment_photos.setDelegate(this);
        }
      } else {
        mVideoImage.setVisibility(View.GONE);
        mNpl_item_moment_photos.setVisibility(View.VISIBLE);
        mNpl_item_moment_photos.setData((ArrayList<String>) mList);
        mNpl_item_moment_photos.setDelegate(this);
      }
    }
    //文件路径
    filepaths = mList;
    //设置初始状态
    if (isintransit.equals(ConstantsCode.STRING_ONE)) {
      //转发
      mZfTitle.setVisibility(View.VISIBLE);
      mZfTitle.setText(mLeavaItemInfo.getTitle());
      mLeaveZfBg.setBackgroundColor(Color.parseColor("#ebebeb"));
      mLeavaItemInfo.setIsintransit(ConstantsCode.STRING_ONE);
      mLeaveFirstPublish.setVisibility(View.VISIBLE);
      mLeaveFirstPublish
          .setText(mWords.substring(mWords.indexOf("@"), mWords.lastIndexOf(":")));
      mLeaveTvContent.setText(mWords.substring(mWords.lastIndexOf(":"), mWords.length()));
    } else if (isintransit.equals(ConstantsCode.STRING_ZERO)) {
      mLeaveTvContent.setText(mWords);
      mLeaveFirstPublish.setVisibility(View.GONE);
      mZfTitle.setVisibility(View.GONE);
      mLeaveZfBg.setBackgroundColor(Color.parseColor("#ffffff"));
      mLeavaItemInfo.setIsintransit(ConstantsCode.STRING_ZERO);
    }
  }

  @Override
  public AnimatorSet getAnimator(View view) {
    AnimatorSet animatorSet = new AnimatorSet();
    Animator animator = ObjectAnimator
        .ofFloat(view, "translationY", DisplayUtil.dp2px(context, 80), 0);
    animator.setDuration(500);
    animator.setInterpolator(new OvershootInterpolator(.5f));

    Animator animatorSx = ObjectAnimator.ofFloat(view, "scaleX", 0.5f, 1f);
    animator.setDuration(500);
    animator.setInterpolator(new OvershootInterpolator(.5f));

    Animator animatorSy = ObjectAnimator.ofFloat(view, "scaleY", 0.5f, 1f);
    animator.setDuration(500);
    animator.setInterpolator(new OvershootInterpolator(.5f));

    animatorSet.playTogether(animator, animatorSx, animatorSy);
    return animatorSet;
  }

  //当点击九宫格图片
  @Override
  public void onClickNinePhotoItem(BGANinePhotoLayout ninePhotoLayout, View view, int position,
      String model, List<String> models) {
    mNpl_item_moment_photos = ninePhotoLayout;
    photoPreviewWrapper();
  }

  /**
   * 图片预览，兼容6.0动态权限
   */
  @AfterPermissionGranted(REQUEST_CODE_PERMISSION_PHOTO_PREVIEW)
  private void photoPreviewWrapper() {
    if (mNpl_item_moment_photos == null) {
      return;
    }
    // 保存图片的目录，改成你自己要保存图片的目录。如果不传递该参数（null）的话就不会显示右上角的保存按钮
    //  File downloadDir = new File(Environment.getExternalStorageDirectory(), "Download");
    String[] perms = {permission.WRITE_EXTERNAL_STORAGE};
    if (EasyPermissions.hasPermissions(getContext(), perms)) {
      if (mNpl_item_moment_photos.getItemCount() == 1) {
        // 预览单张图片
        mContext.startActivity(BGAPhotoPreviewActivity
            .newIntent(mContext, null, mNpl_item_moment_photos.getCurrentClickItem()));
      } else if (mNpl_item_moment_photos.getItemCount() > 1) {
        // 预览多张图片
        mContext.startActivity(BGAPhotoPreviewActivity
            .newIntent(mContext, null, mNpl_item_moment_photos.getData(),
                mNpl_item_moment_photos.getCurrentClickItemPosition()));
      }
    } else {
      EasyPermissions.requestPermissions((Activity) getContext(), "图片预览需要以下权限:访问设备上的照片",
          REQUEST_CODE_PERMISSION_PHOTO_PREVIEW, perms);
//      EasyPermissions.requestPermissions(this, "图片预览需要以下权限:\n\n1.访问设备上的照片",
//          REQUEST_CODE_PERMISSION_PHOTO_PREVIEW, perms);
    }
  }

  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
  }


  public void onPermissionsGranted(int requestCode, List<String> perms) {
  }


  public void onPermissionsDenied(int requestCode, List<String> perms) {
    if (requestCode == REQUEST_CODE_PERMISSION_PHOTO_PREVIEW) {
      Toast.makeText(mContext, "您拒绝了「图片预览」所需要的相关权限!", Toast.LENGTH_SHORT).show();
    }
  }

  @OnClick({R.id.leave_iv_zhuanfa, R.id.leave_iv_pinglun, R.id.leave_iv_dianzan,
      R.id.leave_tv_delete})
  public void onClick(View view) {
    mEventBus = EventBus.getDefault();
    switch (view.getId()) {
      case R.id.leave_iv_zhuanfa:
        //点击转发
        LeaveWallEvent event = new LeaveWallEvent(ConstantsCode.STRING_ONE);
        event.setIsforward(isforward);
        event.setContent(content);
        event.setUid(mUid);
        event.setFilepaths(filepaths);
        event.setPublicname(publicname);
        mEventBus.post(event);
        break;
      case R.id.leave_iv_pinglun:
        if (!mAllowCommon) {
          ToastUtil.getInstant().show("该内容不允许评论！");
          return;
        }
        LeaveWallEvent leaveWallEvent = new LeaveWallEvent("2");
        String toJson = mGson.toJson(mLeavaItemInfo);
        leaveWallEvent.setJson(toJson);
        //传递对象
        mEventBus.post(leaveWallEvent);
        break;
      case R.id.leave_iv_dianzan://点赞
        if (mIsLike) {
          return;
        }
        mLeaveIvDianzan.setImageResource(R.drawable.dianzan_hou);
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.dianzan_anim);
        mLeaveIvDianzan.startAnimation(animation);
        mLikeCount.setText(mGoods + 1 + "");
        mLeavaItemInfo.setIsgoods(ConstantsCode.STRING_ONE);
        mLeavaItemInfo.setGoods(mGoods + 1 + "");
        //发送EvenBus
        LeaveWallEvent event3 = new LeaveWallEvent();
        event3.setType("3");
        event3.setPid(mPid);
        mEventBus.post(event3);
        break;
      case R.id.leave_tv_delete://删除
        LeaveWallEvent event4 = new LeaveWallEvent("4", mPid);
        mEventBus.post(event4);
        break;
    }
  }
}
