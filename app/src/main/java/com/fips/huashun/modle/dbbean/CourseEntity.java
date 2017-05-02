package com.fips.huashun.modle.dbbean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
/** 
 * description: 课程表,用来存储课程名
 * autour: Kevin
 * company:锦绣氘(武汉)科技有限公司
 * date: 2017/4/26 13:35
 * update: 2017/4/26
 * version: 1.21
 * 站在峰顶 看世界
 * 落在谷底 思人生
*/

@DatabaseTable
public class CourseEntity {
  @DatabaseField(generatedId = true)
  private long Id;
  @DatabaseField(unique = true)
  private String courseid;
  @DatabaseField
  private String coursename;
  public CourseEntity() {
  }

  public CourseEntity(String courseid, String coursename) {
    this.courseid = courseid;
    this.coursename = coursename;
  }

  @Override
  public String toString() {
    return "CourseEntity{" +
        "Id=" + Id +
        ", courseid='" + courseid + '\'' +
        ", coursename='" + coursename + '\'' +
        '}';
  }

  public long getId() {
    return Id;
  }

  public void setId(long id) {
    Id = id;
  }

  public String getCourseid() {
    return courseid;
  }

  public void setCourseid(String courseid) {
    this.courseid = courseid;
  }

  public String getCoursename() {
    return coursename;
  }

  public void setCoursename(String coursename) {
    this.coursename = coursename;
  }
}
