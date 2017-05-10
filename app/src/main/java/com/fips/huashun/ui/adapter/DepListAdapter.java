package com.fips.huashun.ui.adapter;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import com.fips.huashun.R;
import com.fips.huashun.modle.dbbean.DepartmentEntity;
import com.fips.huashun.ui.utils.CommonViewHolder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * description: 部门列表的Adapter
 * autour: Kevin
 * company:锦绣氘(武汉)科技有限公司
 * date: 2017/4/26 10:27
 * update: 2017/4/26
 * version: 1.21
 * 站在峰顶 看世界
 * 落在谷底 思人生
 */
public class DepListAdapter extends BaseAdapter {

  private List<DepartmentEntity> mData;
  private HashMap<Integer, Boolean> mSelected = new HashMap<>();
  private List<DepartmentEntity> mDepLists = new ArrayList<>();
  private onDepartmentSelectedListener mOnDepartmentSelectedListener;






  public void setOnDepartmentSelectedListener(
      onDepartmentSelectedListener onDepartmentSelectedListener) {
    mOnDepartmentSelectedListener = onDepartmentSelectedListener;
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
    final DepartmentEntity departmentEntity = mData.get(position);
    CommonViewHolder cvh = CommonViewHolder.createCVH(convertView, R.layout.deplist_item, parent);
    CheckBox cb_dep = (CheckBox) cvh.getView(R.id.cb_check_dep);
    TextView tv_name = (TextView) cvh.getView(R.id.tv_dep_name);
    TextView tv_count = (TextView) cvh.getView(R.id.tv_dep_member_count);
    //绑定数据
    tv_name.setText(departmentEntity.getDep_name());
    tv_count.setText(departmentEntity.getNum() + "");
    //checkbox的选中的监听
    cb_dep.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (mSelected.get(position)) {
          //如果当前已经选中,设置为未选中
          mSelected.put(position, false);
          setSelected(mSelected);
          //并从选中的集合里面移除
          mDepLists.remove(mData.get(position));
        } else {
          //如果之前是未选中,则设置为选中
          mSelected.put(position, true);
          setSelected(mSelected);
          //添加到选中的集合里面
          mDepLists.add(mData.get(position));
        }
        //回调接口
        if (mOnDepartmentSelectedListener != null) {
          mOnDepartmentSelectedListener.onDepSelected(mDepLists);
        }
      }
    });
    cvh.convertView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (mOnDepartmentSelectedListener != null) {
          mOnDepartmentSelectedListener.OnDepItemClick(departmentEntity.getDep_id());
        }
      }
    });
    return cvh.convertView;
  }


  public void setData(List<DepartmentEntity> data) {
    mData = data;
    initCbStates(mData);
  }

  public void setSelected(HashMap<Integer, Boolean> selected) {
    mSelected = selected;
  }

  public HashMap<Integer, Boolean> getIsSelected() {
    return mSelected;
  }

  //初始化checkbox的选中状态
  private void initCbStates(List<DepartmentEntity> data) {
    for (int i = 0; i < data.size(); i++) {
      mSelected.put(i, false);
    }
  }

  //部门选中的监听
  public interface onDepartmentSelectedListener {

    void onDepSelected(List<DepartmentEntity> lists);
    void OnDepItemClick(String dep_id);

  }
}



