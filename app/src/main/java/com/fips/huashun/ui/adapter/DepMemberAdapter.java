package com.fips.huashun.ui.adapter;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.fips.huashun.R;
import com.fips.huashun.modle.testbean.DepMember;
import com.fips.huashun.ui.utils.CommonViewHolder;
import com.fips.huashun.ui.utils.ToastUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * description: 企业部门成员的Adapter
 * autour: Kevin
 * company:锦绣氘(武汉)科技有限公司
 * date: 2017/4/26 10:24
 * update: 2017/4/26
 * version: 1.21
 * 站在峰顶 看世界
 * 落在谷底 思人生
 */
public class DepMemberAdapter extends BaseAdapter {


  private List<DepMember> mData;
  private HashMap<Integer, Boolean> mSelected = new HashMap<>();
  private List<DepMember> mDepMembers = new ArrayList<>();
  private onMemberSelectedListener mOnMemberSelectedListener;

  public void setOnMemberSelectedListener(
      onMemberSelectedListener onMemberSelectedListener) {
    mOnMemberSelectedListener = onMemberSelectedListener;
  }

  @Override
  public int getCount() {
    return mData == null ? 0 : mData.size();
  }

  @Override
  public Object getItem(int position) {
    return mData == null ? null : mData.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(final int position, View convertView, ViewGroup parent) {
    DepMember depMember = mData.get(position);
    View view = View.inflate(parent.getContext(), R.layout.dep_member_item, null);
    CommonViewHolder cvh = CommonViewHolder
        .createCVH(convertView, R.layout.dep_member_item, parent);
    CheckBox cb_pde = (CheckBox) cvh.getView(R.id.cb_check_dep);
    TextView tv_depname = (TextView) cvh.getView(R.id.tv_dep_member_name);
    TextView tv_dep_station = (TextView) cvh.getView(R.id.tv_dep_member_station);
    SimpleDraweeView iv_depicon = (SimpleDraweeView) cvh.getView(R.id.dep_member_icon);
    //绑定数据
    tv_depname.setText(depMember.getDepname());
    tv_dep_station.setText(depMember.getStation() + "");
    iv_depicon.setImageURI(depMember.getIcon_path());
    //勾选的点击的监听
    cb_pde.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (mOnMemberSelectedListener == null) {
          return;
        }
        if (mSelected.get(position)) {
          mSelected.put(position, false);
          setSelected(mSelected);
          mDepMembers.remove(mData.get(position));
          mOnMemberSelectedListener.onMembersSelected(mDepMembers);
        } else {
          mSelected.put(position, true);
          setSelected(mSelected);
          mDepMembers.add(mData.get(position));
          ToastUtil.getInstant().show("我添加了部门的人员 ：" + mData.get(position).toString());
          mOnMemberSelectedListener.onMembersSelected(mDepMembers);
//          ToastUtil.getInstant().show("我选中的部门人员的总数为 ："+mDepMembers.size());
        }
//        if (mOnMemberSelectedListener != null) {
//          mOnMemberSelectedListener.onMembersSelected(mDepMembers);
//        }
      }
    });
    return cvh.convertView;
  }

  public void setSelected(HashMap<Integer, Boolean> selected) {
    mSelected = selected;
  }

  public HashMap<Integer, Boolean> getIsSelected() {
    return mSelected;
  }

  public void setData(List<DepMember> data) {
    mData = data;
    initCbStates(mData);
  }

  private void initCbStates(List<DepMember> data) {
    for (int i = 0; i < data.size(); i++) {
      mSelected.put(i, false);
//      ToastUtil.getInstant().show("初始化了 ："+mDepMembers.size());
    }
  }

  public interface onMemberSelectedListener {

    void onMembersSelected(List<DepMember> list);
  }
}
