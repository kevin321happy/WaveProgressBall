package com.fips.huashun.ui.fragment;

import static com.fips.huashun.R.id.forward_et;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.common.ConstantsCode;
import com.fips.huashun.holder.LeaveWallHolder;
import com.fips.huashun.modle.bean.BackInfoModel;
import com.fips.huashun.modle.bean.InteractiveRoomModel;
import com.fips.huashun.modle.bean.LeavaItemInfo;
import com.fips.huashun.modle.bean.SpaceNoticeModel;
import com.fips.huashun.modle.event.LeaveRefreshEvent;
import com.fips.huashun.modle.event.LeaveTopMenuEvent;
import com.fips.huashun.modle.event.LeaveWallEvent;
import com.fips.huashun.ui.activity.LeaveDetailActivity;
import com.fips.huashun.ui.utils.DisplayUtil;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.ToastUtil;
import com.fips.huashun.widgets.CircleImageView;
import com.fips.huashun.widgets.CustomRefreshHeader;
import com.fips.huashun.widgets.FlowLayout;
import com.fips.huashun.widgets.pickerview.DividerItemDecoration;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.other.ProgressStyle;
import com.lzy.okgo.OkGo;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shuyu.common.CommonRecyclerAdapter;
import com.shuyu.common.CommonRecyclerManager;
import com.shuyu.common.listener.LoadMoreScrollListener;
import com.shuyu.common.listener.OnItemClickListener;
import com.shuyu.common.model.RecyclerBaseModel;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by kevin on 2017/1/15. 留言墙
 */
@SuppressLint("ValidFragment")
public class LeaveWallFragment extends BaseFragment implements OnClickListener {

  private static final String TAG = "LeaveWallFragment";
  @Bind(R.id.top_iv_icon)
  CircleImageView mTopIvIcon;
  @Bind(R.id.xrcy_leave_list)
  XRecyclerView mXrcyLeaveList;
  @Bind(R.id.tv_notice_title)
  TextView mTvNoticeTitle;
  @Bind(R.id.leavewall_top)
  RelativeLayout mLlTop;
  private View mRootView;
  TextView mTopTvTime;
  private CommonRecyclerManager mCommonRecyclerManager;
  private List<RecyclerBaseModel> mLeaveWallModels = new ArrayList<>();
  private CommonRecyclerAdapter mCommonRecyclerAdapter;
  private Context mContext;
  private String activityid;
  private int currentpage;
  private Gson mGson;
  private List<LeavaItemInfo> mItemInfos = new ArrayList<>();
  private boolean isLoadMore;
  private ToastUtil mToastUtil;
  private String seei = ConstantsCode.STRING_ZERO;//是否只看我的0為全部
  private EditText mEt_content;
  private Button mBt_send;
  private List<String> mFilepaths;
  private String picUrls;
  private boolean LEAVEISOPEN = true;
  private TextView mTop_tv_content;
  private List<String> notices = new ArrayList<>();
  private ImageLoader mImageLoader;
  private Dialog mDialog;
  private String mContent;
  private String mPublicname;
  private String mForwardTitle;
  private EditText mForward_et;
  private String mIsforward;
  private String mIsforward1;

  public LeaveWallFragment() {
  }

  public LeaveWallFragment(String activityid, boolean LEAVEISOPEN) {
    this.activityid = activityid;
    this.LEAVEISOPEN = LEAVEISOPEN;
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    mContext = container.getContext();
    mRootView = inflater.inflate(R.layout.leave_wall, container, false);
    ButterKnife.bind(this, mRootView);
    if (!LEAVEISOPEN) {
      mLlTop.setVisibility(View.VISIBLE);
      mRootView.setEnabled(false);
    }
    mTop_tv_content = (TextView) mRootView.findViewById(R.id.top_tv_content);
    mTopTvTime = (TextView) mRootView.findViewById(R.id.top_tv_time);
    mTopIvIcon = (CircleImageView) mRootView.findViewById(R.id.top_iv_icon);
    //注册EvenBus
    EventBus.getDefault().register(this);
    return mRootView;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(getActivity());
    mGson = new Gson();
    mImageLoader = ImageLoader.getInstance();
    mToastUtil = ToastUtil.getInstant();
    mXrcyLeaveList = (XRecyclerView) mRootView.findViewById(R.id.xrcy_leave_list);
    //如果开放加载数据
    if (LEAVEISOPEN) {
      initNoticeData();
      initDate(1, seei);
    }
    initView();
    initListener();
  }

