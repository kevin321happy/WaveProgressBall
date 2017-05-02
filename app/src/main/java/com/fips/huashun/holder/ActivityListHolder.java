package com.fips.huashun.holder;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.fips.huashun.R;
import com.fips.huashun.modle.bean.ActivityListModel.ActivityItemInfo;
import com.fips.huashun.ui.utils.DisplayUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shuyu.common.RecyclerBaseHolder;
import com.shuyu.common.model.RecyclerBaseModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on 2017/2/4.
 */
public class ActivityListHolder extends RecyclerBaseHolder {

  public static final int ID = R.layout.enterprise_activity_list_item;
  @Bind(R.id.ent_activity_iv)
  ImageView mEntActivityIv;
  @Bind(R.id.ent_activity_title)
  TextView mEntActivityTitle;
  @Bind(R.id.ent_activity_address)
  TextView mEntActivityAddress;
  @Bind(R.id.ent_activity_starttime)
  TextView mEntActivityStartTime;
  @Bind(R.id.ent_activity_endtime)
  TextView mEntActivityEndTime;
  @Bind(R.id.tv_baoming)
  TextView mTvBaoming;
  @Bind(R.id.tv_qiandao)
  TextView mTvQiandao;
  @Bind(R.id.tv_diaoyan)
  TextView mTvDiaoyan;
  @Bind(R.id.tv_kaohe)
  TextView mTvKaohe;
//  @Bind(R.id.tv_pk)
//  TextView mTvPk;
  @Bind(R.id.tv_kecheng)
  TextView mTvKecheng;
  @Bind(R.id.tv_liuyan)
  TextView mTvLiuyan;
  @Bind(R.id.tv_fankui)
  TextView mTvFankui;
  @Bind(R.id.ent_activity_tabs)
  LinearLayout mEntActivityTabs;
  private Context mContext;
  private boolean hasPk;
  //活动条目的集合
  private List<ActivityItemInfo> mList = new ArrayList<>();

  public ActivityListHolder(Context context, View v) {
    super(context, v);
  }

  @Override
  public void createView(View v) {
    ButterKnife.bind(this, v);
  }

  //绑定数据
  @Override
  public void onBind(RecyclerBaseModel model, int position) {
    ActivityItemInfo activityItemInfo = (ActivityItemInfo) model;
    ImageLoader.getInstance().displayImage(activityItemInfo.getFilepath(), mEntActivityIv);
    mEntActivityTitle.setText(activityItemInfo.getActivitytitle()==null?"":activityItemInfo.getActivitytitle() + "");
    mEntActivityAddress.setText(activityItemInfo.getActivityaddre()==null?"":activityItemInfo.getActivityaddre()+"");
    mEntActivityStartTime.setText(activityItemInfo.getActivitytime()==null?"":formatTimeString(activityItemInfo.getActivitytime()) + "");
    mEntActivityEndTime.setText(activityItemInfo.getActivityendtime()==null?"":" - " + formatTimeString(activityItemInfo.getActivityendtime()));
    mTvDiaoyan.setVisibility(activityItemInfo.getIssurvey().equals("0") ? View.GONE : View.VISIBLE);
    mTvLiuyan.setVisibility(activityItemInfo.getIssetwords().equals("0") ? View.GONE : View.VISIBLE);
    mTvFankui.setVisibility(activityItemInfo.getIssettickling().equals("0") ? View.GONE : View.VISIBLE);
    mTvKecheng.setVisibility(activityItemInfo.getIsonline().equals("0") ? View.GONE : View.VISIBLE);
    mTvKaohe.setVisibility(activityItemInfo.getIspaper().equals("0") ? View.GONE : View.VISIBLE);
    mTvQiandao.setVisibility(activityItemInfo.getIssign().equals("0") ? View.GONE : View.VISIBLE);
    mTvBaoming.setVisibility(activityItemInfo.getIssignup().equals("0") ? View.GONE : View.VISIBLE);
  }

  //格式化时间
  private String formatTimeString(String activitytime) {
    //Log.i("test",)
    Log.i("test", activitytime);
    String substring = activitytime.substring(5);
    StringBuffer stringBuffer = new StringBuffer(substring);
    stringBuffer.replace(3, 4, "月");
    return stringBuffer.insert(5, "日").toString();
  }

  //不需要可以不写
  @Override
  public AnimatorSet getAnimator(View view) {
    AnimatorSet animatorSet = new AnimatorSet();
    Animator animator = ObjectAnimator
        .ofFloat(view, "translationY", DisplayUtil.dp2px(context, 80), 0);
    animator.setDuration(500);
    animator.setInterpolator(new OvershootInterpolator(.5f));

    Animator animatorSx = ObjectAnimator.ofFloat(view, "scaleX", 0.5f, 1f);
    animator.setDuration(500);
    animator.setInterpolator(new OvershootInterpolator(.5f));

    Animator animatorSy = ObjectAnimator.ofFloat(view, "scaleY", 0.5f, 1f);
    animator.setDuration(500);
    animator.setInterpolator(new OvershootInterpolator(.5f));

    animatorSet.playTogether(animator, animatorSx, animatorSy);
    return animatorSet;
  }
}
