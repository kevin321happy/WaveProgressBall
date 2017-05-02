package com.fips.huashun.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import com.fips.huashun.R;

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
public class DepListAdapter extends RecyclerView.Adapter {

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = View.inflate(parent.getContext(), R.layout.deplist_item, null);
    DepListViewHolder viewHolder = new DepListViewHolder(view);
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    DepListViewHolder viewHolder = (DepListViewHolder) holder;
    //绑定数据

  }

  @Override
  public int getItemCount() {
    return 0;
  }

  class DepListViewHolder extends ViewHolder {

    private CheckBox mCheckBox;
    private TextView mDepName;
    private TextView mDepMemberNum;

    public DepListViewHolder(View itemView) {
      super(itemView);
      mCheckBox = (CheckBox) itemView.findViewById(R.id.cb_check_dep);
      mDepName = (TextView) itemView.findViewById(R.id.tv_dep_name);
      mDepMemberNum = (TextView) itemView.findViewById(R.id.tv_dep_member_count);
    }
  }
}
