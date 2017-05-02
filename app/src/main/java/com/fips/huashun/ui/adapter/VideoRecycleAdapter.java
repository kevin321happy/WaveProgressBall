package com.fips.huashun.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.fips.huashun.R;
import com.fips.huashun.modle.bean.VideoInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class VideoRecycleAdapter extends RecyclerView.Adapter {

  private Context mContext;
  private List<VideoInfo> mVideoInfoList;
  // 用来控制CheckBox的选中状况
  private static HashMap<Integer, Boolean> isSelected = new HashMap<>();
  private boolean HasChoose;
  private List<VideoInfo> mChooseVideos = new ArrayList<>();

  public VideoRecycleAdapter(List<VideoInfo> videoInfoList) {
    mVideoInfoList = videoInfoList;
    initCbStutus(mVideoInfoList);
  }

  //初始化选中的状态
  private void initCbStutus(List<VideoInfo> videoInfoList) {
    for (int i = 0; i < videoInfoList.size(); i++) {
      isSelected.put(i, false);
    }
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    mContext = parent.getContext();
    View view = View.inflate(mContext, R.layout.video_item, null);
    return new ViewHodler(view);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, final int position) {
    final VideoInfo videoInfo = mVideoInfoList.get(position);
    final ViewHodler viewHodler = (ViewHodler) holder;
    viewHodler.tv_time.setText(videoInfo.getTotletime() + "");
    Glide.with(mContext).load(videoInfo.getThumb() + "").into(viewHodler.iv_thumb);
    viewHodler.cb_check.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (HasChoose == true && isSelected.get(position) == false) {
          Toast.makeText(mContext, "只能选一个哦", Toast.LENGTH_SHORT).show();
          viewHodler.cb_check.setChecked(false);
          return;
        }
        if (isSelected.get(position)) {
          //当前如果已经选中设置为未选中
          isSelected.put(position, false);
          setSelected(isSelected);
          mChooseVideos.remove(videoInfo);

          HasChoose = false;
        } else {
          isSelected.put(position, true);
          setSelected(isSelected);
          mChooseVideos.add(videoInfo);
          HasChoose = true;
        }
      }
    });
  }

  @Override
  public int getItemCount() {
    return mVideoInfoList == null ? 0 : mVideoInfoList.size();
  }

  //更新选中的状态
  public void setSelected(HashMap<Integer, Boolean> selected) {
    isSelected = selected;
  }

  //获得选中状态
  public HashMap<Integer, Boolean> getIsSelected() {
    return isSelected;
  }

  public List<VideoInfo> getChooseVideos() {
    return mChooseVideos;
  }

  class ViewHodler extends ViewHolder {
    private ImageView iv_thumb;
    private TextView tv_time;
    private CheckBox cb_check;

    public ViewHodler(View itemView) {
      super(itemView);
      iv_thumb = (ImageView) itemView.findViewById(R.id.iv_thumb);
      tv_time = (TextView) itemView.findViewById(R.id.tv_time);
      cb_check = (CheckBox) itemView.findViewById(R.id.check);
    }
  }
}
