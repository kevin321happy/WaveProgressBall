package com.fips.huashun.modle.dbbean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

//缓存学习时间
@DatabaseTable
public class CacheStudyTimeEntity {
  @DatabaseField(generatedId = true)
  private long Id;//自增ID
  @DatabaseField
  private String studyTime;//学习时间
  @DatabaseField
  private String courseId;//课程Id
  @DatabaseField
  private String uid;//用户ID

  public long getId() {
    return Id;
  }

  public void setId(long id) {
    Id = id;
  }

  public String getStudyTime() {
    return studyTime;
  }

  public void setStudyTime(String studyTime) {
    this.studyTime = studyTime;
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }
  public String getUid() {
    return uid;
  }
  public void setUid(String uid) {
    this.uid = uid;
  }
  @Override
  public String toString() {
    return "CacheStudyTimeEntity{" +
        "Id='" + Id + '\'' +
        ", studyTime='" + studyTime + '\'' +
        ", courseId='" + courseId + '\'' +
        ", uid='" + uid + '\'' +
        '}';
  }
}
