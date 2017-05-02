package com.fips.huashun.holder;

import android.Manifest.permission;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.fips.huashun.modle.event.FeedBackEvent;
import com.fips.huashun.ui.activity.VideoPlayerActivity;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.ToastUtil;
import com.fips.huashun.widgets.HeadTagIamge;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shuyu.common.RecyclerBaseHolder;
import com.shuyu.common.model.RecyclerBaseModel;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by kevin on 2017/2/1. 反馈室
 */
public class FeedBackHolder extends RecyclerBaseHolder implements Delegate {

  public static final int ID = R.layout.feedback_room_item;
  @Bind(R.id.feedback_iv_image)
  HeadTagIamge mFeedbackIvImage;
  @Bind(R.id.feedback_tv_name)
  TextView mFeedbackTvName;
  @Bind(R.id.feedback_tv_department)
  TextView mFeedbackTvDepartment;
  @Bind(R.id.feedback_tv_post)
  TextView mFeedbackTvPost;
  @Bind(R.id.feedback_tv_grade)
  TextView mFeedbackTvGrade;
  @Bind(R.id.feedback_tv_time)
  TextView mFeedbackTvTime;
  @Bind(R.id.feedback_tv_content)
  TextView mFeedbackTvContent;
  @Bind(R.id.feedback_lv_commonlist)
  ListView mFeedbackLvCommonlist;
  @Bind(R.id.leave_division_buttom)
  View mLeaveDivisionButtom;
  @Bind(R.id.feedback_iv_shoucang)
  ImageView mFeedbackIvShoucang;
  @Bind(R.id.feedback_tv_collect)
  TextView mFeedbackTvCollect;
  @Bind(R.id.feedback_iv_pinglun)
  ImageView mFeedbackIvPinglun;
  @Bind(R.id.feedback_tv_commentcount)
  TextView mFeedbackTvCommentcount;
  @Bind(R.id.feedback_iv_dianzan)
  ImageView mFeedbackIvDianzan;
  @Bind(R.id.feedback_tv_likecount)
  TextView mFeedbackTvLikecount;
  /*九宮格控件*/
  @Bind(R.id.feedback_tv_delete)
  TextView mFeedbackTvDelete;
  @Bind(R.id.npl_item_moment_photos)
  BGANinePhotoLayout mNplItemMomentPhotos;
  @Bind(R.id.iv_thum_image)
  SimpleDraweeView mIvThumImage;
  @Bind(R.id.video_image)
  FrameLayout mVideoImage;
  private List<String> mList = new ArrayList<String>();//文件的集合
  private List<String> imageList = new ArrayList<>();
  private static final int REQUEST_CODE_PERMISSION_PHOTO_PREVIEW = 1;
  private static final int REQUEST_CODE_ADD_MOMENT = 1;
  private Context mContext;
  private ImageLoader mImageLoader;
  private EventBus mEventBus;
  private String mPid;
  private int mGoods;//点赞数
  private boolean mIsLike;//是否点赞
  private String mPublicname;
  private String ismanager;//是否管理员
  private ToastUtil mToastUtil;
  private Gson mGson;
  private LeavaItemInfo mItemInfo;
  private String isMark;
  private boolean mAllowCommon;//允许评论

  public FeedBackHolder(Context context, View v) {
    super(context, v);
    this.mContext = context;
    mImageLoader = ImageLoader.getInstance();
    mGson = new Gson();
    mToastUtil = ToastUtil.getInstant();
  }

  @Override
  public void createView(View v) {
    ButterKnife.bind(this, v);
  }

  //綁定数据
  @Override
  public void onBind(RecyclerBaseModel model, int position) {
    this.setIsRecyclable(false);
    mItemInfo = (LeavaItemInfo) model;
    mAllowCommon = mItemInfo.getIsdiscuss().equals("0");//允许评论为0;
    mFeedbackIvImage.setBorderWidth(0);
    mFeedbackIvImage.loadHeadIamge(mContext, mItemInfo.getFilepath());
    if (mItemInfo.getLabel() != null) {
      mFeedbackIvImage.setLableVisible(true);
      mFeedbackIvImage.setLableSize(25);
      mFeedbackIvImage.setLableText(mItemInfo.getLabel());
    } else {
      mFeedbackIvImage.setLableVisible(false);
    }
    mFeedbackTvName.setText(mItemInfo.getPubname() + "");
    mFeedbackTvDepartment.setText(mItemInfo.getDepname() + "");
    mFeedbackTvPost.setText(mItemInfo.getJobname() == null ? "" : mItemInfo.getJobname());
    mFeedbackTvTime.setText(mItemInfo.getAddtime() + "");//时间
    mFeedbackTvDelete.setVisibility(
        mItemInfo.getUid().equals(PreferenceUtils.getUserId()) ? View.VISIBLE : View.GONE);
    mFeedbackTvContent.setText(mItemInfo.getWords() + "");//内容
    //反馈的pid和点赞数获取接受数据
    ismanager = mItemInfo.getIsmanager();
    mPublicname = mItemInfo.getPubname();
    mPid = mItemInfo.getPid();
    mGoods = Integer.parseInt(mItemInfo.getGoods());
    isMark = mItemInfo.getIsmark();//是否评分
    mIsLike = mItemInfo.getIsgoods().equals("1");
    //评分,评论,点赞
    mFeedbackIvShoucang.setImageResource(
        mItemInfo.getIsmark().equals(ConstantsCode.STRING_ZERO) ? R.drawable.xingxing_
            : R.drawable.xingxing_1x);
    mFeedbackTvGrade
        .setText(mItemInfo.getLectormark() == null ? "当前未评分" : "评分为：" + mItemInfo.getLectormark());
    mFeedbackTvCollect.setText(mItemInfo.getMark() == null ? "0" : mItemInfo.getMark());
    mFeedbackTvCommentcount.setText(mItemInfo.getDiscuss());
    mFeedbackTvLikecount.setText(mItemInfo.getGoods());
    mFeedbackIvDianzan.setImageResource(
        mItemInfo.getIsgoods().equals("1") ? R.drawable.dianzan_hou : R.drawable.dian_zan);
    mList.clear();//清除集合

    List<FilesPathInfo> files = mItemInfo.getFiles();
    for (FilesPathInfo file : files) {
      mList.add(file.getUploadpath());
    }

    if (mList.size() > 0 && mList.size() == files.size()) {
      if (mList.size() == 2) {
        FilesPathInfo pathInfo = files.get(1);
        final String uploadpath = pathInfo.getUploadpath();
        if (uploadpath.substring(uploadpath.lastIndexOf(".")).equals(".mp4")) {
          mNplItemMomentPhotos.setVisibility(View.GONE);
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
          mNplItemMomentPhotos.setVisibility(View.VISIBLE);
          mNplItemMomentPhotos.setData((ArrayList<String>) mList);
          mNplItemMomentPhotos.setDelegate(this);
        }
      } else {
        mVideoImage.setVisibility(View.GONE);
        mNplItemMomentPhotos.setVisibility(View.VISIBLE);
        mNplItemMomentPhotos.setData((ArrayList<String>) mList);
        mNplItemMomentPhotos.setDelegate(this);
      }
    }
    //显示九宫格图片
//    if (mList != null && mList.size() == files.size()) {
//      mNplItemMomentPhotos.setVisibility(View.VISIBLE);
//      mNplItemMomentPhotos.setData((ArrayList<String>) mList);
//      mNplItemMomentPhotos.setDelegate(this);
//    }
  }
  @Override
  public void onClickNinePhotoItem(BGANinePhotoLayout ninePhotoLayout, View view, int position,
      String model, List<String> models) {
    mNplItemMomentPhotos = ninePhotoLayout;
    photoPreviewWrapper();
  }

