package com.fips.huashun.modle.event;

/** 
 * description: 章节下载信息反馈
 * autour: Kevin
 * company:锦绣氘(武汉)科技有限公司
 * date: 2017/4/22 11:30 
 * update: 2017/4/22
 * version: 1.21
 * 站在峰顶 看世界
 * 落在谷底 思人生
*/
public class SectionDownloadStateEvent {
  private int state;

  public SectionDownloadStateEvent(int state) {
    this.state = state;
  }

  public SectionDownloadStateEvent() {
  }

  public int getState() {
    return state;
  }

  public void setState(int state) {
    this.state = state;
  }
}
