package com.fips.huashun.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.fips.huashun.R;
import com.fips.huashun.modle.bean.ReplyItemModel;
import com.fips.huashun.ui.utils.CommonViewHolder;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.widgets.HeadTagIamge;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.List;

/**
 * Created by kevin on 2017/2/15. 回复的列表
 */

public class TopReplyAdapter extends BaseAdapter {

  private Context mContext;
  private List<ReplyItemModel> mReplyItemModels;
  private ImageLoader mImageLoader;
  private OnApplyItemClickListener mOnApplyItemClickListener;

  public void setOnApplyItemClickListener(OnApplyItemClickListener onApplyItemClickListener) {
    mOnApplyItemClickListener = onApplyItemClickListener;
  }

  public void setData(List<ReplyItemModel> list) {
    this.mReplyItemModels = list;
  }

  public TopReplyAdapter(Context context) {
    this.mContext = context;
    mImageLoader = ImageLoader.getInstance();
  }

  @Override
  public int getCount() {
    return mReplyItemModels == null ? 0 : mReplyItemModels.size();
  }

  @Override
  public Object getItem(int position) {
    return mReplyItemModels == null ? null : mReplyItemModels.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ReplyItemModel mItemModel = mReplyItemModels.get(position);
    CommonViewHolder cvh = CommonViewHolder
        .createCVH(convertView, R.layout.reply_list_item, parent);
    final TextView putname = cvh.getTv(R.id.tv_applyputname);
    TextView content = (TextView) cvh.getView(R.id.tv_applycontent);
    final TextView getname = (TextView) cvh.getView(R.id.tv_applygetname);
    TextView delete = cvh.getTv(R.id.tv_delete);
    TextView time = (TextView) cvh.getView(R.id.tv_applytime);
    TextView huifu = cvh.getTv(R.id.tv_huifu);
    HeadTagIamge head = (HeadTagIamge) cvh.getView(R.id.iv_applyhead);
    final String topid = mItemModel.getTopid();
    final String getid = mItemModel.getGetid();
    final String putid = mItemModel.getPutid();
    final String space_wordsid = mItemModel.getSpace_wordsid();
    final String applyputname = mItemModel.getPutname();
    final String applygetname = mItemModel.getGetname();
    final String pid = mItemModel.getPid();

    //绑定数据
    // mImageLoader.displayImage(mItemModel.getHeadurl(), head);
    head.setBorderWidth(0);
    head.loadHeadIamge(mContext, mItemModel.getHeadurl());
    //显示标签
    if (mItemModel.getLable() != null) {
    head.setLableVisible(true);
    head.setLableSize(25);
   // head.setLableText("废寝忘食");
     head.setLableText(mItemModel.getLable());
    } else {
      head.setLableVisible(false);
    }

    time.setText(mItemModel.getAddtime().substring(5, mItemModel.getAddtime().length()));
    getname.setText(applygetname);
    delete.setVisibility(
        mItemModel.getGetid().equals(PreferenceUtils.getUserId()) ? View.VISIBLE : View.GONE);
    content.setText(mItemModel.getWords() + "");
    if (null == applyputname || mItemModel.getTopid().equals("0")) {//二级回复
      putname.setVisibility(View.GONE);
      huifu.setVisibility(View.GONE);
    } else {//一级回复
      putname.setText(applyputname);
      huifu.setVisibility(View.VISIBLE);
      putname.setVisibility(View.VISIBLE);
      putname.setOnClickListener(new View.OnClickListener() {//点击姓名回复
        @Override
        public void onClick(View v) {
          if (mOnApplyItemClickListener != null) {
            ReplyItemModel replyItemModel = new ReplyItemModel();
            replyItemModel.setSpace_wordsid(space_wordsid);
            replyItemModel.setTopid(topid);
            replyItemModel.setGetid(getid);
            replyItemModel.setGetname(applygetname);
            replyItemModel.setPutname(applyputname);
            replyItemModel.setPutid(putid);
            replyItemModel.setPid(pid);
            mOnApplyItemClickListener.OnDiscussantClick(replyItemModel);
          }
        }
      });
    }
    //点击删除
    delete.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (mOnApplyItemClickListener != null) {
          mOnApplyItemClickListener.OnDeleteClick(pid);
        }
      }
    });
    //当条目被点击时
    cvh.convertView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (mOnApplyItemClickListener != null) {
          ReplyItemModel replyItemModel = new ReplyItemModel();
          replyItemModel.setSpace_wordsid(space_wordsid);
          replyItemModel.setTopid(topid);
          replyItemModel.setGetid(getid);
          replyItemModel.setGetname(applygetname);
          replyItemModel.setPutname(applyputname);
          replyItemModel.setPutid(putid);
          replyItemModel.setPid(pid);
          mOnApplyItemClickListener.OnApplyItemClick(replyItemModel);
        }
      }
    });
    return cvh.convertView;
  }

  public interface OnApplyItemClickListener {

    //当留言列表条目被点击
    void OnApplyItemClick(ReplyItemModel model);

    void OnDeleteClick(String pid);

    void OnDiscussantClick(ReplyItemModel model);//当评论人被点击
  }
}