  //初始公告信息
  private void initNoticeData() {
    OkGo.post(Constants.LOCAL_GET_PROCLAMATION)
        .params("activityid", activityid)
        .params("type", ConstantsCode.STRING_ONE)
        .execute(new com.lzy.okgo.callback.StringCallback() {
          @Override
          public void onSuccess(String s, Call call, Response response) {
            SpaceNoticeModel noticeModel = mGson.fromJson(s, SpaceNoticeModel.class);
            UpDataNotice(noticeModel);
          }
        });
  }

  //更新公告信息
  private void UpDataNotice(SpaceNoticeModel noticeModel) {
    mTopTvTime.setText(noticeModel.getAddtime() + "");
    notices.add(noticeModel.getProclamation());
    mTop_tv_content.setText(noticeModel.getProclamation() + "");
//    Glide.with(mContext).load(noticeModel.getLogo_url()).placeholder(R.drawable.user_head_default)
//        .error(R.drawable.user_head_default).into(mTopIvIcon);
    ImageLoader.getInstance().displayImage(noticeModel.getLogo_url(), mTopIvIcon);
  }

  private void initListener() {
    mXrcyLeaveList.addOnScrollListener(new LoadMoreScrollListener() {
      @Override
      public void onLoadMore() {
        //注意加锁
        if (!isLoadMore) {
          isLoadMore = true;
          mXrcyLeaveList.postDelayed(new Runnable() {
            @Override
            public void run() {
              currentpage++;
              loadMoreData(currentpage);
            }
          }, 1500);
        }
      }

      @Override
      public void onScrolled(int firstPosition) {
      }
    });
    mXrcyLeaveList.setLoadingListener(new XRecyclerView.LoadingListener() {
      @Override
      public void onRefresh() {
        seei = ConstantsCode.STRING_ZERO;
        mXrcyLeaveList.postDelayed(new Runnable() {
          @Override
          public void run() {
            initDate(1, seei);//下拉刷新
          }
        }, 1500);
      }

      @Override
      public void onLoadMore() {
      }
    });
  }

