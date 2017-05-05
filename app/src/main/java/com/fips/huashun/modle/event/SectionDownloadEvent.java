package com.fips.huashun.modle.event;
/**
 * description: 课程下载的Event
 * autour: Kevin
 * company:锦绣氘(武汉)科技有限公司
 * date: 2017/4/22 9:49 
 * update: 2017/4/22
 * version: 1.21
 * 站在峰顶 看世界
 * 落在谷底 思人生
*/
public class SectionDownloadEvent {

  private String sectionUrl;//章节的路径
  private String sectionId;//章节ID
  private String courseId;//课程的ID

  public SectionDownloadEvent(String sectionUrl, String sectionId, String courseId) {
    this.sectionUrl = sectionUrl;
    this.sectionId = sectionId;
    this.courseId = courseId;
  }

  public SectionDownloadEvent() {
  }

  public String getSectionUrl() {
    return sectionUrl;
  }

  public void setSectionUrl(String sectionUrl) {
    this.sectionUrl = sectionUrl;
  }

  public String getSectionId() {
    return sectionId;
  }

  public void setSectionId(String sectionId) {
    this.sectionId = sectionId;
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  @Override
  public String toString() {
    return "SectionDownloadEvent{" +
        "sectionUrl='" + sectionUrl + '\'' +
        ", sectionId='" + sectionId + '\'' +
        ", courseId='" + courseId + '\'' +
        '}';
  }
}
