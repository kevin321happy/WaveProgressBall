package com.fips.huashun.ui.adapter;


import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.fips.huashun.R;
import com.fips.huashun.modle.dbbean.DepartmentEntity;
import com.fips.huashun.modle.dbbean.MemberEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * description: 组织架构的Adapter分栏显示不同的条目
 * autour: Kevin
 * company:锦绣氘(武汉)科技有限公司
 * date: 2017/5/9 22:02
 * update: 2017/5/9
 * version: 1.2.3
 * 站在峰顶 看世界
 * 落在谷底 思人生
 */
public class OrganizationAdapter extends BaseAdapter {

  private List mData;
  private Context mContext;
  private OnOrganizationItemClickListener mOnOrganizationItemClickListener;
  private HashMap<Integer, Boolean> mSelected = new HashMap<>();
  private List mChooseedItems = new ArrayList();//被选中的集合
  private String chatType;

  public HashMap<Integer, Boolean> getSelected() {
    return mSelected;
  }

  public void setSelected(HashMap<Integer, Boolean> selected) {
    mSelected = selected;
  }

  public void setOnOrganizationItemClickListener(
      OnOrganizationItemClickListener onOrganizationItemClickListener) {
    mOnOrganizationItemClickListener = onOrganizationItemClickListener;
  }

  public OrganizationAdapter(Context context, String type) {
    mContext = context;
    this.chatType = type;
  }

  @Override
  public int getCount() {
    return mData.size();
  }

  @Override
  public Object getItem(int position) {
    return null;
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }

