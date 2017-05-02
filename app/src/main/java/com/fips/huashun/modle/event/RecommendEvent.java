package com.fips.huashun.modle.event;

/**
 * Created by kevin on 2017/3/10. 邮箱：kevin321vip@126.com 公司：锦绣氘(武汉科技有限公司)
 * d当点击推荐课程的时候
 */

public class RecommendEvent {
  String courseid;

  public RecommendEvent() {
  }

  public RecommendEvent(String courseId) {
  }

  public String getCourseid() {
    return courseid;
  }

  public void setCourseid(String courseid) {
    this.courseid = courseid;
  }
}
