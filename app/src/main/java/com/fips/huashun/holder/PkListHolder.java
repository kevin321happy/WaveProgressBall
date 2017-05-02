package com.fips.huashun.holder;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fips.huashun.R;
import com.fips.huashun.modle.bean.PkListModel.RankInfoItem;
import com.fips.huashun.modle.event.PkLikeEvent;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.widgets.HeadTagIamge;
import com.fips.huashun.widgets.HkTextView;
import com.shuyu.common.RecyclerBaseHolder;
import com.shuyu.common.model.RecyclerBaseModel;
import de.greenrobot.event.EventBus;

/**
 * Created by kevin on 2017/2/21. 活动PK榜
 */
public class PkListHolder extends RecyclerBaseHolder {

  public final static int ID = R.layout.pk_list_item;//pk榜条目布局
  private static final String TAG = "PkListHolder";
  @Bind(R.id.pk_item_rank)
  HkTextView mPkItemRank;
  @Bind(R.id.pk_item_icon)
  HeadTagIamge mPkItemIcon;
  @Bind(R.id.pk_item_name)
  HkTextView mPkItemName;
  @Bind(R.id.pk_item_department)
  HkTextView mPkItemDepartment;
  @Bind(R.id.pk_item_score)
  HkTextView mPkItemScore;
  @Bind(R.id.pk_item_like)
  HkTextView mPkItemLike;
  @Bind(R.id.iv_bg)
  LinearLayout mIvBg;
  private int likecount;//d点赞数
  private boolean isLike;//是否已经点赞
  private String useid;


  public PkListHolder(Context context, View v) {
    super(context, v);
  }

  @Override
  public void createView(View v) {
    ButterKnife.bind(this, v);
  }

  @Override
  public void onBind(RecyclerBaseModel model, int position)  {
   RankInfoItem rankInfoItem = (RankInfoItem) model;
    String name = rankInfoItem.getName();
    useid = rankInfoItem.getUserid();
    //绑定数据
    String userid = rankInfoItem.getUserid();
    if (userid.equals(PreferenceUtils.getUserId())) {
      mIvBg.setBackgroundResource(R.drawable.pk_bgme);
    } else {
      mIvBg.setBackgroundResource(R.drawable.pk_bgnormal);
    }
    isLike = rankInfoItem.islike();
    mPkItemRank.setText(position + 4 + "");
    mPkItemDepartment.setText(rankInfoItem.getDep_name());
    String pic_url = rankInfoItem.getImg_path();
    mPkItemIcon.setBorderWidth(0);
    mPkItemIcon.loadHeadIamge(getContext(), pic_url);
    String lable = rankInfoItem.getTag_name();
    if (lable != null) {
      mPkItemIcon.setLableVisible(true);
      mPkItemIcon.setLableSize(14);
      mPkItemIcon.setLableText(lable);
    } else {
      mPkItemIcon.setLableVisible(false);
    }
    mPkItemName.setText(name);
    mPkItemLike.setText(rankInfoItem.getPraise());
    likecount=Integer.parseInt(rankInfoItem.getPraise()==null?"0":rankInfoItem.getPraise());
    String score = rankInfoItem.getScore();
    if (score.length() > 3) {
      score = score.substring(0, 3);
    }
    mPkItemScore.setText(score);
   // mPkItemLike.setText(rankInfoItem.getPraise()==null?"0":rankInfoItem.getPraise());
  //  likecount = Integer.parseInt(rankInfoItem.getPraise());
  }
  //点击了点赞
  @OnClick(R.id.pk_item_like)
  public void onClick() {
    if (isLike) {
      return;
    }
    mPkItemLike.setBackgroundResource(R.drawable.pk_dianzanhou);
    likecount++;
    mPkItemLike.setText(likecount + "");
    //发送EventBus
    PkLikeEvent pkLikeEvent = new PkLikeEvent(useid);
    EventBus.getDefault().post(pkLikeEvent);
    isLike = true;
    mPkItemLike.setEnabled(false);
  }
}

