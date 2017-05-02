package com.fips.huashun.holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.fips.huashun.R;
import com.fips.huashun.common.ConstantsCode;
import com.fips.huashun.modle.bean.ActivityResearchModel.ResearchInfo;
import com.fips.huashun.ui.activity.WebviewActivity;
import com.shuyu.common.RecyclerBaseHolder;
import com.shuyu.common.model.RecyclerBaseModel;

/**
 * Created by kevin on 2017/3/1. 邮箱：kevin321vip@126.com 公司：锦绣氘(武汉)科技有限公司
 */
//活动调研
public class ActResearchHolder extends RecyclerBaseHolder {
  public static final int ID = R.layout.act_research_item;
  @Bind(R.id.tv_title)
  TextView mTvTitle;
  @Bind(R.id.tv_content)
  TextView mTvContent;
  @Bind(R.id.bt_submit)
  Button mBtSubmit;

  public ActResearchHolder(Context context, View v) {
    super(context, v);
  }

  @Override
  public void createView(View v) {
    ButterKnife.bind(this, v);
  }

  @Override
  public void onBind(RecyclerBaseModel model, int position) {
    final ResearchInfo rowBean = (ResearchInfo) model;
    mTvTitle.setText(rowBean.getTitle());
    mTvContent.setText(rowBean.getDescription());
    String is_anwser = rowBean.getIs_anwser();
    if (is_anwser.equals(ConstantsCode.STRING_ONE)) {
      mBtSubmit.setBackgroundResource(R.drawable.task_voted_button);
      mBtSubmit.setText("已完成");
      mBtSubmit.setEnabled(false);
    } else {
      mBtSubmit.setText("去调研");
      mBtSubmit.setBackgroundResource(R.drawable.task_novoted_button);
    }
    //跳转到H5界面
    mBtSubmit.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(context, WebviewActivity.class);
        intent.putExtra("sessoinid", rowBean.getPid());
        intent.putExtra("type","2");
        intent.putExtra("key", 16);
        context.startActivity(intent);

      }
    });
  }
}
