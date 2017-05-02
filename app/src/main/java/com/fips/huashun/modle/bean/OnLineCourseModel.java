package com.fips.huashun.modle.bean;

import java.util.List;

/**
 * Created by kevin on 2017/2/22. 在线学习的课程
 */

public class OnLineCourseModel {

  /**
   * row : [{"activityid":"230","explain":"是哪的房价开始克里斯丁","ext":"jpg","isdownload":"1","name":"看得见阿斯兰的骄傲","path":"http://v1.52qmct.com/test/6Snx6k4rc3.jpg","pid":"35","uploadid":"99720269"},{"activityid":"230","explain":"按时开放几十块","ext":"mp3","isdownload":"1","name":"发生的发生断电覅是","path":"http://v1.52qmct.com/test/DphzkPXHRM.mp3","pid":"36","uploadid":"99720270"},{"activityid":"230","explain":"的方式四季度看了个剪短发","ext":"xlsx","isdownload":"0","name":"方式打开","path":"http://v1.52qmct.com/test/SeWxzFSDMd.xlsx","pid":"37","uploadid":"99720271"}]
   * suc : y
   */

  private String suc;
  /**
   * activityid : 230 explain : 是哪的房价开始克里斯丁 ext : jpg isdownload : 1 name : 看得见阿斯兰的骄傲 path :
   * http://v1.52qmct.com/test/6Snx6k4rc3.jpg pid : 35 uploadid : 99720269
   */

  private List<RowBean> row;

  public String getSuc() {
    return suc;
  }

  public void setSuc(String suc) {
    this.suc = suc;
  }

  public List<RowBean> getRow() {

    return row;
  }

  public void setRow(List<RowBean> row) {
    this.row = row;
  }

  public static class RowBean {

    private String activityid;
    private String explain;
    private String ext;
    private String isdownload;
    private String name;
    private String path;
    private String pid;
    private String uploadid;
    private int state;//文件的状态
    private String size;//文件大小
    private int progerss;//进度
    private long speed;//下载速度
    private boolean isdownliading;//是否正在下载

    public boolean isdownliading() {
      return isdownliading;
    }

    public void setIsdownliading(boolean isdownliading) {
      this.isdownliading = isdownliading;
    }

    public long getSpeed() {
      return speed;
    }

    public void setSpeed(long speed) {
      this.speed = speed;
    }

    public int getState() {
      return state;
    }

    public void setState(int state) {
      this.state = state;
    }

    public int getProgerss() {
      return progerss;
    }

    public void setProgerss(int progerss) {
      this.progerss = progerss;
    }

    public String getSize() {
      return size;
    }

    public void setSize(String size) {
      this.size = size;
    }

    public String getActivityid() {
      return activityid;
    }

    public void setActivityid(String activityid) {
      this.activityid = activityid;
    }

    public String getExplain() {
      return explain;
    }

    public void setExplain(String explain) {
      this.explain = explain;
    }

    public String getExt() {
      return ext;
    }

    public void setExt(String ext) {
      this.ext = ext;
    }

    public String getIsdownload() {
      return isdownload;
    }

    public void setIsdownload(String isdownload) {
      this.isdownload = isdownload;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getPath() {
      return path;
    }

    public void setPath(String path) {
      this.path = path;
    }

    public String getPid() {
      return pid;
    }

    public void setPid(String pid) {
      this.pid = pid;
    }

    public String getUploadid() {
      return uploadid;
    }

    public void setUploadid(String uploadid) {
      this.uploadid = uploadid;
    }
  }
}
