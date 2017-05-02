package com.fips.huashun.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.modle.bean.HadSignUpModel;
import com.fips.huashun.ui.adapter.HadSignUpAdapter;
import com.fips.huashun.ui.utils.NavigationBar;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.BaseRequest;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Response;

//已报名人展示
public class HadSignUpActivity extends BaseActivity {
    @Bind(R.id.ent_nb_had_signup)
    NavigationBar mEntNbHadSignup;
    @Bind(R.id.lv_had_sign_up)
    PullToRefreshListView pullToRefreshListView;

    private int currentpage = 1;
    private ListView mRefreshableView;
    private HadSignUpAdapter mHadSignUpAdapter;
    private String activityid;
    private String PAGE_SIZE = "20";
    private Gson mGson;
    private List<HadSignUpModel.HadSignUpList> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_had_sign_up);
        ButterKnife.bind(this);
        activityid = getIntent().getStringExtra("activityid");
        mGson = new Gson();
        mList = new ArrayList<>();
        Log.d("HadSignUpActivity", getIntent().getStringExtra("activityid"));
        initView();
        initData(1);
    }
    protected void initView() {
        mEntNbHadSignup.setLeftImage(R.drawable.fanhui);
        mEntNbHadSignup.setTitle("已报名者");
        mEntNbHadSignup.setListener(new NavigationBar.NavigationListener() {
            @Override
            public void onButtonClick(int button) {
                if (button == NavigationBar.LEFT_VIEW) {
                    finish();
                }
            }
        });
        //从底部刷新
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        mRefreshableView = pullToRefreshListView.getRefreshableView();
        mRefreshableView.setFooterDividersEnabled(false);//隐藏底部的分割线
        mHadSignUpAdapter = new HadSignUpAdapter(getApplicationContext());
        pullToRefreshListView.setOnRefreshListener(mOnRefreshListener2);
    }
    private void initData(int currentpage) {

        OkGo.post(Constants.LOCAL_SIGN_UP_LIST)
                .params("activityid", activityid)
                .params("cur_page", currentpage+"")
                .params("page_size", PAGE_SIZE)
            .execute(new StringCallback() {
                @Override
                public void onBefore(BaseRequest request) {
                    super.onBefore(request);
                }
                @Override
                public void onSuccess(String s, Call call, Response response) {
                    HadSignUpModel hadSignUpList = mGson.fromJson(s, HadSignUpModel.class);
                    mList = hadSignUpList.getRow();
                    mHadSignUpAdapter.setData(mList);
                    mRefreshableView.setAdapter(mHadSignUpAdapter);
                    mHadSignUpAdapter.notifyDataSetChanged();
                    // 结束刷新
                    pullToRefreshListView.onRefreshComplete();
                }

                @Override
                public void onError(Call call, Response response, Exception e) {
                    super.onError(call, response, e);
                }
            });
    }

    private PullToRefreshBase.OnRefreshListener2 mOnRefreshListener2 = new PullToRefreshBase.OnRefreshListener2() {
        @Override
        public void onPullDownToRefresh(PullToRefreshBase refreshView) {

        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase refreshView) {
            //上拉加载更多
            currentpage++;
            initData(currentpage);
        }
    };


    @Override
    public boolean isSystemBarTranclucent() {
        return false;
    }
}
