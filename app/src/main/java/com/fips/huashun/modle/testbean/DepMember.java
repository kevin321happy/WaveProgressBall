package com.fips.huashun.modle.testbean;


public class DepMember {
//  private CheckBox dep_checkbox;
//  private TextView tv_depname;
//  private TextView tv_station;
//  private SimpleDraweeView iv_icon;
  private String depname;
  private String station;
  private String icon_path;

  public DepMember() {
  }

  public DepMember(String depname, String station, String icon_path) {
    this.depname = depname;
    this.station = station;
    this.icon_path = icon_path;
  }

  public String getDepname() {
    return depname;
  }

  public void setDepname(String depname) {
    this.depname = depname;
  }

  public String getStation() {
    return station;
  }

  public void setStation(String station) {
    this.station = station;
  }

  public String getIcon_path() {
    return icon_path;
  }

  public void setIcon_path(String icon_path) {
    this.icon_path = icon_path;
  }

  @Override
  public String toString() {
    return "DepMember{" +
        "depname='" + depname + '\'' +
        ", station='" + station + '\'' +
        ", icon_path='" + icon_path + '\'' +
        '}';
  }
}
