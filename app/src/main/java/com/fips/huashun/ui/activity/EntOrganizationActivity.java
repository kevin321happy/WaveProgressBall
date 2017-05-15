package com.fips.huashun.ui.activity;

import android.os.Bundle;
<<<<<<< HEAD
import android.util.Log;
import android.view.WindowManager;
=======
import android.support.v7.widget.RecyclerView;
>>>>>>> f8c163e9f9b16c6f8465981156b159495b4df8c8
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.fips.huashun.R;
<<<<<<< HEAD
import com.fips.huashun.modle.testbean.DepList;
import com.fips.huashun.modle.testbean.DepMember;
import com.fips.huashun.ui.adapter.DepListAdapter;
import com.fips.huashun.ui.adapter.DepListAdapter.onDepartmentSelectedListener;
import com.fips.huashun.ui.adapter.DepMemberAdapter;
import com.fips.huashun.ui.adapter.DepMemberAdapter.onMemberSelectedListener;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.NavigationBar.NavigationListener;
import com.fips.huashun.ui.utils.ToastUtil;
import com.fips.huashun.widgets.CustomEditText;
import com.fips.huashun.widgets.NoScrollListView;
import java.util.ArrayList;
import java.util.List;
=======
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.NavigationBar.NavigationListener;
import com.fips.huashun.widgets.CustomEditText;
>>>>>>> f8c163e9f9b16c6f8465981156b159495b4df8c8

/**
 * description: 企业组织架构 autour: Kevin company:锦绣氘(武汉)科技有限公司 date: 2017/4/20 18:53 update: 2017/4/20
 * version: 1.21 站在峰顶 看世界 落在谷底 思人生
 */

public class EntOrganizationActivity extends BaseActivity {

  @Bind(R.id.nb_orgainization)
  NavigationBar mNbOrgainization;
  @Bind(R.id.et_search_friend)
  CustomEditText mEtSearchFriend;
  @Bind(R.id.cb_check_all)
  CheckBox mCbCheckAll;
  @Bind(R.id.ll_member)
  LinearLayout mLlMember;
<<<<<<< HEAD

//  @Bind(R.id.rcv_member)
//  RecyclerView mRcvMember;
//  @Bind(R.id.rcv_department)
//  RecyclerView mRcvDepartment;

=======
  @Bind(R.id.rcv_member)
  RecyclerView mRcvMember;
  @Bind(R.id.rcv_department)
  RecyclerView mRcvDepartment;
>>>>>>> f8c163e9f9b16c6f8465981156b159495b4df8c8
  @Bind(R.id.ll_department)
  LinearLayout mLlDepartment;
  @Bind(R.id.tv_choosed)
  TextView mTvChoosed;
  @Bind(R.id.btn_confirm_choose)
  Button mBtnConfirmChoose;
<<<<<<< HEAD
  @Bind(R.id.lv_dep_list)
  NoScrollListView mLvDepList;
  @Bind(R.id.lv_dep_member)
  NoScrollListView mLvDepMember;
  private List<DepList> mDepLists = new ArrayList<>();
  private List<DepMember> mDepMembers = new ArrayList<>();
  List<Integer> list = new ArrayList<>();

  private DepListAdapter mDepListAdapter;
  private DepMemberAdapter mMemberAdapter;

  private List<DepList> mDepselected = new ArrayList<>();
  private List<DepMember> mMemberselected = new ArrayList<>();
=======
>>>>>>> f8c163e9f9b16c6f8465981156b159495b4df8c8

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_ent_organization);
<<<<<<< HEAD
    getWindow().setSoftInputMode(
        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    ButterKnife.bind(this);
    //设置取消焦点
//    mEtSearchFriend.setFocusable(false);
    mEtSearchFriend.clearFocus();
    mDepListAdapter = new DepListAdapter();
    mMemberAdapter = new DepMemberAdapter();
    //绑定适配器
    mLvDepList.setAdapter(mDepListAdapter);
    mLvDepMember.setAdapter(mMemberAdapter);
    //监听
    mDepListAdapter.setOnDepartmentSelectedListener(mOnDepartmentSelectedListener);
    mMemberAdapter.setOnMemberSelectedListener(mOnMemberSelectedListener);
    initData();
  }

  private onMemberSelectedListener mOnMemberSelectedListener = new onMemberSelectedListener() {
    @Override
    public void onMembersSelected(List<DepMember> list) {
//      if (mMemberselected.size() > 0) {
//        mMemberselected.clear();
//      }
      mMemberselected=list;
      ToastUtil.getInstant().show("回调过来的成员数 ：" + list.size());
      Log.i("test", "回调过来的成员数 ：" + list.size());
      showCount();
    }
  };
  private onDepartmentSelectedListener mOnDepartmentSelectedListener = new onDepartmentSelectedListener() {
    @Override
    public void onDepSelected(List<DepList> lists) {
//      if (mDepselected.size() > 0) {
//        mDepselected.clear();
//      }
      mDepselected = lists;
      ToastUtil.getInstant().show("回调过来的部门数 ：" + lists.size());
      Log.i("test", "回调过来的部门数 ：" + lists.size());
      showCount();
    }
  };

  //显示当前的选中的个数
  private void showCount() {
    list.clear();
    int count=0;
    Log.i("test", "部门数 ：" + mDepselected.size() + "成员数 ：" + mMemberselected.size());
    for (DepList depList : mDepselected) {
      String number = depList.getNumber();
      Integer integer = Integer.valueOf(number);
      count=count+integer;
    }
    count=count+mMemberselected.size();
//    int count = mDepselected.size() + mMemberselected.size();
//    list.add(mDepselected.size());
//    list.add(mMemberselected.size());
    Log.i("test", "选中的总数为" + list.size() + "");
//    ToastUtil.getInstant().show("当前的总人数 ："+list.size()+"人");
    mTvChoosed.setText("当前已经选中人数 ：" + count + "人");
//    ToastUtil.getInstant().show();
  }


  private void initData() {
    for (int i = 0; i < 10; i++) {
      DepList depList = new DepList("http://v1.52qmct.com/qmct.jpg",
          "当前部门" + i, i * 5 + "");
      mDepLists.add(depList);
    }
    for (int i = 0; i < 10; i++) {
      DepMember member = new DepMember("老藤", "今晚打老虎", "http://v1.52qmct.com/qmct.jpg");
      mDepMembers.add(member);
    }
    //设置数据
//    mDepListAdapter.setData(mDepLists);

    mMemberAdapter.setData(mDepMembers);

    mDepListAdapter.setData(mDepLists);
=======
    ButterKnife.bind(this);
    initData();


  }

  private void initData() {


>>>>>>> f8c163e9f9b16c6f8465981156b159495b4df8c8

  }

  public void initView() {
    mNbOrgainization.setTitle("企业圈");
    mNbOrgainization.setLeftImage(R.drawable.ico_back);
    mNbOrgainization.setListener(new NavigationListener() {
      @Override
      public void onButtonClick(int button) {
        if (button == NavigationBar.LEFT_VIEW) {
          finish();
        }
      }
    });
<<<<<<< HEAD
=======


>>>>>>> f8c163e9f9b16c6f8465981156b159495b4df8c8
  }

  @Override
  public boolean isSystemBarTranclucent() {
    return false;
  }
}
