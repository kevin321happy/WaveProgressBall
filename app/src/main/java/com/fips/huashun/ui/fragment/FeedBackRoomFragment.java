package com.fips.huashun.ui.fragment;

import static com.fips.huashun.R.id.feedback_tv_counts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.common.ConstantsCode;
import com.fips.huashun.holder.FeedBackHolder;
import com.fips.huashun.modle.bean.BackInfoModel;
import com.fips.huashun.modle.bean.InteractiveRoomModel;
import com.fips.huashun.modle.bean.LeavaItemInfo;
import com.fips.huashun.modle.bean.SpaceNoticeModel;
import com.fips.huashun.modle.event.FeedBackEvent;
import com.fips.huashun.modle.event.FeedTopMenuEvent;
import com.fips.huashun.modle.event.LeaveRefreshEvent;
import com.fips.huashun.ui.activity.LeaveDetailActivity;
import com.fips.huashun.ui.utils.DisplayUtil;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.ToastUtil;
import com.fips.huashun.widgets.CommonDialog;
import com.fips.huashun.widgets.CustomRefreshHeader;
import com.fips.huashun.widgets.StudentMarkDialog;
import com.fips.huashun.widgets.pickerview.DividerItemDecoration;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.other.ProgressStyle;
import com.lzy.okgo.OkGo;
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
 * Created by kevin on 2017/1/15. 反馈室
 */
@SuppressLint("ValidFragment")
public class FeedBackRoomFragment extends BaseFragment {

  @Bind(feedback_tv_counts)
  TextView mFeedbackTvCounts;
  @Bind(R.id.feedback_ll_counts)
  LinearLayout mFeedbackLlCounts;
  @Bind(R.id.feedback_xrcy)
  XRecyclerView mFeedbackXrcy;
  @Bind(R.id.feedback_top_content)
  TextView mFeedbackTopContent;
  @Bind(R.id.ll_top)
  RelativeLayout mLlTop;
  private View mRootView;
  private List<RecyclerBaseModel> mFeedBackModels = new ArrayList<>();
  private CommonRecyclerManager mCommonRecyclerManager;
  private CommonRecyclerAdapter mCommonRecyclerAdapter;
  private String activityid;
  private int currentpage = 1;
  private Gson mGson;
  private String seei = ConstantsCode.STRING_ZERO;//是否只看我的0全部
  private List<LeavaItemInfo> mItemInfos = new ArrayList<>();
  private boolean isLoadMore;//是否加载跟多
  private String islmark = "3";//未评
  private Context mContext;
  private ToastUtil mToastUtil;
  private String mIsmanager = ConstantsCode.STRING_ZERO;//是否管理员
  private String mPid;
  private String mType;
  private boolean FEEDBACKOPEN;
  private String total;//反馈人数

  public FeedBackRoomFragment() {
  }

  public FeedBackRoomFragment(String activityid, boolean FEEDBACKOPEN) {
    this.activityid = activityid;
    this.FEEDBACKOPEN = FEEDBACKOPEN;
  }

  public FeedBackRoomFragment(String activityid) {
    this.activityid = activityid;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    mContext = container.getContext();
    mRootView = inflater.inflate(R.layout.feed_back_room, container, false);
    ButterKnife.bind(this, mRootView);
    if (!FEEDBACKOPEN) {
      mLlTop.setVisibility(View.VISIBLE);
      mRootView.setEnabled(false);
    }
    mFeedbackTopContent = (TextView) mRootView.findViewById(R.id.feedback_top_content);
    EventBus.getDefault().register(this);
    return mRootView;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(getActivity());
    mToastUtil = ToastUtil.getInstant();
    mGson = new Gson();
    mFeedbackXrcy = (XRecyclerView) mRootView.findViewById(R.id.feedback_xrcy);
    //如果开放加载数据
    if (FEEDBACKOPEN) {
      initData(currentpage, seei);
      initNoticeData();
    }
    initView();
    initListener();
  }

  //初始化公告信息信息
  private void initNoticeData() {
    OkGo.post(Constants.LOCAL_GET_PROCLAMATION)
        .params("activityid", activityid)
        .params("type", "2")
        .execute(new com.lzy.okgo.callback.StringCallback() {
          @Override
          public void onSuccess(String s, Call call, Response response) {
            SpaceNoticeModel noticeModel = mGson.fromJson(s, SpaceNoticeModel.class);
            UpDataNotice(noticeModel);
          }
        });
  }

  //更新公告的界面
  private void UpDataNotice(SpaceNoticeModel noticeModel) {
    mFeedbackTopContent.setText(noticeModel.getProclamation());
  }

