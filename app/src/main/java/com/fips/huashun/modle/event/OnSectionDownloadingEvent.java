package com.fips.huashun.modle.event;

/**
 * description: 正在下载的事件
 * autour: Kevin
 * company:锦绣氘(武汉)科技有限公司
 * date: 2017/5/4 15:29
 * update: 2017/5/4
 * version: 1.21
 * 站在峰顶 看世界
 * 落在谷底 思人生
 */
public class OnSectionDownloadingEvent {

  public OnSectionDownloadingEvent() {
  }

  private String what;
  private int progress;
  private long speed;

  public String getWhat() {
    return what;
  }

  public void setWhat(String what) {
    this.what = what;
  }

  public int getProgress() {
    return progress;
  }

  public void setProgress(int progress) {
    this.progress = progress;
  }

  public long getSpeed() {
    return speed;
  }

  public void setSpeed(long speed) {
    this.speed = speed;
  }
}
