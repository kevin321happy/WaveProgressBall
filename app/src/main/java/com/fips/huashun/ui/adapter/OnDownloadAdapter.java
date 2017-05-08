package com.fips.huashun.ui.adapter;

import static com.fips.huashun.R.id.tv_section_name;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fips.huashun.R;
import com.fips.huashun.db.dao.SectionDownloadDao;
import com.fips.huashun.modle.dbbean.CourseSectionEntity;
import com.fips.huashun.modle.event.OnPuaseDownloadEvent;
import com.fips.huashun.ui.utils.ToastUtil;
import com.fips.huashun.ui.utils.Utils;
import com.fips.huashun.widgets.AnimateHorizontalProgressBar;
import de.greenrobot.event.EventBus;
import java.util.List;

/**
 * description: 课程正在下载的适配器
 * autour: Kevin
 * company:锦绣氘(武汉)科技有限公司
 * date: 2017/5/4 14:29
 * update: 2017/5/4
 * version: 1.21
 * 站在峰顶 看世界
 * 落在谷底 思人生
 */
public class OnDownloadAdapter extends BaseAdapter {

  EventBus eventBus = EventBus.getDefault();
  private List<CourseSectionEntity> mData;
  private Context mContext;
  private SectionDownloadDao mSectionDownloadDao;
  private OnSectionDownloadListener mOnSectionDownloadListener;

  public void setOnSectionDownloadListener(
      OnSectionDownloadListener onSectionDownloadListener) {
    mOnSectionDownloadListener = onSectionDownloadListener;
  }

  public OnDownloadAdapter(Context context) {
    mContext = context;
  }

  @Override
  public int getCount() {
    return mData == null ? 0 : mData.size();
  }