  //初始化界面
  private void initView() {
    mCommonRecyclerManager = new CommonRecyclerManager();
    mCommonRecyclerManager.addType(LeaveWallHolder.ID, LeaveWallHolder.class.getName());
    mCommonRecyclerAdapter = new CommonRecyclerAdapter(getActivity(), mCommonRecyclerManager,
        mLeaveWallModels);
    mXrcyLeaveList.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    mXrcyLeaveList.setAdapter(mCommonRecyclerAdapter);

    mXrcyLeaveList.setLoadingMoreEnabled(true);
    mXrcyLeaveList.setRefreshHeader(new CustomRefreshHeader(getContext()));
    mXrcyLeaveList.setLoadingMoreProgressStyle(ProgressStyle.BallScaleMultiple);
    mXrcyLeaveList.addItemDecoration(
        new DividerItemDecoration(DisplayUtil.dp2px(this.getActivity(), 10), 0, 0));
    mCommonRecyclerAdapter.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(Context context, int position) {
        if (mItemInfos != null || mItemInfos.size() > 0) {
          return;
        }
        LeavaItemInfo leavaItemInfo = mItemInfos.get(position - 1);
        if (leavaItemInfo.getIsdiscuss().equals(ConstantsCode.STRING_ZERO)) {
          String toJson = mGson.toJson(leavaItemInfo);
          Intent intent = new Intent(getActivity(), LeaveDetailActivity.class);
          intent.putExtra("json", toJson);
          startActivityForResult(intent, ConstantsCode.REQUEST_COMMON_CODE);
        } else {
          mToastUtil.show("该内容没有回复信息");
          return;
        }
      }
    });
  }

  //EventBus接受的留言墙里面的点赞评论
  public void onEventMainThread(LeaveWallEvent event) {
    String type = event.getType();
    String pid = event.getPid();
    //文件路径
    switch (type) {
      case ConstantsCode.STRING_ONE:
        //转发
        mIsforward1 = event.getIsforward();
        String uid = event.getUid();
        if (uid.equals(PreferenceUtils.getUserId())) {
          //当前为自己发的留言
          mToastUtil.show("亲，这条留言您已经发过了哦");
          return;
        }
        //内容
        mContent = event.getContent();
        //发布者的名字
        mIsforward = event.getIsforward();
        if (mIsforward.equals(ConstantsCode.STRING_ONE)) {
          mPublicname = "";
        } else {
          mPublicname = event.getPublicname();
        }
        mFilepaths = event.getFilepaths();
        //弹出对话框
        showForwardDialog(mContent, mPublicname);
        break;
      case "2":
        //发送EvenBus
        //跳转到详情
        String json = event.getJson();
        Intent intent = new Intent(getActivity(), LeaveDetailActivity.class);
        Log.i("json", json);
        intent.putExtra("json", json);
        startActivityForResult(intent, ConstantsCode.REQUEST_COMMON_CODE);
        break;
      case "3":
        sendAddLike(pid);
        break;
      case "4":
        deleteLeave(pid);
        break;
    }
  }

  //删除留言
  private void deleteLeave(String pid) {
    OkGo.post(Constants.LOCAL_DEL_WORDS)
        .params("pid", pid)
        .execute(new com.lzy.okgo.callback.StringCallback() {
          @Override
          public void onSuccess(String s, Call call, Response response) {
            mToastUtil.show("删除成功");
            initDate(currentpage, seei);
          }
        });
  }

  //转发
  private void showForwardDialog(final String content, final String publicname) {
    View view = View.inflate(mContext, R.layout.leave_forward_dialog, null);
    mDialog = new Dialog(mContext, R.style.activityDialog);
    mDialog.setContentView(view, new FlowLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT));
    Window window = mDialog.getWindow();
    // 设置显示动画
    window.setWindowAnimations(R.style.main_menu_animstyle);
    window.setGravity(Gravity.BOTTOM);
    WindowManager.LayoutParams wl = window.getAttributes();
    // 以下这两句是为了保证按钮可以水平满屏
    wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
    wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
    //设置显示位置
    mDialog.onWindowAttributesChanged(wl);
    // 设置点击外围解散
    mDialog.setCanceledOnTouchOutside(true);
    mDialog.show();
    TextView tv_cacle = (TextView) window.findViewById(R.id.forward_tv_cacle);
    TextView tv_title = (TextView) window.findViewById(R.id.tv_title);
    TextView tv_forward = (TextView) window.findViewById(R.id.forward_tv_forward);
    mForward_et = (EditText) window.findViewById(forward_et);
    ImageView forward_image = (ImageView) window.findViewById(R.id.forward_image);
    TextView forward_tv_putname = (TextView) window.findViewById(R.id.forward_tv_putname);
    TextView forward_tv_putcontent = (TextView) window.findViewById(R.id.forward_tv_putcontent);
    Log.i("test", "转发人 ：" + publicname + "转发内容 ：" + forward_tv_putcontent);
    if (publicname != null) {
      forward_tv_putname.setText(publicname);
      forward_tv_putcontent.setText(content);
    } else {
      forward_tv_putname.setText(content.substring(content.indexOf(0), content.indexOf(":")));
      forward_tv_putcontent.setText(content.substring(content.lastIndexOf(":"), content.length()));
    }
    if (mFilepaths != null && mFilepaths.size() > 0) {
      mImageLoader.displayImage(mFilepaths.get(0), forward_image);
    } else {
      mImageLoader.displayImage(ConstantsCode.DEFAULT_ICON, forward_image);
    }
    tv_cacle.setOnClickListener(this);
    tv_forward.setOnClickListener(this);
  }

  //转发留言
  private void forwardLeave() {
    //转发的标题
    mForwardTitle = mForward_et.getText().toString();
    StringBuffer buffer = new StringBuffer();
    if (mFilepaths != null && mFilepaths.size() > 0) {
      for (String filepath : mFilepaths) {
        buffer.append(filepath + ";");
      }
    }
    if (buffer.length() > 1) {
      buffer.deleteCharAt(buffer.length() - 1);
    }
    picUrls = buffer.toString();//拼接后的文件路径
    OkGo.post(Constants.LOCAL_ADD_WORDS)
        .params("activityid", activityid)
        .params("publishid", PreferenceUtils.getUserId())
        .params("words", mIsforward1.equals(ConstantsCode.STRING_ONE) ? mPublicname + mContent
            : "@" + mPublicname + ": " + mContent)
        .params("type", ConstantsCode.STRING_ZERO)
        .params("title", mForwardTitle == null ? "转发" : mForwardTitle)
        .params("isintransit", ConstantsCode.STRING_ONE)//转发的消息
        .params("files", picUrls)
        .execute(new com.lzy.okgo.callback.StringCallback() {
          @Override
          public void onSuccess(String s, Call call, Response response) {
            BackInfoModel infoModel = mGson.fromJson(s, BackInfoModel.class);
            ToastUtil.getInstant().show("转发成功");
            initDate(1, seei);
            mDialog.dismiss();
          }

          @Override
          public void onError(Call call, Response response, Exception e) {
            super.onError(call, response, e);
            mDialog.dismiss();
            ToastUtil.getInstant().show("转发失败");
          }
        });
  }

  //EvenBus接受Activity里面的点击只看我的
  public void onEventMainThread(LeaveTopMenuEvent event) {
    //加载只看我的第一页数据
    initDate(1, event.getSeei());
  }

  //EvenBus接受发送留言后刷新
  public void onEventMainThread(LeaveRefreshEvent event) {
    Log.i("test", "type留言墙 .." + event.getType());
    //加载只看我的第一页数据
    if (event.getType() == ConstantsCode.LEAVE_WALL_POST) {
      initDate(currentpage, seei);
    }
  }

  //发送点赞
  private void sendAddLike(String pid) {
    OkGo.post(Constants.LOCAL_GOODS)
        .params("space_wordsid", pid)
        .params("uid", PreferenceUtils.getUserId())
        .execute(new com.lzy.okgo.callback.StringCallback() {
          @Override
          public void onSuccess(String s, Call call, Response response) {
            mToastUtil.show("点赞成功");
          }
        });
  }

  //下拉加载更多
  private void loadMoreData(int currentpage) {
    currentpage++;
    final List<RecyclerBaseModel> list = new ArrayList<>();
    OkGo.post(Constants.LOCAL_GET_WORDS)
        .cacheMode(com.lzy.okgo.cache.CacheMode.REQUEST_FAILED_READ_CACHE)
        .cacheKey(ConstantsCode.CACHE_LEAVE_WALL_LOADMORE)
        .params("activityid", activityid)
        .params("uid", PreferenceUtils.getUserId())
        .params("type", ConstantsCode.STRING_ZERO)
        .params("seei", seei)
        .params("page", String.valueOf(currentpage))
        .params("page_size", "10")
        .execute(new com.lzy.okgo.callback.StringCallback() {
          @Override
          public void onSuccess(String s, Call call, Response response) {
            InteractiveRoomModel interactiveRoomModel = mGson
                .fromJson(s, InteractiveRoomModel.class);
            mItemInfos = interactiveRoomModel.getRow();
            for (LeavaItemInfo leavaItemInfo : mItemInfos) {
              RecyclerBaseModel recyclerBaseModel = leavaItemInfo;
              //设置布局ID
              recyclerBaseModel.setResLayoutId(LeaveWallHolder.ID);
              list.add(recyclerBaseModel);
            }
            //   isLoadMore = false;
            mCommonRecyclerAdapter.addListData(list);
            if (mCommonRecyclerAdapter != null) {
              mCommonRecyclerAdapter.notifyDataSetChanged();//刷新适配器
            }
            mXrcyLeaveList.loadMoreComplete();
            isLoadMore = false;
            list.clear();
          }
        });
  }

  //添加数据
  private void initDate(int currentpage, String seei) {
    this.seei = seei;
    OkGo.post(Constants.LOCAL_GET_WORDS)
        .cacheMode(com.lzy.okgo.cache.CacheMode.FIRST_CACHE_THEN_REQUEST)
        .cacheKey(ConstantsCode.CACHE_LEAVE_WALL)
        .params("activityid", activityid)
        .params("uid", PreferenceUtils.getUserId())
        .params("type", ConstantsCode.STRING_ZERO)
        .params("page", ConstantsCode.STRING_ONE)
        .params("seei", seei)
        .params("page_size", "10")
        .execute(new com.lzy.okgo.callback.StringCallback() {
          @Override
          public void onSuccess(String s, Call call, Response response) {
            mItemInfos.clear();
            mXrcyLeaveList.refreshComplete();
            InteractiveRoomModel interactiveRoomModel = mGson
                .fromJson(s, InteractiveRoomModel.class);
            mItemInfos = interactiveRoomModel.getRow();
            for (LeavaItemInfo leavaItemInfo : mItemInfos) {
              //设置布局ID
              leavaItemInfo.setResLayoutId(LeaveWallHolder.ID);
            }
            setDatas(mItemInfos);
            if (mCommonRecyclerAdapter != null) {
              mCommonRecyclerAdapter.notifyDataSetChanged();//刷新适配器
            }
          }
        });

  }

  public void setDatas(List datas) {
    this.mLeaveWallModels = datas;
    if (mCommonRecyclerAdapter != null) {
      mCommonRecyclerAdapter.setListData(datas);
    }
  }

  @Override
  public View getContentView() {
    return mRootView;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
    EventBus.getDefault().unregister(this);
  }

  //返回结果
  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
      case ConstantsCode.REQUEST_COMMON_CODE:
        initDate(currentpage, seei);
        break;
    }
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.forward_tv_cacle:
        mDialog.dismiss();
        break;
      case R.id.forward_tv_forward:
        forwardLeave();
        break;
    }
  }
}
