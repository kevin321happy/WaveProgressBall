package com.fips.huashun.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.db.dao.DepartmentDao;
import com.fips.huashun.db.dao.MemberDao;
import com.fips.huashun.modle.bean.EntOrganizationModle;
import com.fips.huashun.modle.bean.EntOrganizationModle.DataBean;
import com.fips.huashun.modle.bean.EntOrganizationModle.DataBean.DepListBean;
import com.fips.huashun.modle.bean.EntOrganizationModle.DataBean.MemberListBean;
import com.fips.huashun.modle.dbbean.DepartmentEntity;
import com.fips.huashun.modle.dbbean.MemberEntity;
import com.fips.huashun.ui.adapter.OrganizationAdapter;
import com.fips.huashun.ui.adapter.OrganizationAdapter.OnOrganizationItemClickListener;
import com.fips.huashun.ui.utils.AlertDialogUtils;
import com.fips.huashun.ui.utils.AlertDialogUtils.DialogInputInter;
import com.fips.huashun.ui.utils.CharacterParser;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.NavigationBar.NavigationListener;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.RuleTool;
import com.fips.huashun.ui.utils.ToastUtil;
import com.fips.huashun.ui.utils.Utils;
import com.fips.huashun.widgets.CustomEditText;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.BaseRequest;
import io.rong.imkit.RongIM;
import io.rong.imkit.mention.MemberMentionedActivity.PinyinComparator;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.RongIMClient.CreateDiscussionCallback;
import io.rong.imlib.RongIMClient.ErrorCode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import okhttp3.Call;
import okhttp3.Response;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * description: 企业组织架构 autour: Kevin company:锦绣氘(武汉)科技有限公司 date: 2017/4/20 18:53 update: 2017/4/20
 * version: 1.21 站在峰顶 看世界 落在谷底 思人生
 */

public class EntOrganizationActivity extends BaseActivity implements OnClickListener, TextWatcher {
  @Bind(R.id.nb_orgainization)
  NavigationBar mNbOrgainization;
  @Bind(R.id.et_search_friend)
  CustomEditText mEtSearchFriend;
  @Bind(R.id.tv_choosed)
  TextView mTvChoosed;
  @Bind(R.id.btn_confirm_choose)
  Button mBtnConfirmChoose;
  List<Integer> list = new ArrayList<>();
  @Bind(R.id.cb_check_all)
  CheckBox mCbCheckAll;
  @Bind(R.id.lv_dep_member)
  ListView mLvDepMember;
  @Bind(R.id.ll_member)
  LinearLayout mLlMember;
  @Bind(R.id.ll_have_choose_count)
  LinearLayout mLlHaveChooseCount;
  @Bind(R.id.ll_all_members)
  LinearLayout mLlAllMembers;


