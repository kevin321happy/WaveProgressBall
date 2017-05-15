package com.fips.huashun.modle.testbean;


import java.util.List;

public class SectionInfo {


  /**
   * data : {"Coursrdetail":{"collection":"4","courseId":"237","courseName":"工伤保险条例","coursePrice":"0","courseintro":"工伤保险条例","integral":"1","riokin":"6","teacherid":"0","tip":"质量管理,外来文件"},"buytype":"1","comments":[],"iscollect":"1","isriokin":"1","welltoken":"0"}
   * msg : 查询成功
   * status :
   * suc : y
   */

  private DataBean data;
  private String msg;
  private String status;
  private String suc;

  public DataBean getData() {
    return data;
  }

  public void setData(DataBean data) {
    this.data = data;
  }

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

  public static class DataBean {

    /**
     * Coursrdetail : {"collection":"4","courseId":"237","courseName":"工伤保险条例","coursePrice":"0","courseintro":"工伤保险条例","integral":"1","riokin":"6","teacherid":"0","tip":"质量管理,外来文件"}
     * buytype : 1
     * comments : []
     * iscollect : 1
     * isriokin : 1
     * welltoken : 0
     */
    private CoursrdetailBean Coursrdetail;
    private String buytype;
    private String iscollect;
    private String isriokin;
    private String welltoken;
    private List<?> comments;

    public CoursrdetailBean getCoursrdetail() {
      return Coursrdetail;
    }

    public void setCoursrdetail(CoursrdetailBean Coursrdetail) {
      this.Coursrdetail = Coursrdetail;
    }

    public String getBuytype() {
      return buytype;
    }

    public void setBuytype(String buytype) {
      this.buytype = buytype;
    }

    public String getIscollect() {
      return iscollect;
    }

    public void setIscollect(String iscollect) {
      this.iscollect = iscollect;
    }

    public String getIsriokin() {
      return isriokin;
    }

    public void setIsriokin(String isriokin) {
      this.isriokin = isriokin;
    }

    public String getWelltoken() {
      return welltoken;
    }

    public void setWelltoken(String welltoken) {
      this.welltoken = welltoken;
    }

    public List<?> getComments() {
      return comments;
    }

    public void setComments(List<?> comments) {
      this.comments = comments;
    }

    public static class CoursrdetailBean {

      /**
       * collection : 4
       * courseId : 237
       * courseName : 工伤保险条例
       * coursePrice : 0
       * courseintro : 工伤保险条例
       * integral : 1
       * riokin : 6
       * teacherid : 0
       * tip : 质量管理,外来文件
       */

      private String collection;
      private String courseId;
      private String courseName;
      private String coursePrice;
      private String courseintro;
      private String integral;
      private String riokin;
      private String teacherid;
      private String tip;

      public String getCollection() {
        return collection;
      }

      public void setCollection(String collection) {
        this.collection = collection;
      }

      public String getCourseId() {
        return courseId;
      }

      public void setCourseId(String courseId) {
        this.courseId = courseId;
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

      public String getCourseintro() {
        return courseintro;
      }

      public void setCourseintro(String courseintro) {
        this.courseintro = courseintro;
      }

      public String getIntegral() {
        return integral;
      }

      public void setIntegral(String integral) {
        this.integral = integral;
      }

      public String getRiokin() {
        return riokin;
      }

      public void setRiokin(String riokin) {
        this.riokin = riokin;
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
    }
  }
}
