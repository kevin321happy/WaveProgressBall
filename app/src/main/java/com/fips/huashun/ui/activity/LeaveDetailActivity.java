package com.fips.huashun.ui.activity;

import static com.fips.huashun.R.id.comment_edt;

import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;
import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.common.ConstantsCode;
import com.fips.huashun.modle.bean.FilesPathInfo;
import com.fips.huashun.modle.bean.LeavaItemInfo;
import com.fips.huashun.modle.bean.LeaveReplyModel;
import com.fips.huashun.modle.bean.LeaveReplyModel.ToprowBean;
import com.fips.huashun.modle.bean.ReplyItemModel;
import com.fips.huashun.ui.adapter.TopReplyAdapter;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.ToastUtil;
import com.fips.huashun.widgets.HeadTagIamge;
import com.fips.huashun.widgets.NoScrollListView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Response;
//留言详情界面

public class LeaveDetailActivity extends BaseActivity implements BGANinePhotoLayout.Delegate {

  @Bind(R.id.na_bar)
  NavigationBar mNaBar;
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
  @Bind(R.id.feedback_tv_delete)
  TextView mFeedbackTvDelete;
  @Bind(R.id.feedback_tv_content)
  TextView mFeedbackTvContent;
  @Bind(R.id.npl_item_moment_photos)
  BGANinePhotoLayout mNplItemMomentPhotos;
  @Bind(R.id.leave_division_buttom)
  View mLeaveDivisionButtom;

