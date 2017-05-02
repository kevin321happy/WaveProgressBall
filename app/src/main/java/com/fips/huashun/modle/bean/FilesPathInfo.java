package com.fips.huashun.modle.bean;

import java.io.Serializable;

/**
 * Created by kevin on 2017/3/7. 邮箱：kevin321vip@126.com 公司：锦绣氘(武汉科技有限公司)
 */

public class FilesPathInfo implements Serializable {
  private String uploadpath;

  public String getUploadpath() {
    return uploadpath;
  }

  public void setUploadpath(String uploadpath) {
    this.uploadpath = uploadpath;
  }

  public FilesPathInfo(String uploadpath) {
    this.uploadpath = uploadpath;
  }

  public FilesPathInfo() {
  }

  @Override
  public String toString() {
    return "FilesPathInfo{" +
        "uploadpath='" + uploadpath + '\'' +
        '}';
  }
}
