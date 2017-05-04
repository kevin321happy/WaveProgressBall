package com.fips.huashun.modle.testbean;


public class DepList {
//  private CheckBox mCheckBox;
//  private TextView mDepName;
//  private TextView mDepMemberNum;
  private String imapath;//头像路径
  private String name;//名字
  private String number;//部门的人数

  public DepList() {
  }

  public DepList(String imapath, String name, String number) {
    this.imapath = imapath;
    this.name = name;
    this.number = number;
  }

  public String getImapath() {
    return imapath;
  }

  public void setImapath(String imapath) {
    this.imapath = imapath;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  @Override
  public String toString() {
    return "DepList{" +
        "imapath='" + imapath + '\'' +
        ", name='" + name + '\'' +
        ", number='" + number + '\'' +
        '}';
  }
}
