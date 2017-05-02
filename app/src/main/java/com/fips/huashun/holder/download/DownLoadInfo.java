package com.fips.huashun.holder.download;

/**
 * Created by kevin on 2017/2/24.
 * 下载信息类
 */

public class DownLoadInfo {
  private String url;
  private String pid;
  private String size;
  private int state;

  public DownLoadInfo(String url, String pid, String size, int state) {
    this.url = url;
    this.pid = pid;
    this.size = size;
    this.state = state;
  }

  public DownLoadInfo() {
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getPid() {
    return pid;
  }

  public void setPid(String pid) {
    this.pid = pid;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public int getState() {
    return state;
  }

  public void setState(int state) {
    this.state = state;
  }
}