  @Override
  public Object getItem(int position) {
    return mData.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, final ViewGroup parent) {
    final CourseSectionEntity sectionEntity = mData.get(position);
    final ViewHolder holder;
    if (convertView == null) {
      holder = new ViewHolder();
      convertView = LayoutInflater.from(parent.getContext()).inflate(
          R.layout.on_download_item, null);
      holder.container = (RelativeLayout) convertView.findViewById(R.id.container);
      holder.iv_section = (TextView) convertView.findViewById(R.id.iv_section_image);
      holder.tv_section_name = (TextView) convertView.findViewById(tv_section_name);
      holder.tv_section_totlesize = (TextView) convertView.findViewById(R.id.tv_totlesize);
      holder.tv_section_speed = (TextView) convertView.findViewById(R.id.tv_speed);
      holder.iv_download = (ImageView) convertView.findViewById(R.id.iv_download);
      holder.iv_delete = (ImageView) convertView.findViewById(R.id.iv_delete);
      holder.pb_progress = (AnimateHorizontalProgressBar) convertView
          .findViewById(R.id.pb_progress);
      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }
    //绑定数据
    holder.tv_section_name.setText(sectionEntity.getSectionname() + "");//章节名字
    holder.tv_section_totlesize
        .setText(Utils.FormentFileSize(Long.parseLong(sectionEntity.getFileSize())));//章节大小
    String fileType = sectionEntity.getFileType();
    final int state = sectionEntity.getState();
    ChangeDownloadUi(state, holder.iv_download);
    if (fileType.equals("2")) {
      //视频
      holder.iv_section.setText("视频");
    } else {
      //PDF
      holder.iv_section.setText("PDF");
    }
    //设置进度
    if (sectionEntity.getProgress() != null) {
      String progress = sectionEntity.getProgress();
      String last = progress.substring(progress.length() - 1);
      if (last.equals("%")) {
        progress = progress.substring(0, progress.length() - 1);
      }
      holder.pb_progress.setProgress(Integer.valueOf(progress));
    }
    //暂停点击的监听
    holder.iv_download.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (state == 2) {
          ToastUtil.getInstant().show("已下载成功");
          return;
        }
        OnPuaseDownloadEvent puaseDownloadEvent = new OnPuaseDownloadEvent();
        puaseDownloadEvent.setWhat(sectionEntity.getSectionId());
        puaseDownloadEvent.setSectionUrl(sectionEntity.getSectionUrl());
        puaseDownloadEvent.setSectionName(sectionEntity.getSectionname());
        eventBus.post(puaseDownloadEvent);
      }
    });
    //点击删除的监听
    holder.iv_delete.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (mOnSectionDownloadListener != null) {
          mOnSectionDownloadListener
              .deleteOnDownloadSection(sectionEntity.getLocalpath(), sectionEntity.getSectionId());
        }
      }
    });
    return convertView;
  }

  //根据状态改变下载按钮的UI
  private void ChangeDownloadUi(int state, ImageView iv_download) {
    switch (state) {
      case -1:
        //失败
        iv_download.setImageResource(R.drawable.again_d);
        break;
      case 0:
        iv_download.setImageResource(R.drawable.can_d);
        break;
      case 1:
        iv_download.setImageResource(R.drawable.stop_d);
        break;
      case 2:
        iv_download.setImageResource(R.drawable.finish_d);
      case 3:
        iv_download.setImageResource(R.drawable.can_d);
        break;
    }
  }


  public void setData(List<CourseSectionEntity> data) {
    mData = data;
    if (mSectionDownloadDao == null) {
      mSectionDownloadDao = new SectionDownloadDao(mContext);
    }
  }

  //局部数据
  public void updateView(View view, int itemIndex) {
    if (view == null) {
      return;
    }
    //从View中取出hodler
    ViewHolder hodler = (ViewHolder) view.getTag();
    hodler.container = (RelativeLayout) view.findViewById(R.id.container);
    hodler.iv_section = (TextView) view.findViewById(R.id.iv_section_image);
    hodler.tv_section_name = (TextView) view.findViewById(tv_section_name);
    hodler.tv_section_totlesize = (TextView) view.findViewById(R.id.tv_totlesize);
    hodler.tv_section_speed = (TextView) view.findViewById(R.id.tv_speed);
    hodler.iv_delete = (ImageView) view.findViewById(R.id.iv_delete);
    hodler.pb_progress = (AnimateHorizontalProgressBar) view
        .findViewById(R.id.pb_progress);
    //设置条目的数据
    setItemData(hodler, itemIndex);
  }

  //刷新条目的数据
  private void setItemData(final ViewHolder hodler, int itemIndex) {
    CourseSectionEntity sectionEntity = mData.get(itemIndex);
    final CourseSectionEntity entity = mSectionDownloadDao
        .querySectionBySectionId(sectionEntity.getSectionId());
    //绑定数据
    hodler.tv_section_name.setText(entity.getSectionname() + "");//章节名字
    Integer progress = Integer.valueOf(entity.getProgress());
    long totlesize = Long.parseLong(entity.getFileSize());//总长度
    long courrentsize = totlesize * progress / 100;
    if (progress == 100) {
      if (mOnSectionDownloadListener != null) {
        mOnSectionDownloadListener.onFinishDownloadSection();
      }
    } else {
      hodler.tv_section_totlesize
          .setText(
              Utils.FormentFileSize(courrentsize) + "/" + Utils.FormentFileSize(totlesize));//章节大小
      ChangeDownloadUi(entity.getState(), hodler.iv_download);
    }
    //设置进度
    hodler.pb_progress.setProgress(progress);
    //显示当前的百分比
    hodler.tv_section_speed.setText(progress + "%");
  }


  public interface OnSectionDownloadListener {

    //删除正在下载的课程
    void deleteOnDownloadSection(String localpath, String sectionId);

    //章节下载完成
    void onFinishDownloadSection();
  }

  private class ViewHolder {

    public ViewHolder() {
    }

    private RelativeLayout container;
    private TextView iv_section;
    private TextView tv_section_name;
    private TextView tv_section_totlesize;
    private TextView tv_section_speed;
    private ImageView iv_download;
    private ImageView iv_delete;
    private AnimateHorizontalProgressBar pb_progress;
  }
}
