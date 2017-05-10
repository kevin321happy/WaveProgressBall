package com.fips.huashun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.fips.huashun.R;
import com.fips.huashun.common.CacheConstans;
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
import com.fips.huashun.ui.utils.CharacterParser;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.NavigationBar.NavigationListener;
import com.fips.huashun.ui.utils.ToastUtil;
import com.fips.huashun.widgets.CustomEditText;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.BaseRequest;
import io.rong.imkit.RongIM;
import io.rong.imkit.mention.MemberMentionedActivity.PinyinComparator;
import java.util.ArrayList;
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
  //  @Bind(R.id.lv_dep_list)
//  NoScrollListView mLvDepList;
  List<Integer> list = new ArrayList<>();
  @Bind(R.id.cb_check_all)
  CheckBox mCbCheckAll;
  @Bind(R.id.lv_dep_member)
  ListView mLvDepMember;
  @Bind(R.id.ll_member)
  LinearLayout mLlMember;
  @Bind(R.id.ll_have_choose_count)
  LinearLayout mLlHaveChooseCount;

  private Gson mGson;
  private DepartmentDao mDepartmentDao;
  private MemberDao mMemberDao;
  private List<DepListBean> mDepList;
  private List<MemberListBean> mMemberList;
  private String pid = "0";//根据父部门的pid来获取部门下面的人员和部门数据
  private List dataList = new ArrayList();
  private OrganizationAdapter mOrganizationAdapter;
  private int mCount = 0;
  private boolean mIsChecked;//是否已经勾选
  private int mChooseCount;
  int mTotleCount;//选中的总数
  int mCurrentCount;//当前页面的总人数
  private List mChooseMembers;
  private String type = "0";//单聊的标识
  private Object mDataFormNet;
  private List<MemberEntity> mMemberEntityList;
  private List<DepartmentEntity> mDepartmentEntities;
  private CharacterParser characterParser;// 汉字转拼音
  private PinyinComparator pinyinComparator;// 根据拼音来排列ListView里面的数据类
  private List<MemberEntity> mAllmemberEntityList;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_ent_organization);
    getWindow().setSoftInputMode(
        LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    ButterKnife.bind(this);
    Intent intent = getIntent();
    String dep_id = intent.getStringExtra("pid");
    mCount = intent.getIntExtra("chooseCount", 0);
    //类型
    if (intent.getStringExtra("type") != null) {
      type = intent.getStringExtra("type");
    }
    //已选择的总人数
    mChooseMembers = CacheConstans.getChooseMember();
    //显示选中个数
    showCount(mCount);
    String isChecked = intent.getStringExtra("isChecked");
    if (isChecked != null) {
      mIsChecked = isChecked.equals("1") ? true : false;
    }
    if (dep_id != null) {
      pid = dep_id;
    }
    mGson = new Gson();
    initView();
    initData();
  }


  //显示当前的选中的个数
  private void showCount(int count) {
    mTvChoosed.setText("当前已经选中人数 ：" + count + "人");
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
              //遍历获得当前的页面的总人数
              for (int i = 0; i < list.size(); i++) {
                if (list.get(i) instanceof DepartmentEntity) {
                  DepartmentEntity departmentEntity = (DepartmentEntity) list.get(i);
                  int people_num = Integer.valueOf(departmentEntity.getPeople_num());
                  //部门
                  mCurrentCount = mCurrentCount + people_num;
                } else if (list.get(i) instanceof MemberEntity) {
                  //成员
                  mCurrentCount++;
                }
              }
            }
          }
        });
  }

  //加入集合里面
  private void addToChooesed(List chooseedItems) {
    for (int i = 0; i < chooseedItems.size(); i++) {
      //添加到选中的临时集合
      mChooseMembers.add(chooseedItems.get(i));
    }
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
    mOrganizationAdapter.setOnOrganizationItemClickListener(mOnOrganizationItemClickListener);
    mBtnConfirmChoose.setOnClickListener(this);
  }

  private OnOrganizationItemClickListener mOnOrganizationItemClickListener = new OnOrganizationItemClickListener() {
    @Override
    public void OnDepartmentItemClick(String dep_id, List chooseedItems, Boolean isChecked) {
      addToChooesed(chooseedItems);
      //当点击部门条目,跳转
      Intent intent = new Intent(EntOrganizationActivity.this, EntOrganizationActivity.class);
      intent.putExtra("pid", dep_id);
      intent.putExtra("isChecked", isChecked == true ? "1" : "0");
      intent.putExtra("chooseCount", mTotleCount);
      intent.putExtra("type", type);
      ToastUtil.getInstant().show("当前选择了总人数 ；" + mTotleCount);
      startActivity(intent);
    }

    @Override
    public void OnMemberItemClick(String uid, String member_name) {
      //跳转聊天
      //单聊
      RongIM.getInstance().startPrivateChat(EntOrganizationActivity.this, uid, member_name);
    }

    //当条目被勾选
    @Override
    public void OnItemChoosed(List data) {
      mChooseCount = 0;
      for (int i = 0; i < data.size(); i++) {
        if (data.get(i) instanceof DepartmentEntity) {
          DepartmentEntity departmentEntity = (DepartmentEntity) data.get(i);
          int people_num = Integer.valueOf(departmentEntity.getPeople_num());
          //部门
          mChooseCount = mChooseCount + people_num;
        } else if (data.get(i) instanceof MemberEntity) {
          //成员
          mChooseCount++;
        }
      }
      if (mIsChecked) {
        //当前页面已经是全选了,再点击是取消
        mTotleCount = mCount - (mCurrentCount - mChooseCount);
      } else {
        mTotleCount = mCount + mChooseCount;
      }
      showCount(mTotleCount);
    }
  };

  @Override
  public boolean isSystemBarTranclucent() {
    return false;
  }

  //点击了确定，创建群聊
  @Override
  public void onClick(View v) {
    List<MemberEntity> list = new ArrayList<>();
    for (int i = 0; i < mChooseMembers.size(); i++) {
      for (int j = mChooseMembers.size() - 1; j > i; j--)  //内循环是 外循环一次比较的次数
      {
        if (mChooseMembers.get(i) == mChooseMembers.get(j)) {
          mChooseMembers.remove(j);
        }
      }
    }
    for (int i = 0; i < mChooseMembers.size(); i++) {
      if (mChooseMembers.get(i) instanceof MemberEntity) {
        MemberEntity entity = (MemberEntity) mChooseMembers.get(i);
        list.add(entity);
        Log.i("test00000",entity.toString());
      } else if (mChooseMembers.get(i) instanceof DepartmentEntity) {
        DepartmentEntity departmentEntity = (DepartmentEntity) mChooseMembers.get(i);
        List<MemberEntity> memberEntityList = mMemberDao
            .queryMembersByPid(departmentEntity.getDep_id());
        for (MemberEntity memberEntity : memberEntityList) {
          list.add(memberEntity);
          Log.i("test00000",memberEntity.toString());
        }

      }
    }

  }

  //网络获取数据
  public void getDataFormNet() {
    //获取网络的数据
    OkGo.post(Constants.GETORGANIZATIONLIST_URL)
        .params("company_id", "8")
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
//        suoxie.indexOf(filterStr.toString()) != -1
        if (characterParser.getSelling(name).startsWith(filterStr.toString())) {
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
//    Collections.sort(filterDateList, pinyinComparator);
//    mAdapter.updateListView(filterDateList);

  }

  @Override
  public void afterTextChanged(Editable s) {

  }
}
