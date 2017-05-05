package com.fips.huashun.modle.event;

/** 
 * description: 点击暂停,继续的事件
 * autour: Kevin
 * company:锦绣氘(武汉)科技有限公司
 * date: 2017/5/4 19:18 
 * update: 2017/5/4
 * version: 1.21
 * 站在峰顶 看世界
 * 落在谷底 思人生
*/
public class OnPuaseDownloadEvent {
  private int state;
  private String what;
  private String sectionUrl;
  private String sectionName;

  public String getSectionUrl() {
    return sectionUrl;
  }

  public void setSectionUrl(String sectionUrl) {
    this.sectionUrl = sectionUrl;
  }

  public String getSectionName() {
    return sectionName;
  }

  public void setSectionName(String sectionName) {
    this.sectionName = sectionName;
  }

  public OnPuaseDownloadEvent() {
  }

  public int getState() {
    return state;
  }

  public void setState(int state) {
    this.state = state;
  }

  public String getWhat() {
    return what;
  }

  public void setWhat(String what) {
    this.what = what;
  }
}
