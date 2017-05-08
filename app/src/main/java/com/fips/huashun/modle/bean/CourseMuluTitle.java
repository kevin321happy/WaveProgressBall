package com.fips.huashun.modle.bean;


public class CourseMuluTitle {
  private String titlename;

  public CourseMuluTitle() {
  }

  public String getTitlename() {
    return titlename;
  }

  public void setTitlename(String titlename) {
    this.titlename = titlename;
  }

  @Override
  public String toString() {
    return "CourseMuluTitle{" +
        "titlename='" + titlename + '\'' +
        '}';
  }
}
