package com.fips.huashun.ui.adapter;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
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
public class DepMemberAdapter extends Adapter{

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view=View.inflate(parent.getContext(), R.layout.dep_member_item,null);
    return null;
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {

  }

  @Override
  public int getItemCount() {
    return 0;
  }
}
