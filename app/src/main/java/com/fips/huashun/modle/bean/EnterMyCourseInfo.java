package com.fips.huashun.modle.bean;


import java.io.Serializable;
import java.util.List;

public class EnterMyCourseInfo implements Serializable{

  /**
   * data : [{"courseId":"425","courseImage":"http://v1.52qmct.com/pic/G2TdXjFTaJ.jpg","courseInfo":"欢迎大家学习人力资源管理系列课程，本课程共20讲，全面助您解决人力资源问题。","courseIntro":"","courseName":"人力资源管理","coursePrice":"0","coursemenu":"","courseperiod":"","coursetypeid":"","coursevideo":"","industrytypeid":"","integral":"","is_must":"false","paperid":"340","price":"","result":1,"studyNum":"","teacherName":"内部讲师","teacherid":"","tip":"人力,管理","welltoken":""}]
   * msg : 查询成功
   * status :
   * suc : y
   */

  private String msg;
  private String status;
  private String suc;
  private List<EnterMyCourse> data;

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getSuc() {
    return suc;
  }

  public void setSuc(String suc) {
    this.suc = suc;
  }

  public List<EnterMyCourse> getData() {
    return data;
  }

  public void setData(List<EnterMyCourse> data) {
    this.data = data;
  }

  public static class EnterMyCourse implements Serializable{

    /**
     * courseId : 425
     * courseImage : http://v1.52qmct.com/pic/G2TdXjFTaJ.jpg
     * courseInfo : 欢迎大家学习人力资源管理系列课程，本课程共20讲，全面助您解决人力资源问题。
     * courseIntro :
     * courseName : 人力资源管理
     * coursePrice : 0
     * coursemenu :
     * courseperiod :
     * coursetypeid :
     * coursevideo :
     * industrytypeid :
     * integral :
     * is_must : false
     * paperid : 340
     * price :
     * result : 1
     * studyNum :
     * teacherName : 内部讲师
     * teacherid :
     * tip : 人力,管理
     * welltoken :
     */

    private String courseId;
    private String courseImage;
    private String courseInfo;
    private String courseIntro;
    private String courseName;
    private String coursePrice;
    private String coursemenu;
    private String courseperiod;
    private String coursetypeid;
    private String coursevideo;
    private String industrytypeid;
    private String integral;
    private String is_must;
    private String paperid;
    private String price;
    private int result;
    private String studyNum;
    private String teacherName;
    private String teacherid;
    private String tip;
    private String welltoken;

    public String getCourseId() {
      return courseId;
    }

    public void setCourseId(String courseId) {
      this.courseId = courseId;
    }

    public String getCourseImage() {
      return courseImage;
    }

    public void setCourseImage(String courseImage) {
      this.courseImage = courseImage;
    }

    public String getCourseInfo() {
      return courseInfo;
    }

    public void setCourseInfo(String courseInfo) {
      this.courseInfo = courseInfo;
    }

    public String getCourseIntro() {
      return courseIntro;
    }

    public void setCourseIntro(String courseIntro) {
      this.courseIntro = courseIntro;
    }

    public String getCourseName() {
      return courseName;
    }

    public void setCourseName(String courseName) {
      this.courseName = courseName;
    }

    public String getCoursePrice() {
      return coursePrice;
    }

    public void setCoursePrice(String coursePrice) {
      this.coursePrice = coursePrice;
    }

    public String getCoursemenu() {
      return coursemenu;
    }

    public void setCoursemenu(String coursemenu) {
      this.coursemenu = coursemenu;
    }

    public String getCourseperiod() {
      return courseperiod;
    }

    public void setCourseperiod(String courseperiod) {
      this.courseperiod = courseperiod;
    }

    public String getCoursetypeid() {
      return coursetypeid;
    }

    public void setCoursetypeid(String coursetypeid) {
      this.coursetypeid = coursetypeid;
    }

    public String getCoursevideo() {
      return coursevideo;
    }

    public void setCoursevideo(String coursevideo) {
      this.coursevideo = coursevideo;
    }

    public String getIndustrytypeid() {
      return industrytypeid;
    }

    public void setIndustrytypeid(String industrytypeid) {
      this.industrytypeid = industrytypeid;
    }

    public String getIntegral() {
      return integral;
    }

    public void setIntegral(String integral) {
      this.integral = integral;
    }

    public String getIs_must() {
      return is_must;
    }

    public void setIs_must(String is_must) {
      this.is_must = is_must;
    }

    public String getPaperid() {
      return paperid;
    }

    public void setPaperid(String paperid) {
      this.paperid = paperid;
    }

    public String getPrice() {
      return price;
    }

    public void setPrice(String price) {
      this.price = price;
    }

    public int getResult() {
      return result;
    }

    public void setResult(int result) {
      this.result = result;
    }

    public String getStudyNum() {
      return studyNum;
    }

    public void setStudyNum(String studyNum) {
      this.studyNum = studyNum;
    }

    public String getTeacherName() {
      return teacherName;
    }

    public void setTeacherName(String teacherName) {
      this.teacherName = teacherName;
    }

    public String getTeacherid() {
      return teacherid;
    }

    public void setTeacherid(String teacherid) {
      this.teacherid = teacherid;
    }

    public String getTip() {
      return tip;
    }

    public void setTip(String tip) {
      this.tip = tip;
    }

    public String getWelltoken() {
      return welltoken;
    }

    public void setWelltoken(String welltoken) {
      this.welltoken = welltoken;
    }
  }
}
