package com.fips.huashun.modle.event;

/**
 * Created by kevin on 2017/3/4. 邮箱：kevin321vip@126.com 公司：锦绣氘(武汉科技有限公司)
 * PK点赞
 */

public class PkLikeEvent {
  String uid;
  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public PkLikeEvent() {
  }

  public PkLikeEvent(String uid) {
    this.uid = uid;
  }
}
