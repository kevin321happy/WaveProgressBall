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
<<<<<<< HEAD

  /**
   * 下载的时候的状态
   * -1表示下载失败 0表示下载开始  1表示下载中  2表示下载完成
   */
  private int state;
  private String what;

  public String getWhat() {
    return what;
  }

  public void setWhat(String what) {
    this.what = what;
  }
=======
  private int state;
>>>>>>> f8c163e9f9b16c6f8465981156b159495b4df8c8

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
