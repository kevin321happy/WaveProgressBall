package com.fips.huashun.modle.dbbean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * description: 课程章节信息的实体类 autour: Kevin company:锦绣氘(武汉)科技有限公司 date: 2017/4/21 18:36 update:
 * 2017/4/21 version: 1.21 站在峰顶 看世界 落在谷底 思人生
 */
@DatabaseTable
public class CourseSectionEntity {

  @DatabaseField(generatedId = true)//自增主键
  private long id;
  @DatabaseField(unique = true)//章节ID,唯一字段
  private String sectionId;//章节ID
  @DatabaseField
  private String sectionname;//章节名字
  @DatabaseField
  private String sectionUrl;//文件Url
  @DatabaseField
  private int state;//下载状态
  @DatabaseField
  private String localpath;//文件的本地路径
  @DatabaseField
  private String courseid;//课程的Id
  @DatabaseField
  private String chapterId;//章节ID
  @DatabaseField
  private String chapterName;//章节名
  @DatabaseField
  private String fileType;//文件类型
  @DatabaseField
  private String fileSize;//文件大小
  @DatabaseField
  private String progress;//下载进度

  public String getProgress() {
    return progress;
  }
  public void setProgress(String progress) {
    this.progress = progress;
  }

  public String getChapterId() {
    return chapterId;
  }
  public void setChapterId(String chapterId) {
    this.chapterId = chapterId;
  }

  public String getChapterName() {
    return chapterName;
  }

  public void setChapterName(String chapterName) {
    this.chapterName = chapterName;
  }

  public String getFileType() {
    return fileType;
  }
  public void setFileType(String fileType) {
    this.fileType = fileType;
  }

  public String getFileSize() {
    return fileSize;
  }

  public void setFileSize(String fileSize) {
    this.fileSize = fileSize;
  }

  public String getSectionname() {
    return sectionname;
  }

  public void setSectionname(String sectionname) {
    this.sectionname = sectionname;
  }

  public String getCourseid() {
    return courseid;
  }
  public void setCourseid(String courseid) {
    this.courseid = courseid;
  }

  public CourseSectionEntity() {

  }

  public String getCourseId() {
    return courseid;
  }

  public void setCourseId(String courseId) {
    this.courseid = courseId;
  }

  public CourseSectionEntity(int id, String sectionId, String sectionUrl, int state,
      String localpath) {
    this.id = id;
    this.sectionId = sectionId;
    this.sectionUrl = sectionUrl;
    this.state = state;
    this.localpath = localpath;
  }

  public long getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getSectionId() {
    return sectionId;
  }

  public void setSectionId(String sectionId) {
    this.sectionId = sectionId;
  }

  public String getSectionUrl() {
    return sectionUrl;
  }

  public void setSectionUrl(String sectionUrl) {
    this.sectionUrl = sectionUrl;
  }

  public int getState() {
    return state;
  }

  public void setState(int state) {
    this.state = state;
  }

  public String getLocalpath() {
    return localpath;
  }

  public void setLocalpath(String localpath) {
    this.localpath = localpath;
  }
  @Override
  public String toString() {
    return "CourseSectionEntity{" +
        "id=" + id +
        ", sectionId='" + sectionId + '\'' +
        ", sectionname='" + sectionname + '\'' +
        ", sectionUrl='" + sectionUrl + '\'' +
        ", state=" + state +
        ", localpath='" + localpath + '\'' +
        ", courseid='" + courseid + '\'' +
        ", chapterId='" + chapterId + '\'' +
        ", chapterName='" + chapterName + '\'' +
        ", fileType='" + fileType + '\'' +
        ", fileSize='" + fileSize + '\'' +
        '}';
  }
}