  private Gson mGson;
  private DepartmentDao mDepartmentDao;
  private MemberDao mMemberDao;
  private List<DepListBean> mDepList;
  private List<MemberListBean> mMemberList;
  private String pid = "0";//根据父部门的pid来获取部门下面的人员和部门数据
  private List dataList = new ArrayList();
  private OrganizationAdapter mOrganizationAdapter;
  private boolean mIsChecked;//是否已经勾选
  int mTotleCount;//选中的总数
  private String type = "0";//单聊的标识
  private List<MemberEntity> mMemberEntityList;
  private List<DepartmentEntity> mDepartmentEntities;
  private CharacterParser characterParser;// 汉字转拼音
  private PinyinComparator pinyinComparator;// 根据拼音来排列ListView里面的数据类
  private List<MemberEntity> mAllmemberEntityList;
  private long mChoosedCounts;
  private String mTargetId;


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_ent_organization);
    getWindow().setSoftInputMode(
        LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    ButterKnife.bind(this);
    Intent intent = getIntent();
    String dep_id = intent.getStringExtra("pid");
     mTargetId=intent.getStringExtra("targetId");
    //类型
    if (intent.getStringExtra("type") != null) {
      type = intent.getStringExtra("type");
    }
    String isChecked = intent.getStringExtra("isChecked");
    if (isChecked != null) {
      mIsChecked = isChecked.equals("1") ? true : false;
    }
    if (dep_id != null) {
      pid = dep_id;
    }
    mGson = new Gson();
    initView();
    //显示选中个数
    showCount();
    initData();
  }

  //显示当前的选中的个数
  private void showCount() {
    Observable.create(new OnSubscribe<Long>() {
      @Override
      public void call(Subscriber<? super Long> subscriber) {
        mChoosedCounts = mMemberDao.getChoosedCounts("1");
        subscriber.onNext(mChoosedCounts);
      }
    }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<Long>() {
          @Override
          public void onCompleted() {

          }

          @Override
          public void onError(Throwable e) {

          }

          @Override
          public void onNext(Long o) {
            mTvChoosed.setText("当前已经选中人数 ：" + o + "人");
          }
        });
  }

  private void initData() {
    //异步查询
    Observable.create(new OnSubscribe<List>() {
      @Override
      public void call(Subscriber<? super List> subscriber) {
        dataList.clear();
        mMemberEntityList = mMemberDao.queryMembersByPid(pid);
        mDepartmentEntities = mDepartmentDao.queryDepsByPid(pid);
        mAllmemberEntityList = mMemberDao.queryAllMembers();
        if (mDepartmentEntities.size() == 0 && mMemberEntityList.size() == 0) {
          subscriber.onNext(dataList);
        } else {
          //先添加人员
          if (mMemberEntityList != null && mMemberEntityList.size() > 0) {
            dataList.add("人员");
            dataList.addAll(mMemberEntityList);
          }
          //后添加部门
          if (mDepartmentEntities != null && mDepartmentEntities.size() > 0) {
            dataList.add("部门");
            dataList.addAll(mDepartmentEntities);
          }
          subscriber.onNext(dataList);
        }
      }
    }).subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<List>() {
          @Override
          public void onCompleted() {
          }

          @Override
          public void onError(Throwable e) {
          }

          @Override
          public void onNext(List list) {
            //设置数据刷新适配器
            if (list == null || list.size() == 0) {
              getDataFormNet();
            } else {
              mOrganizationAdapter.setData(list, mIsChecked);
              mLvDepMember.setAdapter(mOrganizationAdapter);
            }
          }
        });
  }

  //保存数据到数据库
  private void saveToDb(DataBean data) {
    //部门集合
    mDepList = data.getDepList();
    //成员集合
    mMemberList = data.getMemberList();
    for (DepListBean depListBean : mDepList) {
      DepartmentEntity entity = new DepartmentEntity();
      entity.setCompany_id(depListBean.getCompany_id() + "");
      entity.setDep_id(depListBean.getDid() + "");
      entity.setDep_name(depListBean.getDep_name());
      entity.setPid(depListBean.getPid() + "");
      entity.setNum(depListBean.getNum() + "");
      entity.setPeople_num(depListBean.getPeople_num() + "");
      entity.setAll_parent_id(depListBean.getAll_parent_id());
      mDepartmentDao.addDepartmentEntity(entity);
    }
    for (MemberListBean memberBean : mMemberList) {
      MemberEntity memberEntity = new MemberEntity();
      memberEntity.setCompany_id(memberBean.getCompany_id() + "");
      memberEntity.setAll_parent_id(memberBean.getAll_parent_id() + "");
      memberEntity.setDepartment_id(memberBean.getDepartment_id() + "");
      memberEntity.setDepartment_name(memberBean.getDepartment_name());
      memberEntity.setHead_image(memberBean.getHead_image());
      memberEntity.setJob_id(memberBean.getJob_id() + "");
      memberEntity.setJob_name(memberBean.getJob_name());
      memberEntity.setMember_name(memberBean.getMember_name());
      memberEntity.setUid(memberBean.getUid() + "");
      memberEntity.setRy_token(memberBean.getRy_token());
      memberEntity.setName_py(memberBean.getMember_name_pinyin());
      memberEntity.setIscheck("0");
      mMemberDao.addMemberEntity(memberEntity);
    }
    initData();
  }

  protected void initView() {
    mNbOrgainization.setTitle("组织架构");
    mNbOrgainization.setLeftImage(R.drawable.fanhui);
    mNbOrgainization.setListener(new NavigationListener() {
      @Override
      public void onButtonClick(int button) {
        if (button == NavigationBar.LEFT_VIEW) {
          finish();
        }
      }
    });
    characterParser = CharacterParser.getInstance();
    pinyinComparator = new PinyinComparator();
    mEtSearchFriend.addTextChangedListener(this);
    mDepartmentDao = new DepartmentDao(this);
    mMemberDao = new MemberDao(this);
    //设置取消焦点
    mEtSearchFriend.clearFocus();
    mOrganizationAdapter = new OrganizationAdapter(this, type);
    mLlHaveChooseCount.setVisibility(type.equals("0") ? View.GONE : View.VISIBLE);
    mLlAllMembers.setVisibility(type.equals("0") ? View.GONE : View.VISIBLE);
    mOrganizationAdapter.setOnOrganizationItemClickListener(mOnOrganizationItemClickListener);
    mBtnConfirmChoose.setOnClickListener(this);
    //点击全选的监听
    mCbCheckAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
          mIsChecked = true;
          showClickAllCounts(pid, true);
          initData();
        } else {
          mIsChecked = false;
          showClickAllCounts(pid, false);
          initData();
        }
      }
    });
  }

  //当点击全选时候显示界面
  private void showClickAllCounts(String pid, boolean checkAll) {
    if (checkAll == true) {
      List<MemberEntity> memberEntityList = mMemberDao.queryAllMemebersByPid(pid);
      mTvChoosed.setText("已选择人数：" + memberEntityList.size() + "人");
      mMemberDao.setCourrentChoosed(pid, checkAll);
    } else {
      mTvChoosed.setText("已选择人数：" + 0 + "人");
      mMemberDao.setCourrentChoosed(pid, checkAll);
    }


  }

  private OnOrganizationItemClickListener mOnOrganizationItemClickListener = new OnOrganizationItemClickListener() {
    @Override
    public void OnDepartmentItemClick(String dep_id, List chooseedItems, Boolean isChecked) {
      Intent intent = new Intent(EntOrganizationActivity.this, EntOrganizationActivity.class);
      intent.putExtra("pid", dep_id);
      intent.putExtra("isChecked", isChecked == true ? "1" : "0");
      intent.putExtra("type", type);
      startActivity(intent);
    }

    @Override
    public void OnMemberChoosed(String uid) {
      upDataMemberState(uid, true);

    }

    @Override
    public void OnMemberCancle(String uid) {
      upDataMemberState(uid, false);
    }

    @Override
    public void OnDepartmentChoosed(String dep_id) {
      upDataDepartmentState(dep_id, true);

    }

    @Override
    public void OnDepartmentCancle(String dep_id) {
      upDataDepartmentState(dep_id, false);
    }

    @Override
    public void OnMemberItemClick(String uid, String member_name) {
      //跳转聊天
      //单聊
      RongIM.getInstance().startPrivateChat(EntOrganizationActivity.this, uid, member_name);
    }
  };

  //更新部门的状态
  private void upDataDepartmentState(final String dep_id, final boolean isadd) {
    Observable.create(new OnSubscribe<Object>() {
      @Override
      public void call(Subscriber<? super Object> subscriber) {
        if (isadd) {
          mMemberDao.upDataDepartmentsStatesByPid(dep_id, true);
        } else {
          mMemberDao.upDataDepartmentsStatesByPid(dep_id, false);
        }
        subscriber.onNext(0);
      }
    }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<Object>() {
          @Override
          public void onCompleted() {

          }

          @Override
          public void onError(Throwable e) {

          }

          @Override
          public void onNext(Object o) {
            showCount();
          }
        });
  }

  //更新成员的信息
  private void upDataMemberState(final String uid, final boolean isadd) {
    Observable.create(new OnSubscribe<Object>() {
      @Override
      public void call(Subscriber<? super Object> subscriber) {
        MemberEntity memberEntity = mMemberDao.querMemmberByUid(uid);
        if (isadd) {
          memberEntity.setIscheck("1");
        } else {
          memberEntity.setIscheck("0");
        }
        mMemberDao.upDataMember(memberEntity);
        subscriber.onNext(0);
      }
    }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<Object>() {
          @Override
          public void onCompleted() {

          }

          @Override
          public void onError(Throwable e) {

          }

          @Override
          public void onNext(Object o) {
            showCount();
          }
        });
  }

  @Override
  public boolean isSystemBarTranclucent() {
    return false;
  }

  //点击了确定，创建群聊
  @Override
  public void onClick(View v) {
    List<MemberEntity> memberEntityList = mMemberDao.queryAllChooseMembers();
    final List<String> userids=new ArrayList<>();
    for (MemberEntity memberEntity : memberEntityList) {
      userids.add(memberEntity.getUid());
    }
    if (type.equals("2")){
      //添加到讨论组
      RongIM.getInstance().addMemberToDiscussion(mTargetId, userids, new RongIMClient.OperationCallback() {
        @Override
        public void onSuccess() {
          ToastUtil.getInstant().show("进来了...");
          RongIM.getInstance().startDiscussionChat(EntOrganizationActivity.this, mTargetId, "标题");
        }

        @Override
        public void onError(RongIMClient.ErrorCode errorCode) {

        }
      });

    }else {
      AlertDialogUtils.showTowBtnInputDialog(this, "创建讨论组", "取消", "创建", new DialogInputInter() {
        @Override
        public void leftClick(AlertDialog dialog) {
          dialog.dismiss();
        }

        @Override
        public void submit(String inputPwd, AlertDialog dialog) {
          mMemberDao.setAllMemberUnChoosed("8");
          startGroupChat(inputPwd, userids);
          dialog.dismiss();
        }
      });
    }
  }

  //开始群聊
  private void startGroupChat(final String dialog, List<String> list) {
    RongIM.getInstance().createDiscussion(dialog, list, new CreateDiscussionCallback() {
      @Override
      public void onSuccess(String s) {
        //跳转到界面
        RongIM.getInstance().startDiscussionChat(EntOrganizationActivity.this, s, dialog);
        finish();
        ToastUtil.getInstant().show(s);
      }

      @Override
      public void onError(ErrorCode errorCode) {

      }
    });
  }

  //网络获取数据
  public void getDataFormNet() {
    //获取组织架构的所有人
    HashMap<String, String> signdata = new HashMap<>();
    String timestamp= Utils.getCurrentTimestamp();
    signdata.put("company_id", PreferenceUtils.getCompanyId());
    signdata.put("timestamp",timestamp);
    signdata.put("userid", PreferenceUtils.getUserId());
    String[] signData = RuleTool.getSignData(this, signdata);
    //获取网络的数据
    OkGo.post(Constants.GETORGANIZATIONLIST_URL)
        .params("userid", PreferenceUtils.getUserId())
        .params("company_id", PreferenceUtils.getCompanyId())
        .params("timestamp",timestamp)
        .params("str",signData[1])
        .params("sign",signData[0])
        .params("qmct_token",PreferenceUtils.getQM_Token())
        .execute(new StringCallback() {
          @Override
          public void onBefore(BaseRequest request) {
            super.onBefore(request);
            showLoadingDialog();
          }

          @Override
          public void onSuccess(String s, Call call, Response response) {
            EntOrganizationModle organizationModle = mGson
                .fromJson(s, EntOrganizationModle.class);
            DataBean data = organizationModle.getData();
            saveToDb(data);
          }
        });
  }
  //搜索框的监听
  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {

  }
  @Override
  public void onTextChanged(CharSequence s, int start, int before, int count) {
    // 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
    filterData(s.toString(), mAllmemberEntityList);
  }

  /**
   * 根据输入框中的值来过滤数据并更新ListView
   */
  private void filterData(String filterStr, List<MemberEntity> memberEntityList) {
    List<MemberEntity> filterDateList = new ArrayList<MemberEntity>();
    if (TextUtils.isEmpty(filterStr)) {
      dataList.clear();
      //先添加部门
      if (mDepartmentEntities != null && mDepartmentEntities.size() > 0) {
        dataList.add("部门");
        dataList.addAll(mDepartmentEntities);
      }
      if (mMemberEntityList != null && mMemberEntityList.size() > 0) {
        dataList.add("人员");
        //后添加人员
        dataList.addAll(mMemberEntityList);
      }
      mOrganizationAdapter.setData(dataList, mIsChecked);
      mLvDepMember.setAdapter(mOrganizationAdapter);
    } else {
      dataList.clear();
      filterDateList.clear();
      for (MemberEntity sortModel : memberEntityList) {
        String name = sortModel.getMember_name();
        String suoxie = sortModel.getName_py();
        String final_suoxie = suoxie.substring(suoxie.lastIndexOf("-"));
        if (name.indexOf(filterStr.toString()) != -1 || suoxie.indexOf(filterStr.toString()) != -1
            ||
            characterParser.getSelling(name).startsWith(filterStr.toString()) ||
            final_suoxie.startsWith(filterStr.toLowerCase().toString())) {
          filterDateList.add(sortModel);
        }
      }
      //循环结束了
      if (mMemberEntityList != null && filterDateList.size() > 0) {
        dataList.add("人员");
      }
      //后添加人员
      dataList.addAll(filterDateList);
      mOrganizationAdapter.setData(dataList, mIsChecked);
      mLvDepMember.setAdapter(mOrganizationAdapter);
    }
    // 根据a-z进行排序
  }

  @Override
  public void afterTextChanged(Editable s) {

  }
}
