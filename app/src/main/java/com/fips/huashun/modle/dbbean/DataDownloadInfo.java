package com.fips.huashun.modle.dbbean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by kevin on 2017/2/23. 活动资料下载信息封装信息
 */
//创建在线课程的表单
@DatabaseTable(tableName = "tb_onlinecourse")
public class DataDownloadInfo {
  @DatabaseField(generatedId=true)//自增主键
  private int id;

  @DatabaseField//活动的id
  private String activityid;

  @DatabaseField(unique = true)
  private String pid;//唯一的字段

  @DatabaseField//下载路径
  private String dowloadUrl;

  @DatabaseField//已下载的文件长度
  private long downloadsize;

  @DatabaseField//文件总长度
  private long size;

  @DatabaseField//文件的下载的状态
  private int state;

  public DataDownloadInfo() {
  }

  public DataDownloadInfo(int id, String activityid, String pid, String dowloadUrl,
      long downloadsize,
      long size, int state) {
    this.id = id;
    this.activityid = activityid;
    this.pid = pid;
    this.dowloadUrl = dowloadUrl;
    this.downloadsize = downloadsize;
    this.size = size;
    this.state = state;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getActivityid() {
    return activityid;
  }

  public void setActivityid(String activityid) {
    this.activityid = activityid;
  }

  public String getPid() {
    return pid;
  }

  public void setPid(String pid) {
    this.pid = pid;
  }

  public String getDowloadUrl() {
    return dowloadUrl;
  }

  public void setDowloadUrl(String dowloadUrl) {
    this.dowloadUrl = dowloadUrl;
  }

  public long getDownloadsize() {
    return downloadsize;
  }

  public void setDownloadsize(long downloadsize) {
    this.downloadsize = downloadsize;
  }

  public long getSize() {
    return size;
  }

  public void setSize(long size) {
    this.size = size;
  }

  public int getState() {
    return state;
  }

  public void setState(int state) {
    this.state = state;
  }

  @Override
  public String toString() {
    return "DataDownloadInfo{" +
        "id=" + id +
        ", activityid='" + activityid + '\'' +
        ", pid='" + pid + '\'' +
        ", dowloadUrl='" + dowloadUrl + '\'' +
        ", downloadsize=" + downloadsize +
        ", size=" + size +
        ", state=" + state +
        '}';
  }
}
