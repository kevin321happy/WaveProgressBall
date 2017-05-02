package com.fips.huashun.ui.pager;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.common.ConstantsCode;
import com.fips.huashun.holder.ActivityListHolder;
import com.fips.huashun.modle.bean.ActivityListModel;
import com.fips.huashun.modle.event.ActListLoadEvent;
import com.fips.huashun.ui.activity.EnterpriseActDetail;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.ToastUtil;
import com.fips.huashun.widgets.CustomRefreshHeader;
import com.fips.huashun.widgets.pickerview.DividerItemDecoration;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.other.ProgressStyle;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.shuyu.common.CommonRecyclerAdapter;
import com.shuyu.common.CommonRecyclerManager;
import com.shuyu.common.listener.OnItemClickListener;
import com.shuyu.common.model.RecyclerBaseModel;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by kevin on 2017/1/10. 企业活动展示
 */
public class EnterpriseActPager {
  @Bind(R.id.xRecycler)
  XRecyclerView mXRecycler;
  private List<String> mList;
  private Context mContext;
  private View mRootView;
  private int position = 0;
  private String mUrl;
  private Gson mGson;
  private CommonRecyclerManager mCommonRecyclerManager;
  private List<RecyclerBaseModel> datalist = new ArrayList<>();
  private CommonRecyclerAdapter mRecyclerAdapter;
  private final Object lock = new Object();
  private String mEnterpriseId;//企业id
  private int type;//活动类型,开始、结束、未进行
  List<ActivityListModel.ActivityItemInfo> mItemInfos;
  private int currentpage = 1;
  private EventBus mEventBus;


  public EnterpriseActPager(Context context, int position, String enterpriseId) {
    this.mEnterpriseId = enterpriseId;
    this.mContext = context;
    this.position = position;
    type = position + 1;
    mRootView = View.inflate(mContext, R.layout.pager_act, null);
    ButterKnife.bind(this, mRootView);
    mGson = new Gson();
    mEventBus = EventBus.getDefault();
    //初始化数据
    initData(1);
    mCommonRecyclerManager = new CommonRecyclerManager();
    mCommonRecyclerManager.addType(ActivityListHolder.ID, ActivityListHolder.class.getName());
    mRecyclerAdapter = new CommonRecyclerAdapter(mContext, mCommonRecyclerManager, datalist);
    mXRecycler.setLayoutManager(new LinearLayoutManager(mContext));
    mXRecycler.setAdapter(mRecyclerAdapter);

    mRecyclerAdapter.setOnItemClickListener(mOnItemClickListener);
    mXRecycler.setLoadingListener(mLoadingListener);
    mXRecycler.setLoadingMoreEnabled(true);
    mXRecycler.setRefreshHeader(new CustomRefreshHeader(mContext));
    mXRecycler.setLoadingMoreProgressStyle(ProgressStyle.BallScaleMultiple);
    mRecyclerAdapter.setNeedAnimation(true);
    //添加分割线
    mXRecycler
        .addItemDecoration(new DividerItemDecoration(dip2px(mContext, 3), dip2px(mContext, 3), 1));
  }

  /**
   * 当条目被点击
   */
  private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {
    @Override
    public void onItemClick(Context context, int position) {
      if (mItemInfos != null) {
        Intent intent = new Intent(mContext, EnterpriseActDetail.class);
        intent.putExtra("activityid", mItemInfos.get(position - 1).getPid());
        mContext.startActivity(intent);
      }
    }
  };
  /**
   * 加载监听
   */
  private XRecyclerView.LoadingListener mLoadingListener = new XRecyclerView.LoadingListener() {
    @Override
    public void onRefresh() {
      mXRecycler.postDelayed(new Runnable() {
        @Override
        public void run() {
          initData(1);
        }
      }, 1000);
    }

    @Override
    public void onLoadMore() {
      mXRecycler.postDelayed(new Runnable() {
        @Override
        public void run() {
          currentpage++;
          LoadMoreData(currentpage);
        }
      }, 1000);
    }
  };

  //加载更多
  private void LoadMoreData(int currentpage) {
    final List<RecyclerBaseModel> list = new ArrayList<>();

    OkGo.post(Constants.LOCAL_ACTIVITYINFOLIST)
        .params("enid", mEnterpriseId)
        .params("uid", PreferenceUtils.getUserId())
        .params("type", String.valueOf(type))
        .params("page", currentpage + "")
        .params("page_size", "10")
        .execute(new StringCallback() {
          @Override
          public void onSuccess(String s, Call call, Response response) {
            ActivityListModel activityListModel = mGson.fromJson(s, ActivityListModel.class);
            //设置数据
            List<ActivityListModel.ActivityItemInfo> activityItemInfos = activityListModel.getRow();
            for (ActivityListModel.ActivityItemInfo activityItemInfo : activityItemInfos) {
              RecyclerBaseModel recyclerBaseModel = activityItemInfo;
              recyclerBaseModel.setResLayoutId(ActivityListHolder.ID);
              list.add(recyclerBaseModel);
            }
            if (list.size() < 1) {
              ToastUtil.getInstant().show("亲,没有更多数据了...");
            }
            mRecyclerAdapter.addListData(list);
            list.clear();
          }
          @Override
          public void onError(Call call, Response response, Exception e) {
            super.onError(call, response, e);
          }

          @Override
          public void onAfter(@Nullable String s, @Nullable Exception e) {
            super.onAfter(s, e);
            mXRecycler.refreshComplete();
          }
        });
  }

  //联网加载数据
  private void initData(final int currentpage) {
    //未开始活动
    OkGo.post(Constants.LOCAL_ACTIVITYINFOLIST)
        .params("enid", mEnterpriseId)
        .params("uid", PreferenceUtils.getUserId())
        .params("type", String.valueOf(type))
        .params("page", "1")
        .params("page_size", "10")
        .cacheMode(com.lzy.okgo.cache.CacheMode.REQUEST_FAILED_READ_CACHE)
        .cacheKey(ConstantsCode.CACHE_ACTIVITY_LIST)
        .execute(new com.lzy.okgo.callback.StringCallback() {
          @Override
          public void onBefore(com.lzy.okgo.request.BaseRequest request) {
            super.onBefore(request);
            mEventBus.post(new ActListLoadEvent(true));
          }
          @Override
          public void onSuccess(String s, Call call, Response response) {
            ActivityListModel activityListModel = mGson.fromJson(s, ActivityListModel.class);
            //设置数据
            mItemInfos = activityListModel.getRow();
            for (ActivityListModel.ActivityItemInfo itemInfo : mItemInfos) {
              //设置布局id
              itemInfo.setResLayoutId(ActivityListHolder.ID);
            }
            setDatas(mItemInfos);
          }
          @Override
          public void onAfter(@Nullable String s, @Nullable Exception e) {
            super.onAfter(s, e);
            mEventBus.post(new ActListLoadEvent(false));
            mXRecycler.refreshComplete();
          }
          @Override
          public void onError(Call call, Response response, Exception e) {
            super.onError(call, response, e);
            mEventBus.post(new ActListLoadEvent(false));
          }
        });
  }

  public void setDatas(List datas) {
    this.datalist = datas;
    if (mRecyclerAdapter != null) {
      mRecyclerAdapter.setListData(datas);
    }
  }
  //返回根布局
  public View getView() {
    return mRootView;
  }
  /*
  * dip转为PX
  */
  public static int dip2px(Context context, float dipValue) {
    float fontScale = context.getResources().getDisplayMetrics().density;
    return (int) (dipValue * fontScale + 0.5f);
  }
}