  @Bind(R.id.commonlist_lv)
  NoScrollListView mCommonlistLv;
  @Bind(comment_edt)
  EditText mCommentEdt;
  @Bind(R.id.send_tv)
  TextView mSendTv;
  @Bind(R.id.send_comment_ll)
  LinearLayout mSendCommentLl;
  @Bind(R.id.activity_leave_detail)
  RelativeLayout mActivityLeaveDetail;
  private LeavaItemInfo mLeavaItemInfo;
  protected Gson mGson;
  private ImageLoader mImageLoader;
  private List<String> mList = new ArrayList<>();
  private String space_wordsid;
  private TopReplyAdapter mTopReplyAdapter;
  private List<ReplyItemModel> mReplyItemModels = new ArrayList<>();
  private boolean ISTOPREPLY = true;//是否是顶级的回复
  private String mTopid;//是否顶级,不是顶级就是上一个人的pid
  private String putid;
  private String getid;//回复人ID
  private String pid = "0";


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_leave_detail);
    ButterKnife.bind(this);
    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    mGson = new Gson();
    setResult(ConstantsCode.REQUEST_COMMON_CODE);
    mImageLoader = ImageLoader.getInstance();
    String json = getIntent().getStringExtra("json");
    mLeavaItemInfo = mGson.fromJson(json, LeavaItemInfo.class);
    space_wordsid = mLeavaItemInfo.getPid();
    initView();
    initData();
  }

  //初始化数据
  private void initData() {

    OkGo.post(Constants.LOCAL_GET_REPLY)
        .params("space_wordsid", space_wordsid)
        .params("uid", PreferenceUtils.getUserId())
        .execute(new com.lzy.okgo.callback.StringCallback() {
          @Override
          public void onSuccess(String s, Call call, Response response) {
            mReplyItemModels.clear();
            LeaveReplyModel replyModel = mGson.fromJson(s, LeaveReplyModel.class);
            List<LeaveReplyModel.ToprowBean> replyModelToprow = replyModel.getToprow();
            if (replyModelToprow != null && replyModelToprow.size() > 0) {
              for (LeaveReplyModel.ToprowBean toprowBean : replyModelToprow) {
                //顶级的留言回复
                LeaveReplyModel.ToprowBean.TopBean topBean = toprowBean.getTop();
                ReplyItemModel itemModel = new ReplyItemModel();
                itemModel.setAddtime(topBean.getAddtime());
                itemModel.setGetid(topBean.getGetid());
                itemModel.setGetname(topBean.getGetname());
                itemModel.setPid(topBean.getPid());
                itemModel.setLable(topBean.getLabel());
                itemModel.setPutid("0");
                itemModel.setPutname("");
                itemModel.setHeadurl(topBean.getHeadurl());
                itemModel.setSpace_wordsid(topBean.getSpace_wordsid());
                itemModel.setTopid("0");
                itemModel.setWords(topBean.getWords());
                mReplyItemModels.add(itemModel);
                for (ToprowBean.RowBean rowBean : topBean.getRow()) {
                  ReplyItemModel itemMode2 = new ReplyItemModel();
                  itemMode2.setTopid(rowBean.getTopid());
                  itemMode2.setSpace_wordsid(rowBean.getSpace_wordsid());
                  itemMode2.setWords(rowBean.getWords());
                  itemMode2.setHeadurl(rowBean.getHeadurl());
                  itemMode2.setPutname(rowBean.getPutname());
                  itemMode2.setAddtime(rowBean.getAddtime());
                  itemMode2.setGetname(rowBean.getGetname());
                  itemMode2.setPutid(rowBean.getPutid());
                  itemMode2.setTopid(rowBean.getTopid());
                  itemMode2.setLable(rowBean.getLabel());
                  itemMode2.setGetid(rowBean.getGetid());
                  itemMode2.setPid(rowBean.getPid());
                  mReplyItemModels.add(itemMode2);
                }
              }
            }
            if (mTopReplyAdapter != null) {
              mTopReplyAdapter.setData(mReplyItemModels);
              mTopReplyAdapter.notifyDataSetChanged();
            }
          }
        });
  }

  //初始化界面
  protected void initView() {
    mNaBar.setTitle("留言详情");
    mNaBar.setLeftImage(R.drawable.fanhui);
    mNaBar.setListener(new NavigationBar.NavigationListener() {
      @Override
      public void onButtonClick(int button) {
        if (button == NavigationBar.LEFT_VIEW) {
          finish();
        }
      }
    });
    //点击空白处隐藏软键盘
    mActivityLeaveDetail.setOnTouchListener(new View.OnTouchListener() {
      public boolean onTouch(View arg0, MotionEvent arg1) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        return imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
      }
    });
    initLeaveDetail();
  }

  //初始化显示留言详情
  private void initLeaveDetail() {
    mFeedbackIvImage.setBorderWidth(0);
    mFeedbackIvImage.loadHeadIamge(this, mLeavaItemInfo.getFilepath());
    if (mLeavaItemInfo.getLabel() != null) {
      mFeedbackIvImage.setLableVisible(true);
      mFeedbackIvImage.setLableText(mLeavaItemInfo.getLabel());
//      mFeedbackIvImage.setLableText("学霸雪山");
      mFeedbackIvImage.setLableSize(25);
    } else {
      mFeedbackIvImage.setLableVisible(false);
    }
    mFeedbackTvName.setText(mLeavaItemInfo.getPubname() + "");
    mFeedbackTvTime.setText(mLeavaItemInfo.getAddtime() + "");
    mFeedbackTvContent.setText(mLeavaItemInfo.getWords() + "");
    if (mLeavaItemInfo.getDepname() != null) {
      mFeedbackTvDepartment.setVisibility(View.VISIBLE);
      mFeedbackTvDepartment.setText(mLeavaItemInfo.getDepname() + "");
    }
    if (mLeavaItemInfo.getJobname() != null) {
      mFeedbackTvPost.setVisibility(View.VISIBLE);
      mFeedbackTvPost.setText(mLeavaItemInfo.getJobname() + "");
    }
    List<FilesPathInfo> files = mLeavaItemInfo.getFiles();
    for (FilesPathInfo file : files) {
      mList.add(file.getUploadpath());
    }
    if (mList != null && mList.size() == files.size()) {
      mNplItemMomentPhotos.setVisibility(View.VISIBLE);
      mNplItemMomentPhotos.setData((ArrayList<String>) mList);
    }
    mTopReplyAdapter = new TopReplyAdapter(this);
    mCommonlistLv.setAdapter(mTopReplyAdapter);
    mTopReplyAdapter.setOnApplyItemClickListener(mOnApplyItemClickListener);
  }

  //留言条目点击事件
  private TopReplyAdapter.OnApplyItemClickListener mOnApplyItemClickListener = new TopReplyAdapter.OnApplyItemClickListener() {
    @Override
    public void OnApplyItemClick(ReplyItemModel ReplyItem) {
      pid = ReplyItem.getPid();
      if (ReplyItem.getGetid().equals(PreferenceUtils.getUserId())) {
        //当前为本人,置为顶级
        putid = ReplyItem.getPutid();
        ISTOPREPLY = true;
      } else {
        //Top=0;给pid的TopId不为o
        //=ReplyItem.getPid();
        String topid = ReplyItem.getTopid();
        if (topid.equals(ConstantsCode.STRING_ZERO)) {//非顶级回复
          mTopid = ReplyItem.getPid();
        } else {
          mTopid = ReplyItem.getTopid();
        }
        putid = ReplyItem.getGetid(); //上一个回复人的uid变成当前人接受的ID
        ISTOPREPLY = false;
      }
      //弹出软键盘
      InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
      imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
      SpannableString s = new SpannableString("回复" + ReplyItem.getGetname() + ":");//这里输入自己想要的提示文字
      mCommentEdt.setHint(s);
      mCommentEdt.setFocusable(true);
    }

    @Override
    public void OnDeleteClick(String pid) {
      deleteReply(pid);
    }

    //点击回复人时
    @Override
    public void OnDiscussantClick(ReplyItemModel model) {
      if (model.getPutid().equals(PreferenceUtils.getUserId())) {
        //当前为本人,置为顶级
        putid = model.getPutid();
        ISTOPREPLY = true;
      } else {
        String topid = model.getTopid();
        if (topid.equals(ConstantsCode.STRING_ZERO)) {//非顶级回复
          mTopid = model.getPid();
        } else {
          mTopid = model.getTopid();
        }
        putid = model.getPutid(); //上一个回复人的uid变成当前人接受的ID
        ISTOPREPLY = false;
      }
      //弹出软键盘
      InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
      imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
      SpannableString s = new SpannableString("回复" + model.getPutname() + ":");//这里输入自己想要的提示文字
      mCommentEdt.setHint(s);
      mCommentEdt.setFocusable(true);
    }
  };

  //删除评论
  private void deleteReply(String pid) {
    OkGo.post(Constants.LOCAL_DEL_REPLY)
        .params("pid", pid)
        .execute(new com.lzy.okgo.callback.StringCallback() {
          @Override
          public void onSuccess(String s, Call call, Response response) {
            ToastUtil.getInstant().show("删除成功");
            mCommentEdt.setText("");
            SpannableString s1 = new SpannableString("说点什么吧...");//这里输入自己想要的提示文字
            mCommentEdt.setHint(s1);
            initData();
          }
        });
  }

  @Override
  public boolean isSystemBarTranclucent() {
    return false;
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }

  //点击图片
  @Override
  public void onClickNinePhotoItem(BGANinePhotoLayout ninePhotoLayout, View view, int position,
      String model, List<String> models) {

  }

  @OnClick(R.id.send_tv)
  public void onClick() {
    //点击发送
    String s = mCommentEdt.getText().toString();
    if (TextUtils.isEmpty(s)) {
      ToastUtil.getInstant().show("请输入回复内容。。。");
      return;
    }
    sendAddReply(s);
  }

  //发送回复信息
  private void sendAddReply(String s) {
    showLoadingDialog();
    //当前未顶级回复
    if (ISTOPREPLY) {
      mTopid = "0";
      putid = "";
    }
    OkGo.post(Constants.LOCAL_ADD_REPLY)
        .params("space_wordsid", space_wordsid)
        .params("topid", mTopid)
        .params("putid", putid)
        .params("getid", PreferenceUtils.getUserId())
        .params("words", s)
        .execute(new com.lzy.okgo.callback.StringCallback() {
          @Override
          public void onSuccess(String s, Call call, Response response) {
            ToastUtil.getInstant().show("发送成功");
            mCommentEdt.setText("");
            SpannableString s1 = new SpannableString("请输入回复...");//这里输入自己想要的提示文字
            mCommentEdt.setHint(s1);
            ISTOPREPLY = true;
            // topid = "0";
            // pid = "0";
            dimissLoadingDialog();
            initData();
          }
        });
  }
}
