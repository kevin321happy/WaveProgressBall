package com.fips.huashun.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.fips.huashun.R;
import com.fips.huashun.modle.dbbean.MemberEntity;
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

  private List<MemberEntity> mData;
  private HashMap<Integer, Boolean> mSelected = new HashMap<>();
  private List<MemberEntity> mDepMembers = new ArrayList<>();
  private onMemberSelectedListener mOnMemberSelectedListener;
  private Context mContext;

  public void setOnMemberSelectedListener(
      onMemberSelectedListener onMemberSelectedListener) {
    mOnMemberSelectedListener = onMemberSelectedListener;
  }

  public DepMemberAdapter(Context context) {
    mContext = context;
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
    MemberEntity depMember = mData.get(position);
    Log.i("test0",depMember.toString());
    ViewHolder holder;
    if (convertView == null) {
      convertView = View.inflate(mContext, R.layout.dep_member_item, null);
      holder = new ViewHolder();
      holder.cb_dep = (CheckBox) convertView.findViewById(R.id.cb_check_dep);
      holder.tv_depname = (TextView) convertView.findViewById(R.id.tv_dep_member_name);
      holder.tv_dep_station = (TextView) convertView.findViewById(R.id.tv_dep_member_station);
      holder.iv_depicon = (SimpleDraweeView) convertView.findViewById(R.id.dep_member_icon);
    convertView.setTag(holder);
    }else {
      holder = (ViewHolder) convertView.getTag();
    }
    //绑定数据
    holder.tv_depname.setText(depMember.getMember_name() + "");
    holder.tv_dep_station.setText(depMember.getJob_name() + "");
    holder.iv_depicon.setImageURI(depMember.getHead_image() + "");
    //勾选的点击的监听
    holder.cb_dep.setOnClickListener(new OnClickListener() {
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
        }
      }
    });
    return convertView;
  }

  public void setSelected(HashMap<Integer, Boolean> selected) {
    mSelected = selected;
  }

  public HashMap<Integer, Boolean> getIsSelected() {
    return mSelected;
  }

  public void setData(List<MemberEntity> data) {
    mData = data;
    initCbStates(mData);
  }

  private void initCbStates(List<MemberEntity> data) {
    for (int i = 0; i < data.size(); i++) {
      mSelected.put(i, false);
    }
  }

  public interface onMemberSelectedListener {

    void onMembersSelected(List<MemberEntity> list);
  }

  private class ViewHolder {
    private CheckBox cb_dep;
    private TextView tv_depname;
    private TextView tv_dep_station;
    private SimpleDraweeView iv_depicon;
  }
}
