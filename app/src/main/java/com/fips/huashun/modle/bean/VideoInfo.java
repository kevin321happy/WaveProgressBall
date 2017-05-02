package com.fips.huashun.modle.bean;


import com.shuyu.common.model.RecyclerBaseModel;

public class VideoInfo extends RecyclerBaseModel {
  private String path;
  private String title;
  private String totletime;
  private String thumb;//封面图片缩略图

  public VideoInfo() {
  }

  public VideoInfo(String path, String title, String totletime, String thumb) {
    this.path = path;
    this.title = title;
    this.totletime = totletime;
    this.thumb = thumb;
  }

  public String getThumb() {
    return thumb;
  }

  public void setThumb(String thumb) {
    this.thumb = thumb;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTotletime() {
    return totletime;
  }

  public void setTotletime(String totletime) {
    this.totletime = totletime;
  }

  @Override
  public String toString() {
    return "VideoInfo{" +
        "path='" + path + '\'' +
        ", title='" + title + '\'' +
        ", totletime='" + totletime + '\'' +
        ", thumb='" + thumb + '\'' +
        '}';
  }
}
