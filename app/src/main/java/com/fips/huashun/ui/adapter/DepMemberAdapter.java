package com.fips.huashun.ui.adapter;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.fips.huashun.R;

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
public class DepMemberAdapter extends Adapter {

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = View.inflate(parent.getContext(), R.layout.dep_member_item, null);

    return null;
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    DepMemberViewHolder viewHolder = (DepMemberViewHolder) holder;

  }

  @Override
  public int getItemCount() {
    return 0;
  }

  //部门成员的ViewHolder
  class DepMemberViewHolder extends ViewHolder {

    private CheckBox dep_checkbox;
    private TextView tv_depname;
    private TextView tv_station;
    private SimpleDraweeView iv_icon;

    public DepMemberViewHolder(View itemView) {
      super(itemView);
      dep_checkbox = (CheckBox) itemView.findViewById(R.id.cb_check_dep);
      tv_depname = (TextView) itemView.findViewById(R.id.tv_dep_member_name);
      tv_station = (TextView) itemView.findViewById(R.id.tv_dep_member_station);
      iv_icon = (SimpleDraweeView) itemView.findViewById(R.id.dep_member_icon);
    }
  }
}