  @Override
  public View getView(final int position, View convertView, ViewGroup parent) {
    final Boolean isChecked = mSelected.get(position);
    vh v = null;
    vh2 v2 = null;
    vh3 v3 = null;
    final int type = getItemViewType(position);
    if (convertView == null) {
      switch (type) {
        case 0:
          //部门
          convertView = View.inflate(mContext, R.layout.deplist_item, null);
          v = new vh();
          convertView.setTag(v);
          v.cb_dep = (CheckBox) convertView.findViewById(R.id.cb_check_dep);
          v.tv_name = (TextView) convertView.findViewById(R.id.tv_dep_name);
          v.tv_count = (TextView) convertView.findViewById(R.id.tv_dep_member_count);
          convertView.setTag(v);
          break;
        case 1:
          convertView = View.inflate(mContext, R.layout.roganization_item_title, null);
          ((TextView) convertView.findViewById(R.id.tv_organization_item_title))
              .setText(mData.get(position).toString());
          break;
        case 2:
          //成员
          convertView = View.inflate(mContext, R.layout.dep_member_item, null);
          v3 = new vh3();
          v3.cb_dep = (CheckBox) convertView.findViewById(R.id.cb_check_dep);
          v3.tv_depname = (TextView) convertView.findViewById(R.id.tv_dep_member_name);
          v3.tv_dep_station = (TextView) convertView.findViewById(R.id.tv_dep_member_station);
          v3.iv_depicon = (SimpleDraweeView) convertView.findViewById(R.id.dep_member_icon);
          convertView.setTag(v3);
          break;
      }
    } else {
      switch (type) {
        case 0:
          v = (vh) convertView.getTag();
          break;
        case 2:
          v3 = (vh3) convertView.getTag();
          break;
      }
    }
    //绑定数据
    switch (type) {
      case 0:
        //部门
        final DepartmentEntity dep = (DepartmentEntity) mData.get(position);
        v.tv_name.setText(dep.getDep_name() + "");
        v.tv_count.setText(dep.getNum() + "");
        //勾选框是否可见
        v.cb_dep.setVisibility(chatType.equals("0") ? View.GONE : View.VISIBLE);
        if (isChecked) {
          v.cb_dep.setChecked(true);
        } else {
          v.cb_dep.setChecked(false);
        }
        v.cb_dep.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v) {
            if (mOnOrganizationItemClickListener == null) {
              return;
            }
            if (mSelected.get(position)) {
              mSelected.put(position, false);
              setSelected(mSelected);
              mOnOrganizationItemClickListener.OnDepartmentCancle(dep.getDep_id());
            } else {
              mSelected.put(position, true);
              setSelected(mSelected);
              mOnOrganizationItemClickListener.OnDepartmentChoosed(dep.getDep_id());
            }
          }
        });
        break;
      case 2:
        //成员
        final MemberEntity memberEntity = (MemberEntity) mData.get(position);
        v3.tv_depname.setText(memberEntity.getMember_name() + "");
        v3.iv_depicon.setImageURI(memberEntity.getHead_image() + "");
        v3.tv_dep_station.setText(memberEntity.getJob_name());
        //勾选框是否可见
        v3.cb_dep.setVisibility(chatType.equals("0") ? View.GONE : View.VISIBLE);
        if (isChecked) {
          v3.cb_dep.setChecked(true);
        } else {
          v3.cb_dep.setChecked(false);
        }
        v3.cb_dep.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v) {
            if (mOnOrganizationItemClickListener == null) {
              return;
            }
            if (mSelected.get(position)) {
              mSelected.put(position, false);
              setSelected(mSelected);
              mOnOrganizationItemClickListener.OnMemberCancle(memberEntity.getUid());
            } else {
              mSelected.put(position, true);
              setSelected(mSelected);
              mOnOrganizationItemClickListener.OnMemberChoosed(memberEntity.getUid());
            }
          }
        });
        break;
    }
    convertView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (mOnOrganizationItemClickListener == null) {
          return;
        }
        if (type == 0) {
          DepartmentEntity departmentEntity = (DepartmentEntity) mData
              .get(position);
          String dep_id = departmentEntity.getDep_id();
          mOnOrganizationItemClickListener
              .OnDepartmentItemClick(dep_id, mChooseedItems, mSelected.get(position));
        } else if (type == 2) {
          if (!chatType.equals("0")) {
            return;
          }
          MemberEntity memberEntity = (MemberEntity) mData.get(position);
          //点击人员跳转聊天
          mOnOrganizationItemClickListener
              .OnMemberItemClick(memberEntity.getUid(), memberEntity.getMember_name());
        } else {
          return;
        }
      }
    });
    return convertView;
  }

  public void setData(List data, boolean isChecked) {
    mData = data;
    initCbStates(isChecked);
  }

  //初始化勾选状态
  private void initCbStates(boolean ischecked) {
    if (ischecked) {
      for (int i = 0; i < mData.size(); i++) {
        mSelected.put(i, true);
        //全部添加到数据集合里面
        mChooseedItems.add(mData.get(i));
      }
    } else {
      for (int i = 0; i < mData.size(); i++) {
        mSelected.put(i, false);
      }
    }
  }

  public interface OnOrganizationItemClickListener {

    //当点击了部门条目,将当前已勾选的数据集合,当前的部门的ID，以及当前是否勾选的状态回调出去
    void OnDepartmentItemClick(String dep_id, List chooseedItems, Boolean isChecked);

    void OnMemberItemClick(String uid, String member_name);

    void OnMemberChoosed(String uid);

    void OnMemberCancle(String uid);

    void OnDepartmentChoosed(String dep_id);

    void OnDepartmentCancle(String dep_id);


  }

  @Override
  public int getViewTypeCount() {
    return 3;
  }

  @Override
  public int getItemViewType(int position) {
    if (mData.get(position) instanceof String) {
      //显示标题栏
      return 1;
    } else if (mData.get(position) instanceof DepartmentEntity) {
      //显示部门列表
      return 0;
    } else {
      //显示成员列表
      return 2;
    }
  }


  class vh {

    public CheckBox cb_dep;
    public TextView tv_name;
    public TextView tv_count;
  }

  class vh2 {

    TextView tv_title;
  }

  class vh3 {

    public CheckBox cb_dep;
    public TextView tv_depname;
    public TextView tv_dep_station;
    public SimpleDraweeView iv_depicon;

  }
}