  //初始化监听
  private void initListener() {
    mFeedbackXrcy.addOnScrollListener(new LoadMoreScrollListener() {
      @Override
      public void onLoadMore() {
        //注意加锁
        if (!isLoadMore) {
          isLoadMore = true;
          mFeedbackXrcy.postDelayed(new Runnable() {
            @Override
            public void run() {
              currentpage++;
              loadMoreData(currentpage);//加载更多
            }
          }, 1500);
        }
      }

      @Override
      public void onScrolled(int firstPosition) {
      }
    });
    mFeedbackXrcy.setLoadingListener(new XRecyclerView.LoadingListener() {
      @Override
      public void onRefresh() {
        seei = ConstantsCode.STRING_ZERO;
        islmark = "3";
        mFeedbackXrcy.postDelayed(new Runnable() {
          @Override
          public void run() {
            initData(1, seei);
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
    mCommonRecyclerManager.addType(FeedBackHolder.ID, FeedBackHolder.class.getName());
    mCommonRecyclerAdapter = new CommonRecyclerAdapter(getActivity(), mCommonRecyclerManager,
        mFeedBackModels);
    mFeedbackXrcy.setLayoutManager(new LinearLayoutManager(getActivity()));
    mFeedbackXrcy.setAdapter(mCommonRecyclerAdapter);
    //分割线和下拉刷新
    mFeedbackXrcy.setLoadingMoreEnabled(true);
    mFeedbackXrcy.setRefreshHeader(new CustomRefreshHeader(getContext()));
    mFeedbackXrcy.setLoadingMoreProgressStyle(ProgressStyle.BallScaleMultiple);
    mFeedbackXrcy.addItemDecoration(
        new DividerItemDecoration(DisplayUtil.dp2px(this.getActivity(), 10), 0, 0));

    mCommonRecyclerAdapter.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(Context context, int position) {
        if (mItemInfos == null || mItemInfos.size() <= 0) {
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

  //加载数据
  private void initData(int currentpage, final String seei) {
    OkGo.post(Constants.LOCAL_GET_WORDS)
        .cacheMode(com.lzy.okgo.cache.CacheMode.REQUEST_FAILED_READ_CACHE)
        .cacheKey(ConstantsCode.CACHE_FEEDBACK)
        .params("activityid", activityid)
        .params("uid", PreferenceUtils.getUserId())
        .params("type", ConstantsCode.STRING_ONE)
        .params("page", ConstantsCode.STRING_ONE)
        .params("islmark", islmark)
        .params("seei", seei)
        .params("page_size", "10")
        .execute(new com.lzy.okgo.callback.StringCallback() {
          @Override
          public void onSuccess(String s, Call call, Response response) {
            InteractiveRoomModel interactiveRoomModel = mGson
                .fromJson(s, InteractiveRoomModel.class);
            total = interactiveRoomModel.getTotal() + "";
            //显示总人数
            mFeedbackTvCounts.setText(total + "");
            //判断是否管理员
            mIsmanager = interactiveRoomModel.getIsmanager();
            mItemInfos = interactiveRoomModel.getRow();
            for (LeavaItemInfo leavaItemInfo : mItemInfos) {
              //设置布局ID
              leavaItemInfo.setResLayoutId(FeedBackHolder.ID);
            }
            setDatas(mItemInfos);
            if (mCommonRecyclerAdapter != null) {
              mCommonRecyclerAdapter.notifyDataSetChanged();
            }
            mFeedbackXrcy.refreshComplete();
          }
        });
  }

  public void setDatas(List datas) {
    this.mFeedBackModels = datas;
    if (mCommonRecyclerAdapter != null) {
      mCommonRecyclerAdapter.setListData(datas);
    }
  }

  //下拉加载更多
  private void loadMoreData(int currentpage) {
    currentpage++;
    final List<RecyclerBaseModel> list = new ArrayList<>();
    OkGo.post(Constants.LOCAL_GET_WORDS)
        .cacheMode(com.lzy.okgo.cache.CacheMode.REQUEST_FAILED_READ_CACHE)
        .cacheKey(ConstantsCode.CACHE_FEEDBACK_LOADMORE)
        .params("activityid", activityid)
        .params("uid", PreferenceUtils.getUserId())
        .params("type", ConstantsCode.STRING_ONE)
        .params("seei", seei)
        .params("islmark", islmark)
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
              recyclerBaseModel.setResLayoutId(FeedBackHolder.ID);
              list.add(recyclerBaseModel);
            }
            mCommonRecyclerAdapter.addListData(list);
            if (mCommonRecyclerAdapter != null) {
              mCommonRecyclerAdapter.notifyDataSetChanged();
              mFeedbackXrcy.loadMoreComplete();
              isLoadMore = false;
            }
            list.clear();
          }
        });
  }

  //EvenBus接受发送留言后刷新
  public void onEventMainThread(LeaveRefreshEvent event) {
    //加载只看我的第一页数据
    if (event.getType() == ConstantsCode.FEED_BACK_POST) {
      initData(currentpage, seei);
    }
  }

  //EvenBus接受Activity里面的点击只看我的
  public void onEventMainThread(FeedTopMenuEvent event) {
    switch (event.getType()) {
      case ConstantsCode.STRING_ONE:
        //只看我的
        initData(currentpage, event.getSeei());
        break;
      case "2":
        if (event.isLecturer()) {//已评分
          islmark = ConstantsCode.STRING_ONE;
          initData(currentpage, seei);
        } else {//未评分
          islmark = "2";
          initData(currentpage, seei);
        }
        break;
    }
    //加载只看我的第一页数据
    initData(1, event.getSeei());
  }

  //EventBus接受的反馈室的评论点赞
  public void onEventMainThread(FeedBackEvent event) {
    mPid = event.getPid();
    //文件路径
    switch (event.getType()) {
      case ConstantsCode.STRING_ONE:
        //普通评分
        showCommonDialog(mIsmanager, mPid);
        break;
      case "2":
        //进入评论页
        String itemjson = event.getItemjson();
        Intent intent = new Intent(mContext, LeaveDetailActivity.class);
        intent.putExtra("json", itemjson);
        startActivityForResult(intent, ConstantsCode.REQUEST_COMMON_CODE);
        break;
      case "3":
        //发送点赞
        sendAddLike(event.getPid());
        break;
      case "4":
        //讲师评分
        showLecturerCommon(mPid);
        break;
      case "5":
        deleteLeave(mPid);
        break;
    }
  }

  //显示讲师评分
  private void showLecturerCommon(String pid) {
    if (!mIsmanager.equals(ConstantsCode.STRING_ONE)) {
      return;
    }
    CommonDialog commonDialog = new CommonDialog(getActivity());
    commonDialog.setTitle("管理员评分");
    commonDialog.show();
    commonDialog.setOnMarkClickListener(mOnMarkClickListener);
  }
//删除留言
  private void deleteLeave(String pid) {
    OkGo.post(Constants.LOCAL_DEL_WORDS)
        .params("pid", pid)
        .execute(new com.lzy.okgo.callback.StringCallback() {
          @Override
          public void onSuccess(String s, Call call, Response response) {
            mToastUtil.show("删除成功");
            initData(currentpage, seei);
          }
        });
  }

  //显示普通评分
  private void showCommonDialog(String ismanager, String pid) {
    if (ismanager.equals(ConstantsCode.STRING_ONE)) {
      return;
    }
    //普通评分
    StudentMarkDialog markDialog = new StudentMarkDialog(getActivity());
    markDialog.show();
    markDialog.setOnConfirmListener(new StudentMarkDialog.OnConfirmListener() {
      @Override
      public void onClick(int mark) {
        if (mark == 0) {
          mToastUtil.show("您未评分！");
          return;
        }
        String score = mark + "";
        sendFeedbackMark(score, mPid);
      }
    });
  }

  private CommonDialog.OnMarkClickListener mOnMarkClickListener = new CommonDialog.OnMarkClickListener() {
    @Override
    public void onmarkChoosed(int leftvalue, int rightvalue) {
      String score = leftvalue + "." + rightvalue;
      //发送管理员评分
      sendLecturerMark(score, mPid);
    }
  };

  //发送管理员评分
  private void sendLecturerMark(String score, String pid) {
    OkGo.post(Constants.LOCAL_LECTORMARK)
        .params("pid", mPid)
        .params("lectorid", PreferenceUtils.getUserId())
        .params("lectormark", score)
        .execute(new com.lzy.okgo.callback.StringCallback() {
          @Override
          public void onSuccess(String s, Call call, Response response) {
            mToastUtil.show("评分成功");
            initData(currentpage, seei);
          }
        });
  }

  //发送反馈评分
  private void sendFeedbackMark(String score, String pid) {
    OkGo.post(Constants.LOCAL_MARK)
        .params("space_wordsid", mPid)
        .params("uid", PreferenceUtils.getUserId())
        .params("mark", score)
        .execute(new com.lzy.okgo.callback.StringCallback() {
          @Override
          public void onSuccess(String s, Call call, Response response) {
            mToastUtil.show("评分成功");
            initData(currentpage, seei);
          }
        });
  }

  //发送点赞
  private void sendAddLike(String pid) {
    OkGo.post(Constants.LOCAL_GOODS)
        .params("space_wordsid", pid)
        .params("uid", PreferenceUtils.getUserId())
        .execute(new com.lzy.okgo.callback.StringCallback() {
          @Override
          public void onSuccess(String s, Call call, Response response) {
            BackInfoModel backInfoModel = mGson.fromJson(s, BackInfoModel.class);
            String suc = backInfoModel.getSuc();
            mToastUtil.show("点赞成功");
          }
        });
  }


  @Override
  public View getContentView() {
    return mRootView;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    EventBus.getDefault().unregister(this);
    ButterKnife.unbind(this);
  }

  //返回结果
  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
      case ConstantsCode.REQUEST_COMMON_CODE:
        initData(currentpage, seei);
        break;
    }
  }
}