  /**
   * 图片预览，兼容6.0动态权限
   */
  @AfterPermissionGranted(REQUEST_CODE_PERMISSION_PHOTO_PREVIEW)
  private void photoPreviewWrapper() {
    if (mNplItemMomentPhotos == null) {
      return;
    }
    // 保存图片的目录，改成你自己要保存图片的目录。如果不传递该参数的话就不会显示右上角的保存按钮
//        File downloadDir = new File(Environment.getExternalStorageDirectory(), "BGAPhotoPickerDownload");
    String[] perms = {permission.WRITE_EXTERNAL_STORAGE};
    if (EasyPermissions.hasPermissions(getContext(), perms)) {
      if (mNplItemMomentPhotos.getItemCount() == 1) {
        // 预览单张图片
        mContext.startActivity(BGAPhotoPreviewActivity
            .newIntent(mContext, null, mNplItemMomentPhotos.getCurrentClickItem()));
      } else if (mNplItemMomentPhotos.getItemCount() > 1) {
        // 预览多张图片
        mContext.startActivity(BGAPhotoPreviewActivity
            .newIntent(mContext, null, mNplItemMomentPhotos.getData(),
                mNplItemMomentPhotos.getCurrentClickItemPosition()));
      }
    } else {
      //  EasyPermissions.requestPermissions(BGAPhotoPreviewActivity.this, "图片预览需要以下权限:\n\n1.访问设备上的照片", REQUEST_CODE_PERMISSION_PHOTO_PREVIEW, perms);
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

  //收藏评论点赞
  @OnClick({R.id.feedback_iv_shoucang, R.id.feedback_iv_pinglun, R.id.feedback_iv_dianzan,
      R.id.feedback_tv_grade, R.id.feedback_tv_delete})
  public void onClick(View view) {
    mEventBus = EventBus.getDefault();
    switch (view.getId()) {
      case R.id.feedback_iv_shoucang:
        if (isMark.equals("1")) {
          mToastUtil.show("您已评分！");
          return;
        }
        FeedBackEvent event1 = new FeedBackEvent();
        event1.setType("1");
        event1.setIsmanager(ismanager);
        event1.setPid(mPid);
        mEventBus.post(event1);
        break;
      case R.id.feedback_iv_pinglun:
        if (!mAllowCommon) {
          mToastUtil.show("该内容不允许评论！");
          return;
        }
        FeedBackEvent event2 = new FeedBackEvent();
        event2.setType("2");
        String toJson = mGson.toJson(mItemInfo);
        event2.setItemjson(toJson);
        mEventBus.post(event2);
        break;
      case R.id.feedback_iv_dianzan:
        if (mIsLike) {
          return;
        }
        mFeedbackIvDianzan.setImageResource(R.drawable.dianzan_hou);
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.dianzan_anim);
        mFeedbackIvDianzan.startAnimation(animation);
        mFeedbackTvLikecount.setText(mGoods + 1 + "");
        mItemInfo.setIsgoods("1");
        mItemInfo.setGoods(mGoods + 1 + "");
        //发送EvenBus
        FeedBackEvent event3 = new FeedBackEvent();
        event3.setType("3");
        event3.setPid(mPid);
        mEventBus.post(event3);
        break;
      case R.id.feedback_tv_grade:
        //讲师评分
        FeedBackEvent event4 = new FeedBackEvent();
        event4.setType("4");
        event4.setIsmanager(ismanager);
        event4.setPid(mPid);
        mEventBus.post(event4);
        break;
      case R.id.feedback_tv_delete:
        FeedBackEvent event5 = new FeedBackEvent();
        event5.setType("5");
        event5.setPid(mPid);
        mEventBus.post(event5);
        break;
    }
  }
}
