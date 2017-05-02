package com.fips.huashun.modle.event;
/** 
 * description: 发布小视频的Event
 * autour: Kevin
 * company:锦绣氘(武汉)科技有限公司
 * date: 2017/4/19 10:15 
 * update: 2017/4/19
 * version: 1.21
 * 站在峰顶 看世界
 * 落在谷底 思人生
*/

public class PostVideoEvent {
  private String type;
  private String activityid;
  private String words;
  private String path;
  private String thumpath;//封面路径

  public String getThumpath() {
    return thumpath;
  }

  public void setThumpath(String thumpath) {
    this.thumpath = thumpath;
  }

  public PostVideoEvent(String type, String activityid, String words) {
    this.type = type;
    this.activityid = activityid;
    this.words = words;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public PostVideoEvent() {

  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getActivityid() {
    return activityid;
  }

  public void setActivityid(String activityid) {
    this.activityid = activityid;
  }

  public String getWords() {
    return words;
  }

  public void setWords(String words) {
    this.words = words;
  }
}